/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PistonExtendsChecker
/*     */ {
/*     */   private final World a;
/*     */   private final BlockPosition b;
/*     */   private final BlockPosition c;
/*     */   private final EnumDirection d;
/*  20 */   private final List<BlockPosition> e = Lists.newArrayList();
/*  21 */   private final List<BlockPosition> f = Lists.newArrayList();
/*     */   
/*     */   public PistonExtendsChecker(World paramWorld, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, boolean paramBoolean) {
/*  24 */     this.a = paramWorld;
/*  25 */     this.b = paramBlockPosition;
/*     */     
/*  27 */     if (paramBoolean) {
/*  28 */       this.d = paramEnumDirection;
/*  29 */       this.c = paramBlockPosition.shift(paramEnumDirection);
/*     */     } else {
/*  31 */       this.d = paramEnumDirection.opposite();
/*  32 */       this.c = paramBlockPosition.shift(paramEnumDirection, 2);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a() {
/*  37 */     this.e.clear();
/*  38 */     this.f.clear();
/*     */     
/*  40 */     Block localBlock = this.a.getType(this.c).getBlock();
/*     */     
/*  42 */     if (!BlockPiston.a(localBlock, this.a, this.c, this.d, false)) {
/*  43 */       if (localBlock.k() != 1)
/*     */       {
/*  45 */         return false;
/*     */       }
/*  47 */       this.f.add(this.c);
/*  48 */       return true;
/*     */     }
/*     */     
/*     */ 
/*  52 */     if (!a(this.c))
/*     */     {
/*  54 */       return false;
/*     */     }
/*     */     
/*  57 */     for (int i = 0; i < this.e.size(); i++) {
/*  58 */       BlockPosition localBlockPosition = (BlockPosition)this.e.get(i);
/*     */       
/*     */ 
/*  61 */       if ((this.a.getType(localBlockPosition).getBlock() == Blocks.SLIME) && 
/*  62 */         (!b(localBlockPosition)))
/*     */       {
/*  64 */         return false;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  69 */     return true;
/*     */   }
/*     */   
/*     */   private boolean a(BlockPosition paramBlockPosition) {
/*  73 */     Block localBlock = this.a.getType(paramBlockPosition).getBlock();
/*  74 */     if (localBlock.getMaterial() == Material.AIR)
/*     */     {
/*  76 */       return true; }
/*  77 */     if (!BlockPiston.a(localBlock, this.a, paramBlockPosition, this.d, false))
/*     */     {
/*  79 */       return true; }
/*  80 */     if (paramBlockPosition.equals(this.b))
/*     */     {
/*  82 */       return true; }
/*  83 */     if (this.e.contains(paramBlockPosition))
/*     */     {
/*  85 */       return true;
/*     */     }
/*     */     
/*  88 */     int i = 1;
/*  89 */     if (i + this.e.size() > 12)
/*     */     {
/*  91 */       return false;
/*     */     }
/*     */     
/*  94 */     while (localBlock == Blocks.SLIME) {
/*  95 */       BlockPosition localBlockPosition1 = paramBlockPosition.shift(this.d.opposite(), i);
/*  96 */       localBlock = this.a.getType(localBlockPosition1).getBlock();
/*     */       
/*  98 */       if ((localBlock.getMaterial() == Material.AIR) || (!BlockPiston.a(localBlock, this.a, localBlockPosition1, this.d, false)) || (localBlockPosition1.equals(this.b))) {
/*     */         break;
/*     */       }
/* 101 */       i++;
/* 102 */       if (i + this.e.size() > 12) {
/* 103 */         return false;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 108 */     int j = 0;
/*     */     
/*     */ 
/* 111 */     for (int k = i - 1; k >= 0; k--) {
/* 112 */       this.e.add(paramBlockPosition.shift(this.d.opposite(), k));
/* 113 */       j++;
/*     */     }
/*     */     
/*     */ 
/* 117 */     for (k = 1;; k++) {
/* 118 */       BlockPosition localBlockPosition2 = paramBlockPosition.shift(this.d, k);
/*     */       
/* 120 */       int m = this.e.indexOf(localBlockPosition2);
/* 121 */       if (m > -1)
/*     */       {
/* 123 */         a(j, m);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 128 */         for (int n = 0; n <= m + j; n++) {
/* 129 */           BlockPosition localBlockPosition3 = (BlockPosition)this.e.get(n);
/* 130 */           if ((this.a.getType(localBlockPosition3).getBlock() == Blocks.SLIME) && 
/* 131 */             (!b(localBlockPosition3))) {
/* 132 */             return false;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 137 */         return true;
/*     */       }
/*     */       
/* 140 */       localBlock = this.a.getType(localBlockPosition2).getBlock();
/*     */       
/* 142 */       if (localBlock.getMaterial() == Material.AIR)
/*     */       {
/* 144 */         return true;
/*     */       }
/*     */       
/* 147 */       if ((!BlockPiston.a(localBlock, this.a, localBlockPosition2, this.d, true)) || (localBlockPosition2.equals(this.b)))
/*     */       {
/* 149 */         return false;
/*     */       }
/*     */       
/* 152 */       if (localBlock.k() == 1) {
/* 153 */         this.f.add(localBlockPosition2);
/* 154 */         return true;
/*     */       }
/*     */       
/* 157 */       if (this.e.size() >= 12) {
/* 158 */         return false;
/*     */       }
/*     */       
/* 161 */       this.e.add(localBlockPosition2);
/* 162 */       j++;
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(int paramInt1, int paramInt2) {
/* 167 */     ArrayList localArrayList1 = Lists.newArrayList();
/* 168 */     ArrayList localArrayList2 = Lists.newArrayList();
/* 169 */     ArrayList localArrayList3 = Lists.newArrayList();
/*     */     
/* 171 */     localArrayList1.addAll(this.e.subList(0, paramInt2));
/* 172 */     localArrayList2.addAll(this.e.subList(this.e.size() - paramInt1, this.e.size()));
/* 173 */     localArrayList3.addAll(this.e.subList(paramInt2, this.e.size() - paramInt1));
/*     */     
/* 175 */     this.e.clear();
/* 176 */     this.e.addAll(localArrayList1);
/* 177 */     this.e.addAll(localArrayList2);
/* 178 */     this.e.addAll(localArrayList3);
/*     */   }
/*     */   
/*     */   private boolean b(BlockPosition paramBlockPosition) {
/* 182 */     for (EnumDirection localEnumDirection : ) {
/* 183 */       if ((localEnumDirection.k() != this.d.k()) && 
/* 184 */         (!a(paramBlockPosition.shift(localEnumDirection)))) {
/* 185 */         return false;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 190 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<BlockPosition> getMovedBlocks()
/*     */   {
/* 198 */     return this.e;
/*     */   }
/*     */   
/*     */   public List<BlockPosition> getBrokenBlocks() {
/* 202 */     return this.f;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PistonExtendsChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */