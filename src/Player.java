/******************************************************************
 * @file :                     Player.java
 * @description:               Represents a soccer player with attributes such as name, jersey number, club, position, nationality, age, appearances,
 *                             wins, goals, and assists. Provides constructors to create player objects from CSV data or by copying another player,
 *                             as well as toString(), equals(), and compareTo(). Does not have getters or setters since each object of type Player is
 *                             only created using the parsed data set passed to the constructor
 * @author:                    Elham Fayzi
 * @date:                      Nov 13, 2025
 *
 * @DataSet:                   All Time Premier League Player Statistics (https://www.kaggle.com/datasets/rishikeshkanabar/premier-league-player-statistics-updated-daily)
 ******************************************************************/

public class Player implements Comparable<Player> {
    // Player attributes
    private String name;
    private int jerseyNum;
    private String club;
    private String position;
    private String nationality;
    private int age;
    private int appearances;
    private int wins;
    private int goals;
    private int assists;

    // Default Constructor
    public Player() {
        name = null;
        jerseyNum = 0;
        club = null;
        position = null;
        nationality = null;
        age = 0;
        appearances = 0;
        wins = 0;
        goals = 0;
        assists = 0;
    }

    // Parameterized Constructor
    public Player(String[] data) {
        name = data[0];
        jerseyNum = Integer.parseInt(data[1].trim());
        club = data[2];
        position = data[3];
        nationality = data[4];
        age = Integer.parseInt(data[5].trim());
        appearances = Integer.parseInt(data[6].trim());
        wins = Integer.parseInt(data[7].trim());
        goals = Integer.parseInt(data[8].trim());
        assists = Integer.parseInt(data[9].trim());
    }

    // Copy Constructor
    public Player(Player p) {
        this.name = p.name;
        this.jerseyNum = p.jerseyNum;
        this.club = p.club;
        this.position = p.position;
        this.nationality = p.nationality;
        this.age = p.age;
        this.appearances = p.appearances;
        this.wins = p.wins;
        this.goals = p.goals;
        this.assists = p.assists;
    }

    // Returns a string representation of the player
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(",").append(jerseyNum).append(",").append(club).append(",").append(position).append(",").append(nationality).append(",").append(age).append(",").append(appearances).append(",").append(wins).append(",").append(goals).append(",").append(assists);
        return sb.toString();
    }

    // Compares players and uses multiple tie-breakers
    @Override
    public int compareTo(Player p) {                        // Compare players based on their number of appearances in games
        if (this.appearances < p.appearances) { return -1; }
        else if (this.appearances > p.appearances) { return 1; }

        // If the number of game appearances of both players were the same, then rank them based on number of wins
        if (this.wins < p.wins) { return -1; }
        else if (this.wins > p.wins) { return 1;}

        // If the number of game appearances and wins of both players were the same, then rank them based on firstName (lexicographic order)
        return this.name.compareTo(p.name);
    }

    // Checks equality between two Player objects
    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }

        Player p = (Player) obj;
        return name.equals(p.name) && jerseyNum == p.jerseyNum && club.equals(p.club);          // Since each object is a player, checking for names, jersey numbers, and clubs would be sufficient to determine whether two players are the same.
    }


    // Getters
    public String getName() { return name; }
    public int getJerseyNum() { return jerseyNum; }
    public String getClub() { return club; }
    public String getPosition() { return position; }
    public String getNationality() { return nationality; }
    public int getAge() { return age; }
    public int getAppearances() { return appearances; }
    public int getWins() { return wins; }
    public int getGoals() { return goals; }
    public int getAssists() { return assists; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setJerseyNum(int jerseyNum) { this.jerseyNum = jerseyNum; }
    public void setClub(String club) { this.club = club; }
    public void setPosition(String position) { this.position = position; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public void setAge(int age) { this.age = age; }
    public void setAppearances(int appearances) { this.appearances = appearances; }
    public void setWins(int wins) { this.wins = wins; }
    public void setGoals(int goals) { this.goals = goals; }
    public void setAssists(int assists) { this.assists = assists; }
}
