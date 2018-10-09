/*     */ package org.bukkit.configuration.serialization;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.FireworkEffect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.block.banner.Pattern;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.util.BlockVector;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigurationSerialization
/*     */ {
/*     */   public static final String SERIALIZED_TYPE_KEY = "==";
/*     */   private final Class<? extends ConfigurationSerializable> clazz;
/*  29 */   private static Map<String, Class<? extends ConfigurationSerializable>> aliases = new HashMap();
/*     */   
/*     */   static {
/*  32 */     registerClass(Vector.class);
/*  33 */     registerClass(BlockVector.class);
/*  34 */     registerClass(ItemStack.class);
/*  35 */     registerClass(Color.class);
/*  36 */     registerClass(PotionEffect.class);
/*  37 */     registerClass(FireworkEffect.class);
/*  38 */     registerClass(Pattern.class);
/*  39 */     registerClass(Location.class);
/*     */   }
/*     */   
/*     */   protected ConfigurationSerialization(Class<? extends ConfigurationSerializable> clazz) {
/*  43 */     this.clazz = clazz;
/*     */   }
/*     */   
/*     */   protected Method getMethod(String name, boolean isStatic) {
/*     */     try {
/*  48 */       Method method = this.clazz.getDeclaredMethod(name, new Class[] { Map.class });
/*     */       
/*  50 */       if (!ConfigurationSerializable.class.isAssignableFrom(method.getReturnType())) {
/*  51 */         return null;
/*     */       }
/*  53 */       if (Modifier.isStatic(method.getModifiers()) != isStatic) {
/*  54 */         return null;
/*     */       }
/*     */       
/*  57 */       return method;
/*     */     } catch (NoSuchMethodException localNoSuchMethodException) {
/*  59 */       return null;
/*     */     } catch (SecurityException localSecurityException) {}
/*  61 */     return null;
/*     */   }
/*     */   
/*     */   protected Constructor<? extends ConfigurationSerializable> getConstructor()
/*     */   {
/*     */     try {
/*  67 */       return this.clazz.getConstructor(new Class[] { Map.class });
/*     */     } catch (NoSuchMethodException localNoSuchMethodException) {
/*  69 */       return null;
/*     */     } catch (SecurityException localSecurityException) {}
/*  71 */     return null;
/*     */   }
/*     */   
/*     */   protected ConfigurationSerializable deserializeViaMethod(Method method, Map<String, ?> args)
/*     */   {
/*     */     try {
/*  77 */       ConfigurationSerializable result = (ConfigurationSerializable)method.invoke(null, new Object[] { args });
/*     */       
/*  79 */       if (result == null) {
/*  80 */         Logger.getLogger(ConfigurationSerialization.class.getName()).log(Level.SEVERE, "Could not call method '" + method.toString() + "' of " + this.clazz + " for deserialization: method returned null");
/*     */       } else {
/*  82 */         return result;
/*     */       }
/*     */     } catch (Throwable ex) {
/*  85 */       Logger.getLogger(ConfigurationSerialization.class.getName()).log(
/*  86 */         Level.SEVERE, 
/*  87 */         "Could not call method '" + method.toString() + "' of " + this.clazz + " for deserialization", 
/*  88 */         (ex instanceof InvocationTargetException) ? ex.getCause() : ex);
/*     */     }
/*     */     
/*  91 */     return null;
/*     */   }
/*     */   
/*     */   protected ConfigurationSerializable deserializeViaCtor(Constructor<? extends ConfigurationSerializable> ctor, Map<String, ?> args) {
/*     */     try {
/*  96 */       return (ConfigurationSerializable)ctor.newInstance(new Object[] { args });
/*     */     } catch (Throwable ex) {
/*  98 */       Logger.getLogger(ConfigurationSerialization.class.getName()).log(
/*  99 */         Level.SEVERE, 
/* 100 */         "Could not call constructor '" + ctor.toString() + "' of " + this.clazz + " for deserialization", 
/* 101 */         (ex instanceof InvocationTargetException) ? ex.getCause() : ex);
/*     */     }
/*     */     
/* 104 */     return null;
/*     */   }
/*     */   
/*     */   public ConfigurationSerializable deserialize(Map<String, ?> args) {
/* 108 */     Validate.notNull(args, "Args must not be null");
/*     */     
/* 110 */     ConfigurationSerializable result = null;
/* 111 */     Method method = null;
/*     */     
/* 113 */     if (result == null) {
/* 114 */       method = getMethod("deserialize", true);
/*     */       
/* 116 */       if (method != null) {
/* 117 */         result = deserializeViaMethod(method, args);
/*     */       }
/*     */     }
/*     */     
/* 121 */     if (result == null) {
/* 122 */       method = getMethod("valueOf", true);
/*     */       
/* 124 */       if (method != null) {
/* 125 */         result = deserializeViaMethod(method, args);
/*     */       }
/*     */     }
/*     */     
/* 129 */     if (result == null) {
/* 130 */       Constructor<? extends ConfigurationSerializable> constructor = getConstructor();
/*     */       
/* 132 */       if (constructor != null) {
/* 133 */         result = deserializeViaCtor(constructor, args);
/*     */       }
/*     */     }
/*     */     
/* 137 */     return result;
/*     */   }
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
/*     */   public static ConfigurationSerializable deserializeObject(Map<String, ?> args, Class<? extends ConfigurationSerializable> clazz)
/*     */   {
/* 156 */     return new ConfigurationSerialization(clazz).deserialize(args);
/*     */   }
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
/*     */   public static ConfigurationSerializable deserializeObject(Map<String, ?> args)
/*     */   {
/* 174 */     Class<? extends ConfigurationSerializable> clazz = null;
/*     */     
/* 176 */     if (args.containsKey("==")) {
/*     */       try {
/* 178 */         String alias = (String)args.get("==");
/*     */         
/* 180 */         if (alias == null) {
/* 181 */           throw new IllegalArgumentException("Cannot have null alias");
/*     */         }
/* 183 */         clazz = getClassByAlias(alias);
/* 184 */         if (clazz != null) break label95;
/* 185 */         throw new IllegalArgumentException("Specified class does not exist ('" + alias + "')");
/*     */       }
/*     */       catch (ClassCastException ex) {
/* 188 */         ex.fillInStackTrace();
/* 189 */         throw ex;
/*     */       }
/*     */     } else {
/* 192 */       throw new IllegalArgumentException("Args doesn't contain type key ('==')");
/*     */     }
/*     */     label95:
/* 195 */     return new ConfigurationSerialization(clazz).deserialize(args);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void registerClass(Class<? extends ConfigurationSerializable> clazz)
/*     */   {
/* 205 */     DelegateDeserialization delegate = (DelegateDeserialization)clazz.getAnnotation(DelegateDeserialization.class);
/*     */     
/* 207 */     if (delegate == null) {
/* 208 */       registerClass(clazz, getAlias(clazz));
/* 209 */       registerClass(clazz, clazz.getName());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void registerClass(Class<? extends ConfigurationSerializable> clazz, String alias)
/*     */   {
/* 222 */     aliases.put(alias, clazz);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void unregisterClass(String alias)
/*     */   {
/* 231 */     aliases.remove(alias);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void unregisterClass(Class<? extends ConfigurationSerializable> clazz)
/*     */   {
/* 241 */     while (aliases.values().remove(clazz)) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Class<? extends ConfigurationSerializable> getClassByAlias(String alias)
/*     */   {
/* 254 */     return (Class)aliases.get(alias);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getAlias(Class<? extends ConfigurationSerializable> clazz)
/*     */   {
/* 265 */     DelegateDeserialization delegate = (DelegateDeserialization)clazz.getAnnotation(DelegateDeserialization.class);
/*     */     
/* 267 */     if (delegate != null) {
/* 268 */       if ((delegate.value() == null) || (delegate.value() == clazz)) {
/* 269 */         delegate = null;
/*     */       } else {
/* 271 */         return getAlias(delegate.value());
/*     */       }
/*     */     }
/*     */     
/* 275 */     if (delegate == null) {
/* 276 */       SerializableAs alias = (SerializableAs)clazz.getAnnotation(SerializableAs.class);
/*     */       
/* 278 */       if ((alias != null) && (alias.value() != null)) {
/* 279 */         return alias.value();
/*     */       }
/*     */     }
/*     */     
/* 283 */     return clazz.getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\serialization\ConfigurationSerialization.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */