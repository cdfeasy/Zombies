package server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import server.actionworker.*;
import server.game.play.GameManager;
import server.netty.Server;
import server.netty.ServerHandler;
import server.game.LobbyManager;
import server.game.NewGameStarter;
import server.game.RequestManager;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 23.12.12
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public class ServerModule extends AbstractModule {
    @Override
    protected void configure() {
       bindConstant().annotatedWith(Names.named("port")).to(18080);
       bind(Server.class).asEagerSingleton();
       bind(ServerHandler.class);
       bind(RequestManager.class).in(Scopes.SINGLETON);
       bind(NewGameStarter.class).in(Scopes.SINGLETON);
       bind(LobbyManager.class).asEagerSingleton();

       bind(ActionManager.class);

        bind(ConnectionWorker.class).in(Scopes.SINGLETON);
       bind(UserInfoWorker.class).in(Scopes.SINGLETON);
       bind(SearchWorker.class);
       bind(StopSearchWorker.class);
       bind(TurnWorker.class);
       // bind(GameManager.class);
        bind(CardInfoWorker.class).in(Scopes.SINGLETON);
        bind(CreateUserWorker.class).in(Scopes.SINGLETON);;
    }
}
