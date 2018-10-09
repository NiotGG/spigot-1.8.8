/*     */ package io.netty.buffer;
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
/*     */ public abstract interface ByteBufProcessor
/*     */ {
/*  24 */   public static final ByteBufProcessor FIND_NUL = new ByteBufProcessor()
/*     */   {
/*     */     public boolean process(byte paramAnonymousByte) throws Exception {
/*  27 */       return paramAnonymousByte != 0;
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  34 */   public static final ByteBufProcessor FIND_NON_NUL = new ByteBufProcessor()
/*     */   {
/*     */     public boolean process(byte paramAnonymousByte) throws Exception {
/*  37 */       return paramAnonymousByte == 0;
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  44 */   public static final ByteBufProcessor FIND_CR = new ByteBufProcessor()
/*     */   {
/*     */     public boolean process(byte paramAnonymousByte) throws Exception {
/*  47 */       return paramAnonymousByte != 13;
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  54 */   public static final ByteBufProcessor FIND_NON_CR = new ByteBufProcessor()
/*     */   {
/*     */     public boolean process(byte paramAnonymousByte) throws Exception {
/*  57 */       return paramAnonymousByte == 13;
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  64 */   public static final ByteBufProcessor FIND_LF = new ByteBufProcessor()
/*     */   {
/*     */     public boolean process(byte paramAnonymousByte) throws Exception {
/*  67 */       return paramAnonymousByte != 10;
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  74 */   public static final ByteBufProcessor FIND_NON_LF = new ByteBufProcessor()
/*     */   {
/*     */     public boolean process(byte paramAnonymousByte) throws Exception {
/*  77 */       return paramAnonymousByte == 10;
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  84 */   public static final ByteBufProcessor FIND_CRLF = new ByteBufProcessor()
/*     */   {
/*     */     public boolean process(byte paramAnonymousByte) throws Exception {
/*  87 */       return (paramAnonymousByte != 13) && (paramAnonymousByte != 10);
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  94 */   public static final ByteBufProcessor FIND_NON_CRLF = new ByteBufProcessor()
/*     */   {
/*     */     public boolean process(byte paramAnonymousByte) throws Exception {
/*  97 */       return (paramAnonymousByte == 13) || (paramAnonymousByte == 10);
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 104 */   public static final ByteBufProcessor FIND_LINEAR_WHITESPACE = new ByteBufProcessor()
/*     */   {
/*     */     public boolean process(byte paramAnonymousByte) throws Exception {
/* 107 */       return (paramAnonymousByte != 32) && (paramAnonymousByte != 9);
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 114 */   public static final ByteBufProcessor FIND_NON_LINEAR_WHITESPACE = new ByteBufProcessor()
/*     */   {
/*     */     public boolean process(byte paramAnonymousByte) throws Exception {
/* 117 */       return (paramAnonymousByte == 32) || (paramAnonymousByte == 9);
/*     */     }
/*     */   };
/*     */   
/*     */   public abstract boolean process(byte paramByte)
/*     */     throws Exception;
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\ByteBufProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */