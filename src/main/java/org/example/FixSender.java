package org.example;

import org.springframework.stereotype.Service;
import quickfix.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.InputStream;

public class FixSender {

    private SocketInitiator initiator;

    @PostConstruct
    public void start() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/quickfix.properties");
        assert inputStream != null;
        SessionSettings settings = new SessionSettings(inputStream);
        Application application = new FixApplication();
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory();

        initiator = new SocketInitiator(application, storeFactory, settings, logFactory, messageFactory);
        initiator.start();
    }

    @PreDestroy
    public void stop() {
        if (initiator != null) {
            initiator.stop();
        }
    }
}
