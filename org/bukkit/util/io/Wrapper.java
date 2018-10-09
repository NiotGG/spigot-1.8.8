/*    */ package org.bukkit.util.io;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.ImmutableMap.Builder;
/*    */ import java.io.Serializable;
/*    */ import java.util.Map;
/*    */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*    */ import org.bukkit.configuration.serialization.ConfigurationSerialization;
/*    */ 
/*    */ class Wrapper<T extends Map<String, ?>,  extends Serializable> implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -986209235411767547L;
/*    */   final T map;
/*    */   
/*    */   static Wrapper<ImmutableMap<String, ?>> newWrapper(ConfigurationSerializable obj)
/*    */   {
/* 17 */     return new Wrapper(ImmutableMap.builder().put("==", ConfigurationSerialization.getAlias(obj.getClass())).putAll(obj.serialize()).build());
/*    */   }
/*    */   
/*    */   private Wrapper(T map) {
/* 21 */     this.map = map;
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\util\io\Wrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */