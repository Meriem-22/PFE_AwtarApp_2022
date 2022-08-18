package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Child.class)
public abstract class Child_ extends com.awtar.myapp.domain.Beneficiary_ {

	public static volatile SingularAttribute<Child, Profile> childProfile;
	public static volatile SetAttribute<Child, TeachingCurriculum> teachingCurricula;
	public static volatile SingularAttribute<Child, Family> family;

	public static final String CHILD_PROFILE = "childProfile";
	public static final String TEACHING_CURRICULA = "teachingCurricula";
	public static final String FAMILY = "family";

}

