package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.Beneficiaries;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Beneficiary.class)
public abstract class Beneficiary_ {

	public static volatile SetAttribute<Beneficiary, Visit> visits;
	public static volatile SetAttribute<Beneficiary, DonationDetails> donationdetails;
	public static volatile SingularAttribute<Beneficiary, Beneficiaries> beneficiaryType;
	public static volatile SingularAttribute<Beneficiary, Boolean> archivated;
	public static volatile SingularAttribute<Beneficiary, Long> id;
	public static volatile SingularAttribute<Beneficiary, AuthorizingOfficer> authorizingOfficer;
	public static volatile SingularAttribute<Beneficiary, String> beneficiaryReference;
	public static volatile SingularAttribute<Beneficiary, Tutor> tutor;

	public static final String VISITS = "visits";
	public static final String DONATIONDETAILS = "donationdetails";
	public static final String BENEFICIARY_TYPE = "beneficiaryType";
	public static final String ARCHIVATED = "archivated";
	public static final String ID = "id";
	public static final String AUTHORIZING_OFFICER = "authorizingOfficer";
	public static final String BENEFICIARY_REFERENCE = "beneficiaryReference";
	public static final String TUTOR = "tutor";

}

