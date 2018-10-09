/*    */ package org.bukkit.configuration.file;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ import org.bukkit.configuration.ConfigurationSection;
/*    */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*    */ import org.bukkit.configuration.serialization.ConfigurationSerialization;
/*    */ import org.yaml.snakeyaml.nodes.Node;
/*    */ import org.yaml.snakeyaml.representer.Representer;
/*    */ import org.yaml.snakeyaml.representer.SafeRepresenter.RepresentMap;
/*    */ 
/*    */ public class YamlRepresenter extends Representer
/*    */ {
/*    */   public YamlRepresenter()
/*    */   {
/* 16 */     this.multiRepresenters.put(ConfigurationSection.class, new RepresentConfigurationSection(null));
/* 17 */     this.multiRepresenters.put(ConfigurationSerializable.class, new RepresentConfigurationSerializable(null));
/*    */   }
/*    */   
/* 20 */   private class RepresentConfigurationSection extends SafeRepresenter.RepresentMap { private RepresentConfigurationSection() { super(); }
/*    */     
/*    */ 
/* 23 */     public Node representData(Object data) { return super.representData(((ConfigurationSection)data).getValues(false)); }
/*    */   }
/*    */   
/*    */   private class RepresentConfigurationSerializable extends SafeRepresenter.RepresentMap {
/* 27 */     private RepresentConfigurationSerializable() { super(); }
/*    */     
/*    */     public Node representData(Object data) {
/* 30 */       ConfigurationSerializable serializable = (ConfigurationSerializable)data;
/* 31 */       Map<String, Object> values = new LinkedHashMap();
/* 32 */       values.put("==", ConfigurationSerialization.getAlias(serializable.getClass()));
/* 33 */       values.putAll(serializable.serialize());
/*    */       
/* 35 */       return super.representData(values);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\file\YamlRepresenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */