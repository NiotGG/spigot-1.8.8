/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Multimap;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class AttributeMapServer extends AttributeMapBase
/*    */ {
/* 11 */   private final Set<AttributeInstance> e = Sets.newHashSet();
/* 12 */   protected final Map<String, AttributeInstance> d = new InsensitiveStringMap();
/*    */   
/*    */   public AttributeModifiable e(IAttribute paramIAttribute)
/*    */   {
/* 16 */     return (AttributeModifiable)super.a(paramIAttribute);
/*    */   }
/*    */   
/*    */   public AttributeModifiable b(String paramString)
/*    */   {
/* 21 */     AttributeInstance localAttributeInstance = super.a(paramString);
/* 22 */     if (localAttributeInstance == null) {
/* 23 */       localAttributeInstance = (AttributeInstance)this.d.get(paramString);
/*    */     }
/* 25 */     return (AttributeModifiable)localAttributeInstance;
/*    */   }
/*    */   
/*    */   public AttributeInstance b(IAttribute paramIAttribute)
/*    */   {
/* 30 */     AttributeInstance localAttributeInstance = super.b(paramIAttribute);
/*    */     
/* 32 */     if (((paramIAttribute instanceof AttributeRanged)) && (((AttributeRanged)paramIAttribute).g() != null)) {
/* 33 */       this.d.put(((AttributeRanged)paramIAttribute).g(), localAttributeInstance);
/*    */     }
/*    */     
/* 36 */     return localAttributeInstance;
/*    */   }
/*    */   
/*    */   protected AttributeInstance c(IAttribute paramIAttribute)
/*    */   {
/* 41 */     return new AttributeModifiable(this, paramIAttribute);
/*    */   }
/*    */   
/*    */   public void a(AttributeInstance paramAttributeInstance)
/*    */   {
/* 46 */     if (paramAttributeInstance.getAttribute().c()) {
/* 47 */       this.e.add(paramAttributeInstance);
/*    */     }
/*    */     
/* 50 */     for (IAttribute localIAttribute : this.c.get(paramAttributeInstance.getAttribute())) {
/* 51 */       AttributeModifiable localAttributeModifiable = e(localIAttribute);
/* 52 */       if (localAttributeModifiable != null) {
/* 53 */         localAttributeModifiable.f();
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public Set<AttributeInstance> getAttributes() {
/* 59 */     return this.e;
/*    */   }
/*    */   
/*    */   public Collection<AttributeInstance> c() {
/* 63 */     java.util.HashSet localHashSet = Sets.newHashSet();
/*    */     
/* 65 */     for (AttributeInstance localAttributeInstance : a()) {
/* 66 */       if (localAttributeInstance.getAttribute().c()) {
/* 67 */         localHashSet.add(localAttributeInstance);
/*    */       }
/*    */     }
/*    */     
/* 71 */     return localHashSet;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\AttributeMapServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */