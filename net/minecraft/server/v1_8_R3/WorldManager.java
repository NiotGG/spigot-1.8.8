/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class WorldManager implements IWorldAccess
/*    */ {
/*    */   private MinecraftServer a;
/*    */   private WorldServer world;
/*    */   
/*    */   public WorldManager(MinecraftServer minecraftserver, WorldServer worldserver) {
/* 11 */     this.a = minecraftserver;
/* 12 */     this.world = worldserver;
/*    */   }
/*    */   
/*    */   public void a(int i, boolean flag, double d0, double d1, double d2, double d3, double d4, double d5, int... aint) {}
/*    */   
/*    */   public void a(Entity entity) {
/* 18 */     this.world.getTracker().track(entity);
/*    */   }
/*    */   
/*    */   public void b(Entity entity) {
/* 22 */     this.world.getTracker().untrackEntity(entity);
/* 23 */     this.world.getScoreboard().a(entity);
/*    */   }
/*    */   
/*    */   public void a(String s, double d0, double d1, double d2, float f, float f1)
/*    */   {
/* 28 */     this.a.getPlayerList().sendPacketNearby(d0, d1, d2, f > 1.0F ? 16.0F * f : 16.0D, this.world.dimension, new PacketPlayOutNamedSoundEffect(s, d0, d1, d2, f, f1));
/*    */   }
/*    */   
/*    */   public void a(EntityHuman entityhuman, String s, double d0, double d1, double d2, float f, float f1)
/*    */   {
/* 33 */     this.a.getPlayerList().sendPacketNearby(entityhuman, d0, d1, d2, f > 1.0F ? 16.0F * f : 16.0D, this.world.dimension, new PacketPlayOutNamedSoundEffect(s, d0, d1, d2, f, f1));
/*    */   }
/*    */   
/*    */   public void a(int i, int j, int k, int l, int i1, int j1) {}
/*    */   
/*    */   public void a(BlockPosition blockposition) {
/* 39 */     this.world.getPlayerChunkMap().flagDirty(blockposition);
/*    */   }
/*    */   
/*    */   public void b(BlockPosition blockposition) {}
/*    */   
/*    */   public void a(String s, BlockPosition blockposition) {}
/*    */   
/*    */   public void a(EntityHuman entityhuman, int i, BlockPosition blockposition, int j)
/*    */   {
/* 48 */     this.a.getPlayerList().sendPacketNearby(entityhuman, blockposition.getX(), blockposition.getY(), blockposition.getZ(), 64.0D, this.world.dimension, new PacketPlayOutWorldEvent(i, blockposition, j, false));
/*    */   }
/*    */   
/*    */   public void a(int i, BlockPosition blockposition, int j) {
/* 52 */     this.a.getPlayerList().sendAll(new PacketPlayOutWorldEvent(i, blockposition, j, true));
/*    */   }
/*    */   
/*    */   public void b(int i, BlockPosition blockposition, int j) {
/* 56 */     Iterator iterator = this.a.getPlayerList().v().iterator();
/*    */     
/*    */ 
/* 59 */     EntityHuman entityhuman = null;
/* 60 */     Entity entity = this.world.a(i);
/* 61 */     if ((entity instanceof EntityHuman)) { entityhuman = (EntityHuman)entity;
/*    */     }
/*    */     
/* 64 */     while (iterator.hasNext()) {
/* 65 */       EntityPlayer entityplayer = (EntityPlayer)iterator.next();
/*    */       
/* 67 */       if ((entityplayer != null) && (entityplayer.world == this.world) && (entityplayer.getId() != i)) {
/* 68 */         double d0 = blockposition.getX() - entityplayer.locX;
/* 69 */         double d1 = blockposition.getY() - entityplayer.locY;
/* 70 */         double d2 = blockposition.getZ() - entityplayer.locZ;
/*    */         
/*    */ 
/* 73 */         if ((entityhuman == null) || (!(entityhuman instanceof EntityPlayer)) || (entityplayer.getBukkitEntity().canSee(((EntityPlayer)entityhuman).getBukkitEntity())))
/*    */         {
/*    */ 
/*    */ 
/*    */ 
/* 78 */           if (d0 * d0 + d1 * d1 + d2 * d2 < 1024.0D) {
/* 79 */             entityplayer.playerConnection.sendPacket(new PacketPlayOutBlockBreakAnimation(i, blockposition, j));
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */