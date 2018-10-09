/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.lang.reflect.Proxy;
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
/*     */ public class ClassLoaderObjectInputStream
/*     */   extends ObjectInputStream
/*     */ {
/*     */   private final ClassLoader classLoader;
/*     */   
/*     */   public ClassLoaderObjectInputStream(ClassLoader paramClassLoader, InputStream paramInputStream)
/*     */     throws IOException, StreamCorruptedException
/*     */   {
/*  51 */     super(paramInputStream);
/*  52 */     this.classLoader = paramClassLoader;
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
/*     */   protected Class<?> resolveClass(ObjectStreamClass paramObjectStreamClass)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/*  68 */     Class localClass = Class.forName(paramObjectStreamClass.getName(), false, this.classLoader);
/*     */     
/*  70 */     if (localClass != null)
/*     */     {
/*  72 */       return localClass;
/*     */     }
/*     */     
/*  75 */     return super.resolveClass(paramObjectStreamClass);
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
/*     */   protected Class<?> resolveProxyClass(String[] paramArrayOfString)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/*  93 */     Class[] arrayOfClass = new Class[paramArrayOfString.length];
/*  94 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/*  95 */       arrayOfClass[i] = Class.forName(paramArrayOfString[i], false, this.classLoader);
/*     */     }
/*     */     try {
/*  98 */       return Proxy.getProxyClass(this.classLoader, arrayOfClass);
/*     */     } catch (IllegalArgumentException localIllegalArgumentException) {}
/* 100 */     return super.resolveProxyClass(paramArrayOfString);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\ClassLoaderObjectInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */