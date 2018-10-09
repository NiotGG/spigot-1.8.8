/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class TileEntityPiston extends TileEntity implements IUpdatePlayerListBox
/*     */ {
/*     */   private IBlockData a;
/*     */   private EnumDirection f;
/*     */   private boolean g;
/*     */   private boolean h;
/*     */   private float i;
/*     */   private float j;
/*  15 */   private List<Entity> k = Lists.newArrayList();
/*     */   
/*     */   public TileEntityPiston() {}
/*     */   
/*     */   public TileEntityPiston(IBlockData iblockdata, EnumDirection enumdirection, boolean flag, boolean flag1) {
/*  20 */     this.a = iblockdata;
/*  21 */     this.f = enumdirection;
/*  22 */     this.g = flag;
/*  23 */     this.h = flag1;
/*     */   }
/*     */   
/*     */   public IBlockData b() {
/*  27 */     return this.a;
/*     */   }
/*     */   
/*     */   public int u() {
/*  31 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  35 */     return this.g;
/*     */   }
/*     */   
/*     */   public EnumDirection e() {
/*  39 */     return this.f;
/*     */   }
/*     */   
/*     */   public float a(float f) {
/*  43 */     if (f > 1.0F) {
/*  44 */       f = 1.0F;
/*     */     }
/*     */     
/*  47 */     return this.j + (this.i - this.j) * f;
/*     */   }
/*     */   
/*     */   private void a(float f, float f1) {
/*  51 */     if (this.g) {
/*  52 */       f = 1.0F - f;
/*     */     } else {
/*  54 */       f -= 1.0F;
/*     */     }
/*     */     
/*  57 */     AxisAlignedBB axisalignedbb = Blocks.PISTON_EXTENSION.a(this.world, this.position, this.a, f, this.f);
/*     */     
/*  59 */     if (axisalignedbb != null) {
/*  60 */       List list = this.world.getEntities(null, axisalignedbb);
/*     */       
/*  62 */       if (!list.isEmpty()) {
/*  63 */         this.k.addAll(list);
/*  64 */         Iterator iterator = this.k.iterator();
/*     */         
/*  66 */         while (iterator.hasNext()) {
/*  67 */           Entity entity = (Entity)iterator.next();
/*     */           
/*  69 */           if ((this.a.getBlock() == Blocks.SLIME) && (this.g)) {}
/*  70 */           switch (SyntheticClass_1.a[this.f.k().ordinal()]) {
/*     */           case 1: 
/*  72 */             entity.motX = this.f.getAdjacentX();
/*  73 */             break;
/*     */           
/*     */           case 2: 
/*  76 */             entity.motY = this.f.getAdjacentY();
/*  77 */             break;
/*     */           
/*     */           case 3: 
/*  80 */             entity.motZ = this.f.getAdjacentZ();
/*     */           
/*     */           default: 
/*  83 */             continue;entity.move(f1 * this.f.getAdjacentX(), f1 * this.f.getAdjacentY(), f1 * this.f.getAdjacentZ());
/*     */           }
/*     */           
/*     */         }
/*  87 */         this.k.clear();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void h()
/*     */   {
/*  94 */     if ((this.j < 1.0F) && (this.world != null)) {
/*  95 */       this.j = (this.i = 1.0F);
/*  96 */       this.world.t(this.position);
/*  97 */       y();
/*  98 */       if (this.world.getType(this.position).getBlock() == Blocks.PISTON_EXTENSION) {
/*  99 */         this.world.setTypeAndData(this.position, this.a, 3);
/* 100 */         this.world.d(this.position, this.a.getBlock());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void c()
/*     */   {
/* 107 */     if (this.world == null) return;
/* 108 */     this.j = this.i;
/* 109 */     if (this.j >= 1.0F) {
/* 110 */       a(1.0F, 0.25F);
/* 111 */       this.world.t(this.position);
/* 112 */       y();
/* 113 */       if (this.world.getType(this.position).getBlock() == Blocks.PISTON_EXTENSION) {
/* 114 */         this.world.setTypeAndData(this.position, this.a, 3);
/* 115 */         this.world.d(this.position, this.a.getBlock());
/*     */       }
/*     */     }
/*     */     else {
/* 119 */       this.i += 0.5F;
/* 120 */       if (this.i >= 1.0F) {
/* 121 */         this.i = 1.0F;
/*     */       }
/*     */       
/* 124 */       if (this.g) {
/* 125 */         a(this.i, this.i - this.j + 0.0625F);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound)
/*     */   {
/* 132 */     super.a(nbttagcompound);
/* 133 */     this.a = Block.getById(nbttagcompound.getInt("blockId")).fromLegacyData(nbttagcompound.getInt("blockData"));
/* 134 */     this.f = EnumDirection.fromType1(nbttagcompound.getInt("facing"));
/* 135 */     this.j = (this.i = nbttagcompound.getFloat("progress"));
/* 136 */     this.g = nbttagcompound.getBoolean("extending");
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 140 */     super.b(nbttagcompound);
/* 141 */     nbttagcompound.setInt("blockId", Block.getId(this.a.getBlock()));
/* 142 */     nbttagcompound.setInt("blockData", this.a.getBlock().toLegacyData(this.a));
/* 143 */     nbttagcompound.setInt("facing", this.f.a());
/* 144 */     nbttagcompound.setFloat("progress", this.j);
/* 145 */     nbttagcompound.setBoolean("extending", this.g);
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/* 150 */     static final int[] a = new int[EnumDirection.EnumAxis.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 154 */         a[EnumDirection.EnumAxis.X.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 160 */         a[EnumDirection.EnumAxis.Y.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/* 166 */         a[EnumDirection.EnumAxis.Z.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityPiston.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */