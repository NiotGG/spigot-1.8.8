/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.util.List;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ 
/*     */ public class RegionFile
/*     */ {
/*  19 */   private static final byte[] a = new byte['က'];
/*     */   private final File b;
/*     */   private RandomAccessFile c;
/*  22 */   private final int[] d = new int['Ѐ'];
/*  23 */   private final int[] e = new int['Ѐ'];
/*     */   private List<Boolean> f;
/*     */   private int g;
/*     */   private long h;
/*     */   
/*     */   public RegionFile(File file) {
/*  29 */     this.b = file;
/*  30 */     this.g = 0;
/*     */     try
/*     */     {
/*  33 */       if (file.exists()) {
/*  34 */         this.h = file.lastModified();
/*     */       }
/*     */       
/*  37 */       this.c = new RandomAccessFile(file, "rw");
/*     */       
/*     */ 
/*  40 */       if (this.c.length() < 4096L)
/*     */       {
/*  42 */         this.c.write(a);
/*  43 */         this.c.write(a);
/*     */         
/*  45 */         this.g += 8192;
/*     */       }
/*     */       
/*  48 */       if ((this.c.length() & 0xFFF) != 0L) {
/*  49 */         for (int i = 0; i < (this.c.length() & 0xFFF); i++) {
/*  50 */           this.c.write(0);
/*     */         }
/*     */       }
/*     */       
/*  54 */       int i = (int)this.c.length() / 4096;
/*  55 */       this.f = Lists.newArrayListWithCapacity(i);
/*     */       
/*     */ 
/*     */ 
/*  59 */       for (int j = 0; j < i; j++) {
/*  60 */         this.f.add(Boolean.valueOf(true));
/*     */       }
/*     */       
/*  63 */       this.f.set(0, Boolean.valueOf(false));
/*  64 */       this.f.set(1, Boolean.valueOf(false));
/*  65 */       this.c.seek(0L);
/*     */       
/*     */ 
/*     */ 
/*  69 */       for (j = 0; j < 1024; j++) {
/*  70 */         int k = this.c.readInt();
/*  71 */         this.d[j] = k;
/*  72 */         if ((k != 0) && ((k >> 8) + (k & 0xFF) <= this.f.size())) {
/*  73 */           for (int l = 0; l < (k & 0xFF); l++) {
/*  74 */             this.f.set((k >> 8) + l, Boolean.valueOf(false));
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*  79 */       for (j = 0; j < 1024; j++) {
/*  80 */         int k = this.c.readInt();
/*  81 */         this.e[j] = k;
/*     */       }
/*     */     } catch (IOException ioexception) {
/*  84 */       ioexception.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public synchronized boolean chunkExists(int i, int j)
/*     */   {
/*  91 */     if (d(i, j)) {
/*  92 */       return false;
/*     */     }
/*     */     try {
/*  95 */       int k = e(i, j);
/*     */       
/*  97 */       if (k == 0) {
/*  98 */         return false;
/*     */       }
/* 100 */       int l = k >> 8;
/* 101 */       int i1 = k & 0xFF;
/*     */       
/* 103 */       if (l + i1 > this.f.size()) {
/* 104 */         return false;
/*     */       }
/*     */       
/* 107 */       this.c.seek(l * 4096);
/* 108 */       int j1 = this.c.readInt();
/*     */       
/* 110 */       if ((j1 > 4096 * i1) || (j1 <= 0)) {
/* 111 */         return false;
/*     */       }
/*     */       
/* 114 */       byte b0 = this.c.readByte();
/* 115 */       if ((b0 == 1) || (b0 == 2)) {
/* 116 */         return true;
/*     */       }
/*     */     }
/*     */     catch (IOException localIOException) {
/* 120 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 124 */     return false;
/*     */   }
/*     */   
/*     */   public synchronized DataInputStream a(int i, int j)
/*     */   {
/* 129 */     if (d(i, j)) {
/* 130 */       return null;
/*     */     }
/*     */     try {
/* 133 */       int k = e(i, j);
/*     */       
/* 135 */       if (k == 0) {
/* 136 */         return null;
/*     */       }
/* 138 */       int l = k >> 8;
/* 139 */       int i1 = k & 0xFF;
/*     */       
/* 141 */       if (l + i1 > this.f.size()) {
/* 142 */         return null;
/*     */       }
/* 144 */       this.c.seek(l * 4096);
/* 145 */       int j1 = this.c.readInt();
/*     */       
/* 147 */       if (j1 > 4096 * i1)
/* 148 */         return null;
/* 149 */       if (j1 <= 0) {
/* 150 */         return null;
/*     */       }
/* 152 */       byte b0 = this.c.readByte();
/*     */       
/*     */ 
/* 155 */       if (b0 == 1) {
/* 156 */         byte[] abyte = new byte[j1 - 1];
/* 157 */         this.c.read(abyte);
/* 158 */         return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte)))); }
/* 159 */       if (b0 == 2) {
/* 160 */         byte[] abyte = new byte[j1 - 1];
/* 161 */         this.c.read(abyte);
/* 162 */         return new DataInputStream(new BufferedInputStream(new java.util.zip.InflaterInputStream(new ByteArrayInputStream(abyte))));
/*     */       }
/* 164 */       return null;
/*     */     }
/*     */     catch (IOException localIOException) {}
/*     */     
/*     */ 
/*     */ 
/* 170 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public DataOutputStream b(int i, int j)
/*     */   {
/* 177 */     return d(i, j) ? null : new DataOutputStream(new BufferedOutputStream(new DeflaterOutputStream(new ChunkBuffer(i, j))));
/*     */   }
/*     */   
/*     */   protected synchronized void a(int i, int j, byte[] abyte, int k) {
/*     */     try {
/* 182 */       int l = e(i, j);
/* 183 */       int i1 = l >> 8;
/* 184 */       int j1 = l & 0xFF;
/* 185 */       int k1 = (k + 5) / 4096 + 1;
/*     */       
/* 187 */       if (k1 >= 256) {
/* 188 */         return;
/*     */       }
/*     */       
/* 191 */       if ((i1 != 0) && (j1 == k1)) {
/* 192 */         a(i1, abyte, k);
/*     */       }
/*     */       else
/*     */       {
/* 196 */         for (int l1 = 0; l1 < j1; l1++) {
/* 197 */           this.f.set(i1 + l1, Boolean.valueOf(true));
/*     */         }
/*     */         
/* 200 */         l1 = this.f.indexOf(Boolean.valueOf(true));
/* 201 */         int i2 = 0;
/*     */         
/*     */ 
/* 204 */         if (l1 != -1) {
/* 205 */           for (int j2 = l1; j2 < this.f.size(); j2++) {
/* 206 */             if (i2 != 0) {
/* 207 */               if (((Boolean)this.f.get(j2)).booleanValue()) {
/* 208 */                 i2++;
/*     */               } else {
/* 210 */                 i2 = 0;
/*     */               }
/* 212 */             } else if (((Boolean)this.f.get(j2)).booleanValue()) {
/* 213 */               l1 = j2;
/* 214 */               i2 = 1;
/*     */             }
/*     */             
/* 217 */             if (i2 >= k1) {
/*     */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 223 */         if (i2 >= k1) {
/* 224 */           i1 = l1;
/* 225 */           a(i, j, l1 << 8 | k1);
/*     */           
/* 227 */           for (int j2 = 0; j2 < k1; j2++) {
/* 228 */             this.f.set(i1 + j2, Boolean.valueOf(false));
/*     */           }
/*     */           
/* 231 */           a(i1, abyte, k);
/*     */         } else {
/* 233 */           this.c.seek(this.c.length());
/* 234 */           i1 = this.f.size();
/*     */           
/* 236 */           for (int j2 = 0; j2 < k1; j2++) {
/* 237 */             this.c.write(a);
/* 238 */             this.f.add(Boolean.valueOf(false));
/*     */           }
/*     */           
/* 241 */           this.g += 4096 * k1;
/* 242 */           a(i1, abyte, k);
/* 243 */           a(i, j, i1 << 8 | k1);
/*     */         }
/*     */       }
/*     */       
/* 247 */       b(i, j, (int)(MinecraftServer.az() / 1000L));
/*     */     } catch (IOException ioexception) {
/* 249 */       ioexception.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private void a(int i, byte[] abyte, int j) throws IOException
/*     */   {
/* 255 */     this.c.seek(i * 4096);
/* 256 */     this.c.writeInt(j + 1);
/* 257 */     this.c.writeByte(2);
/* 258 */     this.c.write(abyte, 0, j);
/*     */   }
/*     */   
/*     */   private boolean d(int i, int j) {
/* 262 */     return (i < 0) || (i >= 32) || (j < 0) || (j >= 32);
/*     */   }
/*     */   
/*     */   private int e(int i, int j) {
/* 266 */     return this.d[(i + j * 32)];
/*     */   }
/*     */   
/*     */   public boolean c(int i, int j) {
/* 270 */     return e(i, j) != 0;
/*     */   }
/*     */   
/*     */   private void a(int i, int j, int k) throws IOException {
/* 274 */     this.d[(i + j * 32)] = k;
/* 275 */     this.c.seek((i + j * 32) * 4);
/* 276 */     this.c.writeInt(k);
/*     */   }
/*     */   
/*     */   private void b(int i, int j, int k) throws IOException {
/* 280 */     this.e[(i + j * 32)] = k;
/* 281 */     this.c.seek(4096 + (i + j * 32) * 4);
/* 282 */     this.c.writeInt(k);
/*     */   }
/*     */   
/*     */   public void c() throws IOException {
/* 286 */     if (this.c != null) {
/* 287 */       this.c.close();
/*     */     }
/*     */   }
/*     */   
/*     */   class ChunkBuffer extends ByteArrayOutputStream
/*     */   {
/*     */     private int b;
/*     */     private int c;
/*     */     
/*     */     public ChunkBuffer(int i, int j)
/*     */     {
/* 298 */       super();
/* 299 */       this.b = i;
/* 300 */       this.c = j;
/*     */     }
/*     */     
/*     */     public void close() {
/* 304 */       RegionFile.this.a(this.b, this.c, this.buf, this.count);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\RegionFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */