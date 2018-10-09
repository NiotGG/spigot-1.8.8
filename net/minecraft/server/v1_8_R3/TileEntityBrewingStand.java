/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.inventory.BrewEvent;
/*     */ import org.bukkit.inventory.BrewerInventory;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class TileEntityBrewingStand extends TileEntityContainer implements IUpdatePlayerListBox, IWorldInventory
/*     */ {
/*  14 */   private static final int[] a = { 3 };
/*  15 */   private static final int[] f = { 0, 1, 2 };
/*  16 */   private ItemStack[] items = new ItemStack[4];
/*     */   public int brewTime;
/*     */   private boolean[] i;
/*     */   private Item j;
/*     */   private String k;
/*  21 */   private int lastTick = MinecraftServer.currentTick;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  26 */   public List<HumanEntity> transaction = new ArrayList();
/*  27 */   private int maxStack = 64;
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  30 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  34 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  38 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public ItemStack[] getContents() {
/*  42 */     return this.items;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  46 */     this.maxStack = size;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  51 */     return hasCustomName() ? this.k : "container.brewing";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/*  55 */     return (this.k != null) && (this.k.length() > 0);
/*     */   }
/*     */   
/*     */   public void a(String s) {
/*  59 */     this.k = s;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  63 */     return this.items.length;
/*     */   }
/*     */   
/*     */   public void c()
/*     */   {
/*  68 */     int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
/*  69 */     this.lastTick = MinecraftServer.currentTick;
/*     */     
/*  71 */     if (this.brewTime > 0) {
/*  72 */       this.brewTime -= elapsedTicks;
/*  73 */       if (this.brewTime <= 0)
/*     */       {
/*  75 */         o();
/*  76 */         update();
/*  77 */       } else if (!n()) {
/*  78 */         this.brewTime = 0;
/*  79 */         update();
/*  80 */       } else if (this.j != this.items[3].getItem()) {
/*  81 */         this.brewTime = 0;
/*  82 */         update();
/*     */       }
/*  84 */     } else if (n()) {
/*  85 */       this.brewTime = 400;
/*  86 */       this.j = this.items[3].getItem();
/*     */     }
/*     */     
/*  89 */     if (!this.world.isClientSide) {
/*  90 */       boolean[] aboolean = m();
/*     */       
/*  92 */       if (!java.util.Arrays.equals(aboolean, this.i)) {
/*  93 */         this.i = aboolean;
/*  94 */         IBlockData iblockdata = this.world.getType(getPosition());
/*     */         
/*  96 */         if (!(iblockdata.getBlock() instanceof BlockBrewingStand)) {
/*  97 */           return;
/*     */         }
/*     */         
/* 100 */         for (int i = 0; i < BlockBrewingStand.HAS_BOTTLE.length; i++) {
/* 101 */           iblockdata = iblockdata.set(BlockBrewingStand.HAS_BOTTLE[i], Boolean.valueOf(aboolean[i]));
/*     */         }
/*     */         
/* 104 */         this.world.setTypeAndData(this.position, iblockdata, 2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean n()
/*     */   {
/* 111 */     if ((this.items[3] != null) && (this.items[3].count > 0)) {
/* 112 */       ItemStack itemstack = this.items[3];
/*     */       
/* 114 */       if (!itemstack.getItem().l(itemstack)) {
/* 115 */         return false;
/*     */       }
/* 117 */       boolean flag = false;
/*     */       
/* 119 */       for (int i = 0; i < 3; i++) {
/* 120 */         if ((this.items[i] != null) && (this.items[i].getItem() == Items.POTION)) {
/* 121 */           int j = this.items[i].getData();
/* 122 */           int k = c(j, itemstack);
/*     */           
/* 124 */           if ((!ItemPotion.f(j)) && (ItemPotion.f(k))) {
/* 125 */             flag = true;
/* 126 */             break;
/*     */           }
/*     */           
/* 129 */           List list = Items.POTION.e(j);
/* 130 */           List list1 = Items.POTION.e(k);
/*     */           
/* 132 */           if (((j <= 0) || (list != list1)) && ((list == null) || ((!list.equals(list1)) && (list1 != null))) && (j != k)) {
/* 133 */             flag = true;
/* 134 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 139 */       return flag;
/*     */     }
/*     */     
/* 142 */     return false;
/*     */   }
/*     */   
/*     */   private void o()
/*     */   {
/* 147 */     if (n()) {
/* 148 */       ItemStack itemstack = this.items[3];
/*     */       
/*     */ 
/* 151 */       if (getOwner() != null) {
/* 152 */         BrewEvent event = new BrewEvent(this.world.getWorld().getBlockAt(this.position.getX(), this.position.getY(), this.position.getZ()), (BrewerInventory)getOwner().getInventory());
/* 153 */         org.bukkit.Bukkit.getPluginManager().callEvent(event);
/* 154 */         if (event.isCancelled()) {
/* 155 */           return;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 160 */       for (int i = 0; i < 3; i++) {
/* 161 */         if ((this.items[i] != null) && (this.items[i].getItem() == Items.POTION)) {
/* 162 */           int j = this.items[i].getData();
/* 163 */           int k = c(j, itemstack);
/* 164 */           List list = Items.POTION.e(j);
/* 165 */           List list1 = Items.POTION.e(k);
/*     */           
/* 167 */           if (((j <= 0) || (list != list1)) && ((list == null) || ((!list.equals(list1)) && (list1 != null)))) {
/* 168 */             if (j != k) {
/* 169 */               this.items[i].setData(k);
/*     */             }
/* 171 */           } else if ((!ItemPotion.f(j)) && (ItemPotion.f(k))) {
/* 172 */             this.items[i].setData(k);
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 177 */       if (itemstack.getItem().r()) {
/* 178 */         this.items[3] = new ItemStack(itemstack.getItem().q());
/*     */       } else {
/* 180 */         this.items[3].count -= 1;
/* 181 */         if (this.items[3].count <= 0) {
/* 182 */           this.items[3] = null;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private int c(int i, ItemStack itemstack)
/*     */   {
/* 190 */     return itemstack.getItem().l(itemstack) ? PotionBrewer.a(i, itemstack.getItem().j(itemstack)) : itemstack == null ? i : i;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 194 */     super.a(nbttagcompound);
/* 195 */     NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
/*     */     
/* 197 */     this.items = new ItemStack[getSize()];
/*     */     
/* 199 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 200 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/* 201 */       byte b0 = nbttagcompound1.getByte("Slot");
/*     */       
/* 203 */       if ((b0 >= 0) && (b0 < this.items.length)) {
/* 204 */         this.items[b0] = ItemStack.createStack(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/* 208 */     this.brewTime = nbttagcompound.getShort("BrewTime");
/* 209 */     if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
/* 210 */       this.k = nbttagcompound.getString("CustomName");
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 216 */     super.b(nbttagcompound);
/* 217 */     nbttagcompound.setShort("BrewTime", (short)this.brewTime);
/* 218 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 220 */     for (int i = 0; i < this.items.length; i++) {
/* 221 */       if (this.items[i] != null) {
/* 222 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 224 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 225 */         this.items[i].save(nbttagcompound1);
/* 226 */         nbttaglist.add(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/* 230 */     nbttagcompound.set("Items", nbttaglist);
/* 231 */     if (hasCustomName()) {
/* 232 */       nbttagcompound.setString("CustomName", this.k);
/*     */     }
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i)
/*     */   {
/* 238 */     return (i >= 0) && (i < this.items.length) ? this.items[i] : null;
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/* 242 */     if ((i >= 0) && (i < this.items.length)) {
/* 243 */       ItemStack itemstack = this.items[i];
/*     */       
/* 245 */       this.items[i] = null;
/* 246 */       return itemstack;
/*     */     }
/* 248 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i)
/*     */   {
/* 253 */     if ((i >= 0) && (i < this.items.length)) {
/* 254 */       ItemStack itemstack = this.items[i];
/*     */       
/* 256 */       this.items[i] = null;
/* 257 */       return itemstack;
/*     */     }
/* 259 */     return null;
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack)
/*     */   {
/* 264 */     if ((i >= 0) && (i < this.items.length)) {
/* 265 */       this.items[i] = itemstack;
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMaxStackSize()
/*     */   {
/* 271 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 275 */     return this.world.getTileEntity(this.position) == this;
/*     */   }
/*     */   
/*     */   public void startOpen(EntityHuman entityhuman) {}
/*     */   
/*     */   public void closeContainer(EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 283 */     return (itemstack.getItem() != Items.POTION) && (itemstack.getItem() != Items.GLASS_BOTTLE) ? false : i == 3 ? itemstack.getItem().l(itemstack) : true;
/*     */   }
/*     */   
/*     */   public boolean[] m() {
/* 287 */     boolean[] aboolean = new boolean[3];
/*     */     
/* 289 */     for (int i = 0; i < 3; i++) {
/* 290 */       if (this.items[i] != null) {
/* 291 */         aboolean[i] = true;
/*     */       }
/*     */     }
/*     */     
/* 295 */     return aboolean;
/*     */   }
/*     */   
/*     */   public int[] getSlotsForFace(EnumDirection enumdirection) {
/* 299 */     return enumdirection == EnumDirection.UP ? a : f;
/*     */   }
/*     */   
/*     */   public boolean canPlaceItemThroughFace(int i, ItemStack itemstack, EnumDirection enumdirection) {
/* 303 */     return b(i, itemstack);
/*     */   }
/*     */   
/*     */   public boolean canTakeItemThroughFace(int i, ItemStack itemstack, EnumDirection enumdirection) {
/* 307 */     return true;
/*     */   }
/*     */   
/*     */   public String getContainerName() {
/* 311 */     return "minecraft:brewing_stand";
/*     */   }
/*     */   
/*     */   public Container createContainer(PlayerInventory playerinventory, EntityHuman entityhuman) {
/* 315 */     return new ContainerBrewingStand(playerinventory, this);
/*     */   }
/*     */   
/*     */   public int getProperty(int i) {
/* 319 */     switch (i) {
/*     */     case 0: 
/* 321 */       return this.brewTime;
/*     */     }
/*     */     
/* 324 */     return 0;
/*     */   }
/*     */   
/*     */   public void b(int i, int j)
/*     */   {
/* 329 */     switch (i) {
/*     */     case 0: 
/* 331 */       this.brewTime = j;
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */   public int g()
/*     */   {
/* 338 */     return 1;
/*     */   }
/*     */   
/*     */   public void l() {
/* 342 */     for (int i = 0; i < this.items.length; i++) {
/* 343 */       this.items[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityBrewingStand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */