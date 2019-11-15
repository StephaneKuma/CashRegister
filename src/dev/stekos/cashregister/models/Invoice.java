/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.stekos.cashregister.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Stekos
 */
@Entity
@Table(name = "invoices")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
    @NamedQuery(name = "Invoice.findById", query = "SELECT i FROM Invoice i WHERE i.id = :id"),
    @NamedQuery(name = "Invoice.findByUserId", query = "SELECT i FROM Invoice i WHERE i.userId = :userId"),
    @NamedQuery(name = "Invoice.findByDiscount", query = "SELECT i FROM Invoice i WHERE i.discount = :discount"),
    @NamedQuery(name = "Invoice.findByPayable", query = "SELECT i FROM Invoice i WHERE i.payable = :payable"),
    @NamedQuery(name = "Invoice.findByPaid", query = "SELECT i FROM Invoice i WHERE i.paid = :paid"),
    @NamedQuery(name = "Invoice.findByReturned", query = "SELECT i FROM Invoice i WHERE i.returned = :returned"),
    @NamedQuery(name = "Invoice.findByRef", query = "SELECT i FROM Invoice i WHERE i.ref = :ref"),
    @NamedQuery(name = "Invoice.findByCreatedAt", query = "SELECT i FROM Invoice i WHERE i.createdAt = :createdAt"),
    @NamedQuery(name = "Invoice.findByUpdatedAt", query = "SELECT i FROM Invoice i WHERE i.updatedAt = :updatedAt")})
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @Column(name = "discount")
    private double discount;
    @Basic(optional = false)
    @Column(name = "payable")
    private double payable;
    @Basic(optional = false)
    @Column(name = "paid")
    private double paid;
    @Basic(optional = false)
    @Column(name = "returned")
    private double returned;
    @Column(name = "ref")
    private String ref;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public Invoice() {
    }

    public Invoice(Integer id) {
        this.id = id;
    }

    public Invoice(Integer id, int userId, double discount, double payable, double paid, double returned, Date createdAt, Date updatedAt) {
        this.id = id;
        this.userId = userId;
        this.discount = discount;
        this.payable = payable;
        this.paid = paid;
        this.returned = returned;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPayable() {
        return payable;
    }

    public void setPayable(double payable) {
        this.payable = payable;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getReturned() {
        return returned;
    }

    public void setReturned(double returned) {
        this.returned = returned;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dev.stekos.cashregister.models.Invoice[ id=" + id + " ]";
    }
    
}
