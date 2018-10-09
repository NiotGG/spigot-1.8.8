/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import net.minecraft.server.v1_8_R3.EntityPlayer;
/*     */ import net.minecraft.server.v1_8_R3.IInventory;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutSetSlot;
/*     */ import net.minecraft.server.v1_8_R3.PlayerConnection;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ public class CraftInventoryPlayer extends CraftInventory implements org.bukkit.inventory.PlayerInventory, org.bukkit.inventory.EntityEquipment
/*     */ {
/*     */   public CraftInventoryPlayer(net.minecraft.server.v1_8_R3.PlayerInventory inventory)
/*     */   {
/*  16 */     super(inventory);
/*     */   }
/*     */   
/*     */   public net.minecraft.server.v1_8_R3.PlayerInventory getInventory()
/*     */   {
/*  21 */     return (net.minecraft.server.v1_8_R3.PlayerInventory)this.inventory;
/*     */   }
/*     */   
/*     */   public int getSize()
/*     */   {
/*  26 */     return super.getSize() - 4;
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack getItemInHand() {
/*  30 */     return CraftItemStack.asCraftMirror(getInventory().getItemInHand());
/*     */   }
/*     */   
/*     */   public void setItemInHand(org.bukkit.inventory.ItemStack stack) {
/*  34 */     setItem(getHeldItemSlot(), stack);
/*     */   }
/*     */   
/*     */   public void setItem(int index, org.bukkit.inventory.ItemStack item)
/*     */   {
/*  39 */     super.setItem(index, item);
/*  40 */     if (getHolder() == null) return;
/*  41 */     EntityPlayer player = ((CraftPlayer)getHolder()).getHandle();
/*  42 */     if (player.playerConnection == null) { return;
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  71 */     if (index < net.minecraft.server.v1_8_R3.PlayerInventory.getHotbarSize()) {
/*  72 */       index += 36;
/*  73 */     } else if (index > 35)
/*  74 */       index = 8 - (index - 36);
/*  75 */     player.playerConnection.sendPacket(new PacketPlayOutSetSlot(player.defaultContainer.windowId, index, CraftItemStack.asNMSCopy(item)));
/*     */   }
/*     */   
/*     */   public int getHeldItemSlot() {
/*  79 */     return getInventory().itemInHandIndex;
/*     */   }
/*     */   
/*     */   public void setHeldItemSlot(int slot) {
/*  83 */     Validate.isTrue((slot >= 0) && (slot < net.minecraft.server.v1_8_R3.PlayerInventory.getHotbarSize()), "Slot is not between 0 and 8 inclusive");
/*  84 */     getInventory().itemInHandIndex = slot;
/*  85 */     ((CraftPlayer)getHolder()).getHandle().playerConnection.sendPacket(new net.minecraft.server.v1_8_R3.PacketPlayOutHeldItemSlot(slot));
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack getHelmet() {
/*  89 */     return getItem(getSize() + 3);
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack getChestplate() {
/*  93 */     return getItem(getSize() + 2);
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack getLeggings() {
/*  97 */     return getItem(getSize() + 1);
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack getBoots() {
/* 101 */     return getItem(getSize() + 0);
/*     */   }
/*     */   
/*     */   public void setHelmet(org.bukkit.inventory.ItemStack helmet) {
/* 105 */     setItem(getSize() + 3, helmet);
/*     */   }
/*     */   
/*     */   public void setChestplate(org.bukkit.inventory.ItemStack chestplate) {
/* 109 */     setItem(getSize() + 2, chestplate);
/*     */   }
/*     */   
/*     */   public void setLeggings(org.bukkit.inventory.ItemStack leggings) {
/* 113 */     setItem(getSize() + 1, leggings);
/*     */   }
/*     */   
/*     */   public void setBoots(org.bukkit.inventory.ItemStack boots) {
/* 117 */     setItem(getSize() + 0, boots);
/*     */   }
/*     */   
/*     */   public org.bukkit.inventory.ItemStack[] getArmorContents() {
/* 121 */     net.minecraft.server.v1_8_R3.ItemStack[] mcItems = getInventory().getArmorContents();
/* 122 */     org.bukkit.inventory.ItemStack[] ret = new org.bukkit.inventory.ItemStack[mcItems.length];
/*     */     
/* 124 */     for (int i = 0; i < mcItems.length; i++) {
/* 125 */       ret[i] = CraftItemStack.asCraftMirror(mcItems[i]);
/*     */     }
/* 127 */     return ret;
/*     */   }
/*     */   
/*     */   public void setArmorContents(org.bukkit.inventory.ItemStack[] items) {
/* 131 */     int cnt = getSize();
/*     */     
/* 133 */     if (items == null)
/* 134 */       items = new org.bukkit.inventory.ItemStack[4];
/*     */     org.bukkit.inventory.ItemStack[] arrayOfItemStack;
/* 136 */     int i = (arrayOfItemStack = items).length; for (int j = 0; j < i; j++) { org.bukkit.inventory.ItemStack item = arrayOfItemStack[j];
/* 137 */       if ((item == null) || (item.getTypeId() == 0)) {
/* 138 */         clear(cnt++);
/*     */       } else {
/* 140 */         setItem(cnt++, item);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int clear(int id, int data) {
/* 146 */     int count = 0;
/* 147 */     org.bukkit.inventory.ItemStack[] items = getContents();
/* 148 */     org.bukkit.inventory.ItemStack[] armor = getArmorContents();
/* 149 */     int armorSlot = getSize();
/*     */     
/* 151 */     for (int i = 0; i < items.length; i++) {
/* 152 */       item = items[i];
/* 153 */       if ((item != null) && 
/* 154 */         ((id <= -1) || (item.getTypeId() == id)) && (
/* 155 */         (data <= -1) || (item.getData().getData() == data)))
/*     */       {
/* 157 */         count += item.getAmount();
/* 158 */         setItem(i, null);
/*     */       } }
/*     */     org.bukkit.inventory.ItemStack[] arrayOfItemStack1;
/* 161 */     org.bukkit.inventory.ItemStack localItemStack1 = (arrayOfItemStack1 = armor).length; for (org.bukkit.inventory.ItemStack item = 0; item < localItemStack1; item++) { org.bukkit.inventory.ItemStack item = arrayOfItemStack1[item];
/* 162 */       if ((item != null) && 
/* 163 */         ((id <= -1) || (item.getTypeId() == id)) && (
/* 164 */         (data <= -1) || (item.getData().getData() == data)))
/*     */       {
/* 166 */         count += item.getAmount();
/* 167 */         setItem(armorSlot++, null);
/*     */       } }
/* 169 */     return count;
/*     */   }
/*     */   
/*     */   public HumanEntity getHolder()
/*     */   {
/* 174 */     return (HumanEntity)this.inventory.getOwner();
/*     */   }
/*     */   
/*     */   public float getItemInHandDropChance() {
/* 178 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public void setItemInHandDropChance(float chance) {
/* 182 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public float getHelmetDropChance() {
/* 186 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public void setHelmetDropChance(float chance) {
/* 190 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public float getChestplateDropChance() {
/* 194 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public void setChestplateDropChance(float chance) {
/* 198 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public float getLeggingsDropChance() {
/* 202 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public void setLeggingsDropChance(float chance) {
/* 206 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public float getBootsDropChance() {
/* 210 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public void setBootsDropChance(float chance) {
/* 214 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftInventoryPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */