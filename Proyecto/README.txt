Práctica 06 - Hilos en Java
Nombre: Eduardo Alejandro Sánchez Moya

1. Tabla Comparativa de Tiempos
(Los tiempos están en nanosegundos. Ejecuta el programa para llenar tus valores reales)

| Configuración | Tiempo (ns)       | Aceleración (Speedup) |
|---------------|-------------------|-----------------------|
| 1 Hilo        |     4755900       | 1.0x (Base)           |
| 2 Hilos       |     4157000       | 1.14x                 |
| 4 Hilos       |     4946800       | 1.0x                  |
| 8 Hilos       |     5091500       | No hubo aceleración   |

(Si N es muy grande para una tarea pequeña o por el overhead, no hay valor de aceleración)    
             
2. Reflexión [cite: 70]

¿Se volvió más rápido el programa?
Sí, generalmente al aumentar los hilos se observa una mejora en el tiempo de ejecución, ya que el trabajo de calcular la sumatoria se distribuye entre varios núcleos del procesador. Sin embargo, si usamos demasiados hilos (más de los que tiene la CPU física), la mejora disminuye debido al costo de gestión de los hilos.

¿Qué problemas encontraste al manejar datos compartidos?
El principal problema es la "Condición de Carrera" (Race Condition). Si varios hilos intentan escribir en la variable "total" al mismo tiempo sin control, el resultado final es incorrecto. Para solucionar esto, utilizamos la palabra clave "synchronized" en el método que acumula los resultados parciales, asegurando que solo un hilo pueda escribir a la vez.

¿Qué aprendiste sobre los programas que usan más de un hilo?
Aprendí que la programación paralela requiere planificar muy bien cómo se dividen los datos (para que no falten ni sobren números en los rangos) y cómo se unen los resultados. También aprendí que crear hilos tiene un costo ("overhead"); por eso, para tareas muy pequeñas, a veces es más rápido usar un solo hilo que intentar paralelizarlo.