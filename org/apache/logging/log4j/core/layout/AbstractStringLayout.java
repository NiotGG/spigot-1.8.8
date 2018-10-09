/*    */ package org.apache.logging.log4j.core.layout;
/*    */ 
/*    */ import java.nio.charset.Charset;
/*    */ import org.apache.logging.log4j.core.LogEvent;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractStringLayout
/*    */   extends AbstractLayout<String>
/*    */ {
/*    */   private final Charset charset;
/*    */   
/*    */   protected AbstractStringLayout(Charset paramCharset)
/*    */   {
/* 34 */     this.charset = paramCharset;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] toByteArray(LogEvent paramLogEvent)
/*    */   {
/* 45 */     return ((String)toSerializable(paramLogEvent)).getBytes(this.charset);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getContentType()
/*    */   {
/* 53 */     return "text/plain";
/*    */   }
/*    */   
/*    */   protected Charset getCharset() {
/* 57 */     return this.charset;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\AbstractStringLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */