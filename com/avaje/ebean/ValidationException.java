/*    */ package com.avaje.ebean;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import javax.persistence.PersistenceException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ValidationException
/*    */   extends PersistenceException
/*    */ {
/*    */   private static final long serialVersionUID = -6185355529565362494L;
/*    */   final InvalidValue invalid;
/*    */   
/*    */   public ValidationException(InvalidValue invalid)
/*    */   {
/* 17 */     super("validation failed for: " + invalid.getBeanType());
/* 18 */     this.invalid = invalid;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public InvalidValue getInvalid()
/*    */   {
/* 29 */     return this.invalid;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public InvalidValue[] getErrors()
/*    */   {
/* 40 */     return this.invalid.getErrors();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 44 */     return super.toString() + ": " + Arrays.toString(getErrors());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\ValidationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */