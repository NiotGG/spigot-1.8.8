/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*    */ import org.bukkit.event.player.PlayerTeleportEvent;
/*    */ 
/*    */ public class EntityEnderPearl extends EntityProjectile
/*    */ {
/*    */   private EntityLiving c;
/*    */   
/*    */   public EntityEnderPearl(World world)
/*    */   {
/* 14 */     super(world);
/*    */   }
/*    */   
/*    */   public EntityEnderPearl(World world, EntityLiving entityliving) {
/* 18 */     super(world, entityliving);
/* 19 */     this.c = entityliving;
/*    */   }
/*    */   
/*    */   protected void a(MovingObjectPosition movingobjectposition) {
/* 23 */     EntityLiving entityliving = getShooter();
/*    */     
/* 25 */     if (movingobjectposition.entity != null) {
/* 26 */       if (movingobjectposition.entity == this.c) {
/* 27 */         return;
/*    */       }
/*    */       
/* 30 */       movingobjectposition.entity.damageEntity(DamageSource.projectile(this, entityliving), 0.0F);
/*    */     }
/*    */     
/* 33 */     for (int i = 0; i < 32; i++) {
/* 34 */       this.world.addParticle(EnumParticle.PORTAL, this.locX, this.locY + this.random.nextDouble() * 2.0D, this.locZ, this.random.nextGaussian(), 0.0D, this.random.nextGaussian(), new int[0]);
/*    */     }
/*    */     
/* 37 */     if (!this.world.isClientSide) {
/* 38 */       if ((entityliving instanceof EntityPlayer)) {
/* 39 */         EntityPlayer entityplayer = (EntityPlayer)entityliving;
/*    */         
/* 41 */         if ((entityplayer.playerConnection.a().g()) && (entityplayer.world == this.world) && (!entityplayer.isSleeping()))
/*    */         {
/* 43 */           CraftPlayer player = entityplayer.getBukkitEntity();
/* 44 */           Location location = getBukkitEntity().getLocation();
/* 45 */           location.setPitch(player.getLocation().getPitch());
/* 46 */           location.setYaw(player.getLocation().getYaw());
/*    */           
/* 48 */           PlayerTeleportEvent teleEvent = new PlayerTeleportEvent(player, player.getLocation(), location, org.bukkit.event.player.PlayerTeleportEvent.TeleportCause.ENDER_PEARL);
/* 49 */           org.bukkit.Bukkit.getPluginManager().callEvent(teleEvent);
/*    */           
/* 51 */           if ((!teleEvent.isCancelled()) && (!entityplayer.playerConnection.isDisconnected())) {
/* 52 */             if ((this.random.nextFloat() < 0.05F) && (this.world.getGameRules().getBoolean("doMobSpawning"))) {
/* 53 */               EntityEndermite entityendermite = new EntityEndermite(this.world);
/*    */               
/* 55 */               entityendermite.a(true);
/* 56 */               entityendermite.setPositionRotation(entityliving.locX, entityliving.locY, entityliving.locZ, entityliving.yaw, entityliving.pitch);
/* 57 */               this.world.addEntity(entityendermite);
/*    */             }
/*    */             
/* 60 */             if (entityliving.au()) {
/* 61 */               entityliving.mount(null);
/*    */             }
/*    */             
/* 64 */             entityplayer.playerConnection.teleport(teleEvent.getTo());
/* 65 */             entityliving.fallDistance = 0.0F;
/* 66 */             org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.entityDamage = this;
/* 67 */             entityliving.damageEntity(DamageSource.FALL, 5.0F);
/* 68 */             org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.entityDamage = null;
/*    */           }
/*    */         }
/*    */       }
/* 72 */       else if (entityliving != null) {
/* 73 */         entityliving.enderTeleportTo(this.locX, this.locY, this.locZ);
/* 74 */         entityliving.fallDistance = 0.0F;
/*    */       }
/*    */       
/* 77 */       die();
/*    */     }
/*    */   }
/*    */   
/*    */   public void t_()
/*    */   {
/* 83 */     EntityLiving entityliving = getShooter();
/*    */     
/* 85 */     if ((entityliving != null) && ((entityliving instanceof EntityHuman)) && (!entityliving.isAlive())) {
/* 86 */       die();
/*    */     } else {
/* 88 */       super.t_();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityEnderPearl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */