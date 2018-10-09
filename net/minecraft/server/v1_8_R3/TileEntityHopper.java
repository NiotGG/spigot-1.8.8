/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.event.inventory.InventoryMoveItemEvent;
/*     */ import org.bukkit.event.inventory.InventoryPickupItemEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.spigotmc.SpigotWorldConfig;
/*     */ 
/*     */ public class TileEntityHopper extends TileEntityContainer implements IHopper, IUpdatePlayerListBox
/*     */ {
/*  17 */   private ItemStack[] items = new ItemStack[5];
/*     */   private String f;
/*  19 */   private int g = -1;
/*     */   
/*     */ 
/*  22 */   public List<org.bukkit.entity.HumanEntity> transaction = new java.util.ArrayList();
/*  23 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  26 */     return this.items;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  30 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  34 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<org.bukkit.entity.HumanEntity> getViewers() {
/*  38 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  42 */     this.maxStack = size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void a(NBTTagCompound nbttagcompound)
/*     */   {
/*  49 */     super.a(nbttagcompound);
/*  50 */     NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
/*     */     
/*  52 */     this.items = new ItemStack[getSize()];
/*  53 */     if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
/*  54 */       this.f = nbttagcompound.getString("CustomName");
/*     */     }
/*     */     
/*  57 */     this.g = nbttagcompound.getInt("TransferCooldown");
/*     */     
/*  59 */     for (int i = 0; i < nbttaglist.size(); i++) {
/*  60 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/*  61 */       byte b0 = nbttagcompound1.getByte("Slot");
/*     */       
/*  63 */       if ((b0 >= 0) && (b0 < this.items.length)) {
/*  64 */         this.items[b0] = ItemStack.createStack(nbttagcompound1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/*  71 */     super.b(nbttagcompound);
/*  72 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  74 */     for (int i = 0; i < this.items.length; i++) {
/*  75 */       if (this.items[i] != null) {
/*  76 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/*  78 */         nbttagcompound1.setByte("Slot", (byte)i);
/*  79 */         this.items[i].save(nbttagcompound1);
/*  80 */         nbttaglist.add(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/*  84 */     nbttagcompound.set("Items", nbttaglist);
/*  85 */     nbttagcompound.setInt("TransferCooldown", this.g);
/*  86 */     if (hasCustomName()) {
/*  87 */       nbttagcompound.setString("CustomName", this.f);
/*     */     }
/*     */   }
/*     */   
/*     */   public void update()
/*     */   {
/*  93 */     super.update();
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  97 */     return this.items.length;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/* 101 */     return this.items[i];
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/* 105 */     if (this.items[i] != null)
/*     */     {
/*     */ 
/* 108 */       if (this.items[i].count <= j) {
/* 109 */         ItemStack itemstack = this.items[i];
/* 110 */         this.items[i] = null;
/* 111 */         return itemstack;
/*     */       }
/* 113 */       ItemStack itemstack = this.items[i].cloneAndSubtract(j);
/* 114 */       if (this.items[i].count == 0) {
/* 115 */         this.items[i] = null;
/*     */       }
/*     */       
/* 118 */       return itemstack;
/*     */     }
/*     */     
/* 121 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i)
/*     */   {
/* 126 */     if (this.items[i] != null) {
/* 127 */       ItemStack itemstack = this.items[i];
/*     */       
/* 129 */       this.items[i] = null;
/* 130 */       return itemstack;
/*     */     }
/* 132 */     return null;
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack)
/*     */   {
/* 137 */     this.items[i] = itemstack;
/* 138 */     if ((itemstack != null) && (itemstack.count > getMaxStackSize())) {
/* 139 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 145 */     return hasCustomName() ? this.f : "container.hopper";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/* 149 */     return (this.f != null) && (this.f.length() > 0);
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 153 */     this.f = s;
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 157 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 161 */     return this.world.getTileEntity(this.position) == this;
/*     */   }
/*     */   
/*     */   public void startOpen(EntityHuman entityhuman) {}
/*     */   
/*     */   public void closeContainer(EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 169 */     return true;
/*     */   }
/*     */   
/*     */   public void c() {
/* 173 */     if ((this.world != null) && (!this.world.isClientSide)) {
/* 174 */       this.g -= 1;
/* 175 */       if (!n()) {
/* 176 */         d(0);
/* 177 */         m();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean m()
/*     */   {
/* 184 */     if ((this.world != null) && (!this.world.isClientSide)) {
/* 185 */       if ((!n()) && (BlockHopper.f(u()))) {
/* 186 */         boolean flag = false;
/*     */         
/* 188 */         if (!p()) {
/* 189 */           flag = r();
/*     */         }
/*     */         
/* 192 */         if (!q()) {
/* 193 */           flag = (a(this)) || (flag);
/*     */         }
/*     */         
/* 196 */         if (flag) {
/* 197 */           d(this.world.spigotConfig.hopperTransfer);
/* 198 */           update();
/* 199 */           return true;
/*     */         }
/*     */       }
/* 202 */       return false;
/*     */     }
/* 204 */     return false;
/*     */   }
/*     */   
/*     */   private boolean p()
/*     */   {
/* 209 */     ItemStack[] aitemstack = this.items;
/* 210 */     int i = aitemstack.length;
/*     */     
/* 212 */     for (int j = 0; j < i; j++) {
/* 213 */       ItemStack itemstack = aitemstack[j];
/*     */       
/* 215 */       if (itemstack != null) {
/* 216 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 220 */     return true;
/*     */   }
/*     */   
/*     */   private boolean q() {
/* 224 */     ItemStack[] aitemstack = this.items;
/* 225 */     int i = aitemstack.length;
/*     */     
/* 227 */     for (int j = 0; j < i; j++) {
/* 228 */       ItemStack itemstack = aitemstack[j];
/*     */       
/* 230 */       if ((itemstack == null) || (itemstack.count != itemstack.getMaxStackSize())) {
/* 231 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 235 */     return true;
/*     */   }
/*     */   
/*     */   private boolean r() {
/* 239 */     IInventory iinventory = H();
/*     */     
/* 241 */     if (iinventory == null) {
/* 242 */       return false;
/*     */     }
/* 244 */     EnumDirection enumdirection = BlockHopper.b(u()).opposite();
/*     */     
/* 246 */     if (a(iinventory, enumdirection)) {
/* 247 */       return false;
/*     */     }
/* 249 */     for (int i = 0; i < getSize(); i++) {
/* 250 */       if (getItem(i) != null) {
/* 251 */         ItemStack itemstack = getItem(i).cloneItemStack();
/*     */         
/*     */ 
/*     */ 
/* 255 */         CraftItemStack oitemstack = CraftItemStack.asCraftMirror(splitStack(i, this.world.spigotConfig.hopperAmount));
/*     */         
/*     */         Inventory destinationInventory;
/*     */         Inventory destinationInventory;
/* 259 */         if ((iinventory instanceof InventoryLargeChest)) {
/* 260 */           destinationInventory = new org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryDoubleChest((InventoryLargeChest)iinventory);
/*     */         } else {
/* 262 */           destinationInventory = iinventory.getOwner().getInventory();
/*     */         }
/*     */         
/* 265 */         InventoryMoveItemEvent event = new InventoryMoveItemEvent(getOwner().getInventory(), oitemstack.clone(), destinationInventory, true);
/* 266 */         getWorld().getServer().getPluginManager().callEvent(event);
/* 267 */         if (event.isCancelled()) {
/* 268 */           setItem(i, itemstack);
/* 269 */           d(this.world.spigotConfig.hopperTransfer);
/* 270 */           return false;
/*     */         }
/* 272 */         int origCount = event.getItem().getAmount();
/* 273 */         ItemStack itemstack1 = addItem(iinventory, CraftItemStack.asNMSCopy(event.getItem()), enumdirection);
/*     */         
/* 275 */         if ((itemstack1 == null) || (itemstack1.count == 0)) {
/* 276 */           if (event.getItem().equals(oitemstack)) {
/* 277 */             iinventory.update();
/*     */           } else {
/* 279 */             setItem(i, itemstack);
/*     */           }
/*     */           
/* 282 */           return true;
/*     */         }
/* 284 */         itemstack.count -= origCount - itemstack1.count;
/* 285 */         setItem(i, itemstack);
/*     */       }
/*     */     }
/*     */     
/* 289 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean a(IInventory iinventory, EnumDirection enumdirection)
/*     */   {
/* 295 */     if ((iinventory instanceof IWorldInventory)) {
/* 296 */       IWorldInventory iworldinventory = (IWorldInventory)iinventory;
/* 297 */       int[] aint = iworldinventory.getSlotsForFace(enumdirection);
/*     */       
/* 299 */       for (int i = 0; i < aint.length; i++) {
/* 300 */         ItemStack itemstack = iworldinventory.getItem(aint[i]);
/*     */         
/* 302 */         if ((itemstack == null) || (itemstack.count != itemstack.getMaxStackSize())) {
/* 303 */           return false;
/*     */         }
/*     */       }
/*     */     } else {
/* 307 */       int j = iinventory.getSize();
/*     */       
/* 309 */       for (int k = 0; k < j; k++) {
/* 310 */         ItemStack itemstack1 = iinventory.getItem(k);
/*     */         
/* 312 */         if ((itemstack1 == null) || (itemstack1.count != itemstack1.getMaxStackSize())) {
/* 313 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 318 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean b(IInventory iinventory, EnumDirection enumdirection) {
/* 322 */     if ((iinventory instanceof IWorldInventory)) {
/* 323 */       IWorldInventory iworldinventory = (IWorldInventory)iinventory;
/* 324 */       int[] aint = iworldinventory.getSlotsForFace(enumdirection);
/*     */       
/* 326 */       for (int i = 0; i < aint.length; i++) {
/* 327 */         if (iworldinventory.getItem(aint[i]) != null) {
/* 328 */           return false;
/*     */         }
/*     */       }
/*     */     } else {
/* 332 */       int j = iinventory.getSize();
/*     */       
/* 334 */       for (int k = 0; k < j; k++) {
/* 335 */         if (iinventory.getItem(k) != null) {
/* 336 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 341 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean a(IHopper ihopper) {
/* 345 */     IInventory iinventory = b(ihopper);
/*     */     
/* 347 */     if (iinventory != null) {
/* 348 */       EnumDirection enumdirection = EnumDirection.DOWN;
/*     */       
/* 350 */       if (b(iinventory, enumdirection)) {
/* 351 */         return false;
/*     */       }
/*     */       
/* 354 */       if ((iinventory instanceof IWorldInventory)) {
/* 355 */         IWorldInventory iworldinventory = (IWorldInventory)iinventory;
/* 356 */         int[] aint = iworldinventory.getSlotsForFace(enumdirection);
/*     */         
/* 358 */         for (int i = 0; i < aint.length; i++) {
/* 359 */           if (a(ihopper, iinventory, aint[i], enumdirection)) {
/* 360 */             return true;
/*     */           }
/*     */         }
/*     */       } else {
/* 364 */         int j = iinventory.getSize();
/*     */         
/* 366 */         for (int k = 0; k < j; k++) {
/* 367 */           if (a(ihopper, iinventory, k, enumdirection)) {
/* 368 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     } else {
/* 373 */       Iterator iterator = a(ihopper.getWorld(), ihopper.A(), ihopper.B() + 1.0D, ihopper.C()).iterator();
/*     */       
/* 375 */       while (iterator.hasNext()) {
/* 376 */         EntityItem entityitem = (EntityItem)iterator.next();
/*     */         
/* 378 */         if (a(ihopper, entityitem)) {
/* 379 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 384 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean a(IHopper ihopper, IInventory iinventory, int i, EnumDirection enumdirection) {
/* 388 */     ItemStack itemstack = iinventory.getItem(i);
/*     */     
/* 390 */     if ((itemstack != null) && (b(iinventory, itemstack, i, enumdirection))) {
/* 391 */       ItemStack itemstack1 = itemstack.cloneItemStack();
/*     */       
/*     */ 
/* 394 */       CraftItemStack oitemstack = CraftItemStack.asCraftMirror(iinventory.splitStack(i, ihopper.getWorld().spigotConfig.hopperAmount));
/*     */       
/*     */       Inventory sourceInventory;
/*     */       Inventory sourceInventory;
/* 398 */       if ((iinventory instanceof InventoryLargeChest)) {
/* 399 */         sourceInventory = new org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryDoubleChest((InventoryLargeChest)iinventory);
/*     */       } else {
/* 401 */         sourceInventory = iinventory.getOwner().getInventory();
/*     */       }
/*     */       
/* 404 */       InventoryMoveItemEvent event = new InventoryMoveItemEvent(sourceInventory, oitemstack.clone(), ihopper.getOwner().getInventory(), false);
/*     */       
/* 406 */       ihopper.getWorld().getServer().getPluginManager().callEvent(event);
/* 407 */       if (event.isCancelled()) {
/* 408 */         iinventory.setItem(i, itemstack1);
/*     */         
/* 410 */         if ((ihopper instanceof TileEntityHopper)) {
/* 411 */           ((TileEntityHopper)ihopper).d(ihopper.getWorld().spigotConfig.hopperTransfer);
/* 412 */         } else if ((ihopper instanceof EntityMinecartHopper)) {
/* 413 */           ((EntityMinecartHopper)ihopper).m(ihopper.getWorld().spigotConfig.hopperTransfer / 2);
/*     */         }
/* 415 */         return false;
/*     */       }
/* 417 */       int origCount = event.getItem().getAmount();
/* 418 */       ItemStack itemstack2 = addItem(ihopper, CraftItemStack.asNMSCopy(event.getItem()), null);
/*     */       
/* 420 */       if ((itemstack2 == null) || (itemstack2.count == 0)) {
/* 421 */         if (event.getItem().equals(oitemstack)) {
/* 422 */           iinventory.update();
/*     */         } else {
/* 424 */           iinventory.setItem(i, itemstack1);
/*     */         }
/*     */         
/* 427 */         return true;
/*     */       }
/* 429 */       itemstack1.count -= origCount - itemstack2.count;
/*     */       
/* 431 */       iinventory.setItem(i, itemstack1);
/*     */     }
/*     */     
/* 434 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean a(IInventory iinventory, EntityItem entityitem) {
/* 438 */     boolean flag = false;
/*     */     
/* 440 */     if (entityitem == null) {
/* 441 */       return false;
/*     */     }
/*     */     
/* 444 */     InventoryPickupItemEvent event = new InventoryPickupItemEvent(iinventory.getOwner().getInventory(), (org.bukkit.entity.Item)entityitem.getBukkitEntity());
/* 445 */     entityitem.world.getServer().getPluginManager().callEvent(event);
/* 446 */     if (event.isCancelled()) {
/* 447 */       return false;
/*     */     }
/*     */     
/* 450 */     ItemStack itemstack = entityitem.getItemStack().cloneItemStack();
/* 451 */     ItemStack itemstack1 = addItem(iinventory, itemstack, null);
/*     */     
/* 453 */     if ((itemstack1 != null) && (itemstack1.count != 0)) {
/* 454 */       entityitem.setItemStack(itemstack1);
/*     */     } else {
/* 456 */       flag = true;
/* 457 */       entityitem.die();
/*     */     }
/*     */     
/* 460 */     return flag;
/*     */   }
/*     */   
/*     */   public static ItemStack addItem(IInventory iinventory, ItemStack itemstack, EnumDirection enumdirection)
/*     */   {
/* 465 */     if (((iinventory instanceof IWorldInventory)) && (enumdirection != null)) {
/* 466 */       IWorldInventory iworldinventory = (IWorldInventory)iinventory;
/* 467 */       int[] aint = iworldinventory.getSlotsForFace(enumdirection);
/*     */       
/* 469 */       int i = 0;
/* 470 */       do { itemstack = c(iinventory, itemstack, aint[i], enumdirection);i++;
/* 469 */         if ((i >= aint.length) || (itemstack == null)) break; } while (itemstack.count > 0);
/*     */     }
/*     */     else
/*     */     {
/* 473 */       int j = iinventory.getSize();
/*     */       
/* 475 */       for (int k = 0; (k < j) && (itemstack != null) && (itemstack.count > 0); k++) {
/* 476 */         itemstack = c(iinventory, itemstack, k, enumdirection);
/*     */       }
/*     */     }
/*     */     
/* 480 */     if ((itemstack != null) && (itemstack.count == 0)) {
/* 481 */       itemstack = null;
/*     */     }
/*     */     
/* 484 */     return itemstack;
/*     */   }
/*     */   
/*     */   private static boolean a(IInventory iinventory, ItemStack itemstack, int i, EnumDirection enumdirection) {
/* 488 */     return iinventory.b(i, itemstack);
/*     */   }
/*     */   
/*     */   private static boolean b(IInventory iinventory, ItemStack itemstack, int i, EnumDirection enumdirection) {
/* 492 */     return (!(iinventory instanceof IWorldInventory)) || (((IWorldInventory)iinventory).canTakeItemThroughFace(i, itemstack, enumdirection));
/*     */   }
/*     */   
/*     */   private static ItemStack c(IInventory iinventory, ItemStack itemstack, int i, EnumDirection enumdirection) {
/* 496 */     ItemStack itemstack1 = iinventory.getItem(i);
/*     */     
/* 498 */     if (a(iinventory, itemstack, i, enumdirection)) {
/* 499 */       boolean flag = false;
/*     */       
/* 501 */       if (itemstack1 == null) {
/* 502 */         iinventory.setItem(i, itemstack);
/* 503 */         itemstack = null;
/* 504 */         flag = true;
/* 505 */       } else if (a(itemstack1, itemstack)) {
/* 506 */         int j = itemstack.getMaxStackSize() - itemstack1.count;
/* 507 */         int k = Math.min(itemstack.count, j);
/*     */         
/* 509 */         itemstack.count -= k;
/* 510 */         itemstack1.count += k;
/* 511 */         flag = k > 0;
/*     */       }
/*     */       
/* 514 */       if (flag) {
/* 515 */         if ((iinventory instanceof TileEntityHopper)) {
/* 516 */           TileEntityHopper tileentityhopper = (TileEntityHopper)iinventory;
/*     */           
/* 518 */           if (tileentityhopper.o()) {
/* 519 */             tileentityhopper.d(tileentityhopper.world.spigotConfig.hopperTransfer);
/*     */           }
/*     */           
/* 522 */           iinventory.update();
/*     */         }
/*     */         
/* 525 */         iinventory.update();
/*     */       }
/*     */     }
/*     */     
/* 529 */     return itemstack;
/*     */   }
/*     */   
/*     */   private IInventory H() {
/* 533 */     EnumDirection enumdirection = BlockHopper.b(u());
/*     */     
/* 535 */     return b(getWorld(), this.position.getX() + enumdirection.getAdjacentX(), this.position.getY() + enumdirection.getAdjacentY(), this.position.getZ() + enumdirection.getAdjacentZ());
/*     */   }
/*     */   
/*     */   public static IInventory b(IHopper ihopper) {
/* 539 */     return b(ihopper.getWorld(), ihopper.A(), ihopper.B() + 1.0D, ihopper.C());
/*     */   }
/*     */   
/*     */   public static List<EntityItem> a(World world, double d0, double d1, double d2) {
/* 543 */     return world.a(EntityItem.class, new AxisAlignedBB(d0 - 0.5D, d1 - 0.5D, d2 - 0.5D, d0 + 0.5D, d1 + 0.5D, d2 + 0.5D), IEntitySelector.a);
/*     */   }
/*     */   
/*     */   public static IInventory b(World world, double d0, double d1, double d2) {
/* 547 */     Object object = null;
/* 548 */     int i = MathHelper.floor(d0);
/* 549 */     int j = MathHelper.floor(d1);
/* 550 */     int k = MathHelper.floor(d2);
/* 551 */     BlockPosition blockposition = new BlockPosition(i, j, k);
/* 552 */     if (!world.isLoaded(blockposition)) return null;
/* 553 */     Block block = world.getType(blockposition).getBlock();
/*     */     
/* 555 */     if (block.isTileEntity()) {
/* 556 */       TileEntity tileentity = world.getTileEntity(blockposition);
/*     */       
/* 558 */       if ((tileentity instanceof IInventory)) {
/* 559 */         object = (IInventory)tileentity;
/* 560 */         if (((object instanceof TileEntityChest)) && ((block instanceof BlockChest))) {
/* 561 */           object = ((BlockChest)block).f(world, blockposition);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 566 */     if (object == null) {
/* 567 */       List list = world.a(null, new AxisAlignedBB(d0 - 0.5D, d1 - 0.5D, d2 - 0.5D, d0 + 0.5D, d1 + 0.5D, d2 + 0.5D), IEntitySelector.c);
/*     */       
/* 569 */       if (list.size() > 0) {
/* 570 */         object = (IInventory)list.get(world.random.nextInt(list.size()));
/*     */       }
/*     */     }
/*     */     
/* 574 */     return (IInventory)object;
/*     */   }
/*     */   
/*     */   private static boolean a(ItemStack itemstack, ItemStack itemstack1) {
/* 578 */     return itemstack.count > itemstack.getMaxStackSize() ? false : itemstack.getData() != itemstack1.getData() ? false : itemstack.getItem() != itemstack1.getItem() ? false : ItemStack.equals(itemstack, itemstack1);
/*     */   }
/*     */   
/*     */   public double A() {
/* 582 */     return this.position.getX() + 0.5D;
/*     */   }
/*     */   
/*     */   public double B() {
/* 586 */     return this.position.getY() + 0.5D;
/*     */   }
/*     */   
/*     */   public double C() {
/* 590 */     return this.position.getZ() + 0.5D;
/*     */   }
/*     */   
/*     */   public void d(int i) {
/* 594 */     this.g = i;
/*     */   }
/*     */   
/*     */   public boolean n() {
/* 598 */     return this.g > 0;
/*     */   }
/*     */   
/*     */   public boolean o() {
/* 602 */     return this.g <= 1;
/*     */   }
/*     */   
/*     */   public String getContainerName() {
/* 606 */     return "minecraft:hopper";
/*     */   }
/*     */   
/*     */   public Container createContainer(PlayerInventory playerinventory, EntityHuman entityhuman) {
/* 610 */     return new ContainerHopper(playerinventory, this, entityhuman);
/*     */   }
/*     */   
/*     */   public int getProperty(int i) {
/* 614 */     return 0;
/*     */   }
/*     */   
/*     */   public void b(int i, int j) {}
/*     */   
/*     */   public int g() {
/* 620 */     return 0;
/*     */   }
/*     */   
/*     */   public void l() {
/* 624 */     for (int i = 0; i < this.items.length; i++) {
/* 625 */       this.items[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityHopper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */