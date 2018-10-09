/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class CommandReplaceItem
/*     */   extends CommandAbstract
/*     */ {
/*  32 */   private static final Map<String, Integer> a = ;
/*     */   
/*  34 */   static { for (int i = 0; i < 54; i++) {
/*  35 */       a.put("slot.container." + i, Integer.valueOf(i));
/*     */     }
/*  37 */     for (i = 0; i < 9; i++) {
/*  38 */       a.put("slot.hotbar." + i, Integer.valueOf(i));
/*     */     }
/*  40 */     for (i = 0; i < 27; i++) {
/*  41 */       a.put("slot.inventory." + i, Integer.valueOf(9 + i));
/*     */     }
/*  43 */     for (i = 0; i < 27; i++) {
/*  44 */       a.put("slot.enderchest." + i, Integer.valueOf(200 + i));
/*     */     }
/*  46 */     for (i = 0; i < 8; i++) {
/*  47 */       a.put("slot.villager." + i, Integer.valueOf(300 + i));
/*     */     }
/*  49 */     for (i = 0; i < 15; i++) {
/*  50 */       a.put("slot.horse." + i, Integer.valueOf(500 + i));
/*     */     }
/*  52 */     a.put("slot.weapon", Integer.valueOf(99));
/*  53 */     a.put("slot.armor.head", Integer.valueOf(103));
/*  54 */     a.put("slot.armor.chest", Integer.valueOf(102));
/*  55 */     a.put("slot.armor.legs", Integer.valueOf(101));
/*  56 */     a.put("slot.armor.feet", Integer.valueOf(100));
/*  57 */     a.put("slot.horse.saddle", Integer.valueOf(400));
/*  58 */     a.put("slot.horse.armor", Integer.valueOf(401));
/*  59 */     a.put("slot.horse.chest", Integer.valueOf(499));
/*     */   }
/*     */   
/*     */   public String getCommand()
/*     */   {
/*  64 */     return "replaceitem";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  69 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  74 */     return "commands.replaceitem.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  79 */     if (paramArrayOfString.length < 1) {
/*  80 */       throw new ExceptionUsage("commands.replaceitem.usage", new Object[0]);
/*     */     }
/*     */     
/*     */     int i;
/*  84 */     if (paramArrayOfString[0].equals("entity")) {
/*  85 */       i = 0;
/*  86 */     } else if (paramArrayOfString[0].equals("block")) {
/*  87 */       i = 1;
/*     */     } else {
/*  89 */       throw new ExceptionUsage("commands.replaceitem.usage", new Object[0]);
/*     */     }
/*     */     
/*     */     int j;
/*  93 */     if (i != 0) {
/*  94 */       if (paramArrayOfString.length < 6) {
/*  95 */         throw new ExceptionUsage("commands.replaceitem.block.usage", new Object[0]);
/*     */       }
/*  97 */       j = 4;
/*     */     } else {
/*  99 */       if (paramArrayOfString.length < 4) {
/* 100 */         throw new ExceptionUsage("commands.replaceitem.entity.usage", new Object[0]);
/*     */       }
/* 102 */       j = 2;
/*     */     }
/*     */     
/* 105 */     int k = e(paramArrayOfString[(j++)]);
/*     */     Item localItem;
/*     */     try {
/* 108 */       localItem = f(paramICommandListener, paramArrayOfString[j]);
/*     */     } catch (ExceptionInvalidNumber localExceptionInvalidNumber) {
/* 110 */       if (Block.getByName(paramArrayOfString[j]) == Blocks.AIR) {
/* 111 */         localItem = null;
/*     */       } else {
/* 113 */         throw localExceptionInvalidNumber;
/*     */       }
/*     */     }
/* 116 */     j++;
/*     */     
/* 118 */     int m = paramArrayOfString.length > j ? a(paramArrayOfString[(j++)], 1, 64) : 1;
/* 119 */     int n = paramArrayOfString.length > j ? a(paramArrayOfString[(j++)]) : 0;
/* 120 */     ItemStack localItemStack = new ItemStack(localItem, m, n);
/* 121 */     Object localObject; if (paramArrayOfString.length > j) {
/* 122 */       localObject = a(paramICommandListener, paramArrayOfString, j).c();
/*     */       try {
/* 124 */         localItemStack.setTag(MojangsonParser.parse((String)localObject));
/*     */       } catch (MojangsonParseException localMojangsonParseException) {
/* 126 */         throw new CommandException("commands.replaceitem.tagError", new Object[] { localMojangsonParseException.getMessage() });
/*     */       }
/*     */     }
/*     */     
/* 130 */     if (localItemStack.getItem() == null) {
/* 131 */       localItemStack = null;
/*     */     }
/*     */     
/* 134 */     if (i != 0) {
/* 135 */       paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, 0);
/* 136 */       localObject = a(paramICommandListener, paramArrayOfString, 1, false);
/* 137 */       World localWorld = paramICommandListener.getWorld();
/* 138 */       TileEntity localTileEntity = localWorld.getTileEntity((BlockPosition)localObject);
/* 139 */       if ((localTileEntity == null) || (!(localTileEntity instanceof IInventory))) {
/* 140 */         throw new CommandException("commands.replaceitem.noContainer", new Object[] { Integer.valueOf(((BlockPosition)localObject).getX()), Integer.valueOf(((BlockPosition)localObject).getY()), Integer.valueOf(((BlockPosition)localObject).getZ()) });
/*     */       }
/* 142 */       IInventory localIInventory = (IInventory)localTileEntity;
/* 143 */       if ((k >= 0) && (k < localIInventory.getSize())) {
/* 144 */         localIInventory.setItem(k, localItemStack);
/*     */       }
/*     */     } else {
/* 147 */       localObject = b(paramICommandListener, paramArrayOfString[1]);
/* 148 */       paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, 0);
/*     */       
/*     */ 
/*     */ 
/* 152 */       if ((localObject instanceof EntityHuman)) {
/* 153 */         ((EntityHuman)localObject).defaultContainer.b();
/*     */       }
/*     */       
/* 156 */       if (!((Entity)localObject).d(k, localItemStack)) {
/* 157 */         throw new CommandException("commands.replaceitem.failed", new Object[] { Integer.valueOf(k), Integer.valueOf(m), localItemStack == null ? "Air" : localItemStack.C() });
/*     */       }
/*     */       
/*     */ 
/* 161 */       if ((localObject instanceof EntityHuman)) {
/* 162 */         ((EntityHuman)localObject).defaultContainer.b();
/*     */       }
/*     */     }
/*     */     
/* 166 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, m);
/* 167 */     a(paramICommandListener, this, "commands.replaceitem.success", new Object[] { Integer.valueOf(k), Integer.valueOf(m), localItemStack == null ? "Air" : localItemStack.C() });
/*     */   }
/*     */   
/*     */   private int e(String paramString) throws CommandException {
/* 171 */     if (!a.containsKey(paramString)) {
/* 172 */       throw new CommandException("commands.generic.parameter.invalid", new Object[] { paramString });
/*     */     }
/* 174 */     return ((Integer)a.get(paramString)).intValue();
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 180 */     if (paramArrayOfString.length == 1) {
/* 181 */       return a(paramArrayOfString, new String[] { "entity", "block" });
/*     */     }
/* 183 */     if ((paramArrayOfString.length == 2) && (paramArrayOfString[0].equals("entity"))) {
/* 184 */       return a(paramArrayOfString, d());
/*     */     }
/* 186 */     if ((paramArrayOfString.length >= 2) && (paramArrayOfString.length <= 4) && (paramArrayOfString[0].equals("block"))) {
/* 187 */       return a(paramArrayOfString, 1, paramBlockPosition);
/*     */     }
/* 189 */     if (((paramArrayOfString.length == 3) && (paramArrayOfString[0].equals("entity"))) || ((paramArrayOfString.length == 5) && (paramArrayOfString[0].equals("block")))) {
/* 190 */       return a(paramArrayOfString, a.keySet());
/*     */     }
/* 192 */     if (((paramArrayOfString.length == 4) && (paramArrayOfString[0].equals("entity"))) || ((paramArrayOfString.length == 6) && (paramArrayOfString[0].equals("block")))) {
/* 193 */       return a(paramArrayOfString, Item.REGISTRY.keySet());
/*     */     }
/*     */     
/* 196 */     return null;
/*     */   }
/*     */   
/*     */   protected String[] d() {
/* 200 */     return MinecraftServer.getServer().getPlayers();
/*     */   }
/*     */   
/*     */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*     */   {
/* 205 */     return (paramArrayOfString.length > 0) && (paramArrayOfString[0].equals("entity")) && (paramInt == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandReplaceItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */