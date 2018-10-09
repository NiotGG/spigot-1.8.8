/*    */ package org.fusesource.jansi.internal;
/*    */ 
/*    */ import org.fusesource.hawtjni.runtime.JniClass;
/*    */ import org.fusesource.hawtjni.runtime.JniField;
/*    */ import org.fusesource.hawtjni.runtime.JniMethod;
/*    */ import org.fusesource.hawtjni.runtime.Library;
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
/*    */ @JniClass
/*    */ public class CLibrary
/*    */ {
/*    */   @JniMethod(flags={org.fusesource.hawtjni.runtime.MethodFlag.CONSTANT_INITIALIZER})
/*    */   private static final native void init();
/*    */   
/*    */   @JniMethod(conditional="defined(HAVE_ISATTY)")
/*    */   public static final native int isatty(int paramInt);
/*    */   
/* 35 */   private static final Library LIBRARY = new Library("jansi", CLibrary.class);
/*    */   
/* 37 */   static { LIBRARY.load();
/* 38 */     init();
/*    */   }
/*    */   
/*    */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, conditional="defined(STDIN_FILENO)")
/*    */   public static int STDIN_FILENO;
/*    */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, conditional="defined(STDOUT_FILENO)")
/*    */   public static int STDOUT_FILENO;
/*    */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, conditional="defined(STDERR_FILENO)")
/*    */   public static int STDERR_FILENO;
/*    */   @JniField(flags={org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT}, accessor="1", conditional="defined(HAVE_ISATTY)")
/*    */   public static boolean HAVE_ISATTY;
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\fusesource\jansi\internal\CLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */