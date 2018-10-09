/*    */ package io.netty.handler.codec.serialization;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.ObjectStreamClass;
/*    */ import java.io.OutputStream;
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
/*    */ class CompactObjectOutputStream
/*    */   extends ObjectOutputStream
/*    */ {
/*    */   static final int TYPE_FAT_DESCRIPTOR = 0;
/*    */   static final int TYPE_THIN_DESCRIPTOR = 1;
/*    */   
/*    */   CompactObjectOutputStream(OutputStream paramOutputStream)
/*    */     throws IOException
/*    */   {
/* 29 */     super(paramOutputStream);
/*    */   }
/*    */   
/*    */   protected void writeStreamHeader() throws IOException
/*    */   {
/* 34 */     writeByte(5);
/*    */   }
/*    */   
/*    */   protected void writeClassDescriptor(ObjectStreamClass paramObjectStreamClass) throws IOException
/*    */   {
/* 39 */     Class localClass = paramObjectStreamClass.forClass();
/* 40 */     if ((localClass.isPrimitive()) || (localClass.isArray()) || (localClass.isInterface()) || (paramObjectStreamClass.getSerialVersionUID() == 0L))
/*    */     {
/* 42 */       write(0);
/* 43 */       super.writeClassDescriptor(paramObjectStreamClass);
/*    */     } else {
/* 45 */       write(1);
/* 46 */       writeUTF(paramObjectStreamClass.getName());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\serialization\CompactObjectOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */