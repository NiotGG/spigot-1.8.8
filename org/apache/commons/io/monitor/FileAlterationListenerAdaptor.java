package org.apache.commons.io.monitor;

import java.io.File;

public class FileAlterationListenerAdaptor
  implements FileAlterationListener
{
  public void onStart(FileAlterationObserver paramFileAlterationObserver) {}
  
  public void onDirectoryCreate(File paramFile) {}
  
  public void onDirectoryChange(File paramFile) {}
  
  public void onDirectoryDelete(File paramFile) {}
  
  public void onFileCreate(File paramFile) {}
  
  public void onFileChange(File paramFile) {}
  
  public void onFileDelete(File paramFile) {}
  
  public void onStop(FileAlterationObserver paramFileAlterationObserver) {}
}


/* Location:              C:\Users\USUARIO\Desktop\spigot-1.8.8-R0.1-SNAPSHOT-latest.jar!\org\apache\commons\io\monitor\FileAlterationListenerAdaptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */