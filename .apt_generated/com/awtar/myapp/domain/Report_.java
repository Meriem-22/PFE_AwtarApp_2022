package com.awtar.myapp.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Report.class)
public abstract class Report_ {

	public static volatile SingularAttribute<Report, LocalDate> date;
	public static volatile SingularAttribute<Report, String> nature;
	public static volatile SingularAttribute<Report, String> urlEnclosedContentType;
	public static volatile SingularAttribute<Report, Boolean> archivated;
	public static volatile SingularAttribute<Report, byte[]> urlEnclosed;
	public static volatile SingularAttribute<Report, Establishment> establishment;
	public static volatile SingularAttribute<Report, Long> id;

	public static final String DATE = "date";
	public static final String NATURE = "nature";
	public static final String URL_ENCLOSED_CONTENT_TYPE = "urlEnclosedContentType";
	public static final String ARCHIVATED = "archivated";
	public static final String URL_ENCLOSED = "urlEnclosed";
	public static final String ESTABLISHMENT = "establishment";
	public static final String ID = "id";

}

