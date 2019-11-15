package dev.stekos.cashregister.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-15T22:06:46")
@StaticMetamodel(Sale.class)
public class Sale_ { 

    public static volatile SingularAttribute<Sale, Date> createdAt;
    public static volatile SingularAttribute<Sale, Double> total;
    public static volatile SingularAttribute<Sale, Integer> quantity;
    public static volatile SingularAttribute<Sale, Integer> productId;
    public static volatile SingularAttribute<Sale, Integer> invoiceId;
    public static volatile SingularAttribute<Sale, Integer> id;
    public static volatile SingularAttribute<Sale, Date> updatedAt;

}