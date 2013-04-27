package zombies.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import zombies.server.guice.ServerModule;
import zombies.server.netty.Server;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.12.12
 * Time: 23:08
 * To change this template use File | Settings | File Templates.
 */
public class App {
    public static void main(String[] args) throws Exception {
        int port = 18080;
        Injector injector = Guice.createInjector(new ServerModule());

        final Server server = injector.getInstance(Server.class);
        server.run();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
            }
        });
    }
}