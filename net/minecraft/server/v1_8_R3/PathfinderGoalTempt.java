/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ public class PathfinderGoalTempt
/*     */   extends PathfinderGoal
/*     */ {
/*     */   private EntityCreature a;
/*     */   private double b;
/*     */   private double c;
/*     */   private double d;
/*     */   private double e;
/*     */   private double f;
/*     */   private double g;
/*     */   private EntityHuman h;
/*     */   private int i;
/*     */   private boolean j;
/*     */   private Item k;
/*     */   private boolean l;
/*     */   private boolean m;
/*     */   
/*     */   public PathfinderGoalTempt(EntityCreature paramEntityCreature, double paramDouble, Item paramItem, boolean paramBoolean)
/*     */   {
/*  22 */     this.a = paramEntityCreature;
/*  23 */     this.b = paramDouble;
/*  24 */     this.k = paramItem;
/*  25 */     this.l = paramBoolean;
/*  26 */     a(3);
/*  27 */     if (!(paramEntityCreature.getNavigation() instanceof Navigation)) {
/*  28 */       throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a()
/*     */   {
/*  34 */     if (this.i > 0) {
/*  35 */       this.i -= 1;
/*  36 */       return false;
/*     */     }
/*  38 */     this.h = this.a.world.findNearbyPlayer(this.a, 10.0D);
/*  39 */     if (this.h == null) {
/*  40 */       return false;
/*     */     }
/*  42 */     ItemStack localItemStack = this.h.bZ();
/*  43 */     if (localItemStack == null) {
/*  44 */       return false;
/*     */     }
/*  46 */     if (localItemStack.getItem() != this.k) {
/*  47 */       return false;
/*     */     }
/*  49 */     return true;
/*     */   }
/*     */   
/*     */   public boolean b()
/*     */   {
/*  54 */     if (this.l) {
/*  55 */       if (this.a.h(this.h) < 36.0D) {
/*  56 */         if (this.h.e(this.c, this.d, this.e) > 0.010000000000000002D) {
/*  57 */           return false;
/*     */         }
/*  59 */         if ((Math.abs(this.h.pitch - this.f) > 5.0D) || (Math.abs(this.h.yaw - this.g) > 5.0D)) {
/*  60 */           return false;
/*     */         }
/*     */       } else {
/*  63 */         this.c = this.h.locX;
/*  64 */         this.d = this.h.locY;
/*  65 */         this.e = this.h.locZ;
/*     */       }
/*  67 */       this.f = this.h.pitch;
/*  68 */       this.g = this.h.yaw;
/*     */     }
/*  70 */     return a();
/*     */   }
/*     */   
/*     */   public void c()
/*     */   {
/*  75 */     this.c = this.h.locX;
/*  76 */     this.d = this.h.locY;
/*  77 */     this.e = this.h.locZ;
/*  78 */     this.j = true;
/*  79 */     this.m = ((Navigation)this.a.getNavigation()).e();
/*  80 */     ((Navigation)this.a.getNavigation()).a(false);
/*     */   }
/*     */   
/*     */   public void d()
/*     */   {
/*  85 */     this.h = null;
/*  86 */     this.a.getNavigation().n();
/*  87 */     this.i = 100;
/*  88 */     this.j = false;
/*  89 */     ((Navigation)this.a.getNavigation()).a(this.m);
/*     */   }
/*     */   
/*     */   public void e()
/*     */   {
/*  94 */     this.a.getControllerLook().a(this.h, 30.0F, this.a.bQ());
/*  95 */     if (this.a.h(this.h) < 6.25D) {
/*  96 */       this.a.getNavigation().n();
/*     */     } else {
/*  98 */       this.a.getNavigation().a(this.h, this.b);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean f() {
/* 103 */     return this.j;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalTempt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */