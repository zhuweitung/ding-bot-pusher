package com.zhuweitung.dbp.cli;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * 命令行工具类
 *
 * @author zhuweitung
 * @create 2022/7/21
 */
public class CommandLineHelper {

    private final static PrintWriter pw = new PrintWriter(System.out);

    public final static Options options = new Options();

    static {
        options.addOption(CommandLineOption.Help.getOption());
        options.addOption(CommandLineOption.Version.getOption());
        options.addOption(CommandLineOption.Token.getOption());
        options.addOption(CommandLineOption.Secret.getOption());
        options.addOption(CommandLineOption.MsgType.getOption());
        options.addOption(CommandLineOption.Title.getOption());
        options.addOption(CommandLineOption.Text.getOption());
        options.addOption(CommandLineOption.AtMobiles.getOption());
        options.addOption(CommandLineOption.AtUserIds.getOption());
        options.addOption(CommandLineOption.IsAtAll.getOption());
        options.addOption(CommandLineOption.MsgUrl.getOption());
        options.addOption(CommandLineOption.PicUrl.getOption());
        options.addOption(CommandLineOption.RetryNum.getOption());
        options.addOption(CommandLineOption.RetryInterval.getOption());
    }

    /**
     * 打印帮助信息
     *
     * @param
     * @return void
     * @author zhuweitung
     * @date 2022/7/21
     */
    public static void help() {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("ding-bot-pusher", options);
    }

    /**
     * 打印版本信息
     *
     * @param
     * @return void
     * @author zhuweitung
     * @date 2022/7/21
     */
    public static void version() {
        String path = "/META-INF/maven/com.zhuweitung/ding-bot-pusher/pom.properties";
        InputStream stream = CommandLineHelper.class.getResourceAsStream(path);
        Properties properties = new Properties();
        try {
            if (stream != null) {
                properties.load(stream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.println(properties.getProperty("version"));
        pw.flush();
    }

}
