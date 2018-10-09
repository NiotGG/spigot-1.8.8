/*     */ package io.netty.util.internal;
/*     */ 
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.Recycler.Handle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.RandomAccess;
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
/*     */ public final class RecyclableArrayList
/*     */   extends ArrayList<Object>
/*     */ {
/*     */   private static final long serialVersionUID = -8605125654176467947L;
/*     */   private static final int DEFAULT_INITIAL_CAPACITY = 8;
/*  36 */   private static final Recycler<RecyclableArrayList> RECYCLER = new Recycler()
/*     */   {
/*     */     protected RecyclableArrayList newObject(Recycler.Handle paramAnonymousHandle) {
/*  39 */       return new RecyclableArrayList(paramAnonymousHandle, null);
/*     */     }
/*     */   };
/*     */   
/*     */   private final Recycler.Handle handle;
/*     */   
/*     */   public static RecyclableArrayList newInstance()
/*     */   {
/*  47 */     return newInstance(8);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static RecyclableArrayList newInstance(int paramInt)
/*     */   {
/*  54 */     RecyclableArrayList localRecyclableArrayList = (RecyclableArrayList)RECYCLER.get();
/*  55 */     localRecyclableArrayList.ensureCapacity(paramInt);
/*  56 */     return localRecyclableArrayList;
/*     */   }
/*     */   
/*     */ 
/*     */   private RecyclableArrayList(Recycler.Handle paramHandle)
/*     */   {
/*  62 */     this(paramHandle, 8);
/*     */   }
/*     */   
/*     */   private RecyclableArrayList(Recycler.Handle paramHandle, int paramInt) {
/*  66 */     super(paramInt);
/*  67 */     this.handle = paramHandle;
/*     */   }
/*     */   
/*     */   public boolean addAll(Collection<?> paramCollection)
/*     */   {
/*  72 */     checkNullElements(paramCollection);
/*  73 */     return super.addAll(paramCollection);
/*     */   }
/*     */   
/*     */   public boolean addAll(int paramInt, Collection<?> paramCollection)
/*     */   {
/*  78 */     checkNullElements(paramCollection);
/*  79 */     return super.addAll(paramInt, paramCollection);
/*     */   }
/*     */   
/*     */   private static void checkNullElements(Collection<?> paramCollection) { Object localObject1;
/*  83 */     if (((paramCollection instanceof RandomAccess)) && ((paramCollection instanceof List)))
/*     */     {
/*  85 */       localObject1 = (List)paramCollection;
/*  86 */       int i = ((List)localObject1).size();
/*  87 */       for (int j = 0; j < i; j++) {
/*  88 */         if (((List)localObject1).get(j) == null) {
/*  89 */           throw new IllegalArgumentException("c contains null values");
/*     */         }
/*     */       }
/*     */     } else {
/*  93 */       for (localObject1 = paramCollection.iterator(); ((Iterator)localObject1).hasNext();) { Object localObject2 = ((Iterator)localObject1).next();
/*  94 */         if (localObject2 == null) {
/*  95 */           throw new IllegalArgumentException("c contains null values");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean add(Object paramObject)
/*     */   {
/* 103 */     if (paramObject == null) {
/* 104 */       throw new NullPointerException("element");
/*     */     }
/* 106 */     return super.add(paramObject);
/*     */   }
/*     */   
/*     */   public void add(int paramInt, Object paramObject)
/*     */   {
/* 111 */     if (paramObject == null) {
/* 112 */       throw new NullPointerException("element");
/*     */     }
/* 114 */     super.add(paramInt, paramObject);
/*     */   }
/*     */   
/*     */   public Object set(int paramInt, Object paramObject)
/*     */   {
/* 119 */     if (paramObject == null) {
/* 120 */       throw new NullPointerException("element");
/*     */     }
/* 122 */     return super.set(paramInt, paramObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean recycle()
/*     */   {
/* 129 */     clear();
/* 130 */     return RECYCLER.recycle(this, this.handle);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\RecyclableArrayList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */