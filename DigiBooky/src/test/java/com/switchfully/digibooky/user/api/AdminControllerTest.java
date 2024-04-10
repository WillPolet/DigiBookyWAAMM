package com.switchfully.digibooky.user.api;

import com.switchfully.digibooky.user.service.dto.admin.AdminDto;
import com.switchfully.digibooky.user.service.dto.admin.CreateAdminDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminControllerTest {

    private final static CreateAdminDto CREATE_ADMIN_DTO = new CreateAdminDto("a@admin.com",
            "lastname",
            "firstname",
            "pswd");
    private final static CreateAdminDto CREATE_ADMIN_DTO_INCORRECT_MAIL = new CreateAdminDto("a",
            "firstname",
            "lastname",
            "pswd");
    private static final String URI = "http://localhost";

    @LocalServerPort
    int localPort;
    @Test
    void createAdmin_whenGivenCorrectCreateAdminDto_thenAdminCreated() {
        //WHEN
        AdminDto actualAdminDto = RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .auth().preemptive().basic("root@root.com", "rootPswd")
                .contentType(ContentType.JSON)
                .body(CREATE_ADMIN_DTO)
                .when()
                .post("/admins")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(AdminDto.class);

        //THEN
        Assertions.assertThat(actualAdminDto.getEmail()).isEqualTo(CREATE_ADMIN_DTO.getEmail());
        Assertions.assertThat(actualAdminDto.getId()).isNotNull();
        Assertions.assertThat(actualAdminDto.getFirstname()).isNotNull();
        Assertions.assertThat(actualAdminDto.getLastname()).isNotNull();
    }
    @Test
    void createAdmin_whenGivenIncorrectMail_thenAdminNotCreated() {
        //GIVEN
        RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .auth().preemptive().basic(CREATE_ADMIN_DTO_INCORRECT_MAIL.getEmail(), CREATE_ADMIN_DTO_INCORRECT_MAIL.getPassword())
                .contentType(ContentType.JSON)
                .body(CREATE_ADMIN_DTO_INCORRECT_MAIL)
                .when()
                .post("/admins")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}