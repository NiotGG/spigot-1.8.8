/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class VillageSiege
/*     */ {
/*     */   private World a;
/*     */   private boolean b;
/*  10 */   private int c = -1;
/*     */   private int d;
/*     */   private int e;
/*     */   private Village f;
/*     */   private int g;
/*     */   private int h;
/*     */   private int i;
/*     */   
/*     */   public VillageSiege(World world) {
/*  19 */     this.a = world;
/*     */   }
/*     */   
/*     */   public void a() {
/*  23 */     if (this.a.w()) {
/*  24 */       this.c = 0;
/*  25 */     } else if (this.c != 2) {
/*  26 */       if (this.c == 0) {
/*  27 */         float f = this.a.c(0.0F);
/*     */         
/*  29 */         if ((f < 0.5D) || (f > 0.501D)) {
/*  30 */           return;
/*     */         }
/*     */         
/*  33 */         this.c = (this.a.random.nextInt(10) == 0 ? 1 : 2);
/*  34 */         this.b = false;
/*  35 */         if (this.c == 2) {
/*  36 */           return;
/*     */         }
/*     */       }
/*     */       
/*  40 */       if (this.c != -1) {
/*  41 */         if (!this.b) {
/*  42 */           if (!b()) {
/*  43 */             return;
/*     */           }
/*     */           
/*  46 */           this.b = true;
/*     */         }
/*     */         
/*  49 */         if (this.e > 0) {
/*  50 */           this.e -= 1;
/*     */         } else {
/*  52 */           this.e = 2;
/*  53 */           if (this.d > 0) {
/*  54 */             c();
/*  55 */             this.d -= 1;
/*     */           } else {
/*  57 */             this.c = 2;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean b()
/*     */   {
/*  66 */     java.util.List list = this.a.players;
/*  67 */     Iterator iterator = list.iterator();
/*     */     
/*  69 */     while (iterator.hasNext()) {
/*  70 */       EntityHuman entityhuman = (EntityHuman)iterator.next();
/*     */       
/*  72 */       if (!entityhuman.isSpectator()) {
/*  73 */         this.f = this.a.ae().getClosestVillage(new BlockPosition(entityhuman), 1);
/*  74 */         if ((this.f != null) && (this.f.c() >= 10) && (this.f.d() >= 20) && (this.f.e() >= 20)) {
/*  75 */           BlockPosition blockposition = this.f.a();
/*  76 */           float f = this.f.b();
/*  77 */           boolean flag = false;
/*  78 */           int i = 0;
/*     */           
/*     */ 
/*  81 */           while (i < 10) {
/*  82 */             float f1 = this.a.random.nextFloat() * 3.1415927F * 2.0F;
/*     */             
/*  84 */             this.g = (blockposition.getX() + (int)(MathHelper.cos(f1) * f * 0.9D));
/*  85 */             this.h = blockposition.getY();
/*  86 */             this.i = (blockposition.getZ() + (int)(MathHelper.sin(f1) * f * 0.9D));
/*  87 */             flag = false;
/*  88 */             Iterator iterator1 = this.a.ae().getVillages().iterator();
/*     */             
/*  90 */             while (iterator1.hasNext()) {
/*  91 */               Village village = (Village)iterator1.next();
/*     */               
/*  93 */               if ((village != this.f) && (village.a(new BlockPosition(this.g, this.h, this.i)))) {
/*  94 */                 flag = true;
/*  95 */                 break;
/*     */               }
/*     */             }
/*     */             
/*  99 */             if (!flag) break;
/* 100 */             i++;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 105 */           if (flag) {
/* 106 */             return false;
/*     */           }
/*     */           
/* 109 */           Vec3D vec3d = a(new BlockPosition(this.g, this.h, this.i));
/*     */           
/* 111 */           if (vec3d != null) {
/* 112 */             this.e = 0;
/* 113 */             this.d = 20;
/* 114 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 122 */     return false;
/*     */   }
/*     */   
/*     */   private boolean c() {
/* 126 */     Vec3D vec3d = a(new BlockPosition(this.g, this.h, this.i));
/*     */     
/* 128 */     if (vec3d == null) {
/* 129 */       return false;
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 134 */       EntityZombie entityzombie = new EntityZombie(this.a);
/* 135 */       entityzombie.prepare(this.a.E(new BlockPosition(entityzombie)), null);
/* 136 */       entityzombie.setVillager(false);
/*     */     } catch (Exception exception) {
/* 138 */       exception.printStackTrace();
/* 139 */       return false;
/*     */     }
/*     */     EntityZombie entityzombie;
/* 142 */     entityzombie.setPositionRotation(vec3d.a, vec3d.b, vec3d.c, this.a.random.nextFloat() * 360.0F, 0.0F);
/* 143 */     this.a.addEntity(entityzombie, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.VILLAGE_INVASION);
/* 144 */     BlockPosition blockposition = this.f.a();
/*     */     
/* 146 */     entityzombie.a(blockposition, this.f.b());
/* 147 */     return true;
/*     */   }
/*     */   
/*     */   private Vec3D a(BlockPosition blockposition)
/*     */   {
/* 152 */     for (int i = 0; i < 10; i++) {
/* 153 */       BlockPosition blockposition1 = blockposition.a(this.a.random.nextInt(16) - 8, this.a.random.nextInt(6) - 3, this.a.random.nextInt(16) - 8);
/*     */       
/* 155 */       if ((this.f.a(blockposition1)) && (SpawnerCreature.a(EntityInsentient.EnumEntityPositionType.ON_GROUND, this.a, blockposition1))) {
/* 156 */         return new Vec3D(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
/*     */       }
/*     */     }
/*     */     
/* 160 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\VillageSiege.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */