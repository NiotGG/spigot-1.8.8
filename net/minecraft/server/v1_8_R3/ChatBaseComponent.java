/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class ChatBaseComponent implements IChatBaseComponent
/*     */ {
/*  11 */   protected List<IChatBaseComponent> a = Lists.newArrayList();
/*     */   
/*     */   private ChatModifier b;
/*     */   
/*     */   public IChatBaseComponent addSibling(IChatBaseComponent ichatbasecomponent)
/*     */   {
/*  17 */     ichatbasecomponent.getChatModifier().setChatModifier(getChatModifier());
/*  18 */     this.a.add(ichatbasecomponent);
/*  19 */     return this;
/*     */   }
/*     */   
/*     */   public List<IChatBaseComponent> a() {
/*  23 */     return this.a;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent a(String s) {
/*  27 */     return addSibling(new ChatComponentText(s));
/*     */   }
/*     */   
/*     */   public IChatBaseComponent setChatModifier(ChatModifier chatmodifier) {
/*  31 */     this.b = chatmodifier;
/*  32 */     Iterator iterator = this.a.iterator();
/*     */     
/*  34 */     while (iterator.hasNext()) {
/*  35 */       IChatBaseComponent ichatbasecomponent = (IChatBaseComponent)iterator.next();
/*     */       
/*  37 */       ichatbasecomponent.getChatModifier().setChatModifier(getChatModifier());
/*     */     }
/*     */     
/*  40 */     return this;
/*     */   }
/*     */   
/*     */   public ChatModifier getChatModifier() {
/*  44 */     if (this.b == null) {
/*  45 */       this.b = new ChatModifier();
/*  46 */       Iterator iterator = this.a.iterator();
/*     */       
/*  48 */       while (iterator.hasNext()) {
/*  49 */         IChatBaseComponent ichatbasecomponent = (IChatBaseComponent)iterator.next();
/*     */         
/*  51 */         ichatbasecomponent.getChatModifier().setChatModifier(this.b);
/*     */       }
/*     */     }
/*     */     
/*  55 */     return this.b;
/*     */   }
/*     */   
/*     */   public Iterator<IChatBaseComponent> iterator() {
/*  59 */     return Iterators.concat(Iterators.forArray(new ChatBaseComponent[] { this }), a(this.a));
/*     */   }
/*     */   
/*     */   public final String c() {
/*  63 */     StringBuilder stringbuilder = new StringBuilder();
/*  64 */     Iterator iterator = iterator();
/*     */     
/*  66 */     while (iterator.hasNext()) {
/*  67 */       IChatBaseComponent ichatbasecomponent = (IChatBaseComponent)iterator.next();
/*     */       
/*  69 */       stringbuilder.append(ichatbasecomponent.getText());
/*     */     }
/*     */     
/*  72 */     return stringbuilder.toString();
/*     */   }
/*     */   
/*     */   public static Iterator<IChatBaseComponent> a(Iterable<IChatBaseComponent> iterable) {
/*  76 */     Iterator iterator = Iterators.concat(Iterators.transform(iterable.iterator(), new Function() {
/*     */       public Iterator<IChatBaseComponent> a(IChatBaseComponent ichatbasecomponent) {
/*  78 */         return ichatbasecomponent.iterator();
/*     */       }
/*     */       
/*     */       public Object apply(Object object) {
/*  82 */         return a((IChatBaseComponent)object);
/*     */       }
/*     */       
/*  85 */     }));
/*  86 */     iterator = Iterators.transform(iterator, new Function() {
/*     */       public IChatBaseComponent a(IChatBaseComponent ichatbasecomponent) {
/*  88 */         IChatBaseComponent ichatbasecomponent1 = ichatbasecomponent.f();
/*     */         
/*  90 */         ichatbasecomponent1.setChatModifier(ichatbasecomponent1.getChatModifier().n());
/*  91 */         return ichatbasecomponent1;
/*     */       }
/*     */       
/*     */       public Object apply(Object object) {
/*  95 */         return a((IChatBaseComponent)object);
/*     */       }
/*  97 */     });
/*  98 */     return iterator;
/*     */   }
/*     */   
/*     */   public boolean equals(Object object) {
/* 102 */     if (this == object)
/* 103 */       return true;
/* 104 */     if (!(object instanceof ChatBaseComponent)) {
/* 105 */       return false;
/*     */     }
/* 107 */     ChatBaseComponent chatbasecomponent = (ChatBaseComponent)object;
/*     */     
/* 109 */     return (this.a.equals(chatbasecomponent.a)) && (getChatModifier().equals(chatbasecomponent.getChatModifier()));
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 114 */     return 31 * getChatModifier().hashCode() + this.a.hashCode();
/*     */   }
/*     */   
/*     */   public String toString() {
/* 118 */     return "BaseComponent{style=" + this.b + ", siblings=" + this.a + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChatBaseComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */