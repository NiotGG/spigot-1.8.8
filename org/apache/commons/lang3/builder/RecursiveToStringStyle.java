/*    */ package org.apache.commons.lang3.builder;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.apache.commons.lang3.ClassUtils;
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
/*    */ public class RecursiveToStringStyle
/*    */   extends ToStringStyle
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public void appendDetail(StringBuffer paramStringBuffer, String paramString, Object paramObject)
/*    */   {
/* 72 */     if ((!ClassUtils.isPrimitiveWrapper(paramObject.getClass())) && (!String.class.equals(paramObject.getClass())) && (accept(paramObject.getClass())))
/*    */     {
/*    */ 
/* 75 */       paramStringBuffer.append(ReflectionToStringBuilder.toString(paramObject, this));
/*    */     } else {
/* 77 */       super.appendDetail(paramStringBuffer, paramString, paramObject);
/*    */     }
/*    */   }
/*    */   
/*    */   protected void appendDetail(StringBuffer paramStringBuffer, String paramString, Collection<?> paramCollection)
/*    */   {
/* 83 */     appendClassName(paramStringBuffer, paramCollection);
/* 84 */     appendIdentityHashCode(paramStringBuffer, paramCollection);
/* 85 */     appendDetail(paramStringBuffer, paramString, paramCollection.toArray());
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
/*    */   protected boolean accept(Class<?> paramClass)
/*    */   {
/* 98 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\lang3\builder\RecursiveToStringStyle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */