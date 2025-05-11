package aiss.githubminer.controller;

import aiss.githubminer.model.Project;
import aiss.githubminer.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GitHubControllerTest {

    private GitHubController controller;
    private ProjectService projectService;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() throws Exception {
        projectService = mock(ProjectService.class);
        restTemplate = mock(RestTemplate.class);

        controller = new GitHubController();

        // Inyectar dependencias privadas usando reflexión
        var serviceField = GitHubController.class.getDeclaredField("service");
        serviceField.setAccessible(true);
        serviceField.set(controller, projectService);

        var restTemplateField = GitHubController.class.getDeclaredField("restTemplate");
        restTemplateField.setAccessible(true);
        restTemplateField.set(controller, restTemplate);
    }

    @Test
    void testGetData() {
        Project mockProject = new Project();
        mockProject.setId("1");
        mockProject.setName("Test Project");

        when(projectService.allData(anyString(), anyString(), anyInt(), anyInt(), anyInt()))
                .thenReturn(mockProject);

        Project result = controller.getData("owner", "repo", 5, 20, 2);
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Test Project", result.getName());
    }

    @Test
    void testSendData() {
        Project mockProject = new Project();
        mockProject.setId("1");
        mockProject.setName("Test Project");

        when(projectService.allData(anyString(), anyString(), anyInt(), anyInt(), anyInt()))
                .thenReturn(mockProject);
        when(restTemplate.exchange(anyString(), any(), any(), eq(Project.class)))
                .thenReturn(ResponseEntity.ok(mockProject));

        ResponseEntity<Project> response = controller.sendData("owner", "repo", 5, 20, 2);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("1", response.getBody().getId());
        assertEquals("Test Project", response.getBody().getName());
    }
}
