package it.queryable.myteam.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import it.ness.queryable.annotations.*;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.List;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

// - Project (uuid, name, budget, developers_uuid)

@Entity
@Table(name = "projects")
@QOrderBy("name asc")
@FilterDef(name = "Project.obj.uuid", parameters = @ParamDef(name = "uuid", type = String.class))
@Filter(name = "Project.obj.uuid", condition = "uuid = :uuid")
@FilterDef(name = "Project.obj.uuids", parameters = @ParamDef(name = "uuids", type = String.class))
@Filter(name = "Project.obj.uuids", condition = "uuid IN (:uuids)")
@FilterDef(name = "Project.like.name", parameters = @ParamDef(name = "name", type = String.class))
@Filter(name = "Project.like.name", condition = "lower(name) LIKE :name")
@FilterDef(name = "Project.obj.budget", parameters = @ParamDef(name = "budget", type = java.math.BigDecimal.class))
@Filter(name = "Project.obj.budget", condition = "budget = :budget")
public class Project extends PanacheEntityBase {

	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "uuid", unique = true)
	@Id
	@Q
	@QList
	public String uuid;

	@QLike
	public String name;

	@Q
	public BigDecimal budget;

	@ElementCollection(fetch = FetchType.LAZY)
	public List<String> developers_uuid;

}