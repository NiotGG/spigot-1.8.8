/*     */ package com.mojang.authlib;
/*     */ 
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import java.util.UUID;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.builder.ToStringBuilder;
/*     */ 
/*     */ public class GameProfile
/*     */ {
/*     */   private final UUID id;
/*     */   private final String name;
/*  12 */   private final PropertyMap properties = new PropertyMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean legacy;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public GameProfile(UUID paramUUID, String paramString)
/*     */   {
/*  25 */     if ((paramUUID == null) && (StringUtils.isBlank(paramString))) { throw new IllegalArgumentException("Name and ID cannot both be blank");
/*     */     }
/*  27 */     this.id = paramUUID;
/*  28 */     this.name = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UUID getId()
/*     */   {
/*  39 */     return this.id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  50 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PropertyMap getProperties()
/*     */   {
/*  59 */     return this.properties;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isComplete()
/*     */   {
/*  70 */     return (this.id != null) && (StringUtils.isNotBlank(getName()));
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  75 */     if (this == paramObject) return true;
/*  76 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) { return false;
/*     */     }
/*  78 */     GameProfile localGameProfile = (GameProfile)paramObject;
/*     */     
/*  80 */     if (this.id != null ? !this.id.equals(localGameProfile.id) : localGameProfile.id != null) return false;
/*  81 */     if (this.name != null ? !this.name.equals(localGameProfile.name) : localGameProfile.name != null) { return false;
/*     */     }
/*  83 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/*  88 */     int i = this.id != null ? this.id.hashCode() : 0;
/*  89 */     i = 31 * i + (this.name != null ? this.name.hashCode() : 0);
/*  90 */     return i;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  95 */     return new ToStringBuilder(this).append("id", this.id).append("name", this.name).append("properties", this.properties).append("legacy", this.legacy).toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isLegacy()
/*     */   {
/* 104 */     return this.legacy;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\GameProfile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */