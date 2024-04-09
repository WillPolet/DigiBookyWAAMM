package com.switchfully.digibooky.user.api;

import com.switchfully.digibooky.user.domain.Member;
import com.switchfully.digibooky.user.domain.userAttribute.Address;
import com.switchfully.digibooky.user.service.UserMapper;
import com.switchfully.digibooky.user.service.dto.librarian.CreateLibrarianDto;
import com.switchfully.digibooky.user.service.dto.librarian.LibrarianDto;
import com.switchfully.digibooky.user.service.dto.member.CreateMemberDto;
import com.switchfully.digibooky.user.service.dto.member.MemberDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MemberControllerTest {
    private final static Address ADDRESS = new Address("street", "number", "0000", "Bxl");
    private final static CreateMemberDto CREATE_MEMBER_DTO = new CreateMemberDto("a@a.com", "lastname", "firstname", "pswd", ADDRESS, "A33");
    private final static CreateMemberDto CREATE_MEMBER_DTO2 = new CreateMemberDto("a@a2.com", "lastname", "firstname", "pswd", ADDRESS, "A34");
    private final static CreateMemberDto CREATE_MEMBER_DTO3 = new CreateMemberDto("a@a3.com", "lastname", "firstname", "pswd", ADDRESS, "A35");
    private final static CreateMemberDto CREATE_MEMBER_DTO_INCORRECT_MAIL = new CreateMemberDto("a", "lastname", "firstname", "pswd", ADDRESS, "A33");
    private final static CreateLibrarianDto CREATE_LIBRARIAN_DTO = new CreateLibrarianDto("l@a.com", "lastname", "firstname", "pswd");
    private UserMapper userMapper = new UserMapper();
    private static final String URI = "http://localhost";

    @LocalServerPort
    int localPort;

    @Test
    @DisplayName("When good info given : create user")
    void createMember_whenGivenCorrectMember_thenMemberCreated() {
        //GIVEN
        MemberDto actualMemberDto = RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .contentType(ContentType.JSON)
                .body(CREATE_MEMBER_DTO)
                .when()
                .post("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(MemberDto.class);

        //THEN
        Assertions.assertThat(actualMemberDto.getEmail()).isEqualTo(CREATE_MEMBER_DTO.getEmail());
        Assertions.assertThat(actualMemberDto.getFirstName()).isNotNull();
        Assertions.assertThat(actualMemberDto.getLastName()).isNotNull();
        Assertions.assertThat(actualMemberDto.getId()).isNotNull();
        Assertions.assertThat(actualMemberDto.getAddress().getStreetName()).isNotNull();
        Assertions.assertThat(actualMemberDto.getAddress().getStreetNumber()).isNotNull();
        Assertions.assertThat(actualMemberDto.getAddress().getCity()).isNotNull();
        Assertions.assertThat(actualMemberDto.getAddress().getZipCode()).isNotNull();
    }

    @Test
    @DisplayName("When email not well formated : user not created & BAD_REQUEST")
    void createMember_whenGivenIncorrectMail_thenMemberNotCreated() {
        //GIVEN
        RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .contentType(ContentType.JSON)
                .body(CREATE_MEMBER_DTO_INCORRECT_MAIL)
                .when()
                .post("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Ask by admin : get all members")
    void getAllMembers_whenAskByAdmin_thenMembersReturned() {
        // GIVEN
        populateRepo(CREATE_MEMBER_DTO);
        populateRepo(CREATE_MEMBER_DTO2);
        populateRepo(CREATE_MEMBER_DTO3);

        List<Member> listMember = Arrays.asList(userMapper.toMember(CREATE_MEMBER_DTO),
                userMapper.toMember(CREATE_MEMBER_DTO2),
                userMapper.toMember(CREATE_MEMBER_DTO3));

        List<MemberDto> listMemberDtoToFind = userMapper.toMemberDto(listMember);

        // WHEN
        List<MemberDto> actualListMemberDto = RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .auth().preemptive().basic("root@root.com", "rootPswd")
                .contentType(ContentType.JSON)
                .when()
                .get("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract().body().jsonPath().getList(".", MemberDto.class);

        // THEN
        Assertions.assertThat(actualListMemberDto).containsExactlyInAnyOrderElementsOf(listMemberDtoToFind);
    }

    @Test
    @DisplayName("Ask by member : BAD_REQUEST")
    void getAllMembers_whenAskByMember_thenForbidden() {
        // GIVEN
        populateRepo(CREATE_MEMBER_DTO);
        // THEN
        RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .auth().preemptive().basic(CREATE_MEMBER_DTO.getEmail(), CREATE_MEMBER_DTO.getPassword())
                .contentType(ContentType.JSON)
                .when()
                .get("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("Ask by librarian : BAD_REQUEST")
    void getAllMembers_whenAskByLibrarian_thenForbidden() {
        // GIVEN
        populateRepoLibrarian(CREATE_LIBRARIAN_DTO);

        // THEN
        RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .auth().preemptive().basic(CREATE_LIBRARIAN_DTO.getEmail(), CREATE_LIBRARIAN_DTO.getPassword())
                .contentType(ContentType.JSON)
                .when()
                .get("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    private void populateRepo(CreateMemberDto createMemberDto) {
        RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .contentType(ContentType.JSON)
                .body(createMemberDto)
                .when()
                .post("/members")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(MemberDto.class);
    }

        private void populateRepoLibrarian(CreateLibrarianDto createLibrarianDto) {
        RestAssured
                .given()
                .baseUri(URI)
                .port(localPort)
                .auth().preemptive().basic("root@root.com", "rootPswd")
                .contentType(ContentType.JSON)
                .body(createLibrarianDto)
                .when()
                .post("/librarians")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(LibrarianDto.class);
    }
}