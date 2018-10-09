/*     */ package org.bukkit;
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Statistic
/*     */ {
/*   7 */   DAMAGE_DEALT, 
/*   8 */   DAMAGE_TAKEN, 
/*   9 */   DEATHS, 
/*  10 */   MOB_KILLS, 
/*  11 */   PLAYER_KILLS, 
/*  12 */   FISH_CAUGHT, 
/*  13 */   ANIMALS_BRED, 
/*  14 */   TREASURE_FISHED, 
/*  15 */   JUNK_FISHED, 
/*  16 */   LEAVE_GAME, 
/*  17 */   JUMP, 
/*  18 */   DROP, 
/*  19 */   PLAY_ONE_TICK, 
/*  20 */   WALK_ONE_CM, 
/*  21 */   SWIM_ONE_CM, 
/*  22 */   FALL_ONE_CM, 
/*  23 */   CLIMB_ONE_CM, 
/*  24 */   FLY_ONE_CM, 
/*  25 */   DIVE_ONE_CM, 
/*  26 */   MINECART_ONE_CM, 
/*  27 */   BOAT_ONE_CM, 
/*  28 */   PIG_ONE_CM, 
/*  29 */   HORSE_ONE_CM, 
/*  30 */   SPRINT_ONE_CM, 
/*  31 */   CROUCH_ONE_CM, 
/*  32 */   MINE_BLOCK(Type.BLOCK), 
/*  33 */   USE_ITEM(Type.ITEM), 
/*  34 */   BREAK_ITEM(Type.ITEM), 
/*  35 */   CRAFT_ITEM(Type.ITEM), 
/*  36 */   KILL_ENTITY(Type.ENTITY), 
/*  37 */   ENTITY_KILLED_BY(Type.ENTITY), 
/*  38 */   TIME_SINCE_DEATH, 
/*  39 */   TALKED_TO_VILLAGER, 
/*  40 */   TRADED_WITH_VILLAGER, 
/*  41 */   CAKE_SLICES_EATEN, 
/*  42 */   CAULDRON_FILLED, 
/*  43 */   CAULDRON_USED, 
/*  44 */   ARMOR_CLEANED, 
/*  45 */   BANNER_CLEANED, 
/*  46 */   BREWINGSTAND_INTERACTION, 
/*  47 */   BEACON_INTERACTION, 
/*  48 */   DROPPER_INSPECTED, 
/*  49 */   HOPPER_INSPECTED, 
/*  50 */   DISPENSER_INSPECTED, 
/*  51 */   NOTEBLOCK_PLAYED, 
/*  52 */   NOTEBLOCK_TUNED, 
/*  53 */   FLOWER_POTTED, 
/*  54 */   TRAPPED_CHEST_TRIGGERED, 
/*  55 */   ENDERCHEST_OPENED, 
/*  56 */   ITEM_ENCHANTED, 
/*  57 */   RECORD_PLAYED, 
/*  58 */   FURNACE_INTERACTION, 
/*  59 */   CRAFTING_TABLE_INTERACTION, 
/*  60 */   CHEST_OPENED;
/*     */   
/*     */   private final Type type;
/*     */   
/*     */   private Statistic() {
/*  65 */     this(Type.UNTYPED);
/*     */   }
/*     */   
/*     */   private Statistic(Type type) {
/*  69 */     this.type = type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Type getType()
/*     */   {
/*  78 */     return this.type;
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
/*     */   public boolean isSubstatistic()
/*     */   {
/*  93 */     return this.type != Type.UNTYPED;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isBlock()
/*     */   {
/* 105 */     return this.type == Type.BLOCK;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum Type
/*     */   {
/* 113 */     UNTYPED, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 118 */     ITEM, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 123 */     BLOCK, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 128 */     ENTITY;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Statistic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */