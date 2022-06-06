package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Report.
 */
@Entity
@Table(name = "report")
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nature", nullable = false)
    private String nature;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Lob
    @Column(name = "url_enclosed", nullable = false)
    private byte[] urlEnclosed;

    @NotNull
    @Column(name = "url_enclosed_content_type", nullable = false)
    private String urlEnclosedContentType;

    @Column(name = "archivated")
    private Boolean archivated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "reports", "establishmentType", "city" }, allowSetters = true)
    private Establishment establishment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Report id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNature() {
        return this.nature;
    }

    public Report nature(String nature) {
        this.setNature(nature);
        return this;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Report date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public byte[] getUrlEnclosed() {
        return this.urlEnclosed;
    }

    public Report urlEnclosed(byte[] urlEnclosed) {
        this.setUrlEnclosed(urlEnclosed);
        return this;
    }

    public void setUrlEnclosed(byte[] urlEnclosed) {
        this.urlEnclosed = urlEnclosed;
    }

    public String getUrlEnclosedContentType() {
        return this.urlEnclosedContentType;
    }

    public Report urlEnclosedContentType(String urlEnclosedContentType) {
        this.urlEnclosedContentType = urlEnclosedContentType;
        return this;
    }

    public void setUrlEnclosedContentType(String urlEnclosedContentType) {
        this.urlEnclosedContentType = urlEnclosedContentType;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public Report archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Establishment getEstablishment() {
        return this.establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public Report establishment(Establishment establishment) {
        this.setEstablishment(establishment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Report)) {
            return false;
        }
        return id != null && id.equals(((Report) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Report{" +
            "id=" + getId() +
            ", nature='" + getNature() + "'" +
            ", date='" + getDate() + "'" +
            ", urlEnclosed='" + getUrlEnclosed() + "'" +
            ", urlEnclosedContentType='" + getUrlEnclosedContentType() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
