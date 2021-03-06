/*    */ package com.avaje.ebeaninternal.server.persist.dmlbind;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class FactoryVersion
/*    */ {
/*    */   public Bindable create(BeanDescriptor<?> desc)
/*    */   {
/* 43 */     List<Bindable> verList = new ArrayList();
/*    */     
/* 45 */     BeanProperty[] vers = desc.propertiesVersion();
/* 46 */     for (int i = 0; i < vers.length; i++) {
/* 47 */       verList.add(new BindableProperty(vers[i]));
/*    */     }
/*    */     
/*    */ 
/* 51 */     BeanPropertyAssocOne<?>[] embedded = desc.propertiesEmbedded();
/* 52 */     for (int j = 0; j < embedded.length; j++)
/*    */     {
/* 54 */       if (embedded[j].isEmbeddedVersion())
/*    */       {
/* 56 */         List<Bindable> bindList = new ArrayList();
/*    */         
/* 58 */         BeanProperty[] embProps = embedded[j].getProperties();
/*    */         
/* 60 */         for (int i = 0; i < embProps.length; i++) {
/* 61 */           if (embProps[i].isVersion()) {
/* 62 */             bindList.add(new BindableProperty(embProps[i]));
/*    */           }
/*    */         }
/*    */         
/* 66 */         verList.add(new BindableEmbedded(embedded[j], bindList));
/*    */       }
/*    */     }
/*    */     
/* 70 */     if (verList.size() == 0) {
/* 71 */       return null;
/*    */     }
/* 73 */     if (verList.size() == 1) {
/* 74 */       return (Bindable)verList.get(0);
/*    */     }
/*    */     
/* 77 */     return new BindableList(verList);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\FactoryVersion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */