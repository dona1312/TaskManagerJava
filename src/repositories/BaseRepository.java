package repositories;

import entity.BaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.*;

public class BaseRepository<T extends BaseEntity> {

    protected Configuration configuration = null;
    protected ServiceRegistryBuilder registry = null;
    protected ServiceRegistry serviceRegistry = null;
    protected Class<T> tClass;

    public BaseRepository() {
        try {
            configuration = new Configuration().configure("/mappings/hibernate.cfg.xml");
            registry = new ServiceRegistryBuilder();
            registry.applySettings(configuration.getProperties());
            serviceRegistry = registry.buildServiceRegistry();

        } catch (Exception ex) {

        }
    }

    public List<T> getAll() {
        List<T> items = new ArrayList<T>();
        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();
        Transaction tran = null;

        try {
            tran = session.beginTransaction();

          items = session.createQuery("from " + this.tClass.getName()).list();

            tran.commit();
        } catch (Exception ex) {
            tran.rollback();
        } finally {
            session.close();
        }
        return items;
    }

    public void save(T item) {
        if (item.getID() != 0) {
            update(item);
        } else {
            add(item);
        }
    }

    protected void add(T item) {
        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();
        Transaction tran = null;
        try {
            tran = session.beginTransaction();
            session.save(item);
            tran.commit();
        } catch (Exception ex) {
            tran.rollback();
        } finally {
            session.close();
        }
    }

    protected void update(T item) {
        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();
        Transaction tran = null;
        try {
            tran = session.beginTransaction();
            session.update(item);
            tran.commit();
        } catch (Exception ex) {
            tran.rollback();
        } finally {
            session.close();
        }
    }

    public void delete(T item) {
        SessionFactory factory = configuration.buildSessionFactory(serviceRegistry);
        Session session = factory.openSession();
        Transaction tran = null;
        try {
            tran = session.beginTransaction();
            session.delete(item);
            tran.commit();
        } catch (Exception ex) {
            tran.rollback();
        } finally {
            session.close();
        }
    }

    public T getByID(int id) {
        return this.getAll().stream().filter(i -> i.getID() == id).findFirst().orElse(null);
    }

}
