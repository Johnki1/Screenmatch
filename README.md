## ScreenMatch: Consulta de Series y Episodios

¡Bienvenido a ScreenMatch!

ScreenMatch es una aplicación backend desarrollada en Java utilizando Spring Boot que te permite consultar y explorar una amplia base de datos de series de televisión y sus emocionantes episodios. Conectándose a la API OMDB, ScreenMatch te brinda acceso instantáneo a una gran cantidad de información detallada sobre tus series favoritas.

## Características Principales

- **Consulta de Series**: Descubre nuevas series buscando por su nombre y obtén información detallada sobre el elenco, la trama y mucho más.
- **Exploración de Episodios**: Explora cada episodio de tus series favoritas, con detalles como título, temporada y calificación.
- **Base de Datos en PostgreSQL**: Almacena los datos de las series y episodios en una base de datos PostgreSQL para un acceso rápido y eficiente.
- **Lenguaje JPQL para Consultas**: Utiliza lenguaje JPQL (Java Persistence Query Language) para realizar consultas avanzadas a la base de datos y obtener resultados precisos.
- **Funcionalidades de Búsqueda Avanzada**: Busca series por título, género, año de estreno y más, permitiéndote descubrir nuevas series según tus preferencias.

## Cómo Empezar

1. **Instalación**: Descarga el código fuente de ScreenMatch desde nuestro repositorio en GitHub.
2. **Configuración de la API Key**: Asegúrate de tener una API Key válida de OMDB y configúrala en tu entorno como `APIOMDB_KEY`.
3. **Configuración de la Base de Datos**: Configura una base de datos PostgreSQL y ajusta la configuración de la aplicación para que se conecte correctamente a la base de datos.
4. **Compilación y Ejecución**: Compila y ejecuta la aplicación utilizando tu IDE favorito o mediante la línea de comandos.
5. **Consulta de Datos**: Una vez iniciada la aplicación, utiliza las diversas funcionalidades de consulta para explorar la base de datos de series y episodios.

## Consultas Disponibles

1. **Buscar Serie por Título**: Utiliza el método `buscarSeriePorTitulo()` para buscar una serie específica por su título.
2. **Explorar Episodios por Serie**: Utiliza el método `buscarEpisodioPorSerie()` para explorar los episodios de una serie seleccionada.
3. **Mostrar Top 5 de Mejores Series**: Utiliza el método `buscarTop5Series()` para encontrar las mejores series según su calificación.
4. **Buscar Series por Categoría**: Utiliza el método `buscarSeriePorCategoria()` para buscar series por género o categoría.
5. **Buscar Series por Temporadas y Evaluación**: Utiliza el método `buscarSeriePorTemporadasYEvaluacion()` para encontrar series que coincidan con cierto número de temporadas y una evaluación específica.
6. **Buscar Episodios por Nombre**: Utiliza el método `buscarEpisodiosPorTitulo()` para buscar episodios por su nombre.
7. **Top 5 Episodios Por Serie**: Utiliza el método `top5Episodios()` para encontrar los mejores episodios de una serie seleccionada.

## Contribución

¡ScreenMatch es un proyecto de código abierto y estamos encantados de recibir contribuciones de la comunidad! Si deseas contribuir al desarrollo de la aplicación, no dudes en enviar pull requests o informar sobre problemas en nuestro repositorio en GitHub.

## ¡Únete a la Aventura!

¡Descarga ScreenMatch ahora y sumérgete en un emocionante viaje a través del mundo de las series de televisión! ¡Con una amplia gama de funcionalidades y una base de datos detallada, ScreenMatch está aquí para ayudarte a descubrir nuevas series y episodios emocionantes!

---

¡Explora nuevas series, sumérgete en emocionantes episodios y descubre el fascinante mundo de la televisión con ScreenMatch!
