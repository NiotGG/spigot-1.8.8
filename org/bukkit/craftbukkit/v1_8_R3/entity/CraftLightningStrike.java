/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityLightning;
/*    */ import org.bukkit.entity.LightningStrike.Spigot;
/*    */ 
/*    */ public class CraftLightningStrike extends CraftEntity implements org.bukkit.entity.LightningStrike
/*    */ {
/*    */   public CraftLightningStrike(org.bukkit.craftbukkit.v1_8_R3.CraftServer server, EntityLightning entity)
/*    */   {
/* 10 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public boolean isEffect() {
/* 14 */     return ((EntityLightning)super.getHandle()).isEffect;
/*    */   }
/*    */   
/*    */   public EntityLightning getHandle()
/*    */   {
/* 19 */     return (EntityLightning)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 24 */     return "CraftLightningStrike";
/*    */   }
/*    */   
/*    */   public org.bukkit.entity.EntityType getType() {
/* 28 */     return org.bukkit.entity.EntityType.LIGHTNING;
/*    */   }
/*    */   
/*    */ 
/* 32 */   private final LightningStrike.Spigot spigot = new LightningStrike.Spigot()
/*    */   {
/*    */ 
/*    */     public boolean isSilent()
/*    */     {
/* 37 */       return CraftLightningStrike.this.getHandle().isSilent;
/*    */     }
/*    */   };
/*    */   
/*    */   public LightningStrike.Spigot spigot()
/*    */   {
/* 43 */     return this.spigot;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftLightningStrike.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */