/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*   5 */ public class BlockJukeBox extends BlockContainer { public static final BlockStateBoolean HAS_RECORD = BlockStateBoolean.of("has_record");
/*     */   
/*     */   protected BlockJukeBox() {
/*   8 */     super(Material.WOOD, MaterialMapColor.l);
/*   9 */     j(this.blockStateList.getBlockData().set(HAS_RECORD, Boolean.valueOf(false)));
/*  10 */     a(CreativeModeTab.c);
/*     */   }
/*     */   
/*     */   public boolean interact(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman, EnumDirection enumdirection, float f, float f1, float f2) {
/*  14 */     if (((Boolean)iblockdata.get(HAS_RECORD)).booleanValue()) {
/*  15 */       dropRecord(world, blockposition, iblockdata);
/*  16 */       iblockdata = iblockdata.set(HAS_RECORD, Boolean.valueOf(false));
/*  17 */       world.setTypeAndData(blockposition, iblockdata, 2);
/*  18 */       return true;
/*     */     }
/*  20 */     return false;
/*     */   }
/*     */   
/*     */   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, ItemStack itemstack)
/*     */   {
/*  25 */     if (!world.isClientSide) {
/*  26 */       TileEntity tileentity = world.getTileEntity(blockposition);
/*     */       
/*  28 */       if ((tileentity instanceof TileEntityRecordPlayer)) {
/*  29 */         ((TileEntityRecordPlayer)tileentity).setRecord(new ItemStack(itemstack.getItem(), 1, itemstack.getData()));
/*  30 */         world.setTypeAndData(blockposition, iblockdata.set(HAS_RECORD, Boolean.valueOf(true)), 2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void dropRecord(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  36 */     if (!world.isClientSide) {
/*  37 */       TileEntity tileentity = world.getTileEntity(blockposition);
/*     */       
/*  39 */       if ((tileentity instanceof TileEntityRecordPlayer)) {
/*  40 */         TileEntityRecordPlayer blockjukebox_tileentityrecordplayer = (TileEntityRecordPlayer)tileentity;
/*  41 */         ItemStack itemstack = blockjukebox_tileentityrecordplayer.getRecord();
/*     */         
/*  43 */         if (itemstack != null) {
/*  44 */           world.triggerEffect(1005, blockposition, 0);
/*  45 */           world.a(blockposition, null);
/*  46 */           blockjukebox_tileentityrecordplayer.setRecord(null);
/*  47 */           float f = 0.7F;
/*  48 */           double d0 = world.random.nextFloat() * f + (1.0F - f) * 0.5D;
/*  49 */           double d1 = world.random.nextFloat() * f + (1.0F - f) * 0.2D + 0.6D;
/*  50 */           double d2 = world.random.nextFloat() * f + (1.0F - f) * 0.5D;
/*  51 */           ItemStack itemstack1 = itemstack.cloneItemStack();
/*  52 */           EntityItem entityitem = new EntityItem(world, blockposition.getX() + d0, blockposition.getY() + d1, blockposition.getZ() + d2, itemstack1);
/*     */           
/*  54 */           entityitem.p();
/*  55 */           world.addEntity(entityitem);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(World world, BlockPosition blockposition, IBlockData iblockdata) {
/*  62 */     dropRecord(world, blockposition, iblockdata);
/*  63 */     super.remove(world, blockposition, iblockdata);
/*     */   }
/*     */   
/*     */   public void dropNaturally(World world, BlockPosition blockposition, IBlockData iblockdata, float f, int i) {
/*  67 */     if (!world.isClientSide) {
/*  68 */       super.dropNaturally(world, blockposition, iblockdata, f, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   public TileEntity a(World world, int i) {
/*  73 */     return new TileEntityRecordPlayer();
/*     */   }
/*     */   
/*     */   public boolean isComplexRedstone() {
/*  77 */     return true;
/*     */   }
/*     */   
/*     */   public int l(World world, BlockPosition blockposition) {
/*  81 */     TileEntity tileentity = world.getTileEntity(blockposition);
/*     */     
/*  83 */     if ((tileentity instanceof TileEntityRecordPlayer)) {
/*  84 */       ItemStack itemstack = ((TileEntityRecordPlayer)tileentity).getRecord();
/*     */       
/*  86 */       if (itemstack != null) {
/*  87 */         return Item.getId(itemstack.getItem()) + 1 - Item.getId(Items.RECORD_13);
/*     */       }
/*     */     }
/*     */     
/*  91 */     return 0;
/*     */   }
/*     */   
/*     */   public int b() {
/*  95 */     return 3;
/*     */   }
/*     */   
/*     */   public IBlockData fromLegacyData(int i) {
/*  99 */     return getBlockData().set(HAS_RECORD, Boolean.valueOf(i > 0));
/*     */   }
/*     */   
/*     */   public int toLegacyData(IBlockData iblockdata) {
/* 103 */     return ((Boolean)iblockdata.get(HAS_RECORD)).booleanValue() ? 1 : 0;
/*     */   }
/*     */   
/*     */   protected BlockStateList getStateList() {
/* 107 */     return new BlockStateList(this, new IBlockState[] { HAS_RECORD });
/*     */   }
/*     */   
/*     */   public static class TileEntityRecordPlayer
/*     */     extends TileEntity
/*     */   {
/*     */     private ItemStack record;
/*     */     
/*     */     public void a(NBTTagCompound nbttagcompound)
/*     */     {
/* 117 */       super.a(nbttagcompound);
/* 118 */       if (nbttagcompound.hasKeyOfType("RecordItem", 10)) {
/* 119 */         setRecord(ItemStack.createStack(nbttagcompound.getCompound("RecordItem")));
/* 120 */       } else if (nbttagcompound.getInt("Record") > 0) {
/* 121 */         setRecord(new ItemStack(Item.getById(nbttagcompound.getInt("Record")), 1, 0));
/*     */       }
/*     */     }
/*     */     
/*     */     public void b(NBTTagCompound nbttagcompound)
/*     */     {
/* 127 */       super.b(nbttagcompound);
/* 128 */       if (getRecord() != null) {
/* 129 */         nbttagcompound.set("RecordItem", getRecord().save(new NBTTagCompound()));
/*     */       }
/*     */     }
/*     */     
/*     */     public ItemStack getRecord()
/*     */     {
/* 135 */       return this.record;
/*     */     }
/*     */     
/*     */     public void setRecord(ItemStack itemstack)
/*     */     {
/* 140 */       if (itemstack != null) {
/* 141 */         itemstack.count = 1;
/*     */       }
/*     */       
/* 144 */       this.record = itemstack;
/* 145 */       update();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockJukeBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */