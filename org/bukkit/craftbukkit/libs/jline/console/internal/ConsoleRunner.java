/*    */ package org.bukkit.craftbukkit.libs.jline.console.internal;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.PrintStream;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.StringTokenizer;
/*    */ import org.bukkit.craftbukkit.libs.jline.console.ConsoleReader;
/*    */ import org.bukkit.craftbukkit.libs.jline.console.completer.ArgumentCompleter;
/*    */ import org.bukkit.craftbukkit.libs.jline.console.completer.Completer;
/*    */ import org.bukkit.craftbukkit.libs.jline.console.history.FileHistory;
/*    */ import org.bukkit.craftbukkit.libs.jline.internal.Configuration;
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
/*    */ public class ConsoleRunner
/*    */ {
/*    */   public static final String property = "org.bukkit.craftbukkit.libs.jline.history";
/*    */   
/*    */   public static void main(String[] args)
/*    */     throws Exception
/*    */   {
/* 40 */     List<String> argList = new ArrayList(Arrays.asList(args));
/* 41 */     if (argList.size() == 0) {
/* 42 */       usage();
/* 43 */       return;
/*    */     }
/*    */     
/* 46 */     String historyFileName = System.getProperty("org.bukkit.craftbukkit.libs.jline.history", null);
/*    */     
/* 48 */     String mainClass = (String)argList.remove(0);
/* 49 */     ConsoleReader reader = new ConsoleReader();
/*    */     
/* 51 */     if (historyFileName != null) {
/* 52 */       reader.setHistory(new FileHistory(new File(Configuration.getUserHome(), String.format(".org.bukkit.craftbukkit.libs.jline-%s.%s.history", new Object[] { mainClass, historyFileName }))));
/*    */     }
/*    */     else
/*    */     {
/* 56 */       reader.setHistory(new FileHistory(new File(Configuration.getUserHome(), String.format(".org.bukkit.craftbukkit.libs.jline-%s.history", new Object[] { mainClass }))));
/*    */     }
/*    */     
/*    */ 
/* 60 */     String completors = System.getProperty(ConsoleRunner.class.getName() + ".completers", "");
/* 61 */     List<Completer> completorList = new ArrayList();
/*    */     
/* 63 */     for (StringTokenizer tok = new StringTokenizer(completors, ","); tok.hasMoreTokens();) {
/* 64 */       Object obj = Class.forName(tok.nextToken()).newInstance();
/* 65 */       completorList.add((Completer)obj);
/*    */     }
/*    */     
/* 68 */     if (completorList.size() > 0) {
/* 69 */       reader.addCompleter(new ArgumentCompleter(completorList));
/*    */     }
/*    */     
/* 72 */     ConsoleReaderInputStream.setIn(reader);
/*    */     try
/*    */     {
/* 75 */       Class type = Class.forName(mainClass);
/* 76 */       Method method = type.getMethod("main", new Class[] { String[].class });
/* 77 */       method.invoke(null, new Object[0]);
/*    */     }
/*    */     finally
/*    */     {
/* 81 */       ConsoleReaderInputStream.restoreIn();
/*    */     }
/*    */   }
/*    */   
/*    */   private static void usage() {
/* 86 */     System.out.println("Usage: \n   java [-Djline.history='name'] " + ConsoleRunner.class.getName() + " <target class name> [args]" + "\n\nThe -Djline.history option will avoid history" + "\nmangling when running ConsoleRunner on the same application." + "\n\nargs will be passed directly to the target class name.");
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\internal\ConsoleRunner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */