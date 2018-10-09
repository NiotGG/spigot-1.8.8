package org.yaml.snakeyaml.emitter;

import java.io.IOException;

abstract interface EmitterState
{
  public abstract void expect()
    throws IOException;
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\emitter\EmitterState.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */