/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemBanner
/*     */   extends ItemBlock
/*     */ {
/*     */   public ItemBanner()
/*     */   {
/*  27 */     super(Blocks.STANDING_BANNER);
/*  28 */     this.maxStackSize = 16;
/*  29 */     a(CreativeModeTab.c);
/*  30 */     a(true);
/*  31 */     setMaxDurability(0);
/*     */   }
/*     */   
/*     */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/*  36 */     if (paramEnumDirection == EnumDirection.DOWN) {
/*  37 */       return false;
/*     */     }
/*  39 */     if (!paramWorld.getType(paramBlockPosition).getBlock().getMaterial().isBuildable()) {
/*  40 */       return false;
/*     */     }
/*     */     
/*  43 */     paramBlockPosition = paramBlockPosition.shift(paramEnumDirection);
/*     */     
/*  45 */     if (!paramEntityHuman.a(paramBlockPosition, paramEnumDirection, paramItemStack)) {
/*  46 */       return false;
/*     */     }
/*  48 */     if (!Blocks.STANDING_BANNER.canPlace(paramWorld, paramBlockPosition)) {
/*  49 */       return false;
/*     */     }
/*     */     
/*  52 */     if (paramWorld.isClientSide) {
/*  53 */       return true;
/*     */     }
/*     */     
/*  56 */     if (paramEnumDirection == EnumDirection.UP) {
/*  57 */       int i = MathHelper.floor((paramEntityHuman.yaw + 180.0F) * 16.0F / 360.0F + 0.5D) & 0xF;
/*  58 */       paramWorld.setTypeAndData(paramBlockPosition, Blocks.STANDING_BANNER.getBlockData().set(BlockFloorSign.ROTATION, Integer.valueOf(i)), 3);
/*     */     } else {
/*  60 */       paramWorld.setTypeAndData(paramBlockPosition, Blocks.WALL_BANNER.getBlockData().set(BlockWallSign.FACING, paramEnumDirection), 3);
/*     */     }
/*     */     
/*  63 */     paramItemStack.count -= 1;
/*  64 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/*  65 */     if ((localTileEntity instanceof TileEntityBanner)) {
/*  66 */       ((TileEntityBanner)localTileEntity).a(paramItemStack);
/*     */     }
/*  68 */     return true;
/*     */   }
/*     */   
/*     */   public String a(ItemStack paramItemStack)
/*     */   {
/*  73 */     String str = "item.banner.";
/*     */     
/*  75 */     EnumColor localEnumColor = h(paramItemStack);
/*  76 */     str = str + localEnumColor.d() + ".name";
/*  77 */     return LocaleI18n.get(str);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private EnumColor h(ItemStack paramItemStack)
/*     */   {
/* 131 */     NBTTagCompound localNBTTagCompound = paramItemStack.a("BlockEntityTag", false);
/* 132 */     EnumColor localEnumColor = null;
/* 133 */     if ((localNBTTagCompound != null) && (localNBTTagCompound.hasKey("Base"))) {
/* 134 */       localEnumColor = EnumColor.fromInvColorIndex(localNBTTagCompound.getInt("Base"));
/*     */     } else {
/* 136 */       localEnumColor = EnumColor.fromInvColorIndex(paramItemStack.getData());
/*     */     }
/* 138 */     return localEnumColor;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemBanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */