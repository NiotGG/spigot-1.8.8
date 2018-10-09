/*     */ package com.avaje.ebeaninternal.server.persist.dml;
/*     */ 
/*     */ import com.avaje.ebean.config.dbplatform.DatabasePlatform;
/*     */ import com.avaje.ebean.config.dbplatform.DbEncrypt;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableId;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableList;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.BindableUnidirectional;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryAssocOnes;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryBaseProperties;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryEmbedded;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryId;
/*     */ import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryVersion;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetaFactory
/*     */ {
/*     */   private final FactoryBaseProperties baseFact;
/*     */   private final FactoryEmbedded embeddedFact;
/*  46 */   private final FactoryVersion versionFact = new FactoryVersion();
/*  47 */   private final FactoryAssocOnes assocOneFact = new FactoryAssocOnes();
/*     */   
/*  49 */   private final FactoryId idFact = new FactoryId();
/*     */   
/*     */ 
/*     */   private static final boolean includeLobs = true;
/*     */   
/*     */ 
/*     */   private final DatabasePlatform dbPlatform;
/*     */   
/*     */   private final boolean emptyStringAsNull;
/*     */   
/*     */ 
/*     */   public MetaFactory(DatabasePlatform dbPlatform)
/*     */   {
/*  62 */     this.dbPlatform = dbPlatform;
/*  63 */     this.emptyStringAsNull = dbPlatform.isTreatEmptyStringsAsNull();
/*     */     
/*     */ 
/*  66 */     DbEncrypt dbEncrypt = dbPlatform.getDbEncrypt();
/*  67 */     boolean bindEncryptDataFirst = dbEncrypt == null ? true : dbEncrypt.isBindEncryptDataFirst();
/*     */     
/*  69 */     this.baseFact = new FactoryBaseProperties(bindEncryptDataFirst);
/*  70 */     this.embeddedFact = new FactoryEmbedded(bindEncryptDataFirst);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public UpdateMeta createUpdate(BeanDescriptor<?> desc)
/*     */   {
/*  78 */     List<Bindable> setList = new ArrayList();
/*     */     
/*  80 */     this.baseFact.create(setList, desc, DmlMode.UPDATE, true);
/*  81 */     this.embeddedFact.create(setList, desc, DmlMode.UPDATE, true);
/*  82 */     this.assocOneFact.create(setList, desc, DmlMode.UPDATE);
/*     */     
/*  84 */     BindableId id = this.idFact.createId(desc);
/*     */     
/*  86 */     Bindable ver = this.versionFact.create(desc);
/*     */     
/*  88 */     List<Bindable> allList = new ArrayList();
/*     */     
/*  90 */     this.baseFact.create(allList, desc, DmlMode.WHERE, false);
/*  91 */     this.embeddedFact.create(allList, desc, DmlMode.WHERE, false);
/*  92 */     this.assocOneFact.create(allList, desc, DmlMode.WHERE);
/*     */     
/*  94 */     Bindable setBindable = new BindableList(setList);
/*  95 */     Bindable allBindable = new BindableList(allList);
/*     */     
/*  97 */     return new UpdateMeta(this.emptyStringAsNull, desc, setBindable, id, ver, allBindable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DeleteMeta createDelete(BeanDescriptor<?> desc)
/*     */   {
/* 105 */     BindableId id = this.idFact.createId(desc);
/*     */     
/* 107 */     Bindable ver = this.versionFact.create(desc);
/*     */     
/* 109 */     List<Bindable> allList = new ArrayList();
/*     */     
/* 111 */     this.baseFact.create(allList, desc, DmlMode.WHERE, false);
/* 112 */     this.embeddedFact.create(allList, desc, DmlMode.WHERE, false);
/* 113 */     this.assocOneFact.create(allList, desc, DmlMode.WHERE);
/*     */     
/* 115 */     Bindable allBindable = new BindableList(allList);
/*     */     
/* 117 */     return new DeleteMeta(this.emptyStringAsNull, desc, id, ver, allBindable);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public InsertMeta createInsert(BeanDescriptor<?> desc)
/*     */   {
/* 125 */     BindableId id = this.idFact.createId(desc);
/*     */     
/* 127 */     List<Bindable> allList = new ArrayList();
/*     */     
/* 129 */     this.baseFact.create(allList, desc, DmlMode.INSERT, true);
/* 130 */     this.embeddedFact.create(allList, desc, DmlMode.INSERT, true);
/* 131 */     this.assocOneFact.create(allList, desc, DmlMode.INSERT);
/*     */     
/* 133 */     Bindable allBindable = new BindableList(allList);
/*     */     
/* 135 */     BeanPropertyAssocOne<?> unidirectional = desc.getUnidirectional();
/*     */     Bindable shadowFkey;
/*     */     Bindable shadowFkey;
/* 138 */     if (unidirectional == null) {
/* 139 */       shadowFkey = null;
/*     */     } else {
/* 141 */       shadowFkey = new BindableUnidirectional(desc, unidirectional);
/*     */     }
/*     */     
/* 144 */     return new InsertMeta(this.dbPlatform, desc, shadowFkey, id, allBindable);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dml\MetaFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */