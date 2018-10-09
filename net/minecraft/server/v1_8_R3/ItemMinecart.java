/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.event.block.BlockDispenseEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class ItemMinecart extends Item
/*     */ {
/*  10 */   private static final IDispenseBehavior a = new DispenseBehaviorItem() {
/*  11 */     private final DispenseBehaviorItem b = new DispenseBehaviorItem();
/*     */     
/*     */     public ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/*  14 */       EnumDirection enumdirection = BlockDispenser.b(isourceblock.f());
/*  15 */       World world = isourceblock.getWorld();
/*  16 */       double d0 = isourceblock.getX() + enumdirection.getAdjacentX() * 1.125D;
/*  17 */       double d1 = Math.floor(isourceblock.getY()) + enumdirection.getAdjacentY();
/*  18 */       double d2 = isourceblock.getZ() + enumdirection.getAdjacentZ() * 1.125D;
/*  19 */       BlockPosition blockposition = isourceblock.getBlockPosition().shift(enumdirection);
/*  20 */       IBlockData iblockdata = world.getType(blockposition);
/*  21 */       BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition = (iblockdata.getBlock() instanceof BlockMinecartTrackAbstract) ? (BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(((BlockMinecartTrackAbstract)iblockdata.getBlock()).n()) : BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
/*     */       double d3;
/*     */       double d3;
/*  24 */       if (BlockMinecartTrackAbstract.d(iblockdata)) { double d3;
/*  25 */         if (blockminecarttrackabstract_enumtrackposition.c()) {
/*  26 */           d3 = 0.6D;
/*     */         } else {
/*  28 */           d3 = 0.1D;
/*     */         }
/*     */       } else {
/*  31 */         if ((iblockdata.getBlock().getMaterial() != Material.AIR) || (!BlockMinecartTrackAbstract.d(world.getType(blockposition.down())))) {
/*  32 */           return this.b.a(isourceblock, itemstack);
/*     */         }
/*     */         
/*  35 */         IBlockData iblockdata1 = world.getType(blockposition.down());
/*  36 */         BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition1 = (iblockdata1.getBlock() instanceof BlockMinecartTrackAbstract) ? (BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata1.get(((BlockMinecartTrackAbstract)iblockdata1.getBlock()).n()) : BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
/*     */         double d3;
/*  38 */         if ((enumdirection != EnumDirection.DOWN) && (blockminecarttrackabstract_enumtrackposition1.c())) {
/*  39 */           d3 = -0.4D;
/*     */         } else {
/*  41 */           d3 = -0.9D;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  47 */       ItemStack itemstack1 = itemstack.cloneAndSubtract(1);
/*  48 */       org.bukkit.block.Block block2 = world.getWorld().getBlockAt(isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ());
/*  49 */       CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
/*     */       
/*  51 */       BlockDispenseEvent event = new BlockDispenseEvent(block2, craftItem.clone(), new Vector(d0, d1 + d3, d2));
/*  52 */       if (!BlockDispenser.eventFired) {
/*  53 */         world.getServer().getPluginManager().callEvent(event);
/*     */       }
/*     */       
/*  56 */       if (event.isCancelled()) {
/*  57 */         itemstack.count += 1;
/*  58 */         return itemstack;
/*     */       }
/*     */       
/*  61 */       if (!event.getItem().equals(craftItem)) {
/*  62 */         itemstack.count += 1;
/*     */         
/*  64 */         ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/*  65 */         IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(eventStack.getItem());
/*  66 */         if ((idispensebehavior != IDispenseBehavior.NONE) && (idispensebehavior != this)) {
/*  67 */           idispensebehavior.a(isourceblock, eventStack);
/*  68 */           return itemstack;
/*     */         }
/*     */       }
/*     */       
/*  72 */       itemstack1 = CraftItemStack.asNMSCopy(event.getItem());
/*  73 */       EntityMinecartAbstract entityminecartabstract = EntityMinecartAbstract.a(world, event.getVelocity().getX(), event.getVelocity().getY(), event.getVelocity().getZ(), ((ItemMinecart)itemstack1.getItem()).b);
/*     */       
/*  75 */       if (itemstack.hasName()) {
/*  76 */         entityminecartabstract.setCustomName(itemstack.getName());
/*     */       }
/*     */       
/*  79 */       world.addEntity(entityminecartabstract);
/*     */       
/*     */ 
/*  82 */       return itemstack;
/*     */     }
/*     */     
/*     */     protected void a(ISourceBlock isourceblock) {
/*  86 */       isourceblock.getWorld().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
/*     */     }
/*     */   };
/*     */   private final EntityMinecartAbstract.EnumMinecartType b;
/*     */   
/*     */   public ItemMinecart(EntityMinecartAbstract.EnumMinecartType entityminecartabstract_enumminecarttype) {
/*  92 */     this.maxStackSize = 1;
/*  93 */     this.b = entityminecartabstract_enumminecarttype;
/*  94 */     a(CreativeModeTab.e);
/*  95 */     BlockDispenser.REGISTRY.a(this, a);
/*     */   }
/*     */   
/*     */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2) {
/*  99 */     IBlockData iblockdata = world.getType(blockposition);
/*     */     
/* 101 */     if (BlockMinecartTrackAbstract.d(iblockdata)) {
/* 102 */       if (!world.isClientSide) {
/* 103 */         BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract_enumtrackposition = (iblockdata.getBlock() instanceof BlockMinecartTrackAbstract) ? (BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(((BlockMinecartTrackAbstract)iblockdata.getBlock()).n()) : BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
/* 104 */         double d0 = 0.0D;
/*     */         
/* 106 */         if (blockminecarttrackabstract_enumtrackposition.c()) {
/* 107 */           d0 = 0.5D;
/*     */         }
/*     */         
/*     */ 
/* 111 */         PlayerInteractEvent event = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callPlayerInteractEvent(entityhuman, org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK, blockposition, enumdirection, itemstack);
/*     */         
/* 113 */         if (event.isCancelled()) {
/* 114 */           return false;
/*     */         }
/*     */         
/*     */ 
/* 118 */         EntityMinecartAbstract entityminecartabstract = EntityMinecartAbstract.a(world, blockposition.getX() + 0.5D, blockposition.getY() + 0.0625D + d0, blockposition.getZ() + 0.5D, this.b);
/*     */         
/* 120 */         if (itemstack.hasName()) {
/* 121 */           entityminecartabstract.setCustomName(itemstack.getName());
/*     */         }
/*     */         
/* 124 */         world.addEntity(entityminecartabstract);
/*     */       }
/*     */       
/* 127 */       itemstack.count -= 1;
/* 128 */       return true;
/*     */     }
/* 130 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemMinecart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */