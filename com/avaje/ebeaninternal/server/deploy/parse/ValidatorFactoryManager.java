/*    */ package com.avaje.ebeaninternal.server.deploy.parse;
/*    */ 
/*    */ import com.avaje.ebean.validation.ValidatorMeta;
/*    */ import com.avaje.ebean.validation.factory.Validator;
/*    */ import com.avaje.ebean.validation.factory.ValidatorFactory;
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ public class ValidatorFactoryManager
/*    */ {
/* 15 */   static final Logger logger = Logger.getLogger(ValidatorFactoryManager.class.getName());
/*    */   Map<Class<?>, ValidatorFactory> factoryMap;
/*    */   
/*    */   public ValidatorFactoryManager()
/*    */   {
/* 20 */     this.factoryMap = new HashMap();
/*    */   }
/*    */   
/*    */   public Validator create(Annotation ann, Class<?> type) {
/* 24 */     synchronized (this) {
/* 25 */       ValidatorMeta meta = (ValidatorMeta)ann.annotationType().getAnnotation(ValidatorMeta.class);
/* 26 */       if (meta == null) {
/* 27 */         return null;
/*    */       }
/* 29 */       Class<?> factoryClass = meta.factory();
/* 30 */       ValidatorFactory factory = getFactory(factoryClass);
/* 31 */       return factory.create(ann, type);
/*    */     }
/*    */   }
/*    */   
/*    */   private ValidatorFactory getFactory(Class<?> factoryClass) {
/*    */     try {
/* 37 */       ValidatorFactory factory = (ValidatorFactory)this.factoryMap.get(factoryClass);
/* 38 */       if (factory == null) {
/* 39 */         factory = (ValidatorFactory)factoryClass.newInstance();
/* 40 */         this.factoryMap.put(factoryClass, factory);
/*    */       }
/* 42 */       return factory;
/*    */     }
/*    */     catch (Exception e) {
/* 45 */       String msg = "Error creating ValidatorFactory " + factoryClass.getName();
/* 46 */       logger.log(Level.SEVERE, msg, e); }
/* 47 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\parse\ValidatorFactoryManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */