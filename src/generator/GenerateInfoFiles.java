package generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * GenerateInfoFiles
 *
 * Al ejecutarse, genera archivos de prueba en la carpeta 'input/' del proyecto:
 * 1. input/products.txt          → información de productos
 * 2. input/salesmen_info.txt     → información de vendedores
 * 3. input/sales_{name}_{id}.txt → ventas pseudoaleatorias de un vendedor
 *
 * No solicita datos al usuario. Muestra en consola un mensaje de éxito o error.
 */
public class GenerateInfoFiles {

    private static Random rnd = new Random();

    // Productos de Tostao
    private static String[] products = {
        "Café Americano", "Latte", "Capuchino", "Mocaccino",
        "Croissant", "Pan de Queso", "Empanada", "Tostao Mixto",
        "Torta de Zanahoria", "Brownie"
    };

    // Vendedores (Friends)
    private static String[] names = { "Rachel", "Ross", "Monica", "Chandler", "Joey", "Phoebe" };
    private static String[] lastNames = { "Green", "Geller", "Geller", "Bing", "Tribbiani", "Buffay" };

    // Guardará los IDs generados para cada vendedor
    private static long[] vendorIds = new long[names.length];

    public static void main(String[] args) {
        try {
            // Genera productos
            createProductsFile(products.length);

            // Genera vendedores y guarda los IDs
            createSalesManInfoFile(names.length);

            // Genera archivos de ventas usando los mismos IDs
            for (int i = 0; i < names.length; i++) {
                createSalesMenFile(5, names[i], vendorIds[i]);
            }

            System.out.println("Generación de archivos completada con éxito.");
        } catch (IOException e) {
            System.err.println("Error al generar archivos: " + e.getMessage());
        }
    }

    public static void createProductsFile(int productsCount) throws IOException {
        File file = new File("input/products.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < productsCount; i++) {
                String id = "P" + (i + 1);
                int price = 2000 + rnd.nextInt(8000); // precios entre 2000 y 10000 COP
                writer.write(id + ";" + products[i] + ";" + price);
                writer.newLine();
            }
        }
    }

    public static void createSalesManInfoFile(int salesmanCount) throws IOException {
        File file = new File("input/salesmen_info.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < salesmanCount; i++) {
                long id = 10_000_000L + rnd.nextInt(90_000_000);
                vendorIds[i] = id; // guardar ID para luego usarlo en los archivos de ventas
                writer.write("CC;" + id + ";" + names[i] + ";" + lastNames[i]);
                writer.newLine();
            }
        }
    }

    public static void createSalesMenFile(int randomSalesCount, String name, long id)
            throws IOException {
        File file = new File("input/sales_" + name + "_" + id + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Línea de cabecera: tipo y documento
            writer.write("CC;" + id);
            writer.newLine();

            // Líneas de ventas
            for (int i = 0; i < randomSalesCount; i++) {
                int productId = rnd.nextInt(products.length) + 1;
                int quantity = rnd.nextInt(5) + 1;
                writer.write("P" + productId + ";" + quantity + ";");
                writer.newLine();
            }
        }
    }
}
