/*    */ package org.bukkit;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum TreeSpecies
/*    */ {
/* 12 */   GENERIC(
/*    */   
/*    */ 
/* 15 */     0), 
/* 16 */   REDWOOD(
/*    */   
/*    */ 
/* 19 */     1), 
/* 20 */   BIRCH(
/*    */   
/*    */ 
/* 23 */     2), 
/* 24 */   JUNGLE(
/*    */   
/*    */ 
/* 27 */     3), 
/* 28 */   ACACIA(
/*    */   
/*    */ 
/* 31 */     4), 
/* 32 */   DARK_OAK(
/*    */   
/*    */ 
/* 35 */     5);
/*    */   
/*    */   private final byte data;
/*    */   private static final Map<Byte, TreeSpecies> BY_DATA;
/*    */   
/*    */   private TreeSpecies(int data)
/*    */   {
/* 42 */     this.data = ((byte)data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public byte getData()
/*    */   {
/* 53 */     return this.data;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public static TreeSpecies getByData(byte data)
/*    */   {
/* 66 */     return (TreeSpecies)BY_DATA.get(Byte.valueOf(data));
/*    */   }
/*    */   
/*    */   static
/*    */   {
/* 39 */     BY_DATA = Maps.newHashMap();
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
/*    */     TreeSpecies[] arrayOfTreeSpecies;
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
/* 70 */     int i = (arrayOfTreeSpecies = values()).length; for (int j = 0; j < i; j++) { TreeSpecies species = arrayOfTreeSpecies[j];
/* 71 */       BY_DATA.put(Byte.valueOf(species.data), species);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\TreeSpecies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */