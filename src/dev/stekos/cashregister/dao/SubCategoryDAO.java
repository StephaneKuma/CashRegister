/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.dao;

import dev.stekos.cashregister.controllers.SubCategoryJpaController;
import dev.stekos.cashregister.controllers.exceptions.NonexistentEntityException;
import dev.stekos.cashregister.models.SubCategory;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Stekos
 */
public class SubCategoryDAO implements DAO<SubCategory> {
    private final EntityManagerFactory emf;
    private final SubCategoryJpaController controller;
    
    public SubCategoryDAO() {
        emf = Persistence.createEntityManagerFactory("CashRegisterPU");
        controller = new SubCategoryJpaController(emf);
    }

    @Override
    public void add(SubCategory entity) throws Exception {
        controller.create(entity);
    }

    @Override
    public void edit(SubCategory entity) throws Exception {
        controller.edit(entity);
    }

    @Override
    public void remove(int id) throws NonexistentEntityException {
        controller.destroy(id);
    }

    @Override
    public List<SubCategory> getAll() {
        return controller.findSubCategoryEntities();
    }

    @Override
    public SubCategory getById(int id) {
        return controller.findSubCategory(id);
    }

    @Override
    public int countEntities() {
        return controller.getSubCategoryCount();
    }
    
    public SubCategory getByName(String name) {
        return controller.findSubCategoryByName(name);
    }
    
    public List<SubCategory> getByCategoryId(int categoryId) {
        return controller.findSubCategoriesByCategoryId(categoryId);
    }
}
