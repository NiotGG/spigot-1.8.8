/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.event.block.BlockIgniteEvent;
/*     */ 
/*     */ public class EntityLightning extends EntityWeather
/*     */ {
/*     */   private int lifeTicks;
/*     */   public long a;
/*     */   private int c;
/*  14 */   public boolean isEffect = false;
/*     */   
/*  16 */   public boolean isSilent = false;
/*     */   
/*     */   public EntityLightning(World world, double d0, double d1, double d2) {
/*  19 */     this(world, d0, d1, d2, false);
/*     */   }
/*     */   
/*     */   public EntityLightning(World world, double d0, double d1, double d2, boolean isEffect)
/*     */   {
/*  24 */     super(world);
/*     */     
/*     */ 
/*  27 */     this.isEffect = isEffect;
/*     */     
/*  29 */     setPositionRotation(d0, d1, d2, 0.0F, 0.0F);
/*  30 */     this.lifeTicks = 2;
/*  31 */     this.a = this.random.nextLong();
/*  32 */     this.c = (this.random.nextInt(3) + 1);
/*  33 */     BlockPosition blockposition = new BlockPosition(this);
/*     */     
/*     */ 
/*  36 */     if ((!isEffect) && (!world.isClientSide) && (world.getGameRules().getBoolean("doFireTick")) && ((world.getDifficulty() == EnumDifficulty.NORMAL) || (world.getDifficulty() == EnumDifficulty.HARD)) && (world.areChunksLoaded(blockposition, 10))) {
/*  37 */       if ((world.getType(blockposition).getBlock().getMaterial() == Material.AIR) && (Blocks.FIRE.canPlace(world, blockposition)))
/*     */       {
/*  39 */         if (!CraftEventFactory.callBlockIgniteEvent(world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this).isCancelled()) {
/*  40 */           world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*  45 */       for (int i = 0; i < 4; i++) {
/*  46 */         BlockPosition blockposition1 = blockposition.a(this.random.nextInt(3) - 1, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
/*     */         
/*  48 */         if ((world.getType(blockposition1).getBlock().getMaterial() == Material.AIR) && (Blocks.FIRE.canPlace(world, blockposition1)))
/*     */         {
/*  50 */           if (!CraftEventFactory.callBlockIgniteEvent(world, blockposition1.getX(), blockposition1.getY(), blockposition1.getZ(), this).isCancelled()) {
/*  51 */             world.setTypeUpdate(blockposition1, Blocks.FIRE.getBlockData());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public EntityLightning(World world, double d0, double d1, double d2, boolean isEffect, boolean isSilent)
/*     */   {
/*  62 */     this(world, d0, d1, d2, isEffect);
/*  63 */     this.isSilent = isSilent;
/*     */   }
/*     */   
/*     */   public void t_()
/*     */   {
/*  68 */     super.t_();
/*  69 */     if ((!this.isSilent) && (this.lifeTicks == 2))
/*     */     {
/*     */ 
/*  72 */       float pitch = 0.8F + this.random.nextFloat() * 0.2F;
/*  73 */       int viewDistance = ((WorldServer)this.world).getServer().getViewDistance() * 16;
/*  74 */       for (EntityPlayer player : this.world.players) {
/*  75 */         double deltaX = this.locX - player.locX;
/*  76 */         double deltaZ = this.locZ - player.locZ;
/*  77 */         double distanceSquared = deltaX * deltaX + deltaZ * deltaZ;
/*  78 */         if (distanceSquared > viewDistance * viewDistance) {
/*  79 */           double deltaLength = Math.sqrt(distanceSquared);
/*  80 */           double relativeX = player.locX + deltaX / deltaLength * viewDistance;
/*  81 */           double relativeZ = player.locZ + deltaZ / deltaLength * viewDistance;
/*  82 */           player.playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect("ambient.weather.thunder", relativeX, this.locY, relativeZ, 10000.0F, pitch));
/*     */         } else {
/*  84 */           player.playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect("ambient.weather.thunder", this.locX, this.locY, this.locZ, 10000.0F, pitch));
/*     */         }
/*     */       }
/*     */       
/*  88 */       this.world.makeSound(this.locX, this.locY, this.locZ, "random.explode", 2.0F, 0.5F + this.random.nextFloat() * 0.2F);
/*     */     }
/*     */     
/*  91 */     this.lifeTicks -= 1;
/*  92 */     if (this.lifeTicks < 0) {
/*  93 */       if (this.c == 0) {
/*  94 */         die();
/*  95 */       } else if (this.lifeTicks < -this.random.nextInt(10)) {
/*  96 */         this.c -= 1;
/*  97 */         this.lifeTicks = 1;
/*  98 */         this.a = this.random.nextLong();
/*  99 */         BlockPosition blockposition = new BlockPosition(this);
/*     */         
/*     */ 
/* 102 */         if ((!this.world.isClientSide) && (this.world.getGameRules().getBoolean("doFireTick")) && (this.world.areChunksLoaded(blockposition, 10)) && (this.world.getType(blockposition).getBlock().getMaterial() == Material.AIR) && (Blocks.FIRE.canPlace(this.world, blockposition)))
/*     */         {
/* 104 */           if ((!this.isEffect) && (!CraftEventFactory.callBlockIgniteEvent(this.world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this).isCancelled())) {
/* 105 */             this.world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 112 */     if ((this.lifeTicks >= 0) && (!this.isEffect)) {
/* 113 */       if (this.world.isClientSide) {
/* 114 */         this.world.d(2);
/*     */       } else {
/* 116 */         double d0 = 3.0D;
/* 117 */         List list = this.world.getEntities(this, new AxisAlignedBB(this.locX - d0, this.locY - d0, this.locZ - d0, this.locX + d0, this.locY + 6.0D + d0, this.locZ + d0));
/*     */         
/* 119 */         for (int i = 0; i < list.size(); i++) {
/* 120 */           Entity entity = (Entity)list.get(i);
/*     */           
/* 122 */           entity.onLightningStrike(this);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void h() {}
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound) {}
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound) {}
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityLightning.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */