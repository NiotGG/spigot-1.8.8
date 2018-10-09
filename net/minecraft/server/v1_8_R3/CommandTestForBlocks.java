/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
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
/*     */ public class CommandTestForBlocks
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  25 */     return "testforblocks";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  30 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  35 */     return "commands.compare.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  40 */     if (paramArrayOfString.length < 9) {
/*  41 */       throw new ExceptionUsage("commands.compare.usage", new Object[0]);
/*     */     }
/*  43 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 0);
/*     */     
/*  45 */     BlockPosition localBlockPosition1 = a(paramICommandListener, paramArrayOfString, 0, false);
/*  46 */     BlockPosition localBlockPosition2 = a(paramICommandListener, paramArrayOfString, 3, false);
/*  47 */     BlockPosition localBlockPosition3 = a(paramICommandListener, paramArrayOfString, 6, false);
/*     */     
/*  49 */     StructureBoundingBox localStructureBoundingBox1 = new StructureBoundingBox(localBlockPosition1, localBlockPosition2);
/*  50 */     StructureBoundingBox localStructureBoundingBox2 = new StructureBoundingBox(localBlockPosition3, localBlockPosition3.a(localStructureBoundingBox1.b()));
/*     */     
/*  52 */     int i = localStructureBoundingBox1.c() * localStructureBoundingBox1.d() * localStructureBoundingBox1.e();
/*  53 */     if (i > 524288) {
/*  54 */       throw new CommandException("commands.compare.tooManyBlocks", new Object[] { Integer.valueOf(i), Integer.valueOf(524288) });
/*     */     }
/*  56 */     if ((localStructureBoundingBox1.b < 0) || (localStructureBoundingBox1.e >= 256) || (localStructureBoundingBox2.b < 0) || (localStructureBoundingBox2.e >= 256)) {
/*  57 */       throw new CommandException("commands.compare.outOfWorld", new Object[0]);
/*     */     }
/*  59 */     World localWorld = paramICommandListener.getWorld();
/*  60 */     if ((!localWorld.a(localStructureBoundingBox1)) || (!localWorld.a(localStructureBoundingBox2))) {
/*  61 */       throw new CommandException("commands.compare.outOfWorld", new Object[0]);
/*     */     }
/*     */     
/*  64 */     int j = 0;
/*  65 */     if ((paramArrayOfString.length > 9) && 
/*  66 */       (paramArrayOfString[9].equals("masked"))) {
/*  67 */       j = 1;
/*     */     }
/*     */     
/*     */ 
/*  71 */     i = 0;
/*  72 */     BlockPosition localBlockPosition4 = new BlockPosition(localStructureBoundingBox2.a - localStructureBoundingBox1.a, localStructureBoundingBox2.b - localStructureBoundingBox1.b, localStructureBoundingBox2.c - localStructureBoundingBox1.c);
/*  73 */     BlockPosition.MutableBlockPosition localMutableBlockPosition1 = new BlockPosition.MutableBlockPosition();
/*  74 */     BlockPosition.MutableBlockPosition localMutableBlockPosition2 = new BlockPosition.MutableBlockPosition();
/*     */     
/*  76 */     for (int k = localStructureBoundingBox1.c; k <= localStructureBoundingBox1.f; k++) {
/*  77 */       for (int m = localStructureBoundingBox1.b; m <= localStructureBoundingBox1.e; m++) {
/*  78 */         for (int n = localStructureBoundingBox1.a; n <= localStructureBoundingBox1.d; n++) {
/*  79 */           localMutableBlockPosition1.c(n, m, k);
/*  80 */           localMutableBlockPosition2.c(n + localBlockPosition4.getX(), m + localBlockPosition4.getY(), k + localBlockPosition4.getZ());
/*     */           
/*  82 */           int i1 = 0;
/*  83 */           IBlockData localIBlockData = localWorld.getType(localMutableBlockPosition1);
/*  84 */           if ((j == 0) || (localIBlockData.getBlock() != Blocks.AIR))
/*     */           {
/*     */ 
/*  87 */             if (localIBlockData == localWorld.getType(localMutableBlockPosition2)) {
/*  88 */               TileEntity localTileEntity1 = localWorld.getTileEntity(localMutableBlockPosition1);
/*  89 */               TileEntity localTileEntity2 = localWorld.getTileEntity(localMutableBlockPosition2);
/*  90 */               if ((localTileEntity1 != null) && (localTileEntity2 != null)) {
/*  91 */                 NBTTagCompound localNBTTagCompound1 = new NBTTagCompound();
/*  92 */                 localTileEntity1.b(localNBTTagCompound1);
/*  93 */                 localNBTTagCompound1.remove("x");
/*  94 */                 localNBTTagCompound1.remove("y");
/*  95 */                 localNBTTagCompound1.remove("z");
/*     */                 
/*  97 */                 NBTTagCompound localNBTTagCompound2 = new NBTTagCompound();
/*  98 */                 localTileEntity2.b(localNBTTagCompound2);
/*  99 */                 localNBTTagCompound2.remove("x");
/* 100 */                 localNBTTagCompound2.remove("y");
/* 101 */                 localNBTTagCompound2.remove("z");
/*     */                 
/* 103 */                 if (!localNBTTagCompound1.equals(localNBTTagCompound2)) {
/* 104 */                   i1 = 1;
/*     */                 }
/* 106 */               } else if (localTileEntity1 != null) {
/* 107 */                 i1 = 1;
/*     */               }
/*     */             } else {
/* 110 */               i1 = 1;
/*     */             }
/*     */             
/* 113 */             i++;
/* 114 */             if (i1 != 0) {
/* 115 */               throw new CommandException("commands.compare.failed", new Object[0]);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 121 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, i);
/* 122 */     a(paramICommandListener, this, "commands.compare.success", new Object[] { Integer.valueOf(i) });
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 128 */     if ((paramArrayOfString.length > 0) && (paramArrayOfString.length <= 3))
/* 129 */       return a(paramArrayOfString, 0, paramBlockPosition);
/* 130 */     if ((paramArrayOfString.length > 3) && (paramArrayOfString.length <= 6))
/* 131 */       return a(paramArrayOfString, 3, paramBlockPosition);
/* 132 */     if ((paramArrayOfString.length > 6) && (paramArrayOfString.length <= 9))
/* 133 */       return a(paramArrayOfString, 6, paramBlockPosition);
/* 134 */     if (paramArrayOfString.length == 10) {
/* 135 */       return a(paramArrayOfString, new String[] { "masked", "all" });
/*     */     }
/*     */     
/* 138 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandTestForBlocks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */