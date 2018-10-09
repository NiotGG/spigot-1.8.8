/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
/*     */ import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
/*     */ 
/*     */ public class EntityExperienceOrb extends Entity
/*     */ {
/*     */   public int a;
/*     */   public int b;
/*     */   public int c;
/*  14 */   private int d = 5;
/*     */   public int value;
/*     */   private EntityHuman targetPlayer;
/*     */   private int targetTime;
/*     */   
/*     */   public EntityExperienceOrb(World world, double d0, double d1, double d2, int i) {
/*  20 */     super(world);
/*  21 */     setSize(0.5F, 0.5F);
/*  22 */     setPosition(d0, d1, d2);
/*  23 */     this.yaw = ((float)(Math.random() * 360.0D));
/*  24 */     this.motX = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
/*  25 */     this.motY = ((float)(Math.random() * 0.2D) * 2.0F);
/*  26 */     this.motZ = ((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
/*  27 */     this.value = i;
/*     */   }
/*     */   
/*     */   protected boolean s_() {
/*  31 */     return false;
/*     */   }
/*     */   
/*     */   public EntityExperienceOrb(World world) {
/*  35 */     super(world);
/*  36 */     setSize(0.25F, 0.25F);
/*     */   }
/*     */   
/*     */   protected void h() {}
/*     */   
/*     */   public void t_() {
/*  42 */     super.t_();
/*  43 */     EntityHuman prevTarget = this.targetPlayer;
/*  44 */     if (this.c > 0) {
/*  45 */       this.c -= 1;
/*     */     }
/*     */     
/*  48 */     this.lastX = this.locX;
/*  49 */     this.lastY = this.locY;
/*  50 */     this.lastZ = this.locZ;
/*  51 */     this.motY -= 0.029999999329447746D;
/*  52 */     if (this.world.getType(new BlockPosition(this)).getBlock().getMaterial() == Material.LAVA) {
/*  53 */       this.motY = 0.20000000298023224D;
/*  54 */       this.motX = ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*  55 */       this.motZ = ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
/*  56 */       makeSound("random.fizz", 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
/*     */     }
/*     */     
/*  59 */     j(this.locX, (getBoundingBox().b + getBoundingBox().e) / 2.0D, this.locZ);
/*  60 */     double d0 = 8.0D;
/*     */     
/*  62 */     if (this.targetTime < this.a - 20 + getId() % 100) {
/*  63 */       if ((this.targetPlayer == null) || (this.targetPlayer.h(this) > d0 * d0)) {
/*  64 */         this.targetPlayer = this.world.findNearbyPlayer(this, d0);
/*     */       }
/*     */       
/*  67 */       this.targetTime = this.a;
/*     */     }
/*     */     
/*  70 */     if ((this.targetPlayer != null) && (this.targetPlayer.isSpectator())) {
/*  71 */       this.targetPlayer = null;
/*     */     }
/*     */     
/*  74 */     if (this.targetPlayer != null)
/*     */     {
/*  76 */       boolean cancelled = false;
/*  77 */       if (this.targetPlayer != prevTarget) {
/*  78 */         EntityTargetLivingEntityEvent event = CraftEventFactory.callEntityTargetLivingEvent(this, this.targetPlayer, EntityTargetEvent.TargetReason.CLOSEST_PLAYER);
/*  79 */         EntityLiving target = event.getTarget() == null ? null : ((CraftLivingEntity)event.getTarget()).getHandle();
/*  80 */         this.targetPlayer = ((target instanceof EntityHuman) ? (EntityHuman)target : null);
/*  81 */         cancelled = event.isCancelled();
/*     */       }
/*     */       
/*  84 */       if ((!cancelled) && (this.targetPlayer != null)) {
/*  85 */         double d1 = (this.targetPlayer.locX - this.locX) / d0;
/*  86 */         double d2 = (this.targetPlayer.locY + this.targetPlayer.getHeadHeight() - this.locY) / d0;
/*  87 */         double d3 = (this.targetPlayer.locZ - this.locZ) / d0;
/*  88 */         double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
/*  89 */         double d5 = 1.0D - d4;
/*     */         
/*  91 */         if (d5 > 0.0D) {
/*  92 */           d5 *= d5;
/*  93 */           this.motX += d1 / d4 * d5 * 0.1D;
/*  94 */           this.motY += d2 / d4 * d5 * 0.1D;
/*  95 */           this.motZ += d3 / d4 * d5 * 0.1D;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 101 */     move(this.motX, this.motY, this.motZ);
/* 102 */     float f = 0.98F;
/*     */     
/* 104 */     if (this.onGround) {
/* 105 */       f = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.98F;
/*     */     }
/*     */     
/* 108 */     this.motX *= f;
/* 109 */     this.motY *= 0.9800000190734863D;
/* 110 */     this.motZ *= f;
/* 111 */     if (this.onGround) {
/* 112 */       this.motY *= -0.8999999761581421D;
/*     */     }
/*     */     
/* 115 */     this.a += 1;
/* 116 */     this.b += 1;
/* 117 */     if (this.b >= 6000) {
/* 118 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean W()
/*     */   {
/* 124 */     return this.world.a(getBoundingBox(), Material.WATER, this);
/*     */   }
/*     */   
/*     */   protected void burn(int i) {
/* 128 */     damageEntity(DamageSource.FIRE, i);
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 132 */     if (isInvulnerable(damagesource)) {
/* 133 */       return false;
/*     */     }
/* 135 */     ac();
/* 136 */     this.d = ((int)(this.d - f));
/* 137 */     if (this.d <= 0) {
/* 138 */       die();
/*     */     }
/*     */     
/* 141 */     return false;
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 146 */     nbttagcompound.setShort("Health", (short)(byte)this.d);
/* 147 */     nbttagcompound.setShort("Age", (short)this.b);
/* 148 */     nbttagcompound.setShort("Value", (short)this.value);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 152 */     this.d = (nbttagcompound.getShort("Health") & 0xFF);
/* 153 */     this.b = nbttagcompound.getShort("Age");
/* 154 */     this.value = nbttagcompound.getShort("Value");
/*     */   }
/*     */   
/*     */   public void d(EntityHuman entityhuman) {
/* 158 */     if ((!this.world.isClientSide) && 
/* 159 */       (this.c == 0) && (entityhuman.bp == 0)) {
/* 160 */       entityhuman.bp = 2;
/* 161 */       this.world.makeSound(entityhuman, "random.orb", 0.1F, 0.5F * ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.8F));
/* 162 */       entityhuman.receive(this, 1);
/* 163 */       entityhuman.giveExp(CraftEventFactory.callPlayerExpChangeEvent(entityhuman, this.value).getAmount());
/* 164 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public int j()
/*     */   {
/* 171 */     return this.value;
/*     */   }
/*     */   
/*     */   public static int getOrbValue(int i)
/*     */   {
/* 176 */     if (i > 162670129) return i - 100000;
/* 177 */     if (i > 81335063) return 81335063;
/* 178 */     if (i > 40667527) return 40667527;
/* 179 */     if (i > 20333759) return 20333759;
/* 180 */     if (i > 10166857) return 10166857;
/* 181 */     if (i > 5083423) return 5083423;
/* 182 */     if (i > 2541701) return 2541701;
/* 183 */     if (i > 1270849) return 1270849;
/* 184 */     if (i > 635413) return 635413;
/* 185 */     if (i > 317701) return 317701;
/* 186 */     if (i > 158849) return 158849;
/* 187 */     if (i > 79423) return 79423;
/* 188 */     if (i > 39709) return 39709;
/* 189 */     if (i > 19853) return 19853;
/* 190 */     if (i > 9923) return 9923;
/* 191 */     if (i > 4957) { return 4957;
/*     */     }
/* 193 */     return i >= 3 ? 3 : i >= 7 ? 7 : i >= 17 ? 17 : i >= 37 ? 37 : i >= 73 ? 73 : i >= 149 ? 149 : i >= 307 ? 307 : i >= 617 ? 617 : i >= 1237 ? 1237 : i >= 2477 ? 2477 : 1;
/*     */   }
/*     */   
/*     */   public boolean aD() {
/* 197 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityExperienceOrb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */