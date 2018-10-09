/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import gnu.trove.map.TIntObjectMap;
/*     */ import gnu.trove.map.TObjectIntMap;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ 
/*     */ public class DataWatcher
/*     */ {
/*     */   private final Entity a;
/*  17 */   private boolean b = true;
/*     */   
/*  19 */   private static final TObjectIntMap classToId = new gnu.trove.map.hash.TObjectIntHashMap(10, 0.5F, -1);
/*  20 */   private final TIntObjectMap dataValues = new gnu.trove.map.hash.TIntObjectHashMap(10, 0.5F, -1);
/*     */   
/*  22 */   private static final java.util.Map<Class<?>, Integer> c = gnu.trove.TDecorators.wrap(classToId);
/*  23 */   private final java.util.Map<Integer, WatchableObject> d = gnu.trove.TDecorators.wrap(this.dataValues);
/*     */   
/*     */   private boolean e;
/*  26 */   private ReadWriteLock f = new java.util.concurrent.locks.ReentrantReadWriteLock();
/*     */   
/*     */   public DataWatcher(Entity entity) {
/*  29 */     this.a = entity;
/*     */   }
/*     */   
/*     */   public <T> void a(int i, T t0) {
/*  33 */     int integer = classToId.get(t0.getClass());
/*     */     
/*  35 */     if (integer == -1)
/*  36 */       throw new IllegalArgumentException("Unknown data type: " + t0.getClass());
/*  37 */     if (i > 31)
/*  38 */       throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + 31 + ")");
/*  39 */     if (this.dataValues.containsKey(i)) {
/*  40 */       throw new IllegalArgumentException("Duplicate id value for " + i + "!");
/*     */     }
/*  42 */     WatchableObject datawatcher_watchableobject = new WatchableObject(integer, i, t0);
/*     */     
/*  44 */     this.f.writeLock().lock();
/*  45 */     this.dataValues.put(i, datawatcher_watchableobject);
/*  46 */     this.f.writeLock().unlock();
/*  47 */     this.b = false;
/*     */   }
/*     */   
/*     */   public void add(int i, int j)
/*     */   {
/*  52 */     WatchableObject datawatcher_watchableobject = new WatchableObject(j, i, null);
/*     */     
/*  54 */     this.f.writeLock().lock();
/*  55 */     this.dataValues.put(i, datawatcher_watchableobject);
/*  56 */     this.f.writeLock().unlock();
/*  57 */     this.b = false;
/*     */   }
/*     */   
/*     */   public byte getByte(int i) {
/*  61 */     return ((Byte)j(i).b()).byteValue();
/*     */   }
/*     */   
/*     */   public short getShort(int i) {
/*  65 */     return ((Short)j(i).b()).shortValue();
/*     */   }
/*     */   
/*     */   public int getInt(int i) {
/*  69 */     return ((Integer)j(i).b()).intValue();
/*     */   }
/*     */   
/*     */   public float getFloat(int i) {
/*  73 */     return ((Float)j(i).b()).floatValue();
/*     */   }
/*     */   
/*     */   public String getString(int i) {
/*  77 */     return (String)j(i).b();
/*     */   }
/*     */   
/*     */   public ItemStack getItemStack(int i) {
/*  81 */     return (ItemStack)j(i).b();
/*     */   }
/*     */   
/*     */   private WatchableObject j(int i) {
/*  85 */     this.f.readLock().lock();
/*     */     
/*     */ 
/*     */     try
/*     */     {
/*  90 */       datawatcher_watchableobject = (WatchableObject)this.dataValues.get(i);
/*     */     } catch (Throwable throwable) { WatchableObject datawatcher_watchableobject;
/*  92 */       CrashReport crashreport = CrashReport.a(throwable, "Getting synched entity data");
/*  93 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Synched entity data");
/*     */       
/*  95 */       crashreportsystemdetails.a("Data ID", Integer.valueOf(i));
/*  96 */       throw new ReportedException(crashreport);
/*     */     }
/*     */     WatchableObject datawatcher_watchableobject;
/*  99 */     this.f.readLock().unlock();
/* 100 */     return datawatcher_watchableobject;
/*     */   }
/*     */   
/*     */   public Vector3f h(int i) {
/* 104 */     return (Vector3f)j(i).b();
/*     */   }
/*     */   
/*     */   public <T> void watch(int i, T t0) {
/* 108 */     WatchableObject datawatcher_watchableobject = j(i);
/*     */     
/* 110 */     if (org.apache.commons.lang3.ObjectUtils.notEqual(t0, datawatcher_watchableobject.b())) {
/* 111 */       datawatcher_watchableobject.a(t0);
/* 112 */       this.a.i(i);
/* 113 */       datawatcher_watchableobject.a(true);
/* 114 */       this.e = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public void update(int i)
/*     */   {
/* 120 */     j(i).d = true;
/* 121 */     this.e = true;
/*     */   }
/*     */   
/*     */   public boolean a() {
/* 125 */     return this.e;
/*     */   }
/*     */   
/*     */   public static void a(List<WatchableObject> list, PacketDataSerializer packetdataserializer) throws IOException {
/* 129 */     if (list != null) {
/* 130 */       Iterator iterator = list.iterator();
/*     */       
/* 132 */       while (iterator.hasNext()) {
/* 133 */         WatchableObject datawatcher_watchableobject = (WatchableObject)iterator.next();
/*     */         
/* 135 */         a(packetdataserializer, datawatcher_watchableobject);
/*     */       }
/*     */     }
/*     */     
/* 139 */     packetdataserializer.writeByte(127);
/*     */   }
/*     */   
/*     */   public List<WatchableObject> b() {
/* 143 */     ArrayList arraylist = null;
/*     */     
/* 145 */     if (this.e) {
/* 146 */       this.f.readLock().lock();
/* 147 */       Iterator iterator = this.dataValues.valueCollection().iterator();
/*     */       
/* 149 */       while (iterator.hasNext()) {
/* 150 */         WatchableObject datawatcher_watchableobject = (WatchableObject)iterator.next();
/*     */         
/* 152 */         if (datawatcher_watchableobject.d()) {
/* 153 */           datawatcher_watchableobject.a(false);
/* 154 */           if (arraylist == null) {
/* 155 */             arraylist = Lists.newArrayList();
/*     */           }
/*     */           
/*     */ 
/* 159 */           if ((datawatcher_watchableobject.b() instanceof ItemStack))
/*     */           {
/* 161 */             datawatcher_watchableobject = new WatchableObject(
/* 162 */               datawatcher_watchableobject.c(), 
/* 163 */               datawatcher_watchableobject.a(), 
/* 164 */               ((ItemStack)datawatcher_watchableobject.b()).cloneItemStack());
/*     */           }
/*     */           
/*     */ 
/*     */ 
/* 169 */           arraylist.add(datawatcher_watchableobject);
/*     */         }
/*     */       }
/*     */       
/* 173 */       this.f.readLock().unlock();
/*     */     }
/*     */     
/* 176 */     this.e = false;
/* 177 */     return arraylist;
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer packetdataserializer) throws IOException {
/* 181 */     this.f.readLock().lock();
/* 182 */     Iterator iterator = this.dataValues.valueCollection().iterator();
/*     */     
/* 184 */     while (iterator.hasNext()) {
/* 185 */       WatchableObject datawatcher_watchableobject = (WatchableObject)iterator.next();
/*     */       
/* 187 */       a(packetdataserializer, datawatcher_watchableobject);
/*     */     }
/*     */     
/* 190 */     this.f.readLock().unlock();
/* 191 */     packetdataserializer.writeByte(127);
/*     */   }
/*     */   
/*     */   public List<WatchableObject> c() {
/* 195 */     ArrayList arraylist = Lists.newArrayList();
/*     */     
/* 197 */     this.f.readLock().lock();
/*     */     
/* 199 */     arraylist.addAll(this.dataValues.valueCollection());
/*     */     
/* 201 */     for (int i = 0; i < arraylist.size(); i++)
/*     */     {
/* 203 */       WatchableObject watchableobject = (WatchableObject)arraylist.get(i);
/* 204 */       if ((watchableobject.b() instanceof ItemStack))
/*     */       {
/* 206 */         watchableobject = new WatchableObject(
/* 207 */           watchableobject.c(), 
/* 208 */           watchableobject.a(), 
/* 209 */           ((ItemStack)watchableobject.b()).cloneItemStack());
/*     */         
/* 211 */         arraylist.set(i, watchableobject);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 216 */     this.f.readLock().unlock();
/* 217 */     return arraylist;
/*     */   }
/*     */   
/*     */   private static void a(PacketDataSerializer packetdataserializer, WatchableObject datawatcher_watchableobject) throws IOException {
/* 221 */     int i = (datawatcher_watchableobject.c() << 5 | datawatcher_watchableobject.a() & 0x1F) & 0xFF;
/*     */     
/* 223 */     packetdataserializer.writeByte(i);
/* 224 */     switch (datawatcher_watchableobject.c()) {
/*     */     case 0: 
/* 226 */       packetdataserializer.writeByte(((Byte)datawatcher_watchableobject.b()).byteValue());
/* 227 */       break;
/*     */     
/*     */     case 1: 
/* 230 */       packetdataserializer.writeShort(((Short)datawatcher_watchableobject.b()).shortValue());
/* 231 */       break;
/*     */     
/*     */     case 2: 
/* 234 */       packetdataserializer.writeInt(((Integer)datawatcher_watchableobject.b()).intValue());
/* 235 */       break;
/*     */     
/*     */     case 3: 
/* 238 */       packetdataserializer.writeFloat(((Float)datawatcher_watchableobject.b()).floatValue());
/* 239 */       break;
/*     */     
/*     */     case 4: 
/* 242 */       packetdataserializer.a((String)datawatcher_watchableobject.b());
/* 243 */       break;
/*     */     
/*     */     case 5: 
/* 246 */       ItemStack itemstack = (ItemStack)datawatcher_watchableobject.b();
/*     */       
/* 248 */       packetdataserializer.a(itemstack);
/* 249 */       break;
/*     */     
/*     */     case 6: 
/* 252 */       BlockPosition blockposition = (BlockPosition)datawatcher_watchableobject.b();
/*     */       
/* 254 */       packetdataserializer.writeInt(blockposition.getX());
/* 255 */       packetdataserializer.writeInt(blockposition.getY());
/* 256 */       packetdataserializer.writeInt(blockposition.getZ());
/* 257 */       break;
/*     */     
/*     */     case 7: 
/* 260 */       Vector3f vector3f = (Vector3f)datawatcher_watchableobject.b();
/*     */       
/* 262 */       packetdataserializer.writeFloat(vector3f.getX());
/* 263 */       packetdataserializer.writeFloat(vector3f.getY());
/* 264 */       packetdataserializer.writeFloat(vector3f.getZ());
/*     */     }
/*     */   }
/*     */   
/*     */   public static List<WatchableObject> b(PacketDataSerializer packetdataserializer) throws IOException
/*     */   {
/* 270 */     ArrayList arraylist = null;
/*     */     
/* 272 */     for (byte b0 = packetdataserializer.readByte(); b0 != Byte.MAX_VALUE; b0 = packetdataserializer.readByte()) {
/* 273 */       if (arraylist == null) {
/* 274 */         arraylist = Lists.newArrayList();
/*     */       }
/*     */       
/* 277 */       int i = (b0 & 0xE0) >> 5;
/* 278 */       int j = b0 & 0x1F;
/* 279 */       WatchableObject datawatcher_watchableobject = null;
/*     */       
/* 281 */       switch (i) {
/*     */       case 0: 
/* 283 */         datawatcher_watchableobject = new WatchableObject(i, j, Byte.valueOf(packetdataserializer.readByte()));
/* 284 */         break;
/*     */       
/*     */       case 1: 
/* 287 */         datawatcher_watchableobject = new WatchableObject(i, j, Short.valueOf(packetdataserializer.readShort()));
/* 288 */         break;
/*     */       
/*     */       case 2: 
/* 291 */         datawatcher_watchableobject = new WatchableObject(i, j, Integer.valueOf(packetdataserializer.readInt()));
/* 292 */         break;
/*     */       
/*     */       case 3: 
/* 295 */         datawatcher_watchableobject = new WatchableObject(i, j, Float.valueOf(packetdataserializer.readFloat()));
/* 296 */         break;
/*     */       
/*     */       case 4: 
/* 299 */         datawatcher_watchableobject = new WatchableObject(i, j, packetdataserializer.c(32767));
/* 300 */         break;
/*     */       
/*     */       case 5: 
/* 303 */         datawatcher_watchableobject = new WatchableObject(i, j, packetdataserializer.i());
/* 304 */         break;
/*     */       
/*     */       case 6: 
/* 307 */         int k = packetdataserializer.readInt();
/* 308 */         int l = packetdataserializer.readInt();
/* 309 */         int i1 = packetdataserializer.readInt();
/*     */         
/* 311 */         datawatcher_watchableobject = new WatchableObject(i, j, new BlockPosition(k, l, i1));
/* 312 */         break;
/*     */       
/*     */       case 7: 
/* 315 */         float f = packetdataserializer.readFloat();
/* 316 */         float f1 = packetdataserializer.readFloat();
/* 317 */         float f2 = packetdataserializer.readFloat();
/*     */         
/* 319 */         datawatcher_watchableobject = new WatchableObject(i, j, new Vector3f(f, f1, f2));
/*     */       }
/*     */       
/* 322 */       arraylist.add(datawatcher_watchableobject);
/*     */     }
/*     */     
/* 325 */     return arraylist;
/*     */   }
/*     */   
/*     */   public boolean d() {
/* 329 */     return this.b;
/*     */   }
/*     */   
/*     */   public void e() {
/* 333 */     this.e = false;
/*     */   }
/*     */   
/*     */   static
/*     */   {
/* 338 */     classToId.put(Byte.class, 0);
/* 339 */     classToId.put(Short.class, 1);
/* 340 */     classToId.put(Integer.class, 2);
/* 341 */     classToId.put(Float.class, 3);
/* 342 */     classToId.put(String.class, 4);
/* 343 */     classToId.put(ItemStack.class, 5);
/* 344 */     classToId.put(BlockPosition.class, 6);
/* 345 */     classToId.put(Vector3f.class, 7);
/*     */   }
/*     */   
/*     */   public static class WatchableObject
/*     */   {
/*     */     private final int a;
/*     */     private final int b;
/*     */     private Object c;
/*     */     private boolean d;
/*     */     
/*     */     public WatchableObject(int i, int j, Object object)
/*     */     {
/* 357 */       this.b = j;
/* 358 */       this.c = object;
/* 359 */       this.a = i;
/* 360 */       this.d = true;
/*     */     }
/*     */     
/*     */     public int a() {
/* 364 */       return this.b;
/*     */     }
/*     */     
/*     */     public void a(Object object) {
/* 368 */       this.c = object;
/*     */     }
/*     */     
/*     */     public Object b() {
/* 372 */       return this.c;
/*     */     }
/*     */     
/*     */     public int c() {
/* 376 */       return this.a;
/*     */     }
/*     */     
/*     */     public boolean d() {
/* 380 */       return this.d;
/*     */     }
/*     */     
/*     */     public void a(boolean flag) {
/* 384 */       this.d = flag;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\DataWatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */