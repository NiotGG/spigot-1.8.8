/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ 
/*     */ public class ItemSkull extends Item
/*     */ {
/*   8 */   private static final String[] a = { "skeleton", "wither", "zombie", "char", "creeper" };
/*     */   
/*     */   public ItemSkull() {
/*  11 */     a(CreativeModeTab.c);
/*  12 */     setMaxDurability(0);
/*  13 */     a(true);
/*     */   }
/*     */   
/*     */   public boolean interactWith(ItemStack itemstack, EntityHuman entityhuman, World world, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2) {
/*  17 */     if (enumdirection == EnumDirection.DOWN) {
/*  18 */       return false;
/*     */     }
/*  20 */     IBlockData iblockdata = world.getType(blockposition);
/*  21 */     Block block = iblockdata.getBlock();
/*  22 */     boolean flag = block.a(world, blockposition);
/*     */     
/*  24 */     if (!flag) {
/*  25 */       if (!world.getType(blockposition).getBlock().getMaterial().isBuildable()) {
/*  26 */         return false;
/*     */       }
/*     */       
/*  29 */       blockposition = blockposition.shift(enumdirection);
/*     */     }
/*     */     
/*  32 */     if (!entityhuman.a(blockposition, enumdirection, itemstack))
/*  33 */       return false;
/*  34 */     if (!Blocks.SKULL.canPlace(world, blockposition)) {
/*  35 */       return false;
/*     */     }
/*  37 */     if (!world.isClientSide)
/*     */     {
/*  39 */       if (!Blocks.SKULL.canPlace(world, blockposition))
/*     */       {
/*  41 */         return false;
/*     */       }
/*     */       
/*  44 */       world.setTypeAndData(blockposition, Blocks.SKULL.getBlockData().set(BlockSkull.FACING, enumdirection), 3);
/*  45 */       int i = 0;
/*     */       
/*  47 */       if (enumdirection == EnumDirection.UP) {
/*  48 */         i = MathHelper.floor(entityhuman.yaw * 16.0F / 360.0F + 0.5D) & 0xF;
/*     */       }
/*     */       
/*  51 */       TileEntity tileentity = world.getTileEntity(blockposition);
/*     */       
/*  53 */       if ((tileentity instanceof TileEntitySkull)) {
/*  54 */         TileEntitySkull tileentityskull = (TileEntitySkull)tileentity;
/*     */         
/*  56 */         if (itemstack.getData() == 3) {
/*  57 */           GameProfile gameprofile = null;
/*     */           
/*  59 */           if (itemstack.hasTag()) {
/*  60 */             NBTTagCompound nbttagcompound = itemstack.getTag();
/*     */             
/*  62 */             if (nbttagcompound.hasKeyOfType("SkullOwner", 10)) {
/*  63 */               gameprofile = GameProfileSerializer.deserialize(nbttagcompound.getCompound("SkullOwner"));
/*  64 */             } else if ((nbttagcompound.hasKeyOfType("SkullOwner", 8)) && (nbttagcompound.getString("SkullOwner").length() > 0)) {
/*  65 */               gameprofile = new GameProfile(null, nbttagcompound.getString("SkullOwner"));
/*     */             }
/*     */           }
/*     */           
/*  69 */           tileentityskull.setGameProfile(gameprofile);
/*     */         } else {
/*  71 */           tileentityskull.setSkullType(itemstack.getData());
/*     */         }
/*     */         
/*  74 */         tileentityskull.setRotation(i);
/*  75 */         Blocks.SKULL.a(world, blockposition, tileentityskull);
/*     */       }
/*     */       
/*  78 */       itemstack.count -= 1;
/*     */     }
/*     */     
/*  81 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public int filterData(int i)
/*     */   {
/*  87 */     return i;
/*     */   }
/*     */   
/*     */   public String e_(ItemStack itemstack) {
/*  91 */     int i = itemstack.getData();
/*     */     
/*  93 */     if ((i < 0) || (i >= a.length)) {
/*  94 */       i = 0;
/*     */     }
/*     */     
/*  97 */     return super.getName() + "." + a[i];
/*     */   }
/*     */   
/*     */   public String a(ItemStack itemstack) {
/* 101 */     if ((itemstack.getData() == 3) && (itemstack.hasTag())) {
/* 102 */       if (itemstack.getTag().hasKeyOfType("SkullOwner", 8)) {
/* 103 */         return LocaleI18n.a("item.skull.player.name", new Object[] { itemstack.getTag().getString("SkullOwner") });
/*     */       }
/*     */       
/* 106 */       if (itemstack.getTag().hasKeyOfType("SkullOwner", 10)) {
/* 107 */         NBTTagCompound nbttagcompound = itemstack.getTag().getCompound("SkullOwner");
/*     */         
/* 109 */         if (nbttagcompound.hasKeyOfType("Name", 8)) {
/* 110 */           return LocaleI18n.a("item.skull.player.name", new Object[] { nbttagcompound.getString("Name") });
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 115 */     return super.a(itemstack);
/*     */   }
/*     */   
/*     */   public boolean a(final NBTTagCompound nbttagcompound) {
/* 119 */     super.a(nbttagcompound);
/* 120 */     if ((nbttagcompound.hasKeyOfType("SkullOwner", 8)) && (nbttagcompound.getString("SkullOwner").length() > 0)) {
/* 121 */       GameProfile gameprofile = new GameProfile(null, nbttagcompound.getString("SkullOwner"));
/*     */       
/*     */ 
/* 124 */       TileEntitySkull.b(gameprofile, new Predicate()
/*     */       {
/*     */         public boolean apply(GameProfile gameprofile)
/*     */         {
/* 128 */           nbttagcompound.set("SkullOwner", GameProfileSerializer.serialize(new NBTTagCompound(), gameprofile));
/* 129 */           return false;
/*     */         }
/*     */         
/* 132 */       });
/* 133 */       return true;
/*     */     }
/* 135 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemSkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */