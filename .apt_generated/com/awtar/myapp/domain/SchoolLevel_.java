package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SchoolLevel.class)
public abstract class SchoolLevel_ {

	public static volatile SingularAttribute<SchoolLevel, String> schoolLevel;
	public static volatile SingularAttribute<SchoolLevel, Boolean> archivated;
	public static volatile SingularAttribute<SchoolLevel, Long> id;
	public static volatile SetAttribute<SchoolLevel, TeachingCurriculum> teachingCurricula;
	public static volatile SetAttribute<SchoolLevel, SchoolLevelItem> items;

	public static final String SCHOOL_LEVEL = "schoolLevel";
	public static final String ARCHIVATED = "archivated";
	public static final String ID = "id";
	public static final String TEACHING_CURRICULA = "teachingCurricula";
	public static final String ITEMS = "items";

}

