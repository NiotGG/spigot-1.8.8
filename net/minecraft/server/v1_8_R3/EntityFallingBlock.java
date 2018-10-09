/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.EntityChangeBlockEvent;
/*     */ import org.spigotmc.AntiXray;
/*     */ 
/*     */ public class EntityFallingBlock extends Entity
/*     */ {
/*     */   private IBlockData block;
/*     */   public int ticksLived;
/*  13 */   public boolean dropItem = true;
/*     */   private boolean e;
/*     */   public boolean hurtEntities;
/*  16 */   private int fallHurtMax = 40;
/*  17 */   private float fallHurtAmount = 2.0F;
/*     */   public NBTTagCompound tileEntityData;
/*     */   
/*     */   public EntityFallingBlock(World world) {
/*  21 */     super(world);
/*     */   }
/*     */   
/*     */   public EntityFallingBlock(World world, double d0, double d1, double d2, IBlockData iblockdata) {
/*  25 */     super(world);
/*  26 */     this.block = iblockdata;
/*  27 */     this.k = true;
/*  28 */     setSize(0.98F, 0.98F);
/*  29 */     setPosition(d0, d1, d2);
/*  30 */     this.motX = 0.0D;
/*  31 */     this.motY = 0.0D;
/*  32 */     this.motZ = 0.0D;
/*  33 */     this.lastX = d0;
/*  34 */     this.lastY = d1;
/*  35 */     this.lastZ = d2;
/*     */   }
/*     */   
/*     */   protected boolean s_() {
/*  39 */     return false;
/*     */   }
/*     */   
/*     */   protected void h() {}
/*     */   
/*     */   public boolean ad() {
/*  45 */     return !this.dead;
/*     */   }
/*     */   
/*     */   public void t_() {
/*  49 */     Block block = this.block.getBlock();
/*     */     
/*  51 */     if (block.getMaterial() == Material.AIR) {
/*  52 */       die();
/*     */     } else {
/*  54 */       this.lastX = this.locX;
/*  55 */       this.lastY = this.locY;
/*  56 */       this.lastZ = this.locZ;
/*     */       
/*     */ 
/*  59 */       if (this.ticksLived++ == 0) {
/*  60 */         BlockPosition blockposition = new BlockPosition(this);
/*  61 */         if ((this.world.getType(blockposition).getBlock() == block) && (!CraftEventFactory.callEntityChangeBlockEvent(this, blockposition.getX(), blockposition.getY(), blockposition.getZ(), Blocks.AIR, 0).isCancelled())) {
/*  62 */           this.world.setAir(blockposition);
/*  63 */           this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, blockposition);
/*  64 */         } else if (!this.world.isClientSide) {
/*  65 */           die();
/*  66 */           return;
/*     */         }
/*     */       }
/*     */       
/*  70 */       this.motY -= 0.03999999910593033D;
/*  71 */       move(this.motX, this.motY, this.motZ);
/*  72 */       this.motX *= 0.9800000190734863D;
/*  73 */       this.motY *= 0.9800000190734863D;
/*  74 */       this.motZ *= 0.9800000190734863D;
/*  75 */       if (!this.world.isClientSide) {
/*  76 */         BlockPosition blockposition = new BlockPosition(this);
/*  77 */         if (this.onGround) {
/*  78 */           this.motX *= 0.699999988079071D;
/*  79 */           this.motZ *= 0.699999988079071D;
/*  80 */           this.motY *= -0.5D;
/*  81 */           if (this.world.getType(blockposition).getBlock() != Blocks.PISTON_EXTENSION) {
/*  82 */             die();
/*  83 */             if (!this.e) {
/*  84 */               if ((this.world.a(block, blockposition, true, EnumDirection.UP, null, null)) && (!BlockFalling.canFall(this.world, blockposition.down())) && (blockposition.getX() >= -30000000) && (blockposition.getZ() >= -30000000) && (blockposition.getX() < 30000000) && (blockposition.getZ() < 30000000) && (blockposition.getY() >= 0) && (blockposition.getY() < 256) && (this.world.getType(blockposition) != this.block)) {
/*  85 */                 if (CraftEventFactory.callEntityChangeBlockEvent(this, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this.block.getBlock(), this.block.getBlock().toLegacyData(this.block)).isCancelled()) {
/*  86 */                   return;
/*     */                 }
/*  88 */                 this.world.setTypeAndData(blockposition, this.block, 3);
/*  89 */                 this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, blockposition);
/*     */                 
/*  91 */                 if ((block instanceof BlockFalling)) {
/*  92 */                   ((BlockFalling)block).a_(this.world, blockposition);
/*     */                 }
/*     */                 
/*  95 */                 if ((this.tileEntityData != null) && ((block instanceof IContainer))) {
/*  96 */                   TileEntity tileentity = this.world.getTileEntity(blockposition);
/*     */                   
/*  98 */                   if (tileentity != null) {
/*  99 */                     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */                     
/* 101 */                     tileentity.b(nbttagcompound);
/* 102 */                     Iterator iterator = this.tileEntityData.c().iterator();
/*     */                     
/* 104 */                     while (iterator.hasNext()) {
/* 105 */                       String s = (String)iterator.next();
/* 106 */                       NBTBase nbtbase = this.tileEntityData.get(s);
/*     */                       
/* 108 */                       if ((!s.equals("x")) && (!s.equals("y")) && (!s.equals("z"))) {
/* 109 */                         nbttagcompound.set(s, nbtbase.clone());
/*     */                       }
/*     */                     }
/*     */                     
/* 113 */                     tileentity.a(nbttagcompound);
/* 114 */                     tileentity.update();
/*     */                   }
/*     */                 }
/* 117 */               } else if ((this.dropItem) && (this.world.getGameRules().getBoolean("doEntityDrops"))) {
/* 118 */                 a(new ItemStack(block, 1, block.getDropData(this.block)), 0.0F);
/*     */               }
/*     */             }
/*     */           }
/* 122 */         } else if (((this.ticksLived > 100) && (!this.world.isClientSide) && ((blockposition.getY() < 1) || (blockposition.getY() > 256))) || (this.ticksLived > 600)) {
/* 123 */           if ((this.dropItem) && (this.world.getGameRules().getBoolean("doEntityDrops"))) {
/* 124 */             a(new ItemStack(block, 1, block.getDropData(this.block)), 0.0F);
/*     */           }
/*     */           
/* 127 */           die();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void e(float f, float f1)
/*     */   {
/* 135 */     Block block = this.block.getBlock();
/*     */     
/* 137 */     if (this.hurtEntities) {
/* 138 */       int i = MathHelper.f(f - 1.0F);
/*     */       
/* 140 */       if (i > 0) {
/* 141 */         ArrayList arraylist = com.google.common.collect.Lists.newArrayList(this.world.getEntities(this, getBoundingBox()));
/* 142 */         boolean flag = block == Blocks.ANVIL;
/* 143 */         DamageSource damagesource = flag ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;
/* 144 */         Iterator iterator = arraylist.iterator();
/*     */         
/* 146 */         while (iterator.hasNext()) {
/* 147 */           Entity entity = (Entity)iterator.next();
/*     */           
/* 149 */           CraftEventFactory.entityDamage = this;
/* 150 */           entity.damageEntity(damagesource, Math.min(MathHelper.d(i * this.fallHurtAmount), this.fallHurtMax));
/* 151 */           CraftEventFactory.entityDamage = null;
/*     */         }
/*     */         
/* 154 */         if ((flag) && (this.random.nextFloat() < 0.05000000074505806D + i * 0.05D)) {
/* 155 */           int j = ((Integer)this.block.get(BlockAnvil.DAMAGE)).intValue();
/*     */           
/* 157 */           j++;
/* 158 */           if (j > 2) {
/* 159 */             this.e = true;
/*     */           } else {
/* 161 */             this.block = this.block.set(BlockAnvil.DAMAGE, Integer.valueOf(j));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 170 */     Block block = this.block != null ? this.block.getBlock() : Blocks.AIR;
/* 171 */     MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(block);
/*     */     
/* 173 */     nbttagcompound.setString("Block", minecraftkey == null ? "" : minecraftkey.toString());
/* 174 */     nbttagcompound.setByte("Data", (byte)block.toLegacyData(this.block));
/* 175 */     nbttagcompound.setByte("Time", (byte)this.ticksLived);
/* 176 */     nbttagcompound.setBoolean("DropItem", this.dropItem);
/* 177 */     nbttagcompound.setBoolean("HurtEntities", this.hurtEntities);
/* 178 */     nbttagcompound.setFloat("FallHurtAmount", this.fallHurtAmount);
/* 179 */     nbttagcompound.setInt("FallHurtMax", this.fallHurtMax);
/* 180 */     if (this.tileEntityData != null) {
/* 181 */       nbttagcompound.set("TileEntityData", this.tileEntityData);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound)
/*     */   {
/* 187 */     int i = nbttagcompound.getByte("Data") & 0xFF;
/*     */     
/* 189 */     if (nbttagcompound.hasKeyOfType("Block", 8)) {
/* 190 */       this.block = Block.getByName(nbttagcompound.getString("Block")).fromLegacyData(i);
/* 191 */     } else if (nbttagcompound.hasKeyOfType("TileID", 99)) {
/* 192 */       this.block = Block.getById(nbttagcompound.getInt("TileID")).fromLegacyData(i);
/*     */     } else {
/* 194 */       this.block = Block.getById(nbttagcompound.getByte("Tile") & 0xFF).fromLegacyData(i);
/*     */     }
/*     */     
/* 197 */     this.ticksLived = (nbttagcompound.getByte("Time") & 0xFF);
/* 198 */     Block block = this.block.getBlock();
/*     */     
/* 200 */     if (nbttagcompound.hasKeyOfType("HurtEntities", 99)) {
/* 201 */       this.hurtEntities = nbttagcompound.getBoolean("HurtEntities");
/* 202 */       this.fallHurtAmount = nbttagcompound.getFloat("FallHurtAmount");
/* 203 */       this.fallHurtMax = nbttagcompound.getInt("FallHurtMax");
/* 204 */     } else if (block == Blocks.ANVIL) {
/* 205 */       this.hurtEntities = true;
/*     */     }
/*     */     
/* 208 */     if (nbttagcompound.hasKeyOfType("DropItem", 99)) {
/* 209 */       this.dropItem = nbttagcompound.getBoolean("DropItem");
/*     */     }
/*     */     
/* 212 */     if (nbttagcompound.hasKeyOfType("TileEntityData", 10)) {
/* 213 */       this.tileEntityData = nbttagcompound.getCompound("TileEntityData");
/*     */     }
/*     */     
/* 216 */     if ((block == null) || (block.getMaterial() == Material.AIR)) {
/* 217 */       this.block = Blocks.SAND.getBlockData();
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(boolean flag)
/*     */   {
/* 223 */     this.hurtEntities = flag;
/*     */   }
/*     */   
/*     */   public void appendEntityCrashDetails(CrashReportSystemDetails crashreportsystemdetails) {
/* 227 */     super.appendEntityCrashDetails(crashreportsystemdetails);
/* 228 */     if (this.block != null) {
/* 229 */       Block block = this.block.getBlock();
/*     */       
/* 231 */       crashreportsystemdetails.a("Immitating block ID", Integer.valueOf(Block.getId(block)));
/* 232 */       crashreportsystemdetails.a("Immitating block data", Integer.valueOf(block.toLegacyData(this.block)));
/*     */     }
/*     */   }
/*     */   
/*     */   public IBlockData getBlock()
/*     */   {
/* 238 */     return this.block;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityFallingBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */