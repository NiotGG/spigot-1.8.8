/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
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
/*     */ public class CommandTrigger
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  23 */     return "trigger";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  28 */     return 0;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  33 */     return "commands.trigger.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  38 */     if (paramArrayOfString.length < 3) {
/*  39 */       throw new ExceptionUsage("commands.trigger.usage", new Object[0]);
/*     */     }
/*     */     
/*     */     EntityPlayer localEntityPlayer;
/*  43 */     if ((paramICommandListener instanceof EntityPlayer)) {
/*  44 */       localEntityPlayer = (EntityPlayer)paramICommandListener;
/*     */     } else {
/*  46 */       localObject = paramICommandListener.f();
/*  47 */       if ((localObject instanceof EntityPlayer)) {
/*  48 */         localEntityPlayer = (EntityPlayer)localObject;
/*     */       } else {
/*  50 */         throw new CommandException("commands.trigger.invalidPlayer", new Object[0]);
/*     */       }
/*     */     }
/*     */     
/*  54 */     Object localObject = MinecraftServer.getServer().getWorldServer(0).getScoreboard();
/*  55 */     ScoreboardObjective localScoreboardObjective = ((Scoreboard)localObject).getObjective(paramArrayOfString[0]);
/*  56 */     if ((localScoreboardObjective == null) || (localScoreboardObjective.getCriteria() != IScoreboardCriteria.c)) {
/*  57 */       throw new CommandException("commands.trigger.invalidObjective", new Object[] { paramArrayOfString[0] });
/*     */     }
/*     */     
/*  60 */     int i = a(paramArrayOfString[2]);
/*  61 */     if (!((Scoreboard)localObject).b(localEntityPlayer.getName(), localScoreboardObjective)) {
/*  62 */       throw new CommandException("commands.trigger.invalidObjective", new Object[] { paramArrayOfString[0] });
/*     */     }
/*     */     
/*  65 */     ScoreboardScore localScoreboardScore = ((Scoreboard)localObject).getPlayerScoreForObjective(localEntityPlayer.getName(), localScoreboardObjective);
/*  66 */     if (localScoreboardScore.g()) {
/*  67 */       throw new CommandException("commands.trigger.disabled", new Object[] { paramArrayOfString[0] });
/*     */     }
/*     */     
/*  70 */     if ("set".equals(paramArrayOfString[1])) {
/*  71 */       localScoreboardScore.setScore(i);
/*  72 */     } else if ("add".equals(paramArrayOfString[1])) {
/*  73 */       localScoreboardScore.addScore(i);
/*     */     } else {
/*  75 */       throw new CommandException("commands.trigger.invalidMode", new Object[] { paramArrayOfString[1] });
/*     */     }
/*     */     
/*  78 */     localScoreboardScore.a(true);
/*  79 */     if (localEntityPlayer.playerInteractManager.isCreative()) {
/*  80 */       a(paramICommandListener, this, "commands.trigger.success", new Object[] { paramArrayOfString[0], paramArrayOfString[1], paramArrayOfString[2] });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/*  87 */     if (paramArrayOfString.length == 1) {
/*  88 */       Scoreboard localScoreboard = MinecraftServer.getServer().getWorldServer(0).getScoreboard();
/*  89 */       ArrayList localArrayList = Lists.newArrayList();
/*  90 */       for (ScoreboardObjective localScoreboardObjective : localScoreboard.getObjectives()) {
/*  91 */         if (localScoreboardObjective.getCriteria() == IScoreboardCriteria.c) {
/*  92 */           localArrayList.add(localScoreboardObjective.getName());
/*     */         }
/*     */       }
/*  95 */       return a(paramArrayOfString, (String[])localArrayList.toArray(new String[localArrayList.size()]));
/*     */     }
/*  97 */     if (paramArrayOfString.length == 2) {
/*  98 */       return a(paramArrayOfString, new String[] { "add", "set" });
/*     */     }
/*     */     
/* 101 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandTrigger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */