/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockTripwire extends Block
/*     */ {
/*  11 */   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
/*  12 */   public static final BlockStateBoolean SUSPENDED = BlockStateBoolean.of("suspended");
/*  13 */   public static final BlockStateBoolean ATTACHED = BlockStateBoolean.of("attached");
/*  14 */   public static final BlockStateBoolean DISARMED = BlockStateBoolean.of("disarmed");
/*  15 */   public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
/*  16 */   public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
/*  17 */   public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
/*  18 */   public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
/*     */   
/*     */   public BlockTripwire() {
/*  21 */     super(Material.ORIENTABLE);
/*  22 */     j(this.blockStateList.getBlockData().set(POWERED, Boolean.valueOf(false)).set(SUSPENDED, Boolean.valueOf(false)).set(ATTACHED, Boolean.valueOf(false)).set(DISARMED, Boolean.valueOf(false)).set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false)));
/*  23 */     a(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
/*  24 */     a(true);
/*     */   }
/*     */   
/*     */   public IBlockData updateState(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
/*  28 */     return iblockdata.set(NORTH, Boolean.valueOf(c(iblockaccess, blockposition, iblockdata, EnumDirection.NORTH))).set(EAST, Boolean.valueOf(c(iblockaccess, blockposition, iblockdata, EnumDirection.EAST))).set(SOUTH, Boolean.valueOf(c(iblockaccess, blockposition, iblockdata, EnumDirection.SOUTH))).set(WEST, Boolean.valueOf(c(iblockaccess, blockposition, iblockdata, EnumDirection.WEST)));
/*     */   }
/*     */   
/*     */   public AxisAlignedBB a(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  32 */     return null;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  36 */     return false;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  40 */     return false;
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i) {
/*  44 */     return Items.STRING;
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block) {
/*  48 */     boolean flag = ((Boolean)iblockdata.get(SUSPENDED)).booleanValue();
/*  49 */     boolean flag1 = !World.a(world, blockposition.down());
/*     */     
/*  51 */     if (flag != flag1) {
/*  52 */       b(world, blockposition, iblockdata, 0);
/*  53 */       world.setAir(blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition)
/*     */   {
/*  59 */     IBlockData iblockdata = iblockaccess.getType(blockposition);
/*  60 */     boolean flag = ((Boolean)iblockdata.get(ATTACHED)).booleanValue();
/*  61 */     boolean flag1 = ((Boolean)iblockdata.get(SUSPENDED)).booleanValue();
/*     */     
/*  63 */     if (!flag1) {
/*  64 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
/*  65 */     } else if (!flag) {
/*  66 */       a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
/*     */     } else {
/*  68 */       a(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  74 */     iblockdata = iblockdata.set(SUSPENDED, Boolean.valueOf(!World.a(world, blockposition.down())));
/*  75 */     world.setTypeAndData(blockposition, iblockdata, 3);
/*  76 */     e(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  80 */     e(world, blockposition, iblockdata.set(POWERED, Boolean.valueOf(true)));
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
/*  84 */     if ((!world.isClientSide) && 
/*  85 */       (entityhuman.bZ() != null) && (entityhuman.bZ().getItem() == Items.SHEARS)) {
/*  86 */       world.setTypeAndData(blockposition, iblockdata.set(DISARMED, Boolean.valueOf(true)), 4);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void e(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  93 */     EnumDirection[] aenumdirection = { EnumDirection.SOUTH, EnumDirection.WEST };
/*  94 */     int i = aenumdirection.length;
/*  95 */     int j = 0;
/*     */     
/*  97 */     while (j < i) {
/*  98 */       EnumDirection enumdirection = aenumdirection[j];
/*  99 */       int k = 1;
/*     */       
/*     */ 
/* 102 */       while (k < 42) {
/* 103 */         BlockPosition blockposition1 = blockposition.shift(enumdirection, k);
/* 104 */         IBlockData iblockdata1 = world.getType(blockposition1);
/*     */         
/* 106 */         if (iblockdata1.getBlock() == Blocks.TRIPWIRE_HOOK) {
/* 107 */           if (iblockdata1.get(BlockTripwireHook.FACING) != enumdirection.opposite()) break;
/* 108 */           Blocks.TRIPWIRE_HOOK.a(world, blockposition1, iblockdata1, false, true, k, iblockdata);
/*     */           
/* 110 */           break; } if (iblockdata1.getBlock() != Blocks.TRIPWIRE) break;
/* 111 */         k++;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 116 */       j++;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity)
/*     */   {
/* 124 */     if ((!world.isClientSide) && 
/* 125 */       (!((Boolean)iblockdata.get(POWERED)).booleanValue())) {
/* 126 */       e(world, blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {}
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random)
/*     */   {
/* 134 */     if ((!world.isClientSide) && 
/* 135 */       (((Boolean)world.getType(blockposition).get(POWERED)).booleanValue())) {
/* 136 */       e(world, blockposition);
/*     */     }
/*     */   }
/*     */   
/*     */   private void e(World world, BlockPosition blockposition)
/*     */   {
/* 142 */     IBlockData iblockdata = world.getType(blockposition);
/* 143 */     boolean flag = ((Boolean)iblockdata.get(POWERED)).booleanValue();
/* 144 */     boolean flag1 = false;
/* 145 */     List list = world.getEntities(null, new AxisAlignedBB(blockposition.getX() + this.minX, blockposition.getY() + this.minY, blockposition.getZ() + this.minZ, blockposition.getX() + this.maxX, blockposition.getY() + this.maxY, blockposition.getZ() + this.maxZ));
/*     */     
/* 147 */     if (!list.isEmpty()) {
/* 148 */       Iterator iterator = list.iterator();
/*     */       
/* 150 */       while (iterator.hasNext()) {
/* 151 */         Entity entity = (Entity)iterator.next();
/*     */         
/* 153 */         if (!entity.aI()) {
/* 154 */           flag1 = true;
/* 155 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 161 */     if ((flag != flag1) && (flag1) && (((Boolean)iblockdata.get(ATTACHED)).booleanValue())) {
/* 162 */       org.bukkit.World bworld = world.getWorld();
/* 163 */       PluginManager manager = world.getServer().getPluginManager();
/* 164 */       org.bukkit.block.Block block = bworld.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/* 165 */       boolean allowed = false;
/*     */       
/*     */ 
/* 168 */       for (Object object : list) {
/* 169 */         if (object != null) {
/*     */           Cancellable cancellable;
/*     */           Cancellable cancellable;
/* 172 */           if ((object instanceof EntityHuman)) {
/* 173 */             cancellable = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callPlayerInteractEvent((EntityHuman)object, org.bukkit.event.block.Action.PHYSICAL, blockposition, null, null);
/* 174 */           } else { if (!(object instanceof Entity)) continue;
/* 175 */             cancellable = new org.bukkit.event.entity.EntityInteractEvent(((Entity)object).getBukkitEntity(), block);
/* 176 */             manager.callEvent((org.bukkit.event.entity.EntityInteractEvent)cancellable);
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 181 */           if (!cancellable.isCancelled()) {
/* 182 */             allowed = true;
/* 183 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 188 */       if (!allowed) {
/* 189 */         return;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 194 */     if (flag1 != flag) {
/* 195 */       iblockdata = iblockdata.set(POWERED, Boolean.valueOf(flag1));
/* 196 */       world.setTypeAndData(blockposition, iblockdata, 3);
/* 197 */       e(world, blockposition, iblockdata);
/*     */     }
/*     */     
/* 200 */     if (flag1) {
/* 201 */       world.a(blockposition, this, a(world));
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean c(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection)
/*     */   {
/* 207 */     BlockPosition blockposition1 = blockposition.shift(enumdirection);
/* 208 */     IBlockData iblockdata1 = iblockaccess.getType(blockposition1);
/* 209 */     Block block = iblockdata1.getBlock();
/*     */     
/* 211 */     if (block == Blocks.TRIPWIRE_HOOK) {
/* 212 */       EnumDirection enumdirection1 = enumdirection.opposite();
/*     */       
/* 214 */       return iblockdata1.get(BlockTripwireHook.FACING) == enumdirection1; }
/* 215 */     if (block == Blocks.TRIPWIRE) {
/* 216 */       boolean flag = ((Boolean)iblockdata.get(SUSPENDED)).booleanValue();
/* 217 */       boolean flag1 = ((Boolean)iblockdata1.get(SUSPENDED)).booleanValue();
/*     */       
/* 219 */       return flag == flag1;
/*     */     }
/* 221 */     return false;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i)
/*     */   {
/* 226 */     return getBlockData().set(POWERED, Boolean.valueOf((i & 0x1) > 0)).set(SUSPENDED, Boolean.valueOf((i & 0x2) > 0)).set(ATTACHED, Boolean.valueOf((i & 0x4) > 0)).set(DISARMED, Boolean.valueOf((i & 0x8) > 0));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 230 */     int i = 0;
/*     */     
/* 232 */     if (((Boolean)iblockdata.get(POWERED)).booleanValue()) {
/* 233 */       i |= 0x1;
/*     */     }
/*     */     
/* 236 */     if (((Boolean)iblockdata.get(SUSPENDED)).booleanValue()) {
/* 237 */       i |= 0x2;
/*     */     }
/*     */     
/* 240 */     if (((Boolean)iblockdata.get(ATTACHED)).booleanValue()) {
/* 241 */       i |= 0x4;
/*     */     }
/*     */     
/* 244 */     if (((Boolean)iblockdata.get(DISARMED)).booleanValue()) {
/* 245 */       i |= 0x8;
/*     */     }
/*     */     
/* 248 */     return i;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 252 */     return new BlockStateList(this, new IBlockState[] { POWERED, SUSPENDED, ATTACHED, DISARMED, NORTH, EAST, WEST, SOUTH });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockTripwire.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */