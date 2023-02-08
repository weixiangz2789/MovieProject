import java.util.ArrayList;

public class MovieCollectionRunner {
  public static void main(String[] args) {
    String csvFile = "src\\movies_data.csv";
    MovieCollection collection = new MovieCollection(csvFile);
    collection.menu();
  }
}
