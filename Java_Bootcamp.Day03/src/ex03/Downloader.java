package ex03;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.InputStream;
import java.util.List;

public class Downloader extends Thread {
    private List<String> urls;
    private int threadsCount;
    private int threadNumber;
    private String files_urls = "files_urls.txt";

    public Downloader(int threadsCount, int threadNumber) {
        this.threadsCount = threadsCount;
        this.threadNumber = threadNumber;
        fillList();
    }

    @Override
    public void run() {
        for (int i = threadNumber; i < urls.size(); i += threadsCount) {
            String url = urls.get(i);
            downloadFile(url, i);
        }
    }

    public void downloadFile(String address, int i) {
        try {
            System.out.println(Thread.currentThread().getName() + " started downloading file number " + (i + 1));
            URL url = new URL(address);
            String fileName = getFileName(address);
            Path outPath = getFilePath(fileName);
            InputStream in = url.openStream();
            Files.copy(in, outPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println(Thread.currentThread().getName() + " finished downloading file number " + (i + 1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String getFileName(String address) {
        String[] parts = address.split("/");
        return parts[parts.length - 1];
    }

    private Path getFilePath(String fileName) throws Exception {
        String currentPath = new java.io.File(".").getCanonicalPath();
        Path path = Path.of(currentPath + "/" + fileName);
        return path;
    }

    private void fillList() {
        try {
            urls = Files.readAllLines(Path.of(files_urls));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void printList() {
        for (String url : urls) {
            System.out.println(url);
        }
    }

}
