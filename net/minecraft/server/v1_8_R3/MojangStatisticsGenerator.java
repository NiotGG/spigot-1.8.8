/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ public class MojangStatisticsGenerator
/*     */ {
/*  18 */   private final Map<String, Object> a = Maps.newHashMap();
/*  19 */   private final Map<String, Object> b = Maps.newHashMap();
/*     */   
/*  21 */   private final String c = UUID.randomUUID().toString();
/*     */   private final URL d;
/*     */   private final IMojangStatistics e;
/*  24 */   private final Timer f = new Timer("Snooper Timer", true);
/*  25 */   private final Object g = new Object();
/*     */   private final long h;
/*     */   private boolean i;
/*     */   private int j;
/*     */   
/*     */   public MojangStatisticsGenerator(String paramString, IMojangStatistics paramIMojangStatistics, long paramLong) {
/*     */     try {
/*  32 */       this.d = new URL("http://snoop.minecraft.net/" + paramString + "?version=" + 2);
/*     */     } catch (MalformedURLException localMalformedURLException) {
/*  34 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*  37 */     this.e = paramIMojangStatistics;
/*  38 */     this.h = paramLong;
/*     */   }
/*     */   
/*     */   public void a() {
/*  42 */     if (this.i) {
/*  43 */       return;
/*     */     }
/*  45 */     this.i = true;
/*     */     
/*  47 */     h();
/*     */     
/*  49 */     this.f.schedule(new TimerTask()
/*     */     {
/*     */       public void run() {
/*  52 */         if (!MojangStatisticsGenerator.a(MojangStatisticsGenerator.this).getSnooperEnabled()) {
/*     */           return;
/*     */         }
/*     */         
/*     */         HashMap localHashMap;
/*  57 */         synchronized (MojangStatisticsGenerator.b(MojangStatisticsGenerator.this)) {
/*  58 */           localHashMap = Maps.newHashMap(MojangStatisticsGenerator.c(MojangStatisticsGenerator.this));
/*  59 */           if (MojangStatisticsGenerator.d(MojangStatisticsGenerator.this) == 0) {
/*  60 */             localHashMap.putAll(MojangStatisticsGenerator.e(MojangStatisticsGenerator.this));
/*     */           }
/*  62 */           localHashMap.put("snooper_count", Integer.valueOf(MojangStatisticsGenerator.f(MojangStatisticsGenerator.this)));
/*  63 */           localHashMap.put("snooper_token", MojangStatisticsGenerator.g(MojangStatisticsGenerator.this));
/*     */         }
/*     */         
/*  66 */         HttpUtilities.a(MojangStatisticsGenerator.h(MojangStatisticsGenerator.this), localHashMap, true); } }, 0L, 900000L);
/*     */   }
/*     */   
/*     */ 
/*     */   private void h()
/*     */   {
/*  72 */     i();
/*     */     
/*  74 */     a("snooper_token", this.c);
/*  75 */     b("snooper_token", this.c);
/*  76 */     b("os_name", System.getProperty("os.name"));
/*  77 */     b("os_version", System.getProperty("os.version"));
/*  78 */     b("os_architecture", System.getProperty("os.arch"));
/*  79 */     b("java_version", System.getProperty("java.version"));
/*  80 */     a("version", "1.8.8");
/*     */     
/*  82 */     this.e.b(this);
/*     */   }
/*     */   
/*     */   private void i() {
/*  86 */     RuntimeMXBean localRuntimeMXBean = ManagementFactory.getRuntimeMXBean();
/*  87 */     List localList = localRuntimeMXBean.getInputArguments();
/*  88 */     int k = 0;
/*     */     
/*  90 */     for (String str : localList) {
/*  91 */       if (str.startsWith("-X")) {
/*  92 */         a("jvm_arg[" + k++ + "]", str);
/*     */       }
/*     */     }
/*     */     
/*  96 */     a("jvm_args", Integer.valueOf(k));
/*     */   }
/*     */   
/*     */   public void b() {
/* 100 */     b("memory_total", Long.valueOf(Runtime.getRuntime().totalMemory()));
/* 101 */     b("memory_max", Long.valueOf(Runtime.getRuntime().maxMemory()));
/* 102 */     b("memory_free", Long.valueOf(Runtime.getRuntime().freeMemory()));
/* 103 */     b("cpu_cores", Integer.valueOf(Runtime.getRuntime().availableProcessors()));
/*     */     
/* 105 */     this.e.a(this);
/*     */   }
/*     */   
/*     */   public void a(String paramString, Object paramObject) {
/* 109 */     synchronized (this.g) {
/* 110 */       this.b.put(paramString, paramObject);
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(String paramString, Object paramObject) {
/* 115 */     synchronized (this.g) {
/* 116 */       this.a.put(paramString, paramObject);
/*     */     }
/*     */   }
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
/*     */   public boolean d()
/*     */   {
/* 139 */     return this.i;
/*     */   }
/*     */   
/*     */   public void e() {
/* 143 */     this.f.cancel();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public long g()
/*     */   {
/* 151 */     return this.h;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MojangStatisticsGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */