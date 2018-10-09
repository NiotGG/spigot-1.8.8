/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ public class CommandTitle
/*     */   extends CommandAbstract
/*     */ {
/*  23 */   private static final Logger a = ;
/*     */   
/*     */ 
/*     */   public String getCommand()
/*     */   {
/*  28 */     return "title";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  33 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  38 */     return "commands.title.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  43 */     if (paramArrayOfString.length < 2) {
/*  44 */       throw new ExceptionUsage("commands.title.usage", new Object[0]);
/*     */     }
/*  46 */     if (paramArrayOfString.length < 3) {
/*  47 */       if (("title".equals(paramArrayOfString[1])) || ("subtitle".equals(paramArrayOfString[1]))) {
/*  48 */         throw new ExceptionUsage("commands.title.usage.title", new Object[0]);
/*     */       }
/*  50 */       if ("times".equals(paramArrayOfString[1])) {
/*  51 */         throw new ExceptionUsage("commands.title.usage.times", new Object[0]);
/*     */       }
/*     */     }
/*     */     
/*  55 */     EntityPlayer localEntityPlayer = a(paramICommandListener, paramArrayOfString[0]);
/*  56 */     PacketPlayOutTitle.EnumTitleAction localEnumTitleAction = PacketPlayOutTitle.EnumTitleAction.a(paramArrayOfString[1]);
/*     */     
/*  58 */     if ((localEnumTitleAction == PacketPlayOutTitle.EnumTitleAction.CLEAR) || (localEnumTitleAction == PacketPlayOutTitle.EnumTitleAction.RESET)) {
/*  59 */       if (paramArrayOfString.length != 2) {
/*  60 */         throw new ExceptionUsage("commands.title.usage", new Object[0]);
/*     */       }
/*  62 */       PacketPlayOutTitle localPacketPlayOutTitle1 = new PacketPlayOutTitle(localEnumTitleAction, null);
/*  63 */       localEntityPlayer.playerConnection.sendPacket(localPacketPlayOutTitle1);
/*  64 */       a(paramICommandListener, this, "commands.title.success", new Object[0]); return;
/*     */     }
/*     */     
/*     */     Object localObject;
/*  68 */     if (localEnumTitleAction == PacketPlayOutTitle.EnumTitleAction.TIMES) {
/*  69 */       if (paramArrayOfString.length != 5) {
/*  70 */         throw new ExceptionUsage("commands.title.usage", new Object[0]);
/*     */       }
/*  72 */       int i = a(paramArrayOfString[2]);
/*  73 */       int j = a(paramArrayOfString[3]);
/*  74 */       int k = a(paramArrayOfString[4]);
/*  75 */       localObject = new PacketPlayOutTitle(i, j, k);
/*  76 */       localEntityPlayer.playerConnection.sendPacket((Packet)localObject);
/*  77 */       a(paramICommandListener, this, "commands.title.success", new Object[0]);
/*  78 */       return;
/*     */     }
/*     */     
/*  81 */     if (paramArrayOfString.length < 3) {
/*  82 */       throw new ExceptionUsage("commands.title.usage", new Object[0]);
/*     */     }
/*     */     
/*  85 */     String str = a(paramArrayOfString, 2);
/*     */     IChatBaseComponent localIChatBaseComponent;
/*     */     try {
/*  88 */       localIChatBaseComponent = IChatBaseComponent.ChatSerializer.a(str);
/*     */     } catch (JsonParseException localJsonParseException) {
/*  90 */       localObject = ExceptionUtils.getRootCause(localJsonParseException);
/*  91 */       throw new ExceptionInvalidSyntax("commands.tellraw.jsonException", new Object[] { localObject == null ? "" : ((Throwable)localObject).getMessage() });
/*     */     }
/*     */     
/*  94 */     PacketPlayOutTitle localPacketPlayOutTitle2 = new PacketPlayOutTitle(localEnumTitleAction, ChatComponentUtils.filterForDisplay(paramICommandListener, localIChatBaseComponent, localEntityPlayer));
/*  95 */     localEntityPlayer.playerConnection.sendPacket(localPacketPlayOutTitle2);
/*  96 */     a(paramICommandListener, this, "commands.title.success", new Object[0]);
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 102 */     if (paramArrayOfString.length == 1) {
/* 103 */       return a(paramArrayOfString, MinecraftServer.getServer().getPlayers());
/*     */     }
/* 105 */     if (paramArrayOfString.length == 2) {
/* 106 */       return a(paramArrayOfString, PacketPlayOutTitle.EnumTitleAction.a());
/*     */     }
/* 108 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*     */   {
/* 113 */     return paramInt == 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandTitle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */