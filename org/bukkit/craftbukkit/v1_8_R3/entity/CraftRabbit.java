/*    */ package org.bukkit.craftbukkit.v1_8_R3.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_8_R3.EntityRabbit;
/*    */ import net.minecraft.server.v1_8_R3.World;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Rabbit.Type;
/*    */ 
/*    */ public class CraftRabbit extends CraftAnimals implements org.bukkit.entity.Rabbit
/*    */ {
/*    */   public CraftRabbit(CraftServer server, EntityRabbit entity)
/*    */   {
/* 14 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public EntityRabbit getHandle()
/*    */   {
/* 19 */     return (EntityRabbit)this.entity;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 24 */     return "CraftRabbit{RabbitType=" + getRabbitType() + "}";
/*    */   }
/*    */   
/*    */   public EntityType getType()
/*    */   {
/* 29 */     return EntityType.RABBIT;
/*    */   }
/*    */   
/*    */   public Rabbit.Type getRabbitType()
/*    */   {
/* 34 */     int type = getHandle().getRabbitType();
/* 35 */     return CraftMagicMapping.fromMagic(type);
/*    */   }
/*    */   
/*    */   public void setRabbitType(Rabbit.Type type)
/*    */   {
/* 40 */     EntityRabbit entity = getHandle();
/* 41 */     if (getRabbitType() == Rabbit.Type.THE_KILLER_BUNNY)
/*    */     {
/* 43 */       World world = ((CraftWorld)getWorld()).getHandle();
/* 44 */       entity.goalSelector = new net.minecraft.server.v1_8_R3.PathfinderGoalSelector((world != null) && (world.methodProfiler != null) ? world.methodProfiler : null);
/* 45 */       entity.targetSelector = new net.minecraft.server.v1_8_R3.PathfinderGoalSelector((world != null) && (world.methodProfiler != null) ? world.methodProfiler : null);
/* 46 */       entity.initializePathFinderGoals();
/*    */     }
/*    */     
/* 49 */     entity.setRabbitType(CraftMagicMapping.toMagic(type));
/*    */   }
/*    */   
/*    */   private static class CraftMagicMapping
/*    */   {
/* 54 */     private static final int[] types = new int[Rabbit.Type.values().length];
/* 55 */     private static final Rabbit.Type[] reverse = new Rabbit.Type[Rabbit.Type.values().length];
/*    */     
/*    */     static {
/* 58 */       set(Rabbit.Type.BROWN, 0);
/* 59 */       set(Rabbit.Type.WHITE, 1);
/* 60 */       set(Rabbit.Type.BLACK, 2);
/* 61 */       set(Rabbit.Type.BLACK_AND_WHITE, 3);
/* 62 */       set(Rabbit.Type.GOLD, 4);
/* 63 */       set(Rabbit.Type.SALT_AND_PEPPER, 5);
/* 64 */       set(Rabbit.Type.THE_KILLER_BUNNY, 99);
/*    */     }
/*    */     
/*    */     private static void set(Rabbit.Type type, int value) {
/* 68 */       types[type.ordinal()] = value;
/* 69 */       if (value < reverse.length) {
/* 70 */         reverse[value] = type;
/*    */       }
/*    */     }
/*    */     
/*    */     public static Rabbit.Type fromMagic(int magic) {
/* 75 */       if ((magic >= 0) && (magic < reverse.length))
/* 76 */         return reverse[magic];
/* 77 */       if (magic == 99) {
/* 78 */         return Rabbit.Type.THE_KILLER_BUNNY;
/*    */       }
/* 80 */       return null;
/*    */     }
/*    */     
/*    */     public static int toMagic(Rabbit.Type type)
/*    */     {
/* 85 */       return types[type.ordinal()];
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\entity\CraftRabbit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */