/*    */ package org.bukkit.configuration.file;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import org.bukkit.configuration.serialization.ConfigurationSerialization;
/*    */ import org.yaml.snakeyaml.constructor.SafeConstructor;
/*    */ import org.yaml.snakeyaml.constructor.SafeConstructor.ConstructYamlMap;
/*    */ import org.yaml.snakeyaml.error.YAMLException;
/*    */ import org.yaml.snakeyaml.nodes.Node;
/*    */ import org.yaml.snakeyaml.nodes.Tag;
/*    */ 
/*    */ 
/*    */ public class YamlConstructor
/*    */   extends SafeConstructor
/*    */ {
/* 16 */   public YamlConstructor() { this.yamlConstructors.put(Tag.MAP, new ConstructCustomObject(null)); }
/*    */   
/*    */   private class ConstructCustomObject extends SafeConstructor.ConstructYamlMap {
/* 19 */     private ConstructCustomObject() { super(); }
/*    */     
/*    */     public Object construct(Node node) {
/* 22 */       if (node.isTwoStepsConstruction()) {
/* 23 */         throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
/*    */       }
/*    */       
/* 26 */       Map<?, ?> raw = (Map)super.construct(node);
/*    */       
/* 28 */       if (raw.containsKey("==")) {
/* 29 */         Map<String, Object> typed = new java.util.LinkedHashMap(raw.size());
/* 30 */         for (Map.Entry<?, ?> entry : raw.entrySet()) {
/* 31 */           typed.put(entry.getKey().toString(), entry.getValue());
/*    */         }
/*    */         try
/*    */         {
/* 35 */           return ConfigurationSerialization.deserializeObject(typed);
/*    */         } catch (IllegalArgumentException ex) {
/* 37 */           throw new YAMLException("Could not deserialize object", ex);
/*    */         }
/*    */       }
/*    */       
/* 41 */       return raw;
/*    */     }
/*    */     
/*    */     public void construct2ndStep(Node node, Object object)
/*    */     {
/* 46 */       throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\file\YamlConstructor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */