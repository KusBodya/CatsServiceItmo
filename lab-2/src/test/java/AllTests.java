import CRUD.Dao;
import models.Animal;
import models.Master;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

class AllTests {
    private static Dao<Master> masterDao;
    private static Dao<Animal> animalDao;
    private static SessionFactory testSessionFactory;

    @BeforeAll
    static void setup() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate-test.cfg.xml");
        testSessionFactory = configuration.buildSessionFactory();

        masterDao = new Dao<>(Master.class);
        animalDao = new Dao<>(Animal.class);
    }

    @AfterAll
    static void tearDown() {
        testSessionFactory.close();
    }


    @BeforeEach
    void clearDatabase() {
        Session session = testSessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createQuery("DELETE FROM Animal").executeUpdate();
            session.createQuery("DELETE FROM Master").executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }



    @Test
    void testSaveAndGetMaster() {
        Master master = new Master("John", "30");
        masterDao.save(master);

        Master found = masterDao.getById(master.getId());
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("John");

    }

    @Test
    void testSaveAndGetAnimal() {
        Master master = new Master("Sarah", "25");
        masterDao.save(master);

        Animal animal = new Animal("Buddy", "3", "Labrador", master);
        animalDao.save(animal);

        Animal found = animalDao.getById(animal.getId());
        assertThat(found.getBreed()).isEqualTo("Labrador");

    }

    @Test
    void testUpdateAnimal() {
        Master master = new Master("Mike", "40");
        masterDao.save(master);

        Animal animal = new Animal("Max", "5", "Poodle", master);
        animalDao.save(animal);

        animal.setName("Charlie");
        animalDao.update(animal);

        Animal updated = animalDao.getById(animal.getId());
        assertThat(updated.getName()).isEqualTo("Charlie");
    }

    @Test
    void testDeleteById() {
        Master master = new Master("Emma", "28");
        masterDao.save(master);

        masterDao.deleteById(master.getId());
        Master deleted = masterDao.getById(master.getId());

        assertThat(deleted).isNull();
    }



}