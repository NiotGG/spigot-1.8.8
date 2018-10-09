/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ public class AttributeRanged extends AttributeBase
/*    */ {
/*    */   private final double a;
/*    */   public double b;
/*    */   private String c;
/*    */   
/*    */   public AttributeRanged(IAttribute iattribute, String s, double d0, double d1, double d2) {
/* 10 */     super(iattribute, s, d0);
/* 11 */     this.a = d1;
/* 12 */     this.b = d2;
/* 13 */     if (d1 > d2)
/* 14 */       throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
/* 15 */     if (d0 < d1)
/* 16 */       throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
/* 17 */     if (d0 > d2) {
/* 18 */       throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
/*    */     }
/*    */   }
/*    */   
/*    */   public AttributeRanged a(String s) {
/* 23 */     this.c = s;
/* 24 */     return this;
/*    */   }
/*    */   
/*    */   public String g() {
/* 28 */     return this.c;
/*    */   }
/*    */   
/*    */   public double a(double d0) {
/* 32 */     d0 = MathHelper.a(d0, this.a, this.b);
/* 33 */     return d0;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\AttributeRanged.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */