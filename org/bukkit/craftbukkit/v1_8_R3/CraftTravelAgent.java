/*    */ package org.bukkit.craftbukkit.v1_8_R3;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.BlockPosition;
/*    */ import net.minecraft.server.v1_8_R3.ChunkProviderServer;
/*    */ import net.minecraft.server.v1_8_R3.PortalTravelAgent;
/*    */ import net.minecraft.server.v1_8_R3.WorldServer;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.TravelAgent;
/*    */ 
/*    */ public class CraftTravelAgent extends PortalTravelAgent implements TravelAgent
/*    */ {
/* 12 */   public static TravelAgent DEFAULT = null;
/*    */   
/* 14 */   private int searchRadius = 128;
/* 15 */   private int creationRadius = 16;
/* 16 */   private boolean canCreatePortal = true;
/*    */   
/*    */   public CraftTravelAgent(WorldServer worldserver) {
/* 19 */     super(worldserver);
/* 20 */     if ((DEFAULT == null) && (worldserver.dimension == 0)) {
/* 21 */       DEFAULT = this;
/*    */     }
/*    */   }
/*    */   
/*    */   public Location findOrCreate(Location target)
/*    */   {
/* 27 */     WorldServer worldServer = ((CraftWorld)target.getWorld()).getHandle();
/* 28 */     boolean before = worldServer.chunkProviderServer.forceChunkLoad;
/* 29 */     worldServer.chunkProviderServer.forceChunkLoad = true;
/*    */     
/* 31 */     Location found = findPortal(target);
/* 32 */     if (found == null) {
/* 33 */       if ((getCanCreatePortal()) && (createPortal(target))) {
/* 34 */         found = findPortal(target);
/*    */       } else {
/* 36 */         found = target;
/*    */       }
/*    */     }
/*    */     
/* 40 */     worldServer.chunkProviderServer.forceChunkLoad = before;
/* 41 */     return found;
/*    */   }
/*    */   
/*    */   public Location findPortal(Location location)
/*    */   {
/* 46 */     PortalTravelAgent pta = ((CraftWorld)location.getWorld()).getHandle().getTravelAgent();
/* 47 */     BlockPosition found = pta.findPortal(location.getX(), location.getY(), location.getZ(), getSearchRadius());
/* 48 */     return found != null ? new Location(location.getWorld(), found.getX(), found.getY(), found.getZ(), location.getYaw(), location.getPitch()) : null;
/*    */   }
/*    */   
/*    */   public boolean createPortal(Location location)
/*    */   {
/* 53 */     PortalTravelAgent pta = ((CraftWorld)location.getWorld()).getHandle().getTravelAgent();
/* 54 */     return pta.createPortal(location.getX(), location.getY(), location.getZ(), getCreationRadius());
/*    */   }
/*    */   
/*    */   public TravelAgent setSearchRadius(int radius)
/*    */   {
/* 59 */     this.searchRadius = radius;
/* 60 */     return this;
/*    */   }
/*    */   
/*    */   public int getSearchRadius()
/*    */   {
/* 65 */     return this.searchRadius;
/*    */   }
/*    */   
/*    */   public TravelAgent setCreationRadius(int radius)
/*    */   {
/* 70 */     this.creationRadius = (radius < 2 ? 0 : radius);
/* 71 */     return this;
/*    */   }
/*    */   
/*    */   public int getCreationRadius()
/*    */   {
/* 76 */     return this.creationRadius;
/*    */   }
/*    */   
/*    */   public boolean getCanCreatePortal()
/*    */   {
/* 81 */     return this.canCreatePortal;
/*    */   }
/*    */   
/*    */   public void setCanCreatePortal(boolean create)
/*    */   {
/* 86 */     this.canCreatePortal = create;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\CraftTravelAgent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */