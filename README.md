Práctica 6: Programación Concurrente (Hilos en Java)
Tareas (Laboratorio 6) | Autor: Eduardo Alejandro Sánchez Moya

Este proyecto implementa una aplicación en Java para calcular una sumatoria matemática compleja distribuyendo la carga de trabajo entre múltiples hilos de ejecución (Threads). El objetivo es demostrar la mejora de rendimiento (aceleración) mediante el paralelismo y solucionar problemas de concurrencia como las condiciones de carrera.

Objetivo
Comparar el tiempo de ejecución entre un proceso serial (1 hilo) y un proceso paralelo (N hilos) al realizar una tarea intensiva de CPU, gestionando correctamente el acceso a recursos compartidos.

Descripción Técnica
El programa calcula la sumatoria de la función $f(i) = i^2 + 3i + 1$ para $i$ desde 1 hasta 1,000,000.

Características principales:
- División de Trabajo: El rango total se divide dinámicamente entre el número de hilos ingresado por el usuario.
- Sincronización: Se utiliza la palabra clave `synchronized` en la clase `Acumulador` para evitar Condiciones de Carrera (Race Conditions) cuando múltiples hilos intentan escribir en la variable `total` simultáneamente.
- Medición de Tiempo: Se utiliza `System.nanoTime()` para medir con precisión la duración de la ejecución y calcular el Speedup (aceleración).

Tecnologías utilizadas
- Lenguaje: Java (Core)
- Concurrencia: Clases `Thread`, `Runnable`.
- Sincronización: Bloques `synchronized`, métodos `wait/join`.

Resultados Obtenidos
A continuación se muestran los tiempos de ejecución obtenidos en las pruebas (en nanosegundos):

| Configuración | Tiempo (ns) | Aceleración (Speedup) |
|---------------|-------------|-----------------------|
| 1 Hilo        | 4,755,900   | 1.0x (Base)           |
| 2 Hilos       | 4,157,000   | 1.14x                 |
| 4 Hilos       | 4,946,800   | 0.96x (Aprox 1.0x)    |
| 8 Hilos       | 5,091,500   | Sin aceleración       |

Análisis de Resultados
1. ¿Se volvió más rápido el programa?
   Sí, al usar 2 hilos se observó una mejora (1.14x) ya que el trabajo se distribuyó. Sin embargo, al aumentar a 4 y 8 hilos, el tiempo empeoró. Esto demuestra que crear hilos tiene un costo (overhead); si la tarea es demasiado pequeña (solo 1 millón de iteraciones), el tiempo que tarda el sistema en gestionar los hilos supera el tiempo que se ahorra paralelizando el cálculo.

2. Manejo de Datos Compartidos
   El principal desafío fue la Condición de Carrera. Si varios hilos escriben en la variable `total` sin control, el resultado es erróneo. Se solucionó encapsulando la suma en un método `synchronized` dentro de la clase `Acumulador`.

Instrucciones de ejecución
Descargar las carpetas
Compilar el código:
Navega a la carpeta src y ejecuta:
`javac SumaParalela.java`
Interacción: El programa te pedirá ingresar el número de hilos (N). Prueba con 1, 2, 4, etc., para ver la diferencia en tiempos.
