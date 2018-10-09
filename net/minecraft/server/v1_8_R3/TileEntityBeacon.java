/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ 
/*     */ public class TileEntityBeacon
/*     */   extends TileEntityContainer
/*     */   implements IUpdatePlayerListBox, IInventory
/*     */ {
/*  15 */   public static final MobEffectList[][] a = { { MobEffectList.FASTER_MOVEMENT, MobEffectList.FASTER_DIG }, { MobEffectList.RESISTANCE, MobEffectList.JUMP }, { MobEffectList.INCREASE_DAMAGE }, { MobEffectList.REGENERATION } };
/*  16 */   private final List<BeaconColorTracker> f = Lists.newArrayList();
/*     */   private boolean i;
/*  18 */   private int j = -1;
/*     */   
/*     */   private int k;
/*     */   private int l;
/*     */   private ItemStack inventorySlot;
/*     */   private String n;
/*  24 */   public List<HumanEntity> transaction = new ArrayList();
/*  25 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  28 */     return new ItemStack[] { this.inventorySlot };
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  32 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  36 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  40 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  44 */     this.maxStack = size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void c()
/*     */   {
/*  51 */     if (this.world.getTime() % 80L == 0L) {
/*  52 */       m();
/*     */     }
/*     */   }
/*     */   
/*     */   public void m()
/*     */   {
/*  58 */     B();
/*  59 */     A();
/*     */   }
/*     */   
/*     */   private void A() {
/*  63 */     if ((this.i) && (this.j > 0) && (!this.world.isClientSide) && (this.k > 0)) {
/*  64 */       double d0 = this.j * 10 + 10;
/*  65 */       byte b0 = 0;
/*     */       
/*  67 */       if ((this.j >= 4) && (this.k == this.l)) {
/*  68 */         b0 = 1;
/*     */       }
/*     */       
/*  71 */       int i = this.position.getX();
/*  72 */       int j = this.position.getY();
/*  73 */       int k = this.position.getZ();
/*  74 */       AxisAlignedBB axisalignedbb = new AxisAlignedBB(i, j, k, i + 1, j + 1, k + 1).grow(d0, d0, d0).a(0.0D, this.world.getHeight(), 0.0D);
/*  75 */       List list = this.world.a(EntityHuman.class, axisalignedbb);
/*  76 */       Iterator iterator = list.iterator();
/*     */       
/*     */ 
/*     */ 
/*  80 */       while (iterator.hasNext()) {
/*  81 */         EntityHuman entityhuman = (EntityHuman)iterator.next();
/*  82 */         entityhuman.addEffect(new MobEffect(this.k, 180, b0, true, true));
/*     */       }
/*     */       
/*  85 */       if ((this.j >= 4) && (this.k != this.l) && (this.l > 0)) {
/*  86 */         iterator = list.iterator();
/*     */         
/*  88 */         while (iterator.hasNext()) {
/*  89 */           EntityHuman entityhuman = (EntityHuman)iterator.next();
/*  90 */           entityhuman.addEffect(new MobEffect(this.l, 180, 0, true, true));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void B()
/*     */   {
/*  98 */     int i = this.j;
/*  99 */     int j = this.position.getX();
/* 100 */     int k = this.position.getY();
/* 101 */     int l = this.position.getZ();
/*     */     
/* 103 */     this.j = 0;
/* 104 */     this.f.clear();
/* 105 */     this.i = true;
/* 106 */     BeaconColorTracker tileentitybeacon_beaconcolortracker = new BeaconColorTracker(EntitySheep.a(EnumColor.WHITE));
/*     */     
/* 108 */     this.f.add(tileentitybeacon_beaconcolortracker);
/* 109 */     boolean flag = true;
/* 110 */     BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();
/*     */     
/*     */ 
/*     */ 
/* 114 */     for (int i1 = k + 1; i1 < 256; i1++) {
/* 115 */       IBlockData iblockdata = this.world.getType(blockposition_mutableblockposition.c(j, i1, l));
/*     */       float[] afloat;
/*     */       float[] afloat;
/* 118 */       if (iblockdata.getBlock() == Blocks.STAINED_GLASS) {
/* 119 */         afloat = EntitySheep.a((EnumColor)iblockdata.get(BlockStainedGlass.COLOR));
/*     */       } else {
/* 121 */         if (iblockdata.getBlock() != Blocks.STAINED_GLASS_PANE) {
/* 122 */           if ((iblockdata.getBlock().p() >= 15) && (iblockdata.getBlock() != Blocks.BEDROCK)) {
/* 123 */             this.i = false;
/* 124 */             this.f.clear();
/* 125 */             break;
/*     */           }
/*     */           
/* 128 */           tileentitybeacon_beaconcolortracker.a();
/* 129 */           continue;
/*     */         }
/*     */         
/* 132 */         afloat = EntitySheep.a((EnumColor)iblockdata.get(BlockStainedGlassPane.COLOR));
/*     */       }
/*     */       
/* 135 */       if (!flag) {
/* 136 */         afloat = new float[] { (tileentitybeacon_beaconcolortracker.b()[0] + afloat[0]) / 2.0F, (tileentitybeacon_beaconcolortracker.b()[1] + afloat[1]) / 2.0F, (tileentitybeacon_beaconcolortracker.b()[2] + afloat[2]) / 2.0F };
/*     */       }
/*     */       
/* 139 */       if (Arrays.equals(afloat, tileentitybeacon_beaconcolortracker.b())) {
/* 140 */         tileentitybeacon_beaconcolortracker.a();
/*     */       } else {
/* 142 */         tileentitybeacon_beaconcolortracker = new BeaconColorTracker(afloat);
/* 143 */         this.f.add(tileentitybeacon_beaconcolortracker);
/*     */       }
/*     */       
/* 146 */       flag = false;
/*     */     }
/*     */     
/* 149 */     if (this.i) {
/* 150 */       for (i1 = 1; i1 <= 4; this.j = (i1++)) {
/* 151 */         int j1 = k - i1;
/*     */         
/* 153 */         if (j1 < 0) {
/*     */           break;
/*     */         }
/*     */         
/* 157 */         boolean flag1 = true;
/*     */         
/* 159 */         for (int k1 = j - i1; (k1 <= j + i1) && (flag1); k1++) {
/* 160 */           for (int l1 = l - i1; l1 <= l + i1; l1++) {
/* 161 */             Block block = this.world.getType(new BlockPosition(k1, j1, l1)).getBlock();
/*     */             
/* 163 */             if ((block != Blocks.EMERALD_BLOCK) && (block != Blocks.GOLD_BLOCK) && (block != Blocks.DIAMOND_BLOCK) && (block != Blocks.IRON_BLOCK)) {
/* 164 */               flag1 = false;
/* 165 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 170 */         if (!flag1) {
/*     */           break;
/*     */         }
/*     */       }
/*     */       
/* 175 */       if (this.j == 0) {
/* 176 */         this.i = false;
/*     */       }
/*     */     }
/*     */     
/* 180 */     if ((!this.world.isClientSide) && (this.j == 4) && (i < this.j)) {
/* 181 */       Iterator iterator = this.world.a(EntityHuman.class, new AxisAlignedBB(j, k, l, j, k - 4, l).grow(10.0D, 5.0D, 10.0D)).iterator();
/*     */       
/* 183 */       while (iterator.hasNext()) {
/* 184 */         EntityHuman entityhuman = (EntityHuman)iterator.next();
/*     */         
/* 186 */         entityhuman.b(AchievementList.K);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Packet getUpdatePacket()
/*     */   {
/* 193 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 195 */     b(nbttagcompound);
/* 196 */     return new PacketPlayOutTileEntityData(this.position, 3, nbttagcompound);
/*     */   }
/*     */   
/*     */   private int h(int i) {
/* 200 */     if ((i >= 0) && (i < MobEffectList.byId.length) && (MobEffectList.byId[i] != null)) {
/* 201 */       MobEffectList mobeffectlist = MobEffectList.byId[i];
/*     */       
/* 203 */       return (mobeffectlist != MobEffectList.FASTER_MOVEMENT) && (mobeffectlist != MobEffectList.FASTER_DIG) && (mobeffectlist != MobEffectList.RESISTANCE) && (mobeffectlist != MobEffectList.JUMP) && (mobeffectlist != MobEffectList.INCREASE_DAMAGE) && (mobeffectlist != MobEffectList.REGENERATION) ? 0 : i;
/*     */     }
/* 205 */     return 0;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound)
/*     */   {
/* 210 */     super.a(nbttagcompound);
/* 211 */     this.k = h(nbttagcompound.getInt("Primary"));
/* 212 */     this.l = h(nbttagcompound.getInt("Secondary"));
/* 213 */     this.j = nbttagcompound.getInt("Levels");
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 217 */     super.b(nbttagcompound);
/* 218 */     nbttagcompound.setInt("Primary", this.k);
/* 219 */     nbttagcompound.setInt("Secondary", this.l);
/* 220 */     nbttagcompound.setInt("Levels", this.j);
/*     */   }
/*     */   
/*     */   public int getSize() {
/* 224 */     return 1;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/* 228 */     return i == 0 ? this.inventorySlot : null;
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/* 232 */     if ((i == 0) && (this.inventorySlot != null)) {
/* 233 */       if (j >= this.inventorySlot.count) {
/* 234 */         ItemStack itemstack = this.inventorySlot;
/*     */         
/* 236 */         this.inventorySlot = null;
/* 237 */         return itemstack;
/*     */       }
/* 239 */       this.inventorySlot.count -= j;
/* 240 */       return new ItemStack(this.inventorySlot.getItem(), j, this.inventorySlot.getData());
/*     */     }
/*     */     
/* 243 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i)
/*     */   {
/* 248 */     if ((i == 0) && (this.inventorySlot != null)) {
/* 249 */       ItemStack itemstack = this.inventorySlot;
/*     */       
/* 251 */       this.inventorySlot = null;
/* 252 */       return itemstack;
/*     */     }
/* 254 */     return null;
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack)
/*     */   {
/* 259 */     if (i == 0) {
/* 260 */       this.inventorySlot = itemstack;
/*     */     }
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 266 */     return hasCustomName() ? this.n : "container.beacon";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/* 270 */     return (this.n != null) && (this.n.length() > 0);
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 274 */     this.n = s;
/*     */   }
/*     */   
/*     */   public int getMaxStackSize() {
/* 278 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 282 */     return this.world.getTileEntity(this.position) == this;
/*     */   }
/*     */   
/*     */   public void startOpen(EntityHuman entityhuman) {}
/*     */   
/*     */   public void closeContainer(EntityHuman entityhuman) {}
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack) {
/* 290 */     return (itemstack.getItem() == Items.EMERALD) || (itemstack.getItem() == Items.DIAMOND) || (itemstack.getItem() == Items.GOLD_INGOT) || (itemstack.getItem() == Items.IRON_INGOT);
/*     */   }
/*     */   
/*     */   public String getContainerName() {
/* 294 */     return "minecraft:beacon";
/*     */   }
/*     */   
/*     */   public Container createContainer(PlayerInventory playerinventory, EntityHuman entityhuman) {
/* 298 */     return new ContainerBeacon(playerinventory, this);
/*     */   }
/*     */   
/*     */   public int getProperty(int i) {
/* 302 */     switch (i) {
/*     */     case 0: 
/* 304 */       return this.j;
/*     */     
/*     */     case 1: 
/* 307 */       return this.k;
/*     */     
/*     */     case 2: 
/* 310 */       return this.l;
/*     */     }
/*     */     
/* 313 */     return 0;
/*     */   }
/*     */   
/*     */   public void b(int i, int j)
/*     */   {
/* 318 */     switch (i) {
/*     */     case 0: 
/* 320 */       this.j = j;
/* 321 */       break;
/*     */     
/*     */     case 1: 
/* 324 */       this.k = h(j);
/* 325 */       break;
/*     */     
/*     */     case 2: 
/* 328 */       this.l = h(j);
/*     */     }
/*     */   }
/*     */   
/*     */   public int g()
/*     */   {
/* 334 */     return 3;
/*     */   }
/*     */   
/*     */   public void l() {
/* 338 */     this.inventorySlot = null;
/*     */   }
/*     */   
/*     */   public boolean c(int i, int j) {
/* 342 */     if (i == 1) {
/* 343 */       m();
/* 344 */       return true;
/*     */     }
/* 346 */     return super.c(i, j);
/*     */   }
/*     */   
/*     */   public static class BeaconColorTracker
/*     */   {
/*     */     private final float[] a;
/*     */     private int b;
/*     */     
/*     */     public BeaconColorTracker(float[] afloat)
/*     */     {
/* 356 */       this.a = afloat;
/* 357 */       this.b = 1;
/*     */     }
/*     */     
/*     */     protected void a() {
/* 361 */       this.b += 1;
/*     */     }
/*     */     
/*     */     public float[] b() {
/* 365 */       return this.a;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityBeacon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */