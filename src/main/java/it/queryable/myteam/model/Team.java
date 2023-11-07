package it.queryable.myteam.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import it.ness.queryable.annotations.*;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name = "teams")
@QOrderBy("name asc")
@FilterDef(name = "Team.like.tagses", parameters = @ParamDef(name = "tagses", type = String.class))
@Filter(name = "Team.like.tagses", condition = "lower(tagses) LIKE :tagses")
@FilterDef(name = "Team.obj.uuid", parameters = @ParamDef(name = "uuid", type = String.class))
@Filter(name = "Team.obj.uuid", condition = "uuid = :uuid")
@FilterDef(name = "Team.obj.uuids", parameters = @ParamDef(name = "uuids", type = String.class))
@Filter(name = "Team.obj.uuids", condition = "uuid IN (:uuids)")
@FilterDef(name = "Team.like.name", parameters = @ParamDef(name = "name", type = String.class))
@Filter(name = "Team.like.name", condition = "lower(name) LIKE :name")
public class Team extends PanacheEntityBase {

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