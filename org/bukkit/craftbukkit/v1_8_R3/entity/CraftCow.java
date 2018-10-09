/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityCow;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.Cow;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftCow extends CraftAnimals implements Cow
/*    */ {
/*    */   public CraftCow(CraftServer server, EntityCow entity)
/*    */   {
/* 12 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityCow getHandle()
/*    */   {
/* 17 */     return (EntityCow)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 22 */     return "CraftCow";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 26 */     return EntityType.COW;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftCow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */