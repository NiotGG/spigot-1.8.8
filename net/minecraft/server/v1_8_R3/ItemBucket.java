/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.event.player.PlayerBucketEmptyEvent;
/*     */ import org.bukkit.event.player.PlayerBucketFillEvent;
/*     */ 
/*     */ public class ItemBucket extends Item
/*     */ {
/*     */   private Block a;
/*     */   
/*     */   public ItemBucket(Block block)
/*     */   {
/*  15 */     this.maxStackSize = 1;
/*  16 */     this.a = block;
/*  17 */     a(CreativeModeTab.f);
/*     */   }
/*     */   
/*     */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/*  21 */     boolean flag = this.a == Blocks.AIR;
/*  22 */     MovingObjectPosition movingobjectposition = a(world, entityhuman, flag);
/*     */     
/*  24 */     if (movingobjectposition == null) {
/*  25 */       return itemstack;
/*     */     }
/*  27 */     if (movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) {
/*  28 */       BlockPosition blockposition = movingobjectposition.a();
/*     */       
/*  30 */       if (!world.a(entityhuman, blockposition)) {
/*  31 */         return itemstack;
/*     */       }
/*     */       
/*  34 */       if (flag) {
/*  35 */         if (!entityhuman.a(blockposition.shift(movingobjectposition.direction), movingobjectposition.direction, itemstack)) {
/*  36 */           return itemstack;
/*     */         }
/*     */         
/*  39 */         IBlockData iblockdata = world.getType(blockposition);
/*  40 */         Material material = iblockdata.getBlock().getMaterial();
/*     */         
/*  42 */         if ((material == Material.WATER) && (((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue() == 0))
/*     */         {
/*  44 */           PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, blockposition.getX(), blockposition.getY(), blockposition.getZ(), null, itemstack, Items.WATER_BUCKET);
/*     */           
/*  46 */           if (event.isCancelled()) {
/*  47 */             return itemstack;
/*     */           }
/*     */           
/*  50 */           world.setAir(blockposition);
/*  51 */           entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/*  52 */           return a(itemstack, entityhuman, Items.WATER_BUCKET, event.getItemStack());
/*     */         }
/*     */         
/*  55 */         if ((material == Material.LAVA) && (((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue() == 0))
/*     */         {
/*  57 */           PlayerBucketFillEvent event = CraftEventFactory.callPlayerBucketFillEvent(entityhuman, blockposition.getX(), blockposition.getY(), blockposition.getZ(), null, itemstack, Items.LAVA_BUCKET);
/*     */           
/*  59 */           if (event.isCancelled()) {
/*  60 */             return itemstack;
/*     */           }
/*     */           
/*  63 */           world.setAir(blockposition);
/*  64 */           entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/*  65 */           return a(itemstack, entityhuman, Items.LAVA_BUCKET, event.getItemStack());
/*     */         }
/*     */       } else {
/*  68 */         if (this.a == Blocks.AIR)
/*     */         {
/*  70 */           PlayerBucketEmptyEvent event = CraftEventFactory.callPlayerBucketEmptyEvent(entityhuman, blockposition.getX(), blockposition.getY(), blockposition.getZ(), movingobjectposition.direction, itemstack);
/*     */           
/*  72 */           if (event.isCancelled()) {
/*  73 */             return itemstack;
/*     */           }
/*     */           
/*  76 */           return CraftItemStack.asNMSCopy(event.getItemStack());
/*     */         }
/*     */         
/*     */ 
/*  80 */         BlockPosition blockposition1 = blockposition.shift(movingobjectposition.direction);
/*     */         
/*  82 */         if (!entityhuman.a(blockposition1, movingobjectposition.direction, itemstack)) {
/*  83 */           return itemstack;
/*     */         }
/*     */         
/*     */ 
/*  87 */         PlayerBucketEmptyEvent event = CraftEventFactory.callPlayerBucketEmptyEvent(entityhuman, blockposition.getX(), blockposition.getY(), blockposition.getZ(), movingobjectposition.direction, itemstack);
/*     */         
/*  89 */         if (event.isCancelled()) {
/*  90 */           return itemstack;
/*     */         }
/*     */         
/*     */ 
/*  94 */         if ((a(world, blockposition1)) && (!entityhuman.abilities.canInstantlyBuild)) {
/*  95 */           entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/*  96 */           return CraftItemStack.asNMSCopy(event.getItemStack());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 101 */     return itemstack;
/*     */   }
/*     */   
/*     */ 
/*     */   private ItemStack a(ItemStack itemstack, EntityHuman entityhuman, Item item, org.bukkit.inventory.ItemStack result)
/*     */   {
/* 107 */     if (entityhuman.abilities.canInstantlyBuild)
/* 108 */       return itemstack;
/* 109 */     if (--itemstack.count <= 0) {
/* 110 */       return CraftItemStack.asNMSCopy(result);
/*     */     }
/* 112 */     if (!entityhuman.inventory.pickup(CraftItemStack.asNMSCopy(result))) {
/* 113 */       entityhuman.drop(CraftItemStack.asNMSCopy(result), false);
/*     */     }
/*     */     
/* 116 */     return itemstack;
/*     */   }
/*     */   
/*     */   public boolean a(World world, BlockPosition blockposition)
/*     */   {
/* 121 */     if (this.a == Blocks.AIR) {
/* 122 */       return false;
/*     */     }
/* 124 */     Material material = world.getType(blockposition).getBlock().getMaterial();
/* 125 */     boolean flag = !material.isBuildable();
/*     */     
/* 127 */     if ((!world.isEmpty(blockposition)) && (!flag)) {
/* 128 */       return false;
/*     */     }
/* 130 */     if ((world.worldProvider.n()) && (this.a == Blocks.FLOWING_WATER)) {
/* 131 */       int i = blockposition.getX();
/* 132 */       int j = blockposition.getY();
/* 133 */       int k = blockposition.getZ();
/*     */       
/* 135 */       world.makeSound(i + 0.5F, j + 0.5F, k + 0.5F, "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
/*     */       
/* 137 */       for (int l = 0; l < 8; l++) {
/* 138 */         world.addParticle(EnumParticle.SMOKE_LARGE, i + Math.random(), j + Math.random(), k + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     } else {
/* 141 */       if ((!world.isClientSide) && (flag) && (!material.isLiquid())) {
/* 142 */         world.setAir(blockposition, true);
/*     */       }
/*     */       
/* 145 */       world.setTypeAndData(blockposition, this.a.getBlockData(), 3);
/*     */     }
/*     */     
/* 148 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemBucket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */