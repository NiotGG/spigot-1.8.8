/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*     */ import java.util.HashMap;
/*     */ import javax.persistence.PersistenceException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransactionMap
/*     */ {
/*     */   private HashMap<String, State> map;
/*     */   
/*     */   public TransactionMap()
/*     */   {
/*  37 */     this.map = new HashMap();
/*     */   }
/*     */   
/*  40 */   public String toString() { return this.map.toString(); }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/*  44 */     return this.map.isEmpty();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public State getState(String serverName)
/*     */   {
/*  52 */     return (State)this.map.get(serverName);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public State getStateWithCreate(String serverName)
/*     */   {
/*  60 */     State state = (State)this.map.get(serverName);
/*  61 */     if (state == null) {
/*  62 */       state = new State();
/*  63 */       this.map.put(serverName, state);
/*     */     }
/*  65 */     return state;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public State removeState(String serverName)
/*     */   {
/*  72 */     return (State)this.map.remove(serverName);
/*     */   }
/*     */   
/*     */ 
/*     */   public static class State
/*     */   {
/*     */     SpiTransaction transaction;
/*     */     
/*     */ 
/*     */     public String toString()
/*     */     {
/*  83 */       return "txn[" + this.transaction + "]";
/*     */     }
/*     */     
/*     */     public SpiTransaction get() {
/*  87 */       return this.transaction;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public void set(SpiTransaction trans)
/*     */     {
/*  95 */       if ((this.transaction != null) && (this.transaction.isActive())) {
/*  96 */         String m = "The existing transaction is still active?";
/*  97 */         throw new PersistenceException(m);
/*     */       }
/*  99 */       this.transaction = trans;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public void commit()
/*     */     {
/* 107 */       this.transaction.commit();
/* 108 */       this.transaction = null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void rollback()
/*     */     {
/* 115 */       this.transaction.rollback();
/* 116 */       this.transaction = null;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void end()
/*     */     {
/* 123 */       if (this.transaction != null) {
/* 124 */         this.transaction.end();
/* 125 */         this.transaction = null;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void replace(SpiTransaction trans)
/*     */     {
/* 133 */       this.transaction = trans;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\TransactionMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */