package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue
    @Column(name = "author_id")
    private Long author_id;

    @Column(name = "author_name", nullable = false)
    private String author_name;

    @Column(name = "importance")
    private Double importance;


    @ManyToMany(mappedBy = "authors")
    private Set<Affiliation> affiliations;

    @ManyToMany(mappedBy = "authors")
    private Set<Article> articles;

    public Author() {

    }

    public Author(String author_name, double importance) {
        this.author_name = author_name;
        this.importance = importance;
        this.articles = new HashSet<>();
        this.affiliations = new HashSet<>();
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public Double getImportance() {
        return importance;
    }

    public Set<Affiliation> getAffiliations () {
        return this.affiliations;
    }

    public Set<Article> getArticles () {
        return this.articles;
    }
}
