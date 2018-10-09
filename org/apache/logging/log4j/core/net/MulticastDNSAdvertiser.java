/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.helpers.Integers;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name="multicastdns", category="Core", elementType="advertiser", printObject=false)
/*     */ public class MulticastDNSAdvertiser
/*     */   implements Advertiser
/*     */ {
/*  40 */   protected static final Logger LOGGER = ;
/*  41 */   private static Object jmDNS = initializeJMDNS();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Class<?> jmDNSClass;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Class<?> serviceInfoClass;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object advertise(Map<String, String> paramMap)
/*     */   {
/*  66 */     HashMap localHashMap = new HashMap();
/*  67 */     for (Object localObject1 = paramMap.entrySet().iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Map.Entry)((Iterator)localObject1).next();
/*     */       
/*  69 */       if ((((String)((Map.Entry)localObject2).getKey()).length() <= 255) && (((String)((Map.Entry)localObject2).getValue()).length() <= 255))
/*     */       {
/*  71 */         localHashMap.put(((Map.Entry)localObject2).getKey(), ((Map.Entry)localObject2).getValue());
/*     */       }
/*     */     }
/*  74 */     localObject1 = (String)localHashMap.get("protocol");
/*  75 */     Object localObject2 = "._log4j._" + (localObject1 != null ? localObject1 : "tcp") + ".local.";
/*     */     
/*  77 */     String str1 = (String)localHashMap.get("port");
/*  78 */     int i = Integers.parseInt(str1, 4555);
/*     */     
/*  80 */     String str2 = (String)localHashMap.get("name");
/*     */     
/*     */ 
/*  83 */     if (jmDNS != null)
/*     */     {
/*  85 */       int j = 0;
/*     */       try
/*     */       {
/*  88 */         jmDNSClass.getMethod("create", (Class[])null);
/*  89 */         j = 1;
/*     */       }
/*     */       catch (NoSuchMethodException localNoSuchMethodException1) {}
/*     */       
/*     */       Object localObject3;
/*  94 */       if (j != 0) {
/*  95 */         localObject3 = buildServiceInfoVersion3((String)localObject2, i, str2, localHashMap);
/*     */       } else {
/*  97 */         localObject3 = buildServiceInfoVersion1((String)localObject2, i, str2, localHashMap);
/*     */       }
/*     */       try
/*     */       {
/* 101 */         Method localMethod = jmDNSClass.getMethod("registerService", new Class[] { serviceInfoClass });
/* 102 */         localMethod.invoke(jmDNS, new Object[] { localObject3 });
/*     */       } catch (IllegalAccessException localIllegalAccessException) {
/* 104 */         LOGGER.warn("Unable to invoke registerService method", localIllegalAccessException);
/*     */       } catch (NoSuchMethodException localNoSuchMethodException2) {
/* 106 */         LOGGER.warn("No registerService method", localNoSuchMethodException2);
/*     */       } catch (InvocationTargetException localInvocationTargetException) {
/* 108 */         LOGGER.warn("Unable to invoke registerService method", localInvocationTargetException);
/*     */       }
/* 110 */       return localObject3;
/*     */     }
/*     */     
/*     */ 
/* 114 */     LOGGER.warn("JMDNS not available - will not advertise ZeroConf support");
/* 115 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void unadvertise(Object paramObject)
/*     */   {
/* 125 */     if (jmDNS != null) {
/*     */       try {
/* 127 */         Method localMethod = jmDNSClass.getMethod("unregisterService", new Class[] { serviceInfoClass });
/* 128 */         localMethod.invoke(jmDNS, new Object[] { paramObject });
/*     */       } catch (IllegalAccessException localIllegalAccessException) {
/* 130 */         LOGGER.warn("Unable to invoke unregisterService method", localIllegalAccessException);
/*     */       } catch (NoSuchMethodException localNoSuchMethodException) {
/* 132 */         LOGGER.warn("No unregisterService method", localNoSuchMethodException);
/*     */       } catch (InvocationTargetException localInvocationTargetException) {
/* 134 */         LOGGER.warn("Unable to invoke unregisterService method", localInvocationTargetException);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static Object createJmDNSVersion1()
/*     */   {
/*     */     try {
/* 142 */       return jmDNSClass.newInstance();
/*     */     } catch (InstantiationException localInstantiationException) {
/* 144 */       LOGGER.warn("Unable to instantiate JMDNS", localInstantiationException);
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 146 */       LOGGER.warn("Unable to instantiate JMDNS", localIllegalAccessException);
/*     */     }
/* 148 */     return null;
/*     */   }
/*     */   
/*     */   private static Object createJmDNSVersion3()
/*     */   {
/*     */     try {
/* 154 */       Method localMethod = jmDNSClass.getMethod("create", (Class[])null);
/* 155 */       return localMethod.invoke(null, (Object[])null);
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 157 */       LOGGER.warn("Unable to instantiate jmdns class", localIllegalAccessException);
/*     */     } catch (NoSuchMethodException localNoSuchMethodException) {
/* 159 */       LOGGER.warn("Unable to access constructor", localNoSuchMethodException);
/*     */     } catch (InvocationTargetException localInvocationTargetException) {
/* 161 */       LOGGER.warn("Unable to call constructor", localInvocationTargetException);
/*     */     }
/* 163 */     return null;
/*     */   }
/*     */   
/*     */   private Object buildServiceInfoVersion1(String paramString1, int paramInt, String paramString2, Map<String, String> paramMap)
/*     */   {
/* 168 */     Hashtable localHashtable = new Hashtable(paramMap);
/*     */     try {
/* 170 */       Class[] arrayOfClass = new Class[6];
/* 171 */       arrayOfClass[0] = String.class;
/* 172 */       arrayOfClass[1] = String.class;
/* 173 */       arrayOfClass[2] = Integer.TYPE;
/* 174 */       arrayOfClass[3] = Integer.TYPE;
/* 175 */       arrayOfClass[4] = Integer.TYPE;
/* 176 */       arrayOfClass[5] = Hashtable.class;
/* 177 */       Constructor localConstructor = serviceInfoClass.getConstructor(arrayOfClass);
/* 178 */       Object[] arrayOfObject = new Object[6];
/* 179 */       arrayOfObject[0] = paramString1;
/* 180 */       arrayOfObject[1] = paramString2;
/* 181 */       arrayOfObject[2] = Integer.valueOf(paramInt);
/* 182 */       arrayOfObject[3] = Integer.valueOf(0);
/* 183 */       arrayOfObject[4] = Integer.valueOf(0);
/* 184 */       arrayOfObject[5] = localHashtable;
/* 185 */       return localConstructor.newInstance(arrayOfObject);
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 187 */       LOGGER.warn("Unable to construct ServiceInfo instance", localIllegalAccessException);
/*     */     } catch (NoSuchMethodException localNoSuchMethodException) {
/* 189 */       LOGGER.warn("Unable to get ServiceInfo constructor", localNoSuchMethodException);
/*     */     } catch (InstantiationException localInstantiationException) {
/* 191 */       LOGGER.warn("Unable to construct ServiceInfo instance", localInstantiationException);
/*     */     } catch (InvocationTargetException localInvocationTargetException) {
/* 193 */       LOGGER.warn("Unable to construct ServiceInfo instance", localInvocationTargetException);
/*     */     }
/* 195 */     return null;
/*     */   }
/*     */   
/*     */   private Object buildServiceInfoVersion3(String paramString1, int paramInt, String paramString2, Map<String, String> paramMap) {
/*     */     try {
/* 200 */       Class[] arrayOfClass = new Class[6];
/* 201 */       arrayOfClass[0] = String.class;
/* 202 */       arrayOfClass[1] = String.class;
/* 203 */       arrayOfClass[2] = Integer.TYPE;
/* 204 */       arrayOfClass[3] = Integer.TYPE;
/* 205 */       arrayOfClass[4] = Integer.TYPE;
/* 206 */       arrayOfClass[5] = Map.class;
/* 207 */       Method localMethod = serviceInfoClass.getMethod("create", arrayOfClass);
/* 208 */       Object[] arrayOfObject = new Object[6];
/* 209 */       arrayOfObject[0] = paramString1;
/* 210 */       arrayOfObject[1] = paramString2;
/* 211 */       arrayOfObject[2] = Integer.valueOf(paramInt);
/* 212 */       arrayOfObject[3] = Integer.valueOf(0);
/* 213 */       arrayOfObject[4] = Integer.valueOf(0);
/* 214 */       arrayOfObject[5] = paramMap;
/* 215 */       return localMethod.invoke(null, arrayOfObject);
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 217 */       LOGGER.warn("Unable to invoke create method", localIllegalAccessException);
/*     */     } catch (NoSuchMethodException localNoSuchMethodException) {
/* 219 */       LOGGER.warn("Unable to find create method", localNoSuchMethodException);
/*     */     } catch (InvocationTargetException localInvocationTargetException) {
/* 221 */       LOGGER.warn("Unable to invoke create method", localInvocationTargetException);
/*     */     }
/* 223 */     return null;
/*     */   }
/*     */   
/*     */   private static Object initializeJMDNS() {
/*     */     try {
/* 228 */       jmDNSClass = Class.forName("javax.jmdns.JmDNS");
/* 229 */       serviceInfoClass = Class.forName("javax.jmdns.ServiceInfo");
/*     */       
/* 231 */       int i = 0;
/*     */       try
/*     */       {
/* 234 */         jmDNSClass.getMethod("create", (Class[])null);
/* 235 */         i = 1;
/*     */       }
/*     */       catch (NoSuchMethodException localNoSuchMethodException) {}
/*     */       
/*     */ 
/* 240 */       if (i != 0) {
/* 241 */         return createJmDNSVersion3();
/*     */       }
/* 243 */       return createJmDNSVersion1();
/*     */     }
/*     */     catch (ClassNotFoundException localClassNotFoundException) {
/* 246 */       LOGGER.warn("JmDNS or serviceInfo class not found", localClassNotFoundException);
/*     */     } catch (ExceptionInInitializerError localExceptionInInitializerError) {
/* 248 */       LOGGER.warn("JmDNS or serviceInfo class not found", localExceptionInInitializerError);
/*     */     }
/* 250 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\net\MulticastDNSAdvertiser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */