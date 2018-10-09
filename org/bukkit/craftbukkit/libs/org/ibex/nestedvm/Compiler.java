/*     */ package org.bukkit.craftbukkit.libs.org.ibex.nestedvm;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Hashtable;
/*     */ import java.util.StringTokenizer;
/*     */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF;
/*     */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF.ELFHeader;
/*     */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF.SHeader;
/*     */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF.Symbol;
/*     */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF.Symtab;
/*     */ 
/*     */ public abstract class Compiler implements Registers
/*     */ {
/*     */   ELF elf;
/*     */   final String fullClassName;
/*  20 */   String source = "unknown.mips.binary";
/*  21 */   public void setSource(String paramString) { this.source = paramString; }
/*     */   
/*     */   static class Exn extends Exception {
/*  24 */     public Exn(String paramString) { super(); }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*  29 */   boolean fastMem = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  35 */   int maxInsnPerMethod = 128;
/*     */   int maxBytesPerMethod;
/*     */   int methodMask;
/*     */   int methodShift;
/*     */   
/*     */   void maxInsnPerMethodInit() throws Compiler.Exn
/*     */   {
/*  42 */     if ((this.maxInsnPerMethod & this.maxInsnPerMethod - 1) != 0) throw new Exn("maxBytesPerMethod is not a power of two");
/*  43 */     this.maxBytesPerMethod = (this.maxInsnPerMethod * 4);
/*  44 */     this.methodMask = (this.maxBytesPerMethod - 1 ^ 0xFFFFFFFF);
/*  45 */     while (this.maxBytesPerMethod >>> this.methodShift != 1) { this.methodShift += 1;
/*     */     }
/*     */   }
/*     */   
/*  49 */   boolean pruneCases = true;
/*     */   
/*  51 */   boolean assumeTailCalls = true;
/*     */   
/*     */ 
/*  54 */   boolean debugCompiler = false;
/*     */   
/*     */ 
/*  57 */   boolean printStats = false;
/*     */   
/*     */ 
/*  60 */   boolean runtimeStats = false;
/*     */   
/*  62 */   boolean supportCall = true;
/*     */   
/*  64 */   boolean nullPointerCheck = false;
/*     */   
/*  66 */   String runtimeClass = "org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime";
/*     */   
/*  68 */   String hashClass = "java.util.Hashtable";
/*     */   
/*     */   boolean unixRuntime;
/*     */   
/*     */   boolean lessConstants;
/*     */   
/*     */   boolean singleFloat;
/*     */   
/*  76 */   int pageSize = 4096;
/*  77 */   int totalPages = 65536;
/*     */   int pageShift;
/*     */   boolean onePage;
/*     */   Hashtable jumpableAddresses;
/*     */   
/*  82 */   void pageSizeInit() throws Compiler.Exn { if ((this.pageSize & this.pageSize - 1) != 0) throw new Exn("pageSize not a multiple of two");
/*  83 */     if ((this.totalPages & this.totalPages - 1) != 0) throw new Exn("totalPages not a multiple of two");
/*  84 */     while (this.pageSize >>> this.pageShift != 1) { this.pageShift += 1;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   ELF.Symbol userInfo;
/*     */   ELF.Symbol gp;
/*     */   private boolean used;
/*     */   private static void usage()
/*     */   {
/*  94 */     System.err.println("Usage: java Compiler [-outfile output.java] [-o options] [-dumpoptions] <classname> <binary.mips>");
/*  95 */     System.err.println("-o takes mount(8) like options and can be specified multiple times");
/*  96 */     System.err.println("Available options:");
/*  97 */     for (int i = 0; i < options.length; i += 2)
/*  98 */       System.err.print(options[i] + ": " + wrapAndIndent(options[(i + 1)], 16 - options[i].length(), 18, 62));
/*  99 */     System.exit(1);
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws IOException {
/* 103 */     String str1 = null;
/* 104 */     String str2 = null;
/* 105 */     String str3 = null;
/* 106 */     String str4 = null;
/* 107 */     String str5 = null;
/* 108 */     String str6 = null;
/* 109 */     int i = 0;
/* 110 */     int j = 0;
/* 111 */     while (paramArrayOfString.length - j > 0) {
/* 112 */       if (paramArrayOfString[j].equals("-outfile")) {
/* 113 */         j++;
/* 114 */         if (j == paramArrayOfString.length) usage();
/* 115 */         str1 = paramArrayOfString[j];
/* 116 */       } else if (paramArrayOfString[j].equals("-d")) {
/* 117 */         j++;
/* 118 */         if (j == paramArrayOfString.length) usage();
/* 119 */         str2 = paramArrayOfString[j];
/* 120 */       } else if (paramArrayOfString[j].equals("-outformat")) {
/* 121 */         j++;
/* 122 */         if (j == paramArrayOfString.length) usage();
/* 123 */         str6 = paramArrayOfString[j];
/* 124 */       } else if (paramArrayOfString[j].equals("-o")) {
/* 125 */         j++;
/* 126 */         if (j == paramArrayOfString.length) usage();
/* 127 */         if ((str3 == null) || (str3.length() == 0)) {
/* 128 */           str3 = paramArrayOfString[j];
/* 129 */         } else if (paramArrayOfString[j].length() != 0)
/* 130 */           str3 = str3 + "," + paramArrayOfString[j];
/* 131 */       } else if (paramArrayOfString[j].equals("-dumpoptions")) {
/* 132 */         i = 1;
/* 133 */       } else if (str4 == null) {
/* 134 */         str4 = paramArrayOfString[j];
/* 135 */       } else if (str5 == null) {
/* 136 */         str5 = paramArrayOfString[j];
/*     */       } else {
/* 138 */         usage();
/*     */       }
/* 140 */       j++;
/*     */     }
/* 142 */     if ((str4 == null) || (str5 == null)) { usage();
/*     */     }
/* 144 */     org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable.File localFile = new org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable.File(str5);
/*     */     
/* 146 */     java.io.FileWriter localFileWriter = null;
/* 147 */     java.io.FileOutputStream localFileOutputStream = null;
/* 148 */     Object localObject1 = null;
/* 149 */     if ((str6 == null) || (str6.equals("class"))) {
/* 150 */       if (str1 != null) {
/* 151 */         localFileOutputStream = new java.io.FileOutputStream(str1);
/* 152 */         localObject1 = new ClassFileCompiler(localFile, str4, localFileOutputStream);
/* 153 */       } else if (str2 != null) {
/* 154 */         File localFile1 = new File(str2);
/* 155 */         if (!localFile1.isDirectory()) {
/* 156 */           System.err.println(str2 + " doesn't exist or is not a directory");
/* 157 */           System.exit(1);
/*     */         }
/* 159 */         localObject1 = new ClassFileCompiler(localFile, str4, localFile1);
/*     */       } else {
/* 161 */         System.err.println("Refusing to write a classfile to stdout - use -outfile foo.class");
/* 162 */         System.exit(1);
/*     */       }
/* 164 */     } else if ((str6.equals("javasource")) || (str6.equals("java"))) {
/* 165 */       localFileWriter = str1 == null ? new java.io.OutputStreamWriter(System.out) : new java.io.FileWriter(str1);
/* 166 */       localObject1 = new JavaSourceCompiler(localFile, str4, localFileWriter);
/*     */     } else {
/* 168 */       System.err.println("Unknown output format: " + str6);
/* 169 */       System.exit(1);
/*     */     }
/*     */     
/* 172 */     ((Compiler)localObject1).parseOptions(str3);
/* 173 */     ((Compiler)localObject1).setSource(str5);
/*     */     
/* 175 */     if (i != 0) {
/* 176 */       System.err.println("== Options ==");
/* 177 */       for (int k = 0; k < options.length; k += 2)
/* 178 */         System.err.println(options[k] + ": " + ((Compiler)localObject1).getOption(options[k]).get());
/* 179 */       System.err.println("== End Options ==");
/*     */     }
/*     */     try
/*     */     {
/* 183 */       ((Compiler)localObject1).go();
/*     */     } catch (Exn localExn) {
/* 185 */       System.err.println("Compiler Error: " + localExn.getMessage());
/* 186 */       System.exit(1);
/*     */     } finally {
/* 188 */       if (localFileWriter != null) localFileWriter.close();
/* 189 */       if (localFileOutputStream != null) localFileOutputStream.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public Compiler(org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable paramSeekable, String paramString) throws IOException {
/* 194 */     this.fullClassName = paramString;
/* 195 */     this.elf = new ELF(paramSeekable);
/*     */     
/* 197 */     if (this.elf.header.type != 2) throw new IOException("Binary is not an executable");
/* 198 */     if (this.elf.header.machine != 8) throw new IOException("Binary is not for the MIPS I Architecture");
/* 199 */     if (this.elf.ident.data != 2) throw new IOException("Binary is not big endian");
/*     */   }
/*     */   
/*     */   abstract void _go() throws Compiler.Exn, IOException;
/*     */   
/*     */   public void go() throws Compiler.Exn, IOException
/*     */   {
/* 206 */     if (this.used) throw new RuntimeException("Compiler instances are good for one shot only");
/* 207 */     this.used = true;
/*     */     
/* 209 */     if ((this.onePage) && (this.pageSize <= 4096)) this.pageSize = 4194304;
/* 210 */     if ((this.nullPointerCheck) && (!this.fastMem)) throw new Exn("fastMem must be enabled for nullPointerCheck to be of any use");
/* 211 */     if ((this.onePage) && (!this.fastMem)) throw new Exn("fastMem must be enabled for onePage to be of any use");
/* 212 */     if ((this.totalPages == 1) && (!this.onePage)) throw new Exn("totalPages == 1 and onePage is not set");
/* 213 */     if (this.onePage) { this.totalPages = 1;
/*     */     }
/* 215 */     maxInsnPerMethodInit();
/* 216 */     pageSizeInit();
/*     */     
/*     */ 
/* 219 */     ELF.Symtab localSymtab = this.elf.getSymtab();
/* 220 */     if (localSymtab == null) { throw new Exn("Binary has no symtab (did you strip it?)");
/*     */     }
/*     */     
/* 223 */     this.userInfo = localSymtab.getGlobalSymbol("user_info");
/* 224 */     this.gp = localSymtab.getGlobalSymbol("_gp");
/* 225 */     if (this.gp == null) { throw new Exn("no _gp symbol (did you strip the binary?)");
/*     */     }
/* 227 */     if (this.pruneCases)
/*     */     {
/* 229 */       this.jumpableAddresses = new Hashtable();
/*     */       
/* 231 */       this.jumpableAddresses.put(new Integer(this.elf.header.entry), Boolean.TRUE);
/*     */       
/* 233 */       ELF.SHeader localSHeader1 = this.elf.sectionWithName(".text");
/* 234 */       if (localSHeader1 == null) { throw new Exn("No .text segment");
/*     */       }
/* 236 */       findBranchesInSymtab(localSymtab, this.jumpableAddresses);
/*     */       
/* 238 */       for (int j = 0; j < this.elf.sheaders.length; j++) {
/* 239 */         ELF.SHeader localSHeader2 = this.elf.sheaders[j];
/* 240 */         String str2 = localSHeader2.name;
/*     */         
/* 242 */         if ((localSHeader2.addr != 0) && (
/* 243 */           (str2.equals(".data")) || (str2.equals(".sdata")) || (str2.equals(".rodata")) || (str2.equals(".ctors")) || (str2.equals(".dtors")))) {
/* 244 */           findBranchesInData(new DataInputStream(localSHeader2.getInputStream()), localSHeader2.size, this.jumpableAddresses, localSHeader1.addr, localSHeader1.addr + localSHeader1.size);
/*     */         }
/*     */       }
/* 247 */       findBranchesInText(localSHeader1.addr, new DataInputStream(localSHeader1.getInputStream()), localSHeader1.size, this.jumpableAddresses);
/*     */     }
/*     */     
/* 250 */     if ((this.unixRuntime) && (this.runtimeClass.startsWith("org.bukkit.craftbukkit.libs.org.ibex.nestedvm."))) { this.runtimeClass = "org.bukkit.craftbukkit.libs.org.ibex.nestedvm.UnixRuntime";
/*     */     }
/* 252 */     for (int i = 0; i < this.elf.sheaders.length; i++) {
/* 253 */       String str1 = this.elf.sheaders[i].name;
/* 254 */       if (((this.elf.sheaders[i].flags & 0x2) != 0) && (!str1.equals(".text")) && (!str1.equals(".data")) && (!str1.equals(".sdata")) && (!str1.equals(".rodata")) && (!str1.equals(".ctors")) && (!str1.equals(".dtors")) && (!str1.equals(".bss")) && (!str1.equals(".sbss")))
/*     */       {
/*     */ 
/* 257 */         throw new Exn("Unknown section: " + str1); }
/*     */     }
/* 259 */     _go();
/*     */   }
/*     */   
/*     */   private void findBranchesInSymtab(ELF.Symtab paramSymtab, Hashtable paramHashtable) {
/* 263 */     ELF.Symbol[] arrayOfSymbol = paramSymtab.symbols;
/* 264 */     int i = 0;
/* 265 */     for (int j = 0; j < arrayOfSymbol.length; j++) {
/* 266 */       ELF.Symbol localSymbol = arrayOfSymbol[j];
/* 267 */       if ((localSymbol.type == 2) && 
/* 268 */         (paramHashtable.put(new Integer(localSymbol.addr), Boolean.TRUE) == null))
/*     */       {
/* 270 */         i++;
/*     */       }
/*     */     }
/*     */     
/* 274 */     if (this.printStats) System.err.println("Found " + i + " additional possible branch targets in Symtab");
/*     */   }
/*     */   
/*     */   private void findBranchesInText(int paramInt1, DataInputStream paramDataInputStream, int paramInt2, Hashtable paramHashtable) throws IOException {
/* 278 */     int i = paramInt2 / 4;
/* 279 */     int j = paramInt1;
/* 280 */     int k = 0;
/* 281 */     int[] arrayOfInt1 = new int[32];
/* 282 */     int[] arrayOfInt2 = new int[32];
/*     */     
/*     */ 
/* 285 */     for (int m = 0; m < i; j += 4) {
/* 286 */       int n = paramDataInputStream.readInt();
/* 287 */       int i1 = n >>> 26 & 0xFF;
/* 288 */       int i2 = n >>> 21 & 0x1F;
/* 289 */       int i3 = n >>> 16 & 0x1F;
/* 290 */       int i4 = n << 16 >> 16;
/* 291 */       int i5 = n & 0xFFFF;
/* 292 */       int i6 = i4;
/* 293 */       int i7 = n & 0x3FFFFFF;
/* 294 */       int i8 = n & 0x3F;
/*     */       
/* 296 */       switch (i1) {
/*     */       case 0: 
/* 298 */         switch (i8) {
/*     */         case 9: 
/* 300 */           if (paramHashtable.put(new Integer(j + 8), Boolean.TRUE) == null) k++;
/*     */           break;
/*     */         case 12: 
/* 303 */           if (paramHashtable.put(new Integer(j + 4), Boolean.TRUE) == null) k++;
/*     */           break;
/*     */         }
/* 306 */         break;
/*     */       case 1: 
/* 308 */         switch (i3) {
/*     */         case 16: 
/*     */         case 17: 
/* 311 */           if (paramHashtable.put(new Integer(j + 8), Boolean.TRUE) == null) { k++;
/*     */           }
/*     */         case 0: 
/*     */         case 1: 
/* 315 */           if (paramHashtable.put(new Integer(j + i6 * 4 + 4), Boolean.TRUE) == null) k++;
/*     */           break;
/*     */         }
/* 318 */         break;
/*     */       case 3: 
/* 320 */         if (paramHashtable.put(new Integer(j + 8), Boolean.TRUE) == null) { k++;
/*     */         }
/*     */       case 2: 
/* 323 */         if (paramHashtable.put(new Integer(j & 0xF0000000 | i7 << 2), Boolean.TRUE) == null) k++;
/*     */         break;
/*     */       case 4: 
/*     */       case 5: 
/*     */       case 6: 
/*     */       case 7: 
/* 329 */         if (paramHashtable.put(new Integer(j + i6 * 4 + 4), Boolean.TRUE) == null) k++;
/*     */         break;
/*     */       case 9: 
/* 332 */         if (j - arrayOfInt2[i2] <= 128) {
/* 333 */           int i9 = (arrayOfInt1[i2] << 16) + i4;
/* 334 */           if (((i9 & 0x3) == 0) && (i9 >= paramInt1) && (i9 < paramInt1 + paramInt2) && 
/* 335 */             (paramHashtable.put(new Integer(i9), Boolean.TRUE) == null))
/*     */           {
/* 337 */             k++;
/*     */           }
/*     */           
/*     */ 
/* 341 */           if (i3 == i2) arrayOfInt2[i2] = 0; }
/* 342 */         break;
/*     */       
/*     */ 
/*     */       case 15: 
/* 346 */         arrayOfInt1[i3] = i5;
/* 347 */         arrayOfInt2[i3] = j;
/* 348 */         break;
/*     */       
/*     */ 
/*     */       case 17: 
/* 352 */         switch (i2) {
/*     */         case 8: 
/* 354 */           if (paramHashtable.put(new Integer(j + i6 * 4 + 4), Boolean.TRUE) == null) { k++;
/*     */           }
/*     */           break;
/*     */         }
/*     */         break;
/*     */       }
/* 285 */       m++;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 360 */     paramDataInputStream.close();
/* 361 */     if (this.printStats) System.err.println("Found " + k + " additional possible branch targets in Text segment");
/*     */   }
/*     */   
/*     */   private void findBranchesInData(DataInputStream paramDataInputStream, int paramInt1, Hashtable paramHashtable, int paramInt2, int paramInt3) throws IOException {
/* 365 */     int i = paramInt1 / 4;
/* 366 */     int j = 0;
/* 367 */     for (int k = 0; k < i; k++) {
/* 368 */       int m = paramDataInputStream.readInt();
/* 369 */       if (((m & 0x3) == 0) && (m >= paramInt2) && (m < paramInt3) && 
/* 370 */         (paramHashtable.put(new Integer(m), Boolean.TRUE) == null))
/*     */       {
/* 372 */         j++;
/*     */       }
/*     */     }
/*     */     
/* 376 */     paramDataInputStream.close();
/* 377 */     if ((j > 0) && (this.printStats)) { System.err.println("Found " + j + " additional possible branch targets in Data segment");
/*     */     }
/*     */   }
/*     */   
/* 381 */   static final String toHex(int paramInt) { return "0x" + Long.toString(paramInt & 0xFFFFFFFF, 16); }
/*     */   
/* 383 */   static final String toHex8(int paramInt) { String str = Long.toString(paramInt & 0xFFFFFFFF, 16);
/* 384 */     StringBuffer localStringBuffer = new StringBuffer("0x");
/* 385 */     for (int i = 8 - str.length(); i > 0; i--) localStringBuffer.append('0');
/* 386 */     localStringBuffer.append(str);
/* 387 */     return localStringBuffer.toString();
/*     */   }
/*     */   
/*     */   static final String toOctal3(int paramInt) {
/* 391 */     char[] arrayOfChar = new char[3];
/* 392 */     for (int i = 2; i >= 0; i--) {
/* 393 */       arrayOfChar[i] = ((char)(48 + (paramInt & 0x7)));
/* 394 */       paramInt >>= 3;
/*     */     }
/* 396 */     return new String(arrayOfChar);
/*     */   }
/*     */   
/*     */   private class Option {
/*     */     private Field field;
/*     */     
/* 402 */     public Option(String paramString) throws NoSuchFieldException { this.field = (paramString == null ? null : Compiler.class.getDeclaredField(paramString)); }
/*     */     
/* 404 */     public void set(Object paramObject) { if (this.field == null) return;
/*     */       try
/*     */       {
/* 407 */         this.field.set(Compiler.this, paramObject);
/*     */       } catch (IllegalAccessException localIllegalAccessException) {
/* 409 */         System.err.println(localIllegalAccessException);
/*     */       }
/*     */     }
/*     */     
/* 413 */     public Object get() { if (this.field == null) return null;
/*     */       try
/*     */       {
/* 416 */         return this.field.get(Compiler.this);
/*     */       } catch (IllegalAccessException localIllegalAccessException) {
/* 418 */         System.err.println(localIllegalAccessException); } return null;
/*     */     }
/*     */     
/* 421 */     public Class getType() { return this.field == null ? null : this.field.getType(); }
/*     */   }
/*     */   
/* 424 */   private static String[] options = { "fastMem", "Enable fast memory access - RuntimeExceptions will be thrown on faults", "nullPointerCheck", "Enables checking at runtime for null pointer accessses (slows things down a bit, only applicable with fastMem)", "maxInsnPerMethod", "Maximum number of MIPS instructions per java method (128 is optimal with Hotspot)", "pruneCases", "Remove unnecessary case 0xAABCCDD blocks from methods - may break some weird code", "assumeTailCalls", "Assume the JIT optimizes tail calls", "optimizedMemcpy", "Use an optimized java version of memcpy where possible", "debugCompiler", "Output information in the generated code for debugging the compiler - will slow down generated code significantly", "printStats", "Output some useful statistics about the compilation", "runtimeStats", "Keep track of some statistics at runtime in the generated code - will slow down generated code significantly", "supportCall", "Keep a stripped down version of the symbol table in the generated code to support the call() method", "runtimeClass", "Full classname of the Runtime class (default: Runtime) - use this is you put Runtime in a package", "hashClass", "Full classname of a Hashtable class (default: java.util.HashMap) - this must support get() and put()", "unixRuntime", "Use the UnixRuntime (has support for fork, wai, du, pipe, etc)", "pageSize", "The page size (must be a power of two)", "totalPages", "Total number of pages (total mem = pageSize*totalPages, must be a power of two)", "onePage", "One page hack (FIXME: document this better)", "lessConstants", "Use less constants at the cost of speed (FIXME: document this better)", "singleFloat", "Support single precision (32-bit) FP ops only" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Option getOption(String paramString)
/*     */   {
/* 446 */     paramString = paramString.toLowerCase();
/*     */     try {
/* 448 */       for (int i = 0; i < options.length; i += 2)
/* 449 */         if (options[i].toLowerCase().equals(paramString))
/* 450 */           return new Option(options[i]);
/* 451 */       return null;
/*     */     } catch (NoSuchFieldException localNoSuchFieldException) {}
/* 453 */     return null;
/*     */   }
/*     */   
/*     */   public void parseOptions(String paramString)
/*     */   {
/* 458 */     if ((paramString == null) || (paramString.length() == 0)) return;
/* 459 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
/* 460 */     while (localStringTokenizer.hasMoreElements()) {
/* 461 */       String str1 = localStringTokenizer.nextToken();
/*     */       String str2;
/*     */       String str3;
/* 464 */       if (str1.indexOf("=") != -1) {
/* 465 */         str2 = str1.substring(0, str1.indexOf("="));
/* 466 */         str3 = str1.substring(str1.indexOf("=") + 1);
/* 467 */       } else if (str1.startsWith("no")) {
/* 468 */         str2 = str1.substring(2);
/* 469 */         str3 = "false";
/*     */       } else {
/* 471 */         str2 = str1;
/* 472 */         str3 = "true";
/*     */       }
/* 474 */       Option localOption = getOption(str2);
/* 475 */       if (localOption == null) {
/* 476 */         System.err.println("WARNING: No such option: " + str2);
/*     */ 
/*     */ 
/*     */       }
/* 480 */       else if (localOption.getType() == String.class) {
/* 481 */         localOption.set(str3);
/* 482 */       } else if (localOption.getType() == Integer.TYPE) {
/*     */         try {
/* 484 */           localOption.set(parseInt(str3));
/*     */         } catch (NumberFormatException localNumberFormatException) {
/* 486 */           System.err.println("WARNING: " + str3 + " is not an integer");
/*     */         }
/* 488 */       } else if (localOption.getType() == Boolean.TYPE) {
/* 489 */         localOption.set(new Boolean((str3.toLowerCase().equals("true")) || (str3.toLowerCase().equals("yes"))));
/*     */       } else
/* 491 */         throw new Error("Unknown type: " + localOption.getType());
/*     */     }
/*     */   }
/*     */   
/*     */   private static Integer parseInt(String paramString) {
/* 496 */     int i = 1;
/* 497 */     paramString = paramString.toLowerCase();
/* 498 */     if ((!paramString.startsWith("0x")) && (paramString.endsWith("m"))) { paramString = paramString.substring(0, paramString.length() - 1);i = 1048576;
/* 499 */     } else if ((!paramString.startsWith("0x")) && (paramString.endsWith("k"))) { paramString = paramString.substring(0, paramString.length() - 1);i = 1024; }
/*     */     int j;
/* 501 */     if ((paramString.length() > 2) && (paramString.startsWith("0x"))) j = Integer.parseInt(paramString.substring(2), 16); else
/* 502 */       j = Integer.parseInt(paramString);
/* 503 */     return new Integer(j * i);
/*     */   }
/*     */   
/*     */   private static String wrapAndIndent(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 507 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, " ");
/* 508 */     StringBuffer localStringBuffer = new StringBuffer();
/* 509 */     for (int i = 0; i < paramInt1; i++)
/* 510 */       localStringBuffer.append(' ');
/* 511 */     i = 0;
/* 512 */     while (localStringTokenizer.hasMoreTokens()) {
/* 513 */       String str = localStringTokenizer.nextToken();
/* 514 */       if ((str.length() + i + 1 > paramInt3) && (i > 0)) {
/* 515 */         localStringBuffer.append('\n');
/* 516 */         for (int j = 0; j < paramInt2; j++) localStringBuffer.append(' ');
/* 517 */         i = 0;
/* 518 */       } else if (i > 0) {
/* 519 */         localStringBuffer.append(' ');
/* 520 */         i++;
/*     */       }
/* 522 */       localStringBuffer.append(str);
/* 523 */       i += str.length();
/*     */     }
/* 525 */     localStringBuffer.append('\n');
/* 526 */     return localStringBuffer.toString();
/*     */   }
/*     */   
/*     */   static String dateTime()
/*     */   {
/*     */     try
/*     */     {
/* 533 */       return new java.util.Date().toString();
/*     */     } catch (RuntimeException localRuntimeException) {}
/* 535 */     return "<unknown>";
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\org\ibex\nestedvm\Compiler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */