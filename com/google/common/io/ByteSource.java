/*     */ package com.google.common.io;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import com.google.common.base.Ascii;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.hash.Funnels;
/*     */ import com.google.common.hash.HashCode;
/*     */ import com.google.common.hash.HashFunction;
/*     */ import com.google.common.hash.Hasher;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Iterator;
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
/*     */ public abstract class ByteSource
/*     */   implements InputSupplier<InputStream>
/*     */ {
/*     */   private static final int BUF_SIZE = 4096;
/*     */   
/*     */   public CharSource asCharSource(Charset charset)
/*     */   {
/*  73 */     return new AsCharSource(charset, null);
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
/*     */   public abstract InputStream openStream()
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public final InputStream getInput()
/*     */     throws IOException
/*     */   {
/*  98 */     return openStream();
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
/*     */   public InputStream openBufferedStream()
/*     */     throws IOException
/*     */   {
/* 114 */     InputStream in = openStream();
/* 115 */     return (in instanceof BufferedInputStream) ? (BufferedInputStream)in : new BufferedInputStream(in);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ByteSource slice(long offset, long length)
/*     */   {
/* 127 */     return new SlicedByteSource(offset, length, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */     throws IOException
/*     */   {
/* 138 */     Closer closer = Closer.create();
/*     */     try {
/* 140 */       InputStream in = (InputStream)closer.register(openStream());
/* 141 */       return in.read() == -1;
/*     */     } catch (Throwable e) {
/* 143 */       throw closer.rethrow(e);
/*     */     } finally {
/* 145 */       closer.close();
/*     */     }
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
/*     */   public long size()
/*     */     throws IOException
/*     */   {
/* 165 */     Closer closer = Closer.create();
/*     */     long l;
/* 167 */     try { InputStream in = (InputStream)closer.register(openStream());
/* 168 */       return countBySkipping(in);
/*     */     }
/*     */     catch (IOException e) {}finally
/*     */     {
/* 172 */       closer.close();
/*     */     }
/*     */     
/* 175 */     closer = Closer.create();
/*     */     try {
/* 177 */       InputStream in = (InputStream)closer.register(openStream());
/* 178 */       return countByReading(in);
/*     */     } catch (Throwable e) {
/* 180 */       throw closer.rethrow(e);
/*     */     } finally {
/* 182 */       closer.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private long countBySkipping(InputStream in)
/*     */     throws IOException
/*     */   {
/* 191 */     long count = 0L;
/*     */     
/*     */     for (;;)
/*     */     {
/* 195 */       long skipped = in.skip(Math.min(in.available(), Integer.MAX_VALUE));
/* 196 */       if (skipped <= 0L) {
/* 197 */         if (in.read() == -1)
/* 198 */           return count;
/* 199 */         if ((count == 0L) && (in.available() == 0))
/*     */         {
/*     */ 
/* 202 */           throw new IOException();
/*     */         }
/* 204 */         count += 1L;
/*     */       } else {
/* 206 */         count += skipped;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/* 211 */   private static final byte[] countBuffer = new byte['က'];
/*     */   
/*     */   private long countByReading(InputStream in) throws IOException {
/* 214 */     long count = 0L;
/*     */     long read;
/* 216 */     while ((read = in.read(countBuffer)) != -1L) {
/* 217 */       count += read;
/*     */     }
/* 219 */     return count;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long copyTo(OutputStream output)
/*     */     throws IOException
/*     */   {
/* 230 */     Preconditions.checkNotNull(output);
/*     */     
/* 232 */     Closer closer = Closer.create();
/*     */     try {
/* 234 */       InputStream in = (InputStream)closer.register(openStream());
/* 235 */       return ByteStreams.copy(in, output);
/*     */     } catch (Throwable e) {
/* 237 */       throw closer.rethrow(e);
/*     */     } finally {
/* 239 */       closer.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long copyTo(ByteSink sink)
/*     */     throws IOException
/*     */   {
/* 250 */     Preconditions.checkNotNull(sink);
/*     */     
/* 252 */     Closer closer = Closer.create();
/*     */     try {
/* 254 */       InputStream in = (InputStream)closer.register(openStream());
/* 255 */       OutputStream out = (OutputStream)closer.register(sink.openStream());
/* 256 */       return ByteStreams.copy(in, out);
/*     */     } catch (Throwable e) {
/* 258 */       throw closer.rethrow(e);
/*     */     } finally {
/* 260 */       closer.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] read()
/*     */     throws IOException
/*     */   {
/* 270 */     Closer closer = Closer.create();
/*     */     try {
/* 272 */       InputStream in = (InputStream)closer.register(openStream());
/* 273 */       return ByteStreams.toByteArray(in);
/*     */     } catch (Throwable e) {
/* 275 */       throw closer.rethrow(e);
/*     */     } finally {
/* 277 */       closer.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Beta
/*     */   public <T> T read(ByteProcessor<T> processor)
/*     */     throws IOException
/*     */   {
/* 292 */     Preconditions.checkNotNull(processor);
/*     */     
/* 294 */     Closer closer = Closer.create();
/*     */     try {
/* 296 */       InputStream in = (InputStream)closer.register(openStream());
/* 297 */       return (T)ByteStreams.readBytes(in, processor);
/*     */     } catch (Throwable e) {
/* 299 */       throw closer.rethrow(e);
/*     */     } finally {
/* 301 */       closer.close();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public HashCode hash(HashFunction hashFunction)
/*     */     throws IOException
/*     */   {
/* 311 */     Hasher hasher = hashFunction.newHasher();
/* 312 */     copyTo(Funnels.asOutputStream(hasher));
/* 313 */     return hasher.hash();
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean contentEquals(ByteSource other)
/*     */     throws IOException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: invokestatic 140	com/google/common/base/Preconditions:checkNotNull	(Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   4: pop
/*     */     //   5: sipush 4096
/*     */     //   8: newarray <illegal type>
/*     */     //   10: astore_2
/*     */     //   11: sipush 4096
/*     */     //   14: newarray <illegal type>
/*     */     //   16: astore_3
/*     */     //   17: invokestatic 79	com/google/common/io/Closer:create	()Lcom/google/common/io/Closer;
/*     */     //   20: astore 4
/*     */     //   22: aload 4
/*     */     //   24: aload_0
/*     */     //   25: invokevirtual 51	com/google/common/io/ByteSource:openStream	()Ljava/io/InputStream;
/*     */     //   28: invokevirtual 83	com/google/common/io/Closer:register	(Ljava/io/Closeable;)Ljava/io/Closeable;
/*     */     //   31: checkcast 56	java/io/InputStream
/*     */     //   34: astore 5
/*     */     //   36: aload 4
/*     */     //   38: aload_1
/*     */     //   39: invokevirtual 51	com/google/common/io/ByteSource:openStream	()Ljava/io/InputStream;
/*     */     //   42: invokevirtual 83	com/google/common/io/Closer:register	(Ljava/io/Closeable;)Ljava/io/Closeable;
/*     */     //   45: checkcast 56	java/io/InputStream
/*     */     //   48: astore 6
/*     */     //   50: aload 5
/*     */     //   52: aload_2
/*     */     //   53: iconst_0
/*     */     //   54: sipush 4096
/*     */     //   57: invokestatic 206	com/google/common/io/ByteStreams:read	(Ljava/io/InputStream;[BII)I
/*     */     //   60: istore 7
/*     */     //   62: aload 6
/*     */     //   64: aload_3
/*     */     //   65: iconst_0
/*     */     //   66: sipush 4096
/*     */     //   69: invokestatic 206	com/google/common/io/ByteStreams:read	(Ljava/io/InputStream;[BII)I
/*     */     //   72: istore 8
/*     */     //   74: iload 7
/*     */     //   76: iload 8
/*     */     //   78: if_icmpne +11 -> 89
/*     */     //   81: aload_2
/*     */     //   82: aload_3
/*     */     //   83: invokestatic 212	java/util/Arrays:equals	([B[B)Z
/*     */     //   86: ifne +14 -> 100
/*     */     //   89: iconst_0
/*     */     //   90: istore 9
/*     */     //   92: aload 4
/*     */     //   94: invokevirtual 90	com/google/common/io/Closer:close	()V
/*     */     //   97: iload 9
/*     */     //   99: ireturn
/*     */     //   100: iload 7
/*     */     //   102: sipush 4096
/*     */     //   105: if_icmpeq +14 -> 119
/*     */     //   108: iconst_1
/*     */     //   109: istore 9
/*     */     //   111: aload 4
/*     */     //   113: invokevirtual 90	com/google/common/io/Closer:close	()V
/*     */     //   116: iload 9
/*     */     //   118: ireturn
/*     */     //   119: goto -69 -> 50
/*     */     //   122: astore 5
/*     */     //   124: aload 4
/*     */     //   126: aload 5
/*     */     //   128: invokevirtual 94	com/google/common/io/Closer:rethrow	(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;
/*     */     //   131: athrow
/*     */     //   132: astore 10
/*     */     //   134: aload 4
/*     */     //   136: invokevirtual 90	com/google/common/io/Closer:close	()V
/*     */     //   139: aload 10
/*     */     //   141: athrow
/*     */     // Line number table:
/*     */     //   Java source line #324	-> byte code offset #0
/*     */     //   Java source line #326	-> byte code offset #5
/*     */     //   Java source line #327	-> byte code offset #11
/*     */     //   Java source line #329	-> byte code offset #17
/*     */     //   Java source line #331	-> byte code offset #22
/*     */     //   Java source line #332	-> byte code offset #36
/*     */     //   Java source line #334	-> byte code offset #50
/*     */     //   Java source line #335	-> byte code offset #62
/*     */     //   Java source line #336	-> byte code offset #74
/*     */     //   Java source line #337	-> byte code offset #89
/*     */     //   Java source line #345	-> byte code offset #92
/*     */     //   Java source line #338	-> byte code offset #100
/*     */     //   Java source line #339	-> byte code offset #108
/*     */     //   Java source line #345	-> byte code offset #111
/*     */     //   Java source line #341	-> byte code offset #119
/*     */     //   Java source line #342	-> byte code offset #122
/*     */     //   Java source line #343	-> byte code offset #124
/*     */     //   Java source line #345	-> byte code offset #132
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	142	0	this	ByteSource
/*     */     //   0	142	1	other	ByteSource
/*     */     //   10	72	2	buf1	byte[]
/*     */     //   16	67	3	buf2	byte[]
/*     */     //   20	115	4	closer	Closer
/*     */     //   34	17	5	in1	InputStream
/*     */     //   122	5	5	e	Throwable
/*     */     //   48	15	6	in2	InputStream
/*     */     //   60	41	7	read1	int
/*     */     //   72	5	8	read2	int
/*     */     //   90	27	9	bool	boolean
/*     */     //   132	8	10	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   22	92	122	java/lang/Throwable
/*     */     //   100	111	122	java/lang/Throwable
/*     */     //   119	122	122	java/lang/Throwable
/*     */     //   22	92	132	finally
/*     */     //   100	111	132	finally
/*     */     //   119	134	132	finally
/*     */   }
/*     */   
/*     */   public static ByteSource concat(Iterable<? extends ByteSource> sources)
/*     */   {
/* 361 */     return new ConcatenatedByteSource(sources);
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
/*     */ 
/*     */ 
/*     */   public static ByteSource concat(Iterator<? extends ByteSource> sources)
/*     */   {
/* 383 */     return concat(ImmutableList.copyOf(sources));
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
/*     */   public static ByteSource concat(ByteSource... sources)
/*     */   {
/* 399 */     return concat(ImmutableList.copyOf(sources));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteSource wrap(byte[] b)
/*     */   {
/* 409 */     return new ByteArrayByteSource(b);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static ByteSource empty()
/*     */   {
/* 418 */     return EmptyByteSource.INSTANCE;
/*     */   }
/*     */   
/*     */ 
/*     */   private final class AsCharSource
/*     */     extends CharSource
/*     */   {
/*     */     private final Charset charset;
/*     */     
/*     */ 
/*     */     private AsCharSource(Charset charset)
/*     */     {
/* 430 */       this.charset = ((Charset)Preconditions.checkNotNull(charset));
/*     */     }
/*     */     
/*     */     public Reader openStream() throws IOException
/*     */     {
/* 435 */       return new InputStreamReader(ByteSource.this.openStream(), this.charset);
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 440 */       return ByteSource.this.toString() + ".asCharSource(" + this.charset + ")";
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private final class SlicedByteSource
/*     */     extends ByteSource
/*     */   {
/*     */     private final long offset;
/*     */     private final long length;
/*     */     
/*     */     private SlicedByteSource(long offset, long length)
/*     */     {
/* 453 */       Preconditions.checkArgument(offset >= 0L, "offset (%s) may not be negative", new Object[] { Long.valueOf(offset) });
/* 454 */       Preconditions.checkArgument(length >= 0L, "length (%s) may not be negative", new Object[] { Long.valueOf(length) });
/* 455 */       this.offset = offset;
/* 456 */       this.length = length;
/*     */     }
/*     */     
/*     */     public InputStream openStream() throws IOException
/*     */     {
/* 461 */       return sliceStream(ByteSource.this.openStream());
/*     */     }
/*     */     
/*     */     public InputStream openBufferedStream() throws IOException
/*     */     {
/* 466 */       return sliceStream(ByteSource.this.openBufferedStream());
/*     */     }
/*     */     
/*     */     private InputStream sliceStream(InputStream in) throws IOException {
/* 470 */       if (this.offset > 0L) {
/*     */         try {
/* 472 */           ByteStreams.skipFully(in, this.offset);
/*     */         } catch (Throwable e) {
/* 474 */           Closer closer = Closer.create();
/* 475 */           closer.register(in);
/*     */           try {
/* 477 */             throw closer.rethrow(e);
/*     */           } finally {
/* 479 */             closer.close();
/*     */           }
/*     */         }
/*     */       }
/* 483 */       return ByteStreams.limit(in, this.length);
/*     */     }
/*     */     
/*     */     public ByteSource slice(long offset, long length)
/*     */     {
/* 488 */       Preconditions.checkArgument(offset >= 0L, "offset (%s) may not be negative", new Object[] { Long.valueOf(offset) });
/* 489 */       Preconditions.checkArgument(length >= 0L, "length (%s) may not be negative", new Object[] { Long.valueOf(length) });
/* 490 */       long maxLength = this.length - offset;
/* 491 */       return ByteSource.this.slice(this.offset + offset, Math.min(length, maxLength));
/*     */     }
/*     */     
/*     */     public boolean isEmpty() throws IOException
/*     */     {
/* 496 */       return (this.length == 0L) || (super.isEmpty());
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 501 */       return ByteSource.this.toString() + ".slice(" + this.offset + ", " + this.length + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ByteArrayByteSource extends ByteSource
/*     */   {
/*     */     protected final byte[] bytes;
/*     */     
/*     */     protected ByteArrayByteSource(byte[] bytes) {
/* 510 */       this.bytes = ((byte[])Preconditions.checkNotNull(bytes));
/*     */     }
/*     */     
/*     */     public InputStream openStream()
/*     */     {
/* 515 */       return new ByteArrayInputStream(this.bytes);
/*     */     }
/*     */     
/*     */     public InputStream openBufferedStream() throws IOException
/*     */     {
/* 520 */       return openStream();
/*     */     }
/*     */     
/*     */     public boolean isEmpty()
/*     */     {
/* 525 */       return this.bytes.length == 0;
/*     */     }
/*     */     
/*     */     public long size()
/*     */     {
/* 530 */       return this.bytes.length;
/*     */     }
/*     */     
/*     */     public byte[] read()
/*     */     {
/* 535 */       return (byte[])this.bytes.clone();
/*     */     }
/*     */     
/*     */     public long copyTo(OutputStream output) throws IOException
/*     */     {
/* 540 */       output.write(this.bytes);
/* 541 */       return this.bytes.length;
/*     */     }
/*     */     
/*     */     public <T> T read(ByteProcessor<T> processor) throws IOException
/*     */     {
/* 546 */       processor.processBytes(this.bytes, 0, this.bytes.length);
/* 547 */       return (T)processor.getResult();
/*     */     }
/*     */     
/*     */     public HashCode hash(HashFunction hashFunction) throws IOException
/*     */     {
/* 552 */       return hashFunction.hashBytes(this.bytes);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 559 */       return "ByteSource.wrap(" + Ascii.truncate(BaseEncoding.base16().encode(this.bytes), 30, "...") + ")";
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class EmptyByteSource
/*     */     extends ByteSource.ByteArrayByteSource
/*     */   {
/* 566 */     private static final EmptyByteSource INSTANCE = new EmptyByteSource();
/*     */     
/*     */     private EmptyByteSource() {
/* 569 */       super();
/*     */     }
/*     */     
/*     */     public CharSource asCharSource(Charset charset)
/*     */     {
/* 574 */       Preconditions.checkNotNull(charset);
/* 575 */       return CharSource.empty();
/*     */     }
/*     */     
/*     */     public byte[] read()
/*     */     {
/* 580 */       return this.bytes;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 585 */       return "ByteSource.empty()";
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ConcatenatedByteSource extends ByteSource
/*     */   {
/*     */     private final Iterable<? extends ByteSource> sources;
/*     */     
/*     */     ConcatenatedByteSource(Iterable<? extends ByteSource> sources) {
/* 594 */       this.sources = ((Iterable)Preconditions.checkNotNull(sources));
/*     */     }
/*     */     
/*     */     public InputStream openStream() throws IOException
/*     */     {
/* 599 */       return new MultiInputStream(this.sources.iterator());
/*     */     }
/*     */     
/*     */     public boolean isEmpty() throws IOException
/*     */     {
/* 604 */       for (ByteSource source : this.sources) {
/* 605 */         if (!source.isEmpty()) {
/* 606 */           return false;
/*     */         }
/*     */       }
/* 609 */       return true;
/*     */     }
/*     */     
/*     */     public long size() throws IOException
/*     */     {
/* 614 */       long result = 0L;
/* 615 */       for (ByteSource source : this.sources) {
/* 616 */         result += source.size();
/*     */       }
/* 618 */       return result;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 623 */       return "ByteSource.concat(" + this.sources + ")";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\io\ByteSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */