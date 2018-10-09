/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Collections2;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BlockFlowers
/*     */   extends BlockPlant
/*     */ {
/*     */   protected BlockStateEnum<EnumFlowerVarient> TYPE;
/*     */   
/*     */   protected BlockFlowers()
/*     */   {
/*  23 */     j(this.blockStateList.getBlockData().set(n(), l() == EnumFlowerType.RED ? EnumFlowerVarient.POPPY : EnumFlowerVarient.DANDELION));
/*     */   }
/*     */   
/*     */   public int getDropData(IBlockData paramIBlockData)
/*     */   {
/*  28 */     return ((EnumFlowerVarient)paramIBlockData.get(n())).b();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IBlockData fromLegacyData(int paramInt)
/*     */   {
/*  40 */     return getBlockData().set(n(), EnumFlowerVarient.a(l(), paramInt));
/*     */   }
/*     */   
/*     */   public abstract EnumFlowerType l();
/*     */   
/*     */   public IBlockState<EnumFlowerVarient> n()
/*     */   {
/*  47 */     if (this.TYPE == null) {
/*  48 */       this.TYPE = BlockStateEnum.a("type", EnumFlowerVarient.class, new Predicate()
/*     */       {
/*     */         public boolean a(BlockFlowers.EnumFlowerVarient paramAnonymousEnumFlowerVarient) {
/*  51 */           return paramAnonymousEnumFlowerVarient.a() == BlockFlowers.this.l();
/*     */         }
/*     */       });
/*     */     }
/*  55 */     return this.TYPE;
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData paramIBlockData)
/*     */   {
/*  60 */     return ((EnumFlowerVarient)paramIBlockData.get(n())).b();
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList()
/*     */   {
/*  65 */     return new BlockStateList(this, new IBlockState[] { n() });
/*     */   }
/*     */   
/*     */   public static enum EnumFlowerType
/*     */   {
/*     */     private EnumFlowerType() {}
/*     */     
/*     */     public BlockFlowers a()
/*     */     {
/*  74 */       if (this == YELLOW) {
/*  75 */         return Blocks.YELLOW_FLOWER;
/*     */       }
/*  77 */       return Blocks.RED_FLOWER;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum EnumFlowerVarient
/*     */     implements INamable
/*     */   {
/*     */     private static final EnumFlowerVarient[][] k;
/*     */     
/*     */ 
/*     */     private final BlockFlowers.EnumFlowerType l;
/*     */     
/*     */ 
/*     */     private final int m;
/*     */     
/*     */ 
/*     */     private final String n;
/*     */     
/*     */     private final String o;
/*     */     
/*     */ 
/*     */     static
/*     */     {
/* 102 */       k = new EnumFlowerVarient[BlockFlowers.EnumFlowerType.values().length][];
/*     */       
/* 104 */       for (BlockFlowers.EnumFlowerType localEnumFlowerType : BlockFlowers.EnumFlowerType.values()) {
/* 105 */         Collection localCollection = Collections2.filter(Lists.newArrayList(values()), new Predicate()
/*     */         {
/*     */           public boolean a(BlockFlowers.EnumFlowerVarient paramAnonymousEnumFlowerVarient) {
/* 108 */             return paramAnonymousEnumFlowerVarient.a() == this.a;
/*     */           }
/*     */           
/* 111 */         });
/* 112 */         k[localEnumFlowerType.ordinal()] = ((EnumFlowerVarient[])localCollection.toArray(new EnumFlowerVarient[localCollection.size()]));
/*     */       }
/*     */     }
/*     */     
/*     */     private EnumFlowerVarient(BlockFlowers.EnumFlowerType paramEnumFlowerType, int paramInt, String paramString) {
/* 117 */       this(paramEnumFlowerType, paramInt, paramString, paramString);
/*     */     }
/*     */     
/*     */     private EnumFlowerVarient(BlockFlowers.EnumFlowerType paramEnumFlowerType, int paramInt, String paramString1, String paramString2) {
/* 121 */       this.l = paramEnumFlowerType;
/* 122 */       this.m = paramInt;
/* 123 */       this.n = paramString1;
/* 124 */       this.o = paramString2;
/*     */     }
/*     */     
/*     */     public BlockFlowers.EnumFlowerType a() {
/* 128 */       return this.l;
/*     */     }
/*     */     
/*     */     public int b() {
/* 132 */       return this.m;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public static EnumFlowerVarient a(BlockFlowers.EnumFlowerType paramEnumFlowerType, int paramInt)
/*     */     {
/* 140 */       EnumFlowerVarient[] arrayOfEnumFlowerVarient = k[paramEnumFlowerType.ordinal()];
/* 141 */       if ((paramInt < 0) || (paramInt >= arrayOfEnumFlowerVarient.length)) {
/* 142 */         paramInt = 0;
/*     */       }
/* 144 */       return arrayOfEnumFlowerVarient[paramInt];
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 153 */       return this.n;
/*     */     }
/*     */     
/*     */     public String getName()
/*     */     {
/* 158 */       return this.n;
/*     */     }
/*     */     
/*     */     public String d() {
/* 162 */       return this.o;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockFlowers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */