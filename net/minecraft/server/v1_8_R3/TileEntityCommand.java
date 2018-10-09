/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_8_R3.command.CraftBlockCommandSender;
/*    */ 
/*  5 */ public class TileEntityCommand extends TileEntity { private final CommandBlockListenerAbstract a = new CommandBlockListenerAbstract()
/*    */   {
/*    */ 
/*    */     public BlockPosition getChunkCoordinates()
/*    */     {
/* 10 */       return TileEntityCommand.this.position;
/*    */     }
/*    */     
/*    */     public Vec3D d() {
/* 14 */       return new Vec3D(TileEntityCommand.this.position.getX() + 0.5D, TileEntityCommand.this.position.getY() + 0.5D, TileEntityCommand.this.position.getZ() + 0.5D);
/*    */     }
/*    */     
/*    */     public World getWorld() {
/* 18 */       return TileEntityCommand.this.getWorld();
/*    */     }
/*    */     
/*    */     public void setCommand(String s) {
/* 22 */       super.setCommand(s);
/* 23 */       TileEntityCommand.this.update();
/*    */     }
/*    */     
/*    */     public void h() {
/* 27 */       TileEntityCommand.this.getWorld().notify(TileEntityCommand.this.position);
/*    */     }
/*    */     
/*    */     public Entity f() {
/* 31 */       return null;
/*    */     }
/*    */   };
/*    */   
/*    */ 
/*    */   public void b(NBTTagCompound nbttagcompound)
/*    */   {
/* 38 */     super.b(nbttagcompound);
/* 39 */     this.a.a(nbttagcompound);
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound nbttagcompound) {
/* 43 */     super.a(nbttagcompound);
/* 44 */     this.a.b(nbttagcompound);
/*    */   }
/*    */   
/*    */   public Packet getUpdatePacket() {
/* 48 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*    */     
/* 50 */     b(nbttagcompound);
/* 51 */     return new PacketPlayOutTileEntityData(this.position, 2, nbttagcompound);
/*    */   }
/*    */   
/*    */   public boolean F() {
/* 55 */     return true;
/*    */   }
/*    */   
/*    */   public CommandBlockListenerAbstract getCommandBlock() {
/* 59 */     return this.a;
/*    */   }
/*    */   
/*    */   public CommandObjectiveExecutor c() {
/* 63 */     return this.a.n();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */