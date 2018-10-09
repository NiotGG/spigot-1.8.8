/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandHandler
/*     */   implements ICommandHandler
/*     */ {
/*  21 */   private static final Logger a = ;
/*  22 */   private final Map<String, ICommand> b = Maps.newHashMap();
/*  23 */   private final Set<ICommand> c = Sets.newHashSet();
/*     */   
/*     */   public int a(ICommandListener paramICommandListener, String paramString)
/*     */   {
/*  27 */     paramString = paramString.trim();
/*  28 */     if (paramString.startsWith("/")) {
/*  29 */       paramString = paramString.substring(1);
/*     */     }
/*     */     
/*  32 */     String[] arrayOfString = paramString.split(" ");
/*  33 */     String str1 = arrayOfString[0];
/*     */     
/*  35 */     arrayOfString = a(arrayOfString);
/*     */     
/*  37 */     ICommand localICommand = (ICommand)this.b.get(str1);
/*  38 */     int i = a(localICommand, arrayOfString);
/*  39 */     int j = 0;
/*     */     Object localObject;
/*  41 */     if (localICommand == null) {
/*  42 */       localObject = new ChatMessage("commands.generic.notFound", new Object[0]);
/*  43 */       ((ChatMessage)localObject).getChatModifier().setColor(EnumChatFormat.RED);
/*  44 */       paramICommandListener.sendMessage((IChatBaseComponent)localObject);
/*     */     }
/*  46 */     else if (localICommand.canUse(paramICommandListener)) {
/*  47 */       if (i > -1)
/*     */       {
/*  49 */         localObject = PlayerSelector.getPlayers(paramICommandListener, arrayOfString[i], Entity.class);
/*  50 */         String str2 = arrayOfString[i];
/*  51 */         paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ENTITIES, ((List)localObject).size());
/*     */         
/*  53 */         for (Entity localEntity : (List)localObject) {
/*  54 */           arrayOfString[i] = localEntity.getUniqueID().toString();
/*     */           
/*  56 */           if (a(paramICommandListener, arrayOfString, localICommand, paramString)) {
/*  57 */             j++;
/*     */           }
/*     */         }
/*  60 */         arrayOfString[i] = str2;
/*     */       } else {
/*  62 */         paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ENTITIES, 1);
/*  63 */         if (a(paramICommandListener, arrayOfString, localICommand, paramString)) {
/*  64 */           j++;
/*     */         }
/*     */       }
/*     */     } else {
/*  68 */       localObject = new ChatMessage("commands.generic.permission", new Object[0]);
/*  69 */       ((ChatMessage)localObject).getChatModifier().setColor(EnumChatFormat.RED);
/*  70 */       paramICommandListener.sendMessage((IChatBaseComponent)localObject);
/*     */     }
/*     */     
/*     */ 
/*  74 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.SUCCESS_COUNT, j);
/*  75 */     return j;
/*     */   }
/*     */   
/*     */   protected boolean a(ICommandListener paramICommandListener, String[] paramArrayOfString, ICommand paramICommand, String paramString) {
/*     */     try {
/*  80 */       paramICommand.execute(paramICommandListener, paramArrayOfString);
/*  81 */       return true;
/*     */     } catch (ExceptionUsage localExceptionUsage) {
/*  83 */       localChatMessage = new ChatMessage("commands.generic.usage", new Object[] { new ChatMessage(localExceptionUsage.getMessage(), localExceptionUsage.getArgs()) });
/*  84 */       localChatMessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  85 */       paramICommandListener.sendMessage(localChatMessage);
/*     */     } catch (CommandException localCommandException) {
/*  87 */       localChatMessage = new ChatMessage(localCommandException.getMessage(), localCommandException.getArgs());
/*  88 */       localChatMessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  89 */       paramICommandListener.sendMessage(localChatMessage);
/*     */     } catch (Throwable localThrowable) {
/*  91 */       ChatMessage localChatMessage = new ChatMessage("commands.generic.exception", new Object[0]);
/*  92 */       localChatMessage.getChatModifier().setColor(EnumChatFormat.RED);
/*  93 */       paramICommandListener.sendMessage(localChatMessage);
/*  94 */       a.warn("Couldn't process command: '" + paramString + "'");
/*     */     }
/*     */     
/*  97 */     return false;
/*     */   }
/*     */   
/*     */   public ICommand a(ICommand paramICommand) {
/* 101 */     this.b.put(paramICommand.getCommand(), paramICommand);
/* 102 */     this.c.add(paramICommand);
/*     */     
/* 104 */     for (String str : paramICommand.b()) {
/* 105 */       ICommand localICommand = (ICommand)this.b.get(str);
/*     */       
/* 107 */       if ((localICommand == null) || (!localICommand.getCommand().equals(str)))
/*     */       {
/*     */ 
/*     */ 
/* 111 */         this.b.put(str, paramICommand);
/*     */       }
/*     */     }
/* 114 */     return paramICommand;
/*     */   }
/*     */   
/*     */   private static String[] a(String[] paramArrayOfString) {
/* 118 */     String[] arrayOfString = new String[paramArrayOfString.length - 1];
/* 119 */     System.arraycopy(paramArrayOfString, 1, arrayOfString, 0, paramArrayOfString.length - 1);
/* 120 */     return arrayOfString;
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> a(ICommandListener paramICommandListener, String paramString, BlockPosition paramBlockPosition)
/*     */   {
/* 126 */     String[] arrayOfString = paramString.split(" ", -1);
/* 127 */     String str = arrayOfString[0];
/*     */     Object localObject;
/* 129 */     if (arrayOfString.length == 1)
/*     */     {
/* 131 */       localObject = Lists.newArrayList();
/*     */       
/* 133 */       for (Map.Entry localEntry : this.b.entrySet()) {
/* 134 */         if ((CommandAbstract.a(str, (String)localEntry.getKey())) && (((ICommand)localEntry.getValue()).canUse(paramICommandListener))) {
/* 135 */           ((List)localObject).add(localEntry.getKey());
/*     */         }
/*     */       }
/*     */       
/* 139 */       return (List<String>)localObject; }
/* 140 */     if (arrayOfString.length > 1)
/*     */     {
/* 142 */       localObject = (ICommand)this.b.get(str);
/*     */       
/* 144 */       if ((localObject != null) && (((ICommand)localObject).canUse(paramICommandListener))) {
/* 145 */         return ((ICommand)localObject).tabComplete(paramICommandListener, a(arrayOfString), paramBlockPosition);
/*     */       }
/*     */     }
/*     */     
/* 149 */     return null;
/*     */   }
/*     */   
/*     */   public List<ICommand> a(ICommandListener paramICommandListener)
/*     */   {
/* 154 */     ArrayList localArrayList = Lists.newArrayList();
/*     */     
/* 156 */     for (ICommand localICommand : this.c) {
/* 157 */       if (localICommand.canUse(paramICommandListener)) {
/* 158 */         localArrayList.add(localICommand);
/*     */       }
/*     */     }
/*     */     
/* 162 */     return localArrayList;
/*     */   }
/*     */   
/*     */   public Map<String, ICommand> getCommands()
/*     */   {
/* 167 */     return this.b;
/*     */   }
/*     */   
/*     */   private int a(ICommand paramICommand, String[] paramArrayOfString) {
/* 171 */     if (paramICommand == null) {
/* 172 */       return -1;
/*     */     }
/*     */     
/* 175 */     for (int i = 0; i < paramArrayOfString.length; i++) {
/* 176 */       if ((paramICommand.isListStart(paramArrayOfString, i)) && (PlayerSelector.isList(paramArrayOfString[i]))) {
/* 177 */         return i;
/*     */       }
/*     */     }
/*     */     
/* 181 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */