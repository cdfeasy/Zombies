package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.hibernate.Query;
import org.hibernate.Session;
import zombies.entity.game.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ZombyesController implements Initializable {

    @FXML
    public TableView CardList;

    @FXML
    public TableView abilityGrid;

    @FXML
    public TableView subfractionTable ;

    @FXML
    public ComboBox fraction;

    @FXML
    public ComboBox subfraction;
    @FXML
    public ComboBox type;
    @FXML
    public TextField id;
    @FXML
    public TextField name;
    @FXML
    public TextField nameEng;
    @FXML
    public TextField description;
    @FXML
    public TextField descriptionEng;
    @FXML
    public TextField strength;
    @FXML
    public TextField hp;
    @FXML
    public TextField armour;
    @FXML
    public TextField imgResourceId;
    @FXML
    public TextField shortImgResourceId;
    @FXML
    public TextField threadLevel;
    @FXML
    public ComboBox cardType;
    @FXML
    public TextField cardLevel;
    @FXML
    public TextField cardGoldCost;
    @FXML
    public TextField uniqueCard;

    @FXML
    public TableView  cardAbilityTable;

    @FXML
    public ComboBox cardSubFraction;


    @FXML
    public ComboBox uniqueAbility;

    @FXML
    public TextArea abilityTextArea;

    @FXML
    public TextField resourceCost1;
    @FXML
    public TextField resourceCost2;
    @FXML
    public TextField resourceCost3;

    @FXML
    public ImageView cardFullImg ;
    @FXML
    public ImageView cardShortImg ;

    @FXML
    public TextField  abilityId ;
    @FXML
    public TextField abilityName ;
    @FXML
    public TextField abilityNameEng;
    @FXML
    public TextField abilityDescription;
    @FXML
    public TextField abilityDescriptionEng ;
    @FXML
    public TextField abilityAction ;

    //---------------------------------------subfraction
    @FXML
    public TextField  subfractionId ;
    @FXML
    public TextField subfractionName ;
    @FXML
    public TextField subfractionNameEng;
    @FXML
    public TextField subfractionDescription;
    @FXML
    public TextField subfractionDescriptionEng ;
    @FXML
    public TextField subfractionLevel;
    @FXML
    public TextField subfractionRes1 ;
    @FXML
    public TextField subfractionRes2;
    @FXML
    public TextField subfractionRes3 ;
    @FXML
    public ComboBox subfractionFraction ;

    ResourceHelper helper;
    AbilityController acntrl;
    CardController cardcntrl;
    SubfractionController subfractionCntrl;





    Session ses;


    public void OnSearchClick(ActionEvent actionEvent) {

        ses.getTransaction().begin();
        String _query=  "select card from Card card where card.subFraction.id=:id order by card.id";
        Query query;
        String creatureType=    type.getSelectionModel().getSelectedItem().toString();
        if(!"all".equals(creatureType)) {
            CardTypeEnum en=CardTypeEnum.valueOf(creatureType);
            _query+=" and card.cardType=:type";
             query = ses.createQuery(_query);
            query.setParameter("id",Long.parseLong(((SubFractionDTO) subfraction.getSelectionModel().getSelectedItem()).getId())) ;
            query.setParameter("type",en.getId());
        } else {
            query = ses.createQuery(_query);
            query.setParameter("id",Long.parseLong(((SubFractionDTO) subfraction.getSelectionModel().getSelectedItem()).getId())) ;
        }

        CardList.getItems().clear();
        for (Object obj : query.list()) {
            Card c = (Card) obj;
            CardList.getItems().add(new CardDTO(c));
        }




        cardAbilityTable.getSelectionModel().clearSelection();


        String _query1=  "select abilities from Abilities abilities order by abilities.id";
        Query query1= ses.createQuery(_query1);
        List<Abilities> lst=query1.list();

        cardAbilityTable.getItems().clear();

        for (Abilities ab:lst) {

            cardAbilityTable.getItems().add(new AbilityDto(ab,""));
        }

        uniqueAbility.getSelectionModel().clearSelection();
        uniqueAbility.getItems().clear();
        for (Abilities ab:lst) {
            uniqueAbility.getItems().add(new AbilityDto(ab,""));
        }


        String _query2=  "select subfraction from SubFraction subfraction order by subfraction.id";
        Query query2= ses.createQuery(_query2);
        List<SubFraction> lst1=query2.list();

        cardSubFraction.getItems().clear();

        for (SubFraction ab:lst1) {

            cardSubFraction.getItems().add(new SubFractionDTO(ab));
        }



        ses.getTransaction().commit();

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            helper=new ResourceHelper();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        acntrl=new AbilityController(this);
        cardcntrl=new CardController(this);
        subfractionCntrl =new SubfractionController(this);
        // TableView cardList = (TableView) scene.lookup("CardList");
        ses = zombies.entity.support.HibernateUtil.getSessionFactory().openSession();
        ses.getTransaction().begin();
        Query query1 = ses.createQuery("select fraction from Fraction fraction");
        for (Object obj : query1.list()) {
            Fraction c = (Fraction) obj;
            fraction.getItems().add(new FractionDTO(c)) ;
        }
        fraction.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<FractionDTO>() {

            @Override
            public void changed(ObservableValue<? extends FractionDTO> arg0, FractionDTO arg1, FractionDTO arg2) {
                Query query2 = ses.createQuery("select subFractions from SubFraction subFractions where subFractions.fraction.id=:id");
                query2.setParameter("id",Long.parseLong(arg2.getId()));
                subfraction.getItems().clear();
                for (Object obj : query2.list()) {
                    SubFraction c = (SubFraction) obj;
                    subfraction.getItems().add(new SubFractionDTO(c)) ;
                }
                subfraction.getSelectionModel().selectFirst();
            }
        });

        fraction.getSelectionModel().selectFirst();
        type.getSelectionModel().selectFirst();



        TableColumn nameCol = new TableColumn("name");
        nameCol.setMinWidth(CardList.getPrefWidth()*0.8);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<CardDTO, String>("name")
        );
        TableColumn idCol = new TableColumn("id");
        idCol.setMinWidth(CardList.getPrefWidth()*0.2);
        idCol.setCellValueFactory(
                new PropertyValueFactory<CardDTO, String>("id")
        );
        CardList.getColumns().clear();
        CardList.getColumns().addAll(idCol, nameCol);


        TableColumn subfractionNameCol = new TableColumn("name");
        subfractionNameCol.setMinWidth(subfractionTable.getPrefWidth()*0.8);
        subfractionNameCol.setCellValueFactory(
                new PropertyValueFactory<CardDTO, String>("name")
        );
        TableColumn subfractionIdCol = new TableColumn("id");
        subfractionIdCol.setMinWidth(subfractionTable.getPrefWidth()*0.2);
        subfractionIdCol.setCellValueFactory(
                new PropertyValueFactory<CardDTO, String>("id")
        );
        subfractionTable.getColumns().clear();
        subfractionTable.getColumns().addAll(subfractionIdCol, subfractionNameCol);


        TableColumn abilityNameCol = new TableColumn("name");
        abilityNameCol.setMinWidth(200);
        abilityNameCol.setCellValueFactory(
                new PropertyValueFactory<CardDTO, String>("name")
        );
        TableColumn abilityIdCol = new TableColumn("id");

        abilityIdCol.setCellValueFactory(
                new PropertyValueFactory<CardDTO, String>("id")
        );

        TableColumn abilityDescrCol = new TableColumn("description");
        abilityDescrCol.setMinWidth(200);
        abilityDescrCol.setCellValueFactory(
                new PropertyValueFactory<CardDTO, String>("description")
        );
        TableColumn unitsCol = new TableColumn("units");
        unitsCol.setMinWidth(200);
        unitsCol.setCellValueFactory(
                new PropertyValueFactory<CardDTO, String>("units")
        );
        abilityGrid.getColumns().clear();
        abilityGrid.getColumns().addAll(abilityIdCol, abilityNameCol,abilityDescrCol,unitsCol);

        TableColumn cardAbilityNameCol = new TableColumn("name");
        cardAbilityNameCol.setMinWidth(300);
        cardAbilityNameCol.setCellValueFactory(
                new PropertyValueFactory<CardDTO, String>("name")
        );


        cardAbilityTable.getColumns().clear();
        cardAbilityTable.getColumns().addAll(cardAbilityNameCol);


        abilityGrid.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object o2) {
                AbilityDto cd=(AbilityDto) o2;
                if(cd==null || cd.getId()==null)
                    return;
                String _query=  "select ab from Abilities ab where ab.id=:id";
                Query query = ses.createQuery(_query);
                query.setParameter("id",Long.parseLong(cd.getId())) ;
                Abilities card= (Abilities) query.uniqueResult();
                abilityToFileds(card);

            }
        });

        subfractionTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object o2) {
                SubFractionDTO cd=(SubFractionDTO) o2;
                if(cd==null || cd.getId()==null)
                    return;
                String _query=  "select ab from SubFraction ab where ab.id=:id";
                Query query = ses.createQuery(_query);
                query.setParameter("id",Long.parseLong(cd.getId())) ;
                SubFraction sf= (SubFraction) query.uniqueResult();
                subfractionToField(sf);

            }
        });

        CardList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object o2) {
                CardDTO cd=(CardDTO) o2;
                if(cd==null || cd.getId()==null)
                    return;
                String _query=  "select card from Card card where card.id=:id";
                Query query = ses.createQuery(_query);
                query.setParameter("id",Long.parseLong(cd.getId())) ;
                Card card= (Card) query.uniqueResult();
                try {
                    cardToFileds(card);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            }
        });



        ses.getTransaction().commit();
    }

    private void abilityToFileds(Abilities ab) {
        abilityId.setText(ab.getId().toString());
        abilityName.setText(ab.getName());
        abilityNameEng.setText(ab.getNameEng());
        abilityDescription.setText(ab.getDescription());
        abilityDescriptionEng.setText(ab.getDescriptionEng());
        abilityAction.setText(ab.getAction());
    }

    private void subfractionToField(SubFraction ab) {
        subfractionId.setText(ab.getId().toString());
        subfractionName.setText(ab.getName());
        subfractionNameEng.setText(ab.getNameEng());
        subfractionDescription.setText(ab.getDescription());
        subfractionDescriptionEng.setText(ab.getDescriptionEng());
        subfractionRes1.setText(Integer.toString(ab.getRes1()));
        subfractionRes2.setText(Integer.toString(ab.getRes2()));
        subfractionRes3.setText(Integer.toString(ab.getRes3()));
        subfractionLevel.setText(ab.getLevel().toString());
        subfractionFraction.getSelectionModel().select(ab.getFraction().getId().intValue());
    }

    private void cardToFileds(Card card) throws FileNotFoundException {
        cardcntrl.cardToFileds(card);
    }


    public void onAddAbilityClick(ActionEvent actionEvent) {
        acntrl.onAddAbilityClick(actionEvent);
    }

    public void onSaveAbilityClick(ActionEvent actionEvent) {
        acntrl.onSaveAbilityClick(actionEvent);

    }

    public void onDelAbilityClick(ActionEvent actionEvent) {
        acntrl.onDelAbilityClick(actionEvent);
    }

    public void onAbilityRefreshClick(ActionEvent actionEvent) {
        acntrl.onAbilityRefreshClick(actionEvent);
    }

    public void cardAddOnClick(ActionEvent actionEvent) {
        cardcntrl.cardAddOnClick(actionEvent);
    }

    public void cardEditOnClick(ActionEvent actionEvent) {
        cardcntrl.cardEditOnClick(actionEvent);
    }

    public void cardDelOnClick(ActionEvent actionEvent) {
        cardcntrl.cardDelOnClick(actionEvent);
    }

    public void onCardDelAbilityCBClick(ActionEvent actionEvent) {
        cardcntrl.onCardDelAbilityCBClick(actionEvent);
    }

    public void onCardAddAbilityCBClick(ActionEvent actionEvent) {
        cardcntrl.onCardAddAbilityCBClick(actionEvent);
    }

    public void OnSubfractionAddButtonClick(ActionEvent actionEvent) {
        subfractionCntrl.OnSubfractionAddButtonClick(actionEvent);
    }

    public void OnSubfractionEditButtonClick(ActionEvent actionEvent) {
        subfractionCntrl.OnSubfractionEditButtonClick(actionEvent);
    }

    public void OnSubfractionDelButtonClick(ActionEvent actionEvent) {
        subfractionCntrl.OnSubfractionDelButtonClick(actionEvent);
    }

    public void OnSubfractionRefreshClick(ActionEvent actionEvent) {
        subfractionCntrl.OnSubfractionRefreshClick(actionEvent);
    }
}
