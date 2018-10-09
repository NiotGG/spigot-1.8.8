/*     */ package org.apache.commons.io.input;
/*     */ 
/*     */ import java.io.IOException;
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
/*     */ public class XmlStreamReaderException
/*     */   extends IOException
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final String bomEncoding;
/*     */   private final String xmlGuessEncoding;
/*     */   private final String xmlEncoding;
/*     */   private final String contentTypeMime;
/*     */   private final String contentTypeEncoding;
/*     */   
/*     */   public XmlStreamReaderException(String paramString1, String paramString2, String paramString3, String paramString4)
/*     */   {
/*  61 */     this(paramString1, null, null, paramString2, paramString3, paramString4);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XmlStreamReaderException(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
/*     */   {
/*  79 */     super(paramString1);
/*  80 */     this.contentTypeMime = paramString2;
/*  81 */     this.contentTypeEncoding = paramString3;
/*  82 */     this.bomEncoding = paramString4;
/*  83 */     this.xmlGuessEncoding = paramString5;
/*  84 */     this.xmlEncoding = paramString6;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getBomEncoding()
/*     */   {
/*  93 */     return this.bomEncoding;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getXmlGuessEncoding()
/*     */   {
/* 102 */     return this.xmlGuessEncoding;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getXmlEncoding()
/*     */   {
/* 111 */     return this.xmlEncoding;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getContentTypeMime()
/*     */   {
/* 122 */     return this.contentTypeMime;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getContentTypeEncoding()
/*     */   {
/* 134 */     return this.contentTypeEncoding;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\input\XmlStreamReaderException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */