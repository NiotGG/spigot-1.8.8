/*     */ package org.apache.logging.log4j.core.config;
/*     */ 
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
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.validation.Schema;
/*     */ import javax.xml.validation.SchemaFactory;
/*     */ import javax.xml.validation.Validator;
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
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
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
/*     */ 
/*     */ public class XMLConfiguration
/*     */   extends BaseConfiguration
/*     */   implements Reconfigurable
/*     */ {
/*     */   private static final String XINCLUDE_FIXUP_LANGUAGE = "http://apache.org/xml/features/xinclude/fixup-language";
/*     */   private static final String XINCLUDE_FIXUP_BASE_URIS = "http://apache.org/xml/features/xinclude/fixup-base-uris";
/*  72 */   private static final String[] VERBOSE_CLASSES = { ResolverUtil.class.getName() };
/*     */   
/*     */   private static final String LOG4J_XSD = "Log4j-config.xsd";
/*     */   
/*     */   private static final int BUF_SIZE = 16384;
/*     */   
/*  78 */   private final List<Status> status = new ArrayList();
/*     */   
/*     */ 
/*     */   private Element rootElement;
/*     */   
/*     */ 
/*     */   private boolean strict;
/*     */   
/*     */ 
/*     */   private String schema;
/*     */   
/*     */   private final File configFile;
/*     */   
/*     */ 
/*     */   static DocumentBuilder newDocumentBuilder()
/*     */     throws ParserConfigurationException
/*     */   {
/*  95 */     DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
/*  96 */     localDocumentBuilderFactory.setNamespaceAware(true);
/*  97 */     enableXInclude(localDocumentBuilderFactory);
/*  98 */     return localDocumentBuilderFactory.newDocumentBuilder();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void enableXInclude(DocumentBuilderFactory paramDocumentBuilderFactory)
/*     */   {
/*     */     try
/*     */     {
/* 110 */       paramDocumentBuilderFactory.setXIncludeAware(true);
/*     */     } catch (UnsupportedOperationException localUnsupportedOperationException) {
/* 112 */       LOGGER.warn("The DocumentBuilderFactory does not support XInclude: " + paramDocumentBuilderFactory, localUnsupportedOperationException);
/*     */     } catch (AbstractMethodError localAbstractMethodError1) {
/* 114 */       LOGGER.warn("The DocumentBuilderFactory is out of date and does not support XInclude: " + paramDocumentBuilderFactory);
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 119 */       paramDocumentBuilderFactory.setFeature("http://apache.org/xml/features/xinclude/fixup-base-uris", true);
/*     */     } catch (ParserConfigurationException localParserConfigurationException1) {
/* 121 */       LOGGER.warn("The DocumentBuilderFactory [" + paramDocumentBuilderFactory + "] does not support the feature [" + "http://apache.org/xml/features/xinclude/fixup-base-uris" + "]", localParserConfigurationException1);
/*     */     }
/*     */     catch (AbstractMethodError localAbstractMethodError2) {
/* 124 */       LOGGER.warn("The DocumentBuilderFactory is out of date and does not support setFeature: " + paramDocumentBuilderFactory);
/*     */     }
/*     */     try {
/* 127 */       paramDocumentBuilderFactory.setFeature("http://apache.org/xml/features/xinclude/fixup-language", true);
/*     */     } catch (ParserConfigurationException localParserConfigurationException2) {
/* 129 */       LOGGER.warn("The DocumentBuilderFactory [" + paramDocumentBuilderFactory + "] does not support the feature [" + "http://apache.org/xml/features/xinclude/fixup-language" + "]", localParserConfigurationException2);
/*     */     }
/*     */     catch (AbstractMethodError localAbstractMethodError3) {
/* 132 */       LOGGER.warn("The DocumentBuilderFactory is out of date and does not support setFeature: " + paramDocumentBuilderFactory);
/*     */     }
/*     */   }
/*     */   
/*     */   public XMLConfiguration(ConfigurationFactory.ConfigurationSource paramConfigurationSource) {
/* 137 */     this.configFile = paramConfigurationSource.getFile();
/* 138 */     byte[] arrayOfByte = null;
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 141 */     try { ArrayList localArrayList = new ArrayList();
/* 142 */       InputStream localInputStream2 = paramConfigurationSource.getInputStream();
/* 143 */       arrayOfByte = toByteArray(localInputStream2);
/* 144 */       localInputStream2.close();
/* 145 */       localObject1 = new InputSource(new ByteArrayInputStream(arrayOfByte));
/* 146 */       localObject2 = newDocumentBuilder().parse((InputSource)localObject1);
/* 147 */       this.rootElement = ((Document)localObject2).getDocumentElement();
/* 148 */       Map localMap = processAttributes(this.rootNode, this.rootElement);
/* 149 */       Object localObject3 = getDefaultStatus();
/* 150 */       boolean bool = false;
/* 151 */       PrintStream localPrintStream = System.out;
/*     */       
/* 153 */       for (Map.Entry localEntry : localMap.entrySet()) { Object localObject4;
/* 154 */         if ("status".equalsIgnoreCase((String)localEntry.getKey())) {
/* 155 */           localObject4 = Level.toLevel(getStrSubstitutor().replace((String)localEntry.getValue()), null);
/* 156 */           if (localObject4 != null) {
/* 157 */             localObject3 = localObject4;
/*     */           } else {
/* 159 */             localArrayList.add("Invalid status specified: " + (String)localEntry.getValue() + ". Defaulting to " + localObject3);
/*     */           }
/* 161 */         } else if ("dest".equalsIgnoreCase((String)localEntry.getKey())) {
/* 162 */           localObject4 = getStrSubstitutor().replace((String)localEntry.getValue());
/* 163 */           if (localObject4 != null) {
/* 164 */             if (((String)localObject4).equalsIgnoreCase("err")) {
/* 165 */               localPrintStream = System.err;
/*     */             } else {
/*     */               try {
/* 168 */                 File localFile = FileUtils.fileFromURI(new URI((String)localObject4));
/* 169 */                 String str1 = Charset.defaultCharset().name();
/* 170 */                 localPrintStream = new PrintStream(new FileOutputStream(localFile), true, str1);
/*     */               } catch (URISyntaxException localURISyntaxException) {
/* 172 */                 System.err.println("Unable to write to " + (String)localObject4 + ". Writing to stdout");
/*     */               }
/*     */             }
/*     */           }
/* 176 */         } else if ("shutdownHook".equalsIgnoreCase((String)localEntry.getKey())) {
/* 177 */           localObject4 = getStrSubstitutor().replace((String)localEntry.getValue());
/* 178 */           this.isShutdownHookEnabled = (!((String)localObject4).equalsIgnoreCase("disable"));
/* 179 */         } else if ("verbose".equalsIgnoreCase((String)localEntry.getKey())) {
/* 180 */           bool = Boolean.parseBoolean(getStrSubstitutor().replace((String)localEntry.getValue()));
/* 181 */         } else if ("packages".equalsIgnoreCase(getStrSubstitutor().replace((String)localEntry.getKey()))) {
/* 182 */           localObject4 = ((String)localEntry.getValue()).split(",");
/* 183 */           for (String str3 : localObject4) {
/* 184 */             PluginManager.addPackage(str3);
/*     */           }
/* 186 */         } else if ("name".equalsIgnoreCase((String)localEntry.getKey())) {
/* 187 */           setName(getStrSubstitutor().replace((String)localEntry.getValue()));
/* 188 */         } else if ("strict".equalsIgnoreCase((String)localEntry.getKey())) {
/* 189 */           this.strict = Boolean.parseBoolean(getStrSubstitutor().replace((String)localEntry.getValue()));
/* 190 */         } else if ("schema".equalsIgnoreCase((String)localEntry.getKey())) {
/* 191 */           this.schema = getStrSubstitutor().replace((String)localEntry.getValue());
/* 192 */         } else if ("monitorInterval".equalsIgnoreCase((String)localEntry.getKey())) {
/* 193 */           int j = Integer.parseInt(getStrSubstitutor().replace((String)localEntry.getValue()));
/* 194 */           if ((j > 0) && (this.configFile != null)) {
/* 195 */             this.monitor = new FileConfigurationMonitor(this, this.configFile, this.listeners, j);
/*     */           }
/* 197 */         } else if ("advertiser".equalsIgnoreCase((String)localEntry.getKey())) {
/* 198 */           createAdvertiser(getStrSubstitutor().replace((String)localEntry.getValue()), paramConfigurationSource, arrayOfByte, "text/xml");
/*     */         }
/*     */       }
/* 201 */       ??? = ((StatusLogger)LOGGER).getListeners();
/* 202 */       int i = 0;
/* 203 */       Object localObject5; while (???.hasNext()) {
/* 204 */         localObject5 = (StatusListener)???.next();
/* 205 */         if ((localObject5 instanceof StatusConsoleListener)) {
/* 206 */           i = 1;
/* 207 */           ((StatusConsoleListener)localObject5).setLevel((Level)localObject3);
/* 208 */           if (!bool) {
/* 209 */             ((StatusConsoleListener)localObject5).setFilters(VERBOSE_CLASSES);
/*     */           }
/*     */         }
/*     */       }
/* 213 */       if ((i == 0) && (localObject3 != Level.OFF)) {
/* 214 */         localObject5 = new StatusConsoleListener((Level)localObject3, localPrintStream);
/* 215 */         if (!bool) {
/* 216 */           ((StatusConsoleListener)localObject5).setFilters(VERBOSE_CLASSES);
/*     */         }
/* 218 */         ((StatusLogger)LOGGER).registerListener((StatusListener)localObject5);
/* 219 */         for (??? = localArrayList.iterator(); ((Iterator)???).hasNext();) { String str2 = (String)((Iterator)???).next();
/* 220 */           LOGGER.error(str2);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (SAXException localSAXException1) {
/* 225 */       LOGGER.error("Error parsing " + paramConfigurationSource.getLocation(), localSAXException1);
/*     */     } catch (IOException localIOException1) {
/* 227 */       LOGGER.error("Error parsing " + paramConfigurationSource.getLocation(), localIOException1);
/*     */     } catch (ParserConfigurationException localParserConfigurationException) {
/* 229 */       LOGGER.error("Error parsing " + paramConfigurationSource.getLocation(), localParserConfigurationException);
/*     */     }
/* 231 */     if ((this.strict) && (this.schema != null) && (arrayOfByte != null)) {
/* 232 */       InputStream localInputStream1 = null;
/*     */       try {
/* 234 */         localInputStream1 = getClass().getClassLoader().getResourceAsStream(this.schema);
/*     */       } catch (Exception localException) {
/* 236 */         LOGGER.error("Unable to access schema " + this.schema);
/*     */       }
/* 238 */       if (localInputStream1 != null) {
/* 239 */         StreamSource localStreamSource = new StreamSource(localInputStream1, "Log4j-config.xsd");
/* 240 */         localObject1 = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
/* 241 */         localObject2 = null;
/*     */         try {
/* 243 */           localObject2 = ((SchemaFactory)localObject1).newSchema(localStreamSource);
/*     */         } catch (SAXException localSAXException2) {
/* 245 */           LOGGER.error("Error parsing Log4j schema", localSAXException2);
/*     */         }
/* 247 */         if (localObject2 != null) {
/* 248 */           Validator localValidator = ((Schema)localObject2).newValidator();
/*     */           try {
/* 250 */             localValidator.validate(new StreamSource(new ByteArrayInputStream(arrayOfByte)));
/*     */           } catch (IOException localIOException2) {
/* 252 */             LOGGER.error("Error reading configuration for validation", localIOException2);
/*     */           } catch (SAXException localSAXException3) {
/* 254 */             LOGGER.error("Error validating configuration", localSAXException3);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 260 */     if (getName() == null) {
/* 261 */       setName(paramConfigurationSource.getLocation());
/*     */     }
/*     */   }
/*     */   
/*     */   public void setup()
/*     */   {
/* 267 */     if (this.rootElement == null) {
/* 268 */       LOGGER.error("No logging configuration");
/* 269 */       return;
/*     */     }
/* 271 */     constructHierarchy(this.rootNode, this.rootElement);
/* 272 */     if (this.status.size() > 0) {
/* 273 */       for (Status localStatus : this.status) {
/* 274 */         LOGGER.error("Error processing element " + localStatus.name + ": " + localStatus.errorType);
/*     */       }
/* 276 */       return;
/*     */     }
/* 278 */     this.rootElement = null;
/*     */   }
/*     */   
/*     */   public Configuration reconfigure()
/*     */   {
/* 283 */     if (this.configFile != null) {
/*     */       try {
/* 285 */         ConfigurationFactory.ConfigurationSource localConfigurationSource = new ConfigurationFactory.ConfigurationSource(new FileInputStream(this.configFile), this.configFile);
/*     */         
/* 287 */         return new XMLConfiguration(localConfigurationSource);
/*     */       } catch (FileNotFoundException localFileNotFoundException) {
/* 289 */         LOGGER.error("Cannot locate file " + this.configFile, localFileNotFoundException);
/*     */       }
/*     */     }
/* 292 */     return null;
/*     */   }
/*     */   
/*     */   private void constructHierarchy(Node paramNode, Element paramElement) {
/* 296 */     processAttributes(paramNode, paramElement);
/* 297 */     StringBuilder localStringBuilder = new StringBuilder();
/* 298 */     NodeList localNodeList = paramElement.getChildNodes();
/* 299 */     List localList = paramNode.getChildren();
/* 300 */     for (int i = 0; i < localNodeList.getLength(); i++) {
/* 301 */       org.w3c.dom.Node localNode = localNodeList.item(i);
/* 302 */       Object localObject; if ((localNode instanceof Element)) {
/* 303 */         localObject = (Element)localNode;
/* 304 */         String str2 = getType((Element)localObject);
/* 305 */         PluginType localPluginType = this.pluginManager.getPluginType(str2);
/* 306 */         Node localNode1 = new Node(paramNode, str2, localPluginType);
/* 307 */         constructHierarchy(localNode1, (Element)localObject);
/* 308 */         if (localPluginType == null) {
/* 309 */           String str3 = localNode1.getValue();
/* 310 */           if ((!localNode1.hasChildren()) && (str3 != null)) {
/* 311 */             paramNode.getAttributes().put(str2, str3);
/*     */           } else {
/* 313 */             this.status.add(new Status(str2, paramElement, ErrorType.CLASS_NOT_FOUND));
/*     */           }
/*     */         } else {
/* 316 */           localList.add(localNode1);
/*     */         }
/* 318 */       } else if ((localNode instanceof Text)) {
/* 319 */         localObject = (Text)localNode;
/* 320 */         localStringBuilder.append(((Text)localObject).getData());
/*     */       }
/*     */     }
/*     */     
/* 324 */     String str1 = localStringBuilder.toString().trim();
/* 325 */     if ((str1.length() > 0) || ((!paramNode.hasChildren()) && (!paramNode.isRoot()))) {
/* 326 */       paramNode.setValue(str1);
/*     */     }
/*     */   }
/*     */   
/*     */   private String getType(Element paramElement) {
/* 331 */     if (this.strict) {
/* 332 */       NamedNodeMap localNamedNodeMap = paramElement.getAttributes();
/* 333 */       for (int i = 0; i < localNamedNodeMap.getLength(); i++) {
/* 334 */         org.w3c.dom.Node localNode = localNamedNodeMap.item(i);
/* 335 */         if ((localNode instanceof Attr)) {
/* 336 */           Attr localAttr = (Attr)localNode;
/* 337 */           if (localAttr.getName().equalsIgnoreCase("type")) {
/* 338 */             String str = localAttr.getValue();
/* 339 */             localNamedNodeMap.removeNamedItem(localAttr.getName());
/* 340 */             return str;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 345 */     return paramElement.getTagName();
/*     */   }
/*     */   
/*     */   private byte[] toByteArray(InputStream paramInputStream) throws IOException {
/* 349 */     ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
/*     */     
/*     */ 
/* 352 */     byte[] arrayOfByte = new byte['䀀'];
/*     */     int i;
/* 354 */     while ((i = paramInputStream.read(arrayOfByte, 0, arrayOfByte.length)) != -1) {
/* 355 */       localByteArrayOutputStream.write(arrayOfByte, 0, i);
/*     */     }
/*     */     
/* 358 */     return localByteArrayOutputStream.toByteArray();
/*     */   }
/*     */   
/*     */   private Map<String, String> processAttributes(Node paramNode, Element paramElement) {
/* 362 */     NamedNodeMap localNamedNodeMap = paramElement.getAttributes();
/* 363 */     Map localMap = paramNode.getAttributes();
/*     */     
/* 365 */     for (int i = 0; i < localNamedNodeMap.getLength(); i++) {
/* 366 */       org.w3c.dom.Node localNode = localNamedNodeMap.item(i);
/* 367 */       if ((localNode instanceof Attr)) {
/* 368 */         Attr localAttr = (Attr)localNode;
/* 369 */         if (!localAttr.getName().equals("xml:base"))
/*     */         {
/*     */ 
/* 372 */           localMap.put(localAttr.getName(), localAttr.getValue()); }
/*     */       }
/*     */     }
/* 375 */     return localMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static enum ErrorType
/*     */   {
/* 382 */     CLASS_NOT_FOUND;
/*     */     
/*     */     private ErrorType() {}
/*     */   }
/*     */   
/*     */   private class Status
/*     */   {
/*     */     private final Element element;
/*     */     private final String name;
/*     */     private final XMLConfiguration.ErrorType errorType;
/*     */     
/*     */     public Status(String paramString, Element paramElement, XMLConfiguration.ErrorType paramErrorType) {
/* 394 */       this.name = paramString;
/* 395 */       this.element = paramElement;
/* 396 */       this.errorType = paramErrorType;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\config\XMLConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */