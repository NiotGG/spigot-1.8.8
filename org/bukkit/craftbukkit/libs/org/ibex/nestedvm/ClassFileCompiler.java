/*      */ package org.bukkit.craftbukkit.libs.org.ibex.nestedvm;
/*      */ 
/*      */ import java.io.DataInputStream;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.util.Hashtable;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.classgen.CGConst;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.classgen.ClassFile;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.classgen.ClassFile.Exn;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.classgen.MethodGen;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.classgen.MethodGen.Pair;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.classgen.MethodGen.PhantomTarget;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.classgen.MethodGen.Switch.Lookup;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.classgen.MethodGen.Switch.Table;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.classgen.Type;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.classgen.Type.Class;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF.ELFHeader;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF.SHeader;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF.Symbol;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF.Symtab;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable;
/*      */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable.File;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClassFileCompiler
/*      */   extends Compiler
/*      */   implements CGConst
/*      */ {
/*      */   private static final boolean OPTIMIZE_CP = true;
/*      */   private OutputStream os;
/*      */   private File outDir;
/*   45 */   private PrintStream warn = System.err;
/*      */   private final Type.Class me;
/*      */   private ClassFile cg;
/*      */   private MethodGen clinit;
/*      */   private MethodGen init;
/*      */   private static int initDataCount;
/*      */   
/*   52 */   public ClassFileCompiler(String paramString1, String paramString2, OutputStream paramOutputStream) throws IOException { this(new Seekable.File(paramString1), paramString2, paramOutputStream); }
/*      */   
/*   54 */   public ClassFileCompiler(Seekable paramSeekable, String paramString, OutputStream paramOutputStream) throws IOException { this(paramSeekable, paramString);
/*   55 */     if (paramOutputStream == null) throw new NullPointerException();
/*   56 */     this.os = paramOutputStream;
/*      */   }
/*      */   
/*   59 */   public ClassFileCompiler(Seekable paramSeekable, String paramString, File paramFile) throws IOException { this(paramSeekable, paramString);
/*   60 */     if (paramFile == null) throw new NullPointerException();
/*   61 */     this.outDir = paramFile;
/*      */   }
/*      */   
/*   64 */   private ClassFileCompiler(Seekable paramSeekable, String paramString) throws IOException { super(paramSeekable, paramString);
/*   65 */     this.me = Type.Class.instance(this.fullClassName);
/*      */   }
/*      */   
/*   68 */   public void setWarnWriter(PrintStream paramPrintStream) { this.warn = paramPrintStream; }
/*      */   
/*      */   protected void _go() throws Compiler.Exn, IOException {
/*      */     try {
/*   72 */       __go();
/*      */     } catch (ClassFile.Exn localExn) {
/*   74 */       localExn.printStackTrace(this.warn);
/*   75 */       throw new Compiler.Exn("Class generation exception: " + localExn.toString());
/*      */     }
/*      */   }
/*      */   
/*      */   private void __go() throws Compiler.Exn, IOException {
/*   80 */     if (!this.pruneCases) { throw new Compiler.Exn("-o prunecases MUST be enabled for ClassFileCompiler");
/*      */     }
/*      */     
/*   83 */     Type.Class localClass1 = Type.Class.instance(this.runtimeClass);
/*   84 */     this.cg = new ClassFile(this.me, localClass1, 49);
/*   85 */     if (this.source != null) { this.cg.setSourceFile(this.source);
/*      */     }
/*      */     
/*   88 */     this.cg.addField("pc", Type.INT, 2);
/*   89 */     this.cg.addField("hi", Type.INT, 2);
/*   90 */     this.cg.addField("lo", Type.INT, 2);
/*   91 */     this.cg.addField("fcsr", Type.INT, 2);
/*   92 */     for (int i = 1; i < 32; i++) this.cg.addField("r" + i, Type.INT, 2);
/*   93 */     for (i = 0; i < 32; i++) { this.cg.addField("f" + i, this.singleFloat ? Type.FLOAT : Type.INT, 2);
/*      */     }
/*      */     
/*   96 */     this.clinit = this.cg.addMethod("<clinit>", Type.VOID, Type.NO_ARGS, 10);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  101 */     this.init = this.cg.addMethod("<init>", Type.VOID, Type.NO_ARGS, 1);
/*  102 */     this.init.add((byte)42);
/*  103 */     this.init.add((byte)18, this.pageSize);
/*  104 */     this.init.add((byte)18, this.totalPages);
/*  105 */     this.init.add((byte)-73, this.me.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT }));
/*  106 */     this.init.add((byte)-79);
/*      */     
/*      */ 
/*  109 */     this.init = this.cg.addMethod("<init>", Type.VOID, new Type[] { Type.BOOLEAN }, 1);
/*  110 */     this.init.add((byte)42);
/*  111 */     this.init.add((byte)18, this.pageSize);
/*  112 */     this.init.add((byte)18, this.totalPages);
/*  113 */     this.init.add((byte)27);
/*  114 */     this.init.add((byte)-73, this.me.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN }));
/*  115 */     this.init.add((byte)-79);
/*      */     
/*      */ 
/*  118 */     this.init = this.cg.addMethod("<init>", Type.VOID, new Type[] { Type.INT, Type.INT }, 1);
/*  119 */     this.init.add((byte)42);
/*  120 */     this.init.add((byte)27);
/*  121 */     this.init.add((byte)28);
/*  122 */     this.init.add((byte)3);
/*  123 */     this.init.add((byte)-73, this.me.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN }));
/*  124 */     this.init.add((byte)-79);
/*      */     
/*      */ 
/*  127 */     this.init = this.cg.addMethod("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN }, 1);
/*  128 */     this.init.add((byte)42);
/*  129 */     this.init.add((byte)27);
/*  130 */     this.init.add((byte)28);
/*  131 */     this.init.add((byte)29);
/*  132 */     this.init.add((byte)-73, localClass1.method("<init>", Type.VOID, new Type[] { Type.INT, Type.INT, Type.BOOLEAN }));
/*      */     
/*  134 */     if (this.onePage) {
/*  135 */       this.cg.addField("page", Type.INT.makeArray(), 18);
/*  136 */       this.init.add((byte)42);
/*  137 */       this.init.add((byte)89);
/*  138 */       this.init.add((byte)-76, this.me.field("readPages", Type.INT.makeArray(2)));
/*  139 */       this.init.add((byte)18, 0);
/*  140 */       this.init.add((byte)50);
/*  141 */       this.init.add((byte)-75, this.me.field("page", Type.INT.makeArray()));
/*      */     }
/*      */     
/*  144 */     if (this.supportCall) {
/*  145 */       this.cg.addField("symbols", Type.Class.instance(this.hashClass), 26);
/*      */     }
/*  147 */     i = 0;
/*      */     
/*  149 */     for (int j = 0; j < this.elf.sheaders.length; j++) {
/*  150 */       localObject2 = this.elf.sheaders[j];
/*  151 */       String str = ((ELF.SHeader)localObject2).name;
/*      */       
/*  153 */       if (((ELF.SHeader)localObject2).addr != 0)
/*      */       {
/*  155 */         i = Math.max(i, ((ELF.SHeader)localObject2).addr + ((ELF.SHeader)localObject2).size);
/*      */         
/*  157 */         if (str.equals(".text")) {
/*  158 */           emitText(((ELF.SHeader)localObject2).addr, new DataInputStream(((ELF.SHeader)localObject2).getInputStream()), ((ELF.SHeader)localObject2).size);
/*  159 */         } else if ((str.equals(".data")) || (str.equals(".sdata")) || (str.equals(".rodata")) || (str.equals(".ctors")) || (str.equals(".dtors"))) {
/*  160 */           emitData(((ELF.SHeader)localObject2).addr, new DataInputStream(((ELF.SHeader)localObject2).getInputStream()), ((ELF.SHeader)localObject2).size, str.equals(".rodata"));
/*  161 */         } else if ((str.equals(".bss")) || (str.equals(".sbss"))) {
/*  162 */           emitBSS(((ELF.SHeader)localObject2).addr, ((ELF.SHeader)localObject2).size);
/*      */         } else {
/*  164 */           throw new Compiler.Exn("Unknown segment: " + str);
/*      */         }
/*      */       }
/*      */     }
/*  168 */     this.init.add((byte)-79);
/*      */     
/*      */ 
/*  171 */     if (this.supportCall) {
/*  172 */       localObject1 = Type.Class.instance(this.hashClass);
/*  173 */       this.clinit.add((byte)-69, localObject1);
/*  174 */       this.clinit.add((byte)89);
/*  175 */       this.clinit.add((byte)89);
/*  176 */       this.clinit.add((byte)-73, ((Type.Class)localObject1).method("<init>", Type.VOID, Type.NO_ARGS));
/*  177 */       this.clinit.add((byte)-77, this.me.field("symbols", (Type)localObject1));
/*  178 */       localObject2 = this.elf.getSymtab().symbols;
/*  179 */       for (k = 0; k < localObject2.length; k++) {
/*  180 */         Object localObject3 = localObject2[k];
/*  181 */         if ((((ELF.Symbol)localObject3).type == 2) && (((ELF.Symbol)localObject3).binding == 1) && ((((ELF.Symbol)localObject3).name.equals("_call_helper")) || (!((ELF.Symbol)localObject3).name.startsWith("_")))) {
/*  182 */           this.clinit.add((byte)89);
/*  183 */           this.clinit.add((byte)18, ((ELF.Symbol)localObject3).name);
/*  184 */           this.clinit.add((byte)-69, Type.INTEGER_OBJECT);
/*  185 */           this.clinit.add((byte)89);
/*  186 */           this.clinit.add((byte)18, ((ELF.Symbol)localObject3).addr);
/*  187 */           this.clinit.add((byte)-73, Type.INTEGER_OBJECT.method("<init>", Type.VOID, new Type[] { Type.INT }));
/*  188 */           this.clinit.add((byte)-74, ((Type.Class)localObject1).method("put", Type.OBJECT, new Type[] { Type.OBJECT, Type.OBJECT }));
/*  189 */           this.clinit.add((byte)87);
/*      */         }
/*      */       }
/*  192 */       this.clinit.add((byte)87);
/*      */     }
/*      */     
/*  195 */     this.clinit.add((byte)-79);
/*      */     
/*  197 */     Object localObject1 = this.elf.sectionWithName(".text");
/*      */     
/*      */ 
/*  200 */     Object localObject2 = this.cg.addMethod("trampoline", Type.VOID, Type.NO_ARGS, 2);
/*      */     
/*  202 */     int k = ((MethodGen)localObject2).size();
/*  203 */     ((MethodGen)localObject2).add((byte)42);
/*  204 */     ((MethodGen)localObject2).add((byte)-76, this.me.field("state", Type.INT));
/*  205 */     ((MethodGen)localObject2).add((byte)-103, ((MethodGen)localObject2).size() + 2);
/*  206 */     ((MethodGen)localObject2).add((byte)-79);
/*      */     
/*  208 */     ((MethodGen)localObject2).add((byte)42);
/*  209 */     ((MethodGen)localObject2).add((byte)42);
/*  210 */     ((MethodGen)localObject2).add((byte)-76, this.me.field("pc", Type.INT));
/*  211 */     ((MethodGen)localObject2).add((byte)18, this.methodShift);
/*  212 */     ((MethodGen)localObject2).add((byte)124);
/*      */     
/*  214 */     int m = ((ELF.SHeader)localObject1).addr >>> this.methodShift;
/*  215 */     int n = ((ELF.SHeader)localObject1).addr + ((ELF.SHeader)localObject1).size + this.maxBytesPerMethod - 1 >>> this.methodShift;
/*      */     
/*  217 */     MethodGen.Switch.Table localTable = new MethodGen.Switch.Table(m, n - 1);
/*  218 */     ((MethodGen)localObject2).add((byte)-86, localTable);
/*  219 */     for (int i1 = m; i1 < n; i1++) {
/*  220 */       localTable.setTargetForVal(i1, ((MethodGen)localObject2).size());
/*  221 */       ((MethodGen)localObject2).add((byte)-73, this.me.method("run_" + toHex(i1 << this.methodShift), Type.VOID, Type.NO_ARGS));
/*  222 */       ((MethodGen)localObject2).add((byte)-89, k);
/*      */     }
/*  224 */     localTable.setDefaultTarget(((MethodGen)localObject2).size());
/*      */     
/*  226 */     ((MethodGen)localObject2).add((byte)87);
/*  227 */     ((MethodGen)localObject2).add((byte)-69, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException"));
/*  228 */     ((MethodGen)localObject2).add((byte)89);
/*  229 */     ((MethodGen)localObject2).add((byte)-69, Type.STRINGBUFFER);
/*  230 */     ((MethodGen)localObject2).add((byte)89);
/*  231 */     ((MethodGen)localObject2).add((byte)18, "Jumped to invalid address in trampoline (r2: ");
/*  232 */     ((MethodGen)localObject2).add((byte)-73, Type.STRINGBUFFER.method("<init>", Type.VOID, new Type[] { Type.STRING }));
/*  233 */     ((MethodGen)localObject2).add((byte)42);
/*  234 */     ((MethodGen)localObject2).add((byte)-76, this.me.field("r2", Type.INT));
/*  235 */     ((MethodGen)localObject2).add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.INT }));
/*  236 */     ((MethodGen)localObject2).add((byte)18, " pc: ");
/*  237 */     ((MethodGen)localObject2).add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.STRING }));
/*  238 */     ((MethodGen)localObject2).add((byte)42);
/*  239 */     ((MethodGen)localObject2).add((byte)-76, this.me.field("pc", Type.INT));
/*  240 */     ((MethodGen)localObject2).add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.INT }));
/*  241 */     ((MethodGen)localObject2).add((byte)18, ")");
/*  242 */     ((MethodGen)localObject2).add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.STRING }));
/*  243 */     ((MethodGen)localObject2).add((byte)-74, Type.STRINGBUFFER.method("toString", Type.STRING, Type.NO_ARGS));
/*      */     
/*  245 */     ((MethodGen)localObject2).add((byte)-73, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { Type.STRING }));
/*  246 */     ((MethodGen)localObject2).add((byte)-65);
/*      */     
/*  248 */     addConstReturnMethod("gp", this.gp.addr);
/*  249 */     addConstReturnMethod("entryPoint", this.elf.header.entry);
/*  250 */     addConstReturnMethod("heapStart", i);
/*      */     
/*  252 */     if (this.userInfo != null) {
/*  253 */       addConstReturnMethod("userInfoBase", this.userInfo.addr);
/*  254 */       addConstReturnMethod("userInfoSize", this.userInfo.size);
/*      */     }
/*      */     
/*  257 */     if (this.supportCall) {
/*  258 */       localClass2 = Type.Class.instance(this.hashClass);
/*  259 */       localMethodGen1 = this.cg.addMethod("lookupSymbol", Type.INT, new Type[] { Type.STRING }, 4);
/*  260 */       localMethodGen1.add((byte)-78, this.me.field("symbols", localClass2));
/*  261 */       localMethodGen1.add((byte)43);
/*  262 */       localMethodGen1.add((byte)-74, localClass2.method("get", Type.OBJECT, new Type[] { Type.OBJECT }));
/*  263 */       localMethodGen1.add((byte)89);
/*  264 */       int i2 = localMethodGen1.add((byte)-58);
/*  265 */       localMethodGen1.add((byte)-64, Type.INTEGER_OBJECT);
/*  266 */       localMethodGen1.add((byte)-74, Type.INTEGER_OBJECT.method("intValue", Type.INT, Type.NO_ARGS));
/*  267 */       localMethodGen1.add((byte)-84);
/*  268 */       localMethodGen1.setArg(i2, localMethodGen1.size());
/*  269 */       localMethodGen1.add((byte)87);
/*  270 */       localMethodGen1.add((byte)2);
/*  271 */       localMethodGen1.add((byte)-84);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  276 */     Type.Class localClass2 = Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$CPUState");
/*  277 */     MethodGen localMethodGen1 = this.cg.addMethod("setCPUState", Type.VOID, new Type[] { localClass2 }, 4);
/*  278 */     MethodGen localMethodGen2 = this.cg.addMethod("getCPUState", Type.VOID, new Type[] { localClass2 }, 4);
/*      */     
/*  280 */     localMethodGen1.add((byte)43);
/*  281 */     localMethodGen2.add((byte)43);
/*  282 */     localMethodGen1.add((byte)-76, localClass2.field("r", Type.INT.makeArray()));
/*  283 */     localMethodGen2.add((byte)-76, localClass2.field("r", Type.INT.makeArray()));
/*  284 */     localMethodGen1.add((byte)77);
/*  285 */     localMethodGen2.add((byte)77);
/*      */     
/*  287 */     for (int i3 = 1; i3 < 32; i3++) {
/*  288 */       localMethodGen1.add((byte)42);
/*  289 */       localMethodGen1.add((byte)44);
/*  290 */       localMethodGen1.add((byte)18, i3);
/*  291 */       localMethodGen1.add((byte)46);
/*  292 */       localMethodGen1.add((byte)-75, this.me.field("r" + i3, Type.INT));
/*      */       
/*  294 */       localMethodGen2.add((byte)44);
/*  295 */       localMethodGen2.add((byte)18, i3);
/*  296 */       localMethodGen2.add((byte)42);
/*  297 */       localMethodGen2.add((byte)-76, this.me.field("r" + i3, Type.INT));
/*  298 */       localMethodGen2.add((byte)79);
/*      */     }
/*      */     
/*  301 */     localMethodGen1.add((byte)43);
/*  302 */     localMethodGen2.add((byte)43);
/*  303 */     localMethodGen1.add((byte)-76, localClass2.field("f", Type.INT.makeArray()));
/*  304 */     localMethodGen2.add((byte)-76, localClass2.field("f", Type.INT.makeArray()));
/*  305 */     localMethodGen1.add((byte)77);
/*  306 */     localMethodGen2.add((byte)77);
/*      */     
/*  308 */     for (i3 = 0; i3 < 32; i3++) {
/*  309 */       localMethodGen1.add((byte)42);
/*  310 */       localMethodGen1.add((byte)44);
/*  311 */       localMethodGen1.add((byte)18, i3);
/*  312 */       localMethodGen1.add((byte)46);
/*  313 */       if (this.singleFloat) localMethodGen1.add((byte)-72, Type.FLOAT_OBJECT.method("intBitsToFloat", Type.FLOAT, new Type[] { Type.INT }));
/*  314 */       localMethodGen1.add((byte)-75, this.me.field("f" + i3, this.singleFloat ? Type.FLOAT : Type.INT));
/*      */       
/*  316 */       localMethodGen2.add((byte)44);
/*  317 */       localMethodGen2.add((byte)18, i3);
/*  318 */       localMethodGen2.add((byte)42);
/*  319 */       localMethodGen2.add((byte)-76, this.me.field("f" + i3, this.singleFloat ? Type.FLOAT : Type.INT));
/*  320 */       if (this.singleFloat) localMethodGen2.add((byte)-72, Type.FLOAT_OBJECT.method("floatToIntBits", Type.INT, new Type[] { Type.FLOAT }));
/*  321 */       localMethodGen2.add((byte)79);
/*      */     }
/*      */     
/*  324 */     String[] arrayOfString = { "hi", "lo", "fcsr", "pc" };
/*  325 */     for (int i4 = 0; i4 < arrayOfString.length; i4++) {
/*  326 */       localMethodGen1.add((byte)42);
/*  327 */       localMethodGen1.add((byte)43);
/*  328 */       localMethodGen1.add((byte)-76, localClass2.field(arrayOfString[i4], Type.INT));
/*  329 */       localMethodGen1.add((byte)-75, this.me.field(arrayOfString[i4], Type.INT));
/*      */       
/*  331 */       localMethodGen2.add((byte)43);
/*  332 */       localMethodGen2.add((byte)42);
/*  333 */       localMethodGen2.add((byte)-76, this.me.field(arrayOfString[i4], Type.INT));
/*  334 */       localMethodGen2.add((byte)-75, localClass2.field(arrayOfString[i4], Type.INT));
/*      */     }
/*  336 */     localMethodGen1.add((byte)-79);
/*  337 */     localMethodGen2.add((byte)-79);
/*      */     
/*      */ 
/*  340 */     MethodGen localMethodGen3 = this.cg.addMethod("_execute", Type.VOID, Type.NO_ARGS, 4);
/*  341 */     int i5 = localMethodGen3.size();
/*  342 */     localMethodGen3.add((byte)42);
/*  343 */     localMethodGen3.add((byte)-73, this.me.method("trampoline", Type.VOID, Type.NO_ARGS));
/*  344 */     int i6 = localMethodGen3.size();
/*  345 */     localMethodGen3.add((byte)-79);
/*      */     
/*  347 */     int i7 = localMethodGen3.size();
/*  348 */     localMethodGen3.add((byte)76);
/*  349 */     localMethodGen3.add((byte)-69, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$FaultException"));
/*  350 */     localMethodGen3.add((byte)89);
/*  351 */     localMethodGen3.add((byte)43);
/*      */     
/*  353 */     localMethodGen3.add((byte)-73, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$FaultException").method("<init>", Type.VOID, new Type[] { Type.Class.instance("java.lang.RuntimeException") }));
/*  354 */     localMethodGen3.add((byte)-65);
/*      */     
/*  356 */     localMethodGen3.addExceptionHandler(i5, i6, i7, Type.Class.instance("java.lang.RuntimeException"));
/*  357 */     localMethodGen3.addThrow(Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException"));
/*      */     
/*  359 */     MethodGen localMethodGen4 = this.cg.addMethod("main", Type.VOID, new Type[] { Type.STRING.makeArray() }, 9);
/*  360 */     localMethodGen4.add((byte)-69, this.me);
/*  361 */     localMethodGen4.add((byte)89);
/*  362 */     localMethodGen4.add((byte)-73, this.me.method("<init>", Type.VOID, Type.NO_ARGS));
/*  363 */     localMethodGen4.add((byte)18, this.fullClassName);
/*  364 */     localMethodGen4.add((byte)42);
/*  365 */     if (this.unixRuntime) {
/*  366 */       Type.Class localClass3 = Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.UnixRuntime");
/*      */       
/*  368 */       localMethodGen4.add((byte)-72, localClass3.method("runAndExec", Type.INT, new Type[] { localClass3, Type.STRING, Type.STRING.makeArray() }));
/*      */     }
/*      */     else {
/*  371 */       localMethodGen4.add((byte)-74, this.me.method("run", Type.INT, new Type[] { Type.STRING, Type.STRING.makeArray() }));
/*      */     }
/*  373 */     localMethodGen4.add((byte)-72, Type.Class.instance("java.lang.System").method("exit", Type.VOID, new Type[] { Type.INT }));
/*  374 */     localMethodGen4.add((byte)-79);
/*      */     
/*  376 */     if (this.outDir != null) {
/*  377 */       if (!this.outDir.isDirectory()) throw new IOException("" + this.outDir + " isn't a directory");
/*  378 */       this.cg.dump(this.outDir);
/*      */     } else {
/*  380 */       this.cg.dump(this.os);
/*      */     }
/*      */   }
/*      */   
/*      */   private void addConstReturnMethod(String paramString, int paramInt) {
/*  385 */     MethodGen localMethodGen = this.cg.addMethod(paramString, Type.INT, Type.NO_ARGS, 4);
/*  386 */     localMethodGen.add((byte)18, paramInt);
/*  387 */     localMethodGen.add((byte)-84);
/*      */   }
/*      */   
/*      */   private void emitData(int paramInt1, DataInputStream paramDataInputStream, int paramInt2, boolean paramBoolean) throws Compiler.Exn, IOException
/*      */   {
/*  392 */     if (((paramInt1 & 0x3) != 0) || ((paramInt2 & 0x3) != 0)) throw new Compiler.Exn("Data section on weird boundaries");
/*  393 */     int i = paramInt1 + paramInt2;
/*  394 */     while (paramInt1 < i) {
/*  395 */       int j = Math.min(paramInt2, 28000);
/*  396 */       StringBuffer localStringBuffer = new StringBuffer();
/*  397 */       for (int k = 0; k < j; k += 7) {
/*  398 */         long l = 0L;
/*  399 */         for (int m = 0; m < 7; m++) {
/*  400 */           l <<= 8;
/*  401 */           int n = k + m < paramInt2 ? paramDataInputStream.readByte() : 1;
/*  402 */           l |= n & 0xFF;
/*      */         }
/*  404 */         for (m = 0; m < 8; m++)
/*  405 */           localStringBuffer.append((char)(int)(l >>> 7 * (7 - m) & 0x7F));
/*      */       }
/*  407 */       String str = "_data" + ++initDataCount;
/*  408 */       this.cg.addField(str, Type.INT.makeArray(), 26);
/*      */       
/*  410 */       this.clinit.add((byte)18, localStringBuffer.toString());
/*  411 */       this.clinit.add((byte)18, j / 4);
/*      */       
/*  413 */       this.clinit.add((byte)-72, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime").method("decodeData", Type.INT.makeArray(), new Type[] { Type.STRING, Type.INT }));
/*  414 */       this.clinit.add((byte)-77, this.me.field(str, Type.INT.makeArray()));
/*  415 */       this.init.add((byte)42);
/*  416 */       this.init.add((byte)-78, this.me.field(str, Type.INT.makeArray()));
/*  417 */       this.init.add((byte)18, paramInt1);
/*  418 */       this.init.add((byte)18, paramBoolean ? 1 : 0);
/*      */       
/*  420 */       this.init.add((byte)-74, this.me.method("initPages", Type.VOID, new Type[] { Type.INT.makeArray(), Type.INT, Type.BOOLEAN }));
/*      */       
/*  422 */       paramInt1 += j;
/*  423 */       paramInt2 -= j;
/*      */     }
/*  425 */     paramDataInputStream.close();
/*      */   }
/*      */   
/*      */   private void emitBSS(int paramInt1, int paramInt2) throws Compiler.Exn {
/*  429 */     if ((paramInt1 & 0x3) != 0) throw new Compiler.Exn("BSS section on weird boundaries");
/*  430 */     paramInt2 = paramInt2 + 3 & 0xFFFFFFFC;
/*  431 */     int i = paramInt2 / 4;
/*      */     
/*  433 */     this.init.add((byte)42);
/*  434 */     this.init.add((byte)18, paramInt1);
/*  435 */     this.init.add((byte)18, i);
/*      */     
/*  437 */     this.init.add((byte)-74, this.me.method("clearPages", Type.VOID, new Type[] { Type.INT, Type.INT }));
/*      */   }
/*      */   
/*      */ 
/*  441 */   private int startOfMethod = 0;
/*  442 */   private int endOfMethod = 0;
/*      */   
/*      */   private MethodGen.PhantomTarget returnTarget;
/*      */   
/*      */   private MethodGen.PhantomTarget defaultTarget;
/*      */   private MethodGen.PhantomTarget[] insnTargets;
/*      */   
/*  449 */   private boolean jumpable(int paramInt) { return this.jumpableAddresses.get(new Integer(paramInt)) != null; }
/*      */   
/*      */   private MethodGen mg;
/*      */   private static final int UNREACHABLE = 1;
/*      */   private static final int SKIP_NEXT = 2;
/*      */   private boolean textDone;
/*      */   private void emitText(int paramInt1, DataInputStream paramDataInputStream, int paramInt2) throws Compiler.Exn, IOException {
/*  456 */     if (this.textDone) throw new Compiler.Exn("Multiple text segments");
/*  457 */     this.textDone = true;
/*      */     
/*  459 */     if (((paramInt1 & 0x3) != 0) || ((paramInt2 & 0x3) != 0)) throw new Compiler.Exn("Section on weird boundaries");
/*  460 */     int i = paramInt2 / 4;
/*  461 */     int j = -1;
/*      */     
/*  463 */     int k = 1;
/*  464 */     boolean bool = false;
/*      */     
/*  466 */     for (int m = 0; m < i; paramInt1 += 4) {
/*  467 */       int n = k != 0 ? paramDataInputStream.readInt() : j;
/*  468 */       j = m == i - 1 ? -1 : paramDataInputStream.readInt();
/*  469 */       if (paramInt1 >= this.endOfMethod) { endMethod(paramInt1, bool);startMethod(paramInt1); }
/*  470 */       if (this.insnTargets[(m % this.maxInsnPerMethod)] != null) {
/*  471 */         this.insnTargets[(m % this.maxInsnPerMethod)].setTarget(this.mg.size());
/*  472 */         bool = false;
/*  473 */       } else { if (bool)
/*      */           break label308;
/*      */       }
/*      */       try {
/*  477 */         int i1 = emitInstruction(paramInt1, n, j);
/*  478 */         bool = (i1 & 0x1) != 0;
/*  479 */         k = (i1 & 0x2) != 0 ? 1 : 0;
/*      */       } catch (Compiler.Exn localExn) {
/*  481 */         localExn.printStackTrace(this.warn);
/*  482 */         this.warn.println("Exception at " + toHex(paramInt1));
/*  483 */         throw localExn;
/*      */       } catch (RuntimeException localRuntimeException) {
/*  485 */         this.warn.println("Exception at " + toHex(paramInt1));
/*  486 */         throw localRuntimeException;
/*      */       }
/*  488 */       if (k != 0) { paramInt1 += 4;m++;
/*      */       }
/*      */       label308:
/*  466 */       m++;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  490 */     endMethod(0, bool);
/*  491 */     paramDataInputStream.close();
/*      */   }
/*      */   
/*      */   private void startMethod(int paramInt) {
/*  495 */     this.startOfMethod = (paramInt & this.methodMask);
/*  496 */     this.endOfMethod = (this.startOfMethod + this.maxBytesPerMethod);
/*      */     
/*  498 */     this.mg = this.cg.addMethod("run_" + toHex(this.startOfMethod), Type.VOID, Type.NO_ARGS, 18);
/*  499 */     if (this.onePage) {
/*  500 */       this.mg.add((byte)42);
/*  501 */       this.mg.add((byte)-76, this.me.field("page", Type.INT.makeArray()));
/*  502 */       this.mg.add((byte)77);
/*      */     } else {
/*  504 */       this.mg.add((byte)42);
/*  505 */       this.mg.add((byte)-76, this.me.field("readPages", Type.INT.makeArray(2)));
/*  506 */       this.mg.add((byte)77);
/*  507 */       this.mg.add((byte)42);
/*  508 */       this.mg.add((byte)-76, this.me.field("writePages", Type.INT.makeArray(2)));
/*  509 */       this.mg.add((byte)78);
/*      */     }
/*      */     
/*  512 */     this.returnTarget = new MethodGen.PhantomTarget();
/*  513 */     this.insnTargets = new MethodGen.PhantomTarget[this.maxBytesPerMethod / 4];
/*      */     
/*  515 */     int[] arrayOfInt = new int[this.maxBytesPerMethod / 4];
/*  516 */     Object[] arrayOfObject = new Object[this.maxBytesPerMethod / 4];
/*  517 */     int i = 0;
/*  518 */     for (int j = paramInt; j < this.endOfMethod; j += 4) {
/*  519 */       if (jumpable(j)) {
/*  520 */         arrayOfObject[i] = (this.insnTargets[((j - this.startOfMethod) / 4)] = new MethodGen.PhantomTarget());
/*  521 */         arrayOfInt[i] = j;
/*  522 */         i++;
/*      */       }
/*      */     }
/*      */     
/*  526 */     MethodGen.Switch.Lookup localLookup = new MethodGen.Switch.Lookup(i);
/*  527 */     System.arraycopy(arrayOfInt, 0, localLookup.vals, 0, i);
/*  528 */     System.arraycopy(arrayOfObject, 0, localLookup.targets, 0, i);
/*  529 */     localLookup.setDefaultTarget(this.defaultTarget = new MethodGen.PhantomTarget());
/*      */     
/*  531 */     fixupRegsStart();
/*      */     
/*  533 */     this.mg.add((byte)42);
/*  534 */     this.mg.add((byte)-76, this.me.field("pc", Type.INT));
/*  535 */     this.mg.add((byte)-85, localLookup);
/*      */   }
/*      */   
/*      */   private void endMethod(int paramInt, boolean paramBoolean) {
/*  539 */     if (this.startOfMethod == 0) { return;
/*      */     }
/*  541 */     if (!paramBoolean) {
/*  542 */       preSetPC();
/*  543 */       this.mg.add((byte)18, paramInt);
/*  544 */       setPC();
/*      */       
/*  546 */       this.jumpableAddresses.put(new Integer(paramInt), Boolean.TRUE);
/*      */     }
/*      */     
/*  549 */     this.returnTarget.setTarget(this.mg.size());
/*      */     
/*  551 */     fixupRegsEnd();
/*      */     
/*  553 */     this.mg.add((byte)-79);
/*      */     
/*  555 */     this.defaultTarget.setTarget(this.mg.size());
/*      */     
/*  557 */     if (this.debugCompiler) {
/*  558 */       this.mg.add((byte)-69, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException"));
/*  559 */       this.mg.add((byte)89);
/*  560 */       this.mg.add((byte)-69, Type.STRINGBUFFER);
/*  561 */       this.mg.add((byte)89);
/*  562 */       this.mg.add((byte)18, "Jumped to invalid address: ");
/*  563 */       this.mg.add((byte)-73, Type.STRINGBUFFER.method("<init>", Type.VOID, new Type[] { Type.STRING }));
/*  564 */       this.mg.add((byte)42);
/*  565 */       this.mg.add((byte)-76, this.me.field("pc", Type.INT));
/*  566 */       this.mg.add((byte)-74, Type.STRINGBUFFER.method("append", Type.STRINGBUFFER, new Type[] { Type.INT }));
/*  567 */       this.mg.add((byte)-74, Type.STRINGBUFFER.method("toString", Type.STRING, Type.NO_ARGS));
/*  568 */       this.mg.add((byte)-73, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { Type.STRING }));
/*  569 */       this.mg.add((byte)-65);
/*      */     } else {
/*  571 */       this.mg.add((byte)-69, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException"));
/*  572 */       this.mg.add((byte)89);
/*  573 */       this.mg.add((byte)18, "Jumped to invalid address");
/*  574 */       this.mg.add((byte)-73, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { Type.STRING }));
/*  575 */       this.mg.add((byte)-65);
/*      */     }
/*      */     
/*  578 */     this.endOfMethod = (this.startOfMethod = 0);
/*      */   }
/*      */   
/*      */   private void leaveMethod()
/*      */   {
/*  583 */     this.mg.add((byte)-89, this.returnTarget);
/*      */   }
/*      */   
/*      */   private void link(int paramInt) {
/*  587 */     preSetReg(31);
/*  588 */     if (this.lessConstants) {
/*  589 */       int i = paramInt + 8 + 32768 & 0xFFFF0000;
/*  590 */       int j = paramInt + 8 - i;
/*  591 */       if ((j < 32768) || (j > 32767)) throw new Error("should never happen " + j);
/*  592 */       this.mg.add((byte)18, i);
/*  593 */       this.mg.add((byte)18, j);
/*  594 */       this.mg.add((byte)96);
/*      */     } else {
/*  596 */       this.mg.add((byte)18, paramInt + 8);
/*      */     }
/*  598 */     setReg();
/*      */   }
/*      */   
/*      */   private void branch(int paramInt1, int paramInt2) {
/*  602 */     if ((paramInt1 & this.methodMask) == (paramInt2 & this.methodMask)) {
/*  603 */       this.mg.add((byte)-89, this.insnTargets[((paramInt2 - this.startOfMethod) / 4)]);
/*      */     } else {
/*  605 */       preSetPC();
/*  606 */       this.mg.add((byte)18, paramInt2);
/*  607 */       setPC();
/*  608 */       leaveMethod();
/*      */     }
/*      */   }
/*      */   
/*      */   private int doIfInstruction(byte paramByte, int paramInt1, int paramInt2, int paramInt3) throws Compiler.Exn
/*      */   {
/*  614 */     emitInstruction(-1, paramInt3, -1);
/*  615 */     if ((paramInt2 & this.methodMask) == (paramInt1 & this.methodMask)) {
/*  616 */       this.mg.add(paramByte, this.insnTargets[((paramInt2 - this.startOfMethod) / 4)]);
/*      */     } else {
/*  618 */       i = this.mg.add(MethodGen.negate(paramByte));
/*  619 */       branch(paramInt1, paramInt2);
/*  620 */       this.mg.setArg(i, this.mg.size());
/*      */     }
/*  622 */     if (!jumpable(paramInt1 + 4)) { return 2;
/*      */     }
/*      */     
/*  625 */     if (paramInt1 + 4 == this.endOfMethod)
/*      */     {
/*  627 */       this.jumpableAddresses.put(new Integer(paramInt1 + 8), Boolean.TRUE);
/*  628 */       branch(paramInt1, paramInt1 + 8);
/*      */       
/*      */ 
/*      */ 
/*  632 */       return 1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  637 */     int i = this.mg.add((byte)-89);
/*  638 */     this.insnTargets[((paramInt1 + 4 - this.startOfMethod) / 4)].setTarget(this.mg.size());
/*  639 */     emitInstruction(-1, paramInt3, 1);
/*  640 */     this.mg.setArg(i, this.mg.size());
/*      */     
/*  642 */     return 2;
/*      */   }
/*      */   
/*      */ 
/*  646 */   private static final Float POINT_5_F = new Float(0.5F);
/*  647 */   private static final Double POINT_5_D = new Double(0.5D);
/*  648 */   private static final Long FFFFFFFF = new Long(4294967295L);
/*      */   private static final int R = 0;
/*      */   
/*  651 */   private int emitInstruction(int paramInt1, int paramInt2, int paramInt3) throws Compiler.Exn { MethodGen localMethodGen = this.mg;
/*  652 */     if (paramInt2 == -1) { throw new Compiler.Exn("insn is -1");
/*      */     }
/*  654 */     int i = 0;
/*      */     
/*  656 */     int j = paramInt2 >>> 26 & 0xFF;
/*  657 */     int k = paramInt2 >>> 21 & 0x1F;
/*  658 */     int m = paramInt2 >>> 16 & 0x1F;
/*  659 */     int n = paramInt2 >>> 16 & 0x1F;
/*  660 */     int i1 = paramInt2 >>> 11 & 0x1F;
/*  661 */     int i2 = paramInt2 >>> 11 & 0x1F;
/*  662 */     int i3 = paramInt2 >>> 6 & 0x1F;
/*  663 */     int i4 = paramInt2 >>> 6 & 0x1F;
/*  664 */     int i5 = paramInt2 & 0x3F;
/*  665 */     int i6 = paramInt2 >>> 6 & 0xFFFFF;
/*      */     
/*  667 */     int i7 = paramInt2 & 0x3FFFFFF;
/*  668 */     int i8 = paramInt2 & 0xFFFF;
/*  669 */     int i9 = paramInt2 << 16 >> 16;
/*  670 */     int i10 = i9;
/*      */     
/*      */     int i11;
/*      */     int i12;
/*      */     int i13;
/*  675 */     switch (j) {
/*      */     case 0: 
/*  677 */       switch (i5) {
/*      */       case 0: 
/*  679 */         if (paramInt2 != 0) {
/*  680 */           preSetReg(0 + i1);
/*  681 */           pushRegWZ(0 + m);
/*  682 */           localMethodGen.add((byte)18, i3);
/*  683 */           localMethodGen.add((byte)120);
/*  684 */           setReg(); }
/*  685 */         break;
/*      */       case 2: 
/*  687 */         preSetReg(0 + i1);
/*  688 */         pushRegWZ(0 + m);
/*  689 */         localMethodGen.add((byte)18, i3);
/*  690 */         localMethodGen.add((byte)124);
/*  691 */         setReg();
/*  692 */         break;
/*      */       case 3: 
/*  694 */         preSetReg(0 + i1);
/*  695 */         pushRegWZ(0 + m);
/*  696 */         localMethodGen.add((byte)18, i3);
/*  697 */         localMethodGen.add((byte)122);
/*  698 */         setReg();
/*  699 */         break;
/*      */       case 4: 
/*  701 */         preSetReg(0 + i1);
/*  702 */         pushRegWZ(0 + m);
/*  703 */         pushRegWZ(0 + k);
/*  704 */         localMethodGen.add((byte)120);
/*  705 */         setReg();
/*  706 */         break;
/*      */       case 6: 
/*  708 */         preSetReg(0 + i1);
/*  709 */         pushRegWZ(0 + m);
/*  710 */         pushRegWZ(0 + k);
/*  711 */         localMethodGen.add((byte)124);
/*  712 */         setReg();
/*  713 */         break;
/*      */       case 7: 
/*  715 */         preSetReg(0 + i1);
/*  716 */         pushRegWZ(0 + m);
/*  717 */         pushRegWZ(0 + k);
/*  718 */         localMethodGen.add((byte)122);
/*  719 */         setReg();
/*  720 */         break;
/*      */       case 8: 
/*  722 */         if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
/*  723 */         emitInstruction(-1, paramInt3, -1);
/*  724 */         preSetPC();
/*  725 */         pushRegWZ(0 + k);
/*  726 */         setPC();
/*  727 */         leaveMethod();
/*  728 */         i |= 0x1;
/*  729 */         break;
/*      */       case 9: 
/*  731 */         if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
/*  732 */         emitInstruction(-1, paramInt3, -1);
/*  733 */         link(paramInt1);
/*  734 */         preSetPC();
/*  735 */         pushRegWZ(0 + k);
/*  736 */         setPC();
/*  737 */         leaveMethod();
/*  738 */         i |= 0x1;
/*  739 */         break;
/*      */       case 12: 
/*  741 */         preSetPC();
/*  742 */         localMethodGen.add((byte)18, paramInt1);
/*  743 */         setPC();
/*      */         
/*      */ 
/*      */ 
/*  747 */         restoreChangedRegs();
/*      */         
/*  749 */         preSetReg(2);
/*  750 */         localMethodGen.add((byte)42);
/*  751 */         pushRegZ(2);
/*  752 */         pushRegZ(4);
/*  753 */         pushRegZ(5);
/*  754 */         pushRegZ(6);
/*  755 */         pushRegZ(7);
/*  756 */         pushRegZ(8);
/*  757 */         pushRegZ(9);
/*      */         
/*  759 */         localMethodGen.add((byte)-74, this.me.method("syscall", Type.INT, new Type[] { Type.INT, Type.INT, Type.INT, Type.INT, Type.INT, Type.INT, Type.INT }));
/*  760 */         setReg();
/*      */         
/*  762 */         localMethodGen.add((byte)42);
/*  763 */         localMethodGen.add((byte)-76, this.me.field("state", Type.INT));
/*  764 */         i11 = localMethodGen.add((byte)-103);
/*  765 */         preSetPC();
/*  766 */         localMethodGen.add((byte)18, paramInt1 + 4);
/*  767 */         setPC();
/*  768 */         leaveMethod();
/*  769 */         localMethodGen.setArg(i11, localMethodGen.size());
/*  770 */         break;
/*      */       case 13: 
/*  772 */         localMethodGen.add((byte)-69, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException"));
/*  773 */         localMethodGen.add((byte)89);
/*  774 */         localMethodGen.add((byte)18, "BREAK Code " + toHex(i6));
/*  775 */         localMethodGen.add((byte)-73, Type.Class.instance("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.Runtime$ExecutionException").method("<init>", Type.VOID, new Type[] { Type.STRING }));
/*  776 */         localMethodGen.add((byte)-65);
/*  777 */         i |= 0x1;
/*  778 */         break;
/*      */       case 16: 
/*  780 */         preSetReg(0 + i1);
/*  781 */         pushReg(64);
/*  782 */         setReg();
/*  783 */         break;
/*      */       case 17: 
/*  785 */         preSetReg(64);
/*  786 */         pushRegZ(0 + k);
/*  787 */         setReg();
/*  788 */         break;
/*      */       case 18: 
/*  790 */         preSetReg(0 + i1);
/*  791 */         pushReg(65);
/*  792 */         setReg();
/*  793 */         break;
/*      */       case 19: 
/*  795 */         preSetReg(65);
/*  796 */         pushRegZ(0 + k);
/*  797 */         setReg();
/*  798 */         break;
/*      */       case 24: 
/*  800 */         pushRegWZ(0 + k);
/*  801 */         localMethodGen.add((byte)-123);
/*  802 */         pushRegWZ(0 + m);
/*  803 */         localMethodGen.add((byte)-123);
/*  804 */         localMethodGen.add((byte)105);
/*  805 */         localMethodGen.add((byte)92);
/*      */         
/*  807 */         localMethodGen.add((byte)-120);
/*  808 */         if (preSetReg(65))
/*  809 */           localMethodGen.add((byte)95);
/*  810 */         setReg();
/*      */         
/*  812 */         localMethodGen.add((byte)18, 32);
/*  813 */         localMethodGen.add((byte)125);
/*  814 */         localMethodGen.add((byte)-120);
/*  815 */         if (preSetReg(64))
/*  816 */           localMethodGen.add((byte)95);
/*  817 */         setReg();
/*      */         
/*  819 */         break;
/*      */       case 25: 
/*  821 */         pushRegWZ(0 + k);
/*  822 */         localMethodGen.add((byte)-123);
/*  823 */         localMethodGen.add((byte)18, FFFFFFFF);
/*  824 */         localMethodGen.add((byte)Byte.MAX_VALUE);
/*  825 */         pushRegWZ(0 + m);
/*  826 */         localMethodGen.add((byte)-123);
/*  827 */         localMethodGen.add((byte)18, FFFFFFFF);
/*  828 */         localMethodGen.add((byte)Byte.MAX_VALUE);
/*  829 */         localMethodGen.add((byte)105);
/*  830 */         localMethodGen.add((byte)92);
/*      */         
/*  832 */         localMethodGen.add((byte)-120);
/*  833 */         if (preSetReg(65))
/*  834 */           localMethodGen.add((byte)95);
/*  835 */         setReg();
/*      */         
/*  837 */         localMethodGen.add((byte)18, 32);
/*  838 */         localMethodGen.add((byte)125);
/*  839 */         localMethodGen.add((byte)-120);
/*  840 */         if (preSetReg(64))
/*  841 */           localMethodGen.add((byte)95);
/*  842 */         setReg();
/*      */         
/*  844 */         break;
/*      */       case 26: 
/*  846 */         pushRegWZ(0 + k);
/*  847 */         pushRegWZ(0 + m);
/*  848 */         localMethodGen.add((byte)92);
/*      */         
/*  850 */         localMethodGen.add((byte)108);
/*  851 */         if (preSetReg(65))
/*  852 */           localMethodGen.add((byte)95);
/*  853 */         setReg();
/*      */         
/*  855 */         localMethodGen.add((byte)112);
/*  856 */         if (preSetReg(64))
/*  857 */           localMethodGen.add((byte)95);
/*  858 */         setReg();
/*      */         
/*  860 */         break;
/*      */       case 27: 
/*  862 */         pushRegWZ(0 + m);
/*  863 */         localMethodGen.add((byte)89);
/*  864 */         setTmp();
/*  865 */         i11 = localMethodGen.add((byte)-103);
/*      */         
/*  867 */         pushRegWZ(0 + k);
/*  868 */         localMethodGen.add((byte)-123);
/*  869 */         localMethodGen.add((byte)18, FFFFFFFF);
/*  870 */         localMethodGen.add((byte)Byte.MAX_VALUE);
/*  871 */         localMethodGen.add((byte)92);
/*  872 */         pushTmp();
/*  873 */         localMethodGen.add((byte)-123);
/*  874 */         localMethodGen.add((byte)18, FFFFFFFF);
/*      */         
/*  876 */         localMethodGen.add((byte)Byte.MAX_VALUE);
/*  877 */         localMethodGen.add((byte)94);
/*  878 */         localMethodGen.add((byte)109);
/*      */         
/*  880 */         localMethodGen.add((byte)-120);
/*  881 */         if (preSetReg(65))
/*  882 */           localMethodGen.add((byte)95);
/*  883 */         setReg();
/*      */         
/*  885 */         localMethodGen.add((byte)113);
/*  886 */         localMethodGen.add((byte)-120);
/*  887 */         if (preSetReg(64))
/*  888 */           localMethodGen.add((byte)95);
/*  889 */         setReg();
/*      */         
/*  891 */         localMethodGen.setArg(i11, localMethodGen.size());
/*      */         
/*  893 */         break;
/*      */       
/*      */       case 32: 
/*  896 */         throw new Compiler.Exn("ADD (add with oveflow trap) not suported");
/*      */       case 33: 
/*  898 */         preSetReg(0 + i1);
/*  899 */         if ((m != 0) && (k != 0)) {
/*  900 */           pushReg(0 + k);
/*  901 */           pushReg(0 + m);
/*  902 */           localMethodGen.add((byte)96);
/*  903 */         } else if (k != 0) {
/*  904 */           pushReg(0 + k);
/*      */         } else {
/*  906 */           pushRegZ(0 + m);
/*      */         }
/*  908 */         setReg();
/*  909 */         break;
/*      */       case 34: 
/*  911 */         throw new Compiler.Exn("SUB (add with oveflow trap) not suported");
/*      */       case 35: 
/*  913 */         preSetReg(0 + i1);
/*  914 */         if ((m != 0) && (k != 0)) {
/*  915 */           pushReg(0 + k);
/*  916 */           pushReg(0 + m);
/*  917 */           localMethodGen.add((byte)100);
/*  918 */         } else if (m != 0) {
/*  919 */           pushReg(0 + m);
/*  920 */           localMethodGen.add((byte)116);
/*      */         } else {
/*  922 */           pushRegZ(0 + k);
/*      */         }
/*  924 */         setReg();
/*  925 */         break;
/*      */       case 36: 
/*  927 */         preSetReg(0 + i1);
/*  928 */         pushRegWZ(0 + k);
/*  929 */         pushRegWZ(0 + m);
/*  930 */         localMethodGen.add((byte)126);
/*  931 */         setReg();
/*  932 */         break;
/*      */       case 37: 
/*  934 */         preSetReg(0 + i1);
/*  935 */         pushRegWZ(0 + k);
/*  936 */         pushRegWZ(0 + m);
/*  937 */         localMethodGen.add((byte)Byte.MIN_VALUE);
/*  938 */         setReg();
/*  939 */         break;
/*      */       case 38: 
/*  941 */         preSetReg(0 + i1);
/*  942 */         pushRegWZ(0 + k);
/*  943 */         pushRegWZ(0 + m);
/*  944 */         localMethodGen.add((byte)-126);
/*  945 */         setReg();
/*  946 */         break;
/*      */       case 39: 
/*  948 */         preSetReg(0 + i1);
/*  949 */         if ((k != 0) || (m != 0)) {
/*  950 */           if ((k != 0) && (m != 0)) {
/*  951 */             pushReg(0 + k);
/*  952 */             pushReg(0 + m);
/*  953 */             localMethodGen.add((byte)Byte.MIN_VALUE);
/*  954 */           } else if (k != 0) {
/*  955 */             pushReg(0 + k);
/*      */           } else {
/*  957 */             pushReg(0 + m);
/*      */           }
/*  959 */           localMethodGen.add((byte)2);
/*  960 */           localMethodGen.add((byte)-126);
/*      */         } else {
/*  962 */           localMethodGen.add((byte)18, -1);
/*      */         }
/*  964 */         setReg();
/*  965 */         break;
/*      */       case 42: 
/*  967 */         preSetReg(0 + i1);
/*  968 */         if (k != m) {
/*  969 */           pushRegZ(0 + k);
/*  970 */           pushRegZ(0 + m);
/*  971 */           i11 = localMethodGen.add((byte)-95);
/*  972 */           localMethodGen.add((byte)3);
/*  973 */           i12 = localMethodGen.add((byte)-89);
/*  974 */           localMethodGen.setArg(i11, localMethodGen.add((byte)4));
/*  975 */           localMethodGen.setArg(i12, localMethodGen.size());
/*      */         } else {
/*  977 */           localMethodGen.add((byte)18, 0);
/*      */         }
/*  979 */         setReg();
/*  980 */         break;
/*      */       case 43: 
/*  982 */         preSetReg(0 + i1);
/*  983 */         if (k != m) {
/*  984 */           if (k != 0) {
/*  985 */             pushReg(0 + k);
/*  986 */             localMethodGen.add((byte)-123);
/*  987 */             localMethodGen.add((byte)18, FFFFFFFF);
/*  988 */             localMethodGen.add((byte)Byte.MAX_VALUE);
/*  989 */             pushReg(0 + m);
/*  990 */             localMethodGen.add((byte)-123);
/*  991 */             localMethodGen.add((byte)18, FFFFFFFF);
/*  992 */             localMethodGen.add((byte)Byte.MAX_VALUE);
/*  993 */             localMethodGen.add((byte)-108);
/*  994 */             i11 = localMethodGen.add((byte)-101);
/*      */           } else {
/*  996 */             pushReg(0 + m);
/*  997 */             i11 = localMethodGen.add((byte)-102);
/*      */           }
/*  999 */           localMethodGen.add((byte)3);
/* 1000 */           i12 = localMethodGen.add((byte)-89);
/* 1001 */           localMethodGen.setArg(i11, localMethodGen.add((byte)4));
/* 1002 */           localMethodGen.setArg(i12, localMethodGen.size());
/*      */         } else {
/* 1004 */           localMethodGen.add((byte)18, 0);
/*      */         }
/* 1006 */         setReg();
/* 1007 */         break;
/*      */       case 1: case 5: case 10: case 11: case 14: case 15: case 20: case 21: case 22: case 23: case 28: case 29: case 30: case 31: case 40: case 41: default: 
/* 1009 */         throw new Compiler.Exn("Illegal instruction 0/" + i5);
/*      */       }
/*      */       
/*      */       break;
/*      */     case 1: 
/* 1014 */       switch (m) {
/*      */       case 0: 
/* 1016 */         if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
/* 1017 */         pushRegWZ(0 + k);
/* 1018 */         return doIfInstruction((byte)-101, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
/*      */       case 1: 
/* 1020 */         if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
/* 1021 */         pushRegWZ(0 + k);
/* 1022 */         return doIfInstruction((byte)-100, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
/*      */       case 16: 
/* 1024 */         if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
/* 1025 */         pushRegWZ(0 + k);
/* 1026 */         i11 = localMethodGen.add((byte)-100);
/* 1027 */         emitInstruction(-1, paramInt3, -1);
/* 1028 */         link(paramInt1);
/* 1029 */         branch(paramInt1, paramInt1 + i10 * 4 + 4);
/* 1030 */         localMethodGen.setArg(i11, localMethodGen.size());
/* 1031 */         break;
/*      */       case 17: 
/* 1033 */         if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
/* 1034 */         i11 = -1;
/* 1035 */         if (k != 0) {
/* 1036 */           pushRegWZ(0 + k);
/* 1037 */           i11 = localMethodGen.add((byte)-101);
/*      */         }
/* 1039 */         emitInstruction(-1, paramInt3, -1);
/* 1040 */         link(paramInt1);
/* 1041 */         branch(paramInt1, paramInt1 + i10 * 4 + 4);
/* 1042 */         if (i11 != -1) localMethodGen.setArg(i11, localMethodGen.size());
/* 1043 */         if (i11 == -1) i |= 0x1;
/*      */         break;
/*      */       default: 
/* 1046 */         throw new Compiler.Exn("Illegal Instruction 1/" + m);
/*      */       }
/*      */       
/*      */       break;
/*      */     case 2: 
/* 1051 */       if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
/* 1052 */       emitInstruction(-1, paramInt3, -1);
/* 1053 */       branch(paramInt1, paramInt1 & 0xF0000000 | i7 << 2);
/* 1054 */       i |= 0x1;
/* 1055 */       break;
/*      */     
/*      */     case 3: 
/* 1058 */       if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
/* 1059 */       i13 = paramInt1 & 0xF0000000 | i7 << 2;
/* 1060 */       emitInstruction(-1, paramInt3, -1);
/* 1061 */       link(paramInt1);
/* 1062 */       branch(paramInt1, i13);
/* 1063 */       i |= 0x1;
/* 1064 */       break;
/*      */     
/*      */     case 4: 
/* 1067 */       if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
/* 1068 */       if (k == m) {
/* 1069 */         emitInstruction(-1, paramInt3, -1);
/* 1070 */         branch(paramInt1, paramInt1 + i10 * 4 + 4);
/* 1071 */         i |= 0x1;
/* 1072 */       } else { if ((k == 0) || (m == 0)) {
/* 1073 */           pushReg(m == 0 ? 0 + k : 0 + m);
/* 1074 */           return doIfInstruction((byte)-103, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
/*      */         }
/* 1076 */         pushReg(0 + k);
/* 1077 */         pushReg(0 + m);
/* 1078 */         return doIfInstruction((byte)-97, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
/*      */       }
/*      */       break;
/*      */     case 5: 
/* 1082 */       if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
/* 1083 */       pushRegWZ(0 + k);
/* 1084 */       if (m == 0) {
/* 1085 */         return doIfInstruction((byte)-102, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
/*      */       }
/* 1087 */       pushReg(0 + m);
/* 1088 */       return doIfInstruction((byte)-96, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
/*      */     
/*      */     case 6: 
/* 1091 */       if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
/* 1092 */       pushRegWZ(0 + k);
/* 1093 */       return doIfInstruction((byte)-98, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
/*      */     case 7: 
/* 1095 */       if (paramInt1 == -1) throw new Compiler.Exn("pc modifying insn in delay slot");
/* 1096 */       pushRegWZ(0 + k);
/* 1097 */       return doIfInstruction((byte)-99, paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
/*      */     case 8: 
/* 1099 */       throw new Compiler.Exn("ADDI (add immediate with oveflow trap) not suported");
/*      */     case 9: 
/* 1101 */       if ((k != 0) && (i9 != 0) && (k == m) && (doLocal(m)) && (i9 >= 32768) && (i9 <= 32767))
/*      */       {
/* 1103 */         this.regLocalWritten[m] = true;
/* 1104 */         localMethodGen.add((byte)-124, new MethodGen.Pair(getLocalForReg(m), i9));
/*      */       } else {
/* 1106 */         preSetReg(0 + m);
/* 1107 */         addiu(k, i9);
/* 1108 */         setReg();
/*      */       }
/* 1110 */       break;
/*      */     case 10: 
/* 1112 */       preSetReg(0 + m);
/* 1113 */       pushRegWZ(0 + k);
/* 1114 */       localMethodGen.add((byte)18, i9);
/* 1115 */       i11 = localMethodGen.add((byte)-95);
/* 1116 */       localMethodGen.add((byte)3);
/* 1117 */       i12 = localMethodGen.add((byte)-89);
/* 1118 */       localMethodGen.setArg(i11, localMethodGen.add((byte)4));
/* 1119 */       localMethodGen.setArg(i12, localMethodGen.size());
/* 1120 */       setReg();
/* 1121 */       break;
/*      */     case 11: 
/* 1123 */       preSetReg(0 + m);
/* 1124 */       pushRegWZ(0 + k);
/* 1125 */       localMethodGen.add((byte)-123);
/* 1126 */       localMethodGen.add((byte)18, FFFFFFFF);
/* 1127 */       localMethodGen.add((byte)Byte.MAX_VALUE);
/*      */       
/* 1129 */       localMethodGen.add((byte)18, new Long(i9 & 0xFFFFFFFF));
/* 1130 */       localMethodGen.add((byte)-108);
/*      */       
/* 1132 */       i11 = localMethodGen.add((byte)-101);
/* 1133 */       localMethodGen.add((byte)3);
/* 1134 */       i12 = localMethodGen.add((byte)-89);
/* 1135 */       localMethodGen.setArg(i11, localMethodGen.add((byte)4));
/* 1136 */       localMethodGen.setArg(i12, localMethodGen.size());
/* 1137 */       setReg();
/* 1138 */       break;
/*      */     case 12: 
/* 1140 */       preSetReg(0 + m);
/* 1141 */       pushRegWZ(0 + k);
/* 1142 */       localMethodGen.add((byte)18, i8);
/* 1143 */       localMethodGen.add((byte)126);
/* 1144 */       setReg();
/* 1145 */       break;
/*      */     case 13: 
/* 1147 */       preSetReg(0 + m);
/* 1148 */       if ((k != 0) && (i8 != 0)) {
/* 1149 */         pushReg(0 + k);
/* 1150 */         localMethodGen.add((byte)18, i8);
/* 1151 */         localMethodGen.add((byte)Byte.MIN_VALUE);
/* 1152 */       } else if (k != 0) {
/* 1153 */         pushReg(0 + k);
/*      */       } else {
/* 1155 */         localMethodGen.add((byte)18, i8);
/*      */       }
/* 1157 */       setReg();
/* 1158 */       break;
/*      */     case 14: 
/* 1160 */       preSetReg(0 + m);
/* 1161 */       pushRegWZ(0 + k);
/* 1162 */       localMethodGen.add((byte)18, i8);
/* 1163 */       localMethodGen.add((byte)-126);
/* 1164 */       setReg();
/* 1165 */       break;
/*      */     case 15: 
/* 1167 */       preSetReg(0 + m);
/* 1168 */       localMethodGen.add((byte)18, i8 << 16);
/* 1169 */       setReg();
/* 1170 */       break;
/*      */     case 16: 
/* 1172 */       throw new Compiler.Exn("TLB/Exception support not implemented");
/*      */     case 17: 
/* 1174 */       switch (k) {
/*      */       case 0: 
/* 1176 */         preSetReg(0 + m);
/* 1177 */         pushReg(32 + i1);
/* 1178 */         setReg();
/* 1179 */         break;
/*      */       case 2: 
/* 1181 */         if (i2 != 31) throw new Compiler.Exn("FCR " + i2 + " unavailable");
/* 1182 */         preSetReg(0 + m);
/* 1183 */         pushReg(66);
/* 1184 */         setReg();
/* 1185 */         break;
/*      */       case 4: 
/* 1187 */         preSetReg(32 + i1);
/* 1188 */         if (m != 0) pushReg(0 + m); else
/* 1189 */           localMethodGen.add((byte)3);
/* 1190 */         setReg();
/* 1191 */         break;
/*      */       case 6: 
/* 1193 */         if (i2 != 31) throw new Compiler.Exn("FCR " + i2 + " unavailable");
/* 1194 */         preSetReg(66);
/* 1195 */         pushReg(0 + m);
/* 1196 */         setReg();
/* 1197 */         break;
/*      */       case 8: 
/* 1199 */         pushReg(66);
/* 1200 */         localMethodGen.add((byte)18, 8388608);
/* 1201 */         localMethodGen.add((byte)126);
/* 1202 */         return doIfInstruction((byte)((paramInt2 >>> 16 & 0x1) == 0 ? -103 : -102), paramInt1, paramInt1 + i10 * 4 + 4, paramInt3);
/*      */       
/*      */ 
/*      */       case 16: 
/*      */       case 17: 
/* 1207 */         i13 = k == 17 ? 1 : 0;
/* 1208 */         switch (i5) {
/*      */         case 0: 
/* 1210 */           preSetDouble(32 + i4, i13);
/* 1211 */           pushDouble(32 + i2, i13);
/* 1212 */           pushDouble(32 + n, i13);
/* 1213 */           localMethodGen.add((byte)(i13 != 0 ? 99 : 98));
/* 1214 */           setDouble(i13);
/* 1215 */           break;
/*      */         case 1: 
/* 1217 */           preSetDouble(32 + i4, i13);
/* 1218 */           pushDouble(32 + i2, i13);
/* 1219 */           pushDouble(32 + n, i13);
/* 1220 */           localMethodGen.add((byte)(i13 != 0 ? 103 : 102));
/* 1221 */           setDouble(i13);
/* 1222 */           break;
/*      */         case 2: 
/* 1224 */           preSetDouble(32 + i4, i13);
/* 1225 */           pushDouble(32 + i2, i13);
/* 1226 */           pushDouble(32 + n, i13);
/* 1227 */           localMethodGen.add((byte)(i13 != 0 ? 107 : 106));
/* 1228 */           setDouble(i13);
/* 1229 */           break;
/*      */         case 3: 
/* 1231 */           preSetDouble(32 + i4, i13);
/* 1232 */           pushDouble(32 + i2, i13);
/* 1233 */           pushDouble(32 + n, i13);
/* 1234 */           localMethodGen.add((byte)(i13 != 0 ? 111 : 110));
/* 1235 */           setDouble(i13);
/* 1236 */           break;
/*      */         case 5: 
/* 1238 */           preSetDouble(32 + i4, i13);
/*      */           
/*      */ 
/* 1241 */           pushDouble(32 + i2, i13);
/* 1242 */           localMethodGen.add((byte)(i13 != 0 ? 92 : 89));
/* 1243 */           localMethodGen.add((byte)(i13 != 0 ? 14 : 11));
/* 1244 */           localMethodGen.add((byte)(i13 != 0 ? -104 : -106));
/*      */           
/* 1246 */           i11 = localMethodGen.add((byte)-99);
/* 1247 */           localMethodGen.add((byte)(i13 != 0 ? 14 : 11));
/* 1248 */           if (i13 != 0) {
/* 1249 */             localMethodGen.add((byte)94);
/* 1250 */             localMethodGen.add((byte)88);
/*      */           } else {
/* 1252 */             localMethodGen.add((byte)95);
/*      */           }
/* 1254 */           localMethodGen.add((byte)(i13 != 0 ? 103 : 102));
/*      */           
/* 1256 */           localMethodGen.setArg(i11, localMethodGen.size());
/* 1257 */           setDouble(i13);
/*      */           
/* 1259 */           break;
/*      */         case 6: 
/* 1261 */           preSetReg(32 + i4);
/* 1262 */           pushReg(32 + i2);
/* 1263 */           setReg();
/*      */           
/* 1265 */           if (i13 != 0) {
/* 1266 */             preSetReg(32 + i4 + 1);
/* 1267 */             pushReg(32 + i2 + 1);
/* 1268 */             setReg();
/*      */           }
/*      */           break;
/*      */         case 7: 
/* 1272 */           preSetDouble(32 + i4, i13);
/* 1273 */           pushDouble(32 + i2, i13);
/* 1274 */           localMethodGen.add((byte)(i13 != 0 ? 119 : 118));
/* 1275 */           setDouble(i13);
/* 1276 */           break;
/*      */         case 32: 
/* 1278 */           preSetFloat(32 + i4);
/* 1279 */           pushDouble(32 + i2, i13);
/* 1280 */           if (i13 != 0) localMethodGen.add((byte)-112);
/* 1281 */           setFloat();
/* 1282 */           break;
/*      */         case 33: 
/* 1284 */           preSetDouble(32 + i4);
/* 1285 */           pushDouble(32 + i2, i13);
/* 1286 */           if (i13 == 0) localMethodGen.add((byte)-115);
/* 1287 */           setDouble();
/* 1288 */           break;
/*      */         case 36: 
/* 1290 */           MethodGen.Switch.Table localTable = new MethodGen.Switch.Table(0, 3);
/* 1291 */           preSetReg(32 + i4);
/* 1292 */           pushDouble(32 + i2, i13);
/* 1293 */           pushReg(66);
/* 1294 */           localMethodGen.add((byte)6);
/* 1295 */           localMethodGen.add((byte)126);
/* 1296 */           localMethodGen.add((byte)-86, localTable);
/*      */           
/*      */ 
/* 1299 */           localTable.setTarget(2, localMethodGen.size());
/* 1300 */           if (i13 == 0) localMethodGen.add((byte)-115);
/* 1301 */           localMethodGen.add((byte)-72, Type.Class.instance("java.lang.Math").method("ceil", Type.DOUBLE, new Type[] { Type.DOUBLE }));
/* 1302 */           if (i13 == 0) localMethodGen.add((byte)-112);
/* 1303 */           i11 = localMethodGen.add((byte)-89);
/*      */           
/*      */ 
/* 1306 */           localTable.setTarget(0, localMethodGen.size());
/* 1307 */           localMethodGen.add((byte)18, i13 != 0 ? POINT_5_D : POINT_5_F);
/* 1308 */           localMethodGen.add((byte)(i13 != 0 ? 99 : 98));
/*      */           
/*      */ 
/*      */ 
/* 1312 */           localTable.setTarget(3, localMethodGen.size());
/* 1313 */           if (i13 == 0) localMethodGen.add((byte)-115);
/* 1314 */           localMethodGen.add((byte)-72, Type.Class.instance("java.lang.Math").method("floor", Type.DOUBLE, new Type[] { Type.DOUBLE }));
/* 1315 */           if (i13 == 0) { localMethodGen.add((byte)-112);
/*      */           }
/* 1317 */           localTable.setTarget(1, localMethodGen.size());
/* 1318 */           localTable.setDefaultTarget(localMethodGen.size());
/* 1319 */           localMethodGen.setArg(i11, localMethodGen.size());
/*      */           
/* 1321 */           localMethodGen.add((byte)(i13 != 0 ? -114 : -117));
/* 1322 */           setReg();
/*      */           
/* 1324 */           break;
/*      */         
/*      */         case 50: 
/*      */         case 60: 
/*      */         case 62: 
/* 1329 */           preSetReg(66);
/* 1330 */           pushReg(66);
/* 1331 */           localMethodGen.add((byte)18, -8388609);
/* 1332 */           localMethodGen.add((byte)126);
/* 1333 */           pushDouble(32 + i2, i13);
/* 1334 */           pushDouble(32 + n, i13);
/* 1335 */           localMethodGen.add((byte)(i13 != 0 ? -104 : -106));
/* 1336 */           switch (i5) {
/* 1337 */           case 50:  i11 = localMethodGen.add((byte)-102); break;
/* 1338 */           case 60:  i11 = localMethodGen.add((byte)-100); break;
/* 1339 */           case 62:  i11 = localMethodGen.add((byte)-99); break;
/* 1340 */           default:  i11 = -1;
/*      */           }
/* 1342 */           localMethodGen.add((byte)18, 8388608);
/* 1343 */           localMethodGen.add((byte)Byte.MIN_VALUE);
/* 1344 */           localMethodGen.setArg(i11, localMethodGen.size());
/* 1345 */           setReg();
/* 1346 */           break;
/* 1347 */         default:  throw new Compiler.Exn("Invalid Instruction 17/" + k + "/" + i5);
/*      */         }
/*      */         
/*      */         break;
/*      */       case 20: 
/* 1352 */         switch (i5) {
/*      */         case 32: 
/* 1354 */           preSetFloat(32 + i4);
/* 1355 */           pushReg(32 + i2);
/* 1356 */           localMethodGen.add((byte)-122);
/* 1357 */           setFloat();
/* 1358 */           break;
/*      */         case 33: 
/* 1360 */           preSetDouble(32 + i4);
/* 1361 */           pushReg(32 + i2);
/* 1362 */           localMethodGen.add((byte)-121);
/* 1363 */           setDouble();
/* 1364 */           break;
/* 1365 */         default:  throw new Compiler.Exn("Invalid Instruction 17/" + k + "/" + i5);
/*      */         }
/*      */         break;
/*      */       case 1: case 3: case 5: case 7: case 9: case 10: case 11: 
/*      */       case 12: case 13: case 14: case 15: case 18: case 19: default: 
/* 1370 */         throw new Compiler.Exn("Invalid Instruction 17/" + k);
/*      */       }
/*      */       break;
/*      */     case 18: 
/*      */     case 19: 
/* 1375 */       throw new Compiler.Exn("coprocessor 2 and 3 instructions not available");
/*      */     case 32: 
/* 1377 */       preSetReg(0 + m);
/* 1378 */       addiu(0 + k, i9);
/* 1379 */       setTmp();
/* 1380 */       preMemRead();
/* 1381 */       pushTmp();
/* 1382 */       memRead(true);
/* 1383 */       pushTmp();
/*      */       
/* 1385 */       localMethodGen.add((byte)2);
/* 1386 */       localMethodGen.add((byte)-126);
/* 1387 */       localMethodGen.add((byte)6);
/* 1388 */       localMethodGen.add((byte)126);
/* 1389 */       localMethodGen.add((byte)6);
/* 1390 */       localMethodGen.add((byte)120);
/* 1391 */       localMethodGen.add((byte)124);
/* 1392 */       localMethodGen.add((byte)-111);
/* 1393 */       setReg();
/* 1394 */       break;
/*      */     
/*      */     case 33: 
/* 1397 */       preSetReg(0 + m);
/* 1398 */       addiu(0 + k, i9);
/* 1399 */       setTmp();
/* 1400 */       preMemRead();
/* 1401 */       pushTmp();
/* 1402 */       memRead(true);
/* 1403 */       pushTmp();
/*      */       
/* 1405 */       localMethodGen.add((byte)2);
/* 1406 */       localMethodGen.add((byte)-126);
/* 1407 */       localMethodGen.add((byte)5);
/* 1408 */       localMethodGen.add((byte)126);
/* 1409 */       localMethodGen.add((byte)6);
/* 1410 */       localMethodGen.add((byte)120);
/* 1411 */       localMethodGen.add((byte)124);
/* 1412 */       localMethodGen.add((byte)-109);
/* 1413 */       setReg();
/* 1414 */       break;
/*      */     
/*      */     case 34: 
/* 1417 */       preSetReg(0 + m);
/* 1418 */       addiu(0 + k, i9);
/* 1419 */       setTmp();
/*      */       
/* 1421 */       pushRegWZ(0 + m);
/* 1422 */       localMethodGen.add((byte)18, 16777215);
/* 1423 */       pushTmp();
/*      */       
/* 1425 */       localMethodGen.add((byte)2);
/* 1426 */       localMethodGen.add((byte)-126);
/* 1427 */       localMethodGen.add((byte)6);
/* 1428 */       localMethodGen.add((byte)126);
/* 1429 */       localMethodGen.add((byte)6);
/* 1430 */       localMethodGen.add((byte)120);
/* 1431 */       localMethodGen.add((byte)124);
/* 1432 */       localMethodGen.add((byte)126);
/*      */       
/* 1434 */       preMemRead();
/* 1435 */       pushTmp();
/* 1436 */       memRead(true);
/* 1437 */       pushTmp();
/*      */       
/* 1439 */       localMethodGen.add((byte)6);
/* 1440 */       localMethodGen.add((byte)126);
/* 1441 */       localMethodGen.add((byte)6);
/* 1442 */       localMethodGen.add((byte)120);
/* 1443 */       localMethodGen.add((byte)120);
/* 1444 */       localMethodGen.add((byte)Byte.MIN_VALUE);
/*      */       
/* 1446 */       setReg();
/*      */       
/* 1448 */       break;
/*      */     
/*      */ 
/*      */     case 35: 
/* 1452 */       preSetReg(0 + m);
/* 1453 */       memRead(0 + k, i9);
/* 1454 */       setReg();
/* 1455 */       break;
/*      */     case 36: 
/* 1457 */       preSetReg(0 + m);
/* 1458 */       addiu(0 + k, i9);
/* 1459 */       setTmp();
/* 1460 */       preMemRead();
/* 1461 */       pushTmp();
/* 1462 */       memRead(true);
/* 1463 */       pushTmp();
/*      */       
/* 1465 */       localMethodGen.add((byte)2);
/* 1466 */       localMethodGen.add((byte)-126);
/* 1467 */       localMethodGen.add((byte)6);
/* 1468 */       localMethodGen.add((byte)126);
/* 1469 */       localMethodGen.add((byte)6);
/* 1470 */       localMethodGen.add((byte)120);
/* 1471 */       localMethodGen.add((byte)124);
/* 1472 */       localMethodGen.add((byte)18, 255);
/* 1473 */       localMethodGen.add((byte)126);
/* 1474 */       setReg();
/* 1475 */       break;
/*      */     
/*      */     case 37: 
/* 1478 */       preSetReg(0 + m);
/* 1479 */       addiu(0 + k, i9);
/* 1480 */       setTmp();
/* 1481 */       preMemRead();
/* 1482 */       pushTmp();
/* 1483 */       memRead(true);
/* 1484 */       pushTmp();
/*      */       
/* 1486 */       localMethodGen.add((byte)2);
/* 1487 */       localMethodGen.add((byte)-126);
/* 1488 */       localMethodGen.add((byte)5);
/* 1489 */       localMethodGen.add((byte)126);
/* 1490 */       localMethodGen.add((byte)6);
/* 1491 */       localMethodGen.add((byte)120);
/* 1492 */       localMethodGen.add((byte)124);
/*      */       
/*      */ 
/* 1495 */       localMethodGen.add((byte)-110);
/* 1496 */       setReg();
/* 1497 */       break;
/*      */     
/*      */     case 38: 
/* 1500 */       preSetReg(0 + m);
/* 1501 */       addiu(0 + k, i9);
/* 1502 */       setTmp();
/*      */       
/* 1504 */       pushRegWZ(0 + m);
/* 1505 */       localMethodGen.add((byte)18, 65280);
/* 1506 */       pushTmp();
/*      */       
/* 1508 */       localMethodGen.add((byte)6);
/* 1509 */       localMethodGen.add((byte)126);
/* 1510 */       localMethodGen.add((byte)6);
/* 1511 */       localMethodGen.add((byte)120);
/* 1512 */       localMethodGen.add((byte)120);
/* 1513 */       localMethodGen.add((byte)126);
/*      */       
/* 1515 */       preMemRead();
/* 1516 */       pushTmp();
/* 1517 */       memRead(true);
/* 1518 */       pushTmp();
/*      */       
/* 1520 */       localMethodGen.add((byte)2);
/* 1521 */       localMethodGen.add((byte)-126);
/* 1522 */       localMethodGen.add((byte)6);
/* 1523 */       localMethodGen.add((byte)126);
/* 1524 */       localMethodGen.add((byte)6);
/* 1525 */       localMethodGen.add((byte)120);
/* 1526 */       localMethodGen.add((byte)124);
/* 1527 */       localMethodGen.add((byte)Byte.MIN_VALUE);
/*      */       
/*      */ 
/* 1530 */       setReg();
/* 1531 */       break;
/*      */     
/*      */     case 40: 
/* 1534 */       addiu(0 + k, i9);
/* 1535 */       setTmp();
/*      */       
/* 1537 */       preMemRead(true);
/* 1538 */       pushTmp();
/* 1539 */       memRead(true);
/*      */       
/* 1541 */       localMethodGen.add((byte)18, -16777216);
/* 1542 */       pushTmp();
/*      */       
/* 1544 */       localMethodGen.add((byte)6);
/* 1545 */       localMethodGen.add((byte)126);
/* 1546 */       localMethodGen.add((byte)6);
/* 1547 */       localMethodGen.add((byte)120);
/* 1548 */       localMethodGen.add((byte)124);
/* 1549 */       localMethodGen.add((byte)2);
/* 1550 */       localMethodGen.add((byte)-126);
/* 1551 */       localMethodGen.add((byte)126);
/*      */       
/* 1553 */       if (m != 0) {
/* 1554 */         pushReg(0 + m);
/* 1555 */         localMethodGen.add((byte)18, 255);
/* 1556 */         localMethodGen.add((byte)126);
/*      */       } else {
/* 1558 */         localMethodGen.add((byte)18, 0);
/*      */       }
/* 1560 */       pushTmp();
/*      */       
/* 1562 */       localMethodGen.add((byte)2);
/* 1563 */       localMethodGen.add((byte)-126);
/* 1564 */       localMethodGen.add((byte)6);
/* 1565 */       localMethodGen.add((byte)126);
/* 1566 */       localMethodGen.add((byte)6);
/* 1567 */       localMethodGen.add((byte)120);
/* 1568 */       localMethodGen.add((byte)120);
/* 1569 */       localMethodGen.add((byte)Byte.MIN_VALUE);
/*      */       
/* 1571 */       memWrite();
/*      */       
/* 1573 */       break;
/*      */     
/*      */     case 41: 
/* 1576 */       addiu(0 + k, i9);
/* 1577 */       setTmp();
/*      */       
/* 1579 */       preMemRead(true);
/* 1580 */       pushTmp();
/* 1581 */       memRead(true);
/*      */       
/* 1583 */       localMethodGen.add((byte)18, 65535);
/* 1584 */       pushTmp();
/*      */       
/* 1586 */       localMethodGen.add((byte)5);
/* 1587 */       localMethodGen.add((byte)126);
/* 1588 */       localMethodGen.add((byte)6);
/* 1589 */       localMethodGen.add((byte)120);
/* 1590 */       localMethodGen.add((byte)120);
/* 1591 */       localMethodGen.add((byte)126);
/*      */       
/* 1593 */       if (m != 0) {
/* 1594 */         pushReg(0 + m);
/* 1595 */         localMethodGen.add((byte)18, 65535);
/* 1596 */         localMethodGen.add((byte)126);
/*      */       } else {
/* 1598 */         localMethodGen.add((byte)18, 0);
/*      */       }
/* 1600 */       pushTmp();
/*      */       
/* 1602 */       localMethodGen.add((byte)2);
/* 1603 */       localMethodGen.add((byte)-126);
/* 1604 */       localMethodGen.add((byte)5);
/* 1605 */       localMethodGen.add((byte)126);
/* 1606 */       localMethodGen.add((byte)6);
/* 1607 */       localMethodGen.add((byte)120);
/* 1608 */       localMethodGen.add((byte)120);
/* 1609 */       localMethodGen.add((byte)Byte.MIN_VALUE);
/*      */       
/* 1611 */       memWrite();
/*      */       
/* 1613 */       break;
/*      */     
/*      */     case 42: 
/* 1616 */       addiu(0 + k, i9);
/* 1617 */       setTmp();
/*      */       
/* 1619 */       preMemRead(true);
/* 1620 */       pushTmp();
/* 1621 */       memRead(true);
/*      */       
/* 1623 */       localMethodGen.add((byte)18, 65280);
/* 1624 */       pushTmp();
/*      */       
/* 1626 */       localMethodGen.add((byte)2);
/* 1627 */       localMethodGen.add((byte)-126);
/* 1628 */       localMethodGen.add((byte)6);
/* 1629 */       localMethodGen.add((byte)126);
/* 1630 */       localMethodGen.add((byte)6);
/* 1631 */       localMethodGen.add((byte)120);
/* 1632 */       localMethodGen.add((byte)120);
/* 1633 */       localMethodGen.add((byte)126);
/*      */       
/* 1635 */       pushRegWZ(0 + m);
/* 1636 */       pushTmp();
/*      */       
/* 1638 */       localMethodGen.add((byte)6);
/* 1639 */       localMethodGen.add((byte)126);
/* 1640 */       localMethodGen.add((byte)6);
/* 1641 */       localMethodGen.add((byte)120);
/* 1642 */       localMethodGen.add((byte)124);
/* 1643 */       localMethodGen.add((byte)Byte.MIN_VALUE);
/*      */       
/* 1645 */       memWrite();
/* 1646 */       break;
/*      */     
/*      */ 
/*      */     case 43: 
/* 1650 */       preMemWrite1();
/* 1651 */       preMemWrite2(0 + k, i9);
/* 1652 */       pushRegZ(0 + m);
/* 1653 */       memWrite();
/* 1654 */       break;
/*      */     case 46: 
/* 1656 */       addiu(0 + k, i9);
/* 1657 */       setTmp();
/*      */       
/* 1659 */       preMemRead(true);
/* 1660 */       pushTmp();
/* 1661 */       memRead(true);
/*      */       
/* 1663 */       localMethodGen.add((byte)18, 16777215);
/* 1664 */       pushTmp();
/*      */       
/* 1666 */       localMethodGen.add((byte)6);
/* 1667 */       localMethodGen.add((byte)126);
/* 1668 */       localMethodGen.add((byte)6);
/* 1669 */       localMethodGen.add((byte)120);
/* 1670 */       localMethodGen.add((byte)124);
/* 1671 */       localMethodGen.add((byte)126);
/*      */       
/* 1673 */       pushRegWZ(0 + m);
/* 1674 */       pushTmp();
/*      */       
/* 1676 */       localMethodGen.add((byte)2);
/* 1677 */       localMethodGen.add((byte)-126);
/* 1678 */       localMethodGen.add((byte)6);
/* 1679 */       localMethodGen.add((byte)126);
/* 1680 */       localMethodGen.add((byte)6);
/* 1681 */       localMethodGen.add((byte)120);
/* 1682 */       localMethodGen.add((byte)120);
/* 1683 */       localMethodGen.add((byte)Byte.MIN_VALUE);
/*      */       
/* 1685 */       memWrite();
/* 1686 */       break;
/*      */     
/*      */ 
/*      */     case 48: 
/* 1690 */       preSetReg(0 + m);
/* 1691 */       memRead(0 + k, i9);
/* 1692 */       setReg();
/* 1693 */       break;
/*      */     
/*      */     case 49: 
/* 1696 */       preSetReg(32 + m);
/* 1697 */       memRead(0 + k, i9);
/* 1698 */       setReg();
/* 1699 */       break;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     case 56: 
/* 1705 */       preSetReg(0 + m);
/* 1706 */       preMemWrite1();
/* 1707 */       preMemWrite2(0 + k, i9);
/* 1708 */       pushReg(0 + m);
/* 1709 */       memWrite();
/* 1710 */       localMethodGen.add((byte)18, 1);
/* 1711 */       setReg();
/* 1712 */       break;
/*      */     
/*      */     case 57: 
/* 1715 */       preMemWrite1();
/* 1716 */       preMemWrite2(0 + k, i9);
/* 1717 */       pushReg(32 + m);
/* 1718 */       memWrite();
/* 1719 */       break;
/*      */     case 20: case 21: case 22: case 23: case 24: case 25: case 26: case 27: case 28: case 29: case 30: case 31: case 39: case 44: case 45: case 47: case 50: case 51: case 52: case 53: case 54: case 55: default: 
/* 1721 */       throw new Compiler.Exn("Invalid Instruction: " + j + " at " + toHex(paramInt1));
/*      */     }
/* 1723 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */   private static final int F = 32;
/*      */   
/*      */   private static final int HI = 64;
/*      */   
/*      */   private static final int LO = 65;
/*      */   private static final int FCSR = 66;
/*      */   private static final int REG_COUNT = 67;
/* 1734 */   private static final String[] regField = { "r0", "r1", "r2", "r3", "r4", "r5", "r6", "r7", "r8", "r9", "r10", "r11", "r12", "r13", "r14", "r15", "r16", "r17", "r18", "r19", "r20", "r21", "r22", "r23", "r24", "r25", "r26", "r27", "r28", "r29", "r30", "r31", "f0", "f1", "f2", "f3", "f4", "f5", "f6", "f7", "f8", "f9", "f10", "f11", "f12", "f13", "f14", "f15", "f16", "f17", "f18", "f19", "f20", "f21", "f22", "f23", "f24", "f25", "f26", "f27", "f28", "f29", "f30", "f31", "hi", "lo", "fcsr" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int MAX_LOCALS = 4;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int LOAD_LENGTH = 3;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 1749 */   private int[] regLocalMapping = new int[67];
/* 1750 */   private boolean[] regLocalWritten = new boolean[67];
/*      */   private int nextAvailLocal;
/*      */   private int loadsStart;
/*      */   private int preSetRegStackPos;
/*      */   
/* 1755 */   private boolean doLocal(int paramInt) { return (paramInt == 2) || (paramInt == 3) || (paramInt == 4) || (paramInt == 29); }
/*      */   
/*      */   private int getLocalForReg(int paramInt)
/*      */   {
/* 1759 */     if (this.regLocalMapping[paramInt] != 0) return this.regLocalMapping[paramInt];
/* 1760 */     this.regLocalMapping[paramInt] = (this.nextAvailLocal++);
/* 1761 */     return this.regLocalMapping[paramInt];
/*      */   }
/*      */   
/*      */   private void fixupRegsStart() {
/* 1765 */     for (int i = 0; i < 67; i++) {
/* 1766 */       this.regLocalMapping[i] = 0;
/* 1767 */       this.regLocalWritten[i] = false;
/*      */     }
/* 1769 */     this.nextAvailLocal = (this.onePage ? 4 : 5);
/* 1770 */     this.loadsStart = this.mg.size();
/* 1771 */     for (i = 0; i < 12; i++)
/* 1772 */       this.mg.add((byte)0);
/*      */   }
/*      */   
/*      */   private void fixupRegsEnd() {
/* 1776 */     int i = this.loadsStart;
/* 1777 */     for (int j = 0; j < 67; j++)
/* 1778 */       if (this.regLocalMapping[j] != 0) {
/* 1779 */         this.mg.set(i++, (byte)42);
/* 1780 */         this.mg.set(i++, (byte)-76, this.me.field(regField[j], Type.INT));
/* 1781 */         this.mg.set(i++, (byte)54, this.regLocalMapping[j]);
/*      */         
/* 1783 */         if (this.regLocalWritten[j] != 0) {
/* 1784 */           this.mg.add((byte)42);
/* 1785 */           this.mg.add((byte)21, this.regLocalMapping[j]);
/* 1786 */           this.mg.add((byte)-75, this.me.field(regField[j], Type.INT));
/*      */         }
/*      */       }
/*      */   }
/*      */   
/*      */   private void restoreChangedRegs() {
/* 1792 */     for (int i = 0; i < 67; i++) {
/* 1793 */       if (this.regLocalWritten[i] != 0) {
/* 1794 */         this.mg.add((byte)42);
/* 1795 */         this.mg.add((byte)21, this.regLocalMapping[i]);
/* 1796 */         this.mg.add((byte)-75, this.me.field(regField[i], Type.INT));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private int pushRegWZ(int paramInt) {
/* 1802 */     if (paramInt == 0) {
/* 1803 */       this.warn.println("Warning: Pushing r0!");
/* 1804 */       new Exception().printStackTrace(this.warn);
/*      */     }
/* 1806 */     return pushRegZ(paramInt);
/*      */   }
/*      */   
/*      */   private int pushRegZ(int paramInt) {
/* 1810 */     if (paramInt == 0) return this.mg.add((byte)3);
/* 1811 */     return pushReg(paramInt);
/*      */   }
/*      */   
/*      */   private int pushReg(int paramInt)
/*      */   {
/* 1816 */     int i = this.mg.size();
/* 1817 */     if (doLocal(paramInt)) {
/* 1818 */       this.mg.add((byte)21, getLocalForReg(paramInt));
/* 1819 */     } else if ((paramInt >= 32) && (paramInt <= 63) && (this.singleFloat)) {
/* 1820 */       this.mg.add((byte)42);
/* 1821 */       this.mg.add((byte)-76, this.me.field(regField[paramInt], Type.FLOAT));
/* 1822 */       this.mg.add((byte)-72, Type.FLOAT_OBJECT.method("floatToIntBits", Type.INT, new Type[] { Type.FLOAT }));
/*      */     } else {
/* 1824 */       this.mg.add((byte)42);
/* 1825 */       this.mg.add((byte)-76, this.me.field(regField[paramInt], Type.INT));
/*      */     }
/* 1827 */     return i;
/*      */   }
/*      */   
/*      */ 
/* 1831 */   private int[] preSetRegStack = new int[8];
/*      */   private int memWriteStage;
/*      */   
/*      */   private boolean preSetReg(int paramInt) {
/* 1835 */     this.preSetRegStack[this.preSetRegStackPos] = paramInt;
/* 1836 */     this.preSetRegStackPos += 1;
/* 1837 */     if (doLocal(paramInt)) {
/* 1838 */       return false;
/*      */     }
/* 1840 */     this.mg.add((byte)42);
/* 1841 */     return true;
/*      */   }
/*      */   
/*      */   private int setReg()
/*      */   {
/* 1846 */     if (this.preSetRegStackPos == 0) throw new RuntimeException("didn't do preSetReg");
/* 1847 */     this.preSetRegStackPos -= 1;
/* 1848 */     int i = this.preSetRegStack[this.preSetRegStackPos];
/* 1849 */     int j = this.mg.size();
/* 1850 */     if (doLocal(i)) {
/* 1851 */       this.mg.add((byte)54, getLocalForReg(i));
/* 1852 */       this.regLocalWritten[i] = true;
/* 1853 */     } else if ((i >= 32) && (i <= 63) && (this.singleFloat)) {
/* 1854 */       this.mg.add((byte)-72, Type.FLOAT_OBJECT.method("intBitsToFloat", Type.FLOAT, new Type[] { Type.INT }));
/* 1855 */       this.mg.add((byte)-75, this.me.field(regField[i], Type.FLOAT));
/*      */     } else {
/* 1857 */       this.mg.add((byte)-75, this.me.field(regField[i], Type.INT));
/*      */     }
/* 1859 */     return j;
/*      */   }
/*      */   
/* 1862 */   private int preSetPC() { return this.mg.add((byte)42); }
/*      */   
/* 1864 */   private int setPC() { return this.mg.add((byte)-75, this.me.field("pc", Type.INT)); }
/*      */   
/*      */ 
/*      */ 
/* 1868 */   private int pushFloat(int paramInt) throws Compiler.Exn { return pushDouble(paramInt, false); }
/*      */   
/* 1870 */   private int pushDouble(int paramInt, boolean paramBoolean) throws Compiler.Exn { if ((paramInt < 32) || (paramInt >= 64)) throw new IllegalArgumentException("" + paramInt);
/* 1871 */     int i = this.mg.size();
/* 1872 */     if (paramBoolean) {
/* 1873 */       if (this.singleFloat) throw new Compiler.Exn("Double operations not supported when singleFloat is enabled");
/* 1874 */       if (paramInt == 63) throw new Compiler.Exn("Tried to use a double in f31");
/* 1875 */       pushReg(paramInt + 1);
/* 1876 */       this.mg.add((byte)-123);
/* 1877 */       this.mg.add((byte)18, 32);
/* 1878 */       this.mg.add((byte)121);
/* 1879 */       pushReg(paramInt);
/* 1880 */       this.mg.add((byte)-123);
/* 1881 */       this.mg.add((byte)18, FFFFFFFF);
/* 1882 */       this.mg.add((byte)Byte.MAX_VALUE);
/* 1883 */       this.mg.add((byte)-127);
/* 1884 */       this.mg.add((byte)-72, Type.DOUBLE_OBJECT.method("longBitsToDouble", Type.DOUBLE, new Type[] { Type.LONG }));
/* 1885 */     } else if (this.singleFloat) {
/* 1886 */       this.mg.add((byte)42);
/* 1887 */       this.mg.add((byte)-76, this.me.field(regField[paramInt], Type.FLOAT));
/*      */     } else {
/* 1889 */       pushReg(paramInt);
/* 1890 */       this.mg.add((byte)-72, Type.Class.instance("java.lang.Float").method("intBitsToFloat", Type.FLOAT, new Type[] { Type.INT }));
/*      */     }
/* 1892 */     return i;
/*      */   }
/*      */   
/* 1895 */   private void preSetFloat(int paramInt) { preSetDouble(paramInt, false); }
/* 1896 */   private void preSetDouble(int paramInt) { preSetDouble(paramInt, true); }
/* 1897 */   private void preSetDouble(int paramInt, boolean paramBoolean) { preSetReg(paramInt); }
/*      */   
/* 1899 */   private int setFloat() throws Compiler.Exn { return setDouble(false); }
/* 1900 */   private int setDouble() throws Compiler.Exn { return setDouble(true); }
/*      */   
/* 1902 */   private int setDouble(boolean paramBoolean) throws Compiler.Exn { int i = this.preSetRegStack[(this.preSetRegStackPos - 1)];
/* 1903 */     if ((i < 32) || (i >= 64)) throw new IllegalArgumentException("" + i);
/* 1904 */     int j = this.mg.size();
/* 1905 */     if (paramBoolean) {
/* 1906 */       if (this.singleFloat) throw new Compiler.Exn("Double operations not supported when singleFloat is enabled");
/* 1907 */       if (i == 63) throw new Compiler.Exn("Tried to use a double in f31");
/* 1908 */       this.mg.add((byte)-72, Type.DOUBLE_OBJECT.method("doubleToLongBits", Type.LONG, new Type[] { Type.DOUBLE }));
/* 1909 */       this.mg.add((byte)92);
/* 1910 */       this.mg.add((byte)18, 32);
/* 1911 */       this.mg.add((byte)125);
/* 1912 */       this.mg.add((byte)-120);
/* 1913 */       if (preSetReg(i + 1))
/* 1914 */         this.mg.add((byte)95);
/* 1915 */       setReg();
/* 1916 */       this.mg.add((byte)-120);
/* 1917 */       setReg();
/* 1918 */     } else if (this.singleFloat)
/*      */     {
/* 1920 */       this.preSetRegStackPos -= 1;
/* 1921 */       this.mg.add((byte)-75, this.me.field(regField[i], Type.FLOAT));
/*      */     }
/*      */     else {
/* 1924 */       this.mg.add((byte)-72, Type.FLOAT_OBJECT.method("floatToRawIntBits", Type.INT, new Type[] { Type.FLOAT }));
/* 1925 */       setReg();
/*      */     }
/* 1927 */     return j;
/*      */   }
/*      */   
/* 1930 */   private void pushTmp() { this.mg.add((byte)27); }
/* 1931 */   private void setTmp() { this.mg.add((byte)60); }
/*      */   
/*      */   private void addiu(int paramInt1, int paramInt2) {
/* 1934 */     if ((paramInt1 != 0) && (paramInt2 != 0)) {
/* 1935 */       pushReg(paramInt1);
/* 1936 */       this.mg.add((byte)18, paramInt2);
/* 1937 */       this.mg.add((byte)96);
/* 1938 */     } else if (paramInt1 != 0) {
/* 1939 */       pushReg(paramInt1);
/*      */     } else {
/* 1941 */       this.mg.add((byte)18, paramInt2);
/*      */     }
/*      */   }
/*      */   
/*      */   private void preMemWrite1() {
/* 1946 */     if (this.memWriteStage != 0) throw new Error("pending preMemWrite1/2");
/* 1947 */     this.memWriteStage = 1;
/* 1948 */     if (this.onePage) {
/* 1949 */       this.mg.add((byte)44);
/* 1950 */     } else if (this.fastMem) {
/* 1951 */       this.mg.add((byte)25, 3);
/*      */     } else
/* 1953 */       this.mg.add((byte)42);
/*      */   }
/*      */   
/*      */   private void preMemWrite2(int paramInt1, int paramInt2) {
/* 1957 */     addiu(paramInt1, paramInt2);
/* 1958 */     preMemWrite2();
/*      */   }
/*      */   
/* 1961 */   private void preMemWrite2() { preMemWrite2(false); }
/*      */   
/* 1963 */   private void preMemWrite2(boolean paramBoolean) { if (this.memWriteStage != 1) throw new Error("pending preMemWrite2 or no preMemWrite1");
/* 1964 */     this.memWriteStage = 2;
/*      */     
/* 1966 */     if (this.nullPointerCheck) {
/* 1967 */       this.mg.add((byte)89);
/* 1968 */       this.mg.add((byte)42);
/* 1969 */       this.mg.add((byte)95);
/*      */       
/* 1971 */       this.mg.add((byte)-74, this.me.method("nullPointerCheck", Type.VOID, new Type[] { Type.INT }));
/*      */     }
/*      */     
/* 1974 */     if (this.onePage) {
/* 1975 */       this.mg.add((byte)5);
/* 1976 */       this.mg.add((byte)124);
/* 1977 */     } else if (this.fastMem) {
/* 1978 */       if (!paramBoolean)
/* 1979 */         this.mg.add((byte)90);
/* 1980 */       this.mg.add((byte)18, this.pageShift);
/* 1981 */       this.mg.add((byte)124);
/* 1982 */       this.mg.add((byte)50);
/* 1983 */       if (paramBoolean) {
/* 1984 */         pushTmp();
/*      */       } else
/* 1986 */         this.mg.add((byte)95);
/* 1987 */       this.mg.add((byte)5);
/* 1988 */       this.mg.add((byte)124);
/* 1989 */       this.mg.add((byte)18, (this.pageSize >> 2) - 1);
/* 1990 */       this.mg.add((byte)126);
/*      */     }
/*      */   }
/*      */   
/*      */   private void memWrite()
/*      */   {
/* 1996 */     if (this.memWriteStage != 2) throw new Error("didn't do preMemWrite1 or preMemWrite2");
/* 1997 */     this.memWriteStage = 0;
/*      */     
/* 1999 */     if (this.onePage) {
/* 2000 */       this.mg.add((byte)79);
/* 2001 */     } else if (this.fastMem) {
/* 2002 */       this.mg.add((byte)79);
/*      */     }
/*      */     else {
/* 2005 */       this.mg.add((byte)-74, this.me.method("unsafeMemWrite", Type.VOID, new Type[] { Type.INT, Type.INT }));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void memRead(int paramInt1, int paramInt2)
/*      */   {
/* 2012 */     preMemRead();
/* 2013 */     addiu(paramInt1, paramInt2);
/* 2014 */     memRead();
/*      */   }
/*      */   
/*      */   private boolean didPreMemRead;
/*      */   private boolean preMemReadDoPreWrite;
/*      */   private void preMemRead() {
/* 2020 */     preMemRead(false); }
/*      */   
/* 2022 */   private void preMemRead(boolean paramBoolean) { if (this.didPreMemRead) throw new Error("pending preMemRead");
/* 2023 */     this.didPreMemRead = true;
/* 2024 */     this.preMemReadDoPreWrite = paramBoolean;
/* 2025 */     if (this.onePage) {
/* 2026 */       this.mg.add((byte)44);
/* 2027 */     } else if (this.fastMem) {
/* 2028 */       this.mg.add((byte)25, paramBoolean ? 3 : 2);
/*      */     } else {
/* 2030 */       this.mg.add((byte)42);
/*      */     }
/*      */   }
/*      */   
/* 2034 */   private void memRead() { memRead(false); }
/*      */   
/*      */   private void memRead(boolean paramBoolean) {
/* 2037 */     if (!this.didPreMemRead) throw new Error("didn't do preMemRead");
/* 2038 */     this.didPreMemRead = false;
/* 2039 */     if (this.preMemReadDoPreWrite) {
/* 2040 */       this.memWriteStage = 2;
/*      */     }
/* 2042 */     if (this.nullPointerCheck) {
/* 2043 */       this.mg.add((byte)89);
/* 2044 */       this.mg.add((byte)42);
/* 2045 */       this.mg.add((byte)95);
/* 2046 */       this.mg.add((byte)-74, this.me.method("nullPointerCheck", Type.VOID, new Type[] { Type.INT }));
/*      */     }
/*      */     
/* 2049 */     if (this.onePage) {
/* 2050 */       this.mg.add((byte)5);
/* 2051 */       this.mg.add((byte)124);
/* 2052 */       if (this.preMemReadDoPreWrite)
/* 2053 */         this.mg.add((byte)92);
/* 2054 */       this.mg.add((byte)46);
/* 2055 */     } else if (this.fastMem) {
/* 2056 */       if (!paramBoolean)
/* 2057 */         this.mg.add((byte)90);
/* 2058 */       this.mg.add((byte)18, this.pageShift);
/* 2059 */       this.mg.add((byte)124);
/* 2060 */       this.mg.add((byte)50);
/* 2061 */       if (paramBoolean) {
/* 2062 */         pushTmp();
/*      */       } else
/* 2064 */         this.mg.add((byte)95);
/* 2065 */       this.mg.add((byte)5);
/* 2066 */       this.mg.add((byte)124);
/* 2067 */       this.mg.add((byte)18, (this.pageSize >> 2) - 1);
/* 2068 */       this.mg.add((byte)126);
/* 2069 */       if (this.preMemReadDoPreWrite)
/* 2070 */         this.mg.add((byte)92);
/* 2071 */       this.mg.add((byte)46);
/*      */     }
/*      */     else {
/* 2074 */       if (this.preMemReadDoPreWrite) {
/* 2075 */         this.mg.add((byte)92);
/*      */       }
/* 2077 */       this.mg.add((byte)-74, this.me.method("unsafeMemRead", Type.INT, new Type[] { Type.INT }));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\org\ibex\nestedvm\ClassFileCompiler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */