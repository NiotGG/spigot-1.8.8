/*     */ package com.avaje.ebeaninternal.server.deploy.id;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanFkeyProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanProperty;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
/*     */ import com.avaje.ebeaninternal.server.deploy.IntersectionRow;
/*     */ import com.avaje.ebeaninternal.server.persist.dml.GenerateDmlRequest;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableRequest;
/*     */ import com.avaje.ebeaninternal.util.ValueUtil;
/*     */ import java.sql.SQLException;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImportedIdEmbedded
/*     */   implements ImportedId
/*     */ {
/*     */   final BeanPropertyAssoc<?> owner;
/*     */   final BeanPropertyAssocOne<?> foreignAssocOne;
/*     */   final ImportedIdSimple[] imported;
/*     */   
/*     */   public ImportedIdEmbedded(BeanPropertyAssoc<?> owner, BeanPropertyAssocOne<?> foreignAssocOne, ImportedIdSimple[] imported)
/*     */   {
/*  29 */     this.owner = owner;
/*  30 */     this.foreignAssocOne = foreignAssocOne;
/*  31 */     this.imported = imported;
/*     */   }
/*     */   
/*     */   public void addFkeys(String name)
/*     */   {
/*  36 */     BeanProperty[] embeddedProps = this.foreignAssocOne.getProperties();
/*     */     
/*  38 */     for (int i = 0; i < this.imported.length; i++) {
/*  39 */       String n = name + "." + this.foreignAssocOne.getName() + "." + embeddedProps[i].getName();
/*  40 */       BeanFkeyProperty fkey = new BeanFkeyProperty(null, n, this.imported[i].localDbColumn, this.foreignAssocOne.getDeployOrder());
/*  41 */       this.owner.getBeanDescriptor().add(fkey);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isScalar() {
/*  46 */     return false;
/*     */   }
/*     */   
/*     */   public String getLogicalName() {
/*  50 */     return this.owner.getName() + "." + this.foreignAssocOne.getName();
/*     */   }
/*     */   
/*     */   public String getDbColumn()
/*     */   {
/*  55 */     return null;
/*     */   }
/*     */   
/*     */   public void sqlAppend(DbSqlContext ctx) {
/*  59 */     for (int i = 0; i < this.imported.length; i++) {
/*  60 */       ctx.appendColumn(this.imported[i].localDbColumn);
/*     */     }
/*     */   }
/*     */   
/*     */   public void dmlAppend(GenerateDmlRequest request)
/*     */   {
/*  66 */     for (int i = 0; i < this.imported.length; i++) {
/*  67 */       request.appendColumn(this.imported[i].localDbColumn);
/*     */     }
/*     */   }
/*     */   
/*     */   public void dmlWhere(GenerateDmlRequest request, Object bean)
/*     */   {
/*  73 */     Object embeddedId = null;
/*  74 */     if (bean != null) {
/*  75 */       embeddedId = this.foreignAssocOne.getValue(bean);
/*     */     }
/*     */     
/*  78 */     if (embeddedId == null) {
/*  79 */       for (int i = 0; i < this.imported.length; i++) {
/*  80 */         if (this.imported[i].owner.isDbUpdatable()) {
/*  81 */           request.appendColumnIsNull(this.imported[i].localDbColumn);
/*     */         }
/*     */         
/*     */       }
/*     */     } else {
/*  86 */       for (int i = 0; i < this.imported.length; i++) {
/*  87 */         if (this.imported[i].owner.isDbUpdatable()) {
/*  88 */           Object value = this.imported[i].foreignProperty.getValue(embeddedId);
/*  89 */           if (value == null) {
/*  90 */             request.appendColumnIsNull(this.imported[i].localDbColumn);
/*     */           } else {
/*  92 */             request.appendColumn(this.imported[i].localDbColumn);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasChanged(Object bean, Object oldValues) {
/* 100 */     Object id = this.foreignAssocOne.getValue(bean);
/* 101 */     Object oldId = this.foreignAssocOne.getValue(oldValues);
/*     */     
/* 103 */     return !ValueUtil.areEqual(id, oldId);
/*     */   }
/*     */   
/*     */   public Object bind(BindableRequest request, Object bean, boolean bindNull) throws SQLException
/*     */   {
/* 108 */     Object embeddedId = null;
/*     */     
/* 110 */     if (bean != null) {
/* 111 */       embeddedId = this.foreignAssocOne.getValue(bean);
/*     */     }
/*     */     
/* 114 */     if (embeddedId == null) {
/* 115 */       for (int i = 0; i < this.imported.length; i++) {
/* 116 */         if (this.imported[i].owner.isUpdateable()) {
/* 117 */           request.bind(null, this.imported[i].foreignProperty, this.imported[i].localDbColumn, true);
/*     */         }
/*     */         
/*     */       }
/*     */     } else {
/* 122 */       for (int i = 0; i < this.imported.length; i++) {
/* 123 */         if (this.imported[i].owner.isUpdateable()) {
/* 124 */           Object scalarValue = this.imported[i].foreignProperty.getValue(embeddedId);
/* 125 */           request.bind(scalarValue, this.imported[i].foreignProperty, this.imported[i].localDbColumn, true);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 130 */     return null;
/*     */   }
/*     */   
/*     */   public void buildImport(IntersectionRow row, Object other)
/*     */   {
/* 135 */     Object embeddedId = this.foreignAssocOne.getValue(other);
/* 136 */     if (embeddedId == null) {
/* 137 */       String msg = "Foreign Key value null?";
/* 138 */       throw new PersistenceException(msg);
/*     */     }
/*     */     
/* 141 */     for (int i = 0; i < this.imported.length; i++) {
/* 142 */       Object scalarValue = this.imported[i].foreignProperty.getValue(embeddedId);
/* 143 */       row.put(this.imported[i].localDbColumn, scalarValue);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BeanProperty findMatchImport(String matchDbColumn)
/*     */   {
/* 153 */     BeanProperty p = null;
/* 154 */     for (int i = 0; i < this.imported.length; i++) {
/* 155 */       p = this.imported[i].findMatchImport(matchDbColumn);
/* 156 */       if (p != null) {
/* 157 */         return p;
/*     */       }
/*     */     }
/*     */     
/* 161 */     return p;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\id\ImportedIdEmbedded.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */