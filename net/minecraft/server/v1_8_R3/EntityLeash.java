/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ 
/*     */ public class EntityLeash extends EntityHanging
/*     */ {
/*     */   public EntityLeash(World world)
/*     */   {
/*  11 */     super(world);
/*     */   }
/*     */   
/*     */   public EntityLeash(World world, BlockPosition blockposition) {
/*  15 */     super(world, blockposition);
/*  16 */     setPosition(blockposition.getX() + 0.5D, blockposition.getY() + 0.5D, blockposition.getZ() + 0.5D);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  21 */     a(new AxisAlignedBB(this.locX - 0.1875D, this.locY - 0.25D + 0.125D, this.locZ - 0.1875D, this.locX + 0.1875D, this.locY + 0.25D + 0.125D, this.locZ + 0.1875D));
/*     */   }
/*     */   
/*     */   protected void h() {
/*  25 */     super.h();
/*     */   }
/*     */   
/*     */   public void setDirection(EnumDirection enumdirection) {}
/*     */   
/*     */   public int l() {
/*  31 */     return 9;
/*     */   }
/*     */   
/*     */   public int m() {
/*  35 */     return 9;
/*     */   }
/*     */   
/*     */   public float getHeadHeight() {
/*  39 */     return -0.0625F;
/*     */   }
/*     */   
/*     */   public void b(Entity entity) {}
/*     */   
/*     */   public boolean d(NBTTagCompound nbttagcompound) {
/*  45 */     return false;
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   public boolean e(EntityHuman entityhuman) {
/*  53 */     ItemStack itemstack = entityhuman.bA();
/*  54 */     boolean flag = false;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  60 */     if ((itemstack != null) && (itemstack.getItem() == Items.LEAD) && (!this.world.isClientSide)) {
/*  61 */       double d0 = 7.0D;
/*  62 */       List list = this.world.a(EntityInsentient.class, new AxisAlignedBB(this.locX - d0, this.locY - d0, this.locZ - d0, this.locX + d0, this.locY + d0, this.locZ + d0));
/*  63 */       Iterator iterator = list.iterator();
/*     */       
/*  65 */       while (iterator.hasNext()) {
/*  66 */         EntityInsentient entityinsentient = (EntityInsentient)iterator.next();
/*  67 */         if ((entityinsentient.cc()) && (entityinsentient.getLeashHolder() == entityhuman))
/*     */         {
/*  69 */           if (CraftEventFactory.callPlayerLeashEntityEvent(entityinsentient, this, entityhuman).isCancelled()) {
/*  70 */             ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, entityinsentient, entityinsentient.getLeashHolder()));
/*     */           }
/*     */           else
/*     */           {
/*  74 */             entityinsentient.setLeashHolder(this, true);
/*  75 */             flag = true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  80 */     if ((!this.world.isClientSide) && (!flag))
/*     */     {
/*     */ 
/*  83 */       boolean die = true;
/*     */       
/*     */ 
/*  86 */       double d0 = 7.0D;
/*  87 */       List list = this.world.a(EntityInsentient.class, new AxisAlignedBB(this.locX - d0, this.locY - d0, this.locZ - d0, this.locX + d0, this.locY + d0, this.locZ + d0));
/*  88 */       Iterator iterator = list.iterator();
/*     */       
/*  90 */       while (iterator.hasNext()) {
/*  91 */         EntityInsentient entityinsentient = (EntityInsentient)iterator.next();
/*  92 */         if ((entityinsentient.cc()) && (entityinsentient.getLeashHolder() == this))
/*     */         {
/*  94 */           if (CraftEventFactory.callPlayerUnleashEntityEvent(entityinsentient, entityhuman).isCancelled()) {
/*  95 */             die = false;
/*     */           }
/*     */           else {
/*  98 */             entityinsentient.unleash(true, !entityhuman.abilities.canInstantlyBuild);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 104 */       if (die) {
/* 105 */         die();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 110 */     return true;
/*     */   }
/*     */   
/*     */   public boolean survives() {
/* 114 */     return this.world.getType(this.blockPosition).getBlock() instanceof BlockFence;
/*     */   }
/*     */   
/*     */   public static EntityLeash a(World world, BlockPosition blockposition) {
/* 118 */     EntityLeash entityleash = new EntityLeash(world, blockposition);
/*     */     
/* 120 */     entityleash.attachedToPlayer = true;
/* 121 */     world.addEntity(entityleash);
/* 122 */     return entityleash;
/*     */   }
/*     */   
/*     */   public static EntityLeash b(World world, BlockPosition blockposition) {
/* 126 */     int i = blockposition.getX();
/* 127 */     int j = blockposition.getY();
/* 128 */     int k = blockposition.getZ();
/* 129 */     List list = world.a(EntityLeash.class, new AxisAlignedBB(i - 1.0D, j - 1.0D, k - 1.0D, i + 1.0D, j + 1.0D, k + 1.0D));
/* 130 */     Iterator iterator = list.iterator();
/*     */     
/*     */     EntityLeash entityleash;
/*     */     do
/*     */     {
/* 135 */       if (!iterator.hasNext()) {
/* 136 */         return null;
/*     */       }
/*     */       
/* 139 */       entityleash = (EntityLeash)iterator.next();
/* 140 */     } while (!entityleash.getBlockPosition().equals(blockposition));
/*     */     
/* 142 */     return entityleash;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityLeash.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */