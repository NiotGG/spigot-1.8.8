/*     */ package org.bukkit.plugin;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableList.Builder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.event.server.ServiceRegisterEvent;
/*     */ import org.bukkit.event.server.ServiceUnregisterEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleServicesManager
/*     */   implements ServicesManager
/*     */ {
/*  27 */   private final Map<Class<?>, List<RegisteredServiceProvider<?>>> providers = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> void register(Class<T> service, T provider, Plugin plugin, ServicePriority priority)
/*     */   {
/*  39 */     RegisteredServiceProvider<T> registeredProvider = null;
/*  40 */     synchronized (this.providers) {
/*  41 */       List<RegisteredServiceProvider<?>> registered = (List)this.providers.get(service);
/*  42 */       if (registered == null) {
/*  43 */         registered = new ArrayList();
/*  44 */         this.providers.put(service, registered);
/*     */       }
/*     */       
/*  47 */       registeredProvider = new RegisteredServiceProvider(service, provider, priority, plugin);
/*     */       
/*     */ 
/*  50 */       int position = Collections.binarySearch(registered, registeredProvider);
/*  51 */       if (position < 0) {
/*  52 */         registered.add(-(position + 1), registeredProvider);
/*     */       } else {
/*  54 */         registered.add(position, registeredProvider);
/*     */       }
/*     */     }
/*     */     
/*  58 */     Bukkit.getServer().getPluginManager().callEvent(new ServiceRegisterEvent(registeredProvider));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unregisterAll(Plugin plugin)
/*     */   {
/*  67 */     ArrayList<ServiceUnregisterEvent> unregisteredEvents = new ArrayList();
/*  68 */     Iterator<Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>>> it; synchronized (this.providers) {
/*  69 */       it = this.providers.entrySet().iterator();
/*     */       try
/*     */       {
/*  72 */         while (it.hasNext()) {
/*  73 */           Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>> entry = (Map.Entry)it.next();
/*  74 */           Iterator<RegisteredServiceProvider<?>> it2 = ((List)entry.getValue()).iterator();
/*     */           
/*     */ 
/*     */           try
/*     */           {
/*  79 */             while (it2.hasNext()) {
/*  80 */               RegisteredServiceProvider<?> registered = (RegisteredServiceProvider)it2.next();
/*     */               
/*  82 */               if (registered.getPlugin().equals(plugin)) {
/*  83 */                 it2.remove();
/*  84 */                 unregisteredEvents.add(new ServiceUnregisterEvent(registered));
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (NoSuchElementException localNoSuchElementException1) {}
/*     */           
/*     */ 
/*  91 */           if (((List)entry.getValue()).size() == 0) {
/*  92 */             it.remove();
/*     */           }
/*     */         }
/*     */       } catch (NoSuchElementException localNoSuchElementException2) {}
/*     */     }
/*  97 */     for (ServiceUnregisterEvent event : unregisteredEvents) {
/*  98 */       Bukkit.getServer().getPluginManager().callEvent(event);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unregister(Class<?> service, Object provider)
/*     */   {
/* 109 */     ArrayList<ServiceUnregisterEvent> unregisteredEvents = new ArrayList();
/* 110 */     Iterator<Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>>> it; synchronized (this.providers) {
/* 111 */       it = this.providers.entrySet().iterator();
/*     */       try
/*     */       {
/* 114 */         while (it.hasNext()) {
/* 115 */           Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>> entry = (Map.Entry)it.next();
/*     */           
/*     */ 
/* 118 */           if (entry.getKey() == service)
/*     */           {
/*     */ 
/*     */ 
/* 122 */             Iterator<RegisteredServiceProvider<?>> it2 = ((List)entry.getValue()).iterator();
/*     */             
/*     */ 
/*     */             try
/*     */             {
/* 127 */               while (it2.hasNext()) {
/* 128 */                 RegisteredServiceProvider<?> registered = (RegisteredServiceProvider)it2.next();
/*     */                 
/* 130 */                 if (registered.getProvider() == provider) {
/* 131 */                   it2.remove();
/* 132 */                   unregisteredEvents.add(new ServiceUnregisterEvent(registered));
/*     */                 }
/*     */               }
/*     */             }
/*     */             catch (NoSuchElementException localNoSuchElementException1) {}
/*     */             
/*     */ 
/* 139 */             if (((List)entry.getValue()).size() == 0)
/* 140 */               it.remove();
/*     */           }
/*     */         }
/*     */       } catch (NoSuchElementException localNoSuchElementException2) {}
/*     */     }
/* 145 */     for (ServiceUnregisterEvent event : unregisteredEvents) {
/* 146 */       Bukkit.getServer().getPluginManager().callEvent(event);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unregister(Object provider)
/*     */   {
/* 156 */     ArrayList<ServiceUnregisterEvent> unregisteredEvents = new ArrayList();
/* 157 */     Iterator<Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>>> it; synchronized (this.providers) {
/* 158 */       it = this.providers.entrySet().iterator();
/*     */       try
/*     */       {
/* 161 */         while (it.hasNext()) {
/* 162 */           Map.Entry<Class<?>, List<RegisteredServiceProvider<?>>> entry = (Map.Entry)it.next();
/* 163 */           Iterator<RegisteredServiceProvider<?>> it2 = ((List)entry.getValue()).iterator();
/*     */           
/*     */ 
/*     */           try
/*     */           {
/* 168 */             while (it2.hasNext()) {
/* 169 */               RegisteredServiceProvider<?> registered = (RegisteredServiceProvider)it2.next();
/*     */               
/* 171 */               if (registered.getProvider().equals(provider)) {
/* 172 */                 it2.remove();
/* 173 */                 unregisteredEvents.add(new ServiceUnregisterEvent(registered));
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (NoSuchElementException localNoSuchElementException1) {}
/*     */           
/*     */ 
/* 180 */           if (((List)entry.getValue()).size() == 0) {
/* 181 */             it.remove();
/*     */           }
/*     */         }
/*     */       } catch (NoSuchElementException localNoSuchElementException2) {}
/*     */     }
/* 186 */     for (ServiceUnregisterEvent event : unregisteredEvents) {
/* 187 */       Bukkit.getServer().getPluginManager().callEvent(event);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> T load(Class<T> service)
/*     */   {
/* 200 */     synchronized (this.providers) {
/* 201 */       List<RegisteredServiceProvider<?>> registered = (List)this.providers.get(service);
/*     */       
/* 203 */       if (registered == null) {
/* 204 */         return null;
/*     */       }
/*     */       
/*     */ 
/* 208 */       return (T)service.cast(((RegisteredServiceProvider)registered.get(0)).getProvider());
/*     */     }
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
/*     */   public <T> RegisteredServiceProvider<T> getRegistration(Class<T> service)
/*     */   {
/* 222 */     synchronized (this.providers) {
/* 223 */       List<RegisteredServiceProvider<?>> registered = (List)this.providers.get(service);
/*     */       
/* 225 */       if (registered == null) {
/* 226 */         return null;
/*     */       }
/*     */       
/*     */ 
/* 230 */       return (RegisteredServiceProvider)registered.get(0);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<RegisteredServiceProvider<?>> getRegistrations(Plugin plugin)
/*     */   {
/* 241 */     ImmutableList.Builder<RegisteredServiceProvider<?>> ret = ImmutableList.builder();
/* 242 */     synchronized (this.providers) { Iterator localIterator2;
/* 243 */       for (Iterator localIterator1 = this.providers.values().iterator(); localIterator1.hasNext(); 
/* 244 */           localIterator2.hasNext())
/*     */       {
/* 243 */         List<RegisteredServiceProvider<?>> registered = (List)localIterator1.next();
/* 244 */         localIterator2 = registered.iterator(); continue;RegisteredServiceProvider<?> provider = (RegisteredServiceProvider)localIterator2.next();
/* 245 */         if (provider.getPlugin().equals(plugin)) {
/* 246 */           ret.add(provider);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 251 */     return ret.build();
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
/*     */   public <T> List<RegisteredServiceProvider<T>> getRegistrations(Class<T> service)
/*     */   {
/* 265 */     synchronized (this.providers) {
/* 266 */       List<RegisteredServiceProvider<?>> registered = (List)this.providers.get(service);
/*     */       
/* 268 */       if (registered == null) {
/* 269 */         return ImmutableList.of();
/*     */       }
/*     */       
/* 272 */       ImmutableList.Builder<RegisteredServiceProvider<T>> ret = ImmutableList.builder();
/*     */       
/* 274 */       for (RegisteredServiceProvider<?> provider : registered) {
/* 275 */         ret.add(provider);
/*     */       }
/*     */     }
/*     */     ImmutableList.Builder<RegisteredServiceProvider<T>> ret;
/* 279 */     return ret.build();
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public Set<Class<?>> getKnownServices()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 29	org/bukkit/plugin/SimpleServicesManager:providers	Ljava/util/Map;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 29	org/bukkit/plugin/SimpleServicesManager:providers	Ljava/util/Map;
/*     */     //   11: invokeinterface 223 1 0
/*     */     //   16: invokestatic 229	com/google/common/collect/ImmutableSet:copyOf	(Ljava/util/Collection;)Lcom/google/common/collect/ImmutableSet;
/*     */     //   19: aload_1
/*     */     //   20: monitorexit
/*     */     //   21: areturn
/*     */     //   22: aload_1
/*     */     //   23: monitorexit
/*     */     //   24: athrow
/*     */     // Line number table:
/*     */     //   Java source line #289	-> byte code offset #0
/*     */     //   Java source line #290	-> byte code offset #7
/*     */     //   Java source line #289	-> byte code offset #22
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	25	0	this	SimpleServicesManager
/*     */     //   5	18	1	Ljava/lang/Object;	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	21	22	finally
/*     */     //   22	24	22	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public <T> boolean isProvidedFor(Class<T> service)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 29	org/bukkit/plugin/SimpleServicesManager:providers	Ljava/util/Map;
/*     */     //   4: dup
/*     */     //   5: astore_2
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 29	org/bukkit/plugin/SimpleServicesManager:providers	Ljava/util/Map;
/*     */     //   11: aload_1
/*     */     //   12: invokeinterface 234 2 0
/*     */     //   17: aload_2
/*     */     //   18: monitorexit
/*     */     //   19: ireturn
/*     */     //   20: aload_2
/*     */     //   21: monitorexit
/*     */     //   22: athrow
/*     */     // Line number table:
/*     */     //   Java source line #302	-> byte code offset #0
/*     */     //   Java source line #303	-> byte code offset #7
/*     */     //   Java source line #302	-> byte code offset #20
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	23	0	this	SimpleServicesManager
/*     */     //   0	23	1	service	Class<T>
/*     */     //   5	16	2	Ljava/lang/Object;	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	19	20	finally
/*     */     //   20	22	20	finally
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\SimpleServicesManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */