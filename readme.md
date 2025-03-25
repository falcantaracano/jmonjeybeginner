# Notas 
En este documento se iran recogiendo las notas que se consideren de interes según se vaya avanzando en el aprendizaje del 
framework jmonkeyengine. Este aprendizaje se basa principalmente en el libro **JMonkey Engine Game Development: Beginner Guide** y el tutorial básico que hay en la wiki del proyecto <https://wiki.jmonkeyengine.org/docs/3.4/documentation.html>

Se está utilizando el siguiente entorno de desarrollo
- IDE: Visual Studio Code
- JDK: version 21
```
openjdk version "21.0.6" 2025-01-21 LTS
OpenJDK Runtime Environment Temurin-21.0.6+7 (build 21.0.6+7-LTS)
OpenJDK 64-Bit Server VM Temurin-21.0.6+7 (build 21.0.6+7-LTS, mixed mode, sharing)
```
- Tipo de proyecto y estructura de proyecto maven
- Backend de logging Log4j2
- Version del motor de juegos: jmonkey engine v3.7.0-STABLE
- Version 3 de la librería de lwjgl (Lightweight java game library).

## Notas de los capítulos del libro

### Capítulo 2
Se ha añadido un programa **AssetDemo** para ver como se tenía que estructuras los assets del proyecto dentro del directorio resources del proyecto maven. Además de como luego había que referenciarlos en el código fuente.
### Capítulo 2
En los programas **TargetPickCursor** y **TargetPickCenter** se ha modificado modificado la propuesta del libro de utilizar el parametro *intensity* del trigger para girar en el eje de las Y los cubos por una *constante de un angulo de un grado en radianes*. Este cambio se debe a que el comportamiento en los giros de los dos cubos era completamente aleatorio

## Notas sobre el juego propuesto en el libro

Habrá un módulo de maven por cada uno de los capítulos donde se van proponiendo actividades a realizar en el juego. 
Puede que en alguna ocasión, el código se unifique en un sólo submodulo para las propuestas de varios capítulos.

Inicialmente, el código java los voy a organizar en paquetes basado en los componente/personajes del juego; aunque, posiblemente,
según vaya adquiriendo experiencia con el framework de jmonkey pueda variar esta division inicial de paquetes. 

Mi idea original erá crear paquetes orientados a organizar el código por estados, controles, etc. Al final, he pensado que sería mejor organizar el código fuende del juego en paquetes más relacionados con los personajes/componentes del propio juego. Es cierto que esto crea cruce entre clases de los paquetes, sobre todo porque los estados de un personaje o de la plataforma de juegos pueden invocar directamente a los controles de los personajes. 
