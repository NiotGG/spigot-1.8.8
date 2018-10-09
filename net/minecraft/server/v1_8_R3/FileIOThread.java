/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FileIOThread implements Runnable
/*    */ {
/*  9 */   private static final FileIOThread a = new FileIOThread();
/* 10 */   private List<IAsyncChunkSaver> b = Collections.synchronizedList(Lists.newArrayList());
/*    */   private volatile long c;
/*    */   private volatile long d;
/*    */   private volatile boolean e;
/*    */   
/*    */   private FileIOThread() {
/* 16 */     Thread thread = new Thread(this, "File IO Thread");
/*    */     
/* 18 */     thread.setPriority(1);
/* 19 */     thread.start();
/*    */   }
/*    */   
/*    */   public static FileIOThread a() {
/* 23 */     return a;
/*    */   }
/*    */   
/*    */   public void run() {
/*    */     for (;;) {
/* 28 */       c();
/*    */     }
/*    */   }
/*    */   
/*    */   private void c() {
/* 33 */     for (int i = 0; i < this.b.size(); i++) {
/* 34 */       IAsyncChunkSaver iasyncchunksaver = (IAsyncChunkSaver)this.b.get(i);
/* 35 */       boolean flag = iasyncchunksaver.c();
/*    */       
/* 37 */       if (!flag) {
/* 38 */         this.b.remove(i--);
/* 39 */         this.d += 1L;
/*    */       }
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 50 */     if (this.b.isEmpty()) {
/*    */       try {
/* 52 */         Thread.sleep(25L);
/*    */       } catch (InterruptedException interruptedexception1) {
/* 54 */         interruptedexception1.printStackTrace();
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void a(IAsyncChunkSaver iasyncchunksaver)
/*    */   {
/* 61 */     if (!this.b.contains(iasyncchunksaver)) {
/* 62 */       this.c += 1L;
/* 63 */       this.b.add(iasyncchunksaver);
/*    */     }
/*    */   }
/*    */   
/*    */   public void b() throws InterruptedException {
/* 68 */     this.e = true;
/*    */     
/* 70 */     while (this.c != this.d) {
/* 71 */       Thread.sleep(10L);
/*    */     }
/*    */     
/* 74 */     this.e = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\FileIOThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */