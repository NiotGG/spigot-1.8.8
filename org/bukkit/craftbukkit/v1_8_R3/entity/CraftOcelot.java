/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityOcelot;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.Ocelot.Type;
/*    */ 
/*    */ public class CraftOcelot extends CraftTameableAnimal implements org.bukkit.entity.Ocelot
/*    */ {
/*    */   public CraftOcelot(CraftServer server, EntityOcelot wolf)
/*    */   {
/* 11 */     super(server, wolf);
/*    */   }
/*    */   
/*    */   public EntityOcelot getHandle()
/*    */   {
/* 16 */     return (EntityOcelot)this.entity;
/*    */   }
/*    */   
/*    */   public Ocelot.Type getCatType() {
/* 20 */     return Ocelot.Type.getType(getHandle().getCatType());
/*    */   }
/*    */   
/*    */   public void setCatType(Ocelot.Type type) {
/* 24 */     org.apache.commons.lang.Validate.notNull(type, "Cat type cannot be null");
/* 25 */     getHandle().setCatType(type.getId());
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType()
/*    */   {
/* 30 */     return org.bukkit.entity.EntityType.OCELOT;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftOcelot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */