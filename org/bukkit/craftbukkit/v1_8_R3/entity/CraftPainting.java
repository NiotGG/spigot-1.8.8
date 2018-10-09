/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityPainting;
/*    */ import net.minecraft.server.v1_8_R3.EntityPainting.EnumArt;
/*    */ import net.minecraft.server.v1_8_R3.WorldServer;
/*    */ import org.bukkit.Art;
/*    */ import org.bukkit.block.BlockFace;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftArt;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Painting;
/*    */ 
/*    */ public class CraftPainting extends CraftHanging implements Painting
/*    */ {
/*    */   public CraftPainting(CraftServer server, EntityPainting entity)
/*    */   {
/* 18 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public Art getArt() {
/* 22 */     EntityPainting.EnumArt art = getHandle().art;
/* 23 */     return CraftArt.NotchToBukkit(art);
/*    */   }
/*    */   
/*    */   public boolean setArt(Art art) {
/* 27 */     return setArt(art, false);
/*    */   }
/*    */   
/*    */   public boolean setArt(Art art, boolean force) {
/* 31 */     EntityPainting painting = getHandle();
/* 32 */     EntityPainting.EnumArt oldArt = painting.art;
/* 33 */     painting.art = CraftArt.BukkitToNotch(art);
/* 34 */     painting.setDirection(painting.direction);
/* 35 */     if ((!force) && (!painting.survives()))
/*    */     {
/* 37 */       painting.art = oldArt;
/* 38 */       painting.setDirection(painting.direction);
/* 39 */       return false;
/*    */     }
/* 41 */     update();
/* 42 */     return true;
/*    */   }
/*    */   
/*    */   public boolean setFacingDirection(BlockFace face, boolean force) {
/* 46 */     if (super.setFacingDirection(face, force)) {
/* 47 */       update();
/* 48 */       return true;
/*    */     }
/*    */     
/* 51 */     return false;
/*    */   }
/*    */   
/*    */   private void update() {
/* 55 */     WorldServer world = ((CraftWorld)getWorld()).getHandle();
/* 56 */     EntityPainting painting = new EntityPainting(world);
/* 57 */     painting.blockPosition = getHandle().blockPosition;
/* 58 */     painting.art = getHandle().art;
/* 59 */     painting.setDirection(getHandle().direction);
/* 60 */     getHandle().die();
/* 61 */     getHandle().velocityChanged = true;
/* 62 */     world.addEntity(painting);
/* 63 */     this.entity = painting;
/*    */   }
/*    */   
/*    */   public EntityPainting getHandle()
/*    */   {
/* 68 */     return (EntityPainting)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 73 */     return "CraftPainting{art=" + getArt() + "}";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 77 */     return EntityType.PAINTING;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftPainting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */