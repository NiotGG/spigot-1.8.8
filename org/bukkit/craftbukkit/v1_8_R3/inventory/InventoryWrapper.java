/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_8_R3.EntityHuman;
/*     */ import net.minecraft.server.v1_8_R3.IChatBaseComponent;
/*     */ import net.minecraft.server.v1_8_R3.IInventory;
/*     */ import net.minecraft.server.v1_8_R3.ItemStack;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ public class InventoryWrapper
/*     */   implements IInventory
/*     */ {
/*     */   private final Inventory inventory;
/*  18 */   private final List<HumanEntity> viewers = new ArrayList();
/*     */   
/*     */   public InventoryWrapper(Inventory inventory) {
/*  21 */     this.inventory = inventory;
/*     */   }
/*     */   
/*     */   public int getSize()
/*     */   {
/*  26 */     return this.inventory.getSize();
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i)
/*     */   {
/*  31 */     return CraftItemStack.asNMSCopy(this.inventory.getItem(i));
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack splitStack(int i, int j)
/*     */   {
/*  37 */     ItemStack stack = getItem(i);
/*     */     
/*  39 */     if (stack == null)
/*  40 */       return null;
/*     */     ItemStack result;
/*  42 */     ItemStack result; if (stack.count <= j) {
/*  43 */       setItem(i, null);
/*  44 */       result = stack;
/*     */     } else {
/*  46 */       result = CraftItemStack.copyNMSStack(stack, j);
/*  47 */       stack.count -= j;
/*     */     }
/*  49 */     update();
/*  50 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */   public ItemStack splitWithoutUpdate(int i)
/*     */   {
/*  56 */     ItemStack stack = getItem(i);
/*     */     
/*  58 */     if (stack == null)
/*  59 */       return null;
/*     */     ItemStack result;
/*  61 */     ItemStack result; if (stack.count <= 1) {
/*  62 */       setItem(i, null);
/*  63 */       result = stack;
/*     */     } else {
/*  65 */       result = CraftItemStack.copyNMSStack(stack, 1);
/*  66 */       stack.count -= 1;
/*     */     }
/*  68 */     return result;
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack)
/*     */   {
/*  73 */     this.inventory.setItem(i, CraftItemStack.asBukkitCopy(itemstack));
/*     */   }
/*     */   
/*     */   public int getMaxStackSize()
/*     */   {
/*  78 */     return this.inventory.getMaxStackSize();
/*     */   }
/*     */   
/*     */ 
/*     */   public void update() {}
/*     */   
/*     */ 
/*     */   public boolean a(EntityHuman entityhuman)
/*     */   {
/*  87 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void startOpen(EntityHuman entityhuman) {}
/*     */   
/*     */ 
/*     */   public void closeContainer(EntityHuman entityhuman) {}
/*     */   
/*     */ 
/*     */   public boolean b(int i, ItemStack itemstack)
/*     */   {
/* 100 */     return true;
/*     */   }
/*     */   
/*     */   public int getProperty(int i)
/*     */   {
/* 105 */     return 0;
/*     */   }
/*     */   
/*     */ 
/*     */   public void b(int i, int j) {}
/*     */   
/*     */ 
/*     */   public int g()
/*     */   {
/* 114 */     return 0;
/*     */   }
/*     */   
/*     */   public void l()
/*     */   {
/* 119 */     this.inventory.clear();
/*     */   }
/*     */   
/*     */   public ItemStack[] getContents()
/*     */   {
/* 124 */     int size = getSize();
/* 125 */     ItemStack[] items = new ItemStack[size];
/*     */     
/* 127 */     for (int i = 0; i < size; i++) {
/* 128 */       items[i] = getItem(i);
/*     */     }
/*     */     
/* 131 */     return items;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who)
/*     */   {
/* 136 */     this.viewers.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who)
/*     */   {
/* 141 */     this.viewers.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers()
/*     */   {
/* 146 */     return this.viewers;
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner()
/*     */   {
/* 151 */     return this.inventory.getHolder();
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size)
/*     */   {
/* 156 */     this.inventory.setMaxStackSize(size);
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 161 */     return this.inventory.getName();
/*     */   }
/*     */   
/*     */   public boolean hasCustomName()
/*     */   {
/* 166 */     return getName() != null;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent getScoreboardDisplayName()
/*     */   {
/* 171 */     return org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage.fromString(getName())[0];
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\InventoryWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */