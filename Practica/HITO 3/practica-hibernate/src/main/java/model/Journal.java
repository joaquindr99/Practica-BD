package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "journal")
public class Journal {

    @Id
    @GeneratedValue
    @Column(name = "journal_id")
    private Long journal_id;

    @Column(name = "journal_name", nullable = false)
    private String journal_name;

    @Column(name = "issn", unique = true)
    private String issn;

    @Column(name = "jif")
    private Double jif;

    @Column(name = "jif_quartile")
    private String jif_quartile;

    @OneToMany(mappedBy = "journal")
    private Set<Article> articles;

    public Journal() {

    }

    public Journal (Long journal_id, String journal_name, String issn, Double jif, String jif_quartile) {
        this.journal_id = journal_id;
        this.journal_name = journal_name;
        this.issn = issn;
        this.jif = jif;
        this.jif_quartile = jif_quartile;
        this.articles = new HashSet<>();
    }

    public Long getJournal_id() {
        return journal_id;
    }

    public String getJournal_name() {
        return journal_name;
    }

    public String getIssn() {
        return issn;
    }

    public Double getJif() {
        return jif;
    }

    public String getJif_quartile() {
        return jif_quartile;
    }

    public Set<Article> getArticles() {
        return articles;
    }
}
