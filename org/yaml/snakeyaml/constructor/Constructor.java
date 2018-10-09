/*     */ package org.yaml.snakeyaml.constructor;
/*     */ 
/*     */ import java.beans.IntrospectionException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ import org.yaml.snakeyaml.TypeDescription;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.introspector.Property;
/*     */ import org.yaml.snakeyaml.introspector.PropertyUtils;
/*     */ import org.yaml.snakeyaml.nodes.MappingNode;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.NodeId;
/*     */ import org.yaml.snakeyaml.nodes.NodeTuple;
/*     */ import org.yaml.snakeyaml.nodes.ScalarNode;
/*     */ import org.yaml.snakeyaml.nodes.SequenceNode;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
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
/*     */ public class Constructor
/*     */   extends SafeConstructor
/*     */ {
/*     */   private final Map<Tag, Class<? extends Object>> typeTags;
/*     */   protected final Map<Class<? extends Object>, TypeDescription> typeDefinitions;
/*     */   
/*     */   public Constructor()
/*     */   {
/*  54 */     this(Object.class);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Constructor(Class<? extends Object> theRoot)
/*     */   {
/*  64 */     this(new TypeDescription(checkRoot(theRoot)));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static Class<? extends Object> checkRoot(Class<? extends Object> theRoot)
/*     */   {
/*  71 */     if (theRoot == null) {
/*  72 */       throw new NullPointerException("Root class must be provided.");
/*     */     }
/*  74 */     return theRoot;
/*     */   }
/*     */   
/*     */   public Constructor(TypeDescription theRoot) {
/*  78 */     if (theRoot == null) {
/*  79 */       throw new NullPointerException("Root type must be provided.");
/*     */     }
/*  81 */     this.yamlConstructors.put(null, new ConstructYamlObject());
/*  82 */     if (!Object.class.equals(theRoot.getType())) {
/*  83 */       this.rootTag = new Tag(theRoot.getType());
/*     */     }
/*  85 */     this.typeTags = new HashMap();
/*  86 */     this.typeDefinitions = new HashMap();
/*  87 */     this.yamlClassConstructors.put(NodeId.scalar, new ConstructScalar());
/*  88 */     this.yamlClassConstructors.put(NodeId.mapping, new ConstructMapping());
/*  89 */     this.yamlClassConstructors.put(NodeId.sequence, new ConstructSequence());
/*  90 */     addTypeDescription(theRoot);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Constructor(String theRoot)
/*     */     throws ClassNotFoundException
/*     */   {
/* 103 */     this(Class.forName(check(theRoot)));
/*     */   }
/*     */   
/*     */   private static final String check(String s) {
/* 107 */     if (s == null) {
/* 108 */       throw new NullPointerException("Root type must be provided.");
/*     */     }
/* 110 */     if (s.trim().length() == 0) {
/* 111 */       throw new YAMLException("Root type must be provided.");
/*     */     }
/* 113 */     return s;
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
/*     */   public TypeDescription addTypeDescription(TypeDescription definition)
/*     */   {
/* 127 */     if (definition == null) {
/* 128 */       throw new NullPointerException("TypeDescription is required.");
/*     */     }
/* 130 */     Tag tag = definition.getTag();
/* 131 */     this.typeTags.put(tag, definition.getType());
/* 132 */     return (TypeDescription)this.typeDefinitions.put(definition.getType(), definition);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected class ConstructMapping
/*     */     implements Construct
/*     */   {
/*     */     protected ConstructMapping() {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Object construct(Node node)
/*     */     {
/* 151 */       MappingNode mnode = (MappingNode)node;
/* 152 */       if (Properties.class.isAssignableFrom(node.getType())) {
/* 153 */         Properties properties = new Properties();
/* 154 */         if (!node.isTwoStepsConstruction()) {
/* 155 */           Constructor.this.constructMapping2ndStep(mnode, properties);
/*     */         } else {
/* 157 */           throw new YAMLException("Properties must not be recursive.");
/*     */         }
/* 159 */         return properties; }
/* 160 */       if (SortedMap.class.isAssignableFrom(node.getType())) {
/* 161 */         SortedMap<Object, Object> map = new TreeMap();
/* 162 */         if (!node.isTwoStepsConstruction()) {
/* 163 */           Constructor.this.constructMapping2ndStep(mnode, map);
/*     */         }
/* 165 */         return map; }
/* 166 */       if (Map.class.isAssignableFrom(node.getType())) {
/* 167 */         if (node.isTwoStepsConstruction()) {
/* 168 */           return Constructor.this.createDefaultMap();
/*     */         }
/* 170 */         return Constructor.this.constructMapping(mnode);
/*     */       }
/* 172 */       if (SortedSet.class.isAssignableFrom(node.getType())) {
/* 173 */         SortedSet<Object> set = new TreeSet();
/*     */         
/*     */ 
/* 176 */         Constructor.this.constructSet2ndStep(mnode, set);
/*     */         
/* 178 */         return set; }
/* 179 */       if (Collection.class.isAssignableFrom(node.getType())) {
/* 180 */         if (node.isTwoStepsConstruction()) {
/* 181 */           return Constructor.this.createDefaultSet();
/*     */         }
/* 183 */         return Constructor.this.constructSet(mnode);
/*     */       }
/*     */       
/* 186 */       if (node.isTwoStepsConstruction()) {
/* 187 */         return createEmptyJavaBean(mnode);
/*     */       }
/* 189 */       return constructJavaBean2ndStep(mnode, createEmptyJavaBean(mnode));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void construct2ndStep(Node node, Object object)
/*     */     {
/* 196 */       if (Map.class.isAssignableFrom(node.getType())) {
/* 197 */         Constructor.this.constructMapping2ndStep((MappingNode)node, (Map)object);
/* 198 */       } else if (Set.class.isAssignableFrom(node.getType())) {
/* 199 */         Constructor.this.constructSet2ndStep((MappingNode)node, (Set)object);
/*     */       } else {
/* 201 */         constructJavaBean2ndStep((MappingNode)node, object);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected Object createEmptyJavaBean(MappingNode node)
/*     */     {
/*     */       try
/*     */       {
/* 215 */         java.lang.reflect.Constructor<?> c = node.getType().getDeclaredConstructor(new Class[0]);
/* 216 */         c.setAccessible(true);
/* 217 */         return c.newInstance(new Object[0]);
/*     */       } catch (Exception e) {
/* 219 */         throw new YAMLException(e);
/*     */       }
/*     */     }
/*     */     
/*     */     protected Object constructJavaBean2ndStep(MappingNode node, Object object) {
/* 224 */       Constructor.this.flattenMapping(node);
/* 225 */       Class<? extends Object> beanType = node.getType();
/* 226 */       List<NodeTuple> nodeValue = node.getValue();
/* 227 */       for (NodeTuple tuple : nodeValue) {
/*     */         ScalarNode keyNode;
/* 229 */         if ((tuple.getKeyNode() instanceof ScalarNode))
/*     */         {
/* 231 */           keyNode = (ScalarNode)tuple.getKeyNode();
/*     */         } else
/* 233 */           throw new YAMLException("Keys must be scalars but found: " + tuple.getKeyNode());
/*     */         ScalarNode keyNode;
/* 235 */         Node valueNode = tuple.getValueNode();
/*     */         
/* 237 */         keyNode.setType(String.class);
/* 238 */         String key = (String)Constructor.this.constructObject(keyNode);
/*     */         try {
/* 240 */           Property property = getProperty(beanType, key);
/* 241 */           valueNode.setType(property.getType());
/* 242 */           TypeDescription memberDescription = (TypeDescription)Constructor.this.typeDefinitions.get(beanType);
/* 243 */           boolean typeDetected = false;
/* 244 */           if (memberDescription != null) {
/* 245 */             switch (Constructor.1.$SwitchMap$org$yaml$snakeyaml$nodes$NodeId[valueNode.getNodeId().ordinal()]) {
/*     */             case 1: 
/* 247 */               SequenceNode snode = (SequenceNode)valueNode;
/* 248 */               Class<? extends Object> memberType = memberDescription.getListPropertyType(key);
/*     */               
/* 250 */               if (memberType != null) {
/* 251 */                 snode.setListType(memberType);
/* 252 */                 typeDetected = true;
/* 253 */               } else if (property.getType().isArray()) {
/* 254 */                 snode.setListType(property.getType().getComponentType());
/* 255 */                 typeDetected = true;
/*     */               }
/*     */               break;
/*     */             case 2: 
/* 259 */               MappingNode mnode = (MappingNode)valueNode;
/* 260 */               Class<? extends Object> keyType = memberDescription.getMapKeyType(key);
/* 261 */               if (keyType != null) {
/* 262 */                 mnode.setTypes(keyType, memberDescription.getMapValueType(key));
/* 263 */                 typeDetected = true;
/*     */               }
/*     */               break;
/*     */             }
/*     */             
/*     */           }
/* 269 */           if ((!typeDetected) && (valueNode.getNodeId() != NodeId.scalar))
/*     */           {
/* 271 */             Class<?>[] arguments = property.getActualTypeArguments();
/* 272 */             if ((arguments != null) && (arguments.length > 0))
/*     */             {
/*     */ 
/* 275 */               if (valueNode.getNodeId() == NodeId.sequence) {
/* 276 */                 Class<?> t = arguments[0];
/* 277 */                 SequenceNode snode = (SequenceNode)valueNode;
/* 278 */                 snode.setListType(t);
/* 279 */               } else if (valueNode.getTag().equals(Tag.SET)) {
/* 280 */                 Class<?> t = arguments[0];
/* 281 */                 MappingNode mnode = (MappingNode)valueNode;
/* 282 */                 mnode.setOnlyKeyType(t);
/* 283 */                 mnode.setUseClassConstructor(Boolean.valueOf(true));
/* 284 */               } else if (property.getType().isAssignableFrom(Map.class)) {
/* 285 */                 Class<?> ketType = arguments[0];
/* 286 */                 Class<?> valueType = arguments[1];
/* 287 */                 MappingNode mnode = (MappingNode)valueNode;
/* 288 */                 mnode.setTypes(ketType, valueType);
/* 289 */                 mnode.setUseClassConstructor(Boolean.valueOf(true));
/*     */               }
/*     */             }
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 297 */           Object value = Constructor.this.constructObject(valueNode);
/*     */           
/*     */ 
/* 300 */           if (((property.getType() == Float.TYPE) || (property.getType() == Float.class)) && 
/* 301 */             ((value instanceof Double))) {
/* 302 */             value = Float.valueOf(((Double)value).floatValue());
/*     */           }
/*     */           
/*     */ 
/* 306 */           property.set(object, value);
/*     */         } catch (Exception e) {
/* 308 */           throw new ConstructorException("Cannot create property=" + key + " for JavaBean=" + object, node.getStartMark(), e.getMessage(), valueNode.getStartMark(), e);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 313 */       return object;
/*     */     }
/*     */     
/*     */     protected Property getProperty(Class<? extends Object> type, String name) throws IntrospectionException
/*     */     {
/* 318 */       return Constructor.this.getPropertyUtils().getProperty(type, name);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected class ConstructYamlObject
/*     */     implements Construct
/*     */   {
/*     */     protected ConstructYamlObject() {}
/*     */     
/*     */ 
/*     */     private Construct getConstructor(Node node)
/*     */     {
/* 331 */       Class<?> cl = Constructor.this.getClassForNode(node);
/* 332 */       node.setType(cl);
/*     */       
/* 334 */       Construct constructor = (Construct)Constructor.this.yamlClassConstructors.get(node.getNodeId());
/* 335 */       return constructor;
/*     */     }
/*     */     
/*     */     public Object construct(Node node) {
/* 339 */       Object result = null;
/*     */       try {
/* 341 */         result = getConstructor(node).construct(node);
/*     */       } catch (ConstructorException e) {
/* 343 */         throw e;
/*     */       } catch (Exception e) {
/* 345 */         throw new ConstructorException(null, null, "Can't construct a java object for " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
/*     */       }
/*     */       
/* 348 */       return result;
/*     */     }
/*     */     
/*     */     public void construct2ndStep(Node node, Object object) {
/*     */       try {
/* 353 */         getConstructor(node).construct2ndStep(node, object);
/*     */       } catch (Exception e) {
/* 355 */         throw new ConstructorException(null, null, "Can't construct a second step for a java object for " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   protected class ConstructScalar
/*     */     extends AbstractConstruct
/*     */   {
/*     */     protected ConstructScalar() {}
/*     */     
/*     */     public Object construct(Node nnode)
/*     */     {
/* 368 */       ScalarNode node = (ScalarNode)nnode;
/* 369 */       Class<?> type = node.getType();
/*     */       Object result;
/* 371 */       Object result; if ((type.isPrimitive()) || (type == String.class) || (Number.class.isAssignableFrom(type)) || (type == Boolean.class) || (Date.class.isAssignableFrom(type)) || (type == Character.class) || (type == BigInteger.class) || (type == BigDecimal.class) || (Enum.class.isAssignableFrom(type)) || (Tag.BINARY.equals(node.getTag())) || (Calendar.class.isAssignableFrom(type)))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 377 */         result = constructStandardJavaInstance(type, node);
/*     */       }
/*     */       else {
/* 380 */         java.lang.reflect.Constructor<?>[] javaConstructors = type.getConstructors();
/* 381 */         int oneArgCount = 0;
/* 382 */         java.lang.reflect.Constructor<?> javaConstructor = null;
/* 383 */         for (java.lang.reflect.Constructor<?> c : javaConstructors) {
/* 384 */           if (c.getParameterTypes().length == 1) {
/* 385 */             oneArgCount++;
/* 386 */             javaConstructor = c;
/*     */           }
/*     */         }
/*     */         
/* 390 */         if (javaConstructor == null)
/* 391 */           throw new YAMLException("No single argument constructor found for " + type);
/* 392 */         Object argument; Object argument; if (oneArgCount == 1) {
/* 393 */           argument = constructStandardJavaInstance(javaConstructor.getParameterTypes()[0], node);
/*     */ 
/*     */ 
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/*     */ 
/* 402 */           argument = Constructor.this.constructScalar(node);
/*     */           try {
/* 404 */             javaConstructor = type.getConstructor(new Class[] { String.class });
/*     */           } catch (Exception e) {
/* 406 */             throw new YAMLException("Can't construct a java object for scalar " + node.getTag() + "; No String constructor found. Exception=" + e.getMessage(), e);
/*     */           }
/*     */         }
/*     */         
/*     */         try
/*     */         {
/* 412 */           result = javaConstructor.newInstance(new Object[] { argument });
/*     */         } catch (Exception e) {
/* 414 */           throw new ConstructorException(null, null, "Can't construct a java object for scalar " + node.getTag() + "; exception=" + e.getMessage(), node.getStartMark(), e);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 419 */       return result;
/*     */     }
/*     */     
/*     */ 
/*     */     private Object constructStandardJavaInstance(Class type, ScalarNode node)
/*     */     {
/*     */       Object result;
/* 426 */       if (type == String.class) {
/* 427 */         Construct stringConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.STR);
/* 428 */         result = stringConstructor.construct(node); } else { Object result;
/* 429 */         if ((type == Boolean.class) || (type == Boolean.TYPE)) {
/* 430 */           Construct boolConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.BOOL);
/* 431 */           result = boolConstructor.construct(node); } else { Object result;
/* 432 */           if ((type == Character.class) || (type == Character.TYPE)) {
/* 433 */             Construct charConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.STR);
/* 434 */             String ch = (String)charConstructor.construct(node);
/* 435 */             Object result; if (ch.length() == 0) {
/* 436 */               result = null;
/* 437 */             } else { if (ch.length() != 1) {
/* 438 */                 throw new YAMLException("Invalid node Character: '" + ch + "'; length: " + ch.length());
/*     */               }
/*     */               
/* 441 */               result = Character.valueOf(ch.charAt(0));
/*     */             }
/* 443 */           } else if (Date.class.isAssignableFrom(type)) {
/* 444 */             Construct dateConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.TIMESTAMP);
/* 445 */             Date date = (Date)dateConstructor.construct(node);
/* 446 */             Object result; if (type == Date.class) {
/* 447 */               result = date;
/*     */             } else {
/*     */               try {
/* 450 */                 java.lang.reflect.Constructor<?> constr = type.getConstructor(new Class[] { Long.TYPE });
/* 451 */                 result = constr.newInstance(new Object[] { Long.valueOf(date.getTime()) });
/*     */               } catch (RuntimeException e) { Object result;
/* 453 */                 throw e;
/*     */               } catch (Exception e) {
/* 455 */                 throw new YAMLException("Cannot construct: '" + type + "'");
/*     */               }
/*     */             }
/* 458 */           } else if ((type == Float.class) || (type == Double.class) || (type == Float.TYPE) || (type == Double.TYPE) || (type == BigDecimal.class)) {
/*     */             Object result;
/* 460 */             if (type == BigDecimal.class) {
/* 461 */               result = new BigDecimal(node.getValue());
/*     */             } else {
/* 463 */               Construct doubleConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.FLOAT);
/* 464 */               Object result = doubleConstructor.construct(node);
/* 465 */               if ((type == Float.class) || (type == Float.TYPE)) {
/* 466 */                 result = new Float(((Double)result).doubleValue());
/*     */               }
/*     */             }
/* 469 */           } else if ((type == Byte.class) || (type == Short.class) || (type == Integer.class) || (type == Long.class) || (type == BigInteger.class) || (type == Byte.TYPE) || (type == Short.TYPE) || (type == Integer.TYPE) || (type == Long.TYPE))
/*     */           {
/*     */ 
/* 472 */             Construct intConstructor = (Construct)Constructor.this.yamlConstructors.get(Tag.INT);
/* 473 */             Object result = intConstructor.construct(node);
/* 474 */             if ((type == Byte.class) || (type == Byte.TYPE)) {
/* 475 */               result = Byte.valueOf(result.toString());
/* 476 */             } else if ((type == Short.class) || (type == Short.TYPE)) {
/* 477 */               result = Short.valueOf(result.toString());
/* 478 */             } else if ((type == Integer.class) || (type == Integer.TYPE)) {
/* 479 */               result = Integer.valueOf(Integer.parseInt(result.toString()));
/* 480 */             } else if ((type == Long.class) || (type == Long.TYPE)) {
/* 481 */               result = Long.valueOf(result.toString());
/*     */             }
/*     */             else {
/* 484 */               result = new BigInteger(result.toString());
/*     */             }
/* 486 */           } else if (Enum.class.isAssignableFrom(type)) {
/* 487 */             String enumValueName = node.getValue();
/*     */             try {
/* 489 */               result = Enum.valueOf(type, enumValueName);
/*     */             } catch (Exception ex) { Object result;
/* 491 */               throw new YAMLException("Unable to find enum value '" + enumValueName + "' for enum class: " + type.getName());
/*     */             }
/*     */           } else { Object result;
/* 494 */             if (Calendar.class.isAssignableFrom(type)) {
/* 495 */               SafeConstructor.ConstructYamlTimestamp contr = new SafeConstructor.ConstructYamlTimestamp();
/* 496 */               contr.construct(node);
/* 497 */               result = contr.getCalendar(); } else { Object result;
/* 498 */               if (Number.class.isAssignableFrom(type)) {
/* 499 */                 SafeConstructor.ConstructYamlNumber contr = new SafeConstructor.ConstructYamlNumber(Constructor.this);
/* 500 */                 result = contr.construct(node);
/*     */               } else {
/* 502 */                 throw new YAMLException("Unsupported class: " + type); } } } } }
/*     */       Object result;
/* 504 */       return result;
/*     */     }
/*     */   }
/*     */   
/*     */   protected class ConstructSequence
/*     */     implements Construct
/*     */   {
/*     */     protected ConstructSequence() {}
/*     */     
/*     */     public Object construct(Node node)
/*     */     {
/* 515 */       SequenceNode snode = (SequenceNode)node;
/* 516 */       if (Set.class.isAssignableFrom(node.getType())) {
/* 517 */         if (node.isTwoStepsConstruction()) {
/* 518 */           throw new YAMLException("Set cannot be recursive.");
/*     */         }
/* 520 */         return Constructor.this.constructSet(snode);
/*     */       }
/* 522 */       if (Collection.class.isAssignableFrom(node.getType())) {
/* 523 */         if (node.isTwoStepsConstruction()) {
/* 524 */           return Constructor.this.createDefaultList(snode.getValue().size());
/*     */         }
/* 526 */         return Constructor.this.constructSequence(snode);
/*     */       }
/* 528 */       if (node.getType().isArray()) {
/* 529 */         if (node.isTwoStepsConstruction()) {
/* 530 */           return Constructor.this.createArray(node.getType(), snode.getValue().size());
/*     */         }
/* 532 */         return Constructor.this.constructArray(snode);
/*     */       }
/*     */       
/*     */ 
/* 536 */       List<java.lang.reflect.Constructor<?>> possibleConstructors = new ArrayList(snode.getValue().size());
/*     */       
/* 538 */       for (java.lang.reflect.Constructor<?> constructor : node.getType().getConstructors())
/*     */       {
/* 540 */         if (snode.getValue().size() == constructor.getParameterTypes().length)
/* 541 */           possibleConstructors.add(constructor); }
/*     */       List<Object> argumentList;
/*     */       Class<?>[] parameterTypes;
/* 544 */       if (!possibleConstructors.isEmpty()) {
/* 545 */         if (possibleConstructors.size() == 1) {
/* 546 */           Object[] argumentList = new Object[snode.getValue().size()];
/* 547 */           java.lang.reflect.Constructor<?> c = (java.lang.reflect.Constructor)possibleConstructors.get(0);
/* 548 */           int index = 0;
/* 549 */           for (Node argumentNode : snode.getValue()) {
/* 550 */             Class<?> type = c.getParameterTypes()[index];
/*     */             
/* 552 */             argumentNode.setType(type);
/* 553 */             argumentList[(index++)] = Constructor.this.constructObject(argumentNode);
/*     */           }
/*     */           try
/*     */           {
/* 557 */             return c.newInstance(argumentList);
/*     */           } catch (Exception e) {
/* 559 */             throw new YAMLException(e);
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 564 */         argumentList = Constructor.this.constructSequence(snode);
/* 565 */         parameterTypes = new Class[argumentList.size()];
/* 566 */         int index = 0;
/* 567 */         for (Object parameter : argumentList) {
/* 568 */           parameterTypes[index] = parameter.getClass();
/* 569 */           index++;
/*     */         }
/*     */         
/* 572 */         for (java.lang.reflect.Constructor<?> c : possibleConstructors) {
/* 573 */           Class<?>[] argTypes = c.getParameterTypes();
/* 574 */           boolean foundConstructor = true;
/* 575 */           for (int i = 0; i < argTypes.length; i++) {
/* 576 */             if (!wrapIfPrimitive(argTypes[i]).isAssignableFrom(parameterTypes[i])) {
/* 577 */               foundConstructor = false;
/* 578 */               break;
/*     */             }
/*     */           }
/* 581 */           if (foundConstructor) {
/*     */             try {
/* 583 */               return c.newInstance(argumentList.toArray());
/*     */             } catch (Exception e) {
/* 585 */               throw new YAMLException(e);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 590 */       throw new YAMLException("No suitable constructor with " + String.valueOf(snode.getValue().size()) + " arguments found for " + node.getType());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private final Class<? extends Object> wrapIfPrimitive(Class<?> clazz)
/*     */     {
/* 598 */       if (!clazz.isPrimitive()) {
/* 599 */         return clazz;
/*     */       }
/* 601 */       if (clazz == Integer.TYPE) {
/* 602 */         return Integer.class;
/*     */       }
/* 604 */       if (clazz == Float.TYPE) {
/* 605 */         return Float.class;
/*     */       }
/* 607 */       if (clazz == Double.TYPE) {
/* 608 */         return Double.class;
/*     */       }
/* 610 */       if (clazz == Boolean.TYPE) {
/* 611 */         return Boolean.class;
/*     */       }
/* 613 */       if (clazz == Long.TYPE) {
/* 614 */         return Long.class;
/*     */       }
/* 616 */       if (clazz == Character.TYPE) {
/* 617 */         return Character.class;
/*     */       }
/* 619 */       if (clazz == Short.TYPE) {
/* 620 */         return Short.class;
/*     */       }
/* 622 */       if (clazz == Byte.TYPE) {
/* 623 */         return Byte.class;
/*     */       }
/* 625 */       throw new YAMLException("Unexpected primitive " + clazz);
/*     */     }
/*     */     
/*     */     public void construct2ndStep(Node node, Object object)
/*     */     {
/* 630 */       SequenceNode snode = (SequenceNode)node;
/* 631 */       if (List.class.isAssignableFrom(node.getType())) {
/* 632 */         List<Object> list = (List)object;
/* 633 */         Constructor.this.constructSequenceStep2(snode, list);
/* 634 */       } else if (node.getType().isArray()) {
/* 635 */         Constructor.this.constructArrayStep2(snode, object);
/*     */       } else {
/* 637 */         throw new YAMLException("Immutable objects cannot be recursive.");
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected Class<?> getClassForNode(Node node) {
/* 643 */     Class<? extends Object> classForTag = (Class)this.typeTags.get(node.getTag());
/* 644 */     if (classForTag == null) {
/* 645 */       String name = node.getTag().getClassName();
/*     */       Class<?> cl;
/*     */       try {
/* 648 */         cl = getClassForName(name);
/*     */       } catch (ClassNotFoundException e) {
/* 650 */         throw new YAMLException("Class not found: " + name);
/*     */       }
/* 652 */       this.typeTags.put(node.getTag(), cl);
/* 653 */       return cl;
/*     */     }
/* 655 */     return classForTag;
/*     */   }
/*     */   
/*     */   protected Class<?> getClassForName(String name) throws ClassNotFoundException
/*     */   {
/* 660 */     return Class.forName(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\constructor\Constructor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */