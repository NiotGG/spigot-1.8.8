/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spigotmc.AsyncCatcher;
/*     */ import org.spigotmc.TrackingRange;
/*     */ 
/*     */ public class EntityTracker
/*     */ {
/*  14 */   private static final Logger a = ;
/*     */   private final WorldServer world;
/*  16 */   private Set<EntityTrackerEntry> c = com.google.common.collect.Sets.newHashSet();
/*  17 */   public IntHashMap<EntityTrackerEntry> trackedEntities = new IntHashMap();
/*     */   private int e;
/*     */   
/*     */   public EntityTracker(WorldServer worldserver) {
/*  21 */     this.world = worldserver;
/*  22 */     this.e = worldserver.getMinecraftServer().getPlayerList().d();
/*     */   }
/*     */   
/*     */   public void track(Entity entity) {
/*  26 */     if ((entity instanceof EntityPlayer)) {
/*  27 */       addEntity(entity, 512, 2);
/*  28 */       EntityPlayer entityplayer = (EntityPlayer)entity;
/*  29 */       Iterator iterator = this.c.iterator();
/*     */       
/*  31 */       while (iterator.hasNext()) {
/*  32 */         EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
/*     */         
/*  34 */         if (entitytrackerentry.tracker != entityplayer) {
/*  35 */           entitytrackerentry.updatePlayer(entityplayer);
/*     */         }
/*     */       }
/*  38 */     } else if ((entity instanceof EntityFishingHook)) {
/*  39 */       addEntity(entity, 64, 5, true);
/*  40 */     } else if ((entity instanceof EntityArrow)) {
/*  41 */       addEntity(entity, 64, 20, false);
/*  42 */     } else if ((entity instanceof EntitySmallFireball)) {
/*  43 */       addEntity(entity, 64, 10, false);
/*  44 */     } else if ((entity instanceof EntityFireball)) {
/*  45 */       addEntity(entity, 64, 10, false);
/*  46 */     } else if ((entity instanceof EntitySnowball)) {
/*  47 */       addEntity(entity, 64, 10, true);
/*  48 */     } else if ((entity instanceof EntityEnderPearl)) {
/*  49 */       addEntity(entity, 64, 10, true);
/*  50 */     } else if ((entity instanceof EntityEnderSignal)) {
/*  51 */       addEntity(entity, 64, 4, true);
/*  52 */     } else if ((entity instanceof EntityEgg)) {
/*  53 */       addEntity(entity, 64, 10, true);
/*  54 */     } else if ((entity instanceof EntityPotion)) {
/*  55 */       addEntity(entity, 64, 10, true);
/*  56 */     } else if ((entity instanceof EntityThrownExpBottle)) {
/*  57 */       addEntity(entity, 64, 10, true);
/*  58 */     } else if ((entity instanceof EntityFireworks)) {
/*  59 */       addEntity(entity, 64, 10, true);
/*  60 */     } else if ((entity instanceof EntityItem)) {
/*  61 */       addEntity(entity, 64, 20, true);
/*  62 */     } else if ((entity instanceof EntityMinecartAbstract)) {
/*  63 */       addEntity(entity, 80, 3, true);
/*  64 */     } else if ((entity instanceof EntityBoat)) {
/*  65 */       addEntity(entity, 80, 3, true);
/*  66 */     } else if ((entity instanceof EntitySquid)) {
/*  67 */       addEntity(entity, 64, 3, true);
/*  68 */     } else if ((entity instanceof EntityWither)) {
/*  69 */       addEntity(entity, 80, 3, false);
/*  70 */     } else if ((entity instanceof EntityBat)) {
/*  71 */       addEntity(entity, 80, 3, false);
/*  72 */     } else if ((entity instanceof EntityEnderDragon)) {
/*  73 */       addEntity(entity, 160, 3, true);
/*  74 */     } else if ((entity instanceof IAnimal)) {
/*  75 */       addEntity(entity, 80, 3, true);
/*  76 */     } else if ((entity instanceof EntityTNTPrimed)) {
/*  77 */       addEntity(entity, 160, 10, true);
/*  78 */     } else if ((entity instanceof EntityFallingBlock)) {
/*  79 */       addEntity(entity, 160, 20, true);
/*  80 */     } else if ((entity instanceof EntityHanging)) {
/*  81 */       addEntity(entity, 160, Integer.MAX_VALUE, false);
/*  82 */     } else if ((entity instanceof EntityArmorStand)) {
/*  83 */       addEntity(entity, 160, 3, true);
/*  84 */     } else if ((entity instanceof EntityExperienceOrb)) {
/*  85 */       addEntity(entity, 160, 20, true);
/*  86 */     } else if ((entity instanceof EntityEnderCrystal)) {
/*  87 */       addEntity(entity, 256, Integer.MAX_VALUE, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addEntity(Entity entity, int i, int j)
/*     */   {
/*  93 */     addEntity(entity, i, j, false);
/*     */   }
/*     */   
/*     */   public void addEntity(Entity entity, int i, int j, boolean flag) {
/*  97 */     AsyncCatcher.catchOp("entity track");
/*  98 */     i = TrackingRange.getEntityTrackingRange(entity, i);
/*  99 */     if (i > this.e) {
/* 100 */       i = this.e;
/*     */     }
/*     */     try
/*     */     {
/* 104 */       if (this.trackedEntities.b(entity.getId())) {
/* 105 */         throw new IllegalStateException("Entity is already tracked!");
/*     */       }
/*     */       
/* 108 */       EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(entity, i, j, flag);
/*     */       
/* 110 */       this.c.add(entitytrackerentry);
/* 111 */       this.trackedEntities.a(entity.getId(), entitytrackerentry);
/* 112 */       entitytrackerentry.scanPlayers(this.world.players);
/*     */     } catch (Throwable throwable) {
/* 114 */       CrashReport crashreport = CrashReport.a(throwable, "Adding entity to track");
/* 115 */       CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity To Track");
/*     */       
/* 117 */       crashreportsystemdetails.a("Tracking range", i + " blocks");
/* 118 */       final int finalI = i;
/* 119 */       crashreportsystemdetails.a("Update interval", new Callable() {
/*     */         public String a() throws Exception {
/* 121 */           String s = "Once per " + finalI + " ticks";
/*     */           
/* 123 */           if (finalI == Integer.MAX_VALUE) {
/* 124 */             s = "Maximum (" + s + ")";
/*     */           }
/*     */           
/* 127 */           return s;
/*     */         }
/*     */         
/*     */         public Object call() throws Exception {
/* 131 */           return a();
/*     */         }
/* 133 */       });
/* 134 */       entity.appendEntityCrashDetails(crashreportsystemdetails);
/* 135 */       CrashReportSystemDetails crashreportsystemdetails1 = crashreport.a("Entity That Is Already Tracked");
/*     */       
/* 137 */       ((EntityTrackerEntry)this.trackedEntities.get(entity.getId())).tracker.appendEntityCrashDetails(crashreportsystemdetails1);
/*     */       try
/*     */       {
/* 140 */         throw new ReportedException(crashreport);
/*     */       } catch (ReportedException reportedexception) {
/* 142 */         a.error("\"Silently\" catching entity tracking error.", reportedexception);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void untrackEntity(Entity entity)
/*     */   {
/* 149 */     AsyncCatcher.catchOp("entity untrack");
/* 150 */     if ((entity instanceof EntityPlayer)) {
/* 151 */       EntityPlayer entityplayer = (EntityPlayer)entity;
/* 152 */       Iterator iterator = this.c.iterator();
/*     */       
/* 154 */       while (iterator.hasNext()) {
/* 155 */         EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
/*     */         
/* 157 */         entitytrackerentry.a(entityplayer);
/*     */       }
/*     */     }
/*     */     
/* 161 */     EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)this.trackedEntities.d(entity.getId());
/*     */     
/* 163 */     if (entitytrackerentry1 != null) {
/* 164 */       this.c.remove(entitytrackerentry1);
/* 165 */       entitytrackerentry1.a();
/*     */     }
/*     */   }
/*     */   
/*     */   public void updatePlayers()
/*     */   {
/* 171 */     ArrayList arraylist = com.google.common.collect.Lists.newArrayList();
/* 172 */     Iterator iterator = this.c.iterator();
/*     */     
/* 174 */     while (iterator.hasNext()) {
/* 175 */       EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
/*     */       
/* 177 */       entitytrackerentry.track(this.world.players);
/* 178 */       if ((entitytrackerentry.n) && ((entitytrackerentry.tracker instanceof EntityPlayer))) {
/* 179 */         arraylist.add((EntityPlayer)entitytrackerentry.tracker);
/*     */       }
/*     */     }
/*     */     
/* 183 */     for (int i = 0; i < arraylist.size(); i++) {
/* 184 */       EntityPlayer entityplayer = (EntityPlayer)arraylist.get(i);
/* 185 */       Iterator iterator1 = this.c.iterator();
/*     */       
/* 187 */       while (iterator1.hasNext()) {
/* 188 */         EntityTrackerEntry entitytrackerentry1 = (EntityTrackerEntry)iterator1.next();
/*     */         
/* 190 */         if (entitytrackerentry1.tracker != entityplayer) {
/* 191 */           entitytrackerentry1.updatePlayer(entityplayer);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(EntityPlayer entityplayer)
/*     */   {
/* 199 */     Iterator iterator = this.c.iterator();
/*     */     
/* 201 */     while (iterator.hasNext()) {
/* 202 */       EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
/*     */       
/* 204 */       if (entitytrackerentry.tracker == entityplayer) {
/* 205 */         entitytrackerentry.scanPlayers(this.world.players);
/*     */       } else {
/* 207 */         entitytrackerentry.updatePlayer(entityplayer);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(Entity entity, Packet packet)
/*     */   {
/* 214 */     EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntities.get(entity.getId());
/*     */     
/* 216 */     if (entitytrackerentry != null) {
/* 217 */       entitytrackerentry.broadcast(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendPacketToEntity(Entity entity, Packet packet)
/*     */   {
/* 223 */     EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)this.trackedEntities.get(entity.getId());
/*     */     
/* 225 */     if (entitytrackerentry != null) {
/* 226 */       entitytrackerentry.broadcastIncludingSelf(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   public void untrackPlayer(EntityPlayer entityplayer)
/*     */   {
/* 232 */     Iterator iterator = this.c.iterator();
/*     */     
/* 234 */     while (iterator.hasNext()) {
/* 235 */       EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
/*     */       
/* 237 */       entitytrackerentry.clear(entityplayer);
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(EntityPlayer entityplayer, Chunk chunk)
/*     */   {
/* 243 */     Iterator iterator = this.c.iterator();
/*     */     
/* 245 */     while (iterator.hasNext()) {
/* 246 */       EntityTrackerEntry entitytrackerentry = (EntityTrackerEntry)iterator.next();
/*     */       
/* 248 */       if ((entitytrackerentry.tracker != entityplayer) && (entitytrackerentry.tracker.ae == chunk.locX) && (entitytrackerentry.tracker.ag == chunk.locZ)) {
/* 249 */         entitytrackerentry.updatePlayer(entityplayer);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EntityTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */