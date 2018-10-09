/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import org.bukkit.event.entity.SheepDyeWoolEvent;
/*     */ 
/*     */ public class ItemDye extends Item
/*     */ {
/*   7 */   public static final int[] a = { 1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320 };
/*     */   
/*     */   public ItemDye() {
/*  10 */     a(true);
/*  11 */     setMaxDurability(0);
/*  12 */     a(CreativeModeTab.l);
/*     */   }
/*     */   
/*     */   public String e_(ItemStack itemstack) {
/*  16 */     int i = itemstack.getData();
/*     */     
/*  18 */     return super.getName() + "." + EnumColor.fromInvColorIndex(i).d();
/*     */   }
/*     */   
/*     */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2) {
/*  22 */     if (!entityhuman.a(blockposition.shift(enumdirection), enumdirection, itemstack)) {
/*  23 */       return false;
/*     */     }
/*  25 */     EnumColor enumcolor = EnumColor.fromInvColorIndex(itemstack.getData());
/*     */     
/*  27 */     if (enumcolor == EnumColor.WHITE) {
/*  28 */       if (a(itemstack, world, blockposition)) {
/*  29 */         if (!world.isClientSide) {
/*  30 */           world.triggerEffect(2005, blockposition, 0);
/*     */         }
/*     */         
/*  33 */         return true;
/*     */       }
/*  35 */     } else if (enumcolor == EnumColor.BROWN) {
/*  36 */       IBlockData iblockdata = world.getType(blockposition);
/*  37 */       Block block = iblockdata.getBlock();
/*     */       
/*  39 */       if ((block == Blocks.LOG) && (iblockdata.get(BlockWood.VARIANT) == BlockWood.EnumLogVariant.JUNGLE)) {
/*  40 */         if (enumdirection == EnumDirection.DOWN) {
/*  41 */           return false;
/*     */         }
/*     */         
/*  44 */         if (enumdirection == EnumDirection.UP) {
/*  45 */           return false;
/*     */         }
/*     */         
/*  48 */         blockposition = blockposition.shift(enumdirection);
/*  49 */         if (world.isEmpty(blockposition)) {
/*  50 */           IBlockData iblockdata1 = Blocks.COCOA.getPlacedState(world, blockposition, enumdirection, f, f1, f2, 0, entityhuman);
/*     */           
/*  52 */           world.setTypeAndData(blockposition, iblockdata1, 2);
/*  53 */           if (!entityhuman.abilities.canInstantlyBuild) {
/*  54 */             itemstack.count -= 1;
/*     */           }
/*     */         }
/*     */         
/*  58 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  62 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean a(ItemStack itemstack, World world, BlockPosition blockposition)
/*     */   {
/*  67 */     IBlockData iblockdata = world.getType(blockposition);
/*     */     
/*  69 */     if ((iblockdata.getBlock() instanceof IBlockFragilePlantElement)) {
/*  70 */       IBlockFragilePlantElement iblockfragileplantelement = (IBlockFragilePlantElement)iblockdata.getBlock();
/*     */       
/*  72 */       if (iblockfragileplantelement.a(world, blockposition, iblockdata, world.isClientSide)) {
/*  73 */         if (!world.isClientSide) {
/*  74 */           if (iblockfragileplantelement.a(world, world.random, blockposition, iblockdata)) {
/*  75 */             iblockfragileplantelement.b(world, world.random, blockposition, iblockdata);
/*     */           }
/*     */           
/*  78 */           itemstack.count -= 1;
/*     */         }
/*     */         
/*  81 */         return true;
/*     */       }
/*     */     }
/*     */     
/*  85 */     return false;
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack itemstack, EntityHuman entityhuman, EntityLiving entityliving) {
/*  89 */     if ((entityliving instanceof EntitySheep)) {
/*  90 */       EntitySheep entitysheep = (EntitySheep)entityliving;
/*  91 */       EnumColor enumcolor = EnumColor.fromInvColorIndex(itemstack.getData());
/*     */       
/*  93 */       if ((!entitysheep.isSheared()) && (entitysheep.getColor() != enumcolor))
/*     */       {
/*  95 */         byte bColor = (byte)enumcolor.getColorIndex();
/*  96 */         SheepDyeWoolEvent event = new SheepDyeWoolEvent((org.bukkit.entity.Sheep)entitysheep.getBukkitEntity(), org.bukkit.DyeColor.getByData(bColor));
/*  97 */         entitysheep.world.getServer().getPluginManager().callEvent(event);
/*     */         
/*  99 */         if (event.isCancelled()) {
/* 100 */           return false;
/*     */         }
/*     */         
/* 103 */         enumcolor = EnumColor.fromColorIndex(event.getColor().getWoolData());
/*     */         
/* 105 */         entitysheep.setColor(enumcolor);
/* 106 */         itemstack.count -= 1;
/*     */       }
/*     */       
/* 109 */       return true;
/*     */     }
/* 111 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemDye.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */