package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Establishment.class)
public abstract class Establishment_ extends com.awtar.myapp.domain.Beneficiary_ {

	public static volatile SetAttribute<Establishment, Report> reports;
	public static volatile SingularAttribute<Establishment, String> managerCin;
	public static volatile SingularAttribute<Establishment, String> activity;
	public static volatile SingularAttribute<Establishment, String> manager;
	public static volatile SingularAttribute<Establishment, City> city;
	public static volatile SingularAttribute<Establishment, String> adress;
	public static volatile SingularAttribute<Establishment, String> remark;
	public static volatile SingularAttribute<Establishment, String> cp;
	public static volatile SingularAttribute<Establishment, String> site;
	public static volatile SingularAttribute<Establishment, String> phone;
	public static volatile SingularAttribute<Establishment, String> contact;
	public static volatile SingularAttribute<Establishment, String> name;
	public static volatile SingularAttribute<Establishment, EstablishmentType> establishmentType;
	public static volatile SingularAttribute<Establishment, String> region;
	public static volatile SingularAttribute<Establishment, String> fax;
	public static volatile SingularAttribute<Establishment, String> email;

	public static final String REPORTS = "reports";
	public static final String MANAGER_CIN = "managerCin";
	public static final String ACTIVITY = "activity";
	public static final String MANAGER = "manager";
	public static final String CITY = "city";
	public static final String ADRESS = "adress";
	public static final String REMARK = "remark";
	public static final String CP = "cp";
	public static final String SITE = "site";
	public static final String PHONE = "phone";
	public static final String CONTACT = "contact";
	public static final String NAME = "name";
	public static final String ESTABLISHMENT_TYPE = "establishmentType";
	public static final String REGION = "region";
	public static final String FAX = "fax";
	public static final String EMAIL = "email";

}

