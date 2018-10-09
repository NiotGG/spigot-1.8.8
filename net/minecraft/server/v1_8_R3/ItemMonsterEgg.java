/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
/*     */ 
/*     */ public class ItemMonsterEgg extends Item {
/*   6 */   public ItemMonsterEgg() { a(true);
/*   7 */     a(CreativeModeTab.f);
/*     */   }
/*     */   
/*     */   public String a(ItemStack itemstack) {
/*  11 */     String s = LocaleI18n.get(new StringBuilder(String.valueOf(getName())).append(".name").toString()).trim();
/*  12 */     String s1 = EntityTypes.b(itemstack.getData());
/*     */     
/*  14 */     if (s1 != null) {
/*  15 */       s = s + " " + LocaleI18n.get(new StringBuilder("entity.").append(s1).append(".name").toString());
/*     */     }
/*     */     
/*  18 */     return s;
/*     */   }
/*     */   
/*     */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2) {
/*  22 */     if (world.isClientSide)
/*  23 */       return true;
/*  24 */     if (!entityhuman.a(blockposition.shift(enumdirection), enumdirection, itemstack)) {
/*  25 */       return false;
/*     */     }
/*  27 */     IBlockData iblockdata = world.getType(blockposition);
/*     */     
/*  29 */     if (iblockdata.getBlock() == Blocks.MOB_SPAWNER) {
/*  30 */       TileEntity tileentity = world.getTileEntity(blockposition);
/*     */       
/*  32 */       if ((tileentity instanceof TileEntityMobSpawner)) {
/*  33 */         MobSpawnerAbstract mobspawnerabstract = ((TileEntityMobSpawner)tileentity).getSpawner();
/*     */         
/*  35 */         mobspawnerabstract.setMobName(EntityTypes.b(itemstack.getData()));
/*  36 */         tileentity.update();
/*  37 */         world.notify(blockposition);
/*  38 */         if (!entityhuman.abilities.canInstantlyBuild) {
/*  39 */           itemstack.count -= 1;
/*     */         }
/*     */         
/*  42 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  46 */     blockposition = blockposition.shift(enumdirection);
/*  47 */     double d0 = 0.0D;
/*     */     
/*  49 */     if ((enumdirection == EnumDirection.UP) && ((iblockdata instanceof BlockFence))) {
/*  50 */       d0 = 0.5D;
/*     */     }
/*     */     
/*  53 */     Entity entity = a(world, itemstack.getData(), blockposition.getX() + 0.5D, blockposition.getY() + d0, blockposition.getZ() + 0.5D);
/*     */     
/*  55 */     if (entity != null) {
/*  56 */       if (((entity instanceof EntityLiving)) && (itemstack.hasName())) {
/*  57 */         entity.setCustomName(itemstack.getName());
/*     */       }
/*     */       
/*  60 */       if (!entityhuman.abilities.canInstantlyBuild) {
/*  61 */         itemstack.count -= 1;
/*     */       }
/*     */     }
/*     */     
/*  65 */     return true;
/*     */   }
/*     */   
/*     */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman)
/*     */   {
/*  70 */     if (world.isClientSide) {
/*  71 */       return itemstack;
/*     */     }
/*  73 */     MovingObjectPosition movingobjectposition = a(world, entityhuman, true);
/*     */     
/*  75 */     if (movingobjectposition == null) {
/*  76 */       return itemstack;
/*     */     }
/*  78 */     if (movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) {
/*  79 */       BlockPosition blockposition = movingobjectposition.a();
/*     */       
/*  81 */       if (!world.a(entityhuman, blockposition)) {
/*  82 */         return itemstack;
/*     */       }
/*     */       
/*  85 */       if (!entityhuman.a(blockposition, movingobjectposition.direction, itemstack)) {
/*  86 */         return itemstack;
/*     */       }
/*     */       
/*  89 */       if ((world.getType(blockposition).getBlock() instanceof BlockFluids)) {
/*  90 */         Entity entity = a(world, itemstack.getData(), blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D);
/*     */         
/*  92 */         if (entity != null) {
/*  93 */           if (((entity instanceof EntityLiving)) && (itemstack.hasName())) {
/*  94 */             ((EntityInsentient)entity).setCustomName(itemstack.getName());
/*     */           }
/*     */           
/*  97 */           if (!entityhuman.abilities.canInstantlyBuild) {
/*  98 */             itemstack.count -= 1;
/*     */           }
/*     */           
/* 101 */           entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 106 */     return itemstack;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static Entity a(World world, int i, double d0, double d1, double d2)
/*     */   {
/* 113 */     return spawnCreature(world, i, d0, d1, d2, CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
/*     */   }
/*     */   
/*     */   public static Entity spawnCreature(World world, int i, double d0, double d1, double d2, CreatureSpawnEvent.SpawnReason spawnReason)
/*     */   {
/* 118 */     if (!EntityTypes.eggInfo.containsKey(Integer.valueOf(i))) {
/* 119 */       return null;
/*     */     }
/* 121 */     Entity entity = null;
/*     */     
/* 123 */     for (int j = 0; j < 1; j++) {
/* 124 */       entity = EntityTypes.a(i, world);
/* 125 */       if ((entity instanceof EntityLiving)) {
/* 126 */         EntityInsentient entityinsentient = (EntityInsentient)entity;
/*     */         
/* 128 */         entity.setPositionRotation(d0, d1, d2, MathHelper.g(world.random.nextFloat() * 360.0F), 0.0F);
/* 129 */         entityinsentient.aK = entityinsentient.yaw;
/* 130 */         entityinsentient.aI = entityinsentient.yaw;
/* 131 */         entityinsentient.prepare(world.E(new BlockPosition(entityinsentient)), null);
/*     */         
/* 133 */         if (!world.addEntity(entity, spawnReason)) {
/* 134 */           entity = null;
/*     */         } else {
/* 136 */           entityinsentient.x();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 142 */     return entity;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemMonsterEgg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */