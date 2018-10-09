/*     */ package org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.channels.FileLock;
/*     */ import java.nio.channels.OverlappingFileLockException;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public abstract class Platform
/*     */ {
/*     */   private static final Platform p;
/*     */   
/*     */   static
/*     */   {
/*     */     float f;
/*     */     try
/*     */     {
/*  28 */       if (getProperty("java.vm.name").equals("SableVM")) {
/*  29 */         f = 1.2F;
/*     */       } else
/*  31 */         f = Float.valueOf(getProperty("java.specification.version")).floatValue();
/*     */     } catch (Exception localException1) {
/*  33 */       System.err.println("WARNING: " + localException1 + " while trying to find jvm version -  assuming 1.1");
/*  34 */       f = 1.1F;
/*     */     }
/*     */     String str;
/*  37 */     if (f >= 1.4F) { str = "Jdk14";
/*  38 */     } else if (f >= 1.3F) { str = "Jdk13";
/*  39 */     } else if (f >= 1.2F) { str = "Jdk12";
/*  40 */     } else if (f >= 1.1F) str = "Jdk11"; else {
/*  41 */       throw new Error("JVM Specification version: " + f + " is too old. (see org.ibex.util.Platform to add support)");
/*     */     }
/*     */     try {
/*  44 */       p = (Platform)Class.forName(Platform.class.getName() + "$" + str).newInstance();
/*     */     } catch (Exception localException2) {
/*  46 */       localException2.printStackTrace();
/*  47 */       throw new Error("Error instansiating platform class");
/*     */     }
/*     */   }
/*     */   
/*     */   public static String getProperty(String paramString) {
/*     */     try {
/*  53 */       return System.getProperty(paramString);
/*     */     } catch (SecurityException localSecurityException) {}
/*  55 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean atomicCreateFile(File paramFile)
/*     */     throws IOException
/*     */   {
/*  61 */     return p._atomicCreateFile(paramFile);
/*     */   }
/*     */   
/*     */   public static Seekable.Lock lockFile(Seekable paramSeekable, RandomAccessFile paramRandomAccessFile, long paramLong1, long paramLong2, boolean paramBoolean) throws IOException {
/*  65 */     return p._lockFile(paramSeekable, paramRandomAccessFile, paramLong1, paramLong2, paramBoolean);
/*     */   }
/*     */   
/*  68 */   public static void socketHalfClose(Socket paramSocket, boolean paramBoolean) throws IOException { p._socketHalfClose(paramSocket, paramBoolean); }
/*     */   
/*     */   public static void socketSetKeepAlive(Socket paramSocket, boolean paramBoolean) throws SocketException {
/*  71 */     p._socketSetKeepAlive(paramSocket, paramBoolean);
/*     */   }
/*     */   
/*  74 */   public static InetAddress inetAddressFromBytes(byte[] paramArrayOfByte) throws UnknownHostException { return p._inetAddressFromBytes(paramArrayOfByte); }
/*     */   
/*     */ 
/*  77 */   public static String timeZoneGetDisplayName(TimeZone paramTimeZone, boolean paramBoolean1, boolean paramBoolean2, Locale paramLocale) { return p._timeZoneGetDisplayName(paramTimeZone, paramBoolean1, paramBoolean2, paramLocale); }
/*  78 */   public static String timeZoneGetDisplayName(TimeZone paramTimeZone, boolean paramBoolean1, boolean paramBoolean2) { return timeZoneGetDisplayName(paramTimeZone, paramBoolean1, paramBoolean2, Locale.getDefault()); }
/*     */   
/*     */   public static void setFileLength(RandomAccessFile paramRandomAccessFile, int paramInt)
/*     */     throws IOException
/*     */   {
/*  83 */     p._setFileLength(paramRandomAccessFile, paramInt);
/*     */   }
/*     */   
/*  86 */   public static File[] listRoots() { return p._listRoots(); }
/*     */   
/*     */ 
/*  89 */   public static File getRoot(File paramFile) { return p._getRoot(paramFile); }
/*     */   
/*     */   abstract boolean _atomicCreateFile(File paramFile) throws IOException;
/*     */   
/*     */   static class Jdk11 extends Platform {
/*  94 */     boolean _atomicCreateFile(File paramFile) throws IOException { if (paramFile.exists()) return false;
/*  95 */       new FileOutputStream(paramFile).close();
/*  96 */       return true;
/*     */     }
/*     */     
/*  99 */     Seekable.Lock _lockFile(Seekable paramSeekable, RandomAccessFile paramRandomAccessFile, long paramLong1, long paramLong2, boolean paramBoolean) throws IOException { throw new IOException("file locking requires jdk 1.4+"); }
/*     */     
/*     */ 
/* 102 */     void _socketHalfClose(Socket paramSocket, boolean paramBoolean) throws IOException { throw new IOException("half closing sockets not supported"); }
/*     */     
/*     */     InetAddress _inetAddressFromBytes(byte[] paramArrayOfByte) throws UnknownHostException {
/* 105 */       if (paramArrayOfByte.length != 4) throw new UnknownHostException("only ipv4 addrs supported");
/* 106 */       return InetAddress.getByName("" + (paramArrayOfByte[0] & 0xFF) + "." + (paramArrayOfByte[1] & 0xFF) + "." + (paramArrayOfByte[2] & 0xFF) + "." + (paramArrayOfByte[3] & 0xFF));
/*     */     }
/*     */     
/* 109 */     void _socketSetKeepAlive(Socket paramSocket, boolean paramBoolean) throws SocketException { if (paramBoolean) throw new SocketException("keepalive not supported");
/*     */     }
/*     */     
/* 112 */     String _timeZoneGetDisplayName(TimeZone paramTimeZone, boolean paramBoolean1, boolean paramBoolean2, Locale paramLocale) { String[][] arrayOfString = new java.text.DateFormatSymbols(paramLocale).getZoneStrings();
/* 113 */       String str = paramTimeZone.getID();
/* 114 */       for (int i = 0; i < arrayOfString.length; i++)
/* 115 */         if (arrayOfString[i][0].equals(str))
/* 116 */           return arrayOfString[i][2];
/* 117 */       StringBuffer localStringBuffer = new StringBuffer("GMT");
/* 118 */       int j = paramTimeZone.getRawOffset() / 1000;
/* 119 */       if (j < 0) { localStringBuffer.append("-");j = -j;
/* 120 */       } else { localStringBuffer.append("+"); }
/* 121 */       localStringBuffer.append(j / 3600);j %= 3600;
/* 122 */       if (j > 0) localStringBuffer.append(":").append(j / 60); j %= 60;
/* 123 */       if (j > 0) localStringBuffer.append(":").append(j);
/* 124 */       return localStringBuffer.toString();
/*     */     }
/*     */     
/*     */     void _setFileLength(RandomAccessFile paramRandomAccessFile, int paramInt) throws IOException {
/* 128 */       FileInputStream localFileInputStream = new FileInputStream(paramRandomAccessFile.getFD());
/* 129 */       FileOutputStream localFileOutputStream = new FileOutputStream(paramRandomAccessFile.getFD());
/*     */       
/* 131 */       byte[] arrayOfByte = new byte['Ѐ'];
/* 132 */       for (; paramInt > 0; paramInt -= i) {
/* 133 */         i = localFileInputStream.read(arrayOfByte, 0, Math.min(paramInt, arrayOfByte.length));
/* 134 */         if (i == -1) break;
/* 135 */         localFileOutputStream.write(arrayOfByte, 0, i);
/*     */       }
/* 137 */       if (paramInt == 0) { return;
/*     */       }
/*     */       
/* 140 */       for (int i = 0; i < arrayOfByte.length; i++) arrayOfByte[i] = 0;
/* 141 */       while (paramInt > 0) {
/* 142 */         localFileOutputStream.write(arrayOfByte, 0, Math.min(paramInt, arrayOfByte.length));
/* 143 */         paramInt -= arrayOfByte.length;
/*     */       }
/*     */     }
/*     */     
/*     */     RandomAccessFile _truncatedRandomAccessFile(File paramFile, String paramString) throws IOException {
/* 148 */       new FileOutputStream(paramFile).close();
/* 149 */       return new RandomAccessFile(paramFile, paramString);
/*     */     }
/*     */     
/*     */     File[] _listRoots() {
/* 153 */       String[] arrayOfString = { "java.home", "java.class.path", "java.library.path", "java.io.tmpdir", "java.ext.dirs", "user.home", "user.dir" };
/* 154 */       Hashtable localHashtable = new Hashtable();
/* 155 */       for (int i = 0; i < arrayOfString.length; i++) {
/* 156 */         String str = getProperty(arrayOfString[i]);
/* 157 */         if (str != null)
/*     */           for (;;) {
/* 159 */             localObject = str;
/*     */             int k;
/* 161 */             if ((k = str.indexOf(File.pathSeparatorChar)) != -1) {
/* 162 */               localObject = str.substring(0, k);
/* 163 */               str = str.substring(k + 1);
/*     */             }
/* 165 */             File localFile = getRoot(new File((String)localObject));
/*     */             
/* 167 */             localHashtable.put(localFile, Boolean.TRUE);
/* 168 */             if (k == -1) break;
/*     */           }
/*     */       }
/* 171 */       File[] arrayOfFile = new File[localHashtable.size()];
/* 172 */       int j = 0;
/* 173 */       for (Object localObject = localHashtable.keys(); ((Enumeration)localObject).hasMoreElements();)
/* 174 */         arrayOfFile[(j++)] = ((File)((Enumeration)localObject).nextElement());
/* 175 */       return arrayOfFile;
/*     */     }
/*     */     
/*     */     File _getRoot(File paramFile) {
/* 179 */       if (!paramFile.isAbsolute()) paramFile = new File(paramFile.getAbsolutePath());
/*     */       String str;
/* 181 */       while ((str = paramFile.getParent()) != null) paramFile = new File(str);
/* 182 */       if (paramFile.getPath().length() == 0) paramFile = new File("/");
/* 183 */       return paramFile;
/*     */     }
/*     */   }
/*     */   
/*     */   abstract Seekable.Lock _lockFile(Seekable paramSeekable, RandomAccessFile paramRandomAccessFile, long paramLong1, long paramLong2, boolean paramBoolean) throws IOException;
/*     */   
/* 189 */   static class Jdk12 extends Platform.Jdk11 { boolean _atomicCreateFile(File paramFile) throws IOException { return paramFile.createNewFile(); }
/*     */     
/*     */     String _timeZoneGetDisplayName(TimeZone paramTimeZone, boolean paramBoolean1, boolean paramBoolean2, Locale paramLocale)
/*     */     {
/* 193 */       return paramTimeZone.getDisplayName(paramBoolean1, paramBoolean2 ? 1 : 0, paramLocale);
/*     */     }
/*     */     
/*     */     void _setFileLength(RandomAccessFile paramRandomAccessFile, int paramInt) throws IOException {
/* 197 */       paramRandomAccessFile.setLength(paramInt);
/*     */     }
/*     */     
/* 200 */     File[] _listRoots() { return File.listRoots(); }
/*     */   }
/*     */   
/*     */   abstract void _socketHalfClose(Socket paramSocket, boolean paramBoolean) throws IOException;
/*     */   
/* 205 */   static class Jdk13 extends Platform.Jdk12 { void _socketHalfClose(Socket paramSocket, boolean paramBoolean) throws IOException { if (paramBoolean) paramSocket.shutdownOutput(); else {
/* 206 */         paramSocket.shutdownInput();
/*     */       }
/*     */     }
/*     */     
/* 210 */     void _socketSetKeepAlive(Socket paramSocket, boolean paramBoolean) throws SocketException { paramSocket.setKeepAlive(paramBoolean); }
/*     */   }
/*     */   
/*     */   abstract void _socketSetKeepAlive(Socket paramSocket, boolean paramBoolean) throws SocketException;
/*     */   
/* 215 */   static class Jdk14 extends Platform.Jdk13 { InetAddress _inetAddressFromBytes(byte[] paramArrayOfByte) throws UnknownHostException { return InetAddress.getByAddress(paramArrayOfByte); }
/*     */     
/*     */     Seekable.Lock _lockFile(Seekable paramSeekable, RandomAccessFile paramRandomAccessFile, long paramLong1, long paramLong2, boolean paramBoolean) throws IOException {
/*     */       FileLock localFileLock;
/*     */       try {
/* 220 */         localFileLock = (paramLong1 == 0L) && (paramLong2 == 0L) ? paramRandomAccessFile.getChannel().lock() : paramRandomAccessFile.getChannel().tryLock(paramLong1, paramLong2, paramBoolean);
/*     */       } catch (OverlappingFileLockException localOverlappingFileLockException) {
/* 222 */         localFileLock = null; }
/* 223 */       if (localFileLock == null) return null;
/* 224 */       return new Platform.Jdk14FileLock(paramSeekable, localFileLock); } }
/*     */   
/*     */   abstract InetAddress _inetAddressFromBytes(byte[] paramArrayOfByte) throws UnknownHostException;
/*     */   
/*     */   abstract String _timeZoneGetDisplayName(TimeZone paramTimeZone, boolean paramBoolean1, boolean paramBoolean2, Locale paramLocale);
/*     */   
/*     */   abstract void _setFileLength(RandomAccessFile paramRandomAccessFile, int paramInt) throws IOException;
/*     */   
/* 232 */   private static final class Jdk14FileLock extends Seekable.Lock { Jdk14FileLock(Seekable paramSeekable, FileLock paramFileLock) { this.s = paramSeekable;this.l = paramFileLock; }
/* 233 */     public Seekable seekable() { return this.s; }
/* 234 */     public boolean isShared() { return this.l.isShared(); }
/* 235 */     public boolean isValid() { return this.l.isValid(); }
/* 236 */     public void release() throws IOException { this.l.release(); }
/* 237 */     public long position() { return this.l.position(); }
/* 238 */     public long size() { return this.l.size(); }
/* 239 */     public String toString() { return this.l.toString(); }
/*     */     
/*     */     private final Seekable s;
/*     */     private final FileLock l;
/*     */   }
/*     */   
/*     */   abstract File[] _listRoots();
/*     */   
/*     */   abstract File _getRoot(File paramFile);
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\org\ibex\nestedvm\util\Platform.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */