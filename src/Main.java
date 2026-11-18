import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    // Klasa koja čuva informacije o fajlu
    static class FileInfo {
        String fileName;
        long lastModified;

        FileInfo(String fileName, long lastModified) {
            this.fileName = fileName;
            this.lastModified = lastModified;
        }
    }

    public static void main(String[] args) {

        // Putanja do foldera koji se analizira
        //String folderPath = "C:\\TestFolder";
        Scanner s = new Scanner(System.in);
        System.out.println("Unesite putanju do foldera:");
        String folderPath = s.nextLine();

        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Folder ne postoji ili nije validan.");
            return;
        }

        File[] files = folder.listFiles();
        ArrayList<FileInfo> fileList = new ArrayList<>();

        // Prolazimo kroz fajlove
        for (File file : files) {
            try {
                if (file.isFile()) {
                    fileList.add(new FileInfo(
                            file.getName(),
                            file.lastModified()
                    ));
                }
            } catch (Exception e) {
                System.out.println("Ne mogu da pristupim fajlu: " + file.getName());
            }
        }

        // Sortiranje fajlova po vremenu izmene
        Collections.sort(fileList, new Comparator<FileInfo>() {
            @Override
            public int compare(FileInfo f1, FileInfo f2) {
                return Long.compare(f1.lastModified, f2.lastModified);
            }
        });

        // Formatiranje datuma
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        System.out.println("=== Timeline aktivnosti fajlova ===");

        for (FileInfo info : fileList) {
            Date date = new Date(info.lastModified);
            System.out.println(
                    sdf.format(date) + " - " + info.fileName
            );
        }
        System.out.println("=====================================");
    }
}