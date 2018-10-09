/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagList;
/*     */ import org.bukkit.DyeColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.banner.Pattern;
/*     */ import org.bukkit.block.banner.PatternType;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.inventory.meta.BannerMeta;
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ public class CraftMetaBanner extends CraftMetaItem implements BannerMeta
/*     */ {
/*  20 */   static final CraftMetaItem.ItemMetaKey BASE = new CraftMetaItem.ItemMetaKey("Base", "base-color");
/*  21 */   static final CraftMetaItem.ItemMetaKey PATTERNS = new CraftMetaItem.ItemMetaKey("Patterns", "patterns");
/*  22 */   static final CraftMetaItem.ItemMetaKey COLOR = new CraftMetaItem.ItemMetaKey("Color", "color");
/*  23 */   static final CraftMetaItem.ItemMetaKey PATTERN = new CraftMetaItem.ItemMetaKey("Pattern", "pattern");
/*     */   
/*     */   private DyeColor base;
/*  26 */   private List<Pattern> patterns = new ArrayList();
/*     */   
/*     */   CraftMetaBanner(CraftMetaItem meta) {
/*  29 */     super(meta);
/*     */     
/*  31 */     if (!(meta instanceof CraftMetaBanner)) {
/*  32 */       return;
/*     */     }
/*     */     
/*  35 */     CraftMetaBanner banner = (CraftMetaBanner)meta;
/*  36 */     this.base = banner.base;
/*  37 */     this.patterns = new ArrayList(banner.patterns);
/*     */   }
/*     */   
/*     */   CraftMetaBanner(NBTTagCompound tag) {
/*  41 */     super(tag);
/*     */     
/*  43 */     if (!tag.hasKey("BlockEntityTag")) {
/*  44 */       return;
/*     */     }
/*     */     
/*  47 */     NBTTagCompound entityTag = tag.getCompound("BlockEntityTag");
/*     */     
/*  49 */     this.base = (entityTag.hasKey(BASE.NBT) ? DyeColor.getByDyeData((byte)entityTag.getInt(BASE.NBT)) : null);
/*     */     
/*  51 */     if (entityTag.hasKey(PATTERNS.NBT)) {
/*  52 */       NBTTagList patterns = entityTag.getList(PATTERNS.NBT, 10);
/*  53 */       for (int i = 0; i < Math.min(patterns.size(), 20); i++) {
/*  54 */         NBTTagCompound p = patterns.get(i);
/*  55 */         this.patterns.add(new Pattern(DyeColor.getByDyeData((byte)p.getInt(COLOR.NBT)), PatternType.getByIdentifier(p.getString(PATTERN.NBT))));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   CraftMetaBanner(Map<String, Object> map) {
/*  61 */     super(map);
/*     */     
/*  63 */     String baseStr = CraftMetaItem.SerializableMeta.getString(map, BASE.BUKKIT, true);
/*  64 */     if (baseStr != null) {
/*  65 */       this.base = DyeColor.valueOf(baseStr);
/*     */     }
/*     */     
/*  68 */     Iterable<?> rawPatternList = (Iterable)CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, PATTERNS.BUKKIT, true);
/*  69 */     if (rawPatternList == null) {
/*  70 */       return;
/*     */     }
/*     */     
/*  73 */     for (Object obj : rawPatternList) {
/*  74 */       if (!(obj instanceof Pattern)) {
/*  75 */         throw new IllegalArgumentException("Object in pattern list is not valid. " + obj.getClass());
/*     */       }
/*  77 */       addPattern((Pattern)obj);
/*     */     }
/*     */   }
/*     */   
/*     */   void applyToItem(NBTTagCompound tag) {
/*  82 */     super.applyToItem(tag);
/*     */     
/*  84 */     NBTTagCompound entityTag = new NBTTagCompound();
/*  85 */     if (this.base != null) {
/*  86 */       entityTag.setInt(BASE.NBT, this.base.getDyeData());
/*     */     }
/*     */     
/*  89 */     NBTTagList newPatterns = new NBTTagList();
/*     */     
/*  91 */     for (Pattern p : this.patterns) {
/*  92 */       NBTTagCompound compound = new NBTTagCompound();
/*  93 */       compound.setInt(COLOR.NBT, p.getColor().getDyeData());
/*  94 */       compound.setString(PATTERN.NBT, p.getPattern().getIdentifier());
/*  95 */       newPatterns.add(compound);
/*     */     }
/*  97 */     entityTag.set(PATTERNS.NBT, newPatterns);
/*     */     
/*  99 */     tag.set("BlockEntityTag", entityTag);
/*     */   }
/*     */   
/*     */   public DyeColor getBaseColor()
/*     */   {
/* 104 */     return this.base;
/*     */   }
/*     */   
/*     */   public void setBaseColor(DyeColor color)
/*     */   {
/* 109 */     this.base = color;
/*     */   }
/*     */   
/*     */   public List<Pattern> getPatterns()
/*     */   {
/* 114 */     return new ArrayList(this.patterns);
/*     */   }
/*     */   
/*     */   public void setPatterns(List<Pattern> patterns)
/*     */   {
/* 119 */     this.patterns = new ArrayList(patterns);
/*     */   }
/*     */   
/*     */   public void addPattern(Pattern pattern)
/*     */   {
/* 124 */     this.patterns.add(pattern);
/*     */   }
/*     */   
/*     */   public Pattern getPattern(int i)
/*     */   {
/* 129 */     return (Pattern)this.patterns.get(i);
/*     */   }
/*     */   
/*     */   public Pattern removePattern(int i)
/*     */   {
/* 134 */     return (Pattern)this.patterns.remove(i);
/*     */   }
/*     */   
/*     */   public void setPattern(int i, Pattern pattern)
/*     */   {
/* 139 */     this.patterns.set(i, pattern);
/*     */   }
/*     */   
/*     */   public int numberOfPatterns()
/*     */   {
/* 144 */     return this.patterns.size();
/*     */   }
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder)
/*     */   {
/* 149 */     super.serialize(builder);
/*     */     
/* 151 */     if (this.base != null) {
/* 152 */       builder.put(BASE.BUKKIT, this.base.toString());
/*     */     }
/*     */     
/* 155 */     if (!this.patterns.isEmpty()) {
/* 156 */       builder.put(PATTERNS.BUKKIT, ImmutableList.copyOf(this.patterns));
/*     */     }
/*     */     
/* 159 */     return builder;
/*     */   }
/*     */   
/*     */   int applyHash()
/*     */   {
/*     */     int original;
/* 165 */     int hash = original = super.applyHash();
/* 166 */     if (this.base != null) {
/* 167 */       hash = 31 * hash + this.base.hashCode();
/*     */     }
/* 169 */     if (!this.patterns.isEmpty()) {
/* 170 */       hash = 31 * hash + this.patterns.hashCode();
/*     */     }
/* 172 */     return original != hash ? CraftMetaBanner.class.hashCode() ^ hash : hash;
/*     */   }
/*     */   
/*     */   public boolean equalsCommon(CraftMetaItem meta)
/*     */   {
/* 177 */     if (!super.equalsCommon(meta)) {
/* 178 */       return false;
/*     */     }
/* 180 */     if ((meta instanceof CraftMetaBanner)) {
/* 181 */       CraftMetaBanner that = (CraftMetaBanner)meta;
/*     */       
/* 183 */       return (this.base == that.base) && (this.patterns.equals(that.patterns));
/*     */     }
/* 185 */     return true;
/*     */   }
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta)
/*     */   {
/* 190 */     return (super.notUncommon(meta)) && (((meta instanceof CraftMetaBanner)) || ((this.patterns.isEmpty()) && (this.base == null)));
/*     */   }
/*     */   
/*     */ 
/*     */   boolean isEmpty()
/*     */   {
/* 196 */     return (super.isEmpty()) && (this.patterns.isEmpty()) && (this.base == null);
/*     */   }
/*     */   
/*     */ 
/*     */   boolean applicableTo(Material type)
/*     */   {
/* 202 */     return type == Material.BANNER;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftMetaBanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */