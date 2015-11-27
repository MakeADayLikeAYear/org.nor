package org.nor.sftp;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHManager
{
    private static final Logger LOGGER = Logger.getLogger(SSHManager.class.getName());
    private JSch jschSSHChannel;
    private String strUserName;
    private String strConnectionIP;
    private int intConnectionPort;
    private String strPassword;
    private Session sesConnection;
    private int intTimeOut;

    private void doCommonConstructorActions(String userName, String password, String connectionIP, String knownHostsFileName) {
        jschSSHChannel = new JSch();
        try     {
            jschSSHChannel.setKnownHosts(knownHostsFileName);
        } catch(JSchException jschX) {
            logError(jschX.getMessage());
        }
        
        strUserName = userName;
        strPassword = password;
        strConnectionIP = connectionIP;
    }
    
    /**
     * 
     * @param userName
     * @param password
     * @param connectionIP
     * @param knownHostsFileName
     */
    public SSHManager(String userName, String password, String connectionIP, String knownHostsFileName) {
        this(userName, password, connectionIP, knownHostsFileName, 22, 60000);
    }
    
    public SSHManager(String userName, String password, String connectionIP, String knownHostsFileName, int connectionPort) {
        this(userName, password, connectionIP, knownHostsFileName, connectionPort, 60000);
    }
    
    public SSHManager(String userName, String password, String connectionIP, String knownHostsFileName, int connectionPort, int timeOutMilliseconds) {
        doCommonConstructorActions(userName, password, connectionIP, knownHostsFileName);
        intConnectionPort = connectionPort;
        intTimeOut = timeOutMilliseconds;
    }
    
    public String connect() {
        String errorMessage = null;
        try {
            sesConnection = jschSSHChannel.getSession(strUserName, strConnectionIP, intConnectionPort);
            sesConnection.setPassword(strPassword);
//          System.out.println( strUserName + "//" + strConnectionIP + "//" + intConnectionPort + "//" + strPassword  );
            // UNCOMMENT THIS FOR TESTING PURPOSES, BUT DO NOT USE IN PRODUCTION
            sesConnection.setConfig("StrictHostKeyChecking", "no");
            sesConnection.connect(intTimeOut);
            System.out.println( sesConnection );
        } catch(JSchException jschX) {
            jschX.printStackTrace();
            errorMessage = jschX.getMessage();
        }
    
        return errorMessage;
    }
    
    public String sendCommand(String command) {
        StringBuilder outputBuffer = new StringBuilder();
    
        try {
            Channel channel = sesConnection.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            InputStream commandOutput = channel.getInputStream();
            channel.connect();
            int readByte = commandOutput.read();
    
            while(readByte != 0xffffffff) {
                outputBuffer.append((char)readByte);
                readByte = commandOutput.read();
            }
    
            channel.disconnect();
        } catch(IOException ioX) {
            ioX.printStackTrace();
            logWarning(ioX.getMessage());
            return null;
        } catch(JSchException jschX) {
            jschX.printStackTrace();
            logWarning(jschX.getMessage());
            return null;
        }
    
        return outputBuffer.toString();
    }
    
    public void close() {
        sesConnection.disconnect();
    }

    private String logError(String errorMessage) {
        if(errorMessage != null) {
            LOGGER.log(Level.SEVERE, "{0}:{1} - {2}", new Object[]{strConnectionIP, intConnectionPort, errorMessage});
        }
    
        return errorMessage;
    }
    
    private String logWarning(String warnMessage) {
        if(warnMessage != null) {
            LOGGER.log(Level.WARNING, "{0}:{1} - {2}", new Object[]{strConnectionIP, intConnectionPort, warnMessage});
        }
        return warnMessage;
    }
    
    /*
    @Test
      public void testSendCommand()
      {
         System.out.println("sendCommand");

         / **
          * YOU MUST CHANGE THE FOLLOWING
          * FILE_NAME: A FILE IN THE DIRECTORY
          * USER: LOGIN USER NAME
          * PASSWORD: PASSWORD FOR THAT USER
          * HOST: IP ADDRESS OF THE SSH SERVER
         ** /
         String command = "ls FILE_NAME";
         String userName = "USER";
         String password = "PASSWORD";
         String connectionIP = "HOST";
         SSHManager instance = new SSHManager(userName, password, connectionIP, "");
         String errorMessage = instance.connect();

         if(errorMessage != null)
         {
            System.out.println(errorMessage);
            fail();
         }

         String expResult = "FILE_NAME\n";
         // call sendCommand for each command and the output 
         //(without prompts) is returned
         String result = instance.sendCommand(command);
         // close only after all commands are sent
         instance.close();
         assertEquals(expResult, result);
      }
*/
}
