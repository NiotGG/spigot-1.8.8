/*     */ package org.yaml.snakeyaml;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
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
/*     */ public class DumperOptions
/*     */ {
/*     */   public static enum ScalarStyle
/*     */   {
/*  37 */     DOUBLE_QUOTED(Character.valueOf('"')),  SINGLE_QUOTED(Character.valueOf('\'')),  LITERAL(Character.valueOf('|')), 
/*  38 */     FOLDED(Character.valueOf('>')),  PLAIN(null);
/*     */     
/*     */     private Character styleChar;
/*     */     
/*  42 */     private ScalarStyle(Character style) { this.styleChar = style; }
/*     */     
/*     */     public Character getChar()
/*     */     {
/*  46 */       return this.styleChar;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/*  51 */       return "Scalar style: '" + this.styleChar + "'";
/*     */     }
/*     */     
/*     */     public static ScalarStyle createStyle(Character style) {
/*  55 */       if (style == null) {
/*  56 */         return PLAIN;
/*     */       }
/*  58 */       switch (style.charValue()) {
/*     */       case '"': 
/*  60 */         return DOUBLE_QUOTED;
/*     */       case '\'': 
/*  62 */         return SINGLE_QUOTED;
/*     */       case '|': 
/*  64 */         return LITERAL;
/*     */       case '>': 
/*  66 */         return FOLDED;
/*     */       }
/*  68 */       throw new YAMLException("Unknown scalar style character: " + style);
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
/*     */   public static enum FlowStyle
/*     */   {
/*  83 */     FLOW(Boolean.TRUE),  BLOCK(Boolean.FALSE),  AUTO(null);
/*     */     
/*     */     private Boolean styleBoolean;
/*     */     
/*     */     private FlowStyle(Boolean flowStyle) {
/*  88 */       this.styleBoolean = flowStyle;
/*     */     }
/*     */     
/*     */     public Boolean getStyleBoolean() {
/*  92 */       return this.styleBoolean;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/*  97 */       return "Flow style: '" + this.styleBoolean + "'";
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum LineBreak
/*     */   {
/* 105 */     WIN("\r\n"),  MAC("\r"),  UNIX("\n");
/*     */     
/*     */     private String lineBreak;
/*     */     
/*     */     private LineBreak(String lineBreak) {
/* 110 */       this.lineBreak = lineBreak;
/*     */     }
/*     */     
/*     */     public String getString() {
/* 114 */       return this.lineBreak;
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 119 */       return "Line break: " + name();
/*     */     }
/*     */     
/*     */     public static LineBreak getPlatformLineBreak() {
/* 123 */       String platformLineBreak = System.getProperty("line.separator");
/* 124 */       for (LineBreak lb : values()) {
/* 125 */         if (lb.lineBreak.equals(platformLineBreak)) {
/* 126 */           return lb;
/*     */         }
/*     */       }
/* 129 */       return UNIX;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static enum Version
/*     */   {
/* 137 */     V1_0(new Integer[] { Integer.valueOf(1), Integer.valueOf(0) }),  V1_1(new Integer[] { Integer.valueOf(1), Integer.valueOf(1) });
/*     */     
/*     */     private Integer[] version;
/*     */     
/*     */     private Version(Integer[] version) {
/* 142 */       this.version = version;
/*     */     }
/*     */     
/* 145 */     public int major() { return this.version[0].intValue(); }
/* 146 */     public int minor() { return this.version[1].intValue(); }
/*     */     
/*     */     public String getRepresentation() {
/* 149 */       return this.version[0] + "." + this.version[1];
/*     */     }
/*     */     
/*     */     public String toString()
/*     */     {
/* 154 */       return "Version: " + getRepresentation();
/*     */     }
/*     */   }
/*     */   
/* 158 */   private ScalarStyle defaultStyle = ScalarStyle.PLAIN;
/* 159 */   private FlowStyle defaultFlowStyle = FlowStyle.AUTO;
/* 160 */   private boolean canonical = false;
/* 161 */   private boolean allowUnicode = true;
/* 162 */   private boolean allowReadOnlyProperties = false;
/* 163 */   private int indent = 2;
/* 164 */   private int bestWidth = 80;
/* 165 */   private boolean splitLines = true;
/* 166 */   private LineBreak lineBreak = LineBreak.UNIX;
/* 167 */   private boolean explicitStart = false;
/* 168 */   private boolean explicitEnd = false;
/* 169 */   private TimeZone timeZone = null;
/*     */   
/* 171 */   private Version version = null;
/* 172 */   private Map<String, String> tags = null;
/* 173 */   private Boolean prettyFlow = Boolean.valueOf(false);
/*     */   
/*     */   public boolean isAllowUnicode() {
/* 176 */     return this.allowUnicode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAllowUnicode(boolean allowUnicode)
/*     */   {
/* 188 */     this.allowUnicode = allowUnicode;
/*     */   }
/*     */   
/*     */   public ScalarStyle getDefaultScalarStyle() {
/* 192 */     return this.defaultStyle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDefaultScalarStyle(ScalarStyle defaultStyle)
/*     */   {
/* 203 */     if (defaultStyle == null) {
/* 204 */       throw new NullPointerException("Use ScalarStyle enum.");
/*     */     }
/* 206 */     this.defaultStyle = defaultStyle;
/*     */   }
/*     */   
/*     */   public void setIndent(int indent) {
/* 210 */     if (indent < 1) {
/* 211 */       throw new YAMLException("Indent must be at least 1");
/*     */     }
/* 213 */     if (indent > 10) {
/* 214 */       throw new YAMLException("Indent must be at most 10");
/*     */     }
/* 216 */     this.indent = indent;
/*     */   }
/*     */   
/*     */   public int getIndent() {
/* 220 */     return this.indent;
/*     */   }
/*     */   
/*     */   public void setVersion(Version version) {
/* 224 */     this.version = version;
/*     */   }
/*     */   
/*     */   public Version getVersion() {
/* 228 */     return this.version;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCanonical(boolean canonical)
/*     */   {
/* 238 */     this.canonical = canonical;
/*     */   }
/*     */   
/*     */   public boolean isCanonical() {
/* 242 */     return this.canonical;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setPrettyFlow(boolean prettyFlow)
/*     */   {
/* 253 */     this.prettyFlow = Boolean.valueOf(prettyFlow);
/*     */   }
/*     */   
/*     */   public boolean isPrettyFlow() {
/* 257 */     return this.prettyFlow.booleanValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWidth(int bestWidth)
/*     */   {
/* 269 */     this.bestWidth = bestWidth;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 273 */     return this.bestWidth;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSplitLines(boolean splitLines)
/*     */   {
/* 284 */     this.splitLines = splitLines;
/*     */   }
/*     */   
/*     */   public boolean getSplitLines() {
/* 288 */     return this.splitLines;
/*     */   }
/*     */   
/*     */   public LineBreak getLineBreak() {
/* 292 */     return this.lineBreak;
/*     */   }
/*     */   
/*     */   public void setDefaultFlowStyle(FlowStyle defaultFlowStyle) {
/* 296 */     if (defaultFlowStyle == null) {
/* 297 */       throw new NullPointerException("Use FlowStyle enum.");
/*     */     }
/* 299 */     this.defaultFlowStyle = defaultFlowStyle;
/*     */   }
/*     */   
/*     */   public FlowStyle getDefaultFlowStyle() {
/* 303 */     return this.defaultFlowStyle;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLineBreak(LineBreak lineBreak)
/*     */   {
/* 312 */     if (lineBreak == null) {
/* 313 */       throw new NullPointerException("Specify line break.");
/*     */     }
/* 315 */     this.lineBreak = lineBreak;
/*     */   }
/*     */   
/*     */   public boolean isExplicitStart() {
/* 319 */     return this.explicitStart;
/*     */   }
/*     */   
/*     */   public void setExplicitStart(boolean explicitStart) {
/* 323 */     this.explicitStart = explicitStart;
/*     */   }
/*     */   
/*     */   public boolean isExplicitEnd() {
/* 327 */     return this.explicitEnd;
/*     */   }
/*     */   
/*     */   public void setExplicitEnd(boolean explicitEnd) {
/* 331 */     this.explicitEnd = explicitEnd;
/*     */   }
/*     */   
/*     */   public Map<String, String> getTags() {
/* 335 */     return this.tags;
/*     */   }
/*     */   
/*     */   public void setTags(Map<String, String> tags)
/*     */   {
/* 340 */     this.tags = tags;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAllowReadOnlyProperties()
/*     */   {
/* 350 */     return this.allowReadOnlyProperties;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAllowReadOnlyProperties(boolean allowReadOnlyProperties)
/*     */   {
/* 362 */     this.allowReadOnlyProperties = allowReadOnlyProperties;
/*     */   }
/*     */   
/*     */   public TimeZone getTimeZone() {
/* 366 */     return this.timeZone;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTimeZone(TimeZone timeZone)
/*     */   {
/* 374 */     this.timeZone = timeZone;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\DumperOptions.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */