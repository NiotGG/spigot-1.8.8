/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
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
/*     */ 
/*     */ 
/*     */ public class NBTTagCompound
/*     */   extends NBTBase
/*     */ {
/*  43 */   private Map<String, NBTBase> map = Maps.newHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */   void write(DataOutput paramDataOutput)
/*     */     throws IOException
/*     */   {
/*  50 */     for (String str : this.map.keySet()) {
/*  51 */       NBTBase localNBTBase = (NBTBase)this.map.get(str);
/*  52 */       a(str, localNBTBase, paramDataOutput);
/*     */     }
/*  54 */     paramDataOutput.writeByte(0);
/*     */   }
/*     */   
/*     */   void load(DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) throws IOException
/*     */   {
/*  59 */     paramNBTReadLimiter.a(384L);
/*     */     
/*  61 */     if (paramInt > 512) {
/*  62 */       throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
/*     */     }
/*  64 */     this.map.clear();
/*     */     byte b;
/*  66 */     while ((b = a(paramDataInput, paramNBTReadLimiter)) != 0) {
/*  67 */       String str = b(paramDataInput, paramNBTReadLimiter);
/*  68 */       paramNBTReadLimiter.a(224 + 16 * str.length());
/*     */       
/*  70 */       NBTBase localNBTBase = a(b, str, paramDataInput, paramInt + 1, paramNBTReadLimiter);
/*  71 */       if (this.map.put(str, localNBTBase) != null) {
/*  72 */         paramNBTReadLimiter.a(288L);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<String> c() {
/*  78 */     return this.map.keySet();
/*     */   }
/*     */   
/*     */   public byte getTypeId()
/*     */   {
/*  83 */     return 10;
/*     */   }
/*     */   
/*     */   public void set(String paramString, NBTBase paramNBTBase) {
/*  87 */     this.map.put(paramString, paramNBTBase);
/*     */   }
/*     */   
/*     */   public void setByte(String paramString, byte paramByte) {
/*  91 */     this.map.put(paramString, new NBTTagByte(paramByte));
/*     */   }
/*     */   
/*     */   public void setShort(String paramString, short paramShort) {
/*  95 */     this.map.put(paramString, new NBTTagShort(paramShort));
/*     */   }
/*     */   
/*     */   public void setInt(String paramString, int paramInt) {
/*  99 */     this.map.put(paramString, new NBTTagInt(paramInt));
/*     */   }
/*     */   
/*     */   public void setLong(String paramString, long paramLong) {
/* 103 */     this.map.put(paramString, new NBTTagLong(paramLong));
/*     */   }
/*     */   
/*     */   public void setFloat(String paramString, float paramFloat) {
/* 107 */     this.map.put(paramString, new NBTTagFloat(paramFloat));
/*     */   }
/*     */   
/*     */   public void setDouble(String paramString, double paramDouble) {
/* 111 */     this.map.put(paramString, new NBTTagDouble(paramDouble));
/*     */   }
/*     */   
/*     */   public void setString(String paramString1, String paramString2) {
/* 115 */     this.map.put(paramString1, new NBTTagString(paramString2));
/*     */   }
/*     */   
/*     */   public void setByteArray(String paramString, byte[] paramArrayOfByte) {
/* 119 */     this.map.put(paramString, new NBTTagByteArray(paramArrayOfByte));
/*     */   }
/*     */   
/*     */   public void setIntArray(String paramString, int[] paramArrayOfInt) {
/* 123 */     this.map.put(paramString, new NBTTagIntArray(paramArrayOfInt));
/*     */   }
/*     */   
/*     */   public void setBoolean(String paramString, boolean paramBoolean) {
/* 127 */     setByte(paramString, (byte)(paramBoolean ? 1 : 0));
/*     */   }
/*     */   
/*     */   public NBTBase get(String paramString) {
/* 131 */     return (NBTBase)this.map.get(paramString);
/*     */   }
/*     */   
/*     */   public byte b(String paramString) {
/* 135 */     NBTBase localNBTBase = (NBTBase)this.map.get(paramString);
/* 136 */     if (localNBTBase != null) {
/* 137 */       return localNBTBase.getTypeId();
/*     */     }
/* 139 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean hasKey(String paramString) {
/* 143 */     return this.map.containsKey(paramString);
/*     */   }
/*     */   
/*     */   public boolean hasKeyOfType(String paramString, int paramInt) {
/* 147 */     int i = b(paramString);
/* 148 */     if (i == paramInt) {
/* 149 */       return true;
/*     */     }
/* 151 */     if (paramInt == 99) {
/* 152 */       return (i == 1) || (i == 2) || (i == 3) || (i == 4) || (i == 5) || (i == 6);
/*     */     }
/*     */     
/* 155 */     if (i > 0) {}
/*     */     
/*     */ 
/*     */ 
/* 159 */     return false;
/*     */   }
/*     */   
/*     */   public byte getByte(String paramString) {
/*     */     try {
/* 164 */       if (!hasKeyOfType(paramString, 99)) {
/* 165 */         return 0;
/*     */       }
/* 167 */       return ((NBTBase.NBTNumber)this.map.get(paramString)).f();
/*     */     } catch (ClassCastException localClassCastException) {}
/* 169 */     return 0;
/*     */   }
/*     */   
/*     */   public short getShort(String paramString)
/*     */   {
/*     */     try {
/* 175 */       if (!hasKeyOfType(paramString, 99)) {
/* 176 */         return 0;
/*     */       }
/* 178 */       return ((NBTBase.NBTNumber)this.map.get(paramString)).e();
/*     */     } catch (ClassCastException localClassCastException) {}
/* 180 */     return 0;
/*     */   }
/*     */   
/*     */   public int getInt(String paramString)
/*     */   {
/*     */     try {
/* 186 */       if (!hasKeyOfType(paramString, 99)) {
/* 187 */         return 0;
/*     */       }
/* 189 */       return ((NBTBase.NBTNumber)this.map.get(paramString)).d();
/*     */     } catch (ClassCastException localClassCastException) {}
/* 191 */     return 0;
/*     */   }
/*     */   
/*     */   public long getLong(String paramString)
/*     */   {
/*     */     try {
/* 197 */       if (!hasKeyOfType(paramString, 99)) {
/* 198 */         return 0L;
/*     */       }
/* 200 */       return ((NBTBase.NBTNumber)this.map.get(paramString)).c();
/*     */     } catch (ClassCastException localClassCastException) {}
/* 202 */     return 0L;
/*     */   }
/*     */   
/*     */   public float getFloat(String paramString)
/*     */   {
/*     */     try {
/* 208 */       if (!hasKeyOfType(paramString, 99)) {
/* 209 */         return 0.0F;
/*     */       }
/* 211 */       return ((NBTBase.NBTNumber)this.map.get(paramString)).h();
/*     */     } catch (ClassCastException localClassCastException) {}
/* 213 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public double getDouble(String paramString)
/*     */   {
/*     */     try {
/* 219 */       if (!hasKeyOfType(paramString, 99)) {
/* 220 */         return 0.0D;
/*     */       }
/* 222 */       return ((NBTBase.NBTNumber)this.map.get(paramString)).g();
/*     */     } catch (ClassCastException localClassCastException) {}
/* 224 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public String getString(String paramString)
/*     */   {
/*     */     try {
/* 230 */       if (!hasKeyOfType(paramString, 8)) {
/* 231 */         return "";
/*     */       }
/* 233 */       return ((NBTBase)this.map.get(paramString)).a_();
/*     */     } catch (ClassCastException localClassCastException) {}
/* 235 */     return "";
/*     */   }
/*     */   
/*     */   public byte[] getByteArray(String paramString)
/*     */   {
/*     */     try {
/* 241 */       if (!hasKeyOfType(paramString, 7)) {
/* 242 */         return new byte[0];
/*     */       }
/* 244 */       return ((NBTTagByteArray)this.map.get(paramString)).c();
/*     */     } catch (ClassCastException localClassCastException) {
/* 246 */       throw new ReportedException(a(paramString, 7, localClassCastException));
/*     */     }
/*     */   }
/*     */   
/*     */   public int[] getIntArray(String paramString) {
/*     */     try {
/* 252 */       if (!hasKeyOfType(paramString, 11)) {
/* 253 */         return new int[0];
/*     */       }
/* 255 */       return ((NBTTagIntArray)this.map.get(paramString)).c();
/*     */     } catch (ClassCastException localClassCastException) {
/* 257 */       throw new ReportedException(a(paramString, 11, localClassCastException));
/*     */     }
/*     */   }
/*     */   
/*     */   public NBTTagCompound getCompound(String paramString) {
/*     */     try {
/* 263 */       if (!hasKeyOfType(paramString, 10)) {
/* 264 */         return new NBTTagCompound();
/*     */       }
/* 266 */       return (NBTTagCompound)this.map.get(paramString);
/*     */     } catch (ClassCastException localClassCastException) {
/* 268 */       throw new ReportedException(a(paramString, 10, localClassCastException));
/*     */     }
/*     */   }
/*     */   
/*     */   public NBTTagList getList(String paramString, int paramInt) {
/*     */     try {
/* 274 */       if (b(paramString) != 9) {
/* 275 */         return new NBTTagList();
/*     */       }
/* 277 */       NBTTagList localNBTTagList = (NBTTagList)this.map.get(paramString);
/* 278 */       if ((localNBTTagList.size() > 0) && (localNBTTagList.f() != paramInt)) {
/* 279 */         return new NBTTagList();
/*     */       }
/* 281 */       return localNBTTagList;
/*     */     } catch (ClassCastException localClassCastException) {
/* 283 */       throw new ReportedException(a(paramString, 9, localClassCastException));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getBoolean(String paramString) {
/* 288 */     return getByte(paramString) != 0;
/*     */   }
/*     */   
/*     */   public void remove(String paramString) {
/* 292 */     this.map.remove(paramString);
/*     */   }
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
/*     */   public String toString()
/*     */   {
/* 312 */     StringBuilder localStringBuilder = new StringBuilder("{");
/* 313 */     for (Map.Entry localEntry : this.map.entrySet()) {
/* 314 */       if (localStringBuilder.length() != 1) {
/* 315 */         localStringBuilder.append(',');
/*     */       }
/* 317 */       localStringBuilder.append((String)localEntry.getKey()).append(':').append(localEntry.getValue());
/*     */     }
/* 319 */     return '}';
/*     */   }
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
/*     */   public boolean isEmpty()
/*     */   {
/* 336 */     return this.map.isEmpty();
/*     */   }
/*     */   
/*     */   private CrashReport a(final String paramString, final int paramInt, ClassCastException paramClassCastException) {
/* 340 */     CrashReport localCrashReport = CrashReport.a(paramClassCastException, "Reading NBT data");
/* 341 */     CrashReportSystemDetails localCrashReportSystemDetails = localCrashReport.a("Corrupt NBT tag", 1);
/*     */     
/* 343 */     localCrashReportSystemDetails.a("Tag type found", new Callable()
/*     */     {
/*     */       public String a() throws Exception {
/* 346 */         return NBTBase.a[((NBTBase)NBTTagCompound.b(NBTTagCompound.this).get(paramString)).getTypeId()];
/*     */       }
/* 348 */     });
/* 349 */     localCrashReportSystemDetails.a("Tag type expected", new Callable()
/*     */     {
/*     */       public String a() throws Exception {
/* 352 */         return NBTBase.a[paramInt];
/*     */       }
/* 354 */     });
/* 355 */     localCrashReportSystemDetails.a("Tag name", paramString);
/*     */     
/* 357 */     return localCrashReport;
/*     */   }
/*     */   
/*     */   public NBTBase clone()
/*     */   {
/* 362 */     NBTTagCompound localNBTTagCompound = new NBTTagCompound();
/* 363 */     for (String str : this.map.keySet()) {
/* 364 */       localNBTTagCompound.set(str, ((NBTBase)this.map.get(str)).clone());
/*     */     }
/* 366 */     return localNBTTagCompound;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 371 */     if (super.equals(paramObject)) {
/* 372 */       NBTTagCompound localNBTTagCompound = (NBTTagCompound)paramObject;
/* 373 */       return this.map.entrySet().equals(localNBTTagCompound.map.entrySet());
/*     */     }
/* 375 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 380 */     return super.hashCode() ^ this.map.hashCode();
/*     */   }
/*     */   
/*     */   private static void a(String paramString, NBTBase paramNBTBase, DataOutput paramDataOutput) throws IOException {
/* 384 */     paramDataOutput.writeByte(paramNBTBase.getTypeId());
/* 385 */     if (paramNBTBase.getTypeId() == 0) {
/* 386 */       return;
/*     */     }
/*     */     
/* 389 */     paramDataOutput.writeUTF(paramString);
/*     */     
/* 391 */     paramNBTBase.write(paramDataOutput);
/*     */   }
/*     */   
/*     */   private static byte a(DataInput paramDataInput, NBTReadLimiter paramNBTReadLimiter) throws IOException {
/* 395 */     return paramDataInput.readByte();
/*     */   }
/*     */   
/*     */   private static String b(DataInput paramDataInput, NBTReadLimiter paramNBTReadLimiter) throws IOException {
/* 399 */     return paramDataInput.readUTF();
/*     */   }
/*     */   
/*     */   static NBTBase a(byte paramByte, String paramString, DataInput paramDataInput, int paramInt, NBTReadLimiter paramNBTReadLimiter) throws IOException {
/* 403 */     NBTBase localNBTBase = NBTBase.createTag(paramByte);
/*     */     try
/*     */     {
/* 406 */       localNBTBase.load(paramDataInput, paramInt, paramNBTReadLimiter);
/*     */     } catch (IOException localIOException) {
/* 408 */       CrashReport localCrashReport = CrashReport.a(localIOException, "Loading NBT data");
/* 409 */       CrashReportSystemDetails localCrashReportSystemDetails = localCrashReport.a("NBT Tag");
/* 410 */       localCrashReportSystemDetails.a("Tag name", paramString);
/* 411 */       localCrashReportSystemDetails.a("Tag type", Byte.valueOf(paramByte));
/* 412 */       throw new ReportedException(localCrashReport);
/*     */     }
/*     */     
/* 415 */     return localNBTBase;
/*     */   }
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
/*     */   public void a(NBTTagCompound paramNBTTagCompound)
/*     */   {
/* 432 */     for (String str : paramNBTTagCompound.map.keySet()) {
/* 433 */       NBTBase localNBTBase = (NBTBase)paramNBTTagCompound.map.get(str);
/*     */       
/*     */ 
/* 436 */       if (localNBTBase.getTypeId() == 10) {
/* 437 */         if (hasKeyOfType(str, 10)) {
/* 438 */           NBTTagCompound localNBTTagCompound = getCompound(str);
/* 439 */           localNBTTagCompound.a((NBTTagCompound)localNBTBase);
/*     */         } else {
/* 441 */           set(str, localNBTBase.clone());
/*     */         }
/*     */       } else {
/* 444 */         set(str, localNBTBase.clone());
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NBTTagCompound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */