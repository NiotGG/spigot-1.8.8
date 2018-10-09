/*    */ package com.avaje.ebeaninternal.server.transaction;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
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
/*    */ public class BeanDeltaProperty
/*    */ {
/*    */   private final BeanProperty beanProperty;
/*    */   private final Object value;
/*    */   
/*    */   public BeanDeltaProperty(BeanProperty beanProperty, Object value)
/*    */   {
/* 35 */     this.beanProperty = beanProperty;
/* 36 */     this.value = value;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 40 */     return this.beanProperty.getName() + ":" + this.value;
/*    */   }
/*    */   
/*    */   public void apply(Object bean) {
/* 44 */     this.beanProperty.setValue(bean, this.value);
/*    */   }
/*    */   
/*    */   public void writeBinaryMessage(BinaryMessage m) throws IOException
/*    */   {
/* 49 */     DataOutputStream os = m.getOs();
/* 50 */     os.writeUTF(this.beanProperty.getName());
/* 51 */     this.beanProperty.getScalarType().writeData(os, this.value);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\BeanDeltaProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */