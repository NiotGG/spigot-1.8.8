/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.inventory.meta.MapMeta;
/*     */ 
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ class CraftMetaMap
/*     */   extends CraftMetaItem
/*     */   implements MapMeta
/*     */ {
/*  16 */   static final CraftMetaItem.ItemMetaKey MAP_SCALING = new CraftMetaItem.ItemMetaKey("map_is_scaling", "scaling");
/*     */   
/*     */   static final byte SCALING_EMPTY = 0;
/*     */   static final byte SCALING_TRUE = 1;
/*     */   static final byte SCALING_FALSE = 2;
/*  21 */   private byte scaling = 0;
/*     */   
/*     */   CraftMetaMap(CraftMetaItem meta) {
/*  24 */     super(meta);
/*     */     
/*  26 */     if (!(meta instanceof CraftMetaMap)) {
/*  27 */       return;
/*     */     }
/*     */     
/*  30 */     CraftMetaMap map = (CraftMetaMap)meta;
/*  31 */     this.scaling = map.scaling;
/*     */   }
/*     */   
/*     */   CraftMetaMap(NBTTagCompound tag) {
/*  35 */     super(tag);
/*     */     
/*  37 */     if (tag.hasKey(MAP_SCALING.NBT)) {
/*  38 */       this.scaling = (tag.getBoolean(MAP_SCALING.NBT) ? 1 : 2);
/*     */     }
/*     */   }
/*     */   
/*     */   CraftMetaMap(Map<String, Object> map) {
/*  43 */     super(map);
/*     */     
/*  45 */     Boolean scaling = (Boolean)CraftMetaItem.SerializableMeta.getObject(Boolean.class, map, MAP_SCALING.BUKKIT, true);
/*  46 */     if (scaling != null) {
/*  47 */       setScaling(scaling.booleanValue());
/*     */     }
/*     */   }
/*     */   
/*     */   void applyToItem(NBTTagCompound tag)
/*     */   {
/*  53 */     super.applyToItem(tag);
/*     */     
/*  55 */     if (hasScaling()) {
/*  56 */       tag.setBoolean(MAP_SCALING.NBT, isScaling());
/*     */     }
/*     */   }
/*     */   
/*     */   boolean applicableTo(Material type)
/*     */   {
/*  62 */     switch (type) {
/*     */     case SADDLE: 
/*  64 */       return true;
/*     */     }
/*  66 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   boolean isEmpty()
/*     */   {
/*  72 */     return (super.isEmpty()) && (isMapEmpty());
/*     */   }
/*     */   
/*     */   boolean isMapEmpty() {
/*  76 */     return !hasScaling();
/*     */   }
/*     */   
/*     */   boolean hasScaling() {
/*  80 */     return this.scaling != 0;
/*     */   }
/*     */   
/*     */   public boolean isScaling() {
/*  84 */     return this.scaling == 1;
/*     */   }
/*     */   
/*     */   public void setScaling(boolean scaling) {
/*  88 */     this.scaling = (scaling ? 1 : 2);
/*     */   }
/*     */   
/*     */   boolean equalsCommon(CraftMetaItem meta)
/*     */   {
/*  93 */     if (!super.equalsCommon(meta)) {
/*  94 */       return false;
/*     */     }
/*  96 */     if ((meta instanceof CraftMetaMap)) {
/*  97 */       CraftMetaMap that = (CraftMetaMap)meta;
/*     */       
/*  99 */       return this.scaling == that.scaling;
/*     */     }
/* 101 */     return true;
/*     */   }
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta)
/*     */   {
/* 106 */     return (super.notUncommon(meta)) && (((meta instanceof CraftMetaMap)) || (isMapEmpty()));
/*     */   }
/*     */   
/*     */   int applyHash()
/*     */   {
/*     */     int original;
/* 112 */     int hash = original = super.applyHash();
/*     */     
/* 114 */     if (hasScaling()) {
/* 115 */       hash ^= 572662306 << (isScaling() ? 1 : -1);
/*     */     }
/*     */     
/* 118 */     return original != hash ? CraftMetaMap.class.hashCode() ^ hash : hash;
/*     */   }
/*     */   
/*     */   public CraftMetaMap clone() {
/* 122 */     return (CraftMetaMap)super.clone();
/*     */   }
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder)
/*     */   {
/* 127 */     super.serialize(builder);
/*     */     
/* 129 */     if (hasScaling()) {
/* 130 */       builder.put(MAP_SCALING.BUKKIT, Boolean.valueOf(isScaling()));
/*     */     }
/*     */     
/* 133 */     return builder;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftMetaMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */