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
/*     */ public class CommandParticle
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  19 */     return "particle";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  24 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  29 */     return "commands.particle.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  34 */     if (paramArrayOfString.length < 8) {
/*  35 */       throw new ExceptionUsage("commands.particle.usage", new Object[0]);
/*     */     }
/*     */     
/*  38 */     int i = 0;
/*  39 */     Object localObject1 = null;
/*  40 */     for (Object localObject3 : EnumParticle.values()) {
/*  41 */       if (((EnumParticle)localObject3).f()) {
/*  42 */         if (paramArrayOfString[0].startsWith(((EnumParticle)localObject3).b())) {
/*  43 */           i = 1;
/*  44 */           localObject1 = localObject3;
/*  45 */           break;
/*     */         }
/*  47 */       } else if (paramArrayOfString[0].equals(((EnumParticle)localObject3).b())) {
/*  48 */         i = 1;
/*  49 */         localObject1 = localObject3;
/*  50 */         break;
/*     */       }
/*     */     }
/*     */     
/*  54 */     if (i == 0) {
/*  55 */       throw new CommandException("commands.particle.notFound", new Object[] { paramArrayOfString[0] });
/*     */     }
/*     */     
/*  58 */     ??? = paramArrayOfString[0];
/*  59 */     Vec3D localVec3D = paramICommandListener.d();
/*  60 */     double d1 = (float)b(localVec3D.a, paramArrayOfString[1], true);
/*  61 */     double d2 = (float)b(localVec3D.b, paramArrayOfString[2], true);
/*  62 */     double d3 = (float)b(localVec3D.c, paramArrayOfString[3], true);
/*  63 */     double d4 = (float)c(paramArrayOfString[4]);
/*  64 */     double d5 = (float)c(paramArrayOfString[5]);
/*  65 */     double d6 = (float)c(paramArrayOfString[6]);
/*  66 */     double d7 = (float)c(paramArrayOfString[7]);
/*     */     
/*  68 */     int m = 0;
/*  69 */     if (paramArrayOfString.length > 8) {
/*  70 */       m = a(paramArrayOfString[8], 0);
/*     */     }
/*     */     
/*  73 */     boolean bool = false;
/*  74 */     if ((paramArrayOfString.length > 9) && ("force".equals(paramArrayOfString[9]))) {
/*  75 */       bool = true;
/*     */     }
/*     */     
/*  78 */     World localWorld = paramICommandListener.getWorld();
/*  79 */     if ((localWorld instanceof WorldServer)) {
/*  80 */       WorldServer localWorldServer = (WorldServer)localWorld;
/*  81 */       int[] arrayOfInt = new int[((EnumParticle)localObject1).d()];
/*  82 */       if (((EnumParticle)localObject1).f()) {
/*  83 */         String[] arrayOfString = paramArrayOfString[0].split("_", 3);
/*  84 */         for (int n = 1; n < arrayOfString.length; n++) {
/*     */           try {
/*  86 */             arrayOfInt[(n - 1)] = Integer.parseInt(arrayOfString[n]);
/*     */           } catch (NumberFormatException localNumberFormatException) {
/*  88 */             throw new CommandException("commands.particle.notFound", new Object[] { paramArrayOfString[0] });
/*     */           }
/*     */         }
/*     */       }
/*  92 */       localWorldServer.a((EnumParticle)localObject1, bool, d1, d2, d3, m, d4, d5, d6, d7, arrayOfInt);
/*  93 */       a(paramICommandListener, this, "commands.particle.success", new Object[] { ???, Integer.valueOf(Math.max(m, 1)) });
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 100 */     if (paramArrayOfString.length == 1)
/* 101 */       return a(paramArrayOfString, EnumParticle.a());
/* 102 */     if ((paramArrayOfString.length > 1) && (paramArrayOfString.length <= 4))
/* 103 */       return a(paramArrayOfString, 1, paramBlockPosition);
/* 104 */     if (paramArrayOfString.length == 10) {
/* 105 */       return a(paramArrayOfString, new String[] { "normal", "force" });
/*     */     }
/* 107 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandParticle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */