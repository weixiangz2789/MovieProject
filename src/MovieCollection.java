import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;


public class MovieCollection {
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName) {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies() {
    return movies;
  }

  public void menu() {
    String menuOption = "";

    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");

    while (!menuOption.equals("q")) {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();

      if (menuOption.equals("t")) {
        searchTitles();
      } else if (menuOption.equals("c")) {
        searchCast();
      } else if (menuOption.equals("k")) {
        searchKeywords();
      } else if (menuOption.equals("g")) {
        listGenres();
      } else if (menuOption.equals("r")) {
        listHighestRated();
      } else if (menuOption.equals("h")) {
        listHighestRevenue();
      } else if (menuOption.equals("q")) {
        System.out.println("Goodbye!");
      } else {
        System.out.println("Invalid choice!");
      }
    }
  }

  private void importMovieList(String fileName) {
    try {
      movies = new ArrayList<Movie>();
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      while ((line = bufferedReader.readLine()) != null) {
        // get data from the columns in the current row and split into an array
        String[] movieFromCSV = line.split(",");

        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keyword = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genre = movieFromCSV[7];
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);

        // create a Movie object with the row data:
        Movie movie = new Movie(title, cast, director, tagline, keyword, overview, runtime, genre, userRating, year, revenue);

        // add the Movie to movies:
        movies.add(movie);

      }
      bufferedReader.close();
    } catch(IOException exception) {
      System.out.println("Unable to access " + exception.getMessage());
    }
  }

  private void searchTitles() {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1) {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      // sort the results by title
      sortResults(results);

      // now, display them all to the user
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();

        // this will print index 0 as choice 1 in the results list; better for user!
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void sortResults(ArrayList<Movie> listToSort) {
    for (int j = 1; j < listToSort.size(); j++) {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie) {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  private void searchKeywords() {
    System.out.print("Enter a keyword: ");
    String keyword = scanner.nextLine();
    keyword = keyword.toLowerCase();

    ArrayList<Movie> results = new ArrayList<Movie>();

    for (int i = 0; i < movies.size(); i++) {
      String movieTitle = movies.get(i).getKeywords();
      movieTitle = movieTitle.toLowerCase();
      if (movieTitle.indexOf(keyword) != -1) {
        results.add(movies.get(i));
      }
    }

    if (results.size() > 0) {
      sortResults(results);
      for (int i = 0; i < results.size(); i++) {
        String title = results.get(i).getTitle();
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + title);
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = results.get(choice - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
      scanner.nextLine();
    }
  }

  private void searchCast() {
    System.out.print("Enter a person to search for (first or last name): ");
    String name = scanner.nextLine();
    name = name.toLowerCase();

    ArrayList<Movie> results = new ArrayList<Movie>();

    for (int i = 0; i < movies.size(); i++) {
      String castName = movies.get(i).getCast();
      castName = castName.toLowerCase();
      if (castName.indexOf(name) != -1) {
        results.add(movies.get(i));
      }
    }
    ArrayList <String> temp = new ArrayList<>();
    if (results.size() > 0) {
      sortResults(results);
      for (int i = 0; i < results.size(); i++) {
        String castName = results.get(i).getCast();
        String[] castNameArr = castName.split("\\|");
        for (int x = 0; x < castNameArr.length; x ++){
          if (castNameArr[x].toLowerCase().contains(name) && !(temp.contains(castNameArr[x]))){
            temp.add(castNameArr[x]);
          }
        }
      }
      Collections.sort(temp);
      for (int i = 0; i < temp.size(); i ++){
        int choiceNum = i + 1;
        System.out.println("" + choiceNum + ". " + temp.get(i));
      }
      System.out.println("Who would you like to learn more about?");
      System.out.print("Enter number: ");
      String choice = temp.get(scanner.nextInt()-1);
      scanner.nextLine();
      ArrayList <Movie> featured = new ArrayList<>();

      for (int i = 0; i < movies.size(); i++) {
        String movieCast = movies.get(i).getCast();
        if (movieCast.indexOf(choice) != -1) {
          featured.add(movies.get(i));
        }
      }
      sortResults(featured);
      for (int i = 0; i < featured.size(); i ++){
        System.out.println(i+1+ ". " + featured.get(i).getTitle());
      }

      System.out.println("Which movie would you like to learn more about?");
      System.out.print("Enter number: ");
      int choice1 = scanner.nextInt();
      scanner.nextLine();
      Movie selectedMovie = featured.get(choice1 - 1);
      displayMovieInfo(selectedMovie);
      System.out.println("\n ** Press Enter to Return to Main Menu **");
    } else {
      System.out.println("\nNo movie titles match that search term!");
      System.out.println("** Press Enter to Return to Main Menu **");
    }
    scanner.nextLine();
  }

  private void listGenres() {
    ArrayList <String> genreList = new ArrayList<>();
    for (int i = 0; i < movies.size(); i ++){
      String genreString = movies.get(i).getGenres();
      String[] genreArray = genreString.split("\\|");
      for (int x = 0; x < genreArray.length; x ++) {
        if (!(genreList.contains(genreArray[x]))) {
          genreList.add(genreArray[x]);
        }
      }
    }
    Collections.sort(genreList);
    for (int i = 0; i < genreList.size(); i ++){
      System.out.println(i+1 + ". " + genreList.get(i));
    }
    System.out.println("Enter a number: ");
    int num = scanner.nextInt();
    scanner.nextLine();
    String genreChoice = genreList.get(num-1);
    ArrayList<Movie> allMovies = new ArrayList<>();
    for (int i = 0; i < movies.size(); i ++){
      if (movies.get(i).getGenres().contains(genreChoice)){
          allMovies.add(movies.get(i));
      }
    }
    sortResults(allMovies);
    for (int i = 0; i < allMovies.size(); i ++){
      System.out.println(i+1 + ". " + allMovies.get(i).getTitle());
    }
  }
  
  private void listHighestRated() {
    ArrayList<Double> temp = new ArrayList<>();
    ArrayList<Double> highestRated = new ArrayList<>();
    ArrayList<Movie> highestRatedList = new ArrayList<>();
    for (int i = 0; i < movies.size(); i ++){
      temp.add(movies.get(i).getUserRating());
    }
    Collections.sort(temp);
    for (int i = temp.size()-50; i < temp.size(); i ++){
      highestRated.add(temp.get(i));
    }
    for (int i = highestRated.size()-1; i >= 0; i --){
      for (int x = 0; x < movies.size(); x ++){
        if (highestRated.get(i) == movies.get(x).getUserRating() && !highestRatedList.contains(movies.get(x))){
          if (highestRatedList.size() == 50){
            break;
          }
          highestRatedList.add(movies.get(x));
        }
      }
    }
    for (int i = 0; i < highestRatedList.size(); i ++){
      System.out.println(i+1 + ". " + highestRatedList.get(i).getTitle() +  ": " + highestRatedList.get(i).getUserRating());
    }
  }
  
  private void listHighestRevenue() {
    ArrayList<Integer> temp = new ArrayList<>();
    ArrayList<Integer> highestRev = new ArrayList<>();
    ArrayList<Movie> highestRevList = new ArrayList<>();
    for (int i = 0; i < movies.size(); i ++){
      temp.add(movies.get(i).getRevenue());
    }
    Collections.sort(temp);
    for (int i = temp.size()-50; i < temp.size(); i ++){
      highestRev.add(temp.get(i));
    }
    for (int i = highestRev.size()-1; i >= 0; i --){
      for (int x = 0; x < movies.size(); x ++){
        if (highestRev.get(i) == movies.get(x).getRevenue() && !highestRevList.contains(movies.get(x))){
          if (highestRevList.size() == 50){
            break;
          }
          highestRevList.add(movies.get(x));
        }
      }
    }
    for (int i = 0; i < highestRevList.size(); i ++){
      System.out.println(i+1 + ". " + highestRevList.get(i).getTitle() +  ": " + highestRevList.get(i).getRevenue());
    }
  }
}