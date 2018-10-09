/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
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
/*     */ public class CommandPlaySound
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  19 */     return "playsound";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  24 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  29 */     return "commands.playsound.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  34 */     if (paramArrayOfString.length < 2) {
/*  35 */       throw new ExceptionUsage(getUsage(paramICommandListener), new Object[0]);
/*     */     }
/*     */     
/*  38 */     int i = 0;
/*  39 */     String str = paramArrayOfString[(i++)];
/*  40 */     EntityPlayer localEntityPlayer = a(paramICommandListener, paramArrayOfString[(i++)]);
/*     */     
/*  42 */     Vec3D localVec3D = paramICommandListener.d();
/*     */     
/*  44 */     double d1 = localVec3D.a;
/*  45 */     if (paramArrayOfString.length > i) {
/*  46 */       d1 = b(d1, paramArrayOfString[(i++)], true);
/*     */     }
/*     */     
/*  49 */     double d2 = localVec3D.b;
/*  50 */     if (paramArrayOfString.length > i) {
/*  51 */       d2 = b(d2, paramArrayOfString[(i++)], 0, 0, false);
/*     */     }
/*     */     
/*  54 */     double d3 = localVec3D.c;
/*  55 */     if (paramArrayOfString.length > i) {
/*  56 */       d3 = b(d3, paramArrayOfString[(i++)], true);
/*     */     }
/*     */     
/*  59 */     double d4 = 1.0D;
/*  60 */     if (paramArrayOfString.length > i) {
/*  61 */       d4 = a(paramArrayOfString[(i++)], 0.0D, 3.4028234663852886E38D);
/*     */     }
/*     */     
/*  64 */     double d5 = 1.0D;
/*  65 */     if (paramArrayOfString.length > i) {
/*  66 */       d5 = a(paramArrayOfString[(i++)], 0.0D, 2.0D);
/*     */     }
/*     */     
/*  69 */     double d6 = 0.0D;
/*  70 */     if (paramArrayOfString.length > i) {
/*  71 */       d6 = a(paramArrayOfString[i], 0.0D, 1.0D);
/*     */     }
/*     */     
/*  74 */     double d7 = d4 > 1.0D ? d4 * 16.0D : 16.0D;
/*  75 */     double d8 = localEntityPlayer.f(d1, d2, d3);
/*     */     
/*  77 */     if (d8 > d7) {
/*  78 */       if (d6 <= 0.0D) {
/*  79 */         throw new CommandException("commands.playsound.playerTooFar", new Object[] { localEntityPlayer.getName() });
/*     */       }
/*     */       
/*  82 */       double d9 = d1 - localEntityPlayer.locX;
/*  83 */       double d10 = d2 - localEntityPlayer.locY;
/*  84 */       double d11 = d3 - localEntityPlayer.locZ;
/*  85 */       double d12 = Math.sqrt(d9 * d9 + d10 * d10 + d11 * d11);
/*     */       
/*  87 */       if (d12 > 0.0D) {
/*  88 */         d1 = localEntityPlayer.locX + d9 / d12 * 2.0D;
/*  89 */         d2 = localEntityPlayer.locY + d10 / d12 * 2.0D;
/*  90 */         d3 = localEntityPlayer.locZ + d11 / d12 * 2.0D;
/*     */       }
/*     */       
/*  93 */       d4 = d6;
/*     */     }
/*     */     
/*  96 */     localEntityPlayer.playerConnection.sendPacket(new PacketPlayOutNamedSoundEffect(str, d1, d2, d3, (float)d4, (float)d5));
/*  97 */     a(paramICommandListener, this, "commands.playsound.success", new Object[] { str, localEntityPlayer.getName() });
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 103 */     if (paramArrayOfString.length == 2)
/* 104 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/* 105 */     if ((paramArrayOfString.length > 2) && (paramArrayOfString.length <= 5)) {
/* 106 */       return a(paramArrayOfString, 2, paramBlockPosition);
/*     */     }
/*     */     
/* 109 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*     */   {
/* 114 */     return paramInt == 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandPlaySound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */