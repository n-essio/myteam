package it.queryable.myteam.service.rs;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import it.queryable.api.service.RsRepositoryServiceV3;
import it.queryable.myteam.model.Team;

import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/api/v1/null")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class TeamServiceRs extends RsRepositoryServiceV3<Team, String> {

	public TeamServiceRs() {
		super(Team.class);
	}

	protected String getId(Team object) {
		// field with @id
		return object.uuid;
	}

	@Override
	protected String getDefaultOrderBy() {
		return "name asc";
	}

	@Override
	public PanacheQuery<Team> getSearch(String orderBy) throws Exception {
		String query = null;
		Map<String, Object> params = null;
		if (nn("like.tagses")) {
			String[] tagses = get("like.tagses").split(",");
			StringBuilder sb = new StringBuilder();
			if (null == params) {
				params = new HashMap<>();
			}
			for (int i = 0; i < tagses.length; i++) {
				final String paramName = String.format("tags%d", i);
				sb.append(String.format("tags LIKE :%s", paramName));
				params.put(paramName, "%" + tagses[i] + "%");
				if (i < tagses.length - 1) {
					sb.append(" OR ");
				}
			}
			if (null == query) {
				query = sb.toString();
			} else {
				query = query + " OR " + sb.toString();
			}
		}
		PanacheQuery<Team> search;
		Sort sort = sort(orderBy);
		if (sort != null) {
			search = Team.find(query, sort, params);
		} else {
			search = Team.find(query, params);
		}
		if (nn("obj.uuid")) {
			search.filter("Team.obj.uuid", Parameters.with("uuid", get("obj.uuid")));
		}
		if (nn("obj.uuids")) {
			search.filter("Team.obj.uuids", Parameters.with("uuids", asList("obj.uuids")));
		}
		if (nn("like.name")) {
			search.filter("Team.like.name", Parameters.with("name", likeParamToLowerCase("like.name")));
		}
		return search;
	}
}