/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Objects.ToStringHelper;
/*     */ import com.google.common.collect.HashBasedTable;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableTable;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class BlockStateList
/*     */ {
/*  16 */   private static final com.google.common.base.Joiner a = com.google.common.base.Joiner.on(", ");
/*  17 */   private static final com.google.common.base.Function<IBlockState, String> b = new com.google.common.base.Function()
/*     */   {
/*     */     public String a(IBlockState paramAnonymousIBlockState)
/*     */     {
/*  21 */       return paramAnonymousIBlockState == null ? "<NULL>" : paramAnonymousIBlockState.a();
/*     */     }
/*     */   };
/*     */   private final Block c;
/*     */   private final ImmutableList<IBlockState> d;
/*     */   private final ImmutableList<IBlockData> e;
/*     */   
/*     */   public BlockStateList(Block paramBlock, IBlockState... paramVarArgs)
/*     */   {
/*  30 */     this.c = paramBlock;
/*     */     
/*     */ 
/*  33 */     java.util.Arrays.sort(paramVarArgs, new java.util.Comparator()
/*     */     {
/*     */       public int a(IBlockState paramAnonymousIBlockState1, IBlockState paramAnonymousIBlockState2) {
/*  36 */         return paramAnonymousIBlockState1.a().compareTo(paramAnonymousIBlockState2.a());
/*     */       }
/*  38 */     });
/*  39 */     this.d = ImmutableList.copyOf(paramVarArgs);
/*     */     
/*     */ 
/*  42 */     java.util.LinkedHashMap localLinkedHashMap = Maps.newLinkedHashMap();
/*  43 */     java.util.ArrayList localArrayList = com.google.common.collect.Lists.newArrayList();
/*     */     
/*  45 */     Iterable localIterable = IteratorUtils.a(e());
/*  46 */     for (Iterator localIterator = localIterable.iterator(); localIterator.hasNext();) { localObject = (List)localIterator.next();
/*  47 */       Map localMap = MapGeneratorUtils.b(this.d, (Iterable)localObject);
/*  48 */       BlockData localBlockData = new BlockData(paramBlock, ImmutableMap.copyOf(localMap), null);
/*     */       
/*  50 */       localLinkedHashMap.put(localMap, localBlockData);
/*  51 */       localArrayList.add(localBlockData);
/*     */     }
/*     */     Object localObject;
/*  54 */     for (localIterator = localArrayList.iterator(); localIterator.hasNext();) { localObject = (BlockData)localIterator.next();
/*  55 */       ((BlockData)localObject).a(localLinkedHashMap);
/*     */     }
/*     */     
/*  58 */     this.e = ImmutableList.copyOf(localArrayList);
/*     */   }
/*     */   
/*     */   public ImmutableList<IBlockData> a() {
/*  62 */     return this.e;
/*     */   }
/*     */   
/*     */   private List<Iterable<Comparable>> e() {
/*  66 */     java.util.ArrayList localArrayList = com.google.common.collect.Lists.newArrayList();
/*  67 */     for (int i = 0; i < this.d.size(); i++) {
/*  68 */       localArrayList.add(((IBlockState)this.d.get(i)).c());
/*     */     }
/*     */     
/*  71 */     return localArrayList;
/*     */   }
/*     */   
/*     */   public IBlockData getBlockData() {
/*  75 */     return (IBlockData)this.e.get(0);
/*     */   }
/*     */   
/*     */   public Block getBlock() {
/*  79 */     return this.c;
/*     */   }
/*     */   
/*     */   public Collection<IBlockState> d() {
/*  83 */     return this.d;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  88 */     return com.google.common.base.Objects.toStringHelper(this).add("block", Block.REGISTRY.c(this.c)).add("properties", com.google.common.collect.Iterables.transform(this.d, b)).toString();
/*     */   }
/*     */   
/*     */   static class BlockData
/*     */     extends BlockDataAbstract
/*     */   {
/*     */     private final Block a;
/*     */     private final ImmutableMap<IBlockState, Comparable> b;
/*     */     private ImmutableTable<IBlockState, Comparable, IBlockData> c;
/*     */     
/*     */     private BlockData(Block paramBlock, ImmutableMap<IBlockState, Comparable> paramImmutableMap)
/*     */     {
/* 100 */       this.a = paramBlock;
/* 101 */       this.b = paramImmutableMap;
/*     */     }
/*     */     
/*     */     public Collection<IBlockState> a()
/*     */     {
/* 106 */       return java.util.Collections.unmodifiableCollection(this.b.keySet());
/*     */     }
/*     */     
/*     */     public <T extends Comparable<T>> T get(IBlockState<T> paramIBlockState)
/*     */     {
/* 111 */       if (!this.b.containsKey(paramIBlockState)) {
/* 112 */         throw new IllegalArgumentException("Cannot get property " + paramIBlockState + " as it does not exist in " + this.a.P());
/*     */       }
/*     */       
/* 115 */       return (Comparable)paramIBlockState.b().cast(this.b.get(paramIBlockState));
/*     */     }
/*     */     
/*     */     public <T extends Comparable<T>, V extends T> IBlockData set(IBlockState<T> paramIBlockState, V paramV)
/*     */     {
/* 120 */       if (!this.b.containsKey(paramIBlockState)) {
/* 121 */         throw new IllegalArgumentException("Cannot set property " + paramIBlockState + " as it does not exist in " + this.a.P());
/*     */       }
/* 123 */       if (!paramIBlockState.c().contains(paramV)) {
/* 124 */         throw new IllegalArgumentException("Cannot set property " + paramIBlockState + " to " + paramV + " on block " + Block.REGISTRY.c(this.a) + ", it is not an allowed value");
/*     */       }
/* 126 */       if (this.b.get(paramIBlockState) == paramV) {
/* 127 */         return this;
/*     */       }
/*     */       
/* 130 */       return (IBlockData)this.c.get(paramIBlockState, paramV);
/*     */     }
/*     */     
/*     */     public ImmutableMap<IBlockState, Comparable> b()
/*     */     {
/* 135 */       return this.b;
/*     */     }
/*     */     
/*     */     public Block getBlock()
/*     */     {
/* 140 */       return this.a;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 146 */       return this == paramObject;
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 151 */       return this.b.hashCode();
/*     */     }
/*     */     
/*     */     public void a(Map<Map<IBlockState, Comparable>, BlockData> paramMap) {
/* 155 */       if (this.c != null) {
/* 156 */         throw new IllegalStateException();
/*     */       }
/*     */       
/* 159 */       HashBasedTable localHashBasedTable = HashBasedTable.create();
/* 160 */       for (Iterator localIterator1 = this.b.keySet().iterator(); localIterator1.hasNext();) { localIBlockState = (IBlockState)localIterator1.next();
/* 161 */         for (Comparable localComparable : localIBlockState.c()) {
/* 162 */           if (localComparable != this.b.get(localIBlockState)) {
/* 163 */             localHashBasedTable.put(localIBlockState, localComparable, paramMap.get(b(localIBlockState, localComparable)));
/*     */           }
/*     */         }
/*     */       }
/*     */       IBlockState localIBlockState;
/* 168 */       this.c = ImmutableTable.copyOf(localHashBasedTable);
/*     */     }
/*     */     
/*     */     private Map<IBlockState, Comparable> b(IBlockState paramIBlockState, Comparable paramComparable) {
/* 172 */       java.util.HashMap localHashMap = Maps.newHashMap(this.b);
/* 173 */       localHashMap.put(paramIBlockState, paramComparable);
/* 174 */       return localHashMap;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\BlockStateList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */