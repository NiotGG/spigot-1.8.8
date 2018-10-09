/*     */ package org.apache.commons.io.output;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.io.input.XmlStreamReader;
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
/*     */ public class XmlStreamWriter
/*     */   extends Writer
/*     */ {
/*     */   private static final int BUFFER_SIZE = 4096;
/*     */   private final OutputStream out;
/*     */   private final String defaultEncoding;
/*  47 */   private StringWriter xmlPrologWriter = new StringWriter(4096);
/*     */   
/*     */ 
/*     */ 
/*     */   private Writer writer;
/*     */   
/*     */ 
/*     */   private String encoding;
/*     */   
/*     */ 
/*     */ 
/*     */   public XmlStreamWriter(OutputStream paramOutputStream)
/*     */   {
/*  60 */     this(paramOutputStream, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XmlStreamWriter(OutputStream paramOutputStream, String paramString)
/*     */   {
/*  71 */     this.out = paramOutputStream;
/*  72 */     this.defaultEncoding = (paramString != null ? paramString : "UTF-8");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XmlStreamWriter(File paramFile)
/*     */     throws FileNotFoundException
/*     */   {
/*  84 */     this(paramFile, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public XmlStreamWriter(File paramFile, String paramString)
/*     */     throws FileNotFoundException
/*     */   {
/*  97 */     this(new FileOutputStream(paramFile), paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getEncoding()
/*     */   {
/* 106 */     return this.encoding;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDefaultEncoding()
/*     */   {
/* 115 */     return this.defaultEncoding;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 125 */     if (this.writer == null) {
/* 126 */       this.encoding = this.defaultEncoding;
/* 127 */       this.writer = new OutputStreamWriter(this.out, this.encoding);
/* 128 */       this.writer.write(this.xmlPrologWriter.toString());
/*     */     }
/* 130 */     this.writer.close();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 140 */     if (this.writer != null) {
/* 141 */       this.writer.flush();
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
/*     */   private void detectEncoding(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 155 */     int i = paramInt2;
/* 156 */     StringBuffer localStringBuffer = this.xmlPrologWriter.getBuffer();
/* 157 */     if (localStringBuffer.length() + paramInt2 > 4096) {
/* 158 */       i = 4096 - localStringBuffer.length();
/*     */     }
/* 160 */     this.xmlPrologWriter.write(paramArrayOfChar, paramInt1, i);
/*     */     
/*     */ 
/* 163 */     if (localStringBuffer.length() >= 5) {
/* 164 */       if (localStringBuffer.substring(0, 5).equals("<?xml"))
/*     */       {
/* 166 */         int j = localStringBuffer.indexOf("?>");
/* 167 */         if (j > 0)
/*     */         {
/* 169 */           Matcher localMatcher = ENCODING_PATTERN.matcher(localStringBuffer.substring(0, j));
/*     */           
/* 171 */           if (localMatcher.find()) {
/* 172 */             this.encoding = localMatcher.group(1).toUpperCase();
/* 173 */             this.encoding = this.encoding.substring(1, this.encoding.length() - 1);
/*     */           }
/*     */           else
/*     */           {
/* 177 */             this.encoding = this.defaultEncoding;
/*     */           }
/*     */         }
/* 180 */         else if (localStringBuffer.length() >= 4096)
/*     */         {
/*     */ 
/* 183 */           this.encoding = this.defaultEncoding;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 188 */         this.encoding = this.defaultEncoding;
/*     */       }
/* 190 */       if (this.encoding != null)
/*     */       {
/* 192 */         this.xmlPrologWriter = null;
/* 193 */         this.writer = new OutputStreamWriter(this.out, this.encoding);
/* 194 */         this.writer.write(localStringBuffer.toString());
/* 195 */         if (paramInt2 > i) {
/* 196 */           this.writer.write(paramArrayOfChar, paramInt1 + i, paramInt2 - i);
/*     */         }
/*     */       }
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
/*     */   public void write(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*     */     throws IOException
/*     */   {
/* 212 */     if (this.xmlPrologWriter != null) {
/* 213 */       detectEncoding(paramArrayOfChar, paramInt1, paramInt2);
/*     */     } else {
/* 215 */       this.writer.write(paramArrayOfChar, paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */   
/* 219 */   static final Pattern ENCODING_PATTERN = XmlStreamReader.ENCODING_PATTERN;
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\XmlStreamWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */