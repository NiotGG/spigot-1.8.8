/*     */ package org.spigotmc;
/*     */ 
/*     */ import gnu.trove.set.TByteSet;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.server.v1_8_R3.Block;
/*     */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*     */ import net.minecraft.server.v1_8_R3.Blocks;
/*     */ import net.minecraft.server.v1_8_R3.IBlockData;
/*     */ import net.minecraft.server.v1_8_R3.World;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*     */ 
/*     */ public class AntiXray
/*     */ {
/*  14 */   private static final CustomTimingsHandler update = new CustomTimingsHandler("xray - update");
/*  15 */   private static final CustomTimingsHandler obfuscate = new CustomTimingsHandler("xray - obfuscate");
/*     */   
/*     */ 
/*  18 */   private final boolean[] obfuscateBlocks = new boolean['翿'];
/*     */   
/*     */   private final byte[] replacementOres;
/*     */   
/*     */ 
/*     */   public AntiXray(SpigotWorldConfig config)
/*     */   {
/*  25 */     for (Iterator localIterator1 = (config.engineMode == 1 ? config.hiddenBlocks : config.replaceBlocks).iterator(); localIterator1.hasNext();) { int id = ((Integer)localIterator1.next()).intValue();
/*     */       
/*  27 */       this.obfuscateBlocks[id] = true;
/*     */     }
/*     */     
/*     */ 
/*  31 */     TByteSet blocks = new gnu.trove.set.hash.TByteHashSet();
/*  32 */     for (Integer i : config.hiddenBlocks)
/*     */     {
/*  34 */       Block block = Block.getById(i.intValue());
/*     */       
/*  36 */       if ((block != null) && (!block.isTileEntity()))
/*     */       {
/*     */ 
/*  39 */         blocks.add((byte)i.intValue());
/*     */       }
/*     */     }
/*     */     
/*  43 */     this.replacementOres = blocks.toArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void updateNearbyBlocks(World world, BlockPosition position)
/*     */   {
/*  52 */     if (world.spigotConfig.antiXray)
/*     */     {
/*  54 */       update.startTiming();
/*  55 */       updateNearbyBlocks(world, position, 2, false);
/*  56 */       update.stopTiming();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void obfuscateSync(int chunkX, int chunkY, int bitmask, byte[] buffer, World world)
/*     */   {
/*  66 */     if (world.spigotConfig.antiXray)
/*     */     {
/*  68 */       obfuscate.startTiming();
/*  69 */       obfuscate(chunkX, chunkY, bitmask, buffer, world);
/*  70 */       obfuscate.stopTiming();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void obfuscate(int chunkX, int chunkY, int bitmask, byte[] buffer, World world)
/*     */   {
/*  80 */     if (world.spigotConfig.antiXray)
/*     */     {
/*     */ 
/*  83 */       int initialRadius = 1;
/*     */       
/*  85 */       int index = 0;
/*     */       
/*  87 */       int randomOre = 0;
/*     */       
/*     */ 
/*  90 */       int startX = chunkX << 4;
/*  91 */       int startZ = chunkY << 4;
/*     */       byte replaceWithTypeId;
/*     */       byte replaceWithTypeId;
/*  94 */       byte replaceWithTypeId; switch (world.getWorld().getEnvironment())
/*     */       {
/*     */       case NORMAL: 
/*  97 */         replaceWithTypeId = (byte)CraftMagicNumbers.getId(Blocks.NETHERRACK);
/*  98 */         break;
/*     */       case THE_END: 
/* 100 */         replaceWithTypeId = (byte)CraftMagicNumbers.getId(Blocks.END_STONE);
/* 101 */         break;
/*     */       default: 
/* 103 */         replaceWithTypeId = (byte)CraftMagicNumbers.getId(Blocks.STONE);
/*     */       }
/*     */       
/*     */       
/*     */ 
/* 108 */       for (int i = 0; i < 16; i++)
/*     */       {
/*     */ 
/* 111 */         if ((bitmask & 1 << i) != 0)
/*     */         {
/*     */ 
/* 114 */           for (int y = 0; y < 16; y++)
/*     */           {
/* 116 */             for (int z = 0; z < 16; z++)
/*     */             {
/* 118 */               for (int x = 0; x < 16; x++)
/*     */               {
/*     */ 
/* 121 */                 if (index >= buffer.length)
/*     */                 {
/* 123 */                   index++;
/*     */ 
/*     */                 }
/*     */                 else
/*     */                 {
/* 128 */                   int blockId = buffer[(index << 1)] & 0xFF | 
/* 129 */                     (buffer[((index << 1) + 1)] & 0xFF) << 8;
/* 130 */                   blockId >>>= 4;
/*     */                   
/* 132 */                   if (this.obfuscateBlocks[blockId] != 0)
/*     */                   {
/*     */ 
/* 135 */                     if (!isLoaded(world, new BlockPosition(startX + x, (i << 4) + y, startZ + z), initialRadius))
/*     */                     {
/* 137 */                       index++;
/* 138 */                       continue;
/*     */                     }
/*     */                     
/* 141 */                     if (!hasTransparentBlockAdjacent(world, new BlockPosition(startX + x, (i << 4) + y, startZ + z), initialRadius))
/*     */                     {
/* 143 */                       int newId = blockId;
/* 144 */                       switch (world.spigotConfig.engineMode)
/*     */                       {
/*     */ 
/*     */                       case 1: 
/* 148 */                         newId = replaceWithTypeId & 0xFF;
/* 149 */                         break;
/*     */                       
/*     */                       case 2: 
/* 152 */                         if (randomOre >= this.replacementOres.length)
/*     */                         {
/* 154 */                           randomOre = 0;
/*     */                         }
/* 156 */                         newId = this.replacementOres[(randomOre++)] & 0xFF;
/*     */                       }
/*     */                       
/* 159 */                       newId = newId << 4;
/* 160 */                       buffer[(index << 1)] = ((byte)(newId & 0xFF));
/* 161 */                       buffer[((index << 1) + 1)] = ((byte)(newId >> 8 & 0xFF));
/*     */                     }
/*     */                   }
/*     */                   
/* 165 */                   index++;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateNearbyBlocks(World world, BlockPosition position, int radius, boolean updateSelf)
/*     */   {
/* 177 */     if (world.isLoaded(position))
/*     */     {
/*     */ 
/* 180 */       Block block = world.getType(position).getBlock();
/*     */       
/*     */ 
/* 183 */       if ((updateSelf) && (this.obfuscateBlocks[Block.getId(block)] != 0))
/*     */       {
/*     */ 
/* 186 */         world.notify(position);
/*     */       }
/*     */       
/*     */ 
/* 190 */       if (radius > 0)
/*     */       {
/* 192 */         updateNearbyBlocks(world, position.east(), radius - 1, true);
/* 193 */         updateNearbyBlocks(world, position.west(), radius - 1, true);
/* 194 */         updateNearbyBlocks(world, position.up(), radius - 1, true);
/* 195 */         updateNearbyBlocks(world, position.down(), radius - 1, true);
/* 196 */         updateNearbyBlocks(world, position.south(), radius - 1, true);
/* 197 */         updateNearbyBlocks(world, position.north(), radius - 1, true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean isLoaded(World world, BlockPosition position, int radius)
/*     */   {
/* 204 */     return (world.isLoaded(position)) && (
/* 205 */       (radius == 0) || (
/* 206 */       (isLoaded(world, position.east(), radius - 1)) && 
/* 207 */       (isLoaded(world, position.west(), radius - 1)) && 
/* 208 */       (isLoaded(world, position.up(), radius - 1)) && 
/* 209 */       (isLoaded(world, position.down(), radius - 1)) && 
/* 210 */       (isLoaded(world, position.south(), radius - 1)) && 
/* 211 */       (isLoaded(world, position.north(), radius - 1))));
/*     */   }
/*     */   
/*     */   private static boolean hasTransparentBlockAdjacent(World world, BlockPosition position, int radius)
/*     */   {
/* 216 */     return (!isSolidBlock(world.getType(position, false).getBlock())) || (
/* 217 */       (radius > 0) && (
/* 218 */       (hasTransparentBlockAdjacent(world, position.east(), radius - 1)) || 
/* 219 */       (hasTransparentBlockAdjacent(world, position.west(), radius - 1)) || 
/* 220 */       (hasTransparentBlockAdjacent(world, position.up(), radius - 1)) || 
/* 221 */       (hasTransparentBlockAdjacent(world, position.down(), radius - 1)) || 
/* 222 */       (hasTransparentBlockAdjacent(world, position.south(), radius - 1)) || 
/* 223 */       (hasTransparentBlockAdjacent(world, position.north(), radius - 1))));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean isSolidBlock(Block block)
/*     */   {
/* 232 */     return (block.isOccluding()) && (block != Blocks.MOB_SPAWNER) && (block != Blocks.BARRIER);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\AntiXray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */