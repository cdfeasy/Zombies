package sample;

import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import org.hibernate.Query;
import zombies.entity.game.Abilities;
import zombies.entity.game.Card;
import zombies.entity.game.CardTypeEnum;
import zombies.entity.game.SubFraction;

import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 02.05.13
 * Time: 19:36
 * To change this template use File | Settings | File Templates.
 */
public class CardController {
    private ZombyesController controller;

    public CardController(ZombyesController controller) {
        this.controller = controller;
    }

    public void OnSearchClick(ActionEvent actionEvent) {

        controller.ses.getTransaction().begin();
        String _query=  "select card from Card card where card.subFraction.id=:id order by card.id";
        Query query;
        String creatureType=    controller.type.getSelectionModel().getSelectedItem().toString();
        if(!"all".equals(creatureType)) {
            CardTypeEnum en=CardTypeEnum.valueOf(creatureType);
            _query+=" and card.cardType=:type";
            query = controller.ses.createQuery(_query);
            query.setParameter("id",Long.parseLong(((SubFractionDTO) controller.subfraction.getSelectionModel().getSelectedItem()).getId())) ;
            query.setParameter("type",en.getId());
        } else {
            query = controller.ses.createQuery(_query);
            query.setParameter("id",Long.parseLong(((SubFractionDTO) controller.subfraction.getSelectionModel().getSelectedItem()).getId())) ;
        }

        controller.CardList.getItems().clear();
        for (Object obj : query.list()) {
            Card c = (Card) obj;
            controller.CardList.getItems().add(new CardDTO(c));
        }
        controller.ses.getTransaction().commit();

    }

    public void onCardDelAbilityCBClick(ActionEvent actionEvent) {
        controller.ses.getTransaction().begin();
        Query query1 = controller.ses.createQuery("select ab from Card ab where id=:id");
        query1.setParameter("id",Long.parseLong(controller.id.getText()));

        Query query2 = controller.ses.createQuery("select ab from Abilities ab where id=:id");
        query2.setParameter("id",Long.parseLong(((AbilityDto)controller.cardAbilityTable.getSelectionModel().getSelectedItem()).getId()));

        Card card= (Card) query1.uniqueResult();
        Abilities ab= (Abilities) query2.uniqueResult();
        if(!card.getAbilities().contains(ab)){
            controller.ses.getTransaction().commit();
            return;
        }
        card.getAbilities().remove(ab);
        String ablt="";
        for(Abilities a:card.getAbilities()){
            ablt+=a.getName()+";";
        }
        controller.abilityTextArea.setText(ablt);

        controller.ses.merge(card);
        controller.ses.getTransaction().commit();
    }

    public void onCardAddAbilityCBClick(ActionEvent actionEvent) {
        controller.ses.getTransaction().begin();
        Query query1 = controller.ses.createQuery("select ab from Card ab where id=:id");
        query1.setParameter("id",Long.parseLong(controller.id.getText()));

        Query query2 = controller.ses.createQuery("select ab from Abilities ab where id=:id");
        query2.setParameter("id",Long.parseLong(((AbilityDto) controller.cardAbilityTable.getSelectionModel().getSelectedItem()).getId()));

        Card card= (Card) query1.uniqueResult();
        Abilities ab= (Abilities) query2.uniqueResult();
        if(card.getAbilities().contains(ab)){
            controller.ses.getTransaction().commit();
            return;
        }
        card.getAbilities().add(ab);
        String ablt="";
        for(Abilities a:card.getAbilities()){
            ablt+=a.getName()+";";
        }
        controller.abilityTextArea.setText(ablt);

        controller.ses.merge(card);
        controller.ses.getTransaction().commit();
    }

    public void cardAddOnClick(ActionEvent actionEvent) {
        controller.ses.getTransaction().begin();
        Query query1 = controller.ses.createQuery("select ab from Card ab where id=:id");
        query1.setParameter("id",Long.parseLong(controller.id.getText()));
        if(!query1.list().isEmpty())    {
            controller.ses.getTransaction().commit();
            return;
        }

        Query query2 = controller.ses.createQuery("select ab from Abilities ab where id=:id");
        query2.setParameter("id",Long.parseLong(((AbilityDto)controller.uniqueAbility.getSelectionModel().getSelectedItem()).getId()));
        Abilities ab= (Abilities) query2.uniqueResult();

        Query query3 = controller.ses.createQuery("select ab from SubFraction ab where id=:id");
        query3.setParameter("id",Long.parseLong(((SubFractionDTO)controller.cardSubFraction.getSelectionModel().getSelectedItem()).getId()));
        SubFraction sf= (SubFraction) query3.uniqueResult();


        Card card=new Card();
        card.setIdString(controller.id.getText());
        card.setName(controller.name.getText());
        card.setNameEng(controller.nameEng.getText());
        card.setDescription(controller.description.getText());
        card.setDescriptionEng(controller.descriptionEng.getText());
        card.setStrengthString(controller.strength.getText());
        card.setHpString(controller.hp.getText());
        card.setArmourString(controller.armour.getText());
        card.setImgResourceIdString(controller.imgResourceId.getText());
        card.setShortImgResourceIdString(controller.shortImgResourceId.getText());
        card.setThreadLevelString(controller.threadLevel.getText());
        card.setCardType(controller.cardType.getSelectionModel().getSelectedIndex());
        card.setCardLevelString(controller.cardLevel.getText());
        card.setUniqueAbility(ab);
        card.setSubFraction(sf);
        card.setCardGoldCostString(controller.cardGoldCost.getText());
        card.setUniqueCardString(controller.uniqueCard.getText());
        card.setResourceCost1String(controller.resourceCost1.getText());
        card.setResourceCost2String(controller.resourceCost2.getText());
        card.setResourceCost3String(controller.resourceCost3.getText());
        

        controller.ses.save(card);
        controller.ses.getTransaction().commit();
        OnSearchClick(null);
    }

