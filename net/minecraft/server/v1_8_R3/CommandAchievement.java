/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class CommandAchievement
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  24 */     return "achievement";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  29 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  34 */     return "commands.achievement.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  39 */     if (paramArrayOfString.length < 2) {
/*  40 */       throw new ExceptionUsage("commands.achievement.usage", new Object[0]);
/*     */     }
/*     */     
/*  43 */     final Statistic localStatistic = StatisticList.getStatistic(paramArrayOfString[1]);
/*  44 */     if ((localStatistic == null) && (!paramArrayOfString[1].equals("*"))) {
/*  45 */       throw new CommandException("commands.achievement.unknownAchievement", new Object[] { paramArrayOfString[1] });
/*     */     }
/*     */     
/*  48 */     final EntityPlayer localEntityPlayer = paramArrayOfString.length >= 3 ? a(paramICommandListener, paramArrayOfString[2]) : b(paramICommandListener);
/*  49 */     boolean bool1 = paramArrayOfString[0].equalsIgnoreCase("give");
/*  50 */     boolean bool2 = paramArrayOfString[0].equalsIgnoreCase("take");
/*  51 */     if ((!bool1) && (!bool2))
/*     */       return;
/*     */     Object localObject1;
/*     */     Object localObject2;
/*  55 */     if (localStatistic == null) {
/*  56 */       if (bool1) {
/*  57 */         for (localObject1 = AchievementList.e.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Achievement)((Iterator)localObject1).next();
/*  58 */           localEntityPlayer.b((Statistic)localObject2);
/*     */         }
/*  60 */         a(paramICommandListener, this, "commands.achievement.give.success.all", new Object[] { localEntityPlayer.getName() });
/*  61 */       } else if (bool2) {
/*  62 */         for (localObject1 = Lists.reverse(AchievementList.e).iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Achievement)((Iterator)localObject1).next();
/*  63 */           localEntityPlayer.a((Statistic)localObject2);
/*     */         }
/*  65 */         a(paramICommandListener, this, "commands.achievement.take.success.all", new Object[] { localEntityPlayer.getName() });
/*     */       }
/*     */       return; }
/*     */     Object localObject4;
/*     */     Achievement localAchievement1;
/*  70 */     if ((localStatistic instanceof Achievement)) {
/*  71 */       localObject1 = (Achievement)localStatistic;
/*     */       Object localObject3;
/*  73 */       if (bool1) {
/*  74 */         if (localEntityPlayer.getStatisticManager().hasAchievement((Achievement)localObject1)) {
/*  75 */           throw new CommandException("commands.achievement.alreadyHave", new Object[] { localEntityPlayer.getName(), localStatistic.j() });
/*     */         }
/*  77 */         localObject2 = Lists.newArrayList();
/*  78 */         while ((((Achievement)localObject1).c != null) && (!localEntityPlayer.getStatisticManager().hasAchievement(((Achievement)localObject1).c))) {
/*  79 */           ((List)localObject2).add(((Achievement)localObject1).c);
/*  80 */           localObject1 = ((Achievement)localObject1).c;
/*     */         }
/*     */         
/*  83 */         for (localObject3 = Lists.reverse((List)localObject2).iterator(); ((Iterator)localObject3).hasNext();) { localObject4 = (Achievement)((Iterator)localObject3).next();
/*  84 */           localEntityPlayer.b((Statistic)localObject4);
/*     */         }
/*  86 */       } else if (bool2) {
/*  87 */         if (!localEntityPlayer.getStatisticManager().hasAchievement((Achievement)localObject1)) {
/*  88 */           throw new CommandException("commands.achievement.dontHave", new Object[] { localEntityPlayer.getName(), localStatistic.j() });
/*     */         }
/*  90 */         localObject2 = Lists.newArrayList(Iterators.filter(AchievementList.e.iterator(), new Predicate()
/*     */         {
/*     */           public boolean a(Achievement paramAnonymousAchievement) {
/*  93 */             return (localEntityPlayer.getStatisticManager().hasAchievement(paramAnonymousAchievement)) && (paramAnonymousAchievement != localStatistic);
/*     */           }
/*  95 */         }));
/*  96 */         localObject3 = Lists.newArrayList((Iterable)localObject2);
/*  97 */         for (localObject4 = ((List)localObject2).iterator(); ((Iterator)localObject4).hasNext();) { localAchievement1 = (Achievement)((Iterator)localObject4).next();
/*  98 */           Achievement localAchievement2 = localAchievement1;
/*  99 */           int i = 0;
/* 100 */           while (localAchievement2 != null) {
/* 101 */             if (localAchievement2 == localStatistic) {
/* 102 */               i = 1;
/*     */             }
/* 104 */             localAchievement2 = localAchievement2.c;
/*     */           }
/* 106 */           if (i == 0)
/*     */           {
/*     */ 
/* 109 */             localAchievement2 = localAchievement1;
/* 110 */             while (localAchievement2 != null) {
/* 111 */               ((List)localObject3).remove(localAchievement1);
/* 112 */               localAchievement2 = localAchievement2.c;
/*     */             }
/*     */           } }
/* 115 */         for (localObject4 = ((List)localObject3).iterator(); ((Iterator)localObject4).hasNext();) { localAchievement1 = (Achievement)((Iterator)localObject4).next();
/* 116 */           localEntityPlayer.a(localAchievement1);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 121 */     if (bool1) {
/* 122 */       localEntityPlayer.b(localStatistic);
/* 123 */       a(paramICommandListener, this, "commands.achievement.give.success.one", new Object[] { localEntityPlayer.getName(), localStatistic.j() });
/* 124 */     } else if (bool2) {
/* 125 */       localEntityPlayer.a(localStatistic);
/* 126 */       a(paramICommandListener, this, "commands.achievement.take.success.one", new Object[] { localStatistic.j(), localEntityPlayer.getName() });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 133 */     if (paramArrayOfString.length == 1) {
/* 134 */       return a(paramArrayOfString, new String[] { "give", "take" });
/*     */     }
/*     */     
/* 137 */     if (paramArrayOfString.length == 2) {
/* 138 */       ArrayList localArrayList = Lists.newArrayList();
/* 139 */       for (Statistic localStatistic : StatisticList.stats) {
/* 140 */         localArrayList.add(localStatistic.name);
/*     */       }
/*     */       
/* 143 */       return a(paramArrayOfString, localArrayList);
/*     */     }
/*     */     
/* 146 */     if (paramArrayOfString.length == 3) {
/* 147 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*     */     }
/*     */     
/* 150 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*     */   {
/* 155 */     return paramInt == 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandAchievement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */