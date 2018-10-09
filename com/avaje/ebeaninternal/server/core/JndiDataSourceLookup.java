/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebean.config.GlobalProperties;
/*    */ import javax.naming.Context;
/*    */ import javax.naming.InitialContext;
/*    */ import javax.naming.NamingException;
/*    */ import javax.persistence.PersistenceException;
/*    */ import javax.sql.DataSource;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JndiDataSourceLookup
/*    */ {
/*    */   private static final String DEFAULT_PREFIX = "java:comp/env/jdbc/";
/* 38 */   String jndiPrefix = GlobalProperties.get("ebean.datasource.jndi.prefix", "java:comp/env/jdbc/");
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
/*    */   public DataSource lookup(String jndiName)
/*    */   {
/*    */     try
/*    */     {
/* 53 */       if (!jndiName.startsWith("java:")) {
/* 54 */         jndiName = this.jndiPrefix + jndiName;
/*    */       }
/*    */       
/* 57 */       Context ctx = new InitialContext();
/* 58 */       DataSource ds = (DataSource)ctx.lookup(jndiName);
/* 59 */       if (ds == null) {
/* 60 */         throw new PersistenceException("JNDI DataSource [" + jndiName + "] not found?");
/*    */       }
/* 62 */       return ds;
/*    */     }
/*    */     catch (NamingException ex) {
/* 65 */       throw new PersistenceException(ex);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\JndiDataSourceLookup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */