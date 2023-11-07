package it.queryable.myteam.service.rs;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import it.queryable.api.service.RsRepositoryServiceV3;
import it.queryable.myteam.model.Developer;

import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/v1/developers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class DeveloperServiceRs extends RsRepositoryServiceV3<Developer, String> {

	public DeveloperServiceRs() {
		super(Developer.class);
	}

	protected String getId(Developer object) {
		// field with @id
		return object.uuid;
	}

	@Override
	protected String getDefaultOrderBy() {
		return "surname asc";
	}

	@Override
	public PanacheQuery<Developer> getSearch(String orderBy) throws Exception {
		PanacheQuery<Developer> search;
		Sort sort = sort(orderBy);
		if (sort != null) {
			search = Developer.find(null, sort);
		} else {
			search = Developer.find(null);
		}
		if (nn("obj.uuid")) {
			search.filter("Developer.obj.uuid", Parameters.with("uuid", get("obj.uuid")));
		}
		if (nn("obj.uuids")) {
			search.filter("Developer.obj.uuids", Parameters.with("uuids", asList("obj.uuids")));
		}
		if (nn("like.name")) {
			search.filter("Developer.like.name", Parameters.with("name", likeParamToLowerCase("like.name")));
		}
		if (nn("like.surname")) {
			search.filter("Developer.like.surname", Parameters.with("surname", likeParamToLowerCase("like.surname")));
		}
		if (nn("obj.team_uuids")) {
			search.filter("Developer.obj.team_uuids", Parameters.with("team_uuids", asList("obj.team_uuids")));
		}
		if (nn("obj.active")) {
			Boolean valueof = _boolean("obj.active");
			search.filter("Developer.obj.active", Parameters.with("active", valueof));
		} else {
			search.filter("Developer.obj.active", Parameters.with("active", true));
		}
		return search;
	}
}