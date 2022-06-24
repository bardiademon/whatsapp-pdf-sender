package com.bardiademon.whatsapp.sender.connector;

import java.io.InputStream;

public interface ConnectorStatus
{
    void onReady();

    void onDisconnect();

    void onQrCode(final InputStream inputStream);
}
