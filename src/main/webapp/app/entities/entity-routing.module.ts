import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'donations-issued',
        data: { pageTitle: 'awtarApp.donationsIssued.home.title' },
        loadChildren: () => import('./donations-issued/donations-issued.module').then(m => m.DonationsIssuedModule),
      },
      {
        path: 'donation-details',
        data: { pageTitle: 'awtarApp.donationDetails.home.title' },
        loadChildren: () => import('./donation-details/donation-details.module').then(m => m.DonationDetailsModule),
      },
      {
        path: 'item',
        data: { pageTitle: 'awtarApp.item.home.title' },
        loadChildren: () => import('./item/item.module').then(m => m.ItemModule),
      },
      {
        path: 'beneficiary',
        data: { pageTitle: 'awtarApp.beneficiary.home.title' },
        loadChildren: () => import('./beneficiary/beneficiary.module').then(m => m.BeneficiaryModule),
      },
      {
        path: 'health-status-category',
        data: { pageTitle: 'awtarApp.healthStatusCategory.home.title' },
        loadChildren: () => import('./health-status-category/health-status-category.module').then(m => m.HealthStatusCategoryModule),
      },
      {
        path: 'teaching-curriculum',
        data: { pageTitle: 'awtarApp.teachingCurriculum.home.title' },
        loadChildren: () => import('./teaching-curriculum/teaching-curriculum.module').then(m => m.TeachingCurriculumModule),
      },
      {
        path: 'donations-received',
        data: { pageTitle: 'awtarApp.donationsReceived.home.title' },
        loadChildren: () => import('./donations-received/donations-received.module').then(m => m.DonationsReceivedModule),
      },
      {
        path: 'child',
        data: { pageTitle: 'awtarApp.child.home.title' },
        loadChildren: () => import('./child/child.module').then(m => m.ChildModule),
      },
      {
        path: 'establishment',
        data: { pageTitle: 'awtarApp.establishment.home.title' },
        loadChildren: () => import('./establishment/establishment.module').then(m => m.EstablishmentModule),
      },
      {
        path: 'educational-institution',
        data: { pageTitle: 'awtarApp.educationalInstitution.home.title' },
        loadChildren: () => import('./educational-institution/educational-institution.module').then(m => m.EducationalInstitutionModule),
      },
      {
        path: 'status-of-health',
        data: { pageTitle: 'awtarApp.statusOfHealth.home.title' },
        loadChildren: () => import('./status-of-health/status-of-health.module').then(m => m.StatusOfHealthModule),
      },
      {
        path: 'family',
        data: { pageTitle: 'awtarApp.family.home.title' },
        loadChildren: () => import('./family/family.module').then(m => m.FamilyModule),
      },
      {
        path: 'nature',
        data: { pageTitle: 'awtarApp.nature.home.title' },
        loadChildren: () => import('./nature/nature.module').then(m => m.NatureModule),
      },
      {
        path: 'school-level',
        data: { pageTitle: 'awtarApp.schoolLevel.home.title' },
        loadChildren: () => import('./school-level/school-level.module').then(m => m.SchoolLevelModule),
      },
      {
        path: 'authorizing-officer',
        data: { pageTitle: 'awtarApp.authorizingOfficer.home.title' },
        loadChildren: () => import('./authorizing-officer/authorizing-officer.module').then(m => m.AuthorizingOfficerModule),
      },
      {
        path: 'parent',
        data: { pageTitle: 'awtarApp.parent.home.title' },
        loadChildren: () => import('./parent/parent.module').then(m => m.ParentModule),
      },
      {
        path: 'profile',
        data: { pageTitle: 'awtarApp.profile.home.title' },
        loadChildren: () => import('./profile/profile.module').then(m => m.ProfileModule),
      },
      {
        path: 'report',
        data: { pageTitle: 'awtarApp.report.home.title' },
        loadChildren: () => import('./report/report.module').then(m => m.ReportModule),
      },
      {
        path: 'child-status',
        data: { pageTitle: 'awtarApp.childStatus.home.title' },
        loadChildren: () => import('./child-status/child-status.module').then(m => m.ChildStatusModule),
      },
      {
        path: 'tutor',
        data: { pageTitle: 'awtarApp.tutor.home.title' },
        loadChildren: () => import('./tutor/tutor.module').then(m => m.TutorModule),
      },
      {
        path: 'establishment-type',
        data: { pageTitle: 'awtarApp.establishmentType.home.title' },
        loadChildren: () => import('./establishment-type/establishment-type.module').then(m => m.EstablishmentTypeModule),
      },
      {
        path: 'item-value',
        data: { pageTitle: 'awtarApp.itemValue.home.title' },
        loadChildren: () => import('./item-value/item-value.module').then(m => m.ItemValueModule),
      },
      {
        path: 'city',
        data: { pageTitle: 'awtarApp.city.home.title' },
        loadChildren: () => import('./city/city.module').then(m => m.CityModule),
      },
      {
        path: 'visit',
        data: { pageTitle: 'awtarApp.visit.home.title' },
        loadChildren: () => import('./visit/visit.module').then(m => m.VisitModule),
      },
      {
        path: 'school-level-item',
        data: { pageTitle: 'awtarApp.schoolLevelItem.home.title' },
        loadChildren: () => import('./school-level-item/school-level-item.module').then(m => m.SchoolLevelItemModule),
      },
      {
        path: 'child-status-item',
        data: { pageTitle: 'awtarApp.childStatusItem.home.title' },
        loadChildren: () => import('./child-status-item/child-status-item.module').then(m => m.ChildStatusItemModule),
      },
      {
        path: 'donations-received-item',
        data: { pageTitle: 'awtarApp.donationsReceivedItem.home.title' },
        loadChildren: () => import('./donations-received-item/donations-received-item.module').then(m => m.DonationsReceivedItemModule),
      },
      {
        path: 'composite-item',
        data: { pageTitle: 'awtarApp.compositeItem.home.title' },
        loadChildren: () => import('./composite-item/composite-item.module').then(m => m.CompositeItemModule),
      },
      {
        path: 'donation-item-details',
        data: { pageTitle: 'awtarApp.donationItemDetails.home.title' },
        loadChildren: () => import('./donation-item-details/donation-item-details.module').then(m => m.DonationItemDetailsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
