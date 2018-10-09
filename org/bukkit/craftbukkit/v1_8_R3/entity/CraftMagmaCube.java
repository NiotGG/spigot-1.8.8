/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityMagmaCube;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.MagmaCube;
/*    */ 
/*    */ public class CraftMagmaCube extends CraftSlime implements MagmaCube
/*    */ {
/*    */   public CraftMagmaCube(CraftServer server, EntityMagmaCube entity)
/*    */   {
/* 12 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityMagmaCube getHandle() {
/* 16 */     return (EntityMagmaCube)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 21 */     return "CraftMagmaCube";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 25 */     return EntityType.MAGMA_CUBE;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftMagmaCube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */