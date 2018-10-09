/*     */ package org.bukkit.craftbukkit.v1_8_R3.projectiles;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.server.v1_8_R3.BlockDispenser;
/*     */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*     */ import net.minecraft.server.v1_8_R3.Entity;
/*     */ import net.minecraft.server.v1_8_R3.EntityArrow;
/*     */ import net.minecraft.server.v1_8_R3.EntityEgg;
/*     */ import net.minecraft.server.v1_8_R3.EntityEnderPearl;
/*     */ import net.minecraft.server.v1_8_R3.EntityFireball;
/*     */ import net.minecraft.server.v1_8_R3.EntityPotion;
/*     */ import net.minecraft.server.v1_8_R3.EntityProjectile;
/*     */ import net.minecraft.server.v1_8_R3.EntitySmallFireball;
/*     */ import net.minecraft.server.v1_8_R3.EntitySnowball;
/*     */ import net.minecraft.server.v1_8_R3.EntityThrownExpBottle;
/*     */ import net.minecraft.server.v1_8_R3.EnumDirection;
/*     */ import net.minecraft.server.v1_8_R3.IPosition;
/*     */ import net.minecraft.server.v1_8_R3.IProjectile;
/*     */ import net.minecraft.server.v1_8_R3.MathHelper;
/*     */ import net.minecraft.server.v1_8_R3.SourceBlock;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityDispenser;
/*     */ import net.minecraft.server.v1_8_R3.World;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.Arrow;
/*     */ import org.bukkit.entity.Egg;
/*     */ import org.bukkit.entity.EnderPearl;
/*     */ import org.bukkit.entity.Fireball;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.entity.SmallFireball;
/*     */ import org.bukkit.entity.ThrownExpBottle;
/*     */ import org.bukkit.entity.ThrownPotion;
/*     */ import org.bukkit.entity.WitherSkull;
/*     */ import org.bukkit.projectiles.BlockProjectileSource;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class CraftBlockProjectileSource implements BlockProjectileSource
/*     */ {
/*     */   private final TileEntityDispenser dispenserBlock;
/*     */   
/*     */   public CraftBlockProjectileSource(TileEntityDispenser dispenserBlock)
/*     */   {
/*  46 */     this.dispenserBlock = dispenserBlock;
/*     */   }
/*     */   
/*     */   public Block getBlock()
/*     */   {
/*  51 */     return this.dispenserBlock.getWorld().getWorld().getBlockAt(this.dispenserBlock.getPosition().getX(), this.dispenserBlock.getPosition().getY(), this.dispenserBlock.getPosition().getZ());
/*     */   }
/*     */   
/*     */   public <T extends Projectile> T launchProjectile(Class<? extends T> projectile)
/*     */   {
/*  56 */     return launchProjectile(projectile, null);
/*     */   }
/*     */   
/*     */   public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity)
/*     */   {
/*  61 */     Validate.isTrue(getBlock().getType() == Material.DISPENSER, "Block is no longer dispenser");
/*     */     
/*  63 */     SourceBlock isourceblock = new SourceBlock(this.dispenserBlock.getWorld(), this.dispenserBlock.getPosition());
/*     */     
/*  65 */     IPosition iposition = BlockDispenser.a(isourceblock);
/*  66 */     EnumDirection enumdirection = BlockDispenser.b(isourceblock.f());
/*  67 */     World world = this.dispenserBlock.getWorld();
/*  68 */     Entity launch = null;
/*     */     
/*  70 */     if (org.bukkit.entity.Snowball.class.isAssignableFrom(projectile)) {
/*  71 */       launch = new EntitySnowball(world, iposition.getX(), iposition.getY(), iposition.getZ());
/*  72 */     } else if (Egg.class.isAssignableFrom(projectile)) {
/*  73 */       launch = new EntityEgg(world, iposition.getX(), iposition.getY(), iposition.getZ());
/*  74 */     } else if (EnderPearl.class.isAssignableFrom(projectile)) {
/*  75 */       launch = new EntityEnderPearl(world, null);
/*  76 */       launch.setPosition(iposition.getX(), iposition.getY(), iposition.getZ());
/*  77 */     } else if (ThrownExpBottle.class.isAssignableFrom(projectile)) {
/*  78 */       launch = new EntityThrownExpBottle(world, iposition.getX(), iposition.getY(), iposition.getZ());
/*  79 */     } else if (ThrownPotion.class.isAssignableFrom(projectile)) {
/*  80 */       launch = new EntityPotion(world, iposition.getX(), iposition.getY(), iposition.getZ(), CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.POTION, 1)));
/*  81 */     } else if (Arrow.class.isAssignableFrom(projectile)) {
/*  82 */       launch = new EntityArrow(world, iposition.getX(), iposition.getY(), iposition.getZ());
/*  83 */       ((EntityArrow)launch).fromPlayer = 1;
/*  84 */       ((EntityArrow)launch).projectileSource = this;
/*  85 */     } else if (Fireball.class.isAssignableFrom(projectile)) {
/*  86 */       double d0 = iposition.getX() + enumdirection.getAdjacentX() * 0.3F;
/*  87 */       double d1 = iposition.getY() + enumdirection.getAdjacentY() * 0.3F;
/*  88 */       double d2 = iposition.getZ() + enumdirection.getAdjacentZ() * 0.3F;
/*  89 */       Random random = world.random;
/*  90 */       double d3 = random.nextGaussian() * 0.05D + enumdirection.getAdjacentX();
/*  91 */       double d4 = random.nextGaussian() * 0.05D + enumdirection.getAdjacentY();
/*  92 */       double d5 = random.nextGaussian() * 0.05D + enumdirection.getAdjacentZ();
/*     */       
/*  94 */       if (SmallFireball.class.isAssignableFrom(projectile)) {
/*  95 */         launch = new EntitySmallFireball(world, d0, d1, d2, d3, d4, d5);
/*  96 */       } else if (WitherSkull.class.isAssignableFrom(projectile)) {
/*  97 */         launch = new net.minecraft.server.v1_8_R3.EntityWitherSkull(world);
/*  98 */         launch.setPosition(d0, d1, d2);
/*  99 */         double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/*     */         
/* 101 */         ((EntityFireball)launch).dirX = (d3 / d6 * 0.1D);
/* 102 */         ((EntityFireball)launch).dirY = (d4 / d6 * 0.1D);
/* 103 */         ((EntityFireball)launch).dirZ = (d5 / d6 * 0.1D);
/*     */       } else {
/* 105 */         launch = new net.minecraft.server.v1_8_R3.EntityLargeFireball(world);
/* 106 */         launch.setPosition(d0, d1, d2);
/* 107 */         double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/*     */         
/* 109 */         ((EntityFireball)launch).dirX = (d3 / d6 * 0.1D);
/* 110 */         ((EntityFireball)launch).dirY = (d4 / d6 * 0.1D);
/* 111 */         ((EntityFireball)launch).dirZ = (d5 / d6 * 0.1D);
/*     */       }
/*     */       
/* 114 */       ((EntityFireball)launch).projectileSource = this;
/*     */     }
/*     */     
/* 117 */     Validate.notNull(launch, "Projectile not supported");
/*     */     
/* 119 */     if ((launch instanceof IProjectile)) {
/* 120 */       if ((launch instanceof EntityProjectile)) {
/* 121 */         ((EntityProjectile)launch).projectileSource = this;
/*     */       }
/*     */       
/* 124 */       float a = 6.0F;
/* 125 */       float b = 1.1F;
/* 126 */       if (((launch instanceof EntityPotion)) || ((launch instanceof ThrownExpBottle)))
/*     */       {
/* 128 */         a *= 0.5F;
/* 129 */         b *= 1.25F;
/*     */       }
/*     */       
/* 132 */       ((IProjectile)launch).shoot(enumdirection.getAdjacentX(), enumdirection.getAdjacentY() + 0.1F, enumdirection.getAdjacentZ(), b, a);
/*     */     }
/*     */     
/* 135 */     if (velocity != null) {
/* 136 */       ((Projectile)launch.getBukkitEntity()).setVelocity(velocity);
/*     */     }
/*     */     
/* 139 */     world.addEntity(launch);
/* 140 */     return (Projectile)launch.getBukkitEntity();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\projectiles\CraftBlockProjectileSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */