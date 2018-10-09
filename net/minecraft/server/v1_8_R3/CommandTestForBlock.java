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
/*     */ public class CommandTestForBlock
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  22 */     return "testforblock";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  27 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  32 */     return "commands.testforblock.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  37 */     if (paramArrayOfString.length < 4) {
/*  38 */       throw new ExceptionUsage("commands.testforblock.usage", new Object[0]);
/*     */     }
/*  40 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 0);
/*     */     
/*  42 */     BlockPosition localBlockPosition = a(paramICommandListener, paramArrayOfString, 0, false);
/*  43 */     Block localBlock1 = Block.getByName(paramArrayOfString[3]);
/*  44 */     if (localBlock1 == null) {
/*  45 */       throw new ExceptionInvalidNumber("commands.setblock.notFound", new Object[] { paramArrayOfString[3] });
/*     */     }
/*     */     
/*  48 */     int i = -1;
/*  49 */     if (paramArrayOfString.length >= 5) {
/*  50 */       i = a(paramArrayOfString[4], -1, 15);
/*     */     }
/*     */     
/*  53 */     World localWorld = paramICommandListener.getWorld();
/*  54 */     if (!localWorld.isLoaded(localBlockPosition)) {
/*  55 */       throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
/*     */     }
/*     */     
/*  58 */     NBTTagCompound localNBTTagCompound1 = new NBTTagCompound();
/*  59 */     int j = 0;
/*  60 */     if ((paramArrayOfString.length >= 6) && (localBlock1.isTileEntity())) {
/*  61 */       localObject = a(paramICommandListener, paramArrayOfString, 5).c();
/*     */       try {
/*  63 */         localNBTTagCompound1 = MojangsonParser.parse((String)localObject);
/*  64 */         j = 1;
/*     */       } catch (MojangsonParseException localMojangsonParseException) {
/*  66 */         throw new CommandException("commands.setblock.tagError", new Object[] { localMojangsonParseException.getMessage() });
/*     */       }
/*     */     }
/*     */     
/*  70 */     Object localObject = localWorld.getType(localBlockPosition);
/*  71 */     Block localBlock2 = ((IBlockData)localObject).getBlock();
/*  72 */     if (localBlock2 != localBlock1) {
/*  73 */       throw new CommandException("commands.testforblock.failed.tile", new Object[] { Integer.valueOf(localBlockPosition.getX()), Integer.valueOf(localBlockPosition.getY()), Integer.valueOf(localBlockPosition.getZ()), localBlock2.getName(), localBlock1.getName() });
/*     */     }
/*     */     
/*  76 */     if (i > -1) {
/*  77 */       int k = ((IBlockData)localObject).getBlock().toLegacyData((IBlockData)localObject);
/*  78 */       if (k != i) {
/*  79 */         throw new CommandException("commands.testforblock.failed.data", new Object[] { Integer.valueOf(localBlockPosition.getX()), Integer.valueOf(localBlockPosition.getY()), Integer.valueOf(localBlockPosition.getZ()), Integer.valueOf(k), Integer.valueOf(i) });
/*     */       }
/*     */     }
/*     */     
/*  83 */     if (j != 0) {
/*  84 */       TileEntity localTileEntity = localWorld.getTileEntity(localBlockPosition);
/*  85 */       if (localTileEntity == null) {
/*  86 */         throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] { Integer.valueOf(localBlockPosition.getX()), Integer.valueOf(localBlockPosition.getY()), Integer.valueOf(localBlockPosition.getZ()) });
/*     */       }
/*  88 */       NBTTagCompound localNBTTagCompound2 = new NBTTagCompound();
/*  89 */       localTileEntity.b(localNBTTagCompound2);
/*     */       
/*  91 */       if (!GameProfileSerializer.a(localNBTTagCompound1, localNBTTagCompound2, true)) {
/*  92 */         throw new CommandException("commands.testforblock.failed.nbt", new Object[] { Integer.valueOf(localBlockPosition.getX()), Integer.valueOf(localBlockPosition.getY()), Integer.valueOf(localBlockPosition.getZ()) });
/*     */       }
/*     */     }
/*  95 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 1);
/*  96 */     a(paramICommandListener, this, "commands.testforblock.success", new Object[] { Integer.valueOf(localBlockPosition.getX()), Integer.valueOf(localBlockPosition.getY()), Integer.valueOf(localBlockPosition.getZ()) });
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 102 */     if ((paramArrayOfString.length > 0) && (paramArrayOfString.length <= 3))
/* 103 */       return a(paramArrayOfString, 0, paramBlockPosition);
/* 104 */     if (paramArrayOfString.length == 4) {
/* 105 */       return a(paramArrayOfString, Block.REGISTRY.keySet());
/*     */     }
/*     */     
/* 108 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandTestForBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */