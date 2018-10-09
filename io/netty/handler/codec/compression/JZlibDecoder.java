/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import com.jcraft.jzlib.Inflater;
/*     */ import com.jcraft.jzlib.JZlib;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import java.util.List;
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
/*     */ public class JZlibDecoder
/*     */   extends ZlibDecoder
/*     */ {
/*  27 */   private final Inflater z = new Inflater();
/*     */   
/*     */ 
/*     */   private byte[] dictionary;
/*     */   
/*     */   private volatile boolean finished;
/*     */   
/*     */ 
/*     */   public JZlibDecoder()
/*     */   {
/*  37 */     this(ZlibWrapper.ZLIB);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JZlibDecoder(ZlibWrapper paramZlibWrapper)
/*     */   {
/*  46 */     if (paramZlibWrapper == null) {
/*  47 */       throw new NullPointerException("wrapper");
/*     */     }
/*     */     
/*  50 */     int i = this.z.init(ZlibUtil.convertWrapperType(paramZlibWrapper));
/*  51 */     if (i != 0) {
/*  52 */       ZlibUtil.fail(this.z, "initialization failure", i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JZlibDecoder(byte[] paramArrayOfByte)
/*     */   {
/*  64 */     if (paramArrayOfByte == null) {
/*  65 */       throw new NullPointerException("dictionary");
/*     */     }
/*  67 */     this.dictionary = paramArrayOfByte;
/*     */     
/*     */ 
/*  70 */     int i = this.z.inflateInit(JZlib.W_ZLIB);
/*  71 */     if (i != 0) {
/*  72 */       ZlibUtil.fail(this.z, "initialization failure", i);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isClosed()
/*     */   {
/*  82 */     return this.finished;
/*     */   }
/*     */   
/*     */   protected void decode(ChannelHandlerContext paramChannelHandlerContext, ByteBuf paramByteBuf, List<Object> paramList) throws Exception
/*     */   {
/*  87 */     if (this.finished)
/*     */     {
/*  89 */       paramByteBuf.skipBytes(paramByteBuf.readableBytes());
/*  90 */       return;
/*     */     }
/*     */     
/*  93 */     if (!paramByteBuf.isReadable()) {
/*  94 */       return;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/*  99 */       int i = paramByteBuf.readableBytes();
/* 100 */       this.z.avail_in = i;
/* 101 */       if (paramByteBuf.hasArray()) {
/* 102 */         this.z.next_in = paramByteBuf.array();
/* 103 */         this.z.next_in_index = (paramByteBuf.arrayOffset() + paramByteBuf.readerIndex());
/*     */       } else {
/* 105 */         byte[] arrayOfByte = new byte[i];
/* 106 */         paramByteBuf.getBytes(paramByteBuf.readerIndex(), arrayOfByte);
/* 107 */         this.z.next_in = arrayOfByte;
/* 108 */         this.z.next_in_index = 0;
/*     */       }
/* 110 */       int j = this.z.next_in_index;
/*     */       
/*     */ 
/* 113 */       int k = i << 1;
/* 114 */       ByteBuf localByteBuf = paramChannelHandlerContext.alloc().heapBuffer(k);
/*     */       try
/*     */       {
/*     */         for (;;) {
/* 118 */           this.z.avail_out = k;
/* 119 */           localByteBuf.ensureWritable(k);
/* 120 */           this.z.next_out = localByteBuf.array();
/* 121 */           this.z.next_out_index = (localByteBuf.arrayOffset() + localByteBuf.writerIndex());
/* 122 */           int m = this.z.next_out_index;
/*     */           
/*     */ 
/* 125 */           int n = this.z.inflate(2);
/* 126 */           int i1 = this.z.next_out_index - m;
/* 127 */           if (i1 > 0) {
/* 128 */             localByteBuf.writerIndex(localByteBuf.writerIndex() + i1);
/*     */           }
/*     */           
/* 131 */           switch (n) {
/*     */           case 2: 
/* 133 */             if (this.dictionary == null) {
/* 134 */               ZlibUtil.fail(this.z, "decompression failure", n);
/*     */             } else {
/* 136 */               n = this.z.inflateSetDictionary(this.dictionary, this.dictionary.length);
/* 137 */               if (n != 0) {
/* 138 */                 ZlibUtil.fail(this.z, "failed to set the dictionary", n);
/*     */               }
/*     */             }
/*     */             break;
/*     */           case 1: 
/* 143 */             this.finished = true;
/* 144 */             this.z.inflateEnd();
/* 145 */             break;
/*     */           case 0: 
/*     */             break;
/*     */           case -5: 
/* 149 */             if (this.z.avail_in > 0) break;
/* 150 */             break;
/*     */           case -4: case -3: 
/*     */           case -2: case -1: 
/*     */           default: 
/* 154 */             ZlibUtil.fail(this.z, "decompression failure", n);
/*     */           }
/*     */         }
/*     */       } finally {
/* 158 */         paramByteBuf.skipBytes(this.z.next_in_index - j);
/* 159 */         if (localByteBuf.isReadable()) {
/* 160 */           paramList.add(localByteBuf);
/*     */         } else {
/* 162 */           localByteBuf.release();
/*     */         }
/*     */         
/*     */       }
/*     */       
/*     */     }
/*     */     finally
/*     */     {
/* 170 */       this.z.next_in = null;
/* 171 */       this.z.next_out = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\compression\JZlibDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */