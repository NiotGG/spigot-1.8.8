/*    */ package org.apache.logging.log4j.core.net;
/*    */ 
/*    */ import org.apache.logging.log4j.Level;
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
/*    */ public class Priority
/*    */ {
/*    */   private final Facility facility;
/*    */   private final Severity severity;
/*    */   
/*    */   public Priority(Facility paramFacility, Severity paramSeverity)
/*    */   {
/* 35 */     this.facility = paramFacility;
/* 36 */     this.severity = paramSeverity;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static int getPriority(Facility paramFacility, Level paramLevel)
/*    */   {
/* 46 */     return (paramFacility.getCode() << 3) + Severity.getSeverity(paramLevel).getCode();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Facility getFacility()
/*    */   {
/* 54 */     return this.facility;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public Severity getSeverity()
/*    */   {
/* 62 */     return this.severity;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getValue()
/*    */   {
/* 70 */     return this.facility.getCode() << 3 + this.severity.getCode();
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 75 */     return Integer.toString(getValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\Priority.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */