/*    */ package org.bukkit.block.banner;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import java.util.NoSuchElementException;
/*    */ import org.bukkit.DyeColor;
/*    */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*    */ import org.bukkit.configuration.serialization.SerializableAs;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @SerializableAs("Pattern")
/*    */ public class Pattern
/*    */   implements ConfigurationSerializable
/*    */ {
/*    */   private static final String COLOR = "color";
/*    */   private static final String PATTERN = "pattern";
/*    */   private final DyeColor color;
/*    */   private final PatternType pattern;
/*    */   
/*    */   public Pattern(DyeColor color, PatternType pattern)
/*    */   {
/* 27 */     this.color = color;
/* 28 */     this.pattern = pattern;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Pattern(Map<String, Object> map)
/*    */   {
/* 37 */     this.color = DyeColor.valueOf(getString(map, "color"));
/* 38 */     this.pattern = PatternType.getByIdentifier(getString(map, "pattern"));
/*    */   }
/*    */   
/*    */   private static String getString(Map<?, ?> map, Object key) {
/* 42 */     Object str = map.get(key);
/* 43 */     if ((str instanceof String)) {
/* 44 */       return (String)str;
/*    */     }
/* 46 */     throw new NoSuchElementException(map + " does not contain " + key);
/*    */   }
/*    */   
/*    */   public Map<String, Object> serialize()
/*    */   {
/* 51 */     return ImmutableMap.of(
/* 52 */       "color", this.color.toString(), 
/* 53 */       "pattern", this.pattern.getIdentifier());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public DyeColor getColor()
/*    */   {
/* 63 */     return this.color;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public PatternType getPattern()
/*    */   {
/* 72 */     return this.pattern;
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 77 */     int hash = 3;
/* 78 */     hash = 97 * hash + (this.color != null ? this.color.hashCode() : 0);
/* 79 */     hash = 97 * hash + (this.pattern != null ? this.pattern.hashCode() : 0);
/* 80 */     return hash;
/*    */   }
/*    */   
/*    */   public boolean equals(Object obj)
/*    */   {
/* 85 */     if (obj == null) {
/* 86 */       return false;
/*    */     }
/* 88 */     if (getClass() != obj.getClass()) {
/* 89 */       return false;
/*    */     }
/* 91 */     Pattern other = (Pattern)obj;
/* 92 */     return (this.color == other.color) && (this.pattern == other.pattern);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\banner\Pattern.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */