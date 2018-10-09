/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.ImmutableSet.Builder;
/*    */ import java.util.Set;
/*    */ import net.minecraft.server.v1_8_R3.EntityComplexPart;
/*    */ import net.minecraft.server.v1_8_R3.EntityEnderDragon;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.entity.ComplexEntityPart;
/*    */ import org.bukkit.entity.EnderDragon;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftEnderDragon
/*    */   extends CraftComplexLivingEntity implements EnderDragon
/*    */ {
/*    */   public CraftEnderDragon(CraftServer server, EntityEnderDragon entity)
/*    */   {
/* 18 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public Set<ComplexEntityPart> getParts() {
/* 22 */     ImmutableSet.Builder<ComplexEntityPart> builder = ImmutableSet.builder();
/*    */     EntityComplexPart[] arrayOfEntityComplexPart;
/* 24 */     int i = (arrayOfEntityComplexPart = getHandle().children).length; for (int j = 0; j < i; j++) { EntityComplexPart part = arrayOfEntityComplexPart[j];
/* 25 */       builder.add((ComplexEntityPart)part.getBukkitEntity());
/*    */     }
/*    */     
/* 28 */     return builder.build();
/*    */   }
/*    */   
/*    */   public EntityEnderDragon getHandle()
/*    */   {
/* 33 */     return (EntityEnderDragon)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 38 */     return "CraftEnderDragon";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 42 */     return EntityType.ENDER_DRAGON;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftEnderDragon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */