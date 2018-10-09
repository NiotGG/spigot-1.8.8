/*    */ package org.yaml.snakeyaml.parser;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.yaml.snakeyaml.DumperOptions.Version;
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
/*    */ class VersionTagsTuple
/*    */ {
/*    */   private DumperOptions.Version version;
/*    */   private Map<String, String> tags;
/*    */   
/*    */   public VersionTagsTuple(DumperOptions.Version version, Map<String, String> tags)
/*    */   {
/* 30 */     this.version = version;
/* 31 */     this.tags = tags;
/*    */   }
/*    */   
/*    */   public DumperOptions.Version getVersion() {
/* 35 */     return this.version;
/*    */   }
/*    */   
/*    */   public Map<String, String> getTags() {
/* 39 */     return this.tags;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 44 */     return String.format("VersionTagsTuple<%s, %s>", new Object[] { this.version, this.tags });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\parser\VersionTagsTuple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */