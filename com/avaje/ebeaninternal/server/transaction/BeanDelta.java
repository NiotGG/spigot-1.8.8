/*     */ package com.avaje.ebeaninternal.server.transaction;
/*     */ 
/*     */ import com.avaje.ebeaninternal.api.SpiEbeanServer;
/*     */ import com.avaje.ebeaninternal.server.cluster.BinaryMessage;
/*     */ import com.avaje.ebeaninternal.server.cluster.BinaryMessageList;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
/*     */ import com.avaje.ebeaninternal.server.type.ScalarType;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class BeanDelta
/*     */ {
/*     */   private final List<BeanDeltaProperty> properties;
/*     */   private final BeanDescriptor<?> beanDescriptor;
/*     */   private final Object id;
/*     */   
/*     */   public BeanDelta(BeanDescriptor<?> beanDescriptor, Object id)
/*     */   {
/*  43 */     this.beanDescriptor = beanDescriptor;
/*  44 */     this.id = id;
/*  45 */     this.properties = new ArrayList();
/*     */   }
/*     */   
/*     */   public BeanDescriptor<?> getBeanDescriptor() {
/*  49 */     return this.beanDescriptor;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  53 */     StringBuilder sb = new StringBuilder();
/*  54 */     sb.append("BeanDelta[");
/*  55 */     sb.append(this.beanDescriptor.getName()).append(":");
/*  56 */     sb.append(this.properties);
/*  57 */     sb.append("]");
/*  58 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public Object getId() {
/*  62 */     return this.id;
/*     */   }
/*     */   
/*     */   public void add(BeanProperty beanProperty, Object value) {
/*  66 */     this.properties.add(new BeanDeltaProperty(beanProperty, value));
/*     */   }
/*     */   
/*     */   public void add(BeanDeltaProperty propertyDelta) {
/*  70 */     this.properties.add(propertyDelta);
/*     */   }
/*     */   
/*     */   public void apply(Object bean)
/*     */   {
/*  75 */     for (int i = 0; i < this.properties.size(); i++) {
/*  76 */       ((BeanDeltaProperty)this.properties.get(i)).apply(bean);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static BeanDelta readBinaryMessage(SpiEbeanServer server, DataInput dataInput)
/*     */     throws IOException
/*     */   {
/*  85 */     String descriptorId = dataInput.readUTF();
/*  86 */     BeanDescriptor<?> desc = server.getBeanDescriptorById(descriptorId);
/*  87 */     Object id = desc.getIdBinder().readData(dataInput);
/*  88 */     BeanDelta bp = new BeanDelta(desc, id);
/*     */     
/*  90 */     int count = dataInput.readInt();
/*  91 */     for (int i = 0; i < count; i++) {
/*  92 */       String propName = dataInput.readUTF();
/*  93 */       BeanProperty beanProperty = desc.getBeanProperty(propName);
/*  94 */       Object value = beanProperty.getScalarType().readData(dataInput);
/*  95 */       bp.add(beanProperty, value);
/*     */     }
/*  97 */     return bp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void writeBinaryMessage(BinaryMessageList msgList)
/*     */     throws IOException
/*     */   {
/* 105 */     BinaryMessage m = new BinaryMessage(50);
/*     */     
/* 107 */     DataOutputStream os = m.getOs();
/* 108 */     os.writeInt(3);
/* 109 */     os.writeUTF(this.beanDescriptor.getDescriptorId());
/*     */     
/* 111 */     this.beanDescriptor.getIdBinder().writeData(os, this.id);
/* 112 */     os.writeInt(this.properties.size());
/*     */     
/* 114 */     for (int i = 0; i < this.properties.size(); i++) {
/* 115 */       ((BeanDeltaProperty)this.properties.get(i)).writeBinaryMessage(m);
/*     */     }
/*     */     
/* 118 */     os.flush();
/* 119 */     msgList.add(m);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\BeanDelta.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */