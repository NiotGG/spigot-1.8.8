/*    */ package org.apache.logging.log4j.core.appender.rolling.helper;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CompositeAction
/*    */   extends AbstractAction
/*    */ {
/*    */   private final Action[] actions;
/*    */   private final boolean stopOnError;
/*    */   
/*    */   public CompositeAction(List<Action> paramList, boolean paramBoolean)
/*    */   {
/* 45 */     this.actions = new Action[paramList.size()];
/* 46 */     paramList.toArray(this.actions);
/* 47 */     this.stopOnError = paramBoolean;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void run()
/*    */   {
/*    */     try
/*    */     {
/* 56 */       execute();
/*    */     } catch (IOException localIOException) {
/* 58 */       LOGGER.warn("Exception during file rollover.", localIOException);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean execute()
/*    */     throws IOException
/*    */   {
/* 70 */     if (this.stopOnError) {
/* 71 */       for (Action localAction1 : this.actions) {
/* 72 */         if (!localAction1.execute()) {
/* 73 */           return false;
/*    */         }
/*    */       }
/*    */       
/* 77 */       return true;
/*    */     }
/* 79 */     boolean bool = true;
/* 80 */     Object localObject = null;
/*    */     
/* 82 */     for (Action localAction2 : this.actions) {
/*    */       try {
/* 84 */         bool &= localAction2.execute();
/*    */       } catch (IOException localIOException) {
/* 86 */         bool = false;
/*    */         
/* 88 */         if (localObject == null) {
/* 89 */           localObject = localIOException;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 94 */     if (localObject != null) {
/* 95 */       throw ((Throwable)localObject);
/*    */     }
/*    */     
/* 98 */     return bool;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\helper\CompositeAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */