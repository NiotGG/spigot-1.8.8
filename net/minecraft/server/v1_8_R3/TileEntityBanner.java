/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ public class TileEntityBanner
/*     */   extends TileEntity
/*     */ {
/*     */   public int color;
/*     */   public NBTTagList patterns;
/*     */   private boolean g;
/*     */   private List<EnumBannerPatternType> h;
/*     */   private List<EnumColor> i;
/*     */   private String j;
/*     */   
/*     */   public void a(ItemStack itemstack)
/*     */   {
/*  17 */     this.patterns = null;
/*  18 */     if ((itemstack.hasTag()) && (itemstack.getTag().hasKeyOfType("BlockEntityTag", 10))) {
/*  19 */       NBTTagCompound nbttagcompound = itemstack.getTag().getCompound("BlockEntityTag");
/*     */       
/*  21 */       if (nbttagcompound.hasKey("Patterns")) {
/*  22 */         this.patterns = ((NBTTagList)nbttagcompound.getList("Patterns", 10).clone());
/*     */         
/*  24 */         while (this.patterns.size() > 20) {
/*  25 */           this.patterns.a(20);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  30 */       if (nbttagcompound.hasKeyOfType("Base", 99)) {
/*  31 */         this.color = nbttagcompound.getInt("Base");
/*     */       } else {
/*  33 */         this.color = (itemstack.getData() & 0xF);
/*     */       }
/*     */     } else {
/*  36 */       this.color = (itemstack.getData() & 0xF);
/*     */     }
/*     */     
/*  39 */     this.h = null;
/*  40 */     this.i = null;
/*  41 */     this.j = "";
/*  42 */     this.g = true;
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  46 */     super.b(nbttagcompound);
/*  47 */     a(nbttagcompound, this.color, this.patterns);
/*     */   }
/*     */   
/*     */   public static void a(NBTTagCompound nbttagcompound, int i, NBTTagList nbttaglist) {
/*  51 */     nbttagcompound.setInt("Base", i);
/*  52 */     if (nbttaglist != null) {
/*  53 */       nbttagcompound.set("Patterns", nbttaglist);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound)
/*     */   {
/*  59 */     super.a(nbttagcompound);
/*  60 */     this.color = nbttagcompound.getInt("Base");
/*  61 */     this.patterns = nbttagcompound.getList("Patterns", 10);
/*     */     
/*  63 */     while (this.patterns.size() > 20) {
/*  64 */       this.patterns.a(20);
/*     */     }
/*     */     
/*  67 */     this.h = null;
/*  68 */     this.i = null;
/*  69 */     this.j = null;
/*  70 */     this.g = true;
/*     */   }
/*     */   
/*     */   public Packet getUpdatePacket() {
/*  74 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/*  76 */     b(nbttagcompound);
/*  77 */     return new PacketPlayOutTileEntityData(this.position, 6, nbttagcompound);
/*     */   }
/*     */   
/*     */   public int b() {
/*  81 */     return this.color;
/*     */   }
/*     */   
/*     */   public static int b(ItemStack itemstack) {
/*  85 */     NBTTagCompound nbttagcompound = itemstack.a("BlockEntityTag", false);
/*     */     
/*  87 */     return (nbttagcompound != null) && (nbttagcompound.hasKey("Base")) ? nbttagcompound.getInt("Base") : itemstack.getData();
/*     */   }
/*     */   
/*     */   public static int c(ItemStack itemstack) {
/*  91 */     NBTTagCompound nbttagcompound = itemstack.a("BlockEntityTag", false);
/*     */     
/*  93 */     return (nbttagcompound != null) && (nbttagcompound.hasKey("Patterns")) ? nbttagcompound.getList("Patterns", 10).size() : 0;
/*     */   }
/*     */   
/*     */   public NBTTagList d() {
/*  97 */     return this.patterns;
/*     */   }
/*     */   
/*     */   public static void e(ItemStack itemstack) {
/* 101 */     NBTTagCompound nbttagcompound = itemstack.a("BlockEntityTag", false);
/*     */     
/* 103 */     if ((nbttagcompound != null) && (nbttagcompound.hasKeyOfType("Patterns", 9))) {
/* 104 */       NBTTagList nbttaglist = nbttagcompound.getList("Patterns", 10);
/*     */       
/* 106 */       if (nbttaglist.size() > 0) {
/* 107 */         nbttaglist.a(nbttaglist.size() - 1);
/* 108 */         if (nbttaglist.isEmpty()) {
/* 109 */           itemstack.getTag().remove("BlockEntityTag");
/* 110 */           if (itemstack.getTag().isEmpty()) {
/* 111 */             itemstack.setTag(null);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum EnumBannerPatternType
/*     */   {
/* 121 */     BASE("base", "b"),  SQUARE_BOTTOM_LEFT("square_bottom_left", "bl", "   ", "   ", "#  "),  SQUARE_BOTTOM_RIGHT("square_bottom_right", "br", "   ", "   ", "  #"),  SQUARE_TOP_LEFT("square_top_left", "tl", "#  ", "   ", "   "),  SQUARE_TOP_RIGHT("square_top_right", "tr", "  #", "   ", "   "),  STRIPE_BOTTOM("stripe_bottom", "bs", "   ", "   ", "###"),  STRIPE_TOP("stripe_top", "ts", "###", "   ", "   "),  STRIPE_LEFT("stripe_left", "ls", "#  ", "#  ", "#  "),  STRIPE_RIGHT("stripe_right", "rs", "  #", "  #", "  #"),  STRIPE_CENTER("stripe_center", "cs", " # ", " # ", " # "),  STRIPE_MIDDLE("stripe_middle", "ms", "   ", "###", "   "),  STRIPE_DOWNRIGHT("stripe_downright", "drs", "#  ", " # ", "  #"),  STRIPE_DOWNLEFT("stripe_downleft", "dls", "  #", " # ", "#  "),  STRIPE_SMALL("small_stripes", "ss", "# #", "# #", "   "),  CROSS("cross", "cr", "# #", " # ", "# #"),  STRAIGHT_CROSS("straight_cross", "sc", " # ", "###", " # "),  TRIANGLE_BOTTOM("triangle_bottom", "bt", "   ", " # ", "# #"),  TRIANGLE_TOP("triangle_top", "tt", "# #", " # ", "   "),  TRIANGLES_BOTTOM("triangles_bottom", "bts", "   ", "# #", " # "),  TRIANGLES_TOP("triangles_top", "tts", " # ", "# #", "   "),  DIAGONAL_LEFT("diagonal_left", "ld", "## ", "#  ", "   "),  DIAGONAL_RIGHT("diagonal_up_right", "rd", "   ", "  #", " ##"),  DIAGONAL_LEFT_MIRROR("diagonal_up_left", "lud", "   ", "#  ", "## "),  DIAGONAL_RIGHT_MIRROR("diagonal_right", "rud", " ##", "  #", "   "),  CIRCLE_MIDDLE("circle", "mc", "   ", " # ", "   "),  RHOMBUS_MIDDLE("rhombus", "mr", " # ", "# #", " # "),  HALF_VERTICAL("half_vertical", "vh", "## ", "## ", "## "),  HALF_HORIZONTAL("half_horizontal", "hh", "###", "###", "   "),  HALF_VERTICAL_MIRROR("half_vertical_right", "vhr", " ##", " ##", " ##"),  HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "hhb", "   ", "###", "###"),  BORDER("border", "bo", "###", "# #", "###"),  CURLY_BORDER("curly_border", "cbo", new ItemStack(Blocks.VINE)),  CREEPER("creeper", "cre", new ItemStack(Items.SKULL, 1, 4)),  GRADIENT("gradient", "gra", "# #", " # ", " # "),  GRADIENT_UP("gradient_up", "gru", " # ", " # ", "# #"),  BRICKS("bricks", "bri", new ItemStack(Blocks.BRICK_BLOCK)),  SKULL("skull", "sku", new ItemStack(Items.SKULL, 1, 1)),  FLOWER("flower", "flo", new ItemStack(Blocks.RED_FLOWER, 1, BlockFlowers.EnumFlowerVarient.OXEYE_DAISY.b())),  MOJANG("mojang", "moj", new ItemStack(Items.GOLDEN_APPLE, 1, 1));
/*     */     
/*     */     private String N;
/*     */     private String O;
/*     */     private String[] P;
/*     */     private ItemStack Q;
/*     */     
/*     */     private EnumBannerPatternType(String s, String s1) {
/* 129 */       this.P = new String[3];
/* 130 */       this.N = s;
/* 131 */       this.O = s1;
/*     */     }
/*     */     
/*     */     private EnumBannerPatternType(String s, String s1, ItemStack itemstack) {
/* 135 */       this(s, s1);
/* 136 */       this.Q = itemstack;
/*     */     }
/*     */     
/*     */     private EnumBannerPatternType(String s, String s1, String s2, String s3, String s4) {
/* 140 */       this(s, s1);
/* 141 */       this.P[0] = s2;
/* 142 */       this.P[1] = s3;
/* 143 */       this.P[2] = s4;
/*     */     }
/*     */     
/*     */     public String b() {
/* 147 */       return this.O;
/*     */     }
/*     */     
/*     */     public String[] c() {
/* 151 */       return this.P;
/*     */     }
/*     */     
/*     */     public boolean d() {
/* 155 */       return (this.Q != null) || (this.P[0] != null);
/*     */     }
/*     */     
/*     */     public boolean e() {
/* 159 */       return this.Q != null;
/*     */     }
/*     */     
/*     */     public ItemStack f() {
/* 163 */       return this.Q;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityBanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */