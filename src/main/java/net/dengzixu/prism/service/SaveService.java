package net.dengzixu.prism.service;


import net.dengzixu.bilvedanmaku.message.body.SimpleMessageBody;

public interface SaveService {
    void save(Long roomID, SimpleMessageBody<?> simpleMessageBody);
}
