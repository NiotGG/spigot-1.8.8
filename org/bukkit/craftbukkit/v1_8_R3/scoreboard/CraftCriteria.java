/*    */ package org.bukkit.craftbukkit.v1_8_R3.scoreboard;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap.Builder;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
/*    */ import net.minecraft.server.v1_8_R3.ScoreboardObjective;
/*    */ 
/*    */ final class CraftCriteria
/*    */ {
/*    */   static final Map<String, CraftCriteria> DEFAULTS;
/*    */   
/*    */   static
/*    */   {
/* 15 */     ImmutableMap.Builder<String, CraftCriteria> defaults = com.google.common.collect.ImmutableMap.builder();
/*    */     
/* 17 */     for (Map.Entry<?, ?> entry : IScoreboardCriteria.criteria.entrySet()) {
/* 18 */       String name = entry.getKey().toString();
/* 19 */       IScoreboardCriteria criteria = (IScoreboardCriteria)entry.getValue();
/*    */       
/* 21 */       defaults.put(name, new CraftCriteria(criteria));
/*    */     }
/*    */     
/* 24 */     DEFAULTS = defaults.build(); }
/* 25 */   static final CraftCriteria DUMMY = (CraftCriteria)DEFAULTS.get("dummy");
/*    */   
/*    */   final IScoreboardCriteria criteria;
/*    */   final String bukkitName;
/*    */   
/*    */   private CraftCriteria(String bukkitName)
/*    */   {
/* 32 */     this.bukkitName = bukkitName;
/* 33 */     this.criteria = DUMMY.criteria;
/*    */   }
/*    */   
/*    */   private CraftCriteria(IScoreboardCriteria criteria) {
/* 37 */     this.criteria = criteria;
/* 38 */     this.bukkitName = criteria.getName();
/*    */   }
/*    */   
/*    */   static CraftCriteria getFromNMS(ScoreboardObjective objective) {
/* 42 */     return (CraftCriteria)DEFAULTS.get(objective.getCriteria().getName());
/*    */   }
/*    */   
/*    */   static CraftCriteria getFromBukkit(String name) {
/* 46 */     CraftCriteria criteria = (CraftCriteria)DEFAULTS.get(name);
/* 47 */     if (criteria != null) {
/* 48 */       return criteria;
/*    */     }
/* 50 */     return new CraftCriteria(name);
/*    */   }
/*    */   
/*    */   public boolean equals(Object that)
/*    */   {
/* 55 */     if (!(that instanceof CraftCriteria)) {
/* 56 */       return false;
/*    */     }
/* 58 */     return ((CraftCriteria)that).bukkitName.equals(this.bukkitName);
/*    */   }
/*    */   
/*    */   public int hashCode()
/*    */   {
/* 63 */     return this.bukkitName.hashCode() ^ CraftCriteria.class.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\scoreboard\CraftCriteria.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */