package fr.isep.algotourism.database;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "immeubles")
@Getter
@Setter
public class Buildings implements Location {

    @Id
    int id;

    String commune;
    Integer code_departement;
    Date dmaj;

    Integer insee;
    String region;
    String departement;
    String appellation_courante;
    String siecle;
    String auteur;
    String statut;

    @Column(columnDefinition = "TEXT")
    String description;
    @Column(columnDefinition = "TEXT")
    String historique;
    String adresse;
    String reference;
    String contact;

    @Column(columnDefinition = "DECIMAL(12, 10)")
    double latitude;
    @Column(columnDefinition = "DECIMAL(12, 10)")
    double longitude;

    long pos_x;
    long pos_y;

    @Column(columnDefinition = "DECIMAL(12, 10)")
    double dist;

    @Override
    public String getName() {
        return appellation_courante;
    }

    @Override
    public String getPeriod() {
        return siecle;
    }

    @Override
    public String toString() {
        return "Buildings{" +
                "id=" + id +
                ", commune='" + commune + '\'' +
                ", code_departement=" + code_departement +
                ", dmaj=" + dmaj +
                ", insee=" + insee +
                ", region='" + region + '\'' +
                ", departement='" + departement + '\'' +
                ", appellation_courante='" + appellation_courante + '\'' +
                ", siecle='" + siecle + '\'' +
                ", auteur='" + auteur + '\'' +
                ", statut='" + statut + '\'' +
                ", description='" + description + '\'' +
                ", historique='" + historique + '\'' +
                ", adresse='" + adresse + '\'' +
                ", reference='" + reference + '\'' +
                ", contact='" + contact + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", pos_x=" + pos_x +
                ", pos_y=" + pos_y +
                ", dist=" + dist +
                '}';
    }
}
