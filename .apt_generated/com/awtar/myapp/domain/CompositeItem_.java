package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CompositeItem.class)
public abstract class CompositeItem_ {

	public static volatile SingularAttribute<CompositeItem, Integer> quantity;
	public static volatile SingularAttribute<CompositeItem, Item> composant;
	public static volatile SingularAttribute<CompositeItem, Boolean> archivated;
	public static volatile SingularAttribute<CompositeItem, Item> composer;
	public static volatile SingularAttribute<CompositeItem, Long> id;

	public static final String QUANTITY = "quantity";
	public static final String COMPOSANT = "composant";
	public static final String ARCHIVATED = "archivated";
	public static final String COMPOSER = "composer";
	public static final String ID = "id";

}

