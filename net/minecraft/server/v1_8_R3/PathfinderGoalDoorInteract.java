/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PathfinderGoalDoorInteract
/*    */   extends PathfinderGoal
/*    */ {
/*    */   protected EntityInsentient a;
/*    */   
/*    */ 
/*    */ 
/* 14 */   protected BlockPosition b = BlockPosition.ZERO;
/*    */   protected BlockDoor c;
/*    */   boolean d;
/*    */   float e;
/*    */   float f;
/*    */   
/* 20 */   public PathfinderGoalDoorInteract(EntityInsentient paramEntityInsentient) { this.a = paramEntityInsentient;
/* 21 */     if (!(paramEntityInsentient.getNavigation() instanceof Navigation)) {
/* 22 */       throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean a()
/*    */   {
/* 28 */     if (!this.a.positionChanged) {
/* 29 */       return false;
/*    */     }
/* 31 */     Navigation localNavigation = (Navigation)this.a.getNavigation();
/* 32 */     PathEntity localPathEntity = localNavigation.j();
/* 33 */     if ((localPathEntity == null) || (localPathEntity.b()) || (!localNavigation.g())) {
/* 34 */       return false;
/*    */     }
/*    */     
/* 37 */     for (int i = 0; i < Math.min(localPathEntity.e() + 2, localPathEntity.d()); i++) {
/* 38 */       PathPoint localPathPoint = localPathEntity.a(i);
/* 39 */       this.b = new BlockPosition(localPathPoint.a, localPathPoint.b + 1, localPathPoint.c);
/* 40 */       if (this.a.e(this.b.getX(), this.a.locY, this.b.getZ()) <= 2.25D)
/*    */       {
/*    */ 
/* 43 */         this.c = a(this.b);
/* 44 */         if (this.c != null)
/*    */         {
/*    */ 
/* 47 */           return true; }
/*    */       }
/*    */     }
/* 50 */     this.b = new BlockPosition(this.a).up();
/* 51 */     this.c = a(this.b);
/* 52 */     return this.c != null;
/*    */   }
/*    */   
/*    */   public boolean b()
/*    */   {
/* 57 */     return !this.d;
/*    */   }
/*    */   
/*    */   public void c()
/*    */   {
/* 62 */     this.d = false;
/* 63 */     this.e = ((float)(this.b.getX() + 0.5F - this.a.locX));
/* 64 */     this.f = ((float)(this.b.getZ() + 0.5F - this.a.locZ));
/*    */   }
/*    */   
/*    */   public void e()
/*    */   {
/* 69 */     float f1 = (float)(this.b.getX() + 0.5F - this.a.locX);
/* 70 */     float f2 = (float)(this.b.getZ() + 0.5F - this.a.locZ);
/* 71 */     float f3 = this.e * f1 + this.f * f2;
/* 72 */     if (f3 < 0.0F) {
/* 73 */       this.d = true;
/*    */     }
/*    */   }
/*    */   
/*    */   private BlockDoor a(BlockPosition paramBlockPosition) {
/* 78 */     Block localBlock = this.a.world.getType(paramBlockPosition).getBlock();
/* 79 */     if (((localBlock instanceof BlockDoor)) && (localBlock.getMaterial() == Material.WOOD)) {
/* 80 */       return (BlockDoor)localBlock;
/*    */     }
/* 82 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PathfinderGoalDoorInteract.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */