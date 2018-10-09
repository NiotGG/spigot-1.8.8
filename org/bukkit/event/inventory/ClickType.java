/*     */ package org.bukkit.event.inventory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ClickType
/*     */ {
/*   8 */   LEFT, 
/*     */   
/*     */ 
/*     */ 
/*  12 */   SHIFT_LEFT, 
/*     */   
/*     */ 
/*     */ 
/*  16 */   RIGHT, 
/*     */   
/*     */ 
/*     */ 
/*  20 */   SHIFT_RIGHT, 
/*     */   
/*     */ 
/*     */ 
/*  24 */   WINDOW_BORDER_LEFT, 
/*     */   
/*     */ 
/*     */ 
/*  28 */   WINDOW_BORDER_RIGHT, 
/*     */   
/*     */ 
/*     */ 
/*  32 */   MIDDLE, 
/*     */   
/*     */ 
/*     */ 
/*  36 */   NUMBER_KEY, 
/*     */   
/*     */ 
/*     */ 
/*  40 */   DOUBLE_CLICK, 
/*     */   
/*     */ 
/*     */ 
/*  44 */   DROP, 
/*     */   
/*     */ 
/*     */ 
/*  48 */   CONTROL_DROP, 
/*     */   
/*     */ 
/*     */ 
/*  52 */   CREATIVE, 
/*     */   
/*     */ 
/*     */ 
/*  56 */   UNKNOWN;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isKeyboardClick()
/*     */   {
/*  74 */     return (this == NUMBER_KEY) || (this == DROP) || (this == CONTROL_DROP);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCreativeAction()
/*     */   {
/*  85 */     return (this == MIDDLE) || (this == CREATIVE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isRightClick()
/*     */   {
/*  94 */     return (this == RIGHT) || (this == SHIFT_RIGHT);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isLeftClick()
/*     */   {
/* 103 */     return (this == LEFT) || (this == SHIFT_LEFT) || (this == DOUBLE_CLICK) || (this == CREATIVE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isShiftClick()
/*     */   {
/* 113 */     return (this == SHIFT_LEFT) || (this == SHIFT_RIGHT) || (this == CONTROL_DROP);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\inventory\ClickType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */