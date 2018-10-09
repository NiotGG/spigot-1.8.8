/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
/*     */ 
/*     */ public class ContainerAnvil extends Container
/*     */ {
/*  13 */   private static final org.apache.logging.log4j.Logger f = ;
/*  14 */   private IInventory g = new InventoryCraftResult();
/*  15 */   private IInventory h = new InventorySubcontainer("Repair", true, 2) {
/*     */     public void update() {
/*  17 */       super.update();
/*  18 */       ContainerAnvil.this.a(this);
/*     */     }
/*     */   };
/*     */   
/*     */   private World i;
/*     */   private BlockPosition j;
/*     */   public int a;
/*     */   private int k;
/*     */   private String l;
/*     */   private final EntityHuman m;
/*  28 */   private CraftInventoryView bukkitEntity = null;
/*     */   private PlayerInventory player;
/*     */   
/*     */   public ContainerAnvil(PlayerInventory playerinventory, final World world, final BlockPosition blockposition, EntityHuman entityhuman)
/*     */   {
/*  33 */     this.player = playerinventory;
/*  34 */     this.j = blockposition;
/*  35 */     this.i = world;
/*  36 */     this.m = entityhuman;
/*  37 */     a(new Slot(this.h, 0, 27, 47));
/*  38 */     a(new Slot(this.h, 1, 76, 47));
/*  39 */     a(new Slot(this.g, 2, 134, 47) {
/*     */       public boolean isAllowed(ItemStack itemstack) {
/*  41 */         return false;
/*     */       }
/*     */       
/*     */       public boolean isAllowed(EntityHuman entityhuman) {
/*  45 */         return ((entityhuman.abilities.canInstantlyBuild) || (entityhuman.expLevel >= ContainerAnvil.this.a)) && (ContainerAnvil.this.a > 0) && (hasItem());
/*     */       }
/*     */       
/*     */       public void a(EntityHuman entityhuman, ItemStack itemstack) {
/*  49 */         if (!entityhuman.abilities.canInstantlyBuild) {
/*  50 */           entityhuman.levelDown(-ContainerAnvil.this.a);
/*     */         }
/*     */         
/*  53 */         ContainerAnvil.this.h.setItem(0, null);
/*  54 */         if (ContainerAnvil.this.k > 0) {
/*  55 */           ItemStack itemstack1 = ContainerAnvil.this.h.getItem(1);
/*     */           
/*  57 */           if ((itemstack1 != null) && (itemstack1.count > ContainerAnvil.this.k)) {
/*  58 */             itemstack1.count -= ContainerAnvil.this.k;
/*  59 */             ContainerAnvil.this.h.setItem(1, itemstack1);
/*     */           } else {
/*  61 */             ContainerAnvil.this.h.setItem(1, null);
/*     */           }
/*     */         } else {
/*  64 */           ContainerAnvil.this.h.setItem(1, null);
/*     */         }
/*     */         
/*  67 */         ContainerAnvil.this.a = 0;
/*  68 */         IBlockData iblockdata = world.getType(blockposition);
/*     */         
/*  70 */         if ((!entityhuman.abilities.canInstantlyBuild) && (!world.isClientSide) && (iblockdata.getBlock() == Blocks.ANVIL) && (entityhuman.bc().nextFloat() < 0.12F)) {
/*  71 */           int i = ((Integer)iblockdata.get(BlockAnvil.DAMAGE)).intValue();
/*     */           
/*  73 */           i++;
/*  74 */           if (i > 2) {
/*  75 */             world.setAir(blockposition);
/*  76 */             world.triggerEffect(1020, blockposition, 0);
/*     */           } else {
/*  78 */             world.setTypeAndData(blockposition, iblockdata.set(BlockAnvil.DAMAGE, Integer.valueOf(i)), 2);
/*  79 */             world.triggerEffect(1021, blockposition, 0);
/*     */           }
/*  81 */         } else if (!world.isClientSide) {
/*  82 */           world.triggerEffect(1021, blockposition, 0);
/*     */         }
/*     */       }
/*     */     });
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  90 */     for (int i = 0; i < 3; i++) {
/*  91 */       for (int j = 0; j < 9; j++) {
/*  92 */         a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  96 */     for (i = 0; i < 9; i++) {
/*  97 */       a(new Slot(playerinventory, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(IInventory iinventory)
/*     */   {
/* 103 */     super.a(iinventory);
/* 104 */     if (iinventory == this.h) {
/* 105 */       e();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void e()
/*     */   {
/* 118 */     ItemStack itemstack = this.h.getItem(0);
/*     */     
/* 120 */     this.a = 1;
/* 121 */     int i = 0;
/* 122 */     byte b0 = 0;
/* 123 */     byte b1 = 0;
/*     */     
/* 125 */     if (itemstack == null) {
/* 126 */       this.g.setItem(0, null);
/* 127 */       this.a = 0;
/*     */     } else {
/* 129 */       ItemStack itemstack1 = itemstack.cloneItemStack();
/* 130 */       ItemStack itemstack2 = this.h.getItem(1);
/* 131 */       Map map = EnchantmentManager.a(itemstack1);
/* 132 */       boolean flag7 = false;
/* 133 */       int j = b0 + itemstack.getRepairCost() + (itemstack2 == null ? 0 : itemstack2.getRepairCost());
/*     */       
/* 135 */       this.k = 0;
/*     */       
/*     */ 
/* 138 */       if (itemstack2 != null) {
/* 139 */         flag7 = (itemstack2.getItem() == Items.ENCHANTED_BOOK) && (Items.ENCHANTED_BOOK.h(itemstack2).size() > 0);
/*     */         
/*     */ 
/*     */ 
/* 143 */         if ((itemstack1.e()) && (itemstack1.getItem().a(itemstack, itemstack2))) {
/* 144 */           int k = Math.min(itemstack1.h(), itemstack1.j() / 4);
/* 145 */           if (k <= 0) {
/* 146 */             this.g.setItem(0, null);
/* 147 */             this.a = 0;
/* 148 */             return;
/*     */           }
/*     */           
/* 151 */           for (int l = 0; (k > 0) && (l < itemstack2.count); l++) {
/* 152 */             int i1 = itemstack1.h() - k;
/* 153 */             itemstack1.setData(i1);
/* 154 */             i++;
/* 155 */             k = Math.min(itemstack1.h(), itemstack1.j() / 4);
/*     */           }
/*     */           
/* 158 */           this.k = l;
/*     */         } else {
/* 160 */           if ((!flag7) && ((itemstack1.getItem() != itemstack2.getItem()) || (!itemstack1.e()))) {
/* 161 */             this.g.setItem(0, null);
/* 162 */             this.a = 0;
/* 163 */             return;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 168 */           if ((itemstack1.e()) && (!flag7)) {
/* 169 */             int k = itemstack.j() - itemstack.h();
/* 170 */             int l = itemstack2.j() - itemstack2.h();
/* 171 */             int i1 = l + itemstack1.j() * 12 / 100;
/* 172 */             int k1 = k + i1;
/*     */             
/* 174 */             int j1 = itemstack1.j() - k1;
/* 175 */             if (j1 < 0) {
/* 176 */               j1 = 0;
/*     */             }
/*     */             
/* 179 */             if (j1 < itemstack1.getData()) {
/* 180 */               itemstack1.setData(j1);
/* 181 */               i += 2;
/*     */             }
/*     */           }
/*     */           
/* 185 */           Map map1 = EnchantmentManager.a(itemstack2);
/* 186 */           Iterator iterator = map1.keySet().iterator();
/*     */           
/* 188 */           while (iterator.hasNext()) {
/* 189 */             int i1 = ((Integer)iterator.next()).intValue();
/* 190 */             Enchantment enchantment = Enchantment.getById(i1);
/*     */             
/* 192 */             if (enchantment != null) {
/* 193 */               int j1 = map.containsKey(Integer.valueOf(i1)) ? ((Integer)map.get(Integer.valueOf(i1))).intValue() : 0;
/* 194 */               int l1 = ((Integer)map1.get(Integer.valueOf(i1))).intValue();
/*     */               int i2;
/*     */               int i2;
/* 197 */               if (j1 == l1) {
/* 198 */                 l1++;
/* 199 */                 i2 = l1;
/*     */               } else {
/* 201 */                 i2 = Math.max(l1, j1);
/*     */               }
/*     */               
/* 204 */               l1 = i2;
/* 205 */               boolean flag8 = enchantment.canEnchant(itemstack);
/*     */               
/* 207 */               if ((this.m.abilities.canInstantlyBuild) || (itemstack.getItem() == Items.ENCHANTED_BOOK)) {
/* 208 */                 flag8 = true;
/*     */               }
/*     */               
/* 211 */               Iterator iterator1 = map.keySet().iterator();
/*     */               
/* 213 */               while (iterator1.hasNext()) {
/* 214 */                 int j2 = ((Integer)iterator1.next()).intValue();
/*     */                 
/* 216 */                 if ((j2 != i1) && (!enchantment.a(Enchantment.getById(j2)))) {
/* 217 */                   flag8 = false;
/* 218 */                   i++;
/*     */                 }
/*     */               }
/*     */               
/* 222 */               if (flag8) {
/* 223 */                 if (l1 > enchantment.getMaxLevel()) {
/* 224 */                   l1 = enchantment.getMaxLevel();
/*     */                 }
/*     */                 
/* 227 */                 map.put(Integer.valueOf(i1), Integer.valueOf(l1));
/* 228 */                 int k2 = 0;
/*     */                 
/* 230 */                 switch (enchantment.getRandomWeight()) {
/*     */                 case 1: 
/* 232 */                   k2 = 8;
/* 233 */                   break;
/*     */                 
/*     */                 case 2: 
/* 236 */                   k2 = 4;
/*     */                 
/*     */                 case 3: 
/*     */                 case 4: 
/*     */                 case 6: 
/*     */                 case 7: 
/*     */                 case 8: 
/*     */                 case 9: 
/*     */                 default: 
/*     */                   break;
/*     */                 
/*     */                 case 5: 
/* 248 */                   k2 = 2;
/* 249 */                   break;
/*     */                 
/*     */                 case 10: 
/* 252 */                   k2 = 1;
/*     */                 }
/*     */                 
/* 255 */                 if (flag7) {
/* 256 */                   k2 = Math.max(1, k2 / 2);
/*     */                 }
/*     */                 
/* 259 */                 i += k2 * l1;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 266 */       if (StringUtils.isBlank(this.l)) {
/* 267 */         if (itemstack.hasName()) {
/* 268 */           b1 = 1;
/* 269 */           i += b1;
/* 270 */           itemstack1.r();
/*     */         }
/* 272 */       } else if (!this.l.equals(itemstack.getName())) {
/* 273 */         b1 = 1;
/* 274 */         i += b1;
/* 275 */         itemstack1.c(this.l);
/*     */       }
/*     */       
/* 278 */       this.a = (j + i);
/* 279 */       if (i <= 0) {
/* 280 */         itemstack1 = null;
/*     */       }
/*     */       
/* 283 */       if ((b1 == i) && (b1 > 0) && (this.a >= 40)) {
/* 284 */         this.a = 39;
/*     */       }
/*     */       
/* 287 */       if ((this.a >= 40) && (!this.m.abilities.canInstantlyBuild)) {
/* 288 */         itemstack1 = null;
/*     */       }
/*     */       
/* 291 */       if (itemstack1 != null) {
/* 292 */         int k = itemstack1.getRepairCost();
/* 293 */         if ((itemstack2 != null) && (k < itemstack2.getRepairCost())) {
/* 294 */           k = itemstack2.getRepairCost();
/*     */         }
/*     */         
/* 297 */         k = k * 2 + 1;
/* 298 */         itemstack1.setRepairCost(k);
/* 299 */         EnchantmentManager.a(map, itemstack1);
/*     */       }
/*     */       
/* 302 */       this.g.setItem(0, itemstack1);
/* 303 */       b();
/*     */     }
/*     */   }
/*     */   
/*     */   public void addSlotListener(ICrafting icrafting) {
/* 308 */     super.addSlotListener(icrafting);
/* 309 */     icrafting.setContainerData(this, 0, this.a);
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 313 */     super.b(entityhuman);
/* 314 */     if (!this.i.isClientSide) {
/* 315 */       for (int i = 0; i < this.h.getSize(); i++) {
/* 316 */         ItemStack itemstack = this.h.splitWithoutUpdate(i);
/*     */         
/* 318 */         if (itemstack != null) {
/* 319 */           entityhuman.drop(itemstack, false);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman)
/*     */   {
/* 327 */     if (!this.checkReachable) return true;
/* 328 */     return this.i.getType(this.j).getBlock() == Blocks.ANVIL;
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/* 332 */     ItemStack itemstack = null;
/* 333 */     Slot slot = (Slot)this.c.get(i);
/*     */     
/* 335 */     if ((slot != null) && (slot.hasItem())) {
/* 336 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/* 338 */       itemstack = itemstack1.cloneItemStack();
/* 339 */       if (i == 2) {
/* 340 */         if (!a(itemstack1, 3, 39, true)) {
/* 341 */           return null;
/*     */         }
/*     */         
/* 344 */         slot.a(itemstack1, itemstack);
/* 345 */       } else if ((i != 0) && (i != 1)) {
/* 346 */         if ((i >= 3) && (i < 39) && (!a(itemstack1, 0, 2, false))) {
/* 347 */           return null;
/*     */         }
/* 349 */       } else if (!a(itemstack1, 3, 39, false)) {
/* 350 */         return null;
/*     */       }
/*     */       
/* 353 */       if (itemstack1.count == 0) {
/* 354 */         slot.set(null);
/*     */       } else {
/* 356 */         slot.f();
/*     */       }
/*     */       
/* 359 */       if (itemstack1.count == itemstack.count) {
/* 360 */         return null;
/*     */       }
/*     */       
/* 363 */       slot.a(entityhuman, itemstack1);
/*     */     }
/*     */     
/* 366 */     return itemstack;
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 370 */     this.l = s;
/* 371 */     if (getSlot(2).hasItem()) {
/* 372 */       ItemStack itemstack = getSlot(2).getItem();
/*     */       
/* 374 */       if (StringUtils.isBlank(s)) {
/* 375 */         itemstack.r();
/*     */       } else {
/* 377 */         itemstack.c(this.l);
/*     */       }
/*     */     }
/*     */     
/* 381 */     e();
/*     */   }
/*     */   
/*     */ 
/*     */   public CraftInventoryView getBukkitView()
/*     */   {
/* 387 */     if (this.bukkitEntity != null) {
/* 388 */       return this.bukkitEntity;
/*     */     }
/*     */     
/* 391 */     org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory inventory = new org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryAnvil(this.h, this.g);
/* 392 */     this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), inventory, this);
/* 393 */     return this.bukkitEntity;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ContainerAnvil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */