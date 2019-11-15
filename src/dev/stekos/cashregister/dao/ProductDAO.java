/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.dao;

import dev.stekos.cashregister.controllers.ProductJpaController;
import dev.stekos.cashregister.controllers.exceptions.NonexistentEntityException;
import dev.stekos.cashregister.models.Product;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Stekos
 */
public class ProductDAO implements DAO<Product> {
    private final ProductJpaController controller;
    private final EntityManagerFactory emf;
    
    public ProductDAO() {
        emf = Persistence.createEntityManagerFactory("CashRegisterPU");
        controller = new ProductJpaController(emf);
    }

    @Override
    public void add(Product product) throws Exception {
        controller.create(product);
    }

    @Override
    public void edit(Product product) throws Exception {
        controller.edit(product);
    }

    @Override
    public void remove(int id) throws NonexistentEntityException {
        controller.destroy(id);
    }

    @Override
    public List<Product> getAll() {
        return controller.findProductEntities();
    }

    @Override
    public Product getById(int id) {
        return controller.findProduct(id);
    }
    
    @Override
    public int countEntities() {
        return getAll().size();
    }
    
    /**
     * 
     * @param id
     * @return 
     */
    public List<Product> getBySubCategoryId(int id) {
        return controller.findProductBySubCategoryId(id);
    }
    
    public Product getByName(String name) {
        return controller.findProuctByName(name);
    }
}
