/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityChicken;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.Chicken;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftChicken extends CraftAnimals implements Chicken
/*    */ {
/*    */   public CraftChicken(CraftServer server, EntityChicken entity)
/*    */   {
/* 12 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityChicken getHandle()
/*    */   {
/* 17 */     return (EntityChicken)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 22 */     return "CraftChicken";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 26 */     return EntityType.CHICKEN;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftChicken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */