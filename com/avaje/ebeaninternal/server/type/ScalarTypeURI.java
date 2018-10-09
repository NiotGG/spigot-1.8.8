/*    */ package com.avaje.ebeaninternal.server.type;
/*    */ 
/*    */ import com.avaje.ebean.text.TextException;
/*    */ import java.net.URI;
/*    */ import java.net.URISyntaxException;
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
/*    */ public class ScalarTypeURI
/*    */   extends ScalarTypeBaseVarchar<URI>
/*    */ {
/*    */   public ScalarTypeURI()
/*    */   {
/* 33 */     super(URI.class);
/*    */   }
/*    */   
/*    */   public URI convertFromDbString(String dbValue)
/*    */   {
/*    */     try {
/* 39 */       return new URI(dbValue);
/*    */     } catch (URISyntaxException e) {
/* 41 */       throw new RuntimeException("Error with URI [" + dbValue + "] " + e);
/*    */     }
/*    */   }
/*    */   
/*    */   public String convertToDbString(URI beanValue)
/*    */   {
/* 47 */     return beanValue.toString();
/*    */   }
/*    */   
/*    */   public String formatValue(URI v) {
/* 51 */     return v.toString();
/*    */   }
/*    */   
/*    */   public URI parse(String value) {
/*    */     try {
/* 56 */       return new URI(value);
/*    */     } catch (URISyntaxException e) {
/* 58 */       throw new TextException("Error with URI [" + value + "] ", e);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\ScalarTypeURI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */