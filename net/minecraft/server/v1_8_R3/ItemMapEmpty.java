/*    */ package net.minecraft.server.v1_8_R3;
/*    */ 
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ 
/*    */ public class ItemMapEmpty extends ItemWorldMapBase {
/*  6 */   protected ItemMapEmpty() { a(CreativeModeTab.f); }
/*    */   
/*    */   public ItemStack a(ItemStack itemstack, World world, EntityHuman entityhuman)
/*    */   {
/* 10 */     World worldMain = (World)world.getServer().getServer().worlds.get(0);
/* 11 */     ItemStack itemstack1 = new ItemStack(Items.FILLED_MAP, 1, worldMain.b("map"));
/* 12 */     String s = "map_" + itemstack1.getData();
/* 13 */     WorldMap worldmap = new WorldMap(s);
/*    */     
/* 15 */     worldMain.a(s, worldmap);
/* 16 */     worldmap.scale = 0;
/* 17 */     worldmap.a(entityhuman.locX, entityhuman.locZ, worldmap.scale);
/* 18 */     worldmap.map = ((byte)((WorldServer)world).dimension);
/* 19 */     worldmap.c();
/*    */     
/* 21 */     org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory.callEvent(new org.bukkit.event.server.MapInitializeEvent(worldmap.mapView));
/*    */     
/* 23 */     itemstack.count -= 1;
/* 24 */     if (itemstack.count <= 0) {
/* 25 */       return itemstack1;
/*    */     }
/* 27 */     if (!entityhuman.inventory.pickup(itemstack1.cloneItemStack())) {
/* 28 */       entityhuman.drop(itemstack1, false);
/*    */     }
/*    */     
/* 31 */     entityhuman.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
/* 32 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\ItemMapEmpty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */