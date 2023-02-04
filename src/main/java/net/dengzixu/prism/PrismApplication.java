package net.dengzixu.prism;

import net.dengzixu.bilvedanmaku.BLiveDanmakuClient;
import net.dengzixu.bilvedanmaku.enums.Message;
import net.dengzixu.bilvedanmaku.message.body.SimpleMessageBody;
import net.dengzixu.bilvedanmaku.message.content.DanmuContent;
import net.dengzixu.prism.properties.PrismSetting;
import net.dengzixu.prism.service.SaveService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PrismSetting.class)
public class PrismApplication implements CommandLineRunner {
    // LOGGER
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(PrismApplication.class);

    private final SaveService saveService;
    private final PrismSetting prismSetting;

    @Autowired
    public PrismApplication(SaveService saveService, PrismSetting prismSetting) {
        this.saveService = saveService;
        this.prismSetting = prismSetting;
    }


    public static void main(String[] args) {
        SpringApplication.run(PrismApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        prismSetting.roomList().roomId().forEach(roomID -> {
            Logger.info("读取到配置直播间: {}", roomID);
            BLiveDanmakuClient bLiveDanmakuClient = BLiveDanmakuClient
                    .getInstance(roomID)
                    .addHandler(new Listener(roomID))
                    .connect();
        });

    }

    private class Listener implements net.dengzixu.bilvedanmaku.handler.Handler {
        private final Long roomID;

        public Listener(Long roomID) {
            this.roomID = roomID;
        }

        @Override
        public void doHandler(SimpleMessageBody<?> simpleMessageBody) {
            if (!Message._NULL.equals(simpleMessageBody.message())) {

                // 处理控制台输出
                if (simpleMessageBody.content() instanceof DanmuContent) {
                    DanmuContent.EmoticonContent emoticonContent = ((DanmuContent) simpleMessageBody.content()).emoticonContent();

                    Logger.info("[直播间: {}] {} {}", roomID,
                            simpleMessageBody.convertToString(),
                            emoticonContent != null ? emoticonContent.url() : "");
                } else {
                    Logger.info("[直播间: {}] {}", roomID, simpleMessageBody.convertToString());
                }

                // 保存
                saveService.save(roomID, simpleMessageBody);
            }
        }
    }
}

