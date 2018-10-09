/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.AbstractSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.ConcurrentMap;
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
/*    */ public final class ConcurrentSet<E>
/*    */   extends AbstractSet<E>
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -6761513279741915432L;
/*    */   private final ConcurrentMap<E, Boolean> map;
/*    */   
/*    */   public ConcurrentSet()
/*    */   {
/* 33 */     this.map = PlatformDependent.newConcurrentHashMap();
/*    */   }
/*    */   
/*    */   public int size()
/*    */   {
/* 38 */     return this.map.size();
/*    */   }
/*    */   
/*    */   public boolean contains(Object paramObject)
/*    */   {
/* 43 */     return this.map.containsKey(paramObject);
/*    */   }
/*    */   
/*    */   public boolean add(E paramE)
/*    */   {
/* 48 */     return this.map.putIfAbsent(paramE, Boolean.TRUE) == null;
/*    */   }
/*    */   
/*    */   public boolean remove(Object paramObject)
/*    */   {
/* 53 */     return this.map.remove(paramObject) != null;
/*    */   }
/*    */   
/*    */   public void clear()
/*    */   {
/* 58 */     this.map.clear();
/*    */   }
/*    */   
/*    */   public Iterator<E> iterator()
/*    */   {
/* 63 */     return this.map.keySet().iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\ConcurrentSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */