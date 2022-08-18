package com.awtar.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DonationDetails.class)
public abstract class DonationDetails_ {

	public static volatile SingularAttribute<DonationDetails, Beneficiary> beneficiary;
	public static volatile SingularAttribute<DonationDetails, Nature> nature;
	public static volatile SingularAttribute<DonationDetails, Boolean> archivated;
	public static volatile SingularAttribute<DonationDetails, String> description;
	public static volatile SingularAttribute<DonationDetails, Long> id;
	public static volatile SetAttribute<DonationDetails, DonationItemDetails> items;
	public static volatile SingularAttribute<DonationDetails, DonationsIssued> donationsIssued;

	public static final String BENEFICIARY = "beneficiary";
	public static final String NATURE = "nature";
	public static final String ARCHIVATED = "archivated";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String ITEMS = "items";
	public static final String DONATIONS_ISSUED = "donationsIssued";

}

