package com.zhuweitung.dbp;

import com.zhuweitung.dbp.cli.CommandLineHelper;
import com.zhuweitung.dbp.cli.CommandLineOption;
import com.zhuweitung.dbp.push.DingBotPushConfig;
import com.zhuweitung.dbp.push.DingBotPushHelper;
import org.apache.commons.cli.*;

/**
 * 程序入口
 *
 * @author zhuweitung
 * @create 2022/7/20
 */
public class PushRunner {

    public static void main(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(CommandLineHelper.options, args);
        if (cmd.hasOption(CommandLineOption.Help.getOption())) {
            CommandLineHelper.help();
            return;
        }
        if (cmd.hasOption(CommandLineOption.Version.getOption())) {
            CommandLineHelper.version();
            return;
        }

        // 生成参数对象
        DingBotPushConfig config = DingBotPushConfig.init(cmd);
        if (config == null) {
            return;
        }

        // 发送消息
        boolean pushSuccess = DingBotPushHelper.push(config);
        if (pushSuccess) {
            System.out.println("消息推送成功");
        } else {
            System.err.println("消息推送失败");
        }
    }
}
