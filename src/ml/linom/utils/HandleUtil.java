package ml.linom.utils;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class HandleUtil {
    private static Properties PROPERTIES;
    private static Properties PROPERTIES1;
    private static File FILE;

    static {
        try {
            PROPERTIES = new Properties();
            PROPERTIES1 = new Properties();
            InputStream resource = HandleUtil.class.getClassLoader().getResourceAsStream("storage.propertise");
            PROPERTIES.load(resource);
            FILE = new File( "config.ini");
            if (!FILE.exists()) FILE.createNewFile();
            PROPERTIES1.load(new FileInputStream(FILE));
            if (PROPERTIES1.isEmpty()){
                PROPERTIES1.setProperty("status","0");
                PROPERTIES1.setProperty("filePath","");
            }
            FileOutputStream fos = new FileOutputStream(FILE);
            PROPERTIES1.store(fos,"create");
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重置后，自动初始化
     * dll扩展文件添加，创建文件副本
     *
     * @return
     */
    public static boolean reset(){
        //TODO 编写重置工具
        PROPERTIES1.setProperty("status", "0");
        init();
        return true;
    }

    public static boolean init() {
        //加载配置
        Properties properties1 = PROPERTIES1;
        if (properties1.getProperty("status").equals("1")) ;
        else {
            System.out.println("请输入原神所在文件夹路径");
            while (true) {
                try {
                    Scanner in = new Scanner(System.in);
                    String ins = in.nextLine();
                    ins = ins.replaceAll("\\\\", "/");
                    properties1.setProperty("filePath", ins);
                    properties1.setProperty("status", "1");
                    //修改本程序配置文件
                    FileOutputStream fos = new FileOutputStream(FILE);
                    properties1.store(fos, "init");
                    fos.flush();
                    fos.close();
                    //修改原神登录器配置文件
                    FileInputStream fis = new FileInputStream(properties1.getProperty("filePath") + "/config.ini");
                    Properties properties = new Properties();
                    int r = 0;
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((r = fis.read()) != -1) {
                        stringBuilder.append((char) r);
                    }
                    String string = stringBuilder.toString();
                    ByteArrayInputStream bais = new ByteArrayInputStream(string.getBytes());
                    properties.load(bais);
                    string = string.replaceFirst("game_install_path=" + properties.getProperty("game_install_path"),
                            "game_install_path=" + properties1.getProperty("filePath") + PROPERTIES.getProperty("childFilePath"));

                    fos = new FileOutputStream(properties1.getProperty("filePath") + "/config.ini");
                    fos.write(string.getBytes());
                    fos.flush();
                    fos.close();
                    System.out.println("***已修改路径为:"+ins);
                    break;
                } catch (IOException e) {
                    System.out.println("输入路径找不到，请重新确认输入");
                }
            }
        }
        return true;
    }

    public static boolean changeServer(int option) {
        try {
            //读取本程序的配置文件
            Properties properties = PROPERTIES;
            Properties properties1 = PROPERTIES1;
            //拼接文件路径
            String s1 = properties1.getProperty("filePath") + "/config.ini";
            String s2 = properties1.getProperty("filePath") + properties.getProperty("childFilePath") + "/config.ini";
            //读取原神两个配置文件
            FileInputStream fin = new FileInputStream(s1);
            FileInputStream finIn = new FileInputStream(s2);
            Properties config = new Properties();

            int r;
            StringBuilder content1 = new StringBuilder();
            StringBuilder content2 = new StringBuilder();
            while ((r = fin.read()) != -1) {
                content1.append((char) r);
            }
            while ((r = finIn.read()) != -1) {
                content2.append((char) r);
            }

            String c1 = content1.toString();

            ByteArrayInputStream bais = new ByteArrayInputStream(c1.getBytes());
            config.load(bais);

            if (option == 0) {//官服
                System.out.println("***选择的是官服");
                replaceFirst(content1, "cps=" + config.getProperty("cps"), "cps=" + properties.getProperty("gcps"));
                replaceFirst(content2, "cps=" + config.getProperty("cps"), "cps=" + properties.getProperty("gcps"));
                replaceFirst(content1, "channel=" + config.getProperty("channel"), "channel=" + properties.getProperty("gchannel"));
                replaceFirst(content2, "channel=" + config.getProperty("channel"), "channel=" + properties.getProperty("gchannel"));
                replaceFirst(content1, "sub_channel=" + config.getProperty("sub_channel"), "sub_channel=" + properties.getProperty("gsub_channel"));
                replaceFirst(content2, "sub_channel=" + config.getProperty("sub_channel"), "sub_channel=" + properties.getProperty("gsub_channel"));
            } else {//b服
                System.out.println("***选择的是b服");
                replaceFirst(content1, "cps=" + config.getProperty("cps"), "cps=" + properties.getProperty("bcps"));
                replaceFirst(content2, "cps=" + config.getProperty("cps"), "cps=" + properties.getProperty("bcps"));
                replaceFirst(content1, "channel=" + config.getProperty("channel"), "channel=" + properties.getProperty("bchannel"));
                replaceFirst(content2, "channel=" + config.getProperty("channel"), "channel=" + properties.getProperty("bchannel"));
                replaceFirst(content1, "sub_channel=" + config.getProperty("sub_channel"), "sub_channel=" + properties.getProperty("bsub_channel"));
                replaceFirst(content2, "sub_channel=" + config.getProperty("sub_channel"), "sub_channel=" + properties.getProperty("bsub_channel"));
            }

            fin.close();
            finIn.close();

            FileOutputStream fos = new FileOutputStream(s1);
            FileOutputStream fosIn = new FileOutputStream(s2);
            fos.write(content1.toString().getBytes());
            fosIn.write(content2.toString().getBytes());
            fos.flush();
            fosIn.flush();
            fos.close();
            fosIn.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void replaceFirst(StringBuilder builder, String from, String to) {
        int index = builder.indexOf(from);
//        while (index != -1) {
        builder.replace(index, index + from.length(), to);
//            index += to.length(); // Move to the end of the replacement
//            index = builder.indexOf(from, index);
//        }
    }

}
