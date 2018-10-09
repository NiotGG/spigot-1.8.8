/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Queue;
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
/*     */ public class BlockSponge
/*     */   extends Block
/*     */ {
/*  25 */   public static final BlockStateBoolean WET = BlockStateBoolean.of("wet");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected BlockSponge()
/*     */   {
/*  34 */     super(Material.SPONGE);
/*  35 */     j(this.blockStateList.getBlockData().set(WET, Boolean.valueOf(false)));
/*  36 */     a(CreativeModeTab.b);
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  41 */     return LocaleI18n.get(a() + ".dry.name");
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/*  46 */     return ((Boolean)paramIBlockData.get(WET)).booleanValue() ? 1 : 0;
/*     */   }
/*     */   
/*     */   public void onPlace(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  51 */     e(paramWorld, paramBlockPosition, paramIBlockData);
/*     */   }
/*     */   
/*     */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*     */   {
/*  56 */     e(paramWorld, paramBlockPosition, paramIBlockData);
/*  57 */     super.doPhysics(paramWorld, paramBlockPosition, paramIBlockData, paramBlock);
/*     */   }
/*     */   
/*     */   protected void e(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData) {
/*  61 */     if ((!((Boolean)paramIBlockData.get(WET)).booleanValue()) && (e(paramWorld, paramBlockPosition)))
/*     */     {
/*  63 */       paramWorld.setTypeAndData(paramBlockPosition, paramIBlockData.set(WET, Boolean.valueOf(true)), 2);
/*  64 */       paramWorld.triggerEffect(2001, paramBlockPosition, Block.getId(Blocks.WATER));
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean e(World paramWorld, BlockPosition paramBlockPosition) {
/*  69 */     LinkedList localLinkedList = Lists.newLinkedList();
/*  70 */     ArrayList localArrayList = Lists.newArrayList();
/*  71 */     localLinkedList.add(new Tuple(paramBlockPosition, Integer.valueOf(0)));
/*     */     
/*  73 */     int i = 0;
/*  74 */     BlockPosition localBlockPosition1; while (!localLinkedList.isEmpty()) {
/*  75 */       localObject = (Tuple)localLinkedList.poll();
/*  76 */       localBlockPosition1 = (BlockPosition)((Tuple)localObject).a();
/*  77 */       int j = ((Integer)((Tuple)localObject).b()).intValue();
/*     */       
/*  79 */       for (EnumDirection localEnumDirection : EnumDirection.values()) {
/*  80 */         BlockPosition localBlockPosition2 = localBlockPosition1.shift(localEnumDirection);
/*  81 */         if (paramWorld.getType(localBlockPosition2).getBlock().getMaterial() == Material.WATER) {
/*  82 */           paramWorld.setTypeAndData(localBlockPosition2, Blocks.AIR.getBlockData(), 2);
/*  83 */           localArrayList.add(localBlockPosition2);
/*  84 */           i++;
/*  85 */           if (j < 6) {
/*  86 */             localLinkedList.add(new Tuple(localBlockPosition2, Integer.valueOf(j + 1)));
/*     */           }
/*     */         }
/*     */       }
/*  90 */       if (i > 64) {
/*     */         break;
/*     */       }
/*     */     }
/*  94 */     for (Object localObject = localArrayList.iterator(); ((Iterator)localObject).hasNext();) { localBlockPosition1 = (BlockPosition)((Iterator)localObject).next();
/*  95 */       paramWorld.applyPhysics(localBlockPosition1, Blocks.AIR);
/*     */     }
/*  97 */     return i > 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/* 108 */     return getBlockData().set(WET, Boolean.valueOf((paramInt & 0x1) == 1));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/* 113 */     return ((Boolean)paramIBlockData.get(WET)).booleanValue() ? 1 : 0;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/* 118 */     return new BlockStateList(this, new IBlockState[] { WET });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockSponge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */