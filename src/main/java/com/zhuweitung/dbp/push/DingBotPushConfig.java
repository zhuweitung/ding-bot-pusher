package com.zhuweitung.dbp.push;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.zhuweitung.dbp.cli.CommandLineOption;
import com.zhuweitung.dbp.constant.MsgType;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

/**
 * 钉钉群聊机器人推送配置
 *
 * @author zhuweitung
 * @create 2022/7/21
 */
@Getter
@Builder
public class DingBotPushConfig {

    public static final int DEFAULT_RETRY_NUM = 3;
    public static final int DEFAULT_RETRY_INTERVAL = 1000;

    private String token;

    private String secret;

    private MsgType msgType;

    private String title;

    private String text;

    private String atMobiles;

    private String atUserIds;

    private boolean isAtAll;

    private String msgUrl;

    private String picUrl;

    private int retryNum;

    private long retryInterval;

    /**
     * 验证并初始化推送配置
     *
     * @param cmd
     * @return com.zhuweitung.dbp.push.DingBotPushConfig
     * @author zhuweitung
     * @date 2022/7/21
     */
    public static DingBotPushConfig init(CommandLine cmd) {

        DingBotPushConfig config = DingBotPushConfig.builder()
                .token(getOptionValue(cmd, CommandLineOption.Token.getOption()))
                .secret(getOptionValue(cmd, CommandLineOption.Secret.getOption()))
                .msgType(getOptionMsgTypeValue(cmd))
                .title(getOptionValue(cmd, CommandLineOption.Title.getOption()))
                .text(getOptionValue(cmd, CommandLineOption.Text.getOption()))
                .atMobiles(getOptionValue(cmd, CommandLineOption.AtMobiles.getOption()))
                .atUserIds(getOptionValue(cmd, CommandLineOption.AtUserIds.getOption()))
                .isAtAll(hasOption(cmd, CommandLineOption.IsAtAll.getOption()))
                .msgUrl(getOptionValue(cmd, CommandLineOption.MsgUrl.getOption()))
                .picUrl(getOptionValue(cmd, CommandLineOption.PicUrl.getOption()))
                .retryNum(getOptionIntValue(cmd, CommandLineOption.RetryNum.getOption(), DEFAULT_RETRY_NUM))
                .retryInterval(getOptionLongValue(cmd, CommandLineOption.RetryNum.getOption(), DEFAULT_RETRY_INTERVAL))
                .build();

        if (StrUtil.isBlank(config.getToken())) {
            System.err.println("token不能为空");
            return null;
        }
        if (config.getMsgType() == null) {
            System.err.println("未知消息类型，仅支持以下类型：" + MsgType.print());
            return null;
        }
        if (StrUtil.isBlank(config.getText())) {
            System.err.println("消息内容不能为空");
            return null;
        }

        switch (config.getMsgType()) {
            case text:
                break;
            case link:
                if (StrUtil.isBlank(config.getTitle())) {
                    System.err.println("消息标题不能为空");
                    return null;
                }
                if (StrUtil.isBlank(config.getMsgUrl())) {
                    System.err.println("点击消息跳转的URL不能为空");
                    return null;
                }
                break;
            case markdown:
                if (StrUtil.isBlank(config.getTitle())) {
                    System.err.println("消息标题不能为空");
                    return null;
                }
                break;
        }

        return config;
    }

    /**
     * 获取选项值
     *
     * @param cmd
     * @param option
     * @return java.lang.String
     * @author zhuweitung
     * @date 2022/7/21
     */
    private static String getOptionValue(CommandLine cmd, Option option) {
        if (cmd.hasOption(option)) {
            return cmd.getOptionValue(option);
        }
        return null;
    }

    /**
     * 获取消息类型选项值
     *
     * @param cmd
     * @return com.zhuweitung.dbp.constant.MsgType
     * @author zhuweitung
     * @date 2022/7/21
     */
    private static MsgType getOptionMsgTypeValue(CommandLine cmd) {
        MsgType msgType = MsgType.text;
        String msgTypeOptionValue = getOptionValue(cmd, CommandLineOption.MsgType.getOption());
        if (msgTypeOptionValue != null) {
            msgType = null;
            if (MsgType.present(msgTypeOptionValue)) {
                msgType = MsgType.valueOf(msgTypeOptionValue);
            }
        }
        return msgType;
    }

    /**
     * 选项是否存在
     *
     * @param cmd
     * @param option
     * @return boolean
     * @author zhuweitung
     * @date 2022/7/21
     */
    private static boolean hasOption(CommandLine cmd, Option option) {
        return cmd.hasOption(option);
    }

    /**
     * 获取int类型选项值
     *
     * @param cmd
     * @param option
     * @param defaultValue
     * @return int
     * @author zhuweitung
     * @date 2022/7/21
     */
    private static int getOptionIntValue(CommandLine cmd, Option option, int defaultValue) {
        String value = getOptionValue(cmd, option);
        if (StrUtil.isNotBlank(value) && NumberUtil.isNumber(value)) {
            return Integer.parseInt(value);
        }
        return defaultValue;
    }

    /**
     * 获取long类型选项值
     *
     * @param cmd
     * @param option
     * @param defaultValue
     * @return long
     * @author zhuweitung
     * @date 2022/7/21
     */
    private static long getOptionLongValue(CommandLine cmd, Option option, long defaultValue) {
        String value = getOptionValue(cmd, option);
        if (StrUtil.isNotBlank(value) && NumberUtil.isNumber(value)) {
            return Long.parseLong(value);
        }
        return defaultValue;
    }

}
