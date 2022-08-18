package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.Status;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ChildStatus.class)
public abstract class ChildStatus_ {

	public static volatile SingularAttribute<ChildStatus, Status> staus;
	public static volatile SingularAttribute<ChildStatus, Boolean> archivated;
	public static volatile SingularAttribute<ChildStatus, Long> id;
	public static volatile SetAttribute<ChildStatus, ChildStatusItem> items;

	public static final String STAUS = "staus";
	public static final String ARCHIVATED = "archivated";
	public static final String ID = "id";
	public static final String ITEMS = "items";

}

