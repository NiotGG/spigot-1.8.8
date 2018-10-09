/*     */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*     */ 
/*     */ import net.minecraft.server.v1_8_R3.EntityArmorStand;
/*     */ import net.minecraft.server.v1_8_R3.Vector3f;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.inventory.EntityEquipment;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.util.EulerAngle;
/*     */ 
/*     */ public class CraftArmorStand extends CraftLivingEntity implements org.bukkit.entity.ArmorStand
/*     */ {
/*     */   public CraftArmorStand(CraftServer server, EntityArmorStand entity)
/*     */   {
/*  14 */     super(server, entity);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  19 */     return "CraftArmorStand";
/*     */   }
/*     */   
/*     */   public org.bukkit.entity.EntityType getType()
/*     */   {
/*  24 */     return org.bukkit.entity.EntityType.ARMOR_STAND;
/*     */   }
/*     */   
/*     */   public EntityArmorStand getHandle()
/*     */   {
/*  29 */     return (EntityArmorStand)super.getHandle();
/*     */   }
/*     */   
/*     */   public ItemStack getItemInHand()
/*     */   {
/*  34 */     return getEquipment().getItemInHand();
/*     */   }
/*     */   
/*     */   public void setItemInHand(ItemStack item)
/*     */   {
/*  39 */     getEquipment().setItemInHand(item);
/*     */   }
/*     */   
/*     */   public ItemStack getBoots()
/*     */   {
/*  44 */     return getEquipment().getBoots();
/*     */   }
/*     */   
/*     */   public void setBoots(ItemStack item)
/*     */   {
/*  49 */     getEquipment().setBoots(item);
/*     */   }
/*     */   
/*     */   public ItemStack getLeggings()
/*     */   {
/*  54 */     return getEquipment().getLeggings();
/*     */   }
/*     */   
/*     */   public void setLeggings(ItemStack item)
/*     */   {
/*  59 */     getEquipment().setLeggings(item);
/*     */   }
/*     */   
/*     */   public ItemStack getChestplate()
/*     */   {
/*  64 */     return getEquipment().getChestplate();
/*     */   }
/*     */   
/*     */   public void setChestplate(ItemStack item)
/*     */   {
/*  69 */     getEquipment().setChestplate(item);
/*     */   }
/*     */   
/*     */   public ItemStack getHelmet()
/*     */   {
/*  74 */     return getEquipment().getHelmet();
/*     */   }
/*     */   
/*     */   public void setHelmet(ItemStack item)
/*     */   {
/*  79 */     getEquipment().setHelmet(item);
/*     */   }
/*     */   
/*     */   public EulerAngle getBodyPose()
/*     */   {
/*  84 */     return fromNMS(getHandle().bodyPose);
/*     */   }
/*     */   
/*     */   public void setBodyPose(EulerAngle pose)
/*     */   {
/*  89 */     getHandle().setBodyPose(toNMS(pose));
/*     */   }
/*     */   
/*     */   public EulerAngle getLeftArmPose()
/*     */   {
/*  94 */     return fromNMS(getHandle().leftArmPose);
/*     */   }
/*     */   
/*     */   public void setLeftArmPose(EulerAngle pose)
/*     */   {
/*  99 */     getHandle().setLeftArmPose(toNMS(pose));
/*     */   }
/*     */   
/*     */   public EulerAngle getRightArmPose()
/*     */   {
/* 104 */     return fromNMS(getHandle().rightArmPose);
/*     */   }
/*     */   
/*     */   public void setRightArmPose(EulerAngle pose)
/*     */   {
/* 109 */     getHandle().setRightArmPose(toNMS(pose));
/*     */   }
/*     */   
/*     */   public EulerAngle getLeftLegPose()
/*     */   {
/* 114 */     return fromNMS(getHandle().leftLegPose);
/*     */   }
/*     */   
/*     */   public void setLeftLegPose(EulerAngle pose)
/*     */   {
/* 119 */     getHandle().setLeftLegPose(toNMS(pose));
/*     */   }
/*     */   
/*     */   public EulerAngle getRightLegPose()
/*     */   {
/* 124 */     return fromNMS(getHandle().rightLegPose);
/*     */   }
/*     */   
/*     */   public void setRightLegPose(EulerAngle pose)
/*     */   {
/* 129 */     getHandle().setRightLegPose(toNMS(pose));
/*     */   }
/*     */   
/*     */   public EulerAngle getHeadPose()
/*     */   {
/* 134 */     return fromNMS(getHandle().headPose);
/*     */   }
/*     */   
/*     */   public void setHeadPose(EulerAngle pose)
/*     */   {
/* 139 */     getHandle().setHeadPose(toNMS(pose));
/*     */   }
/*     */   
/*     */   public boolean hasBasePlate()
/*     */   {
/* 144 */     return !getHandle().hasBasePlate();
/*     */   }
/*     */   
/*     */   public void setBasePlate(boolean basePlate)
/*     */   {
/* 149 */     getHandle().setBasePlate(!basePlate);
/*     */   }
/*     */   
/*     */   public boolean hasGravity()
/*     */   {
/* 154 */     return !getHandle().hasGravity();
/*     */   }
/*     */   
/*     */   public void setGravity(boolean gravity)
/*     */   {
/* 159 */     getHandle().setGravity(!gravity);
/*     */   }
/*     */   
/*     */   public boolean isVisible()
/*     */   {
/* 164 */     return !getHandle().isInvisible();
/*     */   }
/*     */   
/*     */   public void setVisible(boolean visible)
/*     */   {
/* 169 */     getHandle().setInvisible(!visible);
/*     */   }
/*     */   
/*     */   public boolean hasArms()
/*     */   {
/* 174 */     return getHandle().hasArms();
/*     */   }
/*     */   
/*     */   public void setArms(boolean arms)
/*     */   {
/* 179 */     getHandle().setArms(arms);
/*     */   }
/*     */   
/*     */   public boolean isSmall()
/*     */   {
/* 184 */     return getHandle().isSmall();
/*     */   }
/*     */   
/*     */   public void setSmall(boolean small)
/*     */   {
/* 189 */     getHandle().setSmall(small);
/*     */   }
/*     */   
/*     */   private static EulerAngle fromNMS(Vector3f old) {
/* 193 */     return new EulerAngle(
/* 194 */       Math.toRadians(old.getX()), 
/* 195 */       Math.toRadians(old.getY()), 
/* 196 */       Math.toRadians(old.getZ()));
/*     */   }
/*     */   
/*     */   private static Vector3f toNMS(EulerAngle old)
/*     */   {
/* 201 */     return new Vector3f(
/* 202 */       (float)Math.toDegrees(old.getX()), 
/* 203 */       (float)Math.toDegrees(old.getY()), 
/* 204 */       (float)Math.toDegrees(old.getZ()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isMarker()
/*     */   {
/* 211 */     return getHandle().s();
/*     */   }
/*     */   
/*     */ 
/*     */   public void setMarker(boolean marker)
/*     */   {
/* 217 */     getHandle().n(marker);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftArmorStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */