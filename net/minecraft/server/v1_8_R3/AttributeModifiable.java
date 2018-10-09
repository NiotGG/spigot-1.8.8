/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public class AttributeModifiable implements AttributeInstance
/*     */ {
/*     */   private final AttributeMapBase a;
/*     */   private final IAttribute b;
/*  15 */   private final Map<Integer, Set<AttributeModifier>> c = Maps.newHashMap();
/*  16 */   private final Map<String, Set<AttributeModifier>> d = Maps.newHashMap();
/*  17 */   private final Map<UUID, AttributeModifier> e = Maps.newHashMap();
/*     */   private double f;
/*  19 */   private boolean g = true;
/*     */   private double h;
/*     */   
/*     */   public AttributeModifiable(AttributeMapBase paramAttributeMapBase, IAttribute paramIAttribute) {
/*  23 */     this.a = paramAttributeMapBase;
/*  24 */     this.b = paramIAttribute;
/*  25 */     this.f = paramIAttribute.b();
/*     */     
/*  27 */     for (int i = 0; i < 3; i++) {
/*  28 */       this.c.put(Integer.valueOf(i), Sets.newHashSet());
/*     */     }
/*     */   }
/*     */   
/*     */   public IAttribute getAttribute()
/*     */   {
/*  34 */     return this.b;
/*     */   }
/*     */   
/*     */   public double b()
/*     */   {
/*  39 */     return this.f;
/*     */   }
/*     */   
/*     */   public void setValue(double paramDouble)
/*     */   {
/*  44 */     if (paramDouble == b()) {
/*  45 */       return;
/*     */     }
/*  47 */     this.f = paramDouble;
/*  48 */     f();
/*     */   }
/*     */   
/*     */   public Collection<AttributeModifier> a(int paramInt)
/*     */   {
/*  53 */     return (Collection)this.c.get(Integer.valueOf(paramInt));
/*     */   }
/*     */   
/*     */   public Collection<AttributeModifier> c()
/*     */   {
/*  58 */     java.util.HashSet localHashSet = Sets.newHashSet();
/*     */     
/*  60 */     for (int i = 0; i < 3; i++) {
/*  61 */       localHashSet.addAll(a(i));
/*     */     }
/*     */     
/*  64 */     return localHashSet;
/*     */   }
/*     */   
/*     */   public AttributeModifier a(UUID paramUUID)
/*     */   {
/*  69 */     return (AttributeModifier)this.e.get(paramUUID);
/*     */   }
/*     */   
/*     */   public boolean a(AttributeModifier paramAttributeModifier)
/*     */   {
/*  74 */     return this.e.get(paramAttributeModifier.a()) != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void b(AttributeModifier paramAttributeModifier)
/*     */   {
/*  86 */     if (a(paramAttributeModifier.a()) != null) {
/*  87 */       throw new IllegalArgumentException("Modifier is already applied on this attribute!");
/*     */     }
/*     */     
/*  90 */     Object localObject = (Set)this.d.get(paramAttributeModifier.b());
/*     */     
/*  92 */     if (localObject == null) {
/*  93 */       localObject = Sets.newHashSet();
/*  94 */       this.d.put(paramAttributeModifier.b(), localObject);
/*     */     }
/*     */     
/*  97 */     ((Set)this.c.get(Integer.valueOf(paramAttributeModifier.c()))).add(paramAttributeModifier);
/*  98 */     ((Set)localObject).add(paramAttributeModifier);
/*  99 */     this.e.put(paramAttributeModifier.a(), paramAttributeModifier);
/*     */     
/* 101 */     f();
/*     */   }
/*     */   
/*     */   protected void f() {
/* 105 */     this.g = true;
/* 106 */     this.a.a(this);
/*     */   }
/*     */   
/*     */   public void c(AttributeModifier paramAttributeModifier)
/*     */   {
/* 111 */     for (int i = 0; i < 3; i++) {
/* 112 */       Set localSet2 = (Set)this.c.get(Integer.valueOf(i));
/* 113 */       localSet2.remove(paramAttributeModifier);
/*     */     }
/*     */     
/* 116 */     Set localSet1 = (Set)this.d.get(paramAttributeModifier.b());
/*     */     
/* 118 */     if (localSet1 != null) {
/* 119 */       localSet1.remove(paramAttributeModifier);
/*     */       
/* 121 */       if (localSet1.isEmpty()) {
/* 122 */         this.d.remove(paramAttributeModifier.b());
/*     */       }
/*     */     }
/*     */     
/* 126 */     this.e.remove(paramAttributeModifier.a());
/* 127 */     f();
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
/*     */   public double getValue()
/*     */   {
/* 173 */     if (this.g) {
/* 174 */       this.h = g();
/* 175 */       this.g = false;
/*     */     }
/*     */     
/* 178 */     return this.h;
/*     */   }
/*     */   
/*     */   private double g() {
/* 182 */     double d1 = b();
/*     */     
/* 184 */     for (AttributeModifier localAttributeModifier1 : b(0)) {
/* 185 */       d1 += localAttributeModifier1.d();
/*     */     }
/*     */     
/* 188 */     double d2 = d1;
/*     */     
/* 190 */     for (Iterator localIterator2 = b(1).iterator(); localIterator2.hasNext();) { localAttributeModifier2 = (AttributeModifier)localIterator2.next();
/* 191 */       d2 += d1 * localAttributeModifier2.d();
/*     */     }
/*     */     AttributeModifier localAttributeModifier2;
/* 194 */     for (localIterator2 = b(2).iterator(); localIterator2.hasNext();) { localAttributeModifier2 = (AttributeModifier)localIterator2.next();
/* 195 */       d2 *= (1.0D + localAttributeModifier2.d());
/*     */     }
/*     */     
/* 198 */     return this.b.a(d2);
/*     */   }
/*     */   
/*     */   private Collection<AttributeModifier> b(int paramInt) {
/* 202 */     java.util.HashSet localHashSet = Sets.newHashSet(a(paramInt));
/*     */     
/* 204 */     IAttribute localIAttribute = this.b.d();
/* 205 */     while (localIAttribute != null) {
/* 206 */       AttributeInstance localAttributeInstance = this.a.a(localIAttribute);
/* 207 */       if (localAttributeInstance != null) {
/* 208 */         localHashSet.addAll(localAttributeInstance.a(paramInt));
/*     */       }
/* 210 */       localIAttribute = localIAttribute.d();
/*     */     }
/*     */     
/* 213 */     return localHashSet;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\AttributeModifiable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */