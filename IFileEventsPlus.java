package lesson8.hw.MonitorNew;

public interface IFileEventsPlus {
    void onFileAdded(String path);
    void onFileDeleted(String path);

    void onFileChanged(String path);
}