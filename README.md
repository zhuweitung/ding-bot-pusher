# 钉钉群聊机器人消息推送工具

## 实现功能

- [x] 发送text类型消息
- [x] 发送link类型消息
- [x] 发送markdown类型消息

## 使用方式

### 查看帮助

```bash
java -jar ding-bot-pusher.jar -h
usage: ding-bot-pusher
    --at-mobiles <atMobiles>           被@人的手机号
    --at-user-ids <atUserIds>          被@人的用户userid
 -h,--help                             帮助
    --is-at-all                        是否@所有人
 -m,--msg-type <msgType>               消息类型:text,link,markdown
    --msg-url <msgUrl>                 点击消息跳转的URL
    --pic-url <picUrl>                 图片URL
    --retry-interval <retryInterval>   消息发送失败重试间隔，单位毫秒
    --retry-num <retryNum>             消息发送失败重试次数
 -s,--secret <secret>                  加签密钥
 -t,--token <accessToken>              机器人webhook中的access_token参数
    --text <text>                      消息内容
    --title <title>                    消息标题
 -v,--version                          查看版本
```

### 查看版本

```bash
java -jar ding-bot-pusher.jar -v
```

### 发送text类型消息

```bash
java -jar ding-bot-pusher.jar -t your_token -s your_secret -m text --text "your_text"
```

### 发送link类型消息

```bash
java -jar ding-bot-pusher.jar -t your_token -s your_secret -m link --title "your_title" --text "your_text" --msg-url "your_msg_url"
```

### 发送markdown类型消息

```bash
java -jar ding-bot-pusher.jar -t your_token -s your_secret -m markdown --title "your_title" --text "your_markdown"
```

### 其他问题

windows执行乱码

```bash
java -Dfile.encoding=utf-8 -jar ...
```

通过指定编码方式来解决
