/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandWeather
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 20 */     return "weather";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 25 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 30 */     return "commands.weather.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 35 */     if ((paramArrayOfString.length < 1) || (paramArrayOfString.length > 2)) {
/* 36 */       throw new ExceptionUsage("commands.weather.usage", new Object[0]);
/*    */     }
/*    */     
/* 39 */     int i = (300 + new Random().nextInt(600)) * 20;
/* 40 */     if (paramArrayOfString.length >= 2) {
/* 41 */       i = a(paramArrayOfString[1], 1, 1000000) * 20;
/*    */     }
/*    */     
/* 44 */     WorldServer localWorldServer = MinecraftServer.getServer().worldServer[0];
/* 45 */     WorldData localWorldData = localWorldServer.getWorldData();
/*    */     
/* 47 */     if ("clear".equalsIgnoreCase(paramArrayOfString[0])) {
/* 48 */       localWorldData.i(i);
/* 49 */       localWorldData.setWeatherDuration(0);
/* 50 */       localWorldData.setThunderDuration(0);
/* 51 */       localWorldData.setStorm(false);
/* 52 */       localWorldData.setThundering(false);
/* 53 */       a(paramICommandListener, this, "commands.weather.clear", new Object[0]);
/* 54 */     } else if ("rain".equalsIgnoreCase(paramArrayOfString[0])) {
/* 55 */       localWorldData.i(0);
/* 56 */       localWorldData.setWeatherDuration(i);
/* 57 */       localWorldData.setThunderDuration(i);
/* 58 */       localWorldData.setStorm(true);
/* 59 */       localWorldData.setThundering(false);
/* 60 */       a(paramICommandListener, this, "commands.weather.rain", new Object[0]);
/* 61 */     } else if ("thunder".equalsIgnoreCase(paramArrayOfString[0])) {
/* 62 */       localWorldData.i(0);
/* 63 */       localWorldData.setWeatherDuration(i);
/* 64 */       localWorldData.setThunderDuration(i);
/* 65 */       localWorldData.setStorm(true);
/* 66 */       localWorldData.setThundering(true);
/* 67 */       a(paramICommandListener, this, "commands.weather.thunder", new Object[0]);
/*    */     } else {
/* 69 */       throw new ExceptionUsage("commands.weather.usage", new Object[0]);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 76 */     if (paramArrayOfString.length == 1) {
/* 77 */       return a(paramArrayOfString, new String[] { "clear", "rain", "thunder" });
/*    */     }
/*    */     
/* 80 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandWeather.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */