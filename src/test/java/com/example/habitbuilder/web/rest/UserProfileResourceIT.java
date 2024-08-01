package com.example.habitbuilder.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.habitbuilder.IntegrationTest;
import com.example.habitbuilder.domain.UserProfile;
import com.example.habitbuilder.domain.enumeration.ActivityLevel;
import com.example.habitbuilder.domain.enumeration.Diet;
import com.example.habitbuilder.repository.UserProfileRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserProfileResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UserProfileResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_USER_STREAK = 1;
    private static final Integer UPDATED_USER_STREAK = 2;

    private static final ActivityLevel DEFAULT_ACTIVITY_LEVEL = ActivityLevel.LOW;
    private static final ActivityLevel UPDATED_ACTIVITY_LEVEL = ActivityLevel.MEDIUM;

    private static final Diet DEFAULT_DIET = Diet.VEGAN;
    private static final Diet UPDATED_DIET = Diet.VEGETARIAN;

    private static final Boolean DEFAULT_COOKS = false;
    private static final Boolean UPDATED_COOKS = true;

    private static final Boolean DEFAULT_DRIVES = false;
    private static final Boolean UPDATED_DRIVES = true;

    private static final String ENTITY_API_URL = "/api/user-profiles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserProfileRepository userProfileRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserProfileMockMvc;

    private UserProfile userProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .userStreak(DEFAULT_USER_STREAK)
            .activityLevel(DEFAULT_ACTIVITY_LEVEL)
            .diet(DEFAULT_DIET)
            .cooks(DEFAULT_COOKS)
            .drives(DEFAULT_DRIVES);
        return userProfile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createUpdatedEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .userStreak(UPDATED_USER_STREAK)
            .activityLevel(UPDATED_ACTIVITY_LEVEL)
            .diet(UPDATED_DIET)
            .cooks(UPDATED_COOKS)
            .drives(UPDATED_DRIVES);
        return userProfile;
    }

    @BeforeEach
    public void initTest() {
        userProfile = createEntity(em);
    }

    @Test
    @Transactional
    void createUserProfile() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();
        // Create the UserProfile
        restUserProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userProfile)))
            .andExpect(status().isCreated());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate + 1);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUserProfile.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUserProfile.getUserStreak()).isEqualTo(DEFAULT_USER_STREAK);
        assertThat(testUserProfile.getActivityLevel()).isEqualTo(DEFAULT_ACTIVITY_LEVEL);
        assertThat(testUserProfile.getDiet()).isEqualTo(DEFAULT_DIET);
        assertThat(testUserProfile.getCooks()).isEqualTo(DEFAULT_COOKS);
        assertThat(testUserProfile.getDrives()).isEqualTo(DEFAULT_DRIVES);
    }

    @Test
    @Transactional
    void createUserProfileWithExistingId() throws Exception {
        // Create the UserProfile with an existing ID
        userProfile.setId(1L);

        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userProfile)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUserStreakIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setUserStreak(null);

        // Create the UserProfile, which fails.

        restUserProfileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userProfile)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserProfiles() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList
        restUserProfileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].userStreak").value(hasItem(DEFAULT_USER_STREAK)))
            .andExpect(jsonPath("$.[*].activityLevel").value(hasItem(DEFAULT_ACTIVITY_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].diet").value(hasItem(DEFAULT_DIET.toString())))
            .andExpect(jsonPath("$.[*].cooks").value(hasItem(DEFAULT_COOKS.booleanValue())))
            .andExpect(jsonPath("$.[*].drives").value(hasItem(DEFAULT_DRIVES.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserProfilesWithEagerRelationshipsIsEnabled() throws Exception {
        when(userProfileRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserProfileMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(userProfileRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUserProfilesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userProfileRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserProfileMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(userProfileRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get the userProfile
        restUserProfileMockMvc
            .perform(get(ENTITY_API_URL_ID, userProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userProfile.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.userStreak").value(DEFAULT_USER_STREAK))
            .andExpect(jsonPath("$.activityLevel").value(DEFAULT_ACTIVITY_LEVEL.toString()))
            .andExpect(jsonPath("$.diet").value(DEFAULT_DIET.toString()))
            .andExpect(jsonPath("$.cooks").value(DEFAULT_COOKS.booleanValue()))
            .andExpect(jsonPath("$.drives").value(DEFAULT_DRIVES.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserProfile() throws Exception {
        // Get the userProfile
        restUserProfileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Update the userProfile
        UserProfile updatedUserProfile = userProfileRepository.findById(userProfile.getId()).get();
        // Disconnect from session so that the updates on updatedUserProfile are not directly saved in db
        em.detach(updatedUserProfile);
        updatedUserProfile
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .userStreak(UPDATED_USER_STREAK)
            .activityLevel(UPDATED_ACTIVITY_LEVEL)
            .diet(UPDATED_DIET)
            .cooks(UPDATED_COOKS)
            .drives(UPDATED_DRIVES);

        restUserProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserProfile))
            )
            .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUserProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUserProfile.getUserStreak()).isEqualTo(UPDATED_USER_STREAK);
        assertThat(testUserProfile.getActivityLevel()).isEqualTo(UPDATED_ACTIVITY_LEVEL);
        assertThat(testUserProfile.getDiet()).isEqualTo(UPDATED_DIET);
        assertThat(testUserProfile.getCooks()).isEqualTo(UPDATED_COOKS);
        assertThat(testUserProfile.getDrives()).isEqualTo(UPDATED_DRIVES);
    }

    @Test
    @Transactional
    void putNonExistingUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();
        userProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userProfile.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();
        userProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserProfileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();
        userProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserProfileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userProfile)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserProfileWithPatch() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Update the userProfile using partial update
        UserProfile partialUpdatedUserProfile = new UserProfile();
        partialUpdatedUserProfile.setId(userProfile.getId());

        partialUpdatedUserProfile.lastName(UPDATED_LAST_NAME).userStreak(UPDATED_USER_STREAK).diet(UPDATED_DIET);

        restUserProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserProfile))
            )
            .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUserProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUserProfile.getUserStreak()).isEqualTo(UPDATED_USER_STREAK);
        assertThat(testUserProfile.getActivityLevel()).isEqualTo(DEFAULT_ACTIVITY_LEVEL);
        assertThat(testUserProfile.getDiet()).isEqualTo(UPDATED_DIET);
        assertThat(testUserProfile.getCooks()).isEqualTo(DEFAULT_COOKS);
        assertThat(testUserProfile.getDrives()).isEqualTo(DEFAULT_DRIVES);
    }

    @Test
    @Transactional
    void fullUpdateUserProfileWithPatch() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Update the userProfile using partial update
        UserProfile partialUpdatedUserProfile = new UserProfile();
        partialUpdatedUserProfile.setId(userProfile.getId());

        partialUpdatedUserProfile
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .userStreak(UPDATED_USER_STREAK)
            .activityLevel(UPDATED_ACTIVITY_LEVEL)
            .diet(UPDATED_DIET)
            .cooks(UPDATED_COOKS)
            .drives(UPDATED_DRIVES);

        restUserProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserProfile))
            )
            .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUserProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUserProfile.getUserStreak()).isEqualTo(UPDATED_USER_STREAK);
        assertThat(testUserProfile.getActivityLevel()).isEqualTo(UPDATED_ACTIVITY_LEVEL);
        assertThat(testUserProfile.getDiet()).isEqualTo(UPDATED_DIET);
        assertThat(testUserProfile.getCooks()).isEqualTo(UPDATED_COOKS);
        assertThat(testUserProfile.getDrives()).isEqualTo(UPDATED_DRIVES);
    }

    @Test
    @Transactional
    void patchNonExistingUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();
        userProfile.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userProfile.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();
        userProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserProfileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userProfile))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();
        userProfile.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserProfileMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userProfile))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeDelete = userProfileRepository.findAll().size();

        // Delete the userProfile
        restUserProfileMockMvc
            .perform(delete(ENTITY_API_URL_ID, userProfile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
