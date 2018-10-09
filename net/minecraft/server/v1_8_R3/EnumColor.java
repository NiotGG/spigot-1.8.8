/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EnumColor
/*    */   implements INamable
/*    */ {
/*    */   private static final EnumColor[] q;
/*    */   
/*    */ 
/*    */   private static final EnumColor[] r;
/*    */   
/*    */ 
/*    */   private final int s;
/*    */   
/*    */ 
/*    */   private final int t;
/*    */   
/*    */ 
/*    */   private final String u;
/*    */   
/*    */ 
/*    */   private final String v;
/*    */   
/*    */ 
/*    */   private final MaterialMapColor w;
/*    */   
/*    */ 
/*    */   private final EnumChatFormat x;
/*    */   
/*    */ 
/*    */ 
/*    */   static
/*    */   {
/* 36 */     q = new EnumColor[values().length];
/* 37 */     r = new EnumColor[values().length];
/*    */     
/* 39 */     for (EnumColor localEnumColor : values()) {
/* 40 */       q[localEnumColor.getColorIndex()] = localEnumColor;
/* 41 */       r[localEnumColor.getInvColorIndex()] = localEnumColor;
/*    */     }
/*    */   }
/*    */   
/*    */   private EnumColor(int paramInt1, int paramInt2, String paramString1, String paramString2, MaterialMapColor paramMaterialMapColor, EnumChatFormat paramEnumChatFormat) {
/* 46 */     this.s = paramInt1;
/* 47 */     this.t = paramInt2;
/* 48 */     this.u = paramString1;
/* 49 */     this.v = paramString2;
/* 50 */     this.w = paramMaterialMapColor;
/* 51 */     this.x = paramEnumChatFormat;
/*    */   }
/*    */   
/*    */   public int getColorIndex() {
/* 55 */     return this.s;
/*    */   }
/*    */   
/*    */   public int getInvColorIndex() {
/* 59 */     return this.t;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String d()
/*    */   {
/* 67 */     return this.v;
/*    */   }
/*    */   
/*    */   public MaterialMapColor e() {
/* 71 */     return this.w;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static EnumColor fromInvColorIndex(int paramInt)
/*    */   {
/* 79 */     if ((paramInt < 0) || (paramInt >= r.length)) {
/* 80 */       paramInt = 0;
/*    */     }
/* 82 */     return r[paramInt];
/*    */   }
/*    */   
/*    */   public static EnumColor fromColorIndex(int paramInt) {
/* 86 */     if ((paramInt < 0) || (paramInt >= q.length)) {
/* 87 */       paramInt = 0;
/*    */     }
/* 89 */     return q[paramInt];
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 94 */     return this.v;
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 99 */     return this.u;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnumColor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */