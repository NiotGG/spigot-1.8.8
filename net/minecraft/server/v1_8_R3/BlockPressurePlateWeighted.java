/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class BlockPressurePlateWeighted extends BlockPressurePlateAbstract
/*    */ {
/*  7 */   public static final BlockStateInteger POWER = BlockStateInteger.of("power", 0, 15);
/*    */   private final int weight;
/*    */   
/*    */   protected BlockPressurePlateWeighted(Material material, int i) {
/* 11 */     this(material, i, material.r());
/*    */   }
/*    */   
/*    */   protected BlockPressurePlateWeighted(Material material, int i, MaterialMapColor materialmapcolor) {
/* 15 */     super(material, materialmapcolor);
/* 16 */     j(this.blockStateList.getBlockData().set(POWER, Integer.valueOf(0)));
/* 17 */     this.weight = i;
/*    */   }
/*    */   
/*    */ 
/*    */   protected int f(World world, BlockPosition blockposition)
/*    */   {
/* 23 */     int i = 0;
/* 24 */     Iterator iterator = world.a(Entity.class, getBoundingBox(blockposition)).iterator();
/*    */     
/* 26 */     while (iterator.hasNext()) {
/* 27 */       Entity entity = (Entity)iterator.next();
/*    */       
/*    */       org.bukkit.event.Cancellable cancellable;
/*    */       org.bukkit.event.Cancellable cancellable;
/* 31 */       if ((entity instanceof EntityHuman)) {
/* 32 */         cancellable = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, org.bukkit.event.block.Action.PHYSICAL, blockposition, null, null);
/*    */       } else {
/* 34 */         cancellable = new org.bukkit.event.entity.EntityInteractEvent(entity.getBukkitEntity(), world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()));
/* 35 */         world.getServer().getPluginManager().callEvent((org.bukkit.event.entity.EntityInteractEvent)cancellable);
/*    */       }
/*    */       
/*    */ 
/* 39 */       if (!cancellable.isCancelled()) {
/* 40 */         i++;
/*    */       }
/*    */     }
/*    */     
/* 44 */     i = Math.min(i, this.weight);
/*    */     
/*    */ 
/* 47 */     if (i > 0) {
/* 48 */       float f = Math.min(this.weight, i) / this.weight;
/*    */       
/* 50 */       return MathHelper.f(f * 15.0F);
/*    */     }
/* 52 */     return 0;
/*    */   }
/*    */   
/*    */   protected int e(IBlockData iblockdata)
/*    */   {
/* 57 */     return ((Integer)iblockdata.get(POWER)).intValue();
/*    */   }
/*    */   
/*    */   protected IBlockData a(IBlockData iblockdata, int i) {
/* 61 */     return iblockdata.set(POWER, Integer.valueOf(i));
/*    */   }
/*    */   
/*    */   public int a(World world) {
/* 65 */     return 10;
/*    */   }
/*    */   
/*    */   public IBlockData fromLegacyData(int i) {
/* 69 */     return getBlockData().set(POWER, Integer.valueOf(i));
/*    */   }
/*    */   
/*    */   public int toLegacyData(IBlockData iblockdata) {
/* 73 */     return ((Integer)iblockdata.get(POWER)).intValue();
/*    */   }
/*    */   
/*    */   protected BlockStateList getStateList() {
/* 77 */     return new BlockStateList(this, new IBlockState[] { POWER });
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPressurePlateWeighted.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */