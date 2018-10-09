/*     */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*     */ 
/*     */ import net.minecraft.server.v1_8_R3.BlockAnvil.TileEntityContainerAnvil;
/*     */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*     */ import net.minecraft.server.v1_8_R3.Container;
/*     */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*     */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*     */ import net.minecraft.server.v1_8_R3.PlayerConnection;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityBeacon;
/*     */ import net.minecraft.server.v1_8_R3.TileEntityFurnace;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftContainer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryPlayer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ import org.bukkit.inventory.InventoryView.Property;
/*     */ import org.bukkit.permissions.PermissibleBase;
/*     */ import org.bukkit.permissions.Permission;
/*     */ import org.bukkit.permissions.PermissionAttachment;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class CraftHumanEntity extends CraftLivingEntity implements org.bukkit.entity.HumanEntity
/*     */ {
/*     */   private CraftInventoryPlayer inventory;
/*     */   private final CraftInventory enderChest;
/*  34 */   protected final PermissibleBase perm = new PermissibleBase(this);
/*     */   private boolean op;
/*     */   private GameMode mode;
/*     */   
/*     */   public CraftHumanEntity(CraftServer server, EntityHuman entity) {
/*  39 */     super(server, entity);
/*  40 */     this.mode = server.getDefaultGameMode();
/*  41 */     this.inventory = new CraftInventoryPlayer(entity.inventory);
/*  42 */     this.enderChest = new CraftInventory(entity.getEnderChest());
/*     */   }
/*     */   
/*     */   public String getName() {
/*  46 */     return getHandle().getName();
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.PlayerInventory getInventory() {
/*  50 */     return this.inventory;
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.EntityEquipment getEquipment() {
/*  54 */     return this.inventory;
/*     */   }
/*     */   
/*     */   public Inventory getEnderChest() {
/*  58 */     return this.enderChest;
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack getItemInHand() {
/*  62 */     return getInventory().getItemInHand();
/*     */   }
/*     */   
/*     */   public void setItemInHand(org.bukkit.inventory.ItemStack item) {
/*  66 */     getInventory().setItemInHand(item);
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack getItemOnCursor() {
/*  70 */     return CraftItemStack.asCraftMirror(getHandle().inventory.getCarried());
/*     */   }
/*     */   
/*     */   public void setItemOnCursor(org.bukkit.inventory.ItemStack item) {
/*  74 */     net.minecraft.server.v1_8_R3.ItemStack stack = CraftItemStack.asNMSCopy(item);
/*  75 */     getHandle().inventory.setCarried(stack);
/*  76 */     if ((this instanceof CraftPlayer)) {
/*  77 */       ((EntityPlayer)getHandle()).broadcastCarriedItem();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isSleeping() {
/*  82 */     return getHandle().sleeping;
/*     */   }
/*     */   
/*     */   public int getSleepTicks() {
/*  86 */     return getHandle().sleepTicks;
/*     */   }
/*     */   
/*     */   public boolean isOp() {
/*  90 */     return this.op;
/*     */   }
/*     */   
/*     */   public boolean isPermissionSet(String name) {
/*  94 */     return this.perm.isPermissionSet(name);
/*     */   }
/*     */   
/*     */   public boolean isPermissionSet(Permission perm) {
/*  98 */     return this.perm.isPermissionSet(perm);
/*     */   }
/*     */   
/*     */   public boolean hasPermission(String name) {
/* 102 */     return this.perm.hasPermission(name);
/*     */   }
/*     */   
/*     */   public boolean hasPermission(Permission perm) {
/* 106 */     return this.perm.hasPermission(perm);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
/* 110 */     return this.perm.addAttachment(plugin, name, value);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin) {
/* 114 */     return this.perm.addAttachment(plugin);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
/* 118 */     return this.perm.addAttachment(plugin, name, value, ticks);
/*     */   }
/*     */   
/*     */   public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
/* 122 */     return this.perm.addAttachment(plugin, ticks);
/*     */   }
/*     */   
/*     */   public void removeAttachment(PermissionAttachment attachment) {
/* 126 */     this.perm.removeAttachment(attachment);
/*     */   }
/*     */   
/*     */   public void recalculatePermissions() {
/* 130 */     this.perm.recalculatePermissions();
/*     */   }
/*     */   
/*     */   public void setOp(boolean value) {
/* 134 */     this.op = value;
/* 135 */     this.perm.recalculatePermissions();
/*     */   }
/*     */   
/*     */   public java.util.Set<org.bukkit.permissions.PermissionAttachmentInfo> getEffectivePermissions() {
/* 139 */     return this.perm.getEffectivePermissions();
/*     */   }
/*     */   
/*     */   public GameMode getGameMode() {
/* 143 */     return this.mode;
/*     */   }
/*     */   
/*     */   public void setGameMode(GameMode mode) {
/* 147 */     if (mode == null) {
/* 148 */       throw new IllegalArgumentException("Mode cannot be null");
/*     */     }
/*     */     
/* 151 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   public EntityHuman getHandle()
/*     */   {
/* 156 */     return (EntityHuman)this.entity;
/*     */   }
/*     */   
/*     */   public void setHandle(EntityHuman entity) {
/* 160 */     super.setHandle(entity);
/* 161 */     this.inventory = new CraftInventoryPlayer(entity.inventory);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 166 */     return "CraftHumanEntity{id=" + getEntityId() + "name=" + getName() + '}';
/*     */   }
/*     */   
/*     */   public InventoryView getOpenInventory() {
/* 170 */     return getHandle().activeContainer.getBukkitView();
/*     */   }
/*     */   
/*     */   public InventoryView openInventory(Inventory inventory) {
/* 174 */     if (!(getHandle() instanceof EntityPlayer)) return null;
/* 175 */     EntityPlayer player = (EntityPlayer)getHandle();
/* 176 */     org.bukkit.event.inventory.InventoryType type = inventory.getType();
/* 177 */     Container formerContainer = getHandle().activeContainer;
/*     */     
/* 179 */     net.minecraft.server.v1_8_R3.IInventory iinventory = (inventory instanceof CraftInventory) ? ((CraftInventory)inventory).getInventory() : new org.bukkit.craftbukkit.v1_8_R3.inventory.InventoryWrapper(inventory);
/*     */     
/* 181 */     switch (type) {
/*     */     case ANVIL: 
/*     */     case ENCHANTING: 
/*     */     case HOPPER: 
/* 185 */       getHandle().openContainer(iinventory);
/* 186 */       break;
/*     */     case BEACON: 
/* 188 */       if ((iinventory instanceof net.minecraft.server.v1_8_R3.TileEntityDispenser)) {
/* 189 */         getHandle().openContainer((net.minecraft.server.v1_8_R3.TileEntityDispenser)iinventory);
/*     */       } else {
/* 191 */         openCustomInventory(inventory, player, "minecraft:dispenser");
/*     */       }
/* 193 */       break;
/*     */     case BREWING: 
/* 195 */       if ((iinventory instanceof net.minecraft.server.v1_8_R3.TileEntityDropper)) {
/* 196 */         getHandle().openContainer((net.minecraft.server.v1_8_R3.TileEntityDropper)iinventory);
/*     */       } else {
/* 198 */         openCustomInventory(inventory, player, "minecraft:dropper");
/*     */       }
/* 200 */       break;
/*     */     case CHEST: 
/* 202 */       if ((iinventory instanceof TileEntityFurnace)) {
/* 203 */         getHandle().openContainer((TileEntityFurnace)iinventory);
/*     */       } else {
/* 205 */         openCustomInventory(inventory, player, "minecraft:furnace");
/*     */       }
/* 207 */       break;
/*     */     case CRAFTING: 
/* 209 */       openCustomInventory(inventory, player, "minecraft:crafting_table");
/* 210 */       break;
/*     */     case DROPPER: 
/* 212 */       if ((iinventory instanceof net.minecraft.server.v1_8_R3.TileEntityBrewingStand)) {
/* 213 */         getHandle().openContainer((net.minecraft.server.v1_8_R3.TileEntityBrewingStand)iinventory);
/*     */       } else {
/* 215 */         openCustomInventory(inventory, player, "minecraft:brewing_stand");
/*     */       }
/* 217 */       break;
/*     */     case DISPENSER: 
/* 219 */       openCustomInventory(inventory, player, "minecraft:enchanting_table");
/* 220 */       break;
/*     */     case WORKBENCH: 
/* 222 */       if ((iinventory instanceof net.minecraft.server.v1_8_R3.TileEntityHopper)) {
/* 223 */         getHandle().openContainer((net.minecraft.server.v1_8_R3.TileEntityHopper)iinventory);
/* 224 */       } else if ((iinventory instanceof net.minecraft.server.v1_8_R3.EntityMinecartHopper)) {
/* 225 */         getHandle().openContainer((net.minecraft.server.v1_8_R3.EntityMinecartHopper)iinventory);
/*     */       } else {
/* 227 */         openCustomInventory(inventory, player, "minecraft:hopper");
/*     */       }
/* 229 */       break;
/*     */     case PLAYER: 
/* 231 */       if ((iinventory instanceof TileEntityBeacon)) {
/* 232 */         getHandle().openContainer((TileEntityBeacon)iinventory);
/*     */       } else {
/* 234 */         openCustomInventory(inventory, player, "minecraft:beacon");
/*     */       }
/* 236 */       break;
/*     */     case MERCHANT: 
/* 238 */       if ((iinventory instanceof BlockAnvil.TileEntityContainerAnvil)) {
/* 239 */         getHandle().openTileEntity((BlockAnvil.TileEntityContainerAnvil)iinventory);
/*     */       } else {
/* 241 */         openCustomInventory(inventory, player, "minecraft:anvil");
/*     */       }
/* 243 */       break;
/*     */     case CREATIVE: 
/*     */     case ENDER_CHEST: 
/* 246 */       throw new IllegalArgumentException("Can't open a " + type + " inventory!");
/*     */     }
/* 248 */     if (getHandle().activeContainer == formerContainer) {
/* 249 */       return null;
/*     */     }
/* 251 */     getHandle().activeContainer.checkReachable = false;
/* 252 */     return getHandle().activeContainer.getBukkitView();
/*     */   }
/*     */   
/*     */   private void openCustomInventory(Inventory inventory, EntityPlayer player, String windowType) {
/* 256 */     if (player.playerConnection == null) return;
/* 257 */     Container container = new CraftContainer(inventory, this, player.nextContainerCounter());
/*     */     
/* 259 */     container = CraftEventFactory.callInventoryOpenEvent(player, container);
/* 260 */     if (container == null) { return;
/*     */     }
/* 262 */     String title = container.getBukkitView().getTitle();
/* 263 */     int size = container.getBukkitView().getTopInventory().getSize();
/*     */     
/*     */ 
/* 266 */     if ((windowType.equals("minecraft:crafting_table")) || 
/* 267 */       (windowType.equals("minecraft:anvil")) || 
/* 268 */       (windowType.equals("minecraft:enchanting_table")))
/*     */     {
/* 270 */       size = 0;
/*     */     }
/*     */     
/* 273 */     player.playerConnection.sendPacket(new net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow(container.windowId, windowType, new net.minecraft.server.v1_8_R3.ChatComponentText(title), size));
/* 274 */     getHandle().activeContainer = container;
/* 275 */     getHandle().activeContainer.addSlotListener(player);
/*     */   }
/*     */   
/*     */   public InventoryView openWorkbench(Location location, boolean force) {
/* 279 */     if (!force) {
/* 280 */       Block block = location.getBlock();
/* 281 */       if (block.getType() != Material.WORKBENCH) {
/* 282 */         return null;
/*     */       }
/*     */     }
/* 285 */     if (location == null) {
/* 286 */       location = getLocation();
/*     */     }
/* 288 */     getHandle().openTileEntity(new net.minecraft.server.v1_8_R3.BlockWorkbench.TileEntityContainerWorkbench(getHandle().world, new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ())));
/* 289 */     if (force) {
/* 290 */       getHandle().activeContainer.checkReachable = false;
/*     */     }
/* 292 */     return getHandle().activeContainer.getBukkitView();
/*     */   }
/*     */   
/*     */   public InventoryView openEnchanting(Location location, boolean force) {
/* 296 */     if (!force) {
/* 297 */       Block block = location.getBlock();
/* 298 */       if (block.getType() != Material.ENCHANTMENT_TABLE) {
/* 299 */         return null;
/*     */       }
/*     */     }
/* 302 */     if (location == null) {
/* 303 */       location = getLocation();
/*     */     }
/*     */     
/*     */ 
/* 307 */     net.minecraft.server.v1_8_R3.TileEntity container = getHandle().world.getTileEntity(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
/* 308 */     if ((container == null) && (force)) {
/* 309 */       container = new net.minecraft.server.v1_8_R3.TileEntityEnchantTable();
/*     */     }
/* 311 */     getHandle().openTileEntity((net.minecraft.server.v1_8_R3.ITileEntityContainer)container);
/*     */     
/* 313 */     if (force) {
/* 314 */       getHandle().activeContainer.checkReachable = false;
/*     */     }
/* 316 */     return getHandle().activeContainer.getBukkitView();
/*     */   }
/*     */   
/*     */   public void openInventory(InventoryView inventory) {
/* 320 */     if (!(getHandle() instanceof EntityPlayer)) return;
/* 321 */     if (((EntityPlayer)getHandle()).playerConnection == null) return;
/* 322 */     if (getHandle().activeContainer != getHandle().defaultContainer)
/*     */     {
/* 324 */       ((EntityPlayer)getHandle()).playerConnection.a(new net.minecraft.server.v1_8_R3.PacketPlayInCloseWindow(getHandle().activeContainer.windowId));
/*     */     }
/* 326 */     EntityPlayer player = (EntityPlayer)getHandle();
/*     */     Container container;
/* 328 */     if ((inventory instanceof CraftInventoryView)) {
/* 329 */       container = ((CraftInventoryView)inventory).getHandle();
/*     */     } else {
/* 331 */       container = new CraftContainer(inventory, player.nextContainerCounter());
/*     */     }
/*     */     
/*     */ 
/* 335 */     Container container = CraftEventFactory.callInventoryOpenEvent(player, container);
/* 336 */     if (container == null) {
/* 337 */       return;
/*     */     }
/*     */     
/*     */ 
/* 341 */     org.bukkit.event.inventory.InventoryType type = inventory.getType();
/* 342 */     String windowType = CraftContainer.getNotchInventoryType(type);
/* 343 */     String title = inventory.getTitle();
/* 344 */     int size = inventory.getTopInventory().getSize();
/* 345 */     player.playerConnection.sendPacket(new net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow(container.windowId, windowType, new net.minecraft.server.v1_8_R3.ChatComponentText(title), size));
/* 346 */     player.activeContainer = container;
/* 347 */     player.activeContainer.addSlotListener(player);
/*     */   }
/*     */   
/*     */   public void closeInventory() {
/* 351 */     getHandle().closeInventory();
/*     */   }
/*     */   
/*     */   public boolean isBlocking() {
/* 355 */     return getHandle().isBlocking();
/*     */   }
/*     */   
/*     */   public boolean setWindowProperty(InventoryView.Property prop, int value) {
/* 359 */     return false;
/*     */   }
/*     */   
/*     */   public int getExpToLevel() {
/* 363 */     return getHandle().getExpToLevel();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftHumanEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */