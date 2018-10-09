/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.event.entity.EntityUnleashEvent;
/*     */ import org.bukkit.event.entity.EntityUnleashEvent.UnleashReason;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public abstract class EntityCreature extends EntityInsentient
/*     */ {
/*  11 */   public static final UUID bk = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
/*  12 */   public static final AttributeModifier bl = new AttributeModifier(bk, "Fleeing speed bonus", 2.0D, 2).a(false);
/*     */   private BlockPosition a;
/*     */   private float b;
/*     */   private PathfinderGoal c;
/*     */   private boolean bm;
/*     */   
/*     */   public EntityCreature(World world) {
/*  19 */     super(world);
/*  20 */     this.a = BlockPosition.ZERO;
/*  21 */     this.b = -1.0F;
/*  22 */     this.c = new PathfinderGoalMoveTowardsRestriction(this, 1.0D);
/*     */   }
/*     */   
/*     */   public float a(BlockPosition blockposition) {
/*  26 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public boolean bR() {
/*  30 */     return (super.bR()) && (a(new BlockPosition(this.locX, getBoundingBox().b, this.locZ)) >= 0.0F);
/*     */   }
/*     */   
/*     */   public boolean cf() {
/*  34 */     return !this.navigation.m();
/*     */   }
/*     */   
/*     */   public boolean cg() {
/*  38 */     return e(new BlockPosition(this));
/*     */   }
/*     */   
/*     */   public boolean e(BlockPosition blockposition) {
/*  42 */     return this.b == -1.0F;
/*     */   }
/*     */   
/*     */   public void a(BlockPosition blockposition, int i) {
/*  46 */     this.a = blockposition;
/*  47 */     this.b = i;
/*     */   }
/*     */   
/*     */   public BlockPosition ch() {
/*  51 */     return this.a;
/*     */   }
/*     */   
/*     */   public float ci() {
/*  55 */     return this.b;
/*     */   }
/*     */   
/*     */   public void cj() {
/*  59 */     this.b = -1.0F;
/*     */   }
/*     */   
/*     */   public boolean ck() {
/*  63 */     return this.b != -1.0F;
/*     */   }
/*     */   
/*     */   protected void ca() {
/*  67 */     super.ca();
/*  68 */     if ((cc()) && (getLeashHolder() != null) && (getLeashHolder().world == this.world)) {
/*  69 */       Entity entity = getLeashHolder();
/*     */       
/*  71 */       a(new BlockPosition((int)entity.locX, (int)entity.locY, (int)entity.locZ), 5);
/*  72 */       float f = g(entity);
/*     */       
/*  74 */       if (((this instanceof EntityTameableAnimal)) && (((EntityTameableAnimal)this).isSitting())) {
/*  75 */         if (f > 10.0F) {
/*  76 */           this.world.getServer().getPluginManager().callEvent(new EntityUnleashEvent(getBukkitEntity(), EntityUnleashEvent.UnleashReason.DISTANCE));
/*  77 */           unleash(true, true);
/*     */         }
/*     */         
/*  80 */         return;
/*     */       }
/*     */       
/*  83 */       if (!this.bm) {
/*  84 */         this.goalSelector.a(2, this.c);
/*  85 */         if ((getNavigation() instanceof Navigation)) {
/*  86 */           ((Navigation)getNavigation()).a(false);
/*     */         }
/*     */         
/*  89 */         this.bm = true;
/*     */       }
/*     */       
/*  92 */       o(f);
/*  93 */       if (f > 4.0F) {
/*  94 */         getNavigation().a(entity, 1.0D);
/*     */       }
/*     */       
/*  97 */       if (f > 6.0F) {
/*  98 */         double d0 = (entity.locX - this.locX) / f;
/*  99 */         double d1 = (entity.locY - this.locY) / f;
/* 100 */         double d2 = (entity.locZ - this.locZ) / f;
/*     */         
/* 102 */         this.motX += d0 * Math.abs(d0) * 0.4D;
/* 103 */         this.motY += d1 * Math.abs(d1) * 0.4D;
/* 104 */         this.motZ += d2 * Math.abs(d2) * 0.4D;
/*     */       }
/*     */       
/* 107 */       if (f > 10.0F) {
/* 108 */         this.world.getServer().getPluginManager().callEvent(new EntityUnleashEvent(getBukkitEntity(), EntityUnleashEvent.UnleashReason.DISTANCE));
/* 109 */         unleash(true, true);
/*     */       }
/* 111 */     } else if ((!cc()) && (this.bm)) {
/* 112 */       this.bm = false;
/* 113 */       this.goalSelector.a(this.c);
/* 114 */       if ((getNavigation() instanceof Navigation)) {
/* 115 */         ((Navigation)getNavigation()).a(true);
/*     */       }
/*     */       
/* 118 */       cj();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void o(float f) {}
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityCreature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */