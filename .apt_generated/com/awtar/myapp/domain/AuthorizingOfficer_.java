package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AuthorizingOfficer.class)
public abstract class AuthorizingOfficer_ {

	public static volatile SingularAttribute<AuthorizingOfficer, String> managerCin;
	public static volatile SingularAttribute<AuthorizingOfficer, String> activity;
	public static volatile SingularAttribute<AuthorizingOfficer, String> manager;
	public static volatile SetAttribute<AuthorizingOfficer, DonationsReceived> donations;
	public static volatile SingularAttribute<AuthorizingOfficer, Profile> authorizingOfficerProfile;
	public static volatile SingularAttribute<AuthorizingOfficer, Long> id;
	public static volatile SingularAttribute<AuthorizingOfficer, String> abbreviation;
	public static volatile SetAttribute<AuthorizingOfficer, Beneficiary> beneficiaries;

	public static final String MANAGER_CIN = "managerCin";
	public static final String ACTIVITY = "activity";
	public static final String MANAGER = "manager";
	public static final String DONATIONS = "donations";
	public static final String AUTHORIZING_OFFICER_PROFILE = "authorizingOfficerProfile";
	public static final String ID = "id";
	public static final String ABBREVIATION = "abbreviation";
	public static final String BENEFICIARIES = "beneficiaries";

}

