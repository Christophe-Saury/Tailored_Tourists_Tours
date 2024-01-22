package fr.isep.algotourism.database;


public interface Location {

    int getId();

    double getLatitude();

    double getLongitude();

    long getPos_x();

    String getCommune();

    String getAdresse();

    String getName();

    String getPeriod();

    String getDepartement();

    String getDescription();

    long getPos_y();

    double getDist();
}
