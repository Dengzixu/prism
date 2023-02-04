package net.dengzixu.prism.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("prism-setting")
public record PrismSetting(RoomList roomList) {

    public record RoomList(List<Long> roomId) {

    }
}
