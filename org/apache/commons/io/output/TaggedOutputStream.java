/*     */ package org.apache.commons.io.output;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.UUID;
/*     */ import org.apache.commons.io.TaggedIOException;
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
/*     */ public class TaggedOutputStream
/*     */   extends ProxyOutputStream
/*     */ {
/*  69 */   private final Serializable tag = UUID.randomUUID();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TaggedOutputStream(OutputStream paramOutputStream)
/*     */   {
/*  77 */     super(paramOutputStream);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCauseOf(Exception paramException)
/*     */   {
/*  88 */     return TaggedIOException.isTaggedWith(paramException, this.tag);
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
/*     */   public void throwIfCauseOf(Exception paramException)
/*     */     throws IOException
/*     */   {
/* 102 */     TaggedIOException.throwCauseIfTaggedWith(paramException, this.tag);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void handleIOException(IOException paramIOException)
/*     */     throws IOException
/*     */   {
/* 113 */     throw new TaggedIOException(paramIOException, this.tag);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\output\TaggedOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */