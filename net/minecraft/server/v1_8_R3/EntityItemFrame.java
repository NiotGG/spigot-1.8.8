/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ 
/*     */ public class EntityItemFrame extends EntityHanging
/*     */ {
/*   8 */   private float c = 1.0F;
/*     */   
/*     */   public EntityItemFrame(World world) {
/*  11 */     super(world);
/*     */   }
/*     */   
/*     */   public EntityItemFrame(World world, BlockPosition blockposition, EnumDirection enumdirection) {
/*  15 */     super(world, blockposition);
/*  16 */     setDirection(enumdirection);
/*     */   }
/*     */   
/*     */   protected void h() {
/*  20 */     getDataWatcher().add(8, 5);
/*  21 */     getDataWatcher().a(9, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public float ao() {
/*  25 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  29 */     if (isInvulnerable(damagesource))
/*  30 */       return false;
/*  31 */     if ((!damagesource.isExplosion()) && (getItem() != null)) {
/*  32 */       if (!this.world.isClientSide)
/*     */       {
/*  34 */         if ((CraftEventFactory.handleNonLivingEntityDamageEvent(this, damagesource, f, false)) || (this.dead)) {
/*  35 */           return true;
/*     */         }
/*     */         
/*  38 */         a(damagesource.getEntity(), false);
/*  39 */         setItem(null);
/*     */       }
/*     */       
/*  42 */       return true;
/*     */     }
/*  44 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */   
/*     */   public int l()
/*     */   {
/*  49 */     return 12;
/*     */   }
/*     */   
/*     */   public int m() {
/*  53 */     return 12;
/*     */   }
/*     */   
/*     */   public void b(Entity entity) {
/*  57 */     a(entity, true);
/*     */   }
/*     */   
/*     */   public void a(Entity entity, boolean flag) {
/*  61 */     if (this.world.getGameRules().getBoolean("doEntityDrops")) {
/*  62 */       ItemStack itemstack = getItem();
/*     */       
/*  64 */       if ((entity instanceof EntityHuman)) {
/*  65 */         EntityHuman entityhuman = (EntityHuman)entity;
/*     */         
/*  67 */         if (entityhuman.abilities.canInstantlyBuild) {
/*  68 */           b(itemstack);
/*  69 */           return;
/*     */         }
/*     */       }
/*     */       
/*  73 */       if (flag) {
/*  74 */         a(new ItemStack(Items.ITEM_FRAME), 0.0F);
/*     */       }
/*     */       
/*  77 */       if ((itemstack != null) && (this.random.nextFloat() < this.c)) {
/*  78 */         itemstack = itemstack.cloneItemStack();
/*  79 */         b(itemstack);
/*  80 */         a(itemstack, 0.0F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void b(ItemStack itemstack)
/*     */   {
/*  87 */     if (itemstack != null) {
/*  88 */       if (itemstack.getItem() == Items.FILLED_MAP) {
/*  89 */         WorldMap worldmap = ((ItemWorldMap)itemstack.getItem()).getSavedMap(itemstack, this.world);
/*     */         
/*  91 */         worldmap.decorations.remove(java.util.UUID.nameUUIDFromBytes(("frame-" + getId()).getBytes(org.apache.commons.codec.Charsets.US_ASCII)));
/*     */       }
/*     */       
/*  94 */       itemstack.a(null);
/*     */     }
/*     */   }
/*     */   
/*     */   public ItemStack getItem() {
/*  99 */     return getDataWatcher().getItemStack(8);
/*     */   }
/*     */   
/*     */   public void setItem(ItemStack itemstack) {
/* 103 */     setItem(itemstack, true);
/*     */   }
/*     */   
/*     */   private void setItem(ItemStack itemstack, boolean flag) {
/* 107 */     if (itemstack != null) {
/* 108 */       itemstack = itemstack.cloneItemStack();
/* 109 */       itemstack.count = 1;
/* 110 */       itemstack.a(this);
/*     */     }
/*     */     
/* 113 */     getDataWatcher().watch(8, itemstack);
/* 114 */     getDataWatcher().update(8);
/* 115 */     if ((flag) && (this.blockPosition != null)) {
/* 116 */       this.world.updateAdjacentComparators(this.blockPosition, Blocks.AIR);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getRotation()
/*     */   {
/* 122 */     return getDataWatcher().getByte(9);
/*     */   }
/*     */   
/*     */   public void setRotation(int i) {
/* 126 */     setRotation(i, true);
/*     */   }
/*     */   
/*     */   private void setRotation(int i, boolean flag) {
/* 130 */     getDataWatcher().watch(9, Byte.valueOf((byte)(i % 8)));
/* 131 */     if ((flag) && (this.blockPosition != null)) {
/* 132 */       this.world.updateAdjacentComparators(this.blockPosition, Blocks.AIR);
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 138 */     if (getItem() != null) {
/* 139 */       nbttagcompound.set("Item", getItem().save(new NBTTagCompound()));
/* 140 */       nbttagcompound.setByte("ItemRotation", (byte)getRotation());
/* 141 */       nbttagcompound.setFloat("ItemDropChance", this.c);
/*     */     }
/*     */     
/* 144 */     super.b(nbttagcompound);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 148 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Item");
/*     */     
/* 150 */     if ((nbttagcompound1 != null) && (!nbttagcompound1.isEmpty())) {
/* 151 */       setItem(ItemStack.createStack(nbttagcompound1), false);
/* 152 */       setRotation(nbttagcompound.getByte("ItemRotation"), false);
/* 153 */       if (nbttagcompound.hasKeyOfType("ItemDropChance", 99)) {
/* 154 */         this.c = nbttagcompound.getFloat("ItemDropChance");
/*     */       }
/*     */       
/* 157 */       if (nbttagcompound.hasKey("Direction")) {
/* 158 */         setRotation(getRotation() * 2, false);
/*     */       }
/*     */     }
/*     */     
/* 162 */     super.a(nbttagcompound);
/*     */   }
/*     */   
/*     */   public boolean e(EntityHuman entityhuman) {
/* 166 */     if (getItem() == null) {
/* 167 */       ItemStack itemstack = entityhuman.bA();
/*     */       
/* 169 */       if ((itemstack != null) && (!this.world.isClientSide)) {
/* 170 */         setItem(itemstack);
/* 171 */         if (!entityhuman.abilities.canInstantlyBuild) if (--itemstack.count <= 0) {
/* 172 */             entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
/*     */           }
/*     */       }
/* 175 */     } else if (!this.world.isClientSide) {
/* 176 */       setRotation(getRotation() + 1);
/*     */     }
/*     */     
/* 179 */     return true;
/*     */   }
/*     */   
/*     */   public int q() {
/* 183 */     return getItem() == null ? 0 : getRotation() % 8 + 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityItemFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */