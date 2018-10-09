/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
/*     */ import org.bukkit.event.block.BlockDispenseEvent;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class DispenseBehaviorItem implements IDispenseBehavior
/*     */ {
/*     */   public final ItemStack a(ISourceBlock isourceblock, ItemStack itemstack)
/*     */   {
/*  13 */     ItemStack itemstack1 = b(isourceblock, itemstack);
/*     */     
/*  15 */     a(isourceblock);
/*  16 */     a(isourceblock, BlockDispenser.b(isourceblock.f()));
/*  17 */     return itemstack1;
/*     */   }
/*     */   
/*     */   protected ItemStack b(ISourceBlock isourceblock, ItemStack itemstack) {
/*  21 */     EnumDirection enumdirection = BlockDispenser.b(isourceblock.f());
/*  22 */     BlockDispenser.a(isourceblock);
/*  23 */     ItemStack itemstack1 = itemstack.cloneAndSubtract(1);
/*     */     
/*     */ 
/*  26 */     if (!a(isourceblock.getWorld(), itemstack1, 6, enumdirection, isourceblock)) {
/*  27 */       itemstack.count += 1;
/*     */     }
/*     */     
/*  30 */     return itemstack;
/*     */   }
/*     */   
/*     */   public static boolean a(World world, ItemStack itemstack, int i, EnumDirection enumdirection, ISourceBlock isourceblock)
/*     */   {
/*  35 */     IPosition iposition = BlockDispenser.a(isourceblock);
/*     */     
/*  37 */     double d0 = iposition.getX();
/*  38 */     double d1 = iposition.getY();
/*  39 */     double d2 = iposition.getZ();
/*     */     
/*  41 */     if (enumdirection.k() == EnumDirection.EnumAxis.Y) {
/*  42 */       d1 -= 0.125D;
/*     */     } else {
/*  44 */       d1 -= 0.15625D;
/*     */     }
/*     */     
/*  47 */     EntityItem entityitem = new EntityItem(world, d0, d1, d2, itemstack);
/*  48 */     double d3 = world.random.nextDouble() * 0.1D + 0.2D;
/*     */     
/*  50 */     entityitem.motX = (enumdirection.getAdjacentX() * d3);
/*  51 */     entityitem.motY = 0.20000000298023224D;
/*  52 */     entityitem.motZ = (enumdirection.getAdjacentZ() * d3);
/*  53 */     entityitem.motX += world.random.nextGaussian() * 0.007499999832361937D * i;
/*  54 */     entityitem.motY += world.random.nextGaussian() * 0.007499999832361937D * i;
/*  55 */     entityitem.motZ += world.random.nextGaussian() * 0.007499999832361937D * i;
/*     */     
/*     */ 
/*  58 */     Block block = world.getWorld().getBlockAt(isourceblock.getBlockPosition().getX(), isourceblock.getBlockPosition().getY(), isourceblock.getBlockPosition().getZ());
/*  59 */     CraftItemStack craftItem = CraftItemStack.asCraftMirror(itemstack);
/*     */     
/*  61 */     BlockDispenseEvent event = new BlockDispenseEvent(block, craftItem.clone(), new Vector(entityitem.motX, entityitem.motY, entityitem.motZ));
/*  62 */     if (!BlockDispenser.eventFired) {
/*  63 */       world.getServer().getPluginManager().callEvent(event);
/*     */     }
/*     */     
/*  66 */     if (event.isCancelled()) {
/*  67 */       return false;
/*     */     }
/*     */     
/*  70 */     entityitem.setItemStack(CraftItemStack.asNMSCopy(event.getItem()));
/*  71 */     entityitem.motX = event.getVelocity().getX();
/*  72 */     entityitem.motY = event.getVelocity().getY();
/*  73 */     entityitem.motZ = event.getVelocity().getZ();
/*     */     
/*  75 */     if (!event.getItem().getType().equals(craftItem.getType()))
/*     */     {
/*  77 */       ItemStack eventStack = CraftItemStack.asNMSCopy(event.getItem());
/*  78 */       IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(eventStack.getItem());
/*  79 */       if ((idispensebehavior != IDispenseBehavior.NONE) && (idispensebehavior.getClass() != DispenseBehaviorItem.class)) {
/*  80 */         idispensebehavior.a(isourceblock, eventStack);
/*     */       } else {
/*  82 */         world.addEntity(entityitem);
/*     */       }
/*  84 */       return false;
/*     */     }
/*     */     
/*  87 */     world.addEntity(entityitem);
/*     */     
/*  89 */     return true;
/*     */   }
/*     */   
/*     */   protected void a(ISourceBlock isourceblock)
/*     */   {
/*  94 */     isourceblock.getWorld().triggerEffect(1000, isourceblock.getBlockPosition(), 0);
/*     */   }
/*     */   
/*     */   protected void a(ISourceBlock isourceblock, EnumDirection enumdirection) {
/*  98 */     isourceblock.getWorld().triggerEffect(2000, isourceblock.getBlockPosition(), a(enumdirection));
/*     */   }
/*     */   
/*     */   private int a(EnumDirection enumdirection) {
/* 102 */     return enumdirection.getAdjacentX() + 1 + (enumdirection.getAdjacentZ() + 1) * 3;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\DispenseBehaviorItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */