package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.hibernate.Query;
import org.hibernate.Session;
import zombies.entity.game.Card;
import zombies.entity.support.*;


public class Controller {
    @FXML
    public TextField id;
    @FXML
    public TableView<Card> CardList ;

    public void OnSearchClick(ActionEvent actionEvent) {
        Session ses = zombies.entity.support.HibernateUtil.getSessionFactory().openSession();
        ses.getTransaction().begin();
        Query query = ses.createQuery("select card from Card card").setMaxResults(10);

        for(Object obj: query.list()){
            Card c=(Card)obj;
            CardList.
            CardList.getItems().add(c);
        }
        ses.getTransaction().commit();
        ses.close();

    }
}
