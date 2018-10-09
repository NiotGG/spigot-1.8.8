/*     */ package org.bukkit.material;
/*     */ 
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ public class MaterialData
/*     */   implements Cloneable
/*     */ {
/*     */   private final int type;
/*  11 */   private byte data = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public MaterialData(int type)
/*     */   {
/*  19 */     this(type, (byte)0);
/*     */   }
/*     */   
/*     */   public MaterialData(Material type) {
/*  23 */     this(type, (byte)0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public MaterialData(int type, byte data)
/*     */   {
/*  33 */     this.type = type;
/*  34 */     this.data = data;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public MaterialData(Material type, byte data)
/*     */   {
/*  44 */     this(type.getId(), data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public byte getData()
/*     */   {
/*  55 */     return this.data;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public void setData(byte data)
/*     */   {
/*  66 */     this.data = data;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Material getItemType()
/*     */   {
/*  75 */     return Material.getMaterial(this.type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public int getItemTypeId()
/*     */   {
/*  86 */     return this.type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack toItemStack()
/*     */   {
/*  95 */     return new ItemStack(this.type, 0, this.data);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack toItemStack(int amount)
/*     */   {
/* 105 */     return new ItemStack(this.type, amount, this.data);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 110 */     return getItemType() + "(" + getData() + ")";
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 115 */     return getItemTypeId() << 8 ^ getData();
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 120 */     if ((obj != null) && ((obj instanceof MaterialData))) {
/* 121 */       MaterialData md = (MaterialData)obj;
/*     */       
/* 123 */       return (md.getItemTypeId() == getItemTypeId()) && (md.getData() == getData());
/*     */     }
/* 125 */     return false;
/*     */   }
/*     */   
/*     */   public MaterialData clone()
/*     */   {
/*     */     try
/*     */     {
/* 132 */       return (MaterialData)super.clone();
/*     */     } catch (CloneNotSupportedException e) {
/* 134 */       throw new Error(e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\MaterialData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */