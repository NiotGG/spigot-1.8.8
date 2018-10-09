/*    */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.MobSpawnerAbstract;
/*    */ import net.minecraft.server.v1_8_R3.TileEntityMobSpawner;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.CreatureType;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftCreatureSpawner extends CraftBlockState implements org.bukkit.block.CreatureSpawner
/*    */ {
/*    */   private final TileEntityMobSpawner spawner;
/*    */   
/*    */   public CraftCreatureSpawner(Block block)
/*    */   {
/* 16 */     super(block);
/*    */     
/* 18 */     this.spawner = ((TileEntityMobSpawner)((org.bukkit.craftbukkit.v1_8_R3.CraftWorld)block.getWorld()).getTileEntityAt(getX(), getY(), getZ()));
/*    */   }
/*    */   
/*    */   public CraftCreatureSpawner(Material material, TileEntityMobSpawner te) {
/* 22 */     super(material);
/* 23 */     this.spawner = te;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public CreatureType getCreatureType() {
/* 28 */     return CreatureType.fromName(this.spawner.getSpawner().getMobName());
/*    */   }
/*    */   
/*    */   public EntityType getSpawnedType() {
/* 32 */     return EntityType.fromName(this.spawner.getSpawner().getMobName());
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public void setCreatureType(CreatureType creatureType) {
/* 37 */     this.spawner.getSpawner().setMobName(creatureType.getName());
/*    */   }
/*    */   
/*    */   public void setSpawnedType(EntityType entityType) {
/* 41 */     if ((entityType == null) || (entityType.getName() == null)) {
/* 42 */       throw new IllegalArgumentException("Can't spawn EntityType " + entityType + " from mobspawners!");
/*    */     }
/*    */     
/* 45 */     this.spawner.getSpawner().setMobName(entityType.getName());
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public String getCreatureTypeId() {
/* 50 */     return this.spawner.getSpawner().getMobName();
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public void setCreatureTypeId(String creatureName) {
/* 55 */     setCreatureTypeByName(creatureName);
/*    */   }
/*    */   
/*    */   public String getCreatureTypeName() {
/* 59 */     return this.spawner.getSpawner().getMobName();
/*    */   }
/*    */   
/*    */   public void setCreatureTypeByName(String creatureType)
/*    */   {
/* 64 */     EntityType type = EntityType.fromName(creatureType);
/* 65 */     if (type == null) {
/* 66 */       return;
/*    */     }
/* 68 */     setSpawnedType(type);
/*    */   }
/*    */   
/*    */   public int getDelay() {
/* 72 */     return this.spawner.getSpawner().spawnDelay;
/*    */   }
/*    */   
/*    */   public void setDelay(int delay) {
/* 76 */     this.spawner.getSpawner().spawnDelay = delay;
/*    */   }
/*    */   
/*    */   public TileEntityMobSpawner getTileEntity()
/*    */   {
/* 81 */     return this.spawner;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftCreatureSpawner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */