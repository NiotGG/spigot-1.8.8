/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.World.Environment;
/*    */ 
/*    */ public class SecondaryWorldServer extends WorldServer {
/*    */   private WorldServer a;
/*    */   
/*    */   public SecondaryWorldServer(MinecraftServer minecraftserver, IDataManager idatamanager, int i, WorldServer worldserver, MethodProfiler methodprofiler, WorldData worldData, World.Environment env, org.bukkit.generator.ChunkGenerator gen) {
/*  9 */     super(minecraftserver, idatamanager, worldData, i, methodprofiler, env, gen);
/*    */     
/* 11 */     this.a = worldserver;
/*    */   }
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
/*    */ 
/*    */ 
/*    */ 
/*    */   public World b()
/*    */   {
/* 48 */     this.worldMaps = this.a.T();
/*    */     
/* 50 */     String s = PersistentVillage.a(this.worldProvider);
/* 51 */     PersistentVillage persistentvillage = (PersistentVillage)this.worldMaps.get(PersistentVillage.class, s);
/*    */     
/* 53 */     if (persistentvillage == null) {
/* 54 */       this.villages = new PersistentVillage(this);
/* 55 */       this.worldMaps.a(s, this.villages);
/*    */     } else {
/* 57 */       this.villages = persistentvillage;
/* 58 */       this.villages.a(this);
/*    */     }
/*    */     
/* 61 */     return super.b();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\SecondaryWorldServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */