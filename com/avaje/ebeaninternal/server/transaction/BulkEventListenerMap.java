/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebean.event.BulkTableEvent;
/*    */ import com.avaje.ebean.event.BulkTableEventListener;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class BulkEventListenerMap
/*    */ {
/* 13 */   private final HashMap<String, Entry> map = new HashMap();
/*    */   
/*    */   public BulkEventListenerMap(List<BulkTableEventListener> listeners) {
/*    */     Iterator i$;
/* 17 */     if (listeners != null) {
/* 18 */       for (i$ = listeners.iterator(); i$.hasNext();) { l = (BulkTableEventListener)i$.next();
/* 19 */         Set<String> tables = l.registeredTables();
/* 20 */         for (String tableName : tables)
/* 21 */           register(tableName, l);
/*    */       }
/*    */     }
/*    */     BulkTableEventListener l;
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 28 */     return this.map.isEmpty();
/*    */   }
/*    */   
/*    */   public void process(BulkTableEvent event)
/*    */   {
/* 33 */     Entry entry = (Entry)this.map.get(event.getTableName());
/* 34 */     if (entry != null) {
/* 35 */       entry.process(event);
/*    */     }
/*    */   }
/*    */   
/*    */   private void register(String tableName, BulkTableEventListener l) {
/* 40 */     String upperTableName = tableName.trim().toUpperCase();
/* 41 */     Entry entry = (Entry)this.map.get(upperTableName);
/* 42 */     if (entry == null) {
/* 43 */       entry = new Entry(null);
/* 44 */       this.map.put(upperTableName, entry);
/*    */     }
/* 46 */     entry.add(l);
/*    */   }
/*    */   
/*    */   private static class Entry
/*    */   {
/* 51 */     List<BulkTableEventListener> listeners = new ArrayList();
/*    */     
/* 53 */     private void add(BulkTableEventListener l) { this.listeners.add(l); }
/*    */     
/*    */     private void process(BulkTableEvent event)
/*    */     {
/* 57 */       for (int i = 0; i < this.listeners.size(); i++) {
/* 58 */         ((BulkTableEventListener)this.listeners.get(i)).process(event);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\BulkEventListenerMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */