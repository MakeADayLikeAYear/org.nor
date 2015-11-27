package org.nor.sftp;

import static org.apache.commons.io.FilenameUtils.getFullPath;
import static org.apache.commons.io.FilenameUtils.getName;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SFtpUtils {

  private static final Logger log = LoggerFactory.getLogger(SFtpUtilsTest.class);

  /**
   * <pre>
   * from :  "D:/work/TEST/SEND/xx.aaa"
   * to : "ssh://user:pass@host:port/path";
   * <pre>
   * 
   * @param fromUri
   * @param toUri
   * @throws SftpException
   */
  public static void sshCopy(String fromUri, String toUri) throws SftpException {
    try {
      log.info("fromUri : [{}] toUri : [{}]", fromUri, toUri);
      URI from = new URI(fromUri);
      URI to = new URI(toUri);

      if ("ssh".equals(to.getScheme()) && !"ssh".equals(from.getScheme())) {
        upload(new File(fromUri).toURI(), to);
      } else if ("ssh".equals(from.getScheme()) && !"ssh".equals(to.getScheme())) {
        download(from, new File(toUri).toURI());
      } else {
        throw new SftpException(5000, fromUri + " --> " + toUri);
      }
    } catch (URISyntaxException e) {
      throw new SftpException(5100, "Cant not URL" + "\r\n" + e.getMessage());
    }
  }

  /**
   * @param from file
   * @param to directory
   */
  public static void upload(URI from, URI to) throws SftpException {
    Session session = newSession(to);
    try {
      upload(from, to, session);
    } finally {
      session.disconnect();
    }
  }

  /**
   * @param from
   * @param to
   * @param session
   * @throws SftpException
   */
  public static void upload(URI from, URI to, Session session) throws SftpException {
    try (FileInputStream fis = new FileInputStream(new File(from))) {
      ChannelSftp channelSftp = newChannel(session, "sftp");
      channelSftp.cd(to.getPath());
      channelSftp.put(fis, getName(from.getPath()), ChannelSftp.OVERWRITE);
    } catch (org.nor.sftp.SftpException se) {
      throw se;
    } catch (Exception e) {
      throw new SftpException(4000, "Can not upload file" + "\r\n" + e.getMessage());
    }
  }
  /**
   * @param from
   * @param to
   * @throws SftpException
   */
  public static void download(URI from, URI to) throws SftpException {
    Session session = newSession(from);
    try {
      download(from, to, session);
    } finally {
      session.disconnect();
    }
  }

  /**
   * @param from
   * @param to
   * @param session
   * @throws SftpException
   */
  public static void download(URI from, URI to, Session session) throws SftpException {
    File out = new File(new File(to), getName(from.getPath()));
    try (OutputStream os = new FileOutputStream(out);
        BufferedOutputStream bos = new BufferedOutputStream(os)) {
      ChannelSftp channelSftp = newChannel(session, "sftp");
      channelSftp.cd(getFullPath(from.getPath()));
      channelSftp.get(getName(from.getPath()), bos);
    } catch (org.nor.sftp.SftpException se) {
      throw se;
    } catch (Exception e) {
      throw new SftpException(3000, "Can not download file" + "\r\n" + e.getMessage());
    }
  }

  /**
   * @param session
   * @param type - session, sftp, shell, exec, x11, auth-agent@openssh.com, direct-tcpip,
   *        forwarded-tcpip, subsystem
   * @return
   * @throws SftpException
   */
  @SuppressWarnings("unchecked")
  private static <C extends Channel> C newChannel(Session session, String type) throws SftpException {
    try {
      Channel channel = session.openChannel(type);
      channel.connect();
      return (C) channel;
    } catch (JSchException e) {
      throw new SftpException(2000,
          "Can not create " + type + " channel for " + session.getHost() + "\r\n" + e.getMessage());
    }
  }

  /**
   * @param uri
   * @return
   * @throws SftpException
   */
  public static Session newSession(URI uri) throws SftpException {
    Map<String, String> props = new HashMap<>();

    // http://cafe.naver.com/aix/18654
    // ssh(sftp 포함) 접속 시 client에서 host key를 저장하여 접속하는 경우 client home dir에 .ssh/known_hosts에
    // RSA키를 생성하여 저장하고 Server와 접속 시에 해당 키를 참조해서 접속을 하는걸로 알고 있습니다.
    // http://blog.chonnom.com/10113874172
    // 인증키값저장없이 패스워드 찍고 로그인하기
    props.put("StrictHostKeyChecking", "no");
    return newSession(uri, props);
  }

  /**
   * @param uri
   * @param props
   * @return
   * @throws SftpException
   */
  private static Session newSession(URI uri, Map<String, String> props) throws SftpException {
    try {
      String user = (String) FtpInfo.USER.get(uri);
      String pass = (String) FtpInfo.PASS.get(uri);
      String host = (String) FtpInfo.HOST.get(uri);
      int port = (int) FtpInfo.PORT.get(uri);
      log.debug("Host : [{}] Port : [{}] User : [{}] Pass : [{}]",
          new Object[] {host, port, user, pass});

      JSch jsch = new JSch();
      Session session = jsch.getSession(user, host, port);
      session.setPassword(pass);
      Properties config = new Properties();
      config.putAll(props);
      session.setConfig(config);
      session.connect();
      return session;
    } catch (JSchException e) {
      throw new SftpException(1000, "Can not create ssh session " + uri + "\r\n" + e.getMessage());
    }
  }

  /**
   * @param session
   */
  public static void colse(Session session) {
    session.disconnect();
  }
}
