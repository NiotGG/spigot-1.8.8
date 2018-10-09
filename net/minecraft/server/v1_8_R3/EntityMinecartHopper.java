/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
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
/*     */ 
/*     */ public class EntityMinecartHopper
/*     */   extends EntityMinecartContainer
/*     */   implements IHopper
/*     */ {
/*  23 */   private boolean a = true;
/*  24 */   private int b = -1;
/*  25 */   private BlockPosition c = BlockPosition.ZERO;
/*     */   
/*     */   public EntityMinecartHopper(World paramWorld) {
/*  28 */     super(paramWorld);
/*     */   }
/*     */   
/*     */   public EntityMinecartHopper(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/*  32 */     super(paramWorld, paramDouble1, paramDouble2, paramDouble3);
/*     */   }
/*     */   
/*     */   public EntityMinecartAbstract.EnumMinecartType s()
/*     */   {
/*  37 */     return EntityMinecartAbstract.EnumMinecartType.HOPPER;
/*     */   }
/*     */   
/*     */   public IBlockData u()
/*     */   {
/*  42 */     return Blocks.HOPPER.getBlockData();
/*     */   }
/*     */   
/*     */   public int w()
/*     */   {
/*  47 */     return 1;
/*     */   }
/*     */   
/*     */   public int getSize()
/*     */   {
/*  52 */     return 5;
/*     */   }
/*     */   
/*     */   public boolean e(EntityHuman paramEntityHuman)
/*     */   {
/*  57 */     if (!this.world.isClientSide) {
/*  58 */       paramEntityHuman.openContainer(this);
/*     */     }
/*     */     
/*  61 */     return true;
/*     */   }
/*     */   
/*     */   public void a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
/*     */   {
/*  66 */     boolean bool = !paramBoolean;
/*     */     
/*  68 */     if (bool != y()) {
/*  69 */       i(bool);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean y() {
/*  74 */     return this.a;
/*     */   }
/*     */   
/*     */   public void i(boolean paramBoolean) {
/*  78 */     this.a = paramBoolean;
/*     */   }
/*     */   
/*     */   public World getWorld()
/*     */   {
/*  83 */     return this.world;
/*     */   }
/*     */   
/*     */   public double A()
/*     */   {
/*  88 */     return this.locX;
/*     */   }
/*     */   
/*     */   public double B()
/*     */   {
/*  93 */     return this.locY + 0.5D;
/*     */   }
/*     */   
/*     */   public double C()
/*     */   {
/*  98 */     return this.locZ;
/*     */   }
/*     */   
/*     */   public void t_()
/*     */   {
/* 103 */     super.t_();
/*     */     
/* 105 */     if ((!this.world.isClientSide) && (isAlive()) && (y())) {
/* 106 */       BlockPosition localBlockPosition = new BlockPosition(this);
/* 107 */       if (localBlockPosition.equals(this.c)) {
/* 108 */         this.b -= 1;
/*     */       } else {
/* 110 */         m(0);
/*     */       }
/*     */       
/* 113 */       if (!E()) {
/* 114 */         m(0);
/*     */         
/* 116 */         if (D()) {
/* 117 */           m(4);
/* 118 */           update();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean D() {
/* 125 */     if (TileEntityHopper.a(this)) {
/* 126 */       return true;
/*     */     }
/*     */     
/* 129 */     List localList = this.world.a(EntityItem.class, getBoundingBox().grow(0.25D, 0.0D, 0.25D), IEntitySelector.a);
/*     */     
/* 131 */     if (localList.size() > 0) {
/* 132 */       TileEntityHopper.a(this, (EntityItem)localList.get(0));
/*     */     }
/*     */     
/* 135 */     return false;
/*     */   }
/*     */   
/*     */   public void a(DamageSource paramDamageSource)
/*     */   {
/* 140 */     super.a(paramDamageSource);
/*     */     
/* 142 */     if (this.world.getGameRules().getBoolean("doEntityDrops")) {
/* 143 */       a(Item.getItemOf(Blocks.HOPPER), 1, 0.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 149 */     super.b(paramNBTTagCompound);
/* 150 */     paramNBTTagCompound.setInt("TransferCooldown", this.b);
/*     */   }
/*     */   
/*     */   protected void a(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 155 */     super.a(paramNBTTagCompound);
/* 156 */     this.b = paramNBTTagCompound.getInt("TransferCooldown");
/*     */   }
/*     */   
/*     */   public void m(int paramInt) {
/* 160 */     this.b = paramInt;
/*     */   }
/*     */   
/*     */   public boolean E() {
/* 164 */     return this.b > 0;
/*     */   }
/*     */   
/*     */   public String getContainerName()
/*     */   {
/* 169 */     return "minecraft:hopper";
/*     */   }
/*     */   
/*     */   public Container createContainer(PlayerInventory paramPlayerInventory, EntityHuman paramEntityHuman)
/*     */   {
/* 174 */     return new ContainerHopper(paramPlayerInventory, this, paramEntityHuman);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityMinecartHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */