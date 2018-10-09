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
/*     */ public class EntityMinecartFurnace
/*     */   extends EntityMinecartAbstract
/*     */ {
/*     */   private int c;
/*     */   public double a;
/*     */   public double b;
/*     */   
/*     */   public EntityMinecartFurnace(World paramWorld)
/*     */   {
/*  25 */     super(paramWorld);
/*     */   }
/*     */   
/*     */   public EntityMinecartFurnace(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/*  29 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*     */   }
/*     */   
/*     */   public EntityMinecartAbstract.EnumMinecartType s()
/*     */   {
/*  34 */     return EntityMinecartAbstract.EnumMinecartType.FURNACE;
/*     */   }
/*     */   
/*     */   protected void h()
/*     */   {
/*  39 */     super.h();
/*  40 */     this.datawatcher.a(16, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   public void t_()
/*     */   {
/*  45 */     super.t_();
/*     */     
/*  47 */     if (this.c > 0) {
/*  48 */       this.c -= 1;
/*     */     }
/*  50 */     if (this.c <= 0) {
/*  51 */       this.a = (this.b = 0.0D);
/*     */     }
/*  53 */     i(this.c > 0);
/*     */     
/*  55 */     if ((j()) && (this.random.nextInt(4) == 0)) {
/*  56 */       this.world.addParticle(EnumParticle.SMOKE_LARGE, this.locX, this.locY + 0.8D, this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */   }
/*     */   
/*     */   protected double m()
/*     */   {
/*  62 */     return 0.2D;
/*     */   }
/*     */   
/*     */   public void a(DamageSource paramDamageSource)
/*     */   {
/*  67 */     super.a(paramDamageSource);
/*     */     
/*  69 */     if ((!paramDamageSource.isExplosion()) && (this.world.getGameRules().getBoolean("doEntityDrops"))) {
/*  70 */       a(new ItemStack(Blocks.FURNACE, 1), 0.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(BlockPosition paramBlockPosition, IBlockData paramIBlockData)
/*     */   {
/*  76 */     super.a(paramBlockPosition, paramIBlockData);
/*     */     
/*  78 */     double d1 = this.a * this.a + this.b * this.b;
/*  79 */     if ((d1 > 1.0E-4D) && (this.motX * this.motX + this.motZ * this.motZ > 0.001D)) {
/*  80 */       d1 = MathHelper.sqrt(d1);
/*  81 */       this.a /= d1;
/*  82 */       this.b /= d1;
/*     */       
/*  84 */       if (this.a * this.motX + this.b * this.motZ < 0.0D) {
/*  85 */         this.a = 0.0D;
/*  86 */         this.b = 0.0D;
/*     */       } else {
/*  88 */         double d2 = d1 / m();
/*  89 */         this.a *= d2;
/*  90 */         this.b *= d2;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void o()
/*     */   {
/*  97 */     double d1 = this.a * this.a + this.b * this.b;
/*     */     
/*  99 */     if (d1 > 1.0E-4D) {
/* 100 */       d1 = MathHelper.sqrt(d1);
/* 101 */       this.a /= d1;
/* 102 */       this.b /= d1;
/* 103 */       double d2 = 1.0D;
/* 104 */       this.motX *= 0.800000011920929D;
/* 105 */       this.motY *= 0.0D;
/* 106 */       this.motZ *= 0.800000011920929D;
/* 107 */       this.motX += this.a * d2;
/* 108 */       this.motZ += this.b * d2;
/*     */     } else {
/* 110 */       this.motX *= 0.9800000190734863D;
/* 111 */       this.motY *= 0.0D;
/* 112 */       this.motZ *= 0.9800000190734863D;
/*     */     }
/*     */     
/* 115 */     super.o();
/*     */   }
/*     */   
/*     */   public boolean e(EntityHuman paramEntityHuman)
/*     */   {
/* 120 */     ItemStack localItemStack = paramEntityHuman.inventory.getItemInHand();
/* 121 */     if ((localItemStack != null) && (localItemStack.getItem() == Items.COAL)) {
/* 122 */       if (!paramEntityHuman.abilities.canInstantlyBuild) if (--localItemStack.count == 0) {
/* 123 */           paramEntityHuman.inventory.setItem(paramEntityHuman.inventory.itemInHandIndex, null);
/*     */         }
/* 125 */       this.c += 3600;
/*     */     }
/* 127 */     this.a = (this.locX - paramEntityHuman.locX);
/* 128 */     this.b = (this.locZ - paramEntityHuman.locZ);
/*     */     
/* 130 */     return true;
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 135 */     super.b(paramNBTTagCompound);
/* 136 */     paramNBTTagCompound.setDouble("PushX", this.a);
/* 137 */     paramNBTTagCompound.setDouble("PushZ", this.b);
/* 138 */     paramNBTTagCompound.setShort("Fuel", (short)this.c);
/*     */   }
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 143 */     super.a(paramNBTTagCompound);
/* 144 */     this.a = paramNBTTagCompound.getDouble("PushX");
/* 145 */     this.b = paramNBTTagCompound.getDouble("PushZ");
/* 146 */     this.c = paramNBTTagCompound.getShort("Fuel");
/*     */   }
/*     */   
/*     */   protected boolean j() {
/* 150 */     return (this.datawatcher.getByte(16) & 0x1) != 0;
/*     */   }
/*     */   
/*     */   protected void i(boolean paramBoolean) {
/* 154 */     if (paramBoolean) {
/* 155 */       this.datawatcher.watch(16, Byte.valueOf((byte)(this.datawatcher.getByte(16) | 0x1)));
/*     */     } else {
/* 157 */       this.datawatcher.watch(16, Byte.valueOf((byte)(this.datawatcher.getByte(16) & 0xFFFFFFFE)));
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockData u()
/*     */   {
/* 163 */     return (j() ? Blocks.LIT_FURNACE : Blocks.FURNACE).getBlockData().set(BlockFurnace.FACING, EnumDirection.NORTH);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityMinecartFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */