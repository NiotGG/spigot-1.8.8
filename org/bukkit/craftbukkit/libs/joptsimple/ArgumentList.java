/*    */ package org.bukkit.craftbukkit.libs.joptsimple;
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
/*    */ class ArgumentList
/*    */ {
/*    */   private final String[] arguments;
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
/*    */   private int currentIndex;
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
/*    */   ArgumentList(String... arguments)
/*    */   {
/* 41 */     this.arguments = ((String[])arguments.clone());
/*    */   }
/*    */   
/*    */   boolean hasMore() {
/* 45 */     return this.currentIndex < this.arguments.length;
/*    */   }
/*    */   
/*    */   String next() {
/* 49 */     return this.arguments[(this.currentIndex++)];
/*    */   }
/*    */   
/*    */   String peek() {
/* 53 */     return this.arguments[this.currentIndex];
/*    */   }
/*    */   
/*    */   void treatNextAsLongOption() {
/* 57 */     if ('-' != this.arguments[this.currentIndex].charAt(0)) {
/* 58 */       this.arguments[this.currentIndex] = ("--" + this.arguments[this.currentIndex]);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\ArgumentList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */