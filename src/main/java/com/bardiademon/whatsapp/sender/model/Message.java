package com.bardiademon.whatsapp.sender.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class Message
{
    private String phone;

    private String text;
    private List<Media> pdf;

    @Getter
    @Setter
    public static final class Media
    {
        private String path;
        private String test;

        public static boolean checkEmpty(final List<Media> media)
        {
            return media != null && media.size() > 0;
        }
    }
}
