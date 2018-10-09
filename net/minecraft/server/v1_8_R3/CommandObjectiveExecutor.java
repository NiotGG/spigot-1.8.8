/*     */ package net.minecraft.server.v1_8_R3;
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
/*     */ public class CommandObjectiveExecutor
/*     */ {
/*  18 */   private static final int a = EnumCommandResult.values().length;
/*  19 */   private static final String[] b = new String[a];
/*     */   
/*  21 */   private String[] c = b;
/*  22 */   private String[] d = b;
/*     */   
/*     */ 
/*     */ 
/*     */   public void a(final ICommandListener paramICommandListener, EnumCommandResult paramEnumCommandResult, int paramInt)
/*     */   {
/*  28 */     String str1 = this.c[paramEnumCommandResult.a()];
/*  29 */     if (str1 == null) {
/*  30 */       return;
/*     */     }
/*  32 */     ICommandListener local1 = new ICommandListener()
/*     */     {
/*     */       public String getName() {
/*  35 */         return paramICommandListener.getName();
/*     */       }
/*     */       
/*     */       public IChatBaseComponent getScoreboardDisplayName()
/*     */       {
/*  40 */         return paramICommandListener.getScoreboardDisplayName();
/*     */       }
/*     */       
/*     */       public void sendMessage(IChatBaseComponent paramAnonymousIChatBaseComponent)
/*     */       {
/*  45 */         paramICommandListener.sendMessage(paramAnonymousIChatBaseComponent);
/*     */       }
/*     */       
/*     */       public boolean a(int paramAnonymousInt, String paramAnonymousString)
/*     */       {
/*  50 */         return true;
/*     */       }
/*     */       
/*     */       public BlockPosition getChunkCoordinates()
/*     */       {
/*  55 */         return paramICommandListener.getChunkCoordinates();
/*     */       }
/*     */       
/*     */       public Vec3D d()
/*     */       {
/*  60 */         return paramICommandListener.d();
/*     */       }
/*     */       
/*     */       public World getWorld()
/*     */       {
/*  65 */         return paramICommandListener.getWorld();
/*     */       }
/*     */       
/*     */       public Entity f()
/*     */       {
/*  70 */         return paramICommandListener.f();
/*     */       }
/*     */       
/*     */       public boolean getSendCommandFeedback()
/*     */       {
/*  75 */         return paramICommandListener.getSendCommandFeedback();
/*     */       }
/*     */       
/*     */       public void a(CommandObjectiveExecutor.EnumCommandResult paramAnonymousEnumCommandResult, int paramAnonymousInt)
/*     */       {
/*  80 */         paramICommandListener.a(paramAnonymousEnumCommandResult, paramAnonymousInt);
/*     */       }
/*     */     };
/*     */     String str2;
/*     */     try {
/*  85 */       str2 = CommandAbstract.e(local1, str1);
/*     */     } catch (ExceptionEntityNotFound localExceptionEntityNotFound) {
/*  87 */       return;
/*     */     }
/*  89 */     String str3 = this.d[paramEnumCommandResult.a()];
/*  90 */     if (str3 == null) {
/*  91 */       return;
/*     */     }
/*  93 */     Scoreboard localScoreboard = paramICommandListener.getWorld().getScoreboard();
/*  94 */     ScoreboardObjective localScoreboardObjective = localScoreboard.getObjective(str3);
/*  95 */     if (localScoreboardObjective == null) {
/*  96 */       return;
/*     */     }
/*  98 */     if (!localScoreboard.b(str2, localScoreboardObjective)) {
/*  99 */       return;
/*     */     }
/* 101 */     ScoreboardScore localScoreboardScore = localScoreboard.getPlayerScoreForObjective(str2, localScoreboardObjective);
/* 102 */     localScoreboardScore.setScore(paramInt);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 106 */     if (!paramNBTTagCompound.hasKeyOfType("CommandStats", 10)) {
/* 107 */       return;
/*     */     }
/* 109 */     NBTTagCompound localNBTTagCompound = paramNBTTagCompound.getCompound("CommandStats");
/* 110 */     for (EnumCommandResult localEnumCommandResult : EnumCommandResult.values()) {
/* 111 */       String str1 = localEnumCommandResult.b() + "Name";
/* 112 */       String str2 = localEnumCommandResult.b() + "Objective";
/* 113 */       if ((localNBTTagCompound.hasKeyOfType(str1, 8)) && (localNBTTagCompound.hasKeyOfType(str2, 8)))
/*     */       {
/*     */ 
/* 116 */         String str3 = localNBTTagCompound.getString(str1);
/* 117 */         String str4 = localNBTTagCompound.getString(str2);
/* 118 */         a(this, localEnumCommandResult, str3, str4);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 123 */   public void b(NBTTagCompound paramNBTTagCompound) { NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/* 124 */     for (EnumCommandResult localEnumCommandResult : EnumCommandResult.values()) {
/* 125 */       String str1 = this.c[localEnumCommandResult.a()];
/* 126 */       String str2 = this.d[localEnumCommandResult.a()];
/* 127 */       if ((str1 != null) && (str2 != null))
/*     */       {
/*     */ 
/* 130 */         localNBTTagCompound.setString(localEnumCommandResult.b() + "Name", str1);
/* 131 */         localNBTTagCompound.setString(localEnumCommandResult.b() + "Objective", str2);
/*     */       } }
/* 133 */     if (!localNBTTagCompound.isEmpty()) {
/* 134 */       paramNBTTagCompound.set("CommandStats", localNBTTagCompound);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void a(CommandObjectiveExecutor paramCommandObjectiveExecutor, EnumCommandResult paramEnumCommandResult, String paramString1, String paramString2) {
/* 139 */     if ((paramString1 == null) || (paramString1.length() == 0) || (paramString2 == null) || (paramString2.length() == 0)) {
/* 140 */       a(paramCommandObjectiveExecutor, paramEnumCommandResult);
/* 141 */       return;
/*     */     }
/* 143 */     if ((paramCommandObjectiveExecutor.c == b) || (paramCommandObjectiveExecutor.d == b)) {
/* 144 */       paramCommandObjectiveExecutor.c = new String[a];
/* 145 */       paramCommandObjectiveExecutor.d = new String[a];
/*     */     }
/* 147 */     paramCommandObjectiveExecutor.c[paramEnumCommandResult.a()] = paramString1;
/* 148 */     paramCommandObjectiveExecutor.d[paramEnumCommandResult.a()] = paramString2;
/*     */   }
/*     */   
/*     */   private static void a(CommandObjectiveExecutor paramCommandObjectiveExecutor, EnumCommandResult paramEnumCommandResult) {
/* 152 */     if ((paramCommandObjectiveExecutor.c == b) || (paramCommandObjectiveExecutor.d == b)) {
/* 153 */       return;
/*     */     }
/* 155 */     paramCommandObjectiveExecutor.c[paramEnumCommandResult.a()] = null;
/* 156 */     paramCommandObjectiveExecutor.d[paramEnumCommandResult.a()] = null;
/*     */     
/* 158 */     int i = 1;
/* 159 */     for (EnumCommandResult localEnumCommandResult : EnumCommandResult.values()) {
/* 160 */       if ((paramCommandObjectiveExecutor.c[localEnumCommandResult.a()] != null) && (paramCommandObjectiveExecutor.d[localEnumCommandResult.a()] != null)) {
/* 161 */         i = 0;
/* 162 */         break;
/*     */       }
/*     */     }
/* 165 */     if (i != 0) {
/* 166 */       paramCommandObjectiveExecutor.c = b;
/* 167 */       paramCommandObjectiveExecutor.d = b;
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(CommandObjectiveExecutor paramCommandObjectiveExecutor) {
/* 172 */     for (EnumCommandResult localEnumCommandResult : ) {
/* 173 */       a(this, localEnumCommandResult, paramCommandObjectiveExecutor.c[localEnumCommandResult.a()], paramCommandObjectiveExecutor.d[localEnumCommandResult.a()]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum EnumCommandResult
/*     */   {
/*     */     final int f;
/*     */     
/*     */ 
/*     */ 
/*     */     final String g;
/*     */     
/*     */ 
/*     */ 
/*     */     private EnumCommandResult(int paramInt, String paramString)
/*     */     {
/* 192 */       this.f = paramInt;
/* 193 */       this.g = paramString;
/*     */     }
/*     */     
/*     */     public int a() {
/* 197 */       return this.f;
/*     */     }
/*     */     
/*     */     public String b() {
/* 201 */       return this.g;
/*     */     }
/*     */     
/*     */     public static String[] c() {
/* 205 */       String[] arrayOfString = new String[values().length];
/* 206 */       int i = 0;
/* 207 */       for (EnumCommandResult localEnumCommandResult : values()) {
/* 208 */         arrayOfString[(i++)] = localEnumCommandResult.b();
/*     */       }
/* 210 */       return arrayOfString;
/*     */     }
/*     */     
/*     */     public static EnumCommandResult a(String paramString)
/*     */     {
/* 215 */       for (EnumCommandResult localEnumCommandResult : ) {
/* 216 */         if (localEnumCommandResult.b().equals(paramString)) {
/* 217 */           return localEnumCommandResult;
/*     */         }
/*     */       }
/* 220 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandObjectiveExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */