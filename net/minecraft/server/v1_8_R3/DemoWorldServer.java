/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DemoWorldServer
/*    */   extends WorldServer
/*    */ {
/* 14 */   private static final long I = "North Carolina".hashCode();
/*    */   
/* 16 */   public static final WorldSettings a = new WorldSettings(I, WorldSettings.EnumGamemode.SURVIVAL, true, false, WorldType.NORMAL).a();
/*    */   
/*    */   public DemoWorldServer(MinecraftServer paramMinecraftServer, IDataManager paramIDataManager, WorldData paramWorldData, int paramInt, MethodProfiler paramMethodProfiler) {
/* 19 */     super(paramMinecraftServer, paramIDataManager, paramWorldData, paramInt, paramMethodProfiler);
/* 20 */     this.worldData.a(a);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\DemoWorldServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */