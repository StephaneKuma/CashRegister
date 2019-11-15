/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.dao;

import dev.stekos.cashregister.controllers.SaleJpaController;
import dev.stekos.cashregister.controllers.exceptions.NonexistentEntityException;
import dev.stekos.cashregister.models.Sale;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Stekos
 */
public class SaleDAO implements DAO<Sale> {
    private final EntityManagerFactory emf;
    private final SaleJpaController controller;
    
    public SaleDAO() {
        emf = Persistence.createEntityManagerFactory("CashRegisterPU");
        controller = new SaleJpaController(emf);
    }

    @Override
    public void add(Sale entity) throws Exception {
        controller.create(entity);
    }

    @Override
    public void edit(Sale entity) throws Exception {
        controller.edit(entity);
    }

    @Override
    public void remove(int id) throws NonexistentEntityException {
        controller.destroy(id);
    }

    @Override
    public List<Sale> getAll() {
        return controller.findSaleEntities();
    }

    @Override
    public Sale getById(int id) {
        return controller.findSale(id);
    }

    @Override
    public int countEntities() {
        return controller.getSaleCount();
    }
    
}
