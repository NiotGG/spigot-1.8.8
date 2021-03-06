/*    */ package com.avaje.ebeaninternal.server.ddl;
/*    */ 
/*    */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*    */ import com.avaje.ebean.config.dbplatform.DbIdentity;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ public class CreateSequenceVisitor
/*    */   implements BeanVisitor
/*    */ {
/* 13 */   private static final Logger logger = Logger.getLogger(DropSequenceVisitor.class.getName());
/*    */   
/*    */   private final DdlGenContext ctx;
/*    */   private final boolean supportsSequence;
/*    */   
/*    */   public CreateSequenceVisitor(DdlGenContext ctx)
/*    */   {
/* 20 */     this.ctx = ctx;
/* 21 */     this.supportsSequence = ctx.getDbPlatform().getDbIdentity().isSupportsSequence();
/*    */   }
/*    */   
/*    */   public boolean visitBean(BeanDescriptor<?> descriptor)
/*    */   {
/* 26 */     if (!descriptor.isInheritanceRoot()) {
/* 27 */       return false;
/*    */     }
/* 29 */     if (descriptor.getSequenceName() == null) {
/* 30 */       return false;
/*    */     }
/*    */     
/* 33 */     if (!this.supportsSequence)
/*    */     {
/* 35 */       String msg = "Not creating sequence " + descriptor.getSequenceName() + " on Bean " + descriptor.getName() + " as DatabasePlatform does not support sequences";
/*    */       
/* 37 */       logger.warning(msg);
/* 38 */       return false;
/*    */     }
/*    */     
/* 41 */     if (descriptor.getSequenceName() != null) {
/* 42 */       this.ctx.write("create sequence ");
/* 43 */       this.ctx.write(descriptor.getSequenceName());
/* 44 */       this.ctx.write(";").writeNewLine().writeNewLine();
/*    */     }
/* 46 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public void visitBeanEnd(BeanDescriptor<?> descriptor) {}
/*    */   
/*    */ 
/*    */   public void visitBegin() {}
/*    */   
/*    */ 
/*    */   public void visitEnd() {}
/*    */   
/*    */ 
/*    */   public PropertyVisitor visitProperty(BeanProperty p)
/*    */   {
/* 61 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ddl\CreateSequenceVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */