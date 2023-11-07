package it.queryable.myteam.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import it.ness.queryable.annotations.*;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name = "developers")
@QOrderBy("surname asc")
@FilterDef(name = "Developer.obj.uuid", parameters = @ParamDef(name = "uuid", type = String.class))
@Filter(name = "Developer.obj.uuid", condition = "uuid = :uuid")
@FilterDef(name = "Developer.obj.uuids", parameters = @ParamDef(name = "uuids", type = String.class))
@Filter(name = "Developer.obj.uuids", condition = "uuid IN (:uuids)")
@FilterDef(name = "Developer.like.name", parameters = @ParamDef(name = "name", type = String.class))
@Filter(name = "Developer.like.name", condition = "lower(name) LIKE :name")
@FilterDef(name = "Developer.like.surname", parameters = @ParamDef(name = "surname", type = String.class))
@Filter(name = "Developer.like.surname", condition = "lower(surname) LIKE :surname")
@FilterDef(name = "Developer.obj.team_uuids", parameters = @ParamDef(name = "team_uuids", type = String.class))
@Filter(name = "Developer.obj.team_uuids", condition = "team_uuid IN (:team_uuids)")
@FilterDef(name = "Developer.obj.active", parameters = @ParamDef(name = "active", type = Boolean.class))
@Filter(name = "Developer.obj.active", condition = "active = :active")
public class Developer extends PanacheEntityBase {

	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "uuid", unique = true)
	@Id
	@Q
	@QList
	public String uuid;

	@QLike
	public String name;

	@QLike
	public String surname;

	@QList
	public String team_uuid;

	@QLogicalDelete
	boolean active = true;
}