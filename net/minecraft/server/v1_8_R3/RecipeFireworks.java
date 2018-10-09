/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class RecipeFireworks extends ShapelessRecipes implements IRecipe
/*     */ {
/*     */   private ItemStack a;
/*     */   
/*     */   public RecipeFireworks()
/*     */   {
/*  12 */     super(new ItemStack(Items.FIREWORKS, 0, 0), java.util.Arrays.asList(new ItemStack[] { new ItemStack(Items.GUNPOWDER, 0, 5) }));
/*     */   }
/*     */   
/*     */   public boolean a(InventoryCrafting inventorycrafting, World world)
/*     */   {
/*  17 */     this.a = null;
/*  18 */     int i = 0;
/*  19 */     int j = 0;
/*  20 */     int k = 0;
/*  21 */     int l = 0;
/*  22 */     int i1 = 0;
/*  23 */     int j1 = 0;
/*     */     
/*  25 */     for (int k1 = 0; k1 < inventorycrafting.getSize(); k1++) {
/*  26 */       ItemStack itemstack = inventorycrafting.getItem(k1);
/*     */       
/*  28 */       if (itemstack != null) {
/*  29 */         if (itemstack.getItem() == Items.GUNPOWDER) {
/*  30 */           j++;
/*  31 */         } else if (itemstack.getItem() == Items.FIREWORK_CHARGE) {
/*  32 */           l++;
/*  33 */         } else if (itemstack.getItem() == Items.DYE) {
/*  34 */           k++;
/*  35 */         } else if (itemstack.getItem() == Items.PAPER) {
/*  36 */           i++;
/*  37 */         } else if (itemstack.getItem() == Items.GLOWSTONE_DUST) {
/*  38 */           i1++;
/*  39 */         } else if (itemstack.getItem() == Items.DIAMOND) {
/*  40 */           i1++;
/*  41 */         } else if (itemstack.getItem() == Items.FIRE_CHARGE) {
/*  42 */           j1++;
/*  43 */         } else if (itemstack.getItem() == Items.FEATHER) {
/*  44 */           j1++;
/*  45 */         } else if (itemstack.getItem() == Items.GOLD_NUGGET) {
/*  46 */           j1++;
/*     */         } else {
/*  48 */           if (itemstack.getItem() != Items.SKULL) {
/*  49 */             return false;
/*     */           }
/*     */           
/*  52 */           j1++;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  57 */     i1 += k + j1;
/*  58 */     if ((j <= 3) && (i <= 1))
/*     */     {
/*     */ 
/*     */ 
/*  62 */       if ((j >= 1) && (i == 1) && (i1 == 0)) {
/*  63 */         this.a = new ItemStack(Items.FIREWORKS);
/*  64 */         if (l > 0) {
/*  65 */           NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  66 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  67 */           NBTTagList nbttaglist = new NBTTagList();
/*     */           
/*  69 */           for (int l1 = 0; l1 < inventorycrafting.getSize(); l1++) {
/*  70 */             ItemStack itemstack1 = inventorycrafting.getItem(l1);
/*     */             
/*  72 */             if ((itemstack1 != null) && (itemstack1.getItem() == Items.FIREWORK_CHARGE) && (itemstack1.hasTag()) && (itemstack1.getTag().hasKeyOfType("Explosion", 10))) {
/*  73 */               nbttaglist.add(itemstack1.getTag().getCompound("Explosion"));
/*     */             }
/*     */           }
/*     */           
/*  77 */           nbttagcompound1.set("Explosions", nbttaglist);
/*  78 */           nbttagcompound1.setByte("Flight", (byte)j);
/*  79 */           nbttagcompound.set("Fireworks", nbttagcompound1);
/*  80 */           this.a.setTag(nbttagcompound);
/*     */         }
/*     */         
/*  83 */         return true; }
/*  84 */       if ((j == 1) && (i == 0) && (l == 0) && (k > 0) && (j1 <= 1)) {
/*  85 */         this.a = new ItemStack(Items.FIREWORK_CHARGE);
/*  86 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  87 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  88 */         byte b0 = 0;
/*  89 */         ArrayList arraylist = Lists.newArrayList();
/*     */         
/*  91 */         for (int i2 = 0; i2 < inventorycrafting.getSize(); i2++) {
/*  92 */           ItemStack itemstack2 = inventorycrafting.getItem(i2);
/*     */           
/*  94 */           if (itemstack2 != null) {
/*  95 */             if (itemstack2.getItem() == Items.DYE) {
/*  96 */               arraylist.add(Integer.valueOf(ItemDye.a[(itemstack2.getData() & 0xF)]));
/*  97 */             } else if (itemstack2.getItem() == Items.GLOWSTONE_DUST) {
/*  98 */               nbttagcompound1.setBoolean("Flicker", true);
/*  99 */             } else if (itemstack2.getItem() == Items.DIAMOND) {
/* 100 */               nbttagcompound1.setBoolean("Trail", true);
/* 101 */             } else if (itemstack2.getItem() == Items.FIRE_CHARGE) {
/* 102 */               b0 = 1;
/* 103 */             } else if (itemstack2.getItem() == Items.FEATHER) {
/* 104 */               b0 = 4;
/* 105 */             } else if (itemstack2.getItem() == Items.GOLD_NUGGET) {
/* 106 */               b0 = 2;
/* 107 */             } else if (itemstack2.getItem() == Items.SKULL) {
/* 108 */               b0 = 3;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 113 */         int[] aint = new int[arraylist.size()];
/*     */         
/* 115 */         for (int j2 = 0; j2 < aint.length; j2++) {
/* 116 */           aint[j2] = ((Integer)arraylist.get(j2)).intValue();
/*     */         }
/*     */         
/* 119 */         nbttagcompound1.setIntArray("Colors", aint);
/* 120 */         nbttagcompound1.setByte("Type", b0);
/* 121 */         nbttagcompound.set("Explosion", nbttagcompound1);
/* 122 */         this.a.setTag(nbttagcompound);
/* 123 */         return true; }
/* 124 */       if ((j == 0) && (i == 0) && (l == 1) && (k > 0) && (k == i1)) {
/* 125 */         ArrayList arraylist1 = Lists.newArrayList();
/*     */         
/* 127 */         for (int k2 = 0; k2 < inventorycrafting.getSize(); k2++) {
/* 128 */           ItemStack itemstack3 = inventorycrafting.getItem(k2);
/*     */           
/* 130 */           if (itemstack3 != null) {
/* 131 */             if (itemstack3.getItem() == Items.DYE) {
/* 132 */               arraylist1.add(Integer.valueOf(ItemDye.a[(itemstack3.getData() & 0xF)]));
/* 133 */             } else if (itemstack3.getItem() == Items.FIREWORK_CHARGE) {
/* 134 */               this.a = itemstack3.cloneItemStack();
/* 135 */               this.a.count = 1;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 140 */         int[] aint1 = new int[arraylist1.size()];
/*     */         
/* 142 */         for (int l2 = 0; l2 < aint1.length; l2++) {
/* 143 */           aint1[l2] = ((Integer)arraylist1.get(l2)).intValue();
/*     */         }
/*     */         
/* 146 */         if ((this.a != null) && (this.a.hasTag())) {
/* 147 */           NBTTagCompound nbttagcompound2 = this.a.getTag().getCompound("Explosion");
/*     */           
/* 149 */           if (nbttagcompound2 == null) {
/* 150 */             return false;
/*     */           }
/* 152 */           nbttagcompound2.setIntArray("FadeColors", aint1);
/* 153 */           return true;
/*     */         }
/*     */         
/* 156 */         return false;
/*     */       }
/*     */       
/* 159 */       return false;
/*     */     }
/*     */     
/* 162 */     return false;
/*     */   }
/*     */   
/*     */   public ItemStack craftItem(InventoryCrafting inventorycrafting)
/*     */   {
/* 167 */     return this.a.cloneItemStack();
/*     */   }
/*     */   
/*     */   public int a() {
/* 171 */     return 10;
/*     */   }
/*     */   
/*     */   public ItemStack b() {
/* 175 */     return this.a;
/*     */   }
/*     */   
/*     */   public ItemStack[] b(InventoryCrafting inventorycrafting) {
/* 179 */     ItemStack[] aitemstack = new ItemStack[inventorycrafting.getSize()];
/*     */     
/* 181 */     for (int i = 0; i < aitemstack.length; i++) {
/* 182 */       ItemStack itemstack = inventorycrafting.getItem(i);
/*     */       
/* 184 */       if ((itemstack != null) && (itemstack.getItem().r())) {
/* 185 */         aitemstack[i] = new ItemStack(itemstack.getItem().q());
/*     */       }
/*     */     }
/*     */     
/* 189 */     return aitemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RecipeFireworks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */