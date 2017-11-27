package ttsu.mrozu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Communication {
    public static void main(String[] args) throws IOException {
        Algorithm algorithm = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;
        br.readLine();
        System.out.println("PONG");
        while(true){
            input = br.readLine();
            if(input.equals("PRZEGRAŁEŚ") || input.equals("WYGRAŁEŚ"))
                break;
            else {
                String[] values = input.split(" ");

                if(input.equals("ZACZYNAJ"))
                    algorithm.add(0,0,0,0);
                else if(values.length == 1)
                    algorithm = new Algorithm(Integer.parseInt(values[0]));
                else if(values.length == 4)
                    algorithm.add(Integer.parseInt(values[0]),
                                  Integer.parseInt(values[1]),
                                  Integer.parseInt(values[2]),
                                  Integer.parseInt(values[3]));
            }
        }
    }
}
