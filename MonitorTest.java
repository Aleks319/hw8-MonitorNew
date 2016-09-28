package lesson8.hw.MonitorNew;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class MonitorTest {

    private static class MyEvents implements IFileEventsPlus {
        public void onFileAdded(String path) {
            System.out.println("File added: " + path);
        }
        
        public void onFileDeleted(String path) {
            System.out.println("File deleted: " + path);
        }

        @Override
        public void onFileChanged(String path) {
            System.out.println("File changed: " + path + "\n" +
            " last modification: " + new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SS").format(new File(path).lastModified()));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Input directory: ");
        String path = sc.nextLine();

        MonitorNew m = new MonitorNew(path);
        m.setTimeout(5000);
        m.setEvents(new MyEvents());
        m.start();
    }
}