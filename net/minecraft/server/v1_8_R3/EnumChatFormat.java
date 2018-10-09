/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public enum EnumChatFormat
/*     */ {
/*     */   private static final Map<String, EnumChatFormat> w;
/*     */   private static final Pattern x;
/*     */   private final String y;
/*     */   private final char z;
/*     */   private final boolean A;
/*     */   private final String B;
/*     */   private final int C;
/*     */   
/*     */   static
/*     */   {
/*  37 */     w = Maps.newHashMap();
/*  38 */     x = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
/*     */     
/*     */ 
/*  41 */     for (EnumChatFormat localEnumChatFormat : values()) {
/*  42 */       w.put(c(localEnumChatFormat.y), localEnumChatFormat);
/*     */     }
/*     */   }
/*     */   
/*     */   private static String c(String paramString) {
/*  47 */     return paramString.toLowerCase().replaceAll("[^a-z]", "");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private EnumChatFormat(String paramString, char paramChar, int paramInt)
/*     */   {
/*  57 */     this(paramString, paramChar, false, paramInt);
/*     */   }
/*     */   
/*     */   private EnumChatFormat(String paramString, char paramChar, boolean paramBoolean) {
/*  61 */     this(paramString, paramChar, paramBoolean, -1);
/*     */   }
/*     */   
/*     */   private EnumChatFormat(String paramString, char paramChar, boolean paramBoolean, int paramInt) {
/*  65 */     this.y = paramString;
/*  66 */     this.z = paramChar;
/*  67 */     this.A = paramBoolean;
/*  68 */     this.C = paramInt;
/*     */     
/*  70 */     this.B = ("§" + paramChar);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int b()
/*     */   {
/*  78 */     return this.C;
/*     */   }
/*     */   
/*     */   public boolean isFormat() {
/*  82 */     return this.A;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  86 */     return (!this.A) && (this != RESET);
/*     */   }
/*     */   
/*     */   public String e() {
/*  90 */     return name().toLowerCase();
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  95 */     return this.B;
/*     */   }
/*     */   
/*     */   public static String a(String paramString)
/*     */   {
/* 100 */     return paramString == null ? null : x.matcher(paramString).replaceAll("");
/*     */   }
/*     */   
/*     */   public static EnumChatFormat b(String paramString)
/*     */   {
/* 105 */     if (paramString == null) {
/* 106 */       return null;
/*     */     }
/* 108 */     return (EnumChatFormat)w.get(c(paramString));
/*     */   }
/*     */   
/*     */   public static EnumChatFormat a(int paramInt)
/*     */   {
/* 113 */     if (paramInt < 0) {
/* 114 */       return RESET;
/*     */     }
/* 116 */     for (EnumChatFormat localEnumChatFormat : values()) {
/* 117 */       if (localEnumChatFormat.b() == paramInt) {
/* 118 */         return localEnumChatFormat;
/*     */       }
/*     */     }
/* 121 */     return null;
/*     */   }
/*     */   
/*     */   public static Collection<String> a(boolean paramBoolean1, boolean paramBoolean2) {
/* 125 */     ArrayList localArrayList = Lists.newArrayList();
/*     */     
/* 127 */     for (EnumChatFormat localEnumChatFormat : values()) {
/* 128 */       if ((!localEnumChatFormat.d()) || (paramBoolean1))
/*     */       {
/*     */ 
/* 131 */         if ((!localEnumChatFormat.isFormat()) || (paramBoolean2))
/*     */         {
/*     */ 
/* 134 */           localArrayList.add(localEnumChatFormat.e()); }
/*     */       }
/*     */     }
/* 137 */     return localArrayList;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnumChatFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */