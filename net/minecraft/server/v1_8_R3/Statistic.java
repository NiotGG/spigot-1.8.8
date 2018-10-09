/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Statistic
/*     */ {
/*     */   public final String name;
/*     */   private final IChatBaseComponent a;
/*     */   public boolean f;
/*     */   private final Counter b;
/*     */   private final IScoreboardCriteria c;
/*     */   private Class<? extends IJsonStatistic> d;
/*     */   
/*     */   public Statistic(String paramString, IChatBaseComponent paramIChatBaseComponent, Counter paramCounter)
/*     */   {
/*  23 */     this.name = paramString;
/*  24 */     this.a = paramIChatBaseComponent;
/*  25 */     this.b = paramCounter;
/*  26 */     this.c = new ScoreboardStatisticCriteria(this);
/*     */     
/*  28 */     IScoreboardCriteria.criteria.put(this.c.getName(), this.c);
/*     */   }
/*     */   
/*     */   public Statistic(String paramString, IChatBaseComponent paramIChatBaseComponent) {
/*  32 */     this(paramString, paramIChatBaseComponent, g);
/*     */   }
/*     */   
/*     */   public Statistic i() {
/*  36 */     this.f = true;
/*  37 */     return this;
/*     */   }
/*     */   
/*     */   public Statistic h() {
/*  41 */     if (StatisticList.a.containsKey(this.name)) {
/*  42 */       throw new RuntimeException("Duplicate stat id: \"" + ((Statistic)StatisticList.a.get(this.name)).a + "\" and \"" + this.a + "\" at id " + this.name);
/*     */     }
/*  44 */     StatisticList.stats.add(this);
/*  45 */     StatisticList.a.put(this.name, this);
/*     */     
/*  47 */     return this;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  51 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  58 */   private static NumberFormat k = NumberFormat.getIntegerInstance(Locale.US);
/*  59 */   public static Counter g = new Counter() {};
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  66 */   private static DecimalFormat l = new DecimalFormat("########0.00");
/*  67 */   public static Counter h = new Counter() {};
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  89 */   public static Counter i = new Counter() {};
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
/* 105 */   public static Counter j = new Counter() {};
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IChatBaseComponent e()
/*     */   {
/* 113 */     IChatBaseComponent localIChatBaseComponent = this.a.f();
/* 114 */     localIChatBaseComponent.getChatModifier().setColor(EnumChatFormat.GRAY);
/* 115 */     localIChatBaseComponent.getChatModifier().setChatHoverable(new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_ACHIEVEMENT, new ChatComponentText(this.name)));
/* 116 */     return localIChatBaseComponent;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent j() {
/* 120 */     IChatBaseComponent localIChatBaseComponent1 = e();
/* 121 */     IChatBaseComponent localIChatBaseComponent2 = new ChatComponentText("[").addSibling(localIChatBaseComponent1).a("]");
/* 122 */     localIChatBaseComponent2.setChatModifier(localIChatBaseComponent1.getChatModifier());
/* 123 */     return localIChatBaseComponent2;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 128 */     if (this == paramObject) {
/* 129 */       return true;
/*     */     }
/* 131 */     if ((paramObject == null) || (getClass() != paramObject.getClass())) {
/* 132 */       return false;
/*     */     }
/*     */     
/* 135 */     Statistic localStatistic = (Statistic)paramObject;
/*     */     
/* 137 */     return this.name.equals(localStatistic.name);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 142 */     return this.name.hashCode();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 147 */     return "Stat{id=" + this.name + ", nameId=" + this.a + ", awardLocallyOnly=" + this.f + ", formatter=" + this.b + ", objectiveCriteria=" + this.c + '}';
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public IScoreboardCriteria k()
/*     */   {
/* 157 */     return this.c;
/*     */   }
/*     */   
/*     */   public Class<? extends IJsonStatistic> l() {
/* 161 */     return this.d;
/*     */   }
/*     */   
/*     */   public Statistic b(Class<? extends IJsonStatistic> paramClass) {
/* 165 */     this.d = paramClass;
/* 166 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Statistic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */