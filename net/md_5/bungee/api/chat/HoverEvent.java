/*    */ package net.md_5.bungee.api.chat;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public final class HoverEvent { private final Action action;
/*    */   private final BaseComponent[] value;
/*    */   
/*  8 */   public String toString() { return "HoverEvent(action=" + getAction() + ", value=" + Arrays.deepToString(getValue()) + ")"; } @java.beans.ConstructorProperties({"action", "value"})
/*  9 */   public HoverEvent(Action action, BaseComponent[] value) { this.action = action;this.value = value;
/*    */   }
/*    */   
/*    */ 
/* 13 */   public Action getAction() { return this.action; }
/* 14 */   public BaseComponent[] getValue() { return this.value; }
/*    */   
/*    */ 
/*    */   public static enum Action
/*    */   {
/* 19 */     SHOW_TEXT, 
/* 20 */     SHOW_ACHIEVEMENT, 
/* 21 */     SHOW_ITEM;
/*    */     
/*    */     private Action() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\md_5\bungee\api\chat\HoverEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */