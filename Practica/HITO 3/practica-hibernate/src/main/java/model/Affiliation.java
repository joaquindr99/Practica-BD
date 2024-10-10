package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "affiliation")
public class Affiliation {

    @Id
    @GeneratedValue
    @Column(name = "affiliation_id")
    private Long affiliation_id;

    @Column(name = "affiliation_name", nullable = false)
    private String affiliation_name;

    @Column(name = "city")
    private String city;
    
    @Column(name = "country_name")
    private String country_name;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "author_affiliation",
            joinColumns = @JoinColumn(name = "affiliation_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;

    public Affiliation() {

    }

    public Affiliation(String affiliation_name, String city, String country_name) {
        this.affiliation_name = affiliation_name;
        this.city = city;
        this.country_name = country_name;
        this.authors = new HashSet<>();
    }

    public Long getAffiliation_id() {
        return affiliation_id;
    }

    public String getAffiliation_name() {
        return affiliation_name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry_name() {
        return country_name;
    }

    public Set<Author> getAuthors() {
        return authors;
    }
}
