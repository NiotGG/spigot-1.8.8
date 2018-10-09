/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ public class CommandStats
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  25 */     return "stats";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  30 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  35 */     return "commands.stats.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  40 */     if (paramArrayOfString.length < 1) {
/*  41 */       throw new ExceptionUsage("commands.stats.usage", new Object[0]);
/*     */     }
/*     */     
/*     */     int i;
/*  45 */     if (paramArrayOfString[0].equals("entity")) {
/*  46 */       i = 0;
/*  47 */     } else if (paramArrayOfString[0].equals("block")) {
/*  48 */       i = 1;
/*     */     } else {
/*  50 */       throw new ExceptionUsage("commands.stats.usage", new Object[0]);
/*     */     }
/*     */     
/*     */     int j;
/*  54 */     if (i != 0) {
/*  55 */       if (paramArrayOfString.length < 5) {
/*  56 */         throw new ExceptionUsage("commands.stats.block.usage", new Object[0]);
/*     */       }
/*  58 */       j = 4;
/*     */     } else {
/*  60 */       if (paramArrayOfString.length < 3) {
/*  61 */         throw new ExceptionUsage("commands.stats.entity.usage", new Object[0]);
/*     */       }
/*  63 */       j = 2;
/*     */     }
/*     */     
/*  66 */     String str = paramArrayOfString[(j++)];
/*  67 */     if ("set".equals(str)) {
/*  68 */       if (paramArrayOfString.length < j + 3) {
/*  69 */         if (j == 5) {
/*  70 */           throw new ExceptionUsage("commands.stats.block.set.usage", new Object[0]);
/*     */         }
/*  72 */         throw new ExceptionUsage("commands.stats.entity.set.usage", new Object[0]);
/*     */       }
/*  74 */     } else if ("clear".equals(str)) {
/*  75 */       if (paramArrayOfString.length < j + 1) {
/*  76 */         if (j == 5) {
/*  77 */           throw new ExceptionUsage("commands.stats.block.clear.usage", new Object[0]);
/*     */         }
/*  79 */         throw new ExceptionUsage("commands.stats.entity.clear.usage", new Object[0]);
/*     */       }
/*     */     } else {
/*  82 */       throw new ExceptionUsage("commands.stats.usage", new Object[0]);
/*     */     }
/*     */     
/*  85 */     CommandObjectiveExecutor.EnumCommandResult localEnumCommandResult = CommandObjectiveExecutor.EnumCommandResult.a(paramArrayOfString[(j++)]);
/*     */     
/*  87 */     if (localEnumCommandResult == null) {
/*  88 */       throw new CommandException("commands.stats.failed", new Object[0]);
/*     */     }
/*     */     
/*  91 */     World localWorld = paramICommandListener.getWorld();
/*     */     Object localObject1;
/*  93 */     Object localObject2; CommandObjectiveExecutor localCommandObjectiveExecutor; if (i != 0) {
/*  94 */       localObject1 = a(paramICommandListener, paramArrayOfString, 1, false);
/*  95 */       localObject2 = localWorld.getTileEntity((BlockPosition)localObject1);
/*     */       
/*  97 */       if (localObject2 == null) {
/*  98 */         throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { Integer.valueOf(((BlockPosition)localObject1).getX()), Integer.valueOf(((BlockPosition)localObject1).getY()), Integer.valueOf(((BlockPosition)localObject1).getZ()) });
/*     */       }
/*     */       
/* 101 */       if ((localObject2 instanceof TileEntityCommand)) {
/* 102 */         localCommandObjectiveExecutor = ((TileEntityCommand)localObject2).c();
/* 103 */       } else if ((localObject2 instanceof TileEntitySign)) {
/* 104 */         localCommandObjectiveExecutor = ((TileEntitySign)localObject2).d();
/*     */       } else {
/* 106 */         throw new CommandException("commands.stats.noCompatibleBlock", new Object[] { Integer.valueOf(((BlockPosition)localObject1).getX()), Integer.valueOf(((BlockPosition)localObject1).getY()), Integer.valueOf(((BlockPosition)localObject1).getZ()) });
/*     */       }
/*     */     } else {
/* 109 */       localObject1 = b(paramICommandListener, paramArrayOfString[1]);
/* 110 */       localCommandObjectiveExecutor = ((Entity)localObject1).aU();
/*     */     }
/*     */     
/* 113 */     if ("set".equals(str)) {
/* 114 */       localObject1 = paramArrayOfString[(j++)];
/* 115 */       localObject2 = paramArrayOfString[j];
/* 116 */       if ((((String)localObject1).length() == 0) || (((String)localObject2).length() == 0)) {
/* 117 */         throw new CommandException("commands.stats.failed", new Object[0]);
/*     */       }
/* 119 */       CommandObjectiveExecutor.a(localCommandObjectiveExecutor, localEnumCommandResult, (String)localObject1, (String)localObject2);
/* 120 */       a(paramICommandListener, this, "commands.stats.success", new Object[] { localEnumCommandResult.b(), localObject2, localObject1 });
/* 121 */     } else if ("clear".equals(str)) {
/* 122 */       CommandObjectiveExecutor.a(localCommandObjectiveExecutor, localEnumCommandResult, null, null);
/* 123 */       a(paramICommandListener, this, "commands.stats.cleared", new Object[] { localEnumCommandResult.b() });
/*     */     }
/*     */     
/* 126 */     if (i != 0) {
/* 127 */       localObject1 = a(paramICommandListener, paramArrayOfString, 1, false);
/* 128 */       localObject2 = localWorld.getTileEntity((BlockPosition)localObject1);
/* 129 */       ((TileEntity)localObject2).update();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 136 */     if (paramArrayOfString.length == 1) {
/* 137 */       return a(paramArrayOfString, new String[] { "entity", "block" });
/*     */     }
/* 139 */     if ((paramArrayOfString.length == 2) && (paramArrayOfString[0].equals("entity"))) {
/* 140 */       return a(paramArrayOfString, d());
/*     */     }
/* 142 */     if ((paramArrayOfString.length >= 2) && (paramArrayOfString.length <= 4) && (paramArrayOfString[0].equals("block"))) {
/* 143 */       return a(paramArrayOfString, 1, paramBlockPosition);
/*     */     }
/* 145 */     if (((paramArrayOfString.length == 3) && (paramArrayOfString[0].equals("entity"))) || ((paramArrayOfString.length == 5) && (paramArrayOfString[0].equals("block")))) {
/* 146 */       return a(paramArrayOfString, new String[] { "set", "clear" });
/*     */     }
/* 148 */     if (((paramArrayOfString.length == 4) && (paramArrayOfString[0].equals("entity"))) || ((paramArrayOfString.length == 6) && (paramArrayOfString[0].equals("block")))) {
/* 149 */       return a(paramArrayOfString, CommandObjectiveExecutor.EnumCommandResult.c());
/*     */     }
/* 151 */     if (((paramArrayOfString.length == 6) && (paramArrayOfString[0].equals("entity"))) || ((paramArrayOfString.length == 8) && (paramArrayOfString[0].equals("block")))) {
/* 152 */       return a(paramArrayOfString, e());
/*     */     }
/*     */     
/* 155 */     return null;
/*     */   }
/*     */   
/*     */   protected String[] d() {
/* 159 */     return MinecraftServer.getServer().getPlayers();
/*     */   }
/*     */   
/*     */   protected List<String> e() {
/* 163 */     Collection localCollection = MinecraftServer.getServer().getWorldServer(0).getScoreboard().getObjectives();
/* 164 */     ArrayList localArrayList = Lists.newArrayList();
/*     */     
/* 166 */     for (ScoreboardObjective localScoreboardObjective : localCollection) {
/* 167 */       if (!localScoreboardObjective.getCriteria().isReadOnly()) {
/* 168 */         localArrayList.add(localScoreboardObjective.getName());
/*     */       }
/*     */     }
/*     */     
/* 172 */     return localArrayList;
/*     */   }
/*     */   
/*     */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*     */   {
/* 177 */     return (paramArrayOfString.length > 0) && (paramArrayOfString[0].equals("entity")) && (paramInt == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */