package com.switchfully.digibooky.user.api;

import com.switchfully.digibooky.user.service.dto.librarian.CreateLibrarianDto;
import com.switchfully.digibooky.user.service.dto.librarian.LibrarianDto;
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
class LibrarianControllerTest {
    private final static CreateLibrarianDto CREATE_LIBRARIAN_DTO = new CreateLibrarianDto("a@a.com",
            "lastname",
            "firstname",
            "pswd");
    private final static CreateLibrarianDto CREATE_LIBRARIAN_DTO_INCORRECT_MAIL = new CreateLibrarianDto("a",
            "firstname",
            "lastname",
            "pswd");
    private static final String URI = "http://localhost";

    @LocalServerPort
    int localPort;

    @Test
    void createLibrarian_whenGivenCorrectCreateLibrarianDto_thenLibrarianCreated() {
        //WHEN
        LibrarianDto actualLibrarianDto = RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .auth().preemptive().basic("root@root.com", "rootPswd")
                .contentType(ContentType.JSON)
                .body(CREATE_LIBRARIAN_DTO)
                .when()
                .post("/librarians")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(LibrarianDto.class);

        //THEN
        Assertions.assertThat(actualLibrarianDto.getEmail()).isEqualTo(CREATE_LIBRARIAN_DTO.getEmail());
        Assertions.assertThat(actualLibrarianDto.getId()).isNotNull();
        Assertions.assertThat(actualLibrarianDto.getFirstname()).isNotNull();
        Assertions.assertThat(actualLibrarianDto.getLastname()).isNotNull();
    }

    @Test
    void createLibrarian_whenGivenIncorrectMail_thenLibrarianNotCreated() {
        //GIVEN
        RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .auth().preemptive().basic(CREATE_LIBRARIAN_DTO_INCORRECT_MAIL.getEmail(), CREATE_LIBRARIAN_DTO_INCORRECT_MAIL.getPassword())
                .contentType(ContentType.JSON)
                .body(CREATE_LIBRARIAN_DTO_INCORRECT_MAIL)
                .when()
                .post("/librarians")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}