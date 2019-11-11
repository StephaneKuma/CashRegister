/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.dao;

import dev.stekos.cashregister.controllers.UserJpaController;
import dev.stekos.cashregister.controllers.exceptions.NonexistentEntityException;
import dev.stekos.cashregister.models.User;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Stekos
 */
public class UserDAO implements DAO<User> {
    private final UserJpaController controller;
    private final EntityManagerFactory emf;
    
    public UserDAO() {
        emf = Persistence.createEntityManagerFactory("CashRegisterPU");
        controller = new UserJpaController(emf);
    }

    @Override
    public void add(User entity) throws Exception {
        controller.create(entity);
        
    }

    @Override
    public void edit(User entity) throws Exception {
        controller.edit(entity);
    }

    @Override
    public void remove(int id) throws NonexistentEntityException {
        controller.destroy(id);
    }

    @Override
    public List<User> getAll() {
        return controller.findUserEntities();
    }

    @Override
    public User getById(int id) {
        return controller.findUser(id);
    }

    @Override
    public int countEntities() {
        return getAll().size();
    }
}
