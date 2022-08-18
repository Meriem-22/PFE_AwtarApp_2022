package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ChildStatusItem.class)
public abstract class ChildStatusItem_ {

	public static volatile SingularAttribute<ChildStatusItem, Item> item;
	public static volatile SingularAttribute<ChildStatusItem, Boolean> archivated;
	public static volatile SingularAttribute<ChildStatusItem, ChildStatus> childStatus;
	public static volatile SingularAttribute<ChildStatusItem, Long> id;
	public static volatile SingularAttribute<ChildStatusItem, Boolean> affected;

	public static final String ITEM = "item";
	public static final String ARCHIVATED = "archivated";
	public static final String CHILD_STATUS = "childStatus";
	public static final String ID = "id";
	public static final String AFFECTED = "affected";

}

