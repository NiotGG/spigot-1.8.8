/*     */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.server.v1_8_R3.ChatClickable;
/*     */ import net.minecraft.server.v1_8_R3.ChatClickable.EnumClickAction;
/*     */ import net.minecraft.server.v1_8_R3.ChatComponentText;
/*     */ import net.minecraft.server.v1_8_R3.ChatMessage;
/*     */ import net.minecraft.server.v1_8_R3.ChatModifier;
/*     */ import net.minecraft.server.v1_8_R3.EnumChatFormat;
/*     */ import net.minecraft.server.v1_8_R3.IChatBaseComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CraftChatMessage
/*     */ {
/*  22 */   private static final Pattern LINK_PATTERN = Pattern.compile("((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + String.valueOf('§') + " \\n]|$))))");
/*     */   
/*     */   private static class StringMessage { private static final Map<Character, EnumChatFormat> formatMap;
/*  25 */     private static final Pattern INCREMENTAL_PATTERN = Pattern.compile("(" + String.valueOf('§') + "[0-9a-fk-or])|(\\n)|((?:(?:https?):\\/\\/)?(?:[-\\w_\\.]{2,}\\.[a-z]{2,4}.*?(?=[\\.\\?!,;:]?(?:[" + String.valueOf('§') + " \\n]|$))))", 2);
/*     */     
/*     */     static {
/*  28 */       ImmutableMap.Builder<Character, EnumChatFormat> builder = ImmutableMap.builder();
/*  29 */       EnumChatFormat[] arrayOfEnumChatFormat; int i = (arrayOfEnumChatFormat = EnumChatFormat.values()).length; for (int j = 0; j < i; j++) { EnumChatFormat format = arrayOfEnumChatFormat[j];
/*  30 */         builder.put(Character.valueOf(Character.toLowerCase(format.toString().charAt(1))), format);
/*     */       }
/*  32 */       formatMap = builder.build();
/*     */     }
/*     */     
/*  35 */     private final List<IChatBaseComponent> list = new ArrayList();
/*  36 */     private IChatBaseComponent currentChatComponent = new ChatComponentText("");
/*  37 */     private ChatModifier modifier = new ChatModifier();
/*     */     private final IChatBaseComponent[] output;
/*     */     private int currentIndex;
/*     */     private final String message;
/*     */     
/*     */     private StringMessage(String message, boolean keepNewlines) {
/*  43 */       this.message = message;
/*  44 */       if (message == null) {
/*  45 */         this.output = new IChatBaseComponent[] { this.currentChatComponent };
/*  46 */         return;
/*     */       }
/*  48 */       this.list.add(this.currentChatComponent);
/*     */       
/*  50 */       Matcher matcher = INCREMENTAL_PATTERN.matcher(message);
/*  51 */       String match = null;
/*  52 */       while (matcher.find()) {
/*  53 */         int groupId = 0;
/*  54 */         while ((match = matcher.group(++groupId)) == null) {}
/*     */         
/*     */ 
/*  57 */         appendNewComponent(matcher.start(groupId));
/*  58 */         switch (groupId) {
/*     */         case 1: 
/*  60 */           EnumChatFormat format = (EnumChatFormat)formatMap.get(Character.valueOf(match.toLowerCase().charAt(1)));
/*  61 */           if (format == EnumChatFormat.RESET) {
/*  62 */             this.modifier = new ChatModifier();
/*  63 */           } else if (format.isFormat())
/*  64 */             switch (format) {
/*     */             case RESET: 
/*  66 */               this.modifier.setBold(Boolean.TRUE);
/*  67 */               break;
/*     */             case WHITE: 
/*  69 */               this.modifier.setItalic(Boolean.TRUE);
/*  70 */               break;
/*     */             case STRIKETHROUGH: 
/*  72 */               this.modifier.setStrikethrough(Boolean.TRUE);
/*  73 */               break;
/*     */             case UNDERLINE: 
/*  75 */               this.modifier.setUnderline(Boolean.TRUE);
/*  76 */               break;
/*     */             case RED: 
/*  78 */               this.modifier.setRandom(Boolean.TRUE);
/*  79 */               break;
/*     */             default: 
/*  81 */               throw new AssertionError("Unexpected message format");
/*     */               
/*  83 */               break; } else {
/*  84 */             this.modifier = new ChatModifier().setColor(format);
/*     */           }
/*  86 */           break;
/*     */         case 2: 
/*  88 */           if (keepNewlines) {
/*  89 */             this.currentChatComponent.addSibling(new ChatComponentText("\n"));
/*     */           } else {
/*  91 */             this.currentChatComponent = null;
/*     */           }
/*  93 */           break;
/*     */         case 3: 
/*  95 */           if ((!match.startsWith("http://")) && (!match.startsWith("https://"))) {
/*  96 */             match = "http://" + match;
/*     */           }
/*  98 */           this.modifier.setChatClickable(new ChatClickable(ChatClickable.EnumClickAction.OPEN_URL, match));
/*  99 */           appendNewComponent(matcher.end(groupId));
/* 100 */           this.modifier.setChatClickable(null);
/*     */         }
/* 102 */         this.currentIndex = matcher.end(groupId);
/*     */       }
/*     */       
/* 105 */       if (this.currentIndex < message.length()) {
/* 106 */         appendNewComponent(message.length());
/*     */       }
/*     */       
/* 109 */       this.output = ((IChatBaseComponent[])this.list.toArray(new IChatBaseComponent[this.list.size()]));
/*     */     }
/*     */     
/*     */     private void appendNewComponent(int index) {
/* 113 */       if (index <= this.currentIndex) {
/* 114 */         return;
/*     */       }
/* 116 */       IChatBaseComponent addition = new ChatComponentText(this.message.substring(this.currentIndex, index)).setChatModifier(this.modifier);
/* 117 */       this.currentIndex = index;
/* 118 */       this.modifier = this.modifier.clone();
/* 119 */       if (this.currentChatComponent == null) {
/* 120 */         this.currentChatComponent = new ChatComponentText("");
/* 121 */         this.list.add(this.currentChatComponent);
/*     */       }
/* 123 */       this.currentChatComponent.addSibling(addition);
/*     */     }
/*     */     
/*     */     private IChatBaseComponent[] getOutput() {
/* 127 */       return this.output;
/*     */     }
/*     */   }
/*     */   
/*     */   public static IChatBaseComponent[] fromString(String message) {
/* 132 */     return fromString(message, false);
/*     */   }
/*     */   
/*     */   public static IChatBaseComponent[] fromString(String message, boolean keepNewlines) {
/* 136 */     return new StringMessage(message, keepNewlines, null).getOutput();
/*     */   }
/*     */   
/*     */   public static String fromComponent(IChatBaseComponent component) {
/* 140 */     return fromComponent(component, EnumChatFormat.BLACK);
/*     */   }
/*     */   
/*     */   public static String fromComponent(IChatBaseComponent component, EnumChatFormat defaultColor) {
/* 144 */     if (component == null) return "";
/* 145 */     StringBuilder out = new StringBuilder();
/*     */     
/* 147 */     for (IChatBaseComponent c : component) {
/* 148 */       ChatModifier modi = c.getChatModifier();
/* 149 */       out.append(modi.getColor() == null ? defaultColor : modi.getColor());
/* 150 */       if (modi.isBold()) {
/* 151 */         out.append(EnumChatFormat.BOLD);
/*     */       }
/* 153 */       if (modi.isItalic()) {
/* 154 */         out.append(EnumChatFormat.ITALIC);
/*     */       }
/* 156 */       if (modi.isUnderlined()) {
/* 157 */         out.append(EnumChatFormat.UNDERLINE);
/*     */       }
/* 159 */       if (modi.isStrikethrough()) {
/* 160 */         out.append(EnumChatFormat.STRIKETHROUGH);
/*     */       }
/* 162 */       if (modi.isRandom()) {
/* 163 */         out.append(EnumChatFormat.OBFUSCATED);
/*     */       }
/* 165 */       out.append(c.getText());
/*     */     }
/* 167 */     return out.toString().replaceFirst("^(" + defaultColor + ")*", "");
/*     */   }
/*     */   
/*     */   public static IChatBaseComponent fixComponent(IChatBaseComponent component) {
/* 171 */     Matcher matcher = LINK_PATTERN.matcher("");
/* 172 */     return fixComponent(component, matcher);
/*     */   }
/*     */   
/*     */   private static IChatBaseComponent fixComponent(IChatBaseComponent component, Matcher matcher) {
/* 176 */     if ((component instanceof ChatComponentText)) {
/* 177 */       ChatComponentText text = (ChatComponentText)component;
/* 178 */       String msg = text.g();
/* 179 */       if (matcher.reset(msg).find()) {
/* 180 */         matcher.reset();
/*     */         
/* 182 */         ChatModifier modifier = text.getChatModifier() != null ? 
/* 183 */           text.getChatModifier() : new ChatModifier();
/* 184 */         List<IChatBaseComponent> extras = new ArrayList();
/* 185 */         List<IChatBaseComponent> extrasOld = new ArrayList(text.a());
/* 186 */         component = text = new ChatComponentText("");
/*     */         
/* 188 */         int pos = 0;
/* 189 */         ChatComponentText link; while (matcher.find()) {
/* 190 */           String match = matcher.group();
/*     */           
/* 192 */           if ((!match.startsWith("http://")) && (!match.startsWith("https://"))) {
/* 193 */             match = "http://" + match;
/*     */           }
/*     */           
/* 196 */           ChatComponentText prev = new ChatComponentText(msg.substring(pos, matcher.start()));
/* 197 */           prev.setChatModifier(modifier);
/* 198 */           extras.add(prev);
/*     */           
/* 200 */           link = new ChatComponentText(matcher.group());
/* 201 */           ChatModifier linkModi = modifier.clone();
/* 202 */           linkModi.setChatClickable(new ChatClickable(ChatClickable.EnumClickAction.OPEN_URL, match));
/* 203 */           link.setChatModifier(linkModi);
/* 204 */           extras.add(link);
/*     */           
/* 206 */           pos = matcher.end();
/*     */         }
/*     */         
/* 209 */         ChatComponentText prev = new ChatComponentText(msg.substring(pos));
/* 210 */         prev.setChatModifier(modifier);
/* 211 */         extras.add(prev);
/* 212 */         extras.addAll(extrasOld);
/*     */         
/* 214 */         for (IChatBaseComponent c : extras) {
/* 215 */           text.addSibling(c);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 220 */     List extras = component.a();
/* 221 */     for (int i = 0; i < extras.size(); i++) {
/* 222 */       IChatBaseComponent comp = (IChatBaseComponent)extras.get(i);
/* 223 */       if ((comp.getChatModifier() != null) && (comp.getChatModifier().h() == null)) {
/* 224 */         extras.set(i, fixComponent(comp, matcher));
/*     */       }
/*     */     }
/*     */     
/* 228 */     if ((component instanceof ChatMessage)) {
/* 229 */       Object[] subs = ((ChatMessage)component).j();
/* 230 */       for (int i = 0; i < subs.length; i++) {
/* 231 */         Object comp = subs[i];
/* 232 */         if ((comp instanceof IChatBaseComponent)) {
/* 233 */           IChatBaseComponent c = (IChatBaseComponent)comp;
/* 234 */           if ((c.getChatModifier() != null) && (c.getChatModifier().h() == null)) {
/* 235 */             subs[i] = fixComponent(c, matcher);
/*     */           }
/* 237 */         } else if (((comp instanceof String)) && (matcher.reset((String)comp).find())) {
/* 238 */           subs[i] = fixComponent(new ChatComponentText((String)comp), matcher);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 243 */     return component;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\CraftChatMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */