/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_8_R3.ChatComponentText;
/*     */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*     */ import net.minecraft.server.v1_8_R3.IChatBaseComponent;
/*     */ import net.minecraft.server.v1_8_R3.IInventory;
/*     */ import net.minecraft.server.v1_8_R3.ItemStack;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ public class CraftInventoryCustom extends CraftInventory
/*     */ {
/*     */   public CraftInventoryCustom(InventoryHolder owner, InventoryType type)
/*     */   {
/*  20 */     super(new MinecraftInventory(owner, type));
/*     */   }
/*     */   
/*     */   public CraftInventoryCustom(InventoryHolder owner, InventoryType type, String title) {
/*  24 */     super(new MinecraftInventory(owner, type, title));
/*     */   }
/*     */   
/*     */   public CraftInventoryCustom(InventoryHolder owner, int size) {
/*  28 */     super(new MinecraftInventory(owner, size));
/*     */   }
/*     */   
/*     */   public CraftInventoryCustom(InventoryHolder owner, int size, String title) {
/*  32 */     super(new MinecraftInventory(owner, size, title));
/*     */   }
/*     */   
/*     */   static class MinecraftInventory implements IInventory {
/*     */     private final ItemStack[] items;
/*  37 */     private int maxStack = 64;
/*     */     private final List<HumanEntity> viewers;
/*     */     private final String title;
/*     */     private InventoryType type;
/*     */     private final InventoryHolder owner;
/*     */     
/*     */     public MinecraftInventory(InventoryHolder owner, InventoryType type) {
/*  44 */       this(owner, type.getDefaultSize(), type.getDefaultTitle());
/*  45 */       this.type = type;
/*     */     }
/*     */     
/*     */     public MinecraftInventory(InventoryHolder owner, InventoryType type, String title) {
/*  49 */       this(owner, type.getDefaultSize(), title);
/*  50 */       this.type = type;
/*     */     }
/*     */     
/*     */     public MinecraftInventory(InventoryHolder owner, int size) {
/*  54 */       this(owner, size, "Chest");
/*     */     }
/*     */     
/*     */     public MinecraftInventory(InventoryHolder owner, int size, String title) {
/*  58 */       Validate.notNull(title, "Title cannot be null");
/*  59 */       Validate.isTrue(title.length() <= 32, "Title cannot be longer than 32 characters");
/*  60 */       this.items = new ItemStack[size];
/*  61 */       this.title = title;
/*  62 */       this.viewers = new ArrayList();
/*  63 */       this.owner = owner;
/*  64 */       this.type = InventoryType.CHEST;
/*     */     }
/*     */     
/*     */     public int getSize() {
/*  68 */       return this.items.length;
/*     */     }
/*     */     
/*     */     public ItemStack getItem(int i) {
/*  72 */       return this.items[i];
/*     */     }
/*     */     
/*     */     public ItemStack splitStack(int i, int j) {
/*  76 */       ItemStack stack = getItem(i);
/*     */       
/*  78 */       if (stack == null) return null;
/*  79 */       ItemStack result; ItemStack result; if (stack.count <= j) {
/*  80 */         setItem(i, null);
/*  81 */         result = stack;
/*     */       } else {
/*  83 */         result = CraftItemStack.copyNMSStack(stack, j);
/*  84 */         stack.count -= j;
/*     */       }
/*  86 */       update();
/*  87 */       return result;
/*     */     }
/*     */     
/*     */     public ItemStack splitWithoutUpdate(int i) {
/*  91 */       ItemStack stack = getItem(i);
/*     */       
/*  93 */       if (stack == null) return null;
/*  94 */       ItemStack result; ItemStack result; if (stack.count <= 1) {
/*  95 */         setItem(i, null);
/*  96 */         result = stack;
/*     */       } else {
/*  98 */         result = CraftItemStack.copyNMSStack(stack, 1);
/*  99 */         stack.count -= 1;
/*     */       }
/* 101 */       return result;
/*     */     }
/*     */     
/*     */     public void setItem(int i, ItemStack itemstack) {
/* 105 */       this.items[i] = itemstack;
/* 106 */       if ((itemstack != null) && (getMaxStackSize() > 0) && (itemstack.count > getMaxStackSize())) {
/* 107 */         itemstack.count = getMaxStackSize();
/*     */       }
/*     */     }
/*     */     
/*     */     public int getMaxStackSize() {
/* 112 */       return this.maxStack;
/*     */     }
/*     */     
/*     */     public void setMaxStackSize(int size) {
/* 116 */       this.maxStack = size;
/*     */     }
/*     */     
/*     */     public void update() {}
/*     */     
/*     */     public boolean a(EntityHuman entityhuman) {
/* 122 */       return true;
/*     */     }
/*     */     
/*     */     public ItemStack[] getContents() {
/* 126 */       return this.items;
/*     */     }
/*     */     
/*     */     public void onOpen(CraftHumanEntity who) {
/* 130 */       this.viewers.add(who);
/*     */     }
/*     */     
/*     */     public void onClose(CraftHumanEntity who) {
/* 134 */       this.viewers.remove(who);
/*     */     }
/*     */     
/*     */     public List<HumanEntity> getViewers() {
/* 138 */       return this.viewers;
/*     */     }
/*     */     
/*     */     public InventoryType getType() {
/* 142 */       return this.type;
/*     */     }
/*     */     
/*     */     public InventoryHolder getOwner() {
/* 146 */       return this.owner;
/*     */     }
/*     */     
/*     */     public boolean b(int i, ItemStack itemstack) {
/* 150 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void startOpen(EntityHuman entityHuman) {}
/*     */     
/*     */ 
/*     */ 
/*     */     public void closeContainer(EntityHuman entityHuman) {}
/*     */     
/*     */ 
/*     */ 
/*     */     public int getProperty(int i)
/*     */     {
/* 165 */       return 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void b(int i, int i1) {}
/*     */     
/*     */ 
/*     */     public int g()
/*     */     {
/* 175 */       return 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void l() {}
/*     */     
/*     */ 
/*     */     public String getName()
/*     */     {
/* 185 */       return this.title;
/*     */     }
/*     */     
/*     */     public boolean hasCustomName()
/*     */     {
/* 190 */       return this.title != null;
/*     */     }
/*     */     
/*     */     public IChatBaseComponent getScoreboardDisplayName()
/*     */     {
/* 195 */       return new ChatComponentText(this.title);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftInventoryCustom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */