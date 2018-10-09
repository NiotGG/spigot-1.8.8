/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class GameProfileSerializer
/*     */ {
/*     */   public static GameProfile deserialize(NBTTagCompound paramNBTTagCompound)
/*     */   {
/*  17 */     String str1 = null;
/*  18 */     String str2 = null;
/*     */     
/*  20 */     if (paramNBTTagCompound.hasKeyOfType("Name", 8)) {
/*  21 */       str1 = paramNBTTagCompound.getString("Name");
/*     */     }
/*  23 */     if (paramNBTTagCompound.hasKeyOfType("Id", 8)) {
/*  24 */       str2 = paramNBTTagCompound.getString("Id");
/*     */     }
/*     */     
/*  27 */     if ((!UtilColor.b(str1)) || (!UtilColor.b(str2))) {
/*     */       UUID localUUID;
/*     */       try {
/*  30 */         localUUID = UUID.fromString(str2);
/*     */       } catch (Throwable localThrowable) {
/*  32 */         localUUID = null;
/*     */       }
/*  34 */       GameProfile localGameProfile = new GameProfile(localUUID, str1);
/*     */       NBTTagCompound localNBTTagCompound1;
/*  36 */       if (paramNBTTagCompound.hasKeyOfType("Properties", 10)) {
/*  37 */         localNBTTagCompound1 = paramNBTTagCompound.getCompound("Properties");
/*     */         
/*  39 */         for (String str3 : localNBTTagCompound1.c()) {
/*  40 */           NBTTagList localNBTTagList = localNBTTagCompound1.getList(str3, 10);
/*  41 */           for (int i = 0; i < localNBTTagList.size(); i++) {
/*  42 */             NBTTagCompound localNBTTagCompound2 = localNBTTagList.get(i);
/*  43 */             String str4 = localNBTTagCompound2.getString("Value");
/*     */             
/*  45 */             if (localNBTTagCompound2.hasKeyOfType("Signature", 8)) {
/*  46 */               localGameProfile.getProperties().put(str3, new Property(str3, str4, localNBTTagCompound2.getString("Signature")));
/*     */             } else {
/*  48 */               localGameProfile.getProperties().put(str3, new Property(str3, str4));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*  54 */       return localGameProfile;
/*     */     }
/*  56 */     return null;
/*     */   }
/*     */   
/*     */   public static NBTTagCompound serialize(NBTTagCompound paramNBTTagCompound, GameProfile paramGameProfile) {
/*  60 */     if (!UtilColor.b(paramGameProfile.getName())) {
/*  61 */       paramNBTTagCompound.setString("Name", paramGameProfile.getName());
/*     */     }
/*  63 */     if (paramGameProfile.getId() != null) {
/*  64 */       paramNBTTagCompound.setString("Id", paramGameProfile.getId().toString());
/*     */     }
/*  66 */     if (!paramGameProfile.getProperties().isEmpty()) {
/*  67 */       NBTTagCompound localNBTTagCompound1 = new NBTTagCompound();
/*  68 */       for (String str : paramGameProfile.getProperties().keySet()) {
/*  69 */         NBTTagList localNBTTagList = new NBTTagList();
/*  70 */         for (Property localProperty : paramGameProfile.getProperties().get(str)) {
/*  71 */           NBTTagCompound localNBTTagCompound2 = new NBTTagCompound();
/*  72 */           localNBTTagCompound2.setString("Value", localProperty.getValue());
/*  73 */           if (localProperty.hasSignature()) {
/*  74 */             localNBTTagCompound2.setString("Signature", localProperty.getSignature());
/*     */           }
/*  76 */           localNBTTagList.add(localNBTTagCompound2);
/*     */         }
/*  78 */         localNBTTagCompound1.set(str, localNBTTagList);
/*     */       }
/*  80 */       paramNBTTagCompound.set("Properties", localNBTTagCompound1);
/*     */     }
/*     */     
/*  83 */     return paramNBTTagCompound;
/*     */   }
/*     */   
/*     */   public static boolean a(NBTBase paramNBTBase1, NBTBase paramNBTBase2, boolean paramBoolean)
/*     */   {
/*  88 */     if (paramNBTBase1 == paramNBTBase2) {
/*  89 */       return true;
/*     */     }
/*  91 */     if (paramNBTBase1 == null) {
/*  92 */       return true;
/*     */     }
/*  94 */     if (paramNBTBase2 == null) {
/*  95 */       return false;
/*     */     }
/*  97 */     if (!paramNBTBase1.getClass().equals(paramNBTBase2.getClass()))
/*  98 */       return false;
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 101 */     Object localObject3; if ((paramNBTBase1 instanceof NBTTagCompound)) {
/* 102 */       localObject1 = (NBTTagCompound)paramNBTBase1;
/* 103 */       localObject2 = (NBTTagCompound)paramNBTBase2;
/*     */       
/* 105 */       for (Iterator localIterator = ((NBTTagCompound)localObject1).c().iterator(); localIterator.hasNext();) { localObject3 = (String)localIterator.next();
/* 106 */         NBTBase localNBTBase = ((NBTTagCompound)localObject1).get((String)localObject3);
/* 107 */         if (!a(localNBTBase, ((NBTTagCompound)localObject2).get((String)localObject3), paramBoolean)) {
/* 108 */           return false;
/*     */         }
/*     */       }
/*     */       
/* 112 */       return true; }
/* 113 */     if (((paramNBTBase1 instanceof NBTTagList)) && (paramBoolean)) {
/* 114 */       localObject1 = (NBTTagList)paramNBTBase1;
/* 115 */       localObject2 = (NBTTagList)paramNBTBase2;
/*     */       
/* 117 */       if (((NBTTagList)localObject1).size() == 0) {
/* 118 */         return ((NBTTagList)localObject2).size() == 0;
/*     */       }
/*     */       
/* 121 */       for (int i = 0; i < ((NBTTagList)localObject1).size(); i++) {
/* 122 */         localObject3 = ((NBTTagList)localObject1).g(i);
/* 123 */         int j = 0;
/* 124 */         for (int k = 0; k < ((NBTTagList)localObject2).size(); k++) {
/* 125 */           if (a((NBTBase)localObject3, ((NBTTagList)localObject2).g(k), paramBoolean)) {
/* 126 */             j = 1;
/* 127 */             break;
/*     */           }
/*     */         }
/* 130 */         if (j == 0) {
/* 131 */           return false;
/*     */         }
/*     */       }
/*     */       
/* 135 */       return true;
/*     */     }
/* 137 */     return paramNBTBase1.equals(paramNBTBase2);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\GameProfileSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */