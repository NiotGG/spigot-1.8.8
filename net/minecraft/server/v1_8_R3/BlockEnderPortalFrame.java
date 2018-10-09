/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
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
/*     */ public class BlockEnderPortalFrame
/*     */   extends Block
/*     */ {
/*  23 */   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
/*  24 */   public static final BlockStateBoolean EYE = BlockStateBoolean.of("eye");
/*     */   
/*     */   public BlockEnderPortalFrame() {
/*  27 */     super(Material.STONE, MaterialMapColor.C);
/*  28 */     j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(EYE, Boolean.valueOf(false)));
/*     */   }
/*     */   
/*     */   public boolean c()
/*     */   {
/*  33 */     return false;
/*     */   }
/*     */   
/*     */   public void j()
/*     */   {
/*  38 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
/*     */   }
/*     */   
/*     */   public void a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, AxisAlignedBB paramAxisAlignedBB, List<AxisAlignedBB> paramList, Entity paramEntity)
/*     */   {
/*  43 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
/*  44 */     super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*     */     
/*  46 */     if (((Boolean)paramWorld.getType(paramBlockPosition).get(EYE)).booleanValue()) {
/*  47 */       a(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
/*  48 */       super.a(paramWorld, paramBlockPosition, paramIBlockData, paramAxisAlignedBB, paramList, paramEntity);
/*     */     }
/*  50 */     j();
/*     */   }
/*     */   
/*     */ 
/*     */   public Item getDropType(IBlockData paramIBlockData, Random paramRandom, int paramInt)
/*     */   {
/*  56 */     return null;
/*     */   }
/*     */   
/*     */   public IBlockData getPlacedState(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3, int paramInt, EntityLiving paramEntityLiving)
/*     */   {
/*  61 */     return getBlockData().set(FACING, paramEntityLiving.getDirection().opposite()).set(EYE, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone()
/*     */   {
/*  66 */     return true;
/*     */   }
/*     */   
/*     */   public int l(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/*  71 */     if (((Boolean)paramWorld.getType(paramBlockPosition).get(EYE)).booleanValue()) {
/*  72 */       return 15;
/*     */     }
/*     */     
/*  75 */     return 0;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/*  80 */     return getBlockData().set(EYE, Boolean.valueOf((paramInt & 0x4) != 0)).set(FACING, EnumDirection.fromType2(paramInt & 0x3));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  87 */     int i = 0;
/*     */     
/*  89 */     i |= ((EnumDirection)paramIBlockData.get(FACING)).b();
/*     */     
/*  91 */     if (((Boolean)paramIBlockData.get(EYE)).booleanValue()) {
/*  92 */       i |= 0x4;
/*     */     }
/*     */     
/*  95 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 100 */     return new BlockStateList(this, new IBlockState[] { FACING, EYE });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockEnderPortalFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */