/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class WorldGenMineshaft
/*    */   extends StructureGenerator
/*    */ {
/* 10 */   private double d = 0.004D;
/*    */   
/*    */ 
/*    */   public WorldGenMineshaft() {}
/*    */   
/*    */   public String a()
/*    */   {
/* 17 */     return "Mineshaft";
/*    */   }
/*    */   
/*    */   public WorldGenMineshaft(Map<String, String> paramMap) {
/* 21 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 22 */       if (((String)localEntry.getKey()).equals("chance")) {
/* 23 */         this.d = MathHelper.a((String)localEntry.getValue(), this.d);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   protected boolean a(int paramInt1, int paramInt2)
/*    */   {
/* 30 */     return (this.b.nextDouble() < this.d) && (this.b.nextInt(80) < Math.max(Math.abs(paramInt1), Math.abs(paramInt2)));
/*    */   }
/*    */   
/*    */   protected StructureStart b(int paramInt1, int paramInt2)
/*    */   {
/* 35 */     return new WorldGenMineshaftStart(this.c, this.b, paramInt1, paramInt2);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\WorldGenMineshaft.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */