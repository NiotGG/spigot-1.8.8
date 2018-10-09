/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.event.player.PlayerBucketFillEvent;
/*     */ 
/*     */ public class EntityCow extends EntityAnimal
/*     */ {
/*     */   public EntityCow(World world)
/*     */   {
/*  11 */     super(world);
/*  12 */     setSize(0.9F, 1.3F);
/*  13 */     ((Navigation)getNavigation()).a(true);
/*  14 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*  15 */     this.goalSelector.a(1, new PathfinderGoalPanic(this, 2.0D));
/*  16 */     this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
/*  17 */     this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.25D, Items.WHEAT, false));
/*  18 */     this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.25D));
/*  19 */     this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
/*  20 */     this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
/*  21 */     this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  25 */     super.initAttributes();
/*  26 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
/*  27 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.20000000298023224D);
/*     */   }
/*     */   
/*     */   protected String z() {
/*  31 */     return "mob.cow.say";
/*     */   }
/*     */   
/*     */   protected String bo() {
/*  35 */     return "mob.cow.hurt";
/*     */   }
/*     */   
/*     */   protected String bp() {
/*  39 */     return "mob.cow.hurt";
/*     */   }
/*     */   
/*     */   protected void a(BlockPosition blockposition, Block block) {
/*  43 */     makeSound("mob.cow.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected float bB() {
/*  47 */     return 0.4F;
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/*  51 */     return Items.LEATHER;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/*  55 */     int j = this.random.nextInt(3) + this.random.nextInt(1 + i);
/*     */     
/*     */ 
/*     */ 
/*  59 */     for (int k = 0; k < j; k++) {
/*  60 */       a(Items.LEATHER, 1);
/*     */     }
/*     */     
/*  63 */     j = this.random.nextInt(3) + 1 + this.random.nextInt(1 + i);
/*     */     
/*  65 */     for (k = 0; k < j; k++) {
/*  66 */       if (isBurning()) {
/*  67 */         a(Items.COOKED_BEEF, 1);
/*     */       } else {
/*  69 */         a(Items.BEEF, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman)
/*     */   {
/*  76 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/*  78 */     if ((itemstack != null) && (itemstack.getItem() == Items.BUCKET) && (!entityhuman.abilities.canInstantlyBuild) && (!isBaby()))
/*     */     {
/*  80 */       Location loc = getBukkitEntity().getLocation();
/*  81 */       PlayerBucketFillEvent event = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callPlayerBucketFillEvent(entityhuman, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), null, itemstack, Items.MILK_BUCKET);
/*     */       
/*  83 */       if (event.isCancelled()) {
/*  84 */         return false;
/*     */       }
/*     */       
/*  87 */       ItemStack result = org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack.asNMSCopy(event.getItemStack());
/*  88 */       if (--itemstack.count <= 0) {
/*  89 */         entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, result);
/*  90 */       } else if (!entityhuman.inventory.pickup(result)) {
/*  91 */         entityhuman.drop(result, false);
/*     */       }
/*     */       
/*     */ 
/*  95 */       return true;
/*     */     }
/*  97 */     return super.a(entityhuman);
/*     */   }
/*     */   
/*     */   public EntityCow b(EntityAgeable entityageable)
/*     */   {
/* 102 */     return new EntityCow(this.world);
/*     */   }
/*     */   
/*     */   public float getHeadHeight() {
/* 106 */     return this.length;
/*     */   }
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 110 */     return b(entityageable);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityCow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */