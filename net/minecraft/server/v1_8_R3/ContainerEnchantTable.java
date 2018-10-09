/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryEnchanting;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.enchantment.EnchantItemEvent;
/*     */ import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class ContainerEnchantTable extends Container
/*     */ {
/*  20 */   public InventorySubcontainer enchantSlots = new InventorySubcontainer("Enchant", true, 2) {
/*     */     public int getMaxStackSize() {
/*  22 */       return 64;
/*     */     }
/*     */     
/*     */     public void update() {
/*  26 */       super.update();
/*  27 */       ContainerEnchantTable.this.a(this);
/*     */     }
/*     */   };
/*     */   private World world;
/*     */   private BlockPosition position;
/*  32 */   private Random k = new Random();
/*     */   public int f;
/*  34 */   public int[] costs = new int[3];
/*  35 */   public int[] h = { -1, -1, -1 };
/*     */   
/*  37 */   private CraftInventoryView bukkitEntity = null;
/*     */   private Player player;
/*     */   
/*     */   public ContainerEnchantTable(PlayerInventory playerinventory, World world, BlockPosition blockposition)
/*     */   {
/*  42 */     this.world = world;
/*  43 */     this.position = blockposition;
/*  44 */     this.f = playerinventory.player.cj();
/*  45 */     a(new Slot(this.enchantSlots, 0, 15, 47) {
/*     */       public boolean isAllowed(ItemStack itemstack) {
/*  47 */         return true;
/*     */       }
/*     */       
/*     */       public int getMaxStackSize() {
/*  51 */         return 1;
/*     */       }
/*  53 */     });
/*  54 */     a(new Slot(this.enchantSlots, 1, 35, 47) {
/*     */       public boolean isAllowed(ItemStack itemstack) {
/*  56 */         return (itemstack.getItem() == Items.DYE) && (EnumColor.fromInvColorIndex(itemstack.getData()) == EnumColor.BLUE);
/*     */       }
/*     */     });
/*     */     
/*     */ 
/*     */ 
/*  62 */     for (int i = 0; i < 3; i++) {
/*  63 */       for (int j = 0; j < 9; j++) {
/*  64 */         a(new Slot(playerinventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     }
/*     */     
/*  68 */     for (i = 0; i < 9; i++) {
/*  69 */       a(new Slot(playerinventory, i, 8 + i * 18, 142));
/*     */     }
/*     */     
/*     */ 
/*  73 */     this.player = ((Player)playerinventory.player.getBukkitEntity());
/*     */   }
/*     */   
/*     */   public void addSlotListener(ICrafting icrafting)
/*     */   {
/*  78 */     super.addSlotListener(icrafting);
/*  79 */     icrafting.setContainerData(this, 0, this.costs[0]);
/*  80 */     icrafting.setContainerData(this, 1, this.costs[1]);
/*  81 */     icrafting.setContainerData(this, 2, this.costs[2]);
/*  82 */     icrafting.setContainerData(this, 3, this.f & 0xFFFFFFF0);
/*  83 */     icrafting.setContainerData(this, 4, this.h[0]);
/*  84 */     icrafting.setContainerData(this, 5, this.h[1]);
/*  85 */     icrafting.setContainerData(this, 6, this.h[2]);
/*     */   }
/*     */   
/*     */   public void b() {
/*  89 */     super.b();
/*     */     
/*  91 */     for (int i = 0; i < this.listeners.size(); i++) {
/*  92 */       ICrafting icrafting = (ICrafting)this.listeners.get(i);
/*     */       
/*  94 */       icrafting.setContainerData(this, 0, this.costs[0]);
/*  95 */       icrafting.setContainerData(this, 1, this.costs[1]);
/*  96 */       icrafting.setContainerData(this, 2, this.costs[2]);
/*  97 */       icrafting.setContainerData(this, 3, this.f & 0xFFFFFFF0);
/*  98 */       icrafting.setContainerData(this, 4, this.h[0]);
/*  99 */       icrafting.setContainerData(this, 5, this.h[1]);
/* 100 */       icrafting.setContainerData(this, 6, this.h[2]);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(IInventory iinventory)
/*     */   {
/* 106 */     if (iinventory == this.enchantSlots) {
/* 107 */       ItemStack itemstack = iinventory.getItem(0);
/*     */       
/*     */ 
/* 110 */       if (itemstack != null) {
/* 111 */         if (!this.world.isClientSide) {
/* 112 */           int i = 0;
/*     */           
/*     */ 
/*     */ 
/* 116 */           for (int j = -1; j <= 1; j++) {
/* 117 */             for (int k = -1; k <= 1; k++) {
/* 118 */               if (((j != 0) || (k != 0)) && (this.world.isEmpty(this.position.a(k, 0, j))) && (this.world.isEmpty(this.position.a(k, 1, j)))) {
/* 119 */                 if (this.world.getType(this.position.a(k * 2, 0, j * 2)).getBlock() == Blocks.BOOKSHELF) {
/* 120 */                   i++;
/*     */                 }
/*     */                 
/* 123 */                 if (this.world.getType(this.position.a(k * 2, 1, j * 2)).getBlock() == Blocks.BOOKSHELF) {
/* 124 */                   i++;
/*     */                 }
/*     */                 
/* 127 */                 if ((k != 0) && (j != 0)) {
/* 128 */                   if (this.world.getType(this.position.a(k * 2, 0, j)).getBlock() == Blocks.BOOKSHELF) {
/* 129 */                     i++;
/*     */                   }
/*     */                   
/* 132 */                   if (this.world.getType(this.position.a(k * 2, 1, j)).getBlock() == Blocks.BOOKSHELF) {
/* 133 */                     i++;
/*     */                   }
/*     */                   
/* 136 */                   if (this.world.getType(this.position.a(k, 0, j * 2)).getBlock() == Blocks.BOOKSHELF) {
/* 137 */                     i++;
/*     */                   }
/*     */                   
/* 140 */                   if (this.world.getType(this.position.a(k, 1, j * 2)).getBlock() == Blocks.BOOKSHELF) {
/* 141 */                     i++;
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 148 */           this.k.setSeed(this.f);
/*     */           
/* 150 */           for (j = 0; j < 3; j++) {
/* 151 */             this.costs[j] = EnchantmentManager.a(this.k, j, i, itemstack);
/* 152 */             this.h[j] = -1;
/* 153 */             if (this.costs[j] < j + 1) {
/* 154 */               this.costs[j] = 0;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 159 */           CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
/* 160 */           PrepareItemEnchantEvent event = new PrepareItemEnchantEvent(this.player, getBukkitView(), this.world.getWorld().getBlockAt(this.position.getX(), this.position.getY(), this.position.getZ()), item, this.costs, i);
/* 161 */           event.setCancelled(!itemstack.v());
/* 162 */           this.world.getServer().getPluginManager().callEvent(event);
/*     */           
/* 164 */           if (event.isCancelled()) {
/* 165 */             for (i = 0; i < 3; i++) {
/* 166 */               this.costs[i] = 0;
/*     */             }
/* 168 */             return;
/*     */           }
/*     */           
/*     */ 
/* 172 */           for (j = 0; j < 3; j++) {
/* 173 */             if (this.costs[j] > 0) {
/* 174 */               List list = a(itemstack, j, this.costs[j]);
/*     */               
/* 176 */               if ((list != null) && (!list.isEmpty())) {
/* 177 */                 WeightedRandomEnchant weightedrandomenchant = (WeightedRandomEnchant)list.get(this.k.nextInt(list.size()));
/*     */                 
/* 179 */                 this.h[j] = (weightedrandomenchant.enchantment.id | weightedrandomenchant.level << 8);
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 184 */           b();
/*     */         }
/*     */       } else {
/* 187 */         for (int i = 0; i < 3; i++) {
/* 188 */           this.costs[i] = 0;
/* 189 */           this.h[i] = -1;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman, int i)
/*     */   {
/* 197 */     ItemStack itemstack = this.enchantSlots.getItem(0);
/* 198 */     ItemStack itemstack1 = this.enchantSlots.getItem(1);
/* 199 */     int j = i + 1;
/*     */     
/* 201 */     if (((itemstack1 == null) || (itemstack1.count < j)) && (!entityhuman.abilities.canInstantlyBuild))
/* 202 */       return false;
/* 203 */     if ((this.costs[i] > 0) && (itemstack != null) && (((entityhuman.expLevel >= j) && (entityhuman.expLevel >= this.costs[i])) || (entityhuman.abilities.canInstantlyBuild))) {
/* 204 */       if (!this.world.isClientSide) {
/* 205 */         List list = a(itemstack, i, this.costs[i]);
/*     */         
/* 207 */         if (list == null) {
/* 208 */           list = new java.util.ArrayList();
/*     */         }
/*     */         
/* 211 */         boolean flag = itemstack.getItem() == Items.BOOK;
/*     */         
/* 213 */         if (list != null)
/*     */         {
/* 215 */           Map<org.bukkit.enchantments.Enchantment, Integer> enchants = new HashMap();
/* 216 */           for (Object obj : list) {
/* 217 */             WeightedRandomEnchant instance = (WeightedRandomEnchant)obj;
/* 218 */             enchants.put(org.bukkit.enchantments.Enchantment.getById(instance.enchantment.id), Integer.valueOf(instance.level));
/*     */           }
/* 220 */           CraftItemStack item = CraftItemStack.asCraftMirror(itemstack);
/*     */           
/* 222 */           EnchantItemEvent event = new EnchantItemEvent((Player)entityhuman.getBukkitEntity(), getBukkitView(), this.world.getWorld().getBlockAt(this.position.getX(), this.position.getY(), this.position.getZ()), item, this.costs[i], enchants, i);
/* 223 */           this.world.getServer().getPluginManager().callEvent(event);
/*     */           
/* 225 */           int level = event.getExpLevelCost();
/* 226 */           if ((event.isCancelled()) || ((level > entityhuman.expLevel) && (!entityhuman.abilities.canInstantlyBuild)) || (event.getEnchantsToAdd().isEmpty())) {
/* 227 */             return false;
/*     */           }
/* 229 */           if (flag) {
/* 230 */             itemstack.setItem(Items.ENCHANTED_BOOK);
/*     */           }
/*     */           
/* 233 */           for (Map.Entry<org.bukkit.enchantments.Enchantment, Integer> entry : event.getEnchantsToAdd().entrySet()) {
/*     */             try {
/* 235 */               if (flag) {
/* 236 */                 int enchantId = ((org.bukkit.enchantments.Enchantment)entry.getKey()).getId();
/* 237 */                 if (Enchantment.getById(enchantId) != null)
/*     */                 {
/*     */ 
/*     */ 
/* 241 */                   WeightedRandomEnchant enchantment = new WeightedRandomEnchant(Enchantment.getById(enchantId), ((Integer)entry.getValue()).intValue());
/* 242 */                   Items.ENCHANTED_BOOK.a(itemstack, enchantment);
/*     */                 }
/* 244 */               } else { item.addUnsafeEnchantment((org.bukkit.enchantments.Enchantment)entry.getKey(), ((Integer)entry.getValue()).intValue());
/*     */               }
/*     */             }
/*     */             catch (IllegalArgumentException localIllegalArgumentException) {}
/*     */           }
/*     */           
/*     */ 
/* 251 */           entityhuman.enchantDone(j);
/*     */           
/*     */ 
/*     */ 
/* 255 */           if (!entityhuman.abilities.canInstantlyBuild) {
/* 256 */             itemstack1.count -= j;
/* 257 */             if (itemstack1.count <= 0) {
/* 258 */               this.enchantSlots.setItem(1, null);
/*     */             }
/*     */           }
/*     */           
/* 262 */           entityhuman.b(StatisticList.W);
/* 263 */           this.enchantSlots.update();
/* 264 */           this.f = entityhuman.cj();
/* 265 */           a(this.enchantSlots);
/*     */         }
/*     */       }
/*     */       
/* 269 */       return true;
/*     */     }
/* 271 */     return false;
/*     */   }
/*     */   
/*     */   private List<WeightedRandomEnchant> a(ItemStack itemstack, int i, int j)
/*     */   {
/* 276 */     this.k.setSeed(this.f + i);
/* 277 */     List list = EnchantmentManager.b(this.k, itemstack, j);
/*     */     
/* 279 */     if ((itemstack.getItem() == Items.BOOK) && (list != null) && (list.size() > 1)) {
/* 280 */       list.remove(this.k.nextInt(list.size()));
/*     */     }
/*     */     
/* 283 */     return list;
/*     */   }
/*     */   
/*     */   public void b(EntityHuman entityhuman) {
/* 287 */     super.b(entityhuman);
/*     */     
/* 289 */     if (this.world == null) {
/* 290 */       this.world = entityhuman.getWorld();
/*     */     }
/*     */     
/* 293 */     if (!this.world.isClientSide) {
/* 294 */       for (int i = 0; i < this.enchantSlots.getSize(); i++) {
/* 295 */         ItemStack itemstack = this.enchantSlots.splitWithoutUpdate(i);
/*     */         
/* 297 */         if (itemstack != null) {
/* 298 */           entityhuman.drop(itemstack, false);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman)
/*     */   {
/* 306 */     if (!this.checkReachable) return true;
/* 307 */     return this.world.getType(this.position).getBlock() == Blocks.ENCHANTING_TABLE;
/*     */   }
/*     */   
/*     */   public ItemStack b(EntityHuman entityhuman, int i) {
/* 311 */     ItemStack itemstack = null;
/* 312 */     Slot slot = (Slot)this.c.get(i);
/*     */     
/* 314 */     if ((slot != null) && (slot.hasItem())) {
/* 315 */       ItemStack itemstack1 = slot.getItem();
/*     */       
/* 317 */       itemstack = itemstack1.cloneItemStack();
/* 318 */       if (i == 0) {
/* 319 */         if (!a(itemstack1, 2, 38, true)) {
/* 320 */           return null;
/*     */         }
/* 322 */       } else if (i == 1) {
/* 323 */         if (!a(itemstack1, 2, 38, true)) {
/* 324 */           return null;
/*     */         }
/* 326 */       } else if ((itemstack1.getItem() == Items.DYE) && (EnumColor.fromInvColorIndex(itemstack1.getData()) == EnumColor.BLUE)) {
/* 327 */         if (!a(itemstack1, 1, 2, true)) {
/* 328 */           return null;
/*     */         }
/*     */       } else {
/* 331 */         if ((((Slot)this.c.get(0)).hasItem()) || (!((Slot)this.c.get(0)).isAllowed(itemstack1))) {
/* 332 */           return null;
/*     */         }
/*     */         
/* 335 */         if ((itemstack1.hasTag()) && (itemstack1.count == 1)) {
/* 336 */           ((Slot)this.c.get(0)).set(itemstack1.cloneItemStack());
/* 337 */           itemstack1.count = 0;
/* 338 */         } else if (itemstack1.count >= 1)
/*     */         {
/* 340 */           ItemStack clone = itemstack1.cloneItemStack();
/* 341 */           clone.count = 1;
/* 342 */           ((Slot)this.c.get(0)).set(clone);
/*     */           
/* 344 */           itemstack1.count -= 1;
/*     */         }
/*     */       }
/*     */       
/* 348 */       if (itemstack1.count == 0) {
/* 349 */         slot.set(null);
/*     */       } else {
/* 351 */         slot.f();
/*     */       }
/*     */       
/* 354 */       if (itemstack1.count == itemstack.count) {
/* 355 */         return null;
/*     */       }
/*     */       
/* 358 */       slot.a(entityhuman, itemstack1);
/*     */     }
/*     */     
/* 361 */     return itemstack;
/*     */   }
/*     */   
/*     */ 
/*     */   public CraftInventoryView getBukkitView()
/*     */   {
/* 367 */     if (this.bukkitEntity != null) {
/* 368 */       return this.bukkitEntity;
/*     */     }
/*     */     
/* 371 */     CraftInventoryEnchanting inventory = new CraftInventoryEnchanting(this.enchantSlots);
/* 372 */     this.bukkitEntity = new CraftInventoryView(this.player, inventory, this);
/* 373 */     return this.bukkitEntity;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ContainerEnchantTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */