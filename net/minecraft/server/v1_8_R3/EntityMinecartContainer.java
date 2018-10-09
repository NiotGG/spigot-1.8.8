/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ public abstract class EntityMinecartContainer
/*     */   extends EntityMinecartAbstract implements ITileInventory
/*     */ {
/*  13 */   private ItemStack[] items = new ItemStack[27];
/*  14 */   private boolean b = true;
/*     */   
/*     */ 
/*  17 */   public List<HumanEntity> transaction = new ArrayList();
/*  18 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  21 */     return this.items;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  25 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  29 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  33 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner() {
/*  37 */     Entity cart = getBukkitEntity();
/*  38 */     if ((cart instanceof InventoryHolder)) return (InventoryHolder)cart;
/*  39 */     return null;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  43 */     this.maxStack = size;
/*     */   }
/*     */   
/*     */   public EntityMinecartContainer(World world)
/*     */   {
/*  48 */     super(world);
/*     */   }
/*     */   
/*     */   public EntityMinecartContainer(World world, double d0, double d1, double d2) {
/*  52 */     super(world, d0, d1, d2);
/*     */   }
/*     */   
/*     */   public void a(DamageSource damagesource) {
/*  56 */     super.a(damagesource);
/*  57 */     if (this.world.getGameRules().getBoolean("doEntityDrops")) {
/*  58 */       InventoryUtils.dropEntity(this.world, this, this);
/*     */     }
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i)
/*     */   {
/*  64 */     return this.items[i];
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  68 */     if (this.items[i] != null)
/*     */     {
/*     */ 
/*  71 */       if (this.items[i].count <= j) {
/*  72 */         ItemStack itemstack = this.items[i];
/*  73 */         this.items[i] = null;
/*  74 */         return itemstack;
/*     */       }
/*  76 */       ItemStack itemstack = this.items[i].cloneAndSubtract(j);
/*  77 */       if (this.items[i].count == 0) {
/*  78 */         this.items[i] = null;
/*     */       }
/*     */       
/*  81 */       return itemstack;
/*     */     }
/*     */     
/*  84 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i)
/*     */   {
/*  89 */     if (this.items[i] != null) {
/*  90 */       ItemStack itemstack = this.items[i];
/*     */       
/*  92 */       this.items[i] = null;
/*  93 */       return itemstack;
/*     */     }
/*  95 */     return null;
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack)
/*     */   {
/* 100 */     this.items[i] = itemstack;
/* 101 */     if ((itemstack != null) && (itemstack.count > getMaxStackSize())) {
/* 102 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */   }
/*     */   
/*     */   public void update() {}
/*     */   
/*     */   public boolean a(EntityHuman entityhuman)
/*     */   {
/* 110 */     return !this.dead;
/*     */   }
/*     */   
/*     */   public void startOpen(EntityHuman entityhuman) {}
/*     */   
/*     */   public void closeContainer(EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 118 */     return true;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 122 */     return hasCustomName() ? getCustomName() : "container.minecart";
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 126 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public void c(int i)
/*     */   {
/* 131 */     for (HumanEntity human : new ArrayList(this.transaction))
/*     */     {
/* 133 */       human.closeInventory();
/*     */     }
/*     */     
/* 136 */     this.b = false;
/* 137 */     super.c(i);
/*     */   }
/*     */   
/*     */   public void die() {
/* 141 */     if (this.b) {
/* 142 */       InventoryUtils.dropEntity(this.world, this, this);
/*     */     }
/*     */     
/* 145 */     super.die();
/*     */   }
/*     */   
/*     */   protected void b(NBTTagCompound nbttagcompound) {
/* 149 */     super.b(nbttagcompound);
/* 150 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 152 */     for (int i = 0; i < this.items.length; i++) {
/* 153 */       if (this.items[i] != null) {
/* 154 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 156 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 157 */         this.items[i].save(nbttagcompound1);
/* 158 */         nbttaglist.add(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/* 162 */     nbttagcompound.set("Items", nbttaglist);
/*     */   }
/*     */   
/*     */   protected void a(NBTTagCompound nbttagcompound) {
/* 166 */     super.a(nbttagcompound);
/* 167 */     NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
/*     */     
/* 169 */     this.items = new ItemStack[getSize()];
/*     */     
/* 171 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 172 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/* 173 */       int j = nbttagcompound1.getByte("Slot") & 0xFF;
/*     */       
/* 175 */       if ((j >= 0) && (j < this.items.length)) {
/* 176 */         this.items[j] = ItemStack.createStack(nbttagcompound1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean e(EntityHuman entityhuman)
/*     */   {
/* 183 */     if (!this.world.isClientSide) {
/* 184 */       entityhuman.openContainer(this);
/*     */     }
/*     */     
/* 187 */     return true;
/*     */   }
/*     */   
/*     */   protected void o() {
/* 191 */     int i = 15 - Container.b(this);
/* 192 */     float f = 0.98F + i * 0.001F;
/*     */     
/* 194 */     this.motX *= f;
/* 195 */     this.motY *= 0.0D;
/* 196 */     this.motZ *= f;
/*     */   }
/*     */   
/*     */   public int getProperty(int i) {
/* 200 */     return 0;
/*     */   }
/*     */   
/*     */   public void b(int i, int j) {}
/*     */   
/*     */   public int g() {
/* 206 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean r_() {
/* 210 */     return false;
/*     */   }
/*     */   
/*     */   public void a(ChestLock chestlock) {}
/*     */   
/*     */   public ChestLock i() {
/* 216 */     return ChestLock.a;
/*     */   }
/*     */   
/*     */   public void l() {
/* 220 */     for (int i = 0; i < this.items.length; i++) {
/* 221 */       this.items[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityMinecartContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */