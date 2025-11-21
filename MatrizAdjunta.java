import java.io.*;
import java.util.ArrayList;

public class MatrizAdjunta {

    public static void main(String[] args) {

        String archivoEntrada = "matriz.txt";            // matriz original
        String archivoSalida = "matriz_adjunta.txt";      // matriz adjunta

        try {
            double[][] M = leerMatriz(archivoEntrada);

            // Verifico que sea cuadrada
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

                double[][] menor = submatriz(M, i, j);      // elimino fila i y col j
                double detMenor = determinante(menor);      // determinante del menor

                // Fórmula del cofactor: (−1)^(i+j) * det(menor)
                cofactores[i][j] = Math.pow(-1, i + j) * detMenor;
            }
        }

   
        return transpuesta(cofactores);
    }

    public static double[][] submatriz(double[][] M, int filaEliminar, int colEliminar) {

        int n = M.length;
        double[][] menor = new double[n - 1][n - 1];

        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == filaEliminar) continue;

            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == colEliminar) continue;

                menor[r][c] = M[i][j];
                c++;
            }
            r++;
        }

        return menor;
    }

    public static double determinante(double[][] M) {

        int n = M.length;

        // Caso base 1x1
        if (n == 1) return M[0][0];

        // Caso base 2x2
        if (n == 2)
            return M[0][0] * M[1][1] - M[0][1] * M[1][0];

        // Recursivo para 3x3, 4x4, 5x5, etc.
        double det = 0;

        for (int j = 0; j < n; j++) {
            double[][] menor = submatriz(M, 0, j);
            det += Math.pow(-1, j) * M[0][j] * determinante(menor);
        }

        return det;
    }

    public static double[][] transpuesta(double[][] M) {

        int filas = M.length;
        int cols = M[0].length;

        double[][] T = new double[cols][filas];

        for (int i = 0; i < filas; i++)
            for (int j = 0; j < cols; j++)
                T[j][i] = M[i][j];

        return T;
    }

    public static double[][] leerMatriz(String archivo) throws IOException {

        ArrayList<double[]> filas = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;

        while ((linea = br.readLine()) != null) {
            linea = linea.trim();
            if (linea.isEmpty()) continue;

            String[] partes = linea.split("\\s+");
            double[] fila = new double[partes.length];

            for (int i = 0; i < partes.length; i++)
                fila[i] = Double.parseDouble(partes[i]);

            filas.add(fila);
        }

        br.close();

        double[][] M = new double[filas.size()][filas.get(0).length];
        for (int i = 0; i < filas.size(); i++)
            M[i] = filas.get(i);

        return M;
    }

    public static void escribirMatriz(String archivo, double[][] M) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

        for (double[] fila : M) {
            for (int j = 0; j < fila.length; j++) {
                bw.write(String.format("%.4f", fila[j]));
                if (j < fila.length - 1) bw.write(" ");
            }
            bw.newLine();
        }

        bw.close();
    }

    public static void escribirTexto(String archivo, String texto) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        bw.write(texto);
        bw.newLine();
        bw.close();
    }
}
