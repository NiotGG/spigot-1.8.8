/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.Block;
/*    */ import net.minecraft.server.v1_8_R3.EntityFallingBlock;
/*    */ import net.minecraft.server.v1_8_R3.IBlockData;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftFallingSand extends CraftEntity implements org.bukkit.entity.FallingSand
/*    */ {
/*    */   public CraftFallingSand(CraftServer server, EntityFallingBlock entity)
/*    */   {
/* 14 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityFallingBlock getHandle()
/*    */   {
/* 19 */     return (EntityFallingBlock)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 24 */     return "CraftFallingSand";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 28 */     return EntityType.FALLING_BLOCK;
/*    */   }
/*    */   
/*    */   public Material getMaterial() {
/* 32 */     return Material.getMaterial(getBlockId());
/*    */   }
/*    */   
/*    */   public int getBlockId() {
/* 36 */     return org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers.getId(getHandle().getBlock().getBlock());
/*    */   }
/*    */   
/*    */   public byte getBlockData() {
/* 40 */     return (byte)getHandle().getBlock().getBlock().toLegacyData(getHandle().getBlock());
/*    */   }
/*    */   
/*    */   public boolean getDropItem() {
/* 44 */     return getHandle().dropItem;
/*    */   }
/*    */   
/*    */   public void setDropItem(boolean drop) {
/* 48 */     getHandle().dropItem = drop;
/*    */   }
/*    */   
/*    */   public boolean canHurtEntities()
/*    */   {
/* 53 */     return getHandle().hurtEntities;
/*    */   }
/*    */   
/*    */   public void setHurtEntities(boolean hurtEntities)
/*    */   {
/* 58 */     getHandle().hurtEntities = hurtEntities;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftFallingSand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */