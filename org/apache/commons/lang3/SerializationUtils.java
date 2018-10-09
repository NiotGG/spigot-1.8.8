/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ public class SerializationUtils
/*     */ {
/*     */   public static <T extends Serializable> T clone(T paramT)
/*     */   {
/*  79 */     if (paramT == null) {
/*  80 */       return null;
/*     */     }
/*  82 */     byte[] arrayOfByte = serialize(paramT);
/*  83 */     ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/*     */     
/*  85 */     ClassLoaderAwareObjectInputStream localClassLoaderAwareObjectInputStream = null;
/*     */     try
/*     */     {
/*  88 */       localClassLoaderAwareObjectInputStream = new ClassLoaderAwareObjectInputStream(localByteArrayInputStream, paramT.getClass().getClassLoader());
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  96 */       Serializable localSerializable1 = (Serializable)localClassLoaderAwareObjectInputStream.readObject();
/*  97 */       return localSerializable1;
/*     */     }
/*     */     catch (ClassNotFoundException localClassNotFoundException) {
/* 100 */       throw new SerializationException("ClassNotFoundException while reading cloned object data", localClassNotFoundException);
/*     */     } catch (IOException localIOException1) {
/* 102 */       throw new SerializationException("IOException while reading cloned object data", localIOException1);
/*     */     } finally {
/*     */       try {
/* 105 */         if (localClassLoaderAwareObjectInputStream != null) {
/* 106 */           localClassLoaderAwareObjectInputStream.close();
/*     */         }
/*     */       } catch (IOException localIOException3) {
/* 109 */         throw new SerializationException("IOException on closing cloned object data InputStream.", localIOException3);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static <T extends Serializable> T roundtrip(T paramT)
/*     */   {
/* 126 */     return (Serializable)deserialize(serialize(paramT));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void serialize(Serializable paramSerializable, OutputStream paramOutputStream)
/*     */   {
/* 147 */     if (paramOutputStream == null) {
/* 148 */       throw new IllegalArgumentException("The OutputStream must not be null");
/*     */     }
/* 150 */     ObjectOutputStream localObjectOutputStream = null;
/*     */     try
/*     */     {
/* 153 */       localObjectOutputStream = new ObjectOutputStream(paramOutputStream);
/* 154 */       localObjectOutputStream.writeObject(paramSerializable); return;
/*     */     }
/*     */     catch (IOException localIOException2) {
/* 157 */       throw new SerializationException(localIOException2);
/*     */     } finally {
/*     */       try {
/* 160 */         if (localObjectOutputStream != null) {
/* 161 */           localObjectOutputStream.close();
/*     */         }
/*     */       }
/*     */       catch (IOException localIOException3) {}
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
/*     */   public static byte[] serialize(Serializable paramSerializable)
/*     */   {
/* 178 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(512);
/* 179 */     serialize(paramSerializable, localByteArrayOutputStream);
/* 180 */     return localByteArrayOutputStream.toByteArray();
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
/*     */   public static <T> T deserialize(InputStream paramInputStream)
/*     */   {
/* 216 */     if (paramInputStream == null) {
/* 217 */       throw new IllegalArgumentException("The InputStream must not be null");
/*     */     }
/* 219 */     ObjectInputStream localObjectInputStream = null;
/*     */     try
/*     */     {
/* 222 */       localObjectInputStream = new ObjectInputStream(paramInputStream);
/*     */       
/* 224 */       Object localObject1 = localObjectInputStream.readObject();
/* 225 */       return (T)localObject1;
/*     */     }
/*     */     catch (ClassCastException localClassCastException) {
/* 228 */       throw new SerializationException(localClassCastException);
/*     */     } catch (ClassNotFoundException localClassNotFoundException) {
/* 230 */       throw new SerializationException(localClassNotFoundException);
/*     */     } catch (IOException localIOException1) {
/* 232 */       throw new SerializationException(localIOException1);
/*     */     } finally {
/*     */       try {
/* 235 */         if (localObjectInputStream != null) {
/* 236 */           localObjectInputStream.close();
/*     */         }
/*     */       }
/*     */       catch (IOException localIOException3) {}
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
/*     */   public static <T> T deserialize(byte[] paramArrayOfByte)
/*     */   {
/* 265 */     if (paramArrayOfByte == null) {
/* 266 */       throw new IllegalArgumentException("The byte[] must not be null");
/*     */     }
/* 268 */     return (T)deserialize(new ByteArrayInputStream(paramArrayOfByte));
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
/*     */   static class ClassLoaderAwareObjectInputStream
/*     */     extends ObjectInputStream
/*     */   {
/* 285 */     private static final Map<String, Class<?>> primitiveTypes = new HashMap();
/*     */     
/*     */ 
/*     */ 
/*     */     private final ClassLoader classLoader;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public ClassLoaderAwareObjectInputStream(InputStream paramInputStream, ClassLoader paramClassLoader)
/*     */       throws IOException
/*     */     {
/* 297 */       super();
/* 298 */       this.classLoader = paramClassLoader;
/*     */       
/* 300 */       primitiveTypes.put("byte", Byte.TYPE);
/* 301 */       primitiveTypes.put("short", Short.TYPE);
/* 302 */       primitiveTypes.put("int", Integer.TYPE);
/* 303 */       primitiveTypes.put("long", Long.TYPE);
/* 304 */       primitiveTypes.put("float", Float.TYPE);
/* 305 */       primitiveTypes.put("double", Double.TYPE);
/* 306 */       primitiveTypes.put("boolean", Boolean.TYPE);
/* 307 */       primitiveTypes.put("char", Character.TYPE);
/* 308 */       primitiveTypes.put("void", Void.TYPE);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected Class<?> resolveClass(ObjectStreamClass paramObjectStreamClass)
/*     */       throws IOException, ClassNotFoundException
/*     */     {
/* 321 */       String str = paramObjectStreamClass.getName();
/*     */       try {
/* 323 */         return Class.forName(str, false, this.classLoader);
/*     */       } catch (ClassNotFoundException localClassNotFoundException1) {
/*     */         try {
/* 326 */           return Class.forName(str, false, Thread.currentThread().getContextClassLoader());
/*     */         } catch (ClassNotFoundException localClassNotFoundException2) {
/* 328 */           Class localClass = (Class)primitiveTypes.get(str);
/* 329 */           if (localClass != null) {
/* 330 */             return localClass;
/*     */           }
/* 332 */           throw localClassNotFoundException2;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\SerializationUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */