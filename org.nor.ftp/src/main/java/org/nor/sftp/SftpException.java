package org.nor.sftp;

@SuppressWarnings("serial")
public class SftpException extends Exception {
  public int id;
  private Throwable cause = null;

  public SftpException(int id, String message) {
    super(message);
    this.id = id;
  }

  public SftpException(int id, String message, Throwable e) {
    super(message);
    this.id = id;
    this.cause = e;
  }

  public Throwable getCause() {
    return this.cause;
  }

  public String toString() {
    return this.id + ": " + getMessage();
  }
}
