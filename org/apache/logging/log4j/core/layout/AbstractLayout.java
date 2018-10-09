/*    */ package org.apache.logging.log4j.core.layout;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.Layout;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
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
/*    */ 
/*    */ public abstract class AbstractLayout<T extends Serializable>
/*    */   implements Layout<T>
/*    */ {
/* 33 */   protected static final Logger LOGGER = ;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected byte[] header;
/*    */   
/*    */ 
/*    */ 
/*    */   protected byte[] footer;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] getHeader()
/*    */   {
/* 49 */     return this.header;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setHeader(byte[] paramArrayOfByte)
/*    */   {
/* 57 */     this.header = paramArrayOfByte;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public byte[] getFooter()
/*    */   {
/* 66 */     return this.footer;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setFooter(byte[] paramArrayOfByte)
/*    */   {
/* 74 */     this.footer = paramArrayOfByte;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\AbstractLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */