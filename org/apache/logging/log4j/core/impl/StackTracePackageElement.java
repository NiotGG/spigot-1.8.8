/*    */ package org.apache.logging.log4j.core.impl;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StackTracePackageElement
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -2171069569241280505L;
/*    */   private final String location;
/*    */   private final String version;
/*    */   private final boolean isExact;
/*    */   
/*    */   public StackTracePackageElement(String paramString1, String paramString2, boolean paramBoolean)
/*    */   {
/* 41 */     this.location = paramString1;
/* 42 */     this.version = paramString2;
/* 43 */     this.isExact = paramBoolean;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getLocation()
/*    */   {
/* 51 */     return this.location;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getVersion()
/*    */   {
/* 59 */     return this.version;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isExact()
/*    */   {
/* 67 */     return this.isExact;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 72 */     String str = this.isExact ? "" : "~";
/* 73 */     return str + "[" + this.location + ":" + this.version + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\impl\StackTracePackageElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */