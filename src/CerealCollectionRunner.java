import java.util.ArrayList;

public class CerealCollectionRunner {
  public static void main(String arg[]) {
    String csvFile = "src\\cereal.csv";
    CerealCollection collection = new CerealCollection(csvFile);
    ArrayList<Cereal> cerealList = collection.getCereals();
    for (Cereal cereal : cerealList) {
      System.out.println(cereal);
    }
  }
}