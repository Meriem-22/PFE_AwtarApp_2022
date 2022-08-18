package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.Beneficiaries;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Nature.class)
public abstract class Nature_ {

	public static volatile SetAttribute<Nature, DonationDetails> donationdetails;
	public static volatile SingularAttribute<Nature, Beneficiaries> destinedTo;
	public static volatile SingularAttribute<Nature, Boolean> necessityValue;
	public static volatile SingularAttribute<Nature, Boolean> archivated;
	public static volatile SingularAttribute<Nature, String> name;
	public static volatile SingularAttribute<Nature, Long> id;
	public static volatile SetAttribute<Nature, Item> items;

	public static final String DONATIONDETAILS = "donationdetails";
	public static final String DESTINED_TO = "destinedTo";
	public static final String NECESSITY_VALUE = "necessityValue";
	public static final String ARCHIVATED = "archivated";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String ITEMS = "items";

}

