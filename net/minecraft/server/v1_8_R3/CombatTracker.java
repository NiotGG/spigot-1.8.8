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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CombatTracker
/*     */ {
/*  21 */   private final List<CombatEntry> a = Lists.newArrayList();
/*     */   private final EntityLiving b;
/*     */   private int c;
/*     */   private int d;
/*     */   private int e;
/*     */   private boolean f;
/*     */   private boolean g;
/*     */   private String h;
/*     */   
/*     */   public CombatTracker(EntityLiving paramEntityLiving) {
/*  31 */     this.b = paramEntityLiving;
/*     */   }
/*     */   
/*     */   public void a() {
/*  35 */     j();
/*     */     
/*  37 */     if (this.b.k_()) {
/*  38 */       Block localBlock = this.b.world.getType(new BlockPosition(this.b.locX, this.b.getBoundingBox().b, this.b.locZ)).getBlock();
/*     */       
/*  40 */       if (localBlock == Blocks.LADDER) {
/*  41 */         this.h = "ladder";
/*  42 */       } else if (localBlock == Blocks.VINE) {
/*  43 */         this.h = "vines";
/*     */       }
/*  45 */     } else if (this.b.V()) {
/*  46 */       this.h = "water";
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(DamageSource paramDamageSource, float paramFloat1, float paramFloat2) {
/*  51 */     g();
/*  52 */     a();
/*     */     
/*  54 */     CombatEntry localCombatEntry = new CombatEntry(paramDamageSource, this.b.ticksLived, paramFloat1, paramFloat2, this.h, this.b.fallDistance);
/*     */     
/*  56 */     this.a.add(localCombatEntry);
/*  57 */     this.c = this.b.ticksLived;
/*  58 */     this.g = true;
/*     */     
/*  60 */     if ((localCombatEntry.f()) && (!this.f) && (this.b.isAlive())) {
/*  61 */       this.f = true;
/*  62 */       this.d = this.b.ticksLived;
/*  63 */       this.e = this.d;
/*  64 */       this.b.enterCombat();
/*     */     }
/*     */   }
/*     */   
/*     */   public IChatBaseComponent b() {
/*  69 */     if (this.a.size() == 0) {
/*  70 */       return new ChatMessage("death.attack.generic", new Object[] { this.b.getScoreboardDisplayName() });
/*     */     }
/*     */     
/*  73 */     CombatEntry localCombatEntry1 = i();
/*  74 */     CombatEntry localCombatEntry2 = (CombatEntry)this.a.get(this.a.size() - 1);
/*     */     
/*  76 */     IChatBaseComponent localIChatBaseComponent1 = localCombatEntry2.h();
/*  77 */     Entity localEntity1 = localCombatEntry2.a().getEntity();
/*     */     Object localObject1;
/*  79 */     if ((localCombatEntry1 != null) && (localCombatEntry2.a() == DamageSource.FALL)) {
/*  80 */       IChatBaseComponent localIChatBaseComponent2 = localCombatEntry1.h();
/*     */       
/*  82 */       if ((localCombatEntry1.a() == DamageSource.FALL) || (localCombatEntry1.a() == DamageSource.OUT_OF_WORLD)) {
/*  83 */         localObject1 = new ChatMessage("death.fell.accident." + a(localCombatEntry1), new Object[] { this.b.getScoreboardDisplayName() }); } else { Entity localEntity2;
/*  84 */         if ((localIChatBaseComponent2 != null) && ((localIChatBaseComponent1 == null) || (!localIChatBaseComponent2.equals(localIChatBaseComponent1)))) {
/*  85 */           localEntity2 = localCombatEntry1.a().getEntity();
/*  86 */           Object localObject2 = (localEntity2 instanceof EntityLiving) ? ((EntityLiving)localEntity2).bA() : null;
/*     */           
/*  88 */           if ((localObject2 != null) && (((ItemStack)localObject2).hasName())) {
/*  89 */             localObject1 = new ChatMessage("death.fell.assist.item", new Object[] { this.b.getScoreboardDisplayName(), localIChatBaseComponent2, ((ItemStack)localObject2).C() });
/*     */           } else {
/*  91 */             localObject1 = new ChatMessage("death.fell.assist", new Object[] { this.b.getScoreboardDisplayName(), localIChatBaseComponent2 });
/*     */           }
/*  93 */         } else if (localIChatBaseComponent1 != null) {
/*  94 */           localEntity2 = (localEntity1 instanceof EntityLiving) ? ((EntityLiving)localEntity1).bA() : null;
/*  95 */           if ((localEntity2 != null) && (localEntity2.hasName())) {
/*  96 */             localObject1 = new ChatMessage("death.fell.finish.item", new Object[] { this.b.getScoreboardDisplayName(), localIChatBaseComponent1, localEntity2.C() });
/*     */           } else {
/*  98 */             localObject1 = new ChatMessage("death.fell.finish", new Object[] { this.b.getScoreboardDisplayName(), localIChatBaseComponent1 });
/*     */           }
/*     */         } else {
/* 101 */           localObject1 = new ChatMessage("death.fell.killer", new Object[] { this.b.getScoreboardDisplayName() });
/*     */         }
/*     */       }
/* 104 */     } else { localObject1 = localCombatEntry2.a().getLocalizedDeathMessage(this.b);
/*     */     }
/*     */     
/* 107 */     return (IChatBaseComponent)localObject1;
/*     */   }
/*     */   
/*     */   public EntityLiving c() {
/* 111 */     EntityLiving localEntityLiving = null;
/* 112 */     EntityHuman localEntityHuman = null;
/* 113 */     float f1 = 0.0F;
/* 114 */     float f2 = 0.0F;
/*     */     
/* 116 */     for (CombatEntry localCombatEntry : this.a) {
/* 117 */       if (((localCombatEntry.a().getEntity() instanceof EntityHuman)) && ((localEntityHuman == null) || (localCombatEntry.c() > f2))) {
/* 118 */         f2 = localCombatEntry.c();
/* 119 */         localEntityHuman = (EntityHuman)localCombatEntry.a().getEntity();
/*     */       }
/*     */       
/* 122 */       if (((localCombatEntry.a().getEntity() instanceof EntityLiving)) && ((localEntityLiving == null) || (localCombatEntry.c() > f1))) {
/* 123 */         f1 = localCombatEntry.c();
/* 124 */         localEntityLiving = (EntityLiving)localCombatEntry.a().getEntity();
/*     */       }
/*     */     }
/*     */     
/* 128 */     if ((localEntityHuman != null) && (f2 >= f1 / 3.0F)) {
/* 129 */       return localEntityHuman;
/*     */     }
/* 131 */     return localEntityLiving;
/*     */   }
/*     */   
/*     */   private CombatEntry i()
/*     */   {
/* 136 */     Object localObject1 = null;
/* 137 */     Object localObject2 = null;
/* 138 */     int i = 0;
/* 139 */     float f1 = 0.0F;
/*     */     
/* 141 */     for (int j = 0; j < this.a.size(); j++) {
/* 142 */       CombatEntry localCombatEntry = (CombatEntry)this.a.get(j);
/* 143 */       Object localObject3 = j > 0 ? (CombatEntry)this.a.get(j - 1) : null;
/*     */       
/* 145 */       if (((localCombatEntry.a() == DamageSource.FALL) || (localCombatEntry.a() == DamageSource.OUT_OF_WORLD)) && (localCombatEntry.i() > 0.0F) && ((localObject1 == null) || (localCombatEntry.i() > f1))) {
/* 146 */         if (j > 0) {
/* 147 */           localObject1 = localObject3;
/*     */         } else {
/* 149 */           localObject1 = localCombatEntry;
/*     */         }
/* 151 */         f1 = localCombatEntry.i();
/*     */       }
/*     */       
/* 154 */       if ((localCombatEntry.g() != null) && ((localObject2 == null) || (localCombatEntry.c() > i))) {
/* 155 */         localObject2 = localCombatEntry;
/*     */       }
/*     */     }
/*     */     
/* 159 */     if ((f1 > 5.0F) && (localObject1 != null))
/* 160 */       return (CombatEntry)localObject1;
/* 161 */     if ((i > 5) && (localObject2 != null)) {
/* 162 */       return (CombatEntry)localObject2;
/*     */     }
/* 164 */     return null;
/*     */   }
/*     */   
/*     */   private String a(CombatEntry paramCombatEntry)
/*     */   {
/* 169 */     return paramCombatEntry.g() == null ? "generic" : paramCombatEntry.g();
/*     */   }
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
/*     */   public int f()
/*     */   {
/* 183 */     if (this.f) {
/* 184 */       return this.b.ticksLived - this.d;
/*     */     }
/* 186 */     return this.e - this.d;
/*     */   }
/*     */   
/*     */   private void j()
/*     */   {
/* 191 */     this.h = null;
/*     */   }
/*     */   
/*     */   public void g() {
/* 195 */     int i = this.f ? 300 : 100;
/*     */     
/* 197 */     if ((this.g) && ((!this.b.isAlive()) || (this.b.ticksLived - this.c > i))) {
/* 198 */       boolean bool = this.f;
/* 199 */       this.g = false;
/* 200 */       this.f = false;
/* 201 */       this.e = this.b.ticksLived;
/*     */       
/* 203 */       if (bool) {
/* 204 */         this.b.exitCombat();
/*     */       }
/* 206 */       this.a.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public EntityLiving h() {
/* 211 */     return this.b;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CombatTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */