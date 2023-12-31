package it.queryable.myteam.service.rs;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import it.queryable.api.service.RsRepositoryServiceV3;
import it.queryable.myteam.model.Project;

import it.queryable.myteam.service.exception.ExceptionBundle;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Path("/api/v1/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class ProjectServiceRs extends RsRepositoryServiceV3<Project, String> {

	@Inject
	ExceptionBundle exceptionBundle;

	public ProjectServiceRs() {
		super(Project.class);
	}

	protected String getId(Project object) {
		// field with @id
		return object.uuid;
	}

	@Override
	protected String getDefaultOrderBy() {
		return "name asc";
	}

	@Override
	public PanacheQuery<Project> getSearch(String orderBy) throws Exception {
		PanacheQuery<Project> search;
		Sort sort = sort(orderBy);
		if (sort != null) {
			search = Project.find(null, sort);
		} else {
			search = Project.find(null);
		}
		if (nn("obj.uuid")) {
			search.filter("Project.obj.uuid", Parameters.with("uuid", get("obj.uuid")));
		}
		if (nn("obj.uuids")) {
			search.filter("Project.obj.uuids", Parameters.with("uuids", asList("obj.uuids")));
		}
		if (nn("like.name")) {
			search.filter("Project.like.name", Parameters.with("name", likeParamToLowerCase("like.name")));
		}
		if (nn("obj.budget")) {
			BigDecimal numberof = new BigDecimal(get("obj.budget"));
			search.filter("Project.obj.budget", Parameters.with("budget", numberof));
		}
		return search;
	}

	@Override
	protected void prePersist(Project project) throws Exception {
		validateNameUnique(project);
	}

	@Override
	protected Project preUpdate(Project project) throws Exception {
		validateNameUnique(project);
		return project;
	}

	protected void validateNameUnique(Project project) throws Exception {
		if (project.name == null) {
			String msg_105 = "ERROR: name is null";
			logger.error(msg_105);
			throw exceptionBundle.msg_105();
		}

		long c = 0l;

		if (project.uuid == null) {
			c = Project.count("name = :name", Parameters.with("name", project.name));
		} else {
			c = Project.count("name = :name AND uuid != :uuid",
					Parameters.with("name", project.name).and("uuid", project.uuid));
		}
		if (c > 0) {
			String msg_106 = String.format("ERROR: project name should be unique, given %s", project.name);
			logger.error(msg_106);
			throw exceptionBundle.msg_106(project.name);
		}
	}

}