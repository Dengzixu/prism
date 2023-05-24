package net.dengzixu.prism.service.impl;

import net.dengzixu.bilvedanmaku.message.body.SimpleMessageBody;
import net.dengzixu.bilvedanmaku.message.content.DanmuContent;
import net.dengzixu.bilvedanmaku.message.content.GuardBuyContent;
import net.dengzixu.bilvedanmaku.message.content.InteractWordContent;
import net.dengzixu.bilvedanmaku.message.content.SendGiftContent;
import net.dengzixu.bilvedanmaku.message.metadata.TimestampMetadata;
import net.dengzixu.bilvedanmaku.message.metadata.UserMetadata;
import net.dengzixu.prism.mapper.SaveMapper;
import net.dengzixu.prism.service.SaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveServiceImpl implements SaveService {
    // LOGGER
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SaveServiceImpl.class);

    private final SaveMapper saveMapper;

    @Autowired
    public SaveServiceImpl(SaveMapper saveMapper) {
        this.saveMapper = saveMapper;
    }

    @Override
    public void save(Long roomID, SimpleMessageBody<?> simpleMessageBody) {

        UserMetadata userMetadata = simpleMessageBody.userMetadata().isPresent() ? simpleMessageBody.userMetadata().get() : null;
        TimestampMetadata timestampMetadata = simpleMessageBody.timestampMetadata().get();

        switch (simpleMessageBody.message()) {
            case DANMU_MSG -> {
                DanmuContent content = (DanmuContent) simpleMessageBody.content();

                saveMapper.saveDanmu(roomID,
                        userMetadata.uid(), userMetadata.username(),
                        content.text(),
                        timestampMetadata.timestamp());

            }
            case INTERACT_WORD -> {
                InteractWordContent content = (InteractWordContent) simpleMessageBody.content();

                saveMapper.saveInteractWord(roomID,
                        userMetadata.uid(), userMetadata.username(),
                        content.msgType(),
                        timestampMetadata.timestamp());
            }
            case SEND_GIFT -> {
                SendGiftContent content = (SendGiftContent) simpleMessageBody.content();

                saveMapper.saveSendGift(roomID,
                        userMetadata.uid(), userMetadata.username(),
                        content.giftId(), content.giftName(), content.num(),
                        content.coinType(), content.price(), content.discountPrice(),
                        content.totalCoin(), timestampMetadata.timestamp());

            }
            case GUARD_BUY -> {
                GuardBuyContent content = (GuardBuyContent) simpleMessageBody.content();
            }

            default -> logger.debug("忽略");
        }


    }
}
