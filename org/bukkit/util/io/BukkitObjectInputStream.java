/*    */ package org.bukkit.util.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.ObjectInputStream;
/*    */ import org.bukkit.configuration.serialization.ConfigurationSerialization;
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
/*    */ public class BukkitObjectInputStream
/*    */   extends ObjectInputStream
/*    */ {
/*    */   protected BukkitObjectInputStream()
/*    */     throws IOException, SecurityException
/*    */   {
/* 29 */     super.enableResolveObject(true);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public BukkitObjectInputStream(InputStream in)
/*    */     throws IOException
/*    */   {
/* 40 */     super(in);
/* 41 */     super.enableResolveObject(true);
/*    */   }
/*    */   
/*    */   protected Object resolveObject(Object obj) throws IOException
/*    */   {
/* 46 */     if ((obj instanceof Wrapper)) {
/*    */       try {
/* 48 */         (obj = ConfigurationSerialization.deserializeObject(((Wrapper)obj).map)).getClass();
/*    */       } catch (Throwable ex) {
/* 50 */         throw newIOException("Failed to deserialize object", ex);
/*    */       }
/*    */     }
/*    */     
/* 54 */     return super.resolveObject(obj);
/*    */   }
/*    */   
/*    */   private static IOException newIOException(String string, Throwable cause) {
/* 58 */     IOException exception = new IOException(string);
/* 59 */     exception.initCause(cause);
/* 60 */     return exception;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\util\io\BukkitObjectInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */