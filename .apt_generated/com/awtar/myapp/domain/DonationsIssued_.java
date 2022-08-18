package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.Period;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DonationsIssued.class)
public abstract class DonationsIssued_ {

	public static volatile SingularAttribute<DonationsIssued, Boolean> canceledDonations;
	public static volatile SingularAttribute<DonationsIssued, LocalDate> canceledOn;
	public static volatile SingularAttribute<DonationsIssued, String> cancellationReason;
	public static volatile SingularAttribute<DonationsIssued, LocalDate> donationsDate;
	public static volatile SingularAttribute<DonationsIssued, Long> canceledBy;
	public static volatile SingularAttribute<DonationsIssued, Boolean> recurringDonations;
	public static volatile SingularAttribute<DonationsIssued, LocalDate> validationDate;
	public static volatile SingularAttribute<DonationsIssued, Long> validationUser;
	public static volatile SingularAttribute<DonationsIssued, Integer> recurrence;
	public static volatile SingularAttribute<DonationsIssued, Boolean> isValidated;
	public static volatile SetAttribute<DonationsIssued, DonationDetails> donationsDetails;
	public static volatile SingularAttribute<DonationsIssued, Boolean> archivated;
	public static volatile SingularAttribute<DonationsIssued, Period> periodicity;
	public static volatile SingularAttribute<DonationsIssued, String> model;
	public static volatile SingularAttribute<DonationsIssued, Long> id;

	public static final String CANCELED_DONATIONS = "canceledDonations";
	public static final String CANCELED_ON = "canceledOn";
	public static final String CANCELLATION_REASON = "cancellationReason";
	public static final String DONATIONS_DATE = "donationsDate";
	public static final String CANCELED_BY = "canceledBy";
	public static final String RECURRING_DONATIONS = "recurringDonations";
	public static final String VALIDATION_DATE = "validationDate";
	public static final String VALIDATION_USER = "validationUser";
	public static final String RECURRENCE = "recurrence";
	public static final String IS_VALIDATED = "isValidated";
	public static final String DONATIONS_DETAILS = "donationsDetails";
	public static final String ARCHIVATED = "archivated";
	public static final String PERIODICITY = "periodicity";
	public static final String MODEL = "model";
	public static final String ID = "id";

}

