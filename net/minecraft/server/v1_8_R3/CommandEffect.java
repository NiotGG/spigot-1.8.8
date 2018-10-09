/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
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
/*     */ public class CommandEffect
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  22 */     return "effect";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  27 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  32 */     return "commands.effect.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  37 */     if (paramArrayOfString.length < 2) {
/*  38 */       throw new ExceptionUsage("commands.effect.usage", new Object[0]);
/*     */     }
/*     */     
/*  41 */     EntityLiving localEntityLiving = (EntityLiving)a(paramICommandListener, paramArrayOfString[0], EntityLiving.class);
/*     */     
/*  43 */     if (paramArrayOfString[1].equals("clear")) {
/*  44 */       if (localEntityLiving.getEffects().isEmpty()) {
/*  45 */         throw new CommandException("commands.effect.failure.notActive.all", new Object[] { localEntityLiving.getName() });
/*     */       }
/*     */       
/*  48 */       localEntityLiving.removeAllEffects();
/*  49 */       a(paramICommandListener, this, "commands.effect.success.removed.all", new Object[] { localEntityLiving.getName() }); return;
/*     */     }
/*     */     
/*     */     int i;
/*     */     try
/*     */     {
/*  55 */       i = a(paramArrayOfString[1], 1);
/*     */     } catch (ExceptionInvalidNumber localExceptionInvalidNumber) {
/*  57 */       MobEffectList localMobEffectList1 = MobEffectList.b(paramArrayOfString[1]);
/*  58 */       if (localMobEffectList1 == null) {
/*  59 */         throw localExceptionInvalidNumber;
/*     */       }
/*  61 */       i = localMobEffectList1.id;
/*     */     }
/*     */     
/*  64 */     int j = 600;
/*  65 */     int k = 30;
/*  66 */     int m = 0;
/*     */     
/*  68 */     if ((i < 0) || (i >= MobEffectList.byId.length) || (MobEffectList.byId[i] == null)) {
/*  69 */       throw new ExceptionInvalidNumber("commands.effect.notFound", new Object[] { Integer.valueOf(i) });
/*     */     }
/*     */     
/*  72 */     MobEffectList localMobEffectList2 = MobEffectList.byId[i];
/*  73 */     if (paramArrayOfString.length >= 3) {
/*  74 */       k = a(paramArrayOfString[2], 0, 1000000);
/*  75 */       if (localMobEffectList2.isInstant()) {
/*  76 */         j = k;
/*     */       } else {
/*  78 */         j = k * 20;
/*     */       }
/*  80 */     } else if (localMobEffectList2.isInstant()) {
/*  81 */       j = 1;
/*     */     }
/*     */     
/*  84 */     if (paramArrayOfString.length >= 4) {
/*  85 */       m = a(paramArrayOfString[3], 0, 255);
/*     */     }
/*     */     
/*  88 */     boolean bool = true;
/*  89 */     if ((paramArrayOfString.length >= 5) && 
/*  90 */       ("true".equalsIgnoreCase(paramArrayOfString[4]))) {
/*  91 */       bool = false;
/*     */     }
/*     */     
/*     */ 
/*  95 */     if (k > 0) {
/*  96 */       MobEffect localMobEffect = new MobEffect(i, j, m, false, bool);
/*  97 */       localEntityLiving.addEffect(localMobEffect);
/*  98 */       a(paramICommandListener, this, "commands.effect.success", new Object[] { new ChatMessage(localMobEffect.g(), new Object[0]), Integer.valueOf(i), Integer.valueOf(m), localEntityLiving.getName(), Integer.valueOf(k) });
/*  99 */       return;
/*     */     }
/*     */     
/* 102 */     if (localEntityLiving.hasEffect(i)) {
/* 103 */       localEntityLiving.removeEffect(i);
/* 104 */       a(paramICommandListener, this, "commands.effect.success.removed", new Object[] { new ChatMessage(localMobEffectList2.a(), new Object[0]), localEntityLiving.getName() });
/*     */     } else {
/* 106 */       throw new CommandException("commands.effect.failure.notActive", new Object[] { new ChatMessage(localMobEffectList2.a(), new Object[0]), localEntityLiving.getName() });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 113 */     if (paramArrayOfString.length == 1) {
/* 114 */       return a(paramArrayOfString, d());
/*     */     }
/* 116 */     if (paramArrayOfString.length == 2) {
/* 117 */       return a(paramArrayOfString, MobEffectList.c());
/*     */     }
/* 119 */     if (paramArrayOfString.length == 5) {
/* 120 */       return a(paramArrayOfString, new String[] { "true", "false" });
/*     */     }
/*     */     
/* 123 */     return null;
/*     */   }
/*     */   
/*     */   protected String[] d() {
/* 127 */     return MinecraftServer.getServer().getPlayers();
/*     */   }
/*     */   
/*     */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*     */   {
/* 132 */     return paramInt == 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */