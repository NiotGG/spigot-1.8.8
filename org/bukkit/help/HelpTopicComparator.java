/*    */ package org.bukkit.help;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HelpTopicComparator
/*    */   implements Comparator<HelpTopic>
/*    */ {
/* 16 */   private static final TopicNameComparator tnc = new TopicNameComparator(null);
/*    */   
/* 18 */   public static TopicNameComparator topicNameComparatorInstance() { return tnc; }
/*    */   
/*    */ 
/* 21 */   private static final HelpTopicComparator htc = new HelpTopicComparator();
/*    */   
/* 23 */   public static HelpTopicComparator helpTopicComparatorInstance() { return htc; }
/*    */   
/*    */ 
/*    */ 
/*    */   public int compare(HelpTopic lhs, HelpTopic rhs)
/*    */   {
/* 29 */     return tnc.compare(lhs.getName(), rhs.getName());
/*    */   }
/*    */   
/*    */   public static class TopicNameComparator implements Comparator<String>
/*    */   {
/*    */     public int compare(String lhs, String rhs)
/*    */     {
/* 36 */       boolean lhsStartSlash = lhs.startsWith("/");
/* 37 */       boolean rhsStartSlash = rhs.startsWith("/");
/*    */       
/* 39 */       if ((lhsStartSlash) && (!rhsStartSlash))
/* 40 */         return 1;
/* 41 */       if ((!lhsStartSlash) && (rhsStartSlash)) {
/* 42 */         return -1;
/*    */       }
/* 44 */       return lhs.compareToIgnoreCase(rhs);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\help\HelpTopicComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */