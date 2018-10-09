/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public abstract class EntityAgeable extends EntityCreature { protected int a;
/*     */   protected int b;
/*     */   protected int c;
/*   8 */   private float bm = -1.0F;
/*     */   private float bn;
/*  10 */   public boolean ageLocked = false;
/*     */   
/*     */ 
/*     */ 
/*     */   public void inactiveTick()
/*     */   {
/*  16 */     super.inactiveTick();
/*  17 */     if ((this.world.isClientSide) || (this.ageLocked))
/*     */     {
/*  19 */       a(isBaby());
/*     */     }
/*     */     else {
/*  22 */       int i = getAge();
/*     */       
/*  24 */       if (i < 0)
/*     */       {
/*  26 */         i++;
/*  27 */         setAgeRaw(i);
/*  28 */       } else if (i > 0)
/*     */       {
/*  30 */         i--;
/*  31 */         setAgeRaw(i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public EntityAgeable(World world)
/*     */   {
/*  38 */     super(world);
/*     */   }
/*     */   
/*     */   public abstract EntityAgeable createChild(EntityAgeable paramEntityAgeable);
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  44 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/*  46 */     if ((itemstack != null) && (itemstack.getItem() == Items.SPAWN_EGG)) {
/*  47 */       if (!this.world.isClientSide) {
/*  48 */         Class oclass = EntityTypes.a(itemstack.getData());
/*     */         
/*  50 */         if ((oclass != null) && (getClass() == oclass)) {
/*  51 */           EntityAgeable entityageable = createChild(this);
/*     */           
/*  53 */           if (entityageable != null) {
/*  54 */             entityageable.setAgeRaw(41536);
/*  55 */             entityageable.setPositionRotation(this.locX, this.locY, this.locZ, 0.0F, 0.0F);
/*  56 */             this.world.addEntity(entityageable, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SPAWNER_EGG);
/*  57 */             if (itemstack.hasName()) {
/*  58 */               entityageable.setCustomName(itemstack.getName());
/*     */             }
/*     */             
/*  61 */             if (!entityhuman.abilities.canInstantlyBuild) {
/*  62 */               itemstack.count -= 1;
/*  63 */               if (itemstack.count == 0) {
/*  64 */                 entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, null);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*  71 */       return true;
/*     */     }
/*  73 */     return false;
/*     */   }
/*     */   
/*     */   protected void h()
/*     */   {
/*  78 */     super.h();
/*  79 */     this.datawatcher.a(12, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public int getAge() {
/*  83 */     return this.world.isClientSide ? this.datawatcher.getByte(12) : this.a;
/*     */   }
/*     */   
/*     */   public void setAge(int i, boolean flag) {
/*  87 */     int j = getAge();
/*  88 */     int k = j;
/*     */     
/*  90 */     j += i * 20;
/*  91 */     if (j > 0) {
/*  92 */       j = 0;
/*  93 */       if (k < 0) {
/*  94 */         n();
/*     */       }
/*     */     }
/*     */     
/*  98 */     int l = j - k;
/*     */     
/* 100 */     setAgeRaw(j);
/* 101 */     if (flag) {
/* 102 */       this.b += l;
/* 103 */       if (this.c == 0) {
/* 104 */         this.c = 40;
/*     */       }
/*     */     }
/*     */     
/* 108 */     if (getAge() == 0) {
/* 109 */       setAgeRaw(this.b);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setAge(int i)
/*     */   {
/* 115 */     setAge(i, false);
/*     */   }
/*     */   
/*     */   public void setAgeRaw(int i) {
/* 119 */     this.datawatcher.watch(12, Byte.valueOf((byte)MathHelper.clamp(i, -1, 1)));
/* 120 */     this.a = i;
/* 121 */     a(isBaby());
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 125 */     super.b(nbttagcompound);
/* 126 */     nbttagcompound.setInt("Age", getAge());
/* 127 */     nbttagcompound.setInt("ForcedAge", this.b);
/* 128 */     nbttagcompound.setBoolean("AgeLocked", this.ageLocked);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 132 */     super.a(nbttagcompound);
/* 133 */     setAgeRaw(nbttagcompound.getInt("Age"));
/* 134 */     this.b = nbttagcompound.getInt("ForcedAge");
/* 135 */     this.ageLocked = nbttagcompound.getBoolean("AgeLocked");
/*     */   }
/*     */   
/*     */   public void m() {
/* 139 */     super.m();
/* 140 */     if ((this.world.isClientSide) || (this.ageLocked)) {
/* 141 */       if (this.c > 0) {
/* 142 */         if (this.c % 4 == 0) {
/* 143 */           this.world.addParticle(EnumParticle.VILLAGER_HAPPY, this.locX + this.random.nextFloat() * this.width * 2.0F - this.width, this.locY + 0.5D + this.random.nextFloat() * this.length, this.locZ + this.random.nextFloat() * this.width * 2.0F - this.width, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */         
/* 146 */         this.c -= 1;
/*     */       }
/*     */       
/* 149 */       a(isBaby());
/*     */     } else {
/* 151 */       int i = getAge();
/*     */       
/* 153 */       if (i < 0) {
/* 154 */         i++;
/* 155 */         setAgeRaw(i);
/* 156 */         if (i == 0) {
/* 157 */           n();
/*     */         }
/* 159 */       } else if (i > 0) {
/* 160 */         i--;
/* 161 */         setAgeRaw(i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void n() {}
/*     */   
/*     */   public boolean isBaby()
/*     */   {
/* 170 */     return getAge() < 0;
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/* 174 */     a(flag ? 0.5F : 1.0F);
/*     */   }
/*     */   
/*     */   public final void setSize(float f, float f1) {
/* 178 */     boolean flag = this.bm > 0.0F;
/*     */     
/* 180 */     this.bm = f;
/* 181 */     this.bn = f1;
/* 182 */     if (!flag) {
/* 183 */       a(1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void a(float f)
/*     */   {
/* 189 */     super.setSize(this.bm * f, this.bn * f);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityAgeable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */