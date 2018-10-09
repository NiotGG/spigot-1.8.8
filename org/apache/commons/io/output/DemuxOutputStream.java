/*    */ package org.apache.commons.io.output;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ public class DemuxOutputStream
/*    */   extends OutputStream
/*    */ {
/* 31 */   private final InheritableThreadLocal<OutputStream> m_streams = new InheritableThreadLocal();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public OutputStream bindStream(OutputStream paramOutputStream)
/*    */   {
/* 41 */     OutputStream localOutputStream = (OutputStream)this.m_streams.get();
/* 42 */     this.m_streams.set(paramOutputStream);
/* 43 */     return localOutputStream;
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
/* 55 */     OutputStream localOutputStream = (OutputStream)this.m_streams.get();
/* 56 */     if (null != localOutputStream)
/*    */     {
/* 58 */       localOutputStream.close();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void flush()
/*    */     throws IOException
/*    */   {
/* 71 */     OutputStream localOutputStream = (OutputStream)this.m_streams.get();
/* 72 */     if (null != localOutputStream)
/*    */     {
/* 74 */       localOutputStream.flush();
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
/*    */   public void write(int paramInt)
/*    */     throws IOException
/*    */   {
/* 88 */     OutputStream localOutputStream = (OutputStream)this.m_streams.get();
/* 89 */     if (null != localOutputStream)
/*    */     {
/* 91 */       localOutputStream.write(paramInt);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\DemuxOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */