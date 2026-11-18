import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class FolderTymelineAnalyzer {

    // Klasa koja čuva informacije o folderu
    static class FolderInfo {
        String folderName;
        long lastModified;

        FolderInfo(String folderName, long lastModified) {
            this.folderName = folderName;
            this.lastModified = lastModified;
        }
    }

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);
        System.out.println("Unesite putanju do foldera:");
        String folderPath = s.nextLine();
        s.close();

        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Folder ne postoji ili nije validan.");
            return;
        }

        File[] files = folder.listFiles();

        // Bezbednosna provera da li folder može da se pročita
        if (files == null) {
            System.out.println("Nema pristupa sadržaju u izabranom folderu.");
            return;
        }

        ArrayList<FolderInfo> folderList = new ArrayList<>();

        // Prolazimo kroz sadržaj i izdvajamo SAMO FOLDERE
        for (File file : files) {
            try {
                if (file.isDirectory()) { // <--- OVDE JE KLJUČNA PROMENA
                    folderList.add(new FolderInfo(
                            file.getName(),
                            file.lastModified()
                    ));
                }
            } catch (Exception e) {
                System.out.println("Ne mogu da pristupim folderu: " + file.getName());
            }
        }

        // Sortiranje foldera po vremenu izmene (od najstarijeg ka najnovijem)
        folderList.sort((f1, f2) -> Long.compare(f1.lastModified, f2.lastModified));

        // Formatiranje datuma
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        System.out.println("\n=== Timeline aktivnosti foldera ===");

        for (FolderInfo info : folderList) {
            Date date = new Date(info.lastModified);
            System.out.println(
                    sdf.format(date) + " - " + info.folderName
            );
        }
        System.out.println("=====================================");
    }
}