/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import net.minecraft.server.v1_8_R3.IHopper;
/*     */ import net.minecraft.server.v1_8_R3.IInventory;
/*     */ import net.minecraft.server.v1_8_R3.InventoryCrafting;
/*     */ import net.minecraft.server.v1_8_R3.InventoryEnderChest;
/*     */ import net.minecraft.server.v1_8_R3.InventoryMerchant;
/*     */ import net.minecraft.server.v1_8_R3.PlayerInventory;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityBeacon;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityBrewingStand;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityDispenser;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityDropper;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityFurnace;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ public class CraftInventory
/*     */   implements Inventory
/*     */ {
/*     */   protected final IInventory inventory;
/*     */   
/*     */   public CraftInventory(IInventory inventory)
/*     */   {
/*  31 */     this.inventory = inventory;
/*     */   }
/*     */   
/*     */   public IInventory getInventory() {
/*  35 */     return this.inventory;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  39 */     return getInventory().getSize();
/*     */   }
/*     */   
/*     */   public String getName() {
/*  43 */     return getInventory().getName();
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack getItem(int index) {
/*  47 */     net.minecraft.server.v1_8_R3.ItemStack item = getInventory().getItem(index);
/*  48 */     return item == null ? null : CraftItemStack.asCraftMirror(item);
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack[] getContents() {
/*  52 */     org.bukkit.inventory.ItemStack[] items = new org.bukkit.inventory.ItemStack[getSize()];
/*  53 */     net.minecraft.server.v1_8_R3.ItemStack[] mcItems = getInventory().getContents();
/*     */     
/*  55 */     int size = Math.min(items.length, mcItems.length);
/*  56 */     for (int i = 0; i < size; i++) {
/*  57 */       items[i] = (mcItems[i] == null ? null : CraftItemStack.asCraftMirror(mcItems[i]));
/*     */     }
/*  59 */     return items;
/*     */   }
/*     */   
/*     */   public void setContents(org.bukkit.inventory.ItemStack[] items) {
/*  63 */     if (getInventory().getContents().length < items.length) {
/*  64 */       throw new IllegalArgumentException("Invalid inventory size; expected " + getInventory().getContents().length + " or less");
/*     */     }
/*     */     
/*  67 */     net.minecraft.server.v1_8_R3.ItemStack[] mcItems = getInventory().getContents();
/*     */     
/*  69 */     for (int i = 0; i < mcItems.length; i++) {
/*  70 */       if (i >= items.length) {
/*  71 */         mcItems[i] = null;
/*     */       } else {
/*  73 */         mcItems[i] = CraftItemStack.asNMSCopy(items[i]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*  79 */   public void setItem(int index, org.bukkit.inventory.ItemStack item) { getInventory().setItem(index, (item == null) || (item.getTypeId() == 0) ? null : CraftItemStack.asNMSCopy(item)); }
/*     */   
/*     */   public boolean contains(int materialId) {
/*     */     org.bukkit.inventory.ItemStack[] arrayOfItemStack;
/*  83 */     int i = (arrayOfItemStack = getContents()).length; for (int j = 0; j < i; j++) { org.bukkit.inventory.ItemStack item = arrayOfItemStack[j];
/*  84 */       if ((item != null) && (item.getTypeId() == materialId)) {
/*  85 */         return true;
/*     */       }
/*     */     }
/*  88 */     return false;
/*     */   }
/*     */   
/*     */   public boolean contains(Material material) {
/*  92 */     Validate.notNull(material, "Material cannot be null");
/*  93 */     return contains(material.getId());
/*     */   }
/*     */   
/*     */   public boolean contains(org.bukkit.inventory.ItemStack item) {
/*  97 */     if (item == null)
/*  98 */       return false;
/*     */     org.bukkit.inventory.ItemStack[] arrayOfItemStack;
/* 100 */     int i = (arrayOfItemStack = getContents()).length; for (int j = 0; j < i; j++) { org.bukkit.inventory.ItemStack i = arrayOfItemStack[j];
/* 101 */       if (item.equals(i)) {
/* 102 */         return true;
/*     */       }
/*     */     }
/* 105 */     return false;
/*     */   }
/*     */   
/*     */   public boolean contains(int materialId, int amount) {
/* 109 */     if (amount <= 0)
/* 110 */       return true;
/*     */     org.bukkit.inventory.ItemStack[] arrayOfItemStack;
/* 112 */     int i = (arrayOfItemStack = getContents()).length; for (int j = 0; j < i; j++) { org.bukkit.inventory.ItemStack item = arrayOfItemStack[j];
/* 113 */       if ((item != null) && (item.getTypeId() == materialId) && 
/* 114 */         (amount -= item.getAmount() <= 0)) {
/* 115 */         return true;
/*     */       }
/*     */     }
/*     */     
/* 119 */     return false;
/*     */   }
/*     */   
/*     */   public boolean contains(Material material, int amount) {
/* 123 */     Validate.notNull(material, "Material cannot be null");
/* 124 */     return contains(material.getId(), amount);
/*     */   }
/*     */   
/*     */   public boolean contains(org.bukkit.inventory.ItemStack item, int amount) {
/* 128 */     if (item == null) {
/* 129 */       return false;
/*     */     }
/* 131 */     if (amount <= 0)
/* 132 */       return true;
/*     */     org.bukkit.inventory.ItemStack[] arrayOfItemStack;
/* 134 */     int i = (arrayOfItemStack = getContents()).length; for (int j = 0; j < i; j++) { org.bukkit.inventory.ItemStack i = arrayOfItemStack[j];
/* 135 */       if (item.equals(i)) { amount--; if (amount <= 0)
/* 136 */           return true;
/*     */       }
/*     */     }
/* 139 */     return false;
/*     */   }
/*     */   
/*     */   public boolean containsAtLeast(org.bukkit.inventory.ItemStack item, int amount) {
/* 143 */     if (item == null) {
/* 144 */       return false;
/*     */     }
/* 146 */     if (amount <= 0)
/* 147 */       return true;
/*     */     org.bukkit.inventory.ItemStack[] arrayOfItemStack;
/* 149 */     int i = (arrayOfItemStack = getContents()).length; for (int j = 0; j < i; j++) { org.bukkit.inventory.ItemStack i = arrayOfItemStack[j];
/* 150 */       if ((item.isSimilar(i)) && (amount -= i.getAmount() <= 0)) {
/* 151 */         return true;
/*     */       }
/*     */     }
/* 154 */     return false;
/*     */   }
/*     */   
/*     */   public HashMap<Integer, org.bukkit.inventory.ItemStack> all(int materialId) {
/* 158 */     HashMap<Integer, org.bukkit.inventory.ItemStack> slots = new HashMap();
/*     */     
/* 160 */     org.bukkit.inventory.ItemStack[] inventory = getContents();
/* 161 */     for (int i = 0; i < inventory.length; i++) {
/* 162 */       org.bukkit.inventory.ItemStack item = inventory[i];
/* 163 */       if ((item != null) && (item.getTypeId() == materialId)) {
/* 164 */         slots.put(Integer.valueOf(i), item);
/*     */       }
/*     */     }
/* 167 */     return slots;
/*     */   }
/*     */   
/*     */   public HashMap<Integer, org.bukkit.inventory.ItemStack> all(Material material) {
/* 171 */     Validate.notNull(material, "Material cannot be null");
/* 172 */     return all(material.getId());
/*     */   }
/*     */   
/*     */   public HashMap<Integer, org.bukkit.inventory.ItemStack> all(org.bukkit.inventory.ItemStack item) {
/* 176 */     HashMap<Integer, org.bukkit.inventory.ItemStack> slots = new HashMap();
/* 177 */     if (item != null) {
/* 178 */       org.bukkit.inventory.ItemStack[] inventory = getContents();
/* 179 */       for (int i = 0; i < inventory.length; i++) {
/* 180 */         if (item.equals(inventory[i])) {
/* 181 */           slots.put(Integer.valueOf(i), inventory[i]);
/*     */         }
/*     */       }
/*     */     }
/* 185 */     return slots;
/*     */   }
/*     */   
/*     */   public int first(int materialId) {
/* 189 */     org.bukkit.inventory.ItemStack[] inventory = getContents();
/* 190 */     for (int i = 0; i < inventory.length; i++) {
/* 191 */       org.bukkit.inventory.ItemStack item = inventory[i];
/* 192 */       if ((item != null) && (item.getTypeId() == materialId)) {
/* 193 */         return i;
/*     */       }
/*     */     }
/* 196 */     return -1;
/*     */   }
/*     */   
/*     */   public int first(Material material) {
/* 200 */     Validate.notNull(material, "Material cannot be null");
/* 201 */     return first(material.getId());
/*     */   }
/*     */   
/*     */   public int first(org.bukkit.inventory.ItemStack item) {
/* 205 */     return first(item, true);
/*     */   }
/*     */   
/*     */   private int first(org.bukkit.inventory.ItemStack item, boolean withAmount) {
/* 209 */     if (item == null) {
/* 210 */       return -1;
/*     */     }
/* 212 */     org.bukkit.inventory.ItemStack[] inventory = getContents();
/* 213 */     for (int i = 0; i < inventory.length; i++) {
/* 214 */       if (inventory[i] != null)
/*     */       {
/* 216 */         if (withAmount ? item.equals(inventory[i]) : item.isSimilar(inventory[i]))
/* 217 */           return i;
/*     */       }
/*     */     }
/* 220 */     return -1;
/*     */   }
/*     */   
/*     */   public int firstEmpty() {
/* 224 */     org.bukkit.inventory.ItemStack[] inventory = getContents();
/* 225 */     for (int i = 0; i < inventory.length; i++) {
/* 226 */       if (inventory[i] == null) {
/* 227 */         return i;
/*     */       }
/*     */     }
/* 230 */     return -1;
/*     */   }
/*     */   
/*     */   public int firstPartial(int materialId) {
/* 234 */     org.bukkit.inventory.ItemStack[] inventory = getContents();
/* 235 */     for (int i = 0; i < inventory.length; i++) {
/* 236 */       org.bukkit.inventory.ItemStack item = inventory[i];
/* 237 */       if ((item != null) && (item.getTypeId() == materialId) && (item.getAmount() < item.getMaxStackSize())) {
/* 238 */         return i;
/*     */       }
/*     */     }
/* 241 */     return -1;
/*     */   }
/*     */   
/*     */   public int firstPartial(Material material) {
/* 245 */     Validate.notNull(material, "Material cannot be null");
/* 246 */     return firstPartial(material.getId());
/*     */   }
/*     */   
/*     */   private int firstPartial(org.bukkit.inventory.ItemStack item) {
/* 250 */     org.bukkit.inventory.ItemStack[] inventory = getContents();
/* 251 */     org.bukkit.inventory.ItemStack filteredItem = CraftItemStack.asCraftCopy(item);
/* 252 */     if (item == null) {
/* 253 */       return -1;
/*     */     }
/* 255 */     for (int i = 0; i < inventory.length; i++) {
/* 256 */       org.bukkit.inventory.ItemStack cItem = inventory[i];
/* 257 */       if ((cItem != null) && (cItem.getAmount() < cItem.getMaxStackSize()) && (cItem.isSimilar(filteredItem))) {
/* 258 */         return i;
/*     */       }
/*     */     }
/* 261 */     return -1;
/*     */   }
/*     */   
/*     */   public HashMap<Integer, org.bukkit.inventory.ItemStack> addItem(org.bukkit.inventory.ItemStack... items) {
/* 265 */     Validate.noNullElements(items, "Item cannot be null");
/* 266 */     HashMap<Integer, org.bukkit.inventory.ItemStack> leftover = new HashMap();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 274 */     for (int i = 0; i < items.length; i++) {
/* 275 */       org.bukkit.inventory.ItemStack item = items[i];
/*     */       for (;;)
/*     */       {
/* 278 */         int firstPartial = firstPartial(item);
/*     */         
/*     */ 
/* 281 */         if (firstPartial == -1)
/*     */         {
/* 283 */           int firstFree = firstEmpty();
/*     */           
/* 285 */           if (firstFree == -1)
/*     */           {
/* 287 */             leftover.put(Integer.valueOf(i), item);
/* 288 */             break;
/*     */           }
/*     */           
/* 291 */           if (item.getAmount() > getMaxItemStack()) {
/* 292 */             CraftItemStack stack = CraftItemStack.asCraftCopy(item);
/* 293 */             stack.setAmount(getMaxItemStack());
/* 294 */             setItem(firstFree, stack);
/* 295 */             item.setAmount(item.getAmount() - getMaxItemStack());
/*     */           }
/*     */           else {
/* 298 */             setItem(firstFree, item);
/* 299 */             break;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 304 */           org.bukkit.inventory.ItemStack partialItem = getItem(firstPartial);
/*     */           
/* 306 */           int amount = item.getAmount();
/* 307 */           int partialAmount = partialItem.getAmount();
/* 308 */           int maxAmount = partialItem.getMaxStackSize();
/*     */           
/*     */ 
/* 311 */           if (amount + partialAmount <= maxAmount) {
/* 312 */             partialItem.setAmount(amount + partialAmount);
/*     */             
/* 314 */             setItem(firstPartial, partialItem);
/* 315 */             break;
/*     */           }
/*     */           
/*     */ 
/* 319 */           partialItem.setAmount(maxAmount);
/*     */           
/* 321 */           setItem(firstPartial, partialItem);
/* 322 */           item.setAmount(amount + partialAmount - maxAmount);
/*     */         }
/*     */       }
/*     */     }
/* 326 */     return leftover;
/*     */   }
/*     */   
/*     */   public HashMap<Integer, org.bukkit.inventory.ItemStack> removeItem(org.bukkit.inventory.ItemStack... items) {
/* 330 */     Validate.notNull(items, "Items cannot be null");
/* 331 */     HashMap<Integer, org.bukkit.inventory.ItemStack> leftover = new HashMap();
/*     */     
/*     */ 
/*     */ 
/* 335 */     for (int i = 0; i < items.length; i++) {
/* 336 */       org.bukkit.inventory.ItemStack item = items[i];
/* 337 */       int toDelete = item.getAmount();
/*     */       do
/*     */       {
/* 340 */         int first = first(item, false);
/*     */         
/*     */ 
/* 343 */         if (first == -1) {
/* 344 */           item.setAmount(toDelete);
/* 345 */           leftover.put(Integer.valueOf(i), item);
/* 346 */           break;
/*     */         }
/* 348 */         org.bukkit.inventory.ItemStack itemStack = getItem(first);
/* 349 */         int amount = itemStack.getAmount();
/*     */         
/* 351 */         if (amount <= toDelete) {
/* 352 */           toDelete -= amount;
/*     */           
/* 354 */           clear(first);
/*     */         }
/*     */         else {
/* 357 */           itemStack.setAmount(amount - toDelete);
/* 358 */           setItem(first, itemStack);
/* 359 */           toDelete = 0;
/*     */ 
/*     */         }
/*     */         
/*     */       }
/* 364 */       while (toDelete > 0);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 369 */     return leftover;
/*     */   }
/*     */   
/*     */   private int getMaxItemStack() {
/* 373 */     return getInventory().getMaxStackSize();
/*     */   }
/*     */   
/*     */   public void remove(int materialId) {
/* 377 */     org.bukkit.inventory.ItemStack[] items = getContents();
/* 378 */     for (int i = 0; i < items.length; i++) {
/* 379 */       if ((items[i] != null) && (items[i].getTypeId() == materialId)) {
/* 380 */         clear(i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void remove(Material material) {
/* 386 */     Validate.notNull(material, "Material cannot be null");
/* 387 */     remove(material.getId());
/*     */   }
/*     */   
/*     */   public void remove(org.bukkit.inventory.ItemStack item) {
/* 391 */     org.bukkit.inventory.ItemStack[] items = getContents();
/* 392 */     for (int i = 0; i < items.length; i++) {
/* 393 */       if ((items[i] != null) && (items[i].equals(item))) {
/* 394 */         clear(i);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void clear(int index) {
/* 400 */     setItem(index, null);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 404 */     for (int i = 0; i < getSize(); i++) {
/* 405 */       clear(i);
/*     */     }
/*     */   }
/*     */   
/*     */   public ListIterator<org.bukkit.inventory.ItemStack> iterator() {
/* 410 */     return new InventoryIterator(this);
/*     */   }
/*     */   
/*     */   public ListIterator<org.bukkit.inventory.ItemStack> iterator(int index) {
/* 414 */     if (index < 0) {
/* 415 */       index += getSize() + 1;
/*     */     }
/* 417 */     return new InventoryIterator(this, index);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/* 421 */     return this.inventory.getViewers();
/*     */   }
/*     */   
/*     */   public String getTitle() {
/* 425 */     return this.inventory.getName();
/*     */   }
/*     */   
/*     */   public InventoryType getType()
/*     */   {
/* 430 */     if ((this.inventory instanceof InventoryCrafting))
/* 431 */       return this.inventory.getSize() >= 9 ? InventoryType.WORKBENCH : InventoryType.CRAFTING;
/* 432 */     if ((this.inventory instanceof PlayerInventory))
/* 433 */       return InventoryType.PLAYER;
/* 434 */     if ((this.inventory instanceof TileEntityDropper))
/* 435 */       return InventoryType.DROPPER;
/* 436 */     if ((this.inventory instanceof TileEntityDispenser))
/* 437 */       return InventoryType.DISPENSER;
/* 438 */     if ((this.inventory instanceof TileEntityFurnace))
/* 439 */       return InventoryType.FURNACE;
/* 440 */     if ((this instanceof CraftInventoryEnchanting))
/* 441 */       return InventoryType.ENCHANTING;
/* 442 */     if ((this.inventory instanceof TileEntityBrewingStand))
/* 443 */       return InventoryType.BREWING;
/* 444 */     if ((this.inventory instanceof CraftInventoryCustom.MinecraftInventory))
/* 445 */       return ((CraftInventoryCustom.MinecraftInventory)this.inventory).getType();
/* 446 */     if ((this.inventory instanceof InventoryEnderChest))
/* 447 */       return InventoryType.ENDER_CHEST;
/* 448 */     if ((this.inventory instanceof InventoryMerchant))
/* 449 */       return InventoryType.MERCHANT;
/* 450 */     if ((this.inventory instanceof TileEntityBeacon))
/* 451 */       return InventoryType.BEACON;
/* 452 */     if ((this instanceof CraftInventoryAnvil))
/* 453 */       return InventoryType.ANVIL;
/* 454 */     if ((this.inventory instanceof IHopper)) {
/* 455 */       return InventoryType.HOPPER;
/*     */     }
/* 457 */     return InventoryType.CHEST;
/*     */   }
/*     */   
/*     */   public InventoryHolder getHolder()
/*     */   {
/* 462 */     return this.inventory.getOwner();
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 466 */     return this.inventory.getMaxStackSize();
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/* 470 */     this.inventory.setMaxStackSize(size);
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 475 */     return this.inventory.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj)
/*     */   {
/* 480 */     return ((obj instanceof CraftInventory)) && (((CraftInventory)obj).inventory.equals(this.inventory));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */