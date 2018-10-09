/*     */ package org.bukkit.craftbukkit;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.craftbukkit.libs.joptsimple.ArgumentAcceptingOptionSpec;
/*     */ import org.bukkit.craftbukkit.libs.joptsimple.OptionException;
/*     */ import org.bukkit.craftbukkit.libs.joptsimple.OptionParser;
/*     */ import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
/*     */ import org.bukkit.craftbukkit.libs.joptsimple.OptionSpecBuilder;
/*     */ 
/*     */ public class Main
/*     */ {
/*  16 */   public static boolean useJline = true;
/*  17 */   public static boolean useConsole = true;
/*     */   
/*     */   public static void main(String[] args)
/*     */   {
/*  21 */     OptionParser parser = new OptionParser() {};
/* 128 */     OptionSet options = null;
/*     */     try
/*     */     {
/* 131 */       options = parser.parse(args);
/*     */     } catch (OptionException ex) {
/* 133 */       Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
/*     */     }
/*     */     
/* 136 */     if ((options == null) || (options.has("?"))) {
/*     */       try {
/* 138 */         parser.printHelpOn(System.out);
/*     */       } catch (IOException ex) {
/* 140 */         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
/*     */       }
/* 142 */     } else if (options.has("v")) {
/* 143 */       System.out.println(org.bukkit.craftbukkit.v1_8_R3.CraftServer.class.getPackage().getImplementationVersion());
/*     */     }
/*     */     else {
/* 146 */       String path = new File(".").getAbsolutePath();
/* 147 */       if ((path.contains("!")) || (path.contains("+"))) {
/* 148 */         System.err.println("Cannot run server in a directory with ! or + in the pathname. Please rename the affected folders and try again.");
/* 149 */         return;
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 154 */         String jline_UnsupportedTerminal = new String(new char[] { 'j', 'l', 'i', 'n', 'e', '.', 'U', 'n', 's', 'u', 'p', 'p', 'o', 'r', 't', 'e', 'd', 'T', 'e', 'r', 'm', 'i', 'n', 'a', 'l' });
/* 155 */         String jline_terminal = new String(new char[] { 'j', 'l', 'i', 'n', 'e', '.', 't', 'e', 'r', 'm', 'i', 'n', 'a', 'l' });
/*     */         
/* 157 */         useJline = !jline_UnsupportedTerminal.equals(System.getProperty(jline_terminal));
/*     */         
/* 159 */         if (options.has("nojline")) {
/* 160 */           System.setProperty("user.language", "en");
/* 161 */           useJline = false;
/*     */         }
/*     */         
/* 164 */         if (useJline) {
/* 165 */           org.fusesource.jansi.AnsiConsole.systemInstall();
/*     */         }
/*     */         else {
/* 168 */           System.setProperty("org.bukkit.craftbukkit.libs.jline.terminal", org.bukkit.craftbukkit.libs.jline.UnsupportedTerminal.class.getName());
/*     */         }
/*     */         
/*     */ 
/* 172 */         if (options.has("noconsole")) {
/* 173 */           useConsole = false;
/*     */         }
/*     */         
/*     */ 
/* 177 */         int maxPermGen = 0;
/* 178 */         for (String s : java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments())
/*     */         {
/* 180 */           if (s.startsWith("-XX:MaxPermSize"))
/*     */           {
/* 182 */             maxPermGen = Integer.parseInt(s.replaceAll("[^\\d]", ""));
/* 183 */             maxPermGen <<= 10 * "kmg".indexOf(Character.toLowerCase(s.charAt(s.length() - 1)));
/*     */           }
/*     */         }
/* 186 */         if ((Float.parseFloat(System.getProperty("java.class.version")) < 52.0F) && (maxPermGen < 131072))
/*     */         {
/* 188 */           System.out.println("Warning, your max perm gen size is not set or less than 128mb. It is recommended you restart Java with the following argument: -XX:MaxPermSize=128M");
/* 189 */           System.out.println("Please see http://www.spigotmc.org/wiki/changing-permgen-size/ for more details and more in-depth instructions.");
/*     */         }
/*     */         
/* 192 */         System.out.println("Loading libraries, please wait...");
/* 193 */         net.minecraft.server.v1_8_R3.MinecraftServer.main(options);
/*     */       } catch (Throwable t) {
/* 195 */         t.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static java.util.List<String> asList(String... params) {
/* 201 */     return java.util.Arrays.asList(params);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\Main.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */