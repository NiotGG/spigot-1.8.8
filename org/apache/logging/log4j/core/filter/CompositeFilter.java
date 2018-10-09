/*     */ package org.apache.logging.log4j.core.filter;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Filter.Result;
/*     */ import org.apache.logging.log4j.core.LifeCycle;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.Logger;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.message.Message;
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
/*     */ @Plugin(name="filters", category="Core", printObject=true)
/*     */ public final class CompositeFilter
/*     */   implements Iterable<Filter>, Filter, LifeCycle
/*     */ {
/*     */   private final List<Filter> filters;
/*     */   private final boolean hasFilters;
/*     */   private boolean isStarted;
/*     */   
/*     */   private CompositeFilter()
/*     */   {
/*  48 */     this.filters = new ArrayList();
/*  49 */     this.hasFilters = false;
/*     */   }
/*     */   
/*     */   private CompositeFilter(List<Filter> paramList) {
/*  53 */     if (paramList == null) {
/*  54 */       this.filters = Collections.unmodifiableList(new ArrayList());
/*  55 */       this.hasFilters = false;
/*  56 */       return;
/*     */     }
/*  58 */     this.filters = Collections.unmodifiableList(paramList);
/*  59 */     this.hasFilters = (this.filters.size() > 0);
/*     */   }
/*     */   
/*     */   public CompositeFilter addFilter(Filter paramFilter) {
/*  63 */     ArrayList localArrayList = new ArrayList(this.filters);
/*  64 */     localArrayList.add(paramFilter);
/*  65 */     return new CompositeFilter(Collections.unmodifiableList(localArrayList));
/*     */   }
/*     */   
/*     */   public CompositeFilter removeFilter(Filter paramFilter) {
/*  69 */     ArrayList localArrayList = new ArrayList(this.filters);
/*  70 */     localArrayList.remove(paramFilter);
/*  71 */     return new CompositeFilter(Collections.unmodifiableList(localArrayList));
/*     */   }
/*     */   
/*     */   public Iterator<Filter> iterator()
/*     */   {
/*  76 */     return this.filters.iterator();
/*     */   }
/*     */   
/*     */   public List<Filter> getFilters() {
/*  80 */     return this.filters;
/*     */   }
/*     */   
/*     */   public boolean hasFilters() {
/*  84 */     return this.hasFilters;
/*     */   }
/*     */   
/*     */   public int size() {
/*  88 */     return this.filters.size();
/*     */   }
/*     */   
/*     */   public void start()
/*     */   {
/*  93 */     for (Filter localFilter : this.filters) {
/*  94 */       if ((localFilter instanceof LifeCycle)) {
/*  95 */         ((LifeCycle)localFilter).start();
/*     */       }
/*     */     }
/*  98 */     this.isStarted = true;
/*     */   }
/*     */   
/*     */   public void stop()
/*     */   {
/* 103 */     for (Filter localFilter : this.filters) {
/* 104 */       if ((localFilter instanceof LifeCycle)) {
/* 105 */         ((LifeCycle)localFilter).stop();
/*     */       }
/*     */     }
/* 108 */     this.isStarted = false;
/*     */   }
/*     */   
/*     */   public boolean isStarted()
/*     */   {
/* 113 */     return this.isStarted;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Filter.Result getOnMismatch()
/*     */   {
/* 123 */     return Filter.Result.NEUTRAL;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Filter.Result getOnMatch()
/*     */   {
/* 133 */     return Filter.Result.NEUTRAL;
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
/*     */   public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs)
/*     */   {
/* 154 */     Filter.Result localResult = Filter.Result.NEUTRAL;
/* 155 */     for (Filter localFilter : this.filters) {
/* 156 */       localResult = localFilter.filter(paramLogger, paramLevel, paramMarker, paramString, paramVarArgs);
/* 157 */       if ((localResult == Filter.Result.ACCEPT) || (localResult == Filter.Result.DENY)) {
/* 158 */         return localResult;
/*     */       }
/*     */     }
/* 161 */     return localResult;
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
/*     */   public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*     */   {
/* 182 */     Filter.Result localResult = Filter.Result.NEUTRAL;
/* 183 */     for (Filter localFilter : this.filters) {
/* 184 */       localResult = localFilter.filter(paramLogger, paramLevel, paramMarker, paramObject, paramThrowable);
/* 185 */       if ((localResult == Filter.Result.ACCEPT) || (localResult == Filter.Result.DENY)) {
/* 186 */         return localResult;
/*     */       }
/*     */     }
/* 189 */     return localResult;
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
/*     */   public Filter.Result filter(Logger paramLogger, Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*     */   {
/* 210 */     Filter.Result localResult = Filter.Result.NEUTRAL;
/* 211 */     for (Filter localFilter : this.filters) {
/* 212 */       localResult = localFilter.filter(paramLogger, paramLevel, paramMarker, paramMessage, paramThrowable);
/* 213 */       if ((localResult == Filter.Result.ACCEPT) || (localResult == Filter.Result.DENY)) {
/* 214 */         return localResult;
/*     */       }
/*     */     }
/* 217 */     return localResult;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Filter.Result filter(LogEvent paramLogEvent)
/*     */   {
/* 229 */     Filter.Result localResult = Filter.Result.NEUTRAL;
/* 230 */     for (Filter localFilter : this.filters) {
/* 231 */       localResult = localFilter.filter(paramLogEvent);
/* 232 */       if ((localResult == Filter.Result.ACCEPT) || (localResult == Filter.Result.DENY)) {
/* 233 */         return localResult;
/*     */       }
/*     */     }
/* 236 */     return localResult;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 241 */     StringBuilder localStringBuilder = new StringBuilder();
/* 242 */     for (Filter localFilter : this.filters) {
/* 243 */       if (localStringBuilder.length() == 0) {
/* 244 */         localStringBuilder.append("{");
/*     */       } else {
/* 246 */         localStringBuilder.append(", ");
/*     */       }
/* 248 */       localStringBuilder.append(localFilter.toString());
/*     */     }
/* 250 */     if (localStringBuilder.length() > 0) {
/* 251 */       localStringBuilder.append("}");
/*     */     }
/* 253 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @PluginFactory
/*     */   public static CompositeFilter createFilters(@PluginElement("Filters") Filter[] paramArrayOfFilter)
/*     */   {
/* 265 */     List localList = (paramArrayOfFilter == null) || (paramArrayOfFilter.length == 0) ? new ArrayList() : Arrays.asList(paramArrayOfFilter);
/*     */     
/* 267 */     return new CompositeFilter(localList);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\filter\CompositeFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */