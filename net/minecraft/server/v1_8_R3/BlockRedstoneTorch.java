/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockRedstoneTorch extends BlockTorch
/*     */ {
/*  13 */   private static Map<World, List<RedstoneUpdateInfo>> b = new java.util.WeakHashMap();
/*     */   private final boolean isOn;
/*     */   
/*     */   private boolean a(World world, BlockPosition blockposition, boolean flag) {
/*  17 */     if (!b.containsKey(world)) {
/*  18 */       b.put(world, com.google.common.collect.Lists.newArrayList());
/*     */     }
/*     */     
/*  21 */     List list = (List)b.get(world);
/*     */     
/*  23 */     if (flag) {
/*  24 */       list.add(new RedstoneUpdateInfo(blockposition, world.getTime()));
/*     */     }
/*     */     
/*  27 */     int i = 0;
/*     */     
/*  29 */     for (int j = 0; j < list.size(); j++) {
/*  30 */       RedstoneUpdateInfo blockredstonetorch_redstoneupdateinfo = (RedstoneUpdateInfo)list.get(j);
/*     */       
/*  32 */       if (blockredstonetorch_redstoneupdateinfo.a.equals(blockposition)) {
/*  33 */         i++;
/*  34 */         if (i >= 8) {
/*  35 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  40 */     return false;
/*     */   }
/*     */   
/*     */   protected BlockRedstoneTorch(boolean flag) {
/*  44 */     this.isOn = flag;
/*  45 */     a(true);
/*  46 */     a(null);
/*     */   }
/*     */   
/*     */   public int a(World world) {
/*  50 */     return 2;
/*     */   }
/*     */   
/*     */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  54 */     if (this.isOn) {
/*  55 */       EnumDirection[] aenumdirection = EnumDirection.values();
/*  56 */       int i = aenumdirection.length;
/*     */       
/*  58 */       for (int j = 0; j < i; j++) {
/*  59 */         EnumDirection enumdirection = aenumdirection[j];
/*     */         
/*  61 */         world.applyPhysics(blockposition.shift(enumdirection), this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/*  68 */     if (this.isOn) {
/*  69 */       EnumDirection[] aenumdirection = EnumDirection.values();
/*  70 */       int i = aenumdirection.length;
/*     */       
/*  72 */       for (int j = 0; j < i; j++) {
/*  73 */         EnumDirection enumdirection = aenumdirection[j];
/*     */         
/*  75 */         world.applyPhysics(blockposition.shift(enumdirection), this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int a(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection)
/*     */   {
/*  82 */     return (this.isOn) && (iblockdata.get(FACING) != enumdirection) ? 15 : 0;
/*     */   }
/*     */   
/*     */   private boolean g(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  86 */     EnumDirection enumdirection = ((EnumDirection)iblockdata.get(FACING)).opposite();
/*     */     
/*  88 */     return world.isBlockFacePowered(blockposition.shift(enumdirection), enumdirection);
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {}
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random) {
/*  94 */     boolean flag = g(world, blockposition, iblockdata);
/*  95 */     List list = (List)b.get(world);
/*     */     
/*  97 */     while ((list != null) && (!list.isEmpty()) && (world.getTime() - ((RedstoneUpdateInfo)list.get(0)).b > 60L)) {
/*  98 */       list.remove(0);
/*     */     }
/*     */     
/*     */ 
/* 102 */     PluginManager manager = world.getServer().getPluginManager();
/* 103 */     org.bukkit.block.Block block = world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/* 104 */     int oldCurrent = this.isOn ? 15 : 0;
/*     */     
/* 106 */     BlockRedstoneEvent event = new BlockRedstoneEvent(block, oldCurrent, oldCurrent);
/*     */     
/*     */ 
/* 109 */     if (this.isOn) {
/* 110 */       if (flag)
/*     */       {
/* 112 */         if (oldCurrent != 0) {
/* 113 */           event.setNewCurrent(0);
/* 114 */           manager.callEvent(event);
/* 115 */           if (event.getNewCurrent() != 0) {
/* 116 */             return;
/*     */           }
/*     */         }
/*     */         
/* 120 */         world.setTypeAndData(blockposition, Blocks.UNLIT_REDSTONE_TORCH.getBlockData().set(FACING, (EnumDirection)iblockdata.get(FACING)), 3);
/* 121 */         if (a(world, blockposition, true)) {
/* 122 */           world.makeSound(blockposition.getX() + 0.5F, blockposition.getY() + 0.5F, blockposition.getZ() + 0.5F, "random.fizz", 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F);
/*     */           
/* 124 */           for (int i = 0; i < 5; i++) {
/* 125 */             double d0 = blockposition.getX() + random.nextDouble() * 0.6D + 0.2D;
/* 126 */             double d1 = blockposition.getY() + random.nextDouble() * 0.6D + 0.2D;
/* 127 */             double d2 = blockposition.getZ() + random.nextDouble() * 0.6D + 0.2D;
/*     */             
/* 129 */             world.addParticle(EnumParticle.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           }
/*     */           
/* 132 */           world.a(blockposition, world.getType(blockposition).getBlock(), 160);
/*     */         }
/*     */       }
/* 135 */     } else if ((!flag) && (!a(world, blockposition, false)))
/*     */     {
/* 137 */       if (oldCurrent != 15) {
/* 138 */         event.setNewCurrent(15);
/* 139 */         manager.callEvent(event);
/* 140 */         if (event.getNewCurrent() != 15) {
/* 141 */           return;
/*     */         }
/*     */       }
/*     */       
/* 145 */       world.setTypeAndData(blockposition, Blocks.REDSTONE_TORCH.getBlockData().set(FACING, (EnumDirection)iblockdata.get(FACING)), 3);
/*     */     }
/*     */   }
/*     */   
/*     */   public void doPhysics(World world, BlockPosition blockposition, IBlockData iblockdata, Block block)
/*     */   {
/* 151 */     if ((!e(world, blockposition, iblockdata)) && 
/* 152 */       (this.isOn == g(world, blockposition, iblockdata))) {
/* 153 */       world.a(blockposition, this, a(world));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public int b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata, EnumDirection enumdirection)
/*     */   {
/* 160 */     return enumdirection == EnumDirection.DOWN ? a(iblockaccess, blockposition, iblockdata, enumdirection) : 0;
/*     */   }
/*     */   
/*     */   public Item getDropType(IBlockData iblockdata, Random random, int i) {
/* 164 */     return Item.getItemOf(Blocks.REDSTONE_TORCH);
/*     */   }
/*     */   
/*     */   public boolean isPowerSource() {
/* 168 */     return true;
/*     */   }
/*     */   
/*     */   public boolean b(Block block) {
/* 172 */     return (block == Blocks.UNLIT_REDSTONE_TORCH) || (block == Blocks.REDSTONE_TORCH);
/*     */   }
/*     */   
/*     */   static class RedstoneUpdateInfo
/*     */   {
/*     */     BlockPosition a;
/*     */     long b;
/*     */     
/*     */     public RedstoneUpdateInfo(BlockPosition blockposition, long i) {
/* 181 */       this.a = blockposition;
/* 182 */       this.b = i;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockRedstoneTorch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */