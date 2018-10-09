/*    */ package org.apache.commons.codec;
/*    */ 
/*    */ import java.util.Comparator;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringEncoderComparator
/*    */   implements Comparator
/*    */ {
/*    */   private final StringEncoder stringEncoder;
/*    */   
/*    */   @Deprecated
/*    */   public StringEncoderComparator()
/*    */   {
/* 48 */     this.stringEncoder = null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public StringEncoderComparator(StringEncoder paramStringEncoder)
/*    */   {
/* 58 */     this.stringEncoder = paramStringEncoder;
/*    */   }
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
/*    */   public int compare(Object paramObject1, Object paramObject2)
/*    */   {
/* 77 */     int i = 0;
/*    */     
/*    */ 
/*    */     try
/*    */     {
/* 82 */       Comparable localComparable1 = (Comparable)this.stringEncoder.encode(paramObject1);
/* 83 */       Comparable localComparable2 = (Comparable)this.stringEncoder.encode(paramObject2);
/* 84 */       i = localComparable1.compareTo(localComparable2);
/*    */     } catch (EncoderException localEncoderException) {
/* 86 */       i = 0;
/*    */     }
/* 88 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\codec\StringEncoderComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */