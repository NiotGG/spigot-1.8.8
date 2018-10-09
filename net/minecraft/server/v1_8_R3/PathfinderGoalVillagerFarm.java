/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathfinderGoalVillagerFarm
/*     */   extends PathfinderGoalGotoTarget
/*     */ {
/*     */   private final EntityVillager c;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean d;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean e;
/*     */   
/*     */ 
/*     */ 
/*     */   private int f;
/*     */   
/*     */ 
/*     */ 
/*     */   public PathfinderGoalVillagerFarm(EntityVillager paramEntityVillager, double paramDouble)
/*     */   {
/*  27 */     super(paramEntityVillager, paramDouble, 16);
/*  28 */     this.c = paramEntityVillager;
/*     */   }
/*     */   
/*     */   public boolean a()
/*     */   {
/*  33 */     if (this.a <= 0) {
/*  34 */       if (!this.c.world.getGameRules().getBoolean("mobGriefing")) {
/*  35 */         return false;
/*     */       }
/*     */       
/*     */ 
/*  39 */       this.f = -1;
/*  40 */       this.d = this.c.cu();
/*  41 */       this.e = this.c.ct();
/*     */     }
/*     */     
/*  44 */     return super.a();
/*     */   }
/*     */   
/*     */   public boolean b()
/*     */   {
/*  49 */     return (this.f >= 0) && (super.b());
/*     */   }
/*     */   
/*     */   public void c()
/*     */   {
/*  54 */     super.c();
/*     */   }
/*     */   
/*     */   public void d()
/*     */   {
/*  59 */     super.d();
/*     */   }
/*     */   
/*     */   public void e()
/*     */   {
/*  64 */     super.e();
/*     */     
/*  66 */     this.c.getControllerLook().a(this.b.getX() + 0.5D, this.b.getY() + 1, this.b.getZ() + 0.5D, 10.0F, this.c.bQ());
/*     */     
/*  68 */     if (f()) {
/*  69 */       World localWorld = this.c.world;
/*  70 */       BlockPosition localBlockPosition = this.b.up();
/*     */       
/*  72 */       IBlockData localIBlockData = localWorld.getType(localBlockPosition);
/*  73 */       Block localBlock = localIBlockData.getBlock();
/*     */       
/*  75 */       if ((this.f == 0) && ((localBlock instanceof BlockCrops)) && (((Integer)localIBlockData.get(BlockCrops.AGE)).intValue() == 7)) {
/*  76 */         localWorld.setAir(localBlockPosition, true);
/*  77 */       } else if ((this.f == 1) && (localBlock == Blocks.AIR))
/*     */       {
/*     */ 
/*  80 */         InventorySubcontainer localInventorySubcontainer = this.c.cq();
/*  81 */         for (int i = 0; i < localInventorySubcontainer.getSize(); i++) {
/*  82 */           ItemStack localItemStack = localInventorySubcontainer.getItem(i);
/*  83 */           int j = 0;
/*  84 */           if (localItemStack != null) {
/*  85 */             if (localItemStack.getItem() == Items.WHEAT_SEEDS) {
/*  86 */               localWorld.setTypeAndData(localBlockPosition, Blocks.WHEAT.getBlockData(), 3);
/*  87 */               j = 1;
/*  88 */             } else if (localItemStack.getItem() == Items.POTATO) {
/*  89 */               localWorld.setTypeAndData(localBlockPosition, Blocks.POTATOES.getBlockData(), 3);
/*  90 */               j = 1;
/*  91 */             } else if (localItemStack.getItem() == Items.CARROT) {
/*  92 */               localWorld.setTypeAndData(localBlockPosition, Blocks.CARROTS.getBlockData(), 3);
/*  93 */               j = 1;
/*     */             }
/*     */           }
/*  96 */           if (j != 0) {
/*  97 */             localItemStack.count -= 1;
/*  98 */             if (localItemStack.count > 0) break;
/*  99 */             localInventorySubcontainer.setItem(i, null); break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 105 */       this.f = -1;
/*     */       
/* 107 */       this.a = 10;
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean a(World paramWorld, BlockPosition paramBlockPosition)
/*     */   {
/* 113 */     Block localBlock = paramWorld.getType(paramBlockPosition).getBlock();
/*     */     
/* 115 */     if (localBlock == Blocks.FARMLAND) {
/* 116 */       paramBlockPosition = paramBlockPosition.up();
/* 117 */       IBlockData localIBlockData = paramWorld.getType(paramBlockPosition);
/* 118 */       localBlock = localIBlockData.getBlock();
/*     */       
/* 120 */       if (((localBlock instanceof BlockCrops)) && (((Integer)localIBlockData.get(BlockCrops.AGE)).intValue() == 7) && (this.e) && ((this.f == 0) || (this.f < 0))) {
/* 121 */         this.f = 0;
/* 122 */         return true; }
/* 123 */       if ((localBlock == Blocks.AIR) && (this.d) && ((this.f == 1) || (this.f < 0))) {
/* 124 */         this.f = 1;
/* 125 */         return true;
/*     */       }
/*     */     }
/* 128 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalVillagerFarm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */