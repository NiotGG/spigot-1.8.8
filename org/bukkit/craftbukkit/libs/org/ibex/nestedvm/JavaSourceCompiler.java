/*     */ package org.bukkit.craftbukkit.libs.org.ibex.nestedvm;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.HashMap;
/*     */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF;
/*     */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF.SHeader;
/*     */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF.Symbol;
/*     */ 
/*     */ public class JavaSourceCompiler extends Compiler
/*     */ {
/*  13 */   private StringBuffer runs = new StringBuffer();
/*     */   
/*  15 */   private StringBuffer inits = new StringBuffer();
/*     */   
/*  17 */   private StringBuffer classLevel = new StringBuffer();
/*     */   
/*     */   private PrintWriter out;
/*     */   
/*     */   private int indent;
/*     */   
/*  23 */   private void p() { this.out.println(); }
/*     */   
/*  25 */   private void p(String paramString) { this.out.println(indents[this.indent] + paramString); }
/*  26 */   private void pblock(StringBuffer paramStringBuffer) { this.out.print(paramStringBuffer.toString()); }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  31 */   private static String[] indents = new String[16];
/*  32 */   static { String str = ""; for (int i = 0; i < indents.length; str = str + "    ") { indents[i] = str;i++;
/*     */     } }
/*     */   
/*  35 */   public JavaSourceCompiler(org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable paramSeekable, String paramString, java.io.Writer paramWriter) throws IOException { super(paramSeekable, paramString);
/*  36 */     this.out = new PrintWriter(paramWriter);
/*     */   }
/*     */   
/*     */   protected void _go() throws Compiler.Exn, IOException {
/*  40 */     if (this.singleFloat) throw new Compiler.Exn("JavaSourceCompiler doesn't support singleFloat");
/*     */     String str1;
/*     */     String str2;
/*  43 */     if (this.fullClassName.indexOf('.') != -1) {
/*  44 */       str1 = this.fullClassName.substring(0, this.fullClassName.lastIndexOf('.'));
/*  45 */       str2 = this.fullClassName.substring(this.fullClassName.lastIndexOf('.') + 1);
/*     */     } else {
/*  47 */       str2 = this.fullClassName;
/*  48 */       str1 = null;
/*     */     }
/*     */     
/*  51 */     p("/* This file was generated from " + this.source + " by Mips2Java on " + dateTime() + " */");
/*  52 */     if (str1 != null) p("package " + str1 + ";");
/*  53 */     if (this.runtimeStats) p("import java.util.*;");
/*  54 */     p();
/*  55 */     p("public final class " + str2 + " extends " + this.runtimeClass + " {");
/*  56 */     this.indent += 1;
/*     */     
/*  58 */     p("/* program counter */");
/*  59 */     p("private int pc = 0;");
/*  60 */     if (this.debugCompiler)
/*  61 */       p("private int lastPC = 0;");
/*  62 */     p();
/*  63 */     p("/* General Purpose registers */");
/*  64 */     p("private final static int r0 = 0;");
/*  65 */     p("private int      r1,  r2,  r3,  r4,  r5,  r6,  r7,");
/*  66 */     p("            r8,  r9,  r10, r11, r12, r13, r14, r15,");
/*  67 */     p("            r16, r17, r18, r19, r20, r21, r22, r23,");
/*  68 */     p("            r24, r25, r26, r27, r28, r29, r30, r31,");
/*  69 */     p("            hi = 0, lo = 0;");
/*  70 */     p("/* FP registers */");
/*  71 */     p("private int f0,  f1,  f2,  f3,  f4,  f5,  f6,  f7,");
/*  72 */     p("            f8,  f9,  f10, f11, f12, f13, f14, f15,");
/*  73 */     p("            f16, f17, f18, f19, f20, f21, f22, f23,");
/*  74 */     p("            f24, f25, f26, f27, f28, f29, f30, f31;");
/*  75 */     p("/* FP Control Register */");
/*  76 */     p("private int fcsr = 0;");
/*  77 */     p();
/*     */     
/*  79 */     if (this.onePage) { p("private final int[] page = readPages[0];");
/*     */     }
/*     */     
/*  82 */     int i = 0;
/*     */     Object localObject;
/*  84 */     for (int j = 0; j < this.elf.sheaders.length; j++) {
/*  85 */       ELF.SHeader localSHeader = this.elf.sheaders[j];
/*  86 */       localObject = localSHeader.name;
/*     */       
/*  88 */       if (localSHeader.addr != 0)
/*     */       {
/*  90 */         i = Math.max(i, localSHeader.addr + localSHeader.size);
/*     */         
/*  92 */         if (((String)localObject).equals(".text")) {
/*  93 */           emitText(localSHeader.addr, new DataInputStream(localSHeader.getInputStream()), localSHeader.size);
/*  94 */         } else if ((((String)localObject).equals(".data")) || (((String)localObject).equals(".sdata")) || (((String)localObject).equals(".rodata")) || (((String)localObject).equals(".ctors")) || (((String)localObject).equals(".dtors"))) {
/*  95 */           emitData(localSHeader.addr, new DataInputStream(localSHeader.getInputStream()), localSHeader.size, ((String)localObject).equals(".rodata"));
/*  96 */         } else if ((((String)localObject).equals(".bss")) || (((String)localObject).equals(".sbss"))) {
/*  97 */           emitBSS(localSHeader.addr, localSHeader.size);
/*     */         } else
/*  99 */           throw new Compiler.Exn("Unknown segment: " + (String)localObject);
/*     */       } }
/* 101 */     p();
/*     */     
/* 103 */     pblock(this.classLevel);
/* 104 */     p();
/*     */     
/*     */ 
/* 107 */     p("private final void trampoline() throws ExecutionException {");
/* 108 */     this.indent += 1;
/* 109 */     p("while(state == RUNNING) {");
/* 110 */     this.indent += 1;
/* 111 */     p("switch(pc>>>" + this.methodShift + ") {");
/*     */     
/* 113 */     this.indent += 1;
/* 114 */     pblock(this.runs);
/* 115 */     p("default: throw new ExecutionException(\"invalid address 0x\" + Long.toString(this.pc&0xffffffffL,16) + \": r2: \" + r2);");
/* 116 */     this.indent -= 1;p("}");
/* 117 */     this.indent -= 1;p("}");
/* 118 */     this.indent -= 1;p("}");
/* 119 */     p();
/*     */     
/*     */ 
/* 122 */     p("public " + str2 + "() {");
/* 123 */     this.indent += 1;
/* 124 */     p("super(" + this.pageSize + "," + this.totalPages + ");");
/* 125 */     pblock(this.inits);
/* 126 */     this.indent -= 1;
/* 127 */     p("}");
/* 128 */     p();
/*     */     
/* 130 */     p("protected int entryPoint() { return " + toHex(this.elf.header.entry) + "; }");
/* 131 */     p("protected int heapStart() { return " + toHex(i) + "; }");
/* 132 */     p("protected int gp() { return " + toHex(this.gp.addr) + "; }");
/* 133 */     if (this.userInfo != null) {
/* 134 */       p("protected int userInfoBase() { return " + toHex(this.userInfo.addr) + "; }");
/* 135 */       p("protected int userInfoSize() { return " + toHex(this.userInfo.size) + "; }");
/*     */     }
/*     */     
/*     */ 
/* 139 */     p("public static void main(String[] args) throws Exception {");
/* 140 */     this.indent += 1;
/* 141 */     p("" + str2 + " me = new " + str2 + "();");
/* 142 */     p("int status = me.run(\"" + this.fullClassName + "\",args);");
/* 143 */     if (this.runtimeStats) p("me.printStats();");
/* 144 */     p("System.exit(status);");
/* 145 */     this.indent -= 1;
/* 146 */     p("}");
/* 147 */     p();
/*     */     
/*     */ 
/* 150 */     p("protected void _execute() throws ExecutionException { trampoline(); }");
/* 151 */     p();
/*     */     
/* 153 */     p("protected void setCPUState(CPUState state) {");
/* 154 */     this.indent += 1;
/* 155 */     for (j = 1; j < 32; j++) p("r" + j + "=state.r[" + j + "];");
/* 156 */     for (j = 0; j < 32; j++) p("f" + j + "=state.f[" + j + "];");
/* 157 */     p("hi=state.hi; lo=state.lo; fcsr=state.fcsr;");
/* 158 */     p("pc=state.pc;");
/* 159 */     this.indent -= 1;
/* 160 */     p("}");
/* 161 */     p("protected void getCPUState(CPUState state) {");
/* 162 */     this.indent += 1;
/* 163 */     for (j = 1; j < 32; j++) p("state.r[" + j + "]=r" + j + ";");
/* 164 */     for (j = 0; j < 32; j++) p("state.f[" + j + "]=f" + j + ";");
/* 165 */     p("state.hi=hi; state.lo=lo; state.fcsr=fcsr;");
/* 166 */     p("state.pc=pc;");
/* 167 */     this.indent -= 1;
/* 168 */     p("}");
/* 169 */     p();
/*     */     
/* 171 */     if (this.supportCall) {
/* 172 */       p("private static final " + this.hashClass + " symbols = new " + this.hashClass + "();");
/* 173 */       p("static {");
/* 174 */       this.indent += 1;
/* 175 */       ELF.Symbol[] arrayOfSymbol = this.elf.getSymtab().symbols;
/* 176 */       for (int k = 0; k < arrayOfSymbol.length; k++) {
/* 177 */         localObject = arrayOfSymbol[k];
/* 178 */         if ((((ELF.Symbol)localObject).type == 2) && (((ELF.Symbol)localObject).binding == 1) && ((((ELF.Symbol)localObject).name.equals("_call_helper")) || (!((ELF.Symbol)localObject).name.startsWith("_"))))
/* 179 */           p("symbols.put(\"" + ((ELF.Symbol)localObject).name + "\",new Integer(" + toHex(((ELF.Symbol)localObject).addr) + "));");
/*     */       }
/* 181 */       this.indent -= 1;
/* 182 */       p("}");
/* 183 */       p("public int lookupSymbol(String symbol) { Integer i = (Integer) symbols.get(symbol); return i==null ? -1 : i.intValue(); }");
/* 184 */       p();
/*     */     }
/*     */     
/*     */ 
/* 188 */     if (this.runtimeStats) {
/* 189 */       p("private HashMap counters = new HashMap();");
/* 190 */       p("private void inc(String k) { Long i = (Long)counters.get(k); counters.put(k,new Long(i==null ? 1 : i.longValue() + 1)); }");
/* 191 */       p("private void printStats() {");
/* 192 */       p(" Iterator i = new TreeSet(counters.keySet()).iterator();");
/* 193 */       p(" while(i.hasNext()) { Object o = i.next(); System.err.println(\"\" + o + \": \" + counters.get(o)); }");
/* 194 */       p("}");
/* 195 */       p();
/*     */     }
/*     */     
/* 198 */     this.indent -= 1;
/* 199 */     p("}");
/*     */   }
/*     */   
/* 202 */   private int startOfMethod = 0;
/* 203 */   private int endOfMethod = 0;
/*     */   
/*     */   private void startMethod(int paramInt) {
/* 206 */     paramInt &= (this.maxBytesPerMethod - 1 ^ 0xFFFFFFFF);
/* 207 */     this.startOfMethod = paramInt;
/* 208 */     this.endOfMethod = (paramInt + this.maxBytesPerMethod);
/* 209 */     String str = "run_" + Long.toString(paramInt & 0xFFFFFFFF, 16);
/* 210 */     this.runs.append(indents[4] + "case " + toHex(paramInt >>> this.methodShift) + ": " + str + "(); break; \n");
/*     */     
/*     */ 
/* 213 */     p("private final void " + str + "() throws ExecutionException { /" + "* " + toHex(paramInt) + " - " + toHex(this.endOfMethod) + " *" + "/");
/* 214 */     this.indent += 1;
/* 215 */     p("int addr, tmp;");
/* 216 */     p("for(;;) {");
/* 217 */     this.indent += 1;
/* 218 */     p("switch(pc) {");
/* 219 */     this.indent += 1;
/*     */   }
/*     */   
/* 222 */   private void endMethod() { endMethod(this.endOfMethod); }
/*     */   
/* 224 */   private void endMethod(int paramInt) { if (this.startOfMethod == 0) { return;
/*     */     }
/*     */     
/* 227 */     p("case " + toHex(paramInt) + ":");
/* 228 */     this.indent += 1;
/* 229 */     p("pc=" + constant(paramInt) + ";");
/* 230 */     leaveMethod();
/* 231 */     this.indent -= 1;
/* 232 */     if (this.debugCompiler) {
/* 233 */       p("default: throw new ExecutionException(\"invalid address 0x\" + Long.toString(pc&0xffffffffL,16)  + \" (got here from 0x\" + Long.toString(lastPC&0xffffffffL,16)+\")\");");
/*     */     } else
/* 235 */       p("default: throw new ExecutionException(\"invalid address 0x\" + Long.toString(pc&0xffffffffL,16));");
/* 236 */     this.indent -= 1;
/* 237 */     p("}");
/* 238 */     p("/* NOT REACHED */");
/* 239 */     this.indent -= 1;
/* 240 */     p("}");
/* 241 */     this.indent -= 1;
/* 242 */     p("}");
/* 243 */     this.endOfMethod = (this.startOfMethod = 0);
/*     */   }
/*     */   
/* 246 */   private HashMap relativeAddrs = new HashMap();
/*     */   
/* 248 */   private String constant(int paramInt) { if ((paramInt >= 4096) && (this.lessConstants)) {
/* 249 */       int i = paramInt & 0xFC00;
/* 250 */       String str = "N_" + toHex8(i);
/* 251 */       if (this.relativeAddrs.get(new Integer(i)) == null) {
/* 252 */         this.relativeAddrs.put(new Integer(i), Boolean.TRUE);
/* 253 */         this.classLevel.append(indents[1] + "private static int " + str + " = " + toHex(i) + ";\n");
/*     */       }
/* 255 */       return "(" + str + " + " + toHex(paramInt - i) + ")";
/*     */     }
/* 257 */     return toHex(paramInt);
/*     */   }
/*     */   
/*     */   private boolean textDone;
/*     */   private void branch(int paramInt1, int paramInt2) {
/* 262 */     if (this.debugCompiler) p("lastPC = " + toHex(paramInt1) + ";");
/* 263 */     p("pc=" + constant(paramInt2) + ";");
/* 264 */     if (paramInt2 == 0) {
/* 265 */       p("throw new ExecutionException(\"Branch to addr 0x0\");");
/* 266 */     } else if ((paramInt1 & this.methodMask) == (paramInt2 & this.methodMask)) {
/* 267 */       p("continue;");
/* 268 */     } else if (this.assumeTailCalls) {
/* 269 */       p("run_" + Long.toString(paramInt2 & this.methodMask & 0xFFFFFFFF, 16) + "(); return;");
/*     */     } else
/* 271 */       leaveMethod();
/*     */   }
/*     */   
/*     */   private void leaveMethod() {
/* 275 */     p("return;");
/*     */   }
/*     */   
/*     */   private void emitText(int paramInt1, DataInputStream paramDataInputStream, int paramInt2) throws Compiler.Exn, IOException
/*     */   {
/* 280 */     if (this.textDone) throw new Compiler.Exn("Multiple text segments");
/* 281 */     this.textDone = true;
/*     */     
/* 283 */     if (((paramInt1 & 0x3) != 0) || ((paramInt2 & 0x3) != 0)) throw new Compiler.Exn("Section on weird boundaries");
/* 284 */     int i = paramInt2 / 4;
/* 285 */     int j = paramDataInputStream.readInt();
/* 286 */     if (j == -1) { throw new Error("Actually read -1 at " + toHex(paramInt1));
/*     */     }
/*     */     
/* 289 */     for (int k = 0; k < i; paramInt1 += 4) {
/* 290 */       int m = j;
/* 291 */       j = k == i - 1 ? -1 : paramDataInputStream.readInt();
/* 292 */       if (paramInt1 >= this.endOfMethod) { endMethod();startMethod(paramInt1); }
/* 293 */       if ((this.jumpableAddresses == null) || (paramInt1 == this.startOfMethod) || (this.jumpableAddresses.get(new Integer(paramInt1)) != null)) {
/* 294 */         p("case " + toHex(paramInt1) + ":");
/* 295 */         this.unreachable = false;
/* 296 */       } else { if (this.unreachable)
/*     */           break label303;
/* 298 */         if (this.debugCompiler)
/* 299 */           p("/* pc = " + toHex(paramInt1) + "*" + "/");
/*     */       }
/* 301 */       this.indent += 1;
/* 302 */       emitInstruction(paramInt1, m, j);
/* 303 */       this.indent -= 1;
/*     */       label303:
/* 289 */       k++;
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
/* 305 */     endMethod(paramInt1);
/* 306 */     p();
/* 307 */     paramDataInputStream.close();
/*     */   }
/*     */   
/* 310 */   private int initDataCount = 0;
/*     */   
/* 312 */   private void emitData(int paramInt1, DataInputStream paramDataInputStream, int paramInt2, boolean paramBoolean) throws Compiler.Exn, IOException { if (((paramInt1 & 0x3) != 0) || ((paramInt2 & 0x3) != 0)) throw new Compiler.Exn("Data section on weird boundaries");
/* 313 */     int i = paramInt1 + paramInt2;
/* 314 */     while (paramInt1 < i) {
/* 315 */       int j = Math.min(paramInt2, 28000);
/* 316 */       StringBuffer localStringBuffer = new StringBuffer();
/* 317 */       for (int k = 0; k < j; k += 7) {
/* 318 */         long l = 0L;
/* 319 */         char c; for (int m = 0; m < 7; m++) {
/* 320 */           l <<= 8;
/* 321 */           c = k + m < paramInt2 ? paramDataInputStream.readByte() : '\001';
/* 322 */           l |= c & 0xFF;
/*     */         }
/* 324 */         for (m = 0; m < 8; m++) {
/* 325 */           c = (char)(int)(l >>> 7 * (7 - m) & 0x7F);
/* 326 */           if (c == '\n') { localStringBuffer.append("\\n");
/* 327 */           } else if (c == '\r') { localStringBuffer.append("\\r");
/* 328 */           } else if (c == '\\') { localStringBuffer.append("\\\\");
/* 329 */           } else if (c == '"') { localStringBuffer.append("\\\"");
/* 330 */           } else if ((c >= ' ') && (c <= '~')) localStringBuffer.append(c); else
/* 331 */             localStringBuffer.append("\\" + toOctal3(c));
/*     */         }
/*     */       }
/* 334 */       String str = "_data" + ++this.initDataCount;
/* 335 */       p("private static final int[] " + str + " = decodeData(\"" + localStringBuffer.toString() + "\"," + toHex(j / 4) + ");");
/* 336 */       this.inits.append(indents[2] + "initPages(" + str + "," + toHex(paramInt1) + "," + (paramBoolean ? "true" : "false") + ");\n");
/* 337 */       paramInt1 += j;
/* 338 */       paramInt2 -= j;
/*     */     }
/* 340 */     paramDataInputStream.close();
/*     */   }
/*     */   
/*     */   private void emitBSS(int paramInt1, int paramInt2) throws Compiler.Exn {
/* 344 */     if ((paramInt1 & 0x3) != 0) throw new Compiler.Exn("BSS section on weird boundaries");
/* 345 */     paramInt2 = paramInt2 + 3 & 0xFFFFFFFC;
/* 346 */     int i = paramInt2 / 4;
/* 347 */     this.inits.append(indents[2] + "clearPages(" + toHex(paramInt1) + "," + toHex(i) + ");\n");
/*     */   }
/*     */   
/*     */ 
/* 351 */   private boolean unreachable = false;
/*     */   
/*     */   private void emitInstruction(int paramInt1, int paramInt2, int paramInt3) throws IOException, Compiler.Exn {
/* 354 */     if (paramInt2 == -1) { throw new Error("insn is -1");
/*     */     }
/* 356 */     int i = paramInt2 >>> 26 & 0xFF;
/* 357 */     int j = paramInt2 >>> 21 & 0x1F;
/* 358 */     int k = paramInt2 >>> 16 & 0x1F;
/* 359 */     int m = paramInt2 >>> 16 & 0x1F;
/* 360 */     int n = paramInt2 >>> 11 & 0x1F;
/* 361 */     int i1 = paramInt2 >>> 11 & 0x1F;
/* 362 */     int i2 = paramInt2 >>> 6 & 0x1F;
/* 363 */     int i3 = paramInt2 >>> 6 & 0x1F;
/* 364 */     int i4 = paramInt2 & 0x3F;
/*     */     
/* 366 */     int i5 = paramInt2 & 0x3FFFFFF;
/* 367 */     int i6 = paramInt2 & 0xFFFF;
/* 368 */     int i7 = paramInt2 << 16 >> 16;
/* 369 */     int i8 = i7;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 375 */     if (paramInt1 == -1) { p("/* Next insn is delay slot */ ");
/*     */     }
/* 377 */     if ((this.runtimeStats) && (i != 0)) p("inc(\"opcode: " + i + "\");");
/* 378 */     switch (i) {
/*     */     case 0: 
/* 380 */       if ((this.runtimeStats) && (paramInt2 != 0)) p("inc(\"opcode: 0/" + i4 + "\");");
/* 381 */       switch (i4) {
/*     */       case 0: 
/* 383 */         if (paramInt2 != 0)
/* 384 */           p("r" + n + " = r" + k + " << " + i2 + ";");
/*     */         break;
/*     */       case 2: 
/* 387 */         p("r" + n + " = r" + k + " >>> " + i2 + ";");
/* 388 */         break;
/*     */       case 3: 
/* 390 */         p("r" + n + " = r" + k + " >> " + i2 + ";");
/* 391 */         break;
/*     */       case 4: 
/* 393 */         p("r" + n + " = r" + k + " << (r" + j + "&0x1f);");
/* 394 */         break;
/*     */       case 6: 
/* 396 */         p("r" + n + " = r" + k + " >>> (r" + j + "&0x1f);");
/* 397 */         break;
/*     */       case 7: 
/* 399 */         p("r" + n + " = r" + k + " >> (r" + j + "&0x1f);");
/* 400 */         break;
/*     */       case 8: 
/* 402 */         if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
/* 403 */         emitInstruction(-1, paramInt3, -1);
/* 404 */         if (this.debugCompiler) p("lastPC = " + toHex(paramInt1) + ";");
/* 405 */         p("pc=r" + j + ";");
/* 406 */         leaveMethod();
/* 407 */         this.unreachable = true;
/* 408 */         break;
/*     */       case 9: 
/* 410 */         if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
/* 411 */         emitInstruction(-1, paramInt3, -1);
/* 412 */         if (this.debugCompiler) p("lastPC = " + toHex(paramInt1) + ";");
/* 413 */         p("pc=r" + j + ";");
/* 414 */         p("r31=" + constant(paramInt1 + 8) + ";");
/* 415 */         leaveMethod();
/* 416 */         this.unreachable = true;
/* 417 */         break;
/*     */       case 12: 
/* 419 */         p("pc = " + toHex(paramInt1) + ";");
/* 420 */         p("r2 = syscall(r2,r4,r5,r6,r7,r8,r9);");
/* 421 */         p("if (state != RUNNING) {");
/* 422 */         this.indent += 1;
/* 423 */         p("pc = " + toHex(paramInt1 + 4) + ";");
/* 424 */         leaveMethod();
/* 425 */         this.indent -= 1;
/* 426 */         p("}");
/* 427 */         break;
/*     */       case 13: 
/* 429 */         p("throw new ExecutionException(\"Break\");");
/* 430 */         this.unreachable = true;
/* 431 */         break;
/*     */       case 16: 
/* 433 */         p("r" + n + " = hi;");
/* 434 */         break;
/*     */       case 17: 
/* 436 */         p("hi = r" + j + ";");
/* 437 */         break;
/*     */       case 18: 
/* 439 */         p("r" + n + " = lo;");
/* 440 */         break;
/*     */       case 19: 
/* 442 */         p("lo = r" + j + ";");
/* 443 */         break;
/*     */       case 24: 
/* 445 */         p("{ long hilo = (long)(r" + j + ") * ((long)r" + k + "); " + "hi = (int) (hilo >>> 32); " + "lo = (int) hilo; }");
/*     */         
/*     */ 
/* 448 */         break;
/*     */       case 25: 
/* 450 */         p("{ long hilo = (r" + j + " & 0xffffffffL) * (r" + k + " & 0xffffffffL); " + "hi = (int) (hilo >>> 32); " + "lo = (int) hilo; } ");
/*     */         
/*     */ 
/* 453 */         break;
/*     */       case 26: 
/* 455 */         p("hi = r" + j + "%r" + k + "; lo = r" + j + "/r" + k + ";");
/* 456 */         break;
/*     */       case 27: 
/* 458 */         p("if(r" + k + "!=0) {");
/* 459 */         p("hi = (int)((r" + j + " & 0xffffffffL) % (r" + k + " & 0xffffffffL)); " + "lo = (int)((r" + j + " & 0xffffffffL) / (r" + k + " & 0xffffffffL));");
/*     */         
/* 461 */         p("}");
/* 462 */         break;
/*     */       case 32: 
/* 464 */         throw new Compiler.Exn("ADD (add with oveflow trap) not suported");
/*     */       
/*     */ 
/*     */ 
/*     */       case 33: 
/* 469 */         p("r" + n + " = r" + j + " + r" + k + ";");
/* 470 */         break;
/*     */       case 34: 
/* 472 */         throw new Compiler.Exn("SUB (add with oveflow trap) not suported");
/*     */       
/*     */ 
/*     */ 
/*     */       case 35: 
/* 477 */         p("r" + n + " = r" + j + " - r" + k + ";");
/* 478 */         break;
/*     */       case 36: 
/* 480 */         p("r" + n + " = r" + j + " & r" + k + ";");
/* 481 */         break;
/*     */       case 37: 
/* 483 */         p("r" + n + " = r" + j + " | r" + k + ";");
/* 484 */         break;
/*     */       case 38: 
/* 486 */         p("r" + n + " = r" + j + " ^ r" + k + ";");
/* 487 */         break;
/*     */       case 39: 
/* 489 */         p("r" + n + " = ~(r" + j + " | r" + k + ");");
/* 490 */         break;
/*     */       case 42: 
/* 492 */         p("r" + n + " = r" + j + " < r" + k + " ? 1 : 0;");
/* 493 */         break;
/*     */       case 43: 
/* 495 */         p("r" + n + " = ((r" + j + " & 0xffffffffL) < (r" + k + " & 0xffffffffL)) ? 1 : 0;");
/* 496 */         break;
/*     */       case 1: case 5: case 10: case 11: case 14: case 15: case 20: case 21: case 22: case 23: case 28: case 29: case 30: case 31: case 40: case 41: default: 
/* 498 */         throw new RuntimeException("Illegal instruction 0/" + i4);
/*     */       }
/*     */       
/*     */       break;
/*     */     case 1: 
/* 503 */       switch (k) {
/*     */       case 0: 
/* 505 */         if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
/* 506 */         p("if(r" + j + " < 0) {");
/* 507 */         this.indent += 1;
/* 508 */         emitInstruction(-1, paramInt3, -1);
/* 509 */         branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 510 */         this.indent -= 1;
/* 511 */         p("}");
/* 512 */         break;
/*     */       case 1: 
/* 514 */         if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
/* 515 */         p("if(r" + j + " >= 0) {");
/* 516 */         this.indent += 1;
/* 517 */         emitInstruction(-1, paramInt3, -1);
/* 518 */         branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 519 */         this.indent -= 1;
/* 520 */         p("}");
/* 521 */         break;
/*     */       case 16: 
/* 523 */         if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
/* 524 */         p("if(r" + j + " < 0) {");
/* 525 */         this.indent += 1;
/* 526 */         emitInstruction(-1, paramInt3, -1);
/* 527 */         p("r31=" + constant(paramInt1 + 8) + ";");
/* 528 */         branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 529 */         this.indent -= 1;
/* 530 */         p("}");
/* 531 */         break;
/*     */       case 17: 
/* 533 */         if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
/* 534 */         p("if(r" + j + " >= 0) {");
/* 535 */         this.indent += 1;
/* 536 */         emitInstruction(-1, paramInt3, -1);
/* 537 */         p("r31=" + constant(paramInt1 + 8) + ";");
/* 538 */         branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 539 */         this.indent -= 1;
/* 540 */         p("}");
/* 541 */         break;
/*     */       default: 
/* 543 */         throw new RuntimeException("Illegal Instruction 1/" + k);
/*     */       }
/*     */       
/*     */       break;
/*     */     case 2: 
/* 548 */       if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
/* 549 */       emitInstruction(-1, paramInt3, -1);
/* 550 */       branch(paramInt1, paramInt1 & 0xF0000000 | i5 << 2);
/* 551 */       this.unreachable = true;
/* 552 */       break;
/*     */     
/*     */     case 3: 
/* 555 */       if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
/* 556 */       int i9 = paramInt1 & 0xF0000000 | i5 << 2;
/* 557 */       emitInstruction(-1, paramInt3, -1);
/* 558 */       p("r31=" + constant(paramInt1 + 8) + ";");
/* 559 */       branch(paramInt1, i9);
/* 560 */       this.unreachable = true;
/* 561 */       break;
/*     */     
/*     */     case 4: 
/* 564 */       if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
/* 565 */       p("if(r" + j + " == r" + k + ") {");
/* 566 */       this.indent += 1;
/* 567 */       emitInstruction(-1, paramInt3, -1);
/* 568 */       branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 569 */       this.indent -= 1;
/* 570 */       p("}");
/* 571 */       break;
/*     */     case 5: 
/* 573 */       if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
/* 574 */       p("if(r" + j + " != r" + k + ") {");
/* 575 */       this.indent += 1;
/* 576 */       emitInstruction(-1, paramInt3, -1);
/* 577 */       branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 578 */       this.indent -= 1;
/* 579 */       p("}");
/* 580 */       break;
/*     */     case 6: 
/* 582 */       if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
/* 583 */       p("if(r" + j + " <= 0) {");
/* 584 */       this.indent += 1;
/* 585 */       emitInstruction(-1, paramInt3, -1);
/* 586 */       branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 587 */       this.indent -= 1;
/* 588 */       p("}");
/* 589 */       break;
/*     */     case 7: 
/* 591 */       if (paramInt1 == -1) throw new Error("pc modifying insn in delay slot");
/* 592 */       p("if(r" + j + " > 0) {");
/* 593 */       this.indent += 1;
/* 594 */       emitInstruction(-1, paramInt3, -1);
/* 595 */       branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 596 */       this.indent -= 1;
/* 597 */       p("}");
/* 598 */       break;
/*     */     case 8: 
/* 600 */       p("r" + k + " = r" + j + " + " + i7 + ";");
/* 601 */       break;
/*     */     case 9: 
/* 603 */       p("r" + k + " = r" + j + " + " + i7 + ";");
/* 604 */       break;
/*     */     case 10: 
/* 606 */       p("r" + k + " = r" + j + " < " + i7 + " ? 1 : 0;");
/* 607 */       break;
/*     */     case 11: 
/* 609 */       p("r" + k + " = (r" + j + "&0xffffffffL) < (" + i7 + "&0xffffffffL) ? 1 : 0;");
/* 610 */       break;
/*     */     case 12: 
/* 612 */       p("r" + k + " = r" + j + " & " + i6 + ";");
/* 613 */       break;
/*     */     case 13: 
/* 615 */       p("r" + k + " = r" + j + " | " + i6 + ";");
/* 616 */       break;
/*     */     case 14: 
/* 618 */       p("r" + k + " = r" + j + " ^ " + i6 + ";");
/* 619 */       break;
/*     */     case 15: 
/* 621 */       p("r" + k + " = " + i6 + " << 16;");
/* 622 */       break;
/*     */     case 16: 
/* 624 */       throw new Compiler.Exn("TLB/Exception support not implemented");
/*     */     case 17: 
/* 626 */       switch (j) {
/*     */       case 0: 
/* 628 */         p("r" + k + " = f" + n + ";");
/* 629 */         break;
/*     */       case 2: 
/* 631 */         if (i1 != 31) throw new Compiler.Exn("FCR " + i1 + " unavailable");
/* 632 */         p("r" + k + " = fcsr;");
/* 633 */         break;
/*     */       case 4: 
/* 635 */         p("f" + n + " = r" + k + ";");
/* 636 */         break;
/*     */       case 6: 
/* 638 */         if (i1 != 31) throw new Compiler.Exn("FCR " + i1 + " unavailable");
/* 639 */         p("fcsr = r" + k + ";");
/* 640 */         break;
/*     */       case 8: 
/* 642 */         int i10 = paramInt2 >>> 16 & 0x1;
/* 643 */         p("if(((fcsr&0x800000)!=0) == (" + i10 + "!=0)) {");
/* 644 */         this.indent += 1;
/* 645 */         emitInstruction(-1, paramInt3, -1);
/* 646 */         branch(paramInt1, paramInt1 + i8 * 4 + 4);
/* 647 */         this.indent -= 1;
/* 648 */         p("}");
/* 649 */         break;
/*     */       
/*     */       case 16: 
/* 652 */         switch (i4) {
/*     */         case 0: 
/* 654 */           p(setFloat(i3, getFloat(i1) + "+" + getFloat(m)));
/* 655 */           break;
/*     */         case 1: 
/* 657 */           p(setFloat(i3, getFloat(i1) + "-" + getFloat(m)));
/* 658 */           break;
/*     */         case 2: 
/* 660 */           p(setFloat(i3, getFloat(i1) + "*" + getFloat(m)));
/* 661 */           break;
/*     */         case 3: 
/* 663 */           p(setFloat(i3, getFloat(i1) + "/" + getFloat(m)));
/* 664 */           break;
/*     */         case 5: 
/* 666 */           p(setFloat(i3, "Math.abs(" + getFloat(i1) + ")"));
/* 667 */           break;
/*     */         case 6: 
/* 669 */           p("f" + i3 + " = f" + i1 + "; // MOV.S");
/* 670 */           break;
/*     */         case 7: 
/* 672 */           p(setFloat(i3, "-" + getFloat(i1)));
/* 673 */           break;
/*     */         case 33: 
/* 675 */           p(setDouble(i3, "(float)" + getFloat(i1)));
/* 676 */           break;
/*     */         case 36: 
/* 678 */           p("switch(fcsr & 3) {");
/* 679 */           this.indent += 1;
/* 680 */           p("case 0: f" + i3 + " = (int)Math.floor(" + getFloat(i1) + "+0.5); break; // Round to nearest");
/* 681 */           p("case 1: f" + i3 + " = (int)" + getFloat(i1) + "; break; // Round towards zero");
/* 682 */           p("case 2: f" + i3 + " = (int)Math.ceil(" + getFloat(i1) + "); break; // Round towards plus infinity");
/* 683 */           p("case 3: f" + i3 + " = (int)Math.floor(" + getFloat(i1) + "); break; // Round towards minus infinity");
/* 684 */           this.indent -= 1;
/* 685 */           p("}");
/* 686 */           break;
/*     */         case 50: 
/* 688 */           p("fcsr = (fcsr&~0x800000) | ((" + getFloat(i1) + "==" + getFloat(m) + ") ? 0x800000 : 0x000000);");
/* 689 */           break;
/*     */         case 60: 
/* 691 */           p("fcsr = (fcsr&~0x800000) | ((" + getFloat(i1) + "<" + getFloat(m) + ") ? 0x800000 : 0x000000);");
/* 692 */           break;
/*     */         case 62: 
/* 694 */           p("fcsr = (fcsr&~0x800000) | ((" + getFloat(i1) + "<=" + getFloat(m) + ") ? 0x800000 : 0x000000);");
/* 695 */           break;
/* 696 */         default:  throw new Compiler.Exn("Invalid Instruction 17/" + j + "/" + i4);
/*     */         }
/*     */         
/*     */         break;
/*     */       case 17: 
/* 701 */         switch (i4) {
/*     */         case 0: 
/* 703 */           p(setDouble(i3, getDouble(i1) + "+" + getDouble(m)));
/* 704 */           break;
/*     */         case 1: 
/* 706 */           p(setDouble(i3, getDouble(i1) + "-" + getDouble(m)));
/* 707 */           break;
/*     */         case 2: 
/* 709 */           p(setDouble(i3, getDouble(i1) + "*" + getDouble(m)));
/* 710 */           break;
/*     */         case 3: 
/* 712 */           p(setDouble(i3, getDouble(i1) + "/" + getDouble(m)));
/* 713 */           break;
/*     */         case 5: 
/* 715 */           p(setDouble(i3, "Math.abs(" + getDouble(i1) + ")"));
/* 716 */           break;
/*     */         case 6: 
/* 718 */           p("f" + i3 + " = f" + i1 + ";");
/* 719 */           p("f" + (i3 + 1) + " = f" + (i1 + 1) + ";");
/* 720 */           break;
/*     */         case 7: 
/* 722 */           p(setDouble(i3, "-" + getDouble(i1)));
/* 723 */           break;
/*     */         case 32: 
/* 725 */           p(setFloat(i3, "(float)" + getDouble(i1)));
/* 726 */           break;
/*     */         case 36: 
/* 728 */           p("switch(fcsr & 3) {");
/* 729 */           this.indent += 1;
/* 730 */           p("case 0: f" + i3 + " = (int)Math.floor(" + getDouble(i1) + "+0.5); break; // Round to nearest");
/* 731 */           p("case 1: f" + i3 + " = (int)" + getDouble(i1) + "; break; // Round towards zero");
/* 732 */           p("case 2: f" + i3 + " = (int)Math.ceil(" + getDouble(i1) + "); break; // Round towards plus infinity");
/* 733 */           p("case 3: f" + i3 + " = (int)Math.floor(" + getDouble(i1) + "); break; // Round towards minus infinity");
/* 734 */           this.indent -= 1;
/* 735 */           p("}");
/* 736 */           break;
/*     */         case 50: 
/* 738 */           p("fcsr = (fcsr&~0x800000) | ((" + getDouble(i1) + "==" + getDouble(m) + ") ? 0x800000 : 0x000000);");
/* 739 */           break;
/*     */         case 60: 
/* 741 */           p("fcsr = (fcsr&~0x800000) | ((" + getDouble(i1) + "<" + getDouble(m) + ") ? 0x800000 : 0x000000);");
/* 742 */           break;
/*     */         case 62: 
/* 744 */           p("fcsr = (fcsr&~0x800000) | ((" + getDouble(i1) + "<=" + getDouble(m) + ") ? 0x800000 : 0x000000);");
/* 745 */           break;
/* 746 */         default:  throw new Compiler.Exn("Invalid Instruction 17/" + j + "/" + i4);
/*     */         }
/*     */         
/*     */         break;
/*     */       case 20: 
/* 751 */         switch (i4) {
/*     */         case 32: 
/* 753 */           p(" // CVS.S.W");
/* 754 */           p(setFloat(i3, "((float)f" + i1 + ")"));
/* 755 */           break;
/*     */         case 33: 
/* 757 */           p(setDouble(i3, "((double)f" + i1 + ")"));
/* 758 */           break;
/* 759 */         default:  throw new Compiler.Exn("Invalid Instruction 17/" + j + "/" + i4);
/*     */         }
/*     */         break;
/*     */       case 1: case 3: case 5: case 7: case 9: case 10: case 11: 
/*     */       case 12: case 13: case 14: case 15: case 18: case 19: default: 
/* 764 */         throw new Compiler.Exn("Invalid Instruction 17/" + j);
/*     */       }
/*     */       break;
/*     */     case 18: 
/*     */     case 19: 
/* 769 */       throw new Compiler.Exn("coprocessor 2 and 3 instructions not available");
/*     */     case 32: 
/* 771 */       if (this.runtimeStats) p("inc(\"LB\");");
/* 772 */       p("addr=r" + j + "+" + i7 + ";");
/* 773 */       memRead("addr", "tmp");
/* 774 */       p("tmp = (tmp>>>(((~addr)&3)<<3)) & 0xff;");
/* 775 */       p("if((tmp&0x80)!=0) tmp |= 0xffffff00; /* sign extend */");
/* 776 */       p("r" + k + " = tmp;");
/* 777 */       break;
/*     */     
/*     */     case 33: 
/* 780 */       if (this.runtimeStats) p("inc(\"LH\");");
/* 781 */       p("addr=r" + j + "+" + i7 + ";");
/* 782 */       memRead("addr", "tmp");
/* 783 */       p("tmp = (tmp>>>(((~addr)&2)<<3)) & 0xffff;");
/* 784 */       p("if((tmp&0x8000)!=0) tmp |= 0xffff0000; /* sign extend */");
/* 785 */       p("r" + k + " = tmp;");
/* 786 */       break;
/*     */     
/*     */     case 34: 
/* 789 */       p("addr=r" + j + "+" + i7 + ";");
/* 790 */       memRead("addr", "tmp");
/* 791 */       p("r" + k + " = (r" + k + "&(0x00ffffff>>>(((~addr)&3)<<3)))|(tmp<<((addr&3)<<3));");
/* 792 */       break;
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
/*     */     case 35: 
/* 806 */       if (this.runtimeStats) p("inc(\"LW\");");
/* 807 */       memRead("r" + j + "+" + i7, "r" + k);
/* 808 */       break;
/*     */     case 36: 
/* 810 */       p("addr=r" + j + "+" + i7 + ";");
/* 811 */       memRead("addr", "tmp");
/* 812 */       p("tmp = (tmp>>>(((~addr)&3)<<3)) & 0xff;");
/* 813 */       p("r" + k + " = tmp;");
/* 814 */       break;
/*     */     
/*     */     case 37: 
/* 817 */       p("addr=r" + j + "+" + i7 + ";");
/* 818 */       memRead("addr", "tmp");
/* 819 */       p("tmp = (tmp>>>(((~addr)&2)<<3)) & 0xffff;");
/* 820 */       p("r" + k + " = tmp;");
/* 821 */       break;
/*     */     
/*     */     case 38: 
/* 824 */       p("addr=r" + j + "+" + i7 + ";");
/* 825 */       memRead("addr", "tmp");
/* 826 */       p("r" + k + " = (r" + k + "&(0xffffff00<<((addr&3)<<3)))|(tmp>>>(((~addr)&3)<<3));");
/* 827 */       break;
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
/*     */     case 40: 
/* 843 */       if (this.runtimeStats) p("inc(\"SB\");");
/* 844 */       p("addr=r" + j + "+" + i7 + ";");
/* 845 */       memRead("addr", "tmp");
/* 846 */       p("tmp = (tmp&~(0xff000000>>>((addr&3)<<3)))|((r" + k + "&0xff)<<(((~addr)&3)<<3));");
/* 847 */       memWrite("addr", "tmp");
/* 848 */       break;
/*     */     
/*     */     case 41: 
/* 851 */       if (this.runtimeStats) p("inc(\"SH\");");
/* 852 */       p("addr=r" + j + "+" + i7 + ";");
/* 853 */       memRead("addr", "tmp");
/* 854 */       p("tmp = (tmp&(0xffff<<((addr&2)<<3)))|((r" + k + "&0xffff)<<(((~addr)&2)<<3));");
/* 855 */       memWrite("addr", "tmp");
/* 856 */       break;
/*     */     
/*     */     case 42: 
/* 859 */       p(" // SWL");
/* 860 */       p("addr=r" + j + "+" + i7 + ";");
/* 861 */       memRead("addr", "tmp");
/* 862 */       p("tmp = (tmp&(0xffffff00<<(((~addr)&3)<<3)))|(r" + k + ">>>((addr&3)<<3));");
/* 863 */       memWrite("addr", "tmp");
/* 864 */       break;
/*     */     
/*     */     case 43: 
/* 867 */       if (this.runtimeStats) p("inc(\"SW\");");
/* 868 */       memWrite("r" + j + "+" + i7, "r" + k);
/* 869 */       break;
/*     */     case 46: 
/* 871 */       p(" // SWR");
/* 872 */       p("addr=r" + j + "+" + i7 + ";");
/* 873 */       memRead("addr", "tmp");
/* 874 */       p("tmp = (tmp&(0x00ffffff>>>((addr&3)<<3)))|(r" + k + "<<(((~addr)&3)<<3));");
/* 875 */       memWrite("addr", "tmp");
/* 876 */       break;
/*     */     
/*     */ 
/*     */     case 48: 
/* 880 */       memRead("r" + j + "+" + i7, "r" + k);
/* 881 */       break;
/*     */     case 49: 
/* 883 */       memRead("r" + j + "+" + i7, "f" + k);
/* 884 */       break;
/*     */     
/*     */     case 56: 
/* 887 */       memWrite("r" + j + "+" + i7, "r" + k);
/* 888 */       p("r" + k + "=1;");
/* 889 */       break;
/*     */     case 57: 
/* 891 */       memWrite("r" + j + "+" + i7, "f" + k);
/* 892 */       break;
/*     */     case 20: case 21: case 22: case 23: case 24: case 25: case 26: case 27: case 28: case 29: case 30: case 31: case 39: case 44: case 45: case 47: case 50: case 51: case 52: case 53: case 54: case 55: default: 
/* 894 */       throw new Compiler.Exn("Invalid Instruction: " + i + " at " + toHex(paramInt1));
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   private void memWrite(String paramString1, String paramString2)
/*     */   {
/* 901 */     if (this.nullPointerCheck) p("nullPointerCheck(" + paramString1 + ");");
/* 902 */     if (this.onePage) {
/* 903 */       p("page[(" + paramString1 + ")>>>2] = " + paramString2 + ";");
/* 904 */     } else if (this.fastMem) {
/* 905 */       p("writePages[(" + paramString1 + ")>>>" + this.pageShift + "][((" + paramString1 + ")>>>2)&" + toHex((this.pageSize >> 2) - 1) + "] = " + paramString2 + ";");
/*     */     } else
/* 907 */       p("unsafeMemWrite(" + paramString1 + "," + paramString2 + ");");
/*     */   }
/*     */   
/* 910 */   private void memRead(String paramString1, String paramString2) { if (this.nullPointerCheck) p("nullPointerCheck(" + paramString1 + ");");
/* 911 */     if (this.onePage) {
/* 912 */       p(paramString2 + "= page[(" + paramString1 + ")>>>2];");
/* 913 */     } else if (this.fastMem) {
/* 914 */       p(paramString2 + " = readPages[(" + paramString1 + ")>>>" + this.pageShift + "][((" + paramString1 + ")>>>2)&" + toHex((this.pageSize >> 2) - 1) + "];");
/*     */     } else
/* 916 */       p(paramString2 + " = unsafeMemRead(" + paramString1 + ");"); }
/*     */   
/* 918 */   private static String getFloat(int paramInt) { return "(Float.intBitsToFloat(f" + paramInt + "))"; }
/*     */   
/* 920 */   private static String getDouble(int paramInt) { return "(Double.longBitsToDouble(((f" + (paramInt + 1) + "&0xffffffffL) << 32) | (f" + paramInt + "&0xffffffffL)))"; }
/*     */   
/* 922 */   private static String setFloat(int paramInt, String paramString) { return "f" + paramInt + "=Float.floatToRawIntBits(" + paramString + ");"; }
/*     */   
/* 924 */   private static String setDouble(int paramInt, String paramString) { return "{ long l = Double.doubleToLongBits(" + paramString + "); " + "f" + (paramInt + 1) + " = (int)(l >>> 32); f" + paramInt + " = (int)l; }"; }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\org\ibex\nestedvm\JavaSourceCompiler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */