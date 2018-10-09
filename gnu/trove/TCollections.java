/*      */ package gnu.trove;
/*      */ 
/*      */ import gnu.trove.list.TByteList;
/*      */ import gnu.trove.list.TCharList;
/*      */ import gnu.trove.list.TDoubleList;
/*      */ import gnu.trove.list.TFloatList;
/*      */ import gnu.trove.list.TIntList;
/*      */ import gnu.trove.list.TLongList;
/*      */ import gnu.trove.list.TShortList;
/*      */ import gnu.trove.map.TByteByteMap;
/*      */ import gnu.trove.map.TByteCharMap;
/*      */ import gnu.trove.map.TByteDoubleMap;
/*      */ import gnu.trove.map.TByteFloatMap;
/*      */ import gnu.trove.map.TByteIntMap;
/*      */ import gnu.trove.map.TByteLongMap;
/*      */ import gnu.trove.map.TByteObjectMap;
/*      */ import gnu.trove.map.TByteShortMap;
/*      */ import gnu.trove.map.TCharCharMap;
/*      */ import gnu.trove.map.TCharDoubleMap;
/*      */ import gnu.trove.map.TCharLongMap;
/*      */ import gnu.trove.map.TCharObjectMap;
/*      */ import gnu.trove.map.TCharShortMap;
/*      */ import gnu.trove.map.TDoubleByteMap;
/*      */ import gnu.trove.map.TDoubleCharMap;
/*      */ import gnu.trove.map.TDoubleDoubleMap;
/*      */ import gnu.trove.map.TDoubleFloatMap;
/*      */ import gnu.trove.map.TDoubleIntMap;
/*      */ import gnu.trove.map.TDoubleLongMap;
/*      */ import gnu.trove.map.TDoubleShortMap;
/*      */ import gnu.trove.map.TFloatByteMap;
/*      */ import gnu.trove.map.TFloatCharMap;
/*      */ import gnu.trove.map.TFloatDoubleMap;
/*      */ import gnu.trove.map.TFloatFloatMap;
/*      */ import gnu.trove.map.TFloatIntMap;
/*      */ import gnu.trove.map.TFloatLongMap;
/*      */ import gnu.trove.map.TFloatObjectMap;
/*      */ import gnu.trove.map.TFloatShortMap;
/*      */ import gnu.trove.map.TIntByteMap;
/*      */ import gnu.trove.map.TIntCharMap;
/*      */ import gnu.trove.map.TIntFloatMap;
/*      */ import gnu.trove.map.TIntIntMap;
/*      */ import gnu.trove.map.TIntObjectMap;
/*      */ import gnu.trove.map.TLongByteMap;
/*      */ import gnu.trove.map.TLongCharMap;
/*      */ import gnu.trove.map.TLongFloatMap;
/*      */ import gnu.trove.map.TLongIntMap;
/*      */ import gnu.trove.map.TLongLongMap;
/*      */ import gnu.trove.map.TLongObjectMap;
/*      */ import gnu.trove.map.TLongShortMap;
/*      */ import gnu.trove.map.TObjectByteMap;
/*      */ import gnu.trove.map.TObjectCharMap;
/*      */ import gnu.trove.map.TObjectDoubleMap;
/*      */ import gnu.trove.map.TObjectLongMap;
/*      */ import gnu.trove.map.TObjectShortMap;
/*      */ import gnu.trove.map.TShortCharMap;
/*      */ import gnu.trove.map.TShortDoubleMap;
/*      */ import gnu.trove.map.TShortLongMap;
/*      */ import gnu.trove.map.TShortShortMap;
/*      */ import gnu.trove.set.TByteSet;
/*      */ import gnu.trove.set.TCharSet;
/*      */ import gnu.trove.set.TDoubleSet;
/*      */ import gnu.trove.set.TFloatSet;
/*      */ import gnu.trove.set.TIntSet;
/*      */ import gnu.trove.set.TLongSet;
/*      */ import gnu.trove.set.TShortSet;
/*      */ import java.util.RandomAccess;
/*      */ 
/*      */ public class TCollections
/*      */ {
/*      */   public static TDoubleCollection unmodifiableCollection(TDoubleCollection c)
/*      */   {
/*   72 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCollection(c);
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
/*      */   public static TFloatCollection unmodifiableCollection(TFloatCollection c)
/*      */   {
/*   98 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableFloatCollection(c);
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
/*      */   public static TIntCollection unmodifiableCollection(TIntCollection c)
/*      */   {
/*  124 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableIntCollection(c);
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
/*      */   public static TLongCollection unmodifiableCollection(TLongCollection c)
/*      */   {
/*  150 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableLongCollection(c);
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
/*      */   public static TByteCollection unmodifiableCollection(TByteCollection c)
/*      */   {
/*  176 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableByteCollection(c);
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
/*      */   public static TShortCollection unmodifiableCollection(TShortCollection c)
/*      */   {
/*  202 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableShortCollection(c);
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
/*      */   public static TCharCollection unmodifiableCollection(TCharCollection c)
/*      */   {
/*  228 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableCharCollection(c);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TDoubleSet unmodifiableSet(TDoubleSet s)
/*      */   {
/*  247 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableDoubleSet(s);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TFloatSet unmodifiableSet(TFloatSet s)
/*      */   {
/*  264 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableFloatSet(s);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TIntSet unmodifiableSet(TIntSet s)
/*      */   {
/*  281 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableIntSet(s);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TLongSet unmodifiableSet(TLongSet s)
/*      */   {
/*  298 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableLongSet(s);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TByteSet unmodifiableSet(TByteSet s)
/*      */   {
/*  315 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableByteSet(s);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TShortSet unmodifiableSet(TShortSet s)
/*      */   {
/*  332 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableShortSet(s);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TCharSet unmodifiableSet(TCharSet s)
/*      */   {
/*  349 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableCharSet(s);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TDoubleList unmodifiableList(TDoubleList list)
/*      */   {
/*  369 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessDoubleList(list) : new gnu.trove.impl.unmodifiable.TUnmodifiableDoubleList(list);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TFloatList unmodifiableList(TFloatList list)
/*      */   {
/*  390 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessFloatList(list) : new gnu.trove.impl.unmodifiable.TUnmodifiableFloatList(list);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TIntList unmodifiableList(TIntList list)
/*      */   {
/*  411 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessIntList(list) : new gnu.trove.impl.unmodifiable.TUnmodifiableIntList(list);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TLongList unmodifiableList(TLongList list)
/*      */   {
/*  432 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessLongList(list) : new gnu.trove.impl.unmodifiable.TUnmodifiableLongList(list);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TByteList unmodifiableList(TByteList list)
/*      */   {
/*  453 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessByteList(list) : new gnu.trove.impl.unmodifiable.TUnmodifiableByteList(list);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TShortList unmodifiableList(TShortList list)
/*      */   {
/*  474 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessShortList(list) : new gnu.trove.impl.unmodifiable.TUnmodifiableShortList(list);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TCharList unmodifiableList(TCharList list)
/*      */   {
/*  495 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.unmodifiable.TUnmodifiableRandomAccessCharList(list) : new gnu.trove.impl.unmodifiable.TUnmodifiableCharList(list);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TDoubleDoubleMap unmodifiableMap(TDoubleDoubleMap m)
/*      */   {
/*  516 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableDoubleDoubleMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TDoubleFloatMap unmodifiableMap(TDoubleFloatMap m)
/*      */   {
/*  534 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableDoubleFloatMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TDoubleIntMap unmodifiableMap(TDoubleIntMap m)
/*      */   {
/*  552 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableDoubleIntMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TDoubleLongMap unmodifiableMap(TDoubleLongMap m)
/*      */   {
/*  570 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableDoubleLongMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TDoubleByteMap unmodifiableMap(TDoubleByteMap m)
/*      */   {
/*  588 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableDoubleByteMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TDoubleShortMap unmodifiableMap(TDoubleShortMap m)
/*      */   {
/*  606 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableDoubleShortMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TDoubleCharMap unmodifiableMap(TDoubleCharMap m)
/*      */   {
/*  624 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableDoubleCharMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TFloatDoubleMap unmodifiableMap(TFloatDoubleMap m)
/*      */   {
/*  642 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableFloatDoubleMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TFloatFloatMap unmodifiableMap(TFloatFloatMap m)
/*      */   {
/*  660 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableFloatFloatMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TFloatIntMap unmodifiableMap(TFloatIntMap m)
/*      */   {
/*  678 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableFloatIntMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TFloatLongMap unmodifiableMap(TFloatLongMap m)
/*      */   {
/*  696 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableFloatLongMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TFloatByteMap unmodifiableMap(TFloatByteMap m)
/*      */   {
/*  714 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableFloatByteMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TFloatShortMap unmodifiableMap(TFloatShortMap m)
/*      */   {
/*  732 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableFloatShortMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TFloatCharMap unmodifiableMap(TFloatCharMap m)
/*      */   {
/*  750 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableFloatCharMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static gnu.trove.map.TIntDoubleMap unmodifiableMap(gnu.trove.map.TIntDoubleMap m)
/*      */   {
/*  768 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableIntDoubleMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TIntFloatMap unmodifiableMap(TIntFloatMap m)
/*      */   {
/*  786 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableIntFloatMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TIntIntMap unmodifiableMap(TIntIntMap m)
/*      */   {
/*  804 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableIntIntMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static gnu.trove.map.TIntLongMap unmodifiableMap(gnu.trove.map.TIntLongMap m)
/*      */   {
/*  822 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableIntLongMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TIntByteMap unmodifiableMap(TIntByteMap m)
/*      */   {
/*  840 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableIntByteMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static gnu.trove.map.TIntShortMap unmodifiableMap(gnu.trove.map.TIntShortMap m)
/*      */   {
/*  858 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableIntShortMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TIntCharMap unmodifiableMap(TIntCharMap m)
/*      */   {
/*  876 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableIntCharMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static gnu.trove.map.TLongDoubleMap unmodifiableMap(gnu.trove.map.TLongDoubleMap m)
/*      */   {
/*  894 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableLongDoubleMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TLongFloatMap unmodifiableMap(TLongFloatMap m)
/*      */   {
/*  912 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableLongFloatMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TLongIntMap unmodifiableMap(TLongIntMap m)
/*      */   {
/*  930 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableLongIntMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TLongLongMap unmodifiableMap(TLongLongMap m)
/*      */   {
/*  948 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableLongLongMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TLongByteMap unmodifiableMap(TLongByteMap m)
/*      */   {
/*  966 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableLongByteMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TLongShortMap unmodifiableMap(TLongShortMap m)
/*      */   {
/*  984 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableLongShortMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TLongCharMap unmodifiableMap(TLongCharMap m)
/*      */   {
/* 1002 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableLongCharMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TByteDoubleMap unmodifiableMap(TByteDoubleMap m)
/*      */   {
/* 1020 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableByteDoubleMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TByteFloatMap unmodifiableMap(TByteFloatMap m)
/*      */   {
/* 1038 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableByteFloatMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TByteIntMap unmodifiableMap(TByteIntMap m)
/*      */   {
/* 1056 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableByteIntMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TByteLongMap unmodifiableMap(TByteLongMap m)
/*      */   {
/* 1074 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableByteLongMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TByteByteMap unmodifiableMap(TByteByteMap m)
/*      */   {
/* 1092 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableByteByteMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TByteShortMap unmodifiableMap(TByteShortMap m)
/*      */   {
/* 1110 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableByteShortMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TByteCharMap unmodifiableMap(TByteCharMap m)
/*      */   {
/* 1128 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableByteCharMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TShortDoubleMap unmodifiableMap(TShortDoubleMap m)
/*      */   {
/* 1146 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableShortDoubleMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static gnu.trove.map.TShortFloatMap unmodifiableMap(gnu.trove.map.TShortFloatMap m)
/*      */   {
/* 1164 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableShortFloatMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static gnu.trove.map.TShortIntMap unmodifiableMap(gnu.trove.map.TShortIntMap m)
/*      */   {
/* 1182 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableShortIntMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TShortLongMap unmodifiableMap(TShortLongMap m)
/*      */   {
/* 1200 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableShortLongMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static gnu.trove.map.TShortByteMap unmodifiableMap(gnu.trove.map.TShortByteMap m)
/*      */   {
/* 1218 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableShortByteMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TShortShortMap unmodifiableMap(TShortShortMap m)
/*      */   {
/* 1236 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableShortShortMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TShortCharMap unmodifiableMap(TShortCharMap m)
/*      */   {
/* 1254 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableShortCharMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TCharDoubleMap unmodifiableMap(TCharDoubleMap m)
/*      */   {
/* 1272 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableCharDoubleMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static gnu.trove.map.TCharFloatMap unmodifiableMap(gnu.trove.map.TCharFloatMap m)
/*      */   {
/* 1290 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableCharFloatMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static gnu.trove.map.TCharIntMap unmodifiableMap(gnu.trove.map.TCharIntMap m)
/*      */   {
/* 1308 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableCharIntMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TCharLongMap unmodifiableMap(TCharLongMap m)
/*      */   {
/* 1326 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableCharLongMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static gnu.trove.map.TCharByteMap unmodifiableMap(gnu.trove.map.TCharByteMap m)
/*      */   {
/* 1344 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableCharByteMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TCharShortMap unmodifiableMap(TCharShortMap m)
/*      */   {
/* 1362 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableCharShortMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TCharCharMap unmodifiableMap(TCharCharMap m)
/*      */   {
/* 1380 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableCharCharMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <V> gnu.trove.map.TDoubleObjectMap<V> unmodifiableMap(gnu.trove.map.TDoubleObjectMap<V> m)
/*      */   {
/* 1399 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableDoubleObjectMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <V> TFloatObjectMap<V> unmodifiableMap(TFloatObjectMap<V> m)
/*      */   {
/* 1417 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableFloatObjectMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <V> TIntObjectMap<V> unmodifiableMap(TIntObjectMap<V> m)
/*      */   {
/* 1435 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableIntObjectMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <V> TLongObjectMap<V> unmodifiableMap(TLongObjectMap<V> m)
/*      */   {
/* 1453 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableLongObjectMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <V> TByteObjectMap<V> unmodifiableMap(TByteObjectMap<V> m)
/*      */   {
/* 1471 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableByteObjectMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <V> gnu.trove.map.TShortObjectMap<V> unmodifiableMap(gnu.trove.map.TShortObjectMap<V> m)
/*      */   {
/* 1489 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableShortObjectMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <V> TCharObjectMap<V> unmodifiableMap(TCharObjectMap<V> m)
/*      */   {
/* 1507 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableCharObjectMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <K> TObjectDoubleMap<K> unmodifiableMap(TObjectDoubleMap<K> m)
/*      */   {
/* 1526 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableObjectDoubleMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <K> gnu.trove.map.TObjectFloatMap<K> unmodifiableMap(gnu.trove.map.TObjectFloatMap<K> m)
/*      */   {
/* 1544 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableObjectFloatMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <K> gnu.trove.map.TObjectIntMap<K> unmodifiableMap(gnu.trove.map.TObjectIntMap<K> m)
/*      */   {
/* 1562 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableObjectIntMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <K> TObjectLongMap<K> unmodifiableMap(TObjectLongMap<K> m)
/*      */   {
/* 1580 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableObjectLongMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <K> TObjectByteMap<K> unmodifiableMap(TObjectByteMap<K> m)
/*      */   {
/* 1598 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableObjectByteMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <K> TObjectShortMap<K> unmodifiableMap(TObjectShortMap<K> m)
/*      */   {
/* 1616 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableObjectShortMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static <K> TObjectCharMap<K> unmodifiableMap(TObjectCharMap<K> m)
/*      */   {
/* 1634 */     return new gnu.trove.impl.unmodifiable.TUnmodifiableObjectCharMap(m);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static TDoubleCollection synchronizedCollection(TDoubleCollection c)
/*      */   {
/* 1674 */     return new gnu.trove.impl.sync.TSynchronizedDoubleCollection(c);
/*      */   }
/*      */   
/*      */   static TDoubleCollection synchronizedCollection(TDoubleCollection c, Object mutex) {
/* 1678 */     return new gnu.trove.impl.sync.TSynchronizedDoubleCollection(c, mutex);
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
/*      */ 
/*      */ 
/*      */   public static TFloatCollection synchronizedCollection(TFloatCollection c)
/*      */   {
/* 1713 */     return new gnu.trove.impl.sync.TSynchronizedFloatCollection(c);
/*      */   }
/*      */   
/*      */   static TFloatCollection synchronizedCollection(TFloatCollection c, Object mutex) {
/* 1717 */     return new gnu.trove.impl.sync.TSynchronizedFloatCollection(c, mutex);
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
/*      */ 
/*      */ 
/*      */   public static TIntCollection synchronizedCollection(TIntCollection c)
/*      */   {
/* 1752 */     return new gnu.trove.impl.sync.TSynchronizedIntCollection(c);
/*      */   }
/*      */   
/*      */   static TIntCollection synchronizedCollection(TIntCollection c, Object mutex) {
/* 1756 */     return new gnu.trove.impl.sync.TSynchronizedIntCollection(c, mutex);
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
/*      */ 
/*      */ 
/*      */   public static TLongCollection synchronizedCollection(TLongCollection c)
/*      */   {
/* 1791 */     return new gnu.trove.impl.sync.TSynchronizedLongCollection(c);
/*      */   }
/*      */   
/*      */   static TLongCollection synchronizedCollection(TLongCollection c, Object mutex) {
/* 1795 */     return new gnu.trove.impl.sync.TSynchronizedLongCollection(c, mutex);
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
/*      */ 
/*      */ 
/*      */   public static TByteCollection synchronizedCollection(TByteCollection c)
/*      */   {
/* 1830 */     return new gnu.trove.impl.sync.TSynchronizedByteCollection(c);
/*      */   }
/*      */   
/*      */   static TByteCollection synchronizedCollection(TByteCollection c, Object mutex) {
/* 1834 */     return new gnu.trove.impl.sync.TSynchronizedByteCollection(c, mutex);
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
/*      */ 
/*      */ 
/*      */   public static TShortCollection synchronizedCollection(TShortCollection c)
/*      */   {
/* 1869 */     return new gnu.trove.impl.sync.TSynchronizedShortCollection(c);
/*      */   }
/*      */   
/*      */   static TShortCollection synchronizedCollection(TShortCollection c, Object mutex) {
/* 1873 */     return new gnu.trove.impl.sync.TSynchronizedShortCollection(c, mutex);
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
/*      */ 
/*      */ 
/*      */   public static TCharCollection synchronizedCollection(TCharCollection c)
/*      */   {
/* 1908 */     return new gnu.trove.impl.sync.TSynchronizedCharCollection(c);
/*      */   }
/*      */   
/*      */   static TCharCollection synchronizedCollection(TCharCollection c, Object mutex) {
/* 1912 */     return new gnu.trove.impl.sync.TSynchronizedCharCollection(c, mutex);
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
/*      */   public static TDoubleSet synchronizedSet(TDoubleSet s)
/*      */   {
/* 1942 */     return new gnu.trove.impl.sync.TSynchronizedDoubleSet(s);
/*      */   }
/*      */   
/*      */   static TDoubleSet synchronizedSet(TDoubleSet s, Object mutex) {
/* 1946 */     return new gnu.trove.impl.sync.TSynchronizedDoubleSet(s, mutex);
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
/*      */   public static TFloatSet synchronizedSet(TFloatSet s)
/*      */   {
/* 1975 */     return new gnu.trove.impl.sync.TSynchronizedFloatSet(s);
/*      */   }
/*      */   
/*      */   static TFloatSet synchronizedSet(TFloatSet s, Object mutex) {
/* 1979 */     return new gnu.trove.impl.sync.TSynchronizedFloatSet(s, mutex);
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
/*      */   public static TIntSet synchronizedSet(TIntSet s)
/*      */   {
/* 2008 */     return new gnu.trove.impl.sync.TSynchronizedIntSet(s);
/*      */   }
/*      */   
/*      */   static TIntSet synchronizedSet(TIntSet s, Object mutex) {
/* 2012 */     return new gnu.trove.impl.sync.TSynchronizedIntSet(s, mutex);
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
/*      */   public static TLongSet synchronizedSet(TLongSet s)
/*      */   {
/* 2041 */     return new gnu.trove.impl.sync.TSynchronizedLongSet(s);
/*      */   }
/*      */   
/*      */   static TLongSet synchronizedSet(TLongSet s, Object mutex) {
/* 2045 */     return new gnu.trove.impl.sync.TSynchronizedLongSet(s, mutex);
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
/*      */   public static TByteSet synchronizedSet(TByteSet s)
/*      */   {
/* 2074 */     return new gnu.trove.impl.sync.TSynchronizedByteSet(s);
/*      */   }
/*      */   
/*      */   static TByteSet synchronizedSet(TByteSet s, Object mutex) {
/* 2078 */     return new gnu.trove.impl.sync.TSynchronizedByteSet(s, mutex);
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
/*      */   public static TShortSet synchronizedSet(TShortSet s)
/*      */   {
/* 2107 */     return new gnu.trove.impl.sync.TSynchronizedShortSet(s);
/*      */   }
/*      */   
/*      */   static TShortSet synchronizedSet(TShortSet s, Object mutex) {
/* 2111 */     return new gnu.trove.impl.sync.TSynchronizedShortSet(s, mutex);
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
/*      */   public static TCharSet synchronizedSet(TCharSet s)
/*      */   {
/* 2140 */     return new gnu.trove.impl.sync.TSynchronizedCharSet(s);
/*      */   }
/*      */   
/*      */   static TCharSet synchronizedSet(TCharSet s, Object mutex) {
/* 2144 */     return new gnu.trove.impl.sync.TSynchronizedCharSet(s, mutex);
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
/*      */   public static TDoubleList synchronizedList(TDoubleList list)
/*      */   {
/* 2174 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.sync.TSynchronizedRandomAccessDoubleList(list) : new gnu.trove.impl.sync.TSynchronizedDoubleList(list);
/*      */   }
/*      */   
/*      */ 
/*      */   static TDoubleList synchronizedList(TDoubleList list, Object mutex)
/*      */   {
/* 2180 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.sync.TSynchronizedRandomAccessDoubleList(list, mutex) : new gnu.trove.impl.sync.TSynchronizedDoubleList(list, mutex);
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
/*      */   public static TFloatList synchronizedList(TFloatList list)
/*      */   {
/* 2211 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.sync.TSynchronizedRandomAccessFloatList(list) : new gnu.trove.impl.sync.TSynchronizedFloatList(list);
/*      */   }
/*      */   
/*      */ 
/*      */   static TFloatList synchronizedList(TFloatList list, Object mutex)
/*      */   {
/* 2217 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.sync.TSynchronizedRandomAccessFloatList(list, mutex) : new gnu.trove.impl.sync.TSynchronizedFloatList(list, mutex);
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
/*      */   public static TIntList synchronizedList(TIntList list)
/*      */   {
/* 2248 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.sync.TSynchronizedRandomAccessIntList(list) : new gnu.trove.impl.sync.TSynchronizedIntList(list);
/*      */   }
/*      */   
/*      */ 
/*      */   static TIntList synchronizedList(TIntList list, Object mutex)
/*      */   {
/* 2254 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.sync.TSynchronizedRandomAccessIntList(list, mutex) : new gnu.trove.impl.sync.TSynchronizedIntList(list, mutex);
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
/*      */   public static TLongList synchronizedList(TLongList list)
/*      */   {
/* 2285 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.sync.TSynchronizedRandomAccessLongList(list) : new gnu.trove.impl.sync.TSynchronizedLongList(list);
/*      */   }
/*      */   
/*      */ 
/*      */   static TLongList synchronizedList(TLongList list, Object mutex)
/*      */   {
/* 2291 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.sync.TSynchronizedRandomAccessLongList(list, mutex) : new gnu.trove.impl.sync.TSynchronizedLongList(list, mutex);
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
/*      */   public static TByteList synchronizedList(TByteList list)
/*      */   {
/* 2322 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.sync.TSynchronizedRandomAccessByteList(list) : new gnu.trove.impl.sync.TSynchronizedByteList(list);
/*      */   }
/*      */   
/*      */ 
/*      */   static TByteList synchronizedList(TByteList list, Object mutex)
/*      */   {
/* 2328 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.sync.TSynchronizedRandomAccessByteList(list, mutex) : new gnu.trove.impl.sync.TSynchronizedByteList(list, mutex);
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
/*      */   public static TShortList synchronizedList(TShortList list)
/*      */   {
/* 2359 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.sync.TSynchronizedRandomAccessShortList(list) : new gnu.trove.impl.sync.TSynchronizedShortList(list);
/*      */   }
/*      */   
/*      */ 
/*      */   static TShortList synchronizedList(TShortList list, Object mutex)
/*      */   {
/* 2365 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.sync.TSynchronizedRandomAccessShortList(list, mutex) : new gnu.trove.impl.sync.TSynchronizedShortList(list, mutex);
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
/*      */   public static TCharList synchronizedList(TCharList list)
/*      */   {
/* 2396 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.sync.TSynchronizedRandomAccessCharList(list) : new gnu.trove.impl.sync.TSynchronizedCharList(list);
/*      */   }
/*      */   
/*      */ 
/*      */   static TCharList synchronizedList(TCharList list, Object mutex)
/*      */   {
/* 2402 */     return (list instanceof RandomAccess) ? new gnu.trove.impl.sync.TSynchronizedRandomAccessCharList(list, mutex) : new gnu.trove.impl.sync.TSynchronizedCharList(list, mutex);
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
/*      */ 
/*      */   public static TDoubleDoubleMap synchronizedMap(TDoubleDoubleMap m)
/*      */   {
/* 2436 */     return new gnu.trove.impl.sync.TSynchronizedDoubleDoubleMap(m);
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
/*      */   public static TDoubleFloatMap synchronizedMap(TDoubleFloatMap m)
/*      */   {
/* 2467 */     return new gnu.trove.impl.sync.TSynchronizedDoubleFloatMap(m);
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
/*      */   public static TDoubleIntMap synchronizedMap(TDoubleIntMap m)
/*      */   {
/* 2498 */     return new gnu.trove.impl.sync.TSynchronizedDoubleIntMap(m);
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
/*      */   public static TDoubleLongMap synchronizedMap(TDoubleLongMap m)
/*      */   {
/* 2529 */     return new gnu.trove.impl.sync.TSynchronizedDoubleLongMap(m);
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
/*      */   public static TDoubleByteMap synchronizedMap(TDoubleByteMap m)
/*      */   {
/* 2560 */     return new gnu.trove.impl.sync.TSynchronizedDoubleByteMap(m);
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
/*      */   public static TDoubleShortMap synchronizedMap(TDoubleShortMap m)
/*      */   {
/* 2591 */     return new gnu.trove.impl.sync.TSynchronizedDoubleShortMap(m);
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
/*      */   public static TDoubleCharMap synchronizedMap(TDoubleCharMap m)
/*      */   {
/* 2622 */     return new gnu.trove.impl.sync.TSynchronizedDoubleCharMap(m);
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
/*      */   public static TFloatDoubleMap synchronizedMap(TFloatDoubleMap m)
/*      */   {
/* 2653 */     return new gnu.trove.impl.sync.TSynchronizedFloatDoubleMap(m);
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
/*      */   public static TFloatFloatMap synchronizedMap(TFloatFloatMap m)
/*      */   {
/* 2684 */     return new gnu.trove.impl.sync.TSynchronizedFloatFloatMap(m);
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
/*      */   public static TFloatIntMap synchronizedMap(TFloatIntMap m)
/*      */   {
/* 2715 */     return new gnu.trove.impl.sync.TSynchronizedFloatIntMap(m);
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
/*      */   public static TFloatLongMap synchronizedMap(TFloatLongMap m)
/*      */   {
/* 2746 */     return new gnu.trove.impl.sync.TSynchronizedFloatLongMap(m);
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
/*      */   public static TFloatByteMap synchronizedMap(TFloatByteMap m)
/*      */   {
/* 2777 */     return new gnu.trove.impl.sync.TSynchronizedFloatByteMap(m);
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
/*      */   public static TFloatShortMap synchronizedMap(TFloatShortMap m)
/*      */   {
/* 2808 */     return new gnu.trove.impl.sync.TSynchronizedFloatShortMap(m);
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
/*      */   public static TFloatCharMap synchronizedMap(TFloatCharMap m)
/*      */   {
/* 2839 */     return new gnu.trove.impl.sync.TSynchronizedFloatCharMap(m);
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
/*      */   public static gnu.trove.map.TIntDoubleMap synchronizedMap(gnu.trove.map.TIntDoubleMap m)
/*      */   {
/* 2870 */     return new gnu.trove.impl.sync.TSynchronizedIntDoubleMap(m);
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
/*      */   public static TIntFloatMap synchronizedMap(TIntFloatMap m)
/*      */   {
/* 2901 */     return new gnu.trove.impl.sync.TSynchronizedIntFloatMap(m);
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
/*      */   public static TIntIntMap synchronizedMap(TIntIntMap m)
/*      */   {
/* 2932 */     return new gnu.trove.impl.sync.TSynchronizedIntIntMap(m);
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
/*      */   public static gnu.trove.map.TIntLongMap synchronizedMap(gnu.trove.map.TIntLongMap m)
/*      */   {
/* 2963 */     return new gnu.trove.impl.sync.TSynchronizedIntLongMap(m);
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
/*      */   public static TIntByteMap synchronizedMap(TIntByteMap m)
/*      */   {
/* 2994 */     return new gnu.trove.impl.sync.TSynchronizedIntByteMap(m);
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
/*      */   public static gnu.trove.map.TIntShortMap synchronizedMap(gnu.trove.map.TIntShortMap m)
/*      */   {
/* 3025 */     return new gnu.trove.impl.sync.TSynchronizedIntShortMap(m);
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
/*      */   public static TIntCharMap synchronizedMap(TIntCharMap m)
/*      */   {
/* 3056 */     return new gnu.trove.impl.sync.TSynchronizedIntCharMap(m);
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
/*      */   public static gnu.trove.map.TLongDoubleMap synchronizedMap(gnu.trove.map.TLongDoubleMap m)
/*      */   {
/* 3087 */     return new gnu.trove.impl.sync.TSynchronizedLongDoubleMap(m);
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
/*      */   public static TLongFloatMap synchronizedMap(TLongFloatMap m)
/*      */   {
/* 3118 */     return new gnu.trove.impl.sync.TSynchronizedLongFloatMap(m);
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
/*      */   public static TLongIntMap synchronizedMap(TLongIntMap m)
/*      */   {
/* 3149 */     return new gnu.trove.impl.sync.TSynchronizedLongIntMap(m);
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
/*      */   public static TLongLongMap synchronizedMap(TLongLongMap m)
/*      */   {
/* 3180 */     return new gnu.trove.impl.sync.TSynchronizedLongLongMap(m);
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
/*      */   public static TLongByteMap synchronizedMap(TLongByteMap m)
/*      */   {
/* 3211 */     return new gnu.trove.impl.sync.TSynchronizedLongByteMap(m);
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
/*      */   public static TLongShortMap synchronizedMap(TLongShortMap m)
/*      */   {
/* 3242 */     return new gnu.trove.impl.sync.TSynchronizedLongShortMap(m);
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
/*      */   public static TLongCharMap synchronizedMap(TLongCharMap m)
/*      */   {
/* 3273 */     return new gnu.trove.impl.sync.TSynchronizedLongCharMap(m);
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
/*      */   public static TByteDoubleMap synchronizedMap(TByteDoubleMap m)
/*      */   {
/* 3304 */     return new gnu.trove.impl.sync.TSynchronizedByteDoubleMap(m);
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
/*      */   public static TByteFloatMap synchronizedMap(TByteFloatMap m)
/*      */   {
/* 3335 */     return new gnu.trove.impl.sync.TSynchronizedByteFloatMap(m);
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
/*      */   public static TByteIntMap synchronizedMap(TByteIntMap m)
/*      */   {
/* 3366 */     return new gnu.trove.impl.sync.TSynchronizedByteIntMap(m);
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
/*      */   public static TByteLongMap synchronizedMap(TByteLongMap m)
/*      */   {
/* 3397 */     return new gnu.trove.impl.sync.TSynchronizedByteLongMap(m);
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
/*      */   public static TByteByteMap synchronizedMap(TByteByteMap m)
/*      */   {
/* 3428 */     return new gnu.trove.impl.sync.TSynchronizedByteByteMap(m);
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
/*      */   public static TByteShortMap synchronizedMap(TByteShortMap m)
/*      */   {
/* 3459 */     return new gnu.trove.impl.sync.TSynchronizedByteShortMap(m);
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
/*      */   public static TByteCharMap synchronizedMap(TByteCharMap m)
/*      */   {
/* 3490 */     return new gnu.trove.impl.sync.TSynchronizedByteCharMap(m);
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
/*      */   public static TShortDoubleMap synchronizedMap(TShortDoubleMap m)
/*      */   {
/* 3521 */     return new gnu.trove.impl.sync.TSynchronizedShortDoubleMap(m);
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
/*      */   public static gnu.trove.map.TShortFloatMap synchronizedMap(gnu.trove.map.TShortFloatMap m)
/*      */   {
/* 3552 */     return new gnu.trove.impl.sync.TSynchronizedShortFloatMap(m);
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
/*      */   public static gnu.trove.map.TShortIntMap synchronizedMap(gnu.trove.map.TShortIntMap m)
/*      */   {
/* 3583 */     return new gnu.trove.impl.sync.TSynchronizedShortIntMap(m);
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
/*      */   public static TShortLongMap synchronizedMap(TShortLongMap m)
/*      */   {
/* 3614 */     return new gnu.trove.impl.sync.TSynchronizedShortLongMap(m);
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
/*      */   public static gnu.trove.map.TShortByteMap synchronizedMap(gnu.trove.map.TShortByteMap m)
/*      */   {
/* 3645 */     return new gnu.trove.impl.sync.TSynchronizedShortByteMap(m);
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
/*      */   public static TShortShortMap synchronizedMap(TShortShortMap m)
/*      */   {
/* 3676 */     return new gnu.trove.impl.sync.TSynchronizedShortShortMap(m);
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
/*      */   public static TShortCharMap synchronizedMap(TShortCharMap m)
/*      */   {
/* 3707 */     return new gnu.trove.impl.sync.TSynchronizedShortCharMap(m);
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
/*      */   public static TCharDoubleMap synchronizedMap(TCharDoubleMap m)
/*      */   {
/* 3738 */     return new gnu.trove.impl.sync.TSynchronizedCharDoubleMap(m);
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
/*      */   public static gnu.trove.map.TCharFloatMap synchronizedMap(gnu.trove.map.TCharFloatMap m)
/*      */   {
/* 3769 */     return new gnu.trove.impl.sync.TSynchronizedCharFloatMap(m);
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
/*      */   public static gnu.trove.map.TCharIntMap synchronizedMap(gnu.trove.map.TCharIntMap m)
/*      */   {
/* 3800 */     return new gnu.trove.impl.sync.TSynchronizedCharIntMap(m);
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
/*      */   public static TCharLongMap synchronizedMap(TCharLongMap m)
/*      */   {
/* 3831 */     return new gnu.trove.impl.sync.TSynchronizedCharLongMap(m);
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
/*      */   public static gnu.trove.map.TCharByteMap synchronizedMap(gnu.trove.map.TCharByteMap m)
/*      */   {
/* 3862 */     return new gnu.trove.impl.sync.TSynchronizedCharByteMap(m);
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
/*      */   public static TCharShortMap synchronizedMap(TCharShortMap m)
/*      */   {
/* 3893 */     return new gnu.trove.impl.sync.TSynchronizedCharShortMap(m);
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
/*      */   public static TCharCharMap synchronizedMap(TCharCharMap m)
/*      */   {
/* 3924 */     return new gnu.trove.impl.sync.TSynchronizedCharCharMap(m);
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
/*      */   public static <V> gnu.trove.map.TDoubleObjectMap<V> synchronizedMap(gnu.trove.map.TDoubleObjectMap<V> m)
/*      */   {
/* 3956 */     return new gnu.trove.impl.sync.TSynchronizedDoubleObjectMap(m);
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
/*      */   public static <V> TFloatObjectMap<V> synchronizedMap(TFloatObjectMap<V> m)
/*      */   {
/* 3987 */     return new gnu.trove.impl.sync.TSynchronizedFloatObjectMap(m);
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
/*      */   public static <V> TIntObjectMap<V> synchronizedMap(TIntObjectMap<V> m)
/*      */   {
/* 4018 */     return new gnu.trove.impl.sync.TSynchronizedIntObjectMap(m);
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
/*      */   public static <V> TLongObjectMap<V> synchronizedMap(TLongObjectMap<V> m)
/*      */   {
/* 4049 */     return new gnu.trove.impl.sync.TSynchronizedLongObjectMap(m);
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
/*      */   public static <V> TByteObjectMap<V> synchronizedMap(TByteObjectMap<V> m)
/*      */   {
/* 4080 */     return new gnu.trove.impl.sync.TSynchronizedByteObjectMap(m);
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
/*      */   public static <V> gnu.trove.map.TShortObjectMap<V> synchronizedMap(gnu.trove.map.TShortObjectMap<V> m)
/*      */   {
/* 4111 */     return new gnu.trove.impl.sync.TSynchronizedShortObjectMap(m);
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
/*      */   public static <V> TCharObjectMap<V> synchronizedMap(TCharObjectMap<V> m)
/*      */   {
/* 4142 */     return new gnu.trove.impl.sync.TSynchronizedCharObjectMap(m);
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
/*      */   public static <K> TObjectDoubleMap<K> synchronizedMap(TObjectDoubleMap<K> m)
/*      */   {
/* 4174 */     return new gnu.trove.impl.sync.TSynchronizedObjectDoubleMap(m);
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
/*      */   public static <K> gnu.trove.map.TObjectFloatMap<K> synchronizedMap(gnu.trove.map.TObjectFloatMap<K> m)
/*      */   {
/* 4205 */     return new gnu.trove.impl.sync.TSynchronizedObjectFloatMap(m);
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
/*      */   public static <K> gnu.trove.map.TObjectIntMap<K> synchronizedMap(gnu.trove.map.TObjectIntMap<K> m)
/*      */   {
/* 4236 */     return new gnu.trove.impl.sync.TSynchronizedObjectIntMap(m);
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
/*      */   public static <K> TObjectLongMap<K> synchronizedMap(TObjectLongMap<K> m)
/*      */   {
/* 4267 */     return new gnu.trove.impl.sync.TSynchronizedObjectLongMap(m);
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
/*      */   public static <K> TObjectByteMap<K> synchronizedMap(TObjectByteMap<K> m)
/*      */   {
/* 4298 */     return new gnu.trove.impl.sync.TSynchronizedObjectByteMap(m);
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
/*      */   public static <K> TObjectShortMap<K> synchronizedMap(TObjectShortMap<K> m)
/*      */   {
/* 4329 */     return new gnu.trove.impl.sync.TSynchronizedObjectShortMap(m);
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
/*      */   public static <K> TObjectCharMap<K> synchronizedMap(TObjectCharMap<K> m)
/*      */   {
/* 4360 */     return new gnu.trove.impl.sync.TSynchronizedObjectCharMap(m);
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\gnu\trove\TCollections.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */