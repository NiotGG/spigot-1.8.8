/*    */ package org.bukkit.craftbukkit.libs.org.ibex.nestedvm;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable;
/*    */ import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RuntimeCompiler
/*    */ {
/* 13 */   public static Class compile(Seekable paramSeekable) throws IOException, Compiler.Exn { return compile(paramSeekable, null); }
/* 14 */   public static Class compile(Seekable paramSeekable, String paramString) throws IOException, Compiler.Exn { return compile(paramSeekable, paramString, null); }
/*    */   
/*    */   public static Class compile(Seekable paramSeekable, String paramString1, String paramString2) throws IOException, Compiler.Exn {
/* 17 */     String str = "nestedvm.runtimecompiled";
/*    */     byte[] arrayOfByte;
/*    */     try {
/* 20 */       arrayOfByte = runCompiler(paramSeekable, str, paramString1, paramString2, null);
/*    */     } catch (Compiler.Exn localExn) {
/* 22 */       if ((localExn.getMessage() != null) || (localExn.getMessage().indexOf("constant pool full") != -1)) {
/* 23 */         arrayOfByte = runCompiler(paramSeekable, str, paramString1, paramString2, "lessconstants");
/*    */       } else
/* 25 */         throw localExn;
/*    */     }
/* 27 */     return new SingleClassLoader(null).fromBytes(str, arrayOfByte);
/*    */   }
/*    */   
/*    */   private static byte[] runCompiler(Seekable paramSeekable, String paramString1, String paramString2, String paramString3, String paramString4) throws IOException, Compiler.Exn {
/* 31 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/*    */     try
/*    */     {
/* 34 */       ClassFileCompiler localClassFileCompiler = new ClassFileCompiler(paramSeekable, paramString1, localByteArrayOutputStream);
/* 35 */       localClassFileCompiler.parseOptions("nosupportcall,maxinsnpermethod=256");
/* 36 */       localClassFileCompiler.setSource(paramString3);
/* 37 */       if (paramString2 != null) localClassFileCompiler.parseOptions(paramString2);
/* 38 */       if (paramString4 != null) localClassFileCompiler.parseOptions(paramString4);
/* 39 */       localClassFileCompiler.go();
/*    */     } finally {
/* 41 */       paramSeekable.seek(0);
/*    */     }
/*    */     
/* 44 */     localByteArrayOutputStream.close();
/* 45 */     return localByteArrayOutputStream.toByteArray();
/*    */   }
/*    */   
/* 48 */   private static class SingleClassLoader extends ClassLoader { SingleClassLoader(RuntimeCompiler.1 param1) { this(); }
/*    */     
/*    */ 
/* 51 */     public Class loadClass(String paramString, boolean paramBoolean) throws ClassNotFoundException { return super.loadClass(paramString, paramBoolean); }
/*    */     
/* 53 */     public Class fromBytes(String paramString, byte[] paramArrayOfByte) { return fromBytes(paramString, paramArrayOfByte, 0, paramArrayOfByte.length); }
/*    */     
/* 55 */     public Class fromBytes(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2) { Class localClass = super.defineClass(paramString, paramArrayOfByte, paramInt1, paramInt2);
/* 56 */       resolveClass(localClass);
/* 57 */       return localClass;
/*    */     }
/*    */     
/*    */     private SingleClassLoader() {} }
/*    */   
/* 62 */   public static void main(String[] paramArrayOfString) throws Exception { if (paramArrayOfString.length == 0) {
/* 63 */       System.err.println("Usage: RuntimeCompiler mipsbinary");
/* 64 */       System.exit(1);
/*    */     }
/* 66 */     UnixRuntime localUnixRuntime = (UnixRuntime)compile(new Seekable.File(paramArrayOfString[0]), "unixruntime").newInstance();
/* 67 */     System.err.println("Instansiated: " + localUnixRuntime);
/* 68 */     System.exit(UnixRuntime.runAndExec(localUnixRuntime, paramArrayOfString));
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\org\ibex\nestedvm\RuntimeCompiler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */