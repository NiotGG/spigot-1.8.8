/*     */ package net.minecraft.server.v1_8_R3;
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
/*     */ public class ItemWrittenBook
/*     */   extends Item
/*     */ {
/*     */   public ItemWrittenBook()
/*     */   {
/*  36 */     c(1);
/*     */   }
/*     */   
/*     */   public static boolean b(NBTTagCompound paramNBTTagCompound) {
/*  40 */     if (!ItemBookAndQuill.b(paramNBTTagCompound)) {
/*  41 */       return false;
/*     */     }
/*     */     
/*  44 */     if (!paramNBTTagCompound.hasKeyOfType("title", 8)) {
/*  45 */       return false;
/*     */     }
/*  47 */     String str = paramNBTTagCompound.getString("title");
/*  48 */     if ((str == null) || (str.length() > 32)) {
/*  49 */       return false;
/*     */     }
/*     */     
/*  52 */     if (!paramNBTTagCompound.hasKeyOfType("author", 8)) {
/*  53 */       return false;
/*     */     }
/*     */     
/*  56 */     return true;
/*     */   }
/*     */   
/*     */   public static int h(ItemStack paramItemStack) {
/*  60 */     return paramItemStack.getTag().getInt("generation");
/*     */   }
/*     */   
/*     */   public String a(ItemStack paramItemStack)
/*     */   {
/*  65 */     if (paramItemStack.hasTag()) {
/*  66 */       NBTTagCompound localNBTTagCompound = paramItemStack.getTag();
/*     */       
/*  68 */       String str = localNBTTagCompound.getString("title");
/*  69 */       if (!UtilColor.b(str)) {
/*  70 */         return str;
/*     */       }
/*     */     }
/*  73 */     return super.a(paramItemStack);
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
/*     */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*     */   {
/*  92 */     if (!paramWorld.isClientSide) {
/*  93 */       a(paramItemStack, paramEntityHuman);
/*     */     }
/*  95 */     paramEntityHuman.openBook(paramItemStack);
/*  96 */     paramEntityHuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/*  97 */     return paramItemStack;
/*     */   }
/*     */   
/*     */   private void a(ItemStack paramItemStack, EntityHuman paramEntityHuman) {
/* 101 */     if ((paramItemStack == null) || (paramItemStack.getTag() == null)) {
/* 102 */       return;
/*     */     }
/* 104 */     NBTTagCompound localNBTTagCompound = paramItemStack.getTag();
/* 105 */     if (localNBTTagCompound.getBoolean("resolved")) {
/* 106 */       return;
/*     */     }
/* 108 */     localNBTTagCompound.setBoolean("resolved", true);
/* 109 */     if (!b(localNBTTagCompound)) {
/* 110 */       return;
/*     */     }
/* 112 */     NBTTagList localNBTTagList = localNBTTagCompound.getList("pages", 8);
/* 113 */     for (int i = 0; i < localNBTTagList.size(); i++) {
/* 114 */       String str = localNBTTagList.getString(i);
/*     */       Object localObject;
/*     */       try {
/* 117 */         localObject = IChatBaseComponent.ChatSerializer.a(str);
/* 118 */         localObject = ChatComponentUtils.filterForDisplay(paramEntityHuman, (IChatBaseComponent)localObject, paramEntityHuman);
/*     */       } catch (Exception localException) {
/* 120 */         localObject = new ChatComponentText(str);
/*     */       }
/* 122 */       localNBTTagList.a(i, new NBTTagString(IChatBaseComponent.ChatSerializer.a((IChatBaseComponent)localObject)));
/*     */     }
/* 124 */     localNBTTagCompound.set("pages", localNBTTagList);
/* 125 */     if (((paramEntityHuman instanceof EntityPlayer)) && (paramEntityHuman.bZ() == paramItemStack)) {
/* 126 */       Slot localSlot = paramEntityHuman.activeContainer.getSlot(paramEntityHuman.inventory, paramEntityHuman.inventory.itemInHandIndex);
/* 127 */       ((EntityPlayer)paramEntityHuman).playerConnection.sendPacket(new PacketPlayOutSetSlot(0, localSlot.rawSlotIndex, paramItemStack));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemWrittenBook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */