package org.apache.logging.log4j.spi;

import java.util.Collection;
import org.apache.logging.log4j.ThreadContext.ContextStack;

public abstract interface ThreadContextStack
  extends ThreadContext.ContextStack, Collection<String>
{}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\spi\ThreadContextStack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */