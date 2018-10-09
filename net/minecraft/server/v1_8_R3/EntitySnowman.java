/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.event.block.EntityBlockFormEvent;
/*    */ 
/*    */ public class EntitySnowman extends EntityGolem implements IRangedEntity
/*    */ {
/*    */   public EntitySnowman(World world)
/*    */   {
/* 12 */     super(world);
/* 13 */     setSize(0.7F, 1.9F);
/* 14 */     ((Navigation)getNavigation()).a(true);
/* 15 */     this.goalSelector.a(1, new PathfinderGoalArrowAttack(this, 1.25D, 20, 10.0F));
/* 16 */     this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 1.0D));
/* 17 */     this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
/* 18 */     this.goalSelector.a(4, new PathfinderGoalRandomLookaround(this));
/* 19 */     this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityInsentient.class, 10, true, false, IMonster.d));
/*    */   }
/*    */   
/*    */   protected void initAttributes() {
/* 23 */     super.initAttributes();
/* 24 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(4.0D);
/* 25 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
/*    */   }
/*    */   
/*    */   public void m() {
/* 29 */     super.m();
/* 30 */     if (!this.world.isClientSide) {
/* 31 */       int i = MathHelper.floor(this.locX);
/* 32 */       int j = MathHelper.floor(this.locY);
/* 33 */       int k = MathHelper.floor(this.locZ);
/*    */       
/* 35 */       if (U()) {
/* 36 */         damageEntity(DamageSource.DROWN, 1.0F);
/*    */       }
/*    */       
/* 39 */       if (this.world.getBiome(new BlockPosition(i, 0, k)).a(new BlockPosition(i, j, k)) > 1.0F) {
/* 40 */         damageEntity(org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.MELTING, 1.0F);
/*    */       }
/*    */       
/* 43 */       for (int l = 0; l < 4; l++) {
/* 44 */         i = MathHelper.floor(this.locX + (l % 2 * 2 - 1) * 0.25F);
/* 45 */         j = MathHelper.floor(this.locY);
/* 46 */         k = MathHelper.floor(this.locZ + (l / 2 % 2 * 2 - 1) * 0.25F);
/* 47 */         BlockPosition blockposition = new BlockPosition(i, j, k);
/*    */         
/* 49 */         if ((this.world.getType(blockposition).getBlock().getMaterial() == Material.AIR) && (this.world.getBiome(new BlockPosition(i, 0, k)).a(blockposition) < 0.8F) && (Blocks.SNOW_LAYER.canPlace(this.world, blockposition)))
/*    */         {
/* 51 */           BlockState blockState = this.world.getWorld().getBlockAt(i, j, k).getState();
/* 52 */           blockState.setType(org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers.getMaterial(Blocks.SNOW_LAYER));
/*    */           
/* 54 */           EntityBlockFormEvent event = new EntityBlockFormEvent(getBukkitEntity(), blockState.getBlock(), blockState);
/* 55 */           this.world.getServer().getPluginManager().callEvent(event);
/*    */           
/* 57 */           if (!event.isCancelled()) {
/* 58 */             blockState.update(true);
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   protected Item getLoot()
/*    */   {
/* 68 */     return Items.SNOWBALL;
/*    */   }
/*    */   
/*    */   protected void dropDeathLoot(boolean flag, int i) {
/* 72 */     int j = this.random.nextInt(16);
/*    */     
/* 74 */     for (int k = 0; k < j; k++) {
/* 75 */       a(Items.SNOWBALL, 1);
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(EntityLiving entityliving, float f)
/*    */   {
/* 81 */     EntitySnowball entitysnowball = new EntitySnowball(this.world, this);
/* 82 */     double d0 = entityliving.locY + entityliving.getHeadHeight() - 1.100000023841858D;
/* 83 */     double d1 = entityliving.locX - this.locX;
/* 84 */     double d2 = d0 - entitysnowball.locY;
/* 85 */     double d3 = entityliving.locZ - this.locZ;
/* 86 */     float f1 = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
/*    */     
/* 88 */     entitysnowball.shoot(d1, d2 + f1, d3, 1.6F, 12.0F);
/* 89 */     makeSound("random.bow", 1.0F, 1.0F / (bc().nextFloat() * 0.4F + 0.8F));
/* 90 */     this.world.addEntity(entitysnowball);
/*    */   }
/*    */   
/*    */   public float getHeadHeight() {
/* 94 */     return 1.7F;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntitySnowman.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */