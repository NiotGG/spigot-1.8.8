/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.Block;
/*    */ import net.minecraft.server.v1_8_R3.EntityEnderman;
/*    */ import net.minecraft.server.v1_8_R3.IBlockData;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.material.MaterialData;
/*    */ 
/*    */ public class CraftEnderman extends CraftMonster implements org.bukkit.entity.Enderman
/*    */ {
/*    */   public CraftEnderman(CraftServer server, EntityEnderman entity)
/*    */   {
/* 14 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public MaterialData getCarriedMaterial() {
/* 18 */     IBlockData blockData = getHandle().getCarried();
/* 19 */     return org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers.getMaterial(blockData.getBlock()).getNewData((byte)blockData.getBlock().toLegacyData(blockData));
/*    */   }
/*    */   
/*    */   public void setCarriedMaterial(MaterialData data) {
/* 23 */     getHandle().setCarried(org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers.getBlock(data.getItemTypeId()).fromLegacyData(data.getData()));
/*    */   }
/*    */   
/*    */   public EntityEnderman getHandle()
/*    */   {
/* 28 */     return (EntityEnderman)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 33 */     return "CraftEnderman";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 37 */     return EntityType.ENDERMAN;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftEnderman.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */