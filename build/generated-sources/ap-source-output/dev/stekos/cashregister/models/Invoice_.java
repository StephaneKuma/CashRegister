package dev.stekos.cashregister.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-15T22:06:46")
@StaticMetamodel(Invoice.class)
public class Invoice_ { 

    public static volatile SingularAttribute<Invoice, Date> createdAt;
    public static volatile SingularAttribute<Invoice, String> ref;
    public static volatile SingularAttribute<Invoice, Double> payable;
    public static volatile SingularAttribute<Invoice, Double> paid;
    public static volatile SingularAttribute<Invoice, Double> discount;
    public static volatile SingularAttribute<Invoice, Integer> id;
    public static volatile SingularAttribute<Invoice, Double> returned;
    public static volatile SingularAttribute<Invoice, Integer> userId;
    public static volatile SingularAttribute<Invoice, Date> updatedAt;

}