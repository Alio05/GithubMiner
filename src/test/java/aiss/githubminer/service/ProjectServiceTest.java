package aiss.githubminer.service;

import aiss.githubminer.model.Project;
import aiss.githubminer.etl.Transformer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    private ProjectService projectService;
    private Transformer transformer;
    private RestTemplate restTemplate;
    private CommitService commitService;
    private IssueService issueService;

    @BeforeEach
    void setUp() throws Exception {
        transformer = mock(Transformer.class);
        restTemplate = mock(RestTemplate.class);
        commitService = mock(CommitService.class);
        issueService = mock(IssueService.class);

        projectService = new ProjectService();

        // Inyectar dependencias privadas usando reflexión
        Field transformerField = ProjectService.class.getDeclaredField("transformer");
        transformerField.setAccessible(true);
        transformerField.set(projectService, transformer);

        Field restTemplateField = ProjectService.class.getDeclaredField("restTemplate");
        restTemplateField.setAccessible(true);
        restTemplateField.set(projectService, restTemplate);

        Field commitServiceField = ProjectService.class.getDeclaredField("commitService");
        commitServiceField.setAccessible(true);
        commitServiceField.set(projectService, commitService);

        Field issueServiceField = ProjectService.class.getDeclaredField("issueService");
        issueServiceField.setAccessible(true);
        issueServiceField.set(projectService, issueService);

        Field tokenField = ProjectService.class.getDeclaredField("token");
        tokenField.setAccessible(true);
        tokenField.set(projectService, "dummy-token");
    }

    @Test
    void testGetProjectByOwnerAndName() {
        Project mockProject = new Project();
        mockProject.setId("1");
        mockProject.setName("Test Project");

        when(restTemplate.exchange(anyString(), any(), any(), eq(Project.class)))
                .thenReturn(new org.springframework.http.ResponseEntity<>(mockProject, org.springframework.http.HttpStatus.OK));

        Project result = projectService.getProjectByOwnerAndName("owner", "repo");
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Test Project", result.getName());
    }

    @Test
    void testAllData() {
        Project mockProject = new Project();
        mockProject.setId("1");
        mockProject.setName("Test Project");

        when(restTemplate.exchange(anyString(), any(), any(), eq(Project.class)))
                .thenReturn(new org.springframework.http.ResponseEntity<>(mockProject, org.springframework.http.HttpStatus.OK));
        when(commitService.sinceCommits(anyString(), anyString(), any(), any())).thenReturn(java.util.Collections.emptyList());
        when(issueService.sinceIssues(anyString(), anyString(), any(), any())).thenReturn(java.util.Collections.emptyList());
        when(transformer.transform(any(Project.class))).thenReturn(mockProject);

        Project result = projectService.allData("owner", "repo", 5, 20, 2);
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Test Project", result.getName());
    }
}
