/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
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
/*     */ public class CommandClone
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  30 */     return "clone";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  35 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  40 */     return "commands.clone.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  45 */     if (paramArrayOfString.length < 9) {
/*  46 */       throw new ExceptionUsage("commands.clone.usage", new Object[0]);
/*     */     }
/*  48 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 0);
/*     */     
/*  50 */     BlockPosition localBlockPosition1 = a(paramICommandListener, paramArrayOfString, 0, false);
/*  51 */     BlockPosition localBlockPosition2 = a(paramICommandListener, paramArrayOfString, 3, false);
/*  52 */     BlockPosition localBlockPosition3 = a(paramICommandListener, paramArrayOfString, 6, false);
/*     */     
/*  54 */     StructureBoundingBox localStructureBoundingBox1 = new StructureBoundingBox(localBlockPosition1, localBlockPosition2);
/*  55 */     StructureBoundingBox localStructureBoundingBox2 = new StructureBoundingBox(localBlockPosition3, localBlockPosition3.a(localStructureBoundingBox1.b()));
/*     */     
/*  57 */     int i = localStructureBoundingBox1.c() * localStructureBoundingBox1.d() * localStructureBoundingBox1.e();
/*  58 */     if (i > 32768) {
/*  59 */       throw new CommandException("commands.clone.tooManyBlocks", new Object[] { Integer.valueOf(i), Integer.valueOf(32768) });
/*     */     }
/*  61 */     int j = 0;
/*  62 */     Block localBlock = null;
/*  63 */     int k = -1;
/*  64 */     if (((paramArrayOfString.length < 11) || ((!paramArrayOfString[10].equals("force")) && (!paramArrayOfString[10].equals("move")))) && (localStructureBoundingBox1.a(localStructureBoundingBox2)))
/*  65 */       throw new CommandException("commands.clone.noOverlap", new Object[0]);
/*  66 */     if ((paramArrayOfString.length >= 11) && (paramArrayOfString[10].equals("move"))) {
/*  67 */       j = 1;
/*     */     }
/*     */     
/*  70 */     if ((localStructureBoundingBox1.b < 0) || (localStructureBoundingBox1.e >= 256) || (localStructureBoundingBox2.b < 0) || (localStructureBoundingBox2.e >= 256)) {
/*  71 */       throw new CommandException("commands.clone.outOfWorld", new Object[0]);
/*     */     }
/*  73 */     World localWorld = paramICommandListener.getWorld();
/*  74 */     if ((!localWorld.a(localStructureBoundingBox1)) || (!localWorld.a(localStructureBoundingBox2))) {
/*  75 */       throw new CommandException("commands.clone.outOfWorld", new Object[0]);
/*     */     }
/*     */     
/*  78 */     int m = 0;
/*  79 */     if (paramArrayOfString.length >= 10) {
/*  80 */       if (paramArrayOfString[9].equals("masked")) {
/*  81 */         m = 1;
/*  82 */       } else if (paramArrayOfString[9].equals("filtered")) {
/*  83 */         if (paramArrayOfString.length >= 12) {
/*  84 */           localBlock = g(paramICommandListener, paramArrayOfString[11]);
/*     */         } else {
/*  86 */           throw new ExceptionUsage("commands.clone.usage", new Object[0]);
/*     */         }
/*  88 */         if (paramArrayOfString.length >= 13) {
/*  89 */           k = a(paramArrayOfString[12], 0, 15);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  94 */     ArrayList localArrayList1 = Lists.newArrayList();
/*  95 */     ArrayList localArrayList2 = Lists.newArrayList();
/*  96 */     ArrayList localArrayList3 = Lists.newArrayList();
/*  97 */     LinkedList localLinkedList = Lists.newLinkedList();
/*     */     
/*  99 */     BlockPosition localBlockPosition4 = new BlockPosition(localStructureBoundingBox2.a - localStructureBoundingBox1.a, localStructureBoundingBox2.b - localStructureBoundingBox1.b, localStructureBoundingBox2.c - localStructureBoundingBox1.c);
/* 100 */     Object localObject4; Object localObject5; Object localObject6; for (int n = localStructureBoundingBox1.c; n <= localStructureBoundingBox1.f; n++) {
/* 101 */       for (int i1 = localStructureBoundingBox1.b; i1 <= localStructureBoundingBox1.e; i1++) {
/* 102 */         for (int i2 = localStructureBoundingBox1.a; i2 <= localStructureBoundingBox1.d; i2++) {
/* 103 */           localObject4 = new BlockPosition(i2, i1, n);
/* 104 */           localObject5 = ((BlockPosition)localObject4).a(localBlockPosition4);
/*     */           
/* 106 */           localObject6 = localWorld.getType((BlockPosition)localObject4);
/* 107 */           if ((m == 0) || (((IBlockData)localObject6).getBlock() != Blocks.AIR))
/*     */           {
/* 109 */             if (localBlock != null) {
/* 110 */               if (((IBlockData)localObject6).getBlock() == localBlock)
/*     */               {
/*     */ 
/* 113 */                 if ((k >= 0) && (((IBlockData)localObject6).getBlock().toLegacyData((IBlockData)localObject6) != k)) {}
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 118 */               TileEntity localTileEntity = localWorld.getTileEntity((BlockPosition)localObject4);
/* 119 */               if (localTileEntity != null) {
/* 120 */                 NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/* 121 */                 localTileEntity.b(localNBTTagCompound);
/* 122 */                 localArrayList2.add(new CommandCloneStoredTileEntity((BlockPosition)localObject5, (IBlockData)localObject6, localNBTTagCompound));
/* 123 */                 localLinkedList.addLast(localObject4);
/* 124 */               } else if ((((IBlockData)localObject6).getBlock().o()) || (((IBlockData)localObject6).getBlock().d())) {
/* 125 */                 localArrayList1.add(new CommandCloneStoredTileEntity((BlockPosition)localObject5, (IBlockData)localObject6, null));
/* 126 */                 localLinkedList.addLast(localObject4);
/*     */               } else {
/* 128 */                 localArrayList3.add(new CommandCloneStoredTileEntity((BlockPosition)localObject5, (IBlockData)localObject6, null));
/* 129 */                 localLinkedList.addFirst(localObject4);
/*     */               }
/*     */             } }
/*     */         }
/*     */       }
/*     */     }
/* 135 */     if (j != 0) {
/* 136 */       for (localObject1 = localLinkedList.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (BlockPosition)((Iterator)localObject1).next();
/* 137 */         localObject3 = localWorld.getTileEntity((BlockPosition)localObject2);
/* 138 */         if ((localObject3 instanceof IInventory)) {
/* 139 */           ((IInventory)localObject3).l();
/*     */         }
/* 141 */         localWorld.setTypeAndData((BlockPosition)localObject2, Blocks.BARRIER.getBlockData(), 2);
/*     */       }
/* 143 */       for (localObject1 = localLinkedList.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (BlockPosition)((Iterator)localObject1).next();
/* 144 */         localWorld.setTypeAndData((BlockPosition)localObject2, Blocks.AIR.getBlockData(), 3);
/*     */       }
/*     */     }
/*     */     
/* 148 */     Object localObject1 = Lists.newArrayList();
/* 149 */     ((List)localObject1).addAll(localArrayList1);
/* 150 */     ((List)localObject1).addAll(localArrayList2);
/* 151 */     ((List)localObject1).addAll(localArrayList3);
/*     */     
/* 153 */     Object localObject2 = Lists.reverse((List)localObject1);
/* 154 */     for (Object localObject3 = ((List)localObject2).iterator(); ((Iterator)localObject3).hasNext();) { localObject4 = (CommandCloneStoredTileEntity)((Iterator)localObject3).next();
/* 155 */       localObject5 = localWorld.getTileEntity(((CommandCloneStoredTileEntity)localObject4).a);
/* 156 */       if ((localObject5 instanceof IInventory)) {
/* 157 */         ((IInventory)localObject5).l();
/*     */       }
/* 159 */       localWorld.setTypeAndData(((CommandCloneStoredTileEntity)localObject4).a, Blocks.BARRIER.getBlockData(), 2);
/*     */     }
/*     */     
/* 162 */     i = 0;
/* 163 */     for (localObject3 = ((List)localObject1).iterator(); ((Iterator)localObject3).hasNext();) { localObject4 = (CommandCloneStoredTileEntity)((Iterator)localObject3).next();
/* 164 */       if (localWorld.setTypeAndData(((CommandCloneStoredTileEntity)localObject4).a, ((CommandCloneStoredTileEntity)localObject4).b, 2)) {
/* 165 */         i++;
/*     */       }
/*     */     }
/* 168 */     for (localObject3 = localArrayList2.iterator(); ((Iterator)localObject3).hasNext();) { localObject4 = (CommandCloneStoredTileEntity)((Iterator)localObject3).next();
/* 169 */       localObject5 = localWorld.getTileEntity(((CommandCloneStoredTileEntity)localObject4).a);
/* 170 */       if ((((CommandCloneStoredTileEntity)localObject4).c != null) && (localObject5 != null)) {
/* 171 */         ((CommandCloneStoredTileEntity)localObject4).c.setInt("x", ((CommandCloneStoredTileEntity)localObject4).a.getX());
/* 172 */         ((CommandCloneStoredTileEntity)localObject4).c.setInt("y", ((CommandCloneStoredTileEntity)localObject4).a.getY());
/* 173 */         ((CommandCloneStoredTileEntity)localObject4).c.setInt("z", ((CommandCloneStoredTileEntity)localObject4).a.getZ());
/* 174 */         ((TileEntity)localObject5).a(((CommandCloneStoredTileEntity)localObject4).c);
/* 175 */         ((TileEntity)localObject5).update();
/*     */       }
/* 177 */       localWorld.setTypeAndData(((CommandCloneStoredTileEntity)localObject4).a, ((CommandCloneStoredTileEntity)localObject4).b, 2);
/*     */     }
/*     */     
/* 180 */     for (localObject3 = ((List)localObject2).iterator(); ((Iterator)localObject3).hasNext();) { localObject4 = (CommandCloneStoredTileEntity)((Iterator)localObject3).next();
/* 181 */       localWorld.update(((CommandCloneStoredTileEntity)localObject4).a, ((CommandCloneStoredTileEntity)localObject4).b.getBlock());
/*     */     }
/*     */     
/* 184 */     localObject3 = localWorld.a(localStructureBoundingBox1, false);
/* 185 */     if (localObject3 != null) {
/* 186 */       for (localObject4 = ((List)localObject3).iterator(); ((Iterator)localObject4).hasNext();) { localObject5 = (NextTickListEntry)((Iterator)localObject4).next();
/* 187 */         if (localStructureBoundingBox1.b(((NextTickListEntry)localObject5).a)) {
/* 188 */           localObject6 = ((NextTickListEntry)localObject5).a.a(localBlockPosition4);
/* 189 */           localWorld.b((BlockPosition)localObject6, ((NextTickListEntry)localObject5).a(), (int)(((NextTickListEntry)localObject5).b - localWorld.getWorldData().getTime()), ((NextTickListEntry)localObject5).c);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 194 */     if (i <= 0) {
/* 195 */       throw new CommandException("commands.clone.failed", new Object[0]);
/*     */     }
/* 197 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, i);
/* 198 */     a(paramICommandListener, this, "commands.clone.success", new Object[] { Integer.valueOf(i) });
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 204 */     if ((paramArrayOfString.length > 0) && (paramArrayOfString.length <= 3))
/* 205 */       return a(paramArrayOfString, 0, paramBlockPosition);
/* 206 */     if ((paramArrayOfString.length > 3) && (paramArrayOfString.length <= 6))
/* 207 */       return a(paramArrayOfString, 3, paramBlockPosition);
/* 208 */     if ((paramArrayOfString.length > 6) && (paramArrayOfString.length <= 9))
/* 209 */       return a(paramArrayOfString, 6, paramBlockPosition);
/* 210 */     if (paramArrayOfString.length == 10)
/* 211 */       return a(paramArrayOfString, new String[] { "replace", "masked", "filtered" });
/* 212 */     if (paramArrayOfString.length == 11)
/* 213 */       return a(paramArrayOfString, new String[] { "normal", "force", "move" });
/* 214 */     if ((paramArrayOfString.length == 12) && ("filtered".equals(paramArrayOfString[9]))) {
/* 215 */       return a(paramArrayOfString, Block.REGISTRY.keySet());
/*     */     }
/* 217 */     return null;
/*     */   }
/*     */   
/*     */   static class CommandCloneStoredTileEntity {
/*     */     public final BlockPosition a;
/*     */     public final IBlockData b;
/*     */     public final NBTTagCompound c;
/*     */     
/*     */     public CommandCloneStoredTileEntity(BlockPosition paramBlockPosition, IBlockData paramIBlockData, NBTTagCompound paramNBTTagCompound) {
/* 226 */       this.a = paramBlockPosition;
/* 227 */       this.b = paramIBlockData;
/* 228 */       this.c = paramNBTTagCompound;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandClone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */