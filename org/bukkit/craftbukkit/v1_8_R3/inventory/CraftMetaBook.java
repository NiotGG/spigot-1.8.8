/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import java.util.AbstractList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_8_R3.IChatBaseComponent;
/*     */ import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagList;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagString;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage;
/*     */ import org.bukkit.inventory.meta.BookMeta;
/*     */ import org.spigotmc.ValidateUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ public class CraftMetaBook
/*     */   extends CraftMetaItem
/*     */   implements BookMeta
/*     */ {
/*  31 */   static final CraftMetaItem.ItemMetaKey BOOK_TITLE = new CraftMetaItem.ItemMetaKey("title");
/*  32 */   static final CraftMetaItem.ItemMetaKey BOOK_AUTHOR = new CraftMetaItem.ItemMetaKey("author");
/*  33 */   static final CraftMetaItem.ItemMetaKey BOOK_PAGES = new CraftMetaItem.ItemMetaKey("pages");
/*  34 */   static final CraftMetaItem.ItemMetaKey RESOLVED = new CraftMetaItem.ItemMetaKey("resolved");
/*  35 */   static final CraftMetaItem.ItemMetaKey GENERATION = new CraftMetaItem.ItemMetaKey("generation");
/*     */   
/*     */   static final int MAX_PAGE_LENGTH = 32767;
/*     */   static final int MAX_TITLE_LENGTH = 65535;
/*     */   protected String title;
/*     */   protected String author;
/*  41 */   public List<IChatBaseComponent> pages = new ArrayList();
/*     */   protected Integer generation;
/*     */   
/*     */   CraftMetaBook(CraftMetaItem meta) {
/*  45 */     super(meta);
/*     */     
/*  47 */     if ((meta instanceof CraftMetaBook)) {
/*  48 */       CraftMetaBook bookMeta = (CraftMetaBook)meta;
/*  49 */       this.title = bookMeta.title;
/*  50 */       this.author = bookMeta.author;
/*  51 */       this.pages.addAll(bookMeta.pages);
/*  52 */       this.generation = bookMeta.generation;
/*     */     }
/*     */   }
/*     */   
/*     */   CraftMetaBook(NBTTagCompound tag) {
/*  57 */     this(tag, true);
/*     */   }
/*     */   
/*     */   CraftMetaBook(NBTTagCompound tag, boolean handlePages) {
/*  61 */     super(tag);
/*     */     
/*  63 */     if (tag.hasKey(BOOK_TITLE.NBT)) {
/*  64 */       this.title = ValidateUtils.limit(tag.getString(BOOK_TITLE.NBT), 1024);
/*     */     }
/*     */     
/*  67 */     if (tag.hasKey(BOOK_AUTHOR.NBT)) {
/*  68 */       this.author = ValidateUtils.limit(tag.getString(BOOK_AUTHOR.NBT), 1024);
/*     */     }
/*     */     
/*  71 */     boolean resolved = false;
/*  72 */     if (tag.hasKey(RESOLVED.NBT)) {
/*  73 */       resolved = tag.getBoolean(RESOLVED.NBT);
/*     */     }
/*     */     
/*  76 */     if (tag.hasKey(GENERATION.NBT)) {
/*  77 */       this.generation = Integer.valueOf(tag.getInt(GENERATION.NBT));
/*     */     }
/*     */     
/*  80 */     if ((tag.hasKey(BOOK_PAGES.NBT)) && (handlePages)) {
/*  81 */       NBTTagList pages = tag.getList(BOOK_PAGES.NBT, 8);
/*     */       
/*  83 */       for (int i = 0; i < pages.size(); i++) {
/*  84 */         String page = pages.getString(i);
/*  85 */         if (resolved) {
/*     */           try {
/*  87 */             this.pages.add(IChatBaseComponent.ChatSerializer.a(page));
/*     */ 
/*     */           }
/*     */           catch (Exception localException) {}
/*     */         }
/*     */         else
/*  93 */           addPage(new String[] { ValidateUtils.limit(page, 2048) });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   CraftMetaBook(Map<String, Object> map) {
/*  99 */     super(map);
/*     */     
/* 101 */     setAuthor(CraftMetaItem.SerializableMeta.getString(map, BOOK_AUTHOR.BUKKIT, true));
/*     */     
/* 103 */     setTitle(CraftMetaItem.SerializableMeta.getString(map, BOOK_TITLE.BUKKIT, true));
/*     */     
/* 105 */     Iterable<?> pages = (Iterable)CraftMetaItem.SerializableMeta.getObject(Iterable.class, map, BOOK_PAGES.BUKKIT, true);
/* 106 */     if (pages != null) {
/* 107 */       for (Object page : pages) {
/* 108 */         if ((page instanceof String)) {
/* 109 */           addPage(new String[] { (String)page });
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 114 */     this.generation = ((Integer)CraftMetaItem.SerializableMeta.getObject(Integer.class, map, GENERATION.BUKKIT, true));
/*     */   }
/*     */   
/*     */   void applyToItem(NBTTagCompound itemData)
/*     */   {
/* 119 */     applyToItem(itemData, true);
/*     */   }
/*     */   
/*     */   void applyToItem(NBTTagCompound itemData, boolean handlePages) {
/* 123 */     super.applyToItem(itemData);
/*     */     
/* 125 */     if (hasTitle()) {
/* 126 */       itemData.setString(BOOK_TITLE.NBT, this.title);
/*     */     }
/*     */     
/* 129 */     if (hasAuthor()) {
/* 130 */       itemData.setString(BOOK_AUTHOR.NBT, this.author);
/*     */     }
/*     */     
/* 133 */     if (handlePages) {
/* 134 */       if (hasPages()) {
/* 135 */         NBTTagList list = new NBTTagList();
/* 136 */         for (IChatBaseComponent page : this.pages) {
/* 137 */           list.add(new NBTTagString(CraftChatMessage.fromComponent(page)));
/*     */         }
/* 139 */         itemData.set(BOOK_PAGES.NBT, list);
/*     */       }
/*     */       
/* 142 */       itemData.remove(RESOLVED.NBT);
/*     */     }
/*     */     
/* 145 */     if (this.generation != null) {
/* 146 */       itemData.setInt(GENERATION.NBT, this.generation.intValue());
/*     */     }
/*     */   }
/*     */   
/*     */   boolean isEmpty()
/*     */   {
/* 152 */     return (super.isEmpty()) && (isBookEmpty());
/*     */   }
/*     */   
/*     */   boolean isBookEmpty() {
/* 156 */     return (!hasPages()) && (!hasAuthor()) && (!hasTitle());
/*     */   }
/*     */   
/*     */   boolean applicableTo(Material type)
/*     */   {
/* 161 */     switch (type) {
/*     */     case SPRUCE_WOOD_STAIRS: 
/*     */     case STAINED_CLAY: 
/* 164 */       return true;
/*     */     }
/* 166 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasAuthor()
/*     */   {
/* 171 */     return !Strings.isNullOrEmpty(this.author);
/*     */   }
/*     */   
/*     */   public boolean hasTitle() {
/* 175 */     return !Strings.isNullOrEmpty(this.title);
/*     */   }
/*     */   
/*     */   public boolean hasPages() {
/* 179 */     return !this.pages.isEmpty();
/*     */   }
/*     */   
/*     */   public String getTitle() {
/* 183 */     return this.title;
/*     */   }
/*     */   
/*     */   public boolean setTitle(String title) {
/* 187 */     if (title == null) {
/* 188 */       this.title = null;
/* 189 */       return true; }
/* 190 */     if (title.length() > 65535) {
/* 191 */       return false;
/*     */     }
/*     */     
/* 194 */     this.title = title;
/* 195 */     return true;
/*     */   }
/*     */   
/*     */   public String getAuthor() {
/* 199 */     return this.author;
/*     */   }
/*     */   
/*     */   public void setAuthor(String author) {
/* 203 */     this.author = author;
/*     */   }
/*     */   
/*     */   public String getPage(int page) {
/* 207 */     Validate.isTrue(isValidPage(page), "Invalid page number");
/* 208 */     return CraftChatMessage.fromComponent((IChatBaseComponent)this.pages.get(page - 1));
/*     */   }
/*     */   
/*     */   public void setPage(int page, String text) {
/* 212 */     if (!isValidPage(page)) {
/* 213 */       throw new IllegalArgumentException("Invalid page number " + page + "/" + this.pages.size());
/*     */     }
/*     */     
/* 216 */     String newText = text.length() > 32767 ? text.substring(0, 32767) : text == null ? "" : text;
/* 217 */     this.pages.set(page - 1, CraftChatMessage.fromString(newText, true)[0]);
/*     */   }
/*     */   
/*     */   public void setPages(String... pages) {
/* 221 */     this.pages.clear();
/*     */     
/* 223 */     addPage(pages);
/*     */   }
/*     */   
/*     */   public void addPage(String... pages) { String[] arrayOfString;
/* 227 */     int i = (arrayOfString = pages).length; for (int j = 0; j < i; j++) { String page = arrayOfString[j];
/* 228 */       if (page == null) {
/* 229 */         page = "";
/* 230 */       } else if (page.length() > 32767) {
/* 231 */         page = page.substring(0, 32767);
/*     */       }
/*     */       
/* 234 */       this.pages.add(CraftChatMessage.fromString(page, true)[0]);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getPageCount() {
/* 239 */     return this.pages.size();
/*     */   }
/*     */   
/*     */   public List<String> getPages() {
/* 243 */     final List<IChatBaseComponent> copy = ImmutableList.copyOf(this.pages);
/* 244 */     new AbstractList()
/*     */     {
/*     */       public String get(int index)
/*     */       {
/* 248 */         return CraftChatMessage.fromComponent((IChatBaseComponent)copy.get(index));
/*     */       }
/*     */       
/*     */       public int size()
/*     */       {
/* 253 */         return copy.size();
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public void setPages(List<String> pages) {
/* 259 */     this.pages.clear();
/* 260 */     for (String page : pages) {
/* 261 */       addPage(new String[] { page });
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isValidPage(int page) {
/* 266 */     return (page > 0) && (page <= this.pages.size());
/*     */   }
/*     */   
/*     */   public CraftMetaBook clone()
/*     */   {
/* 271 */     CraftMetaBook meta = (CraftMetaBook)super.clone();
/* 272 */     meta.pages = new ArrayList(this.pages);
/* 273 */     return meta;
/*     */   }
/*     */   
/*     */   int applyHash()
/*     */   {
/*     */     int original;
/* 279 */     int hash = original = super.applyHash();
/* 280 */     if (hasTitle()) {
/* 281 */       hash = 61 * hash + this.title.hashCode();
/*     */     }
/* 283 */     if (hasAuthor()) {
/* 284 */       hash = 61 * hash + 13 * this.author.hashCode();
/*     */     }
/* 286 */     if (hasPages()) {
/* 287 */       hash = 61 * hash + 17 * this.pages.hashCode();
/*     */     }
/* 289 */     return original != hash ? CraftMetaBook.class.hashCode() ^ hash : hash;
/*     */   }
/*     */   
/*     */   boolean equalsCommon(CraftMetaItem meta)
/*     */   {
/* 294 */     if (!super.equalsCommon(meta)) {
/* 295 */       return false;
/*     */     }
/* 297 */     if ((meta instanceof CraftMetaBook)) {
/* 298 */       CraftMetaBook that = (CraftMetaBook)meta;
/*     */       
/* 300 */       return (hasTitle() ? (that.hasTitle()) && (this.title.equals(that.title)) : !that.hasTitle()) && 
/* 301 */         (hasAuthor() ? (that.hasAuthor()) && (this.author.equals(that.author)) : !that.hasAuthor()) && 
/* 302 */         (hasPages() ? (that.hasPages()) && (this.pages.equals(that.pages)) : !that.hasPages());
/*     */     }
/* 304 */     return true;
/*     */   }
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta)
/*     */   {
/* 309 */     return (super.notUncommon(meta)) && (((meta instanceof CraftMetaBook)) || (isBookEmpty()));
/*     */   }
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder)
/*     */   {
/* 314 */     super.serialize(builder);
/*     */     
/* 316 */     if (hasTitle()) {
/* 317 */       builder.put(BOOK_TITLE.BUKKIT, this.title);
/*     */     }
/*     */     
/* 320 */     if (hasAuthor()) {
/* 321 */       builder.put(BOOK_AUTHOR.BUKKIT, this.author);
/*     */     }
/*     */     
/* 324 */     if (hasPages()) {
/* 325 */       List<String> pagesString = new ArrayList();
/* 326 */       for (IChatBaseComponent comp : this.pages) {
/* 327 */         pagesString.add(CraftChatMessage.fromComponent(comp));
/*     */       }
/* 329 */       builder.put(BOOK_PAGES.BUKKIT, pagesString);
/*     */     }
/*     */     
/* 332 */     if (this.generation != null) {
/* 333 */       builder.put(GENERATION.BUKKIT, this.generation);
/*     */     }
/*     */     
/* 336 */     return builder;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftMetaBook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */