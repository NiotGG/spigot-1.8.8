/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemBlock
/*     */   extends Item
/*     */ {
/*     */   protected final Block a;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemBlock(Block paramBlock)
/*     */   {
/*  24 */     this.a = paramBlock;
/*     */   }
/*     */   
/*     */   public ItemBlock b(String paramString)
/*     */   {
/*  29 */     super.c(paramString);
/*  30 */     return this;
/*     */   }
/*     */   
/*     */   public boolean interactWith(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/*  35 */     IBlockData localIBlockData1 = paramWorld.getType(paramBlockPosition);
/*  36 */     Block localBlock = localIBlockData1.getBlock();
/*  37 */     if (!localBlock.a(paramWorld, paramBlockPosition)) {
/*  38 */       paramBlockPosition = paramBlockPosition.shift(paramEnumDirection);
/*     */     }
/*     */     
/*  41 */     if (paramItemStack.count == 0) {
/*  42 */       return false;
/*     */     }
/*  44 */     if (!paramEntityHuman.a(paramBlockPosition, paramEnumDirection, paramItemStack)) {
/*  45 */       return false;
/*     */     }
/*     */     
/*  48 */     if (paramWorld.a(this.a, paramBlockPosition, false, paramEnumDirection, null, paramItemStack)) {
/*  49 */       int i = filterData(paramItemStack.getData());
/*  50 */       IBlockData localIBlockData2 = this.a.getPlacedState(paramWorld, paramBlockPosition, paramEnumDirection, paramFloat1, paramFloat2, paramFloat3, i, paramEntityHuman);
/*  51 */       if (paramWorld.setTypeAndData(paramBlockPosition, localIBlockData2, 3)) {
/*  52 */         localIBlockData2 = paramWorld.getType(paramBlockPosition);
/*     */         
/*     */ 
/*     */ 
/*  56 */         if (localIBlockData2.getBlock() == this.a) {
/*  57 */           a(paramWorld, paramEntityHuman, paramBlockPosition, paramItemStack);
/*  58 */           this.a.postPlace(paramWorld, paramBlockPosition, localIBlockData2, paramEntityHuman, paramItemStack);
/*     */         }
/*  60 */         paramWorld.makeSound(paramBlockPosition.getX() + 0.5F, paramBlockPosition.getY() + 0.5F, paramBlockPosition.getZ() + 0.5F, this.a.stepSound.getPlaceSound(), (this.a.stepSound.getVolume1() + 1.0F) / 2.0F, this.a.stepSound.getVolume2() * 0.8F);
/*  61 */         paramItemStack.count -= 1;
/*     */       }
/*  63 */       return true;
/*     */     }
/*  65 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean a(World paramWorld, EntityHuman paramEntityHuman, BlockPosition paramBlockPosition, ItemStack paramItemStack) {
/*  69 */     MinecraftServer localMinecraftServer = MinecraftServer.getServer();
/*  70 */     if (localMinecraftServer == null) {
/*  71 */       return false;
/*     */     }
/*  73 */     if ((paramItemStack.hasTag()) && (paramItemStack.getTag().hasKeyOfType("BlockEntityTag", 10))) {
/*  74 */       TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/*     */       
/*  76 */       if (localTileEntity != null) {
/*  77 */         if ((!paramWorld.isClientSide) && (localTileEntity.F()) && (!localMinecraftServer.getPlayerList().isOp(paramEntityHuman.getProfile()))) {
/*  78 */           return false;
/*     */         }
/*  80 */         NBTTagCompound localNBTTagCompound1 = new NBTTagCompound();
/*  81 */         NBTTagCompound localNBTTagCompound2 = (NBTTagCompound)localNBTTagCompound1.clone();
/*  82 */         localTileEntity.b(localNBTTagCompound1);
/*     */         
/*  84 */         NBTTagCompound localNBTTagCompound3 = (NBTTagCompound)paramItemStack.getTag().get("BlockEntityTag");
/*  85 */         localNBTTagCompound1.a(localNBTTagCompound3);
/*  86 */         localNBTTagCompound1.setInt("x", paramBlockPosition.getX());
/*  87 */         localNBTTagCompound1.setInt("y", paramBlockPosition.getY());
/*  88 */         localNBTTagCompound1.setInt("z", paramBlockPosition.getZ());
/*     */         
/*  90 */         if (!localNBTTagCompound1.equals(localNBTTagCompound2)) {
/*  91 */           localTileEntity.a(localNBTTagCompound1);
/*  92 */           localTileEntity.update();
/*  93 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*  97 */     return false;
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
/*     */   public String e_(ItemStack paramItemStack)
/*     */   {
/* 113 */     return this.a.a();
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 118 */     return this.a.a();
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
/*     */   public Block d()
/*     */   {
/* 132 */     return this.a;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */