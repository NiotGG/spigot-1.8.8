/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.event.block.BlockDispenseEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class ItemArmor extends Item
/*     */ {
/*  13 */   private static final int[] k = { 11, 16, 15, 13 };
/*  14 */   public static final String[] a = { "minecraft:items/empty_armor_slot_helmet", "minecraft:items/empty_armor_slot_chestplate", "minecraft:items/empty_armor_slot_leggings", "minecraft:items/empty_armor_slot_boots" };
/*  15 */   private static final IDispenseBehavior l = new DispenseBehaviorItem() {
/*     */     protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/*  17 */       BlockPosition blockposition = isourceblock.getBlockPosition().shift(BlockDispenser.b(isourceblock.f()));
/*  18 */       int i = blockposition.getX();
/*  19 */       int j = blockposition.getY();
/*  20 */       int k = blockposition.getZ();
/*  21 */       AxisAlignedBB axisalignedbb = new AxisAlignedBB(i, j, k, i + 1, j + 1, k + 1);
/*  22 */       List list = isourceblock.getWorld().a(EntityLiving.class, axisalignedbb, Predicates.and(IEntitySelector.d, new IEntitySelector.EntitySelectorEquipable(itemstack)));
/*     */       
/*  24 */       if (list.size() > 0) {
/*  25 */         EntityLiving entityliving = (EntityLiving)list.get(0);
/*  26 */         int l = (entityliving instanceof EntityHuman) ? 1 : 0;
/*  27 */         int i1 = EntityInsentient.c(itemstack);
/*     */         
/*     */ 
/*  30 */         ItemStack itemstack1 = itemstack.cloneAndSubtract(1);
/*  31 */         World world = isourceblock.getWorld();
/*  32 */         org.bukkit.block.Block block = world.getWorld().getBlockAt(isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ());
/*  33 */         CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack1);
/*     */         
/*  35 */         BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new org.bukkit.util.Vector(0, 0, 0));
/*  36 */         if (!BlockDispenser.eventFired) {
/*  37 */           world.getServer().getPluginManager().callEvent(event);
/*     */         }
/*     */         
/*  40 */         if (event.isCancelled()) {
/*  41 */           itemstack.count += 1;
/*  42 */           return itemstack;
/*     */         }
/*     */         
/*  45 */         if (!event.getItem().equals(craftItem)) {
/*  46 */           itemstack.count += 1;
/*     */           
/*  48 */           ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/*  49 */           IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(eventStack.getItem());
/*  50 */           if ((idispensebehavior != IDispenseBehavior.NONE) && (idispensebehavior != this)) {
/*  51 */             idispensebehavior.a(isourceblock, eventStack);
/*  52 */             return itemstack;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*  57 */         itemstack1.count = 1;
/*  58 */         entityliving.setEquipment(i1 - l, itemstack1);
/*  59 */         if ((entityliving instanceof EntityInsentient)) {
/*  60 */           ((EntityInsentient)entityliving).a(i1, 2.0F);
/*     */         }
/*     */         
/*     */ 
/*  64 */         return itemstack;
/*     */       }
/*  66 */       return super.b(isourceblock, itemstack);
/*     */     }
/*     */   };
/*     */   public final int b;
/*     */   public final int c;
/*     */   public final int d;
/*     */   private final EnumArmorMaterial m;
/*     */   
/*     */   public ItemArmor(EnumArmorMaterial itemarmor_enumarmormaterial, int i, int j)
/*     */   {
/*  76 */     this.m = itemarmor_enumarmormaterial;
/*  77 */     this.b = j;
/*  78 */     this.d = i;
/*  79 */     this.c = itemarmor_enumarmormaterial.b(j);
/*  80 */     setMaxDurability(itemarmor_enumarmormaterial.a(j));
/*  81 */     this.maxStackSize = 1;
/*  82 */     a(CreativeModeTab.j);
/*  83 */     BlockDispenser.REGISTRY.a(this, l);
/*     */   }
/*     */   
/*     */   public int b() {
/*  87 */     return this.m.a();
/*     */   }
/*     */   
/*     */   public EnumArmorMaterial x_() {
/*  91 */     return this.m;
/*     */   }
/*     */   
/*     */   public boolean d_(ItemStack itemstack) {
/*  95 */     return !itemstack.getTag().hasKeyOfType("display", 10) ? false : !itemstack.hasTag() ? false : this.m != EnumArmorMaterial.LEATHER ? false : itemstack.getTag().getCompound("display").hasKeyOfType("color", 3);
/*     */   }
/*     */   
/*     */   public int b(ItemStack itemstack) {
/*  99 */     if (this.m != EnumArmorMaterial.LEATHER) {
/* 100 */       return -1;
/*     */     }
/* 102 */     NBTTagCompound nbttagcompound = itemstack.getTag();
/*     */     
/* 104 */     if (nbttagcompound != null) {
/* 105 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("display");
/*     */       
/* 107 */       if ((nbttagcompound1 != null) && (nbttagcompound1.hasKeyOfType("color", 3))) {
/* 108 */         return nbttagcompound1.getInt("color");
/*     */       }
/*     */     }
/*     */     
/* 112 */     return 10511680;
/*     */   }
/*     */   
/*     */   public void c(ItemStack itemstack)
/*     */   {
/* 117 */     if (this.m == EnumArmorMaterial.LEATHER) {
/* 118 */       NBTTagCompound nbttagcompound = itemstack.getTag();
/*     */       
/* 120 */       if (nbttagcompound != null) {
/* 121 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("display");
/*     */         
/* 123 */         if (nbttagcompound1.hasKey("color")) {
/* 124 */           nbttagcompound1.remove("color");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(ItemStack itemstack, int i)
/*     */   {
/* 132 */     if (this.m != EnumArmorMaterial.LEATHER) {
/* 133 */       throw new UnsupportedOperationException("Can't dye non-leather!");
/*     */     }
/* 135 */     NBTTagCompound nbttagcompound = itemstack.getTag();
/*     */     
/* 137 */     if (nbttagcompound == null) {
/* 138 */       nbttagcompound = new NBTTagCompound();
/* 139 */       itemstack.setTag(nbttagcompound);
/*     */     }
/*     */     
/* 142 */     NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("display");
/*     */     
/* 144 */     if (!nbttagcompound.hasKeyOfType("display", 10)) {
/* 145 */       nbttagcompound.set("display", nbttagcompound1);
/*     */     }
/*     */     
/* 148 */     nbttagcompound1.setInt("color", i);
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack itemstack, ItemStack itemstack1)
/*     */   {
/* 153 */     return this.m.b() == itemstack1.getItem() ? true : super.a(itemstack, itemstack1);
/*     */   }
/*     */   
/*     */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 157 */     int i = EntityInsentient.c(itemstack) - 1;
/* 158 */     ItemStack itemstack1 = entityhuman.q(i);
/*     */     
/* 160 */     if (itemstack1 == null) {
/* 161 */       entityhuman.setEquipment(i, itemstack.cloneItemStack());
/* 162 */       itemstack.count = 0;
/*     */     }
/*     */     
/* 165 */     return itemstack;
/*     */   }
/*     */   
/*     */   public static enum EnumArmorMaterial
/*     */   {
/* 170 */     LEATHER("leather", 5, new int[] { 1, 3, 2, 1 }, 15),  CHAIN("chainmail", 15, new int[] { 2, 5, 4, 1 }, 12),  IRON("iron", 15, new int[] { 2, 6, 5, 2 }, 9),  GOLD("gold", 7, new int[] { 2, 5, 3, 1 }, 25),  DIAMOND("diamond", 33, new int[] { 3, 8, 6, 3 }, 10);
/*     */     
/*     */     private final String f;
/*     */     private final int g;
/*     */     private final int[] h;
/*     */     private final int i;
/*     */     
/*     */     private EnumArmorMaterial(String s, int i, int[] aint, int j) {
/* 178 */       this.f = s;
/* 179 */       this.g = i;
/* 180 */       this.h = aint;
/* 181 */       this.i = j;
/*     */     }
/*     */     
/*     */     public int a(int i) {
/* 185 */       return ItemArmor.k[i] * this.g;
/*     */     }
/*     */     
/*     */     public int b(int i) {
/* 189 */       return this.h[i];
/*     */     }
/*     */     
/*     */     public int a() {
/* 193 */       return this.i;
/*     */     }
/*     */     
/*     */     public Item b() {
/* 197 */       return this == DIAMOND ? Items.DIAMOND : this == IRON ? Items.IRON_INGOT : this == GOLD ? Items.GOLD_INGOT : this == CHAIN ? Items.IRON_INGOT : this == LEATHER ? Items.LEATHER : null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */