/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ public class InventoryEnderChest
/*     */   extends InventorySubcontainer
/*     */ {
/*     */   private TileEntityEnderChest a;
/*  14 */   public List<HumanEntity> transaction = new ArrayList();
/*     */   public Player player;
/*  16 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  19 */     return this.items;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  23 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  27 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  31 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner() {
/*  35 */     return this.player;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  39 */     this.maxStack = size;
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/*  43 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public InventoryEnderChest()
/*     */   {
/*  48 */     super("container.enderchest", false, 27);
/*     */   }
/*     */   
/*     */   public void a(TileEntityEnderChest tileentityenderchest) {
/*  52 */     this.a = tileentityenderchest;
/*     */   }
/*     */   
/*     */ 
/*     */   public void a(NBTTagList nbttaglist)
/*     */   {
/*  58 */     for (int i = 0; i < getSize(); i++) {
/*  59 */       setItem(i, null);
/*     */     }
/*     */     
/*  62 */     for (i = 0; i < nbttaglist.size(); i++) {
/*  63 */       NBTTagCompound nbttagcompound = nbttaglist.get(i);
/*  64 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/*     */       
/*  66 */       if ((j >= 0) && (j < getSize())) {
/*  67 */         setItem(j, ItemStack.createStack(nbttagcompound));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public NBTTagList h()
/*     */   {
/*  74 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  76 */     for (int i = 0; i < getSize(); i++) {
/*  77 */       ItemStack itemstack = getItem(i);
/*     */       
/*  79 */       if (itemstack != null) {
/*  80 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */         
/*  82 */         nbttagcompound.setByte("Slot", (byte)i);
/*  83 */         itemstack.save(nbttagcompound);
/*  84 */         nbttaglist.add(nbttagcompound);
/*     */       }
/*     */     }
/*     */     
/*  88 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  92 */     return (this.a != null) && (!this.a.a(entityhuman)) ? false : super.a(entityhuman);
/*     */   }
/*     */   
/*     */   public void startOpen(EntityHuman entityhuman) {
/*  96 */     if (this.a != null) {
/*  97 */       this.a.b();
/*     */     }
/*     */     
/* 100 */     super.startOpen(entityhuman);
/*     */   }
/*     */   
/*     */   public void closeContainer(EntityHuman entityhuman) {
/* 104 */     if (this.a != null) {
/* 105 */       this.a.d();
/*     */     }
/*     */     
/* 108 */     super.closeContainer(entityhuman);
/* 109 */     this.a = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\InventoryEnderChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */