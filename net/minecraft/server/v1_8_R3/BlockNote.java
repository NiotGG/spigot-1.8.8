/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockNote
/*    */   extends BlockContainer
/*    */ {
/* 19 */   private static final List<String> a = Lists.newArrayList(new String[] { "harp", "bd", "snare", "hat", "bassattack" });
/*    */   
/*    */   public BlockNote() {
/* 22 */     super(Material.WOOD);
/* 23 */     a(CreativeModeTab.d);
/*    */   }
/*    */   
/*    */   public void doPhysics(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, Block paramBlock)
/*    */   {
/* 28 */     boolean bool = paramWorld.isBlockIndirectlyPowered(paramBlockPosition);
/*    */     
/* 30 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 31 */     if ((localTileEntity instanceof TileEntityNote)) {
/* 32 */       TileEntityNote localTileEntityNote = (TileEntityNote)localTileEntity;
/* 33 */       if (localTileEntityNote.f != bool) {
/* 34 */         if (bool) {
/* 35 */           localTileEntityNote.play(paramWorld, paramBlockPosition);
/*    */         }
/* 37 */         localTileEntityNote.f = bool;
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean interact(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, EntityHuman paramEntityHuman, EnumDirection paramEnumDirection, float paramFloat1, float paramFloat2, float paramFloat3)
/*    */   {
/* 44 */     if (paramWorld.isClientSide) {
/* 45 */       return true;
/*    */     }
/*    */     
/* 48 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 49 */     if ((localTileEntity instanceof TileEntityNote)) {
/* 50 */       TileEntityNote localTileEntityNote = (TileEntityNote)localTileEntity;
/*    */       
/* 52 */       localTileEntityNote.b();
/* 53 */       localTileEntityNote.play(paramWorld, paramBlockPosition);
/* 54 */       paramEntityHuman.b(StatisticList.S);
/*    */     }
/*    */     
/* 57 */     return true;
/*    */   }
/*    */   
/*    */   public void attack(World paramWorld, BlockPosition paramBlockPosition, EntityHuman paramEntityHuman)
/*    */   {
/* 62 */     if (paramWorld.isClientSide) {
/* 63 */       return;
/*    */     }
/*    */     
/* 66 */     TileEntity localTileEntity = paramWorld.getTileEntity(paramBlockPosition);
/* 67 */     if ((localTileEntity instanceof TileEntityNote)) {
/* 68 */       ((TileEntityNote)localTileEntity).play(paramWorld, paramBlockPosition);
/* 69 */       paramEntityHuman.b(StatisticList.R);
/*    */     }
/*    */   }
/*    */   
/*    */   public TileEntity a(World paramWorld, int paramInt)
/*    */   {
/* 75 */     return new TileEntityNote();
/*    */   }
/*    */   
/*    */   private String b(int paramInt) {
/* 79 */     if ((paramInt < 0) || (paramInt >= a.size())) {
/* 80 */       paramInt = 0;
/*    */     }
/*    */     
/* 83 */     return (String)a.get(paramInt);
/*    */   }
/*    */   
/*    */   public boolean a(World paramWorld, BlockPosition paramBlockPosition, IBlockData paramIBlockData, int paramInt1, int paramInt2)
/*    */   {
/* 88 */     float f = (float)Math.pow(2.0D, (paramInt2 - 12) / 12.0D);
/*    */     
/* 90 */     paramWorld.makeSound(paramBlockPosition.getX() + 0.5D, paramBlockPosition.getY() + 0.5D, paramBlockPosition.getZ() + 0.5D, "note." + b(paramInt1), 3.0F, f);
/* 91 */     paramWorld.addParticle(EnumParticle.NOTE, paramBlockPosition.getX() + 0.5D, paramBlockPosition.getY() + 1.2D, paramBlockPosition.getZ() + 0.5D, paramInt2 / 24.0D, 0.0D, 0.0D, new int[0]);
/* 92 */     return true;
/*    */   }
/*    */   
/*    */   public int b()
/*    */   {
/* 97 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockNote.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */