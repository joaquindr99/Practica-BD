import model.Affiliation;
import model.Author;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Exception {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();


        // Crea una afiliación
        Affiliation affiliation = new Affiliation("Universidad Politecnica de Madrid", "Madrid", "Spain");
        session.saveOrUpdate(affiliation);

        // Lee el fichero CSV y crea autores
        try (Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/authors.csv"));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                String authorName = csvRecord.get("author_name");
                double importance = Double.parseDouble(csvRecord.get("importance"));
                Author author = new Author(authorName, importance);
                author.getAffiliations().add(affiliation);
                session.save(author);
            }
        }
        tx.commit();
        tx = session.beginTransaction();
        session.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
        tx.commit();
        session.close();
        sessionFactory.close();
    }
}
