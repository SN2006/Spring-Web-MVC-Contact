package com.example.app.controller;

import com.example.app.entity.Contact;
import com.example.app.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contacts")
    public String fetchContacts(Model model) {
        List<Contact> contacts = contactService.fetchAll();
        model.addAttribute("title", "Contacts");
        model.addAttribute("contacts", contacts);
        model.addAttribute("fragmentName", "contact_list");
        return "layout";
    }

    @RequestMapping("/create-contact")
    public String createContact(Model model) {
        model.addAttribute("title", "Add Contact");
        model.addAttribute("fragmentName", "contact_add");
        return "layout";
    }

    @RequestMapping("/update-contact/{id}")
    public String updateContact(@PathVariable("id") Long id, Model model) {
        model.addAttribute("title", "Update Contact");
        Contact contact = contactService.fetchById(id);
        model.addAttribute("contact", contact);
        model.addAttribute("fragmentName", "contact_update");
        return "layout";
    }

    @RequestMapping(value = "/add-contact", method = RequestMethod.POST)
    public RedirectView addContact(@ModelAttribute Contact contact) {
        RedirectView redirectView = new RedirectView("/contacts");
        if (contactService.create(contact)) return redirectView;
        return redirectView;
    }

    @RequestMapping(value = "/change-contact", method = RequestMethod.POST)
    public RedirectView changeContact(@ModelAttribute Contact contact) {
        RedirectView redirectView = new RedirectView("/contacts");
        if (contactService.update(contact.getId(), contact)) return redirectView;
        return redirectView;
    }

    @RequestMapping("/delete-contact/{id}")
    public RedirectView deleteContact(@PathVariable("id") Long id) {
        RedirectView redirectView = new RedirectView("/contacts");
        if (contactService.delete(id)) return redirectView;
        return redirectView;
    }
}
