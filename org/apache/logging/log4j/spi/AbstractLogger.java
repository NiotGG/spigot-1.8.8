/*      */ package org.apache.logging.log4j.spi;
/*      */ 
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.apache.logging.log4j.Marker;
/*      */ import org.apache.logging.log4j.MarkerManager;
/*      */ import org.apache.logging.log4j.message.Message;
/*      */ import org.apache.logging.log4j.message.MessageFactory;
/*      */ import org.apache.logging.log4j.message.ParameterizedMessageFactory;
/*      */ import org.apache.logging.log4j.message.StringFormattedMessage;
/*      */ import org.apache.logging.log4j.status.StatusLogger;
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
/*      */ public abstract class AbstractLogger
/*      */   implements Logger
/*      */ {
/*   38 */   public static final Marker FLOW_MARKER = MarkerManager.getMarker("FLOW");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   43 */   public static final Marker ENTRY_MARKER = MarkerManager.getMarker("ENTRY", FLOW_MARKER);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   48 */   public static final Marker EXIT_MARKER = MarkerManager.getMarker("EXIT", FLOW_MARKER);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   54 */   public static final Marker EXCEPTION_MARKER = MarkerManager.getMarker("EXCEPTION");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   59 */   public static final Marker THROWING_MARKER = MarkerManager.getMarker("THROWING", EXCEPTION_MARKER);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   64 */   public static final Marker CATCHING_MARKER = MarkerManager.getMarker("CATCHING", EXCEPTION_MARKER);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   69 */   public static final Class<? extends MessageFactory> DEFAULT_MESSAGE_FACTORY_CLASS = ParameterizedMessageFactory.class;
/*      */   
/*      */ 
/*   72 */   private static final String FQCN = AbstractLogger.class.getName();
/*      */   
/*      */ 
/*      */   private static final String THROWING = "throwing";
/*      */   
/*      */   private static final String CATCHING = "catching";
/*      */   
/*      */   private final String name;
/*      */   
/*      */   private final MessageFactory messageFactory;
/*      */   
/*      */ 
/*      */   public AbstractLogger()
/*      */   {
/*   86 */     this.name = getClass().getName();
/*   87 */     this.messageFactory = createDefaultMessageFactory();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public AbstractLogger(String paramString)
/*      */   {
/*   96 */     this.name = paramString;
/*   97 */     this.messageFactory = createDefaultMessageFactory();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public AbstractLogger(String paramString, MessageFactory paramMessageFactory)
/*      */   {
/*  107 */     this.name = paramString;
/*  108 */     this.messageFactory = (paramMessageFactory == null ? createDefaultMessageFactory() : paramMessageFactory);
/*      */   }
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
/*      */   public static void checkMessageFactory(Logger paramLogger, MessageFactory paramMessageFactory)
/*      */   {
/*  122 */     String str = paramLogger.getName();
/*  123 */     MessageFactory localMessageFactory = paramLogger.getMessageFactory();
/*  124 */     if ((paramMessageFactory != null) && (!localMessageFactory.equals(paramMessageFactory))) {
/*  125 */       StatusLogger.getLogger().warn("The Logger {} was created with the message factory {} and is now requested with the message factory {}, which may create log events with unexpected formatting.", new Object[] { str, localMessageFactory, paramMessageFactory });
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*  130 */     else if ((paramMessageFactory == null) && (!localMessageFactory.getClass().equals(DEFAULT_MESSAGE_FACTORY_CLASS)))
/*      */     {
/*  132 */       StatusLogger.getLogger().warn("The Logger {} was created with the message factory {} and is now requested with a null message factory (defaults to {}), which may create log events with unexpected formatting.", new Object[] { str, localMessageFactory, DEFAULT_MESSAGE_FACTORY_CLASS.getName() });
/*      */     }
/*      */   }
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
/*      */   public void catching(Level paramLevel, Throwable paramThrowable)
/*      */   {
/*  148 */     catching(FQCN, paramLevel, paramThrowable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void catching(Throwable paramThrowable)
/*      */   {
/*  158 */     catching(FQCN, Level.ERROR, paramThrowable);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void catching(String paramString, Level paramLevel, Throwable paramThrowable)
/*      */   {
/*  169 */     if (isEnabled(paramLevel, CATCHING_MARKER, (Object)null, null)) {
/*  170 */       log(CATCHING_MARKER, paramString, paramLevel, this.messageFactory.newMessage("catching"), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */   private MessageFactory createDefaultMessageFactory() {
/*      */     try {
/*  176 */       return (MessageFactory)DEFAULT_MESSAGE_FACTORY_CLASS.newInstance();
/*      */     } catch (InstantiationException localInstantiationException) {
/*  178 */       throw new IllegalStateException(localInstantiationException);
/*      */     } catch (IllegalAccessException localIllegalAccessException) {
/*  180 */       throw new IllegalStateException(localIllegalAccessException);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void debug(Marker paramMarker, Message paramMessage)
/*      */   {
/*  192 */     if (isEnabled(Level.DEBUG, paramMarker, paramMessage, null)) {
/*  193 */       log(paramMarker, FQCN, Level.DEBUG, paramMessage, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void debug(Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*      */   {
/*  206 */     if (isEnabled(Level.DEBUG, paramMarker, paramMessage, paramThrowable)) {
/*  207 */       log(paramMarker, FQCN, Level.DEBUG, paramMessage, paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void debug(Marker paramMarker, Object paramObject)
/*      */   {
/*  219 */     if (isEnabled(Level.DEBUG, paramMarker, paramObject, null)) {
/*  220 */       log(paramMarker, FQCN, Level.DEBUG, this.messageFactory.newMessage(paramObject), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void debug(Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*      */   {
/*  234 */     if (isEnabled(Level.DEBUG, paramMarker, paramObject, paramThrowable)) {
/*  235 */       log(paramMarker, FQCN, Level.DEBUG, this.messageFactory.newMessage(paramObject), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void debug(Marker paramMarker, String paramString)
/*      */   {
/*  247 */     if (isEnabled(Level.DEBUG, paramMarker, paramString)) {
/*  248 */       log(paramMarker, FQCN, Level.DEBUG, this.messageFactory.newMessage(paramString), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void debug(Marker paramMarker, String paramString, Object... paramVarArgs)
/*      */   {
/*  261 */     if (isEnabled(Level.DEBUG, paramMarker, paramString, paramVarArgs)) {
/*  262 */       Message localMessage = this.messageFactory.newMessage(paramString, paramVarArgs);
/*  263 */       log(paramMarker, FQCN, Level.DEBUG, localMessage, localMessage.getThrowable());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void debug(Marker paramMarker, String paramString, Throwable paramThrowable)
/*      */   {
/*  277 */     if (isEnabled(Level.DEBUG, paramMarker, paramString, paramThrowable)) {
/*  278 */       log(paramMarker, FQCN, Level.DEBUG, this.messageFactory.newMessage(paramString), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void debug(Message paramMessage)
/*      */   {
/*  289 */     if (isEnabled(Level.DEBUG, null, paramMessage, null)) {
/*  290 */       log(null, FQCN, Level.DEBUG, paramMessage, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void debug(Message paramMessage, Throwable paramThrowable)
/*      */   {
/*  302 */     if (isEnabled(Level.DEBUG, null, paramMessage, paramThrowable)) {
/*  303 */       log(null, FQCN, Level.DEBUG, paramMessage, paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void debug(Object paramObject)
/*      */   {
/*  314 */     if (isEnabled(Level.DEBUG, null, paramObject, null)) {
/*  315 */       log(null, FQCN, Level.DEBUG, this.messageFactory.newMessage(paramObject), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void debug(Object paramObject, Throwable paramThrowable)
/*      */   {
/*  328 */     if (isEnabled(Level.DEBUG, null, paramObject, paramThrowable)) {
/*  329 */       log(null, FQCN, Level.DEBUG, this.messageFactory.newMessage(paramObject), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void debug(String paramString)
/*      */   {
/*  340 */     if (isEnabled(Level.DEBUG, null, paramString)) {
/*  341 */       log(null, FQCN, Level.DEBUG, this.messageFactory.newMessage(paramString), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void debug(String paramString, Object... paramVarArgs)
/*      */   {
/*  353 */     if (isEnabled(Level.DEBUG, null, paramString, paramVarArgs)) {
/*  354 */       Message localMessage = this.messageFactory.newMessage(paramString, paramVarArgs);
/*  355 */       log(null, FQCN, Level.DEBUG, localMessage, localMessage.getThrowable());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void debug(String paramString, Throwable paramThrowable)
/*      */   {
/*  368 */     if (isEnabled(Level.DEBUG, null, paramString, paramThrowable)) {
/*  369 */       log(null, FQCN, Level.DEBUG, this.messageFactory.newMessage(paramString), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void entry()
/*      */   {
/*  378 */     entry(FQCN, new Object[0]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void entry(Object... paramVarArgs)
/*      */   {
/*  388 */     entry(FQCN, paramVarArgs);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void entry(String paramString, Object... paramVarArgs)
/*      */   {
/*  398 */     if (isEnabled(Level.TRACE, ENTRY_MARKER, (Object)null, null)) {
/*  399 */       log(ENTRY_MARKER, paramString, Level.TRACE, entryMsg(paramVarArgs.length, paramVarArgs), null);
/*      */     }
/*      */   }
/*      */   
/*      */   private Message entryMsg(int paramInt, Object... paramVarArgs) {
/*  404 */     if (paramInt == 0) {
/*  405 */       return this.messageFactory.newMessage("entry");
/*      */     }
/*  407 */     StringBuilder localStringBuilder = new StringBuilder("entry params(");
/*  408 */     int i = 0;
/*  409 */     for (Object localObject : paramVarArgs) {
/*  410 */       if (localObject != null) {
/*  411 */         localStringBuilder.append(localObject.toString());
/*      */       } else {
/*  413 */         localStringBuilder.append("null");
/*      */       }
/*  415 */       i++; if (i < paramVarArgs.length) {
/*  416 */         localStringBuilder.append(", ");
/*      */       }
/*      */     }
/*  419 */     localStringBuilder.append(")");
/*  420 */     return this.messageFactory.newMessage(localStringBuilder.toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void error(Marker paramMarker, Message paramMessage)
/*      */   {
/*  432 */     if (isEnabled(Level.ERROR, paramMarker, paramMessage, null)) {
/*  433 */       log(paramMarker, FQCN, Level.ERROR, paramMessage, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void error(Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*      */   {
/*  446 */     if (isEnabled(Level.ERROR, paramMarker, paramMessage, paramThrowable)) {
/*  447 */       log(paramMarker, FQCN, Level.ERROR, paramMessage, paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void error(Marker paramMarker, Object paramObject)
/*      */   {
/*  459 */     if (isEnabled(Level.ERROR, paramMarker, paramObject, null)) {
/*  460 */       log(paramMarker, FQCN, Level.ERROR, this.messageFactory.newMessage(paramObject), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void error(Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*      */   {
/*  474 */     if (isEnabled(Level.ERROR, paramMarker, paramObject, paramThrowable)) {
/*  475 */       log(paramMarker, FQCN, Level.ERROR, this.messageFactory.newMessage(paramObject), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void error(Marker paramMarker, String paramString)
/*      */   {
/*  487 */     if (isEnabled(Level.ERROR, paramMarker, paramString)) {
/*  488 */       log(paramMarker, FQCN, Level.ERROR, this.messageFactory.newMessage(paramString), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void error(Marker paramMarker, String paramString, Object... paramVarArgs)
/*      */   {
/*  501 */     if (isEnabled(Level.ERROR, paramMarker, paramString, paramVarArgs)) {
/*  502 */       Message localMessage = this.messageFactory.newMessage(paramString, paramVarArgs);
/*  503 */       log(paramMarker, FQCN, Level.ERROR, localMessage, localMessage.getThrowable());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void error(Marker paramMarker, String paramString, Throwable paramThrowable)
/*      */   {
/*  517 */     if (isEnabled(Level.ERROR, paramMarker, paramString, paramThrowable)) {
/*  518 */       log(paramMarker, FQCN, Level.ERROR, this.messageFactory.newMessage(paramString), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void error(Message paramMessage)
/*      */   {
/*  529 */     if (isEnabled(Level.ERROR, null, paramMessage, null)) {
/*  530 */       log(null, FQCN, Level.ERROR, paramMessage, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void error(Message paramMessage, Throwable paramThrowable)
/*      */   {
/*  542 */     if (isEnabled(Level.ERROR, null, paramMessage, paramThrowable)) {
/*  543 */       log(null, FQCN, Level.ERROR, paramMessage, paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void error(Object paramObject)
/*      */   {
/*  554 */     if (isEnabled(Level.ERROR, null, paramObject, null)) {
/*  555 */       log(null, FQCN, Level.ERROR, this.messageFactory.newMessage(paramObject), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void error(Object paramObject, Throwable paramThrowable)
/*      */   {
/*  568 */     if (isEnabled(Level.ERROR, null, paramObject, paramThrowable)) {
/*  569 */       log(null, FQCN, Level.ERROR, this.messageFactory.newMessage(paramObject), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void error(String paramString)
/*      */   {
/*  580 */     if (isEnabled(Level.ERROR, null, paramString)) {
/*  581 */       log(null, FQCN, Level.ERROR, this.messageFactory.newMessage(paramString), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void error(String paramString, Object... paramVarArgs)
/*      */   {
/*  593 */     if (isEnabled(Level.ERROR, null, paramString, paramVarArgs)) {
/*  594 */       Message localMessage = this.messageFactory.newMessage(paramString, paramVarArgs);
/*  595 */       log(null, FQCN, Level.ERROR, localMessage, localMessage.getThrowable());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void error(String paramString, Throwable paramThrowable)
/*      */   {
/*  608 */     if (isEnabled(Level.ERROR, null, paramString, paramThrowable)) {
/*  609 */       log(null, FQCN, Level.ERROR, this.messageFactory.newMessage(paramString), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public void exit()
/*      */   {
/*  618 */     exit(FQCN, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <R> R exit(R paramR)
/*      */   {
/*  630 */     return (R)exit(FQCN, paramR);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected <R> R exit(String paramString, R paramR)
/*      */   {
/*  641 */     if (isEnabled(Level.TRACE, EXIT_MARKER, (Object)null, null)) {
/*  642 */       log(EXIT_MARKER, paramString, Level.TRACE, toExitMsg(paramR), null);
/*      */     }
/*  644 */     return paramR;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fatal(Marker paramMarker, Message paramMessage)
/*      */   {
/*  655 */     if (isEnabled(Level.FATAL, paramMarker, paramMessage, null)) {
/*  656 */       log(paramMarker, FQCN, Level.FATAL, paramMessage, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fatal(Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*      */   {
/*  669 */     if (isEnabled(Level.FATAL, paramMarker, paramMessage, paramThrowable)) {
/*  670 */       log(paramMarker, FQCN, Level.FATAL, paramMessage, paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fatal(Marker paramMarker, Object paramObject)
/*      */   {
/*  682 */     if (isEnabled(Level.FATAL, paramMarker, paramObject, null)) {
/*  683 */       log(paramMarker, FQCN, Level.FATAL, this.messageFactory.newMessage(paramObject), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fatal(Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*      */   {
/*  697 */     if (isEnabled(Level.FATAL, paramMarker, paramObject, paramThrowable)) {
/*  698 */       log(paramMarker, FQCN, Level.FATAL, this.messageFactory.newMessage(paramObject), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fatal(Marker paramMarker, String paramString)
/*      */   {
/*  710 */     if (isEnabled(Level.FATAL, paramMarker, paramString)) {
/*  711 */       log(paramMarker, FQCN, Level.FATAL, this.messageFactory.newMessage(paramString), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fatal(Marker paramMarker, String paramString, Object... paramVarArgs)
/*      */   {
/*  724 */     if (isEnabled(Level.FATAL, paramMarker, paramString, paramVarArgs)) {
/*  725 */       Message localMessage = this.messageFactory.newMessage(paramString, paramVarArgs);
/*  726 */       log(paramMarker, FQCN, Level.FATAL, localMessage, localMessage.getThrowable());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fatal(Marker paramMarker, String paramString, Throwable paramThrowable)
/*      */   {
/*  740 */     if (isEnabled(Level.FATAL, paramMarker, paramString, paramThrowable)) {
/*  741 */       log(paramMarker, FQCN, Level.FATAL, this.messageFactory.newMessage(paramString), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fatal(Message paramMessage)
/*      */   {
/*  751 */     if (isEnabled(Level.FATAL, null, paramMessage, null)) {
/*  752 */       log(null, FQCN, Level.FATAL, paramMessage, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fatal(Message paramMessage, Throwable paramThrowable)
/*      */   {
/*  764 */     if (isEnabled(Level.FATAL, null, paramMessage, paramThrowable)) {
/*  765 */       log(null, FQCN, Level.FATAL, paramMessage, paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fatal(Object paramObject)
/*      */   {
/*  776 */     if (isEnabled(Level.FATAL, null, paramObject, null)) {
/*  777 */       log(null, FQCN, Level.FATAL, this.messageFactory.newMessage(paramObject), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fatal(Object paramObject, Throwable paramThrowable)
/*      */   {
/*  790 */     if (isEnabled(Level.FATAL, null, paramObject, paramThrowable)) {
/*  791 */       log(null, FQCN, Level.FATAL, this.messageFactory.newMessage(paramObject), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fatal(String paramString)
/*      */   {
/*  802 */     if (isEnabled(Level.FATAL, null, paramString)) {
/*  803 */       log(null, FQCN, Level.FATAL, this.messageFactory.newMessage(paramString), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fatal(String paramString, Object... paramVarArgs)
/*      */   {
/*  815 */     if (isEnabled(Level.FATAL, null, paramString, paramVarArgs)) {
/*  816 */       Message localMessage = this.messageFactory.newMessage(paramString, paramVarArgs);
/*  817 */       log(null, FQCN, Level.FATAL, localMessage, localMessage.getThrowable());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void fatal(String paramString, Throwable paramThrowable)
/*      */   {
/*  830 */     if (isEnabled(Level.FATAL, null, paramString, paramThrowable)) {
/*  831 */       log(null, FQCN, Level.FATAL, this.messageFactory.newMessage(paramString), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MessageFactory getMessageFactory()
/*      */   {
/*  842 */     return this.messageFactory;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getName()
/*      */   {
/*  850 */     return this.name;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void info(Marker paramMarker, Message paramMessage)
/*      */   {
/*  861 */     if (isEnabled(Level.INFO, paramMarker, paramMessage, null)) {
/*  862 */       log(paramMarker, FQCN, Level.INFO, paramMessage, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void info(Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*      */   {
/*  875 */     if (isEnabled(Level.INFO, paramMarker, paramMessage, paramThrowable)) {
/*  876 */       log(paramMarker, FQCN, Level.INFO, paramMessage, paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void info(Marker paramMarker, Object paramObject)
/*      */   {
/*  888 */     if (isEnabled(Level.INFO, paramMarker, paramObject, null)) {
/*  889 */       log(paramMarker, FQCN, Level.INFO, this.messageFactory.newMessage(paramObject), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void info(Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*      */   {
/*  903 */     if (isEnabled(Level.INFO, paramMarker, paramObject, paramThrowable)) {
/*  904 */       log(paramMarker, FQCN, Level.INFO, this.messageFactory.newMessage(paramObject), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void info(Marker paramMarker, String paramString)
/*      */   {
/*  916 */     if (isEnabled(Level.INFO, paramMarker, paramString)) {
/*  917 */       log(paramMarker, FQCN, Level.INFO, this.messageFactory.newMessage(paramString), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void info(Marker paramMarker, String paramString, Object... paramVarArgs)
/*      */   {
/*  930 */     if (isEnabled(Level.INFO, paramMarker, paramString, paramVarArgs)) {
/*  931 */       Message localMessage = this.messageFactory.newMessage(paramString, paramVarArgs);
/*  932 */       log(paramMarker, FQCN, Level.INFO, localMessage, localMessage.getThrowable());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void info(Marker paramMarker, String paramString, Throwable paramThrowable)
/*      */   {
/*  946 */     if (isEnabled(Level.INFO, paramMarker, paramString, paramThrowable)) {
/*  947 */       log(paramMarker, FQCN, Level.INFO, this.messageFactory.newMessage(paramString), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void info(Message paramMessage)
/*      */   {
/*  958 */     if (isEnabled(Level.INFO, null, paramMessage, null)) {
/*  959 */       log(null, FQCN, Level.INFO, paramMessage, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void info(Message paramMessage, Throwable paramThrowable)
/*      */   {
/*  971 */     if (isEnabled(Level.INFO, null, paramMessage, paramThrowable)) {
/*  972 */       log(null, FQCN, Level.INFO, paramMessage, paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void info(Object paramObject)
/*      */   {
/*  983 */     if (isEnabled(Level.INFO, null, paramObject, null)) {
/*  984 */       log(null, FQCN, Level.INFO, this.messageFactory.newMessage(paramObject), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void info(Object paramObject, Throwable paramThrowable)
/*      */   {
/*  998 */     if (isEnabled(Level.INFO, null, paramObject, paramThrowable)) {
/*  999 */       log(null, FQCN, Level.INFO, this.messageFactory.newMessage(paramObject), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void info(String paramString)
/*      */   {
/* 1010 */     if (isEnabled(Level.INFO, null, paramString)) {
/* 1011 */       log(null, FQCN, Level.INFO, this.messageFactory.newMessage(paramString), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void info(String paramString, Object... paramVarArgs)
/*      */   {
/* 1023 */     if (isEnabled(Level.INFO, null, paramString, paramVarArgs)) {
/* 1024 */       Message localMessage = this.messageFactory.newMessage(paramString, paramVarArgs);
/* 1025 */       log(null, FQCN, Level.INFO, localMessage, localMessage.getThrowable());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void info(String paramString, Throwable paramThrowable)
/*      */   {
/* 1038 */     if (isEnabled(Level.INFO, null, paramString, paramThrowable)) {
/* 1039 */       log(null, FQCN, Level.INFO, this.messageFactory.newMessage(paramString), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isDebugEnabled()
/*      */   {
/* 1051 */     return isEnabled(Level.DEBUG, null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isDebugEnabled(Marker paramMarker)
/*      */   {
/* 1063 */     return isEnabled(Level.DEBUG, paramMarker, (Object)null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isEnabled(Level paramLevel)
/*      */   {
/* 1076 */     return isEnabled(paramLevel, null, (Object)null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract boolean isEnabled(Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract boolean isEnabled(Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected abstract boolean isEnabled(Level paramLevel, Marker paramMarker, String paramString, Throwable paramThrowable);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isErrorEnabled()
/*      */   {
/* 1136 */     return isEnabled(Level.ERROR, null, (Object)null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isErrorEnabled(Marker paramMarker)
/*      */   {
/* 1148 */     return isEnabled(Level.ERROR, paramMarker, (Object)null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isFatalEnabled()
/*      */   {
/* 1159 */     return isEnabled(Level.FATAL, null, (Object)null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isFatalEnabled(Marker paramMarker)
/*      */   {
/* 1171 */     return isEnabled(Level.FATAL, paramMarker, (Object)null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isInfoEnabled()
/*      */   {
/* 1182 */     return isEnabled(Level.INFO, null, (Object)null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isInfoEnabled(Marker paramMarker)
/*      */   {
/* 1193 */     return isEnabled(Level.INFO, paramMarker, (Object)null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isTraceEnabled()
/*      */   {
/* 1204 */     return isEnabled(Level.TRACE, null, (Object)null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isTraceEnabled(Marker paramMarker)
/*      */   {
/* 1216 */     return isEnabled(Level.TRACE, paramMarker, (Object)null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isWarnEnabled()
/*      */   {
/* 1228 */     return isEnabled(Level.WARN, null, (Object)null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isWarnEnabled(Marker paramMarker)
/*      */   {
/* 1240 */     return isEnabled(Level.WARN, paramMarker, (Object)null, null);
/*      */   }
/*      */   
/*      */   public boolean isEnabled(Level paramLevel, Marker paramMarker)
/*      */   {
/* 1245 */     return isEnabled(paramLevel, paramMarker, (Object)null, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void log(Level paramLevel, Marker paramMarker, Message paramMessage)
/*      */   {
/* 1257 */     if (isEnabled(paramLevel, paramMarker, paramMessage, null)) {
/* 1258 */       log(paramMarker, FQCN, paramLevel, paramMessage, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void log(Level paramLevel, Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*      */   {
/* 1272 */     if (isEnabled(paramLevel, paramMarker, paramMessage, paramThrowable)) {
/* 1273 */       log(paramMarker, FQCN, paramLevel, paramMessage, paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void log(Level paramLevel, Marker paramMarker, Object paramObject)
/*      */   {
/* 1286 */     if (isEnabled(paramLevel, paramMarker, paramObject, null)) {
/* 1287 */       log(paramMarker, FQCN, paramLevel, this.messageFactory.newMessage(paramObject), null);
/*      */     }
/*      */   }
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
/*      */   public void log(Level paramLevel, Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*      */   {
/* 1302 */     if (isEnabled(paramLevel, paramMarker, paramObject, paramThrowable)) {
/* 1303 */       log(paramMarker, FQCN, paramLevel, this.messageFactory.newMessage(paramObject), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void log(Level paramLevel, Marker paramMarker, String paramString)
/*      */   {
/* 1316 */     if (isEnabled(paramLevel, paramMarker, paramString)) {
/* 1317 */       log(paramMarker, FQCN, paramLevel, this.messageFactory.newMessage(paramString), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void log(Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs)
/*      */   {
/* 1331 */     if (isEnabled(paramLevel, paramMarker, paramString, paramVarArgs)) {
/* 1332 */       Message localMessage = this.messageFactory.newMessage(paramString, paramVarArgs);
/* 1333 */       log(paramMarker, FQCN, paramLevel, localMessage, localMessage.getThrowable());
/*      */     }
/*      */   }
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
/*      */   public void log(Level paramLevel, Marker paramMarker, String paramString, Throwable paramThrowable)
/*      */   {
/* 1348 */     if (isEnabled(paramLevel, paramMarker, paramString, paramThrowable)) {
/* 1349 */       log(paramMarker, FQCN, paramLevel, this.messageFactory.newMessage(paramString), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void log(Level paramLevel, Message paramMessage)
/*      */   {
/* 1361 */     if (isEnabled(paramLevel, null, paramMessage, null)) {
/* 1362 */       log(null, FQCN, paramLevel, paramMessage, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void log(Level paramLevel, Message paramMessage, Throwable paramThrowable)
/*      */   {
/* 1375 */     if (isEnabled(paramLevel, null, paramMessage, paramThrowable)) {
/* 1376 */       log(null, FQCN, paramLevel, paramMessage, paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void log(Level paramLevel, Object paramObject)
/*      */   {
/* 1388 */     if (isEnabled(paramLevel, null, paramObject, null)) {
/* 1389 */       log(null, FQCN, paramLevel, this.messageFactory.newMessage(paramObject), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void log(Level paramLevel, Object paramObject, Throwable paramThrowable)
/*      */   {
/* 1403 */     if (isEnabled(paramLevel, null, paramObject, paramThrowable)) {
/* 1404 */       log(null, FQCN, paramLevel, this.messageFactory.newMessage(paramObject), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void log(Level paramLevel, String paramString)
/*      */   {
/* 1416 */     if (isEnabled(paramLevel, null, paramString)) {
/* 1417 */       log(null, FQCN, paramLevel, this.messageFactory.newMessage(paramString), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void log(Level paramLevel, String paramString, Object... paramVarArgs)
/*      */   {
/* 1430 */     if (isEnabled(paramLevel, null, paramString, paramVarArgs)) {
/* 1431 */       Message localMessage = this.messageFactory.newMessage(paramString, paramVarArgs);
/* 1432 */       log(null, FQCN, paramLevel, localMessage, localMessage.getThrowable());
/*      */     }
/*      */   }
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
/*      */   public void log(Level paramLevel, String paramString, Throwable paramThrowable)
/*      */   {
/* 1447 */     if (isEnabled(paramLevel, null, paramString, paramThrowable)) {
/* 1448 */       log(null, FQCN, paramLevel, this.messageFactory.newMessage(paramString), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printf(Level paramLevel, String paramString, Object... paramVarArgs)
/*      */   {
/* 1460 */     if (isEnabled(paramLevel, null, paramString, paramVarArgs)) {
/* 1461 */       StringFormattedMessage localStringFormattedMessage = new StringFormattedMessage(paramString, paramVarArgs);
/* 1462 */       log(null, FQCN, paramLevel, localStringFormattedMessage, localStringFormattedMessage.getThrowable());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printf(Level paramLevel, Marker paramMarker, String paramString, Object... paramVarArgs)
/*      */   {
/* 1475 */     if (isEnabled(paramLevel, paramMarker, paramString, paramVarArgs)) {
/* 1476 */       StringFormattedMessage localStringFormattedMessage = new StringFormattedMessage(paramString, paramVarArgs);
/* 1477 */       log(paramMarker, FQCN, paramLevel, localStringFormattedMessage, localStringFormattedMessage.getThrowable());
/*      */     }
/*      */   }
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
/*      */   public abstract void log(Marker paramMarker, String paramString, Level paramLevel, Message paramMessage, Throwable paramThrowable);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T extends Throwable> T throwing(Level paramLevel, T paramT)
/*      */   {
/* 1502 */     return throwing(FQCN, paramLevel, paramT);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T extends Throwable> T throwing(T paramT)
/*      */   {
/* 1514 */     return throwing(FQCN, Level.ERROR, paramT);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected <T extends Throwable> T throwing(String paramString, Level paramLevel, T paramT)
/*      */   {
/* 1527 */     if (isEnabled(paramLevel, THROWING_MARKER, (Object)null, null)) {
/* 1528 */       log(THROWING_MARKER, paramString, paramLevel, this.messageFactory.newMessage("throwing"), paramT);
/*      */     }
/* 1530 */     return paramT;
/*      */   }
/*      */   
/*      */   private Message toExitMsg(Object paramObject) {
/* 1534 */     if (paramObject == null) {
/* 1535 */       return this.messageFactory.newMessage("exit");
/*      */     }
/* 1537 */     return this.messageFactory.newMessage("exit with(" + paramObject + ")");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1546 */     return this.name;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void trace(Marker paramMarker, Message paramMessage)
/*      */   {
/* 1557 */     if (isEnabled(Level.TRACE, paramMarker, paramMessage, null)) {
/* 1558 */       log(paramMarker, FQCN, Level.TRACE, paramMessage, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void trace(Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*      */   {
/* 1572 */     if (isEnabled(Level.TRACE, paramMarker, paramMessage, paramThrowable)) {
/* 1573 */       log(paramMarker, FQCN, Level.TRACE, paramMessage, paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void trace(Marker paramMarker, Object paramObject)
/*      */   {
/* 1585 */     if (isEnabled(Level.TRACE, paramMarker, paramObject, null)) {
/* 1586 */       log(paramMarker, FQCN, Level.TRACE, this.messageFactory.newMessage(paramObject), null);
/*      */     }
/*      */   }
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
/*      */   public void trace(Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*      */   {
/* 1604 */     if (isEnabled(Level.TRACE, paramMarker, paramObject, paramThrowable)) {
/* 1605 */       log(paramMarker, FQCN, Level.TRACE, this.messageFactory.newMessage(paramObject), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void trace(Marker paramMarker, String paramString)
/*      */   {
/* 1617 */     if (isEnabled(Level.TRACE, paramMarker, paramString)) {
/* 1618 */       log(paramMarker, FQCN, Level.TRACE, this.messageFactory.newMessage(paramString), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void trace(Marker paramMarker, String paramString, Object... paramVarArgs)
/*      */   {
/* 1631 */     if (isEnabled(Level.TRACE, paramMarker, paramString, paramVarArgs)) {
/* 1632 */       Message localMessage = this.messageFactory.newMessage(paramString, paramVarArgs);
/* 1633 */       log(paramMarker, FQCN, Level.TRACE, localMessage, localMessage.getThrowable());
/*      */     }
/*      */   }
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
/*      */   public void trace(Marker paramMarker, String paramString, Throwable paramThrowable)
/*      */   {
/* 1651 */     if (isEnabled(Level.TRACE, paramMarker, paramString, paramThrowable)) {
/* 1652 */       log(paramMarker, FQCN, Level.TRACE, this.messageFactory.newMessage(paramString), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void trace(Message paramMessage)
/*      */   {
/* 1663 */     if (isEnabled(Level.TRACE, null, paramMessage, null)) {
/* 1664 */       log(null, FQCN, Level.TRACE, paramMessage, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void trace(Message paramMessage, Throwable paramThrowable)
/*      */   {
/* 1676 */     if (isEnabled(Level.TRACE, null, paramMessage, paramThrowable)) {
/* 1677 */       log(null, FQCN, Level.TRACE, paramMessage, paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void trace(Object paramObject)
/*      */   {
/* 1688 */     if (isEnabled(Level.TRACE, null, paramObject, null)) {
/* 1689 */       log(null, FQCN, Level.TRACE, this.messageFactory.newMessage(paramObject), null);
/*      */     }
/*      */   }
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
/*      */   public void trace(Object paramObject, Throwable paramThrowable)
/*      */   {
/* 1706 */     if (isEnabled(Level.TRACE, null, paramObject, paramThrowable)) {
/* 1707 */       log(null, FQCN, Level.TRACE, this.messageFactory.newMessage(paramObject), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void trace(String paramString)
/*      */   {
/* 1718 */     if (isEnabled(Level.TRACE, null, paramString)) {
/* 1719 */       log(null, FQCN, Level.TRACE, this.messageFactory.newMessage(paramString), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void trace(String paramString, Object... paramVarArgs)
/*      */   {
/* 1731 */     if (isEnabled(Level.TRACE, null, paramString, paramVarArgs)) {
/* 1732 */       Message localMessage = this.messageFactory.newMessage(paramString, paramVarArgs);
/* 1733 */       log(null, FQCN, Level.TRACE, localMessage, localMessage.getThrowable());
/*      */     }
/*      */   }
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
/*      */   public void trace(String paramString, Throwable paramThrowable)
/*      */   {
/* 1750 */     if (isEnabled(Level.TRACE, null, paramString, paramThrowable)) {
/* 1751 */       log(null, FQCN, Level.TRACE, this.messageFactory.newMessage(paramString), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void warn(Marker paramMarker, Message paramMessage)
/*      */   {
/* 1763 */     if (isEnabled(Level.WARN, paramMarker, paramMessage, null)) {
/* 1764 */       log(paramMarker, FQCN, Level.WARN, paramMessage, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void warn(Marker paramMarker, Message paramMessage, Throwable paramThrowable)
/*      */   {
/* 1777 */     if (isEnabled(Level.WARN, paramMarker, paramMessage, paramThrowable)) {
/* 1778 */       log(paramMarker, FQCN, Level.WARN, paramMessage, paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void warn(Marker paramMarker, Object paramObject)
/*      */   {
/* 1790 */     if (isEnabled(Level.WARN, paramMarker, paramObject, null)) {
/* 1791 */       log(paramMarker, FQCN, Level.WARN, this.messageFactory.newMessage(paramObject), null);
/*      */     }
/*      */   }
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
/*      */   public void warn(Marker paramMarker, Object paramObject, Throwable paramThrowable)
/*      */   {
/* 1811 */     if (isEnabled(Level.WARN, paramMarker, paramObject, paramThrowable)) {
/* 1812 */       log(paramMarker, FQCN, Level.WARN, this.messageFactory.newMessage(paramObject), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void warn(Marker paramMarker, String paramString)
/*      */   {
/* 1824 */     if (isEnabled(Level.WARN, paramMarker, paramString)) {
/* 1825 */       log(paramMarker, FQCN, Level.WARN, this.messageFactory.newMessage(paramString), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void warn(Marker paramMarker, String paramString, Object... paramVarArgs)
/*      */   {
/* 1838 */     if (isEnabled(Level.WARN, paramMarker, paramString, paramVarArgs)) {
/* 1839 */       Message localMessage = this.messageFactory.newMessage(paramString, paramVarArgs);
/* 1840 */       log(paramMarker, FQCN, Level.WARN, localMessage, localMessage.getThrowable());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void warn(Marker paramMarker, String paramString, Throwable paramThrowable)
/*      */   {
/* 1854 */     if (isEnabled(Level.WARN, paramMarker, paramString, paramThrowable)) {
/* 1855 */       log(paramMarker, FQCN, Level.WARN, this.messageFactory.newMessage(paramString), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void warn(Message paramMessage)
/*      */   {
/* 1866 */     if (isEnabled(Level.WARN, null, paramMessage, null)) {
/* 1867 */       log(null, FQCN, Level.WARN, paramMessage, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void warn(Message paramMessage, Throwable paramThrowable)
/*      */   {
/* 1879 */     if (isEnabled(Level.WARN, null, paramMessage, paramThrowable)) {
/* 1880 */       log(null, FQCN, Level.WARN, paramMessage, paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void warn(Object paramObject)
/*      */   {
/* 1891 */     if (isEnabled(Level.WARN, null, paramObject, null)) {
/* 1892 */       log(null, FQCN, Level.WARN, this.messageFactory.newMessage(paramObject), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void warn(Object paramObject, Throwable paramThrowable)
/*      */   {
/* 1905 */     if (isEnabled(Level.WARN, null, paramObject, paramThrowable)) {
/* 1906 */       log(null, FQCN, Level.WARN, this.messageFactory.newMessage(paramObject), paramThrowable);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void warn(String paramString)
/*      */   {
/* 1917 */     if (isEnabled(Level.WARN, null, paramString)) {
/* 1918 */       log(null, FQCN, Level.WARN, this.messageFactory.newMessage(paramString), null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void warn(String paramString, Object... paramVarArgs)
/*      */   {
/* 1930 */     if (isEnabled(Level.WARN, null, paramString, paramVarArgs)) {
/* 1931 */       Message localMessage = this.messageFactory.newMessage(paramString, paramVarArgs);
/* 1932 */       log(null, FQCN, Level.WARN, localMessage, localMessage.getThrowable());
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void warn(String paramString, Throwable paramThrowable)
/*      */   {
/* 1945 */     if (isEnabled(Level.WARN, null, paramString, paramThrowable)) {
/* 1946 */       log(null, FQCN, Level.WARN, this.messageFactory.newMessage(paramString), paramThrowable);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\spi\AbstractLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */