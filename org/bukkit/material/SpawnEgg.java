/*    */ package org.bukkit.material;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ 
/*    */ public class SpawnEgg
/*    */   extends MaterialData
/*    */ {
/*    */   public SpawnEgg()
/*    */   {
/* 12 */     super(Material.MONSTER_EGG);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public SpawnEgg(int type, byte data)
/*    */   {
/* 22 */     super(type, data);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public SpawnEgg(byte data)
/*    */   {
/* 31 */     super(Material.MONSTER_EGG, data);
/*    */   }
/*    */   
/*    */   public SpawnEgg(EntityType type) {
/* 35 */     this();
/* 36 */     setSpawnedType(type);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public EntityType getSpawnedType()
/*    */   {
/* 45 */     return EntityType.fromId(getData());
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void setSpawnedType(EntityType type)
/*    */   {
/* 54 */     setData((byte)type.getTypeId());
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 59 */     return "SPAWN EGG{" + getSpawnedType() + "}";
/*    */   }
/*    */   
/*    */   public SpawnEgg clone()
/*    */   {
/* 64 */     return (SpawnEgg)super.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\material\SpawnEgg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */