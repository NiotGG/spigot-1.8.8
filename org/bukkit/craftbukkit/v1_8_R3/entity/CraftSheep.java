/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntitySheep;
/*    */ import net.minecraft.server.v1_8_R3.EnumColor;
/*    */ import org.bukkit.DyeColor;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftSheep extends CraftAnimals implements org.bukkit.entity.Sheep
/*    */ {
/*    */   public CraftSheep(CraftServer server, EntitySheep entity)
/*    */   {
/* 13 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public DyeColor getColor() {
/* 17 */     return DyeColor.getByWoolData((byte)getHandle().getColor().getColorIndex());
/*    */   }
/*    */   
/*    */   public void setColor(DyeColor color) {
/* 21 */     getHandle().setColor(EnumColor.fromColorIndex(color.getWoolData()));
/*    */   }
/*    */   
/*    */   public boolean isSheared() {
/* 25 */     return getHandle().isSheared();
/*    */   }
/*    */   
/*    */   public void setSheared(boolean flag) {
/* 29 */     getHandle().setSheared(flag);
/*    */   }
/*    */   
/*    */   public EntitySheep getHandle()
/*    */   {
/* 34 */     return (EntitySheep)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 39 */     return "CraftSheep";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 43 */     return EntityType.SHEEP;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftSheep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */