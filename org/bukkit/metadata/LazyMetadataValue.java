/*     */ package org.bukkit.metadata;
/*     */ 
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.plugin.Plugin;
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
/*     */ public class LazyMetadataValue
/*     */   extends MetadataValueAdapter
/*     */   implements MetadataValue
/*     */ {
/*     */   private Callable<Object> lazyValue;
/*     */   private CacheStrategy cacheStrategy;
/*     */   private SoftReference<Object> internalValue;
/*  24 */   private static final Object ACTUALLY_NULL = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public LazyMetadataValue(Plugin owningPlugin, Callable<Object> lazyValue)
/*     */   {
/*  35 */     this(owningPlugin, CacheStrategy.CACHE_AFTER_FIRST_EVAL, lazyValue);
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
/*     */   public LazyMetadataValue(Plugin owningPlugin, CacheStrategy cacheStrategy, Callable<Object> lazyValue)
/*     */   {
/*  48 */     super(owningPlugin);
/*  49 */     Validate.notNull(cacheStrategy, "cacheStrategy cannot be null");
/*  50 */     Validate.notNull(lazyValue, "lazyValue cannot be null");
/*  51 */     this.internalValue = new SoftReference(null);
/*  52 */     this.lazyValue = lazyValue;
/*  53 */     this.cacheStrategy = cacheStrategy;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected LazyMetadataValue(Plugin owningPlugin)
/*     */   {
/*  63 */     super(owningPlugin);
/*     */   }
/*     */   
/*     */   public Object value() {
/*  67 */     eval();
/*  68 */     Object value = this.internalValue.get();
/*  69 */     if (value == ACTUALLY_NULL) {
/*  70 */       return null;
/*     */     }
/*  72 */     return value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private synchronized void eval()
/*     */     throws MetadataEvaluationException
/*     */   {
/*  82 */     if ((this.cacheStrategy == CacheStrategy.NEVER_CACHE) || (this.internalValue.get() == null)) {
/*     */       try {
/*  84 */         Object value = this.lazyValue.call();
/*  85 */         if (value == null) {
/*  86 */           value = ACTUALLY_NULL;
/*     */         }
/*  88 */         this.internalValue = new SoftReference(value);
/*     */       } catch (Exception e) {
/*  90 */         throw new MetadataEvaluationException(e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public synchronized void invalidate() {
/*  96 */     if (this.cacheStrategy != CacheStrategy.CACHE_ETERNALLY) {
/*  97 */       this.internalValue.clear();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum CacheStrategy
/*     */   {
/* 105 */     CACHE_AFTER_FIRST_EVAL, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 111 */     NEVER_CACHE, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 116 */     CACHE_ETERNALLY;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\metadata\LazyMetadataValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */