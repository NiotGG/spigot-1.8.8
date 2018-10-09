/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum WorldType
/*    */ {
/* 10 */   NORMAL("DEFAULT"), 
/* 11 */   FLAT("FLAT"), 
/* 12 */   VERSION_1_1("DEFAULT_1_1"), 
/* 13 */   LARGE_BIOMES("LARGEBIOMES"), 
/* 14 */   AMPLIFIED("AMPLIFIED"), 
/* 15 */   CUSTOMIZED("CUSTOMIZED");
/*    */   
/*    */   private static final Map<String, WorldType> BY_NAME;
/*    */   private final String name;
/*    */   
/*    */   private WorldType(String name) {
/* 21 */     this.name = name;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 30 */     return this.name;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static WorldType getByName(String name)
/*    */   {
/* 40 */     return (WorldType)BY_NAME.get(name.toUpperCase());
/*    */   }
/*    */   
/*    */   static
/*    */   {
/* 17 */     BY_NAME = Maps.newHashMap();
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     WorldType[] arrayOfWorldType;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 44 */     int i = (arrayOfWorldType = values()).length; for (int j = 0; j < i; j++) { WorldType type = arrayOfWorldType[j];
/* 45 */       BY_NAME.put(type.name, type);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\WorldType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */