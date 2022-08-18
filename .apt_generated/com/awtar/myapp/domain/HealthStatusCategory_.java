package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(HealthStatusCategory.class)
public abstract class HealthStatusCategory_ {

	public static volatile SingularAttribute<HealthStatusCategory, Boolean> archivated;
	public static volatile SingularAttribute<HealthStatusCategory, String> name;
	public static volatile SingularAttribute<HealthStatusCategory, Long> id;
	public static volatile SetAttribute<HealthStatusCategory, StatusOfHealth> stateOfHealths;

	public static final String ARCHIVATED = "archivated";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String STATE_OF_HEALTHS = "stateOfHealths";

}

