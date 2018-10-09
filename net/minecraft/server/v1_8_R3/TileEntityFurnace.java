/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.inventory.FurnaceBurnEvent;
/*     */ import org.bukkit.event.inventory.FurnaceSmeltEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class TileEntityFurnace extends TileEntityContainer implements IUpdatePlayerListBox, IWorldInventory
/*     */ {
/*  15 */   private static final int[] a = new int[1];
/*  16 */   private static final int[] f = { 2, 1 };
/*  17 */   private static final int[] g = { 1 };
/*  18 */   private ItemStack[] items = new ItemStack[3];
/*     */   
/*     */   public int burnTime;
/*     */   
/*     */   private int ticksForCurrentFuel;
/*     */   public int cookTime;
/*     */   private int cookTimeTotal;
/*     */   private String m;
/*  26 */   private int lastTick = MinecraftServer.currentTick;
/*  27 */   private int maxStack = 64;
/*  28 */   public List<HumanEntity> transaction = new java.util.ArrayList();
/*     */   
/*     */   public ItemStack[] getContents() {
/*  31 */     return this.items;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  35 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  39 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  43 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  47 */     this.maxStack = size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getSize()
/*     */   {
/*  54 */     return this.items.length;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  58 */     return this.items[i];
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  62 */     if (this.items[i] != null)
/*     */     {
/*     */ 
/*  65 */       if (this.items[i].count <= j) {
/*  66 */         ItemStack itemstack = this.items[i];
/*  67 */         this.items[i] = null;
/*  68 */         return itemstack;
/*     */       }
/*  70 */       ItemStack itemstack = this.items[i].cloneAndSubtract(j);
/*  71 */       if (this.items[i].count == 0) {
/*  72 */         this.items[i] = null;
/*     */       }
/*     */       
/*  75 */       return itemstack;
/*     */     }
/*     */     
/*  78 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i)
/*     */   {
/*  83 */     if (this.items[i] != null) {
/*  84 */       ItemStack itemstack = this.items[i];
/*     */       
/*  86 */       this.items[i] = null;
/*  87 */       return itemstack;
/*     */     }
/*  89 */     return null;
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack)
/*     */   {
/*  94 */     boolean flag = (itemstack != null) && (itemstack.doMaterialsMatch(this.items[i])) && (ItemStack.equals(itemstack, this.items[i]));
/*     */     
/*  96 */     this.items[i] = itemstack;
/*  97 */     if ((itemstack != null) && (itemstack.count > getMaxStackSize())) {
/*  98 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */     
/* 101 */     if ((i == 0) && (!flag)) {
/* 102 */       this.cookTimeTotal = a(itemstack);
/* 103 */       this.cookTime = 0;
/* 104 */       update();
/*     */     }
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 110 */     return hasCustomName() ? this.m : "container.furnace";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/* 114 */     return (this.m != null) && (this.m.length() > 0);
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 118 */     this.m = s;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 122 */     super.a(nbttagcompound);
/* 123 */     NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
/*     */     
/* 125 */     this.items = new ItemStack[getSize()];
/*     */     
/* 127 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 128 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/* 129 */       byte b0 = nbttagcompound1.getByte("Slot");
/*     */       
/* 131 */       if ((b0 >= 0) && (b0 < this.items.length)) {
/* 132 */         this.items[b0] = ItemStack.createStack(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/* 136 */     this.burnTime = nbttagcompound.getShort("BurnTime");
/* 137 */     this.cookTime = nbttagcompound.getShort("CookTime");
/* 138 */     this.cookTimeTotal = nbttagcompound.getShort("CookTimeTotal");
/* 139 */     this.ticksForCurrentFuel = fuelTime(this.items[1]);
/* 140 */     if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
/* 141 */       this.m = nbttagcompound.getString("CustomName");
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 147 */     super.b(nbttagcompound);
/* 148 */     nbttagcompound.setShort("BurnTime", (short)this.burnTime);
/* 149 */     nbttagcompound.setShort("CookTime", (short)this.cookTime);
/* 150 */     nbttagcompound.setShort("CookTimeTotal", (short)this.cookTimeTotal);
/* 151 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 153 */     for (int i = 0; i < this.items.length; i++) {
/* 154 */       if (this.items[i] != null) {
/* 155 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 157 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 158 */         this.items[i].save(nbttagcompound1);
/* 159 */         nbttaglist.add(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/* 163 */     nbttagcompound.set("Items", nbttaglist);
/* 164 */     if (hasCustomName()) {
/* 165 */       nbttagcompound.setString("CustomName", this.m);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMaxStackSize()
/*     */   {
/* 171 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean isBurning() {
/* 175 */     return this.burnTime > 0;
/*     */   }
/*     */   
/*     */   public void c() {
/* 179 */     boolean flag = w() == Blocks.LIT_FURNACE;
/* 180 */     boolean flag1 = false;
/*     */     
/*     */ 
/* 183 */     int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
/* 184 */     this.lastTick = MinecraftServer.currentTick;
/*     */     
/*     */ 
/* 187 */     if ((isBurning()) && (canBurn())) {
/* 188 */       this.cookTime += elapsedTicks;
/* 189 */       if (this.cookTime >= this.cookTimeTotal) {
/* 190 */         this.cookTime = 0;
/* 191 */         this.cookTimeTotal = a(this.items[0]);
/* 192 */         burn();
/* 193 */         flag1 = true;
/*     */       }
/*     */     } else {
/* 196 */       this.cookTime = 0;
/*     */     }
/*     */     
/*     */ 
/* 200 */     if (isBurning()) {
/* 201 */       this.burnTime -= elapsedTicks;
/*     */     }
/*     */     
/* 204 */     if (!this.world.isClientSide) {
/* 205 */       if ((!isBurning()) && ((this.items[1] == null) || (this.items[0] == null))) {
/* 206 */         if ((!isBurning()) && (this.cookTime > 0)) {
/* 207 */           this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
/*     */         }
/*     */         
/*     */       }
/* 211 */       else if ((this.burnTime <= 0) && (canBurn())) {
/* 212 */         CraftItemStack fuel = CraftItemStack.asCraftMirror(this.items[1]);
/*     */         
/* 214 */         FurnaceBurnEvent furnaceBurnEvent = new FurnaceBurnEvent(this.world.getWorld().getBlockAt(this.position.getX(), this.position.getY(), this.position.getZ()), fuel, fuelTime(this.items[1]));
/* 215 */         this.world.getServer().getPluginManager().callEvent(furnaceBurnEvent);
/*     */         
/* 217 */         if (furnaceBurnEvent.isCancelled()) {
/* 218 */           return;
/*     */         }
/*     */         
/* 221 */         this.ticksForCurrentFuel = furnaceBurnEvent.getBurnTime();
/* 222 */         this.burnTime += this.ticksForCurrentFuel;
/* 223 */         if ((this.burnTime > 0) && (furnaceBurnEvent.isBurning()))
/*     */         {
/* 225 */           flag1 = true;
/* 226 */           if (this.items[1] != null) {
/* 227 */             this.items[1].count -= 1;
/* 228 */             if (this.items[1].count == 0) {
/* 229 */               Item item = this.items[1].getItem().q();
/*     */               
/* 231 */               this.items[1] = (item != null ? new ItemStack(item) : null);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
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
/* 252 */       if (flag != isBurning()) {
/* 253 */         flag1 = true;
/* 254 */         BlockFurnace.a(isBurning(), this.world, this.position);
/* 255 */         E();
/*     */       }
/*     */     }
/*     */     
/* 259 */     if (flag1) {
/* 260 */       update();
/*     */     }
/*     */   }
/*     */   
/*     */   public int a(ItemStack itemstack)
/*     */   {
/* 266 */     return 200;
/*     */   }
/*     */   
/*     */   private boolean canBurn() {
/* 270 */     if (this.items[0] == null) {
/* 271 */       return false;
/*     */     }
/* 273 */     ItemStack itemstack = RecipesFurnace.getInstance().getResult(this.items[0]);
/*     */     
/*     */ 
/* 276 */     return itemstack != null;
/*     */   }
/*     */   
/*     */   public void burn()
/*     */   {
/* 281 */     if (canBurn()) {
/* 282 */       ItemStack itemstack = RecipesFurnace.getInstance().getResult(this.items[0]);
/*     */       
/*     */ 
/* 285 */       CraftItemStack source = CraftItemStack.asCraftMirror(this.items[0]);
/* 286 */       org.bukkit.inventory.ItemStack result = CraftItemStack.asBukkitCopy(itemstack);
/*     */       
/* 288 */       FurnaceSmeltEvent furnaceSmeltEvent = new FurnaceSmeltEvent(this.world.getWorld().getBlockAt(this.position.getX(), this.position.getY(), this.position.getZ()), source, result);
/* 289 */       this.world.getServer().getPluginManager().callEvent(furnaceSmeltEvent);
/*     */       
/* 291 */       if (furnaceSmeltEvent.isCancelled()) {
/* 292 */         return;
/*     */       }
/*     */       
/* 295 */       result = furnaceSmeltEvent.getResult();
/* 296 */       itemstack = CraftItemStack.asNMSCopy(result);
/*     */       
/* 298 */       if (itemstack != null) {
/* 299 */         if (this.items[2] == null) {
/* 300 */           this.items[2] = itemstack;
/* 301 */         } else if (CraftItemStack.asCraftMirror(this.items[2]).isSimilar(result)) {
/* 302 */           this.items[2].count += itemstack.count;
/*     */         } else {
/* 304 */           return;
/*     */         }
/*     */       }
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
/* 317 */       if ((this.items[0].getItem() == Item.getItemOf(Blocks.SPONGE)) && (this.items[0].getData() == 1) && (this.items[1] != null) && (this.items[1].getItem() == Items.BUCKET)) {
/* 318 */         this.items[1] = new ItemStack(Items.WATER_BUCKET);
/*     */       }
/*     */       
/* 321 */       this.items[0].count -= 1;
/* 322 */       if (this.items[0].count <= 0) {
/* 323 */         this.items[0] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static int fuelTime(ItemStack itemstack)
/*     */   {
/* 330 */     if (itemstack == null) {
/* 331 */       return 0;
/*     */     }
/* 333 */     Item item = itemstack.getItem();
/*     */     
/* 335 */     if (((item instanceof ItemBlock)) && (Block.asBlock(item) != Blocks.AIR)) {
/* 336 */       Block block = Block.asBlock(item);
/*     */       
/* 338 */       if (block == Blocks.WOODEN_SLAB) {
/* 339 */         return 150;
/*     */       }
/*     */       
/* 342 */       if (block.getMaterial() == Material.WOOD) {
/* 343 */         return 300;
/*     */       }
/*     */       
/* 346 */       if (block == Blocks.COAL_BLOCK) {
/* 347 */         return 16000;
/*     */       }
/*     */     }
/*     */     
/* 351 */     return item == Items.BLAZE_ROD ? 2400 : item == Item.getItemOf(Blocks.SAPLING) ? 100 : item == Items.LAVA_BUCKET ? 20000 : item == Items.COAL ? 1600 : item == Items.STICK ? 100 : ((item instanceof ItemHoe)) && (((ItemHoe)item).g().equals("WOOD")) ? 200 : ((item instanceof ItemSword)) && (((ItemSword)item).h().equals("WOOD")) ? 200 : ((item instanceof ItemTool)) && (((ItemTool)item).h().equals("WOOD")) ? 200 : 0;
/*     */   }
/*     */   
/*     */   public static boolean isFuel(ItemStack itemstack)
/*     */   {
/* 356 */     return fuelTime(itemstack) > 0;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 360 */     return this.world.getTileEntity(this.position) == this;
/*     */   }
/*     */   
/*     */   public void startOpen(EntityHuman entityhuman) {}
/*     */   
/*     */   public void closeContainer(EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 368 */     return i != 2;
/*     */   }
/*     */   
/*     */   public int[] getSlotsForFace(EnumDirection enumdirection) {
/* 372 */     return enumdirection == EnumDirection.UP ? a : enumdirection == EnumDirection.DOWN ? f : g;
/*     */   }
/*     */   
/*     */   public boolean canPlaceItemThroughFace(int i, ItemStack itemstack, EnumDirection enumdirection) {
/* 376 */     return b(i, itemstack);
/*     */   }
/*     */   
/*     */   public boolean canTakeItemThroughFace(int i, ItemStack itemstack, EnumDirection enumdirection) {
/* 380 */     if ((enumdirection == EnumDirection.DOWN) && (i == 1)) {
/* 381 */       Item item = itemstack.getItem();
/*     */       
/* 383 */       if ((item != Items.WATER_BUCKET) && (item != Items.BUCKET)) {
/* 384 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 388 */     return true;
/*     */   }
/*     */   
/*     */   public String getContainerName() {
/* 392 */     return "minecraft:furnace";
/*     */   }
/*     */   
/*     */   public Container createContainer(PlayerInventory playerinventory, EntityHuman entityhuman) {
/* 396 */     return new ContainerFurnace(playerinventory, this);
/*     */   }
/*     */   
/*     */   public int getProperty(int i) {
/* 400 */     switch (i) {
/*     */     case 0: 
/* 402 */       return this.burnTime;
/*     */     
/*     */     case 1: 
/* 405 */       return this.ticksForCurrentFuel;
/*     */     
/*     */     case 2: 
/* 408 */       return this.cookTime;
/*     */     
/*     */     case 3: 
/* 411 */       return this.cookTimeTotal;
/*     */     }
/*     */     
/* 414 */     return 0;
/*     */   }
/*     */   
/*     */   public void b(int i, int j)
/*     */   {
/* 419 */     switch (i) {
/*     */     case 0: 
/* 421 */       this.burnTime = j;
/* 422 */       break;
/*     */     
/*     */     case 1: 
/* 425 */       this.ticksForCurrentFuel = j;
/* 426 */       break;
/*     */     
/*     */     case 2: 
/* 429 */       this.cookTime = j;
/* 430 */       break;
/*     */     
/*     */     case 3: 
/* 433 */       this.cookTimeTotal = j;
/*     */     }
/*     */   }
/*     */   
/*     */   public int g()
/*     */   {
/* 439 */     return 4;
/*     */   }
/*     */   
/*     */   public void l() {
/* 443 */     for (int i = 0; i < this.items.length; i++) {
/* 444 */       this.items[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityFurnace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */