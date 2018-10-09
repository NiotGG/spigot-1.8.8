/*    */ package org.yaml.snakeyaml.introspector;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import org.yaml.snakeyaml.error.YAMLException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldProperty
/*    */   extends GenericProperty
/*    */ {
/*    */   private final Field field;
/*    */   
/*    */   public FieldProperty(Field field)
/*    */   {
/* 33 */     super(field.getName(), field.getType(), field.getGenericType());
/* 34 */     this.field = field;
/* 35 */     field.setAccessible(true);
/*    */   }
/*    */   
/*    */   public void set(Object object, Object value) throws Exception
/*    */   {
/* 40 */     this.field.set(object, value);
/*    */   }
/*    */   
/*    */   public Object get(Object object)
/*    */   {
/*    */     try {
/* 46 */       return this.field.get(object);
/*    */     } catch (Exception e) {
/* 48 */       throw new YAMLException("Unable to access field " + this.field.getName() + " on object " + object + " : " + e);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\introspector\FieldProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */