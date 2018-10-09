/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import org.bukkit.event.entity.EntityCombustEvent;
/*     */ 
/*     */ public class ItemBow extends Item
/*     */ {
/*   7 */   public static final String[] a = { "pulling_0", "pulling_1", "pulling_2" };
/*     */   
/*     */   public ItemBow() {
/*  10 */     this.maxStackSize = 1;
/*  11 */     setMaxDurability(384);
/*  12 */     a(CreativeModeTab.j);
/*     */   }
/*     */   
/*     */   public void a(ItemStack itemstack, World world, EntityHuman entityhuman, int i) {
/*  16 */     boolean flag = (entityhuman.abilities.canInstantlyBuild) || (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_INFINITE.id, itemstack) > 0);
/*     */     
/*  18 */     if ((flag) || (entityhuman.inventory.b(Items.ARROW))) {
/*  19 */       int j = d(itemstack) - i;
/*  20 */       float f = j / 20.0F;
/*     */       
/*  22 */       f = (f * f + f * 2.0F) / 3.0F;
/*  23 */       if (f < 0.1D) {
/*  24 */         return;
/*     */       }
/*     */       
/*  27 */       if (f > 1.0F) {
/*  28 */         f = 1.0F;
/*     */       }
/*     */       
/*  31 */       EntityArrow entityarrow = new EntityArrow(world, entityhuman, f * 2.0F);
/*     */       
/*  33 */       if (f == 1.0F) {
/*  34 */         entityarrow.setCritical(true);
/*     */       }
/*     */       
/*  37 */       int k = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, itemstack);
/*     */       
/*  39 */       if (k > 0) {
/*  40 */         entityarrow.b(entityarrow.j() + k * 0.5D + 0.5D);
/*     */       }
/*     */       
/*  43 */       int l = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, itemstack);
/*     */       
/*  45 */       if (l > 0) {
/*  46 */         entityarrow.setKnockbackStrength(l);
/*     */       }
/*     */       
/*  49 */       if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, itemstack) > 0)
/*     */       {
/*  51 */         EntityCombustEvent event = new EntityCombustEvent(entityarrow.getBukkitEntity(), 100);
/*  52 */         entityarrow.world.getServer().getPluginManager().callEvent(event);
/*     */         
/*  54 */         if (!event.isCancelled()) {
/*  55 */           entityarrow.setOnFire(event.getDuration());
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*  61 */       org.bukkit.event.entity.EntityShootBowEvent event = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callEntityShootBowEvent(entityhuman, itemstack, entityarrow, f);
/*  62 */       if (event.isCancelled()) {
/*  63 */         event.getProjectile().remove();
/*  64 */         return;
/*     */       }
/*     */       
/*  67 */       if (event.getProjectile() == entityarrow.getBukkitEntity()) {
/*  68 */         world.addEntity(entityarrow);
/*     */       }
/*     */       
/*     */ 
/*  72 */       itemstack.damage(1, entityhuman);
/*  73 */       world.makeSound(entityhuman, "random.bow", 1.0F, 1.0F / (g.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
/*  74 */       if (flag) {
/*  75 */         entityarrow.fromPlayer = 2;
/*     */       } else {
/*  77 */         entityhuman.inventory.a(Items.ARROW);
/*     */       }
/*     */       
/*  80 */       entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public ItemStack b(ItemStack itemstack, World world, EntityHuman entityhuman)
/*     */   {
/*  89 */     return itemstack;
/*     */   }
/*     */   
/*     */   public int d(ItemStack itemstack) {
/*  93 */     return 72000;
/*     */   }
/*     */   
/*     */   public EnumAnimation e(ItemStack itemstack) {
/*  97 */     return EnumAnimation.BOW;
/*     */   }
/*     */   
/*     */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman) {
/* 101 */     if ((entityhuman.abilities.canInstantlyBuild) || (entityhuman.inventory.b(Items.ARROW))) {
/* 102 */       entityhuman.a(itemstack, d(itemstack));
/*     */     }
/*     */     
/* 105 */     return itemstack;
/*     */   }
/*     */   
/*     */   public int b() {
/* 109 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemBow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */