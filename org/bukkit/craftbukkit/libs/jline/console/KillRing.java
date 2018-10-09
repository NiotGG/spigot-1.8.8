/*     */ package org.bukkit.craftbukkit.libs.jline.console;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class KillRing
/*     */ {
/*     */   private static final int DEFAULT_SIZE = 60;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String[] slots;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  29 */   private int head = 0;
/*  30 */   private boolean lastKill = false;
/*  31 */   private boolean lastYank = false;
/*     */   
/*     */ 
/*     */ 
/*     */   public KillRing(int size)
/*     */   {
/*  37 */     this.slots = new String[size];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public KillRing()
/*     */   {
/*  44 */     this(60);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void resetLastYank()
/*     */   {
/*  51 */     this.lastYank = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void resetLastKill()
/*     */   {
/*  58 */     this.lastKill = false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean lastYank()
/*     */   {
/*  65 */     return this.lastYank;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void add(String str)
/*     */   {
/*  73 */     this.lastYank = false;
/*     */     
/*  75 */     if ((this.lastKill) && 
/*  76 */       (this.slots[this.head] != null)) {
/*  77 */       int tmp39_36 = this.head; String[] tmp39_32 = this.slots;tmp39_32[tmp39_36] = (tmp39_32[tmp39_36] + str);
/*  78 */       return;
/*     */     }
/*     */     
/*     */ 
/*  82 */     this.lastKill = true;
/*  83 */     next();
/*  84 */     this.slots[this.head] = str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addBackwards(String str)
/*     */   {
/*  95 */     this.lastYank = false;
/*     */     
/*  97 */     if ((this.lastKill) && 
/*  98 */       (this.slots[this.head] != null)) {
/*  99 */       this.slots[this.head] = (str + this.slots[this.head]);
/* 100 */       return;
/*     */     }
/*     */     
/*     */ 
/* 104 */     this.lastKill = true;
/* 105 */     next();
/* 106 */     this.slots[this.head] = str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String yank()
/*     */   {
/* 114 */     this.lastKill = false;
/* 115 */     this.lastYank = true;
/* 116 */     return this.slots[this.head];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String yankPop()
/*     */   {
/* 125 */     this.lastKill = false;
/* 126 */     if (this.lastYank) {
/* 127 */       prev();
/* 128 */       return this.slots[this.head];
/*     */     }
/* 130 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void next()
/*     */   {
/* 138 */     if ((this.head == 0) && (this.slots[0] == null)) {
/* 139 */       return;
/*     */     }
/* 141 */     this.head += 1;
/* 142 */     if (this.head == this.slots.length) {
/* 143 */       this.head = 0;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void prev()
/*     */   {
/* 153 */     this.head -= 1;
/* 154 */     if (this.head == -1) {
/* 155 */       for (int x = this.slots.length - 1; 
/* 156 */           x >= 0; x--) {
/* 157 */         if (this.slots[x] != null) {
/*     */           break;
/*     */         }
/*     */       }
/* 161 */       this.head = x;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\KillRing.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */