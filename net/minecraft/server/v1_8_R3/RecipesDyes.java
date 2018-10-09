/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecipesDyes
/*     */ {
/*     */   public void a(CraftingManager paramCraftingManager)
/*     */   {
/*  14 */     for (int i = 0; i < 16; i++) {
/*  15 */       paramCraftingManager.registerShapelessRecipe(new ItemStack(Blocks.WOOL, 1, i), new Object[] { new ItemStack(Items.DYE, 1, 15 - i), new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 0) });
/*     */       
/*     */ 
/*     */ 
/*  19 */       paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 8, 15 - i), new Object[] { "###", "#X#", "###", Character.valueOf('#'), new ItemStack(Blocks.HARDENED_CLAY), Character.valueOf('X'), new ItemStack(Items.DYE, 1, i) });
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  27 */       paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.STAINED_GLASS, 8, 15 - i), new Object[] { "###", "#X#", "###", Character.valueOf('#'), new ItemStack(Blocks.GLASS), Character.valueOf('X'), new ItemStack(Items.DYE, 1, i) });
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  35 */       paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.STAINED_GLASS_PANE, 16, i), new Object[] { "###", "###", Character.valueOf('#'), new ItemStack(Blocks.STAINED_GLASS, 1, i) });
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  44 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.YELLOW.getInvColorIndex()), new Object[] { new ItemStack(Blocks.YELLOW_FLOWER, 1, BlockFlowers.EnumFlowerVarient.DANDELION.b()) });
/*     */     
/*     */ 
/*  47 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()), new Object[] { new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.POPPY.b()) });
/*     */     
/*     */ 
/*  50 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 3, EnumColor.WHITE.getInvColorIndex()), new Object[] { Items.BONE });
/*     */     
/*     */ 
/*  53 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.PINK.getInvColorIndex()), new Object[] { new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex()) });
/*     */     
/*     */ 
/*     */ 
/*  57 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.ORANGE.getInvColorIndex()), new Object[] { new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.YELLOW.getInvColorIndex()) });
/*     */     
/*     */ 
/*     */ 
/*  61 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.LIME.getInvColorIndex()), new Object[] { new ItemStack(Items.DYE, 1, EnumColor.GREEN.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex()) });
/*     */     
/*     */ 
/*     */ 
/*  65 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.GRAY.getInvColorIndex()), new Object[] { new ItemStack(Items.DYE, 1, EnumColor.BLACK.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex()) });
/*     */     
/*     */ 
/*     */ 
/*  69 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.SILVER.getInvColorIndex()), new Object[] { new ItemStack(Items.DYE, 1, EnumColor.GRAY.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex()) });
/*     */     
/*     */ 
/*     */ 
/*  73 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 3, EnumColor.SILVER.getInvColorIndex()), new Object[] { new ItemStack(Items.DYE, 1, EnumColor.BLACK.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex()) });
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  78 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.LIGHT_BLUE.getInvColorIndex()), new Object[] { new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex()) });
/*     */     
/*     */ 
/*     */ 
/*  82 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.CYAN.getInvColorIndex()), new Object[] { new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.GREEN.getInvColorIndex()) });
/*     */     
/*     */ 
/*     */ 
/*  86 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.PURPLE.getInvColorIndex()), new Object[] { new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()) });
/*     */     
/*     */ 
/*     */ 
/*  90 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.MAGENTA.getInvColorIndex()), new Object[] { new ItemStack(Items.DYE, 1, EnumColor.PURPLE.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.PINK.getInvColorIndex()) });
/*     */     
/*     */ 
/*     */ 
/*  94 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 3, EnumColor.MAGENTA.getInvColorIndex()), new Object[] { new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.PINK.getInvColorIndex()) });
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  99 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 4, EnumColor.MAGENTA.getInvColorIndex()), new Object[] { new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()), new ItemStack(Items.DYE, 1, EnumColor.WHITE.getInvColorIndex()) });
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 107 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.LIGHT_BLUE.getInvColorIndex()), new Object[] { new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.BLUE_ORCHID.b()) });
/*     */     
/*     */ 
/* 110 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.MAGENTA.getInvColorIndex()), new Object[] { new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.ALLIUM.b()) });
/*     */     
/*     */ 
/* 113 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.SILVER.getInvColorIndex()), new Object[] { new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.HOUSTONIA.b()) });
/*     */     
/*     */ 
/* 116 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.RED.getInvColorIndex()), new Object[] { new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.RED_TULIP.b()) });
/*     */     
/*     */ 
/* 119 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.ORANGE.getInvColorIndex()), new Object[] { new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.ORANGE_TULIP.b()) });
/*     */     
/*     */ 
/* 122 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.SILVER.getInvColorIndex()), new Object[] { new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.WHITE_TULIP.b()) });
/*     */     
/*     */ 
/* 125 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.PINK.getInvColorIndex()), new Object[] { new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.PINK_TULIP.b()) });
/*     */     
/*     */ 
/* 128 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 1, EnumColor.SILVER.getInvColorIndex()), new Object[] { new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.OXEYE_DAISY.b()) });
/*     */     
/*     */ 
/* 131 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.YELLOW.getInvColorIndex()), new Object[] { new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockTallPlant.EnumTallFlowerVariants.SUNFLOWER.a()) });
/*     */     
/*     */ 
/* 134 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.MAGENTA.getInvColorIndex()), new Object[] { new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockTallPlant.EnumTallFlowerVariants.SYRINGA.a()) });
/*     */     
/*     */ 
/* 137 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.RED.getInvColorIndex()), new Object[] { new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockTallPlant.EnumTallFlowerVariants.ROSE.a()) });
/*     */     
/*     */ 
/* 140 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.DYE, 2, EnumColor.PINK.getInvColorIndex()), new Object[] { new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockTallPlant.EnumTallFlowerVariants.PAEONIA.a()) });
/*     */     
/*     */ 
/*     */ 
/* 144 */     for (i = 0; i < 16; i++) {
/* 145 */       paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.CARPET, 3, i), new Object[] { "##", Character.valueOf('#'), new ItemStack(Blocks.WOOL, 1, i) });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RecipesDyes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */