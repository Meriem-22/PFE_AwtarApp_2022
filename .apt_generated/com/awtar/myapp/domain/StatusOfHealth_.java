package com.awtar.myapp.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(StatusOfHealth.class)
public abstract class StatusOfHealth_ {

	public static volatile SingularAttribute<StatusOfHealth, LocalDate> healthStatusDate;
	public static volatile SingularAttribute<StatusOfHealth, Boolean> archivated;
	public static volatile SingularAttribute<StatusOfHealth, Profile> person;
	public static volatile SingularAttribute<StatusOfHealth, byte[]> urlDetailsAttached;
	public static volatile SingularAttribute<StatusOfHealth, String> urlDetailsAttachedContentType;
	public static volatile SingularAttribute<StatusOfHealth, Long> id;
	public static volatile SingularAttribute<StatusOfHealth, HealthStatusCategory> healthStatusCategory;

	public static final String HEALTH_STATUS_DATE = "healthStatusDate";
	public static final String ARCHIVATED = "archivated";
	public static final String PERSON = "person";
	public static final String URL_DETAILS_ATTACHED = "urlDetailsAttached";
	public static final String URL_DETAILS_ATTACHED_CONTENT_TYPE = "urlDetailsAttachedContentType";
	public static final String ID = "id";
	public static final String HEALTH_STATUS_CATEGORY = "healthStatusCategory";

}

