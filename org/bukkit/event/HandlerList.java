/*     */ package org.bukkit.event;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map.Entry;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.RegisteredListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HandlerList
/*     */ {
/*  18 */   private volatile RegisteredListener[] handlers = null;
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
/*  30 */   private static ArrayList<HandlerList> allLists = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void bakeAll()
/*     */   {
/*  38 */     synchronized (allLists) {
/*  39 */       for (HandlerList h : allLists) {
/*  40 */         h.bake();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static void unregisterAll()
/*     */   {
/*  49 */     synchronized (allLists) {
/*  50 */       for (HandlerList h : allLists) {
/*  51 */         synchronized (h) {
/*  52 */           for (List<RegisteredListener> list : h.handlerslots.values()) {
/*  53 */             list.clear();
/*     */           }
/*  55 */           h.handlers = null;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void unregisterAll(Plugin plugin)
/*     */   {
/*  67 */     synchronized (allLists) {
/*  68 */       for (HandlerList h : allLists) {
/*  69 */         h.unregister(plugin);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void unregisterAll(Listener listener)
/*     */   {
/*  80 */     synchronized (allLists) {
/*  81 */       for (HandlerList h : allLists) {
/*  82 */         h.unregister(listener);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  93 */   private final EnumMap<EventPriority, ArrayList<RegisteredListener>> handlerslots = new EnumMap(EventPriority.class);
/*  94 */   public HandlerList() { EventPriority[] arrayOfEventPriority; int i = (arrayOfEventPriority = EventPriority.values()).length; for (int j = 0; j < i; j++) { EventPriority o = arrayOfEventPriority[j];
/*  95 */       this.handlerslots.put(o, new ArrayList());
/*     */     }
/*  97 */     synchronized (allLists) {
/*  98 */       allLists.add(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void register(RegisteredListener listener)
/*     */   {
/* 108 */     if (((ArrayList)this.handlerslots.get(listener.getPriority())).contains(listener))
/* 109 */       throw new IllegalStateException("This listener is already registered to priority " + listener.getPriority().toString());
/* 110 */     this.handlers = null;
/* 111 */     ((ArrayList)this.handlerslots.get(listener.getPriority())).add(listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void registerAll(Collection<RegisteredListener> listeners)
/*     */   {
/* 120 */     for (RegisteredListener listener : listeners) {
/* 121 */       register(listener);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void unregister(RegisteredListener listener)
/*     */   {
/* 131 */     if (((ArrayList)this.handlerslots.get(listener.getPriority())).remove(listener)) {
/* 132 */       this.handlers = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void unregister(Plugin plugin)
/*     */   {
/* 142 */     boolean changed = false;
/* 143 */     ListIterator<RegisteredListener> i; for (Iterator localIterator = this.handlerslots.values().iterator(); localIterator.hasNext(); 
/* 144 */         i.hasNext())
/*     */     {
/* 143 */       List<RegisteredListener> list = (List)localIterator.next();
/* 144 */       i = list.listIterator(); continue;
/* 145 */       if (((RegisteredListener)i.next()).getPlugin().equals(plugin)) {
/* 146 */         i.remove();
/* 147 */         changed = true;
/*     */       }
/*     */     }
/*     */     
/* 151 */     if (changed) { this.handlers = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void unregister(Listener listener)
/*     */   {
/* 160 */     boolean changed = false;
/* 161 */     ListIterator<RegisteredListener> i; for (Iterator localIterator = this.handlerslots.values().iterator(); localIterator.hasNext(); 
/* 162 */         i.hasNext())
/*     */     {
/* 161 */       List<RegisteredListener> list = (List)localIterator.next();
/* 162 */       i = list.listIterator(); continue;
/* 163 */       if (((RegisteredListener)i.next()).getListener().equals(listener)) {
/* 164 */         i.remove();
/* 165 */         changed = true;
/*     */       }
/*     */     }
/*     */     
/* 169 */     if (changed) { this.handlers = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public synchronized void bake()
/*     */   {
/* 176 */     if (this.handlers != null) return;
/* 177 */     List<RegisteredListener> entries = new ArrayList();
/* 178 */     for (Map.Entry<EventPriority, ArrayList<RegisteredListener>> entry : this.handlerslots.entrySet()) {
/* 179 */       entries.addAll((Collection)entry.getValue());
/*     */     }
/* 181 */     this.handlers = ((RegisteredListener[])entries.toArray(new RegisteredListener[entries.size()]));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public RegisteredListener[] getRegisteredListeners()
/*     */   {
/*     */     RegisteredListener[] handlers;
/*     */     
/*     */ 
/* 191 */     while ((handlers = this.handlers) == null) { RegisteredListener[] handlers; bake(); }
/* 192 */     return handlers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ArrayList<RegisteredListener> getRegisteredListeners(Plugin plugin)
/*     */   {
/* 203 */     ArrayList<RegisteredListener> listeners = new ArrayList();
/* 204 */     synchronized (allLists) {
/* 205 */       for (HandlerList h : allLists) {
/* 206 */         synchronized (h) { Iterator localIterator3;
/* 207 */           for (Iterator localIterator2 = h.handlerslots.values().iterator(); localIterator2.hasNext(); 
/* 208 */               localIterator3.hasNext())
/*     */           {
/* 207 */             List<RegisteredListener> list = (List)localIterator2.next();
/* 208 */             localIterator3 = list.iterator(); continue;RegisteredListener listener = (RegisteredListener)localIterator3.next();
/* 209 */             if (listener.getPlugin().equals(plugin)) {
/* 210 */               listeners.add(listener);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 217 */     return listeners;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public static ArrayList<HandlerList> getHandlerLists()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: getstatic 27	org/bukkit/event/HandlerList:allLists	Ljava/util/ArrayList;
/*     */     //   3: dup
/*     */     //   4: astore_0
/*     */     //   5: monitorenter
/*     */     //   6: getstatic 27	org/bukkit/event/HandlerList:allLists	Ljava/util/ArrayList;
/*     */     //   9: invokevirtual 216	java/util/ArrayList:clone	()Ljava/lang/Object;
/*     */     //   12: checkcast 22	java/util/ArrayList
/*     */     //   15: aload_0
/*     */     //   16: monitorexit
/*     */     //   17: areturn
/*     */     //   18: aload_0
/*     */     //   19: monitorexit
/*     */     //   20: athrow
/*     */     // Line number table:
/*     */     //   Java source line #227	-> byte code offset #0
/*     */     //   Java source line #228	-> byte code offset #6
/*     */     //   Java source line #227	-> byte code offset #18
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   4	15	0	Ljava/lang/Object;	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   6	17	18	finally
/*     */     //   18	20	18	finally
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\HandlerList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */