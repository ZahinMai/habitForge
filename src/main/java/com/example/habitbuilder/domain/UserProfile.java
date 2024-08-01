package com.example.habitbuilder.domain;

import com.example.habitbuilder.domain.enumeration.ActivityLevel;
import com.example.habitbuilder.domain.enumeration.Diet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "user_profile")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "user_streak", nullable = false)
    private Integer userStreak;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_level")
    private ActivityLevel activityLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "diet")
    private Diet diet;

    @Column(name = "cooks")
    private Boolean cooks;

    @Column(name = "drives")
    private Boolean drives;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "userProfile")
    @JsonIgnoreProperties(value = { "userProfile" }, allowSetters = true)
    private Set<Task> tasks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserProfile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public UserProfile firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public UserProfile lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getUserStreak() {
        return this.userStreak;
    }

    public UserProfile userStreak(Integer userStreak) {
        this.setUserStreak(userStreak);
        return this;
    }

    public void setUserStreak(Integer userStreak) {
        this.userStreak = userStreak;
    }

    public ActivityLevel getActivityLevel() {
        return this.activityLevel;
    }

    public UserProfile activityLevel(ActivityLevel activityLevel) {
        this.setActivityLevel(activityLevel);
        return this;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    public Diet getDiet() {
        return this.diet;
    }

    public UserProfile diet(Diet diet) {
        this.setDiet(diet);
        return this;
    }

    public void setDiet(Diet diet) {
        this.diet = diet;
    }

    public Boolean getCooks() {
        return this.cooks;
    }

    public UserProfile cooks(Boolean cooks) {
        this.setCooks(cooks);
        return this;
    }

    public void setCooks(Boolean cooks) {
        this.cooks = cooks;
    }

    public Boolean getDrives() {
        return this.drives;
    }

    public UserProfile drives(Boolean drives) {
        this.setDrives(drives);
        return this;
    }

    public void setDrives(Boolean drives) {
        this.drives = drives;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserProfile user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(Set<Task> tasks) {
        if (this.tasks != null) {
            this.tasks.forEach(i -> i.setUserProfile(null));
        }
        if (tasks != null) {
            tasks.forEach(i -> i.setUserProfile(this));
        }
        this.tasks = tasks;
    }

    public UserProfile tasks(Set<Task> tasks) {
        this.setTasks(tasks);
        return this;
    }

    public UserProfile addTasks(Task task) {
        this.tasks.add(task);
        task.setUserProfile(this);
        return this;
    }

    public UserProfile removeTasks(Task task) {
        this.tasks.remove(task);
        task.setUserProfile(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProfile)) {
            return false;
        }
        return id != null && id.equals(((UserProfile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", userStreak=" + getUserStreak() +
            ", activityLevel='" + getActivityLevel() + "'" +
            ", diet='" + getDiet() + "'" +
            ", cooks='" + getCooks() + "'" +
            ", drives='" + getDrives() + "'" +
            "}";
    }
}
