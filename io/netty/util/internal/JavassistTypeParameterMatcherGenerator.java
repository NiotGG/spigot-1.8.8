/*    */ package io.netty.util.internal;
/*    */ 
/*    */ import io.netty.util.internal.logging.InternalLogger;
/*    */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*    */ import java.lang.reflect.Method;
/*    */ import javassist.ClassClassPath;
/*    */ import javassist.ClassPath;
/*    */ import javassist.ClassPool;
/*    */ import javassist.CtClass;
/*    */ import javassist.CtMethod;
/*    */ import javassist.NotFoundException;
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
/*    */ public final class JavassistTypeParameterMatcherGenerator
/*    */ {
/* 32 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(JavassistTypeParameterMatcherGenerator.class);
/*    */   
/*    */ 
/* 35 */   private static final ClassPool classPool = new ClassPool(true);
/*    */   
/*    */   static {
/* 38 */     classPool.appendClassPath(new ClassClassPath(NoOpTypeParameterMatcher.class));
/*    */   }
/*    */   
/*    */   public static void appendClassPath(ClassPath paramClassPath) {
/* 42 */     classPool.appendClassPath(paramClassPath);
/*    */   }
/*    */   
/*    */   public static void appendClassPath(String paramString) throws NotFoundException {
/* 46 */     classPool.appendClassPath(paramString);
/*    */   }
/*    */   
/*    */   public static TypeParameterMatcher generate(Class<?> paramClass) {
/* 50 */     ClassLoader localClassLoader = PlatformDependent.getContextClassLoader();
/* 51 */     if (localClassLoader == null) {
/* 52 */       localClassLoader = PlatformDependent.getSystemClassLoader();
/*    */     }
/* 54 */     return generate(paramClass, localClassLoader);
/*    */   }
/*    */   
/*    */   public static TypeParameterMatcher generate(Class<?> paramClass, ClassLoader paramClassLoader) {
/* 58 */     String str1 = typeName(paramClass);
/* 59 */     String str2 = "io.netty.util.internal.__matchers__." + str1 + "Matcher";
/*    */     try
/*    */     {
/* 62 */       return (TypeParameterMatcher)Class.forName(str2, true, paramClassLoader).newInstance();
/*    */ 
/*    */     }
/*    */     catch (Exception localException1)
/*    */     {
/* 67 */       CtClass localCtClass = classPool.getAndRename(NoOpTypeParameterMatcher.class.getName(), str2);
/* 68 */       localCtClass.setModifiers(localCtClass.getModifiers() | 0x10);
/* 69 */       localCtClass.getDeclaredMethod("match").setBody("{ return $1 instanceof " + str1 + "; }");
/* 70 */       byte[] arrayOfByte = localCtClass.toBytecode();
/* 71 */       localCtClass.detach();
/* 72 */       Method localMethod = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, Integer.TYPE, Integer.TYPE });
/*    */       
/* 74 */       localMethod.setAccessible(true);
/*    */       
/* 76 */       Class localClass = (Class)localMethod.invoke(paramClassLoader, new Object[] { str2, arrayOfByte, Integer.valueOf(0), Integer.valueOf(arrayOfByte.length) });
/* 77 */       if (paramClass != Object.class) {
/* 78 */         logger.debug("Generated: {}", localClass.getName());
/*    */       }
/*    */       
/*    */ 
/* 82 */       return (TypeParameterMatcher)localClass.newInstance();
/*    */     } catch (RuntimeException localRuntimeException) {
/* 84 */       throw localRuntimeException;
/*    */     } catch (Exception localException2) {
/* 86 */       throw new RuntimeException(localException2);
/*    */     }
/*    */   }
/*    */   
/*    */   private static String typeName(Class<?> paramClass) {
/* 91 */     if (paramClass.isArray()) {
/* 92 */       return typeName(paramClass.getComponentType()) + "[]";
/*    */     }
/*    */     
/* 95 */     return paramClass.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\internal\JavassistTypeParameterMatcherGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */