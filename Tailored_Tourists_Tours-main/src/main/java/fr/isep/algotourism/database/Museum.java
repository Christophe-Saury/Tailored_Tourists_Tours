package fr.isep.algotourism.database;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "musees")
@Getter
@Setter
public class Museum implements Location {

    @Id
    int id;
    @Column(columnDefinition = "TEXT")
    String departement;
    int code_postal;
    String region_administrative;
    String identifiant_museofile;
    String commune;
    String nom_officiel_du_musee;
    String adresse;
    String lieu;
    String url;

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
        return nom_officiel_du_musee;
    }

    @Override
    public String getPeriod() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }


    @Override
    public String toString() {
        return "Museum{" +
                "id=" + id +
                ", departement='" + departement + '\'' +
                ", code_postal=" + code_postal +
                ", region_administrative='" + region_administrative + '\'' +
                ", identifiant_museofile='" + identifiant_museofile + '\'' +
                ", commune='" + commune + '\'' +
                ", nom_officiel_du_musee='" + nom_officiel_du_musee + '\'' +
                ", adresse='" + adresse + '\'' +
                ", lieu='" + lieu + '\'' +
                ", url='" + url + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", pos_x=" + pos_x +
                ", pos_y=" + pos_y +
                ", dist=" + dist +
                '}';
    }
}
