package com.avaje.ebean.enhance.asm;

class Edge
{
  static final int NORMAL = 0;
  static final int EXCEPTION = Integer.MAX_VALUE;
  int info;
  Label successor;
  Edge next;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\enhance\asm\Edge.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */