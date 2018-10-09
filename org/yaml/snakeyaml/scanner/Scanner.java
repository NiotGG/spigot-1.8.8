package org.yaml.snakeyaml.scanner;

import org.yaml.snakeyaml.tokens.Token;
import org.yaml.snakeyaml.tokens.Token.ID;

public abstract interface Scanner
{
  public abstract boolean checkToken(Token.ID... paramVarArgs);
  
  public abstract Token peekToken();
  
  public abstract Token getToken();
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\yaml\snakeyaml\scanner\Scanner.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */