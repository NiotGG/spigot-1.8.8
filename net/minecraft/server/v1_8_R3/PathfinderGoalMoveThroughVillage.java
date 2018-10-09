/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
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
/*     */ public class PathfinderGoalMoveThroughVillage
/*     */   extends PathfinderGoal
/*     */ {
/*     */   private EntityCreature a;
/*     */   private double b;
/*     */   private PathEntity c;
/*     */   private VillageDoor d;
/*     */   private boolean e;
/*  23 */   private List<VillageDoor> f = Lists.newArrayList();
/*     */   
/*     */   public PathfinderGoalMoveThroughVillage(EntityCreature paramEntityCreature, double paramDouble, boolean paramBoolean) {
/*  26 */     this.a = paramEntityCreature;
/*  27 */     this.b = paramDouble;
/*  28 */     this.e = paramBoolean;
/*  29 */     a(1);
/*     */     
/*  31 */     if (!(paramEntityCreature.getNavigation() instanceof Navigation)) {
/*  32 */       throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a()
/*     */   {
/*  38 */     f();
/*     */     
/*  40 */     if ((this.e) && (this.a.world.w())) {
/*  41 */       return false;
/*     */     }
/*     */     
/*  44 */     Village localVillage = this.a.world.ae().getClosestVillage(new BlockPosition(this.a), 0);
/*  45 */     if (localVillage == null) {
/*  46 */       return false;
/*     */     }
/*     */     
/*  49 */     this.d = a(localVillage);
/*  50 */     if (this.d == null) {
/*  51 */       return false;
/*     */     }
/*     */     
/*  54 */     Navigation localNavigation = (Navigation)this.a.getNavigation();
/*  55 */     boolean bool = localNavigation.g();
/*  56 */     localNavigation.b(false);
/*  57 */     this.c = localNavigation.a(this.d.d());
/*  58 */     localNavigation.b(bool);
/*  59 */     if (this.c != null) {
/*  60 */       return true;
/*     */     }
/*     */     
/*  63 */     Vec3D localVec3D = RandomPositionGenerator.a(this.a, 10, 7, new Vec3D(this.d.d().getX(), this.d.d().getY(), this.d.d().getZ()));
/*  64 */     if (localVec3D == null) {
/*  65 */       return false;
/*     */     }
/*  67 */     localNavigation.b(false);
/*  68 */     this.c = this.a.getNavigation().a(localVec3D.a, localVec3D.b, localVec3D.c);
/*  69 */     localNavigation.b(bool);
/*  70 */     return this.c != null;
/*     */   }
/*     */   
/*     */   public boolean b()
/*     */   {
/*  75 */     if (this.a.getNavigation().m()) {
/*  76 */       return false;
/*     */     }
/*  78 */     float f1 = this.a.width + 4.0F;
/*  79 */     return this.a.b(this.d.d()) > f1 * f1;
/*     */   }
/*     */   
/*     */   public void c()
/*     */   {
/*  84 */     this.a.getNavigation().a(this.c, this.b);
/*     */   }
/*     */   
/*     */   public void d()
/*     */   {
/*  89 */     if ((this.a.getNavigation().m()) || (this.a.b(this.d.d()) < 16.0D)) {
/*  90 */       this.f.add(this.d);
/*     */     }
/*     */   }
/*     */   
/*     */   private VillageDoor a(Village paramVillage) {
/*  95 */     Object localObject = null;
/*  96 */     int i = Integer.MAX_VALUE;
/*  97 */     List localList = paramVillage.f();
/*  98 */     for (VillageDoor localVillageDoor : localList) {
/*  99 */       int j = localVillageDoor.b(MathHelper.floor(this.a.locX), MathHelper.floor(this.a.locY), MathHelper.floor(this.a.locZ));
/* 100 */       if (j < i)
/* 101 */         if (!a(localVillageDoor))
/*     */         {
/*     */ 
/* 104 */           localObject = localVillageDoor;
/* 105 */           i = j;
/*     */         }
/*     */     }
/* 108 */     return (VillageDoor)localObject;
/*     */   }
/*     */   
/*     */   private boolean a(VillageDoor paramVillageDoor) {
/* 112 */     for (VillageDoor localVillageDoor : this.f) {
/* 113 */       if (paramVillageDoor.d().equals(localVillageDoor.d())) {
/* 114 */         return true;
/*     */       }
/*     */     }
/* 117 */     return false;
/*     */   }
/*     */   
/*     */   private void f() {
/* 121 */     if (this.f.size() > 15) {
/* 122 */       this.f.remove(0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalMoveThroughVillage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */