/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Arrays;
/*     */ import java.util.IllegalFormatException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatMessage
/*     */   extends ChatBaseComponent
/*     */ {
/*     */   private final String d;
/*     */   private final Object[] e;
/*  20 */   private final Object f = new Object();
/*  21 */   private long g = -1L;
/*     */   
/*  23 */   List<IChatBaseComponent> b = Lists.newArrayList();
/*     */   
/*     */ 
/*  26 */   public static final Pattern c = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
/*     */   
/*     */   public ChatMessage(String paramString, Object... paramVarArgs) {
/*  29 */     this.d = paramString;
/*  30 */     this.e = paramVarArgs;
/*     */     
/*  32 */     for (Object localObject : paramVarArgs) {
/*  33 */       if ((localObject instanceof IChatBaseComponent)) {
/*  34 */         ((IChatBaseComponent)localObject).getChatModifier().setChatModifier(getChatModifier());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   synchronized void g()
/*     */   {
/*  41 */     synchronized (this.f) {
/*  42 */       long l = LocaleI18n.a();
/*  43 */       if (l == this.g) {
/*  44 */         return;
/*     */       }
/*  46 */       this.g = l;
/*  47 */       this.b.clear();
/*     */     }
/*     */     try
/*     */     {
/*  51 */       b(LocaleI18n.get(this.d));
/*     */     } catch (ChatMessageException localChatMessageException1) {
/*  53 */       this.b.clear();
/*     */       try {
/*  55 */         b(LocaleI18n.b(this.d));
/*     */       } catch (ChatMessageException localChatMessageException2) {
/*  57 */         throw localChatMessageException1;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void b(String paramString) {
/*  63 */     int i = 0;
/*  64 */     Matcher localMatcher = c.matcher(paramString);
/*     */     
/*  66 */     int j = 0;
/*  67 */     int k = 0;
/*     */     try
/*     */     {
/*  70 */       while (localMatcher.find(k)) {
/*  71 */         int m = localMatcher.start();
/*  72 */         int n = localMatcher.end();
/*     */         
/*     */ 
/*  75 */         if (m > k) {
/*  76 */           localObject1 = new ChatComponentText(String.format(paramString.substring(k, m), new Object[0]));
/*  77 */           ((ChatComponentText)localObject1).getChatModifier().setChatModifier(getChatModifier());
/*  78 */           this.b.add(localObject1);
/*     */         }
/*     */         
/*  81 */         Object localObject1 = localMatcher.group(2);
/*  82 */         String str = paramString.substring(m, n);
/*     */         
/*     */         Object localObject2;
/*  85 */         if (("%".equals(localObject1)) && ("%%".equals(str))) {
/*  86 */           localObject2 = new ChatComponentText("%");
/*  87 */           ((ChatComponentText)localObject2).getChatModifier().setChatModifier(getChatModifier());
/*  88 */           this.b.add(localObject2);
/*  89 */         } else if ("s".equals(localObject1)) {
/*  90 */           localObject2 = localMatcher.group(1);
/*  91 */           int i1 = localObject2 != null ? Integer.parseInt((String)localObject2) - 1 : j++;
/*  92 */           if (i1 < this.e.length) {
/*  93 */             this.b.add(a(i1));
/*     */           }
/*     */         } else {
/*  96 */           throw new ChatMessageException(this, "Unsupported format: '" + str + "'");
/*     */         }
/*     */         
/*  99 */         k = n;
/*     */       }
/*     */       
/*     */ 
/* 103 */       if (k < paramString.length()) {
/* 104 */         ChatComponentText localChatComponentText = new ChatComponentText(String.format(paramString.substring(k), new Object[0]));
/* 105 */         localChatComponentText.getChatModifier().setChatModifier(getChatModifier());
/* 106 */         this.b.add(localChatComponentText);
/*     */       }
/*     */     } catch (IllegalFormatException localIllegalFormatException) {
/* 109 */       throw new ChatMessageException(this, localIllegalFormatException);
/*     */     }
/*     */   }
/*     */   
/*     */   private IChatBaseComponent a(int paramInt) {
/* 114 */     if (paramInt >= this.e.length) {
/* 115 */       throw new ChatMessageException(this, paramInt);
/*     */     }
/*     */     
/* 118 */     Object localObject1 = this.e[paramInt];
/*     */     
/*     */     Object localObject2;
/* 121 */     if ((localObject1 instanceof IChatBaseComponent)) {
/* 122 */       localObject2 = (IChatBaseComponent)localObject1;
/*     */     } else {
/* 124 */       localObject2 = new ChatComponentText(localObject1 == null ? "null" : localObject1.toString());
/* 125 */       ((IChatBaseComponent)localObject2).getChatModifier().setChatModifier(getChatModifier());
/*     */     }
/*     */     
/* 128 */     return (IChatBaseComponent)localObject2;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent setChatModifier(ChatModifier paramChatModifier)
/*     */   {
/* 133 */     super.setChatModifier(paramChatModifier);
/*     */     
/* 135 */     for (Object localObject2 : this.e) {
/* 136 */       if ((localObject2 instanceof IChatBaseComponent)) {
/* 137 */         ((IChatBaseComponent)localObject2).getChatModifier().setChatModifier(getChatModifier());
/*     */       }
/*     */     }
/*     */     
/* 141 */     if (this.g > -1L) {
/* 142 */       for (??? = this.b.iterator(); ((Iterator)???).hasNext();) { IChatBaseComponent localIChatBaseComponent = (IChatBaseComponent)((Iterator)???).next();
/* 143 */         localIChatBaseComponent.getChatModifier().setChatModifier(paramChatModifier);
/*     */       }
/*     */     }
/*     */     
/* 147 */     return this;
/*     */   }
/*     */   
/*     */   public Iterator<IChatBaseComponent> iterator()
/*     */   {
/* 152 */     g();
/*     */     
/* 154 */     return Iterators.concat(a(this.b), a(this.a));
/*     */   }
/*     */   
/*     */   public String getText()
/*     */   {
/* 159 */     g();
/*     */     
/* 161 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     
/* 163 */     for (IChatBaseComponent localIChatBaseComponent : this.b) {
/* 164 */       localStringBuilder.append(localIChatBaseComponent.getText());
/*     */     }
/*     */     
/* 167 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   public ChatMessage h()
/*     */   {
/* 172 */     Object[] arrayOfObject = new Object[this.e.length];
/*     */     
/* 174 */     for (int i = 0; i < this.e.length; i++) {
/* 175 */       if ((this.e[i] instanceof IChatBaseComponent)) {
/* 176 */         arrayOfObject[i] = ((IChatBaseComponent)this.e[i]).f();
/*     */       } else {
/* 178 */         arrayOfObject[i] = this.e[i];
/*     */       }
/*     */     }
/*     */     
/* 182 */     ChatMessage localChatMessage = new ChatMessage(this.d, arrayOfObject);
/* 183 */     localChatMessage.setChatModifier(getChatModifier().clone());
/* 184 */     for (IChatBaseComponent localIChatBaseComponent : a()) {
/* 185 */       localChatMessage.addSibling(localIChatBaseComponent.f());
/*     */     }
/* 187 */     return localChatMessage;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 192 */     if (this == paramObject) {
/* 193 */       return true;
/*     */     }
/*     */     
/* 196 */     if ((paramObject instanceof ChatMessage)) {
/* 197 */       ChatMessage localChatMessage = (ChatMessage)paramObject;
/* 198 */       return (Arrays.equals(this.e, localChatMessage.e)) && (this.d.equals(localChatMessage.d)) && (super.equals(paramObject));
/*     */     }
/*     */     
/* 201 */     return false;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 206 */     int i = super.hashCode();
/* 207 */     i = 31 * i + this.d.hashCode();
/* 208 */     i = 31 * i + Arrays.hashCode(this.e);
/* 209 */     return i;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 214 */     return "TranslatableComponent{key='" + this.d + '\'' + ", args=" + Arrays.toString(this.e) + ", siblings=" + this.a + ", style=" + getChatModifier() + '}';
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String i()
/*     */   {
/* 223 */     return this.d;
/*     */   }
/*     */   
/*     */   public Object[] j() {
/* 227 */     return this.e;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ChatMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */