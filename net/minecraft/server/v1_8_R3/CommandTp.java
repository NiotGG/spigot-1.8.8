/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
/*     */ 
/*     */ public class CommandTp extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  11 */     return "tp";
/*     */   }
/*     */   
/*     */   public int a() {
/*  15 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener icommandlistener) {
/*  19 */     return "commands.tp.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener icommandlistener, String[] astring) throws CommandException {
/*  23 */     if (astring.length < 1) {
/*  24 */       throw new ExceptionUsage("commands.tp.usage", new Object[0]);
/*     */     }
/*  26 */     byte b0 = 0;
/*     */     Object object;
/*     */     Object object;
/*  29 */     if ((astring.length != 2) && (astring.length != 4) && (astring.length != 6)) {
/*  30 */       object = b(icommandlistener);
/*     */     } else {
/*  32 */       object = b(icommandlistener, astring[0]);
/*  33 */       b0 = 1;
/*     */     }
/*     */     
/*  36 */     if ((astring.length != 1) && (astring.length != 2)) {
/*  37 */       if (astring.length < b0 + 3)
/*  38 */         throw new ExceptionUsage("commands.tp.usage", new Object[0]);
/*  39 */       if (((Entity)object).world != null) {
/*  40 */         int i = b0 + 1;
/*  41 */         CommandAbstract.CommandNumber commandabstract_commandnumber = a(((Entity)object).locX, astring[b0], true);
/*  42 */         CommandAbstract.CommandNumber commandabstract_commandnumber1 = a(((Entity)object).locY, astring[(i++)], 0, 0, false);
/*  43 */         CommandAbstract.CommandNumber commandabstract_commandnumber2 = a(((Entity)object).locZ, astring[(i++)], true);
/*  44 */         CommandAbstract.CommandNumber commandabstract_commandnumber3 = a(((Entity)object).yaw, astring.length > i ? astring[(i++)] : "~", false);
/*  45 */         CommandAbstract.CommandNumber commandabstract_commandnumber4 = a(((Entity)object).pitch, astring.length > i ? astring[i] : "~", false);
/*     */         
/*     */ 
/*  48 */         if ((object instanceof EntityPlayer)) {
/*  49 */           EnumSet enumset = EnumSet.noneOf(PacketPlayOutPosition.EnumPlayerTeleportFlags.class);
/*     */           
/*  51 */           if (commandabstract_commandnumber.c()) {
/*  52 */             enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.X);
/*     */           }
/*     */           
/*  55 */           if (commandabstract_commandnumber1.c()) {
/*  56 */             enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y);
/*     */           }
/*     */           
/*  59 */           if (commandabstract_commandnumber2.c()) {
/*  60 */             enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.Z);
/*     */           }
/*     */           
/*  63 */           if (commandabstract_commandnumber4.c()) {
/*  64 */             enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.X_ROT);
/*     */           }
/*     */           
/*  67 */           if (commandabstract_commandnumber3.c()) {
/*  68 */             enumset.add(PacketPlayOutPosition.EnumPlayerTeleportFlags.Y_ROT);
/*     */           }
/*     */           
/*  71 */           float f = (float)commandabstract_commandnumber3.b();
/*  72 */           if (!commandabstract_commandnumber3.c()) {
/*  73 */             f = MathHelper.g(f);
/*     */           }
/*     */           
/*  76 */           float f1 = (float)commandabstract_commandnumber4.b();
/*     */           
/*  78 */           if (!commandabstract_commandnumber4.c()) {
/*  79 */             f1 = MathHelper.g(f1);
/*     */           }
/*     */           
/*  82 */           if ((f1 > 90.0F) || (f1 < -90.0F)) {
/*  83 */             f1 = MathHelper.g(180.0F - f1);
/*  84 */             f = MathHelper.g(f + 180.0F);
/*     */           }
/*     */           
/*  87 */           ((Entity)object).mount(null);
/*  88 */           ((EntityPlayer)object).playerConnection.a(commandabstract_commandnumber.b(), commandabstract_commandnumber1.b(), commandabstract_commandnumber2.b(), f, f1, enumset);
/*  89 */           ((Entity)object).f(f);
/*     */         } else {
/*  91 */           float f2 = (float)MathHelper.g(commandabstract_commandnumber3.a());
/*     */           
/*  93 */           float f = (float)MathHelper.g(commandabstract_commandnumber4.a());
/*  94 */           if ((f > 90.0F) || (f < -90.0F)) {
/*  95 */             f = MathHelper.g(180.0F - f);
/*  96 */             f2 = MathHelper.g(f2 + 180.0F);
/*     */           }
/*     */           
/*  99 */           ((Entity)object).setPositionRotation(commandabstract_commandnumber.a(), commandabstract_commandnumber1.a(), commandabstract_commandnumber2.a(), f2, f);
/* 100 */           ((Entity)object).f(f2);
/*     */         }
/*     */         
/* 103 */         a(icommandlistener, this, "commands.tp.success.coordinates", new Object[] { ((Entity)object).getName(), Double.valueOf(commandabstract_commandnumber.a()), Double.valueOf(commandabstract_commandnumber1.a()), Double.valueOf(commandabstract_commandnumber2.a()) });
/*     */       }
/*     */     } else {
/* 106 */       Entity entity = b(icommandlistener, astring[(astring.length - 1)]);
/*     */       
/*     */ 
/*     */ 
/* 110 */       if (((Entity)object).getBukkitEntity().teleport(entity.getBukkitEntity(), PlayerTeleportEvent.TeleportCause.COMMAND)) {
/* 111 */         a(icommandlistener, this, "commands.tp.success", new Object[] { ((Entity)object).getName(), entity.getName() });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public java.util.List<String> tabComplete(ICommandListener icommandlistener, String[] astring, BlockPosition blockposition)
/*     */   {
/* 119 */     return (astring.length != 1) && (astring.length != 2) ? null : a(astring, MinecraftServer.getServer().getPlayers());
/*     */   }
/*     */   
/*     */   public boolean isListStart(String[] astring, int i) {
/* 123 */     return i == 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public int compareTo(ICommand o)
/*     */   {
/* 129 */     return a(o);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandTp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */