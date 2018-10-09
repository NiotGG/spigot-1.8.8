/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class RecipeArmorDye extends ShapelessRecipes implements IRecipe
/*     */ {
/*     */   public RecipeArmorDye()
/*     */   {
/*  10 */     super(new ItemStack(Items.LEATHER_HELMET, 0, 0), Arrays.asList(new ItemStack[] { new ItemStack(Items.DYE, 0, 5) }));
/*     */   }
/*     */   
/*     */   public boolean a(InventoryCrafting inventorycrafting, World world)
/*     */   {
/*  15 */     ItemStack itemstack = null;
/*  16 */     ArrayList arraylist = com.google.common.collect.Lists.newArrayList();
/*     */     
/*  18 */     for (int i = 0; i < inventorycrafting.getSize(); i++) {
/*  19 */       ItemStack itemstack1 = inventorycrafting.getItem(i);
/*     */       
/*  21 */       if (itemstack1 != null) {
/*  22 */         if ((itemstack1.getItem() instanceof ItemArmor)) {
/*  23 */           ItemArmor itemarmor = (ItemArmor)itemstack1.getItem();
/*     */           
/*  25 */           if ((itemarmor.x_() != ItemArmor.EnumArmorMaterial.LEATHER) || (itemstack != null)) {
/*  26 */             return false;
/*     */           }
/*     */           
/*  29 */           itemstack = itemstack1;
/*     */         } else {
/*  31 */           if (itemstack1.getItem() != Items.DYE) {
/*  32 */             return false;
/*     */           }
/*     */           
/*  35 */           arraylist.add(itemstack1);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  40 */     return (itemstack != null) && (!arraylist.isEmpty());
/*     */   }
/*     */   
/*     */   public ItemStack craftItem(InventoryCrafting inventorycrafting) {
/*  44 */     ItemStack itemstack = null;
/*  45 */     int[] aint = new int[3];
/*  46 */     int i = 0;
/*  47 */     int j = 0;
/*  48 */     ItemArmor itemarmor = null;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  56 */     for (int k = 0; k < inventorycrafting.getSize(); k++) {
/*  57 */       ItemStack itemstack1 = inventorycrafting.getItem(k);
/*     */       
/*  59 */       if (itemstack1 != null) {
/*  60 */         if ((itemstack1.getItem() instanceof ItemArmor)) {
/*  61 */           itemarmor = (ItemArmor)itemstack1.getItem();
/*  62 */           if ((itemarmor.x_() != ItemArmor.EnumArmorMaterial.LEATHER) || (itemstack != null)) {
/*  63 */             return null;
/*     */           }
/*     */           
/*  66 */           itemstack = itemstack1.cloneItemStack();
/*  67 */           itemstack.count = 1;
/*  68 */           if (itemarmor.d_(itemstack1)) {
/*  69 */             int l = itemarmor.b(itemstack);
/*  70 */             float f = (l >> 16 & 0xFF) / 255.0F;
/*  71 */             float f1 = (l >> 8 & 0xFF) / 255.0F;
/*  72 */             float f2 = (l & 0xFF) / 255.0F;
/*     */             
/*  74 */             i = (int)(i + Math.max(f, Math.max(f1, f2)) * 255.0F);
/*  75 */             aint[0] = ((int)(aint[0] + f * 255.0F));
/*  76 */             aint[1] = ((int)(aint[1] + f1 * 255.0F));
/*  77 */             aint[2] = ((int)(aint[2] + f2 * 255.0F));
/*  78 */             j++;
/*     */           }
/*     */         } else {
/*  81 */           if (itemstack1.getItem() != Items.DYE) {
/*  82 */             return null;
/*     */           }
/*     */           
/*  85 */           float[] afloat = EntitySheep.a(EnumColor.fromInvColorIndex(itemstack1.getData()));
/*  86 */           int j1 = (int)(afloat[0] * 255.0F);
/*  87 */           int k1 = (int)(afloat[1] * 255.0F);
/*     */           
/*  89 */           int i1 = (int)(afloat[2] * 255.0F);
/*  90 */           i += Math.max(j1, Math.max(k1, i1));
/*  91 */           aint[0] += j1;
/*  92 */           aint[1] += k1;
/*  93 */           aint[2] += i1;
/*  94 */           j++;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  99 */     if (itemarmor == null) {
/* 100 */       return null;
/*     */     }
/* 102 */     k = aint[0] / j;
/* 103 */     int l1 = aint[1] / j;
/*     */     
/* 105 */     int l = aint[2] / j;
/* 106 */     float f = i / j;
/* 107 */     float f1 = Math.max(k, Math.max(l1, l));
/* 108 */     k = (int)(k * f / f1);
/* 109 */     l1 = (int)(l1 * f / f1);
/* 110 */     l = (int)(l * f / f1);
/* 111 */     int i1 = (k << 8) + l1;
/* 112 */     i1 = (i1 << 8) + l;
/* 113 */     itemarmor.b(itemstack, i1);
/* 114 */     return itemstack;
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/* 119 */     return 10;
/*     */   }
/*     */   
/*     */   public ItemStack b() {
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack[] b(InventoryCrafting inventorycrafting) {
/* 127 */     ItemStack[] aitemstack = new ItemStack[inventorycrafting.getSize()];
/*     */     
/* 129 */     for (int i = 0; i < aitemstack.length; i++) {
/* 130 */       ItemStack itemstack = inventorycrafting.getItem(i);
/*     */       
/* 132 */       if ((itemstack != null) && (itemstack.getItem().r())) {
/* 133 */         aitemstack[i] = new ItemStack(itemstack.getItem().q());
/*     */       }
/*     */     }
/*     */     
/* 137 */     return aitemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RecipeArmorDye.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */