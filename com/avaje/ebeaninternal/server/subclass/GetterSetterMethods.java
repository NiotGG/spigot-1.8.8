/*    */ package com.avaje.ebeaninternal.server.subclass;
/*    */ 
/*    */ import com.avaje.ebean.enhance.agent.ClassMeta;
/*    */ import com.avaje.ebean.enhance.agent.EnhanceConstants;
/*    */ import com.avaje.ebean.enhance.agent.FieldMeta;
/*    */ import com.avaje.ebean.enhance.asm.ClassVisitor;
/*    */ import com.avaje.ebean.enhance.asm.Opcodes;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GetterSetterMethods
/*    */   implements Opcodes, EnhanceConstants
/*    */ {
/*    */   public static void add(ClassVisitor cv, ClassMeta classMeta)
/*    */   {
/* 22 */     List<FieldMeta> localFields = classMeta.getLocalFields();
/* 23 */     for (int x = 0; x < localFields.size(); x++) {
/* 24 */       FieldMeta fieldMeta = (FieldMeta)localFields.get(x);
/* 25 */       fieldMeta.addPublicGetSetMethods(cv, classMeta, true);
/*    */     }
/*    */     
/* 28 */     List<FieldMeta> inheritedFields = classMeta.getInheritedFields();
/* 29 */     for (int i = 0; i < inheritedFields.size(); i++) {
/* 30 */       FieldMeta fieldMeta = (FieldMeta)inheritedFields.get(i);
/*    */       
/*    */ 
/* 33 */       fieldMeta.addPublicGetSetMethods(cv, classMeta, false);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\subclass\GetterSetterMethods.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */