/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class NBTTagList extends NBTBase
/*     */ {
/*  14 */   private static final Logger b = ;
/*  15 */   private List<NBTBase> list = Lists.newArrayList();
/*  16 */   private byte type = 0;
/*     */   
/*     */   void write(DataOutput dataoutput)
/*     */     throws IOException
/*     */   {
/*  21 */     if (!this.list.isEmpty()) {
/*  22 */       this.type = ((NBTBase)this.list.get(0)).getTypeId();
/*     */     } else {
/*  24 */       this.type = 0;
/*     */     }
/*     */     
/*  27 */     dataoutput.writeByte(this.type);
/*  28 */     dataoutput.writeInt(this.list.size());
/*     */     
/*  30 */     for (int i = 0; i < this.list.size(); i++) {
/*  31 */       ((NBTBase)this.list.get(i)).write(dataoutput);
/*     */     }
/*     */   }
/*     */   
/*     */   void load(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException
/*     */   {
/*  37 */     nbtreadlimiter.a(296L);
/*  38 */     if (i > 512) {
/*  39 */       throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
/*     */     }
/*  41 */     this.type = datainput.readByte();
/*  42 */     int j = datainput.readInt();
/*  43 */     nbtreadlimiter.a(j * 8);
/*     */     
/*  45 */     if ((this.type == 0) && (j > 0)) {
/*  46 */       throw new RuntimeException("Missing type on ListTag");
/*     */     }
/*  48 */     nbtreadlimiter.a(32L * j);
/*  49 */     this.list = Lists.newArrayListWithCapacity(j);
/*     */     
/*  51 */     for (int k = 0; k < j; k++) {
/*  52 */       NBTBase nbtbase = NBTBase.createTag(this.type);
/*     */       
/*  54 */       nbtbase.load(datainput, i + 1, nbtreadlimiter);
/*  55 */       this.list.add(nbtbase);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public byte getTypeId()
/*     */   {
/*  63 */     return 9;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  67 */     StringBuilder stringbuilder = new StringBuilder("[");
/*     */     
/*  69 */     for (int i = 0; i < this.list.size(); i++) {
/*  70 */       if (i != 0) {
/*  71 */         stringbuilder.append(',');
/*     */       }
/*     */       
/*  74 */       stringbuilder.append(i).append(':').append(this.list.get(i));
/*     */     }
/*     */     
/*  77 */     return ']';
/*     */   }
/*     */   
/*     */   public void add(NBTBase nbtbase) {
/*  81 */     if (nbtbase.getTypeId() == 0) {
/*  82 */       b.warn("Invalid TagEnd added to ListTag");
/*     */     } else {
/*  84 */       if (this.type == 0) {
/*  85 */         this.type = nbtbase.getTypeId();
/*  86 */       } else if (this.type != nbtbase.getTypeId()) {
/*  87 */         b.warn("Adding mismatching tag types to tag list");
/*  88 */         return;
/*     */       }
/*     */       
/*  91 */       this.list.add(nbtbase);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(int i, NBTBase nbtbase) {
/*  96 */     if (nbtbase.getTypeId() == 0) {
/*  97 */       b.warn("Invalid TagEnd added to ListTag");
/*  98 */     } else if ((i >= 0) && (i < this.list.size())) {
/*  99 */       if (this.type == 0) {
/* 100 */         this.type = nbtbase.getTypeId();
/* 101 */       } else if (this.type != nbtbase.getTypeId()) {
/* 102 */         b.warn("Adding mismatching tag types to tag list");
/* 103 */         return;
/*     */       }
/*     */       
/* 106 */       this.list.set(i, nbtbase);
/*     */     } else {
/* 108 */       b.warn("index out of bounds to set tag in tag list");
/*     */     }
/*     */   }
/*     */   
/*     */   public NBTBase a(int i) {
/* 113 */     return (NBTBase)this.list.remove(i);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 117 */     return this.list.isEmpty();
/*     */   }
/*     */   
/*     */   public NBTTagCompound get(int i) {
/* 121 */     if ((i >= 0) && (i < this.list.size())) {
/* 122 */       NBTBase nbtbase = (NBTBase)this.list.get(i);
/*     */       
/* 124 */       return nbtbase.getTypeId() == 10 ? (NBTTagCompound)nbtbase : new NBTTagCompound();
/*     */     }
/* 126 */     return new NBTTagCompound();
/*     */   }
/*     */   
/*     */   public int[] c(int i)
/*     */   {
/* 131 */     if ((i >= 0) && (i < this.list.size())) {
/* 132 */       NBTBase nbtbase = (NBTBase)this.list.get(i);
/*     */       
/* 134 */       return nbtbase.getTypeId() == 11 ? ((NBTTagIntArray)nbtbase).c() : new int[0];
/*     */     }
/* 136 */     return new int[0];
/*     */   }
/*     */   
/*     */   public double d(int i)
/*     */   {
/* 141 */     if ((i >= 0) && (i < this.list.size())) {
/* 142 */       NBTBase nbtbase = (NBTBase)this.list.get(i);
/*     */       
/* 144 */       return nbtbase.getTypeId() == 6 ? ((NBTTagDouble)nbtbase).g() : 0.0D;
/*     */     }
/* 146 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public float e(int i)
/*     */   {
/* 151 */     if ((i >= 0) && (i < this.list.size())) {
/* 152 */       NBTBase nbtbase = (NBTBase)this.list.get(i);
/*     */       
/* 154 */       return nbtbase.getTypeId() == 5 ? ((NBTTagFloat)nbtbase).h() : 0.0F;
/*     */     }
/* 156 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public String getString(int i)
/*     */   {
/* 161 */     if ((i >= 0) && (i < this.list.size())) {
/* 162 */       NBTBase nbtbase = (NBTBase)this.list.get(i);
/*     */       
/* 164 */       return nbtbase.getTypeId() == 8 ? nbtbase.a_() : nbtbase.toString();
/*     */     }
/* 166 */     return "";
/*     */   }
/*     */   
/*     */   public NBTBase g(int i)
/*     */   {
/* 171 */     return (i >= 0) && (i < this.list.size()) ? (NBTBase)this.list.get(i) : new NBTTagEnd();
/*     */   }
/*     */   
/*     */   public int size() {
/* 175 */     return this.list.size();
/*     */   }
/*     */   
/*     */   public NBTBase clone() {
/* 179 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 181 */     nbttaglist.type = this.type;
/* 182 */     Iterator iterator = this.list.iterator();
/*     */     
/* 184 */     while (iterator.hasNext()) {
/* 185 */       NBTBase nbtbase = (NBTBase)iterator.next();
/* 186 */       NBTBase nbtbase1 = nbtbase.clone();
/*     */       
/* 188 */       nbttaglist.list.add(nbtbase1);
/*     */     }
/*     */     
/* 191 */     return nbttaglist;
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 195 */     if (super.equals(object)) {
/* 196 */       NBTTagList nbttaglist = (NBTTagList)object;
/*     */       
/* 198 */       if (this.type == nbttaglist.type) {
/* 199 */         return this.list.equals(nbttaglist.list);
/*     */       }
/*     */     }
/*     */     
/* 203 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 207 */     return super.hashCode() ^ this.list.hashCode();
/*     */   }
/*     */   
/*     */   public int f() {
/* 211 */     return this.type;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\NBTTagList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */