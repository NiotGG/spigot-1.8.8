/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.lang.management.ManagementFactory;
/*    */ import java.lang.management.ThreadInfo;
/*    */ import java.lang.management.ThreadMXBean;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Timer;
/*    */ import java.util.TimerTask;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThreadWatchdog
/*    */   implements Runnable
/*    */ {
/* 19 */   private static final Logger a = ;
/*    */   
/*    */   private final DedicatedServer b;
/*    */   
/*    */   private final long c;
/*    */   
/*    */   public ThreadWatchdog(DedicatedServer paramDedicatedServer)
/*    */   {
/* 27 */     this.b = paramDedicatedServer;
/* 28 */     this.c = paramDedicatedServer.aS();
/*    */   }
/*    */   
/*    */   public void run()
/*    */   {
/* 33 */     while (this.b.isRunning()) {
/* 34 */       long l1 = this.b.aL();
/* 35 */       long l2 = MinecraftServer.az();
/* 36 */       long l3 = l2 - l1;
/*    */       
/* 38 */       if (l3 > this.c) {
/* 39 */         a.fatal("A single server tick took " + String.format("%.2f", new Object[] { Float.valueOf((float)l3 / 1000.0F) }) + " seconds (should be max " + String.format("%.2f", new Object[] { Float.valueOf(0.05F) }) + ")");
/* 40 */         a.fatal("Considering it to be crashed, server will forcibly shutdown.");
/*    */         
/* 42 */         ThreadMXBean localThreadMXBean = ManagementFactory.getThreadMXBean();
/* 43 */         ThreadInfo[] arrayOfThreadInfo = localThreadMXBean.dumpAllThreads(true, true);
/*    */         
/* 45 */         StringBuilder localStringBuilder = new StringBuilder();
/* 46 */         Error localError = new Error();
/*    */         
/* 48 */         for (Object localObject2 : arrayOfThreadInfo) {
/* 49 */           if (((ThreadInfo)localObject2).getThreadId() == this.b.aM().getId()) {
/* 50 */             localError.setStackTrace(((ThreadInfo)localObject2).getStackTrace());
/*    */           }
/*    */           
/* 53 */           localStringBuilder.append(localObject2);
/* 54 */           localStringBuilder.append("\n");
/*    */         }
/*    */         
/* 57 */         ??? = new CrashReport("Watching Server", localError);
/* 58 */         this.b.b((CrashReport)???);
/* 59 */         CrashReportSystemDetails localCrashReportSystemDetails = ((CrashReport)???).a("Thread Dump");
/* 60 */         localCrashReportSystemDetails.a("Threads", localStringBuilder);
/*    */         
/* 62 */         File localFile = new File(new File(this.b.y(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
/* 63 */         if (((CrashReport)???).a(localFile)) {
/* 64 */           a.error("This crash report has been saved to: " + localFile.getAbsolutePath());
/*    */         } else {
/* 66 */           a.error("We were unable to save this crash report to disk.");
/*    */         }
/*    */         
/* 69 */         a();
/*    */       }
/*    */       try
/*    */       {
/* 73 */         Thread.sleep(l1 + this.c - l2);
/*    */       }
/*    */       catch (InterruptedException localInterruptedException) {}
/*    */     }
/*    */   }
/*    */   
/*    */   private void a() {
/*    */     try {
/* 81 */       Timer localTimer = new Timer();
/* 82 */       localTimer.schedule(new TimerTask()
/*    */       {
/*    */ 
/* 85 */         public void run() { Runtime.getRuntime().halt(1); } }, 10000L);
/*    */       
/*    */ 
/*    */ 
/* 89 */       System.exit(1);
/*    */     } catch (Throwable localThrowable) {
/* 91 */       Runtime.getRuntime().halt(1);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ThreadWatchdog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */