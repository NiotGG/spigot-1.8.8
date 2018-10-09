/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.base.Predicates;
/*    */ import java.util.Random;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*    */ import org.bukkit.event.entity.EntityChangeBlockEvent;
/*    */ 
/*    */ public class PathfinderGoalEatTile extends PathfinderGoal
/*    */ {
/* 13 */   private static final Predicate<IBlockData> b = BlockStatePredicate.a(Blocks.TALLGRASS).a(BlockLongGrass.TYPE, Predicates.equalTo(BlockLongGrass.EnumTallGrassType.GRASS));
/*    */   private EntityInsentient c;
/*    */   private World d;
/*    */   int a;
/*    */   
/*    */   public PathfinderGoalEatTile(EntityInsentient entityinsentient) {
/* 19 */     this.c = entityinsentient;
/* 20 */     this.d = entityinsentient.world;
/* 21 */     a(7);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 25 */     if (this.c.bc().nextInt(this.c.isBaby() ? 50 : 1000) != 0) {
/* 26 */       return false;
/*    */     }
/* 28 */     BlockPosition blockposition = new BlockPosition(this.c.locX, this.c.locY, this.c.locZ);
/*    */     
/* 30 */     return b.apply(this.d.getType(blockposition));
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 35 */     this.a = 40;
/* 36 */     this.d.broadcastEntityEffect(this.c, (byte)10);
/* 37 */     this.c.getNavigation().n();
/*    */   }
/*    */   
/*    */   public void d() {
/* 41 */     this.a = 0;
/*    */   }
/*    */   
/*    */   public boolean b() {
/* 45 */     return this.a > 0;
/*    */   }
/*    */   
/*    */   public int f() {
/* 49 */     return this.a;
/*    */   }
/*    */   
/*    */   public void e() {
/* 53 */     this.a = Math.max(0, this.a - 1);
/* 54 */     if (this.a == 4) {
/* 55 */       BlockPosition blockposition = new BlockPosition(this.c.locX, this.c.locY, this.c.locZ);
/*    */       
/* 57 */       if (b.apply(this.d.getType(blockposition)))
/*    */       {
/* 59 */         if (!CraftEventFactory.callEntityChangeBlockEvent(this.c, this.c.world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), Material.AIR, !this.d.getGameRules().getBoolean("mobGriefing")).isCancelled()) {
/* 60 */           this.d.setAir(blockposition, false);
/*    */         }
/*    */         
/* 63 */         this.c.v();
/*    */       } else {
/* 65 */         BlockPosition blockposition1 = blockposition.down();
/*    */         
/* 67 */         if (this.d.getType(blockposition1).getBlock() == Blocks.GRASS)
/*    */         {
/* 69 */           if (!CraftEventFactory.callEntityChangeBlockEvent(this.c, this.c.world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), Material.AIR, !this.d.getGameRules().getBoolean("mobGriefing")).isCancelled()) {
/* 70 */             this.d.triggerEffect(2001, blockposition1, Block.getId(Blocks.GRASS));
/* 71 */             this.d.setTypeAndData(blockposition1, Blocks.DIRT.getBlockData(), 2);
/*    */           }
/*    */           
/* 74 */           this.c.v();
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalEatTile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */