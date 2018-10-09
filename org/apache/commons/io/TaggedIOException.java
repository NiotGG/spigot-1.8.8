/*     */ package org.apache.commons.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
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
/*     */ public class TaggedIOException
/*     */   extends IOExceptionWithCause
/*     */ {
/*     */   private static final long serialVersionUID = -6994123481142850163L;
/*     */   private final Serializable tag;
/*     */   
/*     */   public static boolean isTaggedWith(Throwable paramThrowable, Object paramObject)
/*     */   {
/*  65 */     return (paramObject != null) && ((paramThrowable instanceof TaggedIOException)) && (paramObject.equals(((TaggedIOException)paramThrowable).tag));
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
/*     */   public static void throwCauseIfTaggedWith(Throwable paramThrowable, Object paramObject)
/*     */     throws IOException
/*     */   {
/*  94 */     if (isTaggedWith(paramThrowable, paramObject)) {
/*  95 */       throw ((TaggedIOException)paramThrowable).getCause();
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
/*     */ 
/*     */   public TaggedIOException(IOException paramIOException, Serializable paramSerializable)
/*     */   {
/* 111 */     super(paramIOException.getMessage(), paramIOException);
/* 112 */     this.tag = paramSerializable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Serializable getTag()
/*     */   {
/* 121 */     return this.tag;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IOException getCause()
/*     */   {
/* 132 */     return (IOException)super.getCause();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\TaggedIOException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */