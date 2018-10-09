/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.event.block.BlockFromToEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockFlowing extends BlockFluids
/*     */ {
/*     */   int a;
/*     */   
/*     */   protected BlockFlowing(Material material)
/*     */   {
/*  18 */     super(material);
/*     */   }
/*     */   
/*     */   private void f(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  22 */     world.setTypeAndData(blockposition, b(this.material).getBlockData().set(LEVEL, (Integer)iblockdata.get(LEVEL)), 2);
/*     */   }
/*     */   
/*     */   public void b(World world, BlockPosition blockposition, IBlockData iblockdata, Random random)
/*     */   {
/*  27 */     org.bukkit.World bworld = world.getWorld();
/*  28 */     Server server = world.getServer();
/*  29 */     org.bukkit.block.Block source = bworld == null ? null : bworld.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*     */     
/*  31 */     int i = ((Integer)iblockdata.get(LEVEL)).intValue();
/*  32 */     byte b0 = 1;
/*     */     
/*  34 */     if ((this.material == Material.LAVA) && (!world.worldProvider.n())) {
/*  35 */       b0 = 2;
/*     */     }
/*     */     
/*  38 */     int j = a(world);
/*     */     
/*     */ 
/*  41 */     if (i > 0) {
/*  42 */       int l = -100;
/*     */       
/*  44 */       this.a = 0;
/*     */       
/*     */       EnumDirection enumdirection;
/*     */       
/*  48 */       for (Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator(); iterator.hasNext(); l = a(world, blockposition.shift(enumdirection), l)) {
/*  49 */         enumdirection = (EnumDirection)iterator.next();
/*     */       }
/*     */       
/*  52 */       int i1 = l + b0;
/*     */       
/*  54 */       if ((i1 >= 8) || (l < 0)) {
/*  55 */         i1 = -1;
/*     */       }
/*     */       
/*  58 */       if (e(world, blockposition.up()) >= 0) {
/*  59 */         int k = e(world, blockposition.up());
/*  60 */         if (k >= 8) {
/*  61 */           i1 = k;
/*     */         } else {
/*  63 */           i1 = k + 8;
/*     */         }
/*     */       }
/*     */       
/*  67 */       if ((this.a >= 2) && (this.material == Material.WATER)) {
/*  68 */         IBlockData iblockdata1 = world.getType(blockposition.down());
/*     */         
/*  70 */         if (iblockdata1.getBlock().getMaterial().isBuildable()) {
/*  71 */           i1 = 0;
/*  72 */         } else if ((iblockdata1.getBlock().getMaterial() == this.material) && (((Integer)iblockdata1.get(LEVEL)).intValue() == 0)) {
/*  73 */           i1 = 0;
/*     */         }
/*     */       }
/*     */       
/*  77 */       if ((this.material == Material.LAVA) && (i < 8) && (i1 < 8) && (i1 > i) && (random.nextInt(4) != 0)) {
/*  78 */         j *= 4;
/*     */       }
/*     */       
/*  81 */       if (i1 == i) {
/*  82 */         f(world, blockposition, iblockdata);
/*     */       } else {
/*  84 */         i = i1;
/*  85 */         if (i1 < 0) {
/*  86 */           world.setAir(blockposition);
/*     */         } else {
/*  88 */           iblockdata = iblockdata.set(LEVEL, Integer.valueOf(i1));
/*  89 */           world.setTypeAndData(blockposition, iblockdata, 2);
/*  90 */           world.a(blockposition, this, j);
/*  91 */           world.applyPhysics(blockposition, this);
/*     */         }
/*     */       }
/*     */     } else {
/*  95 */       f(world, blockposition, iblockdata);
/*     */     }
/*     */     
/*  98 */     IBlockData iblockdata2 = world.getType(blockposition.down());
/*     */     
/* 100 */     if (h(world, blockposition.down(), iblockdata2))
/*     */     {
/* 102 */       BlockFromToEvent event = new BlockFromToEvent(source, BlockFace.DOWN);
/* 103 */       if (server != null) {
/* 104 */         server.getPluginManager().callEvent(event);
/*     */       }
/* 106 */       if (!event.isCancelled()) {
/* 107 */         if ((this.material == Material.LAVA) && (world.getType(blockposition.down()).getBlock().getMaterial() == Material.WATER)) {
/* 108 */           world.setTypeUpdate(blockposition.down(), Blocks.STONE.getBlockData());
/* 109 */           fizz(world, blockposition.down());
/* 110 */           return;
/*     */         }
/*     */         
/* 113 */         if (i >= 8) {
/* 114 */           flow(world, blockposition.down(), iblockdata2, i);
/*     */         } else {
/* 116 */           flow(world, blockposition.down(), iblockdata2, i + 8);
/*     */         }
/*     */       }
/*     */     }
/* 120 */     else if ((i >= 0) && ((i == 0) || (g(world, blockposition.down(), iblockdata2)))) {
/* 121 */       Set set = f(world, blockposition);
/*     */       
/* 123 */       int k = i + b0;
/* 124 */       if (i >= 8) {
/* 125 */         k = 1;
/*     */       }
/*     */       
/* 128 */       if (k >= 8) {
/* 129 */         return;
/*     */       }
/*     */       
/* 132 */       Iterator iterator1 = set.iterator();
/*     */       
/* 134 */       while (iterator1.hasNext()) {
/* 135 */         EnumDirection enumdirection1 = (EnumDirection)iterator1.next();
/*     */         
/*     */ 
/* 138 */         BlockFromToEvent event = new BlockFromToEvent(source, org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock.notchToBlockFace(enumdirection1));
/* 139 */         if (server != null) {
/* 140 */           server.getPluginManager().callEvent(event);
/*     */         }
/*     */         
/* 143 */         if (!event.isCancelled()) {
/* 144 */           flow(world, blockposition.shift(enumdirection1), world.getType(blockposition.shift(enumdirection1)), k);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void flow(World world, BlockPosition blockposition, IBlockData iblockdata, int i)
/*     */   {
/* 153 */     if ((world.isLoaded(blockposition)) && (h(world, blockposition, iblockdata))) {
/* 154 */       if (iblockdata.getBlock() != Blocks.AIR) {
/* 155 */         if (this.material == Material.LAVA) {
/* 156 */           fizz(world, blockposition);
/*     */         } else {
/* 158 */           iblockdata.getBlock().b(world, blockposition, iblockdata, 0);
/*     */         }
/*     */       }
/*     */       
/* 162 */       world.setTypeAndData(blockposition, getBlockData().set(LEVEL, Integer.valueOf(i)), 3);
/*     */     }
/*     */   }
/*     */   
/*     */   private int a(World world, BlockPosition blockposition, int i, EnumDirection enumdirection)
/*     */   {
/* 168 */     int j = 1000;
/* 169 */     Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */     
/* 171 */     while (iterator.hasNext()) {
/* 172 */       EnumDirection enumdirection1 = (EnumDirection)iterator.next();
/*     */       
/* 174 */       if (enumdirection1 != enumdirection) {
/* 175 */         BlockPosition blockposition1 = blockposition.shift(enumdirection1);
/* 176 */         IBlockData iblockdata = world.getType(blockposition1);
/*     */         
/* 178 */         if ((!g(world, blockposition1, iblockdata)) && ((iblockdata.getBlock().getMaterial() != this.material) || (((Integer)iblockdata.get(LEVEL)).intValue() > 0))) {
/* 179 */           if (!g(world, blockposition1.down(), iblockdata)) {
/* 180 */             return i;
/*     */           }
/*     */           
/* 183 */           if (i < 4) {
/* 184 */             int k = a(world, blockposition1, i + 1, enumdirection1.opposite());
/*     */             
/* 186 */             if (k < j) {
/* 187 */               j = k;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 194 */     return j;
/*     */   }
/*     */   
/*     */   private Set<EnumDirection> f(World world, BlockPosition blockposition) {
/* 198 */     int i = 1000;
/* 199 */     EnumSet enumset = EnumSet.noneOf(EnumDirection.class);
/* 200 */     Iterator iterator = EnumDirection.EnumDirectionLimit.HORIZONTAL.iterator();
/*     */     
/* 202 */     while (iterator.hasNext()) {
/* 203 */       EnumDirection enumdirection = (EnumDirection)iterator.next();
/* 204 */       BlockPosition blockposition1 = blockposition.shift(enumdirection);
/* 205 */       IBlockData iblockdata = world.getType(blockposition1);
/*     */       
/* 207 */       if ((!g(world, blockposition1, iblockdata)) && ((iblockdata.getBlock().getMaterial() != this.material) || (((Integer)iblockdata.get(LEVEL)).intValue() > 0))) {
/*     */         int j;
/*     */         int j;
/* 210 */         if (g(world, blockposition1.down(), world.getType(blockposition1.down()))) {
/* 211 */           j = a(world, blockposition1, 1, enumdirection.opposite());
/*     */         } else {
/* 213 */           j = 0;
/*     */         }
/*     */         
/* 216 */         if (j < i) {
/* 217 */           enumset.clear();
/*     */         }
/*     */         
/* 220 */         if (j <= i) {
/* 221 */           enumset.add(enumdirection);
/* 222 */           i = j;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 227 */     return enumset;
/*     */   }
/*     */   
/*     */   private boolean g(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 231 */     Block block = world.getType(blockposition).getBlock();
/*     */     
/* 233 */     return block.material == Material.PORTAL;
/*     */   }
/*     */   
/*     */   protected int a(World world, BlockPosition blockposition, int i) {
/* 237 */     int j = e(world, blockposition);
/*     */     
/* 239 */     if (j < 0) {
/* 240 */       return i;
/*     */     }
/* 242 */     if (j == 0) {
/* 243 */       this.a += 1;
/*     */     }
/*     */     
/* 246 */     if (j >= 8) {
/* 247 */       j = 0;
/*     */     }
/*     */     
/* 250 */     return (i >= 0) && (j >= i) ? i : j;
/*     */   }
/*     */   
/*     */   private boolean h(World world, BlockPosition blockposition, IBlockData iblockdata)
/*     */   {
/* 255 */     Material material = iblockdata.getBlock().getMaterial();
/*     */     
/* 257 */     return (material != this.material) && (material != Material.LAVA) && (!g(world, blockposition, iblockdata));
/*     */   }
/*     */   
/*     */   public void onPlace(World world, BlockPosition blockposition, IBlockData iblockdata) {
/* 261 */     if (!e(world, blockposition, iblockdata)) {
/* 262 */       world.a(blockposition, this, a(world));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockFlowing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */