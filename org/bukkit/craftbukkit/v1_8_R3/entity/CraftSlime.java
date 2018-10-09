/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntitySlime;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Slime;
/*    */ 
/*    */ public class CraftSlime extends CraftLivingEntity implements Slime
/*    */ {
/*    */   public CraftSlime(CraftServer server, EntitySlime entity)
/*    */   {
/* 12 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 16 */     return getHandle().getSize();
/*    */   }
/*    */   
/*    */   public void setSize(int size) {
/* 20 */     getHandle().setSize(size);
/*    */   }
/*    */   
/*    */   public EntitySlime getHandle()
/*    */   {
/* 25 */     return (EntitySlime)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 30 */     return "CraftSlime";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 34 */     return EntityType.SLIME;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftSlime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */