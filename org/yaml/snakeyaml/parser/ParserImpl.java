/*     */ package org.yaml.snakeyaml.parser;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.yaml.snakeyaml.DumperOptions.Version;
/*     */ import org.yaml.snakeyaml.error.Mark;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.events.AliasEvent;
/*     */ import org.yaml.snakeyaml.events.DocumentEndEvent;
/*     */ import org.yaml.snakeyaml.events.DocumentStartEvent;
/*     */ import org.yaml.snakeyaml.events.Event;
/*     */ import org.yaml.snakeyaml.events.Event.ID;
/*     */ import org.yaml.snakeyaml.events.ImplicitTuple;
/*     */ import org.yaml.snakeyaml.events.MappingEndEvent;
/*     */ import org.yaml.snakeyaml.events.MappingStartEvent;
/*     */ import org.yaml.snakeyaml.events.ScalarEvent;
/*     */ import org.yaml.snakeyaml.events.SequenceEndEvent;
/*     */ import org.yaml.snakeyaml.events.SequenceStartEvent;
/*     */ import org.yaml.snakeyaml.events.StreamEndEvent;
/*     */ import org.yaml.snakeyaml.events.StreamStartEvent;
/*     */ import org.yaml.snakeyaml.reader.StreamReader;
/*     */ import org.yaml.snakeyaml.scanner.Scanner;
/*     */ import org.yaml.snakeyaml.scanner.ScannerImpl;
/*     */ import org.yaml.snakeyaml.tokens.AliasToken;
/*     */ import org.yaml.snakeyaml.tokens.AnchorToken;
/*     */ import org.yaml.snakeyaml.tokens.BlockEntryToken;
/*     */ import org.yaml.snakeyaml.tokens.DirectiveToken;
/*     */ import org.yaml.snakeyaml.tokens.ScalarToken;
/*     */ import org.yaml.snakeyaml.tokens.StreamEndToken;
/*     */ import org.yaml.snakeyaml.tokens.StreamStartToken;
/*     */ import org.yaml.snakeyaml.tokens.TagToken;
/*     */ import org.yaml.snakeyaml.tokens.TagTuple;
/*     */ import org.yaml.snakeyaml.tokens.Token;
/*     */ import org.yaml.snakeyaml.tokens.Token.ID;
/*     */ import org.yaml.snakeyaml.util.ArrayStack;
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
/*     */ public final class ParserImpl
/*     */   implements Parser
/*     */ {
/* 117 */   private static final Map<String, String> DEFAULT_TAGS = new HashMap();
/*     */   
/* 119 */   static { DEFAULT_TAGS.put("!", "!");
/* 120 */     DEFAULT_TAGS.put("!!", "tag:yaml.org,2002:");
/*     */   }
/*     */   
/*     */ 
/*     */   private final Scanner scanner;
/*     */   
/*     */   private Event currentEvent;
/*     */   
/*     */   private final ArrayStack<Production> states;
/*     */   public ParserImpl(StreamReader reader)
/*     */   {
/* 131 */     this.scanner = new ScannerImpl(reader);
/* 132 */     this.currentEvent = null;
/* 133 */     this.directives = new VersionTagsTuple(null, new HashMap(DEFAULT_TAGS));
/* 134 */     this.states = new ArrayStack(100);
/* 135 */     this.marks = new ArrayStack(10);
/* 136 */     this.state = new ParseStreamStart(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean checkEvent(Event.ID choices)
/*     */   {
/* 143 */     peekEvent();
/* 144 */     if ((this.currentEvent != null) && 
/* 145 */       (this.currentEvent.is(choices))) {
/* 146 */       return true;
/*     */     }
/*     */     
/* 149 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Event peekEvent()
/*     */   {
/* 156 */     if ((this.currentEvent == null) && 
/* 157 */       (this.state != null)) {
/* 158 */       this.currentEvent = this.state.produce();
/*     */     }
/*     */     
/* 161 */     return this.currentEvent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Event getEvent()
/*     */   {
/* 168 */     peekEvent();
/* 169 */     Event value = this.currentEvent;
/* 170 */     this.currentEvent = null;
/* 171 */     return value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private class ParseStreamStart
/*     */     implements Production
/*     */   {
/*     */     private ParseStreamStart() {}
/*     */     
/*     */ 
/*     */     public Event produce()
/*     */     {
/* 184 */       StreamStartToken token = (StreamStartToken)ParserImpl.this.scanner.getToken();
/* 185 */       Event event = new StreamStartEvent(token.getStartMark(), token.getEndMark());
/*     */       
/* 187 */       ParserImpl.this.state = new ParserImpl.ParseImplicitDocumentStart(ParserImpl.this, null);
/* 188 */       return event;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseImplicitDocumentStart implements Production {
/*     */     private ParseImplicitDocumentStart() {}
/*     */     
/* 195 */     public Event produce() { if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Directive, Token.ID.DocumentStart, Token.ID.StreamEnd })) {
/* 196 */         ParserImpl.this.directives = new VersionTagsTuple(null, ParserImpl.DEFAULT_TAGS);
/* 197 */         Token token = ParserImpl.this.scanner.peekToken();
/* 198 */         Mark startMark = token.getStartMark();
/* 199 */         Mark endMark = startMark;
/* 200 */         Event event = new DocumentStartEvent(startMark, endMark, false, null, null);
/*     */         
/* 202 */         ParserImpl.this.states.push(new ParserImpl.ParseDocumentEnd(ParserImpl.this, null));
/* 203 */         ParserImpl.this.state = new ParserImpl.ParseBlockNode(ParserImpl.this, null);
/* 204 */         return event;
/*     */       }
/* 206 */       Production p = new ParserImpl.ParseDocumentStart(ParserImpl.this, null);
/* 207 */       return p.produce();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseDocumentStart implements Production {
/*     */     private ParseDocumentStart() {}
/*     */     
/*     */     public Event produce() {
/* 215 */       while (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.DocumentEnd })) {
/* 216 */         ParserImpl.this.scanner.getToken();
/*     */       }
/*     */       
/*     */       Event event;
/* 220 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.StreamEnd })) {
/* 221 */         Token token = ParserImpl.this.scanner.peekToken();
/* 222 */         Mark startMark = token.getStartMark();
/* 223 */         VersionTagsTuple tuple = ParserImpl.this.processDirectives();
/* 224 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.DocumentStart })) {
/* 225 */           throw new ParserException(null, null, "expected '<document start>', but found " + ParserImpl.this.scanner.peekToken().getTokenId(), ParserImpl.this.scanner.peekToken().getStartMark());
/*     */         }
/*     */         
/* 228 */         token = ParserImpl.this.scanner.getToken();
/* 229 */         Mark endMark = token.getEndMark();
/* 230 */         Event event = new DocumentStartEvent(startMark, endMark, true, tuple.getVersion(), tuple.getTags());
/*     */         
/* 232 */         ParserImpl.this.states.push(new ParserImpl.ParseDocumentEnd(ParserImpl.this, null));
/* 233 */         ParserImpl.this.state = new ParserImpl.ParseDocumentContent(ParserImpl.this, null);
/*     */       }
/*     */       else {
/* 236 */         StreamEndToken token = (StreamEndToken)ParserImpl.this.scanner.getToken();
/* 237 */         event = new StreamEndEvent(token.getStartMark(), token.getEndMark());
/* 238 */         if (!ParserImpl.this.states.isEmpty()) {
/* 239 */           throw new YAMLException("Unexpected end of stream. States left: " + ParserImpl.this.states);
/*     */         }
/* 241 */         if (!ParserImpl.this.marks.isEmpty()) {
/* 242 */           throw new YAMLException("Unexpected end of stream. Marks left: " + ParserImpl.this.marks);
/*     */         }
/* 244 */         ParserImpl.this.state = null;
/*     */       }
/* 246 */       return event;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseDocumentEnd implements Production {
/*     */     private ParseDocumentEnd() {}
/*     */     
/* 253 */     public Event produce() { Token token = ParserImpl.this.scanner.peekToken();
/* 254 */       Mark startMark = token.getStartMark();
/* 255 */       Mark endMark = startMark;
/* 256 */       boolean explicit = false;
/* 257 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.DocumentEnd })) {
/* 258 */         token = ParserImpl.this.scanner.getToken();
/* 259 */         endMark = token.getEndMark();
/* 260 */         explicit = true;
/*     */       }
/* 262 */       Event event = new DocumentEndEvent(startMark, endMark, explicit);
/*     */       
/* 264 */       ParserImpl.this.state = new ParserImpl.ParseDocumentStart(ParserImpl.this, null);
/* 265 */       return event;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseDocumentContent implements Production {
/*     */     private ParseDocumentContent() {}
/*     */     
/* 272 */     public Event produce() { if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Directive, Token.ID.DocumentStart, Token.ID.DocumentEnd, Token.ID.StreamEnd }))
/*     */       {
/* 274 */         Event event = ParserImpl.this.processEmptyScalar(ParserImpl.this.scanner.peekToken().getStartMark());
/* 275 */         ParserImpl.this.state = ((Production)ParserImpl.this.states.pop());
/* 276 */         return event;
/*     */       }
/* 278 */       Production p = new ParserImpl.ParseBlockNode(ParserImpl.this, null);
/* 279 */       return p.produce();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private VersionTagsTuple processDirectives()
/*     */   {
/* 286 */     DumperOptions.Version yamlVersion = null;
/* 287 */     HashMap<String, String> tagHandles = new HashMap();
/* 288 */     while (this.scanner.checkToken(new Token.ID[] { Token.ID.Directive }))
/*     */     {
/* 290 */       DirectiveToken token = (DirectiveToken)this.scanner.getToken();
/* 291 */       if (token.getName().equals("YAML")) {
/* 292 */         if (yamlVersion != null) {
/* 293 */           throw new ParserException(null, null, "found duplicate YAML directive", token.getStartMark());
/*     */         }
/*     */         
/* 296 */         List<Integer> value = token.getValue();
/* 297 */         Integer major = (Integer)value.get(0);
/* 298 */         if (major.intValue() != 1) {
/* 299 */           throw new ParserException(null, null, "found incompatible YAML document (version 1.* is required)", token.getStartMark());
/*     */         }
/*     */         
/*     */ 
/* 303 */         Integer minor = (Integer)value.get(1);
/* 304 */         switch (minor.intValue()) {
/*     */         case 0: 
/* 306 */           yamlVersion = DumperOptions.Version.V1_0;
/* 307 */           break;
/*     */         
/*     */         default: 
/* 310 */           yamlVersion = DumperOptions.Version.V1_1;
/*     */         }
/*     */       }
/* 313 */       else if (token.getName().equals("TAG")) {
/* 314 */         List<String> value = token.getValue();
/* 315 */         String handle = (String)value.get(0);
/* 316 */         String prefix = (String)value.get(1);
/* 317 */         if (tagHandles.containsKey(handle)) {
/* 318 */           throw new ParserException(null, null, "duplicate tag handle " + handle, token.getStartMark());
/*     */         }
/*     */         
/* 321 */         tagHandles.put(handle, prefix);
/*     */       }
/*     */     }
/* 324 */     if ((yamlVersion != null) || (!tagHandles.isEmpty()))
/*     */     {
/* 326 */       for (String key : DEFAULT_TAGS.keySet())
/*     */       {
/* 328 */         if (!tagHandles.containsKey(key)) {
/* 329 */           tagHandles.put(key, DEFAULT_TAGS.get(key));
/*     */         }
/*     */       }
/* 332 */       this.directives = new VersionTagsTuple(yamlVersion, tagHandles);
/*     */     }
/* 334 */     return this.directives;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final ArrayStack<Mark> marks;
/*     */   
/*     */ 
/*     */ 
/*     */   private Production state;
/*     */   
/*     */ 
/*     */   private VersionTagsTuple directives;
/*     */   
/*     */ 
/*     */   private class ParseBlockNode
/*     */     implements Production
/*     */   {
/*     */     private ParseBlockNode() {}
/*     */     
/*     */ 
/*     */ 
/*     */     public Event produce()
/*     */     {
/* 359 */       return ParserImpl.this.parseNode(true, false);
/*     */     }
/*     */   }
/*     */   
/*     */   private Event parseFlowNode() {
/* 364 */     return parseNode(false, false);
/*     */   }
/*     */   
/*     */   private Event parseBlockNodeOrIndentlessSequence() {
/* 368 */     return parseNode(true, true);
/*     */   }
/*     */   
/*     */   private Event parseNode(boolean block, boolean indentlessSequence)
/*     */   {
/* 373 */     Mark startMark = null;
/* 374 */     Mark endMark = null;
/* 375 */     Mark tagMark = null;
/* 376 */     Event event; if (this.scanner.checkToken(new Token.ID[] { Token.ID.Alias })) {
/* 377 */       AliasToken token = (AliasToken)this.scanner.getToken();
/* 378 */       Event event = new AliasEvent(token.getValue(), token.getStartMark(), token.getEndMark());
/* 379 */       this.state = ((Production)this.states.pop());
/*     */     } else {
/* 381 */       String anchor = null;
/* 382 */       TagTuple tagTokenTag = null;
/* 383 */       if (this.scanner.checkToken(new Token.ID[] { Token.ID.Anchor })) {
/* 384 */         AnchorToken token = (AnchorToken)this.scanner.getToken();
/* 385 */         startMark = token.getStartMark();
/* 386 */         endMark = token.getEndMark();
/* 387 */         anchor = token.getValue();
/* 388 */         if (this.scanner.checkToken(new Token.ID[] { Token.ID.Tag })) {
/* 389 */           TagToken tagToken = (TagToken)this.scanner.getToken();
/* 390 */           tagMark = tagToken.getStartMark();
/* 391 */           endMark = tagToken.getEndMark();
/* 392 */           tagTokenTag = tagToken.getValue();
/*     */         }
/* 394 */       } else if (this.scanner.checkToken(new Token.ID[] { Token.ID.Tag })) {
/* 395 */         TagToken tagToken = (TagToken)this.scanner.getToken();
/* 396 */         startMark = tagToken.getStartMark();
/* 397 */         tagMark = startMark;
/* 398 */         endMark = tagToken.getEndMark();
/* 399 */         tagTokenTag = tagToken.getValue();
/* 400 */         if (this.scanner.checkToken(new Token.ID[] { Token.ID.Anchor })) {
/* 401 */           AnchorToken token = (AnchorToken)this.scanner.getToken();
/* 402 */           endMark = token.getEndMark();
/* 403 */           anchor = token.getValue();
/*     */         }
/*     */       }
/* 406 */       String tag = null;
/* 407 */       if (tagTokenTag != null) {
/* 408 */         String handle = tagTokenTag.getHandle();
/* 409 */         String suffix = tagTokenTag.getSuffix();
/* 410 */         if (handle != null) {
/* 411 */           if (!this.directives.getTags().containsKey(handle)) {
/* 412 */             throw new ParserException("while parsing a node", startMark, "found undefined tag handle " + handle, tagMark);
/*     */           }
/*     */           
/* 415 */           tag = (String)this.directives.getTags().get(handle) + suffix;
/*     */         } else {
/* 417 */           tag = suffix;
/*     */         }
/*     */       }
/* 420 */       if (startMark == null) {
/* 421 */         startMark = this.scanner.peekToken().getStartMark();
/* 422 */         endMark = startMark;
/*     */       }
/* 424 */       event = null;
/* 425 */       boolean implicit = (tag == null) || (tag.equals("!"));
/* 426 */       if (indentlessSequence) if (this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry })) {
/* 427 */           endMark = this.scanner.peekToken().getEndMark();
/* 428 */           event = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.FALSE);
/*     */           
/* 430 */           this.state = new ParseIndentlessSequenceEntry(null);
/*     */           break label1174; }
/* 432 */       if (this.scanner.checkToken(new Token.ID[] { Token.ID.Scalar })) {
/* 433 */         ScalarToken token = (ScalarToken)this.scanner.getToken();
/* 434 */         endMark = token.getEndMark();
/*     */         ImplicitTuple implicitValues;
/* 436 */         ImplicitTuple implicitValues; if (((token.getPlain()) && (tag == null)) || ("!".equals(tag))) {
/* 437 */           implicitValues = new ImplicitTuple(true, false); } else { ImplicitTuple implicitValues;
/* 438 */           if (tag == null) {
/* 439 */             implicitValues = new ImplicitTuple(false, true);
/*     */           } else
/* 441 */             implicitValues = new ImplicitTuple(false, false);
/*     */         }
/* 443 */         event = new ScalarEvent(anchor, tag, implicitValues, token.getValue(), startMark, endMark, Character.valueOf(token.getStyle()));
/*     */         
/* 445 */         this.state = ((Production)this.states.pop());
/* 446 */       } else if (this.scanner.checkToken(new Token.ID[] { Token.ID.FlowSequenceStart })) {
/* 447 */         endMark = this.scanner.peekToken().getEndMark();
/* 448 */         event = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.TRUE);
/*     */         
/* 450 */         this.state = new ParseFlowSequenceFirstEntry(null);
/* 451 */       } else if (this.scanner.checkToken(new Token.ID[] { Token.ID.FlowMappingStart })) {
/* 452 */         endMark = this.scanner.peekToken().getEndMark();
/* 453 */         event = new MappingStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.TRUE);
/*     */         
/* 455 */         this.state = new ParseFlowMappingFirstKey(null);
/* 456 */       } else { if (block) if (this.scanner.checkToken(new Token.ID[] { Token.ID.BlockSequenceStart })) {
/* 457 */             endMark = this.scanner.peekToken().getStartMark();
/* 458 */             event = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.FALSE);
/*     */             
/* 460 */             this.state = new ParseBlockSequenceFirstEntry(null);
/* 461 */             break label1174; } if (block) if (this.scanner.checkToken(new Token.ID[] { Token.ID.BlockMappingStart })) {
/* 462 */             endMark = this.scanner.peekToken().getStartMark();
/* 463 */             event = new MappingStartEvent(anchor, tag, implicit, startMark, endMark, Boolean.FALSE);
/*     */             
/* 465 */             this.state = new ParseBlockMappingFirstKey(null);
/* 466 */             break label1174; } if ((anchor != null) || (tag != null))
/*     */         {
/*     */ 
/* 469 */           event = new ScalarEvent(anchor, tag, new ImplicitTuple(implicit, false), "", startMark, endMark, Character.valueOf('\000'));
/*     */           
/* 471 */           this.state = ((Production)this.states.pop());
/*     */         } else { String node;
/*     */           String node;
/* 474 */           if (block) {
/* 475 */             node = "block";
/*     */           } else {
/* 477 */             node = "flow";
/*     */           }
/* 479 */           Token token = this.scanner.peekToken();
/* 480 */           throw new ParserException("while parsing a " + node + " node", startMark, "expected the node content, but found " + token.getTokenId(), token.getStartMark());
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     label1174:
/* 486 */     return event;
/*     */   }
/*     */   
/*     */   private class ParseBlockSequenceFirstEntry implements Production
/*     */   {
/*     */     private ParseBlockSequenceFirstEntry() {}
/*     */     
/*     */     public Event produce() {
/* 494 */       Token token = ParserImpl.this.scanner.getToken();
/* 495 */       ParserImpl.this.marks.push(token.getStartMark());
/* 496 */       return new ParserImpl.ParseBlockSequenceEntry(ParserImpl.this, null).produce();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseBlockSequenceEntry implements Production { private ParseBlockSequenceEntry() {}
/*     */     
/* 502 */     public Event produce() { if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry })) {
/* 503 */         BlockEntryToken token = (BlockEntryToken)ParserImpl.this.scanner.getToken();
/* 504 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry, Token.ID.BlockEnd })) {
/* 505 */           ParserImpl.this.states.push(new ParseBlockSequenceEntry(ParserImpl.this));
/* 506 */           return new ParserImpl.ParseBlockNode(ParserImpl.this, null).produce();
/*     */         }
/* 508 */         ParserImpl.this.state = new ParseBlockSequenceEntry(ParserImpl.this);
/* 509 */         return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */       }
/*     */       
/* 512 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEnd })) {
/* 513 */         Token token = ParserImpl.this.scanner.peekToken();
/* 514 */         throw new ParserException("while parsing a block collection", (Mark)ParserImpl.this.marks.pop(), "expected <block end>, but found " + token.getTokenId(), token.getStartMark());
/*     */       }
/*     */       
/*     */ 
/* 518 */       Token token = ParserImpl.this.scanner.getToken();
/* 519 */       Event event = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
/* 520 */       ParserImpl.this.state = ((Production)ParserImpl.this.states.pop());
/* 521 */       ParserImpl.this.marks.pop();
/* 522 */       return event;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseIndentlessSequenceEntry implements Production {
/*     */     private ParseIndentlessSequenceEntry() {}
/*     */     
/*     */     public Event produce() {
/* 530 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry })) {
/* 531 */         Token token = ParserImpl.this.scanner.getToken();
/* 532 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry, Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd }))
/*     */         {
/* 534 */           ParserImpl.this.states.push(new ParseIndentlessSequenceEntry(ParserImpl.this));
/* 535 */           return new ParserImpl.ParseBlockNode(ParserImpl.this, null).produce();
/*     */         }
/* 537 */         ParserImpl.this.state = new ParseIndentlessSequenceEntry(ParserImpl.this);
/* 538 */         return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */       }
/*     */       
/* 541 */       Token token = ParserImpl.this.scanner.peekToken();
/* 542 */       Event event = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
/* 543 */       ParserImpl.this.state = ((Production)ParserImpl.this.states.pop());
/* 544 */       return event;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseBlockMappingFirstKey implements Production { private ParseBlockMappingFirstKey() {}
/*     */     
/* 550 */     public Event produce() { Token token = ParserImpl.this.scanner.getToken();
/* 551 */       ParserImpl.this.marks.push(token.getStartMark());
/* 552 */       return new ParserImpl.ParseBlockMappingKey(ParserImpl.this, null).produce();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseBlockMappingKey implements Production { private ParseBlockMappingKey() {}
/*     */     
/* 558 */     public Event produce() { if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key })) {
/* 559 */         Token token = ParserImpl.this.scanner.getToken();
/* 560 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd })) {
/* 561 */           ParserImpl.this.states.push(new ParserImpl.ParseBlockMappingValue(ParserImpl.this, null));
/* 562 */           return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
/*     */         }
/* 564 */         ParserImpl.this.state = new ParserImpl.ParseBlockMappingValue(ParserImpl.this, null);
/* 565 */         return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */       }
/*     */       
/* 568 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEnd })) {
/* 569 */         Token token = ParserImpl.this.scanner.peekToken();
/* 570 */         throw new ParserException("while parsing a block mapping", (Mark)ParserImpl.this.marks.pop(), "expected <block end>, but found " + token.getTokenId(), token.getStartMark());
/*     */       }
/*     */       
/*     */ 
/* 574 */       Token token = ParserImpl.this.scanner.getToken();
/* 575 */       Event event = new MappingEndEvent(token.getStartMark(), token.getEndMark());
/* 576 */       ParserImpl.this.state = ((Production)ParserImpl.this.states.pop());
/* 577 */       ParserImpl.this.marks.pop();
/* 578 */       return event;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseBlockMappingValue implements Production { private ParseBlockMappingValue() {}
/*     */     
/* 584 */     public Event produce() { if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value })) {
/* 585 */         Token token = ParserImpl.this.scanner.getToken();
/* 586 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd })) {
/* 587 */           ParserImpl.this.states.push(new ParserImpl.ParseBlockMappingKey(ParserImpl.this, null));
/* 588 */           return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
/*     */         }
/* 590 */         ParserImpl.this.state = new ParserImpl.ParseBlockMappingKey(ParserImpl.this, null);
/* 591 */         return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */       }
/*     */       
/* 594 */       ParserImpl.this.state = new ParserImpl.ParseBlockMappingKey(ParserImpl.this, null);
/* 595 */       Token token = ParserImpl.this.scanner.peekToken();
/* 596 */       return ParserImpl.this.processEmptyScalar(token.getStartMark());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private class ParseFlowSequenceFirstEntry
/*     */     implements Production
/*     */   {
/*     */     private ParseFlowSequenceFirstEntry() {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Event produce()
/*     */     {
/* 615 */       Token token = ParserImpl.this.scanner.getToken();
/* 616 */       ParserImpl.this.marks.push(token.getStartMark());
/* 617 */       return new ParserImpl.ParseFlowSequenceEntry(ParserImpl.this, true).produce();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseFlowSequenceEntry implements Production {
/* 622 */     private boolean first = false;
/*     */     
/*     */     public ParseFlowSequenceEntry(boolean first) {
/* 625 */       this.first = first;
/*     */     }
/*     */     
/*     */     public Event produce() {
/* 629 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowSequenceEnd })) {
/* 630 */         if (!this.first) {
/* 631 */           if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowEntry })) {
/* 632 */             ParserImpl.this.scanner.getToken();
/*     */           } else {
/* 634 */             Token token = ParserImpl.this.scanner.peekToken();
/* 635 */             throw new ParserException("while parsing a flow sequence", (Mark)ParserImpl.this.marks.pop(), "expected ',' or ']', but got " + token.getTokenId(), token.getStartMark());
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 640 */         if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key })) {
/* 641 */           Token token = ParserImpl.this.scanner.peekToken();
/* 642 */           Event event = new MappingStartEvent(null, null, true, token.getStartMark(), token.getEndMark(), Boolean.TRUE);
/*     */           
/* 644 */           ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingKey(ParserImpl.this, null);
/* 645 */           return event; }
/* 646 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowSequenceEnd })) {
/* 647 */           ParserImpl.this.states.push(new ParseFlowSequenceEntry(ParserImpl.this, false));
/* 648 */           return ParserImpl.this.parseFlowNode();
/*     */         }
/*     */       }
/* 651 */       Token token = ParserImpl.this.scanner.getToken();
/* 652 */       Event event = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
/* 653 */       ParserImpl.this.state = ((Production)ParserImpl.this.states.pop());
/* 654 */       ParserImpl.this.marks.pop();
/* 655 */       return event;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseFlowSequenceEntryMappingKey implements Production { private ParseFlowSequenceEntryMappingKey() {}
/*     */     
/* 661 */     public Event produce() { Token token = ParserImpl.this.scanner.getToken();
/* 662 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowSequenceEnd })) {
/* 663 */         ParserImpl.this.states.push(new ParserImpl.ParseFlowSequenceEntryMappingValue(ParserImpl.this, null));
/* 664 */         return ParserImpl.this.parseFlowNode();
/*     */       }
/* 666 */       ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingValue(ParserImpl.this, null);
/* 667 */       return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseFlowSequenceEntryMappingValue implements Production {
/*     */     private ParseFlowSequenceEntryMappingValue() {}
/*     */     
/* 674 */     public Event produce() { if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value })) {
/* 675 */         Token token = ParserImpl.this.scanner.getToken();
/* 676 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowEntry, Token.ID.FlowSequenceEnd })) {
/* 677 */           ParserImpl.this.states.push(new ParserImpl.ParseFlowSequenceEntryMappingEnd(ParserImpl.this, null));
/* 678 */           return ParserImpl.this.parseFlowNode();
/*     */         }
/* 680 */         ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingEnd(ParserImpl.this, null);
/* 681 */         return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */       }
/*     */       
/* 684 */       ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingEnd(ParserImpl.this, null);
/* 685 */       Token token = ParserImpl.this.scanner.peekToken();
/* 686 */       return ParserImpl.this.processEmptyScalar(token.getStartMark());
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseFlowSequenceEntryMappingEnd implements Production {
/*     */     private ParseFlowSequenceEntryMappingEnd() {}
/*     */     
/* 693 */     public Event produce() { ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntry(ParserImpl.this, false);
/* 694 */       Token token = ParserImpl.this.scanner.peekToken();
/* 695 */       return new MappingEndEvent(token.getStartMark(), token.getEndMark());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private class ParseFlowMappingFirstKey
/*     */     implements Production
/*     */   {
/*     */     private ParseFlowMappingFirstKey() {}
/*     */     
/*     */ 
/*     */ 
/*     */     public Event produce()
/*     */     {
/* 710 */       Token token = ParserImpl.this.scanner.getToken();
/* 711 */       ParserImpl.this.marks.push(token.getStartMark());
/* 712 */       return new ParserImpl.ParseFlowMappingKey(ParserImpl.this, true).produce();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseFlowMappingKey implements Production {
/* 717 */     private boolean first = false;
/*     */     
/*     */     public ParseFlowMappingKey(boolean first) {
/* 720 */       this.first = first;
/*     */     }
/*     */     
/*     */     public Event produce() {
/* 724 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowMappingEnd })) {
/* 725 */         if (!this.first) {
/* 726 */           if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowEntry })) {
/* 727 */             ParserImpl.this.scanner.getToken();
/*     */           } else {
/* 729 */             Token token = ParserImpl.this.scanner.peekToken();
/* 730 */             throw new ParserException("while parsing a flow mapping", (Mark)ParserImpl.this.marks.pop(), "expected ',' or '}', but got " + token.getTokenId(), token.getStartMark());
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 735 */         if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key })) {
/* 736 */           Token token = ParserImpl.this.scanner.getToken();
/* 737 */           if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowMappingEnd }))
/*     */           {
/* 739 */             ParserImpl.this.states.push(new ParserImpl.ParseFlowMappingValue(ParserImpl.this, null));
/* 740 */             return ParserImpl.this.parseFlowNode();
/*     */           }
/* 742 */           ParserImpl.this.state = new ParserImpl.ParseFlowMappingValue(ParserImpl.this, null);
/* 743 */           return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */         }
/* 745 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowMappingEnd })) {
/* 746 */           ParserImpl.this.states.push(new ParserImpl.ParseFlowMappingEmptyValue(ParserImpl.this, null));
/* 747 */           return ParserImpl.this.parseFlowNode();
/*     */         }
/*     */       }
/* 750 */       Token token = ParserImpl.this.scanner.getToken();
/* 751 */       Event event = new MappingEndEvent(token.getStartMark(), token.getEndMark());
/* 752 */       ParserImpl.this.state = ((Production)ParserImpl.this.states.pop());
/* 753 */       ParserImpl.this.marks.pop();
/* 754 */       return event;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseFlowMappingValue implements Production { private ParseFlowMappingValue() {}
/*     */     
/* 760 */     public Event produce() { if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value })) {
/* 761 */         Token token = ParserImpl.this.scanner.getToken();
/* 762 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowEntry, Token.ID.FlowMappingEnd })) {
/* 763 */           ParserImpl.this.states.push(new ParserImpl.ParseFlowMappingKey(ParserImpl.this, false));
/* 764 */           return ParserImpl.this.parseFlowNode();
/*     */         }
/* 766 */         ParserImpl.this.state = new ParserImpl.ParseFlowMappingKey(ParserImpl.this, false);
/* 767 */         return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */       }
/*     */       
/* 770 */       ParserImpl.this.state = new ParserImpl.ParseFlowMappingKey(ParserImpl.this, false);
/* 771 */       Token token = ParserImpl.this.scanner.peekToken();
/* 772 */       return ParserImpl.this.processEmptyScalar(token.getStartMark());
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseFlowMappingEmptyValue implements Production {
/*     */     private ParseFlowMappingEmptyValue() {}
/*     */     
/* 779 */     public Event produce() { ParserImpl.this.state = new ParserImpl.ParseFlowMappingKey(ParserImpl.this, false);
/* 780 */       return ParserImpl.this.processEmptyScalar(ParserImpl.this.scanner.peekToken().getStartMark());
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
/*     */   private Event processEmptyScalar(Mark mark)
/*     */   {
/* 793 */     return new ScalarEvent(null, null, new ImplicitTuple(true, false), "", mark, mark, Character.valueOf('\000'));
/*     */   }
/*     */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\parser\ParserImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */