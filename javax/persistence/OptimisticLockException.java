/*     */ package javax.persistence;
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
/*     */ public class OptimisticLockException
/*     */   extends PersistenceException
/*     */ {
/*     */   Object entity;
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
/*     */   public OptimisticLockException() {}
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
/*     */   public OptimisticLockException(String message)
/*     */   {
/*  52 */     super(message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OptimisticLockException(String message, Throwable cause)
/*     */   {
/*  62 */     super(message, cause);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OptimisticLockException(Throwable cause)
/*     */   {
/*  71 */     super(cause);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OptimisticLockException(Object entity)
/*     */   {
/*  80 */     this.entity = entity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public OptimisticLockException(String message, Throwable cause, Object entity)
/*     */   {
/*  91 */     super(message, cause);
/*  92 */     this.entity = entity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getEntity()
/*     */   {
/* 100 */     return this.entity;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\javax\persistence\OptimisticLockException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */