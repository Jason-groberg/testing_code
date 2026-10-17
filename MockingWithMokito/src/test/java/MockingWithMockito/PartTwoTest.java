package MockingWithMockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

public class PartTwoTest {

    PartTwo partTwo;
    Database2 databaseMock;

    @BeforeEach
    public void setUp() throws Exception {
        databaseMock = Mockito.mock(Database2.class);
        partTwo = new PartTwo(databaseMock);
    }

    @Test
    public void login() throws Exception {
        Mockito.when(databaseMock.getUserId(Mockito.anyString(), Mockito.anyString())).thenReturn("ABC-123");
        Mockito.when(databaseMock.getUserId("Trinity","not-here")).thenThrow(SQLException.class);

        Assertions.assertEquals( "ABC-123", partTwo.login("Gale", "exists"));
        Assertions.assertEquals( "", partTwo.login("Trinity", "not-here"));
    }

    @Test
    public void register() throws Exception{
        Mockito.when(databaseMock.addUser(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenThrow(SQLException.class);

        Assertions.assertThrows(SQLException.class, () -> {
           partTwo.register("Trinity", "cantGuessIt", "mail@mail.com", "Student");
        });

    }

    @Test
    public void logAccountUse() {
        Mockito.when(databaseMock.getAccountType("ABC-123")).thenReturn("Admin");
        Mockito.when(databaseMock.getAccountType("BIG-CHUNG")).thenReturn("Student");
        Mockito.when(databaseMock.getAccountType("A-UUID")).thenReturn("Staff");
        Mockito.when(databaseMock.getAccountType("NOT_A_UUID")).thenReturn("");

        partTwo.logAccountUse("ABC-123");
        partTwo.logAccountUse("BIG-CHUNG");
        partTwo.logAccountUse("A-UUID");
        partTwo.logAccountUse("NOT_A_UUID");

        Mockito.verify(databaseMock).getAccountType("ABC-123");
        Mockito.verify(databaseMock).getAccountType("BIG-CHUNG");
        Mockito.verify(databaseMock).getAccountType("A-UUID");
        Mockito.verify(databaseMock).getAccountType("NOT_A_UUID");

        Mockito.verify(databaseMock, Mockito.times(1)).logAdminUse("ABC-123");
        Mockito.verify(databaseMock, Mockito.times(1)).logStudentUse("BIG-CHUNG");
        Mockito.verify(databaseMock, Mockito.times(1)).logStaffUse("A-UUID");
    }


    @Test
    void deleteUserAccount() throws Exception {
        Mockito.when(databaseMock.getUserId("Gale", "exists"))
                .thenReturn("ABC-123");
        Mockito.when(databaseMock.getUserId("Trinity", "not-here"))
                .thenReturn("NOT_A_UUID");
        Mockito.when(databaseMock.getUserId("Staff", "password"))
                .thenThrow(SQLException.class);

        Mockito.when(databaseMock.deleteUserAccount("ABC-123"))
                .thenReturn(Boolean.TRUE);
        Mockito.when(databaseMock.deleteUserAccount("NOT_A_UUID"))
                .thenReturn(Boolean.FALSE);

        partTwo.deleteUserAccount("Gale", "exists");

        Mockito.verify(databaseMock).deleteUserAccount("ABC-123");

        partTwo.deleteUserAccount("Trinity", "not-here");

        Mockito.verify(databaseMock).deleteUserAccount("NOT_A_UUID");
        Mockito.verify(databaseMock).logErrorMessage("Deletion attempt: User was not deleted");

        partTwo.deleteUserAccount("Staff", "password");
        Mockito.verify(databaseMock).getUserId("Staff", "password");
        Mockito.verify(databaseMock).logErrorMessage("SQL Error during Deletion");
    }
}