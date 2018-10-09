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
/*     */ public class CommandSetBlock
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  25 */     return "setblock";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  30 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  35 */     return "commands.setblock.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  40 */     if (paramArrayOfString.length < 4) {
/*  41 */       throw new ExceptionUsage("commands.setblock.usage", new Object[0]);
/*     */     }
/*  43 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 0);
/*     */     
/*  45 */     BlockPosition localBlockPosition = a(paramICommandListener, paramArrayOfString, 0, false);
/*  46 */     Block localBlock = CommandAbstract.g(paramICommandListener, paramArrayOfString[3]);
/*     */     
/*  48 */     int i = 0;
/*  49 */     if (paramArrayOfString.length >= 5) {
/*  50 */       i = a(paramArrayOfString[4], 0, 15);
/*     */     }
/*     */     
/*  53 */     World localWorld = paramICommandListener.getWorld();
/*  54 */     if (!localWorld.isLoaded(localBlockPosition)) {
/*  55 */       throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
/*     */     }
/*     */     
/*  58 */     NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/*  59 */     int j = 0;
/*  60 */     if ((paramArrayOfString.length >= 7) && (localBlock.isTileEntity())) {
/*  61 */       localObject = a(paramICommandListener, paramArrayOfString, 6).c();
/*     */       try {
/*  63 */         localNBTTagCompound = MojangsonParser.parse((String)localObject);
/*  64 */         j = 1;
/*     */       } catch (MojangsonParseException localMojangsonParseException) {
/*  66 */         throw new CommandException("commands.setblock.tagError", new Object[] { localMojangsonParseException.getMessage() });
/*     */       }
/*     */     }
/*     */     
/*  70 */     if (paramArrayOfString.length >= 6) {
/*  71 */       if (paramArrayOfString[5].equals("destroy")) {
/*  72 */         localWorld.setAir(localBlockPosition, true);
/*  73 */         if (localBlock == Blocks.AIR) {
/*  74 */           a(paramICommandListener, this, "commands.setblock.success", new Object[0]);
/*     */         }
/*     */       }
/*  77 */       else if ((paramArrayOfString[5].equals("keep")) && 
/*  78 */         (!localWorld.isEmpty(localBlockPosition))) {
/*  79 */         throw new CommandException("commands.setblock.noChange", new Object[0]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  84 */     Object localObject = localWorld.getTileEntity(localBlockPosition);
/*  85 */     if (localObject != null) {
/*  86 */       if ((localObject instanceof IInventory)) {
/*  87 */         ((IInventory)localObject).l();
/*     */       }
/*  89 */       localWorld.setTypeAndData(localBlockPosition, Blocks.AIR.getBlockData(), localBlock == Blocks.AIR ? 2 : 4);
/*     */     }
/*     */     
/*  92 */     IBlockData localIBlockData = localBlock.fromLegacyData(i);
/*  93 */     if (!localWorld.setTypeAndData(localBlockPosition, localIBlockData, 2)) {
/*  94 */       throw new CommandException("commands.setblock.noChange", new Object[0]);
/*     */     }
/*     */     
/*  97 */     if (j != 0) {
/*  98 */       TileEntity localTileEntity = localWorld.getTileEntity(localBlockPosition);
/*  99 */       if (localTileEntity != null) {
/* 100 */         localNBTTagCompound.setInt("x", localBlockPosition.getX());
/* 101 */         localNBTTagCompound.setInt("y", localBlockPosition.getY());
/* 102 */         localNBTTagCompound.setInt("z", localBlockPosition.getZ());
/*     */         
/* 104 */         localTileEntity.a(localNBTTagCompound);
/*     */       }
/*     */     }
/* 107 */     localWorld.update(localBlockPosition, localIBlockData.getBlock());
/* 108 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_BLOCKS, 1);
/* 109 */     a(paramICommandListener, this, "commands.setblock.success", new Object[0]);
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 115 */     if ((paramArrayOfString.length > 0) && (paramArrayOfString.length <= 3))
/* 116 */       return a(paramArrayOfString, 0, paramBlockPosition);
/* 117 */     if (paramArrayOfString.length == 4)
/* 118 */       return a(paramArrayOfString, Block.REGISTRY.keySet());
/* 119 */     if (paramArrayOfString.length == 6) {
/* 120 */       return a(paramArrayOfString, new String[] { "replace", "destroy", "keep" });
/*     */     }
/*     */     
/* 123 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandSetBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */