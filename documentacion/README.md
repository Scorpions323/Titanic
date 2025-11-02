# Simulador del Titanic (Gestión de Botes de Emergencia)

**Autor:** Aaron Gómez Ventero  

---

## Índice

- [Simulador del Titanic (Gestión de Botes de Emergencia)](#simulador-del-titanic-gestión-de-botes-de-emergencia)
  - [Índice](#índice)
  - [Análisis del Problema](#análisis-del-problema)
  - [Diseño de la Solución](#diseño-de-la-solución)
    - [Flujo principal del sistema:](#flujo-principal-del-sistema)
  - [Arquitectura (Visión General)](#arquitectura-visión-general)
  - [Componentes del Sistema](#componentes-del-sistema)
  - [Protocolo de Comunicación](#protocolo-de-comunicación)
  - [Elementos Destacables del Desarrollo](#elementos-destacables-del-desarrollo)
  - [Problemas Encontrados](#problemas-encontrados)
  - [Cómo Ejecutar el Programa](#cómo-ejecutar-el-programa)
  - [Repositorio](#repositorio)

---

<a id="analisis-del-problema"></a>

## Análisis del Problema

El hundimiento del Titanic supuso un gran desafío logístico para la evacuación de los pasajeros.  
Este proyecto busca **simular la gestión de los botes salvavidas** desde su lanzamiento hasta la recopilación de datos sobre los supervivientes.

Cada bote funciona de forma **autónoma**, con un número aleatorio de pasajeros (entre 1 y 100) y un retardo en el recuento (entre 2 y 6 segundos).  

Una vez se conoce la información, se comunica al **Servicio de Emergencias**, que genera un informe final en formato Markdown con los datos consolidados de los 20 botes.

El objetivo principal es **gestionar procesos concurrentes**, coordinar su ejecución y generar un informe final que resuma la situación.

---

<a id="diseno-de-la-solucion"></a>

## Diseño de la Solución

La solución se basa en la creación de **procesos independientes** que simulan cada bote, y un proceso principal (`ServicioEmergencias`) que actúa como coordinador general.

### Flujo principal del sistema:

1. **ServicioEmergencias** lanza 20 procesos `Bote` (de `B00` a `B19`).  
2. Cada **Bote** genera un número aleatorio de pasajeros y los clasifica (mujeres, hombres y niños).  
3. Tras un retardo aleatorio (2–6 segundos), el bote devuelve su resultado.  
4. **ServicioEmergencias** recoge los datos de todos los botes y los guarda en memoria.  
5. Una vez finalizado el proceso, se llama a **GeneradorInforme**, que crea el archivo `Informe.md`.  
6. En futuras versiones, se podrá extender la generación del informe a otros formatos mediante `ExportadorInformes`.

---

<a id="arquitectura-vision-general"></a>

## Arquitectura (Visión General)

```text
                    ┌────────────────────────┐
                    │     Usuario / Main     │
                    │ (Inicia la simulación) │
                    └────────────┬───────────┘
                                 │
                                 ▼
               ┌───────────────────────────────────┐
               │        ServicioEmergencias        │
               │───────────────────────────────────│
               │ - Lanza los 20 procesos Bote      │
               │ - Recoge resultados               │
               │ - Gestiona el tiempo de espera    │
               │ - Envía datos al GeneradorInforme │
               └───────────────┬───────────────────┘
                               │
           ┌───────────────────┴───────────────────┐
           ▼                                       ▼
┌───────────────────────────┐         ┌────────────────────────┐
│           Bote            │         │    GeneradorInforme    │
│───────────────────────────│         │────────────────────────│
│ - Proceso independiente   │         │ - Recibe los registros │
│ - Genera nº aleatorio de  │         │ - Calcula totales      │
│   pasajeros (1–100)       │         │ - Crea "Informe.md"    │
│ - Clasifica: mujeres,     │         │ - Imprime mensaje éxito│
│   hombres, niños          │         └─────────────┬──────────┘
└──────────┬────────────────┘                       │
           │                                        ▼
           ▼                          ┌────────────────────────────┐
┌──────────────────────┐              │     ExportadorInformes     │
│      Pasajeros       │              │────────────────────────────│
│──────────────────────│              │ (Futuros formatos PDF, etc)│
│ - Clase simple       │              │ - Métodos no implementados │
│   con tipo aleatorio │              │ - Lanza UnsupportedOpExc   │
└──────────────────────┘              └────────────────────────────┘
```

---

<a id="componentes-del-sistema"></a>

## Componentes del Sistema

| Clase / Componente      | Descripción |
|-------------------------|-------------|
| **Pasajeros**           | Representa un pasajero con un tipo aleatorio: Mujer, Hombre o Niño. |
| **Bote**                | Simula un bote de salvamento. Genera pasajeros, cuenta por tipo y devuelve la información al proceso principal. |
| **ServicioEmergencias** | Controla la ejecución completa: lanza los procesos Bote, recopila resultados y coordina la creación del informe. |
| **GeneradorInforme**    | Genera el archivo `Informe.md` con los resultados de todos los botes y el total general. |
| **ExportadorInformes**  | Clase preparada para exportar informes en futuros formatos (PDF, TXT, DOCX, CSV...). Actualmente lanza una excepción “no implementado”. |
| **Constantes**          | Contiene todos los textos fijos, formatos y mensajes usados en las clases para mejorar la mantenibilidad. |

---

<a id="protocolo-de-comunicacion"></a>

## Protocolo de Comunicación

Cada proceso **Bote** comunica su resultado al **Servicio de Emergencias** mediante su **salida estándar (stdout)**.

El formato de la información transmitida es:

```text
BXX;total;mujeres;hombres;niños
```

Por ejemplo:

```text
B05;78;30;40;8
```

El `ServicioEmergencias` interpreta esta línea, separa los valores y los almacena en un mapa:

```java
Map<String, int[]> registros;
```

---

<a id="elementos-destacables-del-desarrollo"></a>

## Elementos Destacables del Desarrollo

- **Implementación modular** con una clara separación de responsabilidades entre las clases principales (`Bote`, `ServicioEmergencias`, `GeneradorInforme`, `ExportadorInformes`, etc.).  
- **Generación automatizada del informe en formato Markdown**, legible, estructurado y preparado para futuras ampliaciones.  
- **Arquitectura preparada para la extensión de funcionalidades**, especialmente en la exportación de informes a nuevos formatos mediante la clase `ExportadorInformes`.  
- **Centralización de constantes** en una única clase (`Constantes`), lo que mejora la mantenibilidad, legibilidad y facilita la modificación de mensajes o configuraciones globales.  

---

<a id="problemas-encontrados"></a>

## Problemas Encontrados
  
- **Comunicación por consola:** los procesos hijos (`Bote`) devuelven los datos por la salida estándar, lo que requirió parsear el texto con cuidado.  
- **Formato Markdown:** asegurar que la indentación y los saltos de línea sean correctos en el archivo final.  
- **Gestión del classpath en Linux:** fue necesario incluir `System.getProperty("java.class.path")` en el `ProcessBuilder` para que los procesos reconocieran correctamente las clases compiladas.  

---

<a id="como-ejecutar-el-programa"></a>

## Cómo Ejecutar el Programa

Para iniciar la simulación del Titanic, se debe ejecutar la clase `Main.java`.  

Si se ejecuta desde terminal:

```bash
javac es/etg/dam/Main.java
java es.etg.dam.Main
```

---

<a id="repositorio"></a>

## Repositorio

Puedes clonar el repositorio oficial del proyecto con el siguiente comando:

```bash
git clone https://github.com/Scorpions323/Titanic
```
