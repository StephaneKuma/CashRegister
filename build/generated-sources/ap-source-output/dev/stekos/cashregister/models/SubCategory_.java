package dev.stekos.cashregister.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-15T22:06:46")
@StaticMetamodel(SubCategory.class)
public class SubCategory_ { 

    public static volatile SingularAttribute<SubCategory, Date> createdAt;
    public static volatile SingularAttribute<SubCategory, String> description;
    public static volatile SingularAttribute<SubCategory, Integer> id;
    public static volatile SingularAttribute<SubCategory, String> label;
    public static volatile SingularAttribute<SubCategory, Integer> categoryId;
    public static volatile SingularAttribute<SubCategory, Date> updatedAt;

}