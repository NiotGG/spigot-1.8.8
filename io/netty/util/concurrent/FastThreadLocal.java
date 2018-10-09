/*     */ package io.netty.util.concurrent;
/*     */ 
/*     */ import io.netty.util.internal.InternalThreadLocalMap;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.util.Collections;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Set;
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
/*     */ public class FastThreadLocal<V>
/*     */ {
/*  46 */   private static final int variablesToRemoveIndex = ;
/*     */   
/*     */ 
/*     */   private final int index;
/*     */   
/*     */ 
/*     */   public static void removeAll()
/*     */   {
/*  54 */     InternalThreadLocalMap localInternalThreadLocalMap = InternalThreadLocalMap.getIfSet();
/*  55 */     if (localInternalThreadLocalMap == null) {
/*  56 */       return;
/*     */     }
/*     */     try
/*     */     {
/*  60 */       Object localObject1 = localInternalThreadLocalMap.indexedVariable(variablesToRemoveIndex);
/*  61 */       if ((localObject1 != null) && (localObject1 != InternalThreadLocalMap.UNSET))
/*     */       {
/*  63 */         Set localSet = (Set)localObject1;
/*  64 */         FastThreadLocal[] arrayOfFastThreadLocal1 = (FastThreadLocal[])localSet.toArray(new FastThreadLocal[localSet.size()]);
/*     */         
/*  66 */         for (FastThreadLocal localFastThreadLocal : arrayOfFastThreadLocal1) {
/*  67 */           localFastThreadLocal.remove(localInternalThreadLocalMap);
/*     */         }
/*     */       }
/*     */     } finally {
/*  71 */       InternalThreadLocalMap.remove();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static int size()
/*     */   {
/*  79 */     InternalThreadLocalMap localInternalThreadLocalMap = InternalThreadLocalMap.getIfSet();
/*  80 */     if (localInternalThreadLocalMap == null) {
/*  81 */       return 0;
/*     */     }
/*  83 */     return localInternalThreadLocalMap.size();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void destroy() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void addToVariablesToRemove(InternalThreadLocalMap paramInternalThreadLocalMap, FastThreadLocal<?> paramFastThreadLocal)
/*     */   {
/*  99 */     Object localObject = paramInternalThreadLocalMap.indexedVariable(variablesToRemoveIndex);
/*     */     Set localSet;
/* 101 */     if ((localObject == InternalThreadLocalMap.UNSET) || (localObject == null)) {
/* 102 */       localSet = Collections.newSetFromMap(new IdentityHashMap());
/* 103 */       paramInternalThreadLocalMap.setIndexedVariable(variablesToRemoveIndex, localSet);
/*     */     } else {
/* 105 */       localSet = (Set)localObject;
/*     */     }
/*     */     
/* 108 */     localSet.add(paramFastThreadLocal);
/*     */   }
/*     */   
/*     */ 
/*     */   private static void removeFromVariablesToRemove(InternalThreadLocalMap paramInternalThreadLocalMap, FastThreadLocal<?> paramFastThreadLocal)
/*     */   {
/* 114 */     Object localObject = paramInternalThreadLocalMap.indexedVariable(variablesToRemoveIndex);
/*     */     
/* 116 */     if ((localObject == InternalThreadLocalMap.UNSET) || (localObject == null)) {
/* 117 */       return;
/*     */     }
/*     */     
/*     */ 
/* 121 */     Set localSet = (Set)localObject;
/* 122 */     localSet.remove(paramFastThreadLocal);
/*     */   }
/*     */   
/*     */ 
/*     */   public FastThreadLocal()
/*     */   {
/* 128 */     this.index = InternalThreadLocalMap.nextVariableIndex();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final V get()
/*     */   {
/* 135 */     return (V)get(InternalThreadLocalMap.get());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final V get(InternalThreadLocalMap paramInternalThreadLocalMap)
/*     */   {
/* 144 */     Object localObject = paramInternalThreadLocalMap.indexedVariable(this.index);
/* 145 */     if (localObject != InternalThreadLocalMap.UNSET) {
/* 146 */       return (V)localObject;
/*     */     }
/*     */     
/* 149 */     return (V)initialize(paramInternalThreadLocalMap);
/*     */   }
/*     */   
/*     */   private V initialize(InternalThreadLocalMap paramInternalThreadLocalMap) {
/* 153 */     Object localObject = null;
/*     */     try {
/* 155 */       localObject = initialValue();
/*     */     } catch (Exception localException) {
/* 157 */       PlatformDependent.throwException(localException);
/*     */     }
/*     */     
/* 160 */     paramInternalThreadLocalMap.setIndexedVariable(this.index, localObject);
/* 161 */     addToVariablesToRemove(paramInternalThreadLocalMap, this);
/* 162 */     return (V)localObject;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final void set(V paramV)
/*     */   {
/* 169 */     if (paramV != InternalThreadLocalMap.UNSET) {
/* 170 */       set(InternalThreadLocalMap.get(), paramV);
/*     */     } else {
/* 172 */       remove();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final void set(InternalThreadLocalMap paramInternalThreadLocalMap, V paramV)
/*     */   {
/* 180 */     if (paramV != InternalThreadLocalMap.UNSET) {
/* 181 */       if (paramInternalThreadLocalMap.setIndexedVariable(this.index, paramV)) {
/* 182 */         addToVariablesToRemove(paramInternalThreadLocalMap, this);
/*     */       }
/*     */     } else {
/* 185 */       remove(paramInternalThreadLocalMap);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final boolean isSet()
/*     */   {
/* 193 */     return isSet(InternalThreadLocalMap.getIfSet());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isSet(InternalThreadLocalMap paramInternalThreadLocalMap)
/*     */   {
/* 201 */     return (paramInternalThreadLocalMap != null) && (paramInternalThreadLocalMap.isIndexedVariableSet(this.index));
/*     */   }
/*     */   
/*     */ 
/*     */   public final void remove()
/*     */   {
/* 207 */     remove(InternalThreadLocalMap.getIfSet());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void remove(InternalThreadLocalMap paramInternalThreadLocalMap)
/*     */   {
/* 217 */     if (paramInternalThreadLocalMap == null) {
/* 218 */       return;
/*     */     }
/*     */     
/* 221 */     Object localObject = paramInternalThreadLocalMap.removeIndexedVariable(this.index);
/* 222 */     removeFromVariablesToRemove(paramInternalThreadLocalMap, this);
/*     */     
/* 224 */     if (localObject != InternalThreadLocalMap.UNSET) {
/*     */       try {
/* 226 */         onRemoval(localObject);
/*     */       } catch (Exception localException) {
/* 228 */         PlatformDependent.throwException(localException);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected V initialValue()
/*     */     throws Exception
/*     */   {
/* 237 */     return null;
/*     */   }
/*     */   
/*     */   protected void onRemoval(V paramV)
/*     */     throws Exception
/*     */   {}
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\concurrent\FastThreadLocal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */