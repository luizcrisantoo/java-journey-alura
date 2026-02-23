package modulo3;
import java.util.Scanner;

public class PositivoNegativo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite um número: ");
        float num = scanner.nextFloat();

        if(num > 0){
            System.out.println("Número positivo");
        } else if (num == 0){
            System.out.println("Nulo");
        }else {
            System.out.println("Negativo");
        }
    }
    
}