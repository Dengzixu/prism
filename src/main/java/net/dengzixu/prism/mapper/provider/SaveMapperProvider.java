package net.dengzixu.prism.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

public class SaveMapperProvider {

    public String saveDanmuSql(long roomID, long uid, String username, String content, long timestamp) {
        return new SQL() {{
            INSERT_INTO("prism_rec_danmu_body");
            VALUES("room_id", "#{roomID}");
            VALUES("uid", "#{uid}");
            VALUES("username", "#{username}");
            VALUES("content", "#{content}");
            VALUES("timestamp", "FROM_UNIXTIME(#{timestamp})");
        }}.toString();
    }

    public String saveInteractWordSql(long roomID, long uid, String username, int type, long timestamp) {
        return new SQL() {{
            INSERT_INTO("prism_rec_interact_word");
            VALUES("room_id", "#{roomID}");
            VALUES("uid", "#{uid}");
            VALUES("username", "#{username}");
            VALUES("type", "#{type}");
            VALUES("timestamp", "FROM_UNIXTIME(#{timestamp})");
        }}.toString();
    }

    public String saveSendGiftSql(long roomID, long uid, String username,
                                  long giftID, String giftName, long num,
                                  String coinType, int price, int discountPrice,
                                  long totalCoin, long timestamp) {
        return new SQL() {{
            INSERT_INTO("prism_rec_send_gift");
            VALUES("room_id", "#{roomID}");
            VALUES("uid", "#{uid}");
            VALUES("username", "#{username}");
            VALUES("gift_id", "#{giftID}");
            VALUES("gift_name", "#{giftName}");
            VALUES("num", "#{num}");
            VALUES("coin_type", "#{coinType}");
            VALUES("price", "#{price}");
            VALUES("discount_price", "#{discountPrice}");
            VALUES("total_coin", "#{totalCoin}");
            VALUES("timestamp", "FROM_UNIXTIME(#{timestamp})");
        }}.toString();
    }

    public String saveGuardBuySQL(long roomID,
                                  long uid, String username,
                                  int guardLevel, int num,
                                  long timestamp){
        return new SQL(){{
            INSERT_INTO("prism_rec_guard_buy");
            VALUES("room_id", "#{roomID}");
            VALUES("uid", "#{uid}");
            VALUES("username", "#{username}");
            VALUES("guard_level", "#{guardLevel}");
            VALUES("num", "#{num}");
            VALUES("timestamp", "FROM_UNIXTIME(#{timestamp})");
        }}.toString();
    }
}
