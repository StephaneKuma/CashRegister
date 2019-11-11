/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.controllers;

import dev.stekos.cashregister.controllers.exceptions.NonexistentEntityException;
import dev.stekos.cashregister.models.SubCategory;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Stekos
 */
public class SubCategoryJpaController implements Serializable {

    public SubCategoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SubCategory subCategory) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(subCategory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SubCategory subCategory) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            subCategory = em.merge(subCategory);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = subCategory.getId();
                if (findSubCategory(id) == null) {
                    throw new NonexistentEntityException("The subCategory with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SubCategory subCategory;
            try {
                subCategory = em.getReference(SubCategory.class, id);
                subCategory.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subCategory with id " + id + " no longer exists.", enfe);
            }
            em.remove(subCategory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SubCategory> findSubCategoryEntities() {
        return findSubCategoryEntities(true, -1, -1);
    }

    public List<SubCategory> findSubCategoryEntities(int maxResults, int firstResult) {
        return findSubCategoryEntities(false, maxResults, firstResult);
    }

    private List<SubCategory> findSubCategoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SubCategory.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public SubCategory findSubCategory(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SubCategory.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubCategoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SubCategory> rt = cq.from(SubCategory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<SubCategory> findSubCategoriesByCategoryId(int categoryId) {
        EntityManager em = getEntityManager();
        List<SubCategory> subCategories = null;
        try {
            String queryString = "SELECT s FROM SubCategory s WHERE s.categoryId = :categoryId";
            Query query = em.createQuery(queryString);
            query.setParameter("categoryId", categoryId);
            subCategories = query.getResultList();
        } finally {
            em.close();
        }
        return subCategories;
    }
    
    public SubCategory findSubCategoryByName(String name) {
        EntityManager em = getEntityManager();
        SubCategory subCategory = null;
        try {
            String queryString = "SELECT s FROM SubCategory s WHERE s.label = :label";
            Query query = em.createQuery(queryString);
            query.setParameter("label", name);
            subCategory =  (SubCategory) query.getSingleResult();
        } finally {
            em.close();
        }
        return subCategory;
    }
}
