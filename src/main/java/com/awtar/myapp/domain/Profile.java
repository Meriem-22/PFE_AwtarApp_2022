package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name_arabic")
    private String firstNameArabic;

    @Column(name = "last_name_arabic")
    private String lastNameArabic;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "cin")
    private String cin;

    @Lob
    @Column(name = "url_photo")
    private byte[] urlPhoto;

    @Column(name = "url_photo_content_type")
    private String urlPhotoContentType;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "url_cin_attached")
    private byte[] urlCinAttached;

    @Column(name = "url_cin_attached_content_type")
    private String urlCinAttachedContentType;

    @Column(name = "archivated")
    private Boolean archivated;

    @JsonIgnoreProperties(value = { "familyHead", "parentProfile", "family" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Parent parent;

    @JsonIgnoreProperties(value = { "teachingCurricula", "childProfile", "family" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Child child;

    @JsonIgnoreProperties(value = { "donations", "beneficiaries", "authorizingOfficerProfile" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private AuthorizingOfficer authorizingOfficer;

    @JsonIgnoreProperties(value = { "beneficiaries" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Tutor tutor;

    @OneToMany(mappedBy = "person")
    @JsonIgnoreProperties(value = { "person", "healthStatusCategory" }, allowSetters = true)
    private Set<StatusOfHealth> statusOfHealths = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "personbirthPlaces", "personplaceOfResidences", "establishments", "educationalInstitutions" },
        allowSetters = true
    )
    private City birthPlace;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "personbirthPlaces", "personplaceOfResidences", "establishments", "educationalInstitutions" },
        allowSetters = true
    )
    private City placeOfResidence;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Profile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Profile firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Profile lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstNameArabic() {
        return this.firstNameArabic;
    }

    public Profile firstNameArabic(String firstNameArabic) {
        this.setFirstNameArabic(firstNameArabic);
        return this;
    }

    public void setFirstNameArabic(String firstNameArabic) {
        this.firstNameArabic = firstNameArabic;
    }

    public String getLastNameArabic() {
        return this.lastNameArabic;
    }

    public Profile lastNameArabic(String lastNameArabic) {
        this.setLastNameArabic(lastNameArabic);
        return this;
    }

    public void setLastNameArabic(String lastNameArabic) {
        this.lastNameArabic = lastNameArabic;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Profile gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Profile dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCin() {
        return this.cin;
    }

    public Profile cin(String cin) {
        this.setCin(cin);
        return this;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public byte[] getUrlPhoto() {
        return this.urlPhoto;
    }

    public Profile urlPhoto(byte[] urlPhoto) {
        this.setUrlPhoto(urlPhoto);
        return this;
    }

    public void setUrlPhoto(byte[] urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getUrlPhotoContentType() {
        return this.urlPhotoContentType;
    }

    public Profile urlPhotoContentType(String urlPhotoContentType) {
        this.urlPhotoContentType = urlPhotoContentType;
        return this;
    }

    public void setUrlPhotoContentType(String urlPhotoContentType) {
        this.urlPhotoContentType = urlPhotoContentType;
    }

    public String getAddress() {
        return this.address;
    }

    public Profile address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public Profile phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public Profile email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getUrlCinAttached() {
        return this.urlCinAttached;
    }

    public Profile urlCinAttached(byte[] urlCinAttached) {
        this.setUrlCinAttached(urlCinAttached);
        return this;
    }

    public void setUrlCinAttached(byte[] urlCinAttached) {
        this.urlCinAttached = urlCinAttached;
    }

    public String getUrlCinAttachedContentType() {
        return this.urlCinAttachedContentType;
    }

    public Profile urlCinAttachedContentType(String urlCinAttachedContentType) {
        this.urlCinAttachedContentType = urlCinAttachedContentType;
        return this;
    }

    public void setUrlCinAttachedContentType(String urlCinAttachedContentType) {
        this.urlCinAttachedContentType = urlCinAttachedContentType;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public Profile archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Parent getParent() {
        return this.parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Profile parent(Parent parent) {
        this.setParent(parent);
        return this;
    }

    public Child getChild() {
        return this.child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Profile child(Child child) {
        this.setChild(child);
        return this;
    }

    public AuthorizingOfficer getAuthorizingOfficer() {
        return this.authorizingOfficer;
    }

    public void setAuthorizingOfficer(AuthorizingOfficer authorizingOfficer) {
        this.authorizingOfficer = authorizingOfficer;
    }

    public Profile authorizingOfficer(AuthorizingOfficer authorizingOfficer) {
        this.setAuthorizingOfficer(authorizingOfficer);
        return this;
    }

    public Tutor getTutor() {
        return this.tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Profile tutor(Tutor tutor) {
        this.setTutor(tutor);
        return this;
    }

    public Set<StatusOfHealth> getStatusOfHealths() {
        return this.statusOfHealths;
    }

    public void setStatusOfHealths(Set<StatusOfHealth> statusOfHealths) {
        if (this.statusOfHealths != null) {
            this.statusOfHealths.forEach(i -> i.setPerson(null));
        }
        if (statusOfHealths != null) {
            statusOfHealths.forEach(i -> i.setPerson(this));
        }
        this.statusOfHealths = statusOfHealths;
    }

    public Profile statusOfHealths(Set<StatusOfHealth> statusOfHealths) {
        this.setStatusOfHealths(statusOfHealths);
        return this;
    }

    public Profile addStatusOfHealth(StatusOfHealth statusOfHealth) {
        this.statusOfHealths.add(statusOfHealth);
        statusOfHealth.setPerson(this);
        return this;
    }

    public Profile removeStatusOfHealth(StatusOfHealth statusOfHealth) {
        this.statusOfHealths.remove(statusOfHealth);
        statusOfHealth.setPerson(null);
        return this;
    }

    public City getBirthPlace() {
        return this.birthPlace;
    }

    public void setBirthPlace(City city) {
        this.birthPlace = city;
    }

    public Profile birthPlace(City city) {
        this.setBirthPlace(city);
        return this;
    }

    public City getPlaceOfResidence() {
        return this.placeOfResidence;
    }

    public void setPlaceOfResidence(City city) {
        this.placeOfResidence = city;
    }

    public Profile placeOfResidence(City city) {
        this.setPlaceOfResidence(city);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", firstNameArabic='" + getFirstNameArabic() + "'" +
            ", lastNameArabic='" + getLastNameArabic() + "'" +
            ", gender='" + getGender() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", cin='" + getCin() + "'" +
            ", urlPhoto='" + getUrlPhoto() + "'" +
            ", urlPhotoContentType='" + getUrlPhotoContentType() + "'" +
            ", address='" + getAddress() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", urlCinAttached='" + getUrlCinAttached() + "'" +
            ", urlCinAttachedContentType='" + getUrlCinAttachedContentType() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
