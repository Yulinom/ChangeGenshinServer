package ml.linom.bean;

import ml.linom.utils.HandleUtil;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HandleUtil.init();
        System.out.println("请输入对应数字选择功能\n\t0.换服\n\t1.重置（如果原神文件发生修改请选择）");
        Scanner in = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            int i = 0;
            try {
                i = in.nextInt();
            } catch (InputMismatchException e) {
                in.next();
            }
            if (i == 0) {
                System.out.println("请输入对应数字选择服务器\n\t0.官服\n\t1.b服");
                boolean f = true;
                int j = 0;
                while (f) {
                    try {
                        Scanner hin = new Scanner(System.in);
                        j = hin.nextInt();
                        if (j == 0 || j == 1) f = false;
                    } catch (InputMismatchException e) {
                        System.out.println("请输入正确的数字");
                    }
                }
                HandleUtil.changeServer(j);
                flag = false;
            } else if (i == 1) {
                HandleUtil.reset();
                flag = false;
            } else {
                System.out.println("输入有误，请重新输入");
            }
        }
    }
}
