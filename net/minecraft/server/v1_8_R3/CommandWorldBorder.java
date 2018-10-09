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
/*     */ 
/*     */ public class CommandWorldBorder
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  20 */     return "worldborder";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  25 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  30 */     return "commands.worldborder.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  35 */     if (paramArrayOfString.length < 1) {
/*  36 */       throw new ExceptionUsage("commands.worldborder.usage", new Object[0]);
/*     */     }
/*     */     
/*  39 */     WorldBorder localWorldBorder = d();
/*  40 */     double d1; double d2; long l; if (paramArrayOfString[0].equals("set")) {
/*  41 */       if ((paramArrayOfString.length != 2) && (paramArrayOfString.length != 3)) {
/*  42 */         throw new ExceptionUsage("commands.worldborder.set.usage", new Object[0]);
/*     */       }
/*     */       
/*  45 */       d1 = localWorldBorder.j();
/*  46 */       d2 = a(paramArrayOfString[1], 1.0D, 6.0E7D);
/*  47 */       l = paramArrayOfString.length > 2 ? a(paramArrayOfString[2], 0L, 9223372036854775L) * 1000L : 0L;
/*     */       
/*  49 */       if (l > 0L) {
/*  50 */         localWorldBorder.transitionSizeBetween(d1, d2, l);
/*  51 */         if (d1 > d2) {
/*  52 */           a(paramICommandListener, this, "commands.worldborder.setSlowly.shrink.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d1) }), Long.toString(l / 1000L) });
/*     */         } else {
/*  54 */           a(paramICommandListener, this, "commands.worldborder.setSlowly.grow.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d1) }), Long.toString(l / 1000L) });
/*     */         }
/*     */       } else {
/*  57 */         localWorldBorder.setSize(d2);
/*  58 */         a(paramICommandListener, this, "commands.worldborder.set.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d1) }) });
/*     */       }
/*  60 */     } else if (paramArrayOfString[0].equals("add")) {
/*  61 */       if ((paramArrayOfString.length != 2) && (paramArrayOfString.length != 3)) {
/*  62 */         throw new ExceptionUsage("commands.worldborder.add.usage", new Object[0]);
/*     */       }
/*     */       
/*  65 */       d1 = localWorldBorder.getSize();
/*  66 */       d2 = d1 + a(paramArrayOfString[1], -d1, 6.0E7D - d1);
/*  67 */       l = localWorldBorder.i() + (paramArrayOfString.length > 2 ? a(paramArrayOfString[2], 0L, 9223372036854775L) * 1000L : 0L);
/*     */       
/*  69 */       if (l > 0L) {
/*  70 */         localWorldBorder.transitionSizeBetween(d1, d2, l);
/*  71 */         if (d1 > d2) {
/*  72 */           a(paramICommandListener, this, "commands.worldborder.setSlowly.shrink.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d1) }), Long.toString(l / 1000L) });
/*     */         } else {
/*  74 */           a(paramICommandListener, this, "commands.worldborder.setSlowly.grow.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d1) }), Long.toString(l / 1000L) });
/*     */         }
/*     */       } else {
/*  77 */         localWorldBorder.setSize(d2);
/*  78 */         a(paramICommandListener, this, "commands.worldborder.set.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d2) }), String.format("%.1f", new Object[] { Double.valueOf(d1) }) });
/*     */       }
/*  80 */     } else if (paramArrayOfString[0].equals("center")) {
/*  81 */       if (paramArrayOfString.length != 3) {
/*  82 */         throw new ExceptionUsage("commands.worldborder.center.usage", new Object[0]);
/*     */       }
/*     */       
/*  85 */       BlockPosition localBlockPosition = paramICommandListener.getChunkCoordinates();
/*  86 */       double d3 = b(localBlockPosition.getX() + 0.5D, paramArrayOfString[1], true);
/*  87 */       double d4 = b(localBlockPosition.getZ() + 0.5D, paramArrayOfString[2], true);
/*     */       
/*  89 */       localWorldBorder.setCenter(d3, d4);
/*  90 */       a(paramICommandListener, this, "commands.worldborder.center.success", new Object[] { Double.valueOf(d3), Double.valueOf(d4) });
/*  91 */     } else if (paramArrayOfString[0].equals("damage")) {
/*  92 */       if (paramArrayOfString.length < 2) {
/*  93 */         throw new ExceptionUsage("commands.worldborder.damage.usage", new Object[0]);
/*     */       }
/*     */       
/*  96 */       if (paramArrayOfString[1].equals("buffer")) {
/*  97 */         if (paramArrayOfString.length != 3) {
/*  98 */           throw new ExceptionUsage("commands.worldborder.damage.buffer.usage", new Object[0]);
/*     */         }
/*     */         
/* 101 */         d1 = a(paramArrayOfString[2], 0.0D);
/* 102 */         d2 = localWorldBorder.getDamageBuffer();
/* 103 */         localWorldBorder.setDamageBuffer(d1);
/* 104 */         a(paramICommandListener, this, "commands.worldborder.damage.buffer.success", new Object[] { String.format("%.1f", new Object[] { Double.valueOf(d1) }), String.format("%.1f", new Object[] { Double.valueOf(d2) }) });
/* 105 */       } else if (paramArrayOfString[1].equals("amount")) {
/* 106 */         if (paramArrayOfString.length != 3) {
/* 107 */           throw new ExceptionUsage("commands.worldborder.damage.amount.usage", new Object[0]);
/*     */         }
/*     */         
/* 110 */         d1 = a(paramArrayOfString[2], 0.0D);
/* 111 */         d2 = localWorldBorder.getDamageAmount();
/* 112 */         localWorldBorder.setDamageAmount(d1);
/* 113 */         a(paramICommandListener, this, "commands.worldborder.damage.amount.success", new Object[] { String.format("%.2f", new Object[] { Double.valueOf(d1) }), String.format("%.2f", new Object[] { Double.valueOf(d2) }) });
/*     */       }
/* 115 */     } else if (paramArrayOfString[0].equals("warning")) {
/* 116 */       if (paramArrayOfString.length < 2) {
/* 117 */         throw new ExceptionUsage("commands.worldborder.warning.usage", new Object[0]);
/*     */       }
/*     */       
/* 120 */       int i = a(paramArrayOfString[2], 0);
/* 121 */       int j; if (paramArrayOfString[1].equals("time")) {
/* 122 */         if (paramArrayOfString.length != 3) {
/* 123 */           throw new ExceptionUsage("commands.worldborder.warning.time.usage", new Object[0]);
/*     */         }
/*     */         
/* 126 */         j = localWorldBorder.getWarningTime();
/* 127 */         localWorldBorder.setWarningTime(i);
/* 128 */         a(paramICommandListener, this, "commands.worldborder.warning.time.success", new Object[] { Integer.valueOf(i), Integer.valueOf(j) });
/* 129 */       } else if (paramArrayOfString[1].equals("distance")) {
/* 130 */         if (paramArrayOfString.length != 3) {
/* 131 */           throw new ExceptionUsage("commands.worldborder.warning.distance.usage", new Object[0]);
/*     */         }
/*     */         
/* 134 */         j = localWorldBorder.getWarningDistance();
/* 135 */         localWorldBorder.setWarningDistance(i);
/* 136 */         a(paramICommandListener, this, "commands.worldborder.warning.distance.success", new Object[] { Integer.valueOf(i), Integer.valueOf(j) });
/*     */       }
/* 138 */     } else if (paramArrayOfString[0].equals("get")) {
/* 139 */       d1 = localWorldBorder.getSize();
/* 140 */       paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, MathHelper.floor(d1 + 0.5D));
/* 141 */       paramICommandListener.sendMessage(new ChatMessage("commands.worldborder.get.success", new Object[] { String.format("%.0f", new Object[] { Double.valueOf(d1) }) }));
/*     */     } else {
/* 143 */       throw new ExceptionUsage("commands.worldborder.usage", new Object[0]);
/*     */     }
/*     */   }
/*     */   
/*     */   protected WorldBorder d() {
/* 148 */     return MinecraftServer.getServer().worldServer[0].getWorldBorder();
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 154 */     if (paramArrayOfString.length == 1) {
/* 155 */       return a(paramArrayOfString, new String[] { "set", "center", "damage", "warning", "add", "get" });
/*     */     }
/*     */     
/* 158 */     if ((paramArrayOfString.length == 2) && (paramArrayOfString[0].equals("damage")))
/* 159 */       return a(paramArrayOfString, new String[] { "buffer", "amount" });
/* 160 */     if ((paramArrayOfString.length >= 2) && (paramArrayOfString.length <= 3) && (paramArrayOfString[0].equals("center")))
/* 161 */       return b(paramArrayOfString, 1, paramBlockPosition);
/* 162 */     if ((paramArrayOfString.length == 2) && (paramArrayOfString[0].equals("warning"))) {
/* 163 */       return a(paramArrayOfString, new String[] { "time", "distance" });
/*     */     }
/*     */     
/* 166 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandWorldBorder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */