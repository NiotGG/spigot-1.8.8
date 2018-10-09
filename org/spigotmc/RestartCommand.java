/*     */ package org.spigotmc;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*     */ import net.minecraft.server.v1_8_R3.MinecraftServer;
/*     */ import net.minecraft.server.v1_8_R3.ServerConnection;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ 
/*     */ public class RestartCommand extends Command
/*     */ {
/*     */   public RestartCommand(String name)
/*     */   {
/*  15 */     super(name);
/*  16 */     this.description = "Restarts the server";
/*  17 */     this.usageMessage = "/restart";
/*  18 */     setPermission("bukkit.command.restart");
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args)
/*     */   {
/*  24 */     if (testPermission(sender))
/*     */     {
/*  26 */       MinecraftServer.getServer().processQueue.add(new Runnable()
/*     */       {
/*     */         public void run() {}
/*     */       });
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  35 */     return true;
/*     */   }
/*     */   
/*     */   public static void restart()
/*     */   {
/*  40 */     restart(new File(SpigotConfig.restartScript));
/*     */   }
/*     */   
/*     */   public static void restart(File script)
/*     */   {
/*  45 */     AsyncCatcher.enabled = false;
/*     */     try
/*     */     {
/*  48 */       if (script.isFile())
/*     */       {
/*  50 */         System.out.println("Attempting to restart with " + SpigotConfig.restartScript);
/*     */         
/*     */ 
/*  53 */         WatchdogThread.doStop();
/*     */         
/*     */ 
/*  56 */         for (EntityPlayer p : MinecraftServer.getServer().getPlayerList().players)
/*     */         {
/*  58 */           p.playerConnection.disconnect(SpigotConfig.restartMessage);
/*     */         }
/*     */         
/*     */         try
/*     */         {
/*  63 */           Thread.sleep(100L);
/*     */         }
/*     */         catch (InterruptedException localInterruptedException1) {}
/*     */         
/*     */ 
/*  68 */         MinecraftServer.getServer().getServerConnection().b();
/*     */         
/*     */ 
/*     */         try
/*     */         {
/*  73 */           Thread.sleep(100L);
/*     */         }
/*     */         catch (InterruptedException localInterruptedException2) {}
/*     */         
/*     */ 
/*     */ 
/*     */         try
/*     */         {
/*  81 */           MinecraftServer.getServer().stop();
/*     */         }
/*     */         catch (Throwable localThrowable) {}
/*     */         
/*     */ 
/*     */ 
/*  87 */         Thread shutdownHook = new Thread()
/*     */         {
/*     */ 
/*     */           public void run()
/*     */           {
/*     */             try
/*     */             {
/*  94 */               String os = System.getProperty("os.name").toLowerCase();
/*  95 */               if (os.contains("win"))
/*     */               {
/*  97 */                 Runtime.getRuntime().exec("cmd /c start " + RestartCommand.this.getPath());
/*     */               }
/*     */               else {
/* 100 */                 Runtime.getRuntime().exec(
/* 101 */                   new String[] {
/* 102 */                   "sh", RestartCommand.this.getPath() });
/*     */               }
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 107 */               e.printStackTrace();
/*     */             }
/*     */             
/*     */           }
/* 111 */         };
/* 112 */         shutdownHook.setDaemon(true);
/* 113 */         Runtime.getRuntime().addShutdownHook(shutdownHook);
/*     */       }
/*     */       else {
/* 116 */         System.out.println("Startup script '" + SpigotConfig.restartScript + "' does not exist! Stopping server.");
/*     */       }
/* 118 */       System.exit(0);
/*     */     }
/*     */     catch (Exception ex) {
/* 121 */       ex.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\RestartCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */