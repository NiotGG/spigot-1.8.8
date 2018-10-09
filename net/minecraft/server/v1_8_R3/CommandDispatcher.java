/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class CommandDispatcher extends CommandHandler implements ICommandDispatcher
/*     */ {
/*     */   public CommandDispatcher() {
/*   8 */     a(new CommandTime());
/*   9 */     a(new CommandGamemode());
/*  10 */     a(new CommandDifficulty());
/*  11 */     a(new CommandGamemodeDefault());
/*  12 */     a(new CommandKill());
/*  13 */     a(new CommandToggleDownfall());
/*  14 */     a(new CommandWeather());
/*  15 */     a(new CommandXp());
/*  16 */     a(new CommandTp());
/*  17 */     a(new CommandGive());
/*  18 */     a(new CommandReplaceItem());
/*  19 */     a(new CommandStats());
/*  20 */     a(new CommandEffect());
/*  21 */     a(new CommandEnchant());
/*  22 */     a(new CommandParticle());
/*  23 */     a(new CommandMe());
/*  24 */     a(new CommandSeed());
/*  25 */     a(new CommandHelp());
/*  26 */     a(new CommandDebug());
/*  27 */     a(new CommandTell());
/*  28 */     a(new CommandSay());
/*  29 */     a(new CommandSpawnpoint());
/*  30 */     a(new CommandSetWorldSpawn());
/*  31 */     a(new CommandGamerule());
/*  32 */     a(new CommandClear());
/*  33 */     a(new CommandTestFor());
/*  34 */     a(new CommandSpreadPlayers());
/*  35 */     a(new CommandPlaySound());
/*  36 */     a(new CommandScoreboard());
/*  37 */     a(new CommandExecute());
/*  38 */     a(new CommandTrigger());
/*  39 */     a(new CommandAchievement());
/*  40 */     a(new CommandSummon());
/*  41 */     a(new CommandSetBlock());
/*  42 */     a(new CommandFill());
/*  43 */     a(new CommandClone());
/*  44 */     a(new CommandTestForBlocks());
/*  45 */     a(new CommandBlockData());
/*  46 */     a(new CommandTestForBlock());
/*  47 */     a(new CommandTellRaw());
/*  48 */     a(new CommandWorldBorder());
/*  49 */     a(new CommandTitle());
/*  50 */     a(new CommandEntityData());
/*  51 */     if (MinecraftServer.getServer().ae()) {
/*  52 */       a(new CommandOp());
/*  53 */       a(new CommandDeop());
/*  54 */       a(new CommandStop());
/*  55 */       a(new CommandSaveAll());
/*  56 */       a(new CommandSaveOff());
/*  57 */       a(new CommandSaveOn());
/*  58 */       a(new CommandBanIp());
/*  59 */       a(new CommandPardonIP());
/*  60 */       a(new CommandBan());
/*  61 */       a(new CommandBanList());
/*  62 */       a(new CommandPardon());
/*  63 */       a(new CommandKick());
/*  64 */       a(new CommandList());
/*  65 */       a(new CommandWhitelist());
/*  66 */       a(new CommandIdleTimeout());
/*     */     } else {
/*  68 */       a(new CommandPublish());
/*     */     }
/*     */     
/*  71 */     CommandAbstract.a(this);
/*     */   }
/*     */   
/*     */   public void a(ICommandListener icommandlistener, ICommand icommand, int i, String s, Object... aobject) {
/*  75 */     boolean flag = true;
/*  76 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/*  78 */     if (!icommandlistener.getSendCommandFeedback()) {
/*  79 */       flag = false;
/*     */     }
/*     */     
/*  82 */     ChatMessage chatmessage = new ChatMessage("chat.type.admin", new Object[] { icommandlistener.getName(), new ChatMessage(s, aobject) });
/*     */     
/*  84 */     chatmessage.getChatModifier().setColor(EnumChatFormat.GRAY);
/*  85 */     chatmessage.getChatModifier().setItalic(Boolean.valueOf(true));
/*  86 */     if (flag) {
/*  87 */       Iterator iterator = minecraftserver.getPlayerList().v().iterator();
/*     */       
/*  89 */       while (iterator.hasNext()) {
/*  90 */         EntityHuman entityhuman = (EntityHuman)iterator.next();
/*     */         
/*  92 */         if ((entityhuman != icommandlistener) && (minecraftserver.getPlayerList().isOp(entityhuman.getProfile())) && (icommand.canUse(icommandlistener))) {
/*  93 */           boolean flag1 = ((icommandlistener instanceof MinecraftServer)) && (MinecraftServer.getServer().r());
/*  94 */           boolean flag2 = ((icommandlistener instanceof RemoteControlCommandListener)) && (MinecraftServer.getServer().q());
/*     */           
/*  96 */           if ((flag1) || (flag2) || ((!(icommandlistener instanceof RemoteControlCommandListener)) && (!(icommandlistener instanceof MinecraftServer)))) {
/*  97 */             entityhuman.sendMessage(chatmessage);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 103 */     if ((icommandlistener != minecraftserver) && (minecraftserver.worldServer[0].getGameRules().getBoolean("logAdminCommands")) && (!org.spigotmc.SpigotConfig.silentCommandBlocks)) {
/* 104 */       minecraftserver.sendMessage(chatmessage);
/*     */     }
/*     */     
/* 107 */     boolean flag3 = minecraftserver.worldServer[0].getGameRules().getBoolean("sendCommandFeedback");
/*     */     
/* 109 */     if ((icommandlistener instanceof CommandBlockListenerAbstract)) {
/* 110 */       flag3 = ((CommandBlockListenerAbstract)icommandlistener).m();
/*     */     }
/*     */     
/* 113 */     if ((((i & 0x1) != 1) && (flag3)) || ((icommandlistener instanceof MinecraftServer))) {
/* 114 */       icommandlistener.sendMessage(new ChatMessage(s, aobject));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */