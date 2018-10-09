/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class CommandHelp
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  24 */     return "help";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  29 */     return 0;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  34 */     return "commands.help.usage";
/*     */   }
/*     */   
/*     */   public List<String> b()
/*     */   {
/*  39 */     return Arrays.asList(new String[] { "?" });
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  44 */     List localList = d(paramICommandListener);
/*  45 */     int i = 7;
/*  46 */     int j = (localList.size() - 1) / 7;
/*  47 */     int k = 0;
/*     */     try
/*     */     {
/*  50 */       k = paramArrayOfString.length == 0 ? 0 : a(paramArrayOfString[0], 1, j + 1) - 1;
/*     */     }
/*     */     catch (ExceptionInvalidNumber localExceptionInvalidNumber) {
/*  53 */       localObject = d();
/*  54 */       ICommand localICommand1 = (ICommand)((Map)localObject).get(paramArrayOfString[0]);
/*     */       
/*  56 */       if (localICommand1 != null)
/*     */       {
/*  58 */         throw new ExceptionUsage(localICommand1.getUsage(paramICommandListener), new Object[0]); }
/*  59 */       if (MathHelper.a(paramArrayOfString[0], -1) != -1) {
/*  60 */         throw localExceptionInvalidNumber;
/*     */       }
/*  62 */       throw new ExceptionUnknownCommand();
/*     */     }
/*     */     
/*     */ 
/*  66 */     int m = Math.min((k + 1) * 7, localList.size());
/*     */     
/*  68 */     Object localObject = new ChatMessage("commands.help.header", new Object[] { Integer.valueOf(k + 1), Integer.valueOf(j + 1) });
/*  69 */     ((ChatMessage)localObject).getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
/*  70 */     paramICommandListener.sendMessage((IChatBaseComponent)localObject);
/*     */     
/*  72 */     for (int n = k * 7; n < m; n++) {
/*  73 */       ICommand localICommand2 = (ICommand)localList.get(n);
/*     */       
/*  75 */       ChatMessage localChatMessage2 = new ChatMessage(localICommand2.getUsage(paramICommandListener), new Object[0]);
/*  76 */       localChatMessage2.getChatModifier().setChatClickable(new ChatClickable(ChatClickable.EnumClickAction.SUGGEST_COMMAND, "/" + localICommand2.getCommand() + " "));
/*  77 */       paramICommandListener.sendMessage(localChatMessage2);
/*     */     }
/*     */     
/*  80 */     if ((k == 0) && ((paramICommandListener instanceof EntityHuman))) {
/*  81 */       ChatMessage localChatMessage1 = new ChatMessage("commands.help.footer", new Object[0]);
/*  82 */       localChatMessage1.getChatModifier().setColor(EnumChatFormat.GREEN);
/*  83 */       paramICommandListener.sendMessage(localChatMessage1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected List<ICommand> d(ICommandListener paramICommandListener) {
/*  88 */     List localList = MinecraftServer.getServer().getCommandHandler().a(paramICommandListener);
/*  89 */     Collections.sort(localList);
/*  90 */     return localList;
/*     */   }
/*     */   
/*     */   protected Map<String, ICommand> d() {
/*  94 */     return MinecraftServer.getServer().getCommandHandler().getCommands();
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 100 */     if (paramArrayOfString.length == 1) {
/* 101 */       Set localSet = d().keySet();
/* 102 */       return a(paramArrayOfString, (String[])localSet.toArray(new String[localSet.size()]));
/*     */     }
/*     */     
/* 105 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandHelp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */