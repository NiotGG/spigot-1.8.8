/*     */ package com.mojang.authlib;
/*     */ 
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import com.mojang.util.UUIDTypeAdapter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public abstract class BaseUserAuthentication implements UserAuthentication
/*     */ {
/*  17 */   private static final Logger LOGGER = ;
/*     */   
/*     */   protected static final String STORAGE_KEY_PROFILE_NAME = "displayName";
/*     */   
/*     */   protected static final String STORAGE_KEY_PROFILE_ID = "uuid";
/*     */   protected static final String STORAGE_KEY_PROFILE_PROPERTIES = "profileProperties";
/*     */   protected static final String STORAGE_KEY_USER_NAME = "username";
/*     */   protected static final String STORAGE_KEY_USER_ID = "userid";
/*     */   protected static final String STORAGE_KEY_USER_PROPERTIES = "userProperties";
/*     */   private final AuthenticationService authenticationService;
/*  27 */   private final PropertyMap userProperties = new PropertyMap();
/*     */   private String userid;
/*     */   private String username;
/*     */   private String password;
/*     */   private GameProfile selectedProfile;
/*     */   private UserType userType;
/*     */   
/*     */   protected BaseUserAuthentication(AuthenticationService paramAuthenticationService) {
/*  35 */     org.apache.commons.lang3.Validate.notNull(paramAuthenticationService);
/*  36 */     this.authenticationService = paramAuthenticationService;
/*     */   }
/*     */   
/*     */   public boolean canLogIn()
/*     */   {
/*  41 */     return (!canPlayOnline()) && (StringUtils.isNotBlank(getUsername())) && (StringUtils.isNotBlank(getPassword()));
/*     */   }
/*     */   
/*     */   public void logOut()
/*     */   {
/*  46 */     this.password = null;
/*  47 */     this.userid = null;
/*  48 */     setSelectedProfile(null);
/*  49 */     getModifiableUserProperties().clear();
/*  50 */     setUserType(null);
/*     */   }
/*     */   
/*     */   public boolean isLoggedIn()
/*     */   {
/*  55 */     return getSelectedProfile() != null;
/*     */   }
/*     */   
/*     */   public void setUsername(String paramString)
/*     */   {
/*  60 */     if ((isLoggedIn()) && (canPlayOnline())) {
/*  61 */       throw new IllegalStateException("Cannot change username whilst logged in & online");
/*     */     }
/*     */     
/*  64 */     this.username = paramString;
/*     */   }
/*     */   
/*     */   public void setPassword(String paramString)
/*     */   {
/*  69 */     if ((isLoggedIn()) && (canPlayOnline()) && (StringUtils.isNotBlank(paramString))) {
/*  70 */       throw new IllegalStateException("Cannot set password whilst logged in & online");
/*     */     }
/*     */     
/*  73 */     this.password = paramString;
/*     */   }
/*     */   
/*     */   protected String getUsername() {
/*  77 */     return this.username;
/*     */   }
/*     */   
/*     */   protected String getPassword() {
/*  81 */     return this.password;
/*     */   }
/*     */   
/*     */ 
/*     */   public void loadFromStorage(Map<String, Object> paramMap)
/*     */   {
/*  87 */     logOut();
/*     */     
/*  89 */     setUsername(String.valueOf(paramMap.get("username")));
/*     */     
/*  91 */     if (paramMap.containsKey("userid")) {
/*  92 */       this.userid = String.valueOf(paramMap.get("userid"));
/*     */     } else
/*  94 */       this.userid = this.username;
/*     */     Object localObject1;
/*     */     Object localObject2;
/*  97 */     Object localObject3; String str1; String str2; if (paramMap.containsKey("userProperties")) {
/*     */       try {
/*  99 */         List localList = (List)paramMap.get("userProperties");
/*     */         
/* 101 */         for (localObject1 = localList.iterator(); ((Iterator)localObject1).hasNext();) { localObject2 = (Map)((Iterator)localObject1).next();
/* 102 */           localObject3 = (String)((Map)localObject2).get("name");
/* 103 */           str1 = (String)((Map)localObject2).get("value");
/* 104 */           str2 = (String)((Map)localObject2).get("signature");
/*     */           
/* 106 */           if (str2 == null) {
/* 107 */             getModifiableUserProperties().put(localObject3, new Property((String)localObject3, str1));
/*     */           } else {
/* 109 */             getModifiableUserProperties().put(localObject3, new Property((String)localObject3, str1, str2));
/*     */           }
/*     */         }
/*     */       } catch (Throwable localThrowable1) {
/* 113 */         LOGGER.warn("Couldn't deserialize user properties", localThrowable1);
/*     */       }
/*     */     }
/*     */     
/* 117 */     if ((paramMap.containsKey("displayName")) && (paramMap.containsKey("uuid"))) {
/* 118 */       GameProfile localGameProfile = new GameProfile(UUIDTypeAdapter.fromString(String.valueOf(paramMap.get("uuid"))), String.valueOf(paramMap.get("displayName")));
/* 119 */       if (paramMap.containsKey("profileProperties")) {
/*     */         try {
/* 121 */           localObject1 = (List)paramMap.get("profileProperties");
/* 122 */           for (localObject2 = ((List)localObject1).iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (Map)((Iterator)localObject2).next();
/* 123 */             str1 = (String)((Map)localObject3).get("name");
/* 124 */             str2 = (String)((Map)localObject3).get("value");
/* 125 */             String str3 = (String)((Map)localObject3).get("signature");
/*     */             
/* 127 */             if (str3 == null) {
/* 128 */               localGameProfile.getProperties().put(str1, new Property(str1, str2));
/*     */             } else {
/* 130 */               localGameProfile.getProperties().put(str1, new Property(str1, str2, str3));
/*     */             }
/*     */           }
/*     */         } catch (Throwable localThrowable2) {
/* 134 */           LOGGER.warn("Couldn't deserialize profile properties", localThrowable2);
/*     */         }
/*     */       }
/* 137 */       setSelectedProfile(localGameProfile);
/*     */     }
/*     */   }
/*     */   
/*     */   public Map<String, Object> saveForStorage()
/*     */   {
/* 143 */     HashMap localHashMap1 = new HashMap();
/*     */     
/* 145 */     if (getUsername() != null) {
/* 146 */       localHashMap1.put("username", getUsername());
/*     */     }
/* 148 */     if (getUserID() != null) {
/* 149 */       localHashMap1.put("userid", getUserID());
/* 150 */     } else if (getUsername() != null)
/* 151 */       localHashMap1.put("username", getUsername());
/*     */     Object localObject2;
/*     */     Object localObject3;
/* 154 */     Object localObject4; if (!getUserProperties().isEmpty()) {
/* 155 */       localObject1 = new ArrayList();
/* 156 */       for (localObject2 = getUserProperties().values().iterator(); ((Iterator)localObject2).hasNext();) { localObject3 = (Property)((Iterator)localObject2).next();
/* 157 */         localObject4 = new HashMap();
/* 158 */         ((Map)localObject4).put("name", ((Property)localObject3).getName());
/* 159 */         ((Map)localObject4).put("value", ((Property)localObject3).getValue());
/* 160 */         ((Map)localObject4).put("signature", ((Property)localObject3).getSignature());
/* 161 */         ((List)localObject1).add(localObject4);
/*     */       }
/* 163 */       localHashMap1.put("userProperties", localObject1);
/*     */     }
/*     */     
/* 166 */     Object localObject1 = getSelectedProfile();
/* 167 */     if (localObject1 != null) {
/* 168 */       localHashMap1.put("displayName", ((GameProfile)localObject1).getName());
/* 169 */       localHashMap1.put("uuid", ((GameProfile)localObject1).getId());
/*     */       
/* 171 */       localObject2 = new ArrayList();
/* 172 */       for (localObject3 = ((GameProfile)localObject1).getProperties().values().iterator(); ((Iterator)localObject3).hasNext();) { localObject4 = (Property)((Iterator)localObject3).next();
/* 173 */         HashMap localHashMap2 = new HashMap();
/* 174 */         localHashMap2.put("name", ((Property)localObject4).getName());
/* 175 */         localHashMap2.put("value", ((Property)localObject4).getValue());
/* 176 */         localHashMap2.put("signature", ((Property)localObject4).getSignature());
/* 177 */         ((List)localObject2).add(localHashMap2);
/*     */       }
/*     */       
/* 180 */       if (!((List)localObject2).isEmpty()) {
/* 181 */         localHashMap1.put("profileProperties", localObject2);
/*     */       }
/*     */     }
/*     */     
/* 185 */     return localHashMap1;
/*     */   }
/*     */   
/*     */   protected void setSelectedProfile(GameProfile paramGameProfile) {
/* 189 */     this.selectedProfile = paramGameProfile;
/*     */   }
/*     */   
/*     */   public GameProfile getSelectedProfile()
/*     */   {
/* 194 */     return this.selectedProfile;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 199 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     
/* 201 */     localStringBuilder.append(getClass().getSimpleName());
/* 202 */     localStringBuilder.append("{");
/*     */     
/* 204 */     if (isLoggedIn()) {
/* 205 */       localStringBuilder.append("Logged in as ");
/* 206 */       localStringBuilder.append(getUsername());
/*     */       
/* 208 */       if (getSelectedProfile() != null) {
/* 209 */         localStringBuilder.append(" / ");
/* 210 */         localStringBuilder.append(getSelectedProfile());
/* 211 */         localStringBuilder.append(" - ");
/*     */         
/* 213 */         if (canPlayOnline()) {
/* 214 */           localStringBuilder.append("Online");
/*     */         } else {
/* 216 */           localStringBuilder.append("Offline");
/*     */         }
/*     */       }
/*     */     } else {
/* 220 */       localStringBuilder.append("Not logged in");
/*     */     }
/*     */     
/* 223 */     localStringBuilder.append("}");
/*     */     
/* 225 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   public AuthenticationService getAuthenticationService() {
/* 229 */     return this.authenticationService;
/*     */   }
/*     */   
/*     */   public String getUserID()
/*     */   {
/* 234 */     return this.userid;
/*     */   }
/*     */   
/*     */   public PropertyMap getUserProperties()
/*     */   {
/* 239 */     if (isLoggedIn()) {
/* 240 */       PropertyMap localPropertyMap = new PropertyMap();
/* 241 */       localPropertyMap.putAll(getModifiableUserProperties());
/* 242 */       return localPropertyMap;
/*     */     }
/* 244 */     return new PropertyMap();
/*     */   }
/*     */   
/*     */   protected PropertyMap getModifiableUserProperties()
/*     */   {
/* 249 */     return this.userProperties;
/*     */   }
/*     */   
/*     */   public UserType getUserType()
/*     */   {
/* 254 */     if (isLoggedIn()) {
/* 255 */       return this.userType == null ? UserType.LEGACY : this.userType;
/*     */     }
/* 257 */     return null;
/*     */   }
/*     */   
/*     */   protected void setUserType(UserType paramUserType)
/*     */   {
/* 262 */     this.userType = paramUserType;
/*     */   }
/*     */   
/*     */   protected void setUserid(String paramString) {
/* 266 */     this.userid = paramString;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\BaseUserAuthentication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */