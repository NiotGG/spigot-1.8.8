/*     */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BindableCompound
/*     */   implements Bindable
/*     */ {
/*     */   private final Bindable[] items;
/*     */   private final BeanPropertyCompound compound;
/*     */   
/*     */   public BindableCompound(BeanPropertyCompound embProp, List<Bindable> list)
/*     */   {
/*  40 */     this.compound = embProp;
/*  41 */     this.items = ((Bindable[])list.toArray(new Bindable[list.size()]));
/*     */   }
/*     */   
/*     */   public String toString() {
/*  45 */     return "BindableCompound " + this.compound + " items:" + Arrays.toString(this.items);
/*     */   }
/*     */   
/*     */   public void dmlInsert(GenerateDmlRequest request, boolean checkIncludes) {
/*  49 */     dmlAppend(request, checkIncludes);
/*     */   }
/*     */   
/*     */   public void dmlAppend(GenerateDmlRequest request, boolean checkIncludes) {
/*  53 */     if ((checkIncludes) && (!request.isIncluded(this.compound))) {
/*  54 */       return;
/*     */     }
/*     */     
/*  57 */     for (int i = 0; i < this.items.length; i++) {
/*  58 */       this.items[i].dmlAppend(request, false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void dmlWhere(GenerateDmlRequest request, boolean checkIncludes, Object origBean)
/*     */   {
/*  66 */     if ((checkIncludes) && (!request.isIncludedWhere(this.compound))) {
/*  67 */       return;
/*     */     }
/*     */     
/*  70 */     Object valueObject = this.compound.getValue(origBean);
/*     */     
/*  72 */     for (int i = 0; i < this.items.length; i++) {
/*  73 */       this.items[i].dmlWhere(request, false, valueObject);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addChanged(PersistRequestBean<?> request, List<Bindable> list) {
/*  78 */     if (request.hasChanged(this.compound)) {
/*  79 */       list.add(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void dmlBind(BindableRequest bindRequest, boolean checkIncludes, Object bean) throws SQLException {
/*  84 */     if ((checkIncludes) && (!bindRequest.isIncluded(this.compound))) {
/*  85 */       return;
/*     */     }
/*     */     
/*  88 */     Object valueObject = this.compound.getValue(bean);
/*     */     
/*  90 */     for (int i = 0; i < this.items.length; i++) {
/*  91 */       this.items[i].dmlBind(bindRequest, false, valueObject);
/*     */     }
/*     */   }
/*     */   
/*     */   public void dmlBindWhere(BindableRequest bindRequest, boolean checkIncludes, Object bean) throws SQLException {
/*  96 */     if ((checkIncludes) && (!bindRequest.isIncludedWhere(this.compound))) {
/*  97 */       return;
/*     */     }
/*     */     
/* 100 */     Object valueObject = this.compound.getValue(bean);
/*     */     
/* 102 */     for (int i = 0; i < this.items.length; i++) {
/* 103 */       this.items[i].dmlBindWhere(bindRequest, false, valueObject);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableCompound.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */