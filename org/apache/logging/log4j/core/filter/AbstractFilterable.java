/*     */ package org.apache.logging.log4j.core.filter;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Filter.Result;
/*     */ import org.apache.logging.log4j.core.LifeCycle;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ public abstract class AbstractFilterable
/*     */   implements Filterable
/*     */ {
/*  32 */   protected static final Logger LOGGER = ;
/*     */   private volatile Filter filter;
/*     */   
/*     */   protected AbstractFilterable(Filter paramFilter)
/*     */   {
/*  37 */     this.filter = paramFilter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractFilterable() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public Filter getFilter()
/*     */   {
/*  49 */     return this.filter;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void addFilter(Filter paramFilter)
/*     */   {
/*  58 */     if (this.filter == null) {
/*  59 */       this.filter = paramFilter;
/*  60 */     } else if ((paramFilter instanceof CompositeFilter)) {
/*  61 */       this.filter = ((CompositeFilter)this.filter).addFilter(paramFilter);
/*     */     } else {
/*  63 */       Filter[] arrayOfFilter = { this.filter, paramFilter };
/*  64 */       this.filter = CompositeFilter.createFilters(arrayOfFilter);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void removeFilter(Filter paramFilter)
/*     */   {
/*  74 */     if (this.filter == paramFilter) {
/*  75 */       this.filter = null;
/*  76 */     } else if ((paramFilter instanceof CompositeFilter)) {
/*  77 */       CompositeFilter localCompositeFilter = (CompositeFilter)paramFilter;
/*  78 */       localCompositeFilter = localCompositeFilter.removeFilter(paramFilter);
/*  79 */       if (localCompositeFilter.size() > 1) {
/*  80 */         this.filter = localCompositeFilter;
/*  81 */       } else if (localCompositeFilter.size() == 1) {
/*  82 */         Iterator localIterator = localCompositeFilter.iterator();
/*  83 */         this.filter = ((Filter)localIterator.next());
/*     */       } else {
/*  85 */         this.filter = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasFilter()
/*     */   {
/*  96 */     return this.filter != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void startFilter()
/*     */   {
/* 103 */     if ((this.filter != null) && ((this.filter instanceof LifeCycle))) {
/* 104 */       ((LifeCycle)this.filter).start();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void stopFilter()
/*     */   {
/* 112 */     if ((this.filter != null) && ((this.filter instanceof LifeCycle))) {
/* 113 */       ((LifeCycle)this.filter).stop();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFiltered(LogEvent paramLogEvent)
/*     */   {
/* 124 */     return (this.filter != null) && (this.filter.filter(paramLogEvent) == Filter.Result.DENY);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\AbstractFilterable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */