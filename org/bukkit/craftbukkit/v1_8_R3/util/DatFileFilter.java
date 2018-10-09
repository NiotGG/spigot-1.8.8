/*   */ package org.bukkit.craftbukkit.v1_8_R3.util;
/*   */ 
/*   */ import java.io.File;
/*   */ 
/*   */ public class DatFileFilter implements java.io.FilenameFilter
/*   */ {
/*   */   public boolean accept(File dir, String name) {
/* 8 */     return name.endsWith(".dat");
/*   */   }
/*   */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_8_R3\util\DatFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */