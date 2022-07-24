package com.zhuweitung.dbp.push;

import cn.hutool.core.util.StrUtil;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 钉钉群聊机器人消息推送工具类
 *
 * @author zhuweitung
 * @create 2022/7/21
 */
public class DingBotPushHelper {

    public static final String URL = "https://oapi.dingtalk.com/robot/send?access_token=";

    /**
     * 消息推送
     *
     * @param config
     * @return boolean
     * @author zhuweitung
     * @date 2022/7/21
     */
    public static boolean push(DingBotPushConfig config) {

        DingTalkClient client = getDingTalkClient(config.getToken(), config.getSecret());
        int tryTimes = 0;
        boolean pushSuccess = false;
        OapiRobotSendResponse response = null;
        while (!pushSuccess && tryTimes <= config.getRetryNum()) {
            response = doPush(client, config);
            pushSuccess = checkResponse(response);
            tryTimes++;
            if (!pushSuccess) {
                System.err.println(response.getErrmsg());
                System.err.println(StrUtil.format("消息推送失败，开始第{}次重试", tryTimes));
                if (config.getRetryInterval() > 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(config.getRetryInterval());
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
        return pushSuccess;
    }

    /**
     * 获取消息发送客户端
     *
     * @param token
     * @param secret
     * @return java.lang.String
     * @author zhuweitung
     * @date 2022/7/21
     */
    private static DingTalkClient getDingTalkClient(String token, String secret) {
        String url = URL + token;
        // 设置时间戳和签名
        if (StrUtil.isNotBlank(secret)) {
            long timestamp = System.currentTimeMillis();
            String sign = null;
            try {
                String stringToSign = timestamp + "\n" + secret;
                Mac mac = Mac.getInstance("HmacSHA256");
                mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
                byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
                sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
            } catch (Exception e) {
                System.err.println("生成签名失败");
                e.printStackTrace();
            }
            if (StrUtil.isNotBlank(sign)) {
                url += StrUtil.format("&timestamp={}&sign={}", timestamp, sign);
            }
        }

        return new DefaultDingTalkClient(url);
    }

    /**
     * 执行消息推送
     *
     * @param client
     * @param config
     * @return com.dingtalk.api.response.OapiRobotSendResponse
     * @author zhuweitung
     * @date 2022/7/21
     */
    private static OapiRobotSendResponse doPush(DingTalkClient client, DingBotPushConfig config) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        request.setMsgtype(config.getMsgType().name());

        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setAtMobiles(idsToList(config.getAtMobiles()));
        at.setAtUserIds(idsToList(config.getAtUserIds()));
        at.setIsAtAll(config.isAtAll());
        switch (config.getMsgType()) {
            case text:
                OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
                text.setContent(config.getText());
                request.setText(text);
                request.setAt(at);
                break;
            case link:
                OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
                link.setTitle(config.getTitle());
                link.setText(config.getText());
                link.setMessageUrl(config.getMsgUrl());
                link.setPicUrl(config.getPicUrl());
                request.setLink(link);
                break;
            case markdown:
                OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
                markdown.setTitle(config.getTitle());
                markdown.setText(config.getText());
                request.setMarkdown(markdown);
                request.setAt(at);
                break;
        }
        OapiRobotSendResponse response = null;
        try {
            response = client.execute(request);
        } catch (ApiException e) {
            System.err.println("钉钉机器人推送消息失败");
            e.printStackTrace();
        }
        return response;
    }

    /**
     * ids格式转换为集合
     *
     * @param ids
     * @return java.util.List<java.lang.String>
     * @author zhuweitung
     * @date 2022/7/21
     */
    private static List<String> idsToList(String ids) {
        if (StrUtil.isBlank(ids)) {
            return new ArrayList<>();
        }
        return Arrays.stream(ids.split(",")).filter(id -> StrUtil.isNotBlank(id)).collect(Collectors.toList());
    }

    /**
     * 判断消息是否发送成功
     *
     * @param response
     * @return boolean
     * @author zhuweitung
     * @date 2022/7/21
     */
    private static boolean checkResponse(OapiRobotSendResponse response) {
        if (response == null) {
            return false;
        }
        Long responseErrcode = response.getErrcode();
        String responseErrmsg = response.getErrmsg();
        if (null == responseErrcode || null == responseErrmsg) {
            return false;
        }
        return responseErrcode == 0 && "ok".equals(responseErrmsg);
    }


}
