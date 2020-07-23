//Copyright 2020 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.open.api.tool;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author qhsong
 * @since 2020-07-21
 */
public class SignatureUtil {

  public static String getSignature(String appkey,String secret,String timestamp){
    StringBuilder build = new StringBuilder();
    build.append(appkey).append("+")
        .append(secret).append("+")
        .append(timestamp);
    String raw = build.toString();
    String result = stringToMD5(raw);
    return result;
  }

  private static String stringToMD5(String plainText) {
    byte[] secretBytes = null;
    try {
      secretBytes = MessageDigest.getInstance("md5").digest(
          plainText.getBytes());
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("not found the md5");
    }
    String md5code = new BigInteger(1, secretBytes).toString(16);
    for (int i = 0; i < 32 - md5code.length(); i++) {
      md5code = "0" + md5code;
    }
    return md5code;
  }
}
