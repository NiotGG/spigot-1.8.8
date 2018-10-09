/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandDebug
/*     */   extends CommandAbstract
/*     */ {
/*  22 */   private static final Logger a = ;
/*     */   private long b;
/*     */   private int c;
/*     */   
/*     */   public String getCommand()
/*     */   {
/*  28 */     return "debug";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  33 */     return 3;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  38 */     return "commands.debug.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  43 */     if (paramArrayOfString.length < 1) {
/*  44 */       throw new ExceptionUsage("commands.debug.usage", new Object[0]);
/*     */     }
/*     */     
/*  47 */     if (paramArrayOfString[0].equals("start")) {
/*  48 */       if (paramArrayOfString.length != 1) {
/*  49 */         throw new ExceptionUsage("commands.debug.usage", new Object[0]);
/*     */       }
/*     */       
/*  52 */       a(paramICommandListener, this, "commands.debug.start", new Object[0]);
/*     */       
/*  54 */       MinecraftServer.getServer().au();
/*  55 */       this.b = MinecraftServer.az();
/*  56 */       this.c = MinecraftServer.getServer().at();
/*  57 */     } else if (paramArrayOfString[0].equals("stop")) {
/*  58 */       if (paramArrayOfString.length != 1) {
/*  59 */         throw new ExceptionUsage("commands.debug.usage", new Object[0]);
/*     */       }
/*     */       
/*  62 */       if (!MinecraftServer.getServer().methodProfiler.a) {
/*  63 */         throw new CommandException("commands.debug.notStarted", new Object[0]);
/*     */       }
/*     */       
/*  66 */       long l1 = MinecraftServer.az();
/*  67 */       int i = MinecraftServer.getServer().at();
/*     */       
/*  69 */       long l2 = l1 - this.b;
/*  70 */       int j = i - this.c;
/*  71 */       a(l2, j);
/*     */       
/*  73 */       MinecraftServer.getServer().methodProfiler.a = false;
/*  74 */       a(paramICommandListener, this, "commands.debug.stop", new Object[] { Float.valueOf((float)l2 / 1000.0F), Integer.valueOf(j) });
/*     */     } else {
/*  76 */       throw new ExceptionUsage("commands.debug.usage", new Object[0]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(long paramLong, int paramInt) {
/*  81 */     File localFile = new File(MinecraftServer.getServer().d("debug"), "profile-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
/*     */     
/*  83 */     localFile.getParentFile().mkdirs();
/*     */     try
/*     */     {
/*  86 */       FileWriter localFileWriter = new FileWriter(localFile);
/*  87 */       localFileWriter.write(b(paramLong, paramInt));
/*  88 */       localFileWriter.close();
/*     */     } catch (Throwable localThrowable) {
/*  90 */       a.error("Could not save profiler results to " + localFile, localThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   private String b(long paramLong, int paramInt) {
/*  95 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     
/*  97 */     localStringBuilder.append("---- Minecraft Profiler Results ----\n");
/*  98 */     localStringBuilder.append("// ");
/*  99 */     localStringBuilder.append(d());
/* 100 */     localStringBuilder.append("\n\n");
/*     */     
/* 102 */     localStringBuilder.append("Time span: ").append(paramLong).append(" ms\n");
/* 103 */     localStringBuilder.append("Tick span: ").append(paramInt).append(" ticks\n");
/* 104 */     localStringBuilder.append("// This is approximately ").append(String.format("%.2f", new Object[] { Float.valueOf(paramInt / ((float)paramLong / 1000.0F)) })).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
/*     */     
/* 106 */     localStringBuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
/*     */     
/* 108 */     a(0, "root", localStringBuilder);
/*     */     
/* 110 */     localStringBuilder.append("--- END PROFILE DUMP ---\n\n");
/*     */     
/* 112 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private void a(int paramInt, String paramString, StringBuilder paramStringBuilder) {
/* 116 */     List localList = MinecraftServer.getServer().methodProfiler.b(paramString);
/* 117 */     if ((localList == null) || (localList.size() < 3)) {
/* 118 */       return;
/*     */     }
/*     */     
/* 121 */     for (int i = 1; i < localList.size(); i++) {
/* 122 */       MethodProfiler.ProfilerInfo localProfilerInfo = (MethodProfiler.ProfilerInfo)localList.get(i);
/*     */       
/* 124 */       paramStringBuilder.append(String.format("[%02d] ", new Object[] { Integer.valueOf(paramInt) }));
/* 125 */       for (int j = 0; j < paramInt; j++) {
/* 126 */         paramStringBuilder.append(" ");
/*     */       }
/* 128 */       paramStringBuilder.append(localProfilerInfo.c).append(" - ").append(String.format("%.2f", new Object[] { Double.valueOf(localProfilerInfo.a) })).append("%/").append(String.format("%.2f", new Object[] { Double.valueOf(localProfilerInfo.b) })).append("%\n");
/*     */       
/*     */ 
/*     */ 
/* 132 */       if (!localProfilerInfo.c.equals("unspecified")) {
/*     */         try {
/* 134 */           a(paramInt + 1, paramString + "." + localProfilerInfo.c, paramStringBuilder);
/*     */         } catch (Exception localException) {
/* 136 */           paramStringBuilder.append("[[ EXCEPTION ").append(localException).append(" ]]");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static String d()
/*     */   {
/* 144 */     String[] arrayOfString = { "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server." };
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
/*     */     try
/*     */     {
/* 162 */       return arrayOfString[((int)(System.nanoTime() % arrayOfString.length))];
/*     */     } catch (Throwable localThrowable) {}
/* 164 */     return "Witty comment unavailable :(";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 171 */     if (paramArrayOfString.length == 1) {
/* 172 */       return a(paramArrayOfString, new String[] { "start", "stop" });
/*     */     }
/*     */     
/* 175 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandDebug.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */