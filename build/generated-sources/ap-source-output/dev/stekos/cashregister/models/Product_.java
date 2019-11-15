package dev.stekos.cashregister.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-15T22:06:46")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, Date> createdAt;
    public static volatile SingularAttribute<Product, Integer> supplierId;
    public static volatile SingularAttribute<Product, Double> quantity;
    public static volatile SingularAttribute<Product, Double> price;
    public static volatile SingularAttribute<Product, String> name;
    public static volatile SingularAttribute<Product, String> description;
    public static volatile SingularAttribute<Product, Integer> id;
    public static volatile SingularAttribute<Product, Integer> subCategoryId;
    public static volatile SingularAttribute<Product, Date> updatedAt;

}