package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.MaritalStatus;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Parent.class)
public abstract class Parent_ {

	public static volatile SingularAttribute<Parent, Family> familyHead;
	public static volatile SingularAttribute<Parent, String> occupation;
	public static volatile SingularAttribute<Parent, Boolean> deceased;
	public static volatile SingularAttribute<Parent, Long> cnss;
	public static volatile SingularAttribute<Parent, LocalDate> dateOfDeath;
	public static volatile SingularAttribute<Parent, Profile> parentProfile;
	public static volatile SingularAttribute<Parent, Long> id;
	public static volatile SingularAttribute<Parent, Family> family;
	public static volatile SingularAttribute<Parent, Double> annualRevenue;
	public static volatile SingularAttribute<Parent, MaritalStatus> maritalStatus;

	public static final String FAMILY_HEAD = "familyHead";
	public static final String OCCUPATION = "occupation";
	public static final String DECEASED = "deceased";
	public static final String CNSS = "cnss";
	public static final String DATE_OF_DEATH = "dateOfDeath";
	public static final String PARENT_PROFILE = "parentProfile";
	public static final String ID = "id";
	public static final String FAMILY = "family";
	public static final String ANNUAL_REVENUE = "annualRevenue";
	public static final String MARITAL_STATUS = "maritalStatus";

}

