/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.dao;

import dev.stekos.cashregister.controllers.exceptions.NonexistentEntityException;
import java.util.List;

/**
 *
 * @author Stekos
 * @param <Entity>
 */
public interface DAO<Entity> {
    /**
     * Add a new Entity object into the database
     * @param entity
     * @throws Exception 
     */
    public void add(Entity entity) throws Exception;
    
    /**
     * Update an Entity object a merge it into the database
     * @param entity
     * @throws Exception 
     */
    public void edit(Entity entity) throws Exception;
    
    /**
     * Delete an Entity object from the database
     * @param id
     * @throws NonexistentEntityException 
     */
    public void remove(int id) throws NonexistentEntityException;
    
    /**
     * Retrieve all entity object occurences from the database
     * @return 
     */
    public List<Entity> getAll();
    
    /**
     * Retrieve an entity object by it's id from the database
     * @param id
     * @return 
     */
    public Entity getById(int id);
    
    /**
     * Count entity occurences in the database
     * @return 
     */
    public int countEntities();
}
