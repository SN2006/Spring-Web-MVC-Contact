package com.example.app.repository.impl;

import com.example.app.entity.Contact;
import com.example.app.repository.AppRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ContactRepository implements AppRepository<Contact> {

    private final SessionFactory sessionFactory;

    @Autowired
    public ContactRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean create(Contact contact) {
        Session session = sessionFactory.getCurrentSession();
        String hql =
                "INSERT INTO Contact (name, surname, phone) VALUES (:name, :surname, :phone)";
        MutationQuery query = session.createMutationQuery(hql);
        query.setParameter("name", contact.getName());
        query.setParameter("surname", contact.getSurname());
        query.setParameter("phone", contact.getPhone());
        return query.executeUpdate() > 0;
    }

    @Override
    public Optional<List<Contact>> fetchAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<Contact> contacts = session
                    .createQuery("from Contact", Contact.class).list();
            return Optional.of(contacts);
        }catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Contact> fetchById(Long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "FROM Contact WHERE id = :id";
            Query<Contact> query = session.createQuery(hql, Contact.class);
            query.setParameter("id", id);
            return query.uniqueResultOptional();
        }catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean update(Long id, Contact contact) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "UPDATE Contact SET name=:name," +
                    " surname=:surname," +
                    " phone=:phone WHERE id=:id";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("name", contact.getName());
            query.setParameter("surname", contact.getSurname());
            query.setParameter("phone", contact.getPhone());
            query.setParameter("id", id);
            query.executeUpdate();
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "DELETE FROM Contact WHERE id = :id";
            MutationQuery query = session.createMutationQuery(hql);
            query.setParameter("id", id);
            query.executeUpdate();
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
