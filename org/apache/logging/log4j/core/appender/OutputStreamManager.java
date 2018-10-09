/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Layout;
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
/*     */ public class OutputStreamManager
/*     */   extends AbstractManager
/*     */ {
/*     */   private volatile OutputStream os;
/*     */   private final byte[] footer;
/*     */   private final byte[] header;
/*     */   
/*     */   protected OutputStreamManager(OutputStream paramOutputStream, String paramString, Layout<?> paramLayout)
/*     */   {
/*  36 */     super(paramString);
/*  37 */     this.os = paramOutputStream;
/*  38 */     if (paramLayout != null) {
/*  39 */       this.footer = paramLayout.getFooter();
/*  40 */       this.header = paramLayout.getHeader();
/*  41 */       if (this.header != null) {
/*     */         try {
/*  43 */           this.os.write(this.header, 0, this.header.length);
/*     */         } catch (IOException localIOException) {
/*  45 */           LOGGER.error("Unable to write header", localIOException);
/*     */         }
/*     */       }
/*     */     } else {
/*  49 */       this.footer = null;
/*  50 */       this.header = null;
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
/*     */   public static <T> OutputStreamManager getManager(String paramString, T paramT, ManagerFactory<? extends OutputStreamManager, T> paramManagerFactory)
/*     */   {
/*  65 */     return (OutputStreamManager)AbstractManager.getManager(paramString, paramManagerFactory, paramT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void releaseSub()
/*     */   {
/*  73 */     if (this.footer != null) {
/*  74 */       write(this.footer);
/*     */     }
/*  76 */     close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isOpen()
/*     */   {
/*  84 */     return getCount() > 0;
/*     */   }
/*     */   
/*     */   protected OutputStream getOutputStream() {
/*  88 */     return this.os;
/*     */   }
/*     */   
/*     */   protected void setOutputStream(OutputStream paramOutputStream) {
/*  92 */     if (this.header != null) {
/*     */       try {
/*  94 */         paramOutputStream.write(this.header, 0, this.header.length);
/*  95 */         this.os = paramOutputStream;
/*     */       } catch (IOException localIOException) {
/*  97 */         LOGGER.error("Unable to write header", localIOException);
/*     */       }
/*     */     } else {
/* 100 */       this.os = paramOutputStream;
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
/*     */   protected synchronized void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*     */   {
/*     */     try
/*     */     {
/* 115 */       this.os.write(paramArrayOfByte, paramInt1, paramInt2);
/*     */     } catch (IOException localIOException) {
/* 117 */       String str = "Error writing to stream " + getName();
/* 118 */       throw new AppenderLoggingException(str, localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void write(byte[] paramArrayOfByte)
/*     */   {
/* 129 */     write(paramArrayOfByte, 0, paramArrayOfByte.length);
/*     */   }
/*     */   
/*     */   protected synchronized void close() {
/* 133 */     OutputStream localOutputStream = this.os;
/* 134 */     if ((localOutputStream == System.out) || (localOutputStream == System.err)) {
/* 135 */       return;
/*     */     }
/*     */     try {
/* 138 */       localOutputStream.close();
/*     */     } catch (IOException localIOException) {
/* 140 */       LOGGER.error("Unable to close stream " + getName() + ". " + localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public synchronized void flush()
/*     */   {
/*     */     try
/*     */     {
/* 149 */       this.os.flush();
/*     */     } catch (IOException localIOException) {
/* 151 */       String str = "Error flushing stream " + getName();
/* 152 */       throw new AppenderLoggingException(str, localIOException);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\OutputStreamManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */