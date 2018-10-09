/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class RecipesFurnace
/*     */ {
/*  10 */   private static final RecipesFurnace a = new RecipesFurnace();
/*  11 */   public Map<ItemStack, ItemStack> recipes = Maps.newHashMap();
/*  12 */   private Map<ItemStack, Float> c = Maps.newHashMap();
/*  13 */   public Map customRecipes = Maps.newHashMap();
/*     */   
/*     */   public static RecipesFurnace getInstance() {
/*  16 */     return a;
/*     */   }
/*     */   
/*     */   public RecipesFurnace() {
/*  20 */     registerRecipe(Blocks.IRON_ORE, new ItemStack(Items.IRON_INGOT), 0.7F);
/*  21 */     registerRecipe(Blocks.GOLD_ORE, new ItemStack(Items.GOLD_INGOT), 1.0F);
/*  22 */     registerRecipe(Blocks.DIAMOND_ORE, new ItemStack(Items.DIAMOND), 1.0F);
/*  23 */     registerRecipe(Blocks.SAND, new ItemStack(Blocks.GLASS), 0.1F);
/*  24 */     a(Items.PORKCHOP, new ItemStack(Items.COOKED_PORKCHOP), 0.35F);
/*  25 */     a(Items.BEEF, new ItemStack(Items.COOKED_BEEF), 0.35F);
/*  26 */     a(Items.CHICKEN, new ItemStack(Items.COOKED_CHICKEN), 0.35F);
/*  27 */     a(Items.RABBIT, new ItemStack(Items.COOKED_RABBIT), 0.35F);
/*  28 */     a(Items.MUTTON, new ItemStack(Items.COOKED_MUTTON), 0.35F);
/*  29 */     registerRecipe(Blocks.COBBLESTONE, new ItemStack(Blocks.STONE), 0.1F);
/*  30 */     a(new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.b), new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.O), 0.1F);
/*  31 */     a(Items.CLAY_BALL, new ItemStack(Items.BRICK), 0.3F);
/*  32 */     registerRecipe(Blocks.CLAY, new ItemStack(Blocks.HARDENED_CLAY), 0.35F);
/*  33 */     registerRecipe(Blocks.CACTUS, new ItemStack(Items.DYE, 1, EnumColor.GREEN.getInvColorIndex()), 0.2F);
/*  34 */     registerRecipe(Blocks.LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
/*  35 */     registerRecipe(Blocks.LOG2, new ItemStack(Items.COAL, 1, 1), 0.15F);
/*  36 */     registerRecipe(Blocks.EMERALD_ORE, new ItemStack(Items.EMERALD), 1.0F);
/*  37 */     a(Items.POTATO, new ItemStack(Items.BAKED_POTATO), 0.35F);
/*  38 */     registerRecipe(Blocks.NETHERRACK, new ItemStack(Items.NETHERBRICK), 0.1F);
/*  39 */     a(new ItemStack(Blocks.SPONGE, 1, 1), new ItemStack(Blocks.SPONGE, 1, 0), 0.15F);
/*  40 */     ItemFish.EnumFish[] aitemfish_enumfish = ItemFish.EnumFish.values();
/*  41 */     int i = aitemfish_enumfish.length;
/*     */     
/*  43 */     for (int j = 0; j < i; j++) {
/*  44 */       ItemFish.EnumFish itemfish_enumfish = aitemfish_enumfish[j];
/*     */       
/*  46 */       if (itemfish_enumfish.g()) {
/*  47 */         a(new ItemStack(Items.FISH, 1, itemfish_enumfish.a()), new ItemStack(Items.COOKED_FISH, 1, itemfish_enumfish.a()), 0.35F);
/*     */       }
/*     */     }
/*     */     
/*  51 */     registerRecipe(Blocks.COAL_ORE, new ItemStack(Items.COAL), 0.1F);
/*  52 */     registerRecipe(Blocks.REDSTONE_ORE, new ItemStack(Items.REDSTONE), 0.7F);
/*  53 */     registerRecipe(Blocks.LAPIS_ORE, new ItemStack(Items.DYE, 1, EnumColor.BLUE.getInvColorIndex()), 0.2F);
/*  54 */     registerRecipe(Blocks.QUARTZ_ORE, new ItemStack(Items.QUARTZ), 0.2F);
/*     */   }
/*     */   
/*     */   public void registerRecipe(ItemStack itemstack, ItemStack itemstack1)
/*     */   {
/*  59 */     this.customRecipes.put(itemstack, itemstack1);
/*     */   }
/*     */   
/*     */   public void registerRecipe(Block block, ItemStack itemstack, float f)
/*     */   {
/*  64 */     a(Item.getItemOf(block), itemstack, f);
/*     */   }
/*     */   
/*     */   public void a(Item item, ItemStack itemstack, float f) {
/*  68 */     a(new ItemStack(item, 1, 32767), itemstack, f);
/*     */   }
/*     */   
/*     */   public void a(ItemStack itemstack, ItemStack itemstack1, float f) {
/*  72 */     this.recipes.put(itemstack, itemstack1);
/*  73 */     this.c.put(itemstack1, Float.valueOf(f));
/*     */   }
/*     */   
/*     */   public ItemStack getResult(ItemStack itemstack)
/*     */   {
/*  78 */     boolean vanilla = false;
/*  79 */     Iterator iterator = this.customRecipes.entrySet().iterator();
/*     */     
/*     */     Map.Entry entry;
/*     */     
/*     */     do
/*     */     {
/*  85 */       if (!iterator.hasNext())
/*     */       {
/*  87 */         if ((!vanilla) && (!this.recipes.isEmpty())) {
/*  88 */           iterator = this.recipes.entrySet().iterator();
/*  89 */           vanilla = true;
/*     */         } else {
/*  91 */           return null;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  96 */       entry = (Map.Entry)iterator.next();
/*  97 */     } while (!a(itemstack, (ItemStack)entry.getKey()));
/*     */     
/*  99 */     return (ItemStack)entry.getValue();
/*     */   }
/*     */   
/*     */   private boolean a(ItemStack itemstack, ItemStack itemstack1) {
/* 103 */     return (itemstack1.getItem() == itemstack.getItem()) && ((itemstack1.getData() == 32767) || (itemstack1.getData() == itemstack.getData()));
/*     */   }
/*     */   
/*     */   public Map<ItemStack, ItemStack> getRecipes() {
/* 107 */     return this.recipes;
/*     */   }
/*     */   
/*     */   public float b(ItemStack itemstack) {
/* 111 */     Iterator iterator = this.c.entrySet().iterator();
/*     */     
/*     */     Map.Entry entry;
/*     */     do
/*     */     {
/* 116 */       if (!iterator.hasNext()) {
/* 117 */         return 0.0F;
/*     */       }
/*     */       
/* 120 */       entry = (Map.Entry)iterator.next();
/* 121 */     } while (!a(itemstack, (ItemStack)entry.getKey()));
/*     */     
/* 123 */     return ((Float)entry.getValue()).floatValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RecipesFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */