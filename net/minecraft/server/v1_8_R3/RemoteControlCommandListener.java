/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class RemoteControlCommandListener implements ICommandListener
/*    */ {
/*  5 */   private static final RemoteControlCommandListener instance = new RemoteControlCommandListener();
/*  6 */   private StringBuffer b = new StringBuffer();
/*    */   
/*    */ 
/*    */   public static RemoteControlCommandListener getInstance()
/*    */   {
/* 11 */     return instance;
/*    */   }
/*    */   
/*    */   public void i() {
/* 15 */     this.b.setLength(0);
/*    */   }
/*    */   
/*    */   public String j() {
/* 19 */     return this.b.toString();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 23 */     return "Rcon";
/*    */   }
/*    */   
/*    */   public IChatBaseComponent getScoreboardDisplayName() {
/* 27 */     return new ChatComponentText(getName());
/*    */   }
/*    */   
/*    */   public void sendMessage(String message)
/*    */   {
/* 32 */     this.b.append(message);
/*    */   }
/*    */   
/*    */   public void sendMessage(IChatBaseComponent ichatbasecomponent)
/*    */   {
/* 37 */     this.b.append(ichatbasecomponent.c());
/*    */   }
/*    */   
/*    */   public boolean a(int i, String s) {
/* 41 */     return true;
/*    */   }
/*    */   
/*    */   public BlockPosition getChunkCoordinates() {
/* 45 */     return new BlockPosition(0, 0, 0);
/*    */   }
/*    */   
/*    */   public Vec3D d() {
/* 49 */     return new Vec3D(0.0D, 0.0D, 0.0D);
/*    */   }
/*    */   
/*    */   public World getWorld() {
/* 53 */     return MinecraftServer.getServer().getWorld();
/*    */   }
/*    */   
/*    */   public Entity f() {
/* 57 */     return null;
/*    */   }
/*    */   
/*    */   public boolean getSendCommandFeedback() {
/* 61 */     return true;
/*    */   }
/*    */   
/*    */   public void a(CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor_enumcommandresult, int i) {}
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RemoteControlCommandListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */