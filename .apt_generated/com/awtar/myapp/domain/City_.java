package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(City.class)
public abstract class City_ {

	public static volatile SetAttribute<City, EducationalInstitution> educationalInstitutions;
	public static volatile SetAttribute<City, Establishment> establishments;
	public static volatile SetAttribute<City, Profile> personbirthPlaces;
	public static volatile SingularAttribute<City, String> governorate;
	public static volatile SingularAttribute<City, Boolean> archivated;
	public static volatile SingularAttribute<City, String> name;
	public static volatile SetAttribute<City, Profile> personplaceOfResidences;
	public static volatile SingularAttribute<City, Long> id;
	public static volatile SingularAttribute<City, Boolean> isGovernorate;

	public static final String EDUCATIONAL_INSTITUTIONS = "educationalInstitutions";
	public static final String ESTABLISHMENTS = "establishments";
	public static final String PERSONBIRTH_PLACES = "personbirthPlaces";
	public static final String GOVERNORATE = "governorate";
	public static final String ARCHIVATED = "archivated";
	public static final String NAME = "name";
	public static final String PERSONPLACE_OF_RESIDENCES = "personplaceOfResidences";
	public static final String ID = "id";
	public static final String IS_GOVERNORATE = "isGovernorate";

}

