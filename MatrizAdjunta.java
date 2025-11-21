import java.io.*;
import java.util.ArrayList;

public class MatrizAdjunta {

    public static void main(String[] args) {

        String archivoEntrada = "matriz.txt";            
        String archivoSalida = "matriz_adjunta.txt";      

        try {
            double[][] M = leerMatriz(archivoEntrada);

  
            if (M.length != M[0].length) {
                escribirTexto(archivoSalida, "La matriz debe ser cuadrada.");
                return;
            }

            double[][] adj = adjunta(M);

            escribirMatriz(archivoSalida, adj);

            System.out.println("Adjunta generada. Revisa el archivo: " + archivoSalida);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    public static double[][] adjunta(double[][] M) {

        int n = M.length;
        double[][] cofactores = new double[n][n];

        // Aquí saco todos los cofactores
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                double[][] menor = submatriz(M, i, j);      
                double detMenor = determinante(menor);     
                
                cofactores[i][j] = Math.pow(-1, i + j) * detMenor;
            }
        }

      
        return transpuesta(cofactores);
    }
}