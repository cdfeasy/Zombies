package sample;

import javafx.event.ActionEvent;
import org.hibernate.Query;
import zombies.entity.game.Abilities;
import zombies.entity.game.Card;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 02.05.13
 * Time: 19:32
 * To change this template use File | Settings | File Templates.
 */
public class AbilityController {
    private ZombyesController controller;

    public AbilityController(ZombyesController controller) {
        this.controller = controller;
    }

    public void onAddAbilityClick(ActionEvent actionEvent) {
        controller.ses.getTransaction().begin();
        Query query1 = controller.ses.createQuery("select ab from Abilities ab where id=:id");
        query1.setParameter("id",Long.parseLong(controller.abilityId.getText()));
        if(!query1.list().isEmpty())    {
            controller.ses.getTransaction().commit();
            return;
        }
        Abilities ab=new Abilities();
        ab.setId(Long.parseLong(controller.abilityId.getText()));
        ab.setDescription(controller.abilityDescription.getText());
        ab.setDescriptionEng(controller.abilityDescriptionEng.getText());
        ab.setName(controller.abilityName.getText());
        ab.setNameEng(controller.abilityNameEng.getText());
        ab.setAction(controller.abilityAction.getText());
        controller.ses.save(ab);
        controller.ses.getTransaction().commit();
        controller.onAbilityRefreshClick(null);
    }

    public void onSaveAbilityClick(ActionEvent actionEvent) {
        controller.ses.getTransaction().begin();
        Query query1 = controller.ses.createQuery("select ab from Abilities ab where id=:id");
        query1.setParameter("id",Long.parseLong(controller.abilityId.getText()));
        Abilities ab= (Abilities) query1.uniqueResult();
        ab.setDescription(controller.abilityDescription.getText());
        ab.setDescriptionEng(controller.abilityDescriptionEng.getText());
        ab.setName(controller.abilityName.getText());
        ab.setNameEng(controller.abilityNameEng.getText());
        ab.setAction(controller.abilityAction.getText());
        controller.ses.merge(ab);
        controller.ses.getTransaction().commit();

    }

    public void onDelAbilityClick(ActionEvent actionEvent) {

        controller. ses.getTransaction().begin();
        Query query1 = controller.ses.createQuery("select card from Card card left outer join card.abilities as ab  where ab.id=:id");
        query1.setParameter("id",Long.parseLong(controller.abilityId.getText()));
        if(!query1.list().isEmpty()){
            controller.ses.getTransaction().commit();
            return;
        }
        Query query2 = controller.ses.createQuery("select ab from Abilities ab where id=:id");
        query2.setParameter("id",Long.parseLong(controller.abilityId.getText()));
        Abilities ab= (Abilities) query2.uniqueResult();
        controller.ses.delete(ab);
        controller.ses.getTransaction().commit();
        controller.onAbilityRefreshClick(null);


    }

    public void onAbilityRefreshClick(ActionEvent actionEvent) {
        controller.abilityGrid.getSelectionModel().clearSelection();
        controller.ses.getTransaction().begin();

        String _query1=  "select card from Card card ";
        Query query1;
        String creatureType=    controller.type.getSelectionModel().getSelectedItem().toString();
        query1 = controller.ses.createQuery(_query1);


        String _query=  "select abilities from Abilities abilities order by abilities.id";
        Query query= controller.ses.createQuery(_query);
        List<Abilities> lst=query.list();
        List<Card> CardLst=query1.list();


        HashMap<Abilities,String> ability=new HashMap<>();
        for(Abilities ab:lst){
            ability.put(ab,"");
        }

        for(Card crd:CardLst){
            for(Abilities ab:crd.getAbilities()){
                ability.put(ab,ability.get(ab)+crd.getName()+",");
            }
            if(crd.getUniqueAbility()!=null){
                ability.put(crd.getUniqueAbility(),ability.get(crd.getUniqueAbility())+crd.getName()+",");
            }
        }


        controller.abilityGrid.getItems().clear();

        for (Abilities ab:lst) {

            controller.abilityGrid.getItems().add(new AbilityDto(ab,ability.get(ab)));
        }
        controller.ses.getTransaction().commit();

    }
}
