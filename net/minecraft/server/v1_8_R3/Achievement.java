/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Achievement
/*     */   extends Statistic
/*     */ {
/*     */   public final int a;
/*     */   public final int b;
/*     */   public final Achievement c;
/*     */   private final String k;
/*     */   public final ItemStack d;
/*     */   private boolean m;
/*     */   
/*     */   public Achievement(String paramString1, String paramString2, int paramInt1, int paramInt2, Item paramItem, Achievement paramAchievement)
/*     */   {
/*  20 */     this(paramString1, paramString2, paramInt1, paramInt2, new ItemStack(paramItem), paramAchievement);
/*     */   }
/*     */   
/*     */   public Achievement(String paramString1, String paramString2, int paramInt1, int paramInt2, Block paramBlock, Achievement paramAchievement) {
/*  24 */     this(paramString1, paramString2, paramInt1, paramInt2, new ItemStack(paramBlock), paramAchievement);
/*     */   }
/*     */   
/*     */   public Achievement(String paramString1, String paramString2, int paramInt1, int paramInt2, ItemStack paramItemStack, Achievement paramAchievement) {
/*  28 */     super(paramString1, new ChatMessage("achievement." + paramString2, new Object[0]));
/*  29 */     this.d = paramItemStack;
/*     */     
/*  31 */     this.k = ("achievement." + paramString2 + ".desc");
/*  32 */     this.a = paramInt1;
/*  33 */     this.b = paramInt2;
/*     */     
/*  35 */     if (paramInt1 < AchievementList.a) {
/*  36 */       AchievementList.a = paramInt1;
/*     */     }
/*  38 */     if (paramInt2 < AchievementList.b) {
/*  39 */       AchievementList.b = paramInt2;
/*     */     }
/*  41 */     if (paramInt1 > AchievementList.c) {
/*  42 */       AchievementList.c = paramInt1;
/*     */     }
/*  44 */     if (paramInt2 > AchievementList.d) {
/*  45 */       AchievementList.d = paramInt2;
/*     */     }
/*  47 */     this.c = paramAchievement;
/*     */   }
/*     */   
/*     */   public Achievement a()
/*     */   {
/*  52 */     this.f = true;
/*  53 */     return this;
/*     */   }
/*     */   
/*     */   public Achievement b() {
/*  57 */     this.m = true;
/*  58 */     return this;
/*     */   }
/*     */   
/*     */   public Achievement c()
/*     */   {
/*  63 */     super.h();
/*     */     
/*  65 */     AchievementList.e.add(this);
/*     */     
/*  67 */     return this;
/*     */   }
/*     */   
/*     */   public boolean d()
/*     */   {
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent e()
/*     */   {
/*  77 */     IChatBaseComponent localIChatBaseComponent = super.e();
/*  78 */     localIChatBaseComponent.getChatModifier().setColor(g() ? EnumChatFormat.DARK_PURPLE : EnumChatFormat.GREEN);
/*  79 */     return localIChatBaseComponent;
/*     */   }
/*     */   
/*     */   public Achievement a(Class<? extends IJsonStatistic> paramClass)
/*     */   {
/*  84 */     return (Achievement)super.b(paramClass);
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
/*     */ 
/*     */   public boolean g()
/*     */   {
/* 100 */     return this.m;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Achievement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */