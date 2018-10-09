/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_8_R3.GameProfileSerializer;
/*     */ import net.minecraft.server.v1_8_R3.NBTBase;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*     */ import net.minecraft.server.v1_8_R3.TileEntitySkull;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
/*     */ 
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ class CraftMetaSkull
/*     */   extends CraftMetaItem
/*     */   implements SkullMeta
/*     */ {
/*  21 */   static final CraftMetaItem.ItemMetaKey SKULL_PROFILE = new CraftMetaItem.ItemMetaKey("SkullProfile");
/*     */   
/*  23 */   static final CraftMetaItem.ItemMetaKey SKULL_OWNER = new CraftMetaItem.ItemMetaKey("SkullOwner", "skull-owner");
/*     */   static final int MAX_OWNER_LENGTH = 16;
/*     */   private GameProfile profile;
/*     */   
/*     */   CraftMetaSkull(CraftMetaItem meta)
/*     */   {
/*  29 */     super(meta);
/*  30 */     if (!(meta instanceof CraftMetaSkull)) {
/*  31 */       return;
/*     */     }
/*  33 */     CraftMetaSkull skullMeta = (CraftMetaSkull)meta;
/*  34 */     this.profile = skullMeta.profile;
/*     */   }
/*     */   
/*     */   CraftMetaSkull(NBTTagCompound tag) {
/*  38 */     super(tag);
/*     */     
/*  40 */     if (tag.hasKeyOfType(SKULL_OWNER.NBT, 10)) {
/*  41 */       this.profile = GameProfileSerializer.deserialize(tag.getCompound(SKULL_OWNER.NBT));
/*  42 */     } else if ((tag.hasKeyOfType(SKULL_OWNER.NBT, 8)) && (!tag.getString(SKULL_OWNER.NBT).isEmpty())) {
/*  43 */       this.profile = new GameProfile(null, tag.getString(SKULL_OWNER.NBT));
/*     */     }
/*     */   }
/*     */   
/*     */   CraftMetaSkull(Map<String, Object> map) {
/*  48 */     super(map);
/*  49 */     if (this.profile == null) {
/*  50 */       setOwner(CraftMetaItem.SerializableMeta.getString(map, SKULL_OWNER.BUKKIT, true));
/*     */     }
/*     */   }
/*     */   
/*     */   void deserializeInternal(NBTTagCompound tag)
/*     */   {
/*  56 */     if (tag.hasKeyOfType(SKULL_PROFILE.NBT, 10)) {
/*  57 */       this.profile = GameProfileSerializer.deserialize(tag.getCompound(SKULL_PROFILE.NBT));
/*     */     }
/*     */   }
/*     */   
/*     */   void serializeInternal(Map<String, NBTBase> internalTags)
/*     */   {
/*  63 */     if (this.profile != null) {
/*  64 */       NBTTagCompound nbtData = new NBTTagCompound();
/*  65 */       GameProfileSerializer.serialize(nbtData, this.profile);
/*  66 */       internalTags.put(SKULL_PROFILE.NBT, nbtData);
/*     */     }
/*     */   }
/*     */   
/*     */   void applyToItem(final NBTTagCompound tag)
/*     */   {
/*  72 */     super.applyToItem(tag);
/*     */     
/*  74 */     if (hasOwner()) {
/*  75 */       NBTTagCompound owner = new NBTTagCompound();
/*  76 */       GameProfileSerializer.serialize(owner, this.profile);
/*  77 */       tag.set(SKULL_OWNER.NBT, owner);
/*     */       
/*     */ 
/*     */ 
/*  81 */       TileEntitySkull.b(this.profile, new Predicate()
/*     */       {
/*     */         public boolean apply(GameProfile input)
/*     */         {
/*  85 */           NBTTagCompound owner = new NBTTagCompound();
/*  86 */           GameProfileSerializer.serialize(owner, input);
/*  87 */           tag.set(CraftMetaSkull.SKULL_OWNER.NBT, owner);
/*  88 */           return false;
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   boolean isEmpty()
/*     */   {
/*  97 */     return (super.isEmpty()) && (isSkullEmpty());
/*     */   }
/*     */   
/*     */   boolean isSkullEmpty() {
/* 101 */     return !hasOwner();
/*     */   }
/*     */   
/*     */   boolean applicableTo(Material type)
/*     */   {
/* 106 */     switch (type) {
/*     */     case STONE_BUTTON: 
/* 108 */       return true;
/*     */     }
/* 110 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public CraftMetaSkull clone()
/*     */   {
/* 116 */     return (CraftMetaSkull)super.clone();
/*     */   }
/*     */   
/*     */   public boolean hasOwner() {
/* 120 */     return this.profile != null;
/*     */   }
/*     */   
/*     */   public String getOwner() {
/* 124 */     return hasOwner() ? this.profile.getName() : null;
/*     */   }
/*     */   
/*     */   public boolean setOwner(String name) {
/* 128 */     if ((name != null) && (name.length() > 16)) {
/* 129 */       return false;
/*     */     }
/*     */     
/* 132 */     if (name == null) {
/* 133 */       this.profile = null;
/*     */     } else {
/* 135 */       this.profile = new GameProfile(null, name);
/*     */     }
/*     */     
/* 138 */     return true;
/*     */   }
/*     */   
/*     */   int applyHash()
/*     */   {
/*     */     int original;
/* 144 */     int hash = original = super.applyHash();
/* 145 */     if (hasOwner()) {
/* 146 */       hash = 61 * hash + this.profile.hashCode();
/*     */     }
/* 148 */     return original != hash ? CraftMetaSkull.class.hashCode() ^ hash : hash;
/*     */   }
/*     */   
/*     */   boolean equalsCommon(CraftMetaItem meta)
/*     */   {
/* 153 */     if (!super.equalsCommon(meta)) {
/* 154 */       return false;
/*     */     }
/* 156 */     if ((meta instanceof CraftMetaSkull)) {
/* 157 */       CraftMetaSkull that = (CraftMetaSkull)meta;
/*     */       
/* 159 */       return (that.hasOwner()) && (this.profile.equals(that.profile));
/*     */     }
/* 161 */     return true;
/*     */   }
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta)
/*     */   {
/* 166 */     return (super.notUncommon(meta)) && (((meta instanceof CraftMetaSkull)) || (isSkullEmpty()));
/*     */   }
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder)
/*     */   {
/* 171 */     super.serialize(builder);
/* 172 */     if (hasOwner()) {
/* 173 */       return builder.put(SKULL_OWNER.BUKKIT, this.profile.getName());
/*     */     }
/* 175 */     return builder;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftMetaSkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */