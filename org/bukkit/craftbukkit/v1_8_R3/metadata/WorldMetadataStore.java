/*    */ package org.bukkit.craftbukkit.v1_8_R3.metadata;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.metadata.MetadataStore;
/*    */ import org.bukkit.metadata.MetadataStoreBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldMetadataStore
/*    */   extends MetadataStoreBase<World>
/*    */   implements MetadataStore<World>
/*    */ {
/*    */   protected String disambiguate(World world, String metadataKey)
/*    */   {
/* 20 */     return world.getUID().toString() + ":" + metadataKey;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\metadata\WorldMetadataStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */