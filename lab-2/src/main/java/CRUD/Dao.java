package CRUD;

import lombok.Setter;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class Dao<T> {
    private final Class<T> clazz;

    public Dao(Class<T> clazz) {
        this.clazz = clazz;
    }

    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public T save(T entity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.saveOrUpdate(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void deleteById(Integer id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            T entity = session.get(clazz, id);
            if (entity != null) {
                session.delete(entity);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void deleteByEntity(T entity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.delete(entity);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void deleteAll() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.createQuery("DELETE FROM " + clazz.getName()).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public T update(T entity) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.merge(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }


    public T getById(Integer id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(clazz, id);
        } finally {
            session.close();
        }
    }

    public List<T> getAll() {
        Session session = sessionFactory.openSession();
        try {
            return session.createQuery("FROM " + clazz.getName(), clazz).getResultList();
        } finally {
            session.close();
        }
    }
}