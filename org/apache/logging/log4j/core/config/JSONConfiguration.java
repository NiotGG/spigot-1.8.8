/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
/*     */ import com.fasterxml.jackson.core.JsonParser.Feature;
/*     */ import com.fasterxml.jackson.databind.JsonNode;
/*     */ import com.fasterxml.jackson.databind.ObjectMapper;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginManager;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginType;
/*     */ import org.apache.logging.log4j.core.config.plugins.ResolverUtil;
/*     */ import org.apache.logging.log4j.core.helpers.FileUtils;
/*     */ import org.apache.logging.log4j.core.lookup.StrSubstitutor;
/*     */ import org.apache.logging.log4j.status.StatusConsoleListener;
/*     */ import org.apache.logging.log4j.status.StatusListener;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ public class JSONConfiguration
/*     */   extends BaseConfiguration
/*     */   implements Reconfigurable
/*     */ {
/*  54 */   private static final String[] VERBOSE_CLASSES = { ResolverUtil.class.getName() };
/*     */   
/*     */   private static final int BUF_SIZE = 16384;
/*     */   
/*  58 */   private final List<Status> status = new ArrayList();
/*     */   
/*     */   private JsonNode root;
/*     */   
/*  62 */   private final List<String> messages = new ArrayList();
/*     */   private final File configFile;
/*     */   
/*     */   public JSONConfiguration(ConfigurationFactory.ConfigurationSource paramConfigurationSource)
/*     */   {
/*  67 */     this.configFile = paramConfigurationSource.getFile();
/*     */     
/*     */     try
/*     */     {
/*  71 */       InputStream localInputStream = paramConfigurationSource.getInputStream();
/*  72 */       byte[] arrayOfByte = toByteArray(localInputStream);
/*  73 */       localInputStream.close();
/*  74 */       ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/*  75 */       ObjectMapper localObjectMapper = new ObjectMapper().configure(JsonParser.Feature.ALLOW_COMMENTS, true);
/*  76 */       this.root = localObjectMapper.readTree(localByteArrayInputStream);
/*  77 */       if (this.root.size() == 1) {
/*  78 */         localObject1 = this.root.elements();
/*  79 */         this.root = ((JsonNode)((Iterator)localObject1).next());
/*     */       }
/*  81 */       processAttributes(this.rootNode, this.root);
/*  82 */       Object localObject1 = getDefaultStatus();
/*  83 */       boolean bool = false;
/*  84 */       PrintStream localPrintStream = System.out;
/*  85 */       for (Map.Entry localEntry : this.rootNode.getAttributes().entrySet()) {
/*  86 */         if ("status".equalsIgnoreCase((String)localEntry.getKey())) {
/*  87 */           localObject1 = Level.toLevel(getStrSubstitutor().replace((String)localEntry.getValue()), null);
/*  88 */           if (localObject1 == null) {
/*  89 */             localObject1 = Level.ERROR;
/*  90 */             this.messages.add("Invalid status specified: " + (String)localEntry.getValue() + ". Defaulting to ERROR");
/*     */           } } else { Object localObject2;
/*  92 */           if ("dest".equalsIgnoreCase((String)localEntry.getKey())) {
/*  93 */             localObject2 = (String)localEntry.getValue();
/*  94 */             if (localObject2 != null) {
/*  95 */               if (((String)localObject2).equalsIgnoreCase("err")) {
/*  96 */                 localPrintStream = System.err;
/*     */               } else {
/*     */                 try {
/*  99 */                   File localFile = FileUtils.fileFromURI(new URI((String)localObject2));
/* 100 */                   String str1 = Charset.defaultCharset().name();
/* 101 */                   localPrintStream = new PrintStream(new FileOutputStream(localFile), true, str1);
/*     */                 } catch (URISyntaxException localURISyntaxException) {
/* 103 */                   System.err.println("Unable to write to " + (String)localObject2 + ". Writing to stdout");
/*     */                 }
/*     */               }
/*     */             }
/* 107 */           } else if ("shutdownHook".equalsIgnoreCase((String)localEntry.getKey())) {
/* 108 */             localObject2 = getStrSubstitutor().replace((String)localEntry.getValue());
/* 109 */             this.isShutdownHookEnabled = (!((String)localObject2).equalsIgnoreCase("disable"));
/* 110 */           } else if ("verbose".equalsIgnoreCase((String)localEntry.getKey())) {
/* 111 */             bool = Boolean.parseBoolean(getStrSubstitutor().replace((String)localEntry.getValue()));
/* 112 */           } else if ("packages".equalsIgnoreCase((String)localEntry.getKey())) {
/* 113 */             localObject2 = getStrSubstitutor().replace((String)localEntry.getValue()).split(",");
/* 114 */             for (String str3 : localObject2) {
/* 115 */               PluginManager.addPackage(str3);
/*     */             }
/* 117 */           } else if ("name".equalsIgnoreCase((String)localEntry.getKey())) {
/* 118 */             setName(getStrSubstitutor().replace((String)localEntry.getValue()));
/* 119 */           } else if ("monitorInterval".equalsIgnoreCase((String)localEntry.getKey())) {
/* 120 */             int j = Integer.parseInt(getStrSubstitutor().replace((String)localEntry.getValue()));
/* 121 */             if ((j > 0) && (this.configFile != null)) {
/* 122 */               this.monitor = new FileConfigurationMonitor(this, this.configFile, this.listeners, j);
/*     */             }
/* 124 */           } else if ("advertiser".equalsIgnoreCase((String)localEntry.getKey())) {
/* 125 */             createAdvertiser(getStrSubstitutor().replace((String)localEntry.getValue()), paramConfigurationSource, arrayOfByte, "application/json");
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 130 */       ??? = ((StatusLogger)LOGGER).getListeners();
/* 131 */       int i = 0;
/* 132 */       Object localObject3; while (???.hasNext()) {
/* 133 */         localObject3 = (StatusListener)???.next();
/* 134 */         if ((localObject3 instanceof StatusConsoleListener)) {
/* 135 */           i = 1;
/* 136 */           ((StatusConsoleListener)localObject3).setLevel((Level)localObject1);
/* 137 */           if (!bool) {
/* 138 */             ((StatusConsoleListener)localObject3).setFilters(VERBOSE_CLASSES);
/*     */           }
/*     */         }
/*     */       }
/* 142 */       if ((i == 0) && (localObject1 != Level.OFF)) {
/* 143 */         localObject3 = new StatusConsoleListener((Level)localObject1, localPrintStream);
/* 144 */         if (!bool) {
/* 145 */           ((StatusConsoleListener)localObject3).setFilters(VERBOSE_CLASSES);
/*     */         }
/* 147 */         ((StatusLogger)LOGGER).registerListener((StatusListener)localObject3);
/* 148 */         for (??? = this.messages.iterator(); ((Iterator)???).hasNext();) { String str2 = (String)((Iterator)???).next();
/* 149 */           LOGGER.error(str2);
/*     */         }
/*     */       }
/* 152 */       if (getName() == null) {
/* 153 */         setName(paramConfigurationSource.getLocation());
/*     */       }
/*     */     } catch (Exception localException) {
/* 156 */       LOGGER.error("Error parsing " + paramConfigurationSource.getLocation(), localException);
/* 157 */       localException.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void stop()
/*     */   {
/* 163 */     super.stop();
/*     */   }
/*     */   
/*     */   public void setup()
/*     */   {
/* 168 */     Iterator localIterator = this.root.fields();
/* 169 */     List localList = this.rootNode.getChildren();
/* 170 */     Object localObject1; Object localObject2; while (localIterator.hasNext()) {
/* 171 */       localObject1 = (Map.Entry)localIterator.next();
/* 172 */       localObject2 = (JsonNode)((Map.Entry)localObject1).getValue();
/* 173 */       if (((JsonNode)localObject2).isObject()) {
/* 174 */         LOGGER.debug("Processing node for object " + (String)((Map.Entry)localObject1).getKey());
/* 175 */         localList.add(constructNode((String)((Map.Entry)localObject1).getKey(), this.rootNode, (JsonNode)localObject2));
/* 176 */       } else if (((JsonNode)localObject2).isArray()) {
/* 177 */         LOGGER.error("Arrays are not supported at the root configuration.");
/*     */       }
/*     */     }
/* 180 */     LOGGER.debug("Completed parsing configuration");
/* 181 */     if (this.status.size() > 0) {
/* 182 */       for (localObject1 = this.status.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Status)((Iterator)localObject1).next();
/* 183 */         LOGGER.error("Error processing element " + ((Status)localObject2).name + ": " + ((Status)localObject2).errorType);
/*     */       }
/* 185 */       return;
/*     */     }
/*     */   }
/*     */   
/*     */   public Configuration reconfigure()
/*     */   {
/* 191 */     if (this.configFile != null) {
/*     */       try {
/* 193 */         ConfigurationFactory.ConfigurationSource localConfigurationSource = new ConfigurationFactory.ConfigurationSource(new FileInputStream(this.configFile), this.configFile);
/*     */         
/* 195 */         return new JSONConfiguration(localConfigurationSource);
/*     */       } catch (FileNotFoundException localFileNotFoundException) {
/* 197 */         LOGGER.error("Cannot locate file " + this.configFile, localFileNotFoundException);
/*     */       }
/*     */     }
/* 200 */     return null;
/*     */   }
/*     */   
/*     */   private Node constructNode(String paramString, Node paramNode, JsonNode paramJsonNode) {
/* 204 */     PluginType localPluginType1 = this.pluginManager.getPluginType(paramString);
/* 205 */     Node localNode1 = new Node(paramNode, paramString, localPluginType1);
/* 206 */     processAttributes(localNode1, paramJsonNode);
/* 207 */     Iterator localIterator1 = paramJsonNode.fields();
/* 208 */     List localList1 = localNode1.getChildren();
/* 209 */     Object localObject1; while (localIterator1.hasNext()) {
/* 210 */       localObject1 = (Map.Entry)localIterator1.next();
/* 211 */       localObject2 = (JsonNode)((Map.Entry)localObject1).getValue();
/* 212 */       if ((((JsonNode)localObject2).isArray()) || (((JsonNode)localObject2).isObject())) {
/* 213 */         if (localPluginType1 == null) {
/* 214 */           this.status.add(new Status(paramString, (JsonNode)localObject2, ErrorType.CLASS_NOT_FOUND));
/*     */         }
/* 216 */         if (((JsonNode)localObject2).isArray()) {
/* 217 */           LOGGER.debug("Processing node for array " + (String)((Map.Entry)localObject1).getKey());
/* 218 */           for (int i = 0; i < ((JsonNode)localObject2).size(); i++) {
/* 219 */             String str = getType(((JsonNode)localObject2).get(i), (String)((Map.Entry)localObject1).getKey());
/* 220 */             PluginType localPluginType2 = this.pluginManager.getPluginType(str);
/* 221 */             Node localNode2 = new Node(localNode1, (String)((Map.Entry)localObject1).getKey(), localPluginType2);
/* 222 */             processAttributes(localNode2, ((JsonNode)localObject2).get(i));
/* 223 */             if (str.equals(((Map.Entry)localObject1).getKey())) {
/* 224 */               LOGGER.debug("Processing " + (String)((Map.Entry)localObject1).getKey() + "[" + i + "]");
/*     */             } else {
/* 226 */               LOGGER.debug("Processing " + str + " " + (String)((Map.Entry)localObject1).getKey() + "[" + i + "]");
/*     */             }
/* 228 */             Iterator localIterator2 = ((JsonNode)localObject2).get(i).fields();
/* 229 */             List localList2 = localNode2.getChildren();
/* 230 */             while (localIterator2.hasNext()) {
/* 231 */               Map.Entry localEntry = (Map.Entry)localIterator2.next();
/* 232 */               if (((JsonNode)localEntry.getValue()).isObject()) {
/* 233 */                 LOGGER.debug("Processing node for object " + (String)localEntry.getKey());
/* 234 */                 localList2.add(constructNode((String)localEntry.getKey(), localNode2, (JsonNode)localEntry.getValue()));
/*     */               }
/*     */             }
/* 237 */             localList1.add(localNode2);
/*     */           }
/*     */         } else {
/* 240 */           LOGGER.debug("Processing node for object " + (String)((Map.Entry)localObject1).getKey());
/* 241 */           localList1.add(constructNode((String)((Map.Entry)localObject1).getKey(), localNode1, (JsonNode)localObject2));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 247 */     if (localPluginType1 == null) {
/* 248 */       localObject1 = "null";
/*     */     } else {
/* 250 */       localObject1 = localPluginType1.getElementName() + ":" + localPluginType1.getPluginClass();
/*     */     }
/*     */     
/* 253 */     Object localObject2 = localNode1.getParent().getName() == null ? "root" : localNode1.getParent() == null ? "null" : localNode1.getParent().getName();
/*     */     
/* 255 */     LOGGER.debug("Returning " + localNode1.getName() + " with parent " + (String)localObject2 + " of type " + (String)localObject1);
/* 256 */     return localNode1;
/*     */   }
/*     */   
/*     */   private String getType(JsonNode paramJsonNode, String paramString) {
/* 260 */     Iterator localIterator = paramJsonNode.fields();
/* 261 */     while (localIterator.hasNext()) {
/* 262 */       Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 263 */       if (((String)localEntry.getKey()).equalsIgnoreCase("type")) {
/* 264 */         JsonNode localJsonNode = (JsonNode)localEntry.getValue();
/* 265 */         if (localJsonNode.isValueNode()) {
/* 266 */           return localJsonNode.asText();
/*     */         }
/*     */       }
/*     */     }
/* 270 */     return paramString;
/*     */   }
/*     */   
/*     */   private void processAttributes(Node paramNode, JsonNode paramJsonNode) {
/* 274 */     Map localMap = paramNode.getAttributes();
/* 275 */     Iterator localIterator = paramJsonNode.fields();
/* 276 */     while (localIterator.hasNext()) {
/* 277 */       Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 278 */       if (!((String)localEntry.getKey()).equalsIgnoreCase("type")) {
/* 279 */         JsonNode localJsonNode = (JsonNode)localEntry.getValue();
/* 280 */         if (localJsonNode.isValueNode()) {
/* 281 */           localMap.put(localEntry.getKey(), localJsonNode.asText());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected byte[] toByteArray(InputStream paramInputStream) throws IOException {
/* 288 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/*     */     
/*     */ 
/* 291 */     byte[] arrayOfByte = new byte['䀀'];
/*     */     int i;
/* 293 */     while ((i = paramInputStream.read(arrayOfByte, 0, arrayOfByte.length)) != -1) {
/* 294 */       localByteArrayOutputStream.write(arrayOfByte, 0, i);
/*     */     }
/*     */     
/* 297 */     return localByteArrayOutputStream.toByteArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static enum ErrorType
/*     */   {
/* 304 */     CLASS_NOT_FOUND;
/*     */     
/*     */     private ErrorType() {}
/*     */   }
/*     */   
/*     */   private class Status
/*     */   {
/*     */     private final JsonNode node;
/*     */     private final String name;
/*     */     private final JSONConfiguration.ErrorType errorType;
/*     */     
/*     */     public Status(String paramString, JsonNode paramJsonNode, JSONConfiguration.ErrorType paramErrorType) {
/* 316 */       this.name = paramString;
/* 317 */       this.node = paramJsonNode;
/* 318 */       this.errorType = paramErrorType;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\JSONConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */