/*     */ package org.bukkit.craftbukkit.v1_8_R3.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_8_R3.IChatBaseComponent;
/*     */ import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagCompound;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagList;
/*     */ import net.minecraft.server.v1_8_R3.NBTTagString;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*     */ import org.bukkit.inventory.meta.BookMeta;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @DelegateDeserialization(CraftMetaItem.SerializableMeta.class)
/*     */ class CraftMetaBookSigned
/*     */   extends CraftMetaBook
/*     */   implements BookMeta
/*     */ {
/*     */   CraftMetaBookSigned(CraftMetaItem meta)
/*     */   {
/*  25 */     super(meta);
/*     */   }
/*     */   
/*     */   CraftMetaBookSigned(NBTTagCompound tag) {
/*  29 */     super(tag, false);
/*     */     
/*  31 */     boolean resolved = true;
/*  32 */     if (tag.hasKey(RESOLVED.NBT)) {
/*  33 */       resolved = tag.getBoolean(RESOLVED.NBT);
/*     */     }
/*     */     
/*  36 */     if (tag.hasKey(BOOK_PAGES.NBT)) {
/*  37 */       NBTTagList pages = tag.getList(BOOK_PAGES.NBT, 8);
/*     */       
/*  39 */       for (int i = 0; i < pages.size(); i++) {
/*  40 */         String page = pages.getString(i);
/*  41 */         if (resolved) {
/*     */           try {
/*  43 */             this.pages.add(IChatBaseComponent.ChatSerializer.a(page));
/*     */ 
/*     */           }
/*     */           catch (Exception localException) {}
/*     */         }
/*     */         else
/*  49 */           addPage(new String[] { page });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   CraftMetaBookSigned(Map<String, Object> map) {
/*  55 */     super(map);
/*     */   }
/*     */   
/*     */   void applyToItem(NBTTagCompound itemData)
/*     */   {
/*  60 */     super.applyToItem(itemData, false);
/*     */     
/*  62 */     if (hasTitle()) {
/*  63 */       itemData.setString(BOOK_TITLE.NBT, this.title);
/*     */     } else {
/*  65 */       itemData.setString(BOOK_TITLE.NBT, " ");
/*     */     }
/*     */     
/*  68 */     if (hasAuthor()) {
/*  69 */       itemData.setString(BOOK_AUTHOR.NBT, this.author);
/*     */     } else {
/*  71 */       itemData.setString(BOOK_AUTHOR.NBT, " ");
/*     */     }
/*     */     
/*  74 */     if (hasPages()) {
/*  75 */       NBTTagList list = new NBTTagList();
/*  76 */       for (IChatBaseComponent page : this.pages) {
/*  77 */         list.add(new NBTTagString(
/*  78 */           IChatBaseComponent.ChatSerializer.a(page)));
/*     */       }
/*     */       
/*  81 */       itemData.set(BOOK_PAGES.NBT, list);
/*     */     }
/*  83 */     itemData.setBoolean(RESOLVED.NBT, true);
/*     */     
/*  85 */     if (this.generation != null) {
/*  86 */       itemData.setInt(GENERATION.NBT, this.generation.intValue());
/*     */     } else {
/*  88 */       itemData.setInt(GENERATION.NBT, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   boolean isEmpty()
/*     */   {
/*  94 */     return super.isEmpty();
/*     */   }
/*     */   
/*     */   boolean applicableTo(Material type)
/*     */   {
/*  99 */     switch (type) {
/*     */     case SPRUCE_WOOD_STAIRS: 
/*     */     case STAINED_CLAY: 
/* 102 */       return true;
/*     */     }
/* 104 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public CraftMetaBookSigned clone()
/*     */   {
/* 110 */     CraftMetaBookSigned meta = (CraftMetaBookSigned)super.clone();
/* 111 */     return meta;
/*     */   }
/*     */   
/*     */   int applyHash()
/*     */   {
/*     */     int original;
/* 117 */     int hash = original = super.applyHash();
/* 118 */     return original != hash ? CraftMetaBookSigned.class.hashCode() ^ hash : hash;
/*     */   }
/*     */   
/*     */   boolean equalsCommon(CraftMetaItem meta)
/*     */   {
/* 123 */     return super.equalsCommon(meta);
/*     */   }
/*     */   
/*     */   boolean notUncommon(CraftMetaItem meta)
/*     */   {
/* 128 */     return (super.notUncommon(meta)) && (((meta instanceof CraftMetaBookSigned)) || (isBookEmpty()));
/*     */   }
/*     */   
/*     */   ImmutableMap.Builder<String, Object> serialize(ImmutableMap.Builder<String, Object> builder)
/*     */   {
/* 133 */     super.serialize(builder);
/* 134 */     return builder;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\inventory\CraftMetaBookSigned.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */