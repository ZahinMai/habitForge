import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'user-profile',
        data: { pageTitle: 'habitForgeApp.userProfile.home.title' },
        loadChildren: () => import('./user-profile/user-profile.module').then(m => m.UserProfileModule),
      },
      {
        path: 'task',
        data: { pageTitle: 'habitForgeApp.task.home.title' },
        loadChildren: () => import('./task/task.module').then(m => m.TaskModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
