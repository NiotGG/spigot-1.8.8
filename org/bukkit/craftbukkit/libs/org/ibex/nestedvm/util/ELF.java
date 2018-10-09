/*     */ package org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
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
/*     */ public class ELF
/*     */ {
/*     */   private static final int ELF_MAGIC = 2135247942;
/*     */   public static final int ELFCLASSNONE = 0;
/*     */   public static final int ELFCLASS32 = 1;
/*     */   public static final int ELFCLASS64 = 2;
/*     */   public static final int ELFDATANONE = 0;
/*     */   public static final int ELFDATA2LSB = 1;
/*     */   public static final int ELFDATA2MSB = 2;
/*     */   public static final int SHT_SYMTAB = 2;
/*     */   public static final int SHT_STRTAB = 3;
/*     */   public static final int SHT_NOBITS = 8;
/*     */   public static final int SHF_WRITE = 1;
/*     */   public static final int SHF_ALLOC = 2;
/*     */   public static final int SHF_EXECINSTR = 4;
/*     */   public static final int PF_X = 1;
/*     */   public static final int PF_W = 2;
/*     */   public static final int PF_R = 4;
/*     */   public static final int PT_LOAD = 1;
/*     */   public static final short ET_EXEC = 2;
/*     */   public static final short EM_MIPS = 8;
/*     */   private Seekable data;
/*     */   public ELFIdent ident;
/*     */   public ELFHeader header;
/*     */   public PHeader[] pheaders;
/*     */   public SHeader[] sheaders;
/*     */   private byte[] stringTable;
/*     */   private boolean sectionReaderActive;
/*     */   private Symtab _symtab;
/*     */   
/*     */   private void readFully(byte[] paramArrayOfByte)
/*     */     throws IOException
/*     */   {
/*  51 */     int i = paramArrayOfByte.length;
/*  52 */     int j = 0;
/*  53 */     while (i > 0) {
/*  54 */       int k = this.data.read(paramArrayOfByte, j, i);
/*  55 */       if (k == -1) throw new IOException("EOF");
/*  56 */       j += k;
/*  57 */       i -= k;
/*     */     }
/*     */   }
/*     */   
/*     */   private int readIntBE() throws IOException {
/*  62 */     byte[] arrayOfByte = new byte[4];
/*  63 */     readFully(arrayOfByte);
/*  64 */     return (arrayOfByte[0] & 0xFF) << 24 | (arrayOfByte[1] & 0xFF) << 16 | (arrayOfByte[2] & 0xFF) << 8 | (arrayOfByte[3] & 0xFF) << 0;
/*     */   }
/*     */   
/*  67 */   private int readInt() throws IOException { int i = readIntBE();
/*  68 */     if ((this.ident != null) && (this.ident.data == 1))
/*  69 */       i = i << 24 & 0xFF000000 | i << 8 & 0xFF0000 | i >>> 8 & 0xFF00 | i >> 24 & 0xFF;
/*  70 */     return i;
/*     */   }
/*     */   
/*     */   private short readShortBE() throws IOException {
/*  74 */     byte[] arrayOfByte = new byte[2];
/*  75 */     readFully(arrayOfByte);
/*  76 */     return (short)((arrayOfByte[0] & 0xFF) << 8 | (arrayOfByte[1] & 0xFF) << 0);
/*     */   }
/*     */   
/*  79 */   private short readShort() throws IOException { short s = readShortBE();
/*  80 */     if ((this.ident != null) && (this.ident.data == 1))
/*  81 */       s = (short)((s << 8 & 0xFF00 | s >> 8 & 0xFF) & 0xFFFF);
/*  82 */     return s;
/*     */   }
/*     */   
/*     */   private byte readByte() throws IOException {
/*  86 */     byte[] arrayOfByte = new byte[1];
/*  87 */     readFully(arrayOfByte);
/*  88 */     return arrayOfByte[0];
/*     */   }
/*     */   
/*     */   public class ELFIdent {
/*     */     public byte klass;
/*     */     public byte data;
/*     */     public byte osabi;
/*     */     public byte abiversion;
/*     */     
/*     */     ELFIdent() throws IOException {
/*  98 */       if (ELF.this.readIntBE() != 2135247942) { throw new ELF.ELFException(ELF.this, "Bad Magic");
/*     */       }
/* 100 */       this.klass = ELF.this.readByte();
/* 101 */       if (this.klass != 1) { throw new ELF.ELFException(ELF.this, "org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.ELF does not suport 64-bit binaries");
/*     */       }
/* 103 */       this.data = ELF.this.readByte();
/* 104 */       if ((this.data != 1) && (this.data != 2)) { throw new ELF.ELFException(ELF.this, "Unknown byte order");
/*     */       }
/* 106 */       ELF.this.readByte();
/* 107 */       this.osabi = ELF.this.readByte();
/* 108 */       this.abiversion = ELF.this.readByte();
/* 109 */       for (int i = 0; i < 7; i++) ELF.this.readByte();
/*     */     }
/*     */   }
/*     */   
/*     */   public class ELFHeader {
/*     */     public short type;
/*     */     public short machine;
/*     */     public int version;
/*     */     public int entry;
/*     */     public int phoff;
/*     */     public int shoff;
/*     */     public int flags;
/*     */     public short ehsize;
/*     */     public short phentsize;
/*     */     public short phnum;
/*     */     public short shentsize;
/*     */     public short shnum;
/*     */     public short shstrndx;
/*     */     
/*     */     ELFHeader() throws IOException {
/* 129 */       this.type = ELF.this.readShort();
/* 130 */       this.machine = ELF.this.readShort();
/* 131 */       this.version = ELF.this.readInt();
/* 132 */       if (this.version != 1) throw new ELF.ELFException(ELF.this, "version != 1");
/* 133 */       this.entry = ELF.this.readInt();
/* 134 */       this.phoff = ELF.this.readInt();
/* 135 */       this.shoff = ELF.this.readInt();
/* 136 */       this.flags = ELF.this.readInt();
/* 137 */       this.ehsize = ELF.this.readShort();
/* 138 */       this.phentsize = ELF.this.readShort();
/* 139 */       this.phnum = ELF.this.readShort();
/* 140 */       this.shentsize = ELF.this.readShort();
/* 141 */       this.shnum = ELF.this.readShort();
/* 142 */       this.shstrndx = ELF.this.readShort();
/*     */     }
/*     */   }
/*     */   
/*     */   public class PHeader {
/*     */     public int type;
/*     */     public int offset;
/*     */     public int vaddr;
/*     */     public int paddr;
/*     */     public int filesz;
/*     */     public int memsz;
/*     */     public int flags;
/*     */     public int align;
/*     */     
/*     */     PHeader() throws IOException {
/* 157 */       this.type = ELF.this.readInt();
/* 158 */       this.offset = ELF.this.readInt();
/* 159 */       this.vaddr = ELF.this.readInt();
/* 160 */       this.paddr = ELF.this.readInt();
/* 161 */       this.filesz = ELF.this.readInt();
/* 162 */       this.memsz = ELF.this.readInt();
/* 163 */       this.flags = ELF.this.readInt();
/* 164 */       this.align = ELF.this.readInt();
/* 165 */       if (this.filesz > this.memsz) throw new ELF.ELFException(ELF.this, "ELF inconsistency: filesz > memsz (" + ELF.toHex(this.filesz) + " > " + ELF.toHex(this.memsz) + ")");
/*     */     }
/*     */     
/* 168 */     public boolean writable() { return (this.flags & 0x2) != 0; }
/*     */     
/*     */     public InputStream getInputStream() throws IOException {
/* 171 */       return new BufferedInputStream(new ELF.SectionInputStream(ELF.this, this.offset, this.offset + this.filesz));
/*     */     }
/*     */   }
/*     */   
/*     */   public class SHeader
/*     */   {
/*     */     int nameidx;
/*     */     public String name;
/*     */     public int type;
/*     */     public int flags;
/*     */     public int addr;
/*     */     public int offset;
/*     */     public int size;
/*     */     public int link;
/*     */     public int info;
/*     */     public int addralign;
/*     */     public int entsize;
/*     */     
/*     */     SHeader() throws IOException {
/* 190 */       this.nameidx = ELF.this.readInt();
/* 191 */       this.type = ELF.this.readInt();
/* 192 */       this.flags = ELF.this.readInt();
/* 193 */       this.addr = ELF.this.readInt();
/* 194 */       this.offset = ELF.this.readInt();
/* 195 */       this.size = ELF.this.readInt();
/* 196 */       this.link = ELF.this.readInt();
/* 197 */       this.info = ELF.this.readInt();
/* 198 */       this.addralign = ELF.this.readInt();
/* 199 */       this.entsize = ELF.this.readInt();
/*     */     }
/*     */     
/*     */     public InputStream getInputStream() throws IOException {
/* 203 */       return new BufferedInputStream(new ELF.SectionInputStream(ELF.this, this.offset, this.type == 8 ? 0 : this.offset + this.size));
/*     */     }
/*     */     
/*     */ 
/* 207 */     public boolean isText() { return this.name.equals(".text"); }
/* 208 */     public boolean isData() { return (this.name.equals(".data")) || (this.name.equals(".sdata")) || (this.name.equals(".rodata")) || (this.name.equals(".ctors")) || (this.name.equals(".dtors")); }
/* 209 */     public boolean isBSS() { return (this.name.equals(".bss")) || (this.name.equals(".sbss")); }
/*     */   }
/*     */   
/* 212 */   public ELF(String paramString) throws IOException, ELF.ELFException { this(new Seekable.File(paramString, false)); }
/*     */   
/* 214 */   public ELF(Seekable paramSeekable) throws IOException, ELF.ELFException { this.data = paramSeekable;
/* 215 */     this.ident = new ELFIdent();
/* 216 */     this.header = new ELFHeader();
/* 217 */     this.pheaders = new PHeader[this.header.phnum];
/* 218 */     for (int i = 0; i < this.header.phnum; i++) {
/* 219 */       paramSeekable.seek(this.header.phoff + i * this.header.phentsize);
/* 220 */       this.pheaders[i] = new PHeader();
/*     */     }
/* 222 */     this.sheaders = new SHeader[this.header.shnum];
/* 223 */     for (i = 0; i < this.header.shnum; i++) {
/* 224 */       paramSeekable.seek(this.header.shoff + i * this.header.shentsize);
/* 225 */       this.sheaders[i] = new SHeader();
/*     */     }
/* 227 */     if ((this.header.shstrndx < 0) || (this.header.shstrndx >= this.header.shnum)) throw new ELFException("Bad shstrndx");
/* 228 */     paramSeekable.seek(this.sheaders[this.header.shstrndx].offset);
/* 229 */     this.stringTable = new byte[this.sheaders[this.header.shstrndx].size];
/* 230 */     readFully(this.stringTable);
/*     */     
/* 232 */     for (i = 0; i < this.header.shnum; i++) {
/* 233 */       SHeader localSHeader = this.sheaders[i];
/* 234 */       localSHeader.name = getString(localSHeader.nameidx);
/*     */     }
/*     */   }
/*     */   
/* 238 */   private String getString(int paramInt) { return getString(paramInt, this.stringTable); }
/*     */   
/* 240 */   private String getString(int paramInt, byte[] paramArrayOfByte) { StringBuffer localStringBuffer = new StringBuffer();
/* 241 */     if ((paramInt < 0) || (paramInt >= paramArrayOfByte.length)) return "<invalid strtab entry>";
/* 242 */     while ((paramInt >= 0) && (paramInt < paramArrayOfByte.length) && (paramArrayOfByte[paramInt] != 0)) localStringBuffer.append((char)paramArrayOfByte[(paramInt++)]);
/* 243 */     return localStringBuffer.toString();
/*     */   }
/*     */   
/*     */   public SHeader sectionWithName(String paramString) {
/* 247 */     for (int i = 0; i < this.sheaders.length; i++)
/* 248 */       if (this.sheaders[i].name.equals(paramString))
/* 249 */         return this.sheaders[i];
/* 250 */     return null;
/*     */   }
/*     */   
/* 253 */   public class ELFException extends IOException { ELFException(String paramString) { super(); }
/*     */   }
/*     */   
/*     */   private class SectionInputStream extends InputStream { private int pos;
/*     */     private int maxpos;
/*     */     
/* 259 */     SectionInputStream(int paramInt1, int paramInt2) throws IOException { if (ELF.this.sectionReaderActive)
/* 260 */         throw new IOException("Section reader already active");
/* 261 */       ELF.this.sectionReaderActive = true;
/* 262 */       this.pos = paramInt1;
/* 263 */       ELF.this.data.seek(this.pos);
/* 264 */       this.maxpos = paramInt2;
/*     */     }
/*     */     
/* 267 */     private int bytesLeft() { return this.maxpos - this.pos; }
/*     */     
/* 269 */     public int read() throws IOException { byte[] arrayOfByte = new byte[1];
/* 270 */       return read(arrayOfByte, 0, 1) == -1 ? -1 : arrayOfByte[0] & 0xFF;
/*     */     }
/*     */     
/* 273 */     public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException { int i = ELF.this.data.read(paramArrayOfByte, paramInt1, Math.min(paramInt2, bytesLeft())); if (i > 0) this.pos += i; return i; }
/*     */     
/* 275 */     public void close() { ELF.this.sectionReaderActive = false; }
/*     */   }
/*     */   
/*     */   public Symtab getSymtab() throws IOException
/*     */   {
/* 280 */     if (this._symtab != null) { return this._symtab;
/*     */     }
/* 282 */     if (this.sectionReaderActive) { throw new ELFException("Can't read the symtab while a section reader is active");
/*     */     }
/* 284 */     SHeader localSHeader1 = sectionWithName(".symtab");
/* 285 */     if ((localSHeader1 == null) || (localSHeader1.type != 2)) { return null;
/*     */     }
/* 287 */     SHeader localSHeader2 = sectionWithName(".strtab");
/* 288 */     if ((localSHeader2 == null) || (localSHeader2.type != 3)) { return null;
/*     */     }
/* 290 */     byte[] arrayOfByte = new byte[localSHeader2.size];
/* 291 */     DataInputStream localDataInputStream = new DataInputStream(localSHeader2.getInputStream());
/* 292 */     localDataInputStream.readFully(arrayOfByte);
/* 293 */     localDataInputStream.close();
/*     */     
/* 295 */     return this._symtab = new Symtab(localSHeader1.offset, localSHeader1.size, arrayOfByte);
/*     */   }
/*     */   
/*     */   public class Symtab {
/*     */     public ELF.Symbol[] symbols;
/*     */     
/*     */     Symtab(int paramInt1, int paramInt2, byte[] paramArrayOfByte) throws IOException {
/* 302 */       ELF.this.data.seek(paramInt1);
/* 303 */       int i = paramInt2 / 16;
/* 304 */       this.symbols = new ELF.Symbol[i];
/* 305 */       for (int j = 0; j < i; j++) this.symbols[j] = new ELF.Symbol(ELF.this, paramArrayOfByte);
/*     */     }
/*     */     
/*     */     public ELF.Symbol getSymbol(String paramString) {
/* 309 */       ELF.Symbol localSymbol = null;
/* 310 */       for (int i = 0; i < this.symbols.length; i++) {
/* 311 */         if (this.symbols[i].name.equals(paramString)) {
/* 312 */           if (localSymbol == null) {
/* 313 */             localSymbol = this.symbols[i];
/*     */           } else
/* 315 */             System.err.println("WARNING: Multiple symbol matches for " + paramString);
/*     */         }
/*     */       }
/* 318 */       return localSymbol;
/*     */     }
/*     */     
/*     */     public ELF.Symbol getGlobalSymbol(String paramString) {
/* 322 */       for (int i = 0; i < this.symbols.length; i++)
/* 323 */         if ((this.symbols[i].binding == 1) && (this.symbols[i].name.equals(paramString)))
/* 324 */           return this.symbols[i];
/* 325 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   public class Symbol
/*     */   {
/*     */     public String name;
/*     */     public int addr;
/*     */     public int size;
/*     */     public byte info;
/*     */     public byte type;
/*     */     public byte binding;
/*     */     public byte other;
/*     */     public short shndx;
/*     */     public ELF.SHeader sheader;
/*     */     public static final int STT_FUNC = 2;
/*     */     public static final int STB_GLOBAL = 1;
/*     */     
/*     */     Symbol(byte[] paramArrayOfByte) throws IOException {
/* 344 */       this.name = ELF.this.getString(ELF.access$300(ELF.this), paramArrayOfByte);
/* 345 */       this.addr = ELF.this.readInt();
/* 346 */       this.size = ELF.this.readInt();
/* 347 */       this.info = ELF.this.readByte();
/* 348 */       this.type = ((byte)(this.info & 0xF));
/* 349 */       this.binding = ((byte)(this.info >> 4));
/* 350 */       this.other = ELF.this.readByte();
/* 351 */       this.shndx = ELF.this.readShort();
/*     */     }
/*     */   }
/*     */   
/* 355 */   private static String toHex(int paramInt) { return "0x" + Long.toString(paramInt & 0xFFFFFFFF, 16); }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\org\ibex\nestedvm\util\ELF.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */