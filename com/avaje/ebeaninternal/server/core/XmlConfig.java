/*    */ package com.avaje.ebeaninternal.server.core;
/*    */ 
/*    */ import com.avaje.ebeaninternal.server.lib.util.Dnode;
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
/*    */ public class XmlConfig
/*    */ {
/*    */   private final List<Dnode> ebeanOrmXml;
/*    */   private final List<Dnode> ormXml;
/*    */   private final List<Dnode> allXml;
/*    */   
/*    */   public XmlConfig(List<Dnode> ormXml, List<Dnode> ebeanOrmXml)
/*    */   {
/* 39 */     this.ormXml = ormXml;
/* 40 */     this.ebeanOrmXml = ebeanOrmXml;
/* 41 */     this.allXml = new ArrayList(ormXml.size() + ebeanOrmXml.size());
/* 42 */     this.allXml.addAll(ormXml);
/* 43 */     this.allXml.addAll(ebeanOrmXml);
/*    */   }
/*    */   
/*    */   public List<Dnode> getEbeanOrmXml() {
/* 47 */     return this.ebeanOrmXml;
/*    */   }
/*    */   
/*    */   public List<Dnode> getOrmXml() {
/* 51 */     return this.ormXml;
/*    */   }
/*    */   
/*    */   public List<Dnode> find(List<Dnode> entityXml, String element) {
/* 55 */     ArrayList<Dnode> hits = new ArrayList();
/* 56 */     for (int i = 0; i < entityXml.size(); i++) {
/* 57 */       hits.addAll(((Dnode)entityXml.get(i)).findAll(element, 1));
/*    */     }
/* 59 */     return hits;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public List<Dnode> findEntityXml(String className)
/*    */   {
/* 70 */     ArrayList<Dnode> hits = new ArrayList(2);
/*    */     
/* 72 */     for (Dnode ormXml : this.allXml) {
/* 73 */       Dnode entityMappings = ormXml.find("entity-mappings");
/*    */       
/* 75 */       List<Dnode> entities = entityMappings.findAll("entity", "class", className, 1);
/* 76 */       if (entities.size() == 1) {
/* 77 */         hits.add(entities.get(0));
/*    */       }
/*    */     }
/*    */     
/* 81 */     return hits;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\XmlConfig.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */