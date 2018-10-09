/*    */ package org.bukkit.block.banner;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public enum PatternType
/*    */ {
/*  7 */   BASE("b"), 
/*  8 */   SQUARE_BOTTOM_LEFT("bl"), 
/*  9 */   SQUARE_BOTTOM_RIGHT("br"), 
/* 10 */   SQUARE_TOP_LEFT("tl"), 
/* 11 */   SQUARE_TOP_RIGHT("tr"), 
/* 12 */   STRIPE_BOTTOM("bs"), 
/* 13 */   STRIPE_TOP("ts"), 
/* 14 */   STRIPE_LEFT("ls"), 
/* 15 */   STRIPE_RIGHT("rs"), 
/* 16 */   STRIPE_CENTER("cs"), 
/* 17 */   STRIPE_MIDDLE("ms"), 
/* 18 */   STRIPE_DOWNRIGHT("drs"), 
/* 19 */   STRIPE_DOWNLEFT("dls"), 
/* 20 */   STRIPE_SMALL("ss"), 
/* 21 */   CROSS("cr"), 
/* 22 */   STRAIGHT_CROSS("sc"), 
/* 23 */   TRIANGLE_BOTTOM("bt"), 
/* 24 */   TRIANGLE_TOP("tt"), 
/* 25 */   TRIANGLES_BOTTOM("bts"), 
/* 26 */   TRIANGLES_TOP("tts"), 
/* 27 */   DIAGONAL_LEFT("ld"), 
/* 28 */   DIAGONAL_RIGHT("rd"), 
/* 29 */   DIAGONAL_LEFT_MIRROR("lud"), 
/* 30 */   DIAGONAL_RIGHT_MIRROR("rud"), 
/* 31 */   CIRCLE_MIDDLE("mc"), 
/* 32 */   RHOMBUS_MIDDLE("mr"), 
/* 33 */   HALF_VERTICAL("vh"), 
/* 34 */   HALF_HORIZONTAL("hh"), 
/* 35 */   HALF_VERTICAL_MIRROR("vhr"), 
/* 36 */   HALF_HORIZONTAL_MIRROR("hhb"), 
/* 37 */   BORDER("bo"), 
/* 38 */   CURLY_BORDER("cbo"), 
/* 39 */   CREEPER("cre"), 
/* 40 */   GRADIENT("gra"), 
/* 41 */   GRADIENT_UP("gru"), 
/* 42 */   BRICKS("bri"), 
/* 43 */   SKULL("sku"), 
/* 44 */   FLOWER("flo"), 
/* 45 */   MOJANG("moj");
/*    */   
/*    */   static {
/* 48 */     byString = new java.util.HashMap();
/*    */     
/*    */     PatternType[] arrayOfPatternType;
/* 51 */     int i = (arrayOfPatternType = values()).length; for (int j = 0; j < i; j++) { PatternType p = arrayOfPatternType[j];
/* 52 */       byString.put(p.identifier, p);
/*    */     }
/*    */   }
/*    */   
/*    */   private PatternType(String key) {
/* 57 */     this.identifier = key;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   private final String identifier;
/*    */   
/*    */ 
/*    */   public String getIdentifier()
/*    */   {
/* 67 */     return this.identifier;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private static final Map<String, PatternType> byString;
/*    */   
/*    */ 
/*    */   public static PatternType getByIdentifier(String identifier)
/*    */   {
/* 78 */     return (PatternType)byString.get(identifier);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\banner\PatternType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */