/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ 
/*     */ 
/*     */ public class TileEntityDispenser
/*     */   extends TileEntityContainer
/*     */   implements IInventory
/*     */ {
/*  14 */   private static final Random f = new Random();
/*  15 */   private ItemStack[] items = new ItemStack[9];
/*     */   
/*     */   protected String a;
/*     */   
/*  19 */   public List<HumanEntity> transaction = new ArrayList();
/*  20 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  23 */     return this.items;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  27 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  31 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  35 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  39 */     this.maxStack = size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getSize()
/*     */   {
/*  46 */     return 9;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  50 */     return this.items[i];
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  54 */     if (this.items[i] != null)
/*     */     {
/*     */ 
/*  57 */       if (this.items[i].count <= j) {
/*  58 */         ItemStack itemstack = this.items[i];
/*  59 */         this.items[i] = null;
/*  60 */         update();
/*  61 */         return itemstack;
/*     */       }
/*  63 */       ItemStack itemstack = this.items[i].cloneAndSubtract(j);
/*  64 */       if (this.items[i].count == 0) {
/*  65 */         this.items[i] = null;
/*     */       }
/*     */       
/*  68 */       update();
/*  69 */       return itemstack;
/*     */     }
/*     */     
/*  72 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i)
/*     */   {
/*  77 */     if (this.items[i] != null) {
/*  78 */       ItemStack itemstack = this.items[i];
/*     */       
/*  80 */       this.items[i] = null;
/*  81 */       return itemstack;
/*     */     }
/*  83 */     return null;
/*     */   }
/*     */   
/*     */   public int m()
/*     */   {
/*  88 */     int i = -1;
/*  89 */     int j = 1;
/*     */     
/*  91 */     for (int k = 0; k < this.items.length; k++) {
/*  92 */       if ((this.items[k] != null) && (f.nextInt(j++) == 0) && 
/*  93 */         (this.items[k].count != 0)) {
/*  94 */         i = k;
/*     */       }
/*     */     }
/*     */     
/*  98 */     return i;
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack) {
/* 102 */     this.items[i] = itemstack;
/* 103 */     if ((itemstack != null) && (itemstack.count > getMaxStackSize())) {
/* 104 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */     
/* 107 */     update();
/*     */   }
/*     */   
/*     */   public int addItem(ItemStack itemstack) {
/* 111 */     for (int i = 0; i < this.items.length; i++) {
/* 112 */       if ((this.items[i] == null) || (this.items[i].getItem() == null)) {
/* 113 */         setItem(i, itemstack);
/* 114 */         return i;
/*     */       }
/*     */     }
/*     */     
/* 118 */     return -1;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 122 */     return hasCustomName() ? this.a : "container.dispenser";
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 126 */     this.a = s;
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/* 130 */     return this.a != null;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 134 */     super.a(nbttagcompound);
/* 135 */     NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
/*     */     
/* 137 */     this.items = new ItemStack[getSize()];
/*     */     
/* 139 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 140 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/* 141 */       int j = nbttagcompound1.getByte("Slot") & 0xFF;
/*     */       
/* 143 */       if ((j >= 0) && (j < this.items.length)) {
/* 144 */         this.items[j] = ItemStack.createStack(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/* 148 */     if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
/* 149 */       this.a = nbttagcompound.getString("CustomName");
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 155 */     super.b(nbttagcompound);
/* 156 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 158 */     for (int i = 0; i < this.items.length; i++) {
/* 159 */       if (this.items[i] != null) {
/* 160 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 162 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 163 */         this.items[i].save(nbttagcompound1);
/* 164 */         nbttaglist.add(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/* 168 */     nbttagcompound.set("Items", nbttaglist);
/* 169 */     if (hasCustomName()) {
/* 170 */       nbttagcompound.setString("CustomName", this.a);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMaxStackSize()
/*     */   {
/* 176 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 180 */     return this.world.getTileEntity(this.position) == this;
/*     */   }
/*     */   
/*     */   public void startOpen(EntityHuman entityhuman) {}
/*     */   
/*     */   public void closeContainer(EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 188 */     return true;
/*     */   }
/*     */   
/*     */   public String getContainerName() {
/* 192 */     return "minecraft:dispenser";
/*     */   }
/*     */   
/*     */   public Container createContainer(PlayerInventory playerinventory, EntityHuman entityhuman) {
/* 196 */     return new ContainerDispenser(playerinventory, this);
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
/*     */   public void l() {
/* 210 */     for (int i = 0; i < this.items.length; i++) {
/* 211 */       this.items[i] = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityDispenser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */