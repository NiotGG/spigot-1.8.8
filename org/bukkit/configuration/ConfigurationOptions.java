/*    */ package org.bukkit.configuration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfigurationOptions
/*    */ {
/*  8 */   private char pathSeparator = '.';
/*  9 */   private boolean copyDefaults = false;
/*    */   private final Configuration configuration;
/*    */   
/*    */   protected ConfigurationOptions(Configuration configuration) {
/* 13 */     this.configuration = configuration;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Configuration configuration()
/*    */   {
/* 22 */     return this.configuration;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public char pathSeparator()
/*    */   {
/* 35 */     return this.pathSeparator;
/*    */   }
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
/*    */   public ConfigurationOptions pathSeparator(char value)
/*    */   {
/* 49 */     this.pathSeparator = value;
/* 50 */     return this;
/*    */   }
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
/*    */ 
/*    */   public boolean copyDefaults()
/*    */   {
/* 68 */     return this.copyDefaults;
/*    */   }
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
/*    */ 
/*    */ 
/*    */   public ConfigurationOptions copyDefaults(boolean value)
/*    */   {
/* 87 */     this.copyDefaults = value;
/* 88 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\ConfigurationOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */