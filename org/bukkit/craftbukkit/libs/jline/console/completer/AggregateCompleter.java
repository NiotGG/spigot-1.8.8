/*     */ package org.bukkit.craftbukkit.libs.jline.console.completer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.libs.jline.internal.Preconditions;
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
/*     */ public class AggregateCompleter
/*     */   implements Completer
/*     */ {
/*  28 */   private final List<Completer> completers = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AggregateCompleter() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public AggregateCompleter(Collection<Completer> completers)
/*     */   {
/*  41 */     Preconditions.checkNotNull(completers);
/*  42 */     this.completers.addAll(completers);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AggregateCompleter(Completer... completers)
/*     */   {
/*  52 */     this(Arrays.asList(completers));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Collection<Completer> getCompleters()
/*     */   {
/*  61 */     return this.completers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int complete(String buffer, int cursor, List<CharSequence> candidates)
/*     */   {
/*  72 */     Preconditions.checkNotNull(candidates);
/*     */     
/*  74 */     List<Completion> completions = new ArrayList(this.completers.size());
/*     */     
/*     */ 
/*  77 */     int max = -1;
/*  78 */     for (Completer completer : this.completers) {
/*  79 */       Completion completion = new Completion(candidates);
/*  80 */       completion.complete(completer, buffer, cursor);
/*     */       
/*     */ 
/*  83 */       max = Math.max(max, completion.cursor);
/*     */       
/*  85 */       completions.add(completion);
/*     */     }
/*     */     
/*     */ 
/*  89 */     for (Completion completion : completions) {
/*  90 */       if (completion.cursor == max) {
/*  91 */         candidates.addAll(completion.candidates);
/*     */       }
/*     */     }
/*     */     
/*  95 */     return max;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 103 */     return getClass().getSimpleName() + "{" + "completers=" + this.completers + '}';
/*     */   }
/*     */   
/*     */ 
/*     */   private class Completion
/*     */   {
/*     */     public final List<CharSequence> candidates;
/*     */     
/*     */     public int cursor;
/*     */     
/*     */     public Completion()
/*     */     {
/* 115 */       Preconditions.checkNotNull(candidates);
/* 116 */       this.candidates = new LinkedList(candidates);
/*     */     }
/*     */     
/*     */     public void complete(Completer completer, String buffer, int cursor) {
/* 120 */       Preconditions.checkNotNull(completer);
/* 121 */       this.cursor = completer.complete(buffer, cursor, this.candidates);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\AggregateCompleter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */