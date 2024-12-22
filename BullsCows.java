import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class BullsCows {

    public static void main(String[] args) {
        BullsCows game = new BullsCows();
        game.run();
    }
}

class BullsCows  {
    String secretCode;
    Scanner scanner = new Scanner(System.in);
    int digitsNum;
    int symbolsNum;

    public void run() {
        System.out.println("Input the length of the secret code:");
        if(scanner.hasNextInt()){
            digitsNum = scanner.nextInt();
            if(digitsNum <= 0) {
                System.out.printf("Error: \"%s\" isn't a valid number.", digitsNum);
                return;
            }
        }
        else {
            String invalidInput = scanner.nextLine();
            System.out.printf("Error: \"%s\" isn't a valid number.", invalidInput);
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        //symbolsNum = scanner.next();
        if(scanner.hasNextInt()){
            symbolsNum = scanner.nextInt();
        }
        else {
            String invalidInput = scanner.nextLine();
            System.out.printf("Error: \"%s\" isn't a valid number.", invalidInput);
            return;
        }

        if (symbolsNum < digitsNum) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", digitsNum, symbolsNum);
            return;
        }
        else if(symbolsNum > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        secretCode = generateSecretCode(digitsNum, symbolsNum);

        printPreparationMessage();

        int i = 0;
        boolean maxBulls;
        do {
            i++;
            System.out.printf("Turn %d:%n", i);
            String guess = scanner.next();
            maxBulls = getGrade(guess);
        } while(!maxBulls);
        System.out.println("Congratulations! You guessed the secret code.");
    }

    public String generateSecretCode(int n, int s) {
        ArrayList<Character> symbols = getSymbolsPool(s);
        ArrayList<Character> code = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < n; i++) {
            int index = random.nextInt(s - i);
            code.add(symbols.get(index));
            symbols.remove(index);
        }
        //System.out.println(Arrays.toString(symbols.toArray()));
        //System.out.println(Arrays.toString(code.toArray()));
        return getStringFromList(code);
    }

    public ArrayList<Character> getSymbolsPool(int s) {
        ArrayList<Character> symbols = new ArrayList<>();
        if(s <= 10) {
            for(char c = '0';  c < '0' + s; c++){
                symbols.add(c);
            }
        }
        if(s > 10) {
            for(char c = '0';  c <= '9'; c++){
                symbols.add(c);
            }
            for(char c = 'a'; c <'a'+ s - 10; c++) {
                symbols.add(c);
            }
        }
        //System.out.println(Arrays.toString(symbols.toArray()));
        return symbols;
    }

    public boolean getGrade(String guess) {
        char[] guessCharArray = guess.toCharArray();
        char[] secretCodeCharArray = secretCode.toCharArray();
        int bulls = 0;
        int cows = 0;

        for(int i = 0; i < digitsNum; i++) {
            for(int j = 0; j < digitsNum; j++) {
                if(guessCharArray[i]== secretCodeCharArray[j] && i==j){
                    bulls++;
                }
                else if(guessCharArray[i]== secretCodeCharArray[j] && i!=j){
                    cows++;
                }
            }
        }
        if(cows == 0 && bulls == 0)
            System.out.printf("Grade: None%n");
        else if (cows == 0 && bulls > 0)
            System.out.printf("Grade: %d bull(s)%n", bulls);
        else if (cows > 0 && bulls == 0)
            System.out.printf("Grade: %d cow(s) %n", cows);
        else if (cows > 0 && bulls > 0)
            System.out.printf("Grade: %d bull(s) and %d cow(s) %n", bulls, cows);

        return bulls == digitsNum;
    }

    public void printPreparationMessage() {
        System.out.print("The secret is prepared: ");
        for(int i = 0; i < digitsNum; i++) {
            System.out.print("*");
        }
        if(symbolsNum <= 10) {
            System.out.printf(" (0-%c)", '0' + (symbolsNum-1) );
        }
        else {
            System.out.printf(" (0-9, a-%c)", 'a'+ (symbolsNum - 11)  );
        }
        System.out.println("\nOkay, let's start a game!");
    }

    public String getStringFromList(ArrayList<Character> list){
        StringBuilder builder = new StringBuilder(list.size());
        for(char c : list) {
            builder.append(c);
        }
        return builder.toString();
    }
}
