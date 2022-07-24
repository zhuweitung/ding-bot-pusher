package com.zhuweitung.dbp.cli;

import org.apache.commons.cli.Option;

/**
 * 命令行选项
 *
 * @author zhuweitung
 * @create 2022/7/21
 */
public enum CommandLineOption {

    Help(Option.builder("h").longOpt("help").desc("帮助").build()),
    Version(Option.builder("v").longOpt("version").desc("查看版本").build()),
    Token(Option.builder("t").longOpt("token").hasArg().argName("accessToken").desc("机器人webhook中的access_token参数").build()),
    Secret(Option.builder("s").longOpt("secret").hasArg().argName("secret").desc("加签密钥").build()),
    MsgType(Option.builder("m").longOpt("msg-type").hasArg().argName("msgType").desc("消息类型:" + com.zhuweitung.dbp.constant.MsgType.print()).build()),
    Title(Option.builder().longOpt("title").hasArg().desc("消息标题").argName("title").build()),
    Text(Option.builder().longOpt("text").hasArg().desc("消息内容").argName("text").build()),
    AtMobiles(Option.builder().longOpt("at-mobiles").hasArg().argName("atMobiles").desc("被@人的手机号").build()),
    AtUserIds(Option.builder().longOpt("at-user-ids").hasArg().argName("atUserIds").desc("被@人的用户userid").build()),
    IsAtAll(Option.builder().longOpt("is-at-all").desc("是否@所有人").build()),
    MsgUrl(Option.builder().longOpt("msg-url").hasArg().argName("msgUrl").desc("点击消息跳转的URL").build()),
    PicUrl(Option.builder().longOpt("pic-url").hasArg().argName("picUrl").desc("图片URL").build()),
    RetryNum(Option.builder().longOpt("retry-num").hasArg().argName("retryNum").desc("消息发送失败重试次数").build()),
    RetryInterval(Option.builder().longOpt("retry-interval").hasArg().argName("retryInterval").desc("消息发送失败重试间隔，单位毫秒").build());

    private Option option;

    public Option getOption() {
        return option;
    }

    CommandLineOption(Option option) {
        this.option = option;
    }
}
