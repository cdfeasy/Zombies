package sample;

import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import zombies.entity.game.Card;
import zombies.entity.support.ImagePack;
import zombies.entity.support.ImageResource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 02.05.13
 * Time: 18:14
 * To change this template use File | Settings | File Templates.
 */
public class ResourceHelper {
    private HashMap<Integer,byte[]> images=new HashMap<>();
    public ResourceHelper() throws IOException {
        ObjectMapper requestMapper = new ObjectMapper();
        requestMapper.generateJsonSchema(ImagePack.class);
        ImagePack pack=requestMapper.readValue(getClass().getResourceAsStream("/images.bin"), ImagePack.class);
        for(ImageResource res:pack.getImages()){
            images.put(res.getId(),res.getImage());
        }
    }

    public Image getImage(int id){
       byte[] img=images.get(id);
        if(img==null){
            return new Image(getClass().getResourceAsStream("/none.png"));
        } else{
            return new Image(new ByteArrayInputStream(img));
        }
    }
    public static void main(String[] args) throws IOException {
        File dir=new File("C:\\tmp\\imgs");
        ImagePack pack=new ImagePack();
        List<ImageResource> list=new ArrayList<>();
        for(File img:dir.listFiles()){

            ImageResource ires=new ImageResource(Integer.parseInt(FilenameUtils.removeExtension(img.toPath().getFileName().toString())),"", FileUtils.readFileToByteArray(img));
            list.add(ires);
        }
        pack.setImages(list);
        ObjectMapper requestMapper = new ObjectMapper();
        requestMapper.generateJsonSchema(ImagePack.class);
        String s=requestMapper.writeValueAsString(pack);
        FileUtils.writeByteArrayToFile(new File("D:\\ZombiesGit\\Zombies\\CardEditorMaven\\src\\main\\resources\\images.bin"),s.getBytes());


    }
}
