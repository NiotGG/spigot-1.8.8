/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.event.Event.Result;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.block.BlockDamageEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ 
/*     */ public class PlayerInteractManager
/*     */ {
/*     */   public World world;
/*     */   public EntityPlayer player;
/*     */   private WorldSettings.EnumGamemode gamemode;
/*     */   private boolean d;
/*     */   private int lastDigTick;
/*     */   private BlockPosition f;
/*     */   private int currentTick;
/*     */   private boolean h;
/*     */   private BlockPosition i;
/*     */   private int j;
/*     */   private int k;
/*     */   
/*     */   public PlayerInteractManager(World world)
/*     */   {
/*  26 */     this.gamemode = WorldSettings.EnumGamemode.NOT_SET;
/*  27 */     this.f = BlockPosition.ZERO;
/*  28 */     this.i = BlockPosition.ZERO;
/*  29 */     this.k = -1;
/*  30 */     this.world = world;
/*     */   }
/*     */   
/*     */   public void setGameMode(WorldSettings.EnumGamemode worldsettings_enumgamemode) {
/*  34 */     this.gamemode = worldsettings_enumgamemode;
/*  35 */     worldsettings_enumgamemode.a(this.player.abilities);
/*  36 */     this.player.updateAbilities();
/*  37 */     this.player.server.getPlayerList().sendAll(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.UPDATE_GAME_MODE, new EntityPlayer[] { this.player }), this.player);
/*     */   }
/*     */   
/*     */   public WorldSettings.EnumGamemode getGameMode() {
/*  41 */     return this.gamemode;
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  45 */     return this.gamemode.e();
/*     */   }
/*     */   
/*     */   public boolean isCreative() {
/*  49 */     return this.gamemode.d();
/*     */   }
/*     */   
/*     */   public void b(WorldSettings.EnumGamemode worldsettings_enumgamemode) {
/*  53 */     if (this.gamemode == WorldSettings.EnumGamemode.NOT_SET) {
/*  54 */       this.gamemode = worldsettings_enumgamemode;
/*     */     }
/*     */     
/*  57 */     setGameMode(this.gamemode);
/*     */   }
/*     */   
/*     */   public void a() {
/*  61 */     this.currentTick = MinecraftServer.currentTick;
/*     */     
/*     */ 
/*     */ 
/*  65 */     if (this.h) {
/*  66 */       int j = this.currentTick - this.j;
/*  67 */       Block block = this.world.getType(this.i).getBlock();
/*     */       
/*  69 */       if (block.getMaterial() == Material.AIR) {
/*  70 */         this.h = false;
/*     */       } else {
/*  72 */         float f = block.getDamage(this.player, this.player.world, this.i) * (j + 1);
/*  73 */         int i = (int)(f * 10.0F);
/*  74 */         if (i != this.k) {
/*  75 */           this.world.c(this.player.getId(), this.i, i);
/*  76 */           this.k = i;
/*     */         }
/*     */         
/*  79 */         if (f >= 1.0F) {
/*  80 */           this.h = false;
/*  81 */           breakBlock(this.i);
/*     */         }
/*     */       }
/*  84 */     } else if (this.d) {
/*  85 */       Block block1 = this.world.getType(this.f).getBlock();
/*     */       
/*  87 */       if (block1.getMaterial() == Material.AIR) {
/*  88 */         this.world.c(this.player.getId(), this.f, -1);
/*  89 */         this.k = -1;
/*  90 */         this.d = false;
/*     */       } else {
/*  92 */         int k = this.currentTick - this.lastDigTick;
/*     */         
/*  94 */         float f = block1.getDamage(this.player, this.player.world, this.i) * (k + 1);
/*  95 */         int i = (int)(f * 10.0F);
/*  96 */         if (i != this.k) {
/*  97 */           this.world.c(this.player.getId(), this.f, i);
/*  98 */           this.k = i;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void a(BlockPosition blockposition, EnumDirection enumdirection)
/*     */   {
/* 107 */     PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(this.player, Action.LEFT_CLICK_BLOCK, blockposition, enumdirection, this.player.inventory.getItemInHand());
/* 108 */     if (event.isCancelled())
/*     */     {
/* 110 */       this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, blockposition));
/*     */       
/* 112 */       TileEntity tileentity = this.world.getTileEntity(blockposition);
/* 113 */       if (tileentity != null) {
/* 114 */         this.player.playerConnection.sendPacket(tileentity.getUpdatePacket());
/*     */       }
/* 116 */       return;
/*     */     }
/*     */     
/* 119 */     if (isCreative()) {
/* 120 */       if (!this.world.douseFire(null, blockposition, enumdirection)) {
/* 121 */         breakBlock(blockposition);
/*     */       }
/*     */     }
/*     */     else {
/* 125 */       Block block = this.world.getType(blockposition).getBlock();
/*     */       
/* 127 */       if (this.gamemode.c()) {
/* 128 */         if (this.gamemode == WorldSettings.EnumGamemode.SPECTATOR) {
/* 129 */           return;
/*     */         }
/*     */         
/* 132 */         if (!this.player.cn()) {
/* 133 */           ItemStack itemstack = this.player.bZ();
/*     */           
/* 135 */           if (itemstack == null) {
/* 136 */             return;
/*     */           }
/*     */           
/* 139 */           if (!itemstack.c(block)) {
/* 140 */             return;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 146 */       this.lastDigTick = this.currentTick;
/* 147 */       float f = 1.0F;
/*     */       
/*     */ 
/* 150 */       if (event.useInteractedBlock() == Event.Result.DENY)
/*     */       {
/* 152 */         IBlockData data = this.world.getType(blockposition);
/* 153 */         if (block == Blocks.WOODEN_DOOR)
/*     */         {
/* 155 */           boolean bottom = data.get(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER;
/* 156 */           this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, blockposition));
/* 157 */           this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, bottom ? blockposition.up() : blockposition.down()));
/* 158 */         } else if (block == Blocks.TRAPDOOR) {
/* 159 */           this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, blockposition));
/*     */         }
/* 161 */       } else if (block.getMaterial() != Material.AIR) {
/* 162 */         block.attack(this.world, blockposition, this.player);
/* 163 */         f = block.getDamage(this.player, this.player.world, blockposition);
/*     */         
/* 165 */         this.world.douseFire(null, blockposition, enumdirection);
/*     */       }
/*     */       
/* 168 */       if (event.useItemInHand() == Event.Result.DENY)
/*     */       {
/* 170 */         if (f > 1.0F) {
/* 171 */           this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, blockposition));
/*     */         }
/* 173 */         return;
/*     */       }
/* 175 */       BlockDamageEvent blockEvent = CraftEventFactory.callBlockDamageEvent(this.player, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this.player.inventory.getItemInHand(), f >= 1.0F);
/*     */       
/* 177 */       if (blockEvent.isCancelled())
/*     */       {
/* 179 */         this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, blockposition));
/* 180 */         return;
/*     */       }
/*     */       
/* 183 */       if (blockEvent.getInstaBreak()) {
/* 184 */         f = 2.0F;
/*     */       }
/*     */       
/*     */ 
/* 188 */       if ((block.getMaterial() != Material.AIR) && (f >= 1.0F)) {
/* 189 */         breakBlock(blockposition);
/*     */       } else {
/* 191 */         this.d = true;
/* 192 */         this.f = blockposition;
/* 193 */         int i = (int)(f * 10.0F);
/*     */         
/* 195 */         this.world.c(this.player.getId(), blockposition, i);
/* 196 */         this.k = i;
/*     */       }
/*     */     }
/*     */     
/* 200 */     this.world.spigotConfig.antiXrayInstance.updateNearbyBlocks(this.world, blockposition);
/*     */   }
/*     */   
/*     */   public void a(BlockPosition blockposition) {
/* 204 */     if (blockposition.equals(this.f)) {
/* 205 */       this.currentTick = MinecraftServer.currentTick;
/* 206 */       int i = this.currentTick - this.lastDigTick;
/* 207 */       Block block = this.world.getType(blockposition).getBlock();
/*     */       
/* 209 */       if (block.getMaterial() != Material.AIR) {
/* 210 */         float f = block.getDamage(this.player, this.player.world, blockposition) * (i + 1);
/*     */         
/* 212 */         if (f >= 0.7F) {
/* 213 */           this.d = false;
/* 214 */           this.world.c(this.player.getId(), blockposition, -1);
/* 215 */           breakBlock(blockposition);
/* 216 */         } else if (!this.h) {
/* 217 */           this.d = false;
/* 218 */           this.h = true;
/* 219 */           this.i = blockposition;
/* 220 */           this.j = this.lastDigTick;
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/* 225 */       this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, blockposition));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void e()
/*     */   {
/* 232 */     this.d = false;
/* 233 */     this.world.c(this.player.getId(), this.f, -1);
/*     */   }
/*     */   
/*     */   private boolean c(BlockPosition blockposition) {
/* 237 */     IBlockData iblockdata = this.world.getType(blockposition);
/*     */     
/* 239 */     iblockdata.getBlock().a(this.world, blockposition, iblockdata, this.player);
/* 240 */     boolean flag = this.world.setAir(blockposition);
/*     */     
/* 242 */     if (flag) {
/* 243 */       iblockdata.getBlock().postBreak(this.world, blockposition, iblockdata);
/*     */     }
/*     */     
/* 246 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean breakBlock(BlockPosition blockposition)
/*     */   {
/* 251 */     BlockBreakEvent event = null;
/*     */     
/* 253 */     if ((this.player instanceof EntityPlayer)) {
/* 254 */       org.bukkit.block.Block block = this.world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*     */       
/*     */ 
/* 257 */       boolean isSwordNoBreak = (this.gamemode.d()) && (this.player.bA() != null) && ((this.player.bA().getItem() instanceof ItemSword));
/*     */       
/*     */ 
/*     */ 
/* 261 */       if ((this.world.getTileEntity(blockposition) == null) && (!isSwordNoBreak)) {
/* 262 */         PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(this.world, blockposition);
/* 263 */         packet.block = Blocks.AIR.getBlockData();
/* 264 */         this.player.playerConnection.sendPacket(packet);
/*     */       }
/*     */       
/* 267 */       event = new BlockBreakEvent(block, this.player.getBukkitEntity());
/*     */       
/*     */ 
/* 270 */       event.setCancelled(isSwordNoBreak);
/*     */       
/*     */ 
/* 273 */       IBlockData nmsData = this.world.getType(blockposition);
/* 274 */       Block nmsBlock = nmsData.getBlock();
/*     */       
/* 276 */       if ((nmsBlock != null) && (!event.isCancelled()) && (!isCreative()) && (this.player.b(nmsBlock)))
/*     */       {
/* 278 */         if ((!nmsBlock.I()) || (!EnchantmentManager.hasSilkTouchEnchantment(this.player))) {
/* 279 */           block.getData();
/* 280 */           int bonusLevel = EnchantmentManager.getBonusBlockLootEnchantmentLevel(this.player);
/*     */           
/* 282 */           event.setExpToDrop(nmsBlock.getExpDrop(this.world, nmsData, bonusLevel));
/*     */         }
/*     */       }
/*     */       
/* 286 */       this.world.getServer().getPluginManager().callEvent(event);
/*     */       
/* 288 */       if (event.isCancelled()) {
/* 289 */         if (isSwordNoBreak) {
/* 290 */           return false;
/*     */         }
/*     */         
/* 293 */         this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, blockposition));
/*     */         
/* 295 */         TileEntity tileentity = this.world.getTileEntity(blockposition);
/* 296 */         if (tileentity != null) {
/* 297 */           this.player.playerConnection.sendPacket(tileentity.getUpdatePacket());
/*     */         }
/* 299 */         return false;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 305 */     IBlockData iblockdata = this.world.getType(blockposition);
/* 306 */     if (iblockdata.getBlock() == Blocks.AIR) return false;
/* 307 */     TileEntity tileentity = this.world.getTileEntity(blockposition);
/*     */     
/*     */ 
/* 310 */     if ((iblockdata.getBlock() == Blocks.SKULL) && (!isCreative())) {
/* 311 */       iblockdata.getBlock().dropNaturally(this.world, blockposition, iblockdata, 1.0F, 0);
/* 312 */       return c(blockposition);
/*     */     }
/*     */     
/*     */ 
/* 316 */     if (this.gamemode.c()) {
/* 317 */       if (this.gamemode == WorldSettings.EnumGamemode.SPECTATOR) {
/* 318 */         return false;
/*     */       }
/*     */       
/* 321 */       if (!this.player.cn()) {
/* 322 */         ItemStack itemstack = this.player.bZ();
/*     */         
/* 324 */         if (itemstack == null) {
/* 325 */           return false;
/*     */         }
/*     */         
/* 328 */         if (!itemstack.c(iblockdata.getBlock())) {
/* 329 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 334 */     this.world.a(this.player, 2001, blockposition, Block.getCombinedId(iblockdata));
/* 335 */     boolean flag = c(blockposition);
/*     */     
/* 337 */     if (isCreative()) {
/* 338 */       this.player.playerConnection.sendPacket(new PacketPlayOutBlockChange(this.world, blockposition));
/*     */     } else {
/* 340 */       ItemStack itemstack1 = this.player.bZ();
/* 341 */       boolean flag1 = this.player.b(iblockdata.getBlock());
/*     */       
/* 343 */       if (itemstack1 != null) {
/* 344 */         itemstack1.a(this.world, iblockdata.getBlock(), blockposition, this.player);
/* 345 */         if (itemstack1.count == 0) {
/* 346 */           this.player.ca();
/*     */         }
/*     */       }
/*     */       
/* 350 */       if ((flag) && (flag1)) {
/* 351 */         iblockdata.getBlock().a(this.world, this.player, blockposition, iblockdata, tileentity);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 356 */     if ((flag) && (event != null)) {
/* 357 */       iblockdata.getBlock().dropExperience(this.world, blockposition, event.getExpToDrop());
/*     */     }
/*     */     
/*     */ 
/* 361 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean useItem(EntityHuman entityhuman, World world, ItemStack itemstack)
/*     */   {
/* 366 */     if (this.gamemode == WorldSettings.EnumGamemode.SPECTATOR) {
/* 367 */       return false;
/*     */     }
/* 369 */     int i = itemstack.count;
/* 370 */     int j = itemstack.getData();
/* 371 */     ItemStack itemstack1 = itemstack.a(world, entityhuman);
/*     */     
/* 373 */     if ((itemstack1 == itemstack) && ((itemstack1 == null) || ((itemstack1.count == i) && (itemstack1.l() <= 0) && (itemstack1.getData() == j)))) {
/* 374 */       return false;
/*     */     }
/* 376 */     entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = itemstack1;
/* 377 */     if (isCreative()) {
/* 378 */       itemstack1.count = i;
/* 379 */       if (itemstack1.e()) {
/* 380 */         itemstack1.setData(j);
/*     */       }
/*     */     }
/*     */     
/* 384 */     if (itemstack1.count == 0) {
/* 385 */       entityhuman.inventory.items[entityhuman.inventory.itemInHandIndex] = null;
/*     */     }
/*     */     
/* 388 */     if (!entityhuman.bS()) {
/* 389 */       ((EntityPlayer)entityhuman).updateInventory(entityhuman.defaultContainer);
/*     */     }
/*     */     
/* 392 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 398 */   public boolean interactResult = false;
/* 399 */   public boolean firedInteract = false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean interact(EntityHuman entityhuman, World world, ItemStack itemstack, BlockPosition blockposition, EnumDirection enumdirection, float f, float f1, float f2)
/*     */   {
/* 449 */     IBlockData blockdata = world.getType(blockposition);
/* 450 */     boolean result = false;
/* 451 */     if (blockdata.getBlock() != Blocks.AIR) {
/* 452 */       boolean cancelledBlock = false;
/*     */       
/* 454 */       if (this.gamemode == WorldSettings.EnumGamemode.SPECTATOR) {
/* 455 */         TileEntity tileentity = world.getTileEntity(blockposition);
/* 456 */         cancelledBlock = (!(tileentity instanceof ITileInventory)) && (!(tileentity instanceof IInventory));
/*     */       }
/*     */       
/* 459 */       if ((!entityhuman.getBukkitEntity().isOp()) && (itemstack != null) && ((Block.asBlock(itemstack.getItem()) instanceof BlockCommand))) {
/* 460 */         cancelledBlock = true;
/*     */       }
/*     */       
/* 463 */       PlayerInteractEvent event = CraftEventFactory.callPlayerInteractEvent(entityhuman, Action.RIGHT_CLICK_BLOCK, blockposition, enumdirection, itemstack, cancelledBlock);
/* 464 */       this.firedInteract = true;
/* 465 */       this.interactResult = (event.useItemInHand() == Event.Result.DENY);
/*     */       
/* 467 */       if (event.useInteractedBlock() == Event.Result.DENY)
/*     */       {
/* 469 */         if ((blockdata.getBlock() instanceof BlockDoor)) {
/* 470 */           boolean bottom = blockdata.get(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER;
/* 471 */           ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutBlockChange(world, bottom ? blockposition.up() : blockposition.down()));
/*     */         }
/* 473 */         result = event.useItemInHand() != Event.Result.ALLOW;
/* 474 */       } else { if (this.gamemode == WorldSettings.EnumGamemode.SPECTATOR) {
/* 475 */           TileEntity tileentity = world.getTileEntity(blockposition);
/*     */           
/* 477 */           if ((tileentity instanceof ITileInventory)) {
/* 478 */             Block block = world.getType(blockposition).getBlock();
/* 479 */             ITileInventory itileinventory = (ITileInventory)tileentity;
/*     */             
/* 481 */             if (((itileinventory instanceof TileEntityChest)) && ((block instanceof BlockChest))) {
/* 482 */               itileinventory = ((BlockChest)block).f(world, blockposition);
/*     */             }
/*     */             
/* 485 */             if (itileinventory != null) {
/* 486 */               entityhuman.openContainer(itileinventory);
/* 487 */               return true;
/*     */             }
/* 489 */           } else if ((tileentity instanceof IInventory)) {
/* 490 */             entityhuman.openContainer((IInventory)tileentity);
/* 491 */             return true;
/*     */           }
/*     */           
/* 494 */           return false; }
/* 495 */         if ((!entityhuman.isSneaking()) || (itemstack == null)) {
/* 496 */           result = blockdata.getBlock().interact(world, blockposition, blockdata, entityhuman, enumdirection, f, f1, f2);
/*     */         }
/*     */       }
/* 499 */       if ((itemstack != null) && (!result) && (!this.interactResult)) {
/* 500 */         int j1 = itemstack.getData();
/* 501 */         int k1 = itemstack.count;
/*     */         
/* 503 */         result = itemstack.placeItem(entityhuman, world, blockposition, enumdirection, f, f1, f2);
/*     */         
/*     */ 
/* 506 */         if (isCreative()) {
/* 507 */           itemstack.setData(j1);
/* 508 */           itemstack.count = k1;
/*     */         }
/*     */       }
/*     */     }
/* 512 */     return result;
/*     */   }
/*     */   
/*     */   public void a(WorldServer worldserver)
/*     */   {
/* 517 */     this.world = worldserver;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PlayerInteractManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */