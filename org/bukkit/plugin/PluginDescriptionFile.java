/*      */ package org.bukkit.plugin;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.ImmutableList.Builder;
/*      */ import com.google.common.collect.ImmutableMap;
/*      */ import com.google.common.collect.ImmutableMap.Builder;
/*      */ import com.google.common.collect.ImmutableSet;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.Writer;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import org.bukkit.permissions.Permission;
/*      */ import org.bukkit.permissions.PermissionDefault;
/*      */ import org.yaml.snakeyaml.Yaml;
/*      */ import org.yaml.snakeyaml.constructor.AbstractConstruct;
/*      */ import org.yaml.snakeyaml.constructor.SafeConstructor;
/*      */ import org.yaml.snakeyaml.constructor.SafeConstructor.ConstructUndefined;
/*      */ import org.yaml.snakeyaml.nodes.Node;
/*      */ import org.yaml.snakeyaml.nodes.Tag;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class PluginDescriptionFile
/*      */ {
/*  178 */   private static final ThreadLocal<Yaml> YAML = new ThreadLocal()
/*      */   {
/*      */     protected Yaml initialValue() {
/*  181 */       new Yaml(new SafeConstructor() {});
/*      */     }
/*      */   };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  211 */   String rawName = null;
/*  212 */   private String name = null;
/*  213 */   private String main = null;
/*  214 */   private String classLoaderOf = null;
/*  215 */   private List<String> depend = ImmutableList.of();
/*  216 */   private List<String> softDepend = ImmutableList.of();
/*  217 */   private List<String> loadBefore = ImmutableList.of();
/*  218 */   private String version = null;
/*  219 */   private Map<String, Map<String, Object>> commands = null;
/*  220 */   private String description = null;
/*  221 */   private List<String> authors = null;
/*  222 */   private String website = null;
/*  223 */   private String prefix = null;
/*  224 */   private boolean database = false;
/*  225 */   private PluginLoadOrder order = PluginLoadOrder.POSTWORLD;
/*  226 */   private List<Permission> permissions = null;
/*  227 */   private Map<?, ?> lazyPermissions = null;
/*  228 */   private PermissionDefault defaultPerm = PermissionDefault.OP;
/*  229 */   private Set<PluginAwareness> awareness = ImmutableSet.of();
/*      */   
/*      */   public PluginDescriptionFile(InputStream stream) throws InvalidDescriptionException {
/*  232 */     loadMap(asMap(((Yaml)YAML.get()).load(stream)));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PluginDescriptionFile(Reader reader)
/*      */     throws InvalidDescriptionException
/*      */   {
/*  243 */     loadMap(asMap(((Yaml)YAML.get()).load(reader)));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PluginDescriptionFile(String pluginName, String pluginVersion, String mainClass)
/*      */   {
/*  254 */     this.name = pluginName.replace(' ', '_');
/*  255 */     this.version = pluginVersion;
/*  256 */     this.main = mainClass;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getName()
/*      */   {
/*  285 */     return this.name;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getVersion()
/*      */   {
/*  305 */     return this.version;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMain()
/*      */   {
/*  331 */     return this.main;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getDescription()
/*      */   {
/*  350 */     return this.description;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PluginLoadOrder getLoad()
/*      */   {
/*  373 */     return this.order;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<String> getAuthors()
/*      */   {
/*  408 */     return this.authors;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getWebsite()
/*      */   {
/*  427 */     return this.website;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isDatabaseEnabled()
/*      */   {
/*  446 */     return this.database;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<String> getDepend()
/*      */   {
/*  476 */     return this.depend;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<String> getSoftDepend()
/*      */   {
/*  505 */     return this.softDepend;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<String> getLoadBefore()
/*      */   {
/*  534 */     return this.loadBefore;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getPrefix()
/*      */   {
/*  553 */     return this.prefix;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Map<String, Map<String, Object>> getCommands()
/*      */   {
/*  673 */     return this.commands;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<Permission> getPermissions()
/*      */   {
/*  785 */     if (this.permissions == null) {
/*  786 */       if (this.lazyPermissions == null) {
/*  787 */         this.permissions = ImmutableList.of();
/*      */       } else {
/*  789 */         this.permissions = ImmutableList.copyOf(Permission.loadPermissions(this.lazyPermissions, "Permission node '%s' in plugin description file for " + getFullName() + " is invalid", this.defaultPerm));
/*  790 */         this.lazyPermissions = null;
/*      */       }
/*      */     }
/*  793 */     return this.permissions;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PermissionDefault getPermissionDefault()
/*      */   {
/*  814 */     return this.defaultPerm;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Set<PluginAwareness> getAwareness()
/*      */   {
/*  853 */     return this.awareness;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getFullName()
/*      */   {
/*  864 */     return this.name + " v" + this.version;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public String getClassLoaderOf()
/*      */   {
/*  873 */     return this.classLoaderOf;
/*      */   }
/*      */   
/*      */   public void setDatabaseEnabled(boolean database) {
/*  877 */     this.database = database;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void save(Writer writer)
/*      */   {
/*  886 */     ((Yaml)YAML.get()).dump(saveMap(), writer);
/*      */   }
/*      */   
/*      */   private void loadMap(Map<?, ?> map) throws InvalidDescriptionException {
/*      */     try {
/*  891 */       this.name = (this.rawName = map.get("name").toString());
/*      */       
/*  893 */       if (!this.name.matches("^[A-Za-z0-9 _.-]+$")) {
/*  894 */         throw new InvalidDescriptionException("name '" + this.name + "' contains invalid characters.");
/*      */       }
/*  896 */       this.name = this.name.replace(' ', '_');
/*      */     } catch (NullPointerException ex) {
/*  898 */       throw new InvalidDescriptionException(ex, "name is not defined");
/*      */     } catch (ClassCastException ex) {
/*  900 */       throw new InvalidDescriptionException(ex, "name is of wrong type");
/*      */     }
/*      */     try
/*      */     {
/*  904 */       this.version = map.get("version").toString();
/*      */     } catch (NullPointerException ex) {
/*  906 */       throw new InvalidDescriptionException(ex, "version is not defined");
/*      */     } catch (ClassCastException ex) {
/*  908 */       throw new InvalidDescriptionException(ex, "version is of wrong type");
/*      */     }
/*      */     try
/*      */     {
/*  912 */       this.main = map.get("main").toString();
/*  913 */       if (this.main.startsWith("org.bukkit.")) {
/*  914 */         throw new InvalidDescriptionException("main may not be within the org.bukkit namespace");
/*      */       }
/*      */     } catch (NullPointerException ex) {
/*  917 */       throw new InvalidDescriptionException(ex, "main is not defined");
/*      */     } catch (ClassCastException ex) {
/*  919 */       throw new InvalidDescriptionException(ex, "main is of wrong type");
/*      */     }
/*      */     
/*  922 */     if (map.get("commands") != null) {
/*  923 */       ImmutableMap.Builder<String, Map<String, Object>> commandsBuilder = ImmutableMap.builder();
/*      */       try {
/*  925 */         for (Map.Entry<?, ?> command : ((Map)map.get("commands")).entrySet()) {
/*  926 */           ImmutableMap.Builder<String, Object> commandBuilder = ImmutableMap.builder();
/*  927 */           if (command.getValue() != null) {
/*  928 */             for (Map.Entry<?, ?> commandEntry : ((Map)command.getValue()).entrySet()) {
/*  929 */               if ((commandEntry.getValue() instanceof Iterable))
/*      */               {
/*  931 */                 ImmutableList.Builder<Object> commandSubList = ImmutableList.builder();
/*  932 */                 for (Object commandSubListItem : (Iterable)commandEntry.getValue()) {
/*  933 */                   if (commandSubListItem != null) {
/*  934 */                     commandSubList.add(commandSubListItem);
/*      */                   }
/*      */                 }
/*  937 */                 commandBuilder.put(commandEntry.getKey().toString(), commandSubList.build());
/*  938 */               } else if (commandEntry.getValue() != null) {
/*  939 */                 commandBuilder.put(commandEntry.getKey().toString(), commandEntry.getValue());
/*      */               }
/*      */             }
/*      */           }
/*  943 */           commandsBuilder.put(command.getKey().toString(), commandBuilder.build());
/*      */         }
/*      */       } catch (ClassCastException ex) {
/*  946 */         throw new InvalidDescriptionException(ex, "commands are of wrong type");
/*      */       }
/*  948 */       this.commands = commandsBuilder.build();
/*      */     }
/*      */     
/*  951 */     if (map.get("class-loader-of") != null) {
/*  952 */       this.classLoaderOf = map.get("class-loader-of").toString();
/*      */     }
/*      */     
/*  955 */     this.depend = makePluginNameList(map, "depend");
/*  956 */     this.softDepend = makePluginNameList(map, "softdepend");
/*  957 */     this.loadBefore = makePluginNameList(map, "loadbefore");
/*      */     
/*  959 */     if (map.get("database") != null) {
/*      */       try {
/*  961 */         this.database = ((Boolean)map.get("database")).booleanValue();
/*      */       } catch (ClassCastException ex) {
/*  963 */         throw new InvalidDescriptionException(ex, "database is of wrong type");
/*      */       }
/*      */     }
/*      */     
/*  967 */     if (map.get("website") != null) {
/*  968 */       this.website = map.get("website").toString();
/*      */     }
/*      */     
/*  971 */     if (map.get("description") != null) {
/*  972 */       this.description = map.get("description").toString();
/*      */     }
/*      */     
/*  975 */     if (map.get("load") != null) {
/*      */       try {
/*  977 */         this.order = PluginLoadOrder.valueOf(((String)map.get("load")).toUpperCase().replaceAll("\\W", ""));
/*      */       } catch (ClassCastException ex) {
/*  979 */         throw new InvalidDescriptionException(ex, "load is of wrong type");
/*      */       } catch (IllegalArgumentException ex) {
/*  981 */         throw new InvalidDescriptionException(ex, "load is not a valid choice");
/*      */       }
/*      */     }
/*      */     
/*  985 */     if (map.get("authors") != null) {
/*  986 */       ImmutableList.Builder<String> authorsBuilder = ImmutableList.builder();
/*  987 */       if (map.get("author") != null) {
/*  988 */         authorsBuilder.add(map.get("author").toString());
/*      */       }
/*      */       try {
/*  991 */         for (Object o : (Iterable)map.get("authors")) {
/*  992 */           authorsBuilder.add(o.toString());
/*      */         }
/*      */       } catch (ClassCastException ex) {
/*  995 */         throw new InvalidDescriptionException(ex, "authors are of wrong type");
/*      */       } catch (NullPointerException ex) {
/*  997 */         throw new InvalidDescriptionException(ex, "authors are improperly defined");
/*      */       }
/*  999 */       this.authors = authorsBuilder.build();
/* 1000 */     } else if (map.get("author") != null) {
/* 1001 */       this.authors = ImmutableList.of(map.get("author").toString());
/*      */     } else {
/* 1003 */       this.authors = ImmutableList.of();
/*      */     }
/*      */     
/* 1006 */     if (map.get("default-permission") != null) {
/*      */       try {
/* 1008 */         this.defaultPerm = PermissionDefault.getByName(map.get("default-permission").toString());
/*      */       } catch (ClassCastException ex) {
/* 1010 */         throw new InvalidDescriptionException(ex, "default-permission is of wrong type");
/*      */       } catch (IllegalArgumentException ex) {
/* 1012 */         throw new InvalidDescriptionException(ex, "default-permission is not a valid choice");
/*      */       }
/*      */     }
/*      */     
/* 1016 */     if ((map.get("awareness") instanceof Iterable)) {
/* 1017 */       Set<PluginAwareness> awareness = new HashSet();
/*      */       try {
/* 1019 */         for (Object o : (Iterable)map.get("awareness")) {
/* 1020 */           awareness.add((PluginAwareness)o);
/*      */         }
/*      */       } catch (ClassCastException ex) {
/* 1023 */         throw new InvalidDescriptionException(ex, "awareness has wrong type");
/*      */       }
/* 1025 */       this.awareness = ImmutableSet.copyOf(awareness);
/*      */     }
/*      */     try
/*      */     {
/* 1029 */       this.lazyPermissions = ((Map)map.get("permissions"));
/*      */     } catch (ClassCastException ex) {
/* 1031 */       throw new InvalidDescriptionException(ex, "permissions are of the wrong type");
/*      */     }
/*      */     
/* 1034 */     if (map.get("prefix") != null) {
/* 1035 */       this.prefix = map.get("prefix").toString();
/*      */     }
/*      */   }
/*      */   
/*      */   private static List<String> makePluginNameList(Map<?, ?> map, String key) throws InvalidDescriptionException {
/* 1040 */     Object value = map.get(key);
/* 1041 */     if (value == null) {
/* 1042 */       return ImmutableList.of();
/*      */     }
/*      */     
/* 1045 */     ImmutableList.Builder<String> builder = ImmutableList.builder();
/*      */     try {
/* 1047 */       for (Object entry : (Iterable)value) {
/* 1048 */         builder.add(entry.toString().replace(' ', '_'));
/*      */       }
/*      */     } catch (ClassCastException ex) {
/* 1051 */       throw new InvalidDescriptionException(ex, key + " is of wrong type");
/*      */     } catch (NullPointerException ex) {
/* 1053 */       throw new InvalidDescriptionException(ex, "invalid " + key + " format");
/*      */     }
/* 1055 */     return builder.build();
/*      */   }
/*      */   
/*      */   private Map<String, Object> saveMap() {
/* 1059 */     Map<String, Object> map = new HashMap();
/*      */     
/* 1061 */     map.put("name", this.name);
/* 1062 */     map.put("main", this.main);
/* 1063 */     map.put("version", this.version);
/* 1064 */     map.put("database", Boolean.valueOf(this.database));
/* 1065 */     map.put("order", this.order.toString());
/* 1066 */     map.put("default-permission", this.defaultPerm.toString());
/*      */     
/* 1068 */     if (this.commands != null) {
/* 1069 */       map.put("command", this.commands);
/*      */     }
/* 1071 */     if (this.depend != null) {
/* 1072 */       map.put("depend", this.depend);
/*      */     }
/* 1074 */     if (this.softDepend != null) {
/* 1075 */       map.put("softdepend", this.softDepend);
/*      */     }
/* 1077 */     if (this.website != null) {
/* 1078 */       map.put("website", this.website);
/*      */     }
/* 1080 */     if (this.description != null) {
/* 1081 */       map.put("description", this.description);
/*      */     }
/*      */     
/* 1084 */     if (this.authors.size() == 1) {
/* 1085 */       map.put("author", this.authors.get(0));
/* 1086 */     } else if (this.authors.size() > 1) {
/* 1087 */       map.put("authors", this.authors);
/*      */     }
/*      */     
/* 1090 */     if (this.classLoaderOf != null) {
/* 1091 */       map.put("class-loader-of", this.classLoaderOf);
/*      */     }
/*      */     
/* 1094 */     if (this.prefix != null) {
/* 1095 */       map.put("prefix", this.prefix);
/*      */     }
/*      */     
/* 1098 */     return map;
/*      */   }
/*      */   
/*      */   private Map<?, ?> asMap(Object object) throws InvalidDescriptionException {
/* 1102 */     if ((object instanceof Map)) {
/* 1103 */       return (Map)object;
/*      */     }
/* 1105 */     throw new InvalidDescriptionException(object + " is not properly structured.");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public String getRawName()
/*      */   {
/* 1114 */     return this.rawName;
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\PluginDescriptionFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */