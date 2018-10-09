package com.google.common.eventbus;

import com.google.common.collect.Multimap;

abstract interface SubscriberFindingStrategy
{
  public abstract Multimap<Class<?>, EventSubscriber> findAllSubscribers(Object paramObject);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\google\common\eventbus\SubscriberFindingStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */