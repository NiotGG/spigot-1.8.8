/*     */ package org.apache.logging.log4j.core.appender.db.jpa;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import javax.persistence.EntityManager;
/*     */ import javax.persistence.EntityManagerFactory;
/*     */ import javax.persistence.EntityTransaction;
/*     */ import javax.persistence.Persistence;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager;
/*     */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager.AbstractFactoryData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JPADatabaseManager
/*     */   extends AbstractDatabaseManager
/*     */ {
/*  34 */   private static final JPADatabaseManagerFactory FACTORY = new JPADatabaseManagerFactory(null);
/*     */   
/*     */   private final String entityClassName;
/*     */   
/*     */   private final Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor;
/*     */   
/*     */   private final String persistenceUnitName;
/*     */   
/*     */   private EntityManagerFactory entityManagerFactory;
/*     */   
/*     */   private JPADatabaseManager(String paramString1, int paramInt, Class<? extends AbstractLogEventWrapperEntity> paramClass, Constructor<? extends AbstractLogEventWrapperEntity> paramConstructor, String paramString2)
/*     */   {
/*  46 */     super(paramString1, paramInt);
/*  47 */     this.entityClassName = paramClass.getName();
/*  48 */     this.entityConstructor = paramConstructor;
/*  49 */     this.persistenceUnitName = paramString2;
/*     */   }
/*     */   
/*     */   protected void connectInternal()
/*     */   {
/*  54 */     this.entityManagerFactory = Persistence.createEntityManagerFactory(this.persistenceUnitName);
/*     */   }
/*     */   
/*     */   protected void disconnectInternal()
/*     */   {
/*  59 */     if ((this.entityManagerFactory != null) && (this.entityManagerFactory.isOpen())) {
/*  60 */       this.entityManagerFactory.close();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void writeInternal(LogEvent paramLogEvent)
/*     */   {
/*  66 */     if ((!isConnected()) || (this.entityManagerFactory == null)) {
/*  67 */       throw new AppenderLoggingException("Cannot write logging event; JPA manager not connected to the database.");
/*     */     }
/*     */     
/*     */     AbstractLogEventWrapperEntity localAbstractLogEventWrapperEntity;
/*     */     try
/*     */     {
/*  73 */       localAbstractLogEventWrapperEntity = (AbstractLogEventWrapperEntity)this.entityConstructor.newInstance(new Object[] { paramLogEvent });
/*     */     } catch (Exception localException1) {
/*  75 */       throw new AppenderLoggingException("Failed to instantiate entity class [" + this.entityClassName + "].", localException1);
/*     */     }
/*     */     
/*  78 */     EntityManager localEntityManager = null;
/*  79 */     EntityTransaction localEntityTransaction = null;
/*     */     try {
/*  81 */       localEntityManager = this.entityManagerFactory.createEntityManager();
/*  82 */       localEntityTransaction = localEntityManager.getTransaction();
/*  83 */       localEntityTransaction.begin();
/*  84 */       localEntityManager.persist(localAbstractLogEventWrapperEntity);
/*  85 */       localEntityTransaction.commit();
/*     */ 
/*     */     }
/*     */     catch (Exception localException2)
/*     */     {
/*  90 */       throw new AppenderLoggingException("Failed to insert record for log event in JDBC manager: " + localException2.getMessage(), localException2);
/*     */     }
/*     */     finally {
/*  93 */       if ((localEntityManager != null) && (localEntityManager.isOpen())) {
/*  94 */         localEntityManager.close();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static JPADatabaseManager getJPADatabaseManager(String paramString1, int paramInt, Class<? extends AbstractLogEventWrapperEntity> paramClass, Constructor<? extends AbstractLogEventWrapperEntity> paramConstructor, String paramString2)
/*     */   {
/* 117 */     return (JPADatabaseManager)AbstractDatabaseManager.getManager(paramString1, new FactoryData(paramInt, paramClass, paramConstructor, paramString2), FACTORY);
/*     */   }
/*     */   
/*     */ 
/*     */   private static final class FactoryData
/*     */     extends AbstractDatabaseManager.AbstractFactoryData
/*     */   {
/*     */     private final Class<? extends AbstractLogEventWrapperEntity> entityClass;
/*     */     
/*     */     private final Constructor<? extends AbstractLogEventWrapperEntity> entityConstructor;
/*     */     
/*     */     private final String persistenceUnitName;
/*     */     
/*     */ 
/*     */     protected FactoryData(int paramInt, Class<? extends AbstractLogEventWrapperEntity> paramClass, Constructor<? extends AbstractLogEventWrapperEntity> paramConstructor, String paramString)
/*     */     {
/* 133 */       super();
/*     */       
/* 135 */       this.entityClass = paramClass;
/* 136 */       this.entityConstructor = paramConstructor;
/* 137 */       this.persistenceUnitName = paramString;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static final class JPADatabaseManagerFactory
/*     */     implements ManagerFactory<JPADatabaseManager, JPADatabaseManager.FactoryData>
/*     */   {
/*     */     public JPADatabaseManager createManager(String paramString, JPADatabaseManager.FactoryData paramFactoryData)
/*     */     {
/* 147 */       return new JPADatabaseManager(paramString, paramFactoryData.getBufferSize(), JPADatabaseManager.FactoryData.access$100(paramFactoryData), JPADatabaseManager.FactoryData.access$200(paramFactoryData), JPADatabaseManager.FactoryData.access$300(paramFactoryData), null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jpa\JPADatabaseManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */