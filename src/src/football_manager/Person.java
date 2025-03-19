package football_manager;

import java.io.*;
import java.util.*;

public class Person {
    protected final String name;
    protected final String surName;
    protected final String birthDay;
    protected int motivation; // (1-10)
    protected int anualSalary;

    // Constructors
    public Person(String name, String surName, String birthDay, int motivation, int anualSalary) {
        this.name = name;
        this.surName = surName;
        this.birthDay = birthDay;
        this.motivation = motivation;
        this.anualSalary = anualSalary;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public int getMotivation() {
        return motivation;
    }

    public int getAnualSalary() {
        return anualSalary;
    }

    // Setters
    public void setMotivation(int motivation) {
        this.motivation = motivation;
    }

    public void setAnualSalary(int anualSalary) {
        this.anualSalary = anualSalary;
    }

    // Methods
    public void train() {
        // Logic for training
    }

    public static void createNewPerson(String option, HashMap<String, Player> hashPlayers, HashMap<String, Coach> hashCoaches, HashMap<String, Person> hashOwners,
                                       ArrayList<Person> owners, ArrayList<Player> players, ArrayList<Coach> coaches) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int motivation = 5; // Default motivation value

        String personName;
        do {
            System.out.print("\u001B[34m📛 Enter Name: \u001B[0m");
            personName = scanner.nextLine().trim();
            personName = capitalizeFirstLetterNames(personName);
            if (isNameDuplicate(personName, hashPlayers, hashCoaches, hashOwners)) {
                System.out.println("\u001B[31m🚨 Name already exists! Please enter a different one.\u001B[0m");
            }
        } while (isNameDuplicate(personName, hashPlayers, hashCoaches, hashOwners));

        System.out.println("\u001B[34m👨‍👩‍👧‍👦 Surname:\u001B[0m");
        String personSurName = scanner.nextLine();

        System.out.println("\u001B[34m🎂 Birthday (DD-MM-YYYY):\u001B[0m");
        String birthday = scanner.nextLine();

        System.out.println("\u001B[34m💰 Salary:\u001B[0m");
        int salary = validateIntegerInput(scanner);

        if (option.equalsIgnoreCase("Player")) {
            System.out.println("\u001B[34m🔙 Back number:\u001B[0m");
            int back = validateIntegerInput(scanner);

            System.out.println("\u001B[34m⚽ Position (DAV, POR, DEF, MIG):\u001B[0m");
            String position = scanner.nextLine().toUpperCase();

            int quality = random.nextInt(71) + 30;
            Player newPlayer = new Player(personName, personSurName, birthday, motivation, salary, back, position, quality);


            players.add(newPlayer);
            hashPlayers.put(personName, newPlayer);

            System.out.println("\u001B[32m✅ Player successfully added! ⚽\u001B[0m");

        } else if (option.equalsIgnoreCase("Coach")) {
            System.out.println("\u001B[34m🏆 Victories:\u001B[0m");
            int victories = validateIntegerInput(scanner);
            scanner.nextLine(); // Consume newline
            System.out.println("\u001B[34m🌍 Have you been selected for a national team? (yes/no):\u001B[0m");
            boolean nacional = scanner.nextLine().trim().equalsIgnoreCase("yes");

            Coach newCoach = new Coach(personName, personSurName, birthday, motivation, salary, victories, nacional);


            coaches.add(newCoach);
            hashCoaches.put(personName, newCoach);

            System.out.println("\u001B[32m✅ Coach successfully added! 🎓\u001B[0m");

        } else if (option.equalsIgnoreCase("Owner")) {
            Person newOwner = new Person(personName, personSurName, birthday, motivation, salary);


            owners.add(newOwner);
            hashOwners.put(personName, newOwner);

            System.out.println("\u001B[32m✅ Owner successfully added! 🏢\u001B[0m");

        } else {
            System.out.println("\u001B[31m❌ Error: Invalid option! Please choose 'Player', 'Coach', or 'Owner'.\u001B[0m");
        }
    }

    public static int validateIntegerInput(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31m🚨 Invalid input! Please enter a valid number.\u001B[0m");
                scanner.next();
            }
        }
    }

    public static boolean isNameDuplicate(String name, HashMap<String, Player> players, HashMap<String, Coach> coaches, HashMap<String, Person> owners) {
        return players.containsKey(name) || coaches.containsKey(name) || owners.containsKey(name);
    }

    public static String capitalizeFirstLetterNames(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public static void loadHashmaps(HashMap<String, Person> peopleHash, ArrayList<Person> peopleList) {
        peopleList.forEach(person -> peopleHash.put(person.getName(), person));
    }
    public static void modifyPresident(ArrayList<Team> teams, Scanner scanner) {
        System.out.println("Enter the name of the team whose president you want to modify:");
        String teamName = scanner.nextLine();

        Team teamToModify = null;
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(teamName)) {
                teamToModify = team;
                break;
            }
        }

        if (teamToModify != null) {
            System.out.println("Enter the name of the new president:");
            String newPresidentName = scanner.nextLine();

            Person newPresident = teamToModify.getOwner();
            for (Person owner : teams.stream().map(Team::getOwner).toList()) {
                if (owner.getName().equalsIgnoreCase(newPresidentName)) {
                    newPresident = owner;
                    break;
                }
            }

            if (newPresident != null) {
                teamToModify.setOwner(newPresident);
                System.out.println("President of team '" + teamName + "' has been updated to " + newPresident.getName());
            } else {
                System.out.println("President '" + newPresidentName + "' not found.");
            }
        } else {
            System.out.println("Team '" + teamName + "' not found.");
        }
    }
    public String toFileFormat() {
        return String.format("O;%s;%s;%s;%d;%d", name, surName, birthDay, motivation, anualSalary);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", birthDay='" + birthDay + '\'' +
                ", motivation=" + motivation +
                ", anualSalary=" + anualSalary +
                '}';
    }
}