/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
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
/*     */ public class GameRules
/*     */ {
/*  26 */   private TreeMap<String, GameRuleValue> a = new TreeMap();
/*     */   
/*     */   public GameRules() {
/*  29 */     a("doFireTick", "true", EnumGameRuleType.BOOLEAN_VALUE);
/*  30 */     a("mobGriefing", "true", EnumGameRuleType.BOOLEAN_VALUE);
/*  31 */     a("keepInventory", "false", EnumGameRuleType.BOOLEAN_VALUE);
/*  32 */     a("doMobSpawning", "true", EnumGameRuleType.BOOLEAN_VALUE);
/*  33 */     a("doMobLoot", "true", EnumGameRuleType.BOOLEAN_VALUE);
/*  34 */     a("doTileDrops", "true", EnumGameRuleType.BOOLEAN_VALUE);
/*  35 */     a("doEntityDrops", "true", EnumGameRuleType.BOOLEAN_VALUE);
/*  36 */     a("commandBlockOutput", "true", EnumGameRuleType.BOOLEAN_VALUE);
/*  37 */     a("naturalRegeneration", "true", EnumGameRuleType.BOOLEAN_VALUE);
/*  38 */     a("doDaylightCycle", "true", EnumGameRuleType.BOOLEAN_VALUE);
/*  39 */     a("logAdminCommands", "true", EnumGameRuleType.BOOLEAN_VALUE);
/*  40 */     a("showDeathMessages", "true", EnumGameRuleType.BOOLEAN_VALUE);
/*  41 */     a("randomTickSpeed", "3", EnumGameRuleType.NUMERICAL_VALUE);
/*  42 */     a("sendCommandFeedback", "true", EnumGameRuleType.BOOLEAN_VALUE);
/*  43 */     a("reducedDebugInfo", "false", EnumGameRuleType.BOOLEAN_VALUE);
/*     */   }
/*     */   
/*     */   public void a(String paramString1, String paramString2, EnumGameRuleType paramEnumGameRuleType) {
/*  47 */     this.a.put(paramString1, new GameRuleValue(paramString2, paramEnumGameRuleType));
/*     */   }
/*     */   
/*     */   public void set(String paramString1, String paramString2) {
/*  51 */     GameRuleValue localGameRuleValue = (GameRuleValue)this.a.get(paramString1);
/*  52 */     if (localGameRuleValue != null) {
/*  53 */       localGameRuleValue.a(paramString2);
/*     */     } else {
/*  55 */       a(paramString1, paramString2, EnumGameRuleType.ANY_VALUE);
/*     */     }
/*     */   }
/*     */   
/*     */   public String get(String paramString) {
/*  60 */     GameRuleValue localGameRuleValue = (GameRuleValue)this.a.get(paramString);
/*  61 */     if (localGameRuleValue != null) {
/*  62 */       return localGameRuleValue.a();
/*     */     }
/*  64 */     return "";
/*     */   }
/*     */   
/*     */   public boolean getBoolean(String paramString) {
/*  68 */     GameRuleValue localGameRuleValue = (GameRuleValue)this.a.get(paramString);
/*  69 */     if (localGameRuleValue != null) {
/*  70 */       return localGameRuleValue.b();
/*     */     }
/*  72 */     return false;
/*     */   }
/*     */   
/*     */   public int c(String paramString) {
/*  76 */     GameRuleValue localGameRuleValue = (GameRuleValue)this.a.get(paramString);
/*  77 */     if (localGameRuleValue != null) {
/*  78 */       return localGameRuleValue.c();
/*     */     }
/*  80 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NBTTagCompound a()
/*     */   {
/*  92 */     NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/*     */     
/*  94 */     for (String str : this.a.keySet()) {
/*  95 */       GameRuleValue localGameRuleValue = (GameRuleValue)this.a.get(str);
/*  96 */       localNBTTagCompound.setString(str, localGameRuleValue.a());
/*     */     }
/*     */     
/*  99 */     return localNBTTagCompound;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 103 */     Set localSet = paramNBTTagCompound.c();
/* 104 */     for (String str1 : localSet) {
/* 105 */       String str2 = str1;
/* 106 */       String str3 = paramNBTTagCompound.getString(str1);
/*     */       
/* 108 */       set(str2, str3);
/*     */     }
/*     */   }
/*     */   
/*     */   public String[] getGameRules() {
/* 113 */     Set localSet = this.a.keySet();
/* 114 */     return (String[])localSet.toArray(new String[localSet.size()]);
/*     */   }
/*     */   
/*     */   public boolean contains(String paramString) {
/* 118 */     return this.a.containsKey(paramString);
/*     */   }
/*     */   
/*     */   public boolean a(String paramString, EnumGameRuleType paramEnumGameRuleType) {
/* 122 */     GameRuleValue localGameRuleValue = (GameRuleValue)this.a.get(paramString);
/* 123 */     if ((localGameRuleValue != null) && ((localGameRuleValue.e() == paramEnumGameRuleType) || (paramEnumGameRuleType == EnumGameRuleType.ANY_VALUE))) {
/* 124 */       return true;
/*     */     }
/* 126 */     return false;
/*     */   }
/*     */   
/*     */   public static enum EnumGameRuleType { private EnumGameRuleType() {} }
/*     */   
/*     */   static class GameRuleValue { private String a;
/*     */     private boolean b;
/*     */     private int c;
/*     */     private double d;
/*     */     private final GameRules.EnumGameRuleType e;
/*     */     
/* 137 */     public GameRuleValue(String paramString, GameRules.EnumGameRuleType paramEnumGameRuleType) { this.e = paramEnumGameRuleType;
/* 138 */       a(paramString);
/*     */     }
/*     */     
/*     */     public void a(String paramString) {
/* 142 */       this.a = paramString;
/* 143 */       this.b = Boolean.parseBoolean(paramString);
/* 144 */       this.c = (this.b ? 1 : 0);
/*     */       try {
/* 146 */         this.c = Integer.parseInt(paramString);
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException1) {}
/*     */       try {
/* 150 */         this.d = Double.parseDouble(paramString);
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException2) {}
/*     */     }
/*     */     
/*     */     public String a() {
/* 156 */       return this.a;
/*     */     }
/*     */     
/*     */     public boolean b() {
/* 160 */       return this.b;
/*     */     }
/*     */     
/*     */     public int c() {
/* 164 */       return this.c;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public GameRules.EnumGameRuleType e()
/*     */     {
/* 172 */       return this.e;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GameRules.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */