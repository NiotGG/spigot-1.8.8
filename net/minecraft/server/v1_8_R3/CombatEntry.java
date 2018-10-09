/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ public class CombatEntry
/*    */ {
/*    */   private final DamageSource a;
/*    */   private final int b;
/*    */   private final float c;
/*    */   private final float d;
/*    */   private final String e;
/*    */   private final float f;
/*    */   
/*    */   public CombatEntry(DamageSource paramDamageSource, int paramInt, float paramFloat1, float paramFloat2, String paramString, float paramFloat3)
/*    */   {
/* 15 */     this.a = paramDamageSource;
/* 16 */     this.b = paramInt;
/* 17 */     this.c = paramFloat2;
/* 18 */     this.d = paramFloat1;
/* 19 */     this.e = paramString;
/* 20 */     this.f = paramFloat3;
/*    */   }
/*    */   
/*    */   public DamageSource a() {
/* 24 */     return this.a;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public float c()
/*    */   {
/* 32 */     return this.c;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean f()
/*    */   {
/* 44 */     return this.a.getEntity() instanceof EntityLiving;
/*    */   }
/*    */   
/*    */   public String g() {
/* 48 */     return this.e;
/*    */   }
/*    */   
/*    */   public IChatBaseComponent h() {
/* 52 */     return a().getEntity() == null ? null : a().getEntity().getScoreboardDisplayName();
/*    */   }
/*    */   
/*    */   public float i() {
/* 56 */     if (this.a == DamageSource.OUT_OF_WORLD) {
/* 57 */       return Float.MAX_VALUE;
/*    */     }
/* 59 */     return this.f;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CombatEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */