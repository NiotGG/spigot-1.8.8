/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandFill
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  29 */     return "fill";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  34 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  39 */     return "commands.fill.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  44 */     if (paramArrayOfString.length < 7) {
/*  45 */       throw new ExceptionUsage("commands.fill.usage", new Object[0]);
/*     */     }
/*  47 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 0);
/*     */     
/*  49 */     BlockPosition localBlockPosition1 = a(paramICommandListener, paramArrayOfString, 0, false);
/*  50 */     BlockPosition localBlockPosition2 = a(paramICommandListener, paramArrayOfString, 3, false);
/*  51 */     Block localBlock1 = CommandAbstract.g(paramICommandListener, paramArrayOfString[6]);
/*     */     
/*  53 */     int i = 0;
/*  54 */     if (paramArrayOfString.length >= 8) {
/*  55 */       i = a(paramArrayOfString[7], 0, 15);
/*     */     }
/*     */     
/*  58 */     BlockPosition localBlockPosition3 = new BlockPosition(Math.min(localBlockPosition1.getX(), localBlockPosition2.getX()), Math.min(localBlockPosition1.getY(), localBlockPosition2.getY()), Math.min(localBlockPosition1.getZ(), localBlockPosition2.getZ()));
/*  59 */     BlockPosition localBlockPosition4 = new BlockPosition(Math.max(localBlockPosition1.getX(), localBlockPosition2.getX()), Math.max(localBlockPosition1.getY(), localBlockPosition2.getY()), Math.max(localBlockPosition1.getZ(), localBlockPosition2.getZ()));
/*     */     
/*  61 */     int j = (localBlockPosition4.getX() - localBlockPosition3.getX() + 1) * (localBlockPosition4.getY() - localBlockPosition3.getY() + 1) * (localBlockPosition4.getZ() - localBlockPosition3.getZ() + 1);
/*  62 */     if (j > 32768) {
/*  63 */       throw new CommandException("commands.fill.tooManyBlocks", new Object[] { Integer.valueOf(j), Integer.valueOf(32768) });
/*     */     }
/*     */     
/*  66 */     if ((localBlockPosition3.getY() < 0) || (localBlockPosition4.getY() >= 256)) {
/*  67 */       throw new CommandException("commands.fill.outOfWorld", new Object[0]);
/*     */     }
/*     */     
/*  70 */     World localWorld = paramICommandListener.getWorld();
/*  71 */     for (int k = localBlockPosition3.getZ(); k < localBlockPosition4.getZ() + 16; k += 16) {
/*  72 */       for (m = localBlockPosition3.getX(); m < localBlockPosition4.getX() + 16; m += 16) {
/*  73 */         if (!localWorld.isLoaded(new BlockPosition(m, localBlockPosition4.getY() - localBlockPosition3.getY(), k))) {
/*  74 */           throw new CommandException("commands.fill.outOfWorld", new Object[0]);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  79 */     NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/*  80 */     int m = 0;
/*  81 */     if ((paramArrayOfString.length >= 10) && (localBlock1.isTileEntity())) {
/*  82 */       localObject = a(paramICommandListener, paramArrayOfString, 9).c();
/*     */       try {
/*  84 */         localNBTTagCompound = MojangsonParser.parse((String)localObject);
/*  85 */         m = 1;
/*     */       } catch (MojangsonParseException localMojangsonParseException) {
/*  87 */         throw new CommandException("commands.fill.tagError", new Object[] { localMojangsonParseException.getMessage() });
/*     */       }
/*     */     }
/*     */     
/*  91 */     Object localObject = Lists.newArrayList();
/*     */     
/*  93 */     j = 0;
/*  94 */     for (int n = localBlockPosition3.getZ(); n <= localBlockPosition4.getZ(); n++) {
/*  95 */       for (int i1 = localBlockPosition3.getY(); i1 <= localBlockPosition4.getY(); i1++) {
/*  96 */         for (int i2 = localBlockPosition3.getX(); i2 <= localBlockPosition4.getX(); i2++) {
/*  97 */           BlockPosition localBlockPosition6 = new BlockPosition(i2, i1, n);
/*     */           
/*  99 */           if (paramArrayOfString.length >= 9) {
/* 100 */             if ((paramArrayOfString[8].equals("outline")) || (paramArrayOfString[8].equals("hollow"))) {
/* 101 */               if ((i2 != localBlockPosition3.getX()) && (i2 != localBlockPosition4.getX()) && (i1 != localBlockPosition3.getY()) && (i1 != localBlockPosition4.getY()) && (n != localBlockPosition3.getZ()) && (n != localBlockPosition4.getZ())) {
/* 102 */                 if (!paramArrayOfString[8].equals("hollow")) continue;
/* 103 */                 localWorld.setTypeAndData(localBlockPosition6, Blocks.AIR.getBlockData(), 2);
/* 104 */                 ((List)localObject).add(localBlockPosition6); continue;
/*     */               }
/*     */               
/*     */             }
/* 108 */             else if (paramArrayOfString[8].equals("destroy")) {
/* 109 */               localWorld.setAir(localBlockPosition6, true);
/* 110 */             } else if (paramArrayOfString[8].equals("keep")) {
/* 111 */               if (!localWorld.isEmpty(localBlockPosition6)) {
/*     */                 continue;
/*     */               }
/* 114 */             } else if ((paramArrayOfString[8].equals("replace")) && (!localBlock1.isTileEntity())) {
/* 115 */               if (paramArrayOfString.length > 9) {
/* 116 */                 Block localBlock3 = CommandAbstract.g(paramICommandListener, paramArrayOfString[9]);
/* 117 */                 if (localWorld.getType(localBlockPosition6).getBlock() != localBlock3) {}
/*     */ 
/*     */ 
/*     */               }
/* 121 */               else if (paramArrayOfString.length > 10) {
/* 122 */                 int i3 = CommandAbstract.a(paramArrayOfString[10]);
/* 123 */                 localIBlockData = localWorld.getType(localBlockPosition6);
/* 124 */                 if (localIBlockData.getBlock().toLegacyData(localIBlockData) != i3) {
/*     */                   continue;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 131 */           TileEntity localTileEntity1 = localWorld.getTileEntity(localBlockPosition6);
/* 132 */           if (localTileEntity1 != null) {
/* 133 */             if ((localTileEntity1 instanceof IInventory)) {
/* 134 */               ((IInventory)localTileEntity1).l();
/*     */             }
/* 136 */             localWorld.setTypeAndData(localBlockPosition6, Blocks.BARRIER.getBlockData(), localBlock1 == Blocks.BARRIER ? 2 : 4);
/*     */           }
/*     */           
/* 139 */           IBlockData localIBlockData = localBlock1.fromLegacyData(i);
/* 140 */           if (localWorld.setTypeAndData(localBlockPosition6, localIBlockData, 2))
/*     */           {
/*     */ 
/* 143 */             ((List)localObject).add(localBlockPosition6);
/* 144 */             j++;
/*     */             
/* 146 */             if (m != 0) {
/* 147 */               TileEntity localTileEntity2 = localWorld.getTileEntity(localBlockPosition6);
/* 148 */               if (localTileEntity2 != null) {
/* 149 */                 localNBTTagCompound.setInt("x", localBlockPosition6.getX());
/* 150 */                 localNBTTagCompound.setInt("y", localBlockPosition6.getY());
/* 151 */                 localNBTTagCompound.setInt("z", localBlockPosition6.getZ());
/*     */                 
/* 153 */                 localTileEntity2.a(localNBTTagCompound);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 160 */     for (BlockPosition localBlockPosition5 : (List)localObject) {
/* 161 */       Block localBlock2 = localWorld.getType(localBlockPosition5).getBlock();
/* 162 */       localWorld.update(localBlockPosition5, localBlock2);
/*     */     }
/*     */     
/* 165 */     if (j <= 0) {
/* 166 */       throw new CommandException("commands.fill.failed", new Object[0]);
/*     */     }
/* 168 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, j);
/* 169 */     a(paramICommandListener, this, "commands.fill.success", new Object[] { Integer.valueOf(j) });
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 175 */     if ((paramArrayOfString.length > 0) && (paramArrayOfString.length <= 3))
/* 176 */       return a(paramArrayOfString, 0, paramBlockPosition);
/* 177 */     if ((paramArrayOfString.length > 3) && (paramArrayOfString.length <= 6))
/* 178 */       return a(paramArrayOfString, 3, paramBlockPosition);
/* 179 */     if (paramArrayOfString.length == 7)
/* 180 */       return a(paramArrayOfString, Block.REGISTRY.keySet());
/* 181 */     if (paramArrayOfString.length == 9)
/* 182 */       return a(paramArrayOfString, new String[] { "replace", "destroy", "keep", "hollow", "outline" });
/* 183 */     if ((paramArrayOfString.length == 10) && ("replace".equals(paramArrayOfString[8]))) {
/* 184 */       return a(paramArrayOfString, Block.REGISTRY.keySet());
/*     */     }
/*     */     
/* 187 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandFill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */