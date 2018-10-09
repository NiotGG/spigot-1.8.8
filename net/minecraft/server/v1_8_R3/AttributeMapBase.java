/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.HashMultimap;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.google.common.collect.Multimap;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ public abstract class AttributeMapBase
/*    */ {
/* 12 */   protected final Map<IAttribute, AttributeInstance> a = Maps.newHashMap();
/* 13 */   protected final Map<String, AttributeInstance> b = new InsensitiveStringMap();
/* 14 */   protected final Multimap<IAttribute, IAttribute> c = HashMultimap.create();
/*    */   
/*    */   public AttributeInstance a(IAttribute paramIAttribute) {
/* 17 */     return (AttributeInstance)this.a.get(paramIAttribute);
/*    */   }
/*    */   
/*    */   public AttributeInstance a(String paramString) {
/* 21 */     return (AttributeInstance)this.b.get(paramString);
/*    */   }
/*    */   
/*    */   public AttributeInstance b(IAttribute paramIAttribute) {
/* 25 */     if (this.b.containsKey(paramIAttribute.getName())) {
/* 26 */       throw new IllegalArgumentException("Attribute is already registered!");
/*    */     }
/*    */     
/* 29 */     AttributeInstance localAttributeInstance = c(paramIAttribute);
/* 30 */     this.b.put(paramIAttribute.getName(), localAttributeInstance);
/* 31 */     this.a.put(paramIAttribute, localAttributeInstance);
/*    */     
/* 33 */     IAttribute localIAttribute = paramIAttribute.d();
/* 34 */     while (localIAttribute != null) {
/* 35 */       this.c.put(localIAttribute, paramIAttribute);
/* 36 */       localIAttribute = localIAttribute.d();
/*    */     }
/*    */     
/* 39 */     return localAttributeInstance;
/*    */   }
/*    */   
/*    */   protected abstract AttributeInstance c(IAttribute paramIAttribute);
/*    */   
/*    */   public Collection<AttributeInstance> a() {
/* 45 */     return this.b.values();
/*    */   }
/*    */   
/*    */   public void a(AttributeInstance paramAttributeInstance) {}
/*    */   
/*    */   public void a(Multimap<String, AttributeModifier> paramMultimap)
/*    */   {
/* 52 */     for (Map.Entry localEntry : paramMultimap.entries()) {
/* 53 */       AttributeInstance localAttributeInstance = a((String)localEntry.getKey());
/*    */       
/* 55 */       if (localAttributeInstance != null) {
/* 56 */         localAttributeInstance.c((AttributeModifier)localEntry.getValue());
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public void b(Multimap<String, AttributeModifier> paramMultimap) {
/* 62 */     for (Map.Entry localEntry : paramMultimap.entries()) {
/* 63 */       AttributeInstance localAttributeInstance = a((String)localEntry.getKey());
/*    */       
/* 65 */       if (localAttributeInstance != null) {
/* 66 */         localAttributeInstance.c((AttributeModifier)localEntry.getValue());
/* 67 */         localAttributeInstance.b((AttributeModifier)localEntry.getValue());
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\AttributeMapBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */