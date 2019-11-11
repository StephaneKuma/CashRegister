/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.dao;

import dev.stekos.cashregister.controllers.SupplierJpaController;
import dev.stekos.cashregister.controllers.exceptions.NonexistentEntityException;
import dev.stekos.cashregister.models.Supplier;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Stekos
 */
public class SupplierDAO implements DAO<Supplier> {
    private final EntityManagerFactory emf;
    private final SupplierJpaController controller;
    
    public SupplierDAO() {
        emf = Persistence.createEntityManagerFactory("CashRegisterPU");
        controller = new SupplierJpaController(emf);
    }

    @Override
    public void add(Supplier entity) throws Exception {
        controller.create(entity);
    }

    @Override
    public void edit(Supplier entity) throws Exception {
        controller.edit(entity);
    }

    @Override
    public void remove(int id) throws NonexistentEntityException {
        controller.destroy(id);
    }

    @Override
    public List<Supplier> getAll() {
        return controller.findSupplierEntities();
    }

    @Override
    public Supplier getById(int id) {
        return controller.findSupplier(id);
    }

    @Override
    public int countEntities() {
        return controller.getSupplierCount();
    }
    
    public Supplier getByName(String name) {
        return controller.findSupplierByName(name);
    }
}
