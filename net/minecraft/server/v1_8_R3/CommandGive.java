/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
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
/*     */ public class CommandGive
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  23 */     return "give";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  28 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  33 */     return "commands.give.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  38 */     if (paramArrayOfString.length < 2) {
/*  39 */       throw new ExceptionUsage("commands.give.usage", new Object[0]);
/*     */     }
/*     */     
/*  42 */     EntityPlayer localEntityPlayer = a(paramICommandListener, paramArrayOfString[0]);
/*  43 */     Item localItem = f(paramICommandListener, paramArrayOfString[1]);
/*  44 */     int i = paramArrayOfString.length >= 3 ? a(paramArrayOfString[2], 1, 64) : 1;
/*  45 */     int j = paramArrayOfString.length >= 4 ? a(paramArrayOfString[3]) : 0;
/*  46 */     ItemStack localItemStack = new ItemStack(localItem, i, j);
/*     */     
/*  48 */     if (paramArrayOfString.length >= 5) {
/*  49 */       String str = a(paramICommandListener, paramArrayOfString, 4).c();
/*     */       try {
/*  51 */         localItemStack.setTag(MojangsonParser.parse(str));
/*     */       } catch (MojangsonParseException localMojangsonParseException) {
/*  53 */         throw new CommandException("commands.give.tagError", new Object[] { localMojangsonParseException.getMessage() });
/*     */       }
/*     */     }
/*     */     
/*  57 */     boolean bool = localEntityPlayer.inventory.pickup(localItemStack);
/*  58 */     if (bool) {
/*  59 */       localEntityPlayer.world.makeSound(localEntityPlayer, "random.pop", 0.2F, ((localEntityPlayer.bc().nextFloat() - localEntityPlayer.bc().nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*  60 */       localEntityPlayer.defaultContainer.b(); }
/*     */     EntityItem localEntityItem;
/*  62 */     if ((!bool) || (localItemStack.count > 0)) {
/*  63 */       paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, i - localItemStack.count);
/*  64 */       localEntityItem = localEntityPlayer.drop(localItemStack, false);
/*  65 */       if (localEntityItem != null) {
/*  66 */         localEntityItem.q();
/*  67 */         localEntityItem.b(localEntityPlayer.getName());
/*     */       }
/*     */     } else {
/*  70 */       localItemStack.count = 1;
/*  71 */       paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, i);
/*  72 */       localEntityItem = localEntityPlayer.drop(localItemStack, false);
/*  73 */       if (localEntityItem != null) {
/*  74 */         localEntityItem.v();
/*     */       }
/*     */     }
/*     */     
/*  78 */     a(paramICommandListener, this, "commands.give.success", new Object[] { localItemStack.C(), Integer.valueOf(i), localEntityPlayer.getName() });
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/*  84 */     if (paramArrayOfString.length == 1) {
/*  85 */       return a(paramArrayOfString, d());
/*     */     }
/*  87 */     if (paramArrayOfString.length == 2) {
/*  88 */       return a(paramArrayOfString, Item.REGISTRY.keySet());
/*     */     }
/*     */     
/*  91 */     return null;
/*     */   }
/*     */   
/*     */   protected String[] d() {
/*  95 */     return MinecraftServer.getServer().getPlayers();
/*     */   }
/*     */   
/*     */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*     */   {
/* 100 */     return paramInt == 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandGive.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */