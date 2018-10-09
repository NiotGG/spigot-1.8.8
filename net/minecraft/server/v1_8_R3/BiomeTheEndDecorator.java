/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ public class BiomeTheEndDecorator extends BiomeDecorator {
/*    */   protected WorldGenerator M;
/*    */   
/*  8 */   public BiomeTheEndDecorator() { this.M = new WorldGenEnder(Blocks.END_STONE); }
/*    */   
/*    */   protected void a(BiomeBase biomebase)
/*    */   {
/* 12 */     a();
/* 13 */     if (this.b.nextInt(5) == 0) {
/* 14 */       int i = this.b.nextInt(16) + 8;
/* 15 */       int j = this.b.nextInt(16) + 8;
/*    */       
/* 17 */       this.M.generate(this.a, this.b, this.a.r(this.c.a(i, 0, j)));
/*    */     }
/*    */     
/* 20 */     if ((this.c.getX() == 0) && (this.c.getZ() == 0)) {
/* 21 */       EntityEnderDragon entityenderdragon = new EntityEnderDragon(this.a);
/*    */       
/* 23 */       entityenderdragon.setPositionRotation(0.0D, 128.0D, 0.0D, this.b.nextFloat() * 360.0F, 0.0F);
/* 24 */       this.a.addEntity(entityenderdragon, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.CHUNK_GEN);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BiomeTheEndDecorator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */