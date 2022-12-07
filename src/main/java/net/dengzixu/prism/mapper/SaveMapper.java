package net.dengzixu.prism.mapper;

import net.dengzixu.prism.mapper.provider.SaveMapperProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SaveMapper {
    @InsertProvider(type = SaveMapperProvider.class, method = "saveDanmuSql")
    void saveDanmu(long roomID, long uid, String username, String content, long timestamp);

    @InsertProvider(type = SaveMapperProvider.class, method = "saveInteractWordSql")
    void saveInteractWord(long roomID, long uid, String username, int type, long timestamp);

    @InsertProvider(type = SaveMapperProvider.class, method = "saveSendGiftSql")
    void saveSendGift(long roomID, long uid, String username,
                      long giftID, String giftName, long num,
                      String coinType, int price, int discountPrice,
                      long totalCoin, long timestamp);

    void saveGuardBuy();
}
