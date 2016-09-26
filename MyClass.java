package lesson8.Monitor2;

public class MyClass {

    private static class MyEvents implements IFileEvents {
        public void onFileAdded(String path) {
            System.out.println("File added: " + path);
        }
        
        public void onFileDeleted(String path) {
            System.out.println("File deleted: " + path);
        }
    }

    public static void main(String[] args) {
        Monitor m = new Monitor("c:\\folder1");
        m.setTimeout(2000);
        m.setEvents(new MyEvents());
        m.start();
    }
}