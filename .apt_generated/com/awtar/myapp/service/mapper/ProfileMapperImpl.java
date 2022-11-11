package com.awtar.myapp.service.mapper;

import com.awtar.myapp.domain.AuthorizingOfficer;
import com.awtar.myapp.domain.Child;
import com.awtar.myapp.domain.City;
import com.awtar.myapp.domain.Family;
import com.awtar.myapp.domain.Parent;
import com.awtar.myapp.domain.Profile;
import com.awtar.myapp.domain.Tutor;
import com.awtar.myapp.service.dto.AuthorizingOfficerDTO;
import com.awtar.myapp.service.dto.ChildDTO;
import com.awtar.myapp.service.dto.CityDTO;
import com.awtar.myapp.service.dto.FamilyDTO;
import com.awtar.myapp.service.dto.ParentDTO;
import com.awtar.myapp.service.dto.ProfileDTO;
import com.awtar.myapp.service.dto.TutorDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-10T17:58:42+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.200.v20221012-0724, environment: Java 17.0.4.1 (Eclipse Adoptium)"
)
@Component
public class ProfileMapperImpl implements ProfileMapper {

    @Override
    public void partialUpdate(Profile arg0, ProfileDTO arg1) {
        if ( arg1 == null ) {
            return;
        }

        if ( arg1.getAddress() != null ) {
            arg0.setAddress( arg1.getAddress() );
        }
        if ( arg1.getArchivated() != null ) {
            arg0.setArchivated( arg1.getArchivated() );
        }
        if ( arg1.getAuthorizingOfficer() != null ) {
            if ( arg0.getAuthorizingOfficer() == null ) {
                arg0.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            authorizingOfficerDTOToAuthorizingOfficer( arg1.getAuthorizingOfficer(), arg0.getAuthorizingOfficer() );
        }
        if ( arg1.getBirthPlace() != null ) {
            if ( arg0.getBirthPlace() == null ) {
                arg0.setBirthPlace( new City() );
            }
            cityDTOToCity( arg1.getBirthPlace(), arg0.getBirthPlace() );
        }
        if ( arg1.getChild() != null ) {
            if ( arg0.getChild() == null ) {
                arg0.setChild( new Child() );
            }
            childDTOToChild( arg1.getChild(), arg0.getChild() );
        }
        if ( arg1.getCin() != null ) {
            arg0.setCin( arg1.getCin() );
        }
        if ( arg1.getDateOfBirth() != null ) {
            arg0.setDateOfBirth( arg1.getDateOfBirth() );
        }
        if ( arg1.getEmail() != null ) {
            arg0.setEmail( arg1.getEmail() );
        }
        if ( arg1.getFirstName() != null ) {
            arg0.setFirstName( arg1.getFirstName() );
        }
        if ( arg1.getFirstNameArabic() != null ) {
            arg0.setFirstNameArabic( arg1.getFirstNameArabic() );
        }
        if ( arg1.getGender() != null ) {
            arg0.setGender( arg1.getGender() );
        }
        if ( arg1.getId() != null ) {
            arg0.setId( arg1.getId() );
        }
        if ( arg1.getLastName() != null ) {
            arg0.setLastName( arg1.getLastName() );
        }
        if ( arg1.getLastNameArabic() != null ) {
            arg0.setLastNameArabic( arg1.getLastNameArabic() );
        }
        if ( arg1.getParent() != null ) {
            if ( arg0.getParent() == null ) {
                arg0.setParent( new Parent() );
            }
            parentDTOToParent( arg1.getParent(), arg0.getParent() );
        }
        if ( arg1.getPhone() != null ) {
            arg0.setPhone( arg1.getPhone() );
        }
        if ( arg1.getPlaceOfResidence() != null ) {
            if ( arg0.getPlaceOfResidence() == null ) {
                arg0.setPlaceOfResidence( new City() );
            }
            cityDTOToCity( arg1.getPlaceOfResidence(), arg0.getPlaceOfResidence() );
        }
        if ( arg1.getTutor() != null ) {
            if ( arg0.getTutor() == null ) {
                arg0.tutor( new Tutor() );
            }
            tutorDTOToTutor( arg1.getTutor(), arg0.getTutor() );
        }
        byte[] urlCinAttached = arg1.getUrlCinAttached();
        if ( urlCinAttached != null ) {
            arg0.urlCinAttached( Arrays.copyOf( urlCinAttached, urlCinAttached.length ) );
        }
        if ( arg1.getUrlCinAttachedContentType() != null ) {
            arg0.urlCinAttachedContentType( arg1.getUrlCinAttachedContentType() );
        }
        byte[] urlPhoto = arg1.getUrlPhoto();
        if ( urlPhoto != null ) {
            arg0.urlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        if ( arg1.getUrlPhotoContentType() != null ) {
            arg0.urlPhotoContentType( arg1.getUrlPhotoContentType() );
        }
    }

    @Override
    public List<ProfileDTO> toDto(List<Profile> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<ProfileDTO> list = new ArrayList<ProfileDTO>( arg0.size() );
        for ( Profile profile : arg0 ) {
            list.add( toDto( profile ) );
        }

        return list;
    }

    @Override
    public Profile toEntity(ProfileDTO arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Profile profile = new Profile();

        profile.setAddress( arg0.getAddress() );
        profile.setArchivated( arg0.getArchivated() );
        profile.setAuthorizingOfficer( authorizingOfficerDTOToAuthorizingOfficer1( arg0.getAuthorizingOfficer() ) );
        profile.setBirthPlace( cityDTOToCity1( arg0.getBirthPlace() ) );
        profile.setChild( childDTOToChild1( arg0.getChild() ) );
        profile.setCin( arg0.getCin() );
        profile.setDateOfBirth( arg0.getDateOfBirth() );
        profile.setEmail( arg0.getEmail() );
        profile.setFirstName( arg0.getFirstName() );
        profile.setFirstNameArabic( arg0.getFirstNameArabic() );
        profile.setGender( arg0.getGender() );
        profile.setId( arg0.getId() );
        profile.setLastName( arg0.getLastName() );
        profile.setLastNameArabic( arg0.getLastNameArabic() );
        profile.setParent( parentDTOToParent1( arg0.getParent() ) );
        profile.setPhone( arg0.getPhone() );
        profile.setPlaceOfResidence( cityDTOToCity1( arg0.getPlaceOfResidence() ) );
        profile.tutor( tutorDTOToTutor1( arg0.getTutor() ) );
        byte[] urlCinAttached = arg0.getUrlCinAttached();
        if ( urlCinAttached != null ) {
            profile.urlCinAttached( Arrays.copyOf( urlCinAttached, urlCinAttached.length ) );
        }
        profile.urlCinAttachedContentType( arg0.getUrlCinAttachedContentType() );
        byte[] urlPhoto = arg0.getUrlPhoto();
        if ( urlPhoto != null ) {
            profile.urlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        profile.urlPhotoContentType( arg0.getUrlPhotoContentType() );

        return profile;
    }

    @Override
    public List<Profile> toEntity(List<ProfileDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Profile> list = new ArrayList<Profile>( arg0.size() );
        for ( ProfileDTO profileDTO : arg0 ) {
            list.add( toEntity( profileDTO ) );
        }

        return list;
    }

    @Override
    public ProfileDTO toDto(Profile s) {
        if ( s == null ) {
            return null;
        }

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setParent( toDtoParentId( s.getParent() ) );
        profileDTO.setChild( toDtoChildId( s.getChild() ) );
        profileDTO.setAuthorizingOfficer( toDtoAuthorizingOfficerId( s.getAuthorizingOfficer() ) );
        profileDTO.setTutor( toDtoTutorId( s.getTutor() ) );
        profileDTO.setBirthPlace( toDtoCityName( s.getBirthPlace() ) );
        profileDTO.setPlaceOfResidence( toDtoCityName( s.getPlaceOfResidence() ) );
        profileDTO.setAddress( s.getAddress() );
        profileDTO.setArchivated( s.getArchivated() );
        profileDTO.setCin( s.getCin() );
        profileDTO.setDateOfBirth( s.getDateOfBirth() );
        profileDTO.setEmail( s.getEmail() );
        profileDTO.setFirstName( s.getFirstName() );
        profileDTO.setFirstNameArabic( s.getFirstNameArabic() );
        profileDTO.setGender( s.getGender() );
        profileDTO.setId( s.getId() );
        profileDTO.setLastName( s.getLastName() );
        profileDTO.setLastNameArabic( s.getLastNameArabic() );
        profileDTO.setPhone( s.getPhone() );
        byte[] urlCinAttached = s.getUrlCinAttached();
        if ( urlCinAttached != null ) {
            profileDTO.setUrlCinAttached( Arrays.copyOf( urlCinAttached, urlCinAttached.length ) );
        }
        profileDTO.setUrlCinAttachedContentType( s.getUrlCinAttachedContentType() );
        byte[] urlPhoto = s.getUrlPhoto();
        if ( urlPhoto != null ) {
            profileDTO.setUrlPhoto( Arrays.copyOf( urlPhoto, urlPhoto.length ) );
        }
        profileDTO.setUrlPhotoContentType( s.getUrlPhotoContentType() );

        return profileDTO;
    }

    @Override
    public ParentDTO toDtoParentId(Parent parent) {
        if ( parent == null ) {
            return null;
        }

        ParentDTO parentDTO = new ParentDTO();

        parentDTO.setId( parent.getId() );

        return parentDTO;
    }

    @Override
    public ChildDTO toDtoChildId(Child child) {
        if ( child == null ) {
            return null;
        }

        ChildDTO childDTO = new ChildDTO();

        childDTO.setId( child.getId() );

        return childDTO;
    }

    @Override
    public AuthorizingOfficerDTO toDtoAuthorizingOfficerId(AuthorizingOfficer authorizingOfficer) {
        if ( authorizingOfficer == null ) {
            return null;
        }

        AuthorizingOfficerDTO authorizingOfficerDTO = new AuthorizingOfficerDTO();

        authorizingOfficerDTO.setId( authorizingOfficer.getId() );

        return authorizingOfficerDTO;
    }

    @Override
    public TutorDTO toDtoTutorId(Tutor tutor) {
        if ( tutor == null ) {
            return null;
        }

        TutorDTO tutorDTO = new TutorDTO();

        tutorDTO.setId( tutor.getId() );

        return tutorDTO;
    }

    @Override
    public CityDTO toDtoCityName(City city) {
        if ( city == null ) {
            return null;
        }

        CityDTO cityDTO = new CityDTO();

        cityDTO.setId( city.getId() );
        cityDTO.setName( city.getName() );

        return cityDTO;
    }

    protected void authorizingOfficerDTOToAuthorizingOfficer(AuthorizingOfficerDTO authorizingOfficerDTO, AuthorizingOfficer mappingTarget) {
        if ( authorizingOfficerDTO == null ) {
            return;
        }

        if ( authorizingOfficerDTO.getAbbreviation() != null ) {
            mappingTarget.setAbbreviation( authorizingOfficerDTO.getAbbreviation() );
        }
        if ( authorizingOfficerDTO.getActivity() != null ) {
            mappingTarget.setActivity( authorizingOfficerDTO.getActivity() );
        }
        if ( authorizingOfficerDTO.getId() != null ) {
            mappingTarget.setId( authorizingOfficerDTO.getId() );
        }
        if ( authorizingOfficerDTO.getManager() != null ) {
            mappingTarget.setManager( authorizingOfficerDTO.getManager() );
        }
        if ( authorizingOfficerDTO.getManagerCin() != null ) {
            mappingTarget.setManagerCin( authorizingOfficerDTO.getManagerCin() );
        }
    }

    protected void cityDTOToCity(CityDTO cityDTO, City mappingTarget) {
        if ( cityDTO == null ) {
            return;
        }

        if ( cityDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( cityDTO.getArchivated() );
        }
        if ( cityDTO.getGovernorate() != null ) {
            mappingTarget.setGovernorate( cityDTO.getGovernorate() );
        }
        if ( cityDTO.getId() != null ) {
            mappingTarget.setId( cityDTO.getId() );
        }
        if ( cityDTO.getIsGovernorate() != null ) {
            mappingTarget.setIsGovernorate( cityDTO.getIsGovernorate() );
        }
        if ( cityDTO.getName() != null ) {
            mappingTarget.setName( cityDTO.getName() );
        }
    }

    protected void profileDTOToAuthorizingOfficer(ProfileDTO profileDTO, AuthorizingOfficer mappingTarget) {
        if ( profileDTO == null ) {
            return;
        }

        if ( profileDTO.getActivity() != null ) {
            mappingTarget.setActivity( profileDTO.getActivity() );
        }
        if ( profileDTO.getId() != null ) {
            mappingTarget.setId( profileDTO.getId() );
        }
    }

    protected void profileDTOToTutor(ProfileDTO profileDTO, Tutor mappingTarget) {
        if ( profileDTO == null ) {
            return;
        }

        if ( profileDTO.getActivity() != null ) {
            mappingTarget.setActivity( profileDTO.getActivity() );
        }
        if ( profileDTO.getId() != null ) {
            mappingTarget.setId( profileDTO.getId() );
        }
    }

    protected void familyDTOToFamily(FamilyDTO familyDTO, Family mappingTarget) {
        if ( familyDTO == null ) {
            return;
        }

        if ( familyDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            profileDTOToAuthorizingOfficer( familyDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( familyDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.setTutor( new Tutor() );
            }
            profileDTOToTutor( familyDTO.getTutor(), mappingTarget.getTutor() );
        }
        if ( familyDTO.getArchivated() != null ) {
            mappingTarget.setArchivated( familyDTO.getArchivated() );
        }
        if ( familyDTO.getArea() != null ) {
            mappingTarget.setArea( familyDTO.getArea() );
        }
        if ( familyDTO.getDwelling() != null ) {
            mappingTarget.setDwelling( familyDTO.getDwelling() );
        }
        if ( familyDTO.getFamilyName() != null ) {
            mappingTarget.setFamilyName( familyDTO.getFamilyName() );
        }
        if ( familyDTO.getId() != null ) {
            mappingTarget.setId( familyDTO.getId() );
        }
        if ( familyDTO.getNotebookOfHandicap() != null ) {
            mappingTarget.setNotebookOfHandicap( familyDTO.getNotebookOfHandicap() );
        }
        if ( familyDTO.getNotebookOfPoverty() != null ) {
            mappingTarget.setNotebookOfPoverty( familyDTO.getNotebookOfPoverty() );
        }
    }

    protected void childDTOToChild(ChildDTO childDTO, Child mappingTarget) {
        if ( childDTO == null ) {
            return;
        }

        if ( childDTO.getAuthorizingOfficer() != null ) {
            if ( mappingTarget.getAuthorizingOfficer() == null ) {
                mappingTarget.setAuthorizingOfficer( new AuthorizingOfficer() );
            }
            profileDTOToAuthorizingOfficer( childDTO.getAuthorizingOfficer(), mappingTarget.getAuthorizingOfficer() );
        }
        if ( childDTO.getTutor() != null ) {
            if ( mappingTarget.getTutor() == null ) {
                mappingTarget.setTutor( new Tutor() );
            }
            profileDTOToTutor( childDTO.getTutor(), mappingTarget.getTutor() );
        }
        if ( childDTO.getFamily() != null ) {
            if ( mappingTarget.getFamily() == null ) {
                mappingTarget.setFamily( new Family() );
            }
            familyDTOToFamily( childDTO.getFamily(), mappingTarget.getFamily() );
        }
        if ( childDTO.getId() != null ) {
            mappingTarget.setId( childDTO.getId() );
        }
    }

    protected void parentDTOToParent(ParentDTO parentDTO, Parent mappingTarget) {
        if ( parentDTO == null ) {
            return;
        }

        if ( parentDTO.getAnnualRevenue() != null ) {
            mappingTarget.setAnnualRevenue( parentDTO.getAnnualRevenue() );
        }
        if ( parentDTO.getCnss() != null ) {
            mappingTarget.setCnss( parentDTO.getCnss() );
        }
        if ( parentDTO.getDateOfDeath() != null ) {
            mappingTarget.setDateOfDeath( parentDTO.getDateOfDeath() );
        }
        if ( parentDTO.getDeceased() != null ) {
            mappingTarget.setDeceased( parentDTO.getDeceased() );
        }
        if ( parentDTO.getFamily() != null ) {
            if ( mappingTarget.getFamily() == null ) {
                mappingTarget.setFamily( new Family() );
            }
            familyDTOToFamily( parentDTO.getFamily(), mappingTarget.getFamily() );
        }
        if ( parentDTO.getFamilyHead() != null ) {
            if ( mappingTarget.getFamilyHead() == null ) {
                mappingTarget.setFamilyHead( new Family() );
            }
            familyDTOToFamily( parentDTO.getFamilyHead(), mappingTarget.getFamilyHead() );
        }
        if ( parentDTO.getId() != null ) {
            mappingTarget.setId( parentDTO.getId() );
        }
        if ( parentDTO.getMaritalStatus() != null ) {
            mappingTarget.setMaritalStatus( parentDTO.getMaritalStatus() );
        }
        if ( parentDTO.getOccupation() != null ) {
            mappingTarget.setOccupation( parentDTO.getOccupation() );
        }
    }

    protected void tutorDTOToTutor(TutorDTO tutorDTO, Tutor mappingTarget) {
        if ( tutorDTO == null ) {
            return;
        }

        if ( tutorDTO.getActivity() != null ) {
            mappingTarget.setActivity( tutorDTO.getActivity() );
        }
        if ( tutorDTO.getId() != null ) {
            mappingTarget.setId( tutorDTO.getId() );
        }
        if ( tutorDTO.getManager() != null ) {
            mappingTarget.setManager( tutorDTO.getManager() );
        }
        if ( tutorDTO.getManagerCin() != null ) {
            mappingTarget.setManagerCin( tutorDTO.getManagerCin() );
        }
    }

    protected AuthorizingOfficer authorizingOfficerDTOToAuthorizingOfficer1(AuthorizingOfficerDTO authorizingOfficerDTO) {
        if ( authorizingOfficerDTO == null ) {
            return null;
        }

        AuthorizingOfficer authorizingOfficer = new AuthorizingOfficer();

        authorizingOfficer.setAbbreviation( authorizingOfficerDTO.getAbbreviation() );
        authorizingOfficer.setActivity( authorizingOfficerDTO.getActivity() );
        authorizingOfficer.setId( authorizingOfficerDTO.getId() );
        authorizingOfficer.setManager( authorizingOfficerDTO.getManager() );
        authorizingOfficer.setManagerCin( authorizingOfficerDTO.getManagerCin() );

        return authorizingOfficer;
    }

    protected City cityDTOToCity1(CityDTO cityDTO) {
        if ( cityDTO == null ) {
            return null;
        }

        City city = new City();

        city.setArchivated( cityDTO.getArchivated() );
        city.setGovernorate( cityDTO.getGovernorate() );
        city.setId( cityDTO.getId() );
        city.setIsGovernorate( cityDTO.getIsGovernorate() );
        city.setName( cityDTO.getName() );

        return city;
    }

    protected AuthorizingOfficer profileDTOToAuthorizingOfficer1(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        AuthorizingOfficer authorizingOfficer = new AuthorizingOfficer();

        authorizingOfficer.setActivity( profileDTO.getActivity() );
        authorizingOfficer.setId( profileDTO.getId() );

        return authorizingOfficer;
    }

    protected Tutor profileDTOToTutor1(ProfileDTO profileDTO) {
        if ( profileDTO == null ) {
            return null;
        }

        Tutor tutor = new Tutor();

        tutor.setActivity( profileDTO.getActivity() );
        tutor.setId( profileDTO.getId() );

        return tutor;
    }

    protected Family familyDTOToFamily1(FamilyDTO familyDTO) {
        if ( familyDTO == null ) {
            return null;
        }

        Family family = new Family();

        family.setAuthorizingOfficer( profileDTOToAuthorizingOfficer1( familyDTO.getAuthorizingOfficer() ) );
        family.setTutor( profileDTOToTutor1( familyDTO.getTutor() ) );
        family.setArchivated( familyDTO.getArchivated() );
        family.setArea( familyDTO.getArea() );
        family.setDwelling( familyDTO.getDwelling() );
        family.setFamilyName( familyDTO.getFamilyName() );
        family.setId( familyDTO.getId() );
        family.setNotebookOfHandicap( familyDTO.getNotebookOfHandicap() );
        family.setNotebookOfPoverty( familyDTO.getNotebookOfPoverty() );

        return family;
    }

    protected Child childDTOToChild1(ChildDTO childDTO) {
        if ( childDTO == null ) {
            return null;
        }

        Child child = new Child();

        child.setAuthorizingOfficer( profileDTOToAuthorizingOfficer1( childDTO.getAuthorizingOfficer() ) );
        child.setTutor( profileDTOToTutor1( childDTO.getTutor() ) );
        child.setFamily( familyDTOToFamily1( childDTO.getFamily() ) );
        child.setId( childDTO.getId() );

        return child;
    }

    protected Parent parentDTOToParent1(ParentDTO parentDTO) {
        if ( parentDTO == null ) {
            return null;
        }

        Parent parent = new Parent();

        parent.setAnnualRevenue( parentDTO.getAnnualRevenue() );
        parent.setCnss( parentDTO.getCnss() );
        parent.setDateOfDeath( parentDTO.getDateOfDeath() );
        parent.setDeceased( parentDTO.getDeceased() );
        parent.setFamily( familyDTOToFamily1( parentDTO.getFamily() ) );
        parent.setFamilyHead( familyDTOToFamily1( parentDTO.getFamilyHead() ) );
        parent.setId( parentDTO.getId() );
        parent.setMaritalStatus( parentDTO.getMaritalStatus() );
        parent.setOccupation( parentDTO.getOccupation() );

        return parent;
    }

    protected Tutor tutorDTOToTutor1(TutorDTO tutorDTO) {
        if ( tutorDTO == null ) {
            return null;
        }

        Tutor tutor = new Tutor();

        tutor.setActivity( tutorDTO.getActivity() );
        tutor.setId( tutorDTO.getId() );
        tutor.setManager( tutorDTO.getManager() );
        tutor.setManagerCin( tutorDTO.getManagerCin() );

        return tutor;
    }
}
