/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.text.TextException;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScalarTypeURL
/*    */   extends ScalarTypeBaseVarchar<URL>
/*    */ {
/*    */   public ScalarTypeURL()
/*    */   {
/* 33 */     super(URL.class);
/*    */   }
/*    */   
/*    */   public URL convertFromDbString(String dbValue)
/*    */   {
/*    */     try
/*    */     {
/* 40 */       return new URL(dbValue);
/*    */     } catch (MalformedURLException e) {
/* 42 */       throw new RuntimeException("Error with URL [" + dbValue + "] " + e);
/*    */     }
/*    */   }
/*    */   
/*    */   public String convertToDbString(URL beanValue)
/*    */   {
/* 48 */     return formatValue(beanValue);
/*    */   }
/*    */   
/*    */   public String formatValue(URL v) {
/* 52 */     return v.toString();
/*    */   }
/*    */   
/*    */   public URL parse(String value) {
/*    */     try {
/* 57 */       return new URL(value);
/*    */     } catch (MalformedURLException e) {
/* 59 */       throw new TextException(e);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeURL.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */