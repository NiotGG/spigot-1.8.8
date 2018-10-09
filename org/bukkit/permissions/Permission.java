/*     */ package org.bukkit.permissions;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ 
/*     */ public class Permission
/*     */ {
/*  19 */   public static final PermissionDefault DEFAULT_PERMISSION = PermissionDefault.OP;
/*     */   
/*     */   private final String name;
/*  22 */   private final Map<String, Boolean> children = new LinkedHashMap();
/*  23 */   private PermissionDefault defaultValue = DEFAULT_PERMISSION;
/*     */   private String description;
/*     */   
/*     */   public Permission(String name) {
/*  27 */     this(name, null, null, null);
/*     */   }
/*     */   
/*     */   public Permission(String name, String description) {
/*  31 */     this(name, description, null, null);
/*     */   }
/*     */   
/*     */   public Permission(String name, PermissionDefault defaultValue) {
/*  35 */     this(name, null, defaultValue, null);
/*     */   }
/*     */   
/*     */   public Permission(String name, String description, PermissionDefault defaultValue) {
/*  39 */     this(name, description, defaultValue, null);
/*     */   }
/*     */   
/*     */   public Permission(String name, Map<String, Boolean> children) {
/*  43 */     this(name, null, null, children);
/*     */   }
/*     */   
/*     */   public Permission(String name, String description, Map<String, Boolean> children) {
/*  47 */     this(name, description, null, children);
/*     */   }
/*     */   
/*     */   public Permission(String name, PermissionDefault defaultValue, Map<String, Boolean> children) {
/*  51 */     this(name, null, defaultValue, children);
/*     */   }
/*     */   
/*     */   public Permission(String name, String description, PermissionDefault defaultValue, Map<String, Boolean> children) {
/*  55 */     Validate.notNull(name, "Name cannot be null");
/*  56 */     this.name = name;
/*  57 */     this.description = (description == null ? "" : description);
/*     */     
/*  59 */     if (defaultValue != null) {
/*  60 */       this.defaultValue = defaultValue;
/*     */     }
/*     */     
/*  63 */     if (children != null) {
/*  64 */       this.children.putAll(children);
/*     */     }
/*     */     
/*  67 */     recalculatePermissibles();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  76 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Map<String, Boolean> getChildren()
/*     */   {
/*  88 */     return this.children;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PermissionDefault getDefault()
/*     */   {
/*  97 */     return this.defaultValue;
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
/*     */   public void setDefault(PermissionDefault value)
/*     */   {
/* 111 */     if (this.defaultValue == null) {
/* 112 */       throw new IllegalArgumentException("Default value cannot be null");
/*     */     }
/*     */     
/* 115 */     this.defaultValue = value;
/* 116 */     recalculatePermissibles();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 125 */     return this.description;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDescription(String value)
/*     */   {
/* 137 */     if (value == null) {
/* 138 */       this.description = "";
/*     */     } else {
/* 140 */       this.description = value;
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
/*     */   public Set<Permissible> getPermissibles()
/*     */   {
/* 153 */     return Bukkit.getServer().getPluginManager().getPermissionSubscriptions(this.name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void recalculatePermissibles()
/*     */   {
/* 163 */     Set<Permissible> perms = getPermissibles();
/*     */     
/* 165 */     Bukkit.getServer().getPluginManager().recalculatePermissionDefaults(this);
/*     */     
/* 167 */     for (Permissible p : perms) {
/* 168 */       p.recalculatePermissions();
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
/*     */ 
/*     */ 
/*     */   public Permission addParent(String name, boolean value)
/*     */   {
/* 183 */     PluginManager pm = Bukkit.getServer().getPluginManager();
/* 184 */     String lname = name.toLowerCase();
/*     */     
/* 186 */     Permission perm = pm.getPermission(lname);
/*     */     
/* 188 */     if (perm == null) {
/* 189 */       perm = new Permission(lname);
/* 190 */       pm.addPermission(perm);
/*     */     }
/*     */     
/* 193 */     addParent(perm, value);
/*     */     
/* 195 */     return perm;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addParent(Permission perm, boolean value)
/*     */   {
/* 205 */     perm.getChildren().put(getName(), Boolean.valueOf(value));
/* 206 */     perm.recalculatePermissibles();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static List<Permission> loadPermissions(Map<?, ?> data, String error, PermissionDefault def)
/*     */   {
/* 229 */     List<Permission> result = new ArrayList();
/*     */     
/* 231 */     for (Map.Entry<?, ?> entry : data.entrySet()) {
/*     */       try {
/* 233 */         result.add(loadPermission(entry.getKey().toString(), (Map)entry.getValue(), def, result));
/*     */       } catch (Throwable ex) {
/* 235 */         Bukkit.getServer().getLogger().log(Level.SEVERE, String.format(error, new Object[] { entry.getKey() }), ex);
/*     */       }
/*     */     }
/*     */     
/* 239 */     return result;
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
/*     */ 
/*     */ 
/*     */   public static Permission loadPermission(String name, Map<String, Object> data)
/*     */   {
/* 260 */     return loadPermission(name, data, DEFAULT_PERMISSION, null);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Permission loadPermission(String name, Map<?, ?> data, PermissionDefault def, List<Permission> output)
/*     */   {
/* 283 */     Validate.notNull(name, "Name cannot be null");
/* 284 */     Validate.notNull(data, "Data cannot be null");
/*     */     
/* 286 */     String desc = null;
/* 287 */     Map<String, Boolean> children = null;
/*     */     
/* 289 */     if (data.get("default") != null) {
/* 290 */       PermissionDefault value = PermissionDefault.getByName(data.get("default").toString());
/* 291 */       if (value != null) {
/* 292 */         def = value;
/*     */       } else {
/* 294 */         throw new IllegalArgumentException("'default' key contained unknown value");
/*     */       }
/*     */     }
/*     */     
/* 298 */     if (data.get("children") != null) {
/* 299 */       Object childrenNode = data.get("children");
/* 300 */       if ((childrenNode instanceof Iterable)) {
/* 301 */         children = new LinkedHashMap();
/* 302 */         for (Object child : (Iterable)childrenNode) {
/* 303 */           if (child != null) {
/* 304 */             children.put(child.toString(), Boolean.TRUE);
/*     */           }
/*     */         }
/* 307 */       } else if ((childrenNode instanceof Map)) {
/* 308 */         children = extractChildren((Map)childrenNode, name, def, output);
/*     */       } else {
/* 310 */         throw new IllegalArgumentException("'children' key is of wrong type");
/*     */       }
/*     */     }
/*     */     
/* 314 */     if (data.get("description") != null) {
/* 315 */       desc = data.get("description").toString();
/*     */     }
/*     */     
/* 318 */     return new Permission(name, desc, def, children);
/*     */   }
/*     */   
/*     */   private static Map<String, Boolean> extractChildren(Map<?, ?> input, String name, PermissionDefault def, List<Permission> output) {
/* 322 */     Map<String, Boolean> children = new LinkedHashMap();
/*     */     
/* 324 */     for (Map.Entry<?, ?> entry : input.entrySet()) {
/* 325 */       if ((entry.getValue() instanceof Boolean)) {
/* 326 */         children.put(entry.getKey().toString(), (Boolean)entry.getValue());
/* 327 */       } else if ((entry.getValue() instanceof Map)) {
/*     */         try {
/* 329 */           Permission perm = loadPermission(entry.getKey().toString(), (Map)entry.getValue(), def, output);
/* 330 */           children.put(perm.getName(), Boolean.TRUE);
/*     */           
/* 332 */           if (output == null) continue;
/* 333 */           output.add(perm);
/*     */         }
/*     */         catch (Throwable ex) {
/* 336 */           throw new IllegalArgumentException("Permission node '" + entry.getKey().toString() + "' in child of " + name + " is invalid", ex);
/*     */         }
/*     */       } else {
/* 339 */         throw new IllegalArgumentException("Child '" + entry.getKey().toString() + "' contains invalid value");
/*     */       }
/*     */     }
/*     */     
/* 343 */     return children;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\permissions\Permission.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */