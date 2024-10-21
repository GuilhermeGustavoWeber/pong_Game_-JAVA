import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Ponts {

    // Método para salvar dois inteiros em um arquivo    
    public static void savePTS(int num1, int num2) {        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pontos.txt"))) {           
            writer.write(num1 + "\n");            
            writer.write(num2 + "\n");        
        } catch (IOException e) {            
            e.printStackTrace();
        }    
    }
    // Método para ler os inteiros do arquivo    
    public static int[] loadPTS() {        
        int[] pts = new int[2];        
        try (BufferedReader reader = new BufferedReader(new FileReader("pontos.txt"))) {            
            pts[0] = Integer.parseInt(reader.readLine());            
            pts[1] = Integer.parseInt(reader.readLine());        
        } catch (IOException | NumberFormatException e) {            
            e.printStackTrace(); 
        }        
        return pts;    
    }

    public static int[] resetPTS() {
        int[] pts = new int[2];        
        pts[0] = 0;            
        pts[1] = 0;               
        return pts;
    }
}
