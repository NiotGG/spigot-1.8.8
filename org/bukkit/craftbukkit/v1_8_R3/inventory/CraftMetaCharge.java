/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*     */ import org.bukkit.FireworkEffect;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.inventory.meta.FireworkEffectMeta;
/*     */ 
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ class CraftMetaCharge
/*     */   extends CraftMetaItem
/*     */   implements FireworkEffectMeta
/*     */ {
/*  17 */   static final CraftMetaItem.ItemMetaKey EXPLOSION = new CraftMetaItem.ItemMetaKey("Explosion", "firework-effect");
/*     */   private FireworkEffect effect;
/*     */   
/*     */   CraftMetaCharge(CraftMetaItem meta)
/*     */   {
/*  22 */     super(meta);
/*     */     
/*  24 */     if ((meta instanceof CraftMetaCharge)) {
/*  25 */       this.effect = ((CraftMetaCharge)meta).effect;
/*     */     }
/*     */   }
/*     */   
/*     */   CraftMetaCharge(Map<String, Object> map) {
/*  30 */     super(map);
/*     */     
/*  32 */     setEffect((FireworkEffect)CraftMetaItem.SerializableMeta.getObject(FireworkEffect.class, map, EXPLOSION.BUKKIT, true));
/*     */   }
/*     */   
/*     */   CraftMetaCharge(NBTTagCompound tag) {
/*  36 */     super(tag);
/*     */     
/*  38 */     if (tag.hasKey(EXPLOSION.NBT)) {
/*  39 */       this.effect = CraftMetaFirework.getEffect(tag.getCompound(EXPLOSION.NBT));
/*     */     }
/*     */   }
/*     */   
/*     */   public void setEffect(FireworkEffect effect)
/*     */   {
/*  45 */     this.effect = effect;
/*     */   }
/*     */   
/*     */   public boolean hasEffect()
/*     */   {
/*  50 */     return this.effect != null;
/*     */   }
/*     */   
/*     */   public FireworkEffect getEffect()
/*     */   {
/*  55 */     return this.effect;
/*     */   }
/*     */   
/*     */   void applyToItem(NBTTagCompound itemTag)
/*     */   {
/*  60 */     super.applyToItem(itemTag);
/*     */     
/*  62 */     if (hasEffect()) {
/*  63 */       itemTag.set(EXPLOSION.NBT, CraftMetaFirework.getExplosion(this.effect));
/*     */     }
/*     */   }
/*     */   
/*     */   boolean applicableTo(Material type)
/*     */   {
/*  69 */     switch (type) {
/*     */     case STONE_SPADE: 
/*  71 */       return true;
/*     */     }
/*  73 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   boolean isEmpty()
/*     */   {
/*  79 */     return (super.isEmpty()) && (!hasChargeMeta());
/*     */   }
/*     */   
/*     */   boolean hasChargeMeta() {
/*  83 */     return hasEffect();
/*     */   }
/*     */   
/*     */   boolean equalsCommon(CraftMetaItem meta)
/*     */   {
/*  88 */     if (!super.equalsCommon(meta)) {
/*  89 */       return false;
/*     */     }
/*  91 */     if ((meta instanceof CraftMetaCharge)) {
/*  92 */       CraftMetaCharge that = (CraftMetaCharge)meta;
/*     */       
/*  94 */       return (that.hasEffect()) && (this.effect.equals(that.effect));
/*     */     }
/*  96 */     return true;
/*     */   }
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta)
/*     */   {
/* 101 */     return (super.notUncommon(meta)) && (((meta instanceof CraftMetaCharge)) || (!hasChargeMeta()));
/*     */   }
/*     */   
/*     */   int applyHash()
/*     */   {
/*     */     int original;
/* 107 */     int hash = original = super.applyHash();
/*     */     
/* 109 */     if (hasEffect()) {
/* 110 */       hash = 61 * hash + this.effect.hashCode();
/*     */     }
/*     */     
/* 113 */     return hash != original ? CraftMetaCharge.class.hashCode() ^ hash : hash;
/*     */   }
/*     */   
/*     */   public CraftMetaCharge clone()
/*     */   {
/* 118 */     return (CraftMetaCharge)super.clone();
/*     */   }
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder)
/*     */   {
/* 123 */     super.serialize(builder);
/*     */     
/* 125 */     if (hasEffect()) {
/* 126 */       builder.put(EXPLOSION.BUKKIT, this.effect);
/*     */     }
/*     */     
/* 129 */     return builder;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftMetaCharge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */