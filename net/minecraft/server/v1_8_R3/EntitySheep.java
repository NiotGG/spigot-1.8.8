/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.entity.SheepRegrowWoolEvent;
/*     */ import org.bukkit.event.player.PlayerShearEntityEvent;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class EntitySheep extends EntityAnimal
/*     */ {
/*  15 */   private final InventoryCrafting bm = new InventoryCrafting(new Container() {
/*     */     public boolean a(EntityHuman entityhuman) {
/*  17 */       return false;
/*     */     }
/*     */     
/*     */ 
/*     */     public InventoryView getBukkitView()
/*     */     {
/*  23 */       return null;
/*     */     }
/*  15 */   }, 
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
/*  26 */     2, 1);
/*  27 */   private static final Map<EnumColor, float[]> bo = Maps.newEnumMap(EnumColor.class);
/*     */   private int bp;
/*  29 */   private PathfinderGoalEatTile bq = new PathfinderGoalEatTile(this);
/*     */   
/*     */   public static float[] a(EnumColor enumcolor) {
/*  32 */     return (float[])bo.get(enumcolor);
/*     */   }
/*     */   
/*     */   public EntitySheep(World world) {
/*  36 */     super(world);
/*  37 */     setSize(0.9F, 1.3F);
/*  38 */     ((Navigation)getNavigation()).a(true);
/*  39 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*  40 */     this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));
/*  41 */     this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
/*  42 */     this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.1D, Items.WHEAT, false));
/*  43 */     this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.1D));
/*  44 */     this.goalSelector.a(5, this.bq);
/*  45 */     this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
/*  46 */     this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
/*  47 */     this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
/*  48 */     this.bm.setItem(0, new ItemStack(Items.DYE, 1, 0));
/*  49 */     this.bm.setItem(1, new ItemStack(Items.DYE, 1, 0));
/*  50 */     this.bm.resultInventory = new InventoryCraftResult();
/*     */   }
/*     */   
/*     */   protected void E() {
/*  54 */     this.bp = this.bq.f();
/*  55 */     super.E();
/*     */   }
/*     */   
/*     */   public void m() {
/*  59 */     if (this.world.isClientSide) {
/*  60 */       this.bp = Math.max(0, this.bp - 1);
/*     */     }
/*     */     
/*  63 */     super.m();
/*     */   }
/*     */   
/*     */   protected void initAttributes() {
/*  67 */     super.initAttributes();
/*  68 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
/*  69 */     getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.23000000417232513D);
/*     */   }
/*     */   
/*     */   protected void h() {
/*  73 */     super.h();
/*  74 */     this.datawatcher.a(16, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/*  78 */     if (!isSheared()) {
/*  79 */       a(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, getColor().getColorIndex()), 0.0F);
/*     */     }
/*     */     
/*  82 */     int j = this.random.nextInt(2) + 1 + this.random.nextInt(1 + i);
/*     */     
/*  84 */     for (int k = 0; k < j; k++) {
/*  85 */       if (isBurning()) {
/*  86 */         a(Items.COOKED_MUTTON, 1);
/*     */       } else {
/*  88 */         a(Items.MUTTON, 1);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected Item getLoot()
/*     */   {
/*  95 */     return Item.getItemOf(Blocks.WOOL);
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  99 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/* 101 */     if ((itemstack != null) && (itemstack.getItem() == Items.SHEARS) && (!isSheared()) && (!isBaby())) {
/* 102 */       if (!this.world.isClientSide)
/*     */       {
/* 104 */         PlayerShearEntityEvent event = new PlayerShearEntityEvent((Player)entityhuman.getBukkitEntity(), getBukkitEntity());
/* 105 */         this.world.getServer().getPluginManager().callEvent(event);
/*     */         
/* 107 */         if (event.isCancelled()) {
/* 108 */           return false;
/*     */         }
/*     */         
/*     */ 
/* 112 */         setSheared(true);
/* 113 */         int i = 1 + this.random.nextInt(3);
/*     */         
/* 115 */         for (int j = 0; j < i; j++) {
/* 116 */           EntityItem entityitem = a(new ItemStack(Item.getItemOf(Blocks.WOOL), 1, getColor().getColorIndex()), 1.0F);
/*     */           
/* 118 */           entityitem.motY += this.random.nextFloat() * 0.05F;
/* 119 */           entityitem.motX += (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
/* 120 */           entityitem.motZ += (this.random.nextFloat() - this.random.nextFloat()) * 0.1F;
/*     */         }
/*     */       }
/*     */       
/* 124 */       itemstack.damage(1, entityhuman);
/* 125 */       makeSound("mob.sheep.shear", 1.0F, 1.0F);
/*     */     }
/*     */     
/* 128 */     return super.a(entityhuman);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 132 */     super.b(nbttagcompound);
/* 133 */     nbttagcompound.setBoolean("Sheared", isSheared());
/* 134 */     nbttagcompound.setByte("Color", (byte)getColor().getColorIndex());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 138 */     super.a(nbttagcompound);
/* 139 */     setSheared(nbttagcompound.getBoolean("Sheared"));
/* 140 */     setColor(EnumColor.fromColorIndex(nbttagcompound.getByte("Color")));
/*     */   }
/*     */   
/*     */   protected String z() {
/* 144 */     return "mob.sheep.say";
/*     */   }
/*     */   
/*     */   protected String bo() {
/* 148 */     return "mob.sheep.say";
/*     */   }
/*     */   
/*     */   protected String bp() {
/* 152 */     return "mob.sheep.say";
/*     */   }
/*     */   
/*     */   protected void a(BlockPosition blockposition, Block block) {
/* 156 */     makeSound("mob.sheep.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   public EnumColor getColor() {
/* 160 */     return EnumColor.fromColorIndex(this.datawatcher.getByte(16) & 0xF);
/*     */   }
/*     */   
/*     */   public void setColor(EnumColor enumcolor) {
/* 164 */     byte b0 = this.datawatcher.getByte(16);
/*     */     
/* 166 */     this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xF0 | enumcolor.getColorIndex() & 0xF)));
/*     */   }
/*     */   
/*     */   public boolean isSheared() {
/* 170 */     return (this.datawatcher.getByte(16) & 0x10) != 0;
/*     */   }
/*     */   
/*     */   public void setSheared(boolean flag) {
/* 174 */     byte b0 = this.datawatcher.getByte(16);
/*     */     
/* 176 */     if (flag) {
/* 177 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 0x10)));
/*     */     } else {
/* 179 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFEF)));
/*     */     }
/*     */   }
/*     */   
/*     */   public static EnumColor a(Random random)
/*     */   {
/* 185 */     int i = random.nextInt(100);
/*     */     
/* 187 */     return random.nextInt(500) == 0 ? EnumColor.PINK : i < 18 ? EnumColor.BROWN : i < 15 ? EnumColor.SILVER : i < 10 ? EnumColor.GRAY : i < 5 ? EnumColor.BLACK : EnumColor.WHITE;
/*     */   }
/*     */   
/*     */   public EntitySheep b(EntityAgeable entityageable) {
/* 191 */     EntitySheep entitysheep = (EntitySheep)entityageable;
/* 192 */     EntitySheep entitysheep1 = new EntitySheep(this.world);
/*     */     
/* 194 */     entitysheep1.setColor(a(this, entitysheep));
/* 195 */     return entitysheep1;
/*     */   }
/*     */   
/*     */   public void v()
/*     */   {
/* 200 */     SheepRegrowWoolEvent event = new SheepRegrowWoolEvent((org.bukkit.entity.Sheep)getBukkitEntity());
/* 201 */     this.world.getServer().getPluginManager().callEvent(event);
/*     */     
/* 203 */     if (!event.isCancelled()) {
/* 204 */       setSheared(false);
/*     */     }
/*     */     
/* 207 */     if (isBaby()) {
/* 208 */       setAge(60);
/*     */     }
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity)
/*     */   {
/* 214 */     groupdataentity = super.prepare(difficultydamagescaler, groupdataentity);
/* 215 */     setColor(a(this.world.random));
/* 216 */     return groupdataentity;
/*     */   }
/*     */   
/*     */   private EnumColor a(EntityAnimal entityanimal, EntityAnimal entityanimal1) {
/* 220 */     int i = ((EntitySheep)entityanimal).getColor().getInvColorIndex();
/* 221 */     int j = ((EntitySheep)entityanimal1).getColor().getInvColorIndex();
/*     */     
/* 223 */     this.bm.getItem(0).setData(i);
/* 224 */     this.bm.getItem(1).setData(j);
/* 225 */     ItemStack itemstack = CraftingManager.getInstance().craft(this.bm, ((EntitySheep)entityanimal).world);
/*     */     int k;
/*     */     int k;
/* 228 */     if ((itemstack != null) && (itemstack.getItem() == Items.DYE)) {
/* 229 */       k = itemstack.getData();
/*     */     } else {
/* 231 */       k = this.world.random.nextBoolean() ? i : j;
/*     */     }
/*     */     
/* 234 */     return EnumColor.fromInvColorIndex(k);
/*     */   }
/*     */   
/*     */   public float getHeadHeight() {
/* 238 */     return 0.95F * this.length;
/*     */   }
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 242 */     return b(entityageable);
/*     */   }
/*     */   
/*     */   static {
/* 246 */     bo.put(EnumColor.WHITE, new float[] { 1.0F, 1.0F, 1.0F });
/* 247 */     bo.put(EnumColor.ORANGE, new float[] { 0.85F, 0.5F, 0.2F });
/* 248 */     bo.put(EnumColor.MAGENTA, new float[] { 0.7F, 0.3F, 0.85F });
/* 249 */     bo.put(EnumColor.LIGHT_BLUE, new float[] { 0.4F, 0.6F, 0.85F });
/* 250 */     bo.put(EnumColor.YELLOW, new float[] { 0.9F, 0.9F, 0.2F });
/* 251 */     bo.put(EnumColor.LIME, new float[] { 0.5F, 0.8F, 0.1F });
/* 252 */     bo.put(EnumColor.PINK, new float[] { 0.95F, 0.5F, 0.65F });
/* 253 */     bo.put(EnumColor.GRAY, new float[] { 0.3F, 0.3F, 0.3F });
/* 254 */     bo.put(EnumColor.SILVER, new float[] { 0.6F, 0.6F, 0.6F });
/* 255 */     bo.put(EnumColor.CYAN, new float[] { 0.3F, 0.5F, 0.6F });
/* 256 */     bo.put(EnumColor.PURPLE, new float[] { 0.5F, 0.25F, 0.7F });
/* 257 */     bo.put(EnumColor.BLUE, new float[] { 0.2F, 0.3F, 0.7F });
/* 258 */     bo.put(EnumColor.BROWN, new float[] { 0.4F, 0.3F, 0.2F });
/* 259 */     bo.put(EnumColor.GREEN, new float[] { 0.4F, 0.5F, 0.2F });
/* 260 */     bo.put(EnumColor.RED, new float[] { 0.6F, 0.2F, 0.2F });
/* 261 */     bo.put(EnumColor.BLACK, new float[] { 0.1F, 0.1F, 0.1F });
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntitySheep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */