/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.dao;

import dev.stekos.cashregister.controllers.CategoryJpaController;
import dev.stekos.cashregister.controllers.exceptions.NonexistentEntityException;
import dev.stekos.cashregister.models.Category;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Stekos
 */
public class CategoryDAO implements DAO<Category> {
    private final CategoryJpaController controller;
    private final EntityManagerFactory emf;
    
    public CategoryDAO() {
        emf = Persistence.createEntityManagerFactory("CashRegisterPU");
        controller = new CategoryJpaController(emf);
    }

    @Override
    public void add(Category category) throws Exception {
        controller.create(category);
    }

    @Override
    public void edit(Category category) throws Exception {
        controller.edit(category);
    }

    @Override
    public void remove(int id) throws NonexistentEntityException {
        controller.destroy(id);
    }

    @Override
    public List<Category> getAll() {
        return controller.findCategoryEntities();
    }

    @Override
    public Category getById(int id) {
        return controller.findCategory(id);
    }
    
    public Category getByType(String type) {
        return controller.finCategoryByType(type);
    }

    @Override
    public int countEntities() {
        return getAll().size();
    }
}
