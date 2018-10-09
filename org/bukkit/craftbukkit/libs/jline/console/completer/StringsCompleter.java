/*    */ package org.bukkit.craftbukkit.libs.jline.console.completer;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.SortedSet;
/*    */ import java.util.TreeSet;
/*    */ import org.bukkit.craftbukkit.libs.jline.internal.Preconditions;
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
/*    */ public class StringsCompleter
/*    */   implements Completer
/*    */ {
/* 28 */   private final SortedSet<String> strings = new TreeSet();
/*    */   
/*    */ 
/*    */   public StringsCompleter() {}
/*    */   
/*    */   public StringsCompleter(Collection<String> strings)
/*    */   {
/* 35 */     Preconditions.checkNotNull(strings);
/* 36 */     getStrings().addAll(strings);
/*    */   }
/*    */   
/*    */   public StringsCompleter(String... strings) {
/* 40 */     this(Arrays.asList(strings));
/*    */   }
/*    */   
/*    */   public Collection<String> getStrings() {
/* 44 */     return this.strings;
/*    */   }
/*    */   
/*    */   public int complete(String buffer, int cursor, List<CharSequence> candidates)
/*    */   {
/* 49 */     Preconditions.checkNotNull(candidates);
/*    */     
/* 51 */     if (buffer == null) {
/* 52 */       candidates.addAll(this.strings);
/*    */     }
/*    */     else {
/* 55 */       for (String match : this.strings.tailSet(buffer)) {
/* 56 */         if (!match.startsWith(buffer)) {
/*    */           break;
/*    */         }
/*    */         
/* 60 */         candidates.add(match);
/*    */       }
/*    */     }
/*    */     
/* 64 */     if (candidates.size() == 1) {
/* 65 */       candidates.set(0, candidates.get(0) + " ");
/*    */     }
/*    */     
/* 68 */     return candidates.isEmpty() ? -1 : 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\completer\StringsCompleter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */