/*     */ package io.netty.handler.codec.http.cors;
/*     */ 
/*     */ import io.netty.handler.codec.http.DefaultHttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpHeaders;
/*     */ import io.netty.handler.codec.http.HttpMethod;
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
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
/*     */ public final class CorsConfig
/*     */ {
/*     */   private final Set<String> origins;
/*     */   private final boolean anyOrigin;
/*     */   private final boolean enabled;
/*     */   private final Set<String> exposeHeaders;
/*     */   private final boolean allowCredentials;
/*     */   private final long maxAge;
/*     */   private final Set<HttpMethod> allowedRequestMethods;
/*     */   private final Set<String> allowedRequestHeaders;
/*     */   private final boolean allowNullOrigin;
/*     */   private final Map<CharSequence, Callable<?>> preflightHeaders;
/*     */   private final boolean shortCurcuit;
/*     */   
/*     */   private CorsConfig(Builder paramBuilder)
/*     */   {
/*  53 */     this.origins = new LinkedHashSet(paramBuilder.origins);
/*  54 */     this.anyOrigin = paramBuilder.anyOrigin;
/*  55 */     this.enabled = paramBuilder.enabled;
/*  56 */     this.exposeHeaders = paramBuilder.exposeHeaders;
/*  57 */     this.allowCredentials = paramBuilder.allowCredentials;
/*  58 */     this.maxAge = paramBuilder.maxAge;
/*  59 */     this.allowedRequestMethods = paramBuilder.requestMethods;
/*  60 */     this.allowedRequestHeaders = paramBuilder.requestHeaders;
/*  61 */     this.allowNullOrigin = paramBuilder.allowNullOrigin;
/*  62 */     this.preflightHeaders = paramBuilder.preflightHeaders;
/*  63 */     this.shortCurcuit = paramBuilder.shortCurcuit;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCorsSupportEnabled()
/*     */   {
/*  72 */     return this.enabled;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAnyOriginSupported()
/*     */   {
/*  81 */     return this.anyOrigin;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String origin()
/*     */   {
/*  90 */     return this.origins.isEmpty() ? "*" : (String)this.origins.iterator().next();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<String> origins()
/*     */   {
/*  99 */     return this.origins;
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
/*     */   public boolean isNullOriginAllowed()
/*     */   {
/* 112 */     return this.allowNullOrigin;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<String> exposedHeaders()
/*     */   {
/* 138 */     return Collections.unmodifiableSet(this.exposeHeaders);
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
/*     */   public boolean isCredentialsAllowed()
/*     */   {
/* 159 */     return this.allowCredentials;
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
/*     */   public long maxAge()
/*     */   {
/* 173 */     return this.maxAge;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<HttpMethod> allowedRequestMethods()
/*     */   {
/* 183 */     return Collections.unmodifiableSet(this.allowedRequestMethods);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<String> allowedRequestHeaders()
/*     */   {
/* 195 */     return Collections.unmodifiableSet(this.allowedRequestHeaders);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public HttpHeaders preflightResponseHeaders()
/*     */   {
/* 204 */     if (this.preflightHeaders.isEmpty()) {
/* 205 */       return HttpHeaders.EMPTY_HEADERS;
/*     */     }
/* 207 */     DefaultHttpHeaders localDefaultHttpHeaders = new DefaultHttpHeaders();
/* 208 */     for (Map.Entry localEntry : this.preflightHeaders.entrySet()) {
/* 209 */       Object localObject = getValue((Callable)localEntry.getValue());
/* 210 */       if ((localObject instanceof Iterable)) {
/* 211 */         localDefaultHttpHeaders.add((CharSequence)localEntry.getKey(), (Iterable)localObject);
/*     */       } else {
/* 213 */         localDefaultHttpHeaders.add((CharSequence)localEntry.getKey(), localObject);
/*     */       }
/*     */     }
/* 216 */     return localDefaultHttpHeaders;
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
/*     */   public boolean isShortCurcuit()
/*     */   {
/* 230 */     return this.shortCurcuit;
/*     */   }
/*     */   
/*     */   private static <T> T getValue(Callable<T> paramCallable) {
/*     */     try {
/* 235 */       return (T)paramCallable.call();
/*     */     } catch (Exception localException) {
/* 237 */       throw new IllegalStateException("Could not generate value for callable [" + paramCallable + ']', localException);
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 243 */     return StringUtil.simpleClassName(this) + "[enabled=" + this.enabled + ", origins=" + this.origins + ", anyOrigin=" + this.anyOrigin + ", exposedHeaders=" + this.exposeHeaders + ", isCredentialsAllowed=" + this.allowCredentials + ", maxAge=" + this.maxAge + ", allowedRequestMethods=" + this.allowedRequestMethods + ", allowedRequestHeaders=" + this.allowedRequestHeaders + ", preflightHeaders=" + this.preflightHeaders + ']';
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
/*     */   public static Builder withAnyOrigin()
/*     */   {
/* 260 */     return new Builder();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Builder withOrigin(String paramString)
/*     */   {
/* 269 */     if ("*".equals(paramString)) {
/* 270 */       return new Builder();
/*     */     }
/* 272 */     return new Builder(new String[] { paramString });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Builder withOrigins(String... paramVarArgs)
/*     */   {
/* 281 */     return new Builder(paramVarArgs);
/*     */   }
/*     */   
/*     */ 
/*     */   public static class Builder
/*     */   {
/*     */     private final Set<String> origins;
/*     */     
/*     */     private final boolean anyOrigin;
/*     */     
/*     */     private boolean allowNullOrigin;
/* 292 */     private boolean enabled = true;
/*     */     private boolean allowCredentials;
/* 294 */     private final Set<String> exposeHeaders = new HashSet();
/*     */     private long maxAge;
/* 296 */     private final Set<HttpMethod> requestMethods = new HashSet();
/* 297 */     private final Set<String> requestHeaders = new HashSet();
/* 298 */     private final Map<CharSequence, Callable<?>> preflightHeaders = new HashMap();
/*     */     
/*     */ 
/*     */     private boolean noPreflightHeaders;
/*     */     
/*     */     private boolean shortCurcuit;
/*     */     
/*     */ 
/*     */     public Builder(String... paramVarArgs)
/*     */     {
/* 308 */       this.origins = new LinkedHashSet(Arrays.asList(paramVarArgs));
/* 309 */       this.anyOrigin = false;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder()
/*     */     {
/* 318 */       this.anyOrigin = true;
/* 319 */       this.origins = Collections.emptySet();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder allowNullOrigin()
/*     */     {
/* 330 */       this.allowNullOrigin = true;
/* 331 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder disable()
/*     */     {
/* 340 */       this.enabled = false;
/* 341 */       return this;
/*     */     }
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
/*     */     public Builder exposeHeaders(String... paramVarArgs)
/*     */     {
/* 370 */       this.exposeHeaders.addAll(Arrays.asList(paramVarArgs));
/* 371 */       return this;
/*     */     }
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
/*     */     public Builder allowCredentials()
/*     */     {
/* 390 */       this.allowCredentials = true;
/* 391 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder maxAge(long paramLong)
/*     */     {
/* 404 */       this.maxAge = paramLong;
/* 405 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder allowedRequestMethods(HttpMethod... paramVarArgs)
/*     */     {
/* 416 */       this.requestMethods.addAll(Arrays.asList(paramVarArgs));
/* 417 */       return this;
/*     */     }
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
/*     */     public Builder allowedRequestHeaders(String... paramVarArgs)
/*     */     {
/* 437 */       this.requestHeaders.addAll(Arrays.asList(paramVarArgs));
/* 438 */       return this;
/*     */     }
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
/*     */     public Builder preflightResponseHeader(CharSequence paramCharSequence, Object... paramVarArgs)
/*     */     {
/* 452 */       if (paramVarArgs.length == 1) {
/* 453 */         this.preflightHeaders.put(paramCharSequence, new CorsConfig.ConstantValueGenerator(paramVarArgs[0], null));
/*     */       } else {
/* 455 */         preflightResponseHeader(paramCharSequence, Arrays.asList(paramVarArgs));
/*     */       }
/* 457 */       return this;
/*     */     }
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
/*     */     public <T> Builder preflightResponseHeader(CharSequence paramCharSequence, Iterable<T> paramIterable)
/*     */     {
/* 472 */       this.preflightHeaders.put(paramCharSequence, new CorsConfig.ConstantValueGenerator(paramIterable, null));
/* 473 */       return this;
/*     */     }
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
/*     */     public <T> Builder preflightResponseHeader(String paramString, Callable<T> paramCallable)
/*     */     {
/* 492 */       this.preflightHeaders.put(paramString, paramCallable);
/* 493 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder noPreflightResponseHeaders()
/*     */     {
/* 502 */       this.noPreflightHeaders = true;
/* 503 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public CorsConfig build()
/*     */     {
/* 512 */       if ((this.preflightHeaders.isEmpty()) && (!this.noPreflightHeaders)) {
/* 513 */         this.preflightHeaders.put("Date", new CorsConfig.DateValueGenerator());
/* 514 */         this.preflightHeaders.put("Content-Length", new CorsConfig.ConstantValueGenerator("0", null));
/*     */       }
/* 516 */       return new CorsConfig(this, null);
/*     */     }
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
/*     */     public Builder shortCurcuit()
/*     */     {
/* 530 */       this.shortCurcuit = true;
/* 531 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final class ConstantValueGenerator
/*     */     implements Callable<Object>
/*     */   {
/*     */     private final Object value;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private ConstantValueGenerator(Object paramObject)
/*     */     {
/* 550 */       if (paramObject == null) {
/* 551 */         throw new IllegalArgumentException("value must not be null");
/*     */       }
/* 553 */       this.value = paramObject;
/*     */     }
/*     */     
/*     */     public Object call()
/*     */     {
/* 558 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final class DateValueGenerator
/*     */     implements Callable<Date>
/*     */   {
/*     */     public Date call()
/*     */       throws Exception
/*     */     {
/* 571 */       return new Date();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\cors\CorsConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */