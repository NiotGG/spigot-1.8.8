/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagList;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.inventory.meta.PotionMeta;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ 
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ class CraftMetaPotion
/*     */   extends CraftMetaItem
/*     */   implements PotionMeta
/*     */ {
/*  24 */   static final CraftMetaItem.ItemMetaKey AMPLIFIER = new CraftMetaItem.ItemMetaKey("Amplifier", "amplifier");
/*  25 */   static final CraftMetaItem.ItemMetaKey AMBIENT = new CraftMetaItem.ItemMetaKey("Ambient", "ambient");
/*  26 */   static final CraftMetaItem.ItemMetaKey DURATION = new CraftMetaItem.ItemMetaKey("Duration", "duration");
/*  27 */   static final CraftMetaItem.ItemMetaKey SHOW_PARTICLES = new CraftMetaItem.ItemMetaKey("ShowParticles", "has-particles");
/*  28 */   static final CraftMetaItem.ItemMetaKey POTION_EFFECTS = new CraftMetaItem.ItemMetaKey("CustomPotionEffects", "custom-effects");
/*  29 */   static final CraftMetaItem.ItemMetaKey ID = new CraftMetaItem.ItemMetaKey("Id", "potion-id");
/*     */   private List<PotionEffect> customEffects;
/*     */   
/*     */   CraftMetaPotion(CraftMetaItem meta)
/*     */   {
/*  34 */     super(meta);
/*  35 */     if (!(meta instanceof CraftMetaPotion)) {
/*  36 */       return;
/*     */     }
/*  38 */     CraftMetaPotion potionMeta = (CraftMetaPotion)meta;
/*  39 */     if (potionMeta.hasCustomEffects()) {
/*  40 */       this.customEffects = new ArrayList(potionMeta.customEffects);
/*     */     }
/*     */   }
/*     */   
/*     */   CraftMetaPotion(NBTTagCompound tag) {
/*  45 */     super(tag);
/*     */     
/*  47 */     if (tag.hasKey(POTION_EFFECTS.NBT)) {
/*  48 */       NBTTagList list = tag.getList(POTION_EFFECTS.NBT, 10);
/*  49 */       int length = list.size();
/*  50 */       this.customEffects = new ArrayList(length);
/*     */       
/*  52 */       for (int i = 0; i < length; i++) {
/*  53 */         NBTTagCompound effect = list.get(i);
/*  54 */         PotionEffectType type = PotionEffectType.getById(effect.getByte(ID.NBT));
/*  55 */         int amp = effect.getByte(AMPLIFIER.NBT);
/*  56 */         int duration = effect.getInt(DURATION.NBT);
/*  57 */         boolean ambient = effect.getBoolean(AMBIENT.NBT);
/*  58 */         boolean particles = effect.getBoolean(SHOW_PARTICLES.NBT);
/*  59 */         this.customEffects.add(new PotionEffect(type, duration, amp, ambient, particles));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   CraftMetaPotion(Map<String, Object> map) {
/*  65 */     super(map);
/*     */     
/*  67 */     Iterable<?> rawEffectList = (Iterable)CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, POTION_EFFECTS.BUKKIT, true);
/*  68 */     if (rawEffectList == null) {
/*  69 */       return;
/*     */     }
/*     */     
/*  72 */     for (Object obj : rawEffectList) {
/*  73 */       if (!(obj instanceof PotionEffect)) {
/*  74 */         throw new IllegalArgumentException("Object in effect list is not valid. " + obj.getClass());
/*     */       }
/*  76 */       addCustomEffect((PotionEffect)obj, true);
/*     */     }
/*     */   }
/*     */   
/*     */   void applyToItem(NBTTagCompound tag)
/*     */   {
/*  82 */     super.applyToItem(tag);
/*  83 */     if (this.customEffects != null) {
/*  84 */       NBTTagList effectList = new NBTTagList();
/*  85 */       tag.set(POTION_EFFECTS.NBT, effectList);
/*     */       
/*  87 */       for (PotionEffect effect : this.customEffects) {
/*  88 */         NBTTagCompound effectData = new NBTTagCompound();
/*  89 */         effectData.setByte(ID.NBT, (byte)effect.getType().getId());
/*  90 */         effectData.setByte(AMPLIFIER.NBT, (byte)effect.getAmplifier());
/*  91 */         effectData.setInt(DURATION.NBT, effect.getDuration());
/*  92 */         effectData.setBoolean(AMBIENT.NBT, effect.isAmbient());
/*  93 */         effectData.setBoolean(SHOW_PARTICLES.NBT, effect.hasParticles());
/*  94 */         effectList.add(effectData);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   boolean isEmpty()
/*     */   {
/* 101 */     return (super.isEmpty()) && (isPotionEmpty());
/*     */   }
/*     */   
/*     */   boolean isPotionEmpty() {
/* 105 */     return !hasCustomEffects();
/*     */   }
/*     */   
/*     */   boolean applicableTo(Material type)
/*     */   {
/* 110 */     switch (type) {
/*     */     case SMOOTH_STAIRS: 
/* 112 */       return true;
/*     */     }
/* 114 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public CraftMetaPotion clone()
/*     */   {
/* 120 */     CraftMetaPotion clone = (CraftMetaPotion)super.clone();
/* 121 */     if (this.customEffects != null) {
/* 122 */       clone.customEffects = new ArrayList(this.customEffects);
/*     */     }
/* 124 */     return clone;
/*     */   }
/*     */   
/*     */   public boolean hasCustomEffects() {
/* 128 */     return this.customEffects != null;
/*     */   }
/*     */   
/*     */   public List<PotionEffect> getCustomEffects() {
/* 132 */     if (hasCustomEffects()) {
/* 133 */       return ImmutableList.copyOf(this.customEffects);
/*     */     }
/* 135 */     return ImmutableList.of();
/*     */   }
/*     */   
/*     */   public boolean addCustomEffect(PotionEffect effect, boolean overwrite) {
/* 139 */     Validate.notNull(effect, "Potion effect must not be null");
/*     */     
/* 141 */     int index = indexOfEffect(effect.getType());
/* 142 */     if (index != -1) {
/* 143 */       if (overwrite) {
/* 144 */         PotionEffect old = (PotionEffect)this.customEffects.get(index);
/* 145 */         if ((old.getAmplifier() == effect.getAmplifier()) && (old.getDuration() == effect.getDuration()) && (old.isAmbient() == effect.isAmbient())) {
/* 146 */           return false;
/*     */         }
/* 148 */         this.customEffects.set(index, effect);
/* 149 */         return true;
/*     */       }
/* 151 */       return false;
/*     */     }
/*     */     
/* 154 */     if (this.customEffects == null) {
/* 155 */       this.customEffects = new ArrayList();
/*     */     }
/* 157 */     this.customEffects.add(effect);
/* 158 */     return true;
/*     */   }
/*     */   
/*     */   public boolean removeCustomEffect(PotionEffectType type)
/*     */   {
/* 163 */     Validate.notNull(type, "Potion effect type must not be null");
/*     */     
/* 165 */     if (!hasCustomEffects()) {
/* 166 */       return false;
/*     */     }
/*     */     
/* 169 */     boolean changed = false;
/* 170 */     Iterator<PotionEffect> iterator = this.customEffects.iterator();
/* 171 */     while (iterator.hasNext()) {
/* 172 */       PotionEffect effect = (PotionEffect)iterator.next();
/* 173 */       if (effect.getType() == type) {
/* 174 */         iterator.remove();
/* 175 */         changed = true;
/*     */       }
/*     */     }
/* 178 */     if (this.customEffects.isEmpty()) {
/* 179 */       this.customEffects = null;
/*     */     }
/* 181 */     return changed;
/*     */   }
/*     */   
/*     */   public boolean hasCustomEffect(PotionEffectType type) {
/* 185 */     Validate.notNull(type, "Potion effect type must not be null");
/* 186 */     return indexOfEffect(type) != -1;
/*     */   }
/*     */   
/*     */   public boolean setMainEffect(PotionEffectType type) {
/* 190 */     Validate.notNull(type, "Potion effect type must not be null");
/* 191 */     int index = indexOfEffect(type);
/* 192 */     if ((index == -1) || (index == 0)) {
/* 193 */       return false;
/*     */     }
/*     */     
/* 196 */     PotionEffect old = (PotionEffect)this.customEffects.get(0);
/* 197 */     this.customEffects.set(0, (PotionEffect)this.customEffects.get(index));
/* 198 */     this.customEffects.set(index, old);
/* 199 */     return true;
/*     */   }
/*     */   
/*     */   private int indexOfEffect(PotionEffectType type) {
/* 203 */     if (!hasCustomEffects()) {
/* 204 */       return -1;
/*     */     }
/*     */     
/* 207 */     for (int i = 0; i < this.customEffects.size(); i++) {
/* 208 */       if (((PotionEffect)this.customEffects.get(i)).getType().equals(type)) {
/* 209 */         return i;
/*     */       }
/*     */     }
/* 212 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean clearCustomEffects() {
/* 216 */     boolean changed = hasCustomEffects();
/* 217 */     this.customEffects = null;
/* 218 */     return changed;
/*     */   }
/*     */   
/*     */   int applyHash()
/*     */   {
/*     */     int original;
/* 224 */     int hash = original = super.applyHash();
/* 225 */     if (hasCustomEffects()) {
/* 226 */       hash = 73 * hash + this.customEffects.hashCode();
/*     */     }
/* 228 */     return original != hash ? CraftMetaPotion.class.hashCode() ^ hash : hash;
/*     */   }
/*     */   
/*     */   public boolean equalsCommon(CraftMetaItem meta)
/*     */   {
/* 233 */     if (!super.equalsCommon(meta)) {
/* 234 */       return false;
/*     */     }
/* 236 */     if ((meta instanceof CraftMetaPotion)) {
/* 237 */       CraftMetaPotion that = (CraftMetaPotion)meta;
/*     */       
/* 239 */       return (that.hasCustomEffects()) && (this.customEffects.equals(that.customEffects));
/*     */     }
/* 241 */     return true;
/*     */   }
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta)
/*     */   {
/* 246 */     return (super.notUncommon(meta)) && (((meta instanceof CraftMetaPotion)) || (isPotionEmpty()));
/*     */   }
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder)
/*     */   {
/* 251 */     super.serialize(builder);
/*     */     
/* 253 */     if (hasCustomEffects()) {
/* 254 */       builder.put(POTION_EFFECTS.BUKKIT, ImmutableList.copyOf(this.customEffects));
/*     */     }
/*     */     
/* 257 */     return builder;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftMetaPotion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */