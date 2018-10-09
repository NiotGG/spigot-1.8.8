/*     */ package io.netty.util;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
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
/*     */ public final class Version
/*     */ {
/*     */   private static final String PROP_VERSION = ".version";
/*     */   private static final String PROP_BUILD_DATE = ".buildDate";
/*     */   private static final String PROP_COMMIT_DATE = ".commitDate";
/*     */   private static final String PROP_SHORT_COMMIT_HASH = ".shortCommitHash";
/*     */   private static final String PROP_LONG_COMMIT_HASH = ".longCommitHash";
/*     */   private static final String PROP_REPO_STATUS = ".repoStatus";
/*     */   private final String artifactId;
/*     */   private final String artifactVersion;
/*     */   private final long buildTimeMillis;
/*     */   private final long commitTimeMillis;
/*     */   private final String shortCommitHash;
/*     */   private final String longCommitHash;
/*     */   private final String repositoryStatus;
/*     */   
/*     */   public static Map<String, Version> identify()
/*     */   {
/*  56 */     return identify(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Map<String, Version> identify(ClassLoader paramClassLoader)
/*     */   {
/*  65 */     if (paramClassLoader == null) {
/*  66 */       paramClassLoader = PlatformDependent.getContextClassLoader();
/*     */     }
/*     */     
/*     */ 
/*  70 */     Properties localProperties = new Properties();
/*     */     try {
/*  72 */       Enumeration localEnumeration = paramClassLoader.getResources("META-INF/io.netty.versions.properties");
/*  73 */       for (;;) { if (localEnumeration.hasMoreElements()) {
/*  74 */           localObject1 = (URL)localEnumeration.nextElement();
/*  75 */           localObject2 = ((URL)localObject1).openStream();
/*     */           try {
/*  77 */             localProperties.load((InputStream)localObject2);
/*     */             try
/*     */             {
/*  80 */               ((InputStream)localObject2).close(); } catch (Exception localException2) {} } finally { try { ((InputStream)localObject2).close();
/*     */             }
/*     */             catch (Exception localException3) {}
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception localException1) {}
/*     */     
/*     */ 
/*     */ 
/*  91 */     HashSet localHashSet = new HashSet();
/*  92 */     for (Object localObject1 = localProperties.keySet().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = ((Iterator)localObject1).next();
/*  93 */       str1 = (String)localObject2;
/*     */       
/*  95 */       int i = str1.indexOf('.');
/*  96 */       if (i > 0)
/*     */       {
/*     */ 
/*     */ 
/* 100 */         String str2 = str1.substring(0, i);
/*     */         
/*     */ 
/* 103 */         if ((localProperties.containsKey(str2 + ".version")) && (localProperties.containsKey(str2 + ".buildDate")) && (localProperties.containsKey(str2 + ".commitDate")) && (localProperties.containsKey(str2 + ".shortCommitHash")) && (localProperties.containsKey(str2 + ".longCommitHash")) && (localProperties.containsKey(str2 + ".repoStatus")))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 112 */           localHashSet.add(str2); }
/*     */       } }
/*     */     String str1;
/* 115 */     localObject1 = new TreeMap();
/* 116 */     for (Object localObject2 = localHashSet.iterator(); ((Iterator)localObject2).hasNext();) { str1 = (String)((Iterator)localObject2).next();
/* 117 */       ((Map)localObject1).put(str1, new Version(str1, localProperties.getProperty(str1 + ".version"), parseIso8601(localProperties.getProperty(str1 + ".buildDate")), parseIso8601(localProperties.getProperty(str1 + ".commitDate")), localProperties.getProperty(str1 + ".shortCommitHash"), localProperties.getProperty(str1 + ".longCommitHash"), localProperties.getProperty(str1 + ".repoStatus")));
/*     */     }
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
/* 129 */     return (Map<String, Version>)localObject1;
/*     */   }
/*     */   
/*     */   private static long parseIso8601(String paramString) {
/*     */     try {
/* 134 */       return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(paramString).getTime();
/*     */     } catch (ParseException localParseException) {}
/* 136 */     return 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void main(String[] paramArrayOfString)
/*     */   {
/* 144 */     for (Version localVersion : identify().values()) {
/* 145 */       System.err.println(localVersion);
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
/*     */ 
/*     */   private Version(String paramString1, String paramString2, long paramLong1, long paramLong2, String paramString3, String paramString4, String paramString5)
/*     */   {
/* 161 */     this.artifactId = paramString1;
/* 162 */     this.artifactVersion = paramString2;
/* 163 */     this.buildTimeMillis = paramLong1;
/* 164 */     this.commitTimeMillis = paramLong2;
/* 165 */     this.shortCommitHash = paramString3;
/* 166 */     this.longCommitHash = paramString4;
/* 167 */     this.repositoryStatus = paramString5;
/*     */   }
/*     */   
/*     */   public String artifactId() {
/* 171 */     return this.artifactId;
/*     */   }
/*     */   
/*     */   public String artifactVersion() {
/* 175 */     return this.artifactVersion;
/*     */   }
/*     */   
/*     */   public long buildTimeMillis() {
/* 179 */     return this.buildTimeMillis;
/*     */   }
/*     */   
/*     */   public long commitTimeMillis() {
/* 183 */     return this.commitTimeMillis;
/*     */   }
/*     */   
/*     */   public String shortCommitHash() {
/* 187 */     return this.shortCommitHash;
/*     */   }
/*     */   
/*     */   public String longCommitHash() {
/* 191 */     return this.longCommitHash;
/*     */   }
/*     */   
/*     */   public String repositoryStatus() {
/* 195 */     return this.repositoryStatus;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 200 */     return this.artifactId + '-' + this.artifactVersion + '.' + this.shortCommitHash + ("clean".equals(this.repositoryStatus) ? "" : new StringBuilder().append(" (repository: ").append(this.repositoryStatus).append(')').toString());
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\util\Version.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */