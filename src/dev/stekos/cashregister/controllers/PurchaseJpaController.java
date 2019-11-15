/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.controllers;

import dev.stekos.cashregister.controllers.exceptions.NonexistentEntityException;
import dev.stekos.cashregister.models.Purchase;
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
public class PurchaseJpaController implements Serializable {

    public PurchaseJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Purchase purchase) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(purchase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Purchase purchase) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            purchase = em.merge(purchase);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = purchase.getId();
                if (findPurchase(id) == null) {
                    throw new NonexistentEntityException("The purchase with id " + id + " no longer exists.");
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
            Purchase purchase;
            try {
                purchase = em.getReference(Purchase.class, id);
                purchase.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The purchase with id " + id + " no longer exists.", enfe);
            }
            em.remove(purchase);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Purchase> findPurchaseEntities() {
        return findPurchaseEntities(true, -1, -1);
    }

    public List<Purchase> findPurchaseEntities(int maxResults, int firstResult) {
        return findPurchaseEntities(false, maxResults, firstResult);
    }

    private List<Purchase> findPurchaseEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Purchase.class));
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

    public Purchase findPurchase(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Purchase.class, id);
        } finally {
            em.close();
        }
    }

    public int getPurchaseCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Purchase> rt = cq.from(Purchase.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
