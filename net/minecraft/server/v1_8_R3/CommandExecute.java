/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.command.ProxiedNativeCommandSender;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.command.VanillaCommandWrapper;
/*     */ 
/*     */ 
/*     */ public class CommandExecute
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  15 */     return "execute";
/*     */   }
/*     */   
/*     */   public int a() {
/*  19 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener icommandlistener) {
/*  23 */     return "commands.execute.usage";
/*     */   }
/*     */   
/*     */   public void execute(final ICommandListener icommandlistener, String[] astring) throws CommandException {
/*  27 */     if (astring.length < 5) {
/*  28 */       throw new ExceptionUsage("commands.execute.usage", new Object[0]);
/*     */     }
/*  30 */     final Entity entity = a(icommandlistener, astring[0], Entity.class);
/*  31 */     final double d0 = b(entity.locX, astring[1], false);
/*  32 */     double d1 = b(entity.locY, astring[2], false);
/*  33 */     final double d2 = b(entity.locZ, astring[3], false);
/*  34 */     final BlockPosition blockposition = new BlockPosition(d0, d1, d2);
/*  35 */     byte b0 = 4;
/*     */     
/*  37 */     if (("detect".equals(astring[4])) && (astring.length > 10)) {
/*  38 */       World world = entity.getWorld();
/*  39 */       double d3 = b(d0, astring[5], false);
/*  40 */       double d4 = b(d1, astring[6], false);
/*  41 */       double d5 = b(d2, astring[7], false);
/*  42 */       Block block = g(icommandlistener, astring[8]);
/*  43 */       int i = a(astring[9], -1, 15);
/*  44 */       BlockPosition blockposition1 = new BlockPosition(d3, d4, d5);
/*  45 */       IBlockData iblockdata = world.getType(blockposition1);
/*     */       
/*  47 */       if ((iblockdata.getBlock() != block) || ((i >= 0) && (iblockdata.getBlock().toLegacyData(iblockdata) != i))) {
/*  48 */         throw new CommandException("commands.execute.failed", new Object[] { "detect", entity.getName() });
/*     */       }
/*     */       
/*  51 */       b0 = 10;
/*     */     }
/*     */     
/*  54 */     String s = a(astring, b0);
/*  55 */     ICommandListener icommandlistener1 = new ICommandListener() {
/*     */       public String getName() {
/*  57 */         return entity.getName();
/*     */       }
/*     */       
/*     */       public IChatBaseComponent getScoreboardDisplayName() {
/*  61 */         return entity.getScoreboardDisplayName();
/*     */       }
/*     */       
/*     */       public void sendMessage(IChatBaseComponent ichatbasecomponent) {
/*  65 */         icommandlistener.sendMessage(ichatbasecomponent);
/*     */       }
/*     */       
/*     */       public boolean a(int i, String s) {
/*  69 */         return icommandlistener.a(i, s);
/*     */       }
/*     */       
/*     */       public BlockPosition getChunkCoordinates() {
/*  73 */         return blockposition;
/*     */       }
/*     */       
/*     */       public Vec3D d() {
/*  77 */         return new Vec3D(d0, d2, this.val$d2);
/*     */       }
/*     */       
/*     */       public World getWorld() {
/*  81 */         return entity.world;
/*     */       }
/*     */       
/*     */       public Entity f() {
/*  85 */         return entity;
/*     */       }
/*     */       
/*     */       public boolean getSendCommandFeedback() {
/*  89 */         MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */         
/*  91 */         return (minecraftserver == null) || (minecraftserver.worldServer[0].getGameRules().getBoolean("commandBlockOutput"));
/*     */       }
/*     */       
/*     */       public void a(CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor_enumcommandresult, int i) {
/*  95 */         entity.a(commandobjectiveexecutor_enumcommandresult, i);
/*     */       }
/*  97 */     };
/*  98 */     MinecraftServer.getServer().getCommandHandler();
/*     */     
/*     */     try
/*     */     {
/* 102 */       CommandSender sender = null;
/* 103 */       if ((icommandlistener instanceof DedicatedServer)) {
/* 104 */         sender = MinecraftServer.getServer().server.getConsoleSender();
/* 105 */       } else if ((icommandlistener instanceof CommandBlockListenerAbstract)) {
/* 106 */         sender = ((CommandBlockListenerAbstract)icommandlistener).sender;
/* 107 */       } else if (VanillaCommandWrapper.lastSender != null) {
/* 108 */         sender = VanillaCommandWrapper.lastSender;
/* 109 */       } else if (icommandlistener.f() != null) {
/* 110 */         sender = icommandlistener.f().getBukkitEntity();
/*     */       } else {
/* 112 */         throw new CommandException("Unhandled executor " + icommandlistener.getClass().getSimpleName(), new Object[0]);
/*     */       }
/* 114 */       int j = CommandBlockListenerAbstract.executeCommand(icommandlistener1, new ProxiedNativeCommandSender(icommandlistener1, sender, entity.getBukkitEntity()), s);
/*     */       
/*     */ 
/* 117 */       if (j < 1) {
/* 118 */         throw new CommandException("commands.execute.allInvocationsFailed", new Object[] { s });
/*     */       }
/*     */     }
/*     */     catch (Throwable throwable) {
/* 122 */       if ((throwable instanceof CommandException)) {
/* 123 */         throw ((CommandException)throwable);
/*     */       }
/*     */       
/* 126 */       throw new CommandException("commands.execute.failed", new Object[] { s, entity.getName() });
/*     */     }
/*     */   }
/*     */   
/*     */   public List<String> tabComplete(ICommandListener icommandlistener, String[] astring, BlockPosition blockposition)
/*     */   {
/* 132 */     return (astring.length == 9) && ("detect".equals(astring[4])) ? a(astring, Block.REGISTRY.keySet()) : (astring.length > 5) && (astring.length <= 8) && ("detect".equals(astring[4])) ? a(astring, 5, blockposition) : (astring.length > 1) && (astring.length <= 4) ? a(astring, 1, blockposition) : astring.length == 1 ? a(astring, MinecraftServer.getServer().getPlayers()) : null;
/*     */   }
/*     */   
/*     */   public boolean isListStart(String[] astring, int i) {
/* 136 */     return i == 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public int compareTo(ICommand o)
/*     */   {
/* 142 */     return a(o);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandExecute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */