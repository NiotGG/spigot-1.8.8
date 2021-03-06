/*    */ package com.avaje.ebeaninternal.server.ddl;
/*    */ 
/*    */ import com.avaje.ebean.config.dbplatform.DbDdlSyntax;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
/*    */ import com.avaje.ebeaninternal.server.deploy.TableJoin;
/*    */ 
/*    */ 
/*    */ public class DropTableVisitor
/*    */   implements BeanVisitor
/*    */ {
/*    */   final DdlGenContext ctx;
/*    */   final DbDdlSyntax ddlSyntax;
/*    */   
/*    */   public DropTableVisitor(DdlGenContext ctx)
/*    */   {
/* 18 */     this.ctx = ctx;
/* 19 */     this.ddlSyntax = ctx.getDdlSyntax();
/*    */   }
/*    */   
/*    */   protected void writeDropTable(BeanDescriptor<?> descriptor) {
/* 23 */     writeDropTable(descriptor.getBaseTable());
/*    */   }
/*    */   
/*    */   protected void writeDropTable(String tableName) {
/* 27 */     this.ctx.write("drop table ");
/* 28 */     if (this.ddlSyntax.getDropIfExists() != null) {
/* 29 */       this.ctx.write(this.ddlSyntax.getDropIfExists()).write(" ");
/*    */     }
/* 31 */     this.ctx.write(tableName);
/*    */     
/* 33 */     if (this.ddlSyntax.getDropTableCascade() != null) {
/* 34 */       this.ctx.write(" ").write(this.ddlSyntax.getDropTableCascade());
/*    */     }
/* 36 */     this.ctx.write(";").writeNewLine().writeNewLine();
/*    */   }
/*    */   
/*    */   public boolean visitBean(BeanDescriptor<?> descriptor)
/*    */   {
/* 41 */     if (!descriptor.isInheritanceRoot()) {
/* 42 */       return false;
/*    */     }
/*    */     
/* 45 */     writeDropTable(descriptor);
/*    */     
/* 47 */     dropIntersectionTables(descriptor);
/*    */     
/* 49 */     return true;
/*    */   }
/*    */   
/*    */   private void dropIntersectionTables(BeanDescriptor<?> descriptor)
/*    */   {
/* 54 */     BeanPropertyAssocMany<?>[] manyProps = descriptor.propertiesMany();
/* 55 */     for (int i = 0; i < manyProps.length; i++) {
/* 56 */       if (manyProps[i].isManyToMany()) {
/* 57 */         String intTable = manyProps[i].getIntersectionTableJoin().getTable();
/* 58 */         if (this.ctx.isProcessIntersectionTable(intTable)) {
/* 59 */           writeDropTable(intTable);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void visitBeanEnd(BeanDescriptor<?> descriptor) {}
/*    */   
/*    */   public void visitBegin()
/*    */   {
/* 70 */     if (this.ddlSyntax.getDisableReferentialIntegrity() != null) {
/* 71 */       this.ctx.write(this.ddlSyntax.getDisableReferentialIntegrity());
/* 72 */       this.ctx.write(";").writeNewLine().writeNewLine();
/*    */     }
/*    */   }
/*    */   
/*    */   public void visitEnd() {
/* 77 */     if (this.ddlSyntax.getEnableReferentialIntegrity() != null) {
/* 78 */       this.ctx.write(this.ddlSyntax.getEnableReferentialIntegrity());
/* 79 */       this.ctx.write(";").writeNewLine().writeNewLine();
/*    */     }
/*    */   }
/*    */   
/*    */   public PropertyVisitor visitProperty(BeanProperty p)
/*    */   {
/* 85 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\ddl\DropTableVisitor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */