/*    */ package org.yaml.snakeyaml.nodes;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NodeTuple
/*    */ {
/*    */   private Node keyNode;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private Node valueNode;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public NodeTuple(Node keyNode, Node valueNode)
/*    */   {
/* 27 */     if ((keyNode == null) || (valueNode == null)) {
/* 28 */       throw new NullPointerException("Nodes must be provided.");
/*    */     }
/* 30 */     this.keyNode = keyNode;
/* 31 */     this.valueNode = valueNode;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Node getKeyNode()
/*    */   {
/* 38 */     return this.keyNode;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Node getValueNode()
/*    */   {
/* 47 */     return this.valueNode;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 52 */     return "<NodeTuple keyNode=" + this.keyNode.toString() + "; valueNode=" + this.valueNode.toString() + ">";
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\nodes\NodeTuple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */