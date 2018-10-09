/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityEnchantTable
/*     */   extends TileEntity
/*     */   implements IUpdatePlayerListBox, ITileEntityContainer
/*     */ {
/*     */   public int a;
/*     */   public float f;
/*     */   public float g;
/*     */   public float h;
/*     */   public float i;
/*     */   public float j;
/*     */   public float k;
/*     */   public float l;
/*     */   public float m;
/*     */   public float n;
/*  25 */   private static Random o = new Random();
/*     */   private String p;
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound)
/*     */   {
/*  30 */     super.b(paramNBTTagCompound);
/*  31 */     if (hasCustomName()) {
/*  32 */       paramNBTTagCompound.setString("CustomName", this.p);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound)
/*     */   {
/*  38 */     super.a(paramNBTTagCompound);
/*  39 */     if (paramNBTTagCompound.hasKeyOfType("CustomName", 8)) {
/*  40 */       this.p = paramNBTTagCompound.getString("CustomName");
/*     */     }
/*     */   }
/*     */   
/*     */   public void c()
/*     */   {
/*  46 */     this.k = this.j;
/*  47 */     this.m = this.l;
/*     */     
/*  49 */     EntityHuman localEntityHuman = this.world.findNearbyPlayer(this.position.getX() + 0.5F, this.position.getY() + 0.5F, this.position.getZ() + 0.5F, 3.0D);
/*  50 */     if (localEntityHuman != null) {
/*  51 */       double d1 = localEntityHuman.locX - (this.position.getX() + 0.5F);
/*  52 */       double d2 = localEntityHuman.locZ - (this.position.getZ() + 0.5F);
/*     */       
/*  54 */       this.n = ((float)MathHelper.b(d2, d1));
/*     */       
/*  56 */       this.j += 0.1F;
/*     */       
/*  58 */       if ((this.j < 0.5F) || (o.nextInt(40) == 0)) {
/*  59 */         float f1 = this.h;
/*     */         do {
/*  61 */           this.h += o.nextInt(4) - o.nextInt(4);
/*  62 */         } while (f1 == this.h);
/*     */       }
/*     */     } else {
/*  65 */       this.n += 0.02F;
/*  66 */       this.j -= 0.1F;
/*     */     }
/*     */     
/*  69 */     while (this.l >= 3.1415927F) {
/*  70 */       this.l -= 6.2831855F;
/*     */     }
/*  72 */     while (this.l < -3.1415927F) {
/*  73 */       this.l += 6.2831855F;
/*     */     }
/*  75 */     while (this.n >= 3.1415927F) {
/*  76 */       this.n -= 6.2831855F;
/*     */     }
/*  78 */     while (this.n < -3.1415927F) {
/*  79 */       this.n += 6.2831855F;
/*     */     }
/*  81 */     float f2 = this.n - this.l;
/*  82 */     while (f2 >= 3.1415927F) {
/*  83 */       f2 -= 6.2831855F;
/*     */     }
/*  85 */     while (f2 < -3.1415927F) {
/*  86 */       f2 += 6.2831855F;
/*     */     }
/*     */     
/*  89 */     this.l += f2 * 0.4F;
/*     */     
/*  91 */     this.j = MathHelper.a(this.j, 0.0F, 1.0F);
/*     */     
/*  93 */     this.a += 1;
/*  94 */     this.g = this.f;
/*     */     
/*  96 */     float f3 = (this.h - this.f) * 0.4F;
/*  97 */     float f4 = 0.2F;
/*  98 */     f3 = MathHelper.a(f3, -f4, f4);
/*  99 */     this.i += (f3 - this.i) * 0.9F;
/*     */     
/* 101 */     this.f += this.i;
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/* 106 */     return hasCustomName() ? this.p : "container.enchant";
/*     */   }
/*     */   
/*     */   public boolean hasCustomName()
/*     */   {
/* 111 */     return (this.p != null) && (this.p.length() > 0);
/*     */   }
/*     */   
/*     */   public void a(String paramString) {
/* 115 */     this.p = paramString;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent getScoreboardDisplayName()
/*     */   {
/* 120 */     if (hasCustomName()) {
/* 121 */       return new ChatComponentText(getName());
/*     */     }
/* 123 */     return new ChatMessage(getName(), new Object[0]);
/*     */   }
/*     */   
/*     */   public Container createContainer(PlayerInventory paramPlayerInventory, EntityHuman paramEntityHuman)
/*     */   {
/* 128 */     return new ContainerEnchantTable(paramPlayerInventory, this.world, this.position);
/*     */   }
/*     */   
/*     */   public String getContainerName()
/*     */   {
/* 133 */     return "minecraft:enchanting_table";
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityEnchantTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */