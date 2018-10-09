/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
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
/*     */ public class CommandEnchant
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  22 */     return "enchant";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  27 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  32 */     return "commands.enchant.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  37 */     if (paramArrayOfString.length < 2) {
/*  38 */       throw new ExceptionUsage("commands.enchant.usage", new Object[0]);
/*     */     }
/*     */     
/*  41 */     EntityPlayer localEntityPlayer = a(paramICommandListener, paramArrayOfString[0]);
/*  42 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, 0);
/*     */     int i;
/*     */     try
/*     */     {
/*  46 */       i = a(paramArrayOfString[1], 0);
/*     */     } catch (ExceptionInvalidNumber localExceptionInvalidNumber) {
/*  48 */       localObject = Enchantment.getByName(paramArrayOfString[1]);
/*  49 */       if (localObject == null) {
/*  50 */         throw localExceptionInvalidNumber;
/*     */       }
/*  52 */       i = ((Enchantment)localObject).id;
/*     */     }
/*  54 */     int j = 1;
/*     */     
/*  56 */     Object localObject = localEntityPlayer.bZ();
/*  57 */     if (localObject == null) {
/*  58 */       throw new CommandException("commands.enchant.noItem", new Object[0]);
/*     */     }
/*     */     
/*  61 */     Enchantment localEnchantment1 = Enchantment.getById(i);
/*  62 */     if (localEnchantment1 == null) {
/*  63 */       throw new ExceptionInvalidNumber("commands.enchant.notFound", new Object[] { Integer.valueOf(i) });
/*     */     }
/*     */     
/*  66 */     if (!localEnchantment1.canEnchant((ItemStack)localObject)) {
/*  67 */       throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
/*     */     }
/*     */     
/*  70 */     if (paramArrayOfString.length >= 3) {
/*  71 */       j = a(paramArrayOfString[2], localEnchantment1.getStartLevel(), localEnchantment1.getMaxLevel());
/*     */     }
/*     */     
/*  74 */     if (((ItemStack)localObject).hasTag()) {
/*  75 */       NBTTagList localNBTTagList = ((ItemStack)localObject).getEnchantments();
/*  76 */       if (localNBTTagList != null) {
/*  77 */         for (int k = 0; k < localNBTTagList.size(); k++) {
/*  78 */           int m = localNBTTagList.get(k).getShort("id");
/*     */           
/*  80 */           if (Enchantment.getById(m) != null) {
/*  81 */             Enchantment localEnchantment2 = Enchantment.getById(m);
/*  82 */             if (!localEnchantment2.a(localEnchantment1)) {
/*  83 */               throw new CommandException("commands.enchant.cantCombine", new Object[] { localEnchantment1.d(j), localEnchantment2.d(localNBTTagList.get(k).getShort("lvl")) });
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  90 */     ((ItemStack)localObject).addEnchantment(localEnchantment1, j);
/*  91 */     a(paramICommandListener, this, "commands.enchant.success", new Object[0]);
/*  92 */     paramICommandListener.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ITEMS, 1);
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/*  98 */     if (paramArrayOfString.length == 1) {
/*  99 */       return a(paramArrayOfString, d());
/*     */     }
/* 101 */     if (paramArrayOfString.length == 2) {
/* 102 */       return a(paramArrayOfString, Enchantment.getEffects());
/*     */     }
/*     */     
/* 105 */     return null;
/*     */   }
/*     */   
/*     */   protected String[] d() {
/* 109 */     return MinecraftServer.getServer().getPlayers();
/*     */   }
/*     */   
/*     */   public boolean isListStart(String[] paramArrayOfString, int paramInt)
/*     */   {
/* 114 */     return paramInt == 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandEnchant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */