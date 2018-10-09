/*    */ package com.avaje.ebean.config;
/*    */ 
/*    */ 
/*    */ 
/*    */ class ConfigPropertyMap
/*    */   implements GlobalProperties.PropertySource
/*    */ {
/*    */   private final String serverName;
/*    */   
/*    */ 
/*    */   public ConfigPropertyMap(String serverName)
/*    */   {
/* 13 */     this.serverName = serverName;
/*    */   }
/*    */   
/*    */   public String getServerName() {
/* 17 */     return this.serverName;
/*    */   }
/*    */   
/*    */   public String get(String key, String defaultValue) {
/* 21 */     String namedKey = "ebean." + this.serverName + "." + key;
/* 22 */     String inheritKey = "ebean." + key;
/* 23 */     String value = GlobalProperties.get(namedKey, null);
/* 24 */     if (value == null) {
/* 25 */       value = GlobalProperties.get(inheritKey, null);
/*    */     }
/* 27 */     if (value == null) {
/* 28 */       return defaultValue;
/*    */     }
/* 30 */     return value;
/*    */   }
/*    */   
/*    */ 
/*    */   public int getInt(String key, int defaultValue)
/*    */   {
/* 36 */     String value = get(key, String.valueOf(defaultValue));
/* 37 */     return Integer.parseInt(value);
/*    */   }
/*    */   
/*    */   public boolean getBoolean(String key, boolean defaultValue)
/*    */   {
/* 42 */     String value = get(key, String.valueOf(defaultValue));
/* 43 */     return Boolean.parseBoolean(value);
/*    */   }
/*    */   
/*    */   public <T extends Enum<T>> T getEnum(Class<T> enumType, String key, T defaultValue) {
/* 47 */     String level = get(key, defaultValue.name());
/* 48 */     return Enum.valueOf(enumType, level.toUpperCase());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\ConfigPropertyMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */