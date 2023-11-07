package it.queryable.myteam.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import it.ness.queryable.annotations.*;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "teamspro")
@QOrderBy("name asc")
public class TeamPro extends PanacheEntityBase {

    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "uuid", unique = true)
    @Id
    @Q
    @QList
    public String uuid;

    @QLike
    public String name;

    @QLikeList
    public String tags;
}
