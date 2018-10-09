/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.event.entity.PotionSplashEvent;
/*     */ 
/*     */ public class EntityPotion
/*     */   extends EntityProjectile
/*     */ {
/*     */   public ItemStack item;
/*     */   
/*     */   public EntityPotion(World world)
/*     */   {
/*  18 */     super(world);
/*     */   }
/*     */   
/*     */   public EntityPotion(World world, EntityLiving entityliving, int i) {
/*  22 */     this(world, entityliving, new ItemStack(Items.POTION, 1, i));
/*     */   }
/*     */   
/*     */   public EntityPotion(World world, EntityLiving entityliving, ItemStack itemstack) {
/*  26 */     super(world, entityliving);
/*  27 */     this.item = itemstack;
/*     */   }
/*     */   
/*     */   public EntityPotion(World world, double d0, double d1, double d2, ItemStack itemstack) {
/*  31 */     super(world, d0, d1, d2);
/*  32 */     this.item = itemstack;
/*     */   }
/*     */   
/*     */   protected float m() {
/*  36 */     return 0.05F;
/*     */   }
/*     */   
/*     */   protected float j() {
/*  40 */     return 0.5F;
/*     */   }
/*     */   
/*     */   protected float l() {
/*  44 */     return -20.0F;
/*     */   }
/*     */   
/*     */   public void setPotionValue(int i) {
/*  48 */     if (this.item == null) {
/*  49 */       this.item = new ItemStack(Items.POTION, 1, 0);
/*     */     }
/*     */     
/*  52 */     this.item.setData(i);
/*     */   }
/*     */   
/*     */   public int getPotionValue() {
/*  56 */     if (this.item == null) {
/*  57 */       this.item = new ItemStack(Items.POTION, 1, 0);
/*     */     }
/*     */     
/*  60 */     return this.item.getData();
/*     */   }
/*     */   
/*     */   protected void a(MovingObjectPosition movingobjectposition) {
/*  64 */     if (!this.world.isClientSide) {
/*  65 */       List list = Items.POTION.h(this.item);
/*     */       
/*     */ 
/*  68 */       AxisAlignedBB axisalignedbb = getBoundingBox().grow(4.0D, 2.0D, 4.0D);
/*  69 */       List list1 = this.world.a(EntityLiving.class, axisalignedbb);
/*     */       
/*     */ 
/*  72 */       Iterator iterator = list1.iterator();
/*     */       
/*     */ 
/*  75 */       HashMap<LivingEntity, Double> affected = new HashMap();
/*     */       
/*  77 */       while (iterator.hasNext()) {
/*  78 */         EntityLiving entityliving = (EntityLiving)iterator.next();
/*  79 */         double d0 = h(entityliving);
/*     */         
/*  81 */         if (d0 < 16.0D) {
/*  82 */           double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
/*     */           
/*  84 */           if (entityliving == movingobjectposition.entity) {
/*  85 */             d1 = 1.0D;
/*     */           }
/*     */           
/*     */ 
/*  89 */           affected.put((LivingEntity)entityliving.getBukkitEntity(), Double.valueOf(d1));
/*     */         }
/*     */       }
/*     */       
/*  93 */       PotionSplashEvent event = CraftEventFactory.callPotionSplashEvent(this, affected);
/*  94 */       if ((!event.isCancelled()) && (list != null) && (!list.isEmpty())) {
/*  95 */         for (LivingEntity victim : event.getAffectedEntities()) {
/*  96 */           if ((victim instanceof CraftLivingEntity))
/*     */           {
/*     */ 
/*     */ 
/* 100 */             EntityLiving entityliving = ((CraftLivingEntity)victim).getHandle();
/* 101 */             double d1 = event.getIntensity(victim);
/*     */             
/*     */ 
/* 104 */             Iterator iterator1 = list.iterator();
/*     */             
/* 106 */             while (iterator1.hasNext()) {
/* 107 */               MobEffect mobeffect = (MobEffect)iterator1.next();
/* 108 */               int i = mobeffect.getEffectId();
/*     */               
/*     */ 
/* 111 */               if ((this.world.pvpMode) || (!(getShooter() instanceof EntityPlayer)) || (!(entityliving instanceof EntityPlayer)) || (entityliving == getShooter()) || (
/*     */               
/* 113 */                 (i != 2) && (i != 4) && (i != 7) && (i != 15) && (i != 17) && (i != 18) && (i != 19)))
/*     */               {
/*     */ 
/*     */ 
/* 117 */                 if (MobEffectList.byId[i].isInstant()) {
/* 118 */                   MobEffectList.byId[i].applyInstantEffect(this, getShooter(), entityliving, mobeffect.getAmplifier(), d1);
/*     */                 } else {
/* 120 */                   int j = (int)(d1 * mobeffect.getDuration() + 0.5D);
/*     */                   
/* 122 */                   if (j > 20) {
/* 123 */                     entityliving.addEffect(new MobEffect(i, j, mobeffect.getAmplifier()));
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 132 */       this.world.triggerEffect(2002, new BlockPosition(this), getPotionValue());
/* 133 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound)
/*     */   {
/* 139 */     super.a(nbttagcompound);
/* 140 */     if (nbttagcompound.hasKeyOfType("Potion", 10)) {
/* 141 */       this.item = ItemStack.createStack(nbttagcompound.getCompound("Potion"));
/*     */     } else {
/* 143 */       setPotionValue(nbttagcompound.getInt("potionValue"));
/*     */     }
/*     */     
/* 146 */     if (this.item == null) {
/* 147 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 153 */     super.b(nbttagcompound);
/* 154 */     if (this.item != null) {
/* 155 */       nbttagcompound.set("Potion", this.item.save(new NBTTagCompound()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityPotion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */