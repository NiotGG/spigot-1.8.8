/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.potion.CraftPotionEffectType;
/*     */ import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
/*     */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ public class MobEffectList
/*     */ {
/*  17 */   public static final MobEffectList[] byId = new MobEffectList[32];
/*  18 */   private static final Map<MinecraftKey, MobEffectList> I = Maps.newHashMap();
/*  19 */   public static final MobEffectList b = null;
/*  20 */   public static final MobEffectList FASTER_MOVEMENT = new MobEffectList(1, new MinecraftKey("speed"), false, 8171462).c("potion.moveSpeed").b(0, 0).a(GenericAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224D, 2);
/*  21 */   public static final MobEffectList SLOWER_MOVEMENT = new MobEffectList(2, new MinecraftKey("slowness"), true, 5926017).c("potion.moveSlowdown").b(1, 0).a(GenericAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448D, 2);
/*  22 */   public static final MobEffectList FASTER_DIG = new MobEffectList(3, new MinecraftKey("haste"), false, 14270531).c("potion.digSpeed").b(2, 0).a(1.5D);
/*  23 */   public static final MobEffectList SLOWER_DIG = new MobEffectList(4, new MinecraftKey("mining_fatigue"), true, 4866583).c("potion.digSlowDown").b(3, 0);
/*  24 */   public static final MobEffectList INCREASE_DAMAGE = new MobEffectAttackDamage(5, new MinecraftKey("strength"), false, 9643043).c("potion.damageBoost").b(4, 0).a(GenericAttributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 2.5D, 2);
/*  25 */   public static final MobEffectList HEAL = new InstantMobEffect(6, new MinecraftKey("instant_health"), false, 16262179).c("potion.heal");
/*  26 */   public static final MobEffectList HARM = new InstantMobEffect(7, new MinecraftKey("instant_damage"), true, 4393481).c("potion.harm");
/*  27 */   public static final MobEffectList JUMP = new MobEffectList(8, new MinecraftKey("jump_boost"), false, 2293580).c("potion.jump").b(2, 1);
/*  28 */   public static final MobEffectList CONFUSION = new MobEffectList(9, new MinecraftKey("nausea"), true, 5578058).c("potion.confusion").b(3, 1).a(0.25D);
/*  29 */   public static final MobEffectList REGENERATION = new MobEffectList(10, new MinecraftKey("regeneration"), false, 13458603).c("potion.regeneration").b(7, 0).a(0.25D);
/*  30 */   public static final MobEffectList RESISTANCE = new MobEffectList(11, new MinecraftKey("resistance"), false, 10044730).c("potion.resistance").b(6, 1);
/*  31 */   public static final MobEffectList FIRE_RESISTANCE = new MobEffectList(12, new MinecraftKey("fire_resistance"), false, 14981690).c("potion.fireResistance").b(7, 1);
/*  32 */   public static final MobEffectList WATER_BREATHING = new MobEffectList(13, new MinecraftKey("water_breathing"), false, 3035801).c("potion.waterBreathing").b(0, 2);
/*  33 */   public static final MobEffectList INVISIBILITY = new MobEffectList(14, new MinecraftKey("invisibility"), false, 8356754).c("potion.invisibility").b(0, 1);
/*  34 */   public static final MobEffectList BLINDNESS = new MobEffectList(15, new MinecraftKey("blindness"), true, 2039587).c("potion.blindness").b(5, 1).a(0.25D);
/*  35 */   public static final MobEffectList NIGHT_VISION = new MobEffectList(16, new MinecraftKey("night_vision"), false, 2039713).c("potion.nightVision").b(4, 1);
/*  36 */   public static final MobEffectList HUNGER = new MobEffectList(17, new MinecraftKey("hunger"), true, 5797459).c("potion.hunger").b(1, 1);
/*  37 */   public static final MobEffectList WEAKNESS = new MobEffectAttackDamage(18, new MinecraftKey("weakness"), true, 4738376).c("potion.weakness").b(5, 0).a(GenericAttributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0D, 0);
/*  38 */   public static final MobEffectList POISON = new MobEffectList(19, new MinecraftKey("poison"), true, 5149489).c("potion.poison").b(6, 0).a(0.25D);
/*  39 */   public static final MobEffectList WITHER = new MobEffectList(20, new MinecraftKey("wither"), true, 3484199).c("potion.wither").b(1, 2).a(0.25D);
/*  40 */   public static final MobEffectList HEALTH_BOOST = new MobEffectHealthBoost(21, new MinecraftKey("health_boost"), false, 16284963).c("potion.healthBoost").b(2, 2).a(GenericAttributes.maxHealth, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, 0);
/*  41 */   public static final MobEffectList ABSORBTION = new MobEffectAbsorption(22, new MinecraftKey("absorption"), false, 2445989).c("potion.absorption").b(2, 2);
/*  42 */   public static final MobEffectList SATURATION = new InstantMobEffect(23, new MinecraftKey("saturation"), false, 16262179).c("potion.saturation");
/*  43 */   public static final MobEffectList z = null;
/*  44 */   public static final MobEffectList A = null;
/*  45 */   public static final MobEffectList B = null;
/*  46 */   public static final MobEffectList C = null;
/*  47 */   public static final MobEffectList D = null;
/*  48 */   public static final MobEffectList E = null;
/*  49 */   public static final MobEffectList F = null;
/*  50 */   public static final MobEffectList G = null;
/*     */   public final int id;
/*  52 */   private final Map<IAttribute, AttributeModifier> J = Maps.newHashMap();
/*     */   private final boolean K;
/*     */   private final int L;
/*  55 */   private String M = "";
/*  56 */   private int N = -1;
/*     */   private double O;
/*     */   private boolean P;
/*     */   
/*     */   protected MobEffectList(int i, MinecraftKey minecraftkey, boolean flag, int j) {
/*  61 */     this.id = i;
/*  62 */     byId[i] = this;
/*  63 */     I.put(minecraftkey, this);
/*  64 */     this.K = flag;
/*  65 */     if (flag) {
/*  66 */       this.O = 0.5D;
/*     */     } else {
/*  68 */       this.O = 1.0D;
/*     */     }
/*     */     
/*  71 */     this.L = j;
/*  72 */     PotionEffectType.registerPotionEffectType(new CraftPotionEffectType(this));
/*     */   }
/*     */   
/*     */   public static MobEffectList b(String s) {
/*  76 */     return (MobEffectList)I.get(new MinecraftKey(s));
/*     */   }
/*     */   
/*     */   public static Set<MinecraftKey> c() {
/*  80 */     return I.keySet();
/*     */   }
/*     */   
/*     */   protected MobEffectList b(int i, int j) {
/*  84 */     this.N = (i + j * 8);
/*  85 */     return this;
/*     */   }
/*     */   
/*     */   public int getId() {
/*  89 */     return this.id;
/*     */   }
/*     */   
/*     */   public void tick(EntityLiving entityliving, int i) {
/*  93 */     if (this.id == REGENERATION.id) {
/*  94 */       if (entityliving.getHealth() < entityliving.getMaxHealth()) {
/*  95 */         entityliving.heal(1.0F, EntityRegainHealthEvent.RegainReason.MAGIC_REGEN);
/*     */       }
/*  97 */     } else if (this.id == POISON.id) {
/*  98 */       if (entityliving.getHealth() > 1.0F) {
/*  99 */         entityliving.damageEntity(CraftEventFactory.POISON, 1.0F);
/*     */       }
/* 101 */     } else if (this.id == WITHER.id) {
/* 102 */       entityliving.damageEntity(DamageSource.WITHER, 1.0F);
/* 103 */     } else if ((this.id == HUNGER.id) && ((entityliving instanceof EntityHuman))) {
/* 104 */       ((EntityHuman)entityliving).applyExhaustion(0.025F * (i + 1));
/* 105 */     } else if ((this.id == SATURATION.id) && ((entityliving instanceof EntityHuman))) {
/* 106 */       if (!entityliving.world.isClientSide)
/*     */       {
/* 108 */         EntityHuman entityhuman = (EntityHuman)entityliving;
/* 109 */         int oldFoodLevel = entityhuman.getFoodData().foodLevel;
/*     */         
/* 111 */         FoodLevelChangeEvent event = CraftEventFactory.callFoodLevelChangeEvent(entityhuman, i + 1 + oldFoodLevel);
/*     */         
/* 113 */         if (!event.isCancelled()) {
/* 114 */           entityhuman.getFoodData().eat(event.getFoodLevel() - oldFoodLevel, 1.0F);
/*     */         }
/*     */         
/* 117 */         ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutUpdateHealth(((EntityPlayer)entityhuman).getBukkitEntity().getScaledHealth(), entityhuman.getFoodData().foodLevel, entityhuman.getFoodData().saturationLevel));
/*     */       }
/*     */     }
/* 120 */     else if (((this.id != HEAL.id) || (entityliving.bm())) && ((this.id != HARM.id) || (!entityliving.bm()))) {
/* 121 */       if (((this.id == HARM.id) && (!entityliving.bm())) || ((this.id == HEAL.id) && (entityliving.bm()))) {
/* 122 */         entityliving.damageEntity(DamageSource.MAGIC, 6 << i);
/*     */       }
/*     */     } else {
/* 125 */       entityliving.heal(Math.max(4 << i, 0), EntityRegainHealthEvent.RegainReason.MAGIC);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void applyInstantEffect(Entity entity, Entity entity1, EntityLiving entityliving, int i, double d0)
/*     */   {
/* 133 */     if (((this.id != HEAL.id) || (entityliving.bm())) && ((this.id != HARM.id) || (!entityliving.bm()))) {
/* 134 */       if (((this.id == HARM.id) && (!entityliving.bm())) || ((this.id == HEAL.id) && (entityliving.bm()))) {
/* 135 */         int j = (int)(d0 * (6 << i) + 0.5D);
/* 136 */         if (entity == null) {
/* 137 */           entityliving.damageEntity(DamageSource.MAGIC, j);
/*     */         } else {
/* 139 */           entityliving.damageEntity(DamageSource.b(entity, entity1), j);
/*     */         }
/*     */       }
/*     */     } else {
/* 143 */       int j = (int)(d0 * (4 << i) + 0.5D);
/* 144 */       entityliving.heal(j, EntityRegainHealthEvent.RegainReason.MAGIC);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isInstant()
/*     */   {
/* 150 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean a(int i, int j)
/*     */   {
/* 156 */     if (this.id == REGENERATION.id) {
/* 157 */       int k = 50 >> j;
/* 158 */       return i % k == 0; }
/* 159 */     if (this.id == POISON.id) {
/* 160 */       int k = 25 >> j;
/* 161 */       return i % k == 0; }
/* 162 */     if (this.id == WITHER.id) {
/* 163 */       int k = 40 >> j;
/* 164 */       return i % k == 0;
/*     */     }
/* 166 */     return this.id == HUNGER.id;
/*     */   }
/*     */   
/*     */   public MobEffectList c(String s)
/*     */   {
/* 171 */     this.M = s;
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   public String a() {
/* 176 */     return this.M;
/*     */   }
/*     */   
/*     */   protected MobEffectList a(double d0) {
/* 180 */     this.O = d0;
/* 181 */     return this;
/*     */   }
/*     */   
/*     */   public double getDurationModifier() {
/* 185 */     return this.O;
/*     */   }
/*     */   
/*     */   public boolean j() {
/* 189 */     return this.P;
/*     */   }
/*     */   
/*     */   public int k() {
/* 193 */     return this.L;
/*     */   }
/*     */   
/*     */   public MobEffectList a(IAttribute iattribute, String s, double d0, int i) {
/* 197 */     AttributeModifier attributemodifier = new AttributeModifier(java.util.UUID.fromString(s), a(), d0, i);
/*     */     
/* 199 */     this.J.put(iattribute, attributemodifier);
/* 200 */     return this;
/*     */   }
/*     */   
/*     */   public void a(EntityLiving entityliving, AttributeMapBase attributemapbase, int i) {
/* 204 */     Iterator iterator = this.J.entrySet().iterator();
/*     */     
/* 206 */     while (iterator.hasNext()) {
/* 207 */       Map.Entry entry = (Map.Entry)iterator.next();
/* 208 */       AttributeInstance attributeinstance = attributemapbase.a((IAttribute)entry.getKey());
/*     */       
/* 210 */       if (attributeinstance != null) {
/* 211 */         attributeinstance.c((AttributeModifier)entry.getValue());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(EntityLiving entityliving, AttributeMapBase attributemapbase, int i)
/*     */   {
/* 218 */     Iterator iterator = this.J.entrySet().iterator();
/*     */     
/* 220 */     while (iterator.hasNext()) {
/* 221 */       Map.Entry entry = (Map.Entry)iterator.next();
/* 222 */       AttributeInstance attributeinstance = attributemapbase.a((IAttribute)entry.getKey());
/*     */       
/* 224 */       if (attributeinstance != null) {
/* 225 */         AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
/*     */         
/* 227 */         attributeinstance.c(attributemodifier);
/* 228 */         attributeinstance.b(new AttributeModifier(attributemodifier.a(), a() + " " + i, a(i, attributemodifier), attributemodifier.c()));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public double a(int i, AttributeModifier attributemodifier)
/*     */   {
/* 235 */     return attributemodifier.d() * (i + 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\MobEffectList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */