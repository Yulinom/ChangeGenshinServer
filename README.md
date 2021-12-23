# ChangeGenshinServer
- ### Java实现文件修改，简单实现原神官服b服切换
- ### 项目关键代码
--- 
*以下代码全在uitils包下的一个类中，由于功能简单，使用的是面向过程编程*
1. init()函数
2. changeServer(int option)函数
- ### 项目总结
1. java编写并运行.bat文件
    - 起初，由于原神文件所在目录的权限问题，Java无法直接对目录下的文件进行修改，经过查找，得到了解决方法
    - 解决Java修改只读文件的方法
    - 首先将只读文件复制一份，然后在复制中修改，最后将原文件删除，把复制文件改成同名替换
    - 关键在于原文件的删除，我遇到的情况是，目录拒绝访问，更别谈删除目录下的文件
    - 然后想到了在命令行下删除的方法，命令行下实验删除可行，于是问题的解决就变成了Java编写命令行命令
    - Java编写.bat或.cmd等后缀的批处理命令文件，命令像平时的命令行一样即可，即cd进入文件夹等
    - Java执行这些文件的关键语句如下
    ```java 
    /**  
    *@Param locationCmd .cmd或.bat的文件路径  
    */  
      public static void  callCmd(String locationCmd){  
          try {  
          Process child = Runtime.getRuntime().exec(locationCmd);//获取jvm并创建命令行子进程  
          InputStream in = child.getInputStream();  
          int c;  
          while ((c = in.read()) != -1) {  
              System.out.println((char)c);  
      }  
       in.close();  
       try {  
           child.waitFor();  
       } catch (InterruptedException e) {  
           e.printStackTrace();  
       }  
       System.out.println("done");  
     } catch (IOException e) {  
           e.printStackTrace();  
     }
      ```
2. 读取配置文件
  - 读取resources文件夹下的配置文件常用的根据InputStream输入流读取问题：我的原想法是properties.load(InputStream)后，再次利用输入流获取一个StringBuilder
  - 问题就出在这里，获取的StringBuilder内容为空，因为InputStream在load进properties对象后，就不能再次从头读了，正常的Stream流类指针是不可重置的
3. 文件路径问题
5. StringBuilder替换问题
6. 循环Scanner出现异常问题
7. 打jar包
