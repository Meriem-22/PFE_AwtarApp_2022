package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EstablishmentType.class)
public abstract class EstablishmentType_ {

	public static volatile SetAttribute<EstablishmentType, Establishment> establishments;
	public static volatile SingularAttribute<EstablishmentType, Boolean> archivated;
	public static volatile SingularAttribute<EstablishmentType, String> typeName;
	public static volatile SingularAttribute<EstablishmentType, Long> id;

	public static final String ESTABLISHMENTS = "establishments";
	public static final String ARCHIVATED = "archivated";
	public static final String TYPE_NAME = "typeName";
	public static final String ID = "id";

}

