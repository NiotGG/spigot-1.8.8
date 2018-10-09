/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathfinderGoalPassengerCarrotStick
/*     */   extends PathfinderGoal
/*     */ {
/*     */   private final EntityInsentient a;
/*     */   private final float b;
/*     */   private float c;
/*     */   private boolean d;
/*     */   private int e;
/*     */   private int f;
/*     */   
/*     */   public PathfinderGoalPassengerCarrotStick(EntityInsentient paramEntityInsentient, float paramFloat)
/*     */   {
/*  31 */     this.a = paramEntityInsentient;
/*  32 */     this.b = paramFloat;
/*  33 */     a(7);
/*     */   }
/*     */   
/*     */   public void c()
/*     */   {
/*  38 */     this.c = 0.0F;
/*     */   }
/*     */   
/*     */   public void d()
/*     */   {
/*  43 */     this.d = false;
/*  44 */     this.c = 0.0F;
/*     */   }
/*     */   
/*     */   public boolean a()
/*     */   {
/*  49 */     return (this.a.isAlive()) && (this.a.passenger != null) && ((this.a.passenger instanceof EntityHuman)) && ((this.d) || (this.a.bW()));
/*     */   }
/*     */   
/*     */   public void e()
/*     */   {
/*  54 */     EntityHuman localEntityHuman = (EntityHuman)this.a.passenger;
/*  55 */     EntityCreature localEntityCreature = (EntityCreature)this.a;
/*     */     
/*  57 */     float f1 = MathHelper.g(localEntityHuman.yaw - this.a.yaw) * 0.5F;
/*  58 */     if (f1 > 5.0F) {
/*  59 */       f1 = 5.0F;
/*     */     }
/*  61 */     if (f1 < -5.0F) {
/*  62 */       f1 = -5.0F;
/*     */     }
/*     */     
/*  65 */     this.a.yaw = MathHelper.g(this.a.yaw + f1);
/*  66 */     if (this.c < this.b) {
/*  67 */       this.c += (this.b - this.c) * 0.01F;
/*     */     }
/*  69 */     if (this.c > this.b) {
/*  70 */       this.c = this.b;
/*     */     }
/*     */     
/*  73 */     int i = MathHelper.floor(this.a.locX);
/*  74 */     int j = MathHelper.floor(this.a.locY);
/*  75 */     int k = MathHelper.floor(this.a.locZ);
/*  76 */     float f2 = this.c;
/*  77 */     if (this.d) {
/*  78 */       if (this.e++ > this.f) {
/*  79 */         this.d = false;
/*     */       }
/*  81 */       f2 += f2 * 1.15F * MathHelper.sin(this.e / this.f * 3.1415927F);
/*     */     }
/*     */     
/*  84 */     float f3 = 0.91F;
/*  85 */     if (this.a.onGround) {
/*  86 */       f3 = this.a.world.getType(new BlockPosition(MathHelper.d(i), MathHelper.d(j) - 1, MathHelper.d(k))).getBlock().frictionFactor * 0.91F;
/*     */     }
/*  88 */     float f4 = 0.16277136F / (f3 * f3 * f3);
/*  89 */     float f5 = MathHelper.sin(localEntityCreature.yaw * 3.1415927F / 180.0F);
/*  90 */     float f6 = MathHelper.cos(localEntityCreature.yaw * 3.1415927F / 180.0F);
/*  91 */     float f7 = localEntityCreature.bI() * f4;
/*  92 */     float f8 = Math.max(f2, 1.0F);
/*  93 */     f8 = f7 / f8;
/*  94 */     float f9 = f2 * f8;
/*  95 */     float f10 = -(f9 * f5);
/*  96 */     float f11 = f9 * f6;
/*     */     
/*  98 */     if (MathHelper.e(f10) > MathHelper.e(f11)) {
/*  99 */       if (f10 < 0.0F) {
/* 100 */         f10 -= this.a.width / 2.0F;
/*     */       }
/* 102 */       if (f10 > 0.0F) {
/* 103 */         f10 += this.a.width / 2.0F;
/*     */       }
/* 105 */       f11 = 0.0F;
/*     */     } else {
/* 107 */       f10 = 0.0F;
/* 108 */       if (f11 < 0.0F) {
/* 109 */         f11 -= this.a.width / 2.0F;
/*     */       }
/* 111 */       if (f11 > 0.0F) {
/* 112 */         f11 += this.a.width / 2.0F;
/*     */       }
/*     */     }
/*     */     
/* 116 */     int m = MathHelper.floor(this.a.locX + f10);
/* 117 */     int n = MathHelper.floor(this.a.locZ + f11);
/*     */     
/* 119 */     int i1 = MathHelper.d(this.a.width + 1.0F);
/* 120 */     int i2 = MathHelper.d(this.a.length + localEntityHuman.length + 1.0F);
/* 121 */     int i3 = MathHelper.d(this.a.width + 1.0F);
/*     */     Object localObject;
/* 123 */     if ((i != m) || (k != n))
/*     */     {
/*     */ 
/* 126 */       localObject = this.a.world.getType(new BlockPosition(i, j, k)).getBlock();
/* 127 */       int i4 = (!a((Block)localObject)) && ((((Block)localObject).getMaterial() != Material.AIR) || (!a(this.a.world.getType(new BlockPosition(i, j - 1, k)).getBlock()))) ? 1 : 0;
/*     */       
/* 129 */       if ((i4 != 0) && (0 == PathfinderNormal.a(this.a.world, this.a, m, j, n, i1, i2, i3, false, false, true)) && (1 == PathfinderNormal.a(this.a.world, this.a, i, j + 1, k, i1, i2, i3, false, false, true)) && (1 == PathfinderNormal.a(this.a.world, this.a, m, j + 1, n, i1, i2, i3, false, false, true)))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 134 */         localEntityCreature.getControllerJump().a();
/*     */       }
/*     */     }
/*     */     
/* 138 */     if ((!localEntityHuman.abilities.canInstantlyBuild) && (this.c >= this.b * 0.5F) && (this.a.bc().nextFloat() < 0.006F) && (!this.d)) {
/* 139 */       localObject = localEntityHuman.bA();
/*     */       
/* 141 */       if ((localObject != null) && (((ItemStack)localObject).getItem() == Items.CARROT_ON_A_STICK)) {
/* 142 */         ((ItemStack)localObject).damage(1, localEntityHuman);
/*     */         
/* 144 */         if (((ItemStack)localObject).count == 0) {
/* 145 */           ItemStack localItemStack = new ItemStack(Items.FISHING_ROD);
/* 146 */           localItemStack.setTag(((ItemStack)localObject).getTag());
/* 147 */           localEntityHuman.inventory.items[localEntityHuman.inventory.itemInHandIndex] = localItemStack;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 152 */     this.a.g(0.0F, f2);
/*     */   }
/*     */   
/*     */   private boolean a(Block paramBlock) {
/* 156 */     return ((paramBlock instanceof BlockStairs)) || ((paramBlock instanceof BlockStepAbstract));
/*     */   }
/*     */   
/*     */   public boolean f() {
/* 160 */     return this.d;
/*     */   }
/*     */   
/*     */   public void g() {
/* 164 */     this.d = true;
/* 165 */     this.e = 0;
/* 166 */     this.f = (this.a.bc().nextInt(841) + 140);
/*     */   }
/*     */   
/*     */   public boolean h() {
/* 170 */     return (!f()) && (this.c > this.b * 0.3F);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalPassengerCarrotStick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */