package net.dengzixu.prism;

import net.dengzixu.bilvedanmaku.BLiveDanmakuClient;
import net.dengzixu.bilvedanmaku.enums.Message;
import net.dengzixu.bilvedanmaku.message.body.SimpleMessageBody;
import net.dengzixu.bilvedanmaku.message.content.DanmuContent;
import net.dengzixu.prism.service.SaveService;
import org.apache.commons.cli.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PrismApplication implements CommandLineRunner {
    // LOGGER
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PrismApplication.class);

    public static long ROOM_ID = 0;

    private final SaveService saveService;

    @Autowired
    public PrismApplication(SaveService saveService) {
        this.saveService = saveService;
    }

    public static void main(String[] args) {
        SpringApplication.run(PrismApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<String> nArgs = new ArrayList<>();


        for (String arg : args) {
            if (arg.contains("room-id")) {
                nArgs.add(arg);
            }
        }
        CommandLineParser parser = new DefaultParser();

        Options options = new Options()
                .addRequiredOption("ID", "room-id", true, "直播间 ID");

        try {
            CommandLine line = parser.parse(options, nArgs.toArray(new String[0]));

            if (!line.hasOption("room-id")) {
                LOGGER.error("缺少直播间ID");
                System.exit(1);
            }

            ROOM_ID = Long.parseLong(line.getOptionValue("room-id"));

            LOGGER.info("直播间ID: {}", ROOM_ID);
        } catch (ParseException e) {
            LOGGER.error("命令行解析错误", e);
            System.exit(1);
        }

        BLiveDanmakuClient bLiveDanmakuClient = BLiveDanmakuClient
                .getInstance(ROOM_ID)
                .addHandler(new Listener())
                .connect();
    }

    private class Listener implements net.dengzixu.bilvedanmaku.handler.Handler {
        @Override
        public void doHandler(SimpleMessageBody<?> simpleMessageBody) {
            if (!Message._NULL.equals(simpleMessageBody.message())) {

                // 处理控制台输出
                if (simpleMessageBody.content() instanceof DanmuContent) {
                    DanmuContent.EmoticonContent emoticonContent = ((DanmuContent) simpleMessageBody.content()).emoticonContent();

                    LOGGER.info("[直播间: {}] {} {}", ROOM_ID,
                            simpleMessageBody.convertToString(),
                            emoticonContent != null ? emoticonContent.url() : "");
                } else {
                    LOGGER.info("[直播间: {}] {}", ROOM_ID, simpleMessageBody.convertToString());
                }

                // 保存
                saveService.save(simpleMessageBody);
            }
        }
    }
}

