/*    */ package org.yaml.snakeyaml.util;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ public class ArrayStack<T>
/*    */ {
/*    */   private ArrayList<T> stack;
/*    */   
/*    */   public ArrayStack(int initSize)
/*    */   {
/* 24 */     this.stack = new ArrayList(initSize);
/*    */   }
/*    */   
/*    */   public void push(T obj) {
/* 28 */     this.stack.add(obj);
/*    */   }
/*    */   
/*    */   public T pop() {
/* 32 */     return (T)this.stack.remove(this.stack.size() - 1);
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 36 */     return this.stack.isEmpty();
/*    */   }
/*    */   
/*    */   public void clear() {
/* 40 */     this.stack.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\util\ArrayStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */