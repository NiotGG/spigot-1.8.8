/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.HashMultiset;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.event.server.MapInitializeEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class ItemWorldMap extends ItemWorldMapBase
/*     */ {
/*     */   protected ItemWorldMap()
/*     */   {
/*  15 */     a(true);
/*     */   }
/*     */   
/*     */   public WorldMap getSavedMap(ItemStack itemstack, World world) {
/*  19 */     World worldMain = (World)world.getServer().getServer().worlds.get(0);
/*  20 */     String s = "map_" + itemstack.getData();
/*  21 */     WorldMap worldmap = (WorldMap)worldMain.a(WorldMap.class, s);
/*     */     
/*  23 */     if ((worldmap == null) && (!world.isClientSide)) {
/*  24 */       itemstack.setData(worldMain.b("map"));
/*  25 */       s = "map_" + itemstack.getData();
/*  26 */       worldmap = new WorldMap(s);
/*  27 */       worldmap.scale = 3;
/*  28 */       worldmap.a(world.getWorldData().c(), world.getWorldData().e(), worldmap.scale);
/*  29 */       worldmap.map = ((byte)((WorldServer)world).dimension);
/*  30 */       worldmap.c();
/*  31 */       worldMain.a(s, worldmap);
/*     */       
/*     */ 
/*  34 */       MapInitializeEvent event = new MapInitializeEvent(worldmap.mapView);
/*  35 */       Bukkit.getServer().getPluginManager().callEvent(event);
/*     */     }
/*     */     
/*     */ 
/*  39 */     return worldmap;
/*     */   }
/*     */   
/*     */   public void a(World world, Entity entity, WorldMap worldmap)
/*     */   {
/*  44 */     if ((((WorldServer)world).dimension == worldmap.map) && ((entity instanceof EntityHuman))) {
/*  45 */       int i = 1 << worldmap.scale;
/*  46 */       int j = worldmap.centerX;
/*  47 */       int k = worldmap.centerZ;
/*  48 */       int l = MathHelper.floor(entity.locX - j) / i + 64;
/*  49 */       int i1 = MathHelper.floor(entity.locZ - k) / i + 64;
/*  50 */       int j1 = 128 / i;
/*     */       
/*  52 */       if (world.worldProvider.o()) {
/*  53 */         j1 /= 2;
/*     */       }
/*     */       
/*  56 */       WorldMap.WorldMapHumanTracker worldmap_worldmaphumantracker = worldmap.a((EntityHuman)entity);
/*     */       
/*  58 */       worldmap_worldmaphumantracker.b += 1;
/*  59 */       boolean flag = false;
/*     */       
/*  61 */       for (int k1 = l - j1 + 1; k1 < l + j1; k1++) {
/*  62 */         if (((k1 & 0xF) == (worldmap_worldmaphumantracker.b & 0xF)) || (flag)) {
/*  63 */           flag = false;
/*  64 */           double d0 = 0.0D;
/*     */           
/*  66 */           for (int l1 = i1 - j1 - 1; l1 < i1 + j1; l1++) {
/*  67 */             if ((k1 >= 0) && (l1 >= -1) && (k1 < 128) && (l1 < 128)) {
/*  68 */               int i2 = k1 - l;
/*  69 */               int j2 = l1 - i1;
/*  70 */               boolean flag1 = i2 * i2 + j2 * j2 > (j1 - 2) * (j1 - 2);
/*  71 */               int k2 = (j / i + k1 - 64) * i;
/*  72 */               int l2 = (k / i + l1 - 64) * i;
/*  73 */               HashMultiset hashmultiset = HashMultiset.create();
/*  74 */               Chunk chunk = world.getChunkAtWorldCoords(new BlockPosition(k2, 0, l2));
/*     */               
/*  76 */               if (!chunk.isEmpty()) {
/*  77 */                 int i3 = k2 & 0xF;
/*  78 */                 int j3 = l2 & 0xF;
/*  79 */                 int k3 = 0;
/*  80 */                 double d1 = 0.0D;
/*     */                 
/*  82 */                 if (world.worldProvider.o()) {
/*  83 */                   int l3 = k2 + l2 * 231871;
/*     */                   
/*  85 */                   l3 = l3 * l3 * 31287121 + l3 * 11;
/*  86 */                   if ((l3 >> 20 & 0x1) == 0) {
/*  87 */                     hashmultiset.add(Blocks.DIRT.g(Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.DIRT)), 10);
/*     */                   } else {
/*  89 */                     hashmultiset.add(Blocks.STONE.g(Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.STONE)), 100);
/*     */                   }
/*     */                   
/*  92 */                   d1 = 100.0D;
/*     */                 } else {
/*  94 */                   BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*     */                   
/*  96 */                   for (int i4 = 0; i4 < i; i4++) {
/*  97 */                     for (int j4 = 0; j4 < i; j4++) {
/*  98 */                       int k4 = chunk.b(i4 + i3, j4 + j3) + 1;
/*  99 */                       IBlockData iblockdata = Blocks.AIR.getBlockData();
/*     */                       
/* 101 */                       if (k4 > 1) {
/*     */                         do {
/* 103 */                           k4--;
/* 104 */                           iblockdata = chunk.getBlockData(blockposition_mutableblockposition.c(i4 + i3, k4, j4 + j3));
/* 105 */                         } while ((iblockdata.getBlock().g(iblockdata) == MaterialMapColor.b) && (k4 > 0));
/*     */                         
/* 107 */                         if ((k4 > 0) && (iblockdata.getBlock().getMaterial().isLiquid())) {
/* 108 */                           int l4 = k4 - 1;
/*     */                           
/*     */                           Block block;
/*     */                           do
/*     */                           {
/* 113 */                             block = chunk.getTypeAbs(i4 + i3, l4--, j4 + j3);
/* 114 */                             k3++;
/* 115 */                           } while ((l4 > 0) && (block.getMaterial().isLiquid()));
/*     */                         }
/*     */                       }
/*     */                       
/* 119 */                       d1 += k4 / (i * i);
/* 120 */                       hashmultiset.add(iblockdata.getBlock().g(iblockdata));
/*     */                     }
/*     */                   }
/*     */                 }
/*     */                 
/* 125 */                 k3 /= i * i;
/* 126 */                 double d2 = (d1 - d0) * 4.0D / (i + 4) + ((k1 + l1 & 0x1) - 0.5D) * 0.4D;
/* 127 */                 byte b0 = 1;
/*     */                 
/* 129 */                 if (d2 > 0.6D) {
/* 130 */                   b0 = 2;
/*     */                 }
/*     */                 
/* 133 */                 if (d2 < -0.6D) {
/* 134 */                   b0 = 0;
/*     */                 }
/*     */                 
/* 137 */                 MaterialMapColor materialmapcolor = (MaterialMapColor)com.google.common.collect.Iterables.getFirst(com.google.common.collect.Multisets.copyHighestCountFirst(hashmultiset), MaterialMapColor.b);
/*     */                 
/* 139 */                 if (materialmapcolor == MaterialMapColor.n) {
/* 140 */                   d2 = k3 * 0.1D + (k1 + l1 & 0x1) * 0.2D;
/* 141 */                   b0 = 1;
/* 142 */                   if (d2 < 0.5D) {
/* 143 */                     b0 = 2;
/*     */                   }
/*     */                   
/* 146 */                   if (d2 > 0.9D) {
/* 147 */                     b0 = 0;
/*     */                   }
/*     */                 }
/*     */                 
/* 151 */                 d0 = d1;
/* 152 */                 if ((l1 >= 0) && (i2 * i2 + j2 * j2 < j1 * j1) && ((!flag1) || ((k1 + l1 & 0x1) != 0))) {
/* 153 */                   byte b1 = worldmap.colors[(k1 + l1 * 128)];
/* 154 */                   byte b2 = (byte)(materialmapcolor.M * 4 + b0);
/*     */                   
/* 156 */                   if (b1 != b2) {
/* 157 */                     worldmap.colors[(k1 + l1 * 128)] = b2;
/* 158 */                     worldmap.flagDirty(k1, l1);
/* 159 */                     flag = true;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
/*     */   {
/* 172 */     if (!world.isClientSide) {
/* 173 */       WorldMap worldmap = getSavedMap(itemstack, world);
/*     */       
/* 175 */       if ((entity instanceof EntityHuman)) {
/* 176 */         EntityHuman entityhuman = (EntityHuman)entity;
/*     */         
/* 178 */         worldmap.a(entityhuman, itemstack);
/*     */       }
/*     */       
/* 181 */       if (flag) {
/* 182 */         a(world, entity, worldmap);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Packet c(ItemStack itemstack, World world, EntityHuman entityhuman)
/*     */   {
/* 189 */     return getSavedMap(itemstack, world).a(itemstack, world, entityhuman);
/*     */   }
/*     */   
/*     */   public void d(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 193 */     if ((itemstack.hasTag()) && (itemstack.getTag().getBoolean("map_is_scaling"))) {
/* 194 */       WorldMap worldmap = Items.FILLED_MAP.getSavedMap(itemstack, world);
/*     */       
/* 196 */       world = (World)world.getServer().getServer().worlds.get(0);
/*     */       
/* 198 */       itemstack.setData(world.b("map"));
/* 199 */       WorldMap worldmap1 = new WorldMap("map_" + itemstack.getData());
/*     */       
/* 201 */       worldmap1.scale = ((byte)(worldmap.scale + 1));
/* 202 */       if (worldmap1.scale > 4) {
/* 203 */         worldmap1.scale = 4;
/*     */       }
/*     */       
/* 206 */       worldmap1.a(worldmap.centerX, worldmap.centerZ, worldmap1.scale);
/* 207 */       worldmap1.map = worldmap.map;
/* 208 */       worldmap1.c();
/* 209 */       world.a("map_" + itemstack.getData(), worldmap1);
/*     */       
/*     */ 
/* 212 */       MapInitializeEvent event = new MapInitializeEvent(worldmap1.mapView);
/* 213 */       Bukkit.getServer().getPluginManager().callEvent(event);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemWorldMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */