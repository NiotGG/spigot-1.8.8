/*    */ package org.bukkit.craftbukkit.v1_8_R3.metadata;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.metadata.MetadataStore;
/*    */ import org.bukkit.metadata.MetadataStoreBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityMetadataStore
/*    */   extends MetadataStoreBase<Entity>
/*    */   implements MetadataStore<Entity>
/*    */ {
/*    */   protected String disambiguate(Entity entity, String metadataKey)
/*    */   {
/* 21 */     return entity.getUniqueId().toString() + ":" + metadataKey;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\metadata\EntityMetadataStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */