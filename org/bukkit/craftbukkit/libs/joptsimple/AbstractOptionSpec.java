/*     */ package org.bukkit.craftbukkit.libs.joptsimple;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractOptionSpec<V>
/*     */   implements OptionSpec<V>
/*     */ {
/*  41 */   private final List<String> options = new ArrayList();
/*     */   private final String description;
/*     */   
/*     */   protected AbstractOptionSpec(String option) {
/*  45 */     this(Collections.singletonList(option), "");
/*     */   }
/*     */   
/*     */   protected AbstractOptionSpec(Collection<String> options, String description) {
/*  49 */     arrangeOptions(options);
/*     */     
/*  51 */     this.description = description;
/*     */   }
/*     */   
/*     */   public final Collection<String> options() {
/*  55 */     return Collections.unmodifiableCollection(this.options);
/*     */   }
/*     */   
/*     */   public final List<V> values(OptionSet detectedOptions) {
/*  59 */     return detectedOptions.valuesOf(this);
/*     */   }
/*     */   
/*     */   public final V value(OptionSet detectedOptions) {
/*  63 */     return (V)detectedOptions.valueOf(this);
/*     */   }
/*     */   
/*     */   abstract List<V> defaultValues();
/*     */   
/*     */   String description() {
/*  69 */     return this.description;
/*     */   }
/*     */   
/*     */   protected abstract V convert(String paramString);
/*     */   
/*     */   abstract void handleOption(OptionParser paramOptionParser, ArgumentList paramArgumentList, OptionSet paramOptionSet, String paramString);
/*     */   
/*     */   abstract boolean acceptsArguments();
/*     */   
/*     */   abstract boolean requiresArgument();
/*     */   
/*     */   abstract void accept(OptionSpecVisitor paramOptionSpecVisitor);
/*     */   
/*     */   private void arrangeOptions(Collection<String> unarranged)
/*     */   {
/*  84 */     if (unarranged.size() == 1) {
/*  85 */       this.options.addAll(unarranged);
/*  86 */       return;
/*     */     }
/*     */     
/*  89 */     List<String> shortOptions = new ArrayList();
/*  90 */     List<String> longOptions = new ArrayList();
/*     */     
/*  92 */     for (String each : unarranged) {
/*  93 */       if (each.length() == 1) {
/*  94 */         shortOptions.add(each);
/*     */       } else {
/*  96 */         longOptions.add(each);
/*     */       }
/*     */     }
/*  99 */     Collections.sort(shortOptions);
/* 100 */     Collections.sort(longOptions);
/*     */     
/* 102 */     this.options.addAll(shortOptions);
/* 103 */     this.options.addAll(longOptions);
/*     */   }
/*     */   
/*     */   public boolean equals(Object that)
/*     */   {
/* 108 */     if (!(that instanceof AbstractOptionSpec)) {
/* 109 */       return false;
/*     */     }
/* 111 */     AbstractOptionSpec<?> other = (AbstractOptionSpec)that;
/* 112 */     return this.options.equals(other.options);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 117 */     return this.options.hashCode();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 122 */     return this.options.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\AbstractOptionSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */