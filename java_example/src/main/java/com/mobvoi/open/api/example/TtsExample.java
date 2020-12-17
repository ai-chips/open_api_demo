//Copyright 2020 Mobvoi Inc. All Rights Reserved.
package com.mobvoi.open.api.example;

import com.alibaba.fastjson.JSONObject;
import com.mobvoi.open.api.tool.HttpClientUtil;
import com.mobvoi.open.api.tool.SignatureUtil;
import java.io.File;
import java.io.InputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

/**
 * @author qhsong
 * @since 2020-07-22
 * 调用mobvoi开放平台tts接口demo
 */
public class TtsExample {

  private static final String TTS_URL = "https://open.mobvoi.com/api/tts/v1";

  private static final String APPKEY = "你的appkey";

  private static final String SECRET = "你的secret";

  private static String path = "音频文件路径";

  public static void main(String[] args) {
    JSONObject params = new JSONObject();
    params.put("text","出门问问成立于2012年，是一家以语音交互和软硬结合为核心的人工智能公司，为全球40多个国家和地区的消费者、企业提供人工智能产品和服务。");
    params.put("net_type","wifi");
    params.put("platform","pc");
    params.put("speaker","tina");
    params.put("audio_type","mp3");
    params.put("speed","1.1");
    params.put("ignore_limit","true");
    params.put("appkey",APPKEY);
    String timestamp = System.currentTimeMillis()/1000 +"";
    params.put("timestamp",timestamp);
    params.put("signature", SignatureUtil.getSignature(APPKEY,SECRET,timestamp));
    try {
      CloseableHttpResponse response = HttpClientUtil
          .doPostJsonStreaming(TTS_URL, params.toJSONString());
      Header firstHeader = response.getFirstHeader("Content-Type");
      if(response.getEntity().isStreaming() &&
          !firstHeader.getValue().contains("application/json")){
        InputStream input = response.getEntity().getContent();
        byte[] bytes = IOUtils.toByteArray(input);
        FileUtils.writeByteArrayToFile(new File(path),bytes);
      }else {
        System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));
      }
    }catch (Exception e){
      e.printStackTrace();
    }


  }
}
