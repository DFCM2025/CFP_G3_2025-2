package processor;

import java.io.*;
import java.util.*;

/**
 * ProcessSales
 *
 * Lee los archivos en input/ generados en la Entrega 1, valida la información
 * y genera un reporte consolidado en output/report.txt.
 *
 * Mensajes en consola para detectar problemas.
 */
public class ProcessSales {

    public static void main(String[] args) {
        try {
            // 1) Carga los productos
            Map<String, Product> products = loadProducts("input/products.txt");

            // 2) Carga los vendedores
            Map<Long, Salesman> salesmen = loadSalesmen("input/salesmen_info.txt");

            // 3) Procesa los archivos de ventas (todos los que empiezan con "sales_")
            Map<String, SaleSummary> summary = new HashMap<>();
            File inputFolder = new File("input");
            File[] files = inputFolder.listFiles((dir, name) -> name.startsWith("sales_"));

            if (files == null || files.length == 0) {
                System.out.println("⚠ No se encontraron archivos de ventas en input/ (prefijo 'sales_').");
            } else {
                for (File f : files) {
                    processSalesFile(f, products, salesmen, summary);
                }
            }

            // 4) Genera el reporte final (ordenado por total vendido descendente)
            generateReport(summary, "output/report.txt");

            System.out.println("✅ Reporte generado en output/report.txt");
        } catch (IOException e) {
            System.err.println("❌ Error en la ejecución: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------------ MÉTODOS ------------------

    private static Map<String, Product> loadProducts(String path) throws IOException {
        Map<String, Product> map = new HashMap<>();
        File f = new File(path);
        if (!f.exists()) {
            throw new FileNotFoundException("Archivo de productos no encontrado: " + path);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                String[] parts = line.split(";");
                if (parts.length != 3) {
                    System.out.println("⚠ products.txt - línea " + lineNo + " formato inválido: " + line);
                    continue;
                }
                String id = parts[0].trim();       // espera algo como "P1"
                String name = parts[1].trim();
                String priceStr = parts[2].trim();
                try {
                    double price = Double.parseDouble(priceStr.replace(",", "."));
                    if (price < 0) {
                        System.out.println("⚠ products.txt - precio negativo linea " + lineNo + ": " + line);
                        continue;
                    }
                    map.put(id, new Product(id, name, price));
                } catch (NumberFormatException ex) {
                    System.out.println("⚠ products.txt - precio no numérico linea " + lineNo + ": " + line);
                }
            }
        }
        return map;
    }

    private static Map<Long, Salesman> loadSalesmen(String path) throws IOException {
        Map<Long, Salesman> map = new HashMap<>();
        File f = new File(path);
        if (!f.exists()) {
            throw new FileNotFoundException("Archivo de vendedores no encontrado: " + path);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                String[] parts = line.split(";");
                if (parts.length != 4) {
                    System.out.println("⚠ salesmen_info.txt - línea " + lineNo + " formato inválido: " + line);
                    continue;
                }
                try {
                    long id = Long.parseLong(parts[1].trim());
                    String fn = parts[2].trim();
                    String ln = parts[3].trim();
                    map.put(id, new Salesman(id, fn, ln));
                } catch (NumberFormatException ex) {
                    System.out.println("⚠ salesmen_info.txt - ID no numérico línea " + lineNo + ": " + line);
                }
            }
        }
        return map;
    }

    private static void processSalesFile(File file,
                                         Map<String, Product> products,
                                         Map<Long, Salesman> salesmen,
                                         Map<String, SaleSummary> summary) {
        System.out.println("📂 Procesando: " + file.getName());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String header = br.readLine();
            if (header == null) {
                System.out.println("❌ Archivo vacío: " + file.getName());
                return;
            }
            String[] headerParts = header.split(";");
            if (headerParts.length < 2 || !headerParts[0].trim().equals("CC")) {
                System.out.println("❌ Cabecera inválida en " + file.getName() + ": " + header);
                return;
            }
            long salesmanId;
            try {
                salesmanId = Long.parseLong(headerParts[1].trim());
            } catch (NumberFormatException ex) {
                System.out.println("❌ ID vendedor inválido en cabecera de " + file.getName() + ": " + header);
                return;
            }
            if (!salesmen.containsKey(salesmanId)) {
                System.out.println("⚠ Vendedor no registrado (se omite archivo): " + salesmanId + " en " + file.getName());
                return;
            }

            String line;
            int lineNo = 1; // porque ya leí header
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length < 2) {
                    System.out.println("⚠ " + file.getName() + " linea " + lineNo + " formato inválido: " + line);
                    continue;
                }
                String productId = parts[0].trim(); // espera "P3"
                String qtyStr = parts[1].trim();
                // validar producto existe
                if (!products.containsKey(productId)) {
                    System.out.println("⚠ " + file.getName() + " linea " + lineNo + " producto no existe: " + productId);
                    continue;
                }
                int qty;
                try {
                    qty = Integer.parseInt(qtyStr);
                    if (qty <= 0) {
                        System.out.println("⚠ " + file.getName() + " linea " + lineNo + " cantidad inválida: " + qty);
                        continue;
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("⚠ " + file.getName() + " linea " + lineNo + " cantidad no numérica: " + qtyStr);
                    continue;
                }
                Product p = products.get(productId);
                SaleSummary s = summary.get(productId);
                if (s == null) {
                    s = new SaleSummary(p.getName(), 0, 0.0);
                }
                s.addSale(qty, p.getPrice());
                summary.put(productId, s);
            }
        } catch (IOException e) {
            System.out.println("❌ Error leyendo " + file.getName() + ": " + e.getMessage());
        }
    }

    private static void generateReport(Map<String, SaleSummary> summary, String path) throws IOException {
        File out = new File(path);
        if (out.getParentFile() != null) out.getParentFile().mkdirs();

        // ordenar por total vendido descendente
        List<SaleSummary> list = new ArrayList<>(summary.values());
        list.sort((a, b) -> Double.compare(b.getTotal(), a.getTotal()));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(out))) {
            bw.write("Producto;CantidadTotal;TotalVendidoCOP");
            bw.newLine();
            for (SaleSummary s : list) {
                // total como entero (pesos)
                String totalStr = String.format("%.0f", s.getTotal());
                bw.write(s.getName() + ";" + s.getQuantity() + ";" + totalStr);
                bw.newLine();
            }
        }
    }
}

// ------------------ CLASES AUXILIARES ------------------

class Product {
    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        this.id = id; this.name = name; this.price = price;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}

class Salesman {
    private long id;
    private String firstName;
    private String lastName;

    public Salesman(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getter para id (esto elimina la advertencia)
    public long getId() {
        return id;
    }

    // Opcional: getters para nombre y apellido
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

class SaleSummary {
    private String name;
    private int quantity;
    private double total;

    public SaleSummary(String name, int quantity, double total) {
        this.name = name; this.quantity = quantity; this.total = total;
    }

    public void addSale(int q, double price) {
        this.quantity += q;
        this.total += q * price;
    }

    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getTotal() { return total; }
}
