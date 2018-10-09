/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EmptyChunk
/*     */   extends Chunk
/*     */ {
/*     */   public EmptyChunk(World paramWorld, int paramInt1, int paramInt2)
/*     */   {
/*  20 */     super(paramWorld, paramInt1, paramInt2);
/*     */   }
/*     */   
/*     */   public boolean a(int paramInt1, int paramInt2)
/*     */   {
/*  25 */     return (paramInt1 == this.locX) && (paramInt2 == this.locZ);
/*     */   }
/*     */   
/*     */   public int b(int paramInt1, int paramInt2)
/*     */   {
/*  30 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void initLighting() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Block getType(BlockPosition paramBlockPosition)
/*     */   {
/*  43 */     return Blocks.AIR;
/*     */   }
/*     */   
/*     */   public int b(BlockPosition paramBlockPosition)
/*     */   {
/*  48 */     return 255;
/*     */   }
/*     */   
/*     */   public int c(BlockPosition paramBlockPosition)
/*     */   {
/*  53 */     return 0;
/*     */   }
/*     */   
/*     */   public int getBrightness(EnumSkyBlock paramEnumSkyBlock, BlockPosition paramBlockPosition)
/*     */   {
/*  58 */     return paramEnumSkyBlock.c;
/*     */   }
/*     */   
/*     */ 
/*     */   public void a(EnumSkyBlock paramEnumSkyBlock, BlockPosition paramBlockPosition, int paramInt) {}
/*     */   
/*     */ 
/*     */   public int a(BlockPosition paramBlockPosition, int paramInt)
/*     */   {
/*  67 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void a(Entity paramEntity) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void b(Entity paramEntity) {}
/*     */   
/*     */ 
/*     */   public void a(Entity paramEntity, int paramInt) {}
/*     */   
/*     */ 
/*     */   public boolean d(BlockPosition paramBlockPosition)
/*     */   {
/*  84 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public TileEntity a(BlockPosition paramBlockPosition, Chunk.EnumTileEntityState paramEnumTileEntityState)
/*     */   {
/*  90 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void a(TileEntity paramTileEntity) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void a(BlockPosition paramBlockPosition, TileEntity paramTileEntity) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void e(BlockPosition paramBlockPosition) {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void addEntities() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeEntities() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void e() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void a(Entity paramEntity, AxisAlignedBB paramAxisAlignedBB, List<Entity> paramList, Predicate<? super Entity> paramPredicate) {}
/*     */   
/*     */ 
/*     */   public <T extends Entity> void a(Class<? extends T> paramClass, AxisAlignedBB paramAxisAlignedBB, List<T> paramList, Predicate<? super T> paramPredicate) {}
/*     */   
/*     */ 
/*     */   public boolean a(boolean paramBoolean)
/*     */   {
/* 127 */     return false;
/*     */   }
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
/*     */   public Random a(long paramLong)
/*     */   {
/* 153 */     return new Random(getWorld().getSeed() + this.locX * this.locX * 4987142 + this.locX * 5947611 + this.locZ * this.locZ * 4392871L + this.locZ * 389711 ^ paramLong);
/*     */   }
/*     */   
/*     */   public boolean isEmpty()
/*     */   {
/* 158 */     return true;
/*     */   }
/*     */   
/*     */   public boolean c(int paramInt1, int paramInt2)
/*     */   {
/* 163 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EmptyChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */