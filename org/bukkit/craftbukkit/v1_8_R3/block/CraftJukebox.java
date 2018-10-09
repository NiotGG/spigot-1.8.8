/*    */ package org.bukkit.craftbukkit.v1_8_R3.block;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.BlockJukeBox;
/*    */ import net.minecraft.server.v1_8_R3.BlockJukeBox.TileEntityRecordPlayer;
/*    */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
/*    */ 
/*    */ public class CraftJukebox extends CraftBlockState implements org.bukkit.block.Jukebox
/*    */ {
/*    */   private final CraftWorld world;
/*    */   private final BlockJukeBox.TileEntityRecordPlayer jukebox;
/*    */   
/*    */   public CraftJukebox(org.bukkit.block.Block block)
/*    */   {
/* 17 */     super(block);
/*    */     
/* 19 */     this.world = ((CraftWorld)block.getWorld());
/* 20 */     this.jukebox = ((BlockJukeBox.TileEntityRecordPlayer)this.world.getTileEntityAt(getX(), getY(), getZ()));
/*    */   }
/*    */   
/*    */   public CraftJukebox(Material material, BlockJukeBox.TileEntityRecordPlayer te) {
/* 24 */     super(material);
/* 25 */     this.world = null;
/* 26 */     this.jukebox = te;
/*    */   }
/*    */   
/*    */   public Material getPlaying()
/*    */   {
/* 31 */     net.minecraft.server.v1_8_R3.ItemStack record = this.jukebox.getRecord();
/* 32 */     if (record == null) {
/* 33 */       return Material.AIR;
/*    */     }
/* 35 */     return CraftMagicNumbers.getMaterial(record.getItem());
/*    */   }
/*    */   
/*    */   public void setPlaying(Material record)
/*    */   {
/* 40 */     if ((record == null) || (CraftMagicNumbers.getItem(record) == null)) {
/* 41 */       record = Material.AIR;
/* 42 */       this.jukebox.setRecord(null);
/*    */     } else {
/* 44 */       this.jukebox.setRecord(new net.minecraft.server.v1_8_R3.ItemStack(CraftMagicNumbers.getItem(record), 1));
/*    */     }
/* 46 */     if (!isPlaced()) {
/* 47 */       return;
/*    */     }
/* 49 */     this.jukebox.update();
/* 50 */     if (record == Material.AIR) {
/* 51 */       this.world.getHandle().setTypeAndData(new BlockPosition(getX(), getY(), getZ()), 
/* 52 */         net.minecraft.server.v1_8_R3.Blocks.JUKEBOX.getBlockData()
/* 53 */         .set(BlockJukeBox.HAS_RECORD, Boolean.valueOf(false)), 3);
/*    */     } else {
/* 55 */       this.world.getHandle().setTypeAndData(new BlockPosition(getX(), getY(), getZ()), 
/* 56 */         net.minecraft.server.v1_8_R3.Blocks.JUKEBOX.getBlockData()
/* 57 */         .set(BlockJukeBox.HAS_RECORD, Boolean.valueOf(true)), 3);
/*    */     }
/* 59 */     this.world.playEffect(getLocation(), org.bukkit.Effect.RECORD_PLAY, record.getId());
/*    */   }
/*    */   
/*    */   public boolean isPlaying() {
/* 63 */     return getRawData() == 1;
/*    */   }
/*    */   
/*    */   public boolean eject() {
/* 67 */     requirePlaced();
/* 68 */     boolean result = isPlaying();
/* 69 */     ((BlockJukeBox)net.minecraft.server.v1_8_R3.Blocks.JUKEBOX).dropRecord(this.world.getHandle(), new BlockPosition(getX(), getY(), getZ()), null);
/* 70 */     return result;
/*    */   }
/*    */   
/*    */   public BlockJukeBox.TileEntityRecordPlayer getTileEntity()
/*    */   {
/* 75 */     return this.jukebox;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\block\CraftJukebox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */