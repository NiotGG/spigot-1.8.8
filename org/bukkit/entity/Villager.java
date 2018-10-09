/*    */ package org.bukkit.entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract interface Villager
/*    */   extends Ageable, NPC
/*    */ {
/*    */   public abstract Profession getProfession();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public abstract void setProfession(Profession paramProfession);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static enum Profession
/*    */   {
/* 27 */     FARMER(0), 
/* 28 */     LIBRARIAN(1), 
/* 29 */     PRIEST(2), 
/* 30 */     BLACKSMITH(3), 
/* 31 */     BUTCHER(4);
/*    */     
/* 33 */     static { professions = new Profession[values().length];
/*    */       
/*    */       Profession[] arrayOfProfession;
/*    */       
/* 37 */       int i = (arrayOfProfession = values()).length; for (int j = 0; j < i; j++) { Profession type = arrayOfProfession[j];
/* 38 */         professions[type.getId()] = type;
/*    */       }
/*    */     }
/*    */     
/*    */     private Profession(int id) {
/* 43 */       this.id = id;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */     private static final Profession[] professions;
/*    */     
/*    */ 
/*    */     @Deprecated
/*    */     public int getId()
/*    */     {
/* 54 */       return this.id;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */     private final int id;
/*    */     
/*    */ 
/*    */     @Deprecated
/*    */     public static Profession getProfession(int id)
/*    */     {
/* 66 */       return id >= professions.length ? null : professions[id];
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Villager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */