/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class StructurePieceTreasure
/*    */   extends WeightedRandom.WeightedRandomChoice
/*    */ {
/*    */   private ItemStack b;
/*    */   private int c;
/*    */   private int d;
/*    */   
/*    */   public StructurePieceTreasure(Item paramItem, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*    */   {
/* 19 */     super(paramInt4);
/* 20 */     this.b = new ItemStack(paramItem, 1, paramInt1);
/* 21 */     this.c = paramInt2;
/* 22 */     this.d = paramInt3;
/*    */   }
/*    */   
/*    */   public StructurePieceTreasure(ItemStack paramItemStack, int paramInt1, int paramInt2, int paramInt3) {
/* 26 */     super(paramInt3);
/* 27 */     this.b = paramItemStack;
/* 28 */     this.c = paramInt1;
/* 29 */     this.d = paramInt2;
/*    */   }
/*    */   
/*    */   public static void a(Random paramRandom, List<StructurePieceTreasure> paramList, IInventory paramIInventory, int paramInt) {
/* 33 */     for (int i = 0; i < paramInt; i++) {
/* 34 */       StructurePieceTreasure localStructurePieceTreasure = (StructurePieceTreasure)WeightedRandom.a(paramRandom, paramList);
/* 35 */       int j = localStructurePieceTreasure.c + paramRandom.nextInt(localStructurePieceTreasure.d - localStructurePieceTreasure.c + 1);
/*    */       
/* 37 */       if (localStructurePieceTreasure.b.getMaxStackSize() >= j) {
/* 38 */         ItemStack localItemStack1 = localStructurePieceTreasure.b.cloneItemStack();
/* 39 */         localItemStack1.count = j;
/* 40 */         paramIInventory.setItem(paramRandom.nextInt(paramIInventory.getSize()), localItemStack1);
/*    */       }
/*    */       else {
/* 43 */         for (int k = 0; k < j; k++) {
/* 44 */           ItemStack localItemStack2 = localStructurePieceTreasure.b.cloneItemStack();
/* 45 */           localItemStack2.count = 1;
/* 46 */           paramIInventory.setItem(paramRandom.nextInt(paramIInventory.getSize()), localItemStack2);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public static void a(Random paramRandom, List<StructurePieceTreasure> paramList, TileEntityDispenser paramTileEntityDispenser, int paramInt) {
/* 53 */     for (int i = 0; i < paramInt; i++) {
/* 54 */       StructurePieceTreasure localStructurePieceTreasure = (StructurePieceTreasure)WeightedRandom.a(paramRandom, paramList);
/* 55 */       int j = localStructurePieceTreasure.c + paramRandom.nextInt(localStructurePieceTreasure.d - localStructurePieceTreasure.c + 1);
/*    */       
/* 57 */       if (localStructurePieceTreasure.b.getMaxStackSize() >= j) {
/* 58 */         ItemStack localItemStack1 = localStructurePieceTreasure.b.cloneItemStack();
/* 59 */         localItemStack1.count = j;
/* 60 */         paramTileEntityDispenser.setItem(paramRandom.nextInt(paramTileEntityDispenser.getSize()), localItemStack1);
/*    */       }
/*    */       else {
/* 63 */         for (int k = 0; k < j; k++) {
/* 64 */           ItemStack localItemStack2 = localStructurePieceTreasure.b.cloneItemStack();
/* 65 */           localItemStack2.count = 1;
/* 66 */           paramTileEntityDispenser.setItem(paramRandom.nextInt(paramTileEntityDispenser.getSize()), localItemStack2);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public static List<StructurePieceTreasure> a(List<StructurePieceTreasure> paramList, StructurePieceTreasure... paramVarArgs) {
/* 73 */     ArrayList localArrayList = Lists.newArrayList(paramList);
/* 74 */     Collections.addAll(localArrayList, paramVarArgs);
/*    */     
/* 76 */     return localArrayList;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\StructurePieceTreasure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */