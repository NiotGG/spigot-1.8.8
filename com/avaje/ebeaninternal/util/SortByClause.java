/*    */ package com.avaje.ebeaninternal.util;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class SortByClause
/*    */ {
/*    */   public static final String NULLSHIGH = "nullshigh";
/*    */   public static final String NULLSLOW = "nullslow";
/*    */   public static final String ASC = "asc";
/*    */   public static final String DESC = "desc";
/*    */   private List<Property> properties;
/*    */   
/*    */   public SortByClause()
/*    */   {
/* 30 */     this.properties = new ArrayList();
/*    */   }
/*    */   
/*    */ 
/*    */   public int size()
/*    */   {
/* 36 */     return this.properties.size();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public List<Property> getProperties()
/*    */   {
/* 43 */     return this.properties;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void add(Property p)
/*    */   {
/* 50 */     this.properties.add(p);
/*    */   }
/*    */   
/*    */ 
/*    */   public static class Property
/*    */     implements Serializable
/*    */   {
/*    */     private static final long serialVersionUID = 7588760362420690963L;
/*    */     
/*    */     private final String name;
/*    */     
/*    */     private final boolean ascending;
/*    */     
/*    */     private final Boolean nullsHigh;
/*    */     
/*    */     public Property(String name, boolean ascending, Boolean nullsHigh)
/*    */     {
/* 67 */       this.name = name;
/* 68 */       this.ascending = ascending;
/* 69 */       this.nullsHigh = nullsHigh;
/*    */     }
/*    */     
/*    */     public String toString() {
/* 73 */       return this.name + " asc:" + this.ascending;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */     public String getName()
/*    */     {
/* 80 */       return this.name;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */     public boolean isAscending()
/*    */     {
/* 87 */       return this.ascending;
/*    */     }
/*    */     
/*    */     public Boolean getNullsHigh() {
/* 91 */       return this.nullsHigh;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\util\SortByClause.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */