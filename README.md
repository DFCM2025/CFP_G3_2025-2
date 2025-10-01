# Proyecto CFP – Entregas 1 y 2  
**Materia:** Conceptos Fundamentales de Programación  
**Institución:** Politécnico Grancolombiano  
**Estudiante:** *[tu nombre aquí]*  

---

## 📌 Descripción  
Este repositorio contiene las dos primeras entregas del proyecto de CFP.  

- En la **primera entrega** se construyó un generador de archivos de prueba (`GenerateInfoFiles`), que crea los archivos base de productos, vendedores y ventas.  
- En la **segunda entrega** se desarrolló un procesador de archivos (`ProcessSales`), encargado de validar la información y consolidar un reporte con los datos de ventas.  

---

## 📂 Archivos generados y procesados  

### Primera Entrega (`generator/GenerateInfoFiles.java`)  
1. **`products.txt`**  
   ```
   IDProducto;NombreProducto;Precio
   ```
   Ejemplo:  
   ```
   P1;Café Americano;3500
   ```

2. **`salesmen_info.txt`**  
   ```
   TipoDocumento;NúmeroDocumento;Nombre;Apellido
   ```
   Ejemplo:  
   ```
   CC;45283910;Rachel;Green
   ```

3. **`sales_{Nombre}_{Documento}.txt`**  
   ```
   CC;45283910
   P3;2;
   P7;5;
   ```

---

### Segunda Entrega (`processor/ProcessSales.java`)  
- Lee todos los archivos de `input/`.  
- Valida que los productos existan y que las cantidades sean positivas.  
- Reporta inconsistencias en consola (⚠ advertencias, ❌ errores de formato).  
- Genera el archivo `output/report.txt` con el consolidado:  

```
Producto;CantidadTotal;TotalVendidoCOP
Café Americano;15;52500
Brownie;10;45000
...
```

---

## ⚙️ Funcionamiento  

1. **Generar datos de prueba**  
   Ejecutar `GenerateInfoFiles` (paquete `generator`).  
   Esto crea los archivos en la carpeta `input/`.  

2. **Procesar y consolidar ventas**  
   Ejecutar `ProcessSales` (paquete `processor`).  
   Esto produce el archivo `output/report.txt` con el resumen de ventas.  

3. **Mensajes en consola**  
   El sistema informa problemas encontrados, por ejemplo:  
   ```
   📂 Procesando: sales_Rachel_45283910.txt
   ⚠ Producto no existe: P25
   ⚠ Cantidad inválida: -3
   ✅ Reporte generado en output/report.txt
   ```

---

## 📑 Estructura del proyecto  

```
CFP_Proyecto/
 ├─ src/
 │   ├─ generator/
 │   │   └─ GenerateInfoFiles.java
 │   └─ processor/
 │       └─ ProcessSales.java
 ├─ input/
 │   ├─ products.txt
 │   ├─ salesmen_info.txt
 │   ├─ sales_Rachel_45283910.txt
 │   └─ ...
 ├─ output/
 │   └─ report.txt
 └─ README.md
```

---

## ✅ Conclusiones  
- En la **primera entrega** se aprendió a generar datos de prueba en archivos planos.  
- En la **segunda entrega** se procesaron los archivos, se aplicaron validaciones de coherencia y se consolidó la información en un reporte.  
- Se aplicaron buenas prácticas como el uso de métodos auxiliares, validación de entradas y documentación con **JavaDoc**.  

Estas entregas conforman la base del proyecto, que seguirá evolucionando en fases posteriores.  
