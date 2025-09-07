# Proyecto CFP – Entrega 1  
**Materia:** Conceptos Fundamentales de Programación - B01 (2025)  
**Institución:** Politécnico Grancolombiano  
**Grupo:** *CFP-G3*  

---

## 📌 Descripción  
Esta es la **primera entrega del proyecto**.  
El objetivo fue crear un programa en **Java** que genere archivos planos de prueba, los cuales se usarán como entrada en las siguientes etapas del proyecto.  

El programa se implementó en **Eclipse**, en una clase llamada `GenerateInfoFiles`, que al ejecutarse genera automáticamente varios archivos dentro de una carpeta llamada `input/`.  

---

## 📂 Archivos generados  

1. **`products.txt`**  
   - Contiene la lista de productos de la cafetería Tostao.  
   - Cada línea tiene el formato:  
     ```
     IDProducto;NombreProducto;Precio
     ```
   - Ejemplo:  
     ```
     P1;Café Americano;3500
     ```

2. **`salesmen_info.txt`**  
   - Contiene la lista de vendedores.  
   - Cada línea tiene el formato:  
     ```
     TipoDocumento;NúmeroDocumento;Nombre;Apellido
     ```
   - Ejemplo:  
     ```
     CC;45283910;Rachel;Green
     ```

3. **Archivos de ventas por vendedor**  
   - Un archivo por cada vendedor, nombrado así:  
     ```
     sales_{Nombre}_{Documento}.txt
     ```
   - La primera línea identifica al vendedor.  
   - Las siguientes líneas corresponden a productos vendidos:  
     ```
     IDProducto;Cantidad;
     ```
   - Ejemplo:  
     ```
     CC;45283910
     P3;2;
     P7;5;
     ```

---

## ⚙️ Funcionamiento  
- El programa no pide datos al usuario.  
- Todo se genera automáticamente de forma pseudoaleatoria.  
- Al finalizar, muestra en consola:  
  ```
  Generación de archivos completada con éxito.
  ```

---

## 📑 Estructura del proyecto  

```
CFP_Proyecto/
 ├─ src/
 │   └─ generator/
 │       └─ GenerateInfoFiles.java
 ├─ input/
 │   ├─ products.txt
 │   ├─ salesmen_info.txt
 │   ├─ sales_Rachel_45283910.txt
 │   ├─ sales_Ross_23840192.txt
 │   └─ ...
 └─ README.md
```
- Trabajar con arreglos y valores aleatorios para simular datos.  
- Documentar el código usando **JavaDoc**.  

Estos archivos son la base para las siguientes entregas, donde se deberán procesar los datos y generar reportes más avanzados.  
