package it.queryable.myteam.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import it.ness.queryable.annotations.*;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name = "teamspro")
@QOrderBy("name asc")
@FilterDef(name = "TeamPro.like.tagses", parameters = @ParamDef(name = "tagses", type = String.class))
@Filter(name = "TeamPro.like.tagses", condition = "lower(tagses) LIKE :tagses")
@FilterDef(name = "TeamPro.obj.uuid", parameters = @ParamDef(name = "uuid", type = String.class))
@Filter(name = "TeamPro.obj.uuid", condition = "uuid = :uuid")
@FilterDef(name = "TeamPro.obj.uuids", parameters = @ParamDef(name = "uuids", type = String.class))
@Filter(name = "TeamPro.obj.uuids", condition = "uuid IN (:uuids)")
@FilterDef(name = "TeamPro.like.name", parameters = @ParamDef(name = "name", type = String.class))
@Filter(name = "TeamPro.like.name", condition = "lower(name) LIKE :name")
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
