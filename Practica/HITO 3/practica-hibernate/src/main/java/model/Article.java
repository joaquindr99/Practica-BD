package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "article")
public class Article {

    @Id
    @Column(name = "doi", unique = true, nullable = false)
    private String doi;

    @Column(name = "title")
    private String title;

    @Column(name = "publication_date")
    private Integer publication_date;

    @Column(name = "url")
    private String url;

    @Column(name = "num_citations")
    private Integer num_citations;

    @ManyToOne
    @JoinColumn(name = "journal_id")
    private Journal journal;

    @ManyToMany
    @JoinTable(
            name = "article_author",
            joinColumns = @JoinColumn(name = "doi", referencedColumnName = "doi"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "author_id")
    )
    private Set<Author> authors;

    public Article() {

    }

    public Article(String doi, String title, Integer publication_date, String url, Integer num_citations, Journal journal) {
        this.doi = doi;
        this.title = title;
        this.publication_date = publication_date;
        this.url = url;
        this.num_citations = num_citations;
        this.journal = journal;
        this.authors = new HashSet<>();
    }

    public String getDoi() {
        return doi;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPublication_date() {
        return publication_date;
    }

    public String getUrl() {
        return url;
    }

    public Integer getNum_citations() {
        return num_citations;
    }

    public Journal getJournal() {
        return journal;
    }

    public Set<Author> getAuthors() {
        return authors;
    }
}
