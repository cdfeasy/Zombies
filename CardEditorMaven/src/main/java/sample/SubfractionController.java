package sample;

import javafx.event.ActionEvent;
import org.hibernate.Query;
import zombies.entity.game.Abilities;
import zombies.entity.game.Card;
import zombies.entity.game.Fraction;
import zombies.entity.game.SubFraction;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 05.05.13
 * Time: 17:29
 * To change this template use File | Settings | File Templates.
 */
public class SubfractionController {
    private ZombyesController controller;

    public SubfractionController(ZombyesController controller) {
        this.controller = controller;
    }
    public void OnSubfractionAddButtonClick(ActionEvent actionEvent) {
        controller.ses.getTransaction().begin();
        Query query2 = controller.ses.createQuery("select ab from SubFraction ab where id=:id");
        query2.setParameter("id",Long.parseLong(controller.subfractionId.getText()));

        if(!query2.list().isEmpty()) {
            controller.ses.getTransaction().commit();
            return;
        }

        Query query1 = controller.ses.createQuery("select ab from Fraction ab where id=:id");
        query1.setParameter("id",(long)controller.subfractionFraction.getSelectionModel().getSelectedIndex());
        Fraction fr= (Fraction) query1.uniqueResult();
        SubFraction ab=new SubFraction();
        ab.setId(Long.parseLong(controller.subfractionId.getText()));
        ab.setDescription(controller.subfractionDescription.getText());
        ab.setDescriptionEng(controller.subfractionDescriptionEng.getText());
        ab.setName(controller.subfractionName.getText());
        ab.setNameEng(controller.subfractionNameEng.getText());
        ab.setLevel(Integer.parseInt(controller.subfractionLevel.getText()));
        ab.setFraction(fr);
        ab.setRes1(Integer.parseInt(controller.subfractionRes1.getText()));
        ab.setRes1(Integer.parseInt(controller.subfractionRes2.getText()));
        ab.setRes1(Integer.parseInt(controller.subfractionRes3.getText()));
        controller.ses.save(ab);
        controller.ses.getTransaction().commit();
        OnSubfractionRefreshClick(null);
    }

    public void OnSubfractionEditButtonClick(ActionEvent actionEvent) {

        controller.ses.getTransaction().begin();
        Query query2 = controller.ses.createQuery("select ab from SubFraction ab where id=:id");
        query2.setParameter("id",Long.parseLong(controller.subfractionId.getText()));
        SubFraction ab= (SubFraction) query2.uniqueResult();

        Query query1 = controller.ses.createQuery("select ab from Fraction ab where id=:id");
        query1.setParameter("id",(long)controller.subfractionFraction.getSelectionModel().getSelectedIndex());
        Fraction fr= (Fraction) query1.uniqueResult();

        ab.setDescription(controller.subfractionDescription.getText());
        ab.setDescriptionEng(controller.subfractionDescriptionEng.getText());
        ab.setName(controller.subfractionName.getText());
        ab.setNameEng(controller.subfractionNameEng.getText());
        ab.setLevel(Integer.parseInt(controller.subfractionLevel.getText()));
        ab.setFraction(fr);
        ab.setRes1(Integer.parseInt(controller.subfractionRes1.getText()));
        ab.setRes1(Integer.parseInt(controller.subfractionRes2.getText()));
        ab.setRes1(Integer.parseInt(controller.subfractionRes3.getText()));
        controller.ses.merge(ab);
        controller.ses.getTransaction().commit();
    }

    public void OnSubfractionDelButtonClick(ActionEvent actionEvent) {
        controller. ses.getTransaction().begin();
        Query query2 = controller.ses.createQuery("select ab from SubFraction ab where id=:id");
        query2.setParameter("id",Long.parseLong(controller.subfractionId.getText()));
        SubFraction ab= (SubFraction) query2.uniqueResult();
        if(!ab.getDeck().isEmpty())  {
            controller.ses.getTransaction().commit();
            return;
        }
        controller.ses.delete(ab);
        controller.ses.getTransaction().commit();
        controller.OnSubfractionRefreshClick(null);
    }

    public void OnSubfractionRefreshClick(ActionEvent actionEvent) {
        controller.abilityGrid.getSelectionModel().clearSelection();
        controller.ses.getTransaction().begin();

        String _query=  "select sf from SubFraction sf order by sf.id";
        Query query= controller.ses.createQuery(_query);
        List<SubFraction> lst=query.list();



        controller.subfractionTable.getItems().clear();

        for (SubFraction ab:lst) {

            controller.subfractionTable.getItems().add(new SubFractionDTO(ab));
        }
        controller.ses.getTransaction().commit();
    }
}
