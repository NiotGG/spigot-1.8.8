/*    */ package org.apache.commons.io.input;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ public class DemuxInputStream
/*    */   extends InputStream
/*    */ {
/* 31 */   private final InheritableThreadLocal<InputStream> m_streams = new InheritableThreadLocal();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public InputStream bindStream(InputStream paramInputStream)
/*    */   {
/* 41 */     InputStream localInputStream = (InputStream)this.m_streams.get();
/* 42 */     this.m_streams.set(paramInputStream);
/* 43 */     return localInputStream;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void close()
/*    */     throws IOException
/*    */   {
/* 55 */     InputStream localInputStream = (InputStream)this.m_streams.get();
/* 56 */     if (null != localInputStream)
/*    */     {
/* 58 */       localInputStream.close();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int read()
/*    */     throws IOException
/*    */   {
/* 72 */     InputStream localInputStream = (InputStream)this.m_streams.get();
/* 73 */     if (null != localInputStream)
/*    */     {
/* 75 */       return localInputStream.read();
/*    */     }
/*    */     
/*    */ 
/* 79 */     return -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\DemuxInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */