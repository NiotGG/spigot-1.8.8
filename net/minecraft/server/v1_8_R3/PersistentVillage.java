/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ public class PersistentVillage
/*     */   extends PersistentBase
/*     */ {
/*     */   private World world;
/*  25 */   private final List<BlockPosition> c = Lists.newArrayList();
/*  26 */   private final List<VillageDoor> d = Lists.newArrayList();
/*  27 */   private final List<Village> villages = Lists.newArrayList();
/*     */   private int time;
/*     */   
/*     */   public PersistentVillage(String paramString) {
/*  31 */     super(paramString);
/*     */   }
/*     */   
/*     */   public PersistentVillage(World paramWorld) {
/*  35 */     super(a(paramWorld.worldProvider));
/*  36 */     this.world = paramWorld;
/*  37 */     c();
/*     */   }
/*     */   
/*     */   public void a(World paramWorld) {
/*  41 */     this.world = paramWorld;
/*     */     
/*  43 */     for (Village localVillage : this.villages) {
/*  44 */       localVillage.a(paramWorld);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(BlockPosition paramBlockPosition) {
/*  49 */     if (this.c.size() > 64) {
/*  50 */       return;
/*     */     }
/*  52 */     if (!e(paramBlockPosition)) {
/*  53 */       this.c.add(paramBlockPosition);
/*     */     }
/*     */   }
/*     */   
/*     */   public void tick() {
/*  58 */     this.time += 1;
/*  59 */     for (Village localVillage : this.villages) {
/*  60 */       localVillage.a(this.time);
/*     */     }
/*  62 */     e();
/*  63 */     f();
/*  64 */     g();
/*     */     
/*  66 */     if (this.time % 400 == 0) {
/*  67 */       c();
/*     */     }
/*     */   }
/*     */   
/*     */   private void e() {
/*  72 */     for (Iterator localIterator = this.villages.iterator(); localIterator.hasNext();) {
/*  73 */       Village localVillage = (Village)localIterator.next();
/*  74 */       if (localVillage.g()) {
/*  75 */         localIterator.remove();
/*  76 */         c();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public List<Village> getVillages() {
/*  82 */     return this.villages;
/*     */   }
/*     */   
/*     */   public Village getClosestVillage(BlockPosition paramBlockPosition, int paramInt) {
/*  86 */     Object localObject = null;
/*  87 */     double d1 = 3.4028234663852886E38D;
/*  88 */     for (Village localVillage : this.villages) {
/*  89 */       double d2 = localVillage.a().i(paramBlockPosition);
/*  90 */       if (d2 < d1)
/*     */       {
/*     */ 
/*     */ 
/*  94 */         float f = paramInt + localVillage.b();
/*  95 */         if (d2 <= f * f)
/*     */         {
/*     */ 
/*     */ 
/*  99 */           localObject = localVillage;
/* 100 */           d1 = d2;
/*     */         } } }
/* 102 */     return (Village)localObject;
/*     */   }
/*     */   
/*     */   private void f() {
/* 106 */     if (this.c.isEmpty()) {
/* 107 */       return;
/*     */     }
/* 109 */     b((BlockPosition)this.c.remove(0));
/*     */   }
/*     */   
/*     */   private void g()
/*     */   {
/* 114 */     for (int i = 0; i < this.d.size(); i++) {
/* 115 */       VillageDoor localVillageDoor = (VillageDoor)this.d.get(i);
/* 116 */       Village localVillage = getClosestVillage(localVillageDoor.d(), 32);
/* 117 */       if (localVillage == null)
/*     */       {
/* 119 */         localVillage = new Village(this.world);
/* 120 */         this.villages.add(localVillage);
/* 121 */         c();
/*     */       }
/* 123 */       localVillage.a(localVillageDoor);
/*     */     }
/*     */     
/* 126 */     this.d.clear();
/*     */   }
/*     */   
/*     */   private void b(BlockPosition paramBlockPosition) {
/* 130 */     int i = 16;int j = 4;int k = 16;
/* 131 */     for (int m = -i; m < i; m++) {
/* 132 */       for (int n = -j; n < j; n++) {
/* 133 */         for (int i1 = -k; i1 < k; i1++) {
/* 134 */           BlockPosition localBlockPosition = paramBlockPosition.a(m, n, i1);
/* 135 */           if (f(localBlockPosition)) {
/* 136 */             VillageDoor localVillageDoor = c(localBlockPosition);
/* 137 */             if (localVillageDoor == null) {
/* 138 */               d(localBlockPosition);
/*     */             } else {
/* 140 */               localVillageDoor.a(this.time);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private VillageDoor c(BlockPosition paramBlockPosition) {
/* 149 */     for (Iterator localIterator = this.d.iterator(); localIterator.hasNext();) { localObject = (VillageDoor)localIterator.next();
/* 150 */       if ((((VillageDoor)localObject).d().getX() == paramBlockPosition.getX()) && (((VillageDoor)localObject).d().getZ() == paramBlockPosition.getZ()) && (Math.abs(((VillageDoor)localObject).d().getY() - paramBlockPosition.getY()) <= 1))
/* 151 */         return (VillageDoor)localObject;
/*     */     }
/*     */     Object localObject;
/* 154 */     for (localIterator = this.villages.iterator(); localIterator.hasNext();) { localObject = (Village)localIterator.next();
/* 155 */       VillageDoor localVillageDoor = ((Village)localObject).e(paramBlockPosition);
/* 156 */       if (localVillageDoor != null) {
/* 157 */         return localVillageDoor;
/*     */       }
/*     */     }
/* 160 */     return null;
/*     */   }
/*     */   
/*     */   private void d(BlockPosition paramBlockPosition) {
/* 164 */     EnumDirection localEnumDirection1 = BlockDoor.h(this.world, paramBlockPosition);
/* 165 */     EnumDirection localEnumDirection2 = localEnumDirection1.opposite();
/*     */     
/* 167 */     int i = a(paramBlockPosition, localEnumDirection1, 5);
/* 168 */     int j = a(paramBlockPosition, localEnumDirection2, i + 1);
/*     */     
/* 170 */     if (i != j) {
/* 171 */       this.d.add(new VillageDoor(paramBlockPosition, i < j ? localEnumDirection1 : localEnumDirection2, this.time));
/*     */     }
/*     */   }
/*     */   
/*     */   private int a(BlockPosition paramBlockPosition, EnumDirection paramEnumDirection, int paramInt) {
/* 176 */     int i = 0;
/* 177 */     for (int j = 1; j <= 5; j++) {
/* 178 */       if (this.world.i(paramBlockPosition.shift(paramEnumDirection, j)))
/*     */       {
/* 180 */         i++; if (i >= paramInt) {
/* 181 */           return i;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 186 */     return i;
/*     */   }
/*     */   
/*     */   private boolean e(BlockPosition paramBlockPosition) {
/* 190 */     for (BlockPosition localBlockPosition : this.c) {
/* 191 */       if (localBlockPosition.equals(paramBlockPosition)) {
/* 192 */         return true;
/*     */       }
/*     */     }
/* 195 */     return false;
/*     */   }
/*     */   
/*     */   private boolean f(BlockPosition paramBlockPosition) {
/* 199 */     Block localBlock = this.world.getType(paramBlockPosition).getBlock();
/* 200 */     if ((localBlock instanceof BlockDoor)) {
/* 201 */       return localBlock.getMaterial() == Material.WOOD;
/*     */     }
/* 203 */     return false;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 208 */     this.time = paramNBTTagCompound.getInt("Tick");
/* 209 */     NBTTagList localNBTTagList = paramNBTTagCompound.getList("Villages", 10);
/* 210 */     for (int i = 0; i < localNBTTagList.size(); i++) {
/* 211 */       NBTTagCompound localNBTTagCompound = localNBTTagList.get(i);
/* 212 */       Village localVillage = new Village();
/* 213 */       localVillage.a(localNBTTagCompound);
/* 214 */       this.villages.add(localVillage);
/*     */     }
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 220 */     paramNBTTagCompound.setInt("Tick", this.time);
/* 221 */     NBTTagList localNBTTagList = new NBTTagList();
/* 222 */     for (Village localVillage : this.villages) {
/* 223 */       NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/* 224 */       localVillage.b(localNBTTagCompound);
/* 225 */       localNBTTagList.add(localNBTTagCompound);
/*     */     }
/* 227 */     paramNBTTagCompound.set("Villages", localNBTTagList);
/*     */   }
/*     */   
/*     */   public static String a(WorldProvider paramWorldProvider) {
/* 231 */     return "villages" + paramWorldProvider.getSuffix();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PersistentVillage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */