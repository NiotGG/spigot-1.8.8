/*     */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*     */ 
/*     */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*     */ import net.minecraft.server.v1_8_R3.EntityItemFrame;
/*     */ import net.minecraft.server.v1_8_R3.EnumDirection;
/*     */ import net.minecraft.server.v1_8_R3.WorldServer;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Rotation;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.ItemFrame;
/*     */ 
/*     */ public class CraftItemFrame
/*     */   extends CraftHanging implements ItemFrame
/*     */ {
/*     */   public CraftItemFrame(CraftServer server, EntityItemFrame entity)
/*     */   {
/*  21 */     super(server, entity);
/*     */   }
/*     */   
/*     */   public boolean setFacingDirection(BlockFace face, boolean force) {
/*  25 */     if (!super.setFacingDirection(face, force)) {
/*  26 */       return false;
/*     */     }
/*     */     
/*  29 */     update();
/*     */     
/*  31 */     return true;
/*     */   }
/*     */   
/*     */   private void update() {
/*  35 */     EntityItemFrame old = getHandle();
/*     */     
/*  37 */     WorldServer world = ((CraftWorld)getWorld()).getHandle();
/*  38 */     BlockPosition position = old.getBlockPosition();
/*  39 */     EnumDirection direction = old.getDirection();
/*  40 */     net.minecraft.server.v1_8_R3.ItemStack item = old.getItem() != null ? old.getItem().cloneItemStack() : null;
/*     */     
/*  42 */     old.die();
/*     */     
/*  44 */     EntityItemFrame frame = new EntityItemFrame(world, position, direction);
/*  45 */     frame.setItem(item);
/*  46 */     world.addEntity(frame);
/*  47 */     this.entity = frame;
/*     */   }
/*     */   
/*     */   public void setItem(org.bukkit.inventory.ItemStack item)
/*     */   {
/*  52 */     if ((item == null) || (item.getTypeId() == 0)) {
/*  53 */       getHandle().setItem(null);
/*     */     } else {
/*  55 */       getHandle().setItem(CraftItemStack.asNMSCopy(item));
/*     */     }
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack getItem() {
/*  60 */     return CraftItemStack.asBukkitCopy(getHandle().getItem());
/*     */   }
/*     */   
/*     */   public Rotation getRotation() {
/*  64 */     return toBukkitRotation(getHandle().getRotation());
/*     */   }
/*     */   
/*     */   Rotation toBukkitRotation(int value)
/*     */   {
/*  69 */     switch (value) {
/*     */     case 0: 
/*  71 */       return Rotation.NONE;
/*     */     case 1: 
/*  73 */       return Rotation.CLOCKWISE_45;
/*     */     case 2: 
/*  75 */       return Rotation.CLOCKWISE;
/*     */     case 3: 
/*  77 */       return Rotation.CLOCKWISE_135;
/*     */     case 4: 
/*  79 */       return Rotation.FLIPPED;
/*     */     case 5: 
/*  81 */       return Rotation.FLIPPED_45;
/*     */     case 6: 
/*  83 */       return Rotation.COUNTER_CLOCKWISE;
/*     */     case 7: 
/*  85 */       return Rotation.COUNTER_CLOCKWISE_45;
/*     */     }
/*  87 */     throw new AssertionError("Unknown rotation " + value + " for " + getHandle());
/*     */   }
/*     */   
/*     */   public void setRotation(Rotation rotation)
/*     */   {
/*  92 */     Validate.notNull(rotation, "Rotation cannot be null");
/*  93 */     getHandle().setRotation(toInteger(rotation));
/*     */   }
/*     */   
/*     */   static int toInteger(Rotation rotation)
/*     */   {
/*  98 */     switch (rotation) {
/*     */     case CLOCKWISE: 
/* 100 */       return 0;
/*     */     case CLOCKWISE_135: 
/* 102 */       return 1;
/*     */     case CLOCKWISE_45: 
/* 104 */       return 2;
/*     */     case COUNTER_CLOCKWISE: 
/* 106 */       return 3;
/*     */     case COUNTER_CLOCKWISE_45: 
/* 108 */       return 4;
/*     */     case FLIPPED: 
/* 110 */       return 5;
/*     */     case FLIPPED_45: 
/* 112 */       return 6;
/*     */     case NONE: 
/* 114 */       return 7;
/*     */     }
/* 116 */     throw new IllegalArgumentException(rotation + " is not applicable to an ItemFrame");
/*     */   }
/*     */   
/*     */ 
/*     */   public EntityItemFrame getHandle()
/*     */   {
/* 122 */     return (EntityItemFrame)this.entity;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 127 */     return "CraftItemFrame{item=" + getItem() + ", rotation=" + getRotation() + "}";
/*     */   }
/*     */   
/*     */   public EntityType getType() {
/* 131 */     return EntityType.ITEM_FRAME;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftItemFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */