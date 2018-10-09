/*     */ package org.bukkit.craftbukkit.libs.jline.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class NonBlockingInputStream
/*     */   extends InputStream
/*     */   implements Runnable
/*     */ {
/*     */   private InputStream in;
/*  35 */   private int ch = -2;
/*     */   
/*  37 */   private boolean threadIsReading = false;
/*  38 */   private boolean isShutdown = false;
/*  39 */   private IOException exception = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean nonBlockingEnabled;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NonBlockingInputStream(InputStream in, boolean isNonBlockingEnabled)
/*     */   {
/*  55 */     this.in = in;
/*  56 */     this.nonBlockingEnabled = isNonBlockingEnabled;
/*     */     
/*  58 */     if (isNonBlockingEnabled) {
/*  59 */       Thread t = new Thread(this);
/*  60 */       t.setName("NonBlockingInputStreamThread");
/*  61 */       t.setDaemon(true);
/*  62 */       t.start();
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
/*     */   public synchronized void shutdown()
/*     */   {
/*  75 */     if ((!this.isShutdown) && (this.nonBlockingEnabled)) {
/*  76 */       this.isShutdown = true;
/*  77 */       notify();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isNonBlockingEnabled()
/*     */   {
/*  87 */     return (this.nonBlockingEnabled) && (!this.isShutdown);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*  96 */     this.in.close();
/*  97 */     shutdown();
/*     */   }
/*     */   
/*     */   public int read() throws IOException
/*     */   {
/* 102 */     if (this.nonBlockingEnabled)
/* 103 */       return read(0L, false);
/* 104 */     return this.in.read();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int peek(long timeout)
/*     */     throws IOException
/*     */   {
/* 117 */     if ((!this.nonBlockingEnabled) || (this.isShutdown)) {
/* 118 */       throw new UnsupportedOperationException("peek() cannot be called as non-blocking operation is disabled");
/*     */     }
/*     */     
/* 121 */     return read(timeout, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(long timeout)
/*     */     throws IOException
/*     */   {
/* 133 */     if ((!this.nonBlockingEnabled) || (this.isShutdown)) {
/* 134 */       throw new UnsupportedOperationException("read() with timeout cannot be called as non-blocking operation is disabled");
/*     */     }
/*     */     
/* 137 */     return read(timeout, false);
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
/*     */   private synchronized int read(long timeout, boolean isPeek)
/*     */     throws IOException
/*     */   {
/* 152 */     if (this.exception != null) {
/* 153 */       assert (this.ch == -2);
/* 154 */       IOException toBeThrown = this.exception;
/* 155 */       if (!isPeek)
/* 156 */         this.exception = null;
/* 157 */       throw toBeThrown;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 165 */     if (this.ch >= -1) {
/* 166 */       if ((!$assertionsDisabled) && (this.exception != null)) throw new AssertionError();
/*     */     }
/* 168 */     else if (((timeout == 0L) || (this.isShutdown)) && (!this.threadIsReading)) {
/* 169 */       this.ch = this.in.read();
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/* 175 */       if (!this.threadIsReading) {
/* 176 */         this.threadIsReading = true;
/* 177 */         notify();
/*     */       }
/*     */       
/* 180 */       boolean isInfinite = timeout <= 0L;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 186 */       while ((isInfinite) || (timeout > 0L)) {
/* 187 */         long start = System.currentTimeMillis();
/*     */         try
/*     */         {
/* 190 */           wait(timeout);
/*     */         }
/*     */         catch (InterruptedException e) {}
/*     */         
/*     */ 
/*     */ 
/* 196 */         if (this.exception != null) {
/* 197 */           assert (this.ch == -2);
/*     */           
/* 199 */           IOException toBeThrown = this.exception;
/* 200 */           if (!isPeek)
/* 201 */             this.exception = null;
/* 202 */           throw toBeThrown;
/*     */         }
/*     */         
/* 205 */         if (this.ch >= -1) {
/* 206 */           if (($assertionsDisabled) || (this.exception == null)) break; throw new AssertionError();
/*     */         }
/*     */         
/*     */ 
/* 210 */         if (!isInfinite) {
/* 211 */           timeout -= System.currentTimeMillis() - start;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 222 */     int ret = this.ch;
/* 223 */     if (!isPeek) {
/* 224 */       this.ch = -2;
/*     */     }
/* 226 */     return ret;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int read(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 236 */     if (b == null)
/* 237 */       throw new NullPointerException();
/* 238 */     if ((off < 0) || (len < 0) || (len > b.length - off))
/* 239 */       throw new IndexOutOfBoundsException();
/* 240 */     if (len == 0) {
/* 241 */       return 0;
/*     */     }
/*     */     int c;
/*     */     int c;
/* 245 */     if (this.nonBlockingEnabled) {
/* 246 */       c = read(0L);
/*     */     } else {
/* 248 */       c = this.in.read();
/*     */     }
/* 250 */     if (c == -1) {
/* 251 */       return -1;
/*     */     }
/* 253 */     b[off] = ((byte)c);
/* 254 */     return 1;
/*     */   }
/*     */   
/*     */   public void run()
/*     */   {
/* 259 */     Log.debug(new Object[] { "NonBlockingInputStream start" });
/* 260 */     boolean needToShutdown = false;
/* 261 */     boolean needToRead = false;
/*     */     
/* 263 */     while (!needToShutdown)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 269 */       synchronized (this) {
/* 270 */         needToShutdown = this.isShutdown;
/* 271 */         needToRead = this.threadIsReading;
/*     */         
/*     */ 
/*     */ 
/*     */         try
/*     */         {
/* 277 */           if ((!needToShutdown) && (!needToRead)) {
/* 278 */             wait(0L);
/*     */           }
/*     */         }
/*     */         catch (InterruptedException e) {}
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 290 */       if ((!needToShutdown) && (needToRead)) {
/* 291 */         int charRead = -2;
/* 292 */         IOException failure = null;
/*     */         try {
/* 294 */           charRead = this.in.read();
/*     */         }
/*     */         catch (IOException e) {
/* 297 */           failure = e;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 303 */         synchronized (this) {
/* 304 */           this.exception = failure;
/* 305 */           this.ch = charRead;
/* 306 */           this.threadIsReading = false;
/* 307 */           notify();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 312 */     Log.debug(new Object[] { "NonBlockingInputStream shutdown" });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\internal\NonBlockingInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */