package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Family.class)
public abstract class Family_ extends com.awtar.myapp.domain.Beneficiary_ {

	public static volatile SingularAttribute<Family, String> area;
	public static volatile SingularAttribute<Family, Boolean> notebookOfHandicap;
	public static volatile SingularAttribute<Family, Parent> head;
	public static volatile SingularAttribute<Family, Boolean> notebookOfPoverty;
	public static volatile SetAttribute<Family, Child> children;
	public static volatile SingularAttribute<Family, Boolean> archivated;
	public static volatile SingularAttribute<Family, String> familyName;
	public static volatile SingularAttribute<Family, String> dwelling;
	public static volatile SetAttribute<Family, Parent> parents;

	public static final String AREA = "area";
	public static final String NOTEBOOK_OF_HANDICAP = "notebookOfHandicap";
	public static final String HEAD = "head";
	public static final String NOTEBOOK_OF_POVERTY = "notebookOfPoverty";
	public static final String CHILDREN = "children";
	public static final String ARCHIVATED = "archivated";
	public static final String FAMILY_NAME = "familyName";
	public static final String DWELLING = "dwelling";
	public static final String PARENTS = "parents";

}

