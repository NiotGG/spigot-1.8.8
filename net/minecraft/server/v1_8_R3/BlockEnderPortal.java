/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import org.bukkit.event.entity.EntityPortalEnterEvent;
/*    */ 
/*    */ public class BlockEnderPortal extends BlockContainer
/*    */ {
/*    */   protected BlockEnderPortal(Material material)
/*    */   {
/* 11 */     super(material);
/* 12 */     a(1.0F);
/*    */   }
/*    */   
/*    */   public TileEntity a(World world, int i) {
/* 16 */     return new TileEntityEnderPortal();
/*    */   }
/*    */   
/*    */   public void updateShape(IBlockAccess iblockaccess, BlockPosition blockposition) {
/* 20 */     float f = 0.0625F;
/*    */     
/* 22 */     a(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
/*    */   }
/*    */   
/*    */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, AxisAlignedBB axisalignedbb, List<AxisAlignedBB> list, Entity entity) {}
/*    */   
/*    */   public boolean c() {
/* 28 */     return false;
/*    */   }
/*    */   
/*    */   public boolean d() {
/* 32 */     return false;
/*    */   }
/*    */   
/*    */   public int a(Random random) {
/* 36 */     return 0;
/*    */   }
/*    */   
/*    */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
/* 40 */     if ((entity.vehicle == null) && (entity.passenger == null) && (!world.isClientSide))
/*    */     {
/* 42 */       EntityPortalEnterEvent event = new EntityPortalEnterEvent(entity.getBukkitEntity(), new org.bukkit.Location(world.getWorld(), blockposition.getX(), blockposition.getY(), blockposition.getZ()));
/* 43 */       world.getServer().getPluginManager().callEvent(event);
/*    */       
/* 45 */       entity.c(1);
/*    */     }
/*    */   }
/*    */   
/*    */   public MaterialMapColor g(IBlockData iblockdata)
/*    */   {
/* 51 */     return MaterialMapColor.E;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockEnderPortal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */