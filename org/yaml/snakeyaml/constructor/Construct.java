package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.Node;

public abstract interface Construct
{
  public abstract Object construct(Node paramNode);
  
  public abstract void construct2ndStep(Node paramNode, Object paramObject);
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\constructor\Construct.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */