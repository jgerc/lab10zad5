import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String bankCode;

        do {
            System.out.print("Enter the first three digits of account number: ");
            bankCode = scanner.nextLine().trim();
        } while (!(bankCode.matches("\\d{3}")));

        String fileUrl = "https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                URI.create(fileUrl).toURL().openStream()))) {

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(bankCode)) {
                    String[] parts = line.split("\\t");
                    if (parts.length >= 2) {
                        System.out.println("Number of the bank: " + parts[0].trim());
                        System.out.println("Name of the bank: " + parts[1].trim());
                        found = true;
                        break;
                    }
                }
            }

            if (!found) System.out.println("No information was found about the specified bank number.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}