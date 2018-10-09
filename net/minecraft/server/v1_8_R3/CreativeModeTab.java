/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CreativeModeTab
/*     */ {
/*  12 */   public static final CreativeModeTab[] a = new CreativeModeTab[12];
/*  13 */   public static final CreativeModeTab b = new CreativeModeTab(0, "buildingBlocks") {};
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  19 */   public static final CreativeModeTab c = new CreativeModeTab(1, "decorations") {};
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
/*  30 */   public static final CreativeModeTab d = new CreativeModeTab(2, "redstone") {};
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  36 */   public static final CreativeModeTab e = new CreativeModeTab(3, "transportation") {};
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  42 */   public static final CreativeModeTab f = new CreativeModeTab(4, "misc") {}.a(new EnchantmentSlotType[] { EnchantmentSlotType.ALL });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  48 */   public static final CreativeModeTab g = new CreativeModeTab(5, "search") {}.a("item_search.png");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  55 */   public static final CreativeModeTab h = new CreativeModeTab(6, "food") {};
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  61 */   public static final CreativeModeTab i = new CreativeModeTab(7, "tools") {}.a(new EnchantmentSlotType[] { EnchantmentSlotType.DIGGER, EnchantmentSlotType.FISHING_ROD, EnchantmentSlotType.BREAKABLE });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  67 */   public static final CreativeModeTab j = new CreativeModeTab(8, "combat") {}.a(new EnchantmentSlotType[] { EnchantmentSlotType.ARMOR, EnchantmentSlotType.ARMOR_FEET, EnchantmentSlotType.ARMOR_HEAD, EnchantmentSlotType.ARMOR_LEGS, EnchantmentSlotType.ARMOR_TORSO, EnchantmentSlotType.BOW, EnchantmentSlotType.WEAPON });
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  73 */   public static final CreativeModeTab k = new CreativeModeTab(9, "brewing") {};
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  79 */   public static final CreativeModeTab l = new CreativeModeTab(10, "materials") {};
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  85 */   public static final CreativeModeTab m = new CreativeModeTab(11, "inventory") {}.a("inventory.png").k().i();
/*     */   
/*     */ 
/*     */   private final int n;
/*     */   
/*     */ 
/*     */   private final String o;
/*     */   
/*     */ 
/*  94 */   private String p = "items.png";
/*  95 */   private boolean q = true;
/*  96 */   private boolean r = true;
/*     */   private EnchantmentSlotType[] s;
/*     */   
/*     */   public CreativeModeTab(int paramInt, String paramString)
/*     */   {
/* 101 */     this.n = paramInt;
/* 102 */     this.o = paramString;
/*     */     
/* 104 */     a[paramInt] = this;
/*     */   }
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
/*     */   public CreativeModeTab a(String paramString)
/*     */   {
/* 137 */     this.p = paramString;
/* 138 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CreativeModeTab i()
/*     */   {
/* 146 */     this.r = false;
/* 147 */     return this;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public CreativeModeTab k()
/*     */   {
/* 155 */     this.q = false;
/* 156 */     return this;
/*     */   }
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
/*     */   public CreativeModeTab a(EnchantmentSlotType... paramVarArgs)
/*     */   {
/* 172 */     this.s = paramVarArgs;
/* 173 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CreativeModeTab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */