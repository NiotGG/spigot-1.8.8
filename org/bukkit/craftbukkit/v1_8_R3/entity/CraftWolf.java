/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityWolf;
/*    */ import org.bukkit.DyeColor;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftWolf extends CraftTameableAnimal implements org.bukkit.entity.Wolf
/*    */ {
/*    */   public CraftWolf(CraftServer server, EntityWolf wolf)
/*    */   {
/* 12 */     super(server, wolf);
/*    */   }
/*    */   
/*    */   public boolean isAngry() {
/* 16 */     return getHandle().isAngry();
/*    */   }
/*    */   
/*    */   public void setAngry(boolean angry) {
/* 20 */     getHandle().setAngry(angry);
/*    */   }
/*    */   
/*    */   public EntityWolf getHandle()
/*    */   {
/* 25 */     return (EntityWolf)this.entity;
/*    */   }
/*    */   
/*    */   public EntityType getType()
/*    */   {
/* 30 */     return EntityType.WOLF;
/*    */   }
/*    */   
/*    */   public DyeColor getCollarColor() {
/* 34 */     return DyeColor.getByWoolData((byte)getHandle().getCollarColor().getColorIndex());
/*    */   }
/*    */   
/*    */   public void setCollarColor(DyeColor color) {
/* 38 */     getHandle().setCollarColor(net.minecraft.server.v1_8_R3.EnumColor.fromColorIndex(color.getWoolData()));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftWolf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */