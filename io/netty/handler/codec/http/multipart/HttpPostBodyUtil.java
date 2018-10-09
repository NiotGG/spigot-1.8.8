/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.util.CharsetUtil;
/*     */ import java.nio.charset.Charset;
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
/*     */ final class HttpPostBodyUtil
/*     */ {
/*     */   public static final int chunkSize = 8096;
/*     */   public static final String CONTENT_DISPOSITION = "Content-Disposition";
/*     */   public static final String NAME = "name";
/*     */   public static final String FILENAME = "filename";
/*     */   public static final String FORM_DATA = "form-data";
/*     */   public static final String ATTACHMENT = "attachment";
/*     */   public static final String FILE = "file";
/*     */   public static final String MULTIPART_MIXED = "multipart/mixed";
/*  61 */   public static final Charset ISO_8859_1 = CharsetUtil.ISO_8859_1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  66 */   public static final Charset US_ASCII = CharsetUtil.US_ASCII;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String DEFAULT_BINARY_CONTENT_TYPE = "application/octet-stream";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final String DEFAULT_TEXT_CONTENT_TYPE = "text/plain";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum TransferEncodingMechanism
/*     */   {
/*  90 */     BIT7("7bit"), 
/*     */     
/*     */ 
/*     */ 
/*  94 */     BIT8("8bit"), 
/*     */     
/*     */ 
/*     */ 
/*  98 */     BINARY("binary");
/*     */     
/*     */     private final String value;
/*     */     
/*     */     private TransferEncodingMechanism(String paramString) {
/* 103 */       this.value = paramString;
/*     */     }
/*     */     
/*     */     private TransferEncodingMechanism() {
/* 107 */       this.value = name();
/*     */     }
/*     */     
/*     */     public String value() {
/* 111 */       return this.value;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 116 */       return this.value;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   static class SeekAheadNoBackArrayException
/*     */     extends Exception
/*     */   {
/*     */     private static final long serialVersionUID = -630418804938699495L;
/*     */   }
/*     */   
/*     */ 
/*     */   static class SeekAheadOptimize
/*     */   {
/*     */     byte[] bytes;
/*     */     
/*     */     int readerIndex;
/*     */     
/*     */     int pos;
/*     */     
/*     */     int origPos;
/*     */     int limit;
/*     */     ByteBuf buffer;
/*     */     
/*     */     SeekAheadOptimize(ByteBuf paramByteBuf)
/*     */       throws HttpPostBodyUtil.SeekAheadNoBackArrayException
/*     */     {
/* 143 */       if (!paramByteBuf.hasArray()) {
/* 144 */         throw new HttpPostBodyUtil.SeekAheadNoBackArrayException();
/*     */       }
/* 146 */       this.buffer = paramByteBuf;
/* 147 */       this.bytes = paramByteBuf.array();
/* 148 */       this.readerIndex = paramByteBuf.readerIndex();
/* 149 */       this.origPos = (this.pos = paramByteBuf.arrayOffset() + this.readerIndex);
/* 150 */       this.limit = (paramByteBuf.arrayOffset() + paramByteBuf.writerIndex());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     void setReadPosition(int paramInt)
/*     */     {
/* 159 */       this.pos -= paramInt;
/* 160 */       this.readerIndex = getReadPosition(this.pos);
/* 161 */       this.buffer.readerIndex(this.readerIndex);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     int getReadPosition(int paramInt)
/*     */     {
/* 170 */       return paramInt - this.origPos + this.readerIndex;
/*     */     }
/*     */     
/*     */     void clear() {
/* 174 */       this.buffer = null;
/* 175 */       this.bytes = null;
/* 176 */       this.limit = 0;
/* 177 */       this.pos = 0;
/* 178 */       this.readerIndex = 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int findNonWhitespace(String paramString, int paramInt)
/*     */   {
/* 188 */     for (int i = paramInt; i < paramString.length(); i++) {
/* 189 */       if (!Character.isWhitespace(paramString.charAt(i))) {
/*     */         break;
/*     */       }
/*     */     }
/* 193 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int findWhitespace(String paramString, int paramInt)
/*     */   {
/* 202 */     for (int i = paramInt; i < paramString.length(); i++) {
/* 203 */       if (Character.isWhitespace(paramString.charAt(i))) {
/*     */         break;
/*     */       }
/*     */     }
/* 207 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int findEndOfString(String paramString)
/*     */   {
/* 216 */     for (int i = paramString.length(); i > 0; i--) {
/* 217 */       if (!Character.isWhitespace(paramString.charAt(i - 1))) {
/*     */         break;
/*     */       }
/*     */     }
/* 221 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\http\multipart\HttpPostBodyUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */