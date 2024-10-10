import java.sql.*;

public class Main {

    // @TODO Sistituye xxx por los parámetros de tu conexión

    private static final String DB_SERVER ="localhost";

    private static final int DB_PORT = 3306;

    private static final String DB_NAME =  "bd2324";

    private static final String DB_USER = "root";

    private static final String DB_PASS = "";

    private static Connection conn;

    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://" + DB_SERVER + ":" + DB_PORT + "/" + DB_NAME;
        conn = DriverManager.getConnection(url, DB_USER, DB_PASS);

        // @TODO Prueba sus funciones
        // 1. Añadete como autor a la base de datos
        //nuevoAutor("Marquitos");
        // 2. Muestra por pantalla la lista de artículos del autor “Ortega F.” en 2021
        listaArticulosPorAutor("Ortega F.", 2021);
        // 3. Muestra por pantalla una lista de las afiliaciones y el número de autores que
        //    tiene cada una


        // 3. Mostrar por pantalla una lista de las afiliaciones y el número de autores que
        //    tienen cada una
        listaAfiliaciones();
        listaArticulosPorAutor("Ortega F.", 2021);

        conn.close();
    }

    private static void nuevoAutor (String authorName) throws SQLException {
        // @TODO Crea un metodo que añada un nuevo autor a la base de datos con importancia 0.
        // Genera el id de forma aleatoria

        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO author (author_id, author_name, importance) VALUES (?, ?, 0)")) {

            // Generar id de forma aleatoria (puedes ajustarlo según tu lógica)
            int randomId = (int) (Math.random() * 1000);

            preparedStatement.setInt(1, randomId);
            preparedStatement.setString(2, authorName);

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Autor agregado correctamente.");
        }
    }



    private static void listaArticulosPorAutor (String authorName, int year) throws SQLException {
        // @TODO Muestra por pantalla una lista de articulos (título y fecha de publicación) para un
        //  autor y año
        // Muestra por pantalla una lista de artículos (título y fecha de publicación) para un
        // autor y año específicos

        String query = "SELECT DISTINCT ar.title, ar.publication_date " +
                "FROM author a " +
                "JOIN author_article aa ON a.author_id = aa.author_id " +
                "JOIN article ar ON aa.DOI = ar.DOI " +
                "WHERE a.author_name = ? AND YEAR(ar.publication_date) = ? " +
                "ORDER BY ar.publication_date DESC";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, authorName);
            preparedStatement.setInt(2, year);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                System.out.println("Obras de " + authorName + " en " + year + ":");
                while (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String publicationDate = resultSet.getString("publication_date");

                    System.out.println("  - Título: " + title);
                    System.out.println("    Fecha de publicación: " + publicationDate);
                }
            }
        }

    }

    private static void listaAfiliaciones() throws SQLException {
        // @TODO Muestra por pantalla una lista de las instituciones y el numero de autores que
        //  tienen ordenados de más a menos autores

        String query = "SELECT affiliation.affiliation_name, COUNT(DISTINCT author_affiliation.author_id) AS num_autores\n" +
                "FROM affiliation\n" +
                "JOIN author_affiliation ON author_affiliation.affiliation_id = affiliation.affiliation_id\n" +
                "GROUP BY affiliation.affiliation_id\n" +
                "ORDER BY num_autores DESC";

        try(PreparedStatement preparedStatement = conn.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()){
            System.out.println("  - Lista de Afiliaciones y Numero de Autores:");
            while(resultSet.next()){
                String affiliationName = resultSet.getString("affiliation_name");
                int numAutores = resultSet.getInt("num_autores");
                System.out.println("  - Institucion: " + affiliationName + "\t- Numero de autores: " + numAutores);
            }
        }

    }
}


