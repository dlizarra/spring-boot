package com.dlizarra.startuphub.project;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.dlizarra.startuphub.support.AbstractWebIntegrationTest;

public class ProjectRepositoryTest extends AbstractWebIntegrationTest {

	@Autowired
	private ProjectRepository projectRepository;

	@Test
	public void save_ProjectGiven_ShouldSaveProject() {
		// arrange
		final Project project = new Project();
		project.setName("Project test");
		// act
		projectRepository.save(project);
		// assert
		assertThat(project.getId()).isNotNull();
	}

	@Sql({ "classpath:/sql/cleanup.sql", "classpath:/sql/project.sql" })
	@Test
	public void update_ExistingProjectGiven_ShouldUpdateProject() {
		// arrange
		final Project p = new Project();
		p.setId(2);
		p.setName("Project updated");
		// act
		final Project updatedProject = projectRepository.save(p);
		// assert
		assertThat(updatedProject.getName()).isEqualTo(p.getName());
		assertThat(updatedProject.getId()).isEqualTo(p.getId());
	}

	@Test
	public void findOne_ExistingIdGiven_ShouldReturnProject() {
		final Optional<Project> projectOpt = projectRepository.findOne(1);
		assertThat(projectOpt.isPresent()).isTrue();
		final Project project = projectOpt.get();
		// assert
		assertThat(project.getName()).isEqualTo("Project1");
	}

	@Transactional
	@Test
	public void getOne_ExistingIdGiven_ShouldReturnLazyEntity() {
		// act
		final Project p = projectRepository.getOne(1);
		// assert
		assertThat(p).isNotNull();
		assertThat(p.getId()).isEqualTo(1);
	}

	@Sql({ "classpath:/sql/cleanup.sql", "classpath:/sql/project.sql" })
	@Test
	public void findAll_TwoProjectsInDb_ShouldReturnTwoProjects() {
		// act
		final List<Project> allProjects = projectRepository.findAll();
		// assert
		assertThat(allProjects.size()).isEqualTo(2);
	}

	@Sql({ "classpath:/sql/cleanup.sql", "classpath:/sql/project.sql" })
	@Test
	public void delete_ExistingIdGiven_ShouldDeleteProject() {
		// act
		projectRepository.delete(2);
		// assert
		assertThat(projectRepository.findAll().size()).isEqualTo(1);
	}

}
