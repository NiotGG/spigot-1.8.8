/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class RecipesBanner
/*     */ {
/*     */   void a(CraftingManager craftingmanager) {
/*   8 */     EnumColor[] aenumcolor = EnumColor.values();
/*   9 */     int i = aenumcolor.length;
/*     */     
/*  11 */     for (int j = 0; j < i; j++) {
/*  12 */       EnumColor enumcolor = aenumcolor[j];
/*     */       
/*  14 */       craftingmanager.registerShapedRecipe(new ItemStack(Items.BANNER, 1, enumcolor.getInvColorIndex()), new Object[] { "###", "###", " | ", Character.valueOf('#'), new ItemStack(Blocks.WOOL, 1, enumcolor.getColorIndex()), Character.valueOf('|'), Items.STICK });
/*     */     }
/*     */     
/*  17 */     craftingmanager.a(new DuplicateRecipe(null));
/*  18 */     craftingmanager.a(new AddRecipe(null));
/*     */   }
/*     */   
/*     */   static class AddRecipe
/*     */     extends ShapelessRecipes
/*     */     implements IRecipe
/*     */   {
/*     */     private AddRecipe()
/*     */     {
/*  27 */       super(Arrays.asList(new ItemStack[] { new ItemStack(Items.BANNER) }));
/*     */     }
/*     */     
/*     */     public boolean a(InventoryCrafting inventorycrafting, World world)
/*     */     {
/*  32 */       boolean flag = false;
/*     */       
/*  34 */       for (int i = 0; i < inventorycrafting.getSize(); i++) {
/*  35 */         ItemStack itemstack = inventorycrafting.getItem(i);
/*     */         
/*  37 */         if ((itemstack != null) && (itemstack.getItem() == Items.BANNER)) {
/*  38 */           if (flag) {
/*  39 */             return false;
/*     */           }
/*     */           
/*  42 */           if (TileEntityBanner.c(itemstack) >= 6) {
/*  43 */             return false;
/*     */           }
/*     */           
/*  46 */           flag = true;
/*     */         }
/*     */       }
/*     */       
/*  50 */       if (!flag) {
/*  51 */         return false;
/*     */       }
/*  53 */       return c(inventorycrafting) != null;
/*     */     }
/*     */     
/*     */     public ItemStack craftItem(InventoryCrafting inventorycrafting)
/*     */     {
/*  58 */       ItemStack itemstack = null;
/*     */       
/*  60 */       for (int i = 0; i < inventorycrafting.getSize(); i++) {
/*  61 */         ItemStack itemstack1 = inventorycrafting.getItem(i);
/*     */         
/*  63 */         if ((itemstack1 != null) && (itemstack1.getItem() == Items.BANNER)) {
/*  64 */           itemstack = itemstack1.cloneItemStack();
/*  65 */           itemstack.count = 1;
/*  66 */           break;
/*     */         }
/*     */       }
/*     */       
/*  70 */       TileEntityBanner.EnumBannerPatternType tileentitybanner_enumbannerpatterntype = c(inventorycrafting);
/*     */       
/*  72 */       if (tileentitybanner_enumbannerpatterntype != null) {
/*  73 */         int j = 0;
/*     */         
/*     */ 
/*     */ 
/*  77 */         for (int k = 0; k < inventorycrafting.getSize(); k++) {
/*  78 */           ItemStack itemstack2 = inventorycrafting.getItem(k);
/*  79 */           if ((itemstack2 != null) && (itemstack2.getItem() == Items.DYE)) {
/*  80 */             j = itemstack2.getData();
/*  81 */             break;
/*     */           }
/*     */         }
/*     */         
/*  85 */         NBTTagCompound nbttagcompound = itemstack.a("BlockEntityTag", true);
/*     */         
/*  87 */         ItemStack itemstack2 = null;
/*     */         NBTTagList nbttaglist;
/*     */         NBTTagList nbttaglist;
/*  90 */         if (nbttagcompound.hasKeyOfType("Patterns", 9)) {
/*  91 */           nbttaglist = nbttagcompound.getList("Patterns", 10);
/*     */         } else {
/*  93 */           nbttaglist = new NBTTagList();
/*  94 */           nbttagcompound.set("Patterns", nbttaglist);
/*     */         }
/*     */         
/*  97 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/*  99 */         nbttagcompound1.setString("Pattern", tileentitybanner_enumbannerpatterntype.b());
/* 100 */         nbttagcompound1.setInt("Color", j);
/* 101 */         nbttaglist.add(nbttagcompound1);
/*     */       }
/*     */       
/* 104 */       return itemstack;
/*     */     }
/*     */     
/*     */     public int a() {
/* 108 */       return 10;
/*     */     }
/*     */     
/*     */     public ItemStack b() {
/* 112 */       return null;
/*     */     }
/*     */     
/*     */     public ItemStack[] b(InventoryCrafting inventorycrafting) {
/* 116 */       ItemStack[] aitemstack = new ItemStack[inventorycrafting.getSize()];
/*     */       
/* 118 */       for (int i = 0; i < aitemstack.length; i++) {
/* 119 */         ItemStack itemstack = inventorycrafting.getItem(i);
/*     */         
/* 121 */         if ((itemstack != null) && (itemstack.getItem().r())) {
/* 122 */           aitemstack[i] = new ItemStack(itemstack.getItem().q());
/*     */         }
/*     */       }
/*     */       
/* 126 */       return aitemstack;
/*     */     }
/*     */     
/*     */     private TileEntityBanner.EnumBannerPatternType c(InventoryCrafting inventorycrafting) {
/* 130 */       TileEntityBanner.EnumBannerPatternType[] atileentitybanner_enumbannerpatterntype = TileEntityBanner.EnumBannerPatternType.values();
/* 131 */       int i = atileentitybanner_enumbannerpatterntype.length;
/*     */       
/* 133 */       for (int j = 0; j < i; j++) {
/* 134 */         TileEntityBanner.EnumBannerPatternType tileentitybanner_enumbannerpatterntype = atileentitybanner_enumbannerpatterntype[j];
/*     */         
/* 136 */         if (tileentitybanner_enumbannerpatterntype.d()) {
/* 137 */           boolean flag = true;
/*     */           
/*     */ 
/* 140 */           if (tileentitybanner_enumbannerpatterntype.e()) {
/* 141 */             boolean flag1 = false;
/* 142 */             boolean flag2 = false;
/*     */             
/* 144 */             for (int k = 0; (k < inventorycrafting.getSize()) && (flag); k++) {
/* 145 */               ItemStack itemstack = inventorycrafting.getItem(k);
/*     */               
/* 147 */               if ((itemstack != null) && (itemstack.getItem() != Items.BANNER)) {
/* 148 */                 if (itemstack.getItem() == Items.DYE) {
/* 149 */                   if (flag2) {
/* 150 */                     flag = false;
/* 151 */                     break;
/*     */                   }
/*     */                   
/* 154 */                   flag2 = true;
/*     */                 } else {
/* 156 */                   if ((flag1) || (!itemstack.doMaterialsMatch(tileentitybanner_enumbannerpatterntype.f()))) {
/* 157 */                     flag = false;
/* 158 */                     break;
/*     */                   }
/*     */                   
/* 161 */                   flag1 = true;
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/* 166 */             if (!flag1) {
/* 167 */               flag = false;
/*     */             }
/* 169 */           } else if (inventorycrafting.getSize() != tileentitybanner_enumbannerpatterntype.c().length * tileentitybanner_enumbannerpatterntype.c()[0].length()) {
/* 170 */             flag = false;
/*     */           } else {
/* 172 */             int l = -1;
/*     */             
/* 174 */             for (int i1 = 0; (i1 < inventorycrafting.getSize()) && (flag); i1++) {
/* 175 */               int k = i1 / 3;
/* 176 */               int j1 = i1 % 3;
/* 177 */               ItemStack itemstack1 = inventorycrafting.getItem(i1);
/*     */               
/* 179 */               if ((itemstack1 != null) && (itemstack1.getItem() != Items.BANNER)) {
/* 180 */                 if (itemstack1.getItem() != Items.DYE) {
/* 181 */                   flag = false;
/* 182 */                   break;
/*     */                 }
/*     */                 
/* 185 */                 if ((l != -1) && (l != itemstack1.getData())) {
/* 186 */                   flag = false;
/* 187 */                   break;
/*     */                 }
/*     */                 
/* 190 */                 if (tileentitybanner_enumbannerpatterntype.c()[k].charAt(j1) == ' ') {
/* 191 */                   flag = false;
/* 192 */                   break;
/*     */                 }
/*     */                 
/* 195 */                 l = itemstack1.getData();
/* 196 */               } else if (tileentitybanner_enumbannerpatterntype.c()[k].charAt(j1) != ' ') {
/* 197 */                 flag = false;
/* 198 */                 break;
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 203 */           if (flag) {
/* 204 */             return tileentitybanner_enumbannerpatterntype;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 209 */       return null;
/*     */     }
/*     */     
/*     */     AddRecipe(RecipesBanner.SyntheticClass_1 recipesbanner_syntheticclass_1) {
/* 213 */       this();
/*     */     }
/*     */   }
/*     */   
/*     */   static class DuplicateRecipe extends ShapelessRecipes implements IRecipe
/*     */   {
/*     */     private DuplicateRecipe()
/*     */     {
/* 221 */       super(Arrays.asList(new ItemStack[] { new ItemStack(Items.DYE, 0, 5) }));
/*     */     }
/*     */     
/*     */     public boolean a(InventoryCrafting inventorycrafting, World world)
/*     */     {
/* 226 */       ItemStack itemstack = null;
/* 227 */       ItemStack itemstack1 = null;
/*     */       
/* 229 */       for (int i = 0; i < inventorycrafting.getSize(); i++) {
/* 230 */         ItemStack itemstack2 = inventorycrafting.getItem(i);
/*     */         
/* 232 */         if (itemstack2 != null) {
/* 233 */           if (itemstack2.getItem() != Items.BANNER) {
/* 234 */             return false;
/*     */           }
/*     */           
/* 237 */           if ((itemstack != null) && (itemstack1 != null)) {
/* 238 */             return false;
/*     */           }
/*     */           
/* 241 */           int j = TileEntityBanner.b(itemstack2);
/* 242 */           boolean flag = TileEntityBanner.c(itemstack2) > 0;
/*     */           
/* 244 */           if (itemstack != null) {
/* 245 */             if (flag) {
/* 246 */               return false;
/*     */             }
/*     */             
/* 249 */             if (j != TileEntityBanner.b(itemstack)) {
/* 250 */               return false;
/*     */             }
/*     */             
/* 253 */             itemstack1 = itemstack2;
/* 254 */           } else if (itemstack1 != null) {
/* 255 */             if (!flag) {
/* 256 */               return false;
/*     */             }
/*     */             
/* 259 */             if (j != TileEntityBanner.b(itemstack1)) {
/* 260 */               return false;
/*     */             }
/*     */             
/* 263 */             itemstack = itemstack2;
/* 264 */           } else if (flag) {
/* 265 */             itemstack = itemstack2;
/*     */           } else {
/* 267 */             itemstack1 = itemstack2;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 272 */       return (itemstack != null) && (itemstack1 != null);
/*     */     }
/*     */     
/*     */     public ItemStack craftItem(InventoryCrafting inventorycrafting) {
/* 276 */       for (int i = 0; i < inventorycrafting.getSize(); i++) {
/* 277 */         ItemStack itemstack = inventorycrafting.getItem(i);
/*     */         
/* 279 */         if ((itemstack != null) && (TileEntityBanner.c(itemstack) > 0)) {
/* 280 */           ItemStack itemstack1 = itemstack.cloneItemStack();
/*     */           
/* 282 */           itemstack1.count = 1;
/* 283 */           return itemstack1;
/*     */         }
/*     */       }
/*     */       
/* 287 */       return null;
/*     */     }
/*     */     
/*     */     public int a() {
/* 291 */       return 2;
/*     */     }
/*     */     
/*     */     public ItemStack b() {
/* 295 */       return null;
/*     */     }
/*     */     
/*     */     public ItemStack[] b(InventoryCrafting inventorycrafting) {
/* 299 */       ItemStack[] aitemstack = new ItemStack[inventorycrafting.getSize()];
/*     */       
/* 301 */       for (int i = 0; i < aitemstack.length; i++) {
/* 302 */         ItemStack itemstack = inventorycrafting.getItem(i);
/*     */         
/* 304 */         if (itemstack != null) {
/* 305 */           if (itemstack.getItem().r()) {
/* 306 */             aitemstack[i] = new ItemStack(itemstack.getItem().q());
/* 307 */           } else if ((itemstack.hasTag()) && (TileEntityBanner.c(itemstack) > 0)) {
/* 308 */             aitemstack[i] = itemstack.cloneItemStack();
/* 309 */             aitemstack[i].count = 1;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 314 */       return aitemstack;
/*     */     }
/*     */     
/*     */     DuplicateRecipe(RecipesBanner.SyntheticClass_1 recipesbanner_syntheticclass_1) {
/* 318 */       this();
/*     */     }
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1 {}
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RecipesBanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */