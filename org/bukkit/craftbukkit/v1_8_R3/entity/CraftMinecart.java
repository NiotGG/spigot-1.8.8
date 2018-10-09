/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.Block;
/*    */ import net.minecraft.server.v1_8_R3.EntityMinecartAbstract;
/*    */ import net.minecraft.server.v1_8_R3.IBlockData;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*    */ import org.bukkit.entity.Minecart;
/*    */ import org.bukkit.material.MaterialData;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public abstract class CraftMinecart extends CraftVehicle implements Minecart
/*    */ {
/*    */   public CraftMinecart(CraftServer server, EntityMinecartAbstract entity)
/*    */   {
/* 17 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public void setDamage(double damage) {
/* 21 */     getHandle().setDamage((float)damage);
/*    */   }
/*    */   
/*    */   public double getDamage() {
/* 25 */     return getHandle().getDamage();
/*    */   }
/*    */   
/*    */   public double getMaxSpeed() {
/* 29 */     return getHandle().maxSpeed;
/*    */   }
/*    */   
/*    */   public void setMaxSpeed(double speed) {
/* 33 */     if (speed >= 0.0D) {
/* 34 */       getHandle().maxSpeed = speed;
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isSlowWhenEmpty() {
/* 39 */     return getHandle().slowWhenEmpty;
/*    */   }
/*    */   
/*    */   public void setSlowWhenEmpty(boolean slow) {
/* 43 */     getHandle().slowWhenEmpty = slow;
/*    */   }
/*    */   
/*    */   public Vector getFlyingVelocityMod() {
/* 47 */     return getHandle().getFlyingVelocityMod();
/*    */   }
/*    */   
/*    */   public void setFlyingVelocityMod(Vector flying) {
/* 51 */     getHandle().setFlyingVelocityMod(flying);
/*    */   }
/*    */   
/*    */   public Vector getDerailedVelocityMod() {
/* 55 */     return getHandle().getDerailedVelocityMod();
/*    */   }
/*    */   
/*    */   public void setDerailedVelocityMod(Vector derailed) {
/* 59 */     getHandle().setDerailedVelocityMod(derailed);
/*    */   }
/*    */   
/*    */   public EntityMinecartAbstract getHandle()
/*    */   {
/* 64 */     return (EntityMinecartAbstract)this.entity;
/*    */   }
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
/*    */   public void setDisplayBlock(MaterialData material)
/*    */   {
/* 78 */     if (material != null) {
/* 79 */       IBlockData block = CraftMagicNumbers.getBlock(material.getItemTypeId()).fromLegacyData(material.getData());
/* 80 */       getHandle().setDisplayBlock(block);
/*    */     }
/*    */     else {
/* 83 */       getHandle().setDisplayBlock(net.minecraft.server.v1_8_R3.Blocks.AIR.getBlockData());
/* 84 */       getHandle().a(false);
/*    */     }
/*    */   }
/*    */   
/*    */   public MaterialData getDisplayBlock() {
/* 89 */     IBlockData blockData = getHandle().getDisplayBlock();
/* 90 */     return CraftMagicNumbers.getMaterial(blockData.getBlock()).getNewData((byte)blockData.getBlock().toLegacyData(blockData));
/*    */   }
/*    */   
/*    */   public void setDisplayBlockOffset(int offset) {
/* 94 */     getHandle().SetDisplayBlockOffset(offset);
/*    */   }
/*    */   
/*    */   public int getDisplayBlockOffset() {
/* 98 */     return getHandle().getDisplayBlockOffset();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftMinecart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */