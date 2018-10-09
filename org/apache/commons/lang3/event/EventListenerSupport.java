/*     */ package org.apache.commons.lang3.event;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.apache.commons.lang3.Validate;
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
/*     */ public class EventListenerSupport<L>
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3593265990380473632L;
/*  80 */   private List<L> listeners = new CopyOnWriteArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private transient L proxy;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private transient L[] prototypeArray;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T> EventListenerSupport<T> create(Class<T> paramClass)
/*     */   {
/* 110 */     return new EventListenerSupport(paramClass);
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
/*     */   public EventListenerSupport(Class<L> paramClass)
/*     */   {
/* 126 */     this(paramClass, Thread.currentThread().getContextClassLoader());
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
/*     */   public EventListenerSupport(Class<L> paramClass, ClassLoader paramClassLoader)
/*     */   {
/* 143 */     this();
/* 144 */     Validate.notNull(paramClass, "Listener interface cannot be null.", new Object[0]);
/* 145 */     Validate.notNull(paramClassLoader, "ClassLoader cannot be null.", new Object[0]);
/* 146 */     Validate.isTrue(paramClass.isInterface(), "Class {0} is not an interface", new Object[] { paramClass.getName() });
/*     */     
/* 148 */     initializeTransientFields(paramClass, paramClassLoader);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private EventListenerSupport() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public L fire()
/*     */   {
/* 167 */     return (L)this.proxy;
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
/*     */   public void addListener(L paramL)
/*     */   {
/* 183 */     Validate.notNull(paramL, "Listener object cannot be null.", new Object[0]);
/* 184 */     this.listeners.add(paramL);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int getListenerCount()
/*     */   {
/* 193 */     return this.listeners.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeListener(L paramL)
/*     */   {
/* 205 */     Validate.notNull(paramL, "Listener object cannot be null.", new Object[0]);
/* 206 */     this.listeners.remove(paramL);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public L[] getListeners()
/*     */   {
/* 216 */     return this.listeners.toArray(this.prototypeArray);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 225 */     ArrayList localArrayList = new ArrayList();
/*     */     
/*     */ 
/* 228 */     ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(new ByteArrayOutputStream());
/* 229 */     for (Object localObject : this.listeners) {
/*     */       try {
/* 231 */         localObjectOutputStream.writeObject(localObject);
/* 232 */         localArrayList.add(localObject);
/*     */       }
/*     */       catch (IOException localIOException) {
/* 235 */         localObjectOutputStream = new ObjectOutputStream(new ByteArrayOutputStream());
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 242 */     paramObjectOutputStream.writeObject(localArrayList.toArray(this.prototypeArray));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 254 */     Object[] arrayOfObject = (Object[])paramObjectInputStream.readObject();
/*     */     
/* 256 */     this.listeners = new CopyOnWriteArrayList(arrayOfObject);
/*     */     
/*     */ 
/*     */ 
/* 260 */     Class localClass = arrayOfObject.getClass().getComponentType();
/*     */     
/* 262 */     initializeTransientFields(localClass, Thread.currentThread().getContextClassLoader());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void initializeTransientFields(Class<L> paramClass, ClassLoader paramClassLoader)
/*     */   {
/* 273 */     Object[] arrayOfObject = (Object[])Array.newInstance(paramClass, 0);
/* 274 */     this.prototypeArray = arrayOfObject;
/* 275 */     createProxy(paramClass, paramClassLoader);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void createProxy(Class<L> paramClass, ClassLoader paramClassLoader)
/*     */   {
/* 284 */     this.proxy = paramClass.cast(Proxy.newProxyInstance(paramClassLoader, new Class[] { paramClass }, createInvocationHandler()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected InvocationHandler createInvocationHandler()
/*     */   {
/* 294 */     return new ProxyInvocationHandler();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected class ProxyInvocationHandler
/*     */     implements InvocationHandler
/*     */   {
/*     */     protected ProxyInvocationHandler() {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
/*     */       throws Throwable
/*     */     {
/* 316 */       for (Object localObject : EventListenerSupport.this.listeners) {
/* 317 */         paramMethod.invoke(localObject, paramArrayOfObject);
/*     */       }
/* 319 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\event\EventListenerSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */