package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tutor.class)
public abstract class Tutor_ {

	public static volatile SingularAttribute<Tutor, String> managerCin;
	public static volatile SingularAttribute<Tutor, String> activity;
	public static volatile SingularAttribute<Tutor, String> manager;
	public static volatile SingularAttribute<Tutor, Profile> tutorProfile;
	public static volatile SingularAttribute<Tutor, Long> id;
	public static volatile SetAttribute<Tutor, Beneficiary> beneficiaries;

	public static final String MANAGER_CIN = "managerCin";
	public static final String ACTIVITY = "activity";
	public static final String MANAGER = "manager";
	public static final String TUTOR_PROFILE = "tutorProfile";
	public static final String ID = "id";
	public static final String BENEFICIARIES = "beneficiaries";

}

