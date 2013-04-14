package zombies.server.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dmitry
 * Date: 13.04.13
 * Time: 17:30
 * To change this template use File | Settings | File Templates.
 */
public class SharpGenerator {
    private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // System.out.println(Arrays.asList(getClasses("zombies.dto.actions")));
        String ActionDir = "c:/tmp/sharp/Actions/";
        String ReplyDir = "c:/tmp/sharp/Replys/";
        String EntityGameDir = "c:/tmp/sharp/Entity/Game/";
        String EntityServerDir = "c:/tmp/sharp/Entity/Server/";
        String EntitySupportDir = "c:/tmp/sharp/Entity/Support/";
        new File(ActionDir).mkdirs();
        new File(ReplyDir).mkdirs();
        new File(EntityGameDir).mkdirs();
        new File(EntityServerDir).mkdirs();
        new File(EntitySupportDir).mkdirs();
        for (Class c : getClasses("zombies.dto.actions")) {
            String dir = ActionDir;
            if (c.isEnum()) {
                continue;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("using System;\n");
            sb.append("using System.Collections.Generic;\n");
            sb.append("using System.Linq;\n");
            sb.append("using System.Runtime.Serialization;\n");
            sb.append("using System.Text;\n");
            sb.append("using System.Threading.Tasks;\n");
            sb.append("using ZombieSharpClient.Entity.Game;\n");
            sb.append("using ZombieSharpClient.Entity.Server;\n");
            sb.append("using ZombieSharpClient.Entity.Support;\n");
            sb.append("namespace ZombieSharpClient.Actions\n{\n");
            sb.append("\t[DataContract]\n");
            sb.append("\tpublic class ");
            sb.append(c.getSimpleName()).append("{\n");
            Field[] f = c.getDeclaredFields();
            for (Field fld : f) {
                generateFiled(sb, fld);
            }
            sb.append("\t}\n}");
            FileOutputStream fos = new FileOutputStream(dir + c.getSimpleName() + ".cs");
            fos.write(sb.toString().getBytes("utf8"));
            fos.close();
        }

        for (Class c : getClasses("zombies.dto.reply")) {
            String dir = ReplyDir;
            if (c.isEnum()) {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("using System;\n");
            sb.append("using System.Collections.Generic;\n");
            sb.append("using System.Linq;\n");
            sb.append("using System.Runtime.Serialization;\n");
            sb.append("using System.Text;\n");
            sb.append("using System.Threading.Tasks;\n");
            sb.append("using ZombieSharpClient.Entity.Game;\n");
            sb.append("using ZombieSharpClient.Entity.Server;\n");
            sb.append("using ZombieSharpClient.Entity.Support;\n");
            sb.append("namespace ZombieSharpClient.UserReply\n{\n");
            sb.append("\t[DataContract]\n");
            sb.append("\tpublic class ");
            sb.append(c.getSimpleName()).append("{\n");
            Field[] f = c.getDeclaredFields();
            for (Field fld : f) {
                generateFiled(sb, fld);
            }
            sb.append("\t}\n}");
            FileOutputStream fos = new FileOutputStream(dir + c.getSimpleName() + ".cs");
            fos.write(sb.toString().getBytes("utf8"));
            fos.close();
        }

        for (Class c : getClasses("zombies.entity.game")) {
            String dir = EntityGameDir;
            if (c.isEnum()) {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("using System;\n");
            sb.append("using System.Collections.Generic;\n");
            sb.append("using System.Linq;\n");
            sb.append("using System.Runtime.Serialization;\n");
            sb.append("using System.Text;\n");
            sb.append("using System.Threading.Tasks;\n");
            sb.append("using ZombieSharpClient.Entity.Server;\n");
            sb.append("using ZombieSharpClient.Entity.Support;\n");
            sb.append("namespace ZombieSharpClient.Entity.Game\n{\n");
            sb.append("\t[DataContract]\n");
            sb.append("\tpublic class ");
            sb.append(c.getSimpleName()).append("{\n");
            Field[] f = c.getDeclaredFields();
            for (Field fld : f) {
                generateFiled(sb, fld);
            }
            sb.append("\t}\n}");
            FileOutputStream fos = new FileOutputStream(dir + c.getSimpleName() + ".cs");
            fos.write(sb.toString().getBytes("utf8"));
            fos.close();
        }
        for (Class c : getClasses("zombies.entity.support")) {
            String dir = EntitySupportDir;
            if (c.isEnum()) {
                continue;
            }
            if (!("CardWrapper".equals(c.getSimpleName()) ||
                    "DeckInfo".equals(c.getSimpleName()) ||
                    "GameInfo".equals(c.getSimpleName())))  {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("using System;\n");
            sb.append("using System.Collections.Generic;\n");
            sb.append("using System.Linq;\n");
            sb.append("using System.Runtime.Serialization;\n");
            sb.append("using System.Text;\n");
            sb.append("using System.Threading.Tasks;\n");
            sb.append("using ZombieSharpClient.Entity.Game;\n");
            sb.append("using ZombieSharpClient.Entity.Server;\n");
            sb.append("namespace ZombieSharpClient.Entity.Support\n{\n");
            sb.append("\t[DataContract]\n");
            sb.append("\tpublic class ");
            sb.append(c.getSimpleName()).append("{\n");
            Field[] f = c.getDeclaredFields();
            for (Field fld : f) {
                generateFiled(sb, fld);
            }
            sb.append("\t}\n}");
            FileOutputStream fos = new FileOutputStream(dir + c.getSimpleName() + ".cs");
            fos.write(sb.toString().getBytes("utf8"));
            fos.close();
        }

        for (Class c : getClasses("zombies.entity.server")) {
            String dir = EntityServerDir;
            if (c.isEnum()) {
                continue;
            }
            if (!("DetailStatistic".equals(c.getSimpleName()) ||
                    "FriendList".equals(c.getSimpleName()) ||
                    "GameVersion".equals(c.getSimpleName()) ||
                    "History".equals(c.getSimpleName()) ||
                    "User".equals(c.getSimpleName()))) {
                continue;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("using System;\n");
            sb.append("using System.Collections.Generic;\n");
            sb.append("using System.Linq;\n");
            sb.append("using System.Runtime.Serialization;\n");
            sb.append("using System.Text;\n");
            sb.append("using System.Threading.Tasks;\n");
            sb.append("using ZombieSharpClient.Entity.Game;\n");
            sb.append("using ZombieSharpClient.Entity.Support;\n");
            sb.append("namespace ZombieSharpClient.Entity.Server\n{\n");
            sb.append("\t[DataContract]\n");
            sb.append("\tpublic class ");
            sb.append(c.getSimpleName()).append("{\n");
            Field[] f = c.getDeclaredFields();
            for (Field fld : f) {
                generateFiled(sb, fld);
            }
            sb.append("\t}\n}");
            FileOutputStream fos = new FileOutputStream(dir + c.getSimpleName() + ".cs");
            fos.write(sb.toString().getBytes("utf8"));
            fos.close();
        }

    }

    private static void generateFiled(StringBuilder sb, Field fld) {
        sb.append("\t\t[DataMember(Name = \"" + fld.getName() + "\")]\n");
        sb.append("\t\tpublic ");
        switch (fld.getType().getSimpleName()){
            default: sb.append(fld.getType().getSimpleName());break;
            case "String":sb.append("string");break;
            case "Long":sb.append("long");break;
            case "Integer":sb.append("int");break;
            case "Boolean":sb.append("bool");break;
            case "boolean":sb.append("bool");break;
            case "List":
                Object type=((sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl)fld.getGenericType()).getActualTypeArguments()[0];

                sb.append("List<").append(GenericListToString(type)).append(">");break;
            case "Map":
                Object maptype1=((sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl)fld.getGenericType()).getActualTypeArguments()[0];
                Object maptype2=((sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl)fld.getGenericType()).getActualTypeArguments()[1];

                sb.append("Dictionary<").append(GenericListToString(maptype1)).append(",").append(GenericListToString(maptype2)).append(">");break;

        }
        sb.append(" " + fld.getName().substring(0, 1).toUpperCase() + fld.getName().substring(1));
        sb.append(" { get; set; }\n");
    }
    private static String GenericListToString(Object type){
        if(type instanceof Class){
            Class typeClass= (Class) type;
            String typeName=typeClass.getSimpleName();
            switch (typeClass.getSimpleName()){
                case "Long":typeName="long";break;
                case "Integer":typeName="int";break;
                case "String":typeName="string";break;
                case "Boolean":typeName="bool";break;
                case "boolean":typeName="bool";break;
                default:break;
            }
            return typeName;
        } else {
            Object obj=((sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl)type).getActualTypeArguments()[0];
            return  "List<"+GenericListToString(obj)+">";
        }

    }

}
