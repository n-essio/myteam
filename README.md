# myteam

This project uses Quarkus and Queryable Queex.

Following this tutorial:
https://github.com/n-essio/queryable-tutorial

To install queryable:
```
./mvnw it.n-ess.queryable:queryable-maven-plugin:3.0.3:add
./mvnw queryable:install
./mvnw queryable:source

To install qeex api/to generate implementation of created interfaces:
```
./mvnw queryable:qeexinstall
./mvnw queryable:qeexsource
```

We will define our exception typed:
https://github.com/n-essio/myteam/blob/main/src/main/java/it/queryable/myteam/service/exception/ExceptionBundle.java

Pay attention to have:
```
@QeexExceptionBundle(project = "FLW", language = "it") // FLW is our short project name and we can define the default language
public interface ExceptionBundle {

@QeexMessage(message = "one exception for each typed error")
QeexWebException simpleException();
```

we will use in a Rest Service, in this way:
https://github.com/n-essio/myteam/blob/main/src/main/java/it/queryable/myteam/service/rs/ProjectServiceRs.java

where we inject this ExceptionBundle:
```
@Inject
ExceptionBundle exceptionBundle;
```

e lo usa poi qui sotto:
```
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
```
