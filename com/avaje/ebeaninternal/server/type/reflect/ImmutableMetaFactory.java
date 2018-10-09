/*     */ package com.avaje.ebeaninternal.server.type.reflect;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImmutableMetaFactory
/*     */ {
/*  35 */   private static final Logger logger = Logger.getLogger(ImmutableMetaFactory.class.getName());
/*     */   
/*     */   public ImmutableMeta createImmutableMeta(Class<?> cls)
/*     */   {
/*  39 */     ScoreConstructor[] scoreConstructors = scoreConstructors(cls);
/*     */     
/*  41 */     ArrayList<RuntimeException> errors = new ArrayList();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  46 */     for (int i = 0; i < scoreConstructors.length; i++) {
/*  47 */       Constructor<?> constructor = scoreConstructors[i].constructor;
/*     */       try
/*     */       {
/*  50 */         Method[] getters = findGetters(cls, constructor);
/*     */         
/*  52 */         return new ImmutableMeta(constructor, getters);
/*     */       }
/*     */       catch (NoSuchMethodException e) {
/*  55 */         String msg = "Error finding getter method on " + cls + " with constructor " + constructor;
/*  56 */         errors.add(new RuntimeException(msg, e));
/*     */       }
/*     */     }
/*     */     
/*  60 */     String msg = "Was unable to use reflection to find a constructor and appropriate getters forimmutable type " + cls + ".  The errors while looking for the getter methods follow:";
/*     */     
/*  62 */     logger.severe(msg);
/*     */     
/*  64 */     for (RuntimeException runtimeException : errors) {
/*  65 */       logger.log(Level.SEVERE, "Error with " + cls, runtimeException);
/*     */     }
/*     */     
/*  68 */     msg = "Unable to use reflection to build ImmutableMeta for " + cls + ".  Associated Errors trying to find a constructor and getter methods have been logged";
/*     */     
/*     */ 
/*  71 */     throw new RuntimeException(msg);
/*     */   }
/*     */   
/*     */   private ScoreConstructor getScore(Constructor<?> c)
/*     */   {
/*  76 */     Class<?>[] parameterTypes = c.getParameterTypes();
/*  77 */     int score = 64536 * parameterTypes.length;
/*     */     
/*  79 */     for (int i = 0; i < parameterTypes.length; i++) {
/*  80 */       if (parameterTypes[i].equals(String.class))
/*     */       {
/*     */ 
/*  83 */         score += 1;
/*     */       }
/*  85 */       else if (parameterTypes[i].equals(BigDecimal.class)) {
/*  86 */         score -= 10;
/*  87 */       } else if (parameterTypes[i].equals(Timestamp.class)) {
/*  88 */         score -= 10;
/*  89 */       } else if (parameterTypes[i].equals(Double.TYPE)) {
/*  90 */         score -= 9;
/*  91 */       } else if (parameterTypes[i].equals(Double.class)) {
/*  92 */         score -= 8;
/*  93 */       } else if (parameterTypes[i].equals(Float.TYPE)) {
/*  94 */         score -= 7;
/*  95 */       } else if (parameterTypes[i].equals(Float.class)) {
/*  96 */         score -= 6;
/*  97 */       } else if (parameterTypes[i].equals(Long.TYPE)) {
/*  98 */         score -= 5;
/*  99 */       } else if (parameterTypes[i].equals(Long.class)) {
/* 100 */         score -= 4;
/* 101 */       } else if (parameterTypes[i].equals(Integer.TYPE)) {
/* 102 */         score -= 3;
/* 103 */       } else if (parameterTypes[i].equals(Integer.class)) {
/* 104 */         score -= 2;
/*     */       }
/*     */     }
/*     */     
/* 108 */     return new ScoreConstructor(score, c, null);
/*     */   }
/*     */   
/*     */ 
/*     */   private ScoreConstructor[] scoreConstructors(Class<?> cls)
/*     */   {
/* 114 */     int maxParamCount = 0;
/*     */     
/* 116 */     Constructor<?>[] constructors = cls.getConstructors();
/*     */     
/* 118 */     ScoreConstructor[] score = new ScoreConstructor[constructors.length];
/*     */     
/* 120 */     for (int i = 0; i < constructors.length; i++) {
/* 121 */       score[i] = getScore(constructors[i]);
/* 122 */       if (score[i].hasDuplicateParamTypes()) {
/* 123 */         String msg = "Duplicate parameter types in " + score[i].constructor;
/* 124 */         throw new IllegalStateException(msg);
/*     */       }
/* 126 */       if (score[i].getParamCount() > maxParamCount) {
/* 127 */         maxParamCount = score[i].getParamCount();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 132 */     ArrayList<ScoreConstructor> list = new ArrayList();
/* 133 */     for (int i = 0; i < score.length; i++) {
/* 134 */       if (score[i].getParamCount() == maxParamCount) {
/* 135 */         list.add(score[i]);
/*     */       }
/*     */     }
/*     */     
/* 139 */     score = (ScoreConstructor[])list.toArray(new ScoreConstructor[list.size()]);
/*     */     
/*     */ 
/* 142 */     Arrays.sort(score);
/*     */     
/* 144 */     return score;
/*     */   }
/*     */   
/*     */ 
/*     */   private Method[] findGetters(Class<?> cls, Constructor<?> c)
/*     */     throws NoSuchMethodException
/*     */   {
/* 151 */     Method[] methods = cls.getMethods();
/*     */     
/* 153 */     Class<?>[] paramTypes = c.getParameterTypes();
/*     */     
/* 155 */     Method[] readers = new Method[paramTypes.length];
/*     */     
/* 157 */     for (int i = 0; i < paramTypes.length; i++) {
/* 158 */       Method getter = findGetter(paramTypes[i], methods);
/* 159 */       if ((getter == null) && (paramTypes.length == 1) && (paramTypes[i].equals(String.class))) {
/* 160 */         getter = findToString(cls);
/*     */       }
/* 162 */       if (getter == null) {
/* 163 */         throw new NoSuchMethodException("Get Method not found for " + paramTypes[i] + " in " + cls);
/*     */       }
/* 165 */       readers[i] = getter;
/*     */     }
/*     */     
/* 168 */     return readers;
/*     */   }
/*     */   
/*     */   private Method findToString(Class<?> cls) throws NoSuchMethodException
/*     */   {
/*     */     try {
/* 174 */       return cls.getDeclaredMethod("toString", new Class[0]);
/*     */     } catch (SecurityException e) {
/* 176 */       throw new NoSuchMethodException("SecurityException " + e + " trying to find toString method on " + cls);
/*     */     }
/*     */   }
/*     */   
/*     */   private Method findGetter(Class<?> paramType, Method[] methods)
/*     */   {
/* 182 */     for (int i = 0; i < methods.length; i++) {
/* 183 */       if (!Modifier.isStatic(methods[i].getModifiers()))
/*     */       {
/*     */ 
/* 186 */         if (methods[i].getParameterTypes().length == 0)
/*     */         {
/* 188 */           String methName = methods[i].getName();
/* 189 */           if (!methName.equals("hashCode"))
/*     */           {
/* 191 */             if (!methName.equals("toString"))
/*     */             {
/*     */ 
/* 194 */               Class<?> returnType = methods[i].getReturnType();
/* 195 */               if (paramType.equals(returnType))
/* 196 */                 return methods[i];
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 202 */     return null;
/*     */   }
/*     */   
/*     */   private static class ScoreConstructor implements Comparable<ScoreConstructor>
/*     */   {
/*     */     final int score;
/*     */     final Constructor<?> constructor;
/*     */     
/*     */     private ScoreConstructor(int score, Constructor<?> constructor) {
/* 211 */       this.score = score;
/* 212 */       this.constructor = constructor;
/*     */     }
/*     */     
/*     */ 
/*     */     public boolean equals(Object obj)
/*     */     {
/* 218 */       return obj == this;
/*     */     }
/*     */     
/*     */     public int compareTo(ScoreConstructor o) {
/* 222 */       return this.score == o.score ? 0 : this.score < o.score ? -1 : 1;
/*     */     }
/*     */     
/*     */     public int getParamCount() {
/* 226 */       return this.constructor.getParameterTypes().length;
/*     */     }
/*     */     
/*     */     public boolean hasDuplicateParamTypes()
/*     */     {
/* 231 */       Class<?>[] parameterTypes = this.constructor.getParameterTypes();
/* 232 */       if (parameterTypes.length < 2) {
/* 233 */         return false;
/*     */       }
/* 235 */       HashSet<Class<?>> set = new HashSet();
/* 236 */       for (int i = 0; i < parameterTypes.length; i++) {
/* 237 */         if (!set.add(parameterTypes[i])) {
/* 238 */           return true;
/*     */         }
/*     */       }
/* 241 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\reflect\ImmutableMetaFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */