/*    */ package org.yaml.snakeyaml.composer;
/*    */ 
/*    */ import org.yaml.snakeyaml.error.Mark;
/*    */ import org.yaml.snakeyaml.error.MarkedYAMLException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ComposerException
/*    */   extends MarkedYAMLException
/*    */ {
/*    */   private static final long serialVersionUID = 2146314636913113935L;
/*    */   
/*    */   protected ComposerException(String context, Mark contextMark, String problem, Mark problemMark)
/*    */   {
/* 25 */     super(context, contextMark, problem, problemMark);
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\composer\ComposerException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */