/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMinecartCommand;
/*    */ 
/*  5 */ public class EntityMinecartCommandBlock extends EntityMinecartAbstract { private final CommandBlockListenerAbstract a = new CommandBlockListenerAbstract()
/*    */   {
/*    */ 
/*    */     public void h()
/*    */     {
/* 10 */       EntityMinecartCommandBlock.this.getDataWatcher().watch(23, getCommand());
/* 11 */       EntityMinecartCommandBlock.this.getDataWatcher().watch(24, IChatBaseComponent.ChatSerializer.a(k()));
/*    */     }
/*    */     
/*    */     public BlockPosition getChunkCoordinates() {
/* 15 */       return new BlockPosition(EntityMinecartCommandBlock.this.locX, EntityMinecartCommandBlock.this.locY + 0.5D, EntityMinecartCommandBlock.this.locZ);
/*    */     }
/*    */     
/*    */     public Vec3D d() {
/* 19 */       return new Vec3D(EntityMinecartCommandBlock.this.locX, EntityMinecartCommandBlock.this.locY, EntityMinecartCommandBlock.this.locZ);
/*    */     }
/*    */     
/*    */     public World getWorld() {
/* 23 */       return EntityMinecartCommandBlock.this.world;
/*    */     }
/*    */     
/*    */     public Entity f() {
/* 27 */       return EntityMinecartCommandBlock.this;
/*    */     }
/*    */   };
/* 30 */   private int b = 0;
/*    */   
/*    */   public EntityMinecartCommandBlock(World world) {
/* 33 */     super(world);
/*    */   }
/*    */   
/*    */   public EntityMinecartCommandBlock(World world, double d0, double d1, double d2) {
/* 37 */     super(world, d0, d1, d2);
/*    */   }
/*    */   
/*    */   protected void h() {
/* 41 */     super.h();
/* 42 */     getDataWatcher().a(23, "");
/* 43 */     getDataWatcher().a(24, "");
/*    */   }
/*    */   
/*    */   protected void a(NBTTagCompound nbttagcompound) {
/* 47 */     super.a(nbttagcompound);
/* 48 */     this.a.b(nbttagcompound);
/* 49 */     getDataWatcher().watch(23, getCommandBlock().getCommand());
/* 50 */     getDataWatcher().watch(24, IChatBaseComponent.ChatSerializer.a(getCommandBlock().k()));
/*    */   }
/*    */   
/*    */   protected void b(NBTTagCompound nbttagcompound) {
/* 54 */     super.b(nbttagcompound);
/* 55 */     this.a.a(nbttagcompound);
/*    */   }
/*    */   
/*    */   public EntityMinecartAbstract.EnumMinecartType s() {
/* 59 */     return EntityMinecartAbstract.EnumMinecartType.COMMAND_BLOCK;
/*    */   }
/*    */   
/*    */   public IBlockData u() {
/* 63 */     return Blocks.COMMAND_BLOCK.getBlockData();
/*    */   }
/*    */   
/*    */   public CommandBlockListenerAbstract getCommandBlock() {
/* 67 */     return this.a;
/*    */   }
/*    */   
/*    */   public void a(int i, int j, int k, boolean flag) {
/* 71 */     if ((flag) && (this.ticksLived - this.b >= 4)) {
/* 72 */       getCommandBlock().a(this.world);
/* 73 */       this.b = this.ticksLived;
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean e(EntityHuman entityhuman)
/*    */   {
/* 79 */     this.a.a(entityhuman);
/* 80 */     return false;
/*    */   }
/*    */   
/*    */   public void i(int i) {
/* 84 */     super.i(i);
/* 85 */     if (i == 24) {
/*    */       try {
/* 87 */         this.a.b(IChatBaseComponent.ChatSerializer.a(getDataWatcher().getString(24)));
/*    */ 
/*    */       }
/*    */       catch (Throwable localThrowable) {}
/* 91 */     } else if (i == 23) {
/* 92 */       this.a.setCommand(getDataWatcher().getString(23));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityMinecartCommandBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */