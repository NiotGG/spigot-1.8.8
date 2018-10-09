/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*     */ import net.minecraft.server.v1_8_R3.IInventory;
/*     */ import net.minecraft.server.v1_8_R3.PlayerConnection;
/*     */ import net.minecraft.server.v1_8_R3.Slot;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ 
/*     */ public class CraftContainer extends net.minecraft.server.v1_8_R3.Container
/*     */ {
/*     */   private final InventoryView view;
/*     */   private InventoryType cachedType;
/*     */   private String cachedTitle;
/*     */   private final int cachedSize;
/*     */   
/*     */   public CraftContainer(InventoryView view, int id)
/*     */   {
/*  23 */     this.view = view;
/*  24 */     this.windowId = id;
/*     */     
/*  26 */     IInventory top = ((CraftInventory)view.getTopInventory()).getInventory();
/*  27 */     IInventory bottom = ((CraftInventory)view.getBottomInventory()).getInventory();
/*  28 */     this.cachedType = view.getType();
/*  29 */     this.cachedTitle = view.getTitle();
/*  30 */     this.cachedSize = getSize();
/*  31 */     setupSlots(top, bottom);
/*     */   }
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
/*     */   public CraftContainer(Inventory inventory, final HumanEntity player, int id)
/*     */   {
/*  55 */     this(new InventoryView()
/*     */     {
/*     */       public Inventory getTopInventory()
/*     */       {
/*  38 */         return CraftContainer.this;
/*     */       }
/*     */       
/*     */       public Inventory getBottomInventory()
/*     */       {
/*  43 */         return player.getInventory();
/*     */       }
/*     */       
/*     */       public HumanEntity getPlayer()
/*     */       {
/*  48 */         return player;
/*     */       }
/*     */       
/*     */       public InventoryType getType()
/*     */       {
/*  53 */         return CraftContainer.this.getType();
/*     */       }
/*  55 */     }, id);
/*     */   }
/*     */   
/*     */   public InventoryView getBukkitView()
/*     */   {
/*  60 */     return this.view;
/*     */   }
/*     */   
/*     */   private int getSize() {
/*  64 */     return this.view.getTopInventory().getSize();
/*     */   }
/*     */   
/*     */   public boolean c(EntityHuman entityhuman)
/*     */   {
/*  69 */     if ((this.cachedType == this.view.getType()) && (this.cachedSize == getSize()) && (this.cachedTitle.equals(this.view.getTitle()))) {
/*  70 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  75 */     boolean typeChanged = this.cachedType != this.view.getType();
/*  76 */     this.cachedType = this.view.getType();
/*  77 */     this.cachedTitle = this.view.getTitle();
/*  78 */     if ((this.view.getPlayer() instanceof CraftPlayer)) {
/*  79 */       CraftPlayer player = (CraftPlayer)this.view.getPlayer();
/*  80 */       String type = getNotchInventoryType(this.cachedType);
/*  81 */       IInventory top = ((CraftInventory)this.view.getTopInventory()).getInventory();
/*  82 */       IInventory bottom = ((CraftInventory)this.view.getBottomInventory()).getInventory();
/*  83 */       this.b.clear();
/*  84 */       this.c.clear();
/*  85 */       if (typeChanged) {
/*  86 */         setupSlots(top, bottom);
/*     */       }
/*  88 */       int size = getSize();
/*  89 */       player.getHandle().playerConnection.sendPacket(new net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow(this.windowId, type, new net.minecraft.server.v1_8_R3.ChatComponentText(this.cachedTitle), size));
/*  90 */       player.updateInventory();
/*     */     }
/*  92 */     return true;
/*     */   }
/*     */   
/*     */   public static String getNotchInventoryType(InventoryType type) {
/*  96 */     switch (type) {
/*     */     case CRAFTING: 
/*  98 */       return "minecraft:crafting_table";
/*     */     case CHEST: 
/* 100 */       return "minecraft:furnace";
/*     */     case BEACON: 
/* 102 */       return "minecraft:dispenser";
/*     */     case DISPENSER: 
/* 104 */       return "minecraft:enchanting_table";
/*     */     case DROPPER: 
/* 106 */       return "minecraft:brewing_stand";
/*     */     case PLAYER: 
/* 108 */       return "minecraft:beacon";
/*     */     case MERCHANT: 
/* 110 */       return "minecraft:anvil";
/*     */     case WORKBENCH: 
/* 112 */       return "minecraft:hopper";
/*     */     }
/* 114 */     return "minecraft:chest";
/*     */   }
/*     */   
/*     */   private void setupSlots(IInventory top, IInventory bottom)
/*     */   {
/* 119 */     switch (this.cachedType) {
/*     */     case ENDER_CHEST: 
/*     */       break;
/*     */     case ANVIL: 
/*     */     case ENCHANTING: 
/* 124 */       setupChest(top, bottom);
/* 125 */       break;
/*     */     case BEACON: 
/* 127 */       setupDispenser(top, bottom);
/* 128 */       break;
/*     */     case CHEST: 
/* 130 */       setupFurnace(top, bottom);
/* 131 */       break;
/*     */     case CRAFTING: 
/*     */     case CREATIVE: 
/* 134 */       setupWorkbench(top, bottom);
/* 135 */       break;
/*     */     case DISPENSER: 
/* 137 */       setupEnchanting(top, bottom);
/* 138 */       break;
/*     */     case DROPPER: 
/* 140 */       setupBrewing(top, bottom);
/* 141 */       break;
/*     */     case WORKBENCH: 
/* 143 */       setupHopper(top, bottom);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setupChest(IInventory top, IInventory bottom)
/*     */   {
/* 149 */     int rows = top.getSize() / 9;
/*     */     
/*     */ 
/*     */ 
/* 153 */     int i = (rows - 4) * 18;
/* 154 */     for (int row = 0; row < rows; row++) {
/* 155 */       for (int col = 0; col < 9; col++) {
/* 156 */         a(new Slot(top, col + row * 9, 8 + col * 18, 18 + row * 18));
/*     */       }
/*     */     }
/*     */     
/* 160 */     for (row = 0; row < 3; row++) {
/* 161 */       for (int col = 0; col < 9; col++) {
/* 162 */         a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 103 + row * 18 + i));
/*     */       }
/*     */     }
/*     */     
/* 166 */     for (int col = 0; col < 9; col++) {
/* 167 */       a(new Slot(bottom, col, 8 + col * 18, 161 + i));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void setupWorkbench(IInventory top, IInventory bottom)
/*     */   {
/* 174 */     a(new Slot(top, 0, 124, 35));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 179 */     for (int row = 0; row < 3; row++) {
/* 180 */       for (int col = 0; col < 3; col++) {
/* 181 */         a(new Slot(top, 1 + col + row * 3, 30 + col * 18, 17 + row * 18));
/*     */       }
/*     */     }
/*     */     
/* 185 */     for (row = 0; row < 3; row++) {
/* 186 */       for (int col = 0; col < 9; col++) {
/* 187 */         a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
/*     */       }
/*     */     }
/*     */     
/* 191 */     for (int col = 0; col < 9; col++) {
/* 192 */       a(new Slot(bottom, col, 8 + col * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void setupFurnace(IInventory top, IInventory bottom)
/*     */   {
/* 199 */     a(new Slot(top, 0, 56, 17));
/* 200 */     a(new Slot(top, 1, 56, 53));
/* 201 */     a(new Slot(top, 2, 116, 35));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 206 */     for (int row = 0; row < 3; row++) {
/* 207 */       for (int col = 0; col < 9; col++) {
/* 208 */         a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
/*     */       }
/*     */     }
/*     */     
/* 212 */     for (int col = 0; col < 9; col++) {
/* 213 */       a(new Slot(bottom, col, 8 + col * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void setupDispenser(IInventory top, IInventory bottom)
/*     */   {
/* 223 */     for (int row = 0; row < 3; row++) {
/* 224 */       for (int col = 0; col < 3; col++) {
/* 225 */         a(new Slot(top, col + row * 3, 61 + col * 18, 17 + row * 18));
/*     */       }
/*     */     }
/*     */     
/* 229 */     for (row = 0; row < 3; row++) {
/* 230 */       for (int col = 0; col < 9; col++) {
/* 231 */         a(new Slot(bottom, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
/*     */       }
/*     */     }
/*     */     
/* 235 */     for (int col = 0; col < 9; col++) {
/* 236 */       a(new Slot(bottom, col, 8 + col * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void setupEnchanting(IInventory top, IInventory bottom)
/*     */   {
/* 243 */     a(new Slot(top, 0, 25, 47));
/*     */     
/*     */ 
/*     */ 
/* 247 */     for (int row = 0; row < 3; row++) {
/* 248 */       for (int i1 = 0; i1 < 9; i1++) {
/* 249 */         a(new Slot(bottom, i1 + row * 9 + 9, 8 + i1 * 18, 84 + row * 18));
/*     */       }
/*     */     }
/*     */     
/* 253 */     for (row = 0; row < 9; row++) {
/* 254 */       a(new Slot(bottom, row, 8 + row * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void setupBrewing(IInventory top, IInventory bottom)
/*     */   {
/* 261 */     a(new Slot(top, 0, 56, 46));
/* 262 */     a(new Slot(top, 1, 79, 53));
/* 263 */     a(new Slot(top, 2, 102, 46));
/* 264 */     a(new Slot(top, 3, 79, 17));
/*     */     
/*     */ 
/*     */ 
/* 268 */     for (int i = 0; i < 3; i++) {
/* 269 */       for (int j = 0; j < 9; j++) {
/* 270 */         a(new Slot(bottom, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     }
/*     */     
/* 274 */     for (i = 0; i < 9; i++) {
/* 275 */       a(new Slot(bottom, i, 8 + i * 18, 142));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void setupHopper(IInventory top, IInventory bottom)
/*     */   {
/* 282 */     byte b0 = 51;
/*     */     
/*     */ 
/*     */ 
/* 286 */     for (int i = 0; i < top.getSize(); i++) {
/* 287 */       a(new Slot(top, i, 44 + i * 18, 20));
/*     */     }
/*     */     
/* 290 */     for (i = 0; i < 3; i++) {
/* 291 */       for (int j = 0; j < 9; j++) {
/* 292 */         a(new Slot(bottom, j + i * 9 + 9, 8 + j * 18, i * 18 + b0));
/*     */       }
/*     */     }
/*     */     
/* 296 */     for (i = 0; i < 9; i++) {
/* 297 */       a(new Slot(bottom, i, 8 + i * 18, 58 + b0));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entity)
/*     */   {
/* 303 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */