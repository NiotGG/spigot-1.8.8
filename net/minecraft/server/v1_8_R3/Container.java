/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.event.Event.Result;
/*     */ import org.bukkit.event.inventory.InventoryDragEvent;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ public abstract class Container
/*     */ {
/*     */   public abstract InventoryView getBukkitView();
/*     */   
/*  23 */   public List<ItemStack> b = Lists.newArrayList();
/*  24 */   public List<Slot> c = Lists.newArrayList();
/*     */   public int windowId;
/*  26 */   private int dragType = -1;
/*     */   private int g;
/*  28 */   private final Set<Slot> h = com.google.common.collect.Sets.newHashSet();
/*  29 */   protected List<ICrafting> listeners = Lists.newArrayList();
/*  30 */   private Set<EntityHuman> i = com.google.common.collect.Sets.newHashSet();
/*     */   
/*     */   private int tickCount;
/*     */   
/*  34 */   public boolean checkReachable = true;
/*     */   
/*     */   public void transferTo(Container other, CraftHumanEntity player) {
/*  37 */     InventoryView source = getBukkitView();InventoryView destination = other.getBukkitView();
/*  38 */     ((CraftInventory)source.getTopInventory()).getInventory().onClose(player);
/*  39 */     ((CraftInventory)source.getBottomInventory()).getInventory().onClose(player);
/*  40 */     ((CraftInventory)destination.getTopInventory()).getInventory().onOpen(player);
/*  41 */     ((CraftInventory)destination.getBottomInventory()).getInventory().onOpen(player);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected Slot a(Slot slot)
/*     */   {
/*  48 */     slot.rawSlotIndex = this.c.size();
/*  49 */     this.c.add(slot);
/*  50 */     this.b.add(null);
/*  51 */     return slot;
/*     */   }
/*     */   
/*     */   public void addSlotListener(ICrafting icrafting) {
/*  55 */     if (this.listeners.contains(icrafting)) {
/*  56 */       throw new IllegalArgumentException("Listener already listening");
/*     */     }
/*  58 */     this.listeners.add(icrafting);
/*  59 */     icrafting.a(this, a());
/*  60 */     b();
/*     */   }
/*     */   
/*     */   public List<ItemStack> a()
/*     */   {
/*  65 */     ArrayList arraylist = Lists.newArrayList();
/*     */     
/*  67 */     for (int i = 0; i < this.c.size(); i++) {
/*  68 */       arraylist.add(((Slot)this.c.get(i)).getItem());
/*     */     }
/*     */     
/*  71 */     return arraylist;
/*     */   }
/*     */   
/*     */   public void b() {
/*  75 */     for (int i = 0; i < this.c.size(); i++) {
/*  76 */       ItemStack itemstack = ((Slot)this.c.get(i)).getItem();
/*  77 */       ItemStack itemstack1 = (ItemStack)this.b.get(i);
/*     */       
/*  79 */       if ((!ItemStack.fastMatches(itemstack1, itemstack)) || ((this.tickCount % 20 == 0) && (!ItemStack.matches(itemstack1, itemstack)))) {
/*  80 */         itemstack1 = itemstack == null ? null : itemstack.cloneItemStack();
/*  81 */         this.b.set(i, itemstack1);
/*     */         
/*  83 */         for (int j = 0; j < this.listeners.size(); j++) {
/*  84 */           ((ICrafting)this.listeners.get(j)).a(this, i, itemstack1);
/*     */         }
/*     */       }
/*     */     }
/*  88 */     this.tickCount += 1;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman, int i)
/*     */   {
/*  93 */     return false;
/*     */   }
/*     */   
/*     */   public Slot getSlot(IInventory iinventory, int i) {
/*  97 */     for (int j = 0; j < this.c.size(); j++) {
/*  98 */       Slot slot = (Slot)this.c.get(j);
/*     */       
/* 100 */       if (slot.a(iinventory, i)) {
/* 101 */         return slot;
/*     */       }
/*     */     }
/*     */     
/* 105 */     return null;
/*     */   }
/*     */   
/*     */   public Slot getSlot(int i) {
/* 109 */     return (Slot)this.c.get(i);
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/* 113 */     Slot slot = (Slot)this.c.get(i);
/*     */     
/* 115 */     return slot != null ? slot.getItem() : null;
/*     */   }
/*     */   
/*     */   public ItemStack clickItem(int i, int j, int k, EntityHuman entityhuman) {
/* 119 */     ItemStack itemstack = null;
/* 120 */     PlayerInventory playerinventory = entityhuman.inventory;
/*     */     
/*     */ 
/*     */ 
/* 124 */     if (k == 5) {
/* 125 */       int i1 = this.g;
/*     */       
/* 127 */       this.g = c(j);
/* 128 */       if (((i1 != 1) || (this.g != 2)) && (i1 != this.g)) {
/* 129 */         d();
/* 130 */       } else if (playerinventory.getCarried() == null) {
/* 131 */         d();
/* 132 */       } else if (this.g == 0) {
/* 133 */         this.dragType = b(j);
/* 134 */         if (a(this.dragType, entityhuman)) {
/* 135 */           this.g = 1;
/* 136 */           this.h.clear();
/*     */         } else {
/* 138 */           d();
/*     */         }
/* 140 */       } else if (this.g == 1) {
/* 141 */         Slot slot = (Slot)this.c.get(i);
/*     */         
/* 143 */         if ((slot != null) && (a(slot, playerinventory.getCarried(), true)) && (slot.isAllowed(playerinventory.getCarried())) && (playerinventory.getCarried().count > this.h.size()) && (b(slot))) {
/* 144 */           this.h.add(slot);
/*     */         }
/* 146 */       } else if (this.g == 2) {
/* 147 */         if (!this.h.isEmpty()) {
/* 148 */           ItemStack itemstack1 = playerinventory.getCarried().cloneItemStack();
/* 149 */           int l = playerinventory.getCarried().count;
/* 150 */           Iterator iterator = this.h.iterator();
/*     */           
/* 152 */           Map<Integer, ItemStack> draggedSlots = new HashMap();
/* 153 */           while (iterator.hasNext()) {
/* 154 */             Slot slot1 = (Slot)iterator.next();
/*     */             
/* 156 */             if ((slot1 != null) && (a(slot1, playerinventory.getCarried(), true)) && (slot1.isAllowed(playerinventory.getCarried())) && (playerinventory.getCarried().count >= this.h.size()) && (b(slot1))) {
/* 157 */               ItemStack itemstack2 = itemstack1.cloneItemStack();
/* 158 */               int j1 = slot1.hasItem() ? slot1.getItem().count : 0;
/*     */               
/* 160 */               a(this.h, this.dragType, itemstack2, j1);
/* 161 */               if (itemstack2.count > itemstack2.getMaxStackSize()) {
/* 162 */                 itemstack2.count = itemstack2.getMaxStackSize();
/*     */               }
/*     */               
/* 165 */               if (itemstack2.count > slot1.getMaxStackSize(itemstack2)) {
/* 166 */                 itemstack2.count = slot1.getMaxStackSize(itemstack2);
/*     */               }
/*     */               
/* 169 */               l -= itemstack2.count - j1;
/*     */               
/* 171 */               draggedSlots.put(Integer.valueOf(slot1.rawSlotIndex), itemstack2);
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 176 */           InventoryView view = getBukkitView();
/* 177 */           org.bukkit.inventory.ItemStack newcursor = CraftItemStack.asCraftMirror(itemstack1);
/* 178 */           newcursor.setAmount(l);
/* 179 */           Map<Integer, org.bukkit.inventory.ItemStack> eventmap = new HashMap();
/* 180 */           for (Map.Entry<Integer, ItemStack> ditem : draggedSlots.entrySet()) {
/* 181 */             eventmap.put((Integer)ditem.getKey(), CraftItemStack.asBukkitCopy((ItemStack)ditem.getValue()));
/*     */           }
/*     */           
/*     */ 
/* 185 */           ItemStack oldCursor = playerinventory.getCarried();
/* 186 */           playerinventory.setCarried(CraftItemStack.asNMSCopy(newcursor));
/*     */           
/* 188 */           InventoryDragEvent event = new InventoryDragEvent(view, newcursor.getType() != org.bukkit.Material.AIR ? newcursor : null, CraftItemStack.asBukkitCopy(oldCursor), this.dragType == 1, eventmap);
/* 189 */           entityhuman.world.getServer().getPluginManager().callEvent(event);
/*     */           
/*     */ 
/* 192 */           boolean needsUpdate = event.getResult() != Event.Result.DEFAULT;
/*     */           
/* 194 */           if (event.getResult() != Event.Result.DENY) {
/* 195 */             for (Map.Entry<Integer, ItemStack> dslot : draggedSlots.entrySet()) {
/* 196 */               view.setItem(((Integer)dslot.getKey()).intValue(), CraftItemStack.asBukkitCopy((ItemStack)dslot.getValue()));
/*     */             }
/*     */             
/*     */ 
/* 200 */             if (playerinventory.getCarried() != null) {
/* 201 */               playerinventory.setCarried(CraftItemStack.asNMSCopy(event.getCursor()));
/* 202 */               needsUpdate = true;
/*     */             }
/*     */           } else {
/* 205 */             playerinventory.setCarried(oldCursor);
/*     */           }
/*     */           
/* 208 */           if ((needsUpdate) && ((entityhuman instanceof EntityPlayer))) {
/* 209 */             ((EntityPlayer)entityhuman).updateInventory(this);
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 214 */         d();
/*     */       } else {
/* 216 */         d();
/*     */       }
/* 218 */     } else if (this.g != 0) {
/* 219 */       d();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     }
/* 225 */     else if (((k == 0) || (k == 1)) && ((j == 0) || (j == 1))) {
/* 226 */       if (i == 64537) {
/* 227 */         if (playerinventory.getCarried() != null) {
/* 228 */           if (j == 0) {
/* 229 */             entityhuman.drop(playerinventory.getCarried(), true);
/* 230 */             playerinventory.setCarried(null);
/*     */           }
/*     */           
/* 233 */           if (j == 1)
/*     */           {
/* 235 */             ItemStack itemstack4 = playerinventory.getCarried();
/* 236 */             if (itemstack4.count > 0) {
/* 237 */               entityhuman.drop(itemstack4.cloneAndSubtract(1), true);
/*     */             }
/*     */             
/* 240 */             if (itemstack4.count == 0)
/*     */             {
/* 242 */               playerinventory.setCarried(null);
/*     */             }
/*     */           }
/*     */         }
/* 246 */       } else if (k == 1) {
/* 247 */         if (i < 0) {
/* 248 */           return null;
/*     */         }
/*     */         
/* 251 */         Slot slot2 = (Slot)this.c.get(i);
/* 252 */         if ((slot2 != null) && (slot2.isAllowed(entityhuman))) {
/* 253 */           ItemStack itemstack1 = b(entityhuman, i);
/* 254 */           if (itemstack1 != null) {
/* 255 */             Item item = itemstack1.getItem();
/*     */             
/* 257 */             itemstack = itemstack1.cloneItemStack();
/* 258 */             if ((slot2.getItem() != null) && (slot2.getItem().getItem() == item)) {
/* 259 */               a(i, j, true, entityhuman);
/*     */             }
/*     */           }
/*     */         }
/*     */       } else {
/* 264 */         if (i < 0) {
/* 265 */           return null;
/*     */         }
/*     */         
/* 268 */         Slot slot2 = (Slot)this.c.get(i);
/* 269 */         if (slot2 != null) {
/* 270 */           ItemStack itemstack1 = slot2.getItem();
/* 271 */           ItemStack itemstack4 = playerinventory.getCarried();
/*     */           
/* 273 */           if (itemstack1 != null) {
/* 274 */             itemstack = itemstack1.cloneItemStack();
/*     */           }
/*     */           
/* 277 */           if (itemstack1 == null) {
/* 278 */             if ((itemstack4 != null) && (slot2.isAllowed(itemstack4))) {
/* 279 */               int k1 = j == 0 ? itemstack4.count : 1;
/* 280 */               if (k1 > slot2.getMaxStackSize(itemstack4)) {
/* 281 */                 k1 = slot2.getMaxStackSize(itemstack4);
/*     */               }
/*     */               
/* 284 */               if (itemstack4.count >= k1) {
/* 285 */                 slot2.set(itemstack4.cloneAndSubtract(k1));
/*     */               }
/*     */               
/* 288 */               if (itemstack4.count == 0) {
/* 289 */                 playerinventory.setCarried(null);
/*     */               }
/* 291 */               else if ((entityhuman instanceof EntityPlayer)) {
/* 292 */                 ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, entityhuman.inventory.getCarried()));
/*     */               }
/*     */             }
/*     */           }
/* 296 */           else if (slot2.isAllowed(entityhuman)) {
/* 297 */             if (itemstack4 == null) {
/* 298 */               int k1 = j == 0 ? itemstack1.count : (itemstack1.count + 1) / 2;
/* 299 */               ItemStack itemstack3 = slot2.a(k1);
/* 300 */               playerinventory.setCarried(itemstack3);
/* 301 */               if (itemstack1.count == 0) {
/* 302 */                 slot2.set(null);
/*     */               }
/*     */               
/* 305 */               slot2.a(entityhuman, playerinventory.getCarried());
/* 306 */             } else if (slot2.isAllowed(itemstack4)) {
/* 307 */               if ((itemstack1.getItem() == itemstack4.getItem()) && (itemstack1.getData() == itemstack4.getData()) && (ItemStack.equals(itemstack1, itemstack4))) {
/* 308 */                 int k1 = j == 0 ? itemstack4.count : 1;
/* 309 */                 if (k1 > slot2.getMaxStackSize(itemstack4) - itemstack1.count) {
/* 310 */                   k1 = slot2.getMaxStackSize(itemstack4) - itemstack1.count;
/*     */                 }
/*     */                 
/* 313 */                 if (k1 > itemstack4.getMaxStackSize() - itemstack1.count) {
/* 314 */                   k1 = itemstack4.getMaxStackSize() - itemstack1.count;
/*     */                 }
/*     */                 
/* 317 */                 itemstack4.cloneAndSubtract(k1);
/* 318 */                 if (itemstack4.count == 0) {
/* 319 */                   playerinventory.setCarried(null);
/*     */                 }
/* 321 */                 else if ((entityhuman instanceof EntityPlayer)) {
/* 322 */                   ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, entityhuman.inventory.getCarried()));
/*     */                 }
/*     */                 
/*     */ 
/* 326 */                 itemstack1.count += k1;
/* 327 */               } else if (itemstack4.count <= slot2.getMaxStackSize(itemstack4)) {
/* 328 */                 slot2.set(itemstack4);
/* 329 */                 playerinventory.setCarried(itemstack1);
/*     */               }
/* 331 */             } else if ((itemstack1.getItem() == itemstack4.getItem()) && (itemstack4.getMaxStackSize() > 1) && ((!itemstack1.usesData()) || (itemstack1.getData() == itemstack4.getData())) && (ItemStack.equals(itemstack1, itemstack4))) {
/* 332 */               int k1 = itemstack1.count;
/*     */               
/* 334 */               int maxStack = Math.min(itemstack4.getMaxStackSize(), slot2.getMaxStackSize());
/* 335 */               if ((k1 > 0) && (k1 + itemstack4.count <= maxStack)) {
/* 336 */                 itemstack4.count += k1;
/* 337 */                 itemstack1 = slot2.a(k1);
/* 338 */                 if (itemstack1.count == 0) {
/* 339 */                   slot2.set(null);
/*     */                 }
/*     */                 
/* 342 */                 slot2.a(entityhuman, playerinventory.getCarried());
/*     */               }
/* 344 */               else if ((entityhuman instanceof EntityPlayer)) {
/* 345 */                 ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, entityhuman.inventory.getCarried()));
/*     */               }
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 351 */           slot2.f();
/*     */           
/* 353 */           if (((entityhuman instanceof EntityPlayer)) && (slot2.getMaxStackSize() != 64)) {
/* 354 */             ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutSetSlot(this.windowId, slot2.rawSlotIndex, slot2.getItem()));
/*     */             
/* 356 */             if ((getBukkitView().getType() == InventoryType.WORKBENCH) || (getBukkitView().getType() == InventoryType.CRAFTING)) {
/* 357 */               ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutSetSlot(this.windowId, 0, getSlot(0).getItem()));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 363 */     else if ((k == 2) && (j >= 0) && (j < 9)) {
/* 364 */       Slot slot2 = (Slot)this.c.get(i);
/* 365 */       if (slot2.isAllowed(entityhuman)) {
/* 366 */         ItemStack itemstack1 = playerinventory.getItem(j);
/* 367 */         boolean flag = (itemstack1 == null) || ((slot2.inventory == playerinventory) && (slot2.isAllowed(itemstack1)));
/*     */         
/* 369 */         int k1 = -1;
/* 370 */         if (!flag) {
/* 371 */           k1 = playerinventory.getFirstEmptySlotIndex();
/* 372 */           flag |= k1 > -1;
/*     */         }
/*     */         
/* 375 */         if ((slot2.hasItem()) && (flag)) {
/* 376 */           ItemStack itemstack3 = slot2.getItem();
/* 377 */           playerinventory.setItem(j, itemstack3.cloneItemStack());
/* 378 */           if (((slot2.inventory != playerinventory) || (!slot2.isAllowed(itemstack1))) && (itemstack1 != null)) {
/* 379 */             if (k1 > -1) {
/* 380 */               playerinventory.pickup(itemstack1);
/* 381 */               slot2.a(itemstack3.count);
/* 382 */               slot2.set(null);
/* 383 */               slot2.a(entityhuman, itemstack3);
/*     */             }
/*     */           } else {
/* 386 */             slot2.a(itemstack3.count);
/* 387 */             slot2.set(itemstack1);
/* 388 */             slot2.a(entityhuman, itemstack3);
/*     */           }
/* 390 */         } else if ((!slot2.hasItem()) && (itemstack1 != null) && (slot2.isAllowed(itemstack1))) {
/* 391 */           playerinventory.setItem(j, null);
/* 392 */           slot2.set(itemstack1);
/*     */         }
/*     */       }
/* 395 */     } else if ((k == 3) && (entityhuman.abilities.canInstantlyBuild) && (playerinventory.getCarried() == null) && (i >= 0)) {
/* 396 */       Slot slot2 = (Slot)this.c.get(i);
/* 397 */       if ((slot2 != null) && (slot2.hasItem())) {
/* 398 */         ItemStack itemstack1 = slot2.getItem().cloneItemStack();
/* 399 */         itemstack1.count = itemstack1.getMaxStackSize();
/* 400 */         playerinventory.setCarried(itemstack1);
/*     */       }
/* 402 */     } else if ((k == 4) && (playerinventory.getCarried() == null) && (i >= 0)) {
/* 403 */       Slot slot2 = (Slot)this.c.get(i);
/* 404 */       if ((slot2 != null) && (slot2.hasItem()) && (slot2.isAllowed(entityhuman))) {
/* 405 */         ItemStack itemstack1 = slot2.a(j == 0 ? 1 : slot2.getItem().count);
/* 406 */         slot2.a(entityhuman, itemstack1);
/* 407 */         entityhuman.drop(itemstack1, true);
/*     */       }
/* 409 */     } else if ((k == 6) && (i >= 0)) {
/* 410 */       Slot slot2 = (Slot)this.c.get(i);
/* 411 */       ItemStack itemstack1 = playerinventory.getCarried();
/* 412 */       if ((itemstack1 != null) && ((slot2 == null) || (!slot2.hasItem()) || (!slot2.isAllowed(entityhuman)))) {
/* 413 */         int l = j == 0 ? 0 : this.c.size() - 1;
/* 414 */         int k1 = j == 0 ? 1 : -1;
/*     */         
/* 416 */         for (int l1 = 0; l1 < 2; l1++) {
/* 417 */           for (int i2 = l; (i2 >= 0) && (i2 < this.c.size()) && (itemstack1.count < itemstack1.getMaxStackSize()); i2 += k1) {
/* 418 */             Slot slot3 = (Slot)this.c.get(i2);
/*     */             
/* 420 */             if ((slot3.hasItem()) && (a(slot3, itemstack1, true)) && (slot3.isAllowed(entityhuman)) && (a(itemstack1, slot3)) && ((l1 != 0) || (slot3.getItem().count != slot3.getItem().getMaxStackSize()))) {
/* 421 */               int j2 = Math.min(itemstack1.getMaxStackSize() - itemstack1.count, slot3.getItem().count);
/* 422 */               ItemStack itemstack5 = slot3.a(j2);
/*     */               
/* 424 */               itemstack1.count += j2;
/* 425 */               if (itemstack5.count <= 0) {
/* 426 */                 slot3.set(null);
/*     */               }
/*     */               
/* 429 */               slot3.a(entityhuman, itemstack5);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 435 */       b();
/*     */     }
/*     */     
/*     */ 
/* 439 */     return itemstack;
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack itemstack, Slot slot) {
/* 443 */     return true;
/*     */   }
/*     */   
/*     */   protected void a(int i, int j, boolean flag, EntityHuman entityhuman) {
/* 447 */     clickItem(i, j, 1, entityhuman);
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 451 */     PlayerInventory playerinventory = entityhuman.inventory;
/*     */     
/* 453 */     if (playerinventory.getCarried() != null) {
/* 454 */       entityhuman.drop(playerinventory.getCarried(), false);
/* 455 */       playerinventory.setCarried(null);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(IInventory iinventory)
/*     */   {
/* 461 */     b();
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/* 465 */     getSlot(i).set(itemstack);
/*     */   }
/*     */   
/*     */   public boolean c(EntityHuman entityhuman) {
/* 469 */     return !this.i.contains(entityhuman);
/*     */   }
/*     */   
/*     */   public void a(EntityHuman entityhuman, boolean flag) {
/* 473 */     if (flag) {
/* 474 */       this.i.remove(entityhuman);
/*     */     } else {
/* 476 */       this.i.add(entityhuman);
/*     */     }
/*     */   }
/*     */   
/*     */   public abstract boolean a(EntityHuman paramEntityHuman);
/*     */   
/*     */   protected boolean a(ItemStack itemstack, int i, int j, boolean flag)
/*     */   {
/* 484 */     boolean flag1 = false;
/* 485 */     int k = i;
/*     */     
/* 487 */     if (flag) {
/* 488 */       k = j - 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 494 */     if (itemstack.isStackable()) {
/* 495 */       while ((itemstack.count > 0) && (((!flag) && (k < j)) || ((flag) && (k >= i)))) {
/* 496 */         Slot slot = (Slot)this.c.get(k);
/* 497 */         ItemStack itemstack1 = slot.getItem();
/* 498 */         if ((itemstack1 != null) && (itemstack1.getItem() == itemstack.getItem()) && ((!itemstack.usesData()) || (itemstack.getData() == itemstack1.getData())) && (ItemStack.equals(itemstack, itemstack1))) {
/* 499 */           int l = itemstack1.count + itemstack.count;
/*     */           
/*     */ 
/* 502 */           int maxStack = Math.min(itemstack.getMaxStackSize(), slot.getMaxStackSize());
/* 503 */           if (l <= maxStack) {
/* 504 */             itemstack.count = 0;
/* 505 */             itemstack1.count = l;
/* 506 */             slot.f();
/* 507 */             flag1 = true;
/* 508 */           } else if (itemstack1.count < maxStack) {
/* 509 */             itemstack.count -= maxStack - itemstack1.count;
/* 510 */             itemstack1.count = maxStack;
/* 511 */             slot.f();
/* 512 */             flag1 = true;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 517 */         if (flag) {
/* 518 */           k--;
/*     */         } else {
/* 520 */           k++;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 525 */     if (itemstack.count > 0) {
/* 526 */       if (flag) {
/* 527 */         k = j - 1;
/*     */       } else {
/* 529 */         k = i;
/*     */       }
/*     */       
/* 532 */       while (((!flag) && (k < j)) || ((flag) && (k >= i))) {
/* 533 */         Slot slot = (Slot)this.c.get(k);
/* 534 */         ItemStack itemstack1 = slot.getItem();
/* 535 */         if (itemstack1 == null) {
/* 536 */           slot.set(itemstack.cloneItemStack());
/* 537 */           slot.f();
/* 538 */           itemstack.count = 0;
/* 539 */           flag1 = true;
/* 540 */           break;
/*     */         }
/*     */         
/* 543 */         if (flag) {
/* 544 */           k--;
/*     */         } else {
/* 546 */           k++;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 551 */     return flag1;
/*     */   }
/*     */   
/*     */   public static int b(int i) {
/* 555 */     return i >> 2 & 0x3;
/*     */   }
/*     */   
/*     */   public static int c(int i) {
/* 559 */     return i & 0x3;
/*     */   }
/*     */   
/*     */   public static boolean a(int i, EntityHuman entityhuman) {
/* 563 */     return i == 0;
/*     */   }
/*     */   
/*     */   protected void d() {
/* 567 */     this.g = 0;
/* 568 */     this.h.clear();
/*     */   }
/*     */   
/*     */   public static boolean a(Slot slot, ItemStack itemstack, boolean flag) {
/* 572 */     boolean flag1 = (slot == null) || (!slot.hasItem());
/*     */     
/* 574 */     if ((slot != null) && (slot.hasItem()) && (itemstack != null) && (itemstack.doMaterialsMatch(slot.getItem())) && (ItemStack.equals(slot.getItem(), itemstack))) {
/* 575 */       flag1 |= slot.getItem().count + (flag ? 0 : itemstack.count) <= itemstack.getMaxStackSize();
/*     */     }
/*     */     
/* 578 */     return flag1;
/*     */   }
/*     */   
/*     */   public static void a(Set<Slot> set, int i, ItemStack itemstack, int j) {
/* 582 */     switch (i) {
/*     */     case 0: 
/* 584 */       itemstack.count = MathHelper.d(itemstack.count / set.size());
/* 585 */       break;
/*     */     
/*     */     case 1: 
/* 588 */       itemstack.count = 1;
/* 589 */       break;
/*     */     
/*     */     case 2: 
/* 592 */       itemstack.count = itemstack.getItem().getMaxStackSize(); }
/* 593 */     ItemStack tmp71_70 = itemstack;
/*     */     
/* 595 */     tmp71_70.count = (tmp71_70.count + j);
/*     */   }
/*     */   
/*     */   public boolean b(Slot slot) {
/* 599 */     return true;
/*     */   }
/*     */   
/*     */   public static int a(TileEntity tileentity) {
/* 603 */     return (tileentity instanceof IInventory) ? b((IInventory)tileentity) : 0;
/*     */   }
/*     */   
/*     */   public static int b(IInventory iinventory) {
/* 607 */     if (iinventory == null) {
/* 608 */       return 0;
/*     */     }
/* 610 */     int i = 0;
/* 611 */     float f = 0.0F;
/*     */     
/* 613 */     for (int j = 0; j < iinventory.getSize(); j++) {
/* 614 */       ItemStack itemstack = iinventory.getItem(j);
/*     */       
/* 616 */       if (itemstack != null) {
/* 617 */         f += itemstack.count / Math.min(iinventory.getMaxStackSize(), itemstack.getMaxStackSize());
/* 618 */         i++;
/*     */       }
/*     */     }
/*     */     
/* 622 */     f /= iinventory.getSize();
/* 623 */     return MathHelper.d(f * 14.0F) + (i > 0 ? 1 : 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\Container.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */