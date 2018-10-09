/*    */ package org.yaml.snakeyaml.introspector;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MissingProperty
/*    */   extends Property
/*    */ {
/*    */   public MissingProperty(String name)
/*    */   {
/* 25 */     super(name, Object.class);
/*    */   }
/*    */   
/*    */   public Class<?>[] getActualTypeArguments()
/*    */   {
/* 30 */     return new Class[0];
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void set(Object object, Object value)
/*    */     throws Exception
/*    */   {}
/*    */   
/*    */ 
/*    */   public Object get(Object object)
/*    */   {
/* 42 */     return object;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\introspector\MissingProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */