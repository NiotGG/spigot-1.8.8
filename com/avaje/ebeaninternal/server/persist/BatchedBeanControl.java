/*    */ package com.avaje.ebeaninternal.server.persist;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequest;
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BatchedBeanControl
/*    */ {
/* 44 */   private final HashMap<String, BatchedBeanHolder> beanHoldMap = new HashMap();
/*    */   
/*    */   private final SpiTransaction transaction;
/*    */   
/*    */   private final BatchControl batchControl;
/*    */   private int topOrder;
/*    */   
/*    */   public BatchedBeanControl(SpiTransaction t, BatchControl batchControl)
/*    */   {
/* 53 */     this.transaction = t;
/* 54 */     this.batchControl = batchControl;
/*    */   }
/*    */   
/*    */   public ArrayList<PersistRequest> getPersistList(PersistRequestBean<?> request) {
/* 58 */     return getBeanHolder(request).getList(request);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private BatchedBeanHolder getBeanHolder(PersistRequestBean<?> request)
/*    */   {
/* 67 */     BeanDescriptor<?> beanDescriptor = request.getBeanDescriptor();
/* 68 */     BatchedBeanHolder batchBeanHolder = (BatchedBeanHolder)this.beanHoldMap.get(beanDescriptor.getFullName());
/* 69 */     if (batchBeanHolder == null) {
/* 70 */       int relativeDepth = this.transaction.depth(0);
/* 71 */       if (relativeDepth == 0) {
/* 72 */         this.topOrder += 1;
/*    */       }
/* 74 */       int stmtOrder = this.topOrder * 100 + relativeDepth;
/*    */       
/* 76 */       batchBeanHolder = new BatchedBeanHolder(this.batchControl, beanDescriptor, stmtOrder);
/* 77 */       this.beanHoldMap.put(beanDescriptor.getFullName(), batchBeanHolder);
/*    */     }
/* 79 */     return batchBeanHolder;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public boolean isEmpty()
/*    */   {
/* 86 */     return this.beanHoldMap.isEmpty();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public BatchedBeanHolder[] getArray()
/*    */   {
/* 93 */     BatchedBeanHolder[] bsArray = new BatchedBeanHolder[this.beanHoldMap.size()];
/* 94 */     this.beanHoldMap.values().toArray(bsArray);
/* 95 */     return bsArray;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\BatchedBeanControl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */