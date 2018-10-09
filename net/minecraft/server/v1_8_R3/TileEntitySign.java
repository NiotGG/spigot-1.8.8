/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.gson.JsonParseException;
/*     */ 
/*     */ public class TileEntitySign extends TileEntity
/*     */ {
/*   7 */   public final IChatBaseComponent[] lines = { new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("") };
/*   8 */   public int f = -1;
/*   9 */   public boolean isEditable = true;
/*     */   private EntityHuman h;
/*  11 */   private final CommandObjectiveExecutor i = new CommandObjectiveExecutor();
/*     */   
/*     */ 
/*     */   public void b(NBTTagCompound nbttagcompound)
/*     */   {
/*  16 */     super.b(nbttagcompound);
/*     */     
/*  18 */     for (int i = 0; i < 4; i++) {
/*  19 */       String s = IChatBaseComponent.ChatSerializer.a(this.lines[i]);
/*     */       
/*  21 */       nbttagcompound.setString("Text" + (i + 1), s);
/*     */     }
/*     */     
/*     */ 
/*  25 */     if (Boolean.getBoolean("convertLegacySigns")) {
/*  26 */       nbttagcompound.setBoolean("Bukkit.isConverted", true);
/*     */     }
/*     */     
/*     */ 
/*  30 */     this.i.b(nbttagcompound);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  34 */     this.isEditable = false;
/*  35 */     super.a(nbttagcompound);
/*  36 */     ICommandListener icommandlistener = new ICommandListener() {
/*     */       public String getName() {
/*  38 */         return "Sign";
/*     */       }
/*     */       
/*     */       public IChatBaseComponent getScoreboardDisplayName() {
/*  42 */         return new ChatComponentText(getName());
/*     */       }
/*     */       
/*     */       public void sendMessage(IChatBaseComponent ichatbasecomponent) {}
/*     */       
/*     */       public boolean a(int i, String s) {
/*  48 */         return true;
/*     */       }
/*     */       
/*     */       public BlockPosition getChunkCoordinates() {
/*  52 */         return TileEntitySign.this.position;
/*     */       }
/*     */       
/*     */       public Vec3D d() {
/*  56 */         return new Vec3D(TileEntitySign.this.position.getX() + 0.5D, TileEntitySign.this.position.getY() + 0.5D, TileEntitySign.this.position.getZ() + 0.5D);
/*     */       }
/*     */       
/*     */       public World getWorld() {
/*  60 */         return TileEntitySign.this.world;
/*     */       }
/*     */       
/*     */       public Entity f() {
/*  64 */         return null;
/*     */       }
/*     */       
/*     */       public boolean getSendCommandFeedback() {
/*  68 */         return false;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       public void a(CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor_enumcommandresult, int i) {}
/*  77 */     };
/*  78 */     boolean oldSign = (Boolean.getBoolean("convertLegacySigns")) && (!nbttagcompound.getBoolean("Bukkit.isConverted"));
/*     */     
/*  80 */     for (int i = 0; i < 4;) {
/*  81 */       String s = nbttagcompound.getString("Text" + (i + 1));
/*  82 */       if ((s != null) && (s.length() > 2048)) {
/*  83 */         s = "\"\"";
/*     */       }
/*     */       try
/*     */       {
/*  87 */         IChatBaseComponent ichatbasecomponent = IChatBaseComponent.ChatSerializer.a(s);
/*     */         
/*  89 */         if (oldSign) {
/*  90 */           this.lines[i] = org.bukkit.craftbukkit.v1_8_R3.util.CraftChatMessage.fromString(s)[0];
/*     */         }
/*     */         else
/*     */         {
/*     */           try
/*     */           {
/*  96 */             this.lines[i] = ChatComponentUtils.filterForDisplay(icommandlistener, ichatbasecomponent, null);
/*     */           } catch (CommandException localCommandException) {
/*  98 */             this.lines[i] = ichatbasecomponent;
/*     */           }
/*     */         }
/*  80 */         i++;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/*     */       catch (JsonParseException localJsonParseException)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 101 */         this.lines[i] = new ChatComponentText(s);
/*     */       }
/*     */     }
/*     */     
/* 105 */     this.i.a(nbttagcompound);
/*     */   }
/*     */   
/*     */   public Packet getUpdatePacket() {
/* 109 */     IChatBaseComponent[] aichatbasecomponent = new IChatBaseComponent[4];
/*     */     
/* 111 */     System.arraycopy(this.lines, 0, aichatbasecomponent, 0, 4);
/* 112 */     return new PacketPlayOutUpdateSign(this.world, this.position, aichatbasecomponent);
/*     */   }
/*     */   
/*     */   public boolean F() {
/* 116 */     return true;
/*     */   }
/*     */   
/*     */   public boolean b() {
/* 120 */     return this.isEditable;
/*     */   }
/*     */   
/*     */   public void a(EntityHuman entityhuman) {
/* 124 */     this.h = entityhuman;
/*     */   }
/*     */   
/*     */   public EntityHuman c() {
/* 128 */     return this.h;
/*     */   }
/*     */   
/*     */   public boolean b(final EntityHuman entityhuman) {
/* 132 */     new ICommandListener(); {
/*     */       public String getName() {
/* 134 */         return entityhuman.getName();
/*     */       }
/*     */       
/*     */       public IChatBaseComponent getScoreboardDisplayName() {
/* 138 */         return entityhuman.getScoreboardDisplayName();
/*     */       }
/*     */       
/*     */       public void sendMessage(IChatBaseComponent ichatbasecomponent) {}
/*     */       
/*     */       public boolean a(int i, String s) {
/* 144 */         return i <= 2;
/*     */       }
/*     */       
/*     */       public BlockPosition getChunkCoordinates() {
/* 148 */         return TileEntitySign.this.position;
/*     */       }
/*     */       
/*     */       public Vec3D d() {
/* 152 */         return new Vec3D(TileEntitySign.this.position.getX() + 0.5D, TileEntitySign.this.position.getY() + 0.5D, TileEntitySign.this.position.getZ() + 0.5D);
/*     */       }
/*     */       
/*     */       public World getWorld() {
/* 156 */         return entityhuman.getWorld();
/*     */       }
/*     */       
/*     */       public Entity f() {
/* 160 */         return entityhuman;
/*     */       }
/*     */       
/*     */       public boolean getSendCommandFeedback() {
/* 164 */         return false;
/*     */       }
/*     */       
/*     */       public void a(CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor_enumcommandresult, int i) {
/* 168 */         TileEntitySign.this.i.a(this, commandobjectiveexecutor_enumcommandresult, i);
/*     */       }
/*     */     }
/*     */     
/* 172 */     for (int i = 0; i < this.lines.length; i++) {
/* 173 */       ChatModifier chatmodifier = this.lines[i] == null ? null : this.lines[i].getChatModifier();
/*     */       
/* 175 */       if ((chatmodifier != null) && (chatmodifier.h() != null)) {
/* 176 */         ChatClickable chatclickable = chatmodifier.h();
/*     */         
/* 178 */         if (chatclickable.a() == ChatClickable.EnumClickAction.RUN_COMMAND)
/*     */         {
/*     */ 
/* 181 */           CommandBlockListenerAbstract.executeCommand(entityhuman, (org.bukkit.entity.Player)entityhuman.getBukkitEntity(), chatclickable.b());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 187 */     return true;
/*     */   }
/*     */   
/*     */   public CommandObjectiveExecutor d() {
/* 191 */     return this.i;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\TileEntitySign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */