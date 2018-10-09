/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class BlockPressurePlateBinary extends BlockPressurePlateAbstract
/*     */ {
/*  10 */   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
/*     */   private final EnumMobType b;
/*     */   
/*     */   protected BlockPressurePlateBinary(Material material, EnumMobType blockpressureplatebinary_enummobtype) {
/*  14 */     super(material);
/*  15 */     j(this.blockStateList.getBlockData().set(POWERED, Boolean.valueOf(false)));
/*  16 */     this.b = blockpressureplatebinary_enummobtype;
/*     */   }
/*     */   
/*     */   protected int e(IBlockData iblockdata) {
/*  20 */     return ((Boolean)iblockdata.get(POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */   
/*     */   protected IBlockData a(IBlockData iblockdata, int i) {
/*  24 */     return iblockdata.set(POWERED, Boolean.valueOf(i > 0));
/*     */   }
/*     */   
/*     */   protected int f(World world, BlockPosition blockposition) {
/*  28 */     AxisAlignedBB axisalignedbb = getBoundingBox(blockposition);
/*     */     List list;
/*     */     List list;
/*  31 */     switch (SyntheticClass_1.a[this.b.ordinal()]) {
/*     */     case 1: 
/*  33 */       list = world.getEntities(null, axisalignedbb);
/*  34 */       break;
/*     */     
/*     */     case 2: 
/*  37 */       list = world.a(EntityLiving.class, axisalignedbb);
/*  38 */       break;
/*     */     
/*     */     default: 
/*  41 */       return 0;
/*     */     }
/*     */     List list;
/*  44 */     if (!list.isEmpty()) {
/*  45 */       Iterator iterator = list.iterator();
/*     */       
/*  47 */       while (iterator.hasNext()) {
/*  48 */         Entity entity = (Entity)iterator.next();
/*     */         
/*     */ 
/*  51 */         if (e(world.getType(blockposition)) == 0) {
/*  52 */           org.bukkit.World bworld = world.getWorld();
/*  53 */           PluginManager manager = world.getServer().getPluginManager();
/*     */           Cancellable cancellable;
/*     */           Cancellable cancellable;
/*  56 */           if ((entity instanceof EntityHuman)) {
/*  57 */             cancellable = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, org.bukkit.event.block.Action.PHYSICAL, blockposition, null, null);
/*     */           } else {
/*  59 */             cancellable = new org.bukkit.event.entity.EntityInteractEvent(entity.getBukkitEntity(), bworld.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()));
/*  60 */             manager.callEvent((org.bukkit.event.entity.EntityInteractEvent)cancellable);
/*     */           }
/*     */           
/*     */ 
/*  64 */           if (cancellable.isCancelled()) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*  70 */         else if (!entity.aI()) {
/*  71 */           return 15;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  76 */     return 0;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/*  80 */     return getBlockData().set(POWERED, Boolean.valueOf(i == 1));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/*  84 */     return ((Boolean)iblockdata.get(POWERED)).booleanValue() ? 1 : 0;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/*  88 */     return new BlockStateList(this, new IBlockState[] { POWERED });
/*     */   }
/*     */   
/*     */   static class SyntheticClass_1
/*     */   {
/*  93 */     static final int[] a = new int[BlockPressurePlateBinary.EnumMobType.values().length];
/*     */     
/*     */     static {
/*     */       try {
/*  97 */         a[BlockPressurePlateBinary.EnumMobType.EVERYTHING.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 103 */         a[BlockPressurePlateBinary.EnumMobType.MOBS.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum EnumMobType
/*     */   {
/* 113 */     EVERYTHING,  MOBS;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockPressurePlateBinary.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */