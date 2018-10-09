/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public enum EnumParticle
/*     */ {
/*     */   private final String Q;
/*     */   private final int R;
/*     */   private final boolean S;
/*     */   private final int T;
/*     */   private static final Map<Integer, EnumParticle> U;
/*     */   private static final String[] V;
/*     */   
/*     */   static
/*     */   {
/*  58 */     U = Maps.newHashMap();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  63 */     ArrayList localArrayList = Lists.newArrayList();
/*  64 */     for (EnumParticle localEnumParticle : values()) {
/*  65 */       U.put(Integer.valueOf(localEnumParticle.c()), localEnumParticle);
/*     */       
/*  67 */       if (!localEnumParticle.b().endsWith("_")) {
/*  68 */         localArrayList.add(localEnumParticle.b());
/*     */       }
/*     */     }
/*     */     
/*  72 */     V = (String[])localArrayList.toArray(new String[localArrayList.size()]);
/*     */   }
/*     */   
/*     */   private EnumParticle(String paramString, int paramInt1, boolean paramBoolean, int paramInt2) {
/*  76 */     this.Q = paramString;
/*  77 */     this.R = paramInt1;
/*  78 */     this.S = paramBoolean;
/*  79 */     this.T = paramInt2;
/*     */   }
/*     */   
/*     */   private EnumParticle(String paramString, int paramInt, boolean paramBoolean) {
/*  83 */     this(paramString, paramInt, paramBoolean, 0);
/*     */   }
/*     */   
/*     */   public static String[] a() {
/*  87 */     return V;
/*     */   }
/*     */   
/*     */   public String b() {
/*  91 */     return this.Q;
/*     */   }
/*     */   
/*     */   public int c() {
/*  95 */     return this.R;
/*     */   }
/*     */   
/*     */   public int d() {
/*  99 */     return this.T;
/*     */   }
/*     */   
/*     */   public boolean e() {
/* 103 */     return this.S;
/*     */   }
/*     */   
/*     */   public boolean f() {
/* 107 */     return this.T > 0;
/*     */   }
/*     */   
/*     */   public static EnumParticle a(int paramInt) {
/* 111 */     return (EnumParticle)U.get(Integer.valueOf(paramInt));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnumParticle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */