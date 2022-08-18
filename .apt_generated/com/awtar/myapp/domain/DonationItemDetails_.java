package com.awtar.myapp.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DonationItemDetails.class)
public abstract class DonationItemDetails_ {

	public static volatile SingularAttribute<DonationItemDetails, LocalDate> date;
	public static volatile SingularAttribute<DonationItemDetails, Item> item;
	public static volatile SingularAttribute<DonationItemDetails, Integer> quantity;
	public static volatile SingularAttribute<DonationItemDetails, Boolean> archivated;
	public static volatile SingularAttribute<DonationItemDetails, Long> id;
	public static volatile SingularAttribute<DonationItemDetails, DonationDetails> donationDetails;

	public static final String DATE = "date";
	public static final String ITEM = "item";
	public static final String QUANTITY = "quantity";
	public static final String ARCHIVATED = "archivated";
	public static final String ID = "id";
	public static final String DONATION_DETAILS = "donationDetails";

}

