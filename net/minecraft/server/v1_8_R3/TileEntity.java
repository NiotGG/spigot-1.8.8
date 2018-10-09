/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.logging.Level;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ 
/*     */ public abstract class TileEntity
/*     */ {
/*  14 */   public org.spigotmc.CustomTimingsHandler tickTimer = org.bukkit.craftbukkit.v1_8_R3.SpigotTimings.getTileEntityTimings(this);
/*  15 */   private static final org.apache.logging.log4j.Logger a = ;
/*  16 */   private static Map<String, Class<? extends TileEntity>> f = Maps.newHashMap();
/*  17 */   private static Map<Class<? extends TileEntity>, String> g = Maps.newHashMap();
/*     */   protected World world;
/*     */   protected BlockPosition position;
/*     */   protected boolean d;
/*     */   private int h;
/*     */   protected Block e;
/*     */   
/*     */   public TileEntity() {
/*  25 */     this.position = BlockPosition.ZERO;
/*  26 */     this.h = -1;
/*     */   }
/*     */   
/*     */   private static void a(Class<? extends TileEntity> oclass, String s) {
/*  30 */     if (f.containsKey(s)) {
/*  31 */       throw new IllegalArgumentException("Duplicate id: " + s);
/*     */     }
/*  33 */     f.put(s, oclass);
/*  34 */     g.put(oclass, s);
/*     */   }
/*     */   
/*     */   public World getWorld()
/*     */   {
/*  39 */     return this.world;
/*     */   }
/*     */   
/*     */   public void a(World world) {
/*  43 */     this.world = world;
/*     */   }
/*     */   
/*     */   public boolean t() {
/*  47 */     return this.world != null;
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  51 */     this.position = new BlockPosition(nbttagcompound.getInt("x"), nbttagcompound.getInt("y"), nbttagcompound.getInt("z"));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  55 */     String s = (String)g.get(getClass());
/*     */     
/*  57 */     if (s == null) {
/*  58 */       throw new RuntimeException(getClass() + " is missing a mapping! This is a bug!");
/*     */     }
/*  60 */     nbttagcompound.setString("id", s);
/*  61 */     nbttagcompound.setInt("x", this.position.getX());
/*  62 */     nbttagcompound.setInt("y", this.position.getY());
/*  63 */     nbttagcompound.setInt("z", this.position.getZ());
/*     */   }
/*     */   
/*     */   public static TileEntity c(NBTTagCompound nbttagcompound)
/*     */   {
/*  68 */     TileEntity tileentity = null;
/*     */     try
/*     */     {
/*  71 */       Class oclass = (Class)f.get(nbttagcompound.getString("id"));
/*     */       
/*  73 */       if (oclass != null) {
/*  74 */         tileentity = (TileEntity)oclass.newInstance();
/*     */       }
/*     */     } catch (Exception exception) {
/*  77 */       exception.printStackTrace();
/*     */     }
/*     */     
/*  80 */     if (tileentity != null) {
/*  81 */       tileentity.a(nbttagcompound);
/*     */     } else {
/*  83 */       a.warn("Skipping BlockEntity with id " + nbttagcompound.getString("id"));
/*     */     }
/*     */     
/*  86 */     return tileentity;
/*     */   }
/*     */   
/*     */   public int u() {
/*  90 */     if (this.h == -1) {
/*  91 */       IBlockData iblockdata = this.world.getType(this.position);
/*     */       
/*  93 */       this.h = iblockdata.getBlock().toLegacyData(iblockdata);
/*     */     }
/*     */     
/*  96 */     return this.h;
/*     */   }
/*     */   
/*     */   public void update() {
/* 100 */     if (this.world != null) {
/* 101 */       IBlockData iblockdata = this.world.getType(this.position);
/*     */       
/* 103 */       this.h = iblockdata.getBlock().toLegacyData(iblockdata);
/* 104 */       this.world.b(this.position, this);
/* 105 */       if (w() != Blocks.AIR) {
/* 106 */         this.world.updateAdjacentComparators(this.position, w());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public BlockPosition getPosition()
/*     */   {
/* 113 */     return this.position;
/*     */   }
/*     */   
/*     */   public Block w() {
/* 117 */     if (this.e == null) {
/* 118 */       this.e = this.world.getType(this.position).getBlock();
/*     */     }
/*     */     
/* 121 */     return this.e;
/*     */   }
/*     */   
/*     */   public Packet getUpdatePacket() {
/* 125 */     return null;
/*     */   }
/*     */   
/*     */   public boolean x() {
/* 129 */     return this.d;
/*     */   }
/*     */   
/*     */   public void y() {
/* 133 */     this.d = true;
/*     */   }
/*     */   
/*     */   public void D() {
/* 137 */     this.d = false;
/*     */   }
/*     */   
/*     */   public boolean c(int i, int j) {
/* 141 */     return false;
/*     */   }
/*     */   
/*     */   public void E() {
/* 145 */     this.e = null;
/* 146 */     this.h = -1;
/*     */   }
/*     */   
/*     */   public void a(CrashReportSystemDetails crashreportsystemdetails) {
/* 150 */     crashreportsystemdetails.a("Name", new Callable() {
/*     */       public String a() throws Exception {
/* 152 */         return (String)TileEntity.g.get(TileEntity.this.getClass()) + " // " + TileEntity.this.getClass().getCanonicalName();
/*     */       }
/*     */       
/*     */       public Object call() throws Exception {
/* 156 */         return a();
/*     */       }
/*     */     });
/* 159 */     if (this.world != null) {
/* 160 */       CrashReportSystemDetails.a(crashreportsystemdetails, this.position, w(), u());
/* 161 */       crashreportsystemdetails.a("Actual block type", new Callable() {
/*     */         public String a() throws Exception {
/* 163 */           int i = Block.getId(TileEntity.this.world.getType(TileEntity.this.position).getBlock());
/*     */           try
/*     */           {
/* 166 */             return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(i), Block.getById(i).a(), Block.getById(i).getClass().getCanonicalName() });
/*     */           } catch (Throwable localThrowable) {}
/* 168 */           return "ID #" + i;
/*     */         }
/*     */         
/*     */         public Object call() throws Exception
/*     */         {
/* 173 */           return a();
/*     */         }
/* 175 */       });
/* 176 */       crashreportsystemdetails.a("Actual block data value", new Callable() {
/*     */         public String a() throws Exception {
/* 178 */           IBlockData iblockdata = TileEntity.this.world.getType(TileEntity.this.position);
/* 179 */           int i = iblockdata.getBlock().toLegacyData(iblockdata);
/*     */           
/* 181 */           if (i < 0) {
/* 182 */             return "Unknown? (Got " + i + ")";
/*     */           }
/* 184 */           String s = String.format("%4s", new Object[] { Integer.toBinaryString(i) }).replace(" ", "0");
/*     */           
/* 186 */           return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { Integer.valueOf(i), s });
/*     */         }
/*     */         
/*     */         public Object call() throws Exception
/*     */         {
/* 191 */           return a();
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(BlockPosition blockposition) {
/* 198 */     this.position = blockposition;
/*     */   }
/*     */   
/*     */   public boolean F() {
/* 202 */     return false;
/*     */   }
/*     */   
/*     */   static {
/* 206 */     a(TileEntityFurnace.class, "Furnace");
/* 207 */     a(TileEntityChest.class, "Chest");
/* 208 */     a(TileEntityEnderChest.class, "EnderChest");
/* 209 */     a(BlockJukeBox.TileEntityRecordPlayer.class, "RecordPlayer");
/* 210 */     a(TileEntityDispenser.class, "Trap");
/* 211 */     a(TileEntityDropper.class, "Dropper");
/* 212 */     a(TileEntitySign.class, "Sign");
/* 213 */     a(TileEntityMobSpawner.class, "MobSpawner");
/* 214 */     a(TileEntityNote.class, "Music");
/* 215 */     a(TileEntityPiston.class, "Piston");
/* 216 */     a(TileEntityBrewingStand.class, "Cauldron");
/* 217 */     a(TileEntityEnchantTable.class, "EnchantTable");
/* 218 */     a(TileEntityEnderPortal.class, "Airportal");
/* 219 */     a(TileEntityCommand.class, "Control");
/* 220 */     a(TileEntityBeacon.class, "Beacon");
/* 221 */     a(TileEntitySkull.class, "Skull");
/* 222 */     a(TileEntityLightDetector.class, "DLDetector");
/* 223 */     a(TileEntityHopper.class, "Hopper");
/* 224 */     a(TileEntityComparator.class, "Comparator");
/* 225 */     a(TileEntityFlowerPot.class, "FlowerPot");
/* 226 */     a(TileEntityBanner.class, "Banner");
/*     */   }
/*     */   
/*     */   public InventoryHolder getOwner()
/*     */   {
/* 231 */     if (this.world == null) { return null;
/*     */     }
/* 233 */     org.bukkit.block.Block block = this.world.getWorld().getBlockAt(this.position.getX(), this.position.getY(), this.position.getZ());
/* 234 */     if (block == null) {
/* 235 */       org.bukkit.Bukkit.getLogger().log(Level.WARNING, "No block for owner at %s %d %d %d", new Object[] { this.world.getWorld(), Integer.valueOf(this.position.getX()), Integer.valueOf(this.position.getY()), Integer.valueOf(this.position.getZ()) });
/* 236 */       return null;
/*     */     }
/*     */     
/* 239 */     BlockState state = block.getState();
/* 240 */     if ((state instanceof InventoryHolder)) return (InventoryHolder)state;
/* 241 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */