/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class EntityPainting extends EntityHanging
/*     */ {
/*     */   public EnumArt art;
/*     */   
/*     */   public EntityPainting(World world)
/*     */   {
/*  11 */     super(world);
/*  12 */     this.art = EnumArt.values()[this.random.nextInt(EnumArt.values().length)];
/*     */   }
/*     */   
/*     */   public EntityPainting(World world, BlockPosition blockposition, EnumDirection enumdirection) {
/*  16 */     super(world, blockposition);
/*  17 */     ArrayList arraylist = com.google.common.collect.Lists.newArrayList();
/*  18 */     EnumArt[] aentitypainting_enumart = EnumArt.values();
/*  19 */     int i = aentitypainting_enumart.length;
/*     */     
/*  21 */     for (int j = 0; j < i; j++) {
/*  22 */       EnumArt entitypainting_enumart = aentitypainting_enumart[j];
/*     */       
/*  24 */       this.art = entitypainting_enumart;
/*  25 */       setDirection(enumdirection);
/*  26 */       if (survives()) {
/*  27 */         arraylist.add(entitypainting_enumart);
/*     */       }
/*     */     }
/*     */     
/*  31 */     if (!arraylist.isEmpty()) {
/*  32 */       this.art = ((EnumArt)arraylist.get(this.random.nextInt(arraylist.size())));
/*     */     }
/*     */     
/*  35 */     setDirection(enumdirection);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  39 */     nbttagcompound.setString("Motive", this.art.B);
/*  40 */     super.b(nbttagcompound);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  44 */     String s = nbttagcompound.getString("Motive");
/*  45 */     EnumArt[] aentitypainting_enumart = EnumArt.values();
/*  46 */     int i = aentitypainting_enumart.length;
/*     */     
/*  48 */     for (int j = 0; j < i; j++) {
/*  49 */       EnumArt entitypainting_enumart = aentitypainting_enumart[j];
/*     */       
/*  51 */       if (entitypainting_enumart.B.equals(s)) {
/*  52 */         this.art = entitypainting_enumart;
/*     */       }
/*     */     }
/*     */     
/*  56 */     if (this.art == null) {
/*  57 */       this.art = EnumArt.KEBAB;
/*     */     }
/*     */     
/*  60 */     super.a(nbttagcompound);
/*     */   }
/*     */   
/*     */   public int l() {
/*  64 */     return this.art.C;
/*     */   }
/*     */   
/*     */   public int m() {
/*  68 */     return this.art.D;
/*     */   }
/*     */   
/*     */   public void b(Entity entity) {
/*  72 */     if (this.world.getGameRules().getBoolean("doEntityDrops")) {
/*  73 */       if ((entity instanceof EntityHuman)) {
/*  74 */         EntityHuman entityhuman = (EntityHuman)entity;
/*     */         
/*  76 */         if (entityhuman.abilities.canInstantlyBuild) {
/*  77 */           return;
/*     */         }
/*     */       }
/*     */       
/*  81 */       a(new ItemStack(Items.PAINTING), 0.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setPositionRotation(double d0, double d1, double d2, float f, float f1) {
/*  86 */     BlockPosition blockposition = this.blockPosition.a(d0 - this.locX, d1 - this.locY, d2 - this.locZ);
/*     */     
/*  88 */     setPosition(blockposition.getX(), blockposition.getY(), blockposition.getZ());
/*     */   }
/*     */   
/*     */   public static enum EnumArt
/*     */   {
/*  93 */     KEBAB("Kebab", 16, 16, 0, 0),  AZTEC("Aztec", 16, 16, 16, 0),  ALBAN("Alban", 16, 16, 32, 0),  AZTEC_2("Aztec2", 16, 16, 48, 0),  BOMB("Bomb", 16, 16, 64, 0),  PLANT("Plant", 16, 16, 80, 0),  WASTELAND("Wasteland", 16, 16, 96, 0),  POOL("Pool", 32, 16, 0, 32),  COURBET("Courbet", 32, 16, 32, 32),  SEA("Sea", 32, 16, 64, 32),  SUNSET("Sunset", 32, 16, 96, 32),  CREEBET("Creebet", 32, 16, 128, 32),  WANDERER("Wanderer", 16, 32, 0, 64),  GRAHAM("Graham", 16, 32, 16, 64),  MATCH("Match", 32, 32, 0, 128),  BUST("Bust", 32, 32, 32, 128),  STAGE("Stage", 32, 32, 64, 128),  VOID("Void", 32, 32, 96, 128),  SKULL_AND_ROSES("SkullAndRoses", 32, 32, 128, 128),  WITHER("Wither", 32, 32, 160, 128),  FIGHTERS("Fighters", 64, 32, 0, 96),  POINTER("Pointer", 64, 64, 0, 192),  PIGSCENE("Pigscene", 64, 64, 64, 192),  BURNING_SKULL("BurningSkull", 64, 64, 128, 192),  SKELETON("Skeleton", 64, 48, 192, 64),  DONKEY_KONG("DonkeyKong", 64, 48, 192, 112);
/*     */     
/*  95 */     public static final int A = "SkullAndRoses".length();
/*     */     public final String B;
/*     */     public final int C;
/*     */     public final int D;
/*     */     public final int E;
/*     */     public final int F;
/*     */     
/*     */     private EnumArt(String s, int i, int j, int k, int l) {
/* 103 */       this.B = s;
/* 104 */       this.C = i;
/* 105 */       this.D = j;
/* 106 */       this.E = k;
/* 107 */       this.F = l;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityPainting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */