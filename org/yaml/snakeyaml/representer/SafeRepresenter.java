/*     */ package org.yaml.snakeyaml.representer;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TimeZone;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
/*     */ import org.yaml.snakeyaml.reader.StreamReader;
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
/*     */ class SafeRepresenter
/*     */   extends BaseRepresenter
/*     */ {
/*     */   protected Map<Class<? extends Object>, Tag> classTags;
/*  45 */   protected TimeZone timeZone = null;
/*     */   
/*     */   public SafeRepresenter() {
/*  48 */     this.nullRepresenter = new RepresentNull();
/*  49 */     this.representers.put(String.class, new RepresentString());
/*  50 */     this.representers.put(Boolean.class, new RepresentBoolean());
/*  51 */     this.representers.put(Character.class, new RepresentString());
/*  52 */     this.representers.put(byte[].class, new RepresentByteArray());
/*     */     
/*  54 */     Represent primitiveArray = new RepresentPrimitiveArray();
/*  55 */     this.representers.put(short[].class, primitiveArray);
/*  56 */     this.representers.put(int[].class, primitiveArray);
/*  57 */     this.representers.put(long[].class, primitiveArray);
/*  58 */     this.representers.put(float[].class, primitiveArray);
/*  59 */     this.representers.put(double[].class, primitiveArray);
/*  60 */     this.representers.put(char[].class, primitiveArray);
/*  61 */     this.representers.put(boolean[].class, primitiveArray);
/*     */     
/*  63 */     this.multiRepresenters.put(Number.class, new RepresentNumber());
/*  64 */     this.multiRepresenters.put(List.class, new RepresentList());
/*  65 */     this.multiRepresenters.put(Map.class, new RepresentMap());
/*  66 */     this.multiRepresenters.put(Set.class, new RepresentSet());
/*  67 */     this.multiRepresenters.put(Iterator.class, new RepresentIterator());
/*  68 */     this.multiRepresenters.put(new Object[0].getClass(), new RepresentArray());
/*  69 */     this.multiRepresenters.put(Date.class, new RepresentDate());
/*  70 */     this.multiRepresenters.put(Enum.class, new RepresentEnum());
/*  71 */     this.multiRepresenters.put(Calendar.class, new RepresentDate());
/*  72 */     this.classTags = new HashMap();
/*     */   }
/*     */   
/*     */   protected Tag getTag(Class<?> clazz, Tag defaultTag) {
/*  76 */     if (this.classTags.containsKey(clazz)) {
/*  77 */       return (Tag)this.classTags.get(clazz);
/*     */     }
/*  79 */     return defaultTag;
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
/*     */   public Tag addClassTag(Class<? extends Object> clazz, Tag tag)
/*     */   {
/*  94 */     if (tag == null) {
/*  95 */       throw new NullPointerException("Tag must be provided.");
/*     */     }
/*  97 */     return (Tag)this.classTags.put(clazz, tag);
/*     */   }
/*     */   
/*     */   protected class RepresentNull implements Represent { protected RepresentNull() {}
/*     */     
/* 102 */     public Node representData(Object data) { return SafeRepresenter.this.representScalar(Tag.NULL, "null"); }
/*     */   }
/*     */   
/*     */ 
/* 106 */   public static Pattern MULTILINE_PATTERN = Pattern.compile("\n|| | ");
/*     */   
/*     */   protected class RepresentString implements Represent { protected RepresentString() {}
/*     */     
/* 110 */     public Node representData(Object data) { Tag tag = Tag.STR;
/* 111 */       Character style = null;
/* 112 */       String value = data.toString();
/* 113 */       if (StreamReader.NON_PRINTABLE.matcher(value).find()) {
/* 114 */         tag = Tag.BINARY;
/*     */         char[] binary;
/*     */         try {
/* 117 */           binary = Base64Coder.encode(value.getBytes("UTF-8"));
/*     */         } catch (UnsupportedEncodingException e) {
/* 119 */           throw new YAMLException(e);
/*     */         }
/* 121 */         value = String.valueOf(binary);
/* 122 */         style = Character.valueOf('|');
/*     */       }
/*     */       
/*     */ 
/* 126 */       if ((SafeRepresenter.this.defaultScalarStyle == null) && (SafeRepresenter.MULTILINE_PATTERN.matcher(value).find())) {
/* 127 */         style = Character.valueOf('|');
/*     */       }
/* 129 */       return SafeRepresenter.this.representScalar(tag, value, style);
/*     */     } }
/*     */   
/*     */   protected class RepresentBoolean implements Represent { protected RepresentBoolean() {}
/*     */     
/*     */     public Node representData(Object data) { String value;
/*     */       String value;
/* 136 */       if (Boolean.TRUE.equals(data)) {
/* 137 */         value = "true";
/*     */       } else {
/* 139 */         value = "false";
/*     */       }
/* 141 */       return SafeRepresenter.this.representScalar(Tag.BOOL, value);
/*     */     } }
/*     */   
/*     */   protected class RepresentNumber implements Represent { protected RepresentNumber() {}
/*     */     
/*     */     public Node representData(Object data) { String value;
/*     */       Tag tag;
/*     */       String value;
/* 149 */       if (((data instanceof Byte)) || ((data instanceof Short)) || ((data instanceof Integer)) || ((data instanceof Long)) || ((data instanceof BigInteger)))
/*     */       {
/* 151 */         Tag tag = Tag.INT;
/* 152 */         value = data.toString();
/*     */       } else {
/* 154 */         Number number = (Number)data;
/* 155 */         tag = Tag.FLOAT;
/* 156 */         String value; if (number.equals(Double.valueOf(NaN.0D))) {
/* 157 */           value = ".NaN"; } else { String value;
/* 158 */           if (number.equals(Double.valueOf(Double.POSITIVE_INFINITY))) {
/* 159 */             value = ".inf"; } else { String value;
/* 160 */             if (number.equals(Double.valueOf(Double.NEGATIVE_INFINITY))) {
/* 161 */               value = "-.inf";
/*     */             } else
/* 163 */               value = number.toString();
/*     */           }
/*     */         } }
/* 166 */       return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), tag), value);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentList implements Represent {
/*     */     protected RepresentList() {}
/*     */     
/* 173 */     public Node representData(Object data) { return SafeRepresenter.this.representSequence(SafeRepresenter.this.getTag(data.getClass(), Tag.SEQ), (List)data, null); }
/*     */   }
/*     */   
/*     */   protected class RepresentIterator implements Represent {
/*     */     protected RepresentIterator() {}
/*     */     
/*     */     public Node representData(Object data) {
/* 180 */       Iterator<Object> iter = (Iterator)data;
/* 181 */       return SafeRepresenter.this.representSequence(SafeRepresenter.this.getTag(data.getClass(), Tag.SEQ), new SafeRepresenter.IteratorWrapper(iter), null);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class IteratorWrapper implements Iterable<Object>
/*     */   {
/*     */     private Iterator<Object> iter;
/*     */     
/*     */     public IteratorWrapper(Iterator<Object> iter) {
/* 190 */       this.iter = iter;
/*     */     }
/*     */     
/*     */ 
/* 194 */     public Iterator<Object> iterator() { return this.iter; }
/*     */   }
/*     */   
/*     */   protected class RepresentArray implements Represent {
/*     */     protected RepresentArray() {}
/*     */     
/* 200 */     public Node representData(Object data) { Object[] array = (Object[])data;
/* 201 */       List<Object> list = Arrays.asList(array);
/* 202 */       return SafeRepresenter.this.representSequence(Tag.SEQ, list, null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentPrimitiveArray
/*     */     implements Represent
/*     */   {
/*     */     protected RepresentPrimitiveArray() {}
/*     */     
/*     */     public Node representData(Object data)
/*     */     {
/* 213 */       Class<?> type = data.getClass().getComponentType();
/*     */       
/* 215 */       if (Byte.TYPE == type)
/* 216 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asByteList(data), null);
/* 217 */       if (Short.TYPE == type)
/* 218 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asShortList(data), null);
/* 219 */       if (Integer.TYPE == type)
/* 220 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asIntList(data), null);
/* 221 */       if (Long.TYPE == type)
/* 222 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asLongList(data), null);
/* 223 */       if (Float.TYPE == type)
/* 224 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asFloatList(data), null);
/* 225 */       if (Double.TYPE == type)
/* 226 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asDoubleList(data), null);
/* 227 */       if (Character.TYPE == type)
/* 228 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asCharList(data), null);
/* 229 */       if (Boolean.TYPE == type) {
/* 230 */         return SafeRepresenter.this.representSequence(Tag.SEQ, asBooleanList(data), null);
/*     */       }
/*     */       
/* 233 */       throw new YAMLException("Unexpected primitive '" + type.getCanonicalName() + "'");
/*     */     }
/*     */     
/*     */     private List<Byte> asByteList(Object in) {
/* 237 */       byte[] array = (byte[])in;
/* 238 */       List<Byte> list = new ArrayList(array.length);
/* 239 */       for (int i = 0; i < array.length; i++)
/* 240 */         list.add(Byte.valueOf(array[i]));
/* 241 */       return list;
/*     */     }
/*     */     
/*     */     private List<Short> asShortList(Object in) {
/* 245 */       short[] array = (short[])in;
/* 246 */       List<Short> list = new ArrayList(array.length);
/* 247 */       for (int i = 0; i < array.length; i++)
/* 248 */         list.add(Short.valueOf(array[i]));
/* 249 */       return list;
/*     */     }
/*     */     
/*     */     private List<Integer> asIntList(Object in) {
/* 253 */       int[] array = (int[])in;
/* 254 */       List<Integer> list = new ArrayList(array.length);
/* 255 */       for (int i = 0; i < array.length; i++)
/* 256 */         list.add(Integer.valueOf(array[i]));
/* 257 */       return list;
/*     */     }
/*     */     
/*     */     private List<Long> asLongList(Object in) {
/* 261 */       long[] array = (long[])in;
/* 262 */       List<Long> list = new ArrayList(array.length);
/* 263 */       for (int i = 0; i < array.length; i++)
/* 264 */         list.add(Long.valueOf(array[i]));
/* 265 */       return list;
/*     */     }
/*     */     
/*     */     private List<Float> asFloatList(Object in) {
/* 269 */       float[] array = (float[])in;
/* 270 */       List<Float> list = new ArrayList(array.length);
/* 271 */       for (int i = 0; i < array.length; i++)
/* 272 */         list.add(Float.valueOf(array[i]));
/* 273 */       return list;
/*     */     }
/*     */     
/*     */     private List<Double> asDoubleList(Object in) {
/* 277 */       double[] array = (double[])in;
/* 278 */       List<Double> list = new ArrayList(array.length);
/* 279 */       for (int i = 0; i < array.length; i++)
/* 280 */         list.add(Double.valueOf(array[i]));
/* 281 */       return list;
/*     */     }
/*     */     
/*     */     private List<Character> asCharList(Object in) {
/* 285 */       char[] array = (char[])in;
/* 286 */       List<Character> list = new ArrayList(array.length);
/* 287 */       for (int i = 0; i < array.length; i++)
/* 288 */         list.add(Character.valueOf(array[i]));
/* 289 */       return list;
/*     */     }
/*     */     
/*     */     private List<Boolean> asBooleanList(Object in) {
/* 293 */       boolean[] array = (boolean[])in;
/* 294 */       List<Boolean> list = new ArrayList(array.length);
/* 295 */       for (int i = 0; i < array.length; i++)
/* 296 */         list.add(Boolean.valueOf(array[i]));
/* 297 */       return list;
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentMap implements Represent {
/*     */     protected RepresentMap() {}
/*     */     
/* 304 */     public Node representData(Object data) { return SafeRepresenter.this.representMapping(SafeRepresenter.this.getTag(data.getClass(), Tag.MAP), (Map)data, null); }
/*     */   }
/*     */   
/*     */   protected class RepresentSet implements Represent
/*     */   {
/*     */     protected RepresentSet() {}
/*     */     
/*     */     public Node representData(Object data) {
/* 312 */       Map<Object, Object> value = new LinkedHashMap();
/* 313 */       Set<Object> set = (Set)data;
/* 314 */       for (Object key : set) {
/* 315 */         value.put(key, null);
/*     */       }
/* 317 */       return SafeRepresenter.this.representMapping(SafeRepresenter.this.getTag(data.getClass(), Tag.SET), value, null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentDate implements Represent { protected RepresentDate() {}
/*     */     
/*     */     public Node representData(Object data) { Calendar calendar;
/*     */       Calendar calendar;
/* 325 */       if ((data instanceof Calendar)) {
/* 326 */         calendar = (Calendar)data;
/*     */       } else {
/* 328 */         calendar = Calendar.getInstance(SafeRepresenter.this.getTimeZone() == null ? TimeZone.getTimeZone("UTC") : SafeRepresenter.this.timeZone);
/*     */         
/* 330 */         calendar.setTime((Date)data);
/*     */       }
/* 332 */       int years = calendar.get(1);
/* 333 */       int months = calendar.get(2) + 1;
/* 334 */       int days = calendar.get(5);
/* 335 */       int hour24 = calendar.get(11);
/* 336 */       int minutes = calendar.get(12);
/* 337 */       int seconds = calendar.get(13);
/* 338 */       int millis = calendar.get(14);
/* 339 */       StringBuilder buffer = new StringBuilder(String.valueOf(years));
/* 340 */       while (buffer.length() < 4)
/*     */       {
/* 342 */         buffer.insert(0, "0");
/*     */       }
/* 344 */       buffer.append("-");
/* 345 */       if (months < 10) {
/* 346 */         buffer.append("0");
/*     */       }
/* 348 */       buffer.append(String.valueOf(months));
/* 349 */       buffer.append("-");
/* 350 */       if (days < 10) {
/* 351 */         buffer.append("0");
/*     */       }
/* 353 */       buffer.append(String.valueOf(days));
/* 354 */       buffer.append("T");
/* 355 */       if (hour24 < 10) {
/* 356 */         buffer.append("0");
/*     */       }
/* 358 */       buffer.append(String.valueOf(hour24));
/* 359 */       buffer.append(":");
/* 360 */       if (minutes < 10) {
/* 361 */         buffer.append("0");
/*     */       }
/* 363 */       buffer.append(String.valueOf(minutes));
/* 364 */       buffer.append(":");
/* 365 */       if (seconds < 10) {
/* 366 */         buffer.append("0");
/*     */       }
/* 368 */       buffer.append(String.valueOf(seconds));
/* 369 */       if (millis > 0) {
/* 370 */         if (millis < 10) {
/* 371 */           buffer.append(".00");
/* 372 */         } else if (millis < 100) {
/* 373 */           buffer.append(".0");
/*     */         } else {
/* 375 */           buffer.append(".");
/*     */         }
/* 377 */         buffer.append(String.valueOf(millis));
/*     */       }
/* 379 */       if (TimeZone.getTimeZone("UTC").equals(calendar.getTimeZone())) {
/* 380 */         buffer.append("Z");
/*     */       }
/*     */       else {
/* 383 */         int gmtOffset = calendar.getTimeZone().getOffset(calendar.get(0), calendar.get(1), calendar.get(2), calendar.get(5), calendar.get(7), calendar.get(14));
/*     */         
/*     */ 
/*     */ 
/* 387 */         int minutesOffset = gmtOffset / 60000;
/* 388 */         int hoursOffset = minutesOffset / 60;
/* 389 */         int partOfHour = minutesOffset % 60;
/* 390 */         buffer.append((hoursOffset > 0 ? "+" : "") + hoursOffset + ":" + (partOfHour < 10 ? "0" + partOfHour : Integer.valueOf(partOfHour)));
/*     */       }
/*     */       
/* 393 */       return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), Tag.TIMESTAMP), buffer.toString(), null);
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentEnum implements Represent { protected RepresentEnum() {}
/*     */     
/* 399 */     public Node representData(Object data) { Tag tag = new Tag(data.getClass());
/* 400 */       return SafeRepresenter.this.representScalar(SafeRepresenter.this.getTag(data.getClass(), tag), ((Enum)data).name());
/*     */     }
/*     */   }
/*     */   
/*     */   protected class RepresentByteArray implements Represent { protected RepresentByteArray() {}
/*     */     
/* 406 */     public Node representData(Object data) { char[] binary = Base64Coder.encode((byte[])data);
/* 407 */       return SafeRepresenter.this.representScalar(Tag.BINARY, String.valueOf(binary), Character.valueOf('|'));
/*     */     }
/*     */   }
/*     */   
/*     */   public TimeZone getTimeZone() {
/* 412 */     return this.timeZone;
/*     */   }
/*     */   
/*     */   public void setTimeZone(TimeZone timeZone) {
/* 416 */     this.timeZone = timeZone;
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\representer\SafeRepresenter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */