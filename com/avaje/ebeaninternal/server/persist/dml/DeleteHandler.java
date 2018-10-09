/*    */ package com.avaje.ebeaninternal.server.persist.dml;
/*    */ 
/*    */ import com.avaje.ebeaninternal.api.DerivedRelationshipData;
/*    */ import com.avaje.ebeaninternal.api.SpiTransaction;
/*    */ import com.avaje.ebeaninternal.server.core.PersistRequestBean;
/*    */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*    */ import com.avaje.ebeaninternal.server.type.DataBind;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ import java.util.Set;
/*    */ import javax.persistence.OptimisticLockException;
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
/*    */ public class DeleteHandler
/*    */   extends DmlHandler
/*    */ {
/*    */   private final DeleteMeta meta;
/*    */   
/*    */   public DeleteHandler(PersistRequestBean<?> persist, DeleteMeta meta)
/*    */   {
/* 41 */     super(persist, meta.isEmptyStringAsNull());
/* 42 */     this.meta = meta;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void bind()
/*    */     throws SQLException
/*    */   {
/* 50 */     this.sql = this.meta.getSql(this.persistRequest);
/*    */     
/* 52 */     SpiTransaction t = this.persistRequest.getTransaction();
/* 53 */     boolean isBatch = t.isBatchThisRequest();
/*    */     PreparedStatement pstmt;
/*    */     PreparedStatement pstmt;
/* 56 */     if (isBatch) {
/* 57 */       pstmt = getPstmt(t, this.sql, this.persistRequest, false);
/*    */     }
/*    */     else {
/* 60 */       logSql(this.sql);
/* 61 */       pstmt = getPstmt(t, this.sql, false);
/*    */     }
/* 63 */     this.dataBind = new DataBind(pstmt);
/*    */     
/* 65 */     bindLogAppend("Binding Delete [");
/* 66 */     bindLogAppend(this.meta.getTableName());
/* 67 */     bindLogAppend("] where[");
/*    */     
/* 69 */     this.meta.bind(this.persistRequest, this);
/*    */     
/* 71 */     bindLogAppend("]");
/*    */     
/*    */ 
/* 74 */     logBinding();
/*    */   }
/*    */   
/*    */ 
/*    */   public void execute()
/*    */     throws SQLException, OptimisticLockException
/*    */   {
/* 81 */     int rowCount = this.dataBind.executeUpdate();
/* 82 */     checkRowCount(rowCount);
/*    */   }
/*    */   
/*    */   public boolean isIncluded(BeanProperty prop)
/*    */   {
/* 87 */     return (prop.isDbUpdatable()) && (super.isIncluded(prop));
/*    */   }
/*    */   
/*    */   public boolean isIncludedWhere(BeanProperty prop)
/*    */   {
/* 92 */     return (prop.isDbUpdatable()) && ((this.loadedProps == null) || (this.loadedProps.contains(prop.getName())));
/*    */   }
/*    */   
/*    */   public void registerDerivedRelationship(DerivedRelationshipData assocBean) {
/* 96 */     throw new RuntimeException("Never called on delete");
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dml\DeleteHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */