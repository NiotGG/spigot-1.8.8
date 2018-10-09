/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandSummon
/*     */   extends CommandAbstract
/*     */ {
/*     */   public String getCommand()
/*     */   {
/*  26 */     return "summon";
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/*  31 */     return 2;
/*     */   }
/*     */   
/*     */   public String getUsage(ICommandListener paramICommandListener)
/*     */   {
/*  36 */     return "commands.summon.usage";
/*     */   }
/*     */   
/*     */   public void execute(ICommandListener paramICommandListener, String[] paramArrayOfString) throws CommandException
/*     */   {
/*  41 */     if (paramArrayOfString.length < 1) {
/*  42 */       throw new ExceptionUsage("commands.summon.usage", new Object[0]);
/*     */     }
/*     */     
/*  45 */     String str = paramArrayOfString[0];
/*  46 */     BlockPosition localBlockPosition = paramICommandListener.getChunkCoordinates();
/*  47 */     Vec3D localVec3D = paramICommandListener.d();
/*     */     
/*  49 */     double d1 = localVec3D.a;
/*  50 */     double d2 = localVec3D.b;
/*  51 */     double d3 = localVec3D.c;
/*     */     
/*  53 */     if (paramArrayOfString.length >= 4) {
/*  54 */       d1 = b(d1, paramArrayOfString[1], true);
/*  55 */       d2 = b(d2, paramArrayOfString[2], false);
/*  56 */       d3 = b(d3, paramArrayOfString[3], true);
/*  57 */       localBlockPosition = new BlockPosition(d1, d2, d3);
/*     */     }
/*     */     
/*  60 */     World localWorld = paramICommandListener.getWorld();
/*  61 */     if (!localWorld.isLoaded(localBlockPosition)) {
/*  62 */       throw new CommandException("commands.summon.outOfWorld", new Object[0]);
/*     */     }
/*     */     
/*     */ 
/*  66 */     if ("LightningBolt".equals(str)) {
/*  67 */       localWorld.strikeLightning(new EntityLightning(localWorld, d1, d2, d3));
/*  68 */       a(paramICommandListener, this, "commands.summon.success", new Object[0]);
/*  69 */       return;
/*     */     }
/*     */     
/*  72 */     NBTTagCompound localNBTTagCompound1 = new NBTTagCompound();
/*  73 */     int i = 0;
/*  74 */     Object localObject1; if (paramArrayOfString.length >= 5) {
/*  75 */       localObject1 = a(paramICommandListener, paramArrayOfString, 4);
/*     */       try {
/*  77 */         localNBTTagCompound1 = MojangsonParser.parse(((IChatBaseComponent)localObject1).c());
/*  78 */         i = 1;
/*     */       } catch (MojangsonParseException localMojangsonParseException) {
/*  80 */         throw new CommandException("commands.summon.tagError", new Object[] { localMojangsonParseException.getMessage() });
/*     */       }
/*     */     }
/*  83 */     localNBTTagCompound1.setString("id", str);
/*     */     
/*     */     try
/*     */     {
/*  87 */       localObject1 = EntityTypes.a(localNBTTagCompound1, localWorld);
/*     */     } catch (RuntimeException localRuntimeException) {
/*  89 */       throw new CommandException("commands.summon.failed", new Object[0]);
/*     */     }
/*     */     
/*  92 */     if (localObject1 == null) {
/*  93 */       throw new CommandException("commands.summon.failed", new Object[0]);
/*     */     }
/*     */     
/*  96 */     ((Entity)localObject1).setPositionRotation(d1, d2, d3, ((Entity)localObject1).yaw, ((Entity)localObject1).pitch);
/*  97 */     if (i == 0)
/*     */     {
/*  99 */       if ((localObject1 instanceof EntityInsentient)) {
/* 100 */         ((EntityInsentient)localObject1).prepare(localWorld.E(new BlockPosition((Entity)localObject1)), null);
/*     */       }
/*     */     }
/* 103 */     localWorld.addEntity((Entity)localObject1);
/*     */     
/*     */ 
/* 106 */     Object localObject2 = localObject1;
/* 107 */     NBTTagCompound localNBTTagCompound2 = localNBTTagCompound1;
/* 108 */     while ((localObject2 != null) && (localNBTTagCompound2.hasKeyOfType("Riding", 10))) {
/* 109 */       Entity localEntity = EntityTypes.a(localNBTTagCompound2.getCompound("Riding"), localWorld);
/* 110 */       if (localEntity != null) {
/* 111 */         localEntity.setPositionRotation(d1, d2, d3, localEntity.yaw, localEntity.pitch);
/* 112 */         localWorld.addEntity(localEntity);
/* 113 */         ((Entity)localObject2).mount(localEntity);
/*     */       }
/* 115 */       localObject2 = localEntity;
/* 116 */       localNBTTagCompound2 = localNBTTagCompound2.getCompound("Riding");
/*     */     }
/* 118 */     a(paramICommandListener, this, "commands.summon.success", new Object[0]);
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString, BlockPosition paramBlockPosition)
/*     */   {
/* 124 */     if (paramArrayOfString.length == 1)
/* 125 */       return a(paramArrayOfString, EntityTypes.b());
/* 126 */     if ((paramArrayOfString.length > 1) && (paramArrayOfString.length <= 4)) {
/* 127 */       return a(paramArrayOfString, 1, paramBlockPosition);
/*     */     }
/*     */     
/* 130 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\CommandSummon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */