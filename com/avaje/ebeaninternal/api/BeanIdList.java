/*     */ package com.avaje.ebeaninternal.api;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.concurrent.FutureTask;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeanIdList
/*     */ {
/*     */   private final List<Object> idList;
/*  38 */   private boolean hasMore = true;
/*     */   private FutureTask<Integer> fetchFuture;
/*     */   
/*     */   public BeanIdList(List<Object> idList)
/*     */   {
/*  43 */     this.idList = idList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isFetchingInBackground()
/*     */   {
/*  50 */     return this.fetchFuture != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setBackgroundFetch(FutureTask<Integer> fetchFuture)
/*     */   {
/*  57 */     this.fetchFuture = fetchFuture;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void backgroundFetchWait(long wait, TimeUnit timeUnit)
/*     */   {
/*  64 */     if (this.fetchFuture != null) {
/*     */       try {
/*  66 */         this.fetchFuture.get(wait, timeUnit);
/*     */       } catch (Exception e) {
/*  68 */         throw new PersistenceException(e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void backgroundFetchWait()
/*     */   {
/*  77 */     if (this.fetchFuture != null) {
/*     */       try {
/*  79 */         this.fetchFuture.get();
/*     */       } catch (Exception e) {
/*  81 */         throw new PersistenceException(e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void add(Object id)
/*     */   {
/*  90 */     this.idList.add(id);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public List<Object> getIdList()
/*     */   {
/*  97 */     return this.idList;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isHasMore()
/*     */   {
/* 104 */     return this.hasMore;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setHasMore(boolean hasMore)
/*     */   {
/* 111 */     this.hasMore = hasMore;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\BeanIdList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */