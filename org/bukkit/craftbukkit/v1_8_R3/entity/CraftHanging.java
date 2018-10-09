/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityHanging;
/*    */ import net.minecraft.server.v1_8_R3.EnumDirection;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Hanging;
/*    */ 
/*    */ public class CraftHanging extends CraftEntity implements Hanging
/*    */ {
/*    */   public CraftHanging(CraftServer server, EntityHanging entity)
/*    */   {
/* 14 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public BlockFace getAttachedFace() {
/* 18 */     return getFacing().getOppositeFace();
/*    */   }
/*    */   
/*    */   public void setFacingDirection(BlockFace face) {
/* 22 */     setFacingDirection(face, false);
/*    */   }
/*    */   
/*    */   public boolean setFacingDirection(BlockFace face, boolean force) {
/* 26 */     EntityHanging hanging = getHandle();
/* 27 */     EnumDirection dir = hanging.direction;
/* 28 */     switch (face) {
/*    */     case EAST_NORTH_EAST: 
/*    */     default: 
/* 31 */       getHandle().setDirection(EnumDirection.SOUTH);
/* 32 */       break;
/*    */     case EAST_SOUTH_EAST: 
/* 34 */       getHandle().setDirection(EnumDirection.WEST);
/* 35 */       break;
/*    */     case DOWN: 
/* 37 */       getHandle().setDirection(EnumDirection.NORTH);
/* 38 */       break;
/*    */     case EAST: 
/* 40 */       getHandle().setDirection(EnumDirection.EAST);
/*    */     }
/*    */     
/* 43 */     if ((!force) && (!hanging.survives()))
/*    */     {
/* 45 */       hanging.setDirection(dir);
/* 46 */       return false;
/*    */     }
/* 48 */     return true;
/*    */   }
/*    */   
/*    */   public BlockFace getFacing() {
/* 52 */     EnumDirection direction = getHandle().direction;
/* 53 */     if (direction == null) return BlockFace.SELF;
/* 54 */     switch (direction) {
/*    */     case SOUTH: 
/*    */     default: 
/* 57 */       return BlockFace.SOUTH;
/*    */     case UP: 
/* 59 */       return BlockFace.WEST;
/*    */     case NORTH: 
/* 61 */       return BlockFace.NORTH;
/*    */     }
/* 63 */     return BlockFace.EAST;
/*    */   }
/*    */   
/*    */ 
/*    */   public EntityHanging getHandle()
/*    */   {
/* 69 */     return (EntityHanging)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 74 */     return "CraftHanging";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 78 */     return EntityType.UNKNOWN;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftHanging.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */