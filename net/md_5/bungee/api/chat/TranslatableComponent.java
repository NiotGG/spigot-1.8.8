/*     */ package net.md_5.bungee.api.chat;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TranslatableComponent
/*     */   extends BaseComponent
/*     */ {
/*  16 */   public void setTranslate(String translate) { this.translate = translate; }
/*  17 */   public String toString() { return "TranslatableComponent(locales=" + getLocales() + ", format=" + getFormat() + ", translate=" + getTranslate() + ", with=" + getWith() + ")"; }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  22 */   public ResourceBundle getLocales() { return this.locales; } private final ResourceBundle locales = ResourceBundle.getBundle("mojang-translations/en_US");
/*  23 */   public Pattern getFormat() { return this.format; } private final Pattern format = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
/*     */   private String translate;
/*     */   private List<BaseComponent> with;
/*     */   
/*     */   public String getTranslate()
/*     */   {
/*  29 */     return this.translate;
/*     */   }
/*     */   
/*     */   public List<BaseComponent> getWith() {
/*  33 */     return this.with;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TranslatableComponent(TranslatableComponent original)
/*     */   {
/*  42 */     super(original);
/*  43 */     setTranslate(original.getTranslate());
/*  44 */     for (BaseComponent baseComponent : original.getWith())
/*     */     {
/*  46 */       this.with.add(baseComponent.duplicate());
/*     */     }
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
/*     */   public TranslatableComponent(String translate, Object... with)
/*     */   {
/*  62 */     setTranslate(translate);
/*  63 */     List<BaseComponent> temp = new ArrayList();
/*  64 */     for (Object w : with)
/*     */     {
/*  66 */       if ((w instanceof String))
/*     */       {
/*  68 */         temp.add(new TextComponent((String)w));
/*     */       }
/*     */       else {
/*  71 */         temp.add((BaseComponent)w);
/*     */       }
/*     */     }
/*  74 */     setWith(temp);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public BaseComponent duplicate()
/*     */   {
/*  85 */     return new TranslatableComponent(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWith(List<BaseComponent> components)
/*     */   {
/*  96 */     for (BaseComponent component : components)
/*     */     {
/*  98 */       component.parent = this;
/*     */     }
/* 100 */     this.with = components;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addWith(String text)
/*     */   {
/* 111 */     addWith(new TextComponent(text));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addWith(BaseComponent component)
/*     */   {
/* 122 */     if (this.with == null)
/*     */     {
/* 124 */       this.with = new ArrayList();
/*     */     }
/* 126 */     component.parent = this;
/* 127 */     this.with.add(component);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void toPlainText(StringBuilder builder)
/*     */   {
/*     */     String trans;
/*     */     try
/*     */     {
/* 136 */       trans = this.locales.getString(this.translate);
/*     */     } catch (MissingResourceException e) {
/* 138 */       trans = this.translate;
/*     */     }
/*     */     
/* 141 */     Matcher matcher = this.format.matcher(trans);
/* 142 */     int position = 0;
/* 143 */     int i = 0;
/* 144 */     while (matcher.find(position))
/*     */     {
/* 146 */       int pos = matcher.start();
/* 147 */       if (pos != position)
/*     */       {
/* 149 */         builder.append(trans.substring(position, pos));
/*     */       }
/* 151 */       position = matcher.end();
/*     */       
/* 153 */       String formatCode = matcher.group(2);
/* 154 */       switch (formatCode.charAt(0))
/*     */       {
/*     */       case 'd': 
/*     */       case 's': 
/* 158 */         String withIndex = matcher.group(1);
/* 159 */         ((BaseComponent)this.with.get(withIndex != null ? Integer.parseInt(withIndex) - 1 : i++)).toPlainText(builder);
/* 160 */         break;
/*     */       case '%': 
/* 162 */         builder.append('%');
/*     */       }
/*     */       
/*     */     }
/* 166 */     if (trans.length() != position)
/*     */     {
/* 168 */       builder.append(trans.substring(position, trans.length()));
/*     */     }
/*     */     
/* 171 */     super.toPlainText(builder);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void toLegacyText(StringBuilder builder)
/*     */   {
/*     */     String trans;
/*     */     try
/*     */     {
/* 180 */       trans = this.locales.getString(this.translate);
/*     */     } catch (MissingResourceException e) {
/* 182 */       trans = this.translate;
/*     */     }
/*     */     
/* 185 */     Matcher matcher = this.format.matcher(trans);
/* 186 */     int position = 0;
/* 187 */     int i = 0;
/* 188 */     while (matcher.find(position))
/*     */     {
/* 190 */       int pos = matcher.start();
/* 191 */       if (pos != position)
/*     */       {
/* 193 */         addFormat(builder);
/* 194 */         builder.append(trans.substring(position, pos));
/*     */       }
/* 196 */       position = matcher.end();
/*     */       
/* 198 */       String formatCode = matcher.group(2);
/* 199 */       switch (formatCode.charAt(0))
/*     */       {
/*     */       case 'd': 
/*     */       case 's': 
/* 203 */         String withIndex = matcher.group(1);
/* 204 */         ((BaseComponent)this.with.get(withIndex != null ? Integer.parseInt(withIndex) - 1 : i++)).toLegacyText(builder);
/* 205 */         break;
/*     */       case '%': 
/* 207 */         addFormat(builder);
/* 208 */         builder.append('%');
/*     */       }
/*     */       
/*     */     }
/* 212 */     if (trans.length() != position)
/*     */     {
/* 214 */       addFormat(builder);
/* 215 */       builder.append(trans.substring(position, trans.length()));
/*     */     }
/* 217 */     super.toLegacyText(builder);
/*     */   }
/*     */   
/*     */   private void addFormat(StringBuilder builder)
/*     */   {
/* 222 */     builder.append(getColor());
/* 223 */     if (isBold())
/*     */     {
/* 225 */       builder.append(ChatColor.BOLD);
/*     */     }
/* 227 */     if (isItalic())
/*     */     {
/* 229 */       builder.append(ChatColor.ITALIC);
/*     */     }
/* 231 */     if (isUnderlined())
/*     */     {
/* 233 */       builder.append(ChatColor.UNDERLINE);
/*     */     }
/* 235 */     if (isStrikethrough())
/*     */     {
/* 237 */       builder.append(ChatColor.STRIKETHROUGH);
/*     */     }
/* 239 */     if (isObfuscated())
/*     */     {
/* 241 */       builder.append(ChatColor.MAGIC);
/*     */     }
/*     */   }
/*     */   
/*     */   public TranslatableComponent() {}
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\md_5\bungee\api\chat\TranslatableComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */