/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatComponentUtils
/*    */ {
/*    */   public static IChatBaseComponent filterForDisplay(ICommandListener paramICommandListener, IChatBaseComponent paramIChatBaseComponent, Entity paramEntity)
/*    */     throws CommandException
/*    */   {
/* 15 */     Object localObject1 = null;
/* 16 */     Object localObject3; if ((paramIChatBaseComponent instanceof ChatComponentScore)) {
/* 17 */       localObject2 = (ChatComponentScore)paramIChatBaseComponent;
/* 18 */       String str = ((ChatComponentScore)localObject2).g();
/* 19 */       if (PlayerSelector.isPattern(str)) {
/* 20 */         localObject3 = PlayerSelector.getPlayers(paramICommandListener, str, Entity.class);
/* 21 */         if (((List)localObject3).size() == 1) {
/* 22 */           str = ((Entity)((List)localObject3).get(0)).getName();
/*    */         } else {
/* 24 */           throw new ExceptionEntityNotFound();
/*    */         }
/*    */       }
/* 27 */       localObject1 = (paramEntity != null) && (str.equals("*")) ? new ChatComponentScore(paramEntity.getName(), ((ChatComponentScore)localObject2).h()) : new ChatComponentScore(str, ((ChatComponentScore)localObject2).h());
/*    */       
/*    */ 
/*    */ 
/* 31 */       ((ChatComponentScore)localObject1).b(((ChatComponentScore)localObject2).getText());
/* 32 */     } else if ((paramIChatBaseComponent instanceof ChatComponentSelector)) {
/* 33 */       localObject2 = ((ChatComponentSelector)paramIChatBaseComponent).g();
/* 34 */       localObject1 = PlayerSelector.getPlayerNames(paramICommandListener, (String)localObject2);
/* 35 */       if (localObject1 == null) {
/* 36 */         localObject1 = new ChatComponentText("");
/*    */       }
/* 38 */     } else if ((paramIChatBaseComponent instanceof ChatComponentText)) {
/* 39 */       localObject1 = new ChatComponentText(((ChatComponentText)paramIChatBaseComponent).g());
/* 40 */     } else if ((paramIChatBaseComponent instanceof ChatMessage)) {
/* 41 */       localObject2 = ((ChatMessage)paramIChatBaseComponent).j();
/* 42 */       for (int i = 0; i < localObject2.length; i++) {
/* 43 */         localObject3 = localObject2[i];
/* 44 */         if ((localObject3 instanceof IChatBaseComponent)) {
/* 45 */           localObject2[i] = filterForDisplay(paramICommandListener, (IChatBaseComponent)localObject3, paramEntity);
/*    */         }
/*    */       }
/* 48 */       localObject1 = new ChatMessage(((ChatMessage)paramIChatBaseComponent).i(), (Object[])localObject2);
/*    */     } else {
/* 50 */       return paramIChatBaseComponent;
/*    */     }
/* 52 */     Object localObject2 = paramIChatBaseComponent.getChatModifier();
/* 53 */     if (localObject2 != null) {
/* 54 */       ((IChatBaseComponent)localObject1).setChatModifier(((ChatModifier)localObject2).clone());
/*    */     }
/* 56 */     for (Iterator localIterator = paramIChatBaseComponent.a().iterator(); localIterator.hasNext();) { localObject3 = (IChatBaseComponent)localIterator.next();
/* 57 */       ((IChatBaseComponent)localObject1).addSibling(filterForDisplay(paramICommandListener, (IChatBaseComponent)localObject3, paramEntity));
/*    */     }
/* 59 */     return (IChatBaseComponent)localObject1;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChatComponentUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */