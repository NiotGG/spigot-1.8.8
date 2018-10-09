/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemSword
/*     */   extends Item
/*     */ {
/*     */   private float a;
/*     */   private final Item.EnumToolMaterial b;
/*     */   
/*     */   public ItemSword(Item.EnumToolMaterial paramEnumToolMaterial)
/*     */   {
/*  19 */     this.b = paramEnumToolMaterial;
/*  20 */     this.maxStackSize = 1;
/*  21 */     setMaxDurability(paramEnumToolMaterial.a());
/*  22 */     a(CreativeModeTab.j);
/*     */     
/*  24 */     this.a = (4.0F + paramEnumToolMaterial.c());
/*     */   }
/*     */   
/*     */   public float g() {
/*  28 */     return this.b.c();
/*     */   }
/*     */   
/*     */   public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
/*     */   {
/*  33 */     if (paramBlock == Blocks.WEB)
/*     */     {
/*  35 */       return 15.0F;
/*     */     }
/*     */     
/*     */ 
/*  39 */     Material localMaterial = paramBlock.getMaterial();
/*  40 */     if ((localMaterial == Material.PLANT) || (localMaterial == Material.REPLACEABLE_PLANT) || (localMaterial == Material.CORAL) || (localMaterial == Material.LEAVES) || (localMaterial == Material.PUMPKIN)) {
/*  41 */       return 1.5F;
/*     */     }
/*  43 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2)
/*     */   {
/*  48 */     paramItemStack.damage(1, paramEntityLiving2);
/*  49 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean a(ItemStack paramItemStack, World paramWorld, Block paramBlock, BlockPosition paramBlockPosition, EntityLiving paramEntityLiving)
/*     */   {
/*  55 */     if (paramBlock.g(paramWorld, paramBlockPosition) != 0.0D) {
/*  56 */       paramItemStack.damage(2, paramEntityLiving);
/*     */     }
/*  58 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public EnumAnimation e(ItemStack paramItemStack)
/*     */   {
/*  68 */     return EnumAnimation.BLOCK;
/*     */   }
/*     */   
/*     */   public int d(ItemStack paramItemStack)
/*     */   {
/*  73 */     return 72000;
/*     */   }
/*     */   
/*     */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
/*     */   {
/*  78 */     paramEntityHuman.a(paramItemStack, d(paramItemStack));
/*  79 */     return paramItemStack;
/*     */   }
/*     */   
/*     */   public boolean canDestroySpecialBlock(Block paramBlock)
/*     */   {
/*  84 */     return paramBlock == Blocks.WEB;
/*     */   }
/*     */   
/*     */   public int b()
/*     */   {
/*  89 */     return this.b.e();
/*     */   }
/*     */   
/*     */   public String h() {
/*  93 */     return this.b.toString();
/*     */   }
/*     */   
/*     */   public boolean a(ItemStack paramItemStack1, ItemStack paramItemStack2)
/*     */   {
/*  98 */     if (this.b.f() == paramItemStack2.getItem()) {
/*  99 */       return true;
/*     */     }
/* 101 */     return super.a(paramItemStack1, paramItemStack2);
/*     */   }
/*     */   
/*     */   public Multimap<String, AttributeModifier> i()
/*     */   {
/* 106 */     Multimap localMultimap = super.i();
/*     */     
/* 108 */     localMultimap.put(GenericAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(f, "Weapon modifier", this.a, 0));
/*     */     
/* 110 */     return localMultimap;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemSword.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */