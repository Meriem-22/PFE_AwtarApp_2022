package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.DonationDetails} entity.
 */
public class DonationDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    private Boolean archivated;

    private DonationsIssuedDTO donationsIssued;

    private NatureDTO nature;

    private BeneficiaryDTO beneficiary;

    private DonationItemDetailsDTO donationItemDetails;

    private String model;

    private LocalDate validationDate;

    private LocalDate donationsDate;

    private Long itemQuantities;

    private Double totalPrice;

    private Boolean isValidated;

    private Long[] idsBeneficiary;

    private String firstName;

    private String lastName;

    private byte[] urlPhoto;

    private String urlPhotoContentType;

    private String familyName;

    private String name;

    private String natureDetailDon;

    private Long childId;

    private Long detailsid;

    private Long bId;

    public DonationDetailsDTO() {}

    public DonationDetailsDTO(
        Long id,
        String description,
        String natureDetailDon,
        String firstName,
        String lastName,
        byte[] urlPhoto,
        String urlPhotoContentType,
        Long bId
    ) {
        this.id = id;
        this.description = description;
        this.natureDetailDon = natureDetailDon;
        this.firstName = firstName;
        this.lastName = lastName;
        this.urlPhoto = urlPhoto;
        this.urlPhotoContentType = urlPhotoContentType;
        this.bId = bId;
    }

    public DonationDetailsDTO(Long id, String description, String natureDetailDon, Long childId, Long detailsid) {
        this.id = id;
        this.description = description;
        this.natureDetailDon = natureDetailDon;
        this.childId = childId;
        this.detailsid = detailsid;
    }

    public DonationDetailsDTO(Long id, String description, String natureDetailDon, String name, Long bId) {
        this.id = id;
        this.description = description;
        this.natureDetailDon = natureDetailDon;
        this.name = name;
        this.bId = bId;
    }

    public DonationDetailsDTO(Long id, String model, Boolean isValidated, LocalDate donationsDate, Long itemsNumber) {
        this.id = id;
        this.model = model;
        this.isValidated = isValidated;
        this.donationsDate = donationsDate;
        this.itemQuantities = itemsNumber;
    }

    public DonationDetailsDTO(Long id, String model, Boolean isValidated, LocalDate donationsDate) {
        this.id = id;
        this.model = model;
        this.isValidated = isValidated;
        this.donationsDate = donationsDate;
    }

    public DonationDetailsDTO(String model, Long itemsNumber) {
        this.model = model;
        this.itemQuantities = itemsNumber;
    }

    public DonationDetailsDTO(Long itemsNumber) {
        this.itemQuantities = itemsNumber;
    }

    public DonationDetailsDTO(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public DonationsIssuedDTO getDonationsIssued() {
        return donationsIssued;
    }

    public void setDonationsIssued(DonationsIssuedDTO donationsIssued) {
        this.donationsIssued = donationsIssued;
    }

    public NatureDTO getNature() {
        return nature;
    }

    public void setNature(NatureDTO nature) {
        this.nature = nature;
    }

    public BeneficiaryDTO getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(BeneficiaryDTO beneficiary) {
        this.beneficiary = beneficiary;
    }

    public DonationItemDetailsDTO getDonationItemDetails() {
        return donationItemDetails;
    }

    public void setDonationItemDetails(DonationItemDetailsDTO donationItemDetails) {
        this.donationItemDetails = donationItemDetails;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(LocalDate validationDate) {
        this.validationDate = validationDate;
    }

    public LocalDate getDonationsDate() {
        return donationsDate;
    }

    public void setDonationsDate(LocalDate donationsDate) {
        this.donationsDate = donationsDate;
    }

    public Long getItemQuantities() {
        return itemQuantities;
    }

    public void setItemQuantities(Long itemsNumber) {
        this.itemQuantities = itemsNumber;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public Long[] getIdsBeneficiary() {
        return idsBeneficiary;
    }

    public void setIdsBeneficiary(Long[] idsBeneficiary) {
        this.idsBeneficiary = idsBeneficiary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte[] getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(byte[] urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getUrlPhotoContentType() {
        return urlPhotoContentType;
    }

    public void setUrlPhotoContentType(String urlPhotoContentType) {
        this.urlPhotoContentType = urlPhotoContentType;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNatureDetailDon() {
        return natureDetailDon;
    }

    public void setNatureDetailDon(String natureDetailDon) {
        this.natureDetailDon = natureDetailDon;
    }

    public Long getChildId() {
        return childId;
    }

    public void setChildId(Long childId) {
        this.childId = childId;
    }

    public Long getDetailsid() {
        return detailsid;
    }

    public void setDetailsid(Long detailsid) {
        this.detailsid = detailsid;
    }

    public Long getbId() {
        return bId;
    }

    public void setbId(Long bId) {
        this.bId = bId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationDetailsDTO)) {
            return false;
        }

        DonationDetailsDTO donationDetailsDTO = (DonationDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donationDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationDetailsDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", donationsIssued=" + getDonationsIssued() +
            ", nature=" + getNature() +
            ", beneficiary=" + getBeneficiary() +
            "}";
    }
}