    public void cardEditOnClick(ActionEvent actionEvent) {
        controller.ses.getTransaction().begin();
        Query query1 = controller.ses.createQuery("select ab from Card ab where id=:id");
        query1.setParameter("id",Long.parseLong(controller.id.getText()));

        Query query2 = controller.ses.createQuery("select ab from Abilities ab where id=:id");
        query2.setParameter("id",Long.parseLong(((AbilityDto)controller.uniqueAbility.getSelectionModel().getSelectedItem()).getId()));
        Abilities ab= (Abilities) query2.uniqueResult();

        Query query3 = controller.ses.createQuery("select ab from SubFraction ab where id=:id");
        query3.setParameter("id",Long.parseLong(((SubFractionDTO)controller.cardSubFraction.getSelectionModel().getSelectedItem()).getId()));
        SubFraction sf= (SubFraction) query3.uniqueResult();


        Card card= (Card) query1.uniqueResult();
        card.setIdString(controller.id.getText());
        card.setName(controller.name.getText());
        card.setNameEng(controller.nameEng.getText());
        card.setDescription(controller.description.getText());
        card.setDescriptionEng(controller.descriptionEng.getText());
        card.setStrengthString(controller.strength.getText());
        card.setHpString(controller.hp.getText());
        card.setArmourString(controller.armour.getText());
        card.setImgResourceIdString(controller.imgResourceId.getText());
        card.setShortImgResourceIdString(controller.shortImgResourceId.getText());
        card.setThreadLevelString(controller.threadLevel.getText());
        card.setCardType(controller.cardType.getSelectionModel().getSelectedIndex());
        card.setCardLevelString(controller.cardLevel.getText());
        card.setCardGoldCostString(controller.cardGoldCost.getText());
        card.setUniqueCardString(controller.uniqueCard.getText());
        card.setUniqueAbility(ab);
        card.setSubFraction(sf);
        card.setResourceCost1String(controller.resourceCost1.getText());
        card.setResourceCost2String(controller.resourceCost2.getText());
        card.setResourceCost3String(controller.resourceCost3.getText());


        controller.ses.merge(card);



        controller.ses.getTransaction().commit();
    }

    public void cardDelOnClick(ActionEvent actionEvent) {
        controller. ses.getTransaction().begin();
        Query query1 = controller.ses.createQuery("select card from Card card  where card.id=:id");
        query1.setParameter("id",Long.parseLong(controller.id.getText()));
        Card ab= (Card) query1.uniqueResult();
        if(!ab.getAbilities().isEmpty())    {
            controller.ses.getTransaction().commit();
            return;
        }
        controller.ses.delete(ab);
        controller.ses.getTransaction().commit();
        controller.onAbilityRefreshClick(null);
        OnSearchClick(null);
    }
    public void cardToFileds(Card card) throws FileNotFoundException {
        controller.id.setText(card.getId().toString());
        controller.name.setText(card.getName());
        controller.nameEng.setText(card.getNameEng());
        controller.description.setText(card.getDescription());
        controller.descriptionEng.setText(card.getDescriptionEng());
        controller.strength.setText(Integer.toString(card.getStrength()));
        controller.hp.setText(Integer.toString(card.getHp()));
        controller.armour.setText(Integer.toString(card.getArmour()));
        controller.imgResourceId.setText(Integer.toString(card.getImgResourceId()));
        controller.shortImgResourceId.setText(Integer.toString(card.getShortImgResourceId()));
        controller.threadLevel.setText(Integer.toString(card.getThreadLevel()));
        controller.cardType.getSelectionModel().select(card.getCardType());
        controller.cardLevel.setText(Integer.toString(card.getCardLevel()));
        controller.cardGoldCost.setText(Integer.toString(card.getCardGoldCost()));
        controller.uniqueCard.setText(card.getName());
        String ablt="";
        for(Abilities ab:card.getAbilities()){
            ablt+=ab.getName()+";";
        }
        controller.abilityTextArea.setText(ablt);
        //  abilities.setText(card.getName());

        //controller.subFraction.setText(card.getName());
        if(card.getUniqueAbility()==null){
            controller.uniqueAbility.getSelectionModel().select(0);
        } else{
            for(int i=0;i<controller.uniqueAbility.getItems().size();i++){
                AbilityDto ability= (AbilityDto) controller.uniqueAbility.getItems().get(i);
                if(Long.parseLong(ability.getId())==card.getUniqueAbility().getId() ){
                    controller.uniqueAbility.getSelectionModel().select(i);
                    break;
                }
            }
        }

        for(int i=0;i<controller.cardSubFraction.getItems().size();i++){
            SubFractionDTO subfr= (SubFractionDTO) controller.cardSubFraction.getItems().get(i);
            if(Long.parseLong(subfr.getId())==card.getSubFraction().getId() ){
                controller.cardSubFraction.getSelectionModel().select(i);
                break;
            }
        }

        //    uniqueAbility.setText(card.getName());

        controller.resourceCost1.setText(Integer.toString(card.getResourceCost1()));
        controller.resourceCost2.setText(Integer.toString(card.getResourceCost2()));
        controller.resourceCost3.setText(Integer.toString(card.getResourceCost3()));

        Image imageF = controller.helper.getImage(card.getImgResourceId());
        controller.cardFullImg.setImage(imageF);
        Image imageS = controller.helper.getImage(card.getShortImgResourceId());
        controller.cardShortImg.setImage(imageS);




    }
}
