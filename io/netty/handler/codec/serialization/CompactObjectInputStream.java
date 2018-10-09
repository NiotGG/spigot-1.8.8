/*    */ package io.netty.handler.codec.serialization;
/*    */ 
/*    */ import java.io.EOFException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import java.io.ObjectStreamClass;
/*    */ import java.io.StreamCorruptedException;
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
/*    */ class CompactObjectInputStream
/*    */   extends ObjectInputStream
/*    */ {
/*    */   private final ClassResolver classResolver;
/*    */   
/*    */   CompactObjectInputStream(InputStream paramInputStream, ClassResolver paramClassResolver)
/*    */     throws IOException
/*    */   {
/* 30 */     super(paramInputStream);
/* 31 */     this.classResolver = paramClassResolver;
/*    */   }
/*    */   
/*    */   protected void readStreamHeader() throws IOException
/*    */   {
/* 36 */     int i = readByte() & 0xFF;
/* 37 */     if (i != 5) {
/* 38 */       throw new StreamCorruptedException("Unsupported version: " + i);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   protected ObjectStreamClass readClassDescriptor()
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/* 46 */     int i = read();
/* 47 */     if (i < 0) {
/* 48 */       throw new EOFException();
/*    */     }
/* 50 */     switch (i) {
/*    */     case 0: 
/* 52 */       return super.readClassDescriptor();
/*    */     case 1: 
/* 54 */       String str = readUTF();
/* 55 */       Class localClass = this.classResolver.resolve(str);
/* 56 */       return ObjectStreamClass.lookupAny(localClass);
/*    */     }
/* 58 */     throw new StreamCorruptedException("Unexpected class descriptor type: " + i);
/*    */   }
/*    */   
/*    */   protected Class<?> resolveClass(ObjectStreamClass paramObjectStreamClass)
/*    */     throws IOException, ClassNotFoundException
/*    */   {
/*    */     Class localClass;
/*    */     try
/*    */     {
/* 67 */       localClass = this.classResolver.resolve(paramObjectStreamClass.getName());
/*    */     } catch (ClassNotFoundException localClassNotFoundException) {
/* 69 */       localClass = super.resolveClass(paramObjectStreamClass);
/*    */     }
/*    */     
/* 72 */     return localClass;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\serialization\CompactObjectInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */