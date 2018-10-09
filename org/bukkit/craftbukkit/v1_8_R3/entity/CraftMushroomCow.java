/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityMushroomCow;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftMushroomCow extends CraftCow implements org.bukkit.entity.MushroomCow
/*    */ {
/*    */   public CraftMushroomCow(CraftServer server, EntityMushroomCow entity)
/*    */   {
/* 11 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityMushroomCow getHandle()
/*    */   {
/* 16 */     return (EntityMushroomCow)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 21 */     return "CraftMushroomCow";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 25 */     return EntityType.MUSHROOM_COW;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftMushroomCow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */