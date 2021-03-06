/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebean.config.NamingConvention;
/*    */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*    */ import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AnnotationBase
/*    */ {
/*    */   protected final DatabasePlatform databasePlatform;
/*    */   protected final NamingConvention namingConvention;
/*    */   protected final DeployUtil util;
/*    */   
/*    */   protected AnnotationBase(DeployUtil util)
/*    */   {
/* 21 */     this.util = util;
/* 22 */     this.databasePlatform = util.getDbPlatform();
/* 23 */     this.namingConvention = util.getNamingConvention();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract void parse();
/*    */   
/*    */ 
/*    */ 
/*    */   protected boolean isEmpty(String s)
/*    */   {
/* 35 */     if ((s == null) || (s.trim().length() == 0)) {
/* 36 */       return true;
/*    */     }
/* 38 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected <T extends Annotation> T get(DeployBeanProperty prop, Class<T> annClass)
/*    */   {
/* 49 */     T a = null;
/* 50 */     Field field = prop.getField();
/* 51 */     if (field != null) {
/* 52 */       a = field.getAnnotation(annClass);
/*    */     }
/* 54 */     if (a == null) {
/* 55 */       Method m = prop.getReadMethod();
/* 56 */       if (m != null) {
/* 57 */         a = m.getAnnotation(annClass);
/*    */       }
/*    */     }
/* 60 */     return a;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   protected <T extends Annotation> T find(DeployBeanProperty prop, Class<T> annClass)
/*    */   {
/* 70 */     T a = get(prop, annClass);
/* 71 */     if (a == null) {
/* 72 */       a = prop.getOwningType().getAnnotation(annClass);
/*    */     }
/* 74 */     return a;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\parse\AnnotationBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */