/*      */ package io.netty.buffer;
/*      */ 
/*      */ import io.netty.util.ResourceLeak;
/*      */ import io.netty.util.ResourceLeakDetector;
/*      */ import io.netty.util.internal.EmptyArrays;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.channels.GatheringByteChannel;
/*      */ import java.nio.channels.ScatteringByteChannel;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CompositeByteBuf
/*      */   extends AbstractReferenceCountedByteBuf
/*      */ {
/*      */   private final ResourceLeak leak;
/*      */   private final ByteBufAllocator alloc;
/*      */   private final boolean direct;
/*   45 */   private final List<Component> components = new ArrayList();
/*      */   private final int maxNumComponents;
/*   47 */   private static final ByteBuffer FULL_BYTEBUFFER = (ByteBuffer)ByteBuffer.allocate(1).position(1);
/*      */   private boolean freed;
/*      */   
/*      */   public CompositeByteBuf(ByteBufAllocator paramByteBufAllocator, boolean paramBoolean, int paramInt)
/*      */   {
/*   52 */     super(Integer.MAX_VALUE);
/*   53 */     if (paramByteBufAllocator == null) {
/*   54 */       throw new NullPointerException("alloc");
/*      */     }
/*   56 */     this.alloc = paramByteBufAllocator;
/*   57 */     this.direct = paramBoolean;
/*   58 */     this.maxNumComponents = paramInt;
/*   59 */     this.leak = leakDetector.open(this);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf(ByteBufAllocator paramByteBufAllocator, boolean paramBoolean, int paramInt, ByteBuf... paramVarArgs) {
/*   63 */     super(Integer.MAX_VALUE);
/*   64 */     if (paramByteBufAllocator == null) {
/*   65 */       throw new NullPointerException("alloc");
/*      */     }
/*   67 */     if (paramInt < 2) {
/*   68 */       throw new IllegalArgumentException("maxNumComponents: " + paramInt + " (expected: >= 2)");
/*      */     }
/*      */     
/*      */ 
/*   72 */     this.alloc = paramByteBufAllocator;
/*   73 */     this.direct = paramBoolean;
/*   74 */     this.maxNumComponents = paramInt;
/*      */     
/*   76 */     addComponents0(0, paramVarArgs);
/*   77 */     consolidateIfNeeded();
/*   78 */     setIndex(0, capacity());
/*   79 */     this.leak = leakDetector.open(this);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf(ByteBufAllocator paramByteBufAllocator, boolean paramBoolean, int paramInt, Iterable<ByteBuf> paramIterable)
/*      */   {
/*   84 */     super(Integer.MAX_VALUE);
/*   85 */     if (paramByteBufAllocator == null) {
/*   86 */       throw new NullPointerException("alloc");
/*      */     }
/*   88 */     if (paramInt < 2) {
/*   89 */       throw new IllegalArgumentException("maxNumComponents: " + paramInt + " (expected: >= 2)");
/*      */     }
/*      */     
/*      */ 
/*   93 */     this.alloc = paramByteBufAllocator;
/*   94 */     this.direct = paramBoolean;
/*   95 */     this.maxNumComponents = paramInt;
/*   96 */     addComponents0(0, paramIterable);
/*   97 */     consolidateIfNeeded();
/*   98 */     setIndex(0, capacity());
/*   99 */     this.leak = leakDetector.open(this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompositeByteBuf addComponent(ByteBuf paramByteBuf)
/*      */   {
/*  111 */     addComponent0(this.components.size(), paramByteBuf);
/*  112 */     consolidateIfNeeded();
/*  113 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompositeByteBuf addComponents(ByteBuf... paramVarArgs)
/*      */   {
/*  125 */     addComponents0(this.components.size(), paramVarArgs);
/*  126 */     consolidateIfNeeded();
/*  127 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompositeByteBuf addComponents(Iterable<ByteBuf> paramIterable)
/*      */   {
/*  139 */     addComponents0(this.components.size(), paramIterable);
/*  140 */     consolidateIfNeeded();
/*  141 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompositeByteBuf addComponent(int paramInt, ByteBuf paramByteBuf)
/*      */   {
/*  154 */     addComponent0(paramInt, paramByteBuf);
/*  155 */     consolidateIfNeeded();
/*  156 */     return this;
/*      */   }
/*      */   
/*      */   private int addComponent0(int paramInt, ByteBuf paramByteBuf) {
/*  160 */     checkComponentIndex(paramInt);
/*      */     
/*  162 */     if (paramByteBuf == null) {
/*  163 */       throw new NullPointerException("buffer");
/*      */     }
/*      */     
/*  166 */     int i = paramByteBuf.readableBytes();
/*  167 */     if (i == 0) {
/*  168 */       return paramInt;
/*      */     }
/*      */     
/*      */ 
/*  172 */     Component localComponent1 = new Component(paramByteBuf.order(ByteOrder.BIG_ENDIAN).slice());
/*  173 */     if (paramInt == this.components.size()) {
/*  174 */       this.components.add(localComponent1);
/*  175 */       if (paramInt == 0) {
/*  176 */         localComponent1.endOffset = i;
/*      */       } else {
/*  178 */         Component localComponent2 = (Component)this.components.get(paramInt - 1);
/*  179 */         localComponent1.offset = localComponent2.endOffset;
/*  180 */         localComponent1.endOffset = (localComponent1.offset + i);
/*      */       }
/*      */     } else {
/*  183 */       this.components.add(paramInt, localComponent1);
/*  184 */       updateComponentOffsets(paramInt);
/*      */     }
/*  186 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompositeByteBuf addComponents(int paramInt, ByteBuf... paramVarArgs)
/*      */   {
/*  199 */     addComponents0(paramInt, paramVarArgs);
/*  200 */     consolidateIfNeeded();
/*  201 */     return this;
/*      */   }
/*      */   
/*      */   private int addComponents0(int paramInt, ByteBuf... paramVarArgs) {
/*  205 */     checkComponentIndex(paramInt);
/*      */     
/*  207 */     if (paramVarArgs == null) {
/*  208 */       throw new NullPointerException("buffers");
/*      */     }
/*      */     
/*  211 */     int i = 0;
/*  212 */     ByteBuf localByteBuf; for (localByteBuf : paramVarArgs) {
/*  213 */       if (localByteBuf == null) {
/*      */         break;
/*      */       }
/*  216 */       i += localByteBuf.readableBytes();
/*      */     }
/*      */     
/*  219 */     if (i == 0) {
/*  220 */       return paramInt;
/*      */     }
/*      */     
/*      */ 
/*  224 */     for (localByteBuf : paramVarArgs) {
/*  225 */       if (localByteBuf == null) {
/*      */         break;
/*      */       }
/*  228 */       if (localByteBuf.isReadable()) {
/*  229 */         paramInt = addComponent0(paramInt, localByteBuf) + 1;
/*  230 */         int m = this.components.size();
/*  231 */         if (paramInt > m) {
/*  232 */           paramInt = m;
/*      */         }
/*      */       } else {
/*  235 */         localByteBuf.release();
/*      */       }
/*      */     }
/*  238 */     return paramInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompositeByteBuf addComponents(int paramInt, Iterable<ByteBuf> paramIterable)
/*      */   {
/*  251 */     addComponents0(paramInt, paramIterable);
/*  252 */     consolidateIfNeeded();
/*  253 */     return this;
/*      */   }
/*      */   
/*      */   private int addComponents0(int paramInt, Iterable<ByteBuf> paramIterable) {
/*  257 */     if (paramIterable == null) {
/*  258 */       throw new NullPointerException("buffers");
/*      */     }
/*      */     
/*  261 */     if ((paramIterable instanceof ByteBuf))
/*      */     {
/*  263 */       return addComponent0(paramInt, (ByteBuf)paramIterable);
/*      */     }
/*      */     
/*  266 */     if (!(paramIterable instanceof Collection)) {
/*  267 */       localObject = new ArrayList();
/*  268 */       for (ByteBuf localByteBuf : paramIterable) {
/*  269 */         ((List)localObject).add(localByteBuf);
/*      */       }
/*  271 */       paramIterable = (Iterable<ByteBuf>)localObject;
/*      */     }
/*      */     
/*  274 */     Object localObject = (Collection)paramIterable;
/*  275 */     return addComponents0(paramInt, (ByteBuf[])((Collection)localObject).toArray(new ByteBuf[((Collection)localObject).size()]));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void consolidateIfNeeded()
/*      */   {
/*  285 */     int i = this.components.size();
/*  286 */     if (i > this.maxNumComponents) {
/*  287 */       int j = ((Component)this.components.get(i - 1)).endOffset;
/*      */       
/*  289 */       ByteBuf localByteBuf1 = allocBuffer(j);
/*      */       
/*      */ 
/*  292 */       for (int k = 0; k < i; k++) {
/*  293 */         Component localComponent2 = (Component)this.components.get(k);
/*  294 */         ByteBuf localByteBuf2 = localComponent2.buf;
/*  295 */         localByteBuf1.writeBytes(localByteBuf2);
/*  296 */         localComponent2.freeIfNecessary();
/*      */       }
/*  298 */       Component localComponent1 = new Component(localByteBuf1);
/*  299 */       localComponent1.endOffset = localComponent1.length;
/*  300 */       this.components.clear();
/*  301 */       this.components.add(localComponent1);
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkComponentIndex(int paramInt) {
/*  306 */     ensureAccessible();
/*  307 */     if ((paramInt < 0) || (paramInt > this.components.size())) {
/*  308 */       throw new IndexOutOfBoundsException(String.format("cIndex: %d (expected: >= 0 && <= numComponents(%d))", new Object[] { Integer.valueOf(paramInt), Integer.valueOf(this.components.size()) }));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void checkComponentIndex(int paramInt1, int paramInt2)
/*      */   {
/*  315 */     ensureAccessible();
/*  316 */     if ((paramInt1 < 0) || (paramInt1 + paramInt2 > this.components.size())) {
/*  317 */       throw new IndexOutOfBoundsException(String.format("cIndex: %d, numComponents: %d (expected: cIndex >= 0 && cIndex + numComponents <= totalNumComponents(%d))", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(this.components.size()) }));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void updateComponentOffsets(int paramInt)
/*      */   {
/*  325 */     int i = this.components.size();
/*  326 */     if (i <= paramInt) {
/*  327 */       return;
/*      */     }
/*      */     
/*  330 */     Component localComponent1 = (Component)this.components.get(paramInt);
/*  331 */     if (paramInt == 0) {
/*  332 */       localComponent1.offset = 0;
/*  333 */       localComponent1.endOffset = localComponent1.length;
/*  334 */       paramInt++;
/*      */     }
/*      */     
/*  337 */     for (int j = paramInt; j < i; j++) {
/*  338 */       Component localComponent2 = (Component)this.components.get(j - 1);
/*  339 */       Component localComponent3 = (Component)this.components.get(j);
/*  340 */       localComponent3.offset = localComponent2.endOffset;
/*  341 */       localComponent3.endOffset = (localComponent3.offset + localComponent3.length);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompositeByteBuf removeComponent(int paramInt)
/*      */   {
/*  351 */     checkComponentIndex(paramInt);
/*  352 */     ((Component)this.components.remove(paramInt)).freeIfNecessary();
/*  353 */     updateComponentOffsets(paramInt);
/*  354 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompositeByteBuf removeComponents(int paramInt1, int paramInt2)
/*      */   {
/*  364 */     checkComponentIndex(paramInt1, paramInt2);
/*      */     
/*  366 */     List localList = this.components.subList(paramInt1, paramInt1 + paramInt2);
/*  367 */     for (Component localComponent : localList) {
/*  368 */       localComponent.freeIfNecessary();
/*      */     }
/*  370 */     localList.clear();
/*      */     
/*  372 */     updateComponentOffsets(paramInt1);
/*  373 */     return this;
/*      */   }
/*      */   
/*      */   public Iterator<ByteBuf> iterator() {
/*  377 */     ensureAccessible();
/*  378 */     ArrayList localArrayList = new ArrayList(this.components.size());
/*  379 */     for (Component localComponent : this.components) {
/*  380 */       localArrayList.add(localComponent.buf);
/*      */     }
/*  382 */     return localArrayList.iterator();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public List<ByteBuf> decompose(int paramInt1, int paramInt2)
/*      */   {
/*  389 */     checkIndex(paramInt1, paramInt2);
/*  390 */     if (paramInt2 == 0) {
/*  391 */       return Collections.emptyList();
/*      */     }
/*      */     
/*  394 */     int i = toComponentIndex(paramInt1);
/*  395 */     ArrayList localArrayList = new ArrayList(this.components.size());
/*      */     
/*      */ 
/*  398 */     Component localComponent = (Component)this.components.get(i);
/*  399 */     ByteBuf localByteBuf1 = localComponent.buf.duplicate();
/*  400 */     localByteBuf1.readerIndex(paramInt1 - localComponent.offset);
/*      */     
/*  402 */     ByteBuf localByteBuf2 = localByteBuf1;
/*  403 */     int j = paramInt2;
/*      */     do {
/*  405 */       k = localByteBuf2.readableBytes();
/*  406 */       if (j <= k)
/*      */       {
/*  408 */         localByteBuf2.writerIndex(localByteBuf2.readerIndex() + j);
/*  409 */         localArrayList.add(localByteBuf2);
/*  410 */         break;
/*      */       }
/*      */       
/*  413 */       localArrayList.add(localByteBuf2);
/*  414 */       j -= k;
/*  415 */       i++;
/*      */       
/*      */ 
/*  418 */       localByteBuf2 = ((Component)this.components.get(i)).buf.duplicate();
/*      */     }
/*  420 */     while (j > 0);
/*      */     
/*      */ 
/*  423 */     for (int k = 0; k < localArrayList.size(); k++) {
/*  424 */       localArrayList.set(k, ((ByteBuf)localArrayList.get(k)).slice());
/*      */     }
/*      */     
/*  427 */     return localArrayList;
/*      */   }
/*      */   
/*      */   public boolean isDirect()
/*      */   {
/*  432 */     int i = this.components.size();
/*  433 */     if (i == 0) {
/*  434 */       return false;
/*      */     }
/*  436 */     for (int j = 0; j < i; j++) {
/*  437 */       if (!((Component)this.components.get(j)).buf.isDirect()) {
/*  438 */         return false;
/*      */       }
/*      */     }
/*  441 */     return true;
/*      */   }
/*      */   
/*      */   public boolean hasArray()
/*      */   {
/*  446 */     if (this.components.size() == 1) {
/*  447 */       return ((Component)this.components.get(0)).buf.hasArray();
/*      */     }
/*  449 */     return false;
/*      */   }
/*      */   
/*      */   public byte[] array()
/*      */   {
/*  454 */     if (this.components.size() == 1) {
/*  455 */       return ((Component)this.components.get(0)).buf.array();
/*      */     }
/*  457 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */   public int arrayOffset()
/*      */   {
/*  462 */     if (this.components.size() == 1) {
/*  463 */       return ((Component)this.components.get(0)).buf.arrayOffset();
/*      */     }
/*  465 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */   public boolean hasMemoryAddress()
/*      */   {
/*  470 */     if (this.components.size() == 1) {
/*  471 */       return ((Component)this.components.get(0)).buf.hasMemoryAddress();
/*      */     }
/*  473 */     return false;
/*      */   }
/*      */   
/*      */   public long memoryAddress()
/*      */   {
/*  478 */     if (this.components.size() == 1) {
/*  479 */       return ((Component)this.components.get(0)).buf.memoryAddress();
/*      */     }
/*  481 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */   public int capacity()
/*      */   {
/*  486 */     if (this.components.isEmpty()) {
/*  487 */       return 0;
/*      */     }
/*  489 */     return ((Component)this.components.get(this.components.size() - 1)).endOffset;
/*      */   }
/*      */   
/*      */   public CompositeByteBuf capacity(int paramInt)
/*      */   {
/*  494 */     ensureAccessible();
/*  495 */     if ((paramInt < 0) || (paramInt > maxCapacity())) {
/*  496 */       throw new IllegalArgumentException("newCapacity: " + paramInt);
/*      */     }
/*      */     
/*  499 */     int i = capacity();
/*  500 */     int j; Object localObject; if (paramInt > i) {
/*  501 */       j = paramInt - i;
/*      */       
/*  503 */       int k = this.components.size();
/*  504 */       if (k < this.maxNumComponents) {
/*  505 */         localObject = allocBuffer(j);
/*  506 */         ((ByteBuf)localObject).setIndex(0, j);
/*  507 */         addComponent0(this.components.size(), (ByteBuf)localObject);
/*      */       } else {
/*  509 */         localObject = allocBuffer(j);
/*  510 */         ((ByteBuf)localObject).setIndex(0, j);
/*      */         
/*      */ 
/*  513 */         addComponent0(this.components.size(), (ByteBuf)localObject);
/*  514 */         consolidateIfNeeded();
/*      */       }
/*  516 */     } else if (paramInt < i) {
/*  517 */       j = i - paramInt;
/*  518 */       for (localObject = this.components.listIterator(this.components.size()); ((ListIterator)localObject).hasPrevious();) {
/*  519 */         Component localComponent1 = (Component)((ListIterator)localObject).previous();
/*  520 */         if (j >= localComponent1.length) {
/*  521 */           j -= localComponent1.length;
/*  522 */           ((ListIterator)localObject).remove();
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  527 */           Component localComponent2 = new Component(localComponent1.buf.slice(0, localComponent1.length - j));
/*  528 */           localComponent2.offset = localComponent1.offset;
/*  529 */           localComponent2.endOffset = (localComponent2.offset + localComponent2.length);
/*  530 */           ((ListIterator)localObject).set(localComponent2);
/*      */         }
/*      */       }
/*      */       
/*  534 */       if (readerIndex() > paramInt) {
/*  535 */         setIndex(paramInt, paramInt);
/*  536 */       } else if (writerIndex() > paramInt) {
/*  537 */         writerIndex(paramInt);
/*      */       }
/*      */     }
/*  540 */     return this;
/*      */   }
/*      */   
/*      */   public ByteBufAllocator alloc()
/*      */   {
/*  545 */     return this.alloc;
/*      */   }
/*      */   
/*      */   public ByteOrder order()
/*      */   {
/*  550 */     return ByteOrder.BIG_ENDIAN;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int numComponents()
/*      */   {
/*  557 */     return this.components.size();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int maxNumComponents()
/*      */   {
/*  564 */     return this.maxNumComponents;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public int toComponentIndex(int paramInt)
/*      */   {
/*  571 */     checkIndex(paramInt);
/*      */     
/*  573 */     int i = 0; for (int j = this.components.size(); i <= j;) {
/*  574 */       int k = i + j >>> 1;
/*  575 */       Component localComponent = (Component)this.components.get(k);
/*  576 */       if (paramInt >= localComponent.endOffset) {
/*  577 */         i = k + 1;
/*  578 */       } else if (paramInt < localComponent.offset) {
/*  579 */         j = k - 1;
/*      */       } else {
/*  581 */         return k;
/*      */       }
/*      */     }
/*      */     
/*  585 */     throw new Error("should not reach here");
/*      */   }
/*      */   
/*      */   public int toByteIndex(int paramInt) {
/*  589 */     checkComponentIndex(paramInt);
/*  590 */     return ((Component)this.components.get(paramInt)).offset;
/*      */   }
/*      */   
/*      */   public byte getByte(int paramInt)
/*      */   {
/*  595 */     return _getByte(paramInt);
/*      */   }
/*      */   
/*      */   protected byte _getByte(int paramInt)
/*      */   {
/*  600 */     Component localComponent = findComponent(paramInt);
/*  601 */     return localComponent.buf.getByte(paramInt - localComponent.offset);
/*      */   }
/*      */   
/*      */   protected short _getShort(int paramInt)
/*      */   {
/*  606 */     Component localComponent = findComponent(paramInt);
/*  607 */     if (paramInt + 2 <= localComponent.endOffset)
/*  608 */       return localComponent.buf.getShort(paramInt - localComponent.offset);
/*  609 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  610 */       return (short)((_getByte(paramInt) & 0xFF) << 8 | _getByte(paramInt + 1) & 0xFF);
/*      */     }
/*  612 */     return (short)(_getByte(paramInt) & 0xFF | (_getByte(paramInt + 1) & 0xFF) << 8);
/*      */   }
/*      */   
/*      */ 
/*      */   protected int _getUnsignedMedium(int paramInt)
/*      */   {
/*  618 */     Component localComponent = findComponent(paramInt);
/*  619 */     if (paramInt + 3 <= localComponent.endOffset)
/*  620 */       return localComponent.buf.getUnsignedMedium(paramInt - localComponent.offset);
/*  621 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  622 */       return (_getShort(paramInt) & 0xFFFF) << 8 | _getByte(paramInt + 2) & 0xFF;
/*      */     }
/*  624 */     return _getShort(paramInt) & 0xFFFF | (_getByte(paramInt + 2) & 0xFF) << 16;
/*      */   }
/*      */   
/*      */ 
/*      */   protected int _getInt(int paramInt)
/*      */   {
/*  630 */     Component localComponent = findComponent(paramInt);
/*  631 */     if (paramInt + 4 <= localComponent.endOffset)
/*  632 */       return localComponent.buf.getInt(paramInt - localComponent.offset);
/*  633 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  634 */       return (_getShort(paramInt) & 0xFFFF) << 16 | _getShort(paramInt + 2) & 0xFFFF;
/*      */     }
/*  636 */     return _getShort(paramInt) & 0xFFFF | (_getShort(paramInt + 2) & 0xFFFF) << 16;
/*      */   }
/*      */   
/*      */ 
/*      */   protected long _getLong(int paramInt)
/*      */   {
/*  642 */     Component localComponent = findComponent(paramInt);
/*  643 */     if (paramInt + 8 <= localComponent.endOffset)
/*  644 */       return localComponent.buf.getLong(paramInt - localComponent.offset);
/*  645 */     if (order() == ByteOrder.BIG_ENDIAN) {
/*  646 */       return (_getInt(paramInt) & 0xFFFFFFFF) << 32 | _getInt(paramInt + 4) & 0xFFFFFFFF;
/*      */     }
/*  648 */     return _getInt(paramInt) & 0xFFFFFFFF | (_getInt(paramInt + 4) & 0xFFFFFFFF) << 32;
/*      */   }
/*      */   
/*      */ 
/*      */   public CompositeByteBuf getBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*      */   {
/*  654 */     checkDstIndex(paramInt1, paramInt3, paramInt2, paramArrayOfByte.length);
/*  655 */     if (paramInt3 == 0) {
/*  656 */       return this;
/*      */     }
/*      */     
/*  659 */     int i = toComponentIndex(paramInt1);
/*  660 */     while (paramInt3 > 0) {
/*  661 */       Component localComponent = (Component)this.components.get(i);
/*  662 */       ByteBuf localByteBuf = localComponent.buf;
/*  663 */       int j = localComponent.offset;
/*  664 */       int k = Math.min(paramInt3, localByteBuf.capacity() - (paramInt1 - j));
/*  665 */       localByteBuf.getBytes(paramInt1 - j, paramArrayOfByte, paramInt2, k);
/*  666 */       paramInt1 += k;
/*  667 */       paramInt2 += k;
/*  668 */       paramInt3 -= k;
/*  669 */       i++;
/*      */     }
/*  671 */     return this;
/*      */   }
/*      */   
/*      */   public CompositeByteBuf getBytes(int paramInt, ByteBuffer paramByteBuffer)
/*      */   {
/*  676 */     int i = paramByteBuffer.limit();
/*  677 */     int j = paramByteBuffer.remaining();
/*      */     
/*  679 */     checkIndex(paramInt, j);
/*  680 */     if (j == 0) {
/*  681 */       return this;
/*      */     }
/*      */     
/*  684 */     int k = toComponentIndex(paramInt);
/*      */     try {
/*  686 */       while (j > 0) {
/*  687 */         Component localComponent = (Component)this.components.get(k);
/*  688 */         ByteBuf localByteBuf = localComponent.buf;
/*  689 */         int m = localComponent.offset;
/*  690 */         int n = Math.min(j, localByteBuf.capacity() - (paramInt - m));
/*  691 */         paramByteBuffer.limit(paramByteBuffer.position() + n);
/*  692 */         localByteBuf.getBytes(paramInt - m, paramByteBuffer);
/*  693 */         paramInt += n;
/*  694 */         j -= n;
/*  695 */         k++;
/*      */       }
/*      */     } finally {
/*  698 */       paramByteBuffer.limit(i);
/*      */     }
/*  700 */     return this;
/*      */   }
/*      */   
/*      */   public CompositeByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*      */   {
/*  705 */     checkDstIndex(paramInt1, paramInt3, paramInt2, paramByteBuf.capacity());
/*  706 */     if (paramInt3 == 0) {
/*  707 */       return this;
/*      */     }
/*      */     
/*  710 */     int i = toComponentIndex(paramInt1);
/*  711 */     while (paramInt3 > 0) {
/*  712 */       Component localComponent = (Component)this.components.get(i);
/*  713 */       ByteBuf localByteBuf = localComponent.buf;
/*  714 */       int j = localComponent.offset;
/*  715 */       int k = Math.min(paramInt3, localByteBuf.capacity() - (paramInt1 - j));
/*  716 */       localByteBuf.getBytes(paramInt1 - j, paramByteBuf, paramInt2, k);
/*  717 */       paramInt1 += k;
/*  718 */       paramInt2 += k;
/*  719 */       paramInt3 -= k;
/*  720 */       i++;
/*      */     }
/*  722 */     return this;
/*      */   }
/*      */   
/*      */   public int getBytes(int paramInt1, GatheringByteChannel paramGatheringByteChannel, int paramInt2)
/*      */     throws IOException
/*      */   {
/*  728 */     int i = nioBufferCount();
/*  729 */     if (i == 1) {
/*  730 */       return paramGatheringByteChannel.write(internalNioBuffer(paramInt1, paramInt2));
/*      */     }
/*  732 */     long l = paramGatheringByteChannel.write(nioBuffers(paramInt1, paramInt2));
/*  733 */     if (l > 2147483647L) {
/*  734 */       return Integer.MAX_VALUE;
/*      */     }
/*  736 */     return (int)l;
/*      */   }
/*      */   
/*      */ 
/*      */   public CompositeByteBuf getBytes(int paramInt1, OutputStream paramOutputStream, int paramInt2)
/*      */     throws IOException
/*      */   {
/*  743 */     checkIndex(paramInt1, paramInt2);
/*  744 */     if (paramInt2 == 0) {
/*  745 */       return this;
/*      */     }
/*      */     
/*  748 */     int i = toComponentIndex(paramInt1);
/*  749 */     while (paramInt2 > 0) {
/*  750 */       Component localComponent = (Component)this.components.get(i);
/*  751 */       ByteBuf localByteBuf = localComponent.buf;
/*  752 */       int j = localComponent.offset;
/*  753 */       int k = Math.min(paramInt2, localByteBuf.capacity() - (paramInt1 - j));
/*  754 */       localByteBuf.getBytes(paramInt1 - j, paramOutputStream, k);
/*  755 */       paramInt1 += k;
/*  756 */       paramInt2 -= k;
/*  757 */       i++;
/*      */     }
/*  759 */     return this;
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setByte(int paramInt1, int paramInt2)
/*      */   {
/*  764 */     Component localComponent = findComponent(paramInt1);
/*  765 */     localComponent.buf.setByte(paramInt1 - localComponent.offset, paramInt2);
/*  766 */     return this;
/*      */   }
/*      */   
/*      */   protected void _setByte(int paramInt1, int paramInt2)
/*      */   {
/*  771 */     setByte(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setShort(int paramInt1, int paramInt2)
/*      */   {
/*  776 */     return (CompositeByteBuf)super.setShort(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   protected void _setShort(int paramInt1, int paramInt2)
/*      */   {
/*  781 */     Component localComponent = findComponent(paramInt1);
/*  782 */     if (paramInt1 + 2 <= localComponent.endOffset) {
/*  783 */       localComponent.buf.setShort(paramInt1 - localComponent.offset, paramInt2);
/*  784 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/*  785 */       _setByte(paramInt1, (byte)(paramInt2 >>> 8));
/*  786 */       _setByte(paramInt1 + 1, (byte)paramInt2);
/*      */     } else {
/*  788 */       _setByte(paramInt1, (byte)paramInt2);
/*  789 */       _setByte(paramInt1 + 1, (byte)(paramInt2 >>> 8));
/*      */     }
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setMedium(int paramInt1, int paramInt2)
/*      */   {
/*  795 */     return (CompositeByteBuf)super.setMedium(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   protected void _setMedium(int paramInt1, int paramInt2)
/*      */   {
/*  800 */     Component localComponent = findComponent(paramInt1);
/*  801 */     if (paramInt1 + 3 <= localComponent.endOffset) {
/*  802 */       localComponent.buf.setMedium(paramInt1 - localComponent.offset, paramInt2);
/*  803 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/*  804 */       _setShort(paramInt1, (short)(paramInt2 >> 8));
/*  805 */       _setByte(paramInt1 + 2, (byte)paramInt2);
/*      */     } else {
/*  807 */       _setShort(paramInt1, (short)paramInt2);
/*  808 */       _setByte(paramInt1 + 2, (byte)(paramInt2 >>> 16));
/*      */     }
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setInt(int paramInt1, int paramInt2)
/*      */   {
/*  814 */     return (CompositeByteBuf)super.setInt(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   protected void _setInt(int paramInt1, int paramInt2)
/*      */   {
/*  819 */     Component localComponent = findComponent(paramInt1);
/*  820 */     if (paramInt1 + 4 <= localComponent.endOffset) {
/*  821 */       localComponent.buf.setInt(paramInt1 - localComponent.offset, paramInt2);
/*  822 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/*  823 */       _setShort(paramInt1, (short)(paramInt2 >>> 16));
/*  824 */       _setShort(paramInt1 + 2, (short)paramInt2);
/*      */     } else {
/*  826 */       _setShort(paramInt1, (short)paramInt2);
/*  827 */       _setShort(paramInt1 + 2, (short)(paramInt2 >>> 16));
/*      */     }
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setLong(int paramInt, long paramLong)
/*      */   {
/*  833 */     return (CompositeByteBuf)super.setLong(paramInt, paramLong);
/*      */   }
/*      */   
/*      */   protected void _setLong(int paramInt, long paramLong)
/*      */   {
/*  838 */     Component localComponent = findComponent(paramInt);
/*  839 */     if (paramInt + 8 <= localComponent.endOffset) {
/*  840 */       localComponent.buf.setLong(paramInt - localComponent.offset, paramLong);
/*  841 */     } else if (order() == ByteOrder.BIG_ENDIAN) {
/*  842 */       _setInt(paramInt, (int)(paramLong >>> 32));
/*  843 */       _setInt(paramInt + 4, (int)paramLong);
/*      */     } else {
/*  845 */       _setInt(paramInt, (int)paramLong);
/*  846 */       _setInt(paramInt + 4, (int)(paramLong >>> 32));
/*      */     }
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setBytes(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
/*      */   {
/*  852 */     checkSrcIndex(paramInt1, paramInt3, paramInt2, paramArrayOfByte.length);
/*  853 */     if (paramInt3 == 0) {
/*  854 */       return this;
/*      */     }
/*      */     
/*  857 */     int i = toComponentIndex(paramInt1);
/*  858 */     while (paramInt3 > 0) {
/*  859 */       Component localComponent = (Component)this.components.get(i);
/*  860 */       ByteBuf localByteBuf = localComponent.buf;
/*  861 */       int j = localComponent.offset;
/*  862 */       int k = Math.min(paramInt3, localByteBuf.capacity() - (paramInt1 - j));
/*  863 */       localByteBuf.setBytes(paramInt1 - j, paramArrayOfByte, paramInt2, k);
/*  864 */       paramInt1 += k;
/*  865 */       paramInt2 += k;
/*  866 */       paramInt3 -= k;
/*  867 */       i++;
/*      */     }
/*  869 */     return this;
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setBytes(int paramInt, ByteBuffer paramByteBuffer)
/*      */   {
/*  874 */     int i = paramByteBuffer.limit();
/*  875 */     int j = paramByteBuffer.remaining();
/*      */     
/*  877 */     checkIndex(paramInt, j);
/*  878 */     if (j == 0) {
/*  879 */       return this;
/*      */     }
/*      */     
/*  882 */     int k = toComponentIndex(paramInt);
/*      */     try {
/*  884 */       while (j > 0) {
/*  885 */         Component localComponent = (Component)this.components.get(k);
/*  886 */         ByteBuf localByteBuf = localComponent.buf;
/*  887 */         int m = localComponent.offset;
/*  888 */         int n = Math.min(j, localByteBuf.capacity() - (paramInt - m));
/*  889 */         paramByteBuffer.limit(paramByteBuffer.position() + n);
/*  890 */         localByteBuf.setBytes(paramInt - m, paramByteBuffer);
/*  891 */         paramInt += n;
/*  892 */         j -= n;
/*  893 */         k++;
/*      */       }
/*      */     } finally {
/*  896 */       paramByteBuffer.limit(i);
/*      */     }
/*  898 */     return this;
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2, int paramInt3)
/*      */   {
/*  903 */     checkSrcIndex(paramInt1, paramInt3, paramInt2, paramByteBuf.capacity());
/*  904 */     if (paramInt3 == 0) {
/*  905 */       return this;
/*      */     }
/*      */     
/*  908 */     int i = toComponentIndex(paramInt1);
/*  909 */     while (paramInt3 > 0) {
/*  910 */       Component localComponent = (Component)this.components.get(i);
/*  911 */       ByteBuf localByteBuf = localComponent.buf;
/*  912 */       int j = localComponent.offset;
/*  913 */       int k = Math.min(paramInt3, localByteBuf.capacity() - (paramInt1 - j));
/*  914 */       localByteBuf.setBytes(paramInt1 - j, paramByteBuf, paramInt2, k);
/*  915 */       paramInt1 += k;
/*  916 */       paramInt2 += k;
/*  917 */       paramInt3 -= k;
/*  918 */       i++;
/*      */     }
/*  920 */     return this;
/*      */   }
/*      */   
/*      */   public int setBytes(int paramInt1, InputStream paramInputStream, int paramInt2) throws IOException
/*      */   {
/*  925 */     checkIndex(paramInt1, paramInt2);
/*  926 */     if (paramInt2 == 0) {
/*  927 */       return paramInputStream.read(EmptyArrays.EMPTY_BYTES);
/*      */     }
/*      */     
/*  930 */     int i = toComponentIndex(paramInt1);
/*  931 */     int j = 0;
/*      */     do
/*      */     {
/*  934 */       Component localComponent = (Component)this.components.get(i);
/*  935 */       ByteBuf localByteBuf = localComponent.buf;
/*  936 */       int k = localComponent.offset;
/*  937 */       int m = Math.min(paramInt2, localByteBuf.capacity() - (paramInt1 - k));
/*  938 */       int n = localByteBuf.setBytes(paramInt1 - k, paramInputStream, m);
/*  939 */       if (n < 0) {
/*  940 */         if (j != 0) break;
/*  941 */         return -1;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  947 */       if (n == m) {
/*  948 */         paramInt1 += m;
/*  949 */         paramInt2 -= m;
/*  950 */         j += m;
/*  951 */         i++;
/*      */       } else {
/*  953 */         paramInt1 += n;
/*  954 */         paramInt2 -= n;
/*  955 */         j += n;
/*      */       }
/*  957 */     } while (paramInt2 > 0);
/*      */     
/*  959 */     return j;
/*      */   }
/*      */   
/*      */   public int setBytes(int paramInt1, ScatteringByteChannel paramScatteringByteChannel, int paramInt2) throws IOException
/*      */   {
/*  964 */     checkIndex(paramInt1, paramInt2);
/*  965 */     if (paramInt2 == 0) {
/*  966 */       return paramScatteringByteChannel.read(FULL_BYTEBUFFER);
/*      */     }
/*      */     
/*  969 */     int i = toComponentIndex(paramInt1);
/*  970 */     int j = 0;
/*      */     do {
/*  972 */       Component localComponent = (Component)this.components.get(i);
/*  973 */       ByteBuf localByteBuf = localComponent.buf;
/*  974 */       int k = localComponent.offset;
/*  975 */       int m = Math.min(paramInt2, localByteBuf.capacity() - (paramInt1 - k));
/*  976 */       int n = localByteBuf.setBytes(paramInt1 - k, paramScatteringByteChannel, m);
/*      */       
/*  978 */       if (n == 0) {
/*      */         break;
/*      */       }
/*      */       
/*  982 */       if (n < 0) {
/*  983 */         if (j != 0) break;
/*  984 */         return -1;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  990 */       if (n == m) {
/*  991 */         paramInt1 += m;
/*  992 */         paramInt2 -= m;
/*  993 */         j += m;
/*  994 */         i++;
/*      */       } else {
/*  996 */         paramInt1 += n;
/*  997 */         paramInt2 -= n;
/*  998 */         j += n;
/*      */       }
/* 1000 */     } while (paramInt2 > 0);
/*      */     
/* 1002 */     return j;
/*      */   }
/*      */   
/*      */   public ByteBuf copy(int paramInt1, int paramInt2)
/*      */   {
/* 1007 */     checkIndex(paramInt1, paramInt2);
/* 1008 */     ByteBuf localByteBuf = Unpooled.buffer(paramInt2);
/* 1009 */     if (paramInt2 != 0) {
/* 1010 */       copyTo(paramInt1, paramInt2, toComponentIndex(paramInt1), localByteBuf);
/*      */     }
/* 1012 */     return localByteBuf;
/*      */   }
/*      */   
/*      */   private void copyTo(int paramInt1, int paramInt2, int paramInt3, ByteBuf paramByteBuf) {
/* 1016 */     int i = 0;
/* 1017 */     int j = paramInt3;
/*      */     
/* 1019 */     while (paramInt2 > 0) {
/* 1020 */       Component localComponent = (Component)this.components.get(j);
/* 1021 */       ByteBuf localByteBuf = localComponent.buf;
/* 1022 */       int k = localComponent.offset;
/* 1023 */       int m = Math.min(paramInt2, localByteBuf.capacity() - (paramInt1 - k));
/* 1024 */       localByteBuf.getBytes(paramInt1 - k, paramByteBuf, i, m);
/* 1025 */       paramInt1 += m;
/* 1026 */       i += m;
/* 1027 */       paramInt2 -= m;
/* 1028 */       j++;
/*      */     }
/*      */     
/* 1031 */     paramByteBuf.writerIndex(paramByteBuf.capacity());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuf component(int paramInt)
/*      */   {
/* 1041 */     return internalComponent(paramInt).duplicate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuf componentAtOffset(int paramInt)
/*      */   {
/* 1051 */     return internalComponentAtOffset(paramInt).duplicate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuf internalComponent(int paramInt)
/*      */   {
/* 1061 */     checkComponentIndex(paramInt);
/* 1062 */     return ((Component)this.components.get(paramInt)).buf;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ByteBuf internalComponentAtOffset(int paramInt)
/*      */   {
/* 1072 */     return findComponent(paramInt).buf;
/*      */   }
/*      */   
/*      */   private Component findComponent(int paramInt) {
/* 1076 */     checkIndex(paramInt);
/*      */     
/* 1078 */     int i = 0; for (int j = this.components.size(); i <= j;) {
/* 1079 */       int k = i + j >>> 1;
/* 1080 */       Component localComponent = (Component)this.components.get(k);
/* 1081 */       if (paramInt >= localComponent.endOffset) {
/* 1082 */         i = k + 1;
/* 1083 */       } else if (paramInt < localComponent.offset) {
/* 1084 */         j = k - 1;
/*      */       } else {
/* 1086 */         return localComponent;
/*      */       }
/*      */     }
/*      */     
/* 1090 */     throw new Error("should not reach here");
/*      */   }
/*      */   
/*      */   public int nioBufferCount()
/*      */   {
/* 1095 */     if (this.components.size() == 1) {
/* 1096 */       return ((Component)this.components.get(0)).buf.nioBufferCount();
/*      */     }
/* 1098 */     int i = 0;
/* 1099 */     int j = this.components.size();
/* 1100 */     for (int k = 0; k < j; k++) {
/* 1101 */       Component localComponent = (Component)this.components.get(k);
/* 1102 */       i += localComponent.buf.nioBufferCount();
/*      */     }
/* 1104 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */   public ByteBuffer internalNioBuffer(int paramInt1, int paramInt2)
/*      */   {
/* 1110 */     if (this.components.size() == 1) {
/* 1111 */       return ((Component)this.components.get(0)).buf.internalNioBuffer(paramInt1, paramInt2);
/*      */     }
/* 1113 */     throw new UnsupportedOperationException();
/*      */   }
/*      */   
/*      */   public ByteBuffer nioBuffer(int paramInt1, int paramInt2)
/*      */   {
/* 1118 */     if (this.components.size() == 1) {
/* 1119 */       localObject = ((Component)this.components.get(0)).buf;
/* 1120 */       if (((ByteBuf)localObject).nioBufferCount() == 1) {
/* 1121 */         return ((Component)this.components.get(0)).buf.nioBuffer(paramInt1, paramInt2);
/*      */       }
/*      */     }
/* 1124 */     Object localObject = ByteBuffer.allocate(paramInt2).order(order());
/* 1125 */     ByteBuffer[] arrayOfByteBuffer = nioBuffers(paramInt1, paramInt2);
/*      */     
/*      */ 
/* 1128 */     for (int i = 0; i < arrayOfByteBuffer.length; i++) {
/* 1129 */       ((ByteBuffer)localObject).put(arrayOfByteBuffer[i]);
/*      */     }
/*      */     
/* 1132 */     ((ByteBuffer)localObject).flip();
/* 1133 */     return (ByteBuffer)localObject;
/*      */   }
/*      */   
/*      */   public ByteBuffer[] nioBuffers(int paramInt1, int paramInt2)
/*      */   {
/* 1138 */     checkIndex(paramInt1, paramInt2);
/* 1139 */     if (paramInt2 == 0) {
/* 1140 */       return EmptyArrays.EMPTY_BYTE_BUFFERS;
/*      */     }
/*      */     
/* 1143 */     ArrayList localArrayList = new ArrayList(this.components.size());
/* 1144 */     int i = toComponentIndex(paramInt1);
/* 1145 */     while (paramInt2 > 0) {
/* 1146 */       Component localComponent = (Component)this.components.get(i);
/* 1147 */       ByteBuf localByteBuf = localComponent.buf;
/* 1148 */       int j = localComponent.offset;
/* 1149 */       int k = Math.min(paramInt2, localByteBuf.capacity() - (paramInt1 - j));
/* 1150 */       switch (localByteBuf.nioBufferCount()) {
/*      */       case 0: 
/* 1152 */         throw new UnsupportedOperationException();
/*      */       case 1: 
/* 1154 */         localArrayList.add(localByteBuf.nioBuffer(paramInt1 - j, k));
/* 1155 */         break;
/*      */       default: 
/* 1157 */         Collections.addAll(localArrayList, localByteBuf.nioBuffers(paramInt1 - j, k));
/*      */       }
/*      */       
/* 1160 */       paramInt1 += k;
/* 1161 */       paramInt2 -= k;
/* 1162 */       i++;
/*      */     }
/*      */     
/* 1165 */     return (ByteBuffer[])localArrayList.toArray(new ByteBuffer[localArrayList.size()]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public CompositeByteBuf consolidate()
/*      */   {
/* 1172 */     ensureAccessible();
/* 1173 */     int i = numComponents();
/* 1174 */     if (i <= 1) {
/* 1175 */       return this;
/*      */     }
/*      */     
/* 1178 */     Component localComponent1 = (Component)this.components.get(i - 1);
/* 1179 */     int j = localComponent1.endOffset;
/* 1180 */     ByteBuf localByteBuf1 = allocBuffer(j);
/*      */     
/* 1182 */     for (int k = 0; k < i; k++) {
/* 1183 */       Component localComponent2 = (Component)this.components.get(k);
/* 1184 */       ByteBuf localByteBuf2 = localComponent2.buf;
/* 1185 */       localByteBuf1.writeBytes(localByteBuf2);
/* 1186 */       localComponent2.freeIfNecessary();
/*      */     }
/*      */     
/* 1189 */     this.components.clear();
/* 1190 */     this.components.add(new Component(localByteBuf1));
/* 1191 */     updateComponentOffsets(0);
/* 1192 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CompositeByteBuf consolidate(int paramInt1, int paramInt2)
/*      */   {
/* 1202 */     checkComponentIndex(paramInt1, paramInt2);
/* 1203 */     if (paramInt2 <= 1) {
/* 1204 */       return this;
/*      */     }
/*      */     
/* 1207 */     int i = paramInt1 + paramInt2;
/* 1208 */     Component localComponent1 = (Component)this.components.get(i - 1);
/* 1209 */     int j = localComponent1.endOffset - ((Component)this.components.get(paramInt1)).offset;
/* 1210 */     ByteBuf localByteBuf1 = allocBuffer(j);
/*      */     
/* 1212 */     for (int k = paramInt1; k < i; k++) {
/* 1213 */       Component localComponent2 = (Component)this.components.get(k);
/* 1214 */       ByteBuf localByteBuf2 = localComponent2.buf;
/* 1215 */       localByteBuf1.writeBytes(localByteBuf2);
/* 1216 */       localComponent2.freeIfNecessary();
/*      */     }
/*      */     
/* 1219 */     this.components.subList(paramInt1 + 1, i).clear();
/* 1220 */     this.components.set(paramInt1, new Component(localByteBuf1));
/* 1221 */     updateComponentOffsets(paramInt1);
/* 1222 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public CompositeByteBuf discardReadComponents()
/*      */   {
/* 1229 */     ensureAccessible();
/* 1230 */     int i = readerIndex();
/* 1231 */     if (i == 0) {
/* 1232 */       return this;
/*      */     }
/*      */     
/*      */ 
/* 1236 */     int j = writerIndex();
/* 1237 */     if ((i == j) && (j == capacity())) {
/* 1238 */       for (Component localComponent1 : this.components) {
/* 1239 */         localComponent1.freeIfNecessary();
/*      */       }
/* 1241 */       this.components.clear();
/* 1242 */       setIndex(0, 0);
/* 1243 */       adjustMarkers(i);
/* 1244 */       return this;
/*      */     }
/*      */     
/*      */ 
/* 1248 */     int k = toComponentIndex(i);
/* 1249 */     for (int m = 0; m < k; m++) {
/* 1250 */       ((Component)this.components.get(m)).freeIfNecessary();
/*      */     }
/* 1252 */     this.components.subList(0, k).clear();
/*      */     
/*      */ 
/* 1255 */     Component localComponent2 = (Component)this.components.get(0);
/* 1256 */     int n = localComponent2.offset;
/* 1257 */     updateComponentOffsets(0);
/* 1258 */     setIndex(i - n, j - n);
/* 1259 */     adjustMarkers(n);
/* 1260 */     return this;
/*      */   }
/*      */   
/*      */   public CompositeByteBuf discardReadBytes()
/*      */   {
/* 1265 */     ensureAccessible();
/* 1266 */     int i = readerIndex();
/* 1267 */     if (i == 0) {
/* 1268 */       return this;
/*      */     }
/*      */     
/*      */ 
/* 1272 */     int j = writerIndex();
/* 1273 */     if ((i == j) && (j == capacity())) {
/* 1274 */       for (Component localComponent1 : this.components) {
/* 1275 */         localComponent1.freeIfNecessary();
/*      */       }
/* 1277 */       this.components.clear();
/* 1278 */       setIndex(0, 0);
/* 1279 */       adjustMarkers(i);
/* 1280 */       return this;
/*      */     }
/*      */     
/*      */ 
/* 1284 */     int k = toComponentIndex(i);
/* 1285 */     for (int m = 0; m < k; m++) {
/* 1286 */       ((Component)this.components.get(m)).freeIfNecessary();
/*      */     }
/* 1288 */     this.components.subList(0, k).clear();
/*      */     
/*      */ 
/* 1291 */     Component localComponent2 = (Component)this.components.get(0);
/* 1292 */     int n = i - localComponent2.offset;
/* 1293 */     if (n == localComponent2.length)
/*      */     {
/* 1295 */       this.components.remove(0);
/*      */     } else {
/* 1297 */       Component localComponent3 = new Component(localComponent2.buf.slice(n, localComponent2.length - n));
/* 1298 */       this.components.set(0, localComponent3);
/*      */     }
/*      */     
/*      */ 
/* 1302 */     updateComponentOffsets(0);
/* 1303 */     setIndex(0, j - i);
/* 1304 */     adjustMarkers(i);
/* 1305 */     return this;
/*      */   }
/*      */   
/*      */   private ByteBuf allocBuffer(int paramInt) {
/* 1309 */     if (this.direct) {
/* 1310 */       return alloc().directBuffer(paramInt);
/*      */     }
/* 1312 */     return alloc().heapBuffer(paramInt);
/*      */   }
/*      */   
/*      */   public String toString()
/*      */   {
/* 1317 */     String str = super.toString();
/* 1318 */     str = str.substring(0, str.length() - 1);
/* 1319 */     return str + ", components=" + this.components.size() + ')';
/*      */   }
/*      */   
/*      */   private static final class Component {
/*      */     final ByteBuf buf;
/*      */     final int length;
/*      */     int offset;
/*      */     int endOffset;
/*      */     
/*      */     Component(ByteBuf paramByteBuf) {
/* 1329 */       this.buf = paramByteBuf;
/* 1330 */       this.length = paramByteBuf.readableBytes();
/*      */     }
/*      */     
/*      */     void freeIfNecessary()
/*      */     {
/* 1335 */       this.buf.release();
/*      */     }
/*      */   }
/*      */   
/*      */   public CompositeByteBuf readerIndex(int paramInt)
/*      */   {
/* 1341 */     return (CompositeByteBuf)super.readerIndex(paramInt);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writerIndex(int paramInt)
/*      */   {
/* 1346 */     return (CompositeByteBuf)super.writerIndex(paramInt);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setIndex(int paramInt1, int paramInt2)
/*      */   {
/* 1351 */     return (CompositeByteBuf)super.setIndex(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf clear()
/*      */   {
/* 1356 */     return (CompositeByteBuf)super.clear();
/*      */   }
/*      */   
/*      */   public CompositeByteBuf markReaderIndex()
/*      */   {
/* 1361 */     return (CompositeByteBuf)super.markReaderIndex();
/*      */   }
/*      */   
/*      */   public CompositeByteBuf resetReaderIndex()
/*      */   {
/* 1366 */     return (CompositeByteBuf)super.resetReaderIndex();
/*      */   }
/*      */   
/*      */   public CompositeByteBuf markWriterIndex()
/*      */   {
/* 1371 */     return (CompositeByteBuf)super.markWriterIndex();
/*      */   }
/*      */   
/*      */   public CompositeByteBuf resetWriterIndex()
/*      */   {
/* 1376 */     return (CompositeByteBuf)super.resetWriterIndex();
/*      */   }
/*      */   
/*      */   public CompositeByteBuf ensureWritable(int paramInt)
/*      */   {
/* 1381 */     return (CompositeByteBuf)super.ensureWritable(paramInt);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf getBytes(int paramInt, ByteBuf paramByteBuf)
/*      */   {
/* 1386 */     return (CompositeByteBuf)super.getBytes(paramInt, paramByteBuf);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf getBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2)
/*      */   {
/* 1391 */     return (CompositeByteBuf)super.getBytes(paramInt1, paramByteBuf, paramInt2);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf getBytes(int paramInt, byte[] paramArrayOfByte)
/*      */   {
/* 1396 */     return (CompositeByteBuf)super.getBytes(paramInt, paramArrayOfByte);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setBoolean(int paramInt, boolean paramBoolean)
/*      */   {
/* 1401 */     return (CompositeByteBuf)super.setBoolean(paramInt, paramBoolean);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setChar(int paramInt1, int paramInt2)
/*      */   {
/* 1406 */     return (CompositeByteBuf)super.setChar(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setFloat(int paramInt, float paramFloat)
/*      */   {
/* 1411 */     return (CompositeByteBuf)super.setFloat(paramInt, paramFloat);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setDouble(int paramInt, double paramDouble)
/*      */   {
/* 1416 */     return (CompositeByteBuf)super.setDouble(paramInt, paramDouble);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setBytes(int paramInt, ByteBuf paramByteBuf)
/*      */   {
/* 1421 */     return (CompositeByteBuf)super.setBytes(paramInt, paramByteBuf);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setBytes(int paramInt1, ByteBuf paramByteBuf, int paramInt2)
/*      */   {
/* 1426 */     return (CompositeByteBuf)super.setBytes(paramInt1, paramByteBuf, paramInt2);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setBytes(int paramInt, byte[] paramArrayOfByte)
/*      */   {
/* 1431 */     return (CompositeByteBuf)super.setBytes(paramInt, paramArrayOfByte);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf setZero(int paramInt1, int paramInt2)
/*      */   {
/* 1436 */     return (CompositeByteBuf)super.setZero(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuf paramByteBuf)
/*      */   {
/* 1441 */     return (CompositeByteBuf)super.readBytes(paramByteBuf);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuf paramByteBuf, int paramInt)
/*      */   {
/* 1446 */     return (CompositeByteBuf)super.readBytes(paramByteBuf, paramInt);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*      */   {
/* 1451 */     return (CompositeByteBuf)super.readBytes(paramByteBuf, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf readBytes(byte[] paramArrayOfByte)
/*      */   {
/* 1456 */     return (CompositeByteBuf)super.readBytes(paramArrayOfByte);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf readBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/* 1461 */     return (CompositeByteBuf)super.readBytes(paramArrayOfByte, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf readBytes(ByteBuffer paramByteBuffer)
/*      */   {
/* 1466 */     return (CompositeByteBuf)super.readBytes(paramByteBuffer);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf readBytes(OutputStream paramOutputStream, int paramInt) throws IOException
/*      */   {
/* 1471 */     return (CompositeByteBuf)super.readBytes(paramOutputStream, paramInt);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf skipBytes(int paramInt)
/*      */   {
/* 1476 */     return (CompositeByteBuf)super.skipBytes(paramInt);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeBoolean(boolean paramBoolean)
/*      */   {
/* 1481 */     return (CompositeByteBuf)super.writeBoolean(paramBoolean);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeByte(int paramInt)
/*      */   {
/* 1486 */     return (CompositeByteBuf)super.writeByte(paramInt);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeShort(int paramInt)
/*      */   {
/* 1491 */     return (CompositeByteBuf)super.writeShort(paramInt);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeMedium(int paramInt)
/*      */   {
/* 1496 */     return (CompositeByteBuf)super.writeMedium(paramInt);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeInt(int paramInt)
/*      */   {
/* 1501 */     return (CompositeByteBuf)super.writeInt(paramInt);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeLong(long paramLong)
/*      */   {
/* 1506 */     return (CompositeByteBuf)super.writeLong(paramLong);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeChar(int paramInt)
/*      */   {
/* 1511 */     return (CompositeByteBuf)super.writeChar(paramInt);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeFloat(float paramFloat)
/*      */   {
/* 1516 */     return (CompositeByteBuf)super.writeFloat(paramFloat);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeDouble(double paramDouble)
/*      */   {
/* 1521 */     return (CompositeByteBuf)super.writeDouble(paramDouble);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuf paramByteBuf)
/*      */   {
/* 1526 */     return (CompositeByteBuf)super.writeBytes(paramByteBuf);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuf paramByteBuf, int paramInt)
/*      */   {
/* 1531 */     return (CompositeByteBuf)super.writeBytes(paramByteBuf, paramInt);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuf paramByteBuf, int paramInt1, int paramInt2)
/*      */   {
/* 1536 */     return (CompositeByteBuf)super.writeBytes(paramByteBuf, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeBytes(byte[] paramArrayOfByte)
/*      */   {
/* 1541 */     return (CompositeByteBuf)super.writeBytes(paramArrayOfByte);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */   {
/* 1546 */     return (CompositeByteBuf)super.writeBytes(paramArrayOfByte, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeBytes(ByteBuffer paramByteBuffer)
/*      */   {
/* 1551 */     return (CompositeByteBuf)super.writeBytes(paramByteBuffer);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf writeZero(int paramInt)
/*      */   {
/* 1556 */     return (CompositeByteBuf)super.writeZero(paramInt);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf retain(int paramInt)
/*      */   {
/* 1561 */     return (CompositeByteBuf)super.retain(paramInt);
/*      */   }
/*      */   
/*      */   public CompositeByteBuf retain()
/*      */   {
/* 1566 */     return (CompositeByteBuf)super.retain();
/*      */   }
/*      */   
/*      */   public ByteBuffer[] nioBuffers()
/*      */   {
/* 1571 */     return nioBuffers(readerIndex(), readableBytes());
/*      */   }
/*      */   
/*      */   public CompositeByteBuf discardSomeReadBytes()
/*      */   {
/* 1576 */     return discardReadComponents();
/*      */   }
/*      */   
/*      */   protected void deallocate()
/*      */   {
/* 1581 */     if (this.freed) {
/* 1582 */       return;
/*      */     }
/*      */     
/* 1585 */     this.freed = true;
/* 1586 */     int i = this.components.size();
/*      */     
/*      */ 
/* 1589 */     for (int j = 0; j < i; j++) {
/* 1590 */       ((Component)this.components.get(j)).freeIfNecessary();
/*      */     }
/*      */     
/* 1593 */     if (this.leak != null) {
/* 1594 */       this.leak.close();
/*      */     }
/*      */   }
/*      */   
/*      */   public ByteBuf unwrap()
/*      */   {
/* 1600 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\buffer\CompositeByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */