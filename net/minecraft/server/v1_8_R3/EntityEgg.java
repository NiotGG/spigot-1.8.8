/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.entity.Ageable;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.event.player.PlayerEggThrowEvent;
/*    */ 
/*    */ public class EntityEgg extends EntityProjectile
/*    */ {
/*    */   public EntityEgg(World world)
/*    */   {
/* 13 */     super(world);
/*    */   }
/*    */   
/*    */   public EntityEgg(World world, EntityLiving entityliving) {
/* 17 */     super(world, entityliving);
/*    */   }
/*    */   
/*    */   public EntityEgg(World world, double d0, double d1, double d2) {
/* 21 */     super(world, d0, d1, d2);
/*    */   }
/*    */   
/*    */   protected void a(MovingObjectPosition movingobjectposition) {
/* 25 */     if (movingobjectposition.entity != null) {
/* 26 */       movingobjectposition.entity.damageEntity(DamageSource.projectile(this, getShooter()), 0.0F);
/*    */     }
/*    */     
/*    */ 
/* 30 */     boolean hatching = (!this.world.isClientSide) && (this.random.nextInt(8) == 0);
/* 31 */     int numHatching = this.random.nextInt(32) == 0 ? 4 : 1;
/* 32 */     if (!hatching) {
/* 33 */       numHatching = 0;
/*    */     }
/*    */     
/* 36 */     EntityType hatchingType = EntityType.CHICKEN;
/*    */     
/* 38 */     Entity shooter = getShooter();
/* 39 */     if ((shooter instanceof EntityPlayer)) {
/* 40 */       org.bukkit.entity.Player player = shooter == null ? null : (org.bukkit.entity.Player)shooter.getBukkitEntity();
/*    */       
/* 42 */       PlayerEggThrowEvent event = new PlayerEggThrowEvent(player, (org.bukkit.entity.Egg)getBukkitEntity(), hatching, (byte)numHatching, hatchingType);
/* 43 */       this.world.getServer().getPluginManager().callEvent(event);
/*    */       
/* 45 */       hatching = event.isHatching();
/* 46 */       numHatching = event.getNumHatches();
/* 47 */       hatchingType = event.getHatchingType();
/*    */     }
/*    */     
/* 50 */     if (hatching) {
/* 51 */       for (int k = 0; k < numHatching; k++) {
/* 52 */         Entity entity = this.world.getWorld().createEntity(new org.bukkit.Location(this.world.getWorld(), this.locX, this.locY, this.locZ, this.yaw, 0.0F), hatchingType.getEntityClass());
/* 53 */         if ((entity.getBukkitEntity() instanceof Ageable)) {
/* 54 */           ((Ageable)entity.getBukkitEntity()).setBaby();
/*    */         }
/* 56 */         this.world.getWorld().addEntity(entity, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.EGG);
/*    */       }
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/* 63 */     for (int j = 0; j < 8; j++) {
/* 64 */       this.world.addParticle(EnumParticle.ITEM_CRACK, this.locX, this.locY, this.locZ, (this.random.nextFloat() - 0.5D) * 0.08D, (this.random.nextFloat() - 0.5D) * 0.08D, (this.random.nextFloat() - 0.5D) * 0.08D, new int[] { Item.getId(Items.EGG) });
/*    */     }
/*    */     
/* 67 */     if (!this.world.isClientSide) {
/* 68 */       die();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityEgg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */