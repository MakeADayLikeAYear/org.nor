package org.nor.sftp;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class SFtpUtilsTest {

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
  public void newSession() throws Exception {
    URI uri = new URI(prop.getProperty("sftpURI"));
    assertTrue(SFtpUtils.newSession(uri).isConnected());
  }


  @Test
  public void sshCopy() throws Exception {

    String fileName = "양식.xlsx";
    String fromPath = "D:/work/TEST/SEND";
    String toPath = "D:/work/TEST/RECV";

    String serverPath = prop.getProperty("sftpURI");

    SFtpUtils.sshCopy(fromPath + "/" + fileName, serverPath);
    SFtpUtils.sshCopy(serverPath + "/" + fileName, toPath);

    File fromFile = new File(fromPath + "/" + fileName);
    File toFile = new File(toPath + "/" + fileName);

    assertTrue(toFile.isFile());
    assertTrue(fromFile.length() == toFile.length());
  }
}
