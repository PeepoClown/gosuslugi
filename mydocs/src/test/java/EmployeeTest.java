import com.example.project.controller.AuthorizationController;
import com.example.project.dto.EmployeeRequestDto;
import com.example.project.dto.EmployeeResponseDto;
import com.example.project.entity.Employee;
import com.example.project.service.EmployeeService;
import com.example.project.util.status.StatusWithData;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeTest {
    private static final Logger logger = Logger.getLogger(EmployeeTest.class);

    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private AuthorizationController authorizationController;

    @Test
    public void createEmployeeTest() {
        try {
            EmployeeRequestDto reqDto = new EmployeeRequestDto("Евсеев Ф. В.", "employee1", "password");
            given(employeeService
                    .create(eq(reqDto)))
                    .willReturn(new Employee(reqDto.getLogin(), reqDto.getPassword(), reqDto.getInitials()));
            ResponseEntity<?> response = authorizationController.registration(reqDto);

            then(employeeService).should().create(eq(reqDto));
            assertThat(response.getStatusCode())
                    .isEqualTo(HttpStatus.OK);

            EmployeeResponseDto respDto = new EmployeeResponseDto(
                    reqDto.getLogin(),
                    reqDto.getInitials()
            );
            StatusWithData<?> responseBody = (StatusWithData<?>) response.getBody();
            assertThat(((EmployeeResponseDto) responseBody.getData()).getLogin()).isEqualTo(respDto.getLogin());
            assertThat(((EmployeeResponseDto) responseBody.getData()).getInitials()).isEqualTo(respDto.getInitials());
            logger.info("Successful createEmployeeTest: " + response.getStatusCode());
        } catch (Exception ex) {
            logger.info("Exception in createEmployeeTest: " + ex.getMessage());
        }
    }
}
