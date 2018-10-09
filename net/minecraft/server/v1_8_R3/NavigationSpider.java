/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NavigationSpider
/*    */   extends Navigation
/*    */ {
/*    */   private BlockPosition f;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public NavigationSpider(EntityInsentient paramEntityInsentient, World paramWorld)
/*    */   {
/* 24 */     super(paramEntityInsentient, paramWorld);
/*    */   }
/*    */   
/*    */   public PathEntity a(BlockPosition paramBlockPosition)
/*    */   {
/* 29 */     this.f = paramBlockPosition;
/* 30 */     return super.a(paramBlockPosition);
/*    */   }
/*    */   
/*    */   public PathEntity a(Entity paramEntity)
/*    */   {
/* 35 */     this.f = new BlockPosition(paramEntity);
/* 36 */     return super.a(paramEntity);
/*    */   }
/*    */   
/*    */   public boolean a(Entity paramEntity, double paramDouble)
/*    */   {
/* 41 */     PathEntity localPathEntity = a(paramEntity);
/* 42 */     if (localPathEntity != null) {
/* 43 */       return a(localPathEntity, paramDouble);
/*    */     }
/* 45 */     this.f = new BlockPosition(paramEntity);
/* 46 */     this.e = paramDouble;
/* 47 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public void k()
/*    */   {
/* 53 */     if (m()) {
/* 54 */       if (this.f != null) {
/* 55 */         double d = this.b.width * this.b.width;
/*    */         
/* 57 */         if ((this.b.c(this.f) < d) || ((this.b.locY > this.f.getY()) && (this.b.c(new BlockPosition(this.f.getX(), MathHelper.floor(this.b.locY), this.f.getZ())) < d))) {
/* 58 */           this.f = null;
/*    */         } else {
/* 60 */           this.b.getControllerMove().a(this.f.getX(), this.f.getY(), this.f.getZ(), this.e);
/*    */         }
/*    */       }
/* 63 */       return;
/*    */     }
/* 65 */     super.k();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NavigationSpider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */