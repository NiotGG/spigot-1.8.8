/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.event.player.PlayerShearEntityEvent;
/*    */ 
/*    */ public class EntityMushroomCow extends EntityCow
/*    */ {
/*    */   public EntityMushroomCow(World world) {
/*  8 */     super(world);
/*  9 */     setSize(0.9F, 1.3F);
/* 10 */     this.bn = Blocks.MYCELIUM;
/*    */   }
/*    */   
/*    */   public boolean a(EntityHuman entityhuman) {
/* 14 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*    */     
/* 16 */     if ((itemstack != null) && (itemstack.getItem() == Items.BOWL) && (getAge() >= 0)) {
/* 17 */       if (itemstack.count == 1) {
/* 18 */         entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, new ItemStack(Items.MUSHROOM_STEW));
/* 19 */         return true;
/*    */       }
/*    */       
/* 22 */       if ((entityhuman.inventory.pickup(new ItemStack(Items.MUSHROOM_STEW))) && (!entityhuman.abilities.canInstantlyBuild)) {
/* 23 */         entityhuman.inventory.splitStack(entityhuman.inventory.itemInHandIndex, 1);
/* 24 */         return true;
/*    */       }
/*    */     }
/*    */     
/* 28 */     if ((itemstack != null) && (itemstack.getItem() == Items.SHEARS) && (getAge() >= 0))
/*    */     {
/* 30 */       PlayerShearEntityEvent event = new PlayerShearEntityEvent((org.bukkit.entity.Player)entityhuman.getBukkitEntity(), getBukkitEntity());
/* 31 */       this.world.getServer().getPluginManager().callEvent(event);
/*    */       
/* 33 */       if (event.isCancelled()) {
/* 34 */         return false;
/*    */       }
/*    */       
/* 37 */       die();
/* 38 */       this.world.addParticle(EnumParticle.EXPLOSION_LARGE, this.locX, this.locY + this.length / 2.0F, this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);
/* 39 */       if (!this.world.isClientSide) {
/* 40 */         EntityCow entitycow = new EntityCow(this.world);
/*    */         
/* 42 */         entitycow.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/* 43 */         entitycow.setHealth(getHealth());
/* 44 */         entitycow.aI = this.aI;
/* 45 */         if (hasCustomName()) {
/* 46 */           entitycow.setCustomName(getCustomName());
/*    */         }
/*    */         
/* 49 */         this.world.addEntity(entitycow);
/*    */         
/* 51 */         for (int i = 0; i < 5; i++) {
/* 52 */           this.world.addEntity(new EntityItem(this.world, this.locX, this.locY + this.length, this.locZ, new ItemStack(Blocks.RED_MUSHROOM)));
/*    */         }
/*    */         
/* 55 */         itemstack.damage(1, entityhuman);
/* 56 */         makeSound("mob.sheep.shear", 1.0F, 1.0F);
/*    */       }
/*    */       
/* 59 */       return true;
/*    */     }
/* 61 */     return super.a(entityhuman);
/*    */   }
/*    */   
/*    */   public EntityMushroomCow c(EntityAgeable entityageable)
/*    */   {
/* 66 */     return new EntityMushroomCow(this.world);
/*    */   }
/*    */   
/*    */   public EntityCow b(EntityAgeable entityageable) {
/* 70 */     return c(entityageable);
/*    */   }
/*    */   
/*    */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 74 */     return c(entityageable);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityMushroomCow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */