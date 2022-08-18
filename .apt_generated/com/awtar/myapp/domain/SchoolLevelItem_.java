package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SchoolLevelItem.class)
public abstract class SchoolLevelItem_ {

	public static volatile SingularAttribute<SchoolLevelItem, SchoolLevel> schoolLevel;
	public static volatile SingularAttribute<SchoolLevelItem, Item> item;
	public static volatile SingularAttribute<SchoolLevelItem, Boolean> archivated;
	public static volatile SingularAttribute<SchoolLevelItem, Integer> quantityNeeded;
	public static volatile SingularAttribute<SchoolLevelItem, Long> id;
	public static volatile SingularAttribute<SchoolLevelItem, Boolean> isSchoolItem;

	public static final String SCHOOL_LEVEL = "schoolLevel";
	public static final String ITEM = "item";
	public static final String ARCHIVATED = "archivated";
	public static final String QUANTITY_NEEDED = "quantityNeeded";
	public static final String ID = "id";
	public static final String IS_SCHOOL_ITEM = "isSchoolItem";

}

