/*     */ package org.bukkit.event.entity;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerDeathEvent
/*     */   extends EntityDeathEvent
/*     */ {
/*  12 */   private int newExp = 0;
/*  13 */   private String deathMessage = "";
/*  14 */   private int newLevel = 0;
/*  15 */   private int newTotalExp = 0;
/*  16 */   private boolean keepLevel = false;
/*  17 */   private boolean keepInventory = false;
/*     */   
/*     */   public PlayerDeathEvent(Player player, List<ItemStack> drops, int droppedExp, String deathMessage) {
/*  20 */     this(player, drops, droppedExp, 0, deathMessage);
/*     */   }
/*     */   
/*     */   public PlayerDeathEvent(Player player, List<ItemStack> drops, int droppedExp, int newExp, String deathMessage) {
/*  24 */     this(player, drops, droppedExp, newExp, 0, 0, deathMessage);
/*     */   }
/*     */   
/*     */   public PlayerDeathEvent(Player player, List<ItemStack> drops, int droppedExp, int newExp, int newTotalExp, int newLevel, String deathMessage) {
/*  28 */     super(player, drops, droppedExp);
/*  29 */     this.newExp = newExp;
/*  30 */     this.newTotalExp = newTotalExp;
/*  31 */     this.newLevel = newLevel;
/*  32 */     this.deathMessage = deathMessage;
/*     */   }
/*     */   
/*     */   public Player getEntity()
/*     */   {
/*  37 */     return (Player)this.entity;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDeathMessage(String deathMessage)
/*     */   {
/*  46 */     this.deathMessage = deathMessage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDeathMessage()
/*     */   {
/*  55 */     return this.deathMessage;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getNewExp()
/*     */   {
/*  67 */     return this.newExp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNewExp(int exp)
/*     */   {
/*  79 */     this.newExp = exp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getNewLevel()
/*     */   {
/*  88 */     return this.newLevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNewLevel(int level)
/*     */   {
/*  97 */     this.newLevel = level;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getNewTotalExp()
/*     */   {
/* 106 */     return this.newTotalExp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNewTotalExp(int totalExp)
/*     */   {
/* 115 */     this.newTotalExp = totalExp;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getKeepLevel()
/*     */   {
/* 126 */     return this.keepLevel;
/*     */   }
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
/*     */   public void setKeepLevel(boolean keepLevel)
/*     */   {
/* 141 */     this.keepLevel = keepLevel;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setKeepInventory(boolean keepInventory)
/*     */   {
/* 150 */     this.keepInventory = keepInventory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getKeepInventory()
/*     */   {
/* 159 */     return this.keepInventory;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\PlayerDeathEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */