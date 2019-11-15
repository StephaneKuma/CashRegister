/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.dao;

import dev.stekos.cashregister.controllers.InvoiceJpaController;
import dev.stekos.cashregister.controllers.exceptions.NonexistentEntityException;
import dev.stekos.cashregister.models.Invoice;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Stekos
 */
public class InvoiceDAO implements DAO<Invoice> {
    private final InvoiceJpaController controller;
    private final EntityManagerFactory emf;
    
    public InvoiceDAO() {
        emf = Persistence.createEntityManagerFactory("CashRegisterPU");
        controller = new InvoiceJpaController(emf);
    }

    @Override
    public void add(Invoice invoice) throws Exception {
        controller.create(invoice);
    }

    @Override
    public void edit(Invoice invoice) throws Exception {
        controller.edit(invoice);
    }

    @Override
    public void remove(int id) throws NonexistentEntityException {
        controller.destroy(id);
    }

    @Override
    public List<Invoice> getAll() {
        return controller.findInvoiceEntities();
    }

    @Override
    public Invoice getById(int id) {
        return controller.findInvoice(id);
    }

    @Override
    public int countEntities() {
        return getAll().size();
    }

    public Invoice getInvoice(String ref) {
        return controller.findInvoice(ref);
    }
}
