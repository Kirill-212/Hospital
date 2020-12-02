package by.bolvako.Hospital.model;

import javax.persistence.*;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne( cascade = CascadeType.ALL, orphanRemoval =
            true)
    private User user;
    @Column(name = "Homeadress")
    private String Homeadress;

    @Column(name = "Passport")
    private String Passport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHomeadress() {
        return Homeadress;
    }

    public void setHomeadress(String homeadress) {
        Homeadress = homeadress;
    }

    public String getPassport() {
        return Passport;
    }

    public void setPassport(String passport) {
        Passport = passport;
    }
}
