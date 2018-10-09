/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Charsets;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.buffer.ByteBufInputStream;
/*     */ import io.netty.buffer.ByteBufOutputStream;
/*     */ import io.netty.buffer.ByteBufProcessor;
/*     */ import io.netty.handler.codec.DecoderException;
/*     */ import io.netty.handler.codec.EncoderException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketDataSerializer
/*     */   extends ByteBuf
/*     */ {
/*     */   private final ByteBuf a;
/*     */   
/*     */   public PacketDataSerializer(ByteBuf bytebuf)
/*     */   {
/*  31 */     this.a = bytebuf;
/*     */   }
/*     */   
/*     */   public static int a(int i) {
/*  35 */     for (int j = 1; j < 5; j++) {
/*  36 */       if ((i & -1 << j * 7) == 0) {
/*  37 */         return j;
/*     */       }
/*     */     }
/*     */     
/*  41 */     return 5;
/*     */   }
/*     */   
/*     */   public void a(byte[] abyte) {
/*  45 */     b(abyte.length);
/*  46 */     writeBytes(abyte);
/*     */   }
/*     */   
/*     */   public byte[] a() {
/*  50 */     byte[] abyte = new byte[e()];
/*     */     
/*  52 */     readBytes(abyte);
/*  53 */     return abyte;
/*     */   }
/*     */   
/*     */   public BlockPosition c() {
/*  57 */     return BlockPosition.fromLong(readLong());
/*     */   }
/*     */   
/*     */   public void a(BlockPosition blockposition) {
/*  61 */     writeLong(blockposition.asLong());
/*     */   }
/*     */   
/*     */   public IChatBaseComponent d() throws IOException {
/*  65 */     return IChatBaseComponent.ChatSerializer.a(c(32767));
/*     */   }
/*     */   
/*     */   public void a(IChatBaseComponent ichatbasecomponent) throws IOException {
/*  69 */     a(IChatBaseComponent.ChatSerializer.a(ichatbasecomponent));
/*     */   }
/*     */   
/*     */   public <T extends Enum<T>> T a(Class<T> oclass) {
/*  73 */     return ((Enum[])oclass.getEnumConstants())[e()];
/*     */   }
/*     */   
/*     */   public void a(Enum<?> oenum) {
/*  77 */     b(oenum.ordinal());
/*     */   }
/*     */   
/*     */   public int e() {
/*  81 */     int i = 0;
/*  82 */     int j = 0;
/*     */     
/*     */     byte b0;
/*     */     do
/*     */     {
/*  87 */       b0 = readByte();
/*  88 */       i |= (b0 & 0x7F) << j++ * 7;
/*  89 */       if (j > 5) {
/*  90 */         throw new RuntimeException("VarInt too big");
/*     */       }
/*  92 */     } while ((b0 & 0x80) == 128);
/*     */     
/*  94 */     return i;
/*     */   }
/*     */   
/*     */   public long f() {
/*  98 */     long i = 0L;
/*  99 */     int j = 0;
/*     */     
/*     */     byte b0;
/*     */     do
/*     */     {
/* 104 */       b0 = readByte();
/* 105 */       i |= (b0 & 0x7F) << j++ * 7;
/* 106 */       if (j > 10) {
/* 107 */         throw new RuntimeException("VarLong too big");
/*     */       }
/* 109 */     } while ((b0 & 0x80) == 128);
/*     */     
/* 111 */     return i;
/*     */   }
/*     */   
/*     */   public void a(UUID uuid) {
/* 115 */     writeLong(uuid.getMostSignificantBits());
/* 116 */     writeLong(uuid.getLeastSignificantBits());
/*     */   }
/*     */   
/*     */   public UUID g() {
/* 120 */     return new UUID(readLong(), readLong());
/*     */   }
/*     */   
/*     */   public void b(int i) {
/* 124 */     while ((i & 0xFFFFFF80) != 0) {
/* 125 */       writeByte(i & 0x7F | 0x80);
/* 126 */       i >>>= 7;
/*     */     }
/*     */     
/* 129 */     writeByte(i);
/*     */   }
/*     */   
/*     */   public void b(long i) {
/* 133 */     while ((i & 0xFFFFFFFFFFFFFF80) != 0L) {
/* 134 */       writeByte((int)(i & 0x7F) | 0x80);
/* 135 */       i >>>= 7;
/*     */     }
/*     */     
/* 138 */     writeByte((int)i);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 142 */     if (nbttagcompound == null) {
/* 143 */       writeByte(0);
/*     */     } else {
/*     */       try {
/* 146 */         NBTCompressedStreamTools.a(nbttagcompound, new ByteBufOutputStream(this));
/*     */       } catch (Exception ioexception) {
/* 148 */         throw new EncoderException(ioexception);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public NBTTagCompound h() throws IOException
/*     */   {
/* 155 */     int i = readerIndex();
/* 156 */     byte b0 = readByte();
/*     */     
/* 158 */     if (b0 == 0) {
/* 159 */       return null;
/*     */     }
/* 161 */     readerIndex(i);
/* 162 */     return NBTCompressedStreamTools.a(new ByteBufInputStream(this), new NBTReadLimiter(2097152L));
/*     */   }
/*     */   
/*     */   public void a(ItemStack itemstack)
/*     */   {
/* 167 */     if ((itemstack == null) || (itemstack.getItem() == null)) {
/* 168 */       writeShort(-1);
/*     */     } else {
/* 170 */       writeShort(Item.getId(itemstack.getItem()));
/* 171 */       writeByte(itemstack.count);
/* 172 */       writeShort(itemstack.getData());
/* 173 */       NBTTagCompound nbttagcompound = null;
/*     */       
/* 175 */       if ((itemstack.getItem().usesDurability()) || (itemstack.getItem().p()))
/*     */       {
/* 177 */         itemstack = itemstack.cloneItemStack();
/* 178 */         CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
/*     */         
/* 180 */         nbttagcompound = itemstack.getTag();
/*     */       }
/*     */       
/* 183 */       a(nbttagcompound);
/*     */     }
/*     */   }
/*     */   
/*     */   public ItemStack i() throws IOException
/*     */   {
/* 189 */     ItemStack itemstack = null;
/* 190 */     short short0 = readShort();
/*     */     
/* 192 */     if (short0 >= 0) {
/* 193 */       byte b0 = readByte();
/* 194 */       short short1 = readShort();
/*     */       
/* 196 */       itemstack = new ItemStack(Item.getById(short0), b0, short1);
/* 197 */       itemstack.setTag(h());
/*     */       
/* 199 */       if (itemstack.getTag() != null) {
/* 200 */         CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 205 */     return itemstack;
/*     */   }
/*     */   
/*     */   public String c(int i) {
/* 209 */     int j = e();
/*     */     
/* 211 */     if (j > i * 4)
/* 212 */       throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + j + " > " + i * 4 + ")");
/* 213 */     if (j < 0) {
/* 214 */       throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
/*     */     }
/* 216 */     String s = new String(readBytes(j).array(), Charsets.UTF_8);
/*     */     
/* 218 */     if (s.length() > i) {
/* 219 */       throw new DecoderException("The received string length is longer than maximum allowed (" + j + " > " + i + ")");
/*     */     }
/* 221 */     return s;
/*     */   }
/*     */   
/*     */ 
/*     */   public PacketDataSerializer a(String s)
/*     */   {
/* 227 */     byte[] abyte = s.getBytes(Charsets.UTF_8);
/*     */     
/* 229 */     if (abyte.length > 32767) {
/* 230 */       throw new EncoderException("String too big (was " + s.length() + " bytes encoded, max " + 32767 + ")");
/*     */     }
/* 232 */     b(abyte.length);
/* 233 */     writeBytes(abyte);
/* 234 */     return this;
/*     */   }
/*     */   
/*     */   public int capacity()
/*     */   {
/* 239 */     return this.a.capacity();
/*     */   }
/*     */   
/*     */   public ByteBuf capacity(int i) {
/* 243 */     return this.a.capacity(i);
/*     */   }
/*     */   
/*     */   public int maxCapacity() {
/* 247 */     return this.a.maxCapacity();
/*     */   }
/*     */   
/*     */   public ByteBufAllocator alloc() {
/* 251 */     return this.a.alloc();
/*     */   }
/*     */   
/*     */   public ByteOrder order() {
/* 255 */     return this.a.order();
/*     */   }
/*     */   
/*     */   public ByteBuf order(ByteOrder byteorder) {
/* 259 */     return this.a.order(byteorder);
/*     */   }
/*     */   
/*     */   public ByteBuf unwrap() {
/* 263 */     return this.a.unwrap();
/*     */   }
/*     */   
/*     */   public boolean isDirect() {
/* 267 */     return this.a.isDirect();
/*     */   }
/*     */   
/*     */   public int readerIndex() {
/* 271 */     return this.a.readerIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf readerIndex(int i) {
/* 275 */     return this.a.readerIndex(i);
/*     */   }
/*     */   
/*     */   public int writerIndex() {
/* 279 */     return this.a.writerIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf writerIndex(int i) {
/* 283 */     return this.a.writerIndex(i);
/*     */   }
/*     */   
/*     */   public ByteBuf setIndex(int i, int j) {
/* 287 */     return this.a.setIndex(i, j);
/*     */   }
/*     */   
/*     */   public int readableBytes() {
/* 291 */     return this.a.readableBytes();
/*     */   }
/*     */   
/*     */   public int writableBytes() {
/* 295 */     return this.a.writableBytes();
/*     */   }
/*     */   
/*     */   public int maxWritableBytes() {
/* 299 */     return this.a.maxWritableBytes();
/*     */   }
/*     */   
/*     */   public boolean isReadable() {
/* 303 */     return this.a.isReadable();
/*     */   }
/*     */   
/*     */   public boolean isReadable(int i) {
/* 307 */     return this.a.isReadable(i);
/*     */   }
/*     */   
/*     */   public boolean isWritable() {
/* 311 */     return this.a.isWritable();
/*     */   }
/*     */   
/*     */   public boolean isWritable(int i) {
/* 315 */     return this.a.isWritable(i);
/*     */   }
/*     */   
/*     */   public ByteBuf clear() {
/* 319 */     return this.a.clear();
/*     */   }
/*     */   
/*     */   public ByteBuf markReaderIndex() {
/* 323 */     return this.a.markReaderIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf resetReaderIndex() {
/* 327 */     return this.a.resetReaderIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf markWriterIndex() {
/* 331 */     return this.a.markWriterIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf resetWriterIndex() {
/* 335 */     return this.a.resetWriterIndex();
/*     */   }
/*     */   
/*     */   public ByteBuf discardReadBytes() {
/* 339 */     return this.a.discardReadBytes();
/*     */   }
/*     */   
/*     */   public ByteBuf discardSomeReadBytes() {
/* 343 */     return this.a.discardSomeReadBytes();
/*     */   }
/*     */   
/*     */   public ByteBuf ensureWritable(int i) {
/* 347 */     return this.a.ensureWritable(i);
/*     */   }
/*     */   
/*     */   public int ensureWritable(int i, boolean flag) {
/* 351 */     return this.a.ensureWritable(i, flag);
/*     */   }
/*     */   
/*     */   public boolean getBoolean(int i) {
/* 355 */     return this.a.getBoolean(i);
/*     */   }
/*     */   
/*     */   public byte getByte(int i) {
/* 359 */     return this.a.getByte(i);
/*     */   }
/*     */   
/*     */   public short getUnsignedByte(int i) {
/* 363 */     return this.a.getUnsignedByte(i);
/*     */   }
/*     */   
/*     */   public short getShort(int i) {
/* 367 */     return this.a.getShort(i);
/*     */   }
/*     */   
/*     */   public int getUnsignedShort(int i) {
/* 371 */     return this.a.getUnsignedShort(i);
/*     */   }
/*     */   
/*     */   public int getMedium(int i) {
/* 375 */     return this.a.getMedium(i);
/*     */   }
/*     */   
/*     */   public int getUnsignedMedium(int i) {
/* 379 */     return this.a.getUnsignedMedium(i);
/*     */   }
/*     */   
/*     */   public int getInt(int i) {
/* 383 */     return this.a.getInt(i);
/*     */   }
/*     */   
/*     */   public long getUnsignedInt(int i) {
/* 387 */     return this.a.getUnsignedInt(i);
/*     */   }
/*     */   
/*     */   public long getLong(int i) {
/* 391 */     return this.a.getLong(i);
/*     */   }
/*     */   
/*     */   public char getChar(int i) {
/* 395 */     return this.a.getChar(i);
/*     */   }
/*     */   
/*     */   public float getFloat(int i) {
/* 399 */     return this.a.getFloat(i);
/*     */   }
/*     */   
/*     */   public double getDouble(int i) {
/* 403 */     return this.a.getDouble(i);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int i, ByteBuf bytebuf) {
/* 407 */     return this.a.getBytes(i, bytebuf);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int i, ByteBuf bytebuf, int j) {
/* 411 */     return this.a.getBytes(i, bytebuf, j);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int i, ByteBuf bytebuf, int j, int k) {
/* 415 */     return this.a.getBytes(i, bytebuf, j, k);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int i, byte[] abyte) {
/* 419 */     return this.a.getBytes(i, abyte);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int i, byte[] abyte, int j, int k) {
/* 423 */     return this.a.getBytes(i, abyte, j, k);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int i, ByteBuffer bytebuffer) {
/* 427 */     return this.a.getBytes(i, bytebuffer);
/*     */   }
/*     */   
/*     */   public ByteBuf getBytes(int i, OutputStream outputstream, int j) throws IOException {
/* 431 */     return this.a.getBytes(i, outputstream, j);
/*     */   }
/*     */   
/*     */   public int getBytes(int i, GatheringByteChannel gatheringbytechannel, int j) throws IOException {
/* 435 */     return this.a.getBytes(i, gatheringbytechannel, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setBoolean(int i, boolean flag) {
/* 439 */     return this.a.setBoolean(i, flag);
/*     */   }
/*     */   
/*     */   public ByteBuf setByte(int i, int j) {
/* 443 */     return this.a.setByte(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setShort(int i, int j) {
/* 447 */     return this.a.setShort(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setMedium(int i, int j) {
/* 451 */     return this.a.setMedium(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setInt(int i, int j) {
/* 455 */     return this.a.setInt(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setLong(int i, long j) {
/* 459 */     return this.a.setLong(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setChar(int i, int j) {
/* 463 */     return this.a.setChar(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setFloat(int i, float f) {
/* 467 */     return this.a.setFloat(i, f);
/*     */   }
/*     */   
/*     */   public ByteBuf setDouble(int i, double d0) {
/* 471 */     return this.a.setDouble(i, d0);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int i, ByteBuf bytebuf) {
/* 475 */     return this.a.setBytes(i, bytebuf);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int i, ByteBuf bytebuf, int j) {
/* 479 */     return this.a.setBytes(i, bytebuf, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int i, ByteBuf bytebuf, int j, int k) {
/* 483 */     return this.a.setBytes(i, bytebuf, j, k);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int i, byte[] abyte) {
/* 487 */     return this.a.setBytes(i, abyte);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int i, byte[] abyte, int j, int k) {
/* 491 */     return this.a.setBytes(i, abyte, j, k);
/*     */   }
/*     */   
/*     */   public ByteBuf setBytes(int i, ByteBuffer bytebuffer) {
/* 495 */     return this.a.setBytes(i, bytebuffer);
/*     */   }
/*     */   
/*     */   public int setBytes(int i, InputStream inputstream, int j) throws IOException {
/* 499 */     return this.a.setBytes(i, inputstream, j);
/*     */   }
/*     */   
/*     */   public int setBytes(int i, ScatteringByteChannel scatteringbytechannel, int j) throws IOException {
/* 503 */     return this.a.setBytes(i, scatteringbytechannel, j);
/*     */   }
/*     */   
/*     */   public ByteBuf setZero(int i, int j) {
/* 507 */     return this.a.setZero(i, j);
/*     */   }
/*     */   
/*     */   public boolean readBoolean() {
/* 511 */     return this.a.readBoolean();
/*     */   }
/*     */   
/*     */   public byte readByte() {
/* 515 */     return this.a.readByte();
/*     */   }
/*     */   
/*     */   public short readUnsignedByte() {
/* 519 */     return this.a.readUnsignedByte();
/*     */   }
/*     */   
/*     */   public short readShort() {
/* 523 */     return this.a.readShort();
/*     */   }
/*     */   
/*     */   public int readUnsignedShort() {
/* 527 */     return this.a.readUnsignedShort();
/*     */   }
/*     */   
/*     */   public int readMedium() {
/* 531 */     return this.a.readMedium();
/*     */   }
/*     */   
/*     */   public int readUnsignedMedium() {
/* 535 */     return this.a.readUnsignedMedium();
/*     */   }
/*     */   
/*     */   public int readInt() {
/* 539 */     return this.a.readInt();
/*     */   }
/*     */   
/*     */   public long readUnsignedInt() {
/* 543 */     return this.a.readUnsignedInt();
/*     */   }
/*     */   
/*     */   public long readLong() {
/* 547 */     return this.a.readLong();
/*     */   }
/*     */   
/*     */   public char readChar() {
/* 551 */     return this.a.readChar();
/*     */   }
/*     */   
/*     */   public float readFloat() {
/* 555 */     return this.a.readFloat();
/*     */   }
/*     */   
/*     */   public double readDouble() {
/* 559 */     return this.a.readDouble();
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(int i) {
/* 563 */     return this.a.readBytes(i);
/*     */   }
/*     */   
/*     */   public ByteBuf readSlice(int i) {
/* 567 */     return this.a.readSlice(i);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf bytebuf) {
/* 571 */     return this.a.readBytes(bytebuf);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf bytebuf, int i) {
/* 575 */     return this.a.readBytes(bytebuf, i);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf bytebuf, int i, int j) {
/* 579 */     return this.a.readBytes(bytebuf, i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] abyte) {
/* 583 */     return this.a.readBytes(abyte);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(byte[] abyte, int i, int j) {
/* 587 */     return this.a.readBytes(abyte, i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer bytebuffer) {
/* 591 */     return this.a.readBytes(bytebuffer);
/*     */   }
/*     */   
/*     */   public ByteBuf readBytes(OutputStream outputstream, int i) throws IOException {
/* 595 */     return this.a.readBytes(outputstream, i);
/*     */   }
/*     */   
/*     */   public int readBytes(GatheringByteChannel gatheringbytechannel, int i) throws IOException {
/* 599 */     return this.a.readBytes(gatheringbytechannel, i);
/*     */   }
/*     */   
/*     */   public ByteBuf skipBytes(int i) {
/* 603 */     return this.a.skipBytes(i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBoolean(boolean flag) {
/* 607 */     return this.a.writeBoolean(flag);
/*     */   }
/*     */   
/*     */   public ByteBuf writeByte(int i) {
/* 611 */     return this.a.writeByte(i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeShort(int i) {
/* 615 */     return this.a.writeShort(i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeMedium(int i) {
/* 619 */     return this.a.writeMedium(i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeInt(int i) {
/* 623 */     return this.a.writeInt(i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeLong(long i) {
/* 627 */     return this.a.writeLong(i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeChar(int i) {
/* 631 */     return this.a.writeChar(i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeFloat(float f) {
/* 635 */     return this.a.writeFloat(f);
/*     */   }
/*     */   
/*     */   public ByteBuf writeDouble(double d0) {
/* 639 */     return this.a.writeDouble(d0);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf bytebuf) {
/* 643 */     return this.a.writeBytes(bytebuf);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf bytebuf, int i) {
/* 647 */     return this.a.writeBytes(bytebuf, i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf bytebuf, int i, int j) {
/* 651 */     return this.a.writeBytes(bytebuf, i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(byte[] abyte) {
/* 655 */     return this.a.writeBytes(abyte);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(byte[] abyte, int i, int j) {
/* 659 */     return this.a.writeBytes(abyte, i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuffer bytebuffer) {
/* 663 */     return this.a.writeBytes(bytebuffer);
/*     */   }
/*     */   
/*     */   public int writeBytes(InputStream inputstream, int i) throws IOException {
/* 667 */     return this.a.writeBytes(inputstream, i);
/*     */   }
/*     */   
/*     */   public int writeBytes(ScatteringByteChannel scatteringbytechannel, int i) throws IOException {
/* 671 */     return this.a.writeBytes(scatteringbytechannel, i);
/*     */   }
/*     */   
/*     */   public ByteBuf writeZero(int i) {
/* 675 */     return this.a.writeZero(i);
/*     */   }
/*     */   
/*     */   public int indexOf(int i, int j, byte b0) {
/* 679 */     return this.a.indexOf(i, j, b0);
/*     */   }
/*     */   
/*     */   public int bytesBefore(byte b0) {
/* 683 */     return this.a.bytesBefore(b0);
/*     */   }
/*     */   
/*     */   public int bytesBefore(int i, byte b0) {
/* 687 */     return this.a.bytesBefore(i, b0);
/*     */   }
/*     */   
/*     */   public int bytesBefore(int i, int j, byte b0) {
/* 691 */     return this.a.bytesBefore(i, j, b0);
/*     */   }
/*     */   
/*     */   public int forEachByte(ByteBufProcessor bytebufprocessor) {
/* 695 */     return this.a.forEachByte(bytebufprocessor);
/*     */   }
/*     */   
/*     */   public int forEachByte(int i, int j, ByteBufProcessor bytebufprocessor) {
/* 699 */     return this.a.forEachByte(i, j, bytebufprocessor);
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(ByteBufProcessor bytebufprocessor) {
/* 703 */     return this.a.forEachByteDesc(bytebufprocessor);
/*     */   }
/*     */   
/*     */   public int forEachByteDesc(int i, int j, ByteBufProcessor bytebufprocessor) {
/* 707 */     return this.a.forEachByteDesc(i, j, bytebufprocessor);
/*     */   }
/*     */   
/*     */   public ByteBuf copy() {
/* 711 */     return this.a.copy();
/*     */   }
/*     */   
/*     */   public ByteBuf copy(int i, int j) {
/* 715 */     return this.a.copy(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf slice() {
/* 719 */     return this.a.slice();
/*     */   }
/*     */   
/*     */   public ByteBuf slice(int i, int j) {
/* 723 */     return this.a.slice(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuf duplicate() {
/* 727 */     return this.a.duplicate();
/*     */   }
/*     */   
/*     */   public int nioBufferCount() {
/* 731 */     return this.a.nioBufferCount();
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer() {
/* 735 */     return this.a.nioBuffer();
/*     */   }
/*     */   
/*     */   public ByteBuffer nioBuffer(int i, int j) {
/* 739 */     return this.a.nioBuffer(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int i, int j) {
/* 743 */     return this.a.internalNioBuffer(i, j);
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers() {
/* 747 */     return this.a.nioBuffers();
/*     */   }
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int i, int j) {
/* 751 */     return this.a.nioBuffers(i, j);
/*     */   }
/*     */   
/*     */   public boolean hasArray() {
/* 755 */     return this.a.hasArray();
/*     */   }
/*     */   
/*     */   public byte[] array() {
/* 759 */     return this.a.array();
/*     */   }
/*     */   
/*     */   public int arrayOffset() {
/* 763 */     return this.a.arrayOffset();
/*     */   }
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 767 */     return this.a.hasMemoryAddress();
/*     */   }
/*     */   
/*     */   public long memoryAddress() {
/* 771 */     return this.a.memoryAddress();
/*     */   }
/*     */   
/*     */   public String toString(Charset charset) {
/* 775 */     return this.a.toString(charset);
/*     */   }
/*     */   
/*     */   public String toString(int i, int j, Charset charset) {
/* 779 */     return this.a.toString(i, j, charset);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 783 */     return this.a.hashCode();
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 787 */     return this.a.equals(object);
/*     */   }
/*     */   
/*     */   public int compareTo(ByteBuf bytebuf) {
/* 791 */     return this.a.compareTo(bytebuf);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 795 */     return this.a.toString();
/*     */   }
/*     */   
/*     */   public ByteBuf retain(int i) {
/* 799 */     return this.a.retain(i);
/*     */   }
/*     */   
/*     */   public ByteBuf retain() {
/* 803 */     return this.a.retain();
/*     */   }
/*     */   
/*     */   public int refCnt() {
/* 807 */     return this.a.refCnt();
/*     */   }
/*     */   
/*     */   public boolean release() {
/* 811 */     return this.a.release();
/*     */   }
/*     */   
/*     */   public boolean release(int i) {
/* 815 */     return this.a.release(i);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\PacketDataSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */