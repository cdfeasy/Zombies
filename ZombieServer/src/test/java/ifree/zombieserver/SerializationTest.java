package ifree.zombieserver;

import zombies.entity.game.Card;
import zombies.entity.game.CardTypeEnum;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 17.01.13
 * Time: 22:54
 * To change this template use File | Settings | File Templates.
 */
public class SerializationTest {
    @Test
    public void SerializationTest() throws InterruptedException, IOException {
        Card half = new Card(3l,"Ползун", "Ползун, нападает через баррикады", 1, 10, 0, 1, CardTypeEnum.creature.getId(), 3, 0, 0,1);
       // half.setImg(new byte[]{1,2,4,5,6,7,8});
        ObjectMapper requestMapper = new ObjectMapper();
        requestMapper.generateJsonSchema(Card.class);
        String s=requestMapper.writeValueAsString(half);
        System.out.println(s);

    }
}
