
import java.util.*;
import java.io.*;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class chatbot {
    static HashMap<String, String> responses = new HashMap<>();

    public static void loadResponses(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader("responses.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length >= 2) {
                    responses.put(parts[0].trim().toLowerCase(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("error reading response.");
        }
    }

    static Random random = new Random();

    static String[] failureResponse = {
            "I'm not sure I understand.",
            "Can you rephrase that?",
            "Sorry, I don’t know how to respond to that.",
            "I'm still learning. Try asking something else."
    };

    public static String defaultresponse() {
        int n = random.nextInt(failureResponse.length);
        return failureResponse[n];
    }


    public static void timeBasedGreeting() {
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();
        if (hour >= 5 && hour < 12) {
            System.out.println("Good Morning");
        } else if (hour >= 12 && hour < 17) {
            System.out.println("Good Afternoon");
        } else {
            System.out.println("Good Evening");
        }
    }
    public  static String getcurrentTime(){
        DateTimeFormatter timeFormatter= DateTimeFormatter.ofPattern("HH:mm:ss a");
        return LocalTime.now().format(timeFormatter).toString();
    }
    public static String getCurrentDate() {
        DateTimeFormatter dateFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.now().format(dateFormatter).toString();
    }
    public static void getCurrentDay(){
        LocalDate currentDate=LocalDate.now();
        DayOfWeek dayOfWeek=currentDate.getDayOfWeek();
        System.out.println(dayOfWeek);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        loadResponses("responses.txt");
        timeBasedGreeting();
        System.out.println("Hi how can I help you: type bye to quit");
        while (true) {
            System.out.print("You: ");
            input = scanner.nextLine().toLowerCase();
            if (input.equals("bye")) {
                System.out.println("Chatbot: " + responses.get("bye"));
                break;
            } else if (responses.containsKey(input)) {
                System.out.println("chatbot: " + responses.get(input));
            } else if(input.equals("time")){ 
                System.out.println("Chatbot: current time is "+getcurrentTime());
            } else if (input.equals("date")) {
                System.out.println("Chatbot: Today's date is: "+getCurrentDate());
            } else if(input.equals("dayofweek")){
                System.out.print("Chatbot: Today is ");
                getCurrentDay();
            }else {
                System.out.println("Chatbot: " + defaultresponse());
            }
        }
        scanner.close();
    }
    
}
