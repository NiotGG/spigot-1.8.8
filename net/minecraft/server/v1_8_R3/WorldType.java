/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class WorldType {
/*  4 */   public static final WorldType[] types = new WorldType[16];
/*    */   
/*  6 */   public static final WorldType NORMAL = new WorldType(0, "default", 1).i();
/*  7 */   public static final WorldType FLAT = new WorldType(1, "flat");
/*  8 */   public static final WorldType LARGE_BIOMES = new WorldType(2, "largeBiomes");
/*  9 */   public static final WorldType AMPLIFIED = new WorldType(3, "amplified").j();
/* 10 */   public static final WorldType CUSTOMIZED = new WorldType(4, "customized");
/* 11 */   public static final WorldType DEBUG_ALL_BLOCK_STATES = new WorldType(5, "debug_all_block_states");
/*    */   
/* 13 */   public static final WorldType NORMAL_1_1 = new WorldType(8, "default_1_1", 0).a(false);
/*    */   private final int i;
/*    */   private final String name;
/*    */   private final int version;
/*    */   private boolean l;
/*    */   private boolean m;
/*    */   private boolean n;
/*    */   
/*    */   private WorldType(int paramInt, String paramString)
/*    */   {
/* 23 */     this(paramInt, paramString, 0);
/*    */   }
/*    */   
/*    */   private WorldType(int paramInt1, String paramString, int paramInt2) {
/* 27 */     this.name = paramString;
/* 28 */     this.version = paramInt2;
/* 29 */     this.l = true;
/* 30 */     this.i = paramInt1;
/* 31 */     types[paramInt1] = this;
/*    */   }
/*    */   
/*    */   public String name() {
/* 35 */     return this.name;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getVersion()
/*    */   {
/* 47 */     return this.version;
/*    */   }
/*    */   
/*    */   public WorldType a(int paramInt) {
/* 51 */     if ((this == NORMAL) && (paramInt == 0)) {
/* 52 */       return NORMAL_1_1;
/*    */     }
/* 54 */     return this;
/*    */   }
/*    */   
/*    */   private WorldType a(boolean paramBoolean) {
/* 58 */     this.l = paramBoolean;
/* 59 */     return this;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private WorldType i()
/*    */   {
/* 67 */     this.m = true;
/* 68 */     return this;
/*    */   }
/*    */   
/*    */   public boolean f() {
/* 72 */     return this.m;
/*    */   }
/*    */   
/*    */   public static WorldType getType(String paramString) {
/* 76 */     for (int j = 0; j < types.length; j++) {
/* 77 */       if ((types[j] != null) && (types[j].name.equalsIgnoreCase(paramString))) {
/* 78 */         return types[j];
/*    */       }
/*    */     }
/* 81 */     return null;
/*    */   }
/*    */   
/*    */   public int g() {
/* 85 */     return this.i;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private WorldType j()
/*    */   {
/* 93 */     this.n = true;
/* 94 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */