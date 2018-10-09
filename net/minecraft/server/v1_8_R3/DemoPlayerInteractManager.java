/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DemoPlayerInteractManager
/*     */   extends PlayerInteractManager
/*     */ {
/*     */   private boolean c;
/*     */   
/*     */ 
/*     */   private boolean d;
/*     */   
/*     */ 
/*     */   private int e;
/*     */   
/*     */ 
/*     */   private int f;
/*     */   
/*     */ 
/*     */   public DemoPlayerInteractManager(World paramWorld)
/*     */   {
/*  22 */     super(paramWorld);
/*     */   }
/*     */   
/*     */   public void a()
/*     */   {
/*  27 */     super.a();
/*  28 */     this.f += 1;
/*     */     
/*  30 */     long l1 = this.world.getTime();
/*  31 */     long l2 = l1 / 24000L + 1L;
/*     */     
/*  33 */     if ((!this.c) && (this.f > 20)) {
/*  34 */       this.c = true;
/*  35 */       this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 0.0F));
/*     */     }
/*     */     
/*  38 */     this.d = (l1 > 120500L);
/*  39 */     if (this.d) {
/*  40 */       this.e += 1;
/*     */     }
/*     */     
/*  43 */     if (l1 % 24000L == 500L) {
/*  44 */       if (l2 <= 6L) {
/*  45 */         this.player.sendMessage(new ChatMessage("demo.day." + l2, new Object[0]));
/*     */       }
/*  47 */     } else if (l2 == 1L) {
/*  48 */       if (l1 == 100L) {
/*  49 */         this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 101.0F));
/*  50 */       } else if (l1 == 175L) {
/*  51 */         this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 102.0F));
/*  52 */       } else if (l1 == 250L) {
/*  53 */         this.player.playerConnection.sendPacket(new PacketPlayOutGameStateChange(5, 103.0F));
/*     */       }
/*  55 */     } else if ((l2 == 5L) && 
/*  56 */       (l1 % 24000L == 22000L)) {
/*  57 */       this.player.sendMessage(new ChatMessage("demo.day.warning", new Object[0]));
/*     */     }
/*     */   }
/*     */   
/*     */   private void f()
/*     */   {
/*  63 */     if (this.e > 100) {
/*  64 */       this.player.sendMessage(new ChatMessage("demo.reminder", new Object[0]));
/*  65 */       this.e = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(BlockPosition paramBlockPosition, EnumDirection paramEnumDirection)
/*     */   {
/*  71 */     if (this.d) {
/*  72 */       f();
/*  73 */       return;
/*     */     }
/*  75 */     super.a(paramBlockPosition, paramEnumDirection);
/*     */   }
/*     */   
/*     */   public void a(BlockPosition paramBlockPosition)
/*     */   {
/*  80 */     if (this.d) {
/*  81 */       return;
/*     */     }
/*  83 */     super.a(paramBlockPosition);
/*     */   }
/*     */   
/*     */   public boolean breakBlock(BlockPosition paramBlockPosition)
/*     */   {
/*  88 */     if (this.d) {
/*  89 */       return false;
/*     */     }
/*  91 */     return super.breakBlock(paramBlockPosition);
/*     */   }
/*     */   
/*     */   public boolean useItem(EntityHuman paramEntityHuman, World paramWorld, ItemStack paramItemStack)
/*     */   {
/*  96 */     if (this.d) {
/*  97 */       f();
/*  98 */       return false;
/*     */     }
/* 100 */     return super.useItem(paramEntityHuman, paramWorld, paramItemStack);
/*     */   }
/*     */   
/*     */   public boolean interact(EntityHuman paramEntityHuman, World paramWorld, ItemStack paramItemStack, BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*     */   {
/* 105 */     if (this.d) {
/* 106 */       f();
/* 107 */       return false;
/*     */     }
/* 109 */     return super.interact(paramEntityHuman, paramWorld, paramItemStack, paramBlockPosition, paramEnumDirection, paramFloat1, paramFloat2, paramFloat3);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\DemoPlayerInteractManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */