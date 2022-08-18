package com.awtar.myapp.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Visit.class)
public abstract class Visit_ {

	public static volatile SingularAttribute<Visit, String> attachedFileContentType;
	public static volatile SingularAttribute<Visit, Beneficiary> beneficiary;
	public static volatile SingularAttribute<Visit, Boolean> archivated;
	public static volatile SingularAttribute<Visit, String> description;
	public static volatile SingularAttribute<Visit, LocalDate> visitDate;
	public static volatile SingularAttribute<Visit, Long> id;
	public static volatile SingularAttribute<Visit, byte[]> attachedFile;
	public static volatile SingularAttribute<Visit, Long> realizedBy;

	public static final String ATTACHED_FILE_CONTENT_TYPE = "attachedFileContentType";
	public static final String BENEFICIARY = "beneficiary";
	public static final String ARCHIVATED = "archivated";
	public static final String DESCRIPTION = "description";
	public static final String VISIT_DATE = "visitDate";
	public static final String ID = "id";
	public static final String ATTACHED_FILE = "attachedFile";
	public static final String REALIZED_BY = "realizedBy";

}

