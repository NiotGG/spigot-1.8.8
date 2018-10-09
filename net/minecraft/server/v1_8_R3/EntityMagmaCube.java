/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class EntityMagmaCube
/*     */   extends EntitySlime
/*     */ {
/*     */   public EntityMagmaCube(World paramWorld)
/*     */   {
/*  11 */     super(paramWorld);
/*  12 */     this.fireProof = true;
/*     */   }
/*     */   
/*     */   protected void initAttributes()
/*     */   {
/*  17 */     super.initAttributes();
/*     */     
/*  19 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
/*     */   }
/*     */   
/*     */   public boolean bR()
/*     */   {
/*  24 */     return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
/*     */   }
/*     */   
/*     */   public boolean canSpawn()
/*     */   {
/*  29 */     return (this.world.a(getBoundingBox(), this)) && (this.world.getCubes(this, getBoundingBox()).isEmpty()) && (!this.world.containsLiquid(getBoundingBox()));
/*     */   }
/*     */   
/*     */   public int br()
/*     */   {
/*  34 */     return getSize() * 3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public float c(float paramFloat)
/*     */   {
/*  44 */     return 1.0F;
/*     */   }
/*     */   
/*     */   protected EnumParticle n()
/*     */   {
/*  49 */     return EnumParticle.FLAME;
/*     */   }
/*     */   
/*     */   protected EntitySlime cf()
/*     */   {
/*  54 */     return new EntityMagmaCube(this.world);
/*     */   }
/*     */   
/*     */   protected Item getLoot()
/*     */   {
/*  59 */     return Items.MAGMA_CREAM;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean paramBoolean, int paramInt)
/*     */   {
/*  64 */     Item localItem = getLoot();
/*  65 */     if ((localItem != null) && (getSize() > 1)) {
/*  66 */       int i = this.random.nextInt(4) - 2;
/*  67 */       if (paramInt > 0) {
/*  68 */         i += this.random.nextInt(paramInt + 1);
/*     */       }
/*  70 */       for (int j = 0; j < i; j++) {
/*  71 */         a(localItem, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isBurning()
/*     */   {
/*  78 */     return false;
/*     */   }
/*     */   
/*     */   protected int cg()
/*     */   {
/*  83 */     return super.cg() * 4;
/*     */   }
/*     */   
/*     */   protected void ch()
/*     */   {
/*  88 */     this.a *= 0.9F;
/*     */   }
/*     */   
/*     */   protected void bF()
/*     */   {
/*  93 */     this.motY = (0.42F + getSize() * 0.1F);
/*  94 */     this.ai = true;
/*     */   }
/*     */   
/*     */   protected void bH()
/*     */   {
/*  99 */     this.motY = (0.22F + getSize() * 0.05F);
/* 100 */     this.ai = true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void e(float paramFloat1, float paramFloat2) {}
/*     */   
/*     */ 
/*     */   protected boolean ci()
/*     */   {
/* 109 */     return true;
/*     */   }
/*     */   
/*     */   protected int cj()
/*     */   {
/* 114 */     return super.cj() + 2;
/*     */   }
/*     */   
/*     */   protected String ck()
/*     */   {
/* 119 */     if (getSize() > 1) {
/* 120 */       return "mob.magmacube.big";
/*     */     }
/* 122 */     return "mob.magmacube.small";
/*     */   }
/*     */   
/*     */   protected boolean cl()
/*     */   {
/* 127 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityMagmaCube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */