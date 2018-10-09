/*     */ package org.apache.logging.log4j.core.appender.rolling;
/*     */ 
/*     */ import org.apache.logging.log4j.core.appender.rolling.helper.Action;
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
/*     */ public final class RolloverDescriptionImpl
/*     */   implements RolloverDescription
/*     */ {
/*     */   private final String activeFileName;
/*     */   private final boolean append;
/*     */   private final Action synchronous;
/*     */   private final Action asynchronous;
/*     */   
/*     */   public RolloverDescriptionImpl(String paramString, boolean paramBoolean, Action paramAction1, Action paramAction2)
/*     */   {
/*  58 */     if (paramString == null) {
/*  59 */       throw new NullPointerException("activeFileName");
/*     */     }
/*     */     
/*  62 */     this.append = paramBoolean;
/*  63 */     this.activeFileName = paramString;
/*  64 */     this.synchronous = paramAction1;
/*  65 */     this.asynchronous = paramAction2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getActiveFileName()
/*     */   {
/*  75 */     return this.activeFileName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getAppend()
/*     */   {
/*  83 */     return this.append;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Action getSynchronous()
/*     */   {
/*  94 */     return this.synchronous;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Action getAsynchronous()
/*     */   {
/* 105 */     return this.asynchronous;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\RolloverDescriptionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */