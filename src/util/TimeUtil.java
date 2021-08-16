package util;

import java.text.SimpleDateFormat;

public class TimeUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");

    public interface Task {
        void execute();
    }

    public static void check(String title, Task task) {
        if (title == null) {
            title = "测试";
        }
        System.out.println("****************");
        System.out.println("【" + title + "】");
        long start = System.currentTimeMillis();
        task.execute();
        long end = System.currentTimeMillis();
        System.out.println("经历：" + (end - start) + "mm");
        System.out.println("****************");
    }

}
