/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShapeDetectorBlock
/*    */ {
/*    */   private final World a;
/*    */   private final BlockPosition b;
/*    */   private final boolean c;
/*    */   private IBlockData d;
/*    */   private TileEntity e;
/*    */   private boolean f;
/*    */   
/*    */   public ShapeDetectorBlock(World paramWorld, BlockPosition paramBlockPosition, boolean paramBoolean)
/*    */   {
/* 20 */     this.a = paramWorld;
/* 21 */     this.b = paramBlockPosition;
/* 22 */     this.c = paramBoolean;
/*    */   }
/*    */   
/*    */   public IBlockData a() {
/* 26 */     if ((this.d == null) && ((this.c) || (this.a.isLoaded(this.b)))) {
/* 27 */       this.d = this.a.getType(this.b);
/*    */     }
/*    */     
/* 30 */     return this.d;
/*    */   }
/*    */   
/*    */   public TileEntity b()
/*    */   {
/* 35 */     if ((this.e == null) && (!this.f)) {
/* 36 */       this.e = this.a.getTileEntity(this.b);
/* 37 */       this.f = true;
/*    */     }
/*    */     
/* 40 */     return this.e;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public BlockPosition getPosition()
/*    */   {
/* 48 */     return this.b;
/*    */   }
/*    */   
/*    */   public static Predicate<ShapeDetectorBlock> a(Predicate<IBlockData> paramPredicate) {
/* 52 */     new Predicate()
/*    */     {
/*    */       public boolean a(ShapeDetectorBlock paramAnonymousShapeDetectorBlock) {
/* 55 */         return (paramAnonymousShapeDetectorBlock != null) && (this.a.apply(paramAnonymousShapeDetectorBlock.a()));
/*    */       }
/*    */     };
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ShapeDetectorBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */