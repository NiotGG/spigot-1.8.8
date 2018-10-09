/*    */ package org.bukkit.util.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.ObjectOutputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.Serializable;
/*    */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
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
/*    */ public class BukkitObjectOutputStream
/*    */   extends ObjectOutputStream
/*    */ {
/*    */   protected BukkitObjectOutputStream()
/*    */     throws IOException, SecurityException
/*    */   {
/* 29 */     super.enableReplaceObject(true);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public BukkitObjectOutputStream(OutputStream out)
/*    */     throws IOException
/*    */   {
/* 40 */     super(out);
/* 41 */     super.enableReplaceObject(true);
/*    */   }
/*    */   
/*    */   protected Object replaceObject(Object obj) throws IOException
/*    */   {
/* 46 */     if ((!(obj instanceof Serializable)) && ((obj instanceof ConfigurationSerializable))) {
/* 47 */       obj = Wrapper.newWrapper((ConfigurationSerializable)obj);
/*    */     }
/*    */     
/* 50 */     return super.replaceObject(obj);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\util\io\BukkitObjectOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */