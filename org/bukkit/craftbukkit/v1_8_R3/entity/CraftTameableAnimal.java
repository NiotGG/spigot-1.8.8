/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.server.v1_8_R3.EntityTameableAnimal;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.AnimalTamer;
/*    */ 
/*    */ public class CraftTameableAnimal extends CraftAnimals implements org.bukkit.entity.Tameable, org.bukkit.entity.Creature
/*    */ {
/*    */   public CraftTameableAnimal(CraftServer server, EntityTameableAnimal entity)
/*    */   {
/* 13 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityTameableAnimal getHandle()
/*    */   {
/* 18 */     return (EntityTameableAnimal)super.getHandle();
/*    */   }
/*    */   
/*    */   public UUID getOwnerUUID() {
/*    */     try {
/* 23 */       return UUID.fromString(getHandle().getOwnerUUID());
/*    */     } catch (IllegalArgumentException localIllegalArgumentException) {}
/* 25 */     return null;
/*    */   }
/*    */   
/*    */   public void setOwnerUUID(UUID uuid)
/*    */   {
/* 30 */     if (uuid == null) {
/* 31 */       getHandle().setOwnerUUID("");
/*    */     } else {
/* 33 */       getHandle().setOwnerUUID(uuid.toString());
/*    */     }
/*    */   }
/*    */   
/*    */   public AnimalTamer getOwner() {
/* 38 */     if (getOwnerUUID() == null) {
/* 39 */       return null;
/*    */     }
/*    */     
/* 42 */     AnimalTamer owner = getServer().getPlayer(getOwnerUUID());
/* 43 */     if (owner == null) {
/* 44 */       owner = getServer().getOfflinePlayer(getOwnerUUID());
/*    */     }
/*    */     
/* 47 */     return owner;
/*    */   }
/*    */   
/*    */   public boolean isTamed() {
/* 51 */     return getHandle().isTamed();
/*    */   }
/*    */   
/*    */   public void setOwner(AnimalTamer tamer) {
/* 55 */     if (tamer != null) {
/* 56 */       setTamed(true);
/* 57 */       getHandle().setGoalTarget(null, null, false);
/* 58 */       setOwnerUUID(tamer.getUniqueId());
/*    */     } else {
/* 60 */       setTamed(false);
/* 61 */       setOwnerUUID(null);
/*    */     }
/*    */   }
/*    */   
/*    */   public void setTamed(boolean tame) {
/* 66 */     getHandle().setTamed(tame);
/* 67 */     if (!tame) {
/* 68 */       setOwnerUUID(null);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isSitting() {
/* 73 */     return getHandle().isSitting();
/*    */   }
/*    */   
/*    */   public void setSitting(boolean sitting) {
/* 77 */     getHandle().getGoalSit().setSitting(sitting);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 82 */     return getClass().getSimpleName() + "{owner=" + getOwner() + ",tamed=" + isTamed() + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftTameableAnimal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */