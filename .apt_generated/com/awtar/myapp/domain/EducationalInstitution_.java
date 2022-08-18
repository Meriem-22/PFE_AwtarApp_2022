package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EducationalInstitution.class)
public abstract class EducationalInstitution_ {

	public static volatile SingularAttribute<EducationalInstitution, City> city;
	public static volatile SingularAttribute<EducationalInstitution, Boolean> archivated;
	public static volatile SingularAttribute<EducationalInstitution, String> name;
	public static volatile SingularAttribute<EducationalInstitution, String> adress;
	public static volatile SingularAttribute<EducationalInstitution, Long> id;
	public static volatile SetAttribute<EducationalInstitution, TeachingCurriculum> teachingCurricula;

	public static final String CITY = "city";
	public static final String ARCHIVATED = "archivated";
	public static final String NAME = "name";
	public static final String ADRESS = "adress";
	public static final String ID = "id";
	public static final String TEACHING_CURRICULA = "teachingCurricula";

}

