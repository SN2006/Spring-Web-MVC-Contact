package com.example.app.service;

import com.example.app.entity.Contact;
import com.example.app.repository.impl.ContactRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService{

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public boolean create(Contact contact) {
        return contactRepository.create(contact);
    }

    @Transactional
    public List<Contact> fetchAll() {
        return contactRepository.fetchAll().orElse(Collections.emptyList());
    }

    @Transactional
    public Contact fetchById(Long id) {
        return contactRepository.fetchById(id).orElse(null);
    }

    @Transactional
    public boolean update(Long id, Contact contact) {
        return contactRepository.update(id, contact);
    }

    @Transactional
    public boolean delete(Long id) {
        Optional<Contact> optionalContact = contactRepository.fetchById(id);
        if (optionalContact.isPresent()) {
            return contactRepository.delete(id);
        }
        return false;
    }
}
