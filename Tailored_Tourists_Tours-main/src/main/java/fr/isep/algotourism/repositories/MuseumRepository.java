package fr.isep.algotourism.repositories;

import fr.isep.algotourism.database.Museum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Repository
public interface MuseumRepository extends CrudRepository<Museum, Long> {

    @Query(value = """
            SELECT m.*, SQRT(POW(:ox - pos_x, 2) + POW(:ox - pos_y, 2)) as dist FROM musees m WHERE
            pos_x BETWEEN :ox - :range AND :ox + :range AND
            pos_y BETWEEN :oy - :range AND :oy + :range
            AND id NOT IN :excluded_ids
            ORDER BY POW(:ox - pos_x, 2) + POW(:oy - pos_y, 2)
            LIMIT 1""", nativeQuery = true)
    public Museum findFirstWithinRange(@Param("ox") int ox, @Param("oy") int oy, @Param("range") int range, @Param("excluded_ids") @NotEmpty List<Integer> excluded_ids);


    @Query(value = """
            SELECT m.*, SQRT(POW(:ox - pos_x, 2) + POW(:oy - pos_y, 2)) as dist FROM musees m WHERE
            pos_x BETWEEN :ox - :range / 2 AND :ox + :range / 2 AND
            pos_y BETWEEN :oy AND :oy + :range
            AND id NOT IN :excluded_ids
            ORDER BY POW(:ox - pos_x, 2) + POW(:oy - pos_y, 2)
            LIMIT 1""", nativeQuery = true)
    public Museum findNearestNorth(@Param("ox") int ox, @Param("oy") int oy, @Param("range") int range, @Param("excluded_ids") @NotEmpty List<Integer> excluded_ids);

    @Query(value = """
            SELECT m.*, SQRT(POW(:ox - pos_x, 2) + POW(:oy - pos_y, 2)) as dist FROM musees m WHERE
            pos_x BETWEEN :ox AND :ox + :range AND
            pos_y BETWEEN :oy - :range / 2 AND :oy + :range / 2
            AND id NOT IN :excluded_ids
            ORDER BY POW(:ox - pos_x, 2) + POW(:oy - pos_y, 2)
            LIMIT 1""", nativeQuery = true)
    public Museum findNearestEast(@Param("ox") int ox, @Param("oy") int oy, @Param("range") int range, @Param("excluded_ids") @NotEmpty List<Integer> excluded_ids);


    @Query(value = """
            SELECT m.*, SQRT(POW(:ox - pos_x, 2) + POW(:oy - pos_y, 2)) as dist FROM musees m WHERE
            pos_x BETWEEN :ox - :range AND :ox AND
            pos_y BETWEEN :oy - :range / 2 AND :oy + :range / 2
            AND id NOT IN :excluded_ids
            ORDER BY POW(:ox - pos_x, 2) + POW(:oy - pos_y, 2)
            LIMIT 1""", nativeQuery = true)
    public Museum findNearestWest(@Param("ox") int ox, @Param("oy") int oy, @Param("range") int range, @Param("excluded_ids") @NotEmpty List<Integer> excluded_ids);


    @Query(value = """
            SELECT m.*, SQRT(POW(:ox - pos_x, 2) + POW(:oy - pos_y, 2)) as dist FROM musees m WHERE
            pos_x BETWEEN :ox - :range / 2 AND :ox + :range / 2 AND
            pos_y BETWEEN :oy - :range AND :oy
            AND id NOT IN :excluded_ids
            ORDER BY POW(:ox - pos_x, 2) + POW(:oy - pos_y, 2) ASC
            LIMIT 1""", nativeQuery = true)
    public Museum findNearestSouth(@Param("ox") int ox, @Param("oy") int oy, @Param("range") int range, @Param("excluded_ids") @NotEmpty List<Integer> excluded_ids);


    @Query(value = """
            WITH nearestInsertion as ((SELECT m.*, SQRT(POW(:ex - pos_x, 2) + POW(:ey - pos_y, 2)) as dist
                                       FROM musees m
                                       WHERE pos_x BETWEEN LEAST(:ox, :ex) AND GREATEST(:ox, :ex)
                                         AND pos_y BETWEEN LEAST(:oy, :ey) AND GREATEST(:oy, :ey)
                                         AND pos_x != :ox
                                         AND pos_y != :oy
                                         AND pos_x != :ex
                                         AND pos_y != :ey
                                         AND id NOT IN :excluded_ids
                                       ORDER BY POW(:ex - pos_x, 2) + POW(:ey - pos_y, 2)
                                       LIMIT 1)
                                      UNION ALL
                                      (SELECT m.*, SQRT(POW(:ox - pos_x, 2) + POW(:oy - pos_y, 2)) as dist
                                       FROM musees m
                                       WHERE pos_x BETWEEN LEAST(:ox, :ex) AND GREATEST(:ox, :ex)
                                         AND pos_y BETWEEN LEAST(:oy, :ey) AND GREATEST(:oy, :ey)
                                         AND pos_x != :ox
                                         AND pos_y != :oy
                                         AND pos_x != :ex
                                         AND pos_y != :ey
                                         AND id NOT IN :excluded_ids
                                       ORDER BY POW(:ox - pos_x, 2) + POW(:oy - pos_y, 2)
                                       LIMIT 1))
            SELECT *
            FROM nearestInsertion
            ORDER BY dist
            LIMIT 1;""", nativeQuery = true)
    public Museum findNearestInsertion(
            @Param("ox") long ox,
            @Param("oy") long oy,
            @Param("ex") long ex,
            @Param("ey") long ey,
            @Param("excluded_ids") @NotEmpty List<Integer> excluded_ids
    );


}
