/*      */ package org.yaml.snakeyaml.emitter;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Writer;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import java.util.concurrent.ArrayBlockingQueue;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.yaml.snakeyaml.DumperOptions;
/*      */ import org.yaml.snakeyaml.DumperOptions.LineBreak;
/*      */ import org.yaml.snakeyaml.DumperOptions.Version;
/*      */ import org.yaml.snakeyaml.error.YAMLException;
/*      */ import org.yaml.snakeyaml.events.AliasEvent;
/*      */ import org.yaml.snakeyaml.events.CollectionEndEvent;
/*      */ import org.yaml.snakeyaml.events.CollectionStartEvent;
/*      */ import org.yaml.snakeyaml.events.DocumentEndEvent;
/*      */ import org.yaml.snakeyaml.events.DocumentStartEvent;
/*      */ import org.yaml.snakeyaml.events.Event;
/*      */ import org.yaml.snakeyaml.events.ImplicitTuple;
/*      */ import org.yaml.snakeyaml.events.MappingEndEvent;
/*      */ import org.yaml.snakeyaml.events.MappingStartEvent;
/*      */ import org.yaml.snakeyaml.events.NodeEvent;
/*      */ import org.yaml.snakeyaml.events.ScalarEvent;
/*      */ import org.yaml.snakeyaml.events.SequenceEndEvent;
/*      */ import org.yaml.snakeyaml.events.SequenceStartEvent;
/*      */ import org.yaml.snakeyaml.events.StreamEndEvent;
/*      */ import org.yaml.snakeyaml.events.StreamStartEvent;
/*      */ import org.yaml.snakeyaml.reader.StreamReader;
/*      */ import org.yaml.snakeyaml.scanner.Constant;
/*      */ import org.yaml.snakeyaml.util.ArrayStack;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Emitter
/*      */   implements Emitable
/*      */ {
/*   63 */   private static final Map<Character, String> ESCAPE_REPLACEMENTS = new HashMap();
/*      */   
/*      */   public static final int MIN_INDENT = 1;
/*      */   public static final int MAX_INDENT = 10;
/*   67 */   private static final char[] SPACE = { ' ' };
/*      */   private static final Map<String, String> DEFAULT_TAG_PREFIXES;
/*      */   
/*   70 */   static { ESCAPE_REPLACEMENTS.put(Character.valueOf('\000'), "0");
/*   71 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\007'), "a");
/*   72 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\b'), "b");
/*   73 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\t'), "t");
/*   74 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\n'), "n");
/*   75 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\013'), "v");
/*   76 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\f'), "f");
/*   77 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\r'), "r");
/*   78 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\033'), "e");
/*   79 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('"'), "\"");
/*   80 */     ESCAPE_REPLACEMENTS.put(Character.valueOf('\\'), "\\");
/*   81 */     ESCAPE_REPLACEMENTS.put(Character.valueOf(''), "N");
/*   82 */     ESCAPE_REPLACEMENTS.put(Character.valueOf(' '), "_");
/*   83 */     ESCAPE_REPLACEMENTS.put(Character.valueOf(' '), "L");
/*   84 */     ESCAPE_REPLACEMENTS.put(Character.valueOf(' '), "P");
/*      */     
/*      */ 
/*   87 */     DEFAULT_TAG_PREFIXES = new LinkedHashMap();
/*      */     
/*   89 */     DEFAULT_TAG_PREFIXES.put("!", "!");
/*   90 */     DEFAULT_TAG_PREFIXES.put("tag:yaml.org,2002:", "!!");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private final Writer stream;
/*      */   
/*      */ 
/*      */   private final ArrayStack<EmitterState> states;
/*      */   
/*      */ 
/*      */   private EmitterState state;
/*      */   
/*      */ 
/*      */   private final Queue<Event> events;
/*      */   
/*      */ 
/*      */   private Event event;
/*      */   
/*      */   private final ArrayStack<Integer> indents;
/*      */   
/*      */   private Integer indent;
/*      */   
/*      */   private int flowLevel;
/*      */   
/*      */   private boolean rootContext;
/*      */   
/*      */   private boolean mappingContext;
/*      */   
/*      */   private boolean simpleKeyContext;
/*      */   
/*      */   private int column;
/*      */   
/*      */   private boolean whitespace;
/*      */   
/*      */   private boolean indention;
/*      */   
/*      */   private boolean openEnded;
/*      */   
/*      */   private Boolean canonical;
/*      */   
/*      */   private Boolean prettyFlow;
/*      */   
/*      */   private boolean allowUnicode;
/*      */   
/*      */   private int bestIndent;
/*      */   
/*      */   private int bestWidth;
/*      */   
/*      */   private char[] bestLineBreak;
/*      */   
/*      */   private boolean splitLines;
/*      */   
/*      */   private Map<String, String> tagPrefixes;
/*      */   
/*      */   private String preparedAnchor;
/*      */   
/*      */   private String preparedTag;
/*      */   
/*      */   private ScalarAnalysis analysis;
/*      */   
/*      */   private Character style;
/*      */   
/*      */   public Emitter(Writer stream, DumperOptions opts)
/*      */   {
/*  155 */     this.stream = stream;
/*      */     
/*      */ 
/*  158 */     this.states = new ArrayStack(100);
/*  159 */     this.state = new ExpectStreamStart(null);
/*      */     
/*  161 */     this.events = new ArrayBlockingQueue(100);
/*  162 */     this.event = null;
/*      */     
/*  164 */     this.indents = new ArrayStack(10);
/*  165 */     this.indent = null;
/*      */     
/*  167 */     this.flowLevel = 0;
/*      */     
/*  169 */     this.mappingContext = false;
/*  170 */     this.simpleKeyContext = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  178 */     this.column = 0;
/*  179 */     this.whitespace = true;
/*  180 */     this.indention = true;
/*      */     
/*      */ 
/*  183 */     this.openEnded = false;
/*      */     
/*      */ 
/*  186 */     this.canonical = Boolean.valueOf(opts.isCanonical());
/*  187 */     this.prettyFlow = Boolean.valueOf(opts.isPrettyFlow());
/*  188 */     this.allowUnicode = opts.isAllowUnicode();
/*  189 */     this.bestIndent = 2;
/*  190 */     if ((opts.getIndent() > 1) && (opts.getIndent() < 10)) {
/*  191 */       this.bestIndent = opts.getIndent();
/*      */     }
/*  193 */     this.bestWidth = 80;
/*  194 */     if (opts.getWidth() > this.bestIndent * 2) {
/*  195 */       this.bestWidth = opts.getWidth();
/*      */     }
/*  197 */     this.bestLineBreak = opts.getLineBreak().getString().toCharArray();
/*  198 */     this.splitLines = opts.getSplitLines();
/*      */     
/*      */ 
/*  201 */     this.tagPrefixes = new LinkedHashMap();
/*      */     
/*      */ 
/*  204 */     this.preparedAnchor = null;
/*  205 */     this.preparedTag = null;
/*      */     
/*      */ 
/*  208 */     this.analysis = null;
/*  209 */     this.style = null;
/*      */   }
/*      */   
/*      */   public void emit(Event event) throws IOException {
/*  213 */     this.events.add(event);
/*  214 */     while (!needMoreEvents()) {
/*  215 */       this.event = ((Event)this.events.poll());
/*  216 */       this.state.expect();
/*  217 */       this.event = null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean needMoreEvents()
/*      */   {
/*  224 */     if (this.events.isEmpty()) {
/*  225 */       return true;
/*      */     }
/*  227 */     Event event = (Event)this.events.peek();
/*  228 */     if ((event instanceof DocumentStartEvent))
/*  229 */       return needEvents(1);
/*  230 */     if ((event instanceof SequenceStartEvent))
/*  231 */       return needEvents(2);
/*  232 */     if ((event instanceof MappingStartEvent)) {
/*  233 */       return needEvents(3);
/*      */     }
/*  235 */     return false;
/*      */   }
/*      */   
/*      */   private boolean needEvents(int count)
/*      */   {
/*  240 */     int level = 0;
/*  241 */     Iterator<Event> iter = this.events.iterator();
/*  242 */     iter.next();
/*  243 */     while (iter.hasNext()) {
/*  244 */       Event event = (Event)iter.next();
/*  245 */       if (((event instanceof DocumentStartEvent)) || ((event instanceof CollectionStartEvent))) {
/*  246 */         level++;
/*  247 */       } else if (((event instanceof DocumentEndEvent)) || ((event instanceof CollectionEndEvent))) {
/*  248 */         level--;
/*  249 */       } else if ((event instanceof StreamEndEvent)) {
/*  250 */         level = -1;
/*      */       }
/*  252 */       if (level < 0) {
/*  253 */         return false;
/*      */       }
/*      */     }
/*  256 */     return this.events.size() < count + 1;
/*      */   }
/*      */   
/*      */   private void increaseIndent(boolean flow, boolean indentless) {
/*  260 */     this.indents.push(this.indent);
/*  261 */     if (this.indent == null) {
/*  262 */       if (flow) {
/*  263 */         this.indent = Integer.valueOf(this.bestIndent);
/*      */       } else {
/*  265 */         this.indent = Integer.valueOf(0);
/*      */       }
/*  267 */     } else if (!indentless) {
/*  268 */       Emitter localEmitter = this;(localEmitter.indent = Integer.valueOf(localEmitter.indent.intValue() + this.bestIndent));
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectStreamStart implements EmitterState
/*      */   {
/*      */     private ExpectStreamStart() {}
/*      */     
/*      */     public void expect() throws IOException
/*      */     {
/*  278 */       if ((Emitter.this.event instanceof StreamStartEvent)) {
/*  279 */         Emitter.this.writeStreamStart();
/*  280 */         Emitter.this.state = new Emitter.ExpectFirstDocumentStart(Emitter.this, null);
/*      */       } else {
/*  282 */         throw new EmitterException("expected StreamStartEvent, but got " + Emitter.this.event);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectNothing implements EmitterState { private ExpectNothing() {}
/*      */     
/*  289 */     public void expect() throws IOException { throw new EmitterException("expecting nothing, but got " + Emitter.this.event); }
/*      */   }
/*      */   
/*      */   private class ExpectFirstDocumentStart implements EmitterState
/*      */   {
/*      */     private ExpectFirstDocumentStart() {}
/*      */     
/*      */     public void expect() throws IOException {
/*  297 */       new Emitter.ExpectDocumentStart(Emitter.this, true).expect();
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectDocumentStart implements EmitterState {
/*      */     private boolean first;
/*      */     
/*      */     public ExpectDocumentStart(boolean first) {
/*  305 */       this.first = first;
/*      */     }
/*      */     
/*      */     public void expect() throws IOException {
/*  309 */       if ((Emitter.this.event instanceof DocumentStartEvent)) {
/*  310 */         DocumentStartEvent ev = (DocumentStartEvent)Emitter.this.event;
/*  311 */         if (((ev.getVersion() != null) || (ev.getTags() != null)) && (Emitter.this.openEnded)) {
/*  312 */           Emitter.this.writeIndicator("...", true, false, false);
/*  313 */           Emitter.this.writeIndent();
/*      */         }
/*  315 */         if (ev.getVersion() != null) {
/*  316 */           String versionText = Emitter.this.prepareVersion(ev.getVersion());
/*  317 */           Emitter.this.writeVersionDirective(versionText);
/*      */         }
/*  319 */         Emitter.this.tagPrefixes = new LinkedHashMap(Emitter.DEFAULT_TAG_PREFIXES);
/*  320 */         if (ev.getTags() != null) {
/*  321 */           Set<String> handles = new TreeSet(ev.getTags().keySet());
/*  322 */           for (String handle : handles) {
/*  323 */             String prefix = (String)ev.getTags().get(handle);
/*  324 */             Emitter.this.tagPrefixes.put(prefix, handle);
/*  325 */             String handleText = Emitter.this.prepareTagHandle(handle);
/*  326 */             String prefixText = Emitter.this.prepareTagPrefix(prefix);
/*  327 */             Emitter.this.writeTagDirective(handleText, prefixText);
/*      */           }
/*      */         }
/*  330 */         boolean implicit = (this.first) && (!ev.getExplicit()) && (!Emitter.this.canonical.booleanValue()) && (ev.getVersion() == null) && ((ev.getTags() == null) || (ev.getTags().isEmpty())) && (!Emitter.this.checkEmptyDocument());
/*      */         
/*      */ 
/*      */ 
/*  334 */         if (!implicit) {
/*  335 */           Emitter.this.writeIndent();
/*  336 */           Emitter.this.writeIndicator("---", true, false, false);
/*  337 */           if (Emitter.this.canonical.booleanValue()) {
/*  338 */             Emitter.this.writeIndent();
/*      */           }
/*      */         }
/*  341 */         Emitter.this.state = new Emitter.ExpectDocumentRoot(Emitter.this, null);
/*  342 */       } else if ((Emitter.this.event instanceof StreamEndEvent))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  348 */         Emitter.this.writeStreamEnd();
/*  349 */         Emitter.this.state = new Emitter.ExpectNothing(Emitter.this, null);
/*      */       } else {
/*  351 */         throw new EmitterException("expected DocumentStartEvent, but got " + Emitter.this.event);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectDocumentEnd implements EmitterState { private ExpectDocumentEnd() {}
/*      */     
/*  358 */     public void expect() throws IOException { if ((Emitter.this.event instanceof DocumentEndEvent)) {
/*  359 */         Emitter.this.writeIndent();
/*  360 */         if (((DocumentEndEvent)Emitter.this.event).getExplicit()) {
/*  361 */           Emitter.this.writeIndicator("...", true, false, false);
/*  362 */           Emitter.this.writeIndent();
/*      */         }
/*  364 */         Emitter.this.flushStream();
/*  365 */         Emitter.this.state = new Emitter.ExpectDocumentStart(Emitter.this, false);
/*      */       } else {
/*  367 */         throw new EmitterException("expected DocumentEndEvent, but got " + Emitter.this.event);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectDocumentRoot implements EmitterState { private ExpectDocumentRoot() {}
/*      */     
/*  374 */     public void expect() throws IOException { Emitter.this.states.push(new Emitter.ExpectDocumentEnd(Emitter.this, null));
/*  375 */       Emitter.this.expectNode(true, false, false);
/*      */     }
/*      */   }
/*      */   
/*      */   private void expectNode(boolean root, boolean mapping, boolean simpleKey)
/*      */     throws IOException
/*      */   {
/*  382 */     this.rootContext = root;
/*  383 */     this.mappingContext = mapping;
/*  384 */     this.simpleKeyContext = simpleKey;
/*  385 */     if ((this.event instanceof AliasEvent)) {
/*  386 */       expectAlias();
/*  387 */     } else if (((this.event instanceof ScalarEvent)) || ((this.event instanceof CollectionStartEvent))) {
/*  388 */       processAnchor("&");
/*  389 */       processTag();
/*  390 */       if ((this.event instanceof ScalarEvent)) {
/*  391 */         expectScalar();
/*  392 */       } else if ((this.event instanceof SequenceStartEvent)) {
/*  393 */         if ((this.flowLevel != 0) || (this.canonical.booleanValue()) || (((SequenceStartEvent)this.event).getFlowStyle().booleanValue()) || (checkEmptySequence()))
/*      */         {
/*  395 */           expectFlowSequence();
/*      */         } else {
/*  397 */           expectBlockSequence();
/*      */         }
/*      */       }
/*  400 */       else if ((this.flowLevel != 0) || (this.canonical.booleanValue()) || (((MappingStartEvent)this.event).getFlowStyle().booleanValue()) || (checkEmptyMapping()))
/*      */       {
/*  402 */         expectFlowMapping();
/*      */       } else {
/*  404 */         expectBlockMapping();
/*      */       }
/*      */     }
/*      */     else {
/*  408 */       throw new EmitterException("expected NodeEvent, but got " + this.event);
/*      */     }
/*      */   }
/*      */   
/*      */   private void expectAlias() throws IOException {
/*  413 */     if (((NodeEvent)this.event).getAnchor() == null) {
/*  414 */       throw new EmitterException("anchor is not specified for alias");
/*      */     }
/*  416 */     processAnchor("*");
/*  417 */     this.state = ((EmitterState)this.states.pop());
/*      */   }
/*      */   
/*      */   private void expectScalar() throws IOException {
/*  421 */     increaseIndent(true, false);
/*  422 */     processScalar();
/*  423 */     this.indent = ((Integer)this.indents.pop());
/*  424 */     this.state = ((EmitterState)this.states.pop());
/*      */   }
/*      */   
/*      */   private void expectFlowSequence()
/*      */     throws IOException
/*      */   {
/*  430 */     writeIndicator("[", true, true, false);
/*  431 */     this.flowLevel += 1;
/*  432 */     increaseIndent(true, false);
/*  433 */     if (this.prettyFlow.booleanValue()) {
/*  434 */       writeIndent();
/*      */     }
/*  436 */     this.state = new ExpectFirstFlowSequenceItem(null);
/*      */   }
/*      */   
/*      */   private class ExpectFirstFlowSequenceItem implements EmitterState { private ExpectFirstFlowSequenceItem() {}
/*      */     
/*  441 */     public void expect() throws IOException { if ((Emitter.this.event instanceof SequenceEndEvent)) {
/*  442 */         Emitter.this.indent = ((Integer)Emitter.this.indents.pop());
/*  443 */         Emitter.access$2010(Emitter.this);
/*  444 */         Emitter.this.writeIndicator("]", false, false, false);
/*  445 */         Emitter.this.state = ((EmitterState)Emitter.this.states.pop());
/*      */       } else {
/*  447 */         if ((Emitter.this.canonical.booleanValue()) || ((Emitter.this.column > Emitter.this.bestWidth) && (Emitter.this.splitLines)) || (Emitter.this.prettyFlow.booleanValue())) {
/*  448 */           Emitter.this.writeIndent();
/*      */         }
/*  450 */         Emitter.this.states.push(new Emitter.ExpectFlowSequenceItem(Emitter.this, null));
/*  451 */         Emitter.this.expectNode(false, false, false);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectFlowSequenceItem implements EmitterState { private ExpectFlowSequenceItem() {}
/*      */     
/*  458 */     public void expect() throws IOException { if ((Emitter.this.event instanceof SequenceEndEvent)) {
/*  459 */         Emitter.this.indent = ((Integer)Emitter.this.indents.pop());
/*  460 */         Emitter.access$2010(Emitter.this);
/*  461 */         if (Emitter.this.canonical.booleanValue()) {
/*  462 */           Emitter.this.writeIndicator(",", false, false, false);
/*  463 */           Emitter.this.writeIndent();
/*      */         }
/*  465 */         Emitter.this.writeIndicator("]", false, false, false);
/*  466 */         if (Emitter.this.prettyFlow.booleanValue()) {
/*  467 */           Emitter.this.writeIndent();
/*      */         }
/*  469 */         Emitter.this.state = ((EmitterState)Emitter.this.states.pop());
/*      */       } else {
/*  471 */         Emitter.this.writeIndicator(",", false, false, false);
/*  472 */         if ((Emitter.this.canonical.booleanValue()) || ((Emitter.this.column > Emitter.this.bestWidth) && (Emitter.this.splitLines)) || (Emitter.this.prettyFlow.booleanValue())) {
/*  473 */           Emitter.this.writeIndent();
/*      */         }
/*  475 */         Emitter.this.states.push(new ExpectFlowSequenceItem(Emitter.this));
/*  476 */         Emitter.this.expectNode(false, false, false);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void expectFlowMapping()
/*      */     throws IOException
/*      */   {
/*  484 */     writeIndicator("{", true, true, false);
/*  485 */     this.flowLevel += 1;
/*  486 */     increaseIndent(true, false);
/*  487 */     if (this.prettyFlow.booleanValue()) {
/*  488 */       writeIndent();
/*      */     }
/*  490 */     this.state = new ExpectFirstFlowMappingKey(null);
/*      */   }
/*      */   
/*      */   private class ExpectFirstFlowMappingKey implements EmitterState { private ExpectFirstFlowMappingKey() {}
/*      */     
/*  495 */     public void expect() throws IOException { if ((Emitter.this.event instanceof MappingEndEvent)) {
/*  496 */         Emitter.this.indent = ((Integer)Emitter.this.indents.pop());
/*  497 */         Emitter.access$2010(Emitter.this);
/*  498 */         Emitter.this.writeIndicator("}", false, false, false);
/*  499 */         Emitter.this.state = ((EmitterState)Emitter.this.states.pop());
/*      */       } else {
/*  501 */         if ((Emitter.this.canonical.booleanValue()) || ((Emitter.this.column > Emitter.this.bestWidth) && (Emitter.this.splitLines)) || (Emitter.this.prettyFlow.booleanValue())) {
/*  502 */           Emitter.this.writeIndent();
/*      */         }
/*  504 */         if ((!Emitter.this.canonical.booleanValue()) && (Emitter.this.checkSimpleKey())) {
/*  505 */           Emitter.this.states.push(new Emitter.ExpectFlowMappingSimpleValue(Emitter.this, null));
/*  506 */           Emitter.this.expectNode(false, true, true);
/*      */         } else {
/*  508 */           Emitter.this.writeIndicator("?", true, false, false);
/*  509 */           Emitter.this.states.push(new Emitter.ExpectFlowMappingValue(Emitter.this, null));
/*  510 */           Emitter.this.expectNode(false, true, false);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectFlowMappingKey implements EmitterState { private ExpectFlowMappingKey() {}
/*      */     
/*  518 */     public void expect() throws IOException { if ((Emitter.this.event instanceof MappingEndEvent)) {
/*  519 */         Emitter.this.indent = ((Integer)Emitter.this.indents.pop());
/*  520 */         Emitter.access$2010(Emitter.this);
/*  521 */         if (Emitter.this.canonical.booleanValue()) {
/*  522 */           Emitter.this.writeIndicator(",", false, false, false);
/*  523 */           Emitter.this.writeIndent();
/*      */         }
/*  525 */         if (Emitter.this.prettyFlow.booleanValue()) {
/*  526 */           Emitter.this.writeIndent();
/*      */         }
/*  528 */         Emitter.this.writeIndicator("}", false, false, false);
/*  529 */         Emitter.this.state = ((EmitterState)Emitter.this.states.pop());
/*      */       } else {
/*  531 */         Emitter.this.writeIndicator(",", false, false, false);
/*  532 */         if ((Emitter.this.canonical.booleanValue()) || ((Emitter.this.column > Emitter.this.bestWidth) && (Emitter.this.splitLines)) || (Emitter.this.prettyFlow.booleanValue())) {
/*  533 */           Emitter.this.writeIndent();
/*      */         }
/*  535 */         if ((!Emitter.this.canonical.booleanValue()) && (Emitter.this.checkSimpleKey())) {
/*  536 */           Emitter.this.states.push(new Emitter.ExpectFlowMappingSimpleValue(Emitter.this, null));
/*  537 */           Emitter.this.expectNode(false, true, true);
/*      */         } else {
/*  539 */           Emitter.this.writeIndicator("?", true, false, false);
/*  540 */           Emitter.this.states.push(new Emitter.ExpectFlowMappingValue(Emitter.this, null));
/*  541 */           Emitter.this.expectNode(false, true, false);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectFlowMappingSimpleValue implements EmitterState { private ExpectFlowMappingSimpleValue() {}
/*      */     
/*  549 */     public void expect() throws IOException { Emitter.this.writeIndicator(":", false, false, false);
/*  550 */       Emitter.this.states.push(new Emitter.ExpectFlowMappingKey(Emitter.this, null));
/*  551 */       Emitter.this.expectNode(false, true, false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectFlowMappingValue implements EmitterState { private ExpectFlowMappingValue() {}
/*      */     
/*  557 */     public void expect() throws IOException { if ((Emitter.this.canonical.booleanValue()) || (Emitter.this.column > Emitter.this.bestWidth) || (Emitter.this.prettyFlow.booleanValue())) {
/*  558 */         Emitter.this.writeIndent();
/*      */       }
/*  560 */       Emitter.this.writeIndicator(":", true, false, false);
/*  561 */       Emitter.this.states.push(new Emitter.ExpectFlowMappingKey(Emitter.this, null));
/*  562 */       Emitter.this.expectNode(false, true, false);
/*      */     }
/*      */   }
/*      */   
/*      */   private void expectBlockSequence()
/*      */     throws IOException
/*      */   {
/*  569 */     boolean indentless = (this.mappingContext) && (!this.indention);
/*  570 */     increaseIndent(false, indentless);
/*  571 */     this.state = new ExpectFirstBlockSequenceItem(null);
/*      */   }
/*      */   
/*      */   private class ExpectFirstBlockSequenceItem implements EmitterState { private ExpectFirstBlockSequenceItem() {}
/*      */     
/*  576 */     public void expect() throws IOException { new Emitter.ExpectBlockSequenceItem(Emitter.this, true).expect(); }
/*      */   }
/*      */   
/*      */   private class ExpectBlockSequenceItem implements EmitterState
/*      */   {
/*      */     private boolean first;
/*      */     
/*      */     public ExpectBlockSequenceItem(boolean first) {
/*  584 */       this.first = first;
/*      */     }
/*      */     
/*      */     public void expect() throws IOException {
/*  588 */       if ((!this.first) && ((Emitter.this.event instanceof SequenceEndEvent))) {
/*  589 */         Emitter.this.indent = ((Integer)Emitter.this.indents.pop());
/*  590 */         Emitter.this.state = ((EmitterState)Emitter.this.states.pop());
/*      */       } else {
/*  592 */         Emitter.this.writeIndent();
/*  593 */         Emitter.this.writeIndicator("-", true, false, true);
/*  594 */         Emitter.this.states.push(new ExpectBlockSequenceItem(Emitter.this, false));
/*  595 */         Emitter.this.expectNode(false, false, false);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void expectBlockMapping() throws IOException
/*      */   {
/*  602 */     increaseIndent(false, false);
/*  603 */     this.state = new ExpectFirstBlockMappingKey(null);
/*      */   }
/*      */   
/*      */   private class ExpectFirstBlockMappingKey implements EmitterState { private ExpectFirstBlockMappingKey() {}
/*      */     
/*  608 */     public void expect() throws IOException { new Emitter.ExpectBlockMappingKey(Emitter.this, true).expect(); }
/*      */   }
/*      */   
/*      */   private class ExpectBlockMappingKey implements EmitterState
/*      */   {
/*      */     private boolean first;
/*      */     
/*      */     public ExpectBlockMappingKey(boolean first) {
/*  616 */       this.first = first;
/*      */     }
/*      */     
/*      */     public void expect() throws IOException {
/*  620 */       if ((!this.first) && ((Emitter.this.event instanceof MappingEndEvent))) {
/*  621 */         Emitter.this.indent = ((Integer)Emitter.this.indents.pop());
/*  622 */         Emitter.this.state = ((EmitterState)Emitter.this.states.pop());
/*      */       } else {
/*  624 */         Emitter.this.writeIndent();
/*  625 */         if (Emitter.this.checkSimpleKey()) {
/*  626 */           Emitter.this.states.push(new Emitter.ExpectBlockMappingSimpleValue(Emitter.this, null));
/*  627 */           Emitter.this.expectNode(false, true, true);
/*      */         } else {
/*  629 */           Emitter.this.writeIndicator("?", true, false, true);
/*  630 */           Emitter.this.states.push(new Emitter.ExpectBlockMappingValue(Emitter.this, null));
/*  631 */           Emitter.this.expectNode(false, true, false);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectBlockMappingSimpleValue implements EmitterState { private ExpectBlockMappingSimpleValue() {}
/*      */     
/*  639 */     public void expect() throws IOException { Emitter.this.writeIndicator(":", false, false, false);
/*  640 */       Emitter.this.states.push(new Emitter.ExpectBlockMappingKey(Emitter.this, false));
/*  641 */       Emitter.this.expectNode(false, true, false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExpectBlockMappingValue implements EmitterState { private ExpectBlockMappingValue() {}
/*      */     
/*  647 */     public void expect() throws IOException { Emitter.this.writeIndent();
/*  648 */       Emitter.this.writeIndicator(":", true, false, true);
/*  649 */       Emitter.this.states.push(new Emitter.ExpectBlockMappingKey(Emitter.this, false));
/*  650 */       Emitter.this.expectNode(false, true, false);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean checkEmptySequence()
/*      */   {
/*  657 */     return ((this.event instanceof SequenceStartEvent)) && (!this.events.isEmpty()) && ((this.events.peek() instanceof SequenceEndEvent));
/*      */   }
/*      */   
/*      */   private boolean checkEmptyMapping() {
/*  661 */     return ((this.event instanceof MappingStartEvent)) && (!this.events.isEmpty()) && ((this.events.peek() instanceof MappingEndEvent));
/*      */   }
/*      */   
/*      */   private boolean checkEmptyDocument() {
/*  665 */     if ((!(this.event instanceof DocumentStartEvent)) || (this.events.isEmpty())) {
/*  666 */       return false;
/*      */     }
/*  668 */     Event event = (Event)this.events.peek();
/*  669 */     if ((event instanceof ScalarEvent)) {
/*  670 */       ScalarEvent e = (ScalarEvent)event;
/*  671 */       return (e.getAnchor() == null) && (e.getTag() == null) && (e.getImplicit() != null) && (e.getValue().length() == 0);
/*      */     }
/*      */     
/*  674 */     return false;
/*      */   }
/*      */   
/*      */   private boolean checkSimpleKey() {
/*  678 */     int length = 0;
/*  679 */     if (((this.event instanceof NodeEvent)) && (((NodeEvent)this.event).getAnchor() != null)) {
/*  680 */       if (this.preparedAnchor == null) {
/*  681 */         this.preparedAnchor = prepareAnchor(((NodeEvent)this.event).getAnchor());
/*      */       }
/*  683 */       length += this.preparedAnchor.length();
/*      */     }
/*  685 */     String tag = null;
/*  686 */     if ((this.event instanceof ScalarEvent)) {
/*  687 */       tag = ((ScalarEvent)this.event).getTag();
/*  688 */     } else if ((this.event instanceof CollectionStartEvent)) {
/*  689 */       tag = ((CollectionStartEvent)this.event).getTag();
/*      */     }
/*  691 */     if (tag != null) {
/*  692 */       if (this.preparedTag == null) {
/*  693 */         this.preparedTag = prepareTag(tag);
/*      */       }
/*  695 */       length += this.preparedTag.length();
/*      */     }
/*  697 */     if ((this.event instanceof ScalarEvent)) {
/*  698 */       if (this.analysis == null) {
/*  699 */         this.analysis = analyzeScalar(((ScalarEvent)this.event).getValue());
/*      */       }
/*  701 */       length += this.analysis.scalar.length();
/*      */     }
/*  703 */     return (length < 128) && (((this.event instanceof AliasEvent)) || (((this.event instanceof ScalarEvent)) && (!this.analysis.empty) && (!this.analysis.multiline)) || (checkEmptySequence()) || (checkEmptyMapping()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void processAnchor(String indicator)
/*      */     throws IOException
/*      */   {
/*  711 */     NodeEvent ev = (NodeEvent)this.event;
/*  712 */     if (ev.getAnchor() == null) {
/*  713 */       this.preparedAnchor = null;
/*  714 */       return;
/*      */     }
/*  716 */     if (this.preparedAnchor == null) {
/*  717 */       this.preparedAnchor = prepareAnchor(ev.getAnchor());
/*      */     }
/*  719 */     writeIndicator(indicator + this.preparedAnchor, true, false, false);
/*  720 */     this.preparedAnchor = null;
/*      */   }
/*      */   
/*      */   private void processTag() throws IOException {
/*  724 */     String tag = null;
/*  725 */     if ((this.event instanceof ScalarEvent)) {
/*  726 */       ScalarEvent ev = (ScalarEvent)this.event;
/*  727 */       tag = ev.getTag();
/*  728 */       if (this.style == null) {
/*  729 */         this.style = chooseScalarStyle();
/*      */       }
/*  731 */       if (((!this.canonical.booleanValue()) || (tag == null)) && (((this.style == null) && (ev.getImplicit().canOmitTagInPlainScalar())) || ((this.style != null) && (ev.getImplicit().canOmitTagInNonPlainScalar()))))
/*      */       {
/*      */ 
/*  734 */         this.preparedTag = null;
/*  735 */         return;
/*      */       }
/*  737 */       if ((ev.getImplicit().canOmitTagInPlainScalar()) && (tag == null)) {
/*  738 */         tag = "!";
/*  739 */         this.preparedTag = null;
/*      */       }
/*      */     } else {
/*  742 */       CollectionStartEvent ev = (CollectionStartEvent)this.event;
/*  743 */       tag = ev.getTag();
/*  744 */       if (((!this.canonical.booleanValue()) || (tag == null)) && (ev.getImplicit())) {
/*  745 */         this.preparedTag = null;
/*  746 */         return;
/*      */       }
/*      */     }
/*  749 */     if (tag == null) {
/*  750 */       throw new EmitterException("tag is not specified");
/*      */     }
/*  752 */     if (this.preparedTag == null) {
/*  753 */       this.preparedTag = prepareTag(tag);
/*      */     }
/*  755 */     writeIndicator(this.preparedTag, true, false, false);
/*  756 */     this.preparedTag = null;
/*      */   }
/*      */   
/*      */   private Character chooseScalarStyle() {
/*  760 */     ScalarEvent ev = (ScalarEvent)this.event;
/*  761 */     if (this.analysis == null) {
/*  762 */       this.analysis = analyzeScalar(ev.getValue());
/*      */     }
/*  764 */     if (((ev.getStyle() != null) && (ev.getStyle().charValue() == '"')) || (this.canonical.booleanValue())) {
/*  765 */       return Character.valueOf('"');
/*      */     }
/*  767 */     if ((ev.getStyle() == null) && (ev.getImplicit().canOmitTagInPlainScalar()) && 
/*  768 */       ((!this.simpleKeyContext) || ((!this.analysis.empty) && (!this.analysis.multiline))) && (((this.flowLevel != 0) && (this.analysis.allowFlowPlain)) || ((this.flowLevel == 0) && (this.analysis.allowBlockPlain))))
/*      */     {
/*  770 */       return null;
/*      */     }
/*      */     
/*  773 */     if ((ev.getStyle() != null) && ((ev.getStyle().charValue() == '|') || (ev.getStyle().charValue() == '>')) && 
/*  774 */       (this.flowLevel == 0) && (!this.simpleKeyContext) && (this.analysis.allowBlock)) {
/*  775 */       return ev.getStyle();
/*      */     }
/*      */     
/*  778 */     if (((ev.getStyle() == null) || (ev.getStyle().charValue() == '\'')) && 
/*  779 */       (this.analysis.allowSingleQuoted) && ((!this.simpleKeyContext) || (!this.analysis.multiline))) {
/*  780 */       return Character.valueOf('\'');
/*      */     }
/*      */     
/*  783 */     return Character.valueOf('"');
/*      */   }
/*      */   
/*      */   private void processScalar() throws IOException {
/*  787 */     ScalarEvent ev = (ScalarEvent)this.event;
/*  788 */     if (this.analysis == null) {
/*  789 */       this.analysis = analyzeScalar(ev.getValue());
/*      */     }
/*  791 */     if (this.style == null) {
/*  792 */       this.style = chooseScalarStyle();
/*      */     }
/*  794 */     boolean split = (!this.simpleKeyContext) && (this.splitLines);
/*  795 */     if (this.style == null) {
/*  796 */       writePlain(this.analysis.scalar, split);
/*      */     } else {
/*  798 */       switch (this.style.charValue()) {
/*      */       case '"': 
/*  800 */         writeDoubleQuoted(this.analysis.scalar, split);
/*  801 */         break;
/*      */       case '\'': 
/*  803 */         writeSingleQuoted(this.analysis.scalar, split);
/*  804 */         break;
/*      */       case '>': 
/*  806 */         writeFolded(this.analysis.scalar, split);
/*  807 */         break;
/*      */       case '|': 
/*  809 */         writeLiteral(this.analysis.scalar);
/*  810 */         break;
/*      */       default: 
/*  812 */         throw new YAMLException("Unexpected style: " + this.style);
/*      */       }
/*      */     }
/*  815 */     this.analysis = null;
/*  816 */     this.style = null;
/*      */   }
/*      */   
/*      */ 
/*      */   private String prepareVersion(DumperOptions.Version version)
/*      */   {
/*  822 */     if (version.major() != 1) {
/*  823 */       throw new EmitterException("unsupported YAML version: " + version);
/*      */     }
/*  825 */     return version.getRepresentation();
/*      */   }
/*      */   
/*  828 */   private static final Pattern HANDLE_FORMAT = Pattern.compile("^![-_\\w]*!$");
/*      */   
/*      */   private String prepareTagHandle(String handle) {
/*  831 */     if (handle.length() == 0)
/*  832 */       throw new EmitterException("tag handle must not be empty");
/*  833 */     if ((handle.charAt(0) != '!') || (handle.charAt(handle.length() - 1) != '!'))
/*  834 */       throw new EmitterException("tag handle must start and end with '!': " + handle);
/*  835 */     if ((!"!".equals(handle)) && (!HANDLE_FORMAT.matcher(handle).matches())) {
/*  836 */       throw new EmitterException("invalid character in the tag handle: " + handle);
/*      */     }
/*  838 */     return handle;
/*      */   }
/*      */   
/*      */   private String prepareTagPrefix(String prefix) {
/*  842 */     if (prefix.length() == 0) {
/*  843 */       throw new EmitterException("tag prefix must not be empty");
/*      */     }
/*  845 */     StringBuilder chunks = new StringBuilder();
/*  846 */     int start = 0;
/*  847 */     int end = 0;
/*  848 */     if (prefix.charAt(0) == '!') {
/*  849 */       end = 1;
/*      */     }
/*  851 */     while (end < prefix.length()) {
/*  852 */       end++;
/*      */     }
/*  854 */     if (start < end) {
/*  855 */       chunks.append(prefix.substring(start, end));
/*      */     }
/*  857 */     return chunks.toString();
/*      */   }
/*      */   
/*      */   private String prepareTag(String tag) {
/*  861 */     if (tag.length() == 0) {
/*  862 */       throw new EmitterException("tag must not be empty");
/*      */     }
/*  864 */     if ("!".equals(tag)) {
/*  865 */       return tag;
/*      */     }
/*  867 */     String handle = null;
/*  868 */     String suffix = tag;
/*      */     
/*  870 */     for (String prefix : this.tagPrefixes.keySet()) {
/*  871 */       if ((tag.startsWith(prefix)) && (("!".equals(prefix)) || (prefix.length() < tag.length()))) {
/*  872 */         handle = prefix;
/*      */       }
/*      */     }
/*  875 */     if (handle != null) {
/*  876 */       suffix = tag.substring(handle.length());
/*  877 */       handle = (String)this.tagPrefixes.get(handle);
/*      */     }
/*      */     
/*  880 */     int end = suffix.length();
/*  881 */     String suffixText = end > 0 ? suffix.substring(0, end) : "";
/*      */     
/*  883 */     if (handle != null) {
/*  884 */       return handle + suffixText;
/*      */     }
/*  886 */     return "!<" + suffixText + ">";
/*      */   }
/*      */   
/*  889 */   private static final Pattern ANCHOR_FORMAT = Pattern.compile("^[-_\\w]*$");
/*      */   
/*      */   static String prepareAnchor(String anchor) {
/*  892 */     if (anchor.length() == 0) {
/*  893 */       throw new EmitterException("anchor must not be empty");
/*      */     }
/*  895 */     if (!ANCHOR_FORMAT.matcher(anchor).matches()) {
/*  896 */       throw new EmitterException("invalid character in the anchor: " + anchor);
/*      */     }
/*  898 */     return anchor;
/*      */   }
/*      */   
/*      */   private ScalarAnalysis analyzeScalar(String scalar)
/*      */   {
/*  903 */     if (scalar.length() == 0) {
/*  904 */       return new ScalarAnalysis(scalar, true, false, false, true, true, false);
/*      */     }
/*      */     
/*  907 */     boolean blockIndicators = false;
/*  908 */     boolean flowIndicators = false;
/*  909 */     boolean lineBreaks = false;
/*  910 */     boolean specialCharacters = false;
/*      */     
/*      */ 
/*  913 */     boolean leadingSpace = false;
/*  914 */     boolean leadingBreak = false;
/*  915 */     boolean trailingSpace = false;
/*  916 */     boolean trailingBreak = false;
/*  917 */     boolean breakSpace = false;
/*  918 */     boolean spaceBreak = false;
/*      */     
/*      */ 
/*  921 */     if ((scalar.startsWith("---")) || (scalar.startsWith("..."))) {
/*  922 */       blockIndicators = true;
/*  923 */       flowIndicators = true;
/*      */     }
/*      */     
/*  926 */     boolean preceededByWhitespace = true;
/*  927 */     boolean followedByWhitespace = (scalar.length() == 1) || (Constant.NULL_BL_T_LINEBR.has(scalar.charAt(1)));
/*      */     
/*  929 */     boolean previousSpace = false;
/*      */     
/*      */ 
/*  932 */     boolean previousBreak = false;
/*      */     
/*  934 */     int index = 0;
/*      */     
/*  936 */     while (index < scalar.length()) {
/*  937 */       char ch = scalar.charAt(index);
/*      */       
/*  939 */       if (index == 0)
/*      */       {
/*  941 */         if ("#,[]{}&*!|>'\"%@`".indexOf(ch) != -1) {
/*  942 */           flowIndicators = true;
/*  943 */           blockIndicators = true;
/*      */         }
/*  945 */         if ((ch == '?') || (ch == ':')) {
/*  946 */           flowIndicators = true;
/*  947 */           if (followedByWhitespace) {
/*  948 */             blockIndicators = true;
/*      */           }
/*      */         }
/*  951 */         if ((ch == '-') && (followedByWhitespace)) {
/*  952 */           flowIndicators = true;
/*  953 */           blockIndicators = true;
/*      */         }
/*      */       }
/*      */       else {
/*  957 */         if (",?[]{}".indexOf(ch) != -1) {
/*  958 */           flowIndicators = true;
/*      */         }
/*  960 */         if (ch == ':') {
/*  961 */           flowIndicators = true;
/*  962 */           if (followedByWhitespace) {
/*  963 */             blockIndicators = true;
/*      */           }
/*      */         }
/*  966 */         if ((ch == '#') && (preceededByWhitespace)) {
/*  967 */           flowIndicators = true;
/*  968 */           blockIndicators = true;
/*      */         }
/*      */       }
/*      */       
/*  972 */       boolean isLineBreak = Constant.LINEBR.has(ch);
/*  973 */       if (isLineBreak) {
/*  974 */         lineBreaks = true;
/*      */       }
/*  976 */       if ((ch != '\n') && ((' ' > ch) || (ch > '~'))) {
/*  977 */         if (((ch == '') || ((' ' <= ch) && (ch <= 55295)) || ((57344 <= ch) && (ch <= 65533))) && (ch != 65279))
/*      */         {
/*      */ 
/*  980 */           if (!this.allowUnicode) {
/*  981 */             specialCharacters = true;
/*      */           }
/*      */         } else {
/*  984 */           specialCharacters = true;
/*      */         }
/*      */       }
/*      */       
/*  988 */       if (ch == ' ') {
/*  989 */         if (index == 0) {
/*  990 */           leadingSpace = true;
/*      */         }
/*  992 */         if (index == scalar.length() - 1) {
/*  993 */           trailingSpace = true;
/*      */         }
/*  995 */         if (previousBreak) {
/*  996 */           breakSpace = true;
/*      */         }
/*  998 */         previousSpace = true;
/*  999 */         previousBreak = false;
/* 1000 */       } else if (isLineBreak) {
/* 1001 */         if (index == 0) {
/* 1002 */           leadingBreak = true;
/*      */         }
/* 1004 */         if (index == scalar.length() - 1) {
/* 1005 */           trailingBreak = true;
/*      */         }
/* 1007 */         if (previousSpace) {
/* 1008 */           spaceBreak = true;
/*      */         }
/* 1010 */         previousSpace = false;
/* 1011 */         previousBreak = true;
/*      */       } else {
/* 1013 */         previousSpace = false;
/* 1014 */         previousBreak = false;
/*      */       }
/*      */       
/*      */ 
/* 1018 */       index++;
/* 1019 */       preceededByWhitespace = (Constant.NULL_BL_T.has(ch)) || (isLineBreak);
/* 1020 */       followedByWhitespace = (index + 1 >= scalar.length()) || (Constant.NULL_BL_T.has(scalar.charAt(index + 1))) || (isLineBreak);
/*      */     }
/*      */     
/*      */ 
/* 1024 */     boolean allowFlowPlain = true;
/* 1025 */     boolean allowBlockPlain = true;
/* 1026 */     boolean allowSingleQuoted = true;
/* 1027 */     boolean allowBlock = true;
/*      */     
/* 1029 */     if ((leadingSpace) || (leadingBreak) || (trailingSpace) || (trailingBreak)) {
/* 1030 */       allowFlowPlain = allowBlockPlain = 0;
/*      */     }
/*      */     
/* 1033 */     if (trailingSpace) {
/* 1034 */       allowBlock = false;
/*      */     }
/*      */     
/*      */ 
/* 1038 */     if (breakSpace) {
/* 1039 */       allowFlowPlain = allowBlockPlain = allowSingleQuoted = 0;
/*      */     }
/*      */     
/*      */ 
/* 1043 */     if ((spaceBreak) || (specialCharacters)) {
/* 1044 */       allowFlowPlain = allowBlockPlain = allowSingleQuoted = allowBlock = 0;
/*      */     }
/*      */     
/*      */ 
/* 1048 */     if (lineBreaks) {
/* 1049 */       allowFlowPlain = false;
/*      */     }
/*      */     
/* 1052 */     if (flowIndicators) {
/* 1053 */       allowFlowPlain = false;
/*      */     }
/*      */     
/* 1056 */     if (blockIndicators) {
/* 1057 */       allowBlockPlain = false;
/*      */     }
/*      */     
/* 1060 */     return new ScalarAnalysis(scalar, false, lineBreaks, allowFlowPlain, allowBlockPlain, allowSingleQuoted, allowBlock);
/*      */   }
/*      */   
/*      */ 
/*      */   void flushStream()
/*      */     throws IOException
/*      */   {
/* 1067 */     this.stream.flush();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void writeStreamEnd()
/*      */     throws IOException
/*      */   {
/* 1075 */     flushStream();
/*      */   }
/*      */   
/*      */   void writeIndicator(String indicator, boolean needWhitespace, boolean whitespace, boolean indentation) throws IOException
/*      */   {
/* 1080 */     if ((!this.whitespace) && (needWhitespace)) {
/* 1081 */       this.column += 1;
/* 1082 */       this.stream.write(SPACE);
/*      */     }
/* 1084 */     this.whitespace = whitespace;
/* 1085 */     this.indention = ((this.indention) && (indentation));
/* 1086 */     this.column += indicator.length();
/* 1087 */     this.openEnded = false;
/* 1088 */     this.stream.write(indicator);
/*      */   }
/*      */   
/*      */   void writeIndent() throws IOException { int indent;
/*      */     int indent;
/* 1093 */     if (this.indent != null) {
/* 1094 */       indent = this.indent.intValue();
/*      */     } else {
/* 1096 */       indent = 0;
/*      */     }
/*      */     
/* 1099 */     if ((!this.indention) || (this.column > indent) || ((this.column == indent) && (!this.whitespace))) {
/* 1100 */       writeLineBreak(null);
/*      */     }
/*      */     
/* 1103 */     if (this.column < indent) {
/* 1104 */       this.whitespace = true;
/* 1105 */       char[] data = new char[indent - this.column];
/* 1106 */       for (int i = 0; i < data.length; i++) {
/* 1107 */         data[i] = ' ';
/*      */       }
/* 1109 */       this.column = indent;
/* 1110 */       this.stream.write(data);
/*      */     }
/*      */   }
/*      */   
/*      */   private void writeLineBreak(String data) throws IOException {
/* 1115 */     this.whitespace = true;
/* 1116 */     this.indention = true;
/* 1117 */     this.column = 0;
/* 1118 */     if (data == null) {
/* 1119 */       this.stream.write(this.bestLineBreak);
/*      */     } else {
/* 1121 */       this.stream.write(data);
/*      */     }
/*      */   }
/*      */   
/*      */   void writeVersionDirective(String versionText) throws IOException {
/* 1126 */     this.stream.write("%YAML ");
/* 1127 */     this.stream.write(versionText);
/* 1128 */     writeLineBreak(null);
/*      */   }
/*      */   
/*      */   void writeTagDirective(String handleText, String prefixText)
/*      */     throws IOException
/*      */   {
/* 1134 */     this.stream.write("%TAG ");
/* 1135 */     this.stream.write(handleText);
/* 1136 */     this.stream.write(SPACE);
/* 1137 */     this.stream.write(prefixText);
/* 1138 */     writeLineBreak(null);
/*      */   }
/*      */   
/*      */   private void writeSingleQuoted(String text, boolean split) throws IOException
/*      */   {
/* 1143 */     writeIndicator("'", true, false, false);
/* 1144 */     boolean spaces = false;
/* 1145 */     boolean breaks = false;
/* 1146 */     int start = 0;int end = 0;
/*      */     
/* 1148 */     while (end <= text.length()) {
/* 1149 */       char ch = '\000';
/* 1150 */       if (end < text.length()) {
/* 1151 */         ch = text.charAt(end);
/*      */       }
/* 1153 */       if (spaces) {
/* 1154 */         if ((ch == 0) || (ch != ' ')) {
/* 1155 */           if ((start + 1 == end) && (this.column > this.bestWidth) && (split) && (start != 0) && (end != text.length()))
/*      */           {
/* 1157 */             writeIndent();
/*      */           } else {
/* 1159 */             int len = end - start;
/* 1160 */             this.column += len;
/* 1161 */             this.stream.write(text, start, len);
/*      */           }
/* 1163 */           start = end;
/*      */         }
/* 1165 */       } else if (breaks) {
/* 1166 */         if ((ch == 0) || (Constant.LINEBR.hasNo(ch))) {
/* 1167 */           if (text.charAt(start) == '\n') {
/* 1168 */             writeLineBreak(null);
/*      */           }
/* 1170 */           String data = text.substring(start, end);
/* 1171 */           for (char br : data.toCharArray()) {
/* 1172 */             if (br == '\n') {
/* 1173 */               writeLineBreak(null);
/*      */             } else {
/* 1175 */               writeLineBreak(String.valueOf(br));
/*      */             }
/*      */           }
/* 1178 */           writeIndent();
/* 1179 */           start = end;
/*      */         }
/*      */       }
/* 1182 */       else if ((Constant.LINEBR.has(ch, "\000 '")) && 
/* 1183 */         (start < end)) {
/* 1184 */         int len = end - start;
/* 1185 */         this.column += len;
/* 1186 */         this.stream.write(text, start, len);
/* 1187 */         start = end;
/*      */       }
/*      */       
/*      */ 
/* 1191 */       if (ch == '\'') {
/* 1192 */         this.column += 2;
/* 1193 */         this.stream.write("''");
/* 1194 */         start = end + 1;
/*      */       }
/* 1196 */       if (ch != 0) {
/* 1197 */         spaces = ch == ' ';
/* 1198 */         breaks = Constant.LINEBR.has(ch);
/*      */       }
/* 1200 */       end++;
/*      */     }
/* 1202 */     writeIndicator("'", false, false, false);
/*      */   }
/*      */   
/*      */   private void writeDoubleQuoted(String text, boolean split) throws IOException {
/* 1206 */     writeIndicator("\"", true, false, false);
/* 1207 */     int start = 0;
/* 1208 */     int end = 0;
/* 1209 */     while (end <= text.length()) {
/* 1210 */       Character ch = null;
/* 1211 */       if (end < text.length()) {
/* 1212 */         ch = Character.valueOf(text.charAt(end));
/*      */       }
/* 1214 */       if ((ch == null) || ("\"\\  ﻿".indexOf(ch.charValue()) != -1) || (' ' > ch.charValue()) || (ch.charValue() > '~'))
/*      */       {
/* 1216 */         if (start < end) {
/* 1217 */           int len = end - start;
/* 1218 */           this.column += len;
/* 1219 */           this.stream.write(text, start, len);
/* 1220 */           start = end;
/*      */         }
/* 1222 */         if (ch != null) { String data;
/*      */           String data;
/* 1224 */           if (ESCAPE_REPLACEMENTS.containsKey(ch)) {
/* 1225 */             data = "\\" + (String)ESCAPE_REPLACEMENTS.get(ch); } else { String data;
/* 1226 */             if ((!this.allowUnicode) || (!StreamReader.isPrintable(ch.charValue())))
/*      */             {
/*      */               String data;
/* 1229 */               if (ch.charValue() <= 'ÿ') {
/* 1230 */                 String s = "0" + Integer.toString(ch.charValue(), 16);
/* 1231 */                 data = "\\x" + s.substring(s.length() - 2); } else { String data;
/* 1232 */                 if ((ch.charValue() >= 55296) && (ch.charValue() <= 56319)) { String data;
/* 1233 */                   if (end + 1 < text.length()) {
/* 1234 */                     Character ch2 = Character.valueOf(text.charAt(++end));
/* 1235 */                     String s = "000" + Long.toHexString(Character.toCodePoint(ch.charValue(), ch2.charValue()));
/* 1236 */                     data = "\\U" + s.substring(s.length() - 8);
/*      */                   } else {
/* 1238 */                     String s = "000" + Integer.toString(ch.charValue(), 16);
/* 1239 */                     data = "\\u" + s.substring(s.length() - 4);
/*      */                   }
/*      */                 } else {
/* 1242 */                   String s = "000" + Integer.toString(ch.charValue(), 16);
/* 1243 */                   data = "\\u" + s.substring(s.length() - 4);
/*      */                 }
/*      */               }
/* 1246 */             } else { data = String.valueOf(ch);
/*      */             } }
/* 1248 */           this.column += data.length();
/* 1249 */           this.stream.write(data);
/* 1250 */           start = end + 1;
/*      */         }
/*      */       }
/* 1253 */       if ((0 < end) && (end < text.length() - 1) && ((ch.charValue() == ' ') || (start >= end)) && (this.column + (end - start) > this.bestWidth) && (split)) {
/*      */         String data;
/*      */         String data;
/* 1256 */         if (start >= end) {
/* 1257 */           data = "\\";
/*      */         } else {
/* 1259 */           data = text.substring(start, end) + "\\";
/*      */         }
/* 1261 */         if (start < end) {
/* 1262 */           start = end;
/*      */         }
/* 1264 */         this.column += data.length();
/* 1265 */         this.stream.write(data);
/* 1266 */         writeIndent();
/* 1267 */         this.whitespace = false;
/* 1268 */         this.indention = false;
/* 1269 */         if (text.charAt(start) == ' ') {
/* 1270 */           data = "\\";
/* 1271 */           this.column += data.length();
/* 1272 */           this.stream.write(data);
/*      */         }
/*      */       }
/* 1275 */       end++;
/*      */     }
/* 1277 */     writeIndicator("\"", false, false, false);
/*      */   }
/*      */   
/*      */   private String determineBlockHints(String text) {
/* 1281 */     StringBuilder hints = new StringBuilder();
/* 1282 */     if (Constant.LINEBR.has(text.charAt(0), " ")) {
/* 1283 */       hints.append(this.bestIndent);
/*      */     }
/* 1285 */     char ch1 = text.charAt(text.length() - 1);
/* 1286 */     if (Constant.LINEBR.hasNo(ch1)) {
/* 1287 */       hints.append("-");
/* 1288 */     } else if ((text.length() == 1) || (Constant.LINEBR.has(text.charAt(text.length() - 2)))) {
/* 1289 */       hints.append("+");
/*      */     }
/* 1291 */     return hints.toString();
/*      */   }
/*      */   
/*      */   void writeFolded(String text, boolean split) throws IOException {
/* 1295 */     String hints = determineBlockHints(text);
/* 1296 */     writeIndicator(">" + hints, true, false, false);
/* 1297 */     if ((hints.length() > 0) && (hints.charAt(hints.length() - 1) == '+')) {
/* 1298 */       this.openEnded = true;
/*      */     }
/* 1300 */     writeLineBreak(null);
/* 1301 */     boolean leadingSpace = true;
/* 1302 */     boolean spaces = false;
/* 1303 */     boolean breaks = true;
/* 1304 */     int start = 0;int end = 0;
/* 1305 */     while (end <= text.length()) {
/* 1306 */       char ch = '\000';
/* 1307 */       if (end < text.length()) {
/* 1308 */         ch = text.charAt(end);
/*      */       }
/* 1310 */       if (breaks) {
/* 1311 */         if ((ch == 0) || (Constant.LINEBR.hasNo(ch))) {
/* 1312 */           if ((!leadingSpace) && (ch != 0) && (ch != ' ') && (text.charAt(start) == '\n')) {
/* 1313 */             writeLineBreak(null);
/*      */           }
/* 1315 */           leadingSpace = ch == ' ';
/* 1316 */           String data = text.substring(start, end);
/* 1317 */           for (char br : data.toCharArray()) {
/* 1318 */             if (br == '\n') {
/* 1319 */               writeLineBreak(null);
/*      */             } else {
/* 1321 */               writeLineBreak(String.valueOf(br));
/*      */             }
/*      */           }
/* 1324 */           if (ch != 0) {
/* 1325 */             writeIndent();
/*      */           }
/* 1327 */           start = end;
/*      */         }
/* 1329 */       } else if (spaces) {
/* 1330 */         if (ch != ' ') {
/* 1331 */           if ((start + 1 == end) && (this.column > this.bestWidth) && (split)) {
/* 1332 */             writeIndent();
/*      */           } else {
/* 1334 */             int len = end - start;
/* 1335 */             this.column += len;
/* 1336 */             this.stream.write(text, start, len);
/*      */           }
/* 1338 */           start = end;
/*      */         }
/*      */       }
/* 1341 */       else if (Constant.LINEBR.has(ch, "\000 ")) {
/* 1342 */         int len = end - start;
/* 1343 */         this.column += len;
/* 1344 */         this.stream.write(text, start, len);
/* 1345 */         if (ch == 0) {
/* 1346 */           writeLineBreak(null);
/*      */         }
/* 1348 */         start = end;
/*      */       }
/*      */       
/* 1351 */       if (ch != 0) {
/* 1352 */         breaks = Constant.LINEBR.has(ch);
/* 1353 */         spaces = ch == ' ';
/*      */       }
/* 1355 */       end++;
/*      */     }
/*      */   }
/*      */   
/*      */   void writeLiteral(String text) throws IOException {
/* 1360 */     String hints = determineBlockHints(text);
/* 1361 */     writeIndicator("|" + hints, true, false, false);
/* 1362 */     if ((hints.length() > 0) && (hints.charAt(hints.length() - 1) == '+')) {
/* 1363 */       this.openEnded = true;
/*      */     }
/* 1365 */     writeLineBreak(null);
/* 1366 */     boolean breaks = true;
/* 1367 */     int start = 0;int end = 0;
/* 1368 */     while (end <= text.length()) {
/* 1369 */       char ch = '\000';
/* 1370 */       if (end < text.length()) {
/* 1371 */         ch = text.charAt(end);
/*      */       }
/* 1373 */       if (breaks) {
/* 1374 */         if ((ch == 0) || (Constant.LINEBR.hasNo(ch))) {
/* 1375 */           String data = text.substring(start, end);
/* 1376 */           for (char br : data.toCharArray()) {
/* 1377 */             if (br == '\n') {
/* 1378 */               writeLineBreak(null);
/*      */             } else {
/* 1380 */               writeLineBreak(String.valueOf(br));
/*      */             }
/*      */           }
/* 1383 */           if (ch != 0) {
/* 1384 */             writeIndent();
/*      */           }
/* 1386 */           start = end;
/*      */         }
/*      */       }
/* 1389 */       else if ((ch == 0) || (Constant.LINEBR.has(ch))) {
/* 1390 */         this.stream.write(text, start, end - start);
/* 1391 */         if (ch == 0) {
/* 1392 */           writeLineBreak(null);
/*      */         }
/* 1394 */         start = end;
/*      */       }
/*      */       
/* 1397 */       if (ch != 0) {
/* 1398 */         breaks = Constant.LINEBR.has(ch);
/*      */       }
/* 1400 */       end++;
/*      */     }
/*      */   }
/*      */   
/*      */   void writePlain(String text, boolean split) throws IOException {
/* 1405 */     if (this.rootContext) {
/* 1406 */       this.openEnded = true;
/*      */     }
/* 1408 */     if (text.length() == 0) {
/* 1409 */       return;
/*      */     }
/* 1411 */     if (!this.whitespace) {
/* 1412 */       this.column += 1;
/* 1413 */       this.stream.write(SPACE);
/*      */     }
/* 1415 */     this.whitespace = false;
/* 1416 */     this.indention = false;
/* 1417 */     boolean spaces = false;
/* 1418 */     boolean breaks = false;
/* 1419 */     int start = 0;int end = 0;
/* 1420 */     while (end <= text.length()) {
/* 1421 */       char ch = '\000';
/* 1422 */       if (end < text.length()) {
/* 1423 */         ch = text.charAt(end);
/*      */       }
/* 1425 */       if (spaces) {
/* 1426 */         if (ch != ' ') {
/* 1427 */           if ((start + 1 == end) && (this.column > this.bestWidth) && (split)) {
/* 1428 */             writeIndent();
/* 1429 */             this.whitespace = false;
/* 1430 */             this.indention = false;
/*      */           } else {
/* 1432 */             int len = end - start;
/* 1433 */             this.column += len;
/* 1434 */             this.stream.write(text, start, len);
/*      */           }
/* 1436 */           start = end;
/*      */         }
/* 1438 */       } else if (breaks) {
/* 1439 */         if (Constant.LINEBR.hasNo(ch)) {
/* 1440 */           if (text.charAt(start) == '\n') {
/* 1441 */             writeLineBreak(null);
/*      */           }
/* 1443 */           String data = text.substring(start, end);
/* 1444 */           for (char br : data.toCharArray()) {
/* 1445 */             if (br == '\n') {
/* 1446 */               writeLineBreak(null);
/*      */             } else {
/* 1448 */               writeLineBreak(String.valueOf(br));
/*      */             }
/*      */           }
/* 1451 */           writeIndent();
/* 1452 */           this.whitespace = false;
/* 1453 */           this.indention = false;
/* 1454 */           start = end;
/*      */         }
/*      */       }
/* 1457 */       else if ((ch == 0) || (Constant.LINEBR.has(ch))) {
/* 1458 */         int len = end - start;
/* 1459 */         this.column += len;
/* 1460 */         this.stream.write(text, start, len);
/* 1461 */         start = end;
/*      */       }
/*      */       
/* 1464 */       if (ch != 0) {
/* 1465 */         spaces = ch == ' ';
/* 1466 */         breaks = Constant.LINEBR.has(ch);
/*      */       }
/* 1468 */       end++;
/*      */     }
/*      */   }
/*      */   
/*      */   void writeStreamStart() {}
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\emitter\Emitter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */