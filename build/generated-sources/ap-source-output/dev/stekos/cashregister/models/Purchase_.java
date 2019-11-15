package dev.stekos.cashregister.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-15T22:06:46")
@StaticMetamodel(Purchase.class)
public class Purchase_ { 

    public static volatile SingularAttribute<Purchase, Date> createdAt;
    public static volatile SingularAttribute<Purchase, Integer> supplierId;
    public static volatile SingularAttribute<Purchase, Integer> quantity;
    public static volatile SingularAttribute<Purchase, Double> price;
    public static volatile SingularAttribute<Purchase, Integer> id;
    public static volatile SingularAttribute<Purchase, Integer> userId;
    public static volatile SingularAttribute<Purchase, Date> updatedAt;

}