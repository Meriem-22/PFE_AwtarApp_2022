import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProfileService } from '../service/profile.service';
import { IProfile, Profile } from '../profile.model';
import { IParent } from 'app/entities/parent/parent.model';
import { ParentService } from 'app/entities/parent/service/parent.service';
import { IChild } from 'app/entities/child/child.model';
import { ChildService } from 'app/entities/child/service/child.service';
import { IAuthorizingOfficer } from 'app/entities/authorizing-officer/authorizing-officer.model';
import { AuthorizingOfficerService } from 'app/entities/authorizing-officer/service/authorizing-officer.service';
import { ITutor } from 'app/entities/tutor/tutor.model';
import { TutorService } from 'app/entities/tutor/service/tutor.service';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';

import { ProfileUpdateComponent } from './profile-update.component';

describe('Profile Management Update Component', () => {
  let comp: ProfileUpdateComponent;
  let fixture: ComponentFixture<ProfileUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let profileService: ProfileService;
  let parentService: ParentService;
  let childService: ChildService;
  let authorizingOfficerService: AuthorizingOfficerService;
  let tutorService: TutorService;
  let cityService: CityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProfileUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProfileUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProfileUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    profileService = TestBed.inject(ProfileService);
    parentService = TestBed.inject(ParentService);
    childService = TestBed.inject(ChildService);
    authorizingOfficerService = TestBed.inject(AuthorizingOfficerService);
    tutorService = TestBed.inject(TutorService);
    cityService = TestBed.inject(CityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call parent query and add missing value', () => {
      const profile: IProfile = { id: 456 };
      const parent: IParent = { id: 90710 };
      profile.parent = parent;

      const parentCollection: IParent[] = [{ id: 15967 }];
      jest.spyOn(parentService, 'query').mockReturnValue(of(new HttpResponse({ body: parentCollection })));
      const expectedCollection: IParent[] = [parent, ...parentCollection];
      jest.spyOn(parentService, 'addParentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      expect(parentService.query).toHaveBeenCalled();
      expect(parentService.addParentToCollectionIfMissing).toHaveBeenCalledWith(parentCollection, parent);
      expect(comp.parentsCollection).toEqual(expectedCollection);
    });

    it('Should call child query and add missing value', () => {
      const profile: IProfile = { id: 456 };
      const child: IChild = { id: 4752 };
      profile.child = child;

      const childCollection: IChild[] = [{ id: 34812 }];
      jest.spyOn(childService, 'query').mockReturnValue(of(new HttpResponse({ body: childCollection })));
      const expectedCollection: IChild[] = [child, ...childCollection];
      jest.spyOn(childService, 'addChildToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      expect(childService.query).toHaveBeenCalled();
      expect(childService.addChildToCollectionIfMissing).toHaveBeenCalledWith(childCollection, child);
      expect(comp.childrenCollection).toEqual(expectedCollection);
    });

    it('Should call authorizingOfficer query and add missing value', () => {
      const profile: IProfile = { id: 456 };
      const authorizingOfficer: IAuthorizingOfficer = { id: 20596 };
      profile.authorizingOfficer = authorizingOfficer;

      const authorizingOfficerCollection: IAuthorizingOfficer[] = [{ id: 75736 }];
      jest.spyOn(authorizingOfficerService, 'query').mockReturnValue(of(new HttpResponse({ body: authorizingOfficerCollection })));
      const expectedCollection: IAuthorizingOfficer[] = [authorizingOfficer, ...authorizingOfficerCollection];
      jest.spyOn(authorizingOfficerService, 'addAuthorizingOfficerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      expect(authorizingOfficerService.query).toHaveBeenCalled();
      expect(authorizingOfficerService.addAuthorizingOfficerToCollectionIfMissing).toHaveBeenCalledWith(
        authorizingOfficerCollection,
        authorizingOfficer
      );
      expect(comp.authorizingOfficersCollection).toEqual(expectedCollection);
    });

    it('Should call tutor query and add missing value', () => {
      const profile: IProfile = { id: 456 };
      const tutor: ITutor = { id: 27095 };
      profile.tutor = tutor;

      const tutorCollection: ITutor[] = [{ id: 19355 }];
      jest.spyOn(tutorService, 'query').mockReturnValue(of(new HttpResponse({ body: tutorCollection })));
      const expectedCollection: ITutor[] = [tutor, ...tutorCollection];
      jest.spyOn(tutorService, 'addTutorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      expect(tutorService.query).toHaveBeenCalled();
      expect(tutorService.addTutorToCollectionIfMissing).toHaveBeenCalledWith(tutorCollection, tutor);
      expect(comp.tutorsCollection).toEqual(expectedCollection);
    });

    it('Should call City query and add missing value', () => {
      const profile: IProfile = { id: 456 };
      const birthPlace: ICity = { id: 79463 };
      profile.birthPlace = birthPlace;
      const placeOfResidence: ICity = { id: 60129 };
      profile.placeOfResidence = placeOfResidence;

      const cityCollection: ICity[] = [{ id: 56682 }];
      jest.spyOn(cityService, 'query').mockReturnValue(of(new HttpResponse({ body: cityCollection })));
      const additionalCities = [birthPlace, placeOfResidence];
      const expectedCollection: ICity[] = [...additionalCities, ...cityCollection];
      jest.spyOn(cityService, 'addCityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      expect(cityService.query).toHaveBeenCalled();
      expect(cityService.addCityToCollectionIfMissing).toHaveBeenCalledWith(cityCollection, ...additionalCities);
      expect(comp.citiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const profile: IProfile = { id: 456 };
      const parent: IParent = { id: 7833 };
      profile.parent = parent;
      const child: IChild = { id: 54514 };
      profile.child = child;
      const authorizingOfficer: IAuthorizingOfficer = { id: 46120 };
      profile.authorizingOfficer = authorizingOfficer;
      const tutor: ITutor = { id: 26797 };
      profile.tutor = tutor;
      const birthPlace: ICity = { id: 82969 };
      profile.birthPlace = birthPlace;
      const placeOfResidence: ICity = { id: 84331 };
      profile.placeOfResidence = placeOfResidence;

      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(profile));
      expect(comp.parentsCollection).toContain(parent);
      expect(comp.childrenCollection).toContain(child);
      expect(comp.authorizingOfficersCollection).toContain(authorizingOfficer);
      expect(comp.tutorsCollection).toContain(tutor);
      expect(comp.citiesSharedCollection).toContain(birthPlace);
      expect(comp.citiesSharedCollection).toContain(placeOfResidence);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Profile>>();
      const profile = { id: 123 };
      jest.spyOn(profileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profile }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(profileService.update).toHaveBeenCalledWith(profile);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Profile>>();
      const profile = new Profile();
      jest.spyOn(profileService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profile }));
      saveSubject.complete();

      // THEN
      expect(profileService.create).toHaveBeenCalledWith(profile);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Profile>>();
      const profile = { id: 123 };
      jest.spyOn(profileService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profile });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(profileService.update).toHaveBeenCalledWith(profile);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackParentById', () => {
      it('Should return tracked Parent primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackParentById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackChildById', () => {
      it('Should return tracked Child primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackChildById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackAuthorizingOfficerById', () => {
      it('Should return tracked AuthorizingOfficer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAuthorizingOfficerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTutorById', () => {
      it('Should return tracked Tutor primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTutorById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCityById', () => {
      it('Should return tracked City primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCityById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
