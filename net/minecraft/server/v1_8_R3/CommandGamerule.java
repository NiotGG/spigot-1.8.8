/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CommandGamerule
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 11 */     return "gamerule";
/*    */   }
/*    */   
/*    */   public int a() {
/* 15 */     return 2;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener icommandlistener) {
/* 19 */     return "commands.gamerule.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener icommandlistener, String[] astring) throws CommandException {
/* 23 */     GameRules gamerules = icommandlistener.getWorld().getGameRules();
/* 24 */     String s = astring.length > 0 ? astring[0] : "";
/* 25 */     String s1 = astring.length > 1 ? a(astring, 1) : "";
/*    */     
/* 27 */     switch (astring.length) {
/*    */     case 0: 
/* 29 */       icommandlistener.sendMessage(new ChatComponentText(a(gamerules.getGameRules())));
/* 30 */       break;
/*    */     
/*    */     case 1: 
/* 33 */       if (!gamerules.contains(s)) {
/* 34 */         throw new CommandException("commands.gamerule.norule", new Object[] { s });
/*    */       }
/*    */       
/* 37 */       String s2 = gamerules.get(s);
/*    */       
/* 39 */       icommandlistener.sendMessage(new ChatComponentText(s).a(" = ").a(s2));
/* 40 */       icommandlistener.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, gamerules.c(s));
/* 41 */       break;
/*    */     
/*    */     default: 
/* 44 */       if ((gamerules.a(s, GameRules.EnumGameRuleType.BOOLEAN_VALUE)) && (!"true".equals(s1)) && (!"false".equals(s1))) {
/* 45 */         throw new CommandException("commands.generic.boolean.invalid", new Object[] { s1 });
/*    */       }
/*    */       
/* 48 */       gamerules.set(s, s1);
/* 49 */       a(gamerules, s);
/* 50 */       a(icommandlistener, this, "commands.gamerule.success", new Object[0]);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void a(GameRules gamerules, String s)
/*    */   {
/* 56 */     if ("reducedDebugInfo".equals(s)) {
/* 57 */       int i = gamerules.getBoolean(s) ? 22 : 23;
/* 58 */       Iterator iterator = MinecraftServer.getServer().getPlayerList().v().iterator();
/*    */       
/* 60 */       while (iterator.hasNext()) {
/* 61 */         EntityPlayer entityplayer = (EntityPlayer)iterator.next();
/*    */         
/* 63 */         entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityStatus(entityplayer, (byte)i));
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public List<String> tabComplete(ICommandListener icommandlistener, String[] astring, BlockPosition blockposition)
/*    */   {
/* 70 */     if (astring.length == 1) {
/* 71 */       return a(astring, d().getGameRules());
/*    */     }
/* 73 */     if (astring.length == 2) {
/* 74 */       GameRules gamerules = d();
/*    */       
/* 76 */       if (gamerules.a(astring[0], GameRules.EnumGameRuleType.BOOLEAN_VALUE)) {
/* 77 */         return a(astring, new String[] { "true", "false" });
/*    */       }
/*    */     }
/*    */     
/* 81 */     return null;
/*    */   }
/*    */   
/*    */   private GameRules d()
/*    */   {
/* 86 */     return MinecraftServer.getServer().getWorldServer(0).getGameRules();
/*    */   }
/*    */   
/*    */ 
/*    */   public int compareTo(ICommand o)
/*    */   {
/* 92 */     return a(o);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandGamerule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */