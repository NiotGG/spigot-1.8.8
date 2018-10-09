/*    */ package com.mojang.authlib.yggdrasil;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import com.mojang.authlib.Agent;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import com.mojang.authlib.ProfileLookupCallback;
/*    */ import com.mojang.authlib.exceptions.AuthenticationException;
/*    */ import com.mojang.authlib.yggdrasil.response.ProfileSearchResultsResponse;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class YggdrasilGameProfileRepository implements com.mojang.authlib.GameProfileRepository
/*    */ {
/* 16 */   private static final Logger LOGGER = ;
/*    */   private static final String BASE_URL = "https://api.mojang.com/";
/*    */   private static final String SEARCH_PAGE_URL = "https://api.mojang.com/profiles/";
/*    */   private static final int ENTRIES_PER_PAGE = 2;
/*    */   private static final int MAX_FAIL_COUNT = 3;
/*    */   private static final int DELAY_BETWEEN_PAGES = 100;
/*    */   private static final int DELAY_BETWEEN_FAILURES = 750;
/*    */   private final YggdrasilAuthenticationService authenticationService;
/*    */   
/*    */   public YggdrasilGameProfileRepository(YggdrasilAuthenticationService paramYggdrasilAuthenticationService)
/*    */   {
/* 27 */     this.authenticationService = paramYggdrasilAuthenticationService;
/*    */   }
/*    */   
/*    */   public void findProfilesByNames(String[] paramArrayOfString, Agent paramAgent, ProfileLookupCallback paramProfileLookupCallback)
/*    */   {
/* 32 */     java.util.HashSet localHashSet = Sets.newHashSet();
/*    */     
/* 34 */     for (String str1 : paramArrayOfString) {
/* 35 */       if (!com.google.common.base.Strings.isNullOrEmpty(str1)) {
/* 36 */         localHashSet.add(str1.toLowerCase());
/*    */       }
/*    */     }
/*    */     
/* 40 */     int i = 0;
/*    */     
/* 42 */     for (List localList : com.google.common.collect.Iterables.partition(localHashSet, 2)) {
/* 43 */       int m = 0;
/*    */       int n;
/*    */       do
/*    */       {
/* 47 */         n = 0;
/*    */         try
/*    */         {
/* 50 */           ProfileSearchResultsResponse localProfileSearchResultsResponse = (ProfileSearchResultsResponse)this.authenticationService.makeRequest(com.mojang.authlib.HttpAuthenticationService.constantURL("https://api.mojang.com/profiles/" + paramAgent.getName().toLowerCase()), localList, ProfileSearchResultsResponse.class);
/* 51 */           m = 0;
/*    */           
/* 53 */           LOGGER.debug("Page {} returned {} results, parsing", new Object[] { Integer.valueOf(i), Integer.valueOf(localProfileSearchResultsResponse.getProfiles().length) });
/*    */           
/* 55 */           localObject1 = Sets.newHashSet(localList);
/* 56 */           for (GameProfile localGameProfile : localProfileSearchResultsResponse.getProfiles()) {
/* 57 */             LOGGER.debug("Successfully looked up profile {}", new Object[] { localGameProfile });
/* 58 */             ((Set)localObject1).remove(localGameProfile.getName().toLowerCase());
/* 59 */             paramProfileLookupCallback.onProfileLookupSucceeded(localGameProfile);
/*    */           }
/*    */           
/* 62 */           for (??? = ((Set)localObject1).iterator(); ((Iterator)???).hasNext();) { String str3 = (String)((Iterator)???).next();
/* 63 */             LOGGER.debug("Couldn't find profile {}", new Object[] { str3 });
/* 64 */             paramProfileLookupCallback.onProfileLookupFailed(new GameProfile(null, str3), new ProfileNotFoundException("Server did not find the requested profile"));
/*    */           }
/*    */           try
/*    */           {
/* 68 */             Thread.sleep(100L);
/*    */           } catch (InterruptedException localInterruptedException2) {}
/*    */         } catch (AuthenticationException localAuthenticationException) {
/*    */           Object localObject1;
/* 72 */           m++;
/*    */           
/* 74 */           if (m == 3) {
/* 75 */             for (localObject1 = localList.iterator(); ((Iterator)localObject1).hasNext();) { String str2 = (String)((Iterator)localObject1).next();
/* 76 */               LOGGER.debug("Couldn't find profile {} because of a server error", new Object[] { str2 });
/* 77 */               paramProfileLookupCallback.onProfileLookupFailed(new GameProfile(null, str2), localAuthenticationException);
/*    */             }
/*    */           } else {
/*    */             try {
/* 81 */               Thread.sleep(750L);
/*    */             }
/*    */             catch (InterruptedException localInterruptedException1) {}
/* 84 */             n = 1;
/*    */           }
/*    */         }
/* 87 */       } while (n != 0);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\mojang\authlib\yggdrasil\YggdrasilGameProfileRepository.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */