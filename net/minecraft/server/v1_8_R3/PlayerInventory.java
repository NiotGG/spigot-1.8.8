/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ 
/*     */ public class PlayerInventory
/*     */   implements IInventory
/*     */ {
/*  14 */   public ItemStack[] items = new ItemStack[36];
/*  15 */   public ItemStack[] armor = new ItemStack[4];
/*     */   
/*     */   public int itemInHandIndex;
/*     */   
/*     */   public EntityHuman player;
/*     */   private ItemStack f;
/*     */   public boolean e;
/*  22 */   public List<HumanEntity> transaction = new ArrayList();
/*  23 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  26 */     return this.items;
/*     */   }
/*     */   
/*     */   public ItemStack[] getArmorContents() {
/*  30 */     return this.armor;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  34 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  38 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  42 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner() {
/*  46 */     return this.player.getBukkitEntity();
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  50 */     this.maxStack = size;
/*     */   }
/*     */   
/*     */   public PlayerInventory(EntityHuman entityhuman)
/*     */   {
/*  55 */     this.player = entityhuman;
/*     */   }
/*     */   
/*     */   public ItemStack getItemInHand() {
/*  59 */     return (this.itemInHandIndex < 9) && (this.itemInHandIndex >= 0) ? this.items[this.itemInHandIndex] : null;
/*     */   }
/*     */   
/*     */   public static int getHotbarSize() {
/*  63 */     return 9;
/*     */   }
/*     */   
/*     */   private int c(Item item) {
/*  67 */     for (int i = 0; i < this.items.length; i++) {
/*  68 */       if ((this.items[i] != null) && (this.items[i].getItem() == item)) {
/*  69 */         return i;
/*     */       }
/*     */     }
/*     */     
/*  73 */     return -1;
/*     */   }
/*     */   
/*     */   private int firstPartial(ItemStack itemstack) {
/*  77 */     for (int i = 0; i < this.items.length; i++) {
/*  78 */       if ((this.items[i] != null) && (this.items[i].getItem() == itemstack.getItem()) && (this.items[i].isStackable()) && (this.items[i].count < this.items[i].getMaxStackSize()) && (this.items[i].count < getMaxStackSize()) && ((!this.items[i].usesData()) || (this.items[i].getData() == itemstack.getData())) && (ItemStack.equals(this.items[i], itemstack))) {
/*  79 */         return i;
/*     */       }
/*     */     }
/*     */     
/*  83 */     return -1;
/*     */   }
/*     */   
/*     */   public int canHold(ItemStack itemstack)
/*     */   {
/*  88 */     int remains = itemstack.count;
/*  89 */     for (int i = 0; i < this.items.length; i++) {
/*  90 */       if (this.items[i] == null) { return itemstack.count;
/*     */       }
/*     */       
/*  93 */       if ((this.items[i] != null) && (this.items[i].getItem() == itemstack.getItem()) && (this.items[i].isStackable()) && (this.items[i].count < this.items[i].getMaxStackSize()) && (this.items[i].count < getMaxStackSize()) && ((!this.items[i].usesData()) || (this.items[i].getData() == itemstack.getData())) && (ItemStack.equals(this.items[i], itemstack))) {
/*  94 */         remains -= (this.items[i].getMaxStackSize() < getMaxStackSize() ? this.items[i].getMaxStackSize() : getMaxStackSize()) - this.items[i].count;
/*     */       }
/*  96 */       if (remains <= 0) return itemstack.count;
/*     */     }
/*  98 */     return itemstack.count - remains;
/*     */   }
/*     */   
/*     */   public int getFirstEmptySlotIndex()
/*     */   {
/* 103 */     for (int i = 0; i < this.items.length; i++) {
/* 104 */       if (this.items[i] == null) {
/* 105 */         return i;
/*     */       }
/*     */     }
/*     */     
/* 109 */     return -1;
/*     */   }
/*     */   
/*     */   public int a(Item item, int i, int j, NBTTagCompound nbttagcompound) {
/* 113 */     int k = 0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 119 */     for (int l = 0; l < this.items.length; l++) {
/* 120 */       ItemStack itemstack = this.items[l];
/* 121 */       if ((itemstack != null) && ((item == null) || (itemstack.getItem() == item)) && ((i <= -1) || (itemstack.getData() == i)) && ((nbttagcompound == null) || (GameProfileSerializer.a(nbttagcompound, itemstack.getTag(), true)))) {
/* 122 */         int i1 = j <= 0 ? itemstack.count : Math.min(j - k, itemstack.count);
/* 123 */         k += i1;
/* 124 */         if (j != 0) {
/* 125 */           this.items[l].count -= i1;
/* 126 */           if (this.items[l].count == 0) {
/* 127 */             this.items[l] = null;
/*     */           }
/*     */           
/* 130 */           if ((j > 0) && (k >= j)) {
/* 131 */             return k;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 137 */     for (l = 0; l < this.armor.length; l++) {
/* 138 */       ItemStack itemstack = this.armor[l];
/* 139 */       if ((itemstack != null) && ((item == null) || (itemstack.getItem() == item)) && ((i <= -1) || (itemstack.getData() == i)) && ((nbttagcompound == null) || (GameProfileSerializer.a(nbttagcompound, itemstack.getTag(), false)))) {
/* 140 */         int i1 = j <= 0 ? itemstack.count : Math.min(j - k, itemstack.count);
/* 141 */         k += i1;
/* 142 */         if (j != 0) {
/* 143 */           this.armor[l].count -= i1;
/* 144 */           if (this.armor[l].count == 0) {
/* 145 */             this.armor[l] = null;
/*     */           }
/*     */           
/* 148 */           if ((j > 0) && (k >= j)) {
/* 149 */             return k;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 155 */     if (this.f != null) {
/* 156 */       if ((item != null) && (this.f.getItem() != item)) {
/* 157 */         return k;
/*     */       }
/*     */       
/* 160 */       if ((i > -1) && (this.f.getData() != i)) {
/* 161 */         return k;
/*     */       }
/*     */       
/* 164 */       if ((nbttagcompound != null) && (!GameProfileSerializer.a(nbttagcompound, this.f.getTag(), false))) {
/* 165 */         return k;
/*     */       }
/*     */       
/* 168 */       l = j <= 0 ? this.f.count : Math.min(j - k, this.f.count);
/* 169 */       k += l;
/* 170 */       if (j != 0) {
/* 171 */         this.f.count -= l;
/* 172 */         if (this.f.count == 0) {
/* 173 */           this.f = null;
/*     */         }
/*     */         
/* 176 */         if ((j > 0) && (k >= j)) {
/* 177 */           return k;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 182 */     return k;
/*     */   }
/*     */   
/*     */   private int e(ItemStack itemstack) {
/* 186 */     Item item = itemstack.getItem();
/* 187 */     int i = itemstack.count;
/* 188 */     int j = firstPartial(itemstack);
/*     */     
/* 190 */     if (j < 0) {
/* 191 */       j = getFirstEmptySlotIndex();
/*     */     }
/*     */     
/* 194 */     if (j < 0) {
/* 195 */       return i;
/*     */     }
/* 197 */     if (this.items[j] == null) {
/* 198 */       this.items[j] = new ItemStack(item, 0, itemstack.getData());
/* 199 */       if (itemstack.hasTag()) {
/* 200 */         this.items[j].setTag((NBTTagCompound)itemstack.getTag().clone());
/*     */       }
/*     */     }
/*     */     
/* 204 */     int k = i;
/*     */     
/* 206 */     if (i > this.items[j].getMaxStackSize() - this.items[j].count) {
/* 207 */       k = this.items[j].getMaxStackSize() - this.items[j].count;
/*     */     }
/*     */     
/* 210 */     if (k > getMaxStackSize() - this.items[j].count) {
/* 211 */       k = getMaxStackSize() - this.items[j].count;
/*     */     }
/*     */     
/* 214 */     if (k == 0) {
/* 215 */       return i;
/*     */     }
/* 217 */     i -= k;
/* 218 */     this.items[j].count += k;
/* 219 */     this.items[j].c = 5;
/* 220 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */   public void k()
/*     */   {
/* 226 */     for (int i = 0; i < this.items.length; i++) {
/* 227 */       if (this.items[i] != null) {
/* 228 */         this.items[i].a(this.player.world, this.player, i, this.itemInHandIndex == i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(Item item)
/*     */   {
/* 235 */     int i = c(item);
/*     */     
/* 237 */     if (i < 0) {
/* 238 */       return false;
/*     */     }
/* 240 */     if (--this.items[i].count <= 0) {
/* 241 */       this.items[i] = null;
/*     */     }
/*     */     
/* 244 */     return true;
/*     */   }
/*     */   
/*     */   public boolean b(Item item)
/*     */   {
/* 249 */     int i = c(item);
/*     */     
/* 251 */     return i >= 0;
/*     */   }
/*     */   
/*     */   public boolean pickup(final ItemStack itemstack) {
/* 255 */     if ((itemstack != null) && (itemstack.count != 0) && (itemstack.getItem() != null))
/*     */     {
/*     */       try
/*     */       {
/* 259 */         if (itemstack.g()) {
/* 260 */           int i = getFirstEmptySlotIndex();
/* 261 */           if (i >= 0) {
/* 262 */             this.items[i] = ItemStack.b(itemstack);
/* 263 */             this.items[i].c = 5;
/* 264 */             itemstack.count = 0;
/* 265 */             return true; }
/* 266 */           if (this.player.abilities.canInstantlyBuild) {
/* 267 */             itemstack.count = 0;
/* 268 */             return true;
/*     */           }
/* 270 */           return false;
/*     */         }
/*     */         int i;
/*     */         do {
/* 274 */           i = itemstack.count;
/* 275 */           itemstack.count = e(itemstack);
/* 276 */         } while ((itemstack.count > 0) && (itemstack.count < i));
/*     */         
/* 278 */         if ((itemstack.count == i) && (this.player.abilities.canInstantlyBuild)) {
/* 279 */           itemstack.count = 0;
/* 280 */           return true;
/*     */         }
/* 282 */         return itemstack.count < i;
/*     */       }
/*     */       catch (Throwable throwable)
/*     */       {
/* 286 */         CrashReport crashreport = CrashReport.a(throwable, "Adding item to inventory");
/* 287 */         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Item being added");
/*     */         
/* 289 */         crashreportsystemdetails.a("Item ID", Integer.valueOf(Item.getId(itemstack.getItem())));
/* 290 */         crashreportsystemdetails.a("Item data", Integer.valueOf(itemstack.getData()));
/* 291 */         crashreportsystemdetails.a("Item name", new Callable() {
/*     */           public String a() throws Exception {
/* 293 */             return itemstack.getName();
/*     */           }
/*     */           
/*     */           public Object call() throws Exception {
/* 297 */             return a();
/*     */           }
/* 299 */         });
/* 300 */         throw new ReportedException(crashreport);
/*     */       }
/*     */     }
/* 303 */     return false;
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j)
/*     */   {
/* 308 */     ItemStack[] aitemstack = this.items;
/*     */     
/* 310 */     if (i >= this.items.length) {
/* 311 */       aitemstack = this.armor;
/* 312 */       i -= this.items.length;
/*     */     }
/*     */     
/* 315 */     if (aitemstack[i] != null)
/*     */     {
/*     */ 
/* 318 */       if (aitemstack[i].count <= j) {
/* 319 */         ItemStack itemstack = aitemstack[i];
/* 320 */         aitemstack[i] = null;
/* 321 */         return itemstack;
/*     */       }
/* 323 */       ItemStack itemstack = aitemstack[i].cloneAndSubtract(j);
/* 324 */       if (aitemstack[i].count == 0) {
/* 325 */         aitemstack[i] = null;
/*     */       }
/*     */       
/* 328 */       return itemstack;
/*     */     }
/*     */     
/* 331 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i)
/*     */   {
/* 336 */     ItemStack[] aitemstack = this.items;
/*     */     
/* 338 */     if (i >= this.items.length) {
/* 339 */       aitemstack = this.armor;
/* 340 */       i -= this.items.length;
/*     */     }
/*     */     
/* 343 */     if (aitemstack[i] != null) {
/* 344 */       ItemStack itemstack = aitemstack[i];
/*     */       
/* 346 */       aitemstack[i] = null;
/* 347 */       return itemstack;
/*     */     }
/* 349 */     return null;
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack)
/*     */   {
/* 354 */     ItemStack[] aitemstack = this.items;
/*     */     
/* 356 */     if (i >= aitemstack.length) {
/* 357 */       i -= aitemstack.length;
/* 358 */       aitemstack = this.armor;
/*     */     }
/*     */     
/* 361 */     aitemstack[i] = itemstack;
/*     */   }
/*     */   
/*     */   public float a(Block block) {
/* 365 */     float f = 1.0F;
/*     */     
/* 367 */     if (this.items[this.itemInHandIndex] != null) {
/* 368 */       f *= this.items[this.itemInHandIndex].a(block);
/*     */     }
/*     */     
/* 371 */     return f;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public NBTTagList a(NBTTagList nbttaglist)
/*     */   {
/* 378 */     for (int i = 0; i < this.items.length; i++) {
/* 379 */       if (this.items[i] != null) {
/* 380 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 381 */         nbttagcompound.setByte("Slot", (byte)i);
/* 382 */         this.items[i].save(nbttagcompound);
/* 383 */         nbttaglist.add(nbttagcompound);
/*     */       }
/*     */     }
/*     */     
/* 387 */     for (i = 0; i < this.armor.length; i++) {
/* 388 */       if (this.armor[i] != null) {
/* 389 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 390 */         nbttagcompound.setByte("Slot", (byte)(i + 100));
/* 391 */         this.armor[i].save(nbttagcompound);
/* 392 */         nbttaglist.add(nbttagcompound);
/*     */       }
/*     */     }
/*     */     
/* 396 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   public void b(NBTTagList nbttaglist) {
/* 400 */     this.items = new ItemStack[36];
/* 401 */     this.armor = new ItemStack[4];
/*     */     
/* 403 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 404 */       NBTTagCompound nbttagcompound = nbttaglist.get(i);
/* 405 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/* 406 */       ItemStack itemstack = ItemStack.createStack(nbttagcompound);
/*     */       
/* 408 */       if (itemstack != null) {
/* 409 */         if ((j >= 0) && (j < this.items.length)) {
/* 410 */           this.items[j] = itemstack;
/*     */         }
/*     */         
/* 413 */         if ((j >= 100) && (j < this.armor.length + 100)) {
/* 414 */           this.armor[(j - 100)] = itemstack;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int getSize()
/*     */   {
/* 422 */     return this.items.length + 4;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/* 426 */     ItemStack[] aitemstack = this.items;
/*     */     
/* 428 */     if (i >= aitemstack.length) {
/* 429 */       i -= aitemstack.length;
/* 430 */       aitemstack = this.armor;
/*     */     }
/*     */     
/* 433 */     return aitemstack[i];
/*     */   }
/*     */   
/*     */   public String getName() {
/* 437 */     return "container.inventory";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/* 441 */     return false;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent getScoreboardDisplayName() {
/* 445 */     return hasCustomName() ? new ChatComponentText(getName()) : new ChatMessage(getName(), new Object[0]);
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 449 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean b(Block block) {
/* 453 */     if (block.getMaterial().isAlwaysDestroyable()) {
/* 454 */       return true;
/*     */     }
/* 456 */     ItemStack itemstack = getItem(this.itemInHandIndex);
/*     */     
/* 458 */     return itemstack != null ? itemstack.b(block) : false;
/*     */   }
/*     */   
/*     */   public ItemStack e(int i)
/*     */   {
/* 463 */     return this.armor[i];
/*     */   }
/*     */   
/*     */   public int m() {
/* 467 */     int i = 0;
/*     */     
/* 469 */     for (int j = 0; j < this.armor.length; j++) {
/* 470 */       if ((this.armor[j] != null) && ((this.armor[j].getItem() instanceof ItemArmor))) {
/* 471 */         int k = ((ItemArmor)this.armor[j].getItem()).c;
/*     */         
/* 473 */         i += k;
/*     */       }
/*     */     }
/*     */     
/* 477 */     return i;
/*     */   }
/*     */   
/*     */   public void a(float f) {
/* 481 */     f /= 4.0F;
/* 482 */     if (f < 1.0F) {
/* 483 */       f = 1.0F;
/*     */     }
/*     */     
/* 486 */     for (int i = 0; i < this.armor.length; i++) {
/* 487 */       if ((this.armor[i] != null) && ((this.armor[i].getItem() instanceof ItemArmor))) {
/* 488 */         this.armor[i].damage((int)f, this.player);
/* 489 */         if (this.armor[i].count == 0) {
/* 490 */           this.armor[i] = null;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void n()
/*     */   {
/* 500 */     for (int i = 0; i < this.items.length; i++) {
/* 501 */       if (this.items[i] != null) {
/* 502 */         this.player.a(this.items[i], true, false);
/* 503 */         this.items[i] = null;
/*     */       }
/*     */     }
/*     */     
/* 507 */     for (i = 0; i < this.armor.length; i++) {
/* 508 */       if (this.armor[i] != null) {
/* 509 */         this.player.a(this.armor[i], true, false);
/* 510 */         this.armor[i] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void update()
/*     */   {
/* 517 */     this.e = true;
/*     */   }
/*     */   
/*     */   public void setCarried(ItemStack itemstack) {
/* 521 */     this.f = itemstack;
/*     */   }
/*     */   
/*     */   public ItemStack getCarried()
/*     */   {
/* 526 */     if ((this.f != null) && (this.f.count == 0)) {
/* 527 */       setCarried(null);
/*     */     }
/*     */     
/* 530 */     return this.f;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 534 */     return !this.player.dead;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean c(ItemStack itemstack)
/*     */   {
/* 540 */     for (int i = 0; i < this.armor.length; i++) {
/* 541 */       if ((this.armor[i] != null) && (this.armor[i].doMaterialsMatch(itemstack))) {
/* 542 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 546 */     for (i = 0; i < this.items.length; i++) {
/* 547 */       if ((this.items[i] != null) && (this.items[i].doMaterialsMatch(itemstack))) {
/* 548 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 552 */     return false;
/*     */   }
/*     */   
/*     */   public void startOpen(EntityHuman entityhuman) {}
/*     */   
/*     */   public void closeContainer(EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 560 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public void b(PlayerInventory playerinventory)
/*     */   {
/* 566 */     for (int i = 0; i < this.items.length; i++) {
/* 567 */       this.items[i] = ItemStack.b(playerinventory.items[i]);
/*     */     }
/*     */     
/* 570 */     for (i = 0; i < this.armor.length; i++) {
/* 571 */       this.armor[i] = ItemStack.b(playerinventory.armor[i]);
/*     */     }
/*     */     
/* 574 */     this.itemInHandIndex = playerinventory.itemInHandIndex;
/*     */   }
/*     */   
/*     */   public int getProperty(int i) {
/* 578 */     return 0;
/*     */   }
/*     */   
/*     */   public void b(int i, int j) {}
/*     */   
/*     */   public int g() {
/* 584 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void l()
/*     */   {
/* 590 */     for (int i = 0; i < this.items.length; i++) {
/* 591 */       this.items[i] = null;
/*     */     }
/*     */     
/* 594 */     for (i = 0; i < this.armor.length; i++) {
/* 595 */       this.armor[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PlayerInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */