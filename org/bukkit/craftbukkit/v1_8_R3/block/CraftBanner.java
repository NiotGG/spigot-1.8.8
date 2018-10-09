/*     */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagList;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityBanner;
/*     */ import org.bukkit.DyeColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Banner;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.banner.Pattern;
/*     */ import org.bukkit.block.banner.PatternType;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ 
/*     */ public class CraftBanner extends CraftBlockState implements Banner
/*     */ {
/*     */   private final TileEntityBanner banner;
/*     */   private DyeColor base;
/*  20 */   private List<Pattern> patterns = new ArrayList();
/*     */   
/*     */   public CraftBanner(Block block) {
/*  23 */     super(block);
/*     */     
/*  25 */     CraftWorld world = (CraftWorld)block.getWorld();
/*  26 */     this.banner = ((TileEntityBanner)world.getTileEntityAt(getX(), getY(), getZ()));
/*     */     
/*  28 */     this.base = DyeColor.getByDyeData((byte)this.banner.color);
/*     */     
/*  30 */     if (this.banner.patterns != null) {
/*  31 */       for (int i = 0; i < this.banner.patterns.size(); i++) {
/*  32 */         NBTTagCompound p = this.banner.patterns.get(i);
/*  33 */         this.patterns.add(new Pattern(DyeColor.getByDyeData((byte)p.getInt("Color")), PatternType.getByIdentifier(p.getString("Pattern"))));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public CraftBanner(Material material, TileEntityBanner te) {
/*  39 */     super(material);
/*  40 */     this.banner = te;
/*     */     
/*  42 */     this.base = DyeColor.getByDyeData((byte)this.banner.color);
/*     */     
/*  44 */     if (this.banner.patterns != null) {
/*  45 */       for (int i = 0; i < this.banner.patterns.size(); i++) {
/*  46 */         NBTTagCompound p = this.banner.patterns.get(i);
/*  47 */         this.patterns.add(new Pattern(DyeColor.getByDyeData((byte)p.getInt("Color")), PatternType.getByIdentifier(p.getString("Pattern"))));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public DyeColor getBaseColor()
/*     */   {
/*  54 */     return this.base;
/*     */   }
/*     */   
/*     */   public void setBaseColor(DyeColor color)
/*     */   {
/*  59 */     this.base = color;
/*     */   }
/*     */   
/*     */   public List<Pattern> getPatterns()
/*     */   {
/*  64 */     return new ArrayList(this.patterns);
/*     */   }
/*     */   
/*     */   public void setPatterns(List<Pattern> patterns)
/*     */   {
/*  69 */     this.patterns = new ArrayList(patterns);
/*     */   }
/*     */   
/*     */   public void addPattern(Pattern pattern)
/*     */   {
/*  74 */     this.patterns.add(pattern);
/*     */   }
/*     */   
/*     */   public Pattern getPattern(int i)
/*     */   {
/*  79 */     return (Pattern)this.patterns.get(i);
/*     */   }
/*     */   
/*     */   public Pattern removePattern(int i)
/*     */   {
/*  84 */     return (Pattern)this.patterns.remove(i);
/*     */   }
/*     */   
/*     */   public void setPattern(int i, Pattern pattern)
/*     */   {
/*  89 */     this.patterns.set(i, pattern);
/*     */   }
/*     */   
/*     */   public int numberOfPatterns()
/*     */   {
/*  94 */     return this.patterns.size();
/*     */   }
/*     */   
/*     */   public boolean update(boolean force, boolean applyPhysics)
/*     */   {
/*  99 */     boolean result = super.update(force, applyPhysics);
/*     */     
/* 101 */     if (result) {
/* 102 */       this.banner.color = this.base.getDyeData();
/*     */       
/* 104 */       NBTTagList newPatterns = new NBTTagList();
/*     */       
/* 106 */       for (Pattern p : this.patterns) {
/* 107 */         NBTTagCompound compound = new NBTTagCompound();
/* 108 */         compound.setInt("Color", p.getColor().getDyeData());
/* 109 */         compound.setString("Pattern", p.getPattern().getIdentifier());
/* 110 */         newPatterns.add(compound);
/*     */       }
/*     */       
/* 113 */       this.banner.patterns = newPatterns;
/*     */       
/* 115 */       this.banner.update();
/*     */     }
/*     */     
/* 118 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftBanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */