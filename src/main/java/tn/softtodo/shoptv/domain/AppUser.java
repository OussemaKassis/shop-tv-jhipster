package tn.softtodo.shoptv.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import tn.softtodo.shoptv.domain.enumeration.Role;

/**
 * A AppUser.
 */
@Entity
@Table(name = "app_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "status")
    private String status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "afterEffectsTemplate", "user" }, allowSetters = true)
    private Set<Video> videos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "appUsers", "afterEffectsTemplates" }, allowSetters = true)
    private Company company;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public AppUser avatarUrl(String avatarUrl) {
        this.setAvatarUrl(avatarUrl);
        return this;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getStatus() {
        return this.status;
    }

    public AppUser status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Role getRole() {
        return this.role;
    }

    public AppUser role(Role role) {
        this.setRole(role);
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Video> getVideos() {
        return this.videos;
    }

    public void setVideos(Set<Video> videos) {
        if (this.videos != null) {
            this.videos.forEach(i -> i.setUser(null));
        }
        if (videos != null) {
            videos.forEach(i -> i.setUser(this));
        }
        this.videos = videos;
    }

    public AppUser videos(Set<Video> videos) {
        this.setVideos(videos);
        return this;
    }

    public AppUser addVideo(Video video) {
        this.videos.add(video);
        video.setUser(this);
        return this;
    }

    public AppUser removeVideo(Video video) {
        this.videos.remove(video);
        video.setUser(null);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public AppUser company(Company company) {
        this.setCompany(company);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AppUser user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUser)) {
            return false;
        }
        return id != null && id.equals(((AppUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUser{" +
            "id=" + getId() +
            ", avatarUrl='" + getAvatarUrl() + "'" +
            ", status='" + getStatus() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }
}
