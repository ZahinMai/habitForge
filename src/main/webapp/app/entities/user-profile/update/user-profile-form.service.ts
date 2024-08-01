import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IUserProfile, NewUserProfile } from '../user-profile.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUserProfile for edit and NewUserProfileFormGroupInput for create.
 */
type UserProfileFormGroupInput = IUserProfile | PartialWithRequiredKeyOf<NewUserProfile>;

type UserProfileFormDefaults = Pick<NewUserProfile, 'id' | 'cooks' | 'drives'>;

type UserProfileFormGroupContent = {
  id: FormControl<IUserProfile['id'] | NewUserProfile['id']>;
  firstName: FormControl<IUserProfile['firstName']>;
  lastName: FormControl<IUserProfile['lastName']>;
  userStreak: FormControl<IUserProfile['userStreak']>;
  activityLevel: FormControl<IUserProfile['activityLevel']>;
  diet: FormControl<IUserProfile['diet']>;
  cooks: FormControl<IUserProfile['cooks']>;
  drives: FormControl<IUserProfile['drives']>;
  user: FormControl<IUserProfile['user']>;
};

export type UserProfileFormGroup = FormGroup<UserProfileFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UserProfileFormService {
  createUserProfileFormGroup(userProfile: UserProfileFormGroupInput = { id: null }): UserProfileFormGroup {
    const userProfileRawValue = {
      ...this.getFormDefaults(),
      ...userProfile,
    };
    return new FormGroup<UserProfileFormGroupContent>({
      id: new FormControl(
        { value: userProfileRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      firstName: new FormControl(userProfileRawValue.firstName),
      lastName: new FormControl(userProfileRawValue.lastName),
      userStreak: new FormControl(userProfileRawValue.userStreak, {
        validators: [Validators.required],
      }),
      activityLevel: new FormControl(userProfileRawValue.activityLevel),
      diet: new FormControl(userProfileRawValue.diet),
      cooks: new FormControl(userProfileRawValue.cooks),
      drives: new FormControl(userProfileRawValue.drives),
      user: new FormControl(userProfileRawValue.user),
    });
  }

  getUserProfile(form: UserProfileFormGroup): IUserProfile | NewUserProfile {
    return form.getRawValue() as IUserProfile | NewUserProfile;
  }

  resetForm(form: UserProfileFormGroup, userProfile: UserProfileFormGroupInput): void {
    const userProfileRawValue = { ...this.getFormDefaults(), ...userProfile };
    form.reset(
      {
        ...userProfileRawValue,
        id: { value: userProfileRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): UserProfileFormDefaults {
    return {
      id: null,
      cooks: false,
      drives: false,
    };
  }
}
