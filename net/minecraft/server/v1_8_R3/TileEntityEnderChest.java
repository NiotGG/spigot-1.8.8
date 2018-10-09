/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileEntityEnderChest
/*    */   extends TileEntity
/*    */   implements IUpdatePlayerListBox
/*    */ {
/*    */   public float a;
/*    */   public float f;
/*    */   public int g;
/*    */   private int h;
/*    */   
/*    */   public void c()
/*    */   {
/* 18 */     if (++this.h % 20 * 4 == 0) {
/* 19 */       this.world.playBlockAction(this.position, Blocks.ENDER_CHEST, 1, this.g);
/*    */     }
/*    */     
/* 22 */     this.f = this.a;
/*    */     
/* 24 */     int i = this.position.getX();
/* 25 */     int j = this.position.getY();
/* 26 */     int k = this.position.getZ();
/*    */     
/* 28 */     float f1 = 0.1F;
/* 29 */     double d2; if ((this.g > 0) && (this.a == 0.0F)) {
/* 30 */       double d1 = i + 0.5D;
/* 31 */       d2 = k + 0.5D;
/*    */       
/* 33 */       this.world.makeSound(d1, j + 0.5D, d2, "random.chestopen", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
/*    */     }
/* 35 */     if (((this.g == 0) && (this.a > 0.0F)) || ((this.g > 0) && (this.a < 1.0F))) {
/* 36 */       float f2 = this.a;
/* 37 */       if (this.g > 0) {
/* 38 */         this.a += f1;
/*    */       } else {
/* 40 */         this.a -= f1;
/*    */       }
/* 42 */       if (this.a > 1.0F) {
/* 43 */         this.a = 1.0F;
/*    */       }
/* 45 */       float f3 = 0.5F;
/* 46 */       if ((this.a < f3) && (f2 >= f3)) {
/* 47 */         d2 = i + 0.5D;
/* 48 */         double d3 = k + 0.5D;
/*    */         
/* 50 */         this.world.makeSound(d2, j + 0.5D, d3, "random.chestclosed", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
/*    */       }
/* 52 */       if (this.a < 0.0F) {
/* 53 */         this.a = 0.0F;
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean c(int paramInt1, int paramInt2)
/*    */   {
/* 60 */     if (paramInt1 == 1) {
/* 61 */       this.g = paramInt2;
/* 62 */       return true;
/*    */     }
/* 64 */     return super.c(paramInt1, paramInt2);
/*    */   }
/*    */   
/*    */   public void y()
/*    */   {
/* 69 */     E();
/* 70 */     super.y();
/*    */   }
/*    */   
/*    */   public void b() {
/* 74 */     this.g += 1;
/* 75 */     this.world.playBlockAction(this.position, Blocks.ENDER_CHEST, 1, this.g);
/*    */   }
/*    */   
/*    */   public void d() {
/* 79 */     this.g -= 1;
/* 80 */     this.world.playBlockAction(this.position, Blocks.ENDER_CHEST, 1, this.g);
/*    */   }
/*    */   
/*    */   public boolean a(EntityHuman paramEntityHuman) {
/* 84 */     if (this.world.getTileEntity(this.position) != this) {
/* 85 */       return false;
/*    */     }
/* 87 */     if (paramEntityHuman.e(this.position.getX() + 0.5D, this.position.getY() + 0.5D, this.position.getZ() + 0.5D) > 64.0D) {
/* 88 */       return false;
/*    */     }
/*    */     
/* 91 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityEnderChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */