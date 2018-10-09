/*     */ package net.minecraft.server.v1_8_R3;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum EnumProtocol
/*     */ {
/*     */   private static int e;
/*     */   private static int f;
/*     */   private static final EnumProtocol[] g;
/*     */   private static final Map<Class<? extends Packet>, EnumProtocol> h;
/*     */   private final int i;
/* 148 */   private final Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet>>> j = Maps.newEnumMap(EnumProtocolDirection.class);
/*     */   
/*     */   private EnumProtocol(int paramInt) {
/* 151 */     this.i = paramInt;
/*     */   }
/*     */   
/*     */   protected EnumProtocol a(EnumProtocolDirection paramEnumProtocolDirection, Class<? extends Packet> paramClass) {
/* 155 */     Object localObject = (BiMap)this.j.get(paramEnumProtocolDirection);
/*     */     
/* 157 */     if (localObject == null) {
/* 158 */       localObject = HashBiMap.create();
/* 159 */       this.j.put(paramEnumProtocolDirection, localObject);
/*     */     }
/*     */     
/* 162 */     if (((BiMap)localObject).containsValue(paramClass)) {
/* 163 */       String str = paramEnumProtocolDirection + " packet " + paramClass + " is already known to ID " + ((BiMap)localObject).inverse().get(paramClass);
/* 164 */       LogManager.getLogger().fatal(str);
/* 165 */       throw new IllegalArgumentException(str);
/*     */     }
/*     */     
/* 168 */     ((BiMap)localObject).put(Integer.valueOf(((BiMap)localObject).size()), paramClass);
/* 169 */     return this;
/*     */   }
/*     */   
/*     */   public Integer a(EnumProtocolDirection paramEnumProtocolDirection, Packet paramPacket) {
/* 173 */     return (Integer)((BiMap)this.j.get(paramEnumProtocolDirection)).inverse().get(paramPacket.getClass());
/*     */   }
/*     */   
/*     */   public Packet a(EnumProtocolDirection paramEnumProtocolDirection, int paramInt) throws IllegalAccessException, InstantiationException
/*     */   {
/* 178 */     Class localClass = (Class)((BiMap)this.j.get(paramEnumProtocolDirection)).get(Integer.valueOf(paramInt));
/*     */     
/* 180 */     if (localClass == null) {
/* 181 */       return null;
/*     */     }
/* 183 */     return (Packet)localClass.newInstance();
/*     */   }
/*     */   
/*     */   public int a()
/*     */   {
/* 188 */     return this.i;
/*     */   }
/*     */   
/*     */   static
/*     */   {
/* 143 */     e = -1;
/* 144 */     f = 2;
/* 145 */     g = new EnumProtocol[f - e + 1];
/* 146 */     h = Maps.newHashMap();
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
/*     */ 
/*     */     EnumProtocol localEnumProtocol;
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
/*     */ 
/* 192 */     for (localEnumProtocol : values()) {
/* 193 */       int i1 = localEnumProtocol.a();
/* 194 */       if ((i1 < e) || (i1 > f)) {
/* 195 */         throw new Error("Invalid protocol ID " + Integer.toString(i1));
/*     */       }
/* 197 */       g[(i1 - e)] = localEnumProtocol;
/*     */       
/* 199 */       for (EnumProtocolDirection localEnumProtocolDirection : localEnumProtocol.j.keySet()) {
/* 200 */         for (Class localClass : ((BiMap)localEnumProtocol.j.get(localEnumProtocolDirection)).values()) {
/* 201 */           if ((h.containsKey(localClass)) && (h.get(localClass) != localEnumProtocol)) {
/* 202 */             throw new Error("Packet " + localClass + " is already assigned to protocol " + h.get(localClass) + " - can't reassign to " + localEnumProtocol);
/*     */           }
/*     */           try
/*     */           {
/* 206 */             localClass.newInstance();
/*     */           } catch (Throwable localThrowable) {
/* 208 */             throw new Error("Packet " + localClass + " fails instantiation checks! " + localClass);
/*     */           }
/*     */           
/* 211 */           h.put(localClass, localEnumProtocol);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static EnumProtocol a(int paramInt) {
/* 218 */     if ((paramInt < e) || (paramInt > f)) {
/* 219 */       return null;
/*     */     }
/* 221 */     return g[(paramInt - e)];
/*     */   }
/*     */   
/*     */   public static EnumProtocol a(Packet paramPacket) {
/* 225 */     return (EnumProtocol)h.get(paramPacket.getClass());
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_8_R3\EnumProtocol.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */