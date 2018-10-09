/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ 
/*     */ public class TileEntityChest extends TileEntityContainer implements IUpdatePlayerListBox, IInventory
/*     */ {
/*  13 */   private ItemStack[] items = new ItemStack[27];
/*     */   public boolean a;
/*     */   public TileEntityChest f;
/*     */   public TileEntityChest g;
/*     */   public TileEntityChest h;
/*     */   public TileEntityChest i;
/*     */   public float j;
/*     */   public float k;
/*     */   public int l;
/*     */   private int n;
/*  23 */   private int o = -1;
/*     */   
/*     */ 
/*     */   private String p;
/*     */   
/*     */ 
/*  29 */   public List<HumanEntity> transaction = new ArrayList();
/*  30 */   private int maxStack = 64;
/*     */   
/*     */   public ItemStack[] getContents() {
/*  33 */     return this.items;
/*     */   }
/*     */   
/*     */   public void onOpen(CraftHumanEntity who) {
/*  37 */     this.transaction.add(who);
/*     */   }
/*     */   
/*     */   public void onClose(CraftHumanEntity who) {
/*  41 */     this.transaction.remove(who);
/*     */   }
/*     */   
/*     */   public List<HumanEntity> getViewers() {
/*  45 */     return this.transaction;
/*     */   }
/*     */   
/*     */   public void setMaxStackSize(int size) {
/*  49 */     this.maxStack = size;
/*     */   }
/*     */   
/*     */   public int getSize()
/*     */   {
/*  54 */     return 27;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int i) {
/*  58 */     return this.items[i];
/*     */   }
/*     */   
/*     */   public ItemStack splitStack(int i, int j) {
/*  62 */     if (this.items[i] != null)
/*     */     {
/*     */ 
/*  65 */       if (this.items[i].count <= j) {
/*  66 */         ItemStack itemstack = this.items[i];
/*  67 */         this.items[i] = null;
/*  68 */         update();
/*  69 */         return itemstack;
/*     */       }
/*  71 */       ItemStack itemstack = this.items[i].cloneAndSubtract(j);
/*  72 */       if (this.items[i].count == 0) {
/*  73 */         this.items[i] = null;
/*     */       }
/*     */       
/*  76 */       update();
/*  77 */       return itemstack;
/*     */     }
/*     */     
/*  80 */     return null;
/*     */   }
/*     */   
/*     */   public ItemStack splitWithoutUpdate(int i)
/*     */   {
/*  85 */     if (this.items[i] != null) {
/*  86 */       ItemStack itemstack = this.items[i];
/*     */       
/*  88 */       this.items[i] = null;
/*  89 */       return itemstack;
/*     */     }
/*  91 */     return null;
/*     */   }
/*     */   
/*     */   public void setItem(int i, ItemStack itemstack)
/*     */   {
/*  96 */     this.items[i] = itemstack;
/*  97 */     if ((itemstack != null) && (itemstack.count > getMaxStackSize())) {
/*  98 */       itemstack.count = getMaxStackSize();
/*     */     }
/*     */     
/* 101 */     update();
/*     */   }
/*     */   
/*     */   public String getName() {
/* 105 */     return hasCustomName() ? this.p : "container.chest";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName() {
/* 109 */     return (this.p != null) && (this.p.length() > 0);
/*     */   }
/*     */   
/*     */   public void a(String s) {
/* 113 */     this.p = s;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 117 */     super.a(nbttagcompound);
/* 118 */     NBTTagList nbttaglist = nbttagcompound.getList("Items", 10);
/*     */     
/* 120 */     this.items = new ItemStack[getSize()];
/* 121 */     if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
/* 122 */       this.p = nbttagcompound.getString("CustomName");
/*     */     }
/*     */     
/* 125 */     for (int i = 0; i < nbttaglist.size(); i++) {
/* 126 */       NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
/* 127 */       int j = nbttagcompound1.getByte("Slot") & 0xFF;
/*     */       
/* 129 */       if ((j >= 0) && (j < this.items.length)) {
/* 130 */         this.items[j] = ItemStack.createStack(nbttagcompound1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/* 137 */     super.b(nbttagcompound);
/* 138 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 140 */     for (int i = 0; i < this.items.length; i++) {
/* 141 */       if (this.items[i] != null) {
/* 142 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*     */         
/* 144 */         nbttagcompound1.setByte("Slot", (byte)i);
/* 145 */         this.items[i].save(nbttagcompound1);
/* 146 */         nbttaglist.add(nbttagcompound1);
/*     */       }
/*     */     }
/*     */     
/* 150 */     nbttagcompound.set("Items", nbttaglist);
/* 151 */     if (hasCustomName()) {
/* 152 */       nbttagcompound.setString("CustomName", this.p);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMaxStackSize()
/*     */   {
/* 158 */     return this.maxStack;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 162 */     if (this.world == null) return true;
/* 163 */     return this.world.getTileEntity(this.position) == this;
/*     */   }
/*     */   
/*     */   public void E() {
/* 167 */     super.E();
/* 168 */     this.a = false;
/*     */   }
/*     */   
/*     */   private void a(TileEntityChest tileentitychest, EnumDirection enumdirection) {
/* 172 */     if (tileentitychest.x()) {
/* 173 */       this.a = false;
/* 174 */     } else if (this.a) {
/* 175 */       switch (SyntheticClass_1.a[enumdirection.ordinal()]) {
/*     */       case 1: 
/* 177 */         if (this.f != tileentitychest) {
/* 178 */           this.a = false;
/*     */         }
/* 180 */         break;
/*     */       
/*     */       case 2: 
/* 183 */         if (this.i != tileentitychest) {
/* 184 */           this.a = false;
/*     */         }
/* 186 */         break;
/*     */       
/*     */       case 3: 
/* 189 */         if (this.g != tileentitychest) {
/* 190 */           this.a = false;
/*     */         }
/* 192 */         break;
/*     */       
/*     */       case 4: 
/* 195 */         if (this.h != tileentitychest) {
/* 196 */           this.a = false;
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void m() {
/* 204 */     if (!this.a) {
/* 205 */       this.a = true;
/* 206 */       this.h = a(EnumDirection.WEST);
/* 207 */       this.g = a(EnumDirection.EAST);
/* 208 */       this.f = a(EnumDirection.NORTH);
/* 209 */       this.i = a(EnumDirection.SOUTH);
/*     */     }
/*     */   }
/*     */   
/*     */   protected TileEntityChest a(EnumDirection enumdirection) {
/* 214 */     BlockPosition blockposition = this.position.shift(enumdirection);
/*     */     
/* 216 */     if (b(blockposition)) {
/* 217 */       TileEntity tileentity = this.world.getTileEntity(blockposition);
/*     */       
/* 219 */       if ((tileentity instanceof TileEntityChest)) {
/* 220 */         TileEntityChest tileentitychest = (TileEntityChest)tileentity;
/*     */         
/* 222 */         tileentitychest.a(this, enumdirection.opposite());
/* 223 */         return tileentitychest;
/*     */       }
/*     */     }
/*     */     
/* 227 */     return null;
/*     */   }
/*     */   
/*     */   private boolean b(BlockPosition blockposition) {
/* 231 */     if (this.world == null) {
/* 232 */       return false;
/*     */     }
/* 234 */     Block block = this.world.getType(blockposition).getBlock();
/*     */     
/* 236 */     return ((block instanceof BlockChest)) && (((BlockChest)block).b == n());
/*     */   }
/*     */   
/*     */   public void c()
/*     */   {
/* 241 */     m();
/* 242 */     int i = this.position.getX();
/* 243 */     int j = this.position.getY();
/* 244 */     int k = this.position.getZ();
/*     */     
/* 246 */     this.n += 1;
/*     */     
/*     */ 
/* 249 */     if ((!this.world.isClientSide) && (this.l != 0) && ((this.n + i + j + k) % 200 == 0)) {
/* 250 */       this.l = 0;
/* 251 */       float f = 5.0F;
/* 252 */       List list = this.world.a(EntityHuman.class, new AxisAlignedBB(i - f, j - f, k - f, i + 1 + f, j + 1 + f, k + 1 + f));
/* 253 */       Iterator iterator = list.iterator();
/*     */       
/* 255 */       while (iterator.hasNext()) {
/* 256 */         EntityHuman entityhuman = (EntityHuman)iterator.next();
/*     */         
/* 258 */         if ((entityhuman.activeContainer instanceof ContainerChest)) {
/* 259 */           IInventory iinventory = ((ContainerChest)entityhuman.activeContainer).e();
/*     */           
/* 261 */           if ((iinventory == this) || (((iinventory instanceof InventoryLargeChest)) && (((InventoryLargeChest)iinventory).a(this)))) {
/* 262 */             this.l += 1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 268 */     this.k = this.j;
/* 269 */     float f = 0.1F;
/*     */     
/*     */ 
/* 272 */     if ((this.l > 0) && (this.j == 0.0F) && (this.f == null) && (this.h == null)) {
/* 273 */       double d1 = i + 0.5D;
/*     */       
/* 275 */       double d0 = k + 0.5D;
/* 276 */       if (this.i != null) {
/* 277 */         d0 += 0.5D;
/*     */       }
/*     */       
/* 280 */       if (this.g != null) {
/* 281 */         d1 += 0.5D;
/*     */       }
/*     */       
/* 284 */       this.world.makeSound(d1, j + 0.5D, d0, "random.chestopen", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
/*     */     }
/*     */     
/* 287 */     if (((this.l == 0) && (this.j > 0.0F)) || ((this.l > 0) && (this.j < 1.0F))) {
/* 288 */       float f1 = this.j;
/*     */       
/* 290 */       if (this.l > 0) {
/* 291 */         this.j += f;
/*     */       } else {
/* 293 */         this.j -= f;
/*     */       }
/*     */       
/* 296 */       if (this.j > 1.0F) {
/* 297 */         this.j = 1.0F;
/*     */       }
/*     */       
/* 300 */       float f2 = 0.5F;
/*     */       
/* 302 */       if ((this.j < f2) && (f1 >= f2) && (this.f == null) && (this.h == null)) {
/* 303 */         double d0 = i + 0.5D;
/* 304 */         double d2 = k + 0.5D;
/*     */         
/* 306 */         if (this.i != null) {
/* 307 */           d2 += 0.5D;
/*     */         }
/*     */         
/* 310 */         if (this.g != null) {
/* 311 */           d0 += 0.5D;
/*     */         }
/*     */         
/* 314 */         this.world.makeSound(d0, j + 0.5D, d2, "random.chestclosed", 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
/*     */       }
/*     */       
/* 317 */       if (this.j < 0.0F) {
/* 318 */         this.j = 0.0F;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean c(int i, int j)
/*     */   {
/* 325 */     if (i == 1) {
/* 326 */       this.l = j;
/* 327 */       return true;
/*     */     }
/* 329 */     return super.c(i, j);
/*     */   }
/*     */   
/*     */   public void startOpen(EntityHuman entityhuman)
/*     */   {
/* 334 */     if (!entityhuman.isSpectator()) {
/* 335 */       if (this.l < 0) {
/* 336 */         this.l = 0;
/*     */       }
/* 338 */       int oldPower = Math.max(0, Math.min(15, this.l));
/*     */       
/* 340 */       this.l += 1;
/* 341 */       if (this.world == null) return;
/* 342 */       this.world.playBlockAction(this.position, w(), 1, this.l);
/*     */       
/*     */ 
/* 345 */       if (w() == Blocks.TRAPPED_CHEST) {
/* 346 */         int newPower = Math.max(0, Math.min(15, this.l));
/*     */         
/* 348 */         if (oldPower != newPower) {
/* 349 */           CraftEventFactory.callRedstoneChange(this.world, this.position.getX(), this.position.getY(), this.position.getZ(), oldPower, newPower);
/*     */         }
/*     */       }
/*     */       
/* 353 */       this.world.applyPhysics(this.position, w());
/* 354 */       this.world.applyPhysics(this.position.down(), w());
/*     */     }
/*     */   }
/*     */   
/*     */   public void closeContainer(EntityHuman entityhuman)
/*     */   {
/* 360 */     if ((!entityhuman.isSpectator()) && ((w() instanceof BlockChest))) {
/* 361 */       int oldPower = Math.max(0, Math.min(15, this.l));
/* 362 */       this.l -= 1;
/* 363 */       if (this.world == null) return;
/* 364 */       this.world.playBlockAction(this.position, w(), 1, this.l);
/*     */       
/*     */ 
/* 367 */       if (w() == Blocks.TRAPPED_CHEST) {
/* 368 */         int newPower = Math.max(0, Math.min(15, this.l));
/*     */         
/* 370 */         if (oldPower != newPower) {
/* 371 */           CraftEventFactory.callRedstoneChange(this.world, this.position.getX(), this.position.getY(), this.position.getZ(), oldPower, newPower);
/*     */         }
/*     */       }
/*     */       
/* 375 */       this.world.applyPhysics(this.position, w());
/* 376 */       this.world.applyPhysics(this.position.down(), w());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean b(int i, ItemStack itemstack)
/*     */   {
/* 382 */     return true;
/*     */   }
/*     */   
/*     */   public void y() {
/* 386 */     super.y();
/* 387 */     E();
/* 388 */     m();
/*     */   }
/*     */   
/*     */   public int n() {
/* 392 */     if (this.o == -1) {
/* 393 */       if ((this.world == null) || (!(w() instanceof BlockChest))) {
/* 394 */         return 0;
/*     */       }
/*     */       
/* 397 */       this.o = ((BlockChest)w()).b;
/*     */     }
/*     */     
/* 400 */     return this.o;
/*     */   }
/*     */   
/*     */   public String getContainerName() {
/* 404 */     return "minecraft:chest";
/*     */   }
/*     */   
/*     */   public Container createContainer(PlayerInventory playerinventory, EntityHuman entityhuman) {
/* 408 */     return new ContainerChest(playerinventory, this, entityhuman);
/*     */   }
/*     */   
/*     */   public int getProperty(int i) {
/* 412 */     return 0;
/*     */   }
/*     */   
/*     */   public void b(int i, int j) {}
/*     */   
/*     */   public int g() {
/* 418 */     return 0;
/*     */   }
/*     */   
/*     */   public void l() {
/* 422 */     for (int i = 0; i < this.items.length; i++) {
/* 423 */       this.items[i] = null;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean F()
/*     */   {
/* 432 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   static class SyntheticClass_1
/*     */   {
/* 438 */     static final int[] a = new int[EnumDirection.values().length];
/*     */     
/*     */     static {
/*     */       try {
/* 442 */         a[EnumDirection.NORTH.ordinal()] = 1;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError1) {}
/*     */       
/*     */       try
/*     */       {
/* 448 */         a[EnumDirection.SOUTH.ordinal()] = 2;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError2) {}
/*     */       
/*     */       try
/*     */       {
/* 454 */         a[EnumDirection.EAST.ordinal()] = 3;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError3) {}
/*     */       
/*     */       try
/*     */       {
/* 460 */         a[EnumDirection.WEST.ordinal()] = 4;
/*     */       }
/*     */       catch (NoSuchFieldError localNoSuchFieldError4) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityChest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */