/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftCrashReport;
/*     */ 
/*     */ 
/*     */ public class CrashReport
/*     */ {
/*  22 */   private static final Logger a = ;
/*     */   private final String b;
/*     */   private final Throwable c;
/*  25 */   private final CrashReportSystemDetails d = new CrashReportSystemDetails(this, "System Details");
/*  26 */   private final List<CrashReportSystemDetails> e = Lists.newArrayList();
/*     */   private File f;
/*  28 */   private boolean g = true;
/*  29 */   private StackTraceElement[] h = new StackTraceElement[0];
/*     */   
/*     */   public CrashReport(String s, Throwable throwable) {
/*  32 */     this.b = s;
/*  33 */     this.c = throwable;
/*  34 */     h();
/*     */   }
/*     */   
/*     */   private void h() {
/*  38 */     this.d.a("Minecraft Version", new Callable() {
/*     */       public String a() {
/*  40 */         return "1.8.8";
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/*  44 */         return a();
/*     */       }
/*  46 */     });
/*  47 */     this.d.a("Operating System", new Callable() {
/*     */       public String a() {
/*  49 */         return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/*  53 */         return a();
/*     */       }
/*  55 */     });
/*  56 */     this.d.a("Java Version", new Callable() {
/*     */       public String a() {
/*  58 */         return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/*  62 */         return a();
/*     */       }
/*  64 */     });
/*  65 */     this.d.a("Java VM Version", new Callable() {
/*     */       public String a() {
/*  67 */         return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/*  71 */         return a();
/*     */       }
/*  73 */     });
/*  74 */     this.d.a("Memory", new Callable() {
/*     */       public String a() {
/*  76 */         Runtime runtime = Runtime.getRuntime();
/*  77 */         long i = runtime.maxMemory();
/*  78 */         long j = runtime.totalMemory();
/*  79 */         long k = runtime.freeMemory();
/*  80 */         long l = i / 1024L / 1024L;
/*  81 */         long i1 = j / 1024L / 1024L;
/*  82 */         long j1 = k / 1024L / 1024L;
/*     */         
/*  84 */         return k + " bytes (" + j1 + " MB) / " + j + " bytes (" + i1 + " MB) up to " + i + " bytes (" + l + " MB)";
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/*  88 */         return a();
/*     */       }
/*  90 */     });
/*  91 */     this.d.a("JVM Flags", new Callable() {
/*     */       public String a() {
/*  93 */         RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
/*  94 */         List list = runtimemxbean.getInputArguments();
/*  95 */         int i = 0;
/*  96 */         StringBuilder stringbuilder = new StringBuilder();
/*  97 */         Iterator iterator = list.iterator();
/*     */         
/*  99 */         while (iterator.hasNext()) {
/* 100 */           String s = (String)iterator.next();
/*     */           
/* 102 */           if (s.startsWith("-X")) {
/* 103 */             if (i++ > 0) {
/* 104 */               stringbuilder.append(" ");
/*     */             }
/*     */             
/* 107 */             stringbuilder.append(s);
/*     */           }
/*     */         }
/*     */         
/* 111 */         return String.format("%d total; %s", new Object[] { Integer.valueOf(i), stringbuilder.toString() });
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/* 115 */         return a();
/*     */       }
/* 117 */     });
/* 118 */     this.d.a("IntCache", new Callable() {
/*     */       public String a() throws Exception {
/* 120 */         return IntCache.b();
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/* 124 */         return a();
/*     */       }
/* 126 */     });
/* 127 */     this.d.a("CraftBukkit Information", new CraftCrashReport());
/*     */   }
/*     */   
/*     */   public String a() {
/* 131 */     return this.b;
/*     */   }
/*     */   
/*     */   public Throwable b() {
/* 135 */     return this.c;
/*     */   }
/*     */   
/*     */   public void a(StringBuilder stringbuilder) {
/* 139 */     if (((this.h == null) || (this.h.length <= 0)) && (this.e.size() > 0)) {
/* 140 */       this.h = ((StackTraceElement[])ArrayUtils.subarray(((CrashReportSystemDetails)this.e.get(0)).a(), 0, 1));
/*     */     }
/*     */     
/* 143 */     if ((this.h != null) && (this.h.length > 0)) {
/* 144 */       stringbuilder.append("-- Head --\n");
/* 145 */       stringbuilder.append("Stacktrace:\n");
/* 146 */       StackTraceElement[] astacktraceelement = this.h;
/* 147 */       int i = astacktraceelement.length;
/*     */       
/* 149 */       for (int j = 0; j < i; j++) {
/* 150 */         StackTraceElement stacktraceelement = astacktraceelement[j];
/*     */         
/* 152 */         stringbuilder.append("\t").append("at ").append(stacktraceelement.toString());
/* 153 */         stringbuilder.append("\n");
/*     */       }
/*     */       
/* 156 */       stringbuilder.append("\n");
/*     */     }
/*     */     
/* 159 */     Iterator iterator = this.e.iterator();
/*     */     
/* 161 */     while (iterator.hasNext()) {
/* 162 */       CrashReportSystemDetails crashreportsystemdetails = (CrashReportSystemDetails)iterator.next();
/*     */       
/* 164 */       crashreportsystemdetails.a(stringbuilder);
/* 165 */       stringbuilder.append("\n\n");
/*     */     }
/*     */     
/* 168 */     this.d.a(stringbuilder);
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public String d()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aconst_null
/*     */     //   1: astore_1
/*     */     //   2: aconst_null
/*     */     //   3: astore_2
/*     */     //   4: aload_0
/*     */     //   5: getfield 77	net/minecraft/server/v1_8_R3/CrashReport:c	Ljava/lang/Throwable;
/*     */     //   8: astore_3
/*     */     //   9: aload_3
/*     */     //   10: checkcast 186	java/lang/Throwable
/*     */     //   13: invokevirtual 189	java/lang/Throwable:getMessage	()Ljava/lang/String;
/*     */     //   16: ifnonnull +80 -> 96
/*     */     //   19: aload_3
/*     */     //   20: instanceof 191
/*     */     //   23: ifeq +18 -> 41
/*     */     //   26: new 191	java/lang/NullPointerException
/*     */     //   29: dup
/*     */     //   30: aload_0
/*     */     //   31: getfield 75	net/minecraft/server/v1_8_R3/CrashReport:b	Ljava/lang/String;
/*     */     //   34: invokespecial 194	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
/*     */     //   37: astore_3
/*     */     //   38: goto +44 -> 82
/*     */     //   41: aload_3
/*     */     //   42: instanceof 200
/*     */     //   45: ifeq +18 -> 63
/*     */     //   48: new 200	java/lang/StackOverflowError
/*     */     //   51: dup
/*     */     //   52: aload_0
/*     */     //   53: getfield 75	net/minecraft/server/v1_8_R3/CrashReport:b	Ljava/lang/String;
/*     */     //   56: invokespecial 201	java/lang/StackOverflowError:<init>	(Ljava/lang/String;)V
/*     */     //   59: astore_3
/*     */     //   60: goto +22 -> 82
/*     */     //   63: aload_3
/*     */     //   64: instanceof 203
/*     */     //   67: ifeq +15 -> 82
/*     */     //   70: new 203	java/lang/OutOfMemoryError
/*     */     //   73: dup
/*     */     //   74: aload_0
/*     */     //   75: getfield 75	net/minecraft/server/v1_8_R3/CrashReport:b	Ljava/lang/String;
/*     */     //   78: invokespecial 204	java/lang/OutOfMemoryError:<init>	(Ljava/lang/String;)V
/*     */     //   81: astore_3
/*     */     //   82: aload_3
/*     */     //   83: checkcast 186	java/lang/Throwable
/*     */     //   86: aload_0
/*     */     //   87: getfield 77	net/minecraft/server/v1_8_R3/CrashReport:c	Ljava/lang/Throwable;
/*     */     //   90: invokevirtual 207	java/lang/Throwable:getStackTrace	()[Ljava/lang/StackTraceElement;
/*     */     //   93: invokevirtual 211	java/lang/Throwable:setStackTrace	([Ljava/lang/StackTraceElement;)V
/*     */     //   96: aload_3
/*     */     //   97: checkcast 186	java/lang/Throwable
/*     */     //   100: invokevirtual 212	java/lang/Throwable:toString	()Ljava/lang/String;
/*     */     //   103: astore 4
/*     */     //   105: new 196	java/io/StringWriter
/*     */     //   108: dup
/*     */     //   109: invokespecial 213	java/io/StringWriter:<init>	()V
/*     */     //   112: astore_1
/*     */     //   113: new 198	java/io/PrintWriter
/*     */     //   116: dup
/*     */     //   117: aload_1
/*     */     //   118: invokespecial 216	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
/*     */     //   121: astore_2
/*     */     //   122: aload_3
/*     */     //   123: checkcast 186	java/lang/Throwable
/*     */     //   126: aload_2
/*     */     //   127: invokevirtual 220	java/lang/Throwable:printStackTrace	(Ljava/io/PrintWriter;)V
/*     */     //   130: aload_1
/*     */     //   131: invokevirtual 221	java/io/StringWriter:toString	()Ljava/lang/String;
/*     */     //   134: astore 4
/*     */     //   136: goto +16 -> 152
/*     */     //   139: astore 5
/*     */     //   141: aload_1
/*     */     //   142: invokestatic 228	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/Writer;)V
/*     */     //   145: aload_2
/*     */     //   146: invokestatic 228	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/Writer;)V
/*     */     //   149: aload 5
/*     */     //   151: athrow
/*     */     //   152: aload_1
/*     */     //   153: invokestatic 228	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/Writer;)V
/*     */     //   156: aload_2
/*     */     //   157: invokestatic 228	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/Writer;)V
/*     */     //   160: aload 4
/*     */     //   162: areturn
/*     */     // Line number table:
/*     */     //   Java source line #172	-> byte code offset #0
/*     */     //   Java source line #173	-> byte code offset #2
/*     */     //   Java source line #174	-> byte code offset #4
/*     */     //   Java source line #176	-> byte code offset #9
/*     */     //   Java source line #177	-> byte code offset #19
/*     */     //   Java source line #178	-> byte code offset #26
/*     */     //   Java source line #179	-> byte code offset #38
/*     */     //   Java source line #180	-> byte code offset #48
/*     */     //   Java source line #181	-> byte code offset #60
/*     */     //   Java source line #182	-> byte code offset #70
/*     */     //   Java source line #185	-> byte code offset #82
/*     */     //   Java source line #188	-> byte code offset #96
/*     */     //   Java source line #191	-> byte code offset #105
/*     */     //   Java source line #192	-> byte code offset #113
/*     */     //   Java source line #193	-> byte code offset #122
/*     */     //   Java source line #194	-> byte code offset #130
/*     */     //   Java source line #195	-> byte code offset #136
/*     */     //   Java source line #196	-> byte code offset #141
/*     */     //   Java source line #197	-> byte code offset #145
/*     */     //   Java source line #198	-> byte code offset #149
/*     */     //   Java source line #196	-> byte code offset #152
/*     */     //   Java source line #197	-> byte code offset #156
/*     */     //   Java source line #200	-> byte code offset #160
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	163	0	this	CrashReport
/*     */     //   1	152	1	stringwriter	java.io.StringWriter
/*     */     //   3	154	2	printwriter	java.io.PrintWriter
/*     */     //   8	115	3	object	Object
/*     */     //   103	58	4	s	String
/*     */     //   139	11	5	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   105	139	139	finally
/*     */   }
/*     */   
/*     */   public String e()
/*     */   {
/* 204 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 206 */     stringbuilder.append("---- Minecraft Crash Report ----\n");
/* 207 */     stringbuilder.append("// ");
/* 208 */     stringbuilder.append(i());
/* 209 */     stringbuilder.append("\n\n");
/* 210 */     stringbuilder.append("Time: ");
/* 211 */     stringbuilder.append(new SimpleDateFormat().format(new Date()));
/* 212 */     stringbuilder.append("\n");
/* 213 */     stringbuilder.append("Description: ");
/* 214 */     stringbuilder.append(this.b);
/* 215 */     stringbuilder.append("\n\n");
/* 216 */     stringbuilder.append(d());
/* 217 */     stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
/*     */     
/* 219 */     for (int i = 0; i < 87; i++) {
/* 220 */       stringbuilder.append("-");
/*     */     }
/*     */     
/* 223 */     stringbuilder.append("\n\n");
/* 224 */     a(stringbuilder);
/* 225 */     return stringbuilder.toString();
/*     */   }
/*     */   
/*     */   public boolean a(File file) {
/* 229 */     if (this.f != null) {
/* 230 */       return false;
/*     */     }
/* 232 */     if (file.getParentFile() != null) {
/* 233 */       file.getParentFile().mkdirs();
/*     */     }
/*     */     try
/*     */     {
/* 237 */       FileWriter filewriter = new FileWriter(file);
/*     */       
/* 239 */       filewriter.write(e());
/* 240 */       filewriter.close();
/* 241 */       this.f = file;
/* 242 */       return true;
/*     */     } catch (Throwable throwable) {
/* 244 */       a.error("Could not save crash report to " + file, throwable); }
/* 245 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public CrashReportSystemDetails g()
/*     */   {
/* 251 */     return this.d;
/*     */   }
/*     */   
/*     */   public CrashReportSystemDetails a(String s) {
/* 255 */     return a(s, 1);
/*     */   }
/*     */   
/*     */   public CrashReportSystemDetails a(String s, int i) {
/* 259 */     CrashReportSystemDetails crashreportsystemdetails = new CrashReportSystemDetails(this, s);
/*     */     
/* 261 */     if (this.g) {
/* 262 */       int j = crashreportsystemdetails.a(i);
/* 263 */       StackTraceElement[] astacktraceelement = this.c.getStackTrace();
/* 264 */       StackTraceElement stacktraceelement = null;
/* 265 */       StackTraceElement stacktraceelement1 = null;
/* 266 */       int k = astacktraceelement.length - j;
/*     */       
/* 268 */       if (k < 0) {
/* 269 */         System.out.println("Negative index in crash report handler (" + astacktraceelement.length + "/" + j + ")");
/*     */       }
/*     */       
/* 272 */       if ((astacktraceelement != null) && (k >= 0) && (k < astacktraceelement.length)) {
/* 273 */         stacktraceelement = astacktraceelement[k];
/* 274 */         if (astacktraceelement.length + 1 - j < astacktraceelement.length) {
/* 275 */           stacktraceelement1 = astacktraceelement[(astacktraceelement.length + 1 - j)];
/*     */         }
/*     */       }
/*     */       
/* 279 */       this.g = crashreportsystemdetails.a(stacktraceelement, stacktraceelement1);
/* 280 */       if ((j > 0) && (!this.e.isEmpty())) {
/* 281 */         CrashReportSystemDetails crashreportsystemdetails1 = (CrashReportSystemDetails)this.e.get(this.e.size() - 1);
/*     */         
/* 283 */         crashreportsystemdetails1.b(j);
/* 284 */       } else if ((astacktraceelement != null) && (astacktraceelement.length >= j) && (k >= 0) && (k < astacktraceelement.length)) {
/* 285 */         this.h = new StackTraceElement[k];
/* 286 */         System.arraycopy(astacktraceelement, 0, this.h, 0, this.h.length);
/*     */       } else {
/* 288 */         this.g = false;
/*     */       }
/*     */     }
/*     */     
/* 292 */     this.e.add(crashreportsystemdetails);
/* 293 */     return crashreportsystemdetails;
/*     */   }
/*     */   
/*     */   private static String i() {
/* 297 */     String[] astring = { "Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine." };
/*     */     try
/*     */     {
/* 300 */       return astring[((int)(System.nanoTime() % astring.length))];
/*     */     } catch (Throwable localThrowable) {}
/* 302 */     return "Witty comment unavailable :(";
/*     */   }
/*     */   
/*     */   public static CrashReport a(Throwable throwable, String s)
/*     */   {
/*     */     CrashReport crashreport;
/*     */     CrashReport crashreport;
/* 309 */     if ((throwable instanceof ReportedException)) {
/* 310 */       crashreport = ((ReportedException)throwable).a();
/*     */     } else {
/* 312 */       crashreport = new CrashReport(s, throwable);
/*     */     }
/*     */     
/* 315 */     return crashreport;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CrashReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */