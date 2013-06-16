package zombies.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.LoggerFactory;
import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;
import zombies.server.guice.ServerModule;
import zombies.server.netty.Server;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 28.12.12
 * Time: 23:08
 * To change this template use File | Settings | File Templates.
 */
public class App implements WrapperListener {
    static org.slf4j.Logger logger= LoggerFactory.getLogger(App.class);
    static int port = 18080;
    Injector injector;
    Server server;
    public static void main(String[] args) throws Exception {
        WrapperManager.start( new App(), args );
//        int port = 18080;
//        Injector injector = Guice.createInjector(new ServerModule());
//
//        server = injector.getInstance(Server.class);
//
//        new Thread( new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Server started");
//                logger.info("Server started");
//                server.run();
//            }
//        }).start();
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                server.stop();
//                System.out.println("Server stopped");
//                logger.info("Server stopped");
//            }
//        });
//        System.out.println("Server starting");
//        logger.info("Server starting");

    }

    @Override
    public Integer start(String[] strings) {
        injector = Guice.createInjector(new ServerModule());
        server = injector.getInstance(Server.class);
        new Thread( new Runnable() {
            @Override
            public void run() {
                System.out.println("Server started");
                logger.info("Server started");
                server.run();
            }
        }).start();
        return null;
    }

    @Override
    public int stop(int i) {
        server.stop();
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void controlEvent(int event) {
        if ( ( event == WrapperManager.WRAPPER_CTRL_LOGOFF_EVENT )
                && ( WrapperManager.isLaunchedAsService()) )
        {
            // Ignore
        }
        else
        {
            WrapperManager.stop( 0 );
            // Will not get here.
        }
    }
}
