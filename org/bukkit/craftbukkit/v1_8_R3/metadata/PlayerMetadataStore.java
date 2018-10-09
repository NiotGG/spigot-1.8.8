/*    */ package org.bukkit.craftbukkit.v1_8_R3.metadata;
/*    */ 
/*    */ import org.bukkit.OfflinePlayer;
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
/*    */ 
/*    */ public class PlayerMetadataStore
/*    */   extends MetadataStoreBase<OfflinePlayer>
/*    */   implements MetadataStore<OfflinePlayer>
/*    */ {
/*    */   protected String disambiguate(OfflinePlayer player, String metadataKey)
/*    */   {
/* 21 */     return player.getName().toLowerCase() + ":" + metadataKey;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\metadata\PlayerMetadataStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */