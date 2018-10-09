/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class EntitySquid extends EntityWaterAnimal {
/*     */   public float a;
/*     */   public float b;
/*     */   public float c;
/*     */   public float bk;
/*     */   public float bl;
/*     */   public float bm;
/*     */   public float bn;
/*     */   public float bo;
/*     */   private float bp;
/*     */   private float bq;
/*     */   private float br;
/*     */   private float bs;
/*     */   private float bt;
/*     */   private float bu;
/*     */   
/*  21 */   public EntitySquid(World world) { super(world);
/*  22 */     setSize(0.95F, 0.95F);
/*  23 */     this.random.setSeed(1 + getId());
/*  24 */     this.bq = (1.0F / (this.random.nextFloat() + 1.0F) * 0.2F);
/*  25 */     this.goalSelector.a(0, new PathfinderGoalSquid(this));
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  29 */     super.initAttributes();
/*  30 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
/*     */   }
/*     */   
/*     */   public float getHeadHeight() {
/*  34 */     return this.length * 0.5F;
/*     */   }
/*     */   
/*     */   protected String z() {
/*  38 */     return null;
/*     */   }
/*     */   
/*     */   protected String bo() {
/*  42 */     return null;
/*     */   }
/*     */   
/*     */   protected String bp() {
/*  46 */     return null;
/*     */   }
/*     */   
/*     */   protected float bB() {
/*  50 */     return 0.4F;
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/*  54 */     return null;
/*     */   }
/*     */   
/*     */   protected boolean s_() {
/*  58 */     return false;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/*  62 */     int j = this.random.nextInt(3 + i) + 1;
/*     */     
/*  64 */     for (int k = 0; k < j; k++) {
/*  65 */       a(new ItemStack(Items.DYE, 1, EnumColor.BLACK.getInvColorIndex()), 0.0F);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void m()
/*     */   {
/*  77 */     super.m();
/*  78 */     this.b = this.a;
/*  79 */     this.bk = this.c;
/*  80 */     this.bm = this.bl;
/*  81 */     this.bo = this.bn;
/*  82 */     this.bl += this.bq;
/*  83 */     if (this.bl > 6.283185307179586D) {
/*  84 */       if (this.world.isClientSide) {
/*  85 */         this.bl = 6.2831855F;
/*     */       } else {
/*  87 */         this.bl = ((float)(this.bl - 6.283185307179586D));
/*  88 */         if (this.random.nextInt(10) == 0) {
/*  89 */           this.bq = (1.0F / (this.random.nextFloat() + 1.0F) * 0.2F);
/*     */         }
/*     */         
/*  92 */         this.world.broadcastEntityEffect(this, (byte)19);
/*     */       }
/*     */     }
/*     */     
/*  96 */     if (this.inWater)
/*     */     {
/*     */ 
/*  99 */       if (this.bl < 3.1415927F) {
/* 100 */         float f = this.bl / 3.1415927F;
/* 101 */         this.bn = (MathHelper.sin(f * f * 3.1415927F) * 3.1415927F * 0.25F);
/* 102 */         if (f > 0.75D) {
/* 103 */           this.bp = 1.0F;
/* 104 */           this.br = 1.0F;
/*     */         } else {
/* 106 */           this.br *= 0.8F;
/*     */         }
/*     */       } else {
/* 109 */         this.bn = 0.0F;
/* 110 */         this.bp *= 0.9F;
/* 111 */         this.br *= 0.99F;
/*     */       }
/*     */       
/* 114 */       if (!this.world.isClientSide) {
/* 115 */         this.motX = (this.bs * this.bp);
/* 116 */         this.motY = (this.bt * this.bp);
/* 117 */         this.motZ = (this.bu * this.bp);
/*     */       }
/*     */       
/* 120 */       float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/* 121 */       this.aI += (-(float)MathHelper.b(this.motX, this.motZ) * 180.0F / 3.1415927F - this.aI) * 0.1F;
/* 122 */       this.yaw = this.aI;
/* 123 */       this.c = ((float)(this.c + 3.141592653589793D * this.br * 1.5D));
/* 124 */       this.a += (-(float)MathHelper.b(f, this.motY) * 180.0F / 3.1415927F - this.a) * 0.1F;
/*     */     } else {
/* 126 */       this.bn = (MathHelper.e(MathHelper.sin(this.bl)) * 3.1415927F * 0.25F);
/* 127 */       if (!this.world.isClientSide) {
/* 128 */         this.motX = 0.0D;
/* 129 */         this.motY -= 0.08D;
/* 130 */         this.motY *= 0.9800000190734863D;
/* 131 */         this.motZ = 0.0D;
/*     */       }
/*     */       
/* 134 */       this.a = ((float)(this.a + (-90.0F - this.a) * 0.02D));
/*     */     }
/*     */   }
/*     */   
/*     */   public void g(float f, float f1)
/*     */   {
/* 140 */     move(this.motX, this.motY, this.motZ);
/*     */   }
/*     */   
/*     */   public boolean bR() {
/* 144 */     return (this.locY > 45.0D) && (this.locY < this.world.F()) && (super.bR());
/*     */   }
/*     */   
/*     */   public void b(float f, float f1, float f2) {
/* 148 */     this.bs = f;
/* 149 */     this.bt = f1;
/* 150 */     this.bu = f2;
/*     */   }
/*     */   
/*     */   public boolean n() {
/* 154 */     return (this.bs != 0.0F) || (this.bt != 0.0F) || (this.bu != 0.0F);
/*     */   }
/*     */   
/*     */   static class PathfinderGoalSquid extends PathfinderGoal
/*     */   {
/*     */     private EntitySquid a;
/*     */     
/*     */     public PathfinderGoalSquid(EntitySquid entitysquid) {
/* 162 */       this.a = entitysquid;
/*     */     }
/*     */     
/*     */     public boolean a() {
/* 166 */       return true;
/*     */     }
/*     */     
/*     */     public void e() {
/* 170 */       int i = this.a.bh();
/*     */       
/* 172 */       if (i > 100) {
/* 173 */         this.a.b(0.0F, 0.0F, 0.0F);
/* 174 */       } else if ((this.a.bc().nextInt(50) == 0) || (!this.a.inWater) || (!this.a.n())) {
/* 175 */         float f = this.a.bc().nextFloat() * 3.1415927F * 2.0F;
/* 176 */         float f1 = MathHelper.cos(f) * 0.2F;
/* 177 */         float f2 = -0.1F + this.a.bc().nextFloat() * 0.2F;
/* 178 */         float f3 = MathHelper.sin(f) * 0.2F;
/*     */         
/* 180 */         this.a.b(f1, f2, f3);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntitySquid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */