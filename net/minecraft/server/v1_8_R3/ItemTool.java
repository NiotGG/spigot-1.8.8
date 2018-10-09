/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import com.google.common.collect.Multimap;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemTool
/*    */   extends Item
/*    */ {
/*    */   private Set<Block> c;
/* 15 */   protected float a = 4.0F;
/*    */   private float d;
/*    */   protected Item.EnumToolMaterial b;
/*    */   
/*    */   protected ItemTool(float paramFloat, Item.EnumToolMaterial paramEnumToolMaterial, Set<Block> paramSet)
/*    */   {
/* 21 */     this.b = paramEnumToolMaterial;
/* 22 */     this.c = paramSet;
/* 23 */     this.maxStackSize = 1;
/* 24 */     setMaxDurability(paramEnumToolMaterial.a());
/* 25 */     this.a = paramEnumToolMaterial.b();
/* 26 */     this.d = (paramFloat + paramEnumToolMaterial.c());
/* 27 */     a(CreativeModeTab.i);
/*    */   }
/*    */   
/*    */   public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
/*    */   {
/* 32 */     return this.c.contains(paramBlock) ? this.a : 1.0F;
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2)
/*    */   {
/* 37 */     paramItemStack.damage(2, paramEntityLiving2);
/* 38 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */   public boolean a(ItemStack paramItemStack, World paramWorld, Block paramBlock, BlockPosition paramBlockPosition, EntityLiving paramEntityLiving)
/*    */   {
/* 44 */     if (paramBlock.g(paramWorld, paramBlockPosition) != 0.0D) {
/* 45 */       paramItemStack.damage(1, paramEntityLiving);
/*    */     }
/* 47 */     return true;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Item.EnumToolMaterial g()
/*    */   {
/* 56 */     return this.b;
/*    */   }
/*    */   
/*    */   public int b()
/*    */   {
/* 61 */     return this.b.e();
/*    */   }
/*    */   
/*    */   public String h() {
/* 65 */     return this.b.toString();
/*    */   }
/*    */   
/*    */   public boolean a(ItemStack paramItemStack1, ItemStack paramItemStack2)
/*    */   {
/* 70 */     if (this.b.f() == paramItemStack2.getItem()) {
/* 71 */       return true;
/*    */     }
/* 73 */     return super.a(paramItemStack1, paramItemStack2);
/*    */   }
/*    */   
/*    */   public Multimap<String, AttributeModifier> i()
/*    */   {
/* 78 */     Multimap localMultimap = super.i();
/*    */     
/* 80 */     localMultimap.put(GenericAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(f, "Tool modifier", this.d, 0));
/*    */     
/* 82 */     return localMultimap;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemTool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */