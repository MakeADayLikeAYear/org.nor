package org.nor.sftp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class FtpInfoTest {
  
  Properties prop = new Properties();

  @Before
  public void init() throws Exception {

    InputStream input = null;
    // sftpURI.txt File
    // sftpURI=ssh://user:pass@host:ip/path
    input = new FileInputStream("D:/03_PRO/workspace/sftpURI.txt");

    // load a properties file
    prop.load(input);
  }
  
  @Test
  public void FtpInfo() throws Exception {
    URI uri = new URI(prop.getProperty("sftpURI"));
    Object host = FtpInfo.HOST.get(uri);
    System.out.println(host);
  }
}
