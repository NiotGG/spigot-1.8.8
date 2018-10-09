/*    */ package com.avaje.ebeaninternal.server.ldap;
/*    */ 
/*    */ import javax.persistence.PersistenceException;
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
/*    */ public class LdapPersistenceException
/*    */   extends PersistenceException
/*    */ {
/*    */   private static final long serialVersionUID = -3170359404117927668L;
/*    */   
/*    */   public LdapPersistenceException(Throwable e)
/*    */   {
/* 29 */     super(e);
/*    */   }
/*    */   
/*    */   public LdapPersistenceException(String msg, Throwable e) {
/* 33 */     super(msg, e);
/*    */   }
/*    */   
/*    */   public LdapPersistenceException(String msg) {
/* 37 */     super(msg);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ldap\LdapPersistenceException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */