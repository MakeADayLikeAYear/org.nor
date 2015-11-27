package org.nor.sftp;

import java.net.URI;

public enum SFtpInfo {

  USER {
    String get(URI uri) {
      return uri.getUserInfo().split(":")[0];
    }
  },
  PASS {
    String get(URI uri) {
      return uri.getUserInfo().split(":")[1];
    }
  },
  HOST {
    String get(URI uri) {
      return uri.getHost();
    }
  },
  PORT {
    Object get(URI uri) {
      return uri.getPort() < 0 ? 22 : uri.getPort();
    }
  };

  abstract Object get(URI uri);
}
