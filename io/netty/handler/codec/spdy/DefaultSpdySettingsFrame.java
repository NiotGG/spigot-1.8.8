/*     */ package io.netty.handler.codec.spdy;
/*     */ 
/*     */ import io.netty.util.internal.StringUtil;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
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
/*     */ public class DefaultSpdySettingsFrame
/*     */   implements SpdySettingsFrame
/*     */ {
/*     */   private boolean clear;
/*     */   private final Map<Integer, Setting> settingsMap;
/*     */   
/*     */   public DefaultSpdySettingsFrame()
/*     */   {
/*  30 */     this.settingsMap = new TreeMap();
/*     */   }
/*     */   
/*     */   public Set<Integer> ids() {
/*  34 */     return this.settingsMap.keySet();
/*     */   }
/*     */   
/*     */   public boolean isSet(int paramInt)
/*     */   {
/*  39 */     Integer localInteger = Integer.valueOf(paramInt);
/*  40 */     return this.settingsMap.containsKey(localInteger);
/*     */   }
/*     */   
/*     */   public int getValue(int paramInt)
/*     */   {
/*  45 */     Integer localInteger = Integer.valueOf(paramInt);
/*  46 */     if (this.settingsMap.containsKey(localInteger)) {
/*  47 */       return ((Setting)this.settingsMap.get(localInteger)).getValue();
/*     */     }
/*  49 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */   public SpdySettingsFrame setValue(int paramInt1, int paramInt2)
/*     */   {
/*  55 */     return setValue(paramInt1, paramInt2, false, false);
/*     */   }
/*     */   
/*     */   public SpdySettingsFrame setValue(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  60 */     if ((paramInt1 < 0) || (paramInt1 > 16777215)) {
/*  61 */       throw new IllegalArgumentException("Setting ID is not valid: " + paramInt1);
/*     */     }
/*  63 */     Integer localInteger = Integer.valueOf(paramInt1);
/*  64 */     if (this.settingsMap.containsKey(localInteger)) {
/*  65 */       Setting localSetting = (Setting)this.settingsMap.get(localInteger);
/*  66 */       localSetting.setValue(paramInt2);
/*  67 */       localSetting.setPersist(paramBoolean1);
/*  68 */       localSetting.setPersisted(paramBoolean2);
/*     */     } else {
/*  70 */       this.settingsMap.put(localInteger, new Setting(paramInt2, paramBoolean1, paramBoolean2));
/*     */     }
/*  72 */     return this;
/*     */   }
/*     */   
/*     */   public SpdySettingsFrame removeValue(int paramInt)
/*     */   {
/*  77 */     Integer localInteger = Integer.valueOf(paramInt);
/*  78 */     if (this.settingsMap.containsKey(localInteger)) {
/*  79 */       this.settingsMap.remove(localInteger);
/*     */     }
/*  81 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isPersistValue(int paramInt)
/*     */   {
/*  86 */     Integer localInteger = Integer.valueOf(paramInt);
/*  87 */     if (this.settingsMap.containsKey(localInteger)) {
/*  88 */       return ((Setting)this.settingsMap.get(localInteger)).isPersist();
/*     */     }
/*  90 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public SpdySettingsFrame setPersistValue(int paramInt, boolean paramBoolean)
/*     */   {
/*  96 */     Integer localInteger = Integer.valueOf(paramInt);
/*  97 */     if (this.settingsMap.containsKey(localInteger)) {
/*  98 */       ((Setting)this.settingsMap.get(localInteger)).setPersist(paramBoolean);
/*     */     }
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isPersisted(int paramInt)
/*     */   {
/* 105 */     Integer localInteger = Integer.valueOf(paramInt);
/* 106 */     if (this.settingsMap.containsKey(localInteger)) {
/* 107 */       return ((Setting)this.settingsMap.get(localInteger)).isPersisted();
/*     */     }
/* 109 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */   public SpdySettingsFrame setPersisted(int paramInt, boolean paramBoolean)
/*     */   {
/* 115 */     Integer localInteger = Integer.valueOf(paramInt);
/* 116 */     if (this.settingsMap.containsKey(localInteger)) {
/* 117 */       ((Setting)this.settingsMap.get(localInteger)).setPersisted(paramBoolean);
/*     */     }
/* 119 */     return this;
/*     */   }
/*     */   
/*     */   public boolean clearPreviouslyPersistedSettings()
/*     */   {
/* 124 */     return this.clear;
/*     */   }
/*     */   
/*     */   public SpdySettingsFrame setClearPreviouslyPersistedSettings(boolean paramBoolean)
/*     */   {
/* 129 */     this.clear = paramBoolean;
/* 130 */     return this;
/*     */   }
/*     */   
/*     */   private Set<Map.Entry<Integer, Setting>> getSettings() {
/* 134 */     return this.settingsMap.entrySet();
/*     */   }
/*     */   
/*     */   private void appendSettings(StringBuilder paramStringBuilder) {
/* 138 */     for (Map.Entry localEntry : getSettings()) {
/* 139 */       Setting localSetting = (Setting)localEntry.getValue();
/* 140 */       paramStringBuilder.append("--> ");
/* 141 */       paramStringBuilder.append(localEntry.getKey());
/* 142 */       paramStringBuilder.append(':');
/* 143 */       paramStringBuilder.append(localSetting.getValue());
/* 144 */       paramStringBuilder.append(" (persist value: ");
/* 145 */       paramStringBuilder.append(localSetting.isPersist());
/* 146 */       paramStringBuilder.append("; persisted: ");
/* 147 */       paramStringBuilder.append(localSetting.isPersisted());
/* 148 */       paramStringBuilder.append(')');
/* 149 */       paramStringBuilder.append(StringUtil.NEWLINE);
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 155 */     StringBuilder localStringBuilder = new StringBuilder();
/* 156 */     localStringBuilder.append(StringUtil.simpleClassName(this));
/* 157 */     localStringBuilder.append(StringUtil.NEWLINE);
/* 158 */     appendSettings(localStringBuilder);
/* 159 */     localStringBuilder.setLength(localStringBuilder.length() - StringUtil.NEWLINE.length());
/* 160 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static final class Setting
/*     */   {
/*     */     private int value;
/*     */     private boolean persist;
/*     */     private boolean persisted;
/*     */     
/*     */     Setting(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 170 */       this.value = paramInt;
/* 171 */       this.persist = paramBoolean1;
/* 172 */       this.persisted = paramBoolean2;
/*     */     }
/*     */     
/*     */     int getValue() {
/* 176 */       return this.value;
/*     */     }
/*     */     
/*     */     void setValue(int paramInt) {
/* 180 */       this.value = paramInt;
/*     */     }
/*     */     
/*     */     boolean isPersist() {
/* 184 */       return this.persist;
/*     */     }
/*     */     
/*     */     void setPersist(boolean paramBoolean) {
/* 188 */       this.persist = paramBoolean;
/*     */     }
/*     */     
/*     */     boolean isPersisted() {
/* 192 */       return this.persisted;
/*     */     }
/*     */     
/*     */     void setPersisted(boolean paramBoolean) {
/* 196 */       this.persisted = paramBoolean;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\io\netty\handler\codec\spdy\DefaultSpdySettingsFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */