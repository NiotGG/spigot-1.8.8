/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandOp
/*    */   extends CommandAbstract
/*    */ {
/*    */   public String getCommand()
/*    */   {
/* 18 */     return "op";
/*    */   }
/*    */   
/*    */   public int a()
/*    */   {
/* 23 */     return 3;
/*    */   }
/*    */   
/*    */   public String getUsage(ICommandListener paramICommandListener)
/*    */   {
/* 28 */     return "commands.op.usage";
/*    */   }
/*    */   
/*    */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*    */   {
/* 33 */     if ((paramArrayOfString.length != 1) || (paramArrayOfString[0].length() <= 0)) {
/* 34 */       throw new ExceptionUsage("commands.op.usage", new Object[0]);
/*    */     }
/*    */     
/* 37 */     MinecraftServer localMinecraftServer = MinecraftServer.getServer();
/* 38 */     GameProfile localGameProfile = localMinecraftServer.getUserCache().getProfile(paramArrayOfString[0]);
/* 39 */     if (localGameProfile == null) {
/* 40 */       throw new CommandException("commands.op.failed", new Object[] { paramArrayOfString[0] });
/*    */     }
/*    */     
/* 43 */     localMinecraftServer.getPlayerList().addOp(localGameProfile);
/* 44 */     a(paramICommandListener, this, "commands.op.success", new Object[] { paramArrayOfString[0] });
/*    */   }
/*    */   
/*    */ 
/*    */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*    */   {
/* 50 */     if (paramArrayOfString.length == 1) {
/* 51 */       String str = paramArrayOfString[(paramArrayOfString.length - 1)];
/* 52 */       ArrayList localArrayList = Lists.newArrayList();
/*    */       
/* 54 */       for (GameProfile localGameProfile : MinecraftServer.getServer().L()) {
/* 55 */         if ((!MinecraftServer.getServer().getPlayerList().isOp(localGameProfile)) && (a(str, localGameProfile.getName()))) {
/* 56 */           localArrayList.add(localGameProfile.getName());
/*    */         }
/*    */       }
/*    */       
/* 60 */       return localArrayList;
/*    */     }
/*    */     
/* 63 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandOp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */