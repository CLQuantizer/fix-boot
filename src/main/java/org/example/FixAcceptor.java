package org.example;

import org.springframework.stereotype.Service;
import quickfix.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.InputStream;

@Service
public class FixAcceptor {

    private SocketAcceptor acceptor;

    @PostConstruct
    public void start() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("/quickfix-acc.properties");
        assert inputStream != null;
        SessionSettings settings = new SessionSettings(inputStream);
        Application application = new FixApplication();
        MessageStoreFactory storeFactory = new FileStoreFactory(settings);
        LogFactory logFactory = new ScreenLogFactory(settings);
        MessageFactory messageFactory = new DefaultMessageFactory();

        acceptor = new SocketAcceptor(application, storeFactory, settings, logFactory, messageFactory);
        acceptor.start();
    }

    @PreDestroy
    public void stop() {
        if (acceptor != null) {
            acceptor.stop();
        }
    }
}
