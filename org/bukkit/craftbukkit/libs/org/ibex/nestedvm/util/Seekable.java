/*     */ package org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ public abstract class Seekable {
/*     */   public abstract int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   public abstract int write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException;
/*     */   
/*     */   public abstract int length() throws IOException;
/*     */   
/*     */   public abstract void seek(int paramInt) throws IOException;
/*     */   
/*     */   public abstract void close() throws IOException;
/*     */   
/*     */   public abstract int pos() throws IOException;
/*     */   
/*  18 */   public void sync() throws IOException { throw new IOException("sync not implemented for " + getClass()); }
/*     */   
/*     */   public void resize(long paramLong) throws IOException {
/*  21 */     throw new IOException("resize not implemented for " + getClass());
/*     */   }
/*     */   
/*     */   public Lock lock(long paramLong1, long paramLong2, boolean paramBoolean) throws IOException {
/*  25 */     throw new IOException("lock not implemented for " + getClass());
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/*  29 */     byte[] arrayOfByte = new byte[1];
/*  30 */     int i = read(arrayOfByte, 0, 1);
/*  31 */     return i == -1 ? -1 : arrayOfByte[0] & 0xFF;
/*     */   }
/*     */   
/*     */   public int tryReadFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
/*  35 */     int i = 0;
/*  36 */     while (paramInt2 > 0) {
/*  37 */       int j = read(paramArrayOfByte, paramInt1, paramInt2);
/*  38 */       if (j == -1) break;
/*  39 */       paramInt1 += j;
/*  40 */       paramInt2 -= j;
/*  41 */       i += j;
/*     */     }
/*  43 */     return i == 0 ? -1 : i;
/*     */   }
/*     */   
/*     */   public static class ByteArray extends Seekable {
/*     */     protected byte[] data;
/*     */     protected int pos;
/*     */     private final boolean writable;
/*     */     
/*     */     public ByteArray(byte[] paramArrayOfByte, boolean paramBoolean) {
/*  52 */       this.data = paramArrayOfByte;
/*  53 */       this.pos = 0;
/*  54 */       this.writable = paramBoolean;
/*     */     }
/*     */     
/*     */     public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
/*  58 */       paramInt2 = Math.min(paramInt2, this.data.length - this.pos);
/*  59 */       if (paramInt2 <= 0) return -1;
/*  60 */       System.arraycopy(this.data, this.pos, paramArrayOfByte, paramInt1, paramInt2);
/*  61 */       this.pos += paramInt2;
/*  62 */       return paramInt2;
/*     */     }
/*     */     
/*     */     public int write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
/*  66 */       if (!this.writable) throw new IOException("read-only data");
/*  67 */       paramInt2 = Math.min(paramInt2, this.data.length - this.pos);
/*  68 */       if (paramInt2 <= 0) throw new IOException("no space");
/*  69 */       System.arraycopy(paramArrayOfByte, paramInt1, this.data, this.pos, paramInt2);
/*  70 */       this.pos += paramInt2;
/*  71 */       return paramInt2;
/*     */     }
/*     */     
/*  74 */     public int length() { return this.data.length; }
/*  75 */     public int pos() { return this.pos; }
/*  76 */     public void seek(int paramInt) { this.pos = paramInt; }
/*     */     
/*     */     public void close() {}
/*     */   }
/*     */   
/*     */   public static class File extends Seekable { private final java.io.File file;
/*     */     private final java.io.RandomAccessFile raf;
/*     */     
/*  84 */     public File(String paramString) throws IOException { this(paramString, false); }
/*  85 */     public File(String paramString, boolean paramBoolean) throws IOException { this(new java.io.File(paramString), paramBoolean, false); }
/*     */     
/*     */     public File(java.io.File paramFile, boolean paramBoolean1, boolean paramBoolean2) throws IOException {
/*  88 */       this.file = paramFile;
/*  89 */       String str = paramBoolean1 ? "rw" : "r";
/*  90 */       this.raf = new java.io.RandomAccessFile(paramFile, str);
/*  91 */       if (paramBoolean2) Platform.setFileLength(this.raf, 0);
/*     */     }
/*     */     
/*  94 */     public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException { return this.raf.read(paramArrayOfByte, paramInt1, paramInt2); }
/*  95 */     public int write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException { this.raf.write(paramArrayOfByte, paramInt1, paramInt2);return paramInt2; }
/*  96 */     public void sync() throws IOException { this.raf.getFD().sync(); }
/*  97 */     public void seek(int paramInt) throws IOException { this.raf.seek(paramInt); }
/*  98 */     public int pos() throws IOException { return (int)this.raf.getFilePointer(); }
/*  99 */     public int length() throws IOException { return (int)this.raf.length(); }
/* 100 */     public void close() throws IOException { this.raf.close(); }
/* 101 */     public void resize(long paramLong) throws IOException { Platform.setFileLength(this.raf, (int)paramLong); }
/*     */     
/* 103 */     public boolean equals(Object paramObject) { return (paramObject != null) && ((paramObject instanceof File)) && (this.file.equals(((File)paramObject).file)); }
/*     */     
/*     */     public Seekable.Lock lock(long paramLong1, long paramLong2, boolean paramBoolean)
/*     */       throws IOException
/*     */     {
/* 108 */       return Platform.lockFile(this, this.raf, paramLong1, paramLong2, paramBoolean);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class InputStream extends Seekable {
/* 113 */     private byte[] buffer = new byte['က'];
/* 114 */     private int bytesRead = 0;
/* 115 */     private boolean eof = false;
/*     */     private int pos;
/*     */     private java.io.InputStream is;
/*     */     
/* 119 */     public InputStream(java.io.InputStream paramInputStream) { this.is = paramInputStream; }
/*     */     
/*     */     public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
/* 122 */       if ((this.pos >= this.bytesRead) && (!this.eof)) readTo(this.pos + 1);
/* 123 */       paramInt2 = Math.min(paramInt2, this.bytesRead - this.pos);
/* 124 */       if (paramInt2 <= 0) return -1;
/* 125 */       System.arraycopy(this.buffer, this.pos, paramArrayOfByte, paramInt1, paramInt2);
/* 126 */       this.pos += paramInt2;
/* 127 */       return paramInt2;
/*     */     }
/*     */     
/*     */     private void readTo(int paramInt) throws IOException {
/* 131 */       if (paramInt >= this.buffer.length) {
/* 132 */         byte[] arrayOfByte = new byte[Math.max(this.buffer.length + Math.min(this.buffer.length, 65536), paramInt)];
/* 133 */         System.arraycopy(this.buffer, 0, arrayOfByte, 0, this.bytesRead);
/* 134 */         this.buffer = arrayOfByte;
/*     */       }
/* 136 */       while (this.bytesRead < paramInt) {
/* 137 */         int i = this.is.read(this.buffer, this.bytesRead, this.buffer.length - this.bytesRead);
/* 138 */         if (i == -1) {
/* 139 */           this.eof = true;
/* 140 */           break;
/*     */         }
/* 142 */         this.bytesRead += i;
/*     */       }
/*     */     }
/*     */     
/*     */     public int length() throws IOException {
/* 147 */       while (!this.eof) readTo(this.bytesRead + 4096);
/* 148 */       return this.bytesRead;
/*     */     }
/*     */     
/* 151 */     public int write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException { throw new IOException("read-only"); }
/* 152 */     public void seek(int paramInt) { this.pos = paramInt; }
/* 153 */     public int pos() { return this.pos; }
/* 154 */     public void close() throws IOException { this.is.close(); } }
/*     */   public static abstract class Lock { public abstract Seekable seekable();
/*     */     public abstract boolean isShared();
/*     */     public abstract boolean isValid();
/* 158 */     private Object owner = null;
/*     */     
/*     */ 
/*     */     public abstract void release() throws IOException;
/*     */     
/*     */     public abstract long position();
/*     */     
/*     */     public abstract long size();
/*     */     
/* 167 */     public void setOwner(Object paramObject) { this.owner = paramObject; }
/* 168 */     public Object getOwner() { return this.owner; }
/*     */     
/*     */     public final boolean contains(int paramInt1, int paramInt2) {
/* 171 */       return (paramInt1 >= position()) && (position() + size() >= paramInt1 + paramInt2);
/*     */     }
/*     */     
/*     */     public final boolean contained(int paramInt1, int paramInt2) {
/* 175 */       return (paramInt1 < position()) && (position() + size() < paramInt1 + paramInt2);
/*     */     }
/*     */     
/*     */     public final boolean overlaps(int paramInt1, int paramInt2) {
/* 179 */       return (contains(paramInt1, paramInt2)) || (contained(paramInt1, paramInt2));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\org\ibex\nestedvm\util\Seekable.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */