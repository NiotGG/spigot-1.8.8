/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.event.block.NotePlayEvent;
/*    */ 
/*    */ public class TileEntityNote extends TileEntity
/*    */ {
/*    */   public byte note;
/*    */   public boolean f;
/*    */   
/*    */   public void b(NBTTagCompound nbttagcompound) {
/* 11 */     super.b(nbttagcompound);
/* 12 */     nbttagcompound.setByte("note", this.note);
/*    */   }
/*    */   
/*    */   public void a(NBTTagCompound nbttagcompound) {
/* 16 */     super.a(nbttagcompound);
/* 17 */     this.note = nbttagcompound.getByte("note");
/* 18 */     this.note = ((byte)MathHelper.clamp(this.note, 0, 24));
/*    */   }
/*    */   
/*    */   public void b() {
/* 22 */     this.note = ((byte)((this.note + 1) % 25));
/* 23 */     update();
/*    */   }
/*    */   
/*    */   public void play(World world, BlockPosition blockposition) {
/* 27 */     if (world.getType(blockposition.up()).getBlock().getMaterial() == Material.AIR) {
/* 28 */       Material material = world.getType(blockposition.down()).getBlock().getMaterial();
/* 29 */       byte b0 = 0;
/*    */       
/* 31 */       if (material == Material.STONE) {
/* 32 */         b0 = 1;
/*    */       }
/*    */       
/* 35 */       if (material == Material.SAND) {
/* 36 */         b0 = 2;
/*    */       }
/*    */       
/* 39 */       if (material == Material.SHATTERABLE) {
/* 40 */         b0 = 3;
/*    */       }
/*    */       
/* 43 */       if (material == Material.WOOD) {
/* 44 */         b0 = 4;
/*    */       }
/*    */       
/*    */ 
/* 48 */       NotePlayEvent event = org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callNotePlayEvent(this.world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), b0, this.note);
/* 49 */       if (!event.isCancelled()) {
/* 50 */         world.playBlockAction(blockposition, Blocks.NOTEBLOCK, event.getInstrument().getType(), event.getNote().getId());
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntityNote.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */