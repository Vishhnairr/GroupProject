import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;

public class UserTest {

    private User user;
    @Mock private File fileMock;
    @Mock private FileOutputStream fosMock;
    @Mock private PrintWriter pwMock;
    @Mock private FileReader frMock;
    @Mock private BufferedReader bfrMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        user = new User("johndoe", "password123", "John", "Doe", "john@example.com", "Bio", true, false);

        // Setup common mocks
        when(new File(anyString())).thenReturn(fileMock);
        when(new FileOutputStream(any(File.class), anyBoolean())).thenReturn(fosMock);
        when(new PrintWriter(any(FileOutputStream.class))).thenReturn(pwMock);
        when(new FileReader(any(File.class))).thenReturn(frMock);
        when(new BufferedReader(any(FileReader.class))).thenReturn(bfrMock);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("johndoe", user.getUsername());
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testSetUsernameSuccessful() throws Exception {
        when(bfrMock.readLine()).thenReturn(null);
        assertTrue(user.setUsername("janedoe"));
        verify(pwMock).println("janedoe");
    }

    @Test
    public void testSetUsernameFailure() throws Exception {
        when(bfrMock.readLine()).thenReturn("johndoe", (String) null);
        assertFalse(user.setUsername("johndoe")); // Username already exists
    }

    @Test
    public void testCreateAccountInvalidData() {
        User invalidUser = new User("johndoe", "", "John", "", "john@example.com", "Bio", true, false);
        assertFalse(invalidUser.createAccount());
    }

    @Test
    public void testCreateAccountValidData() throws Exception {
        when(bfrMock.readLine()).thenReturn(null); // Username not found in the file
        assertTrue(user.createAccount());
        verify(pwMock).println("User name: johndoe");
    }

    @Test
    public void testLogInSuccess() throws Exception {
        when(bfrMock.readLine()).thenReturn("User name: johndoe", "Password: password123", "First name: John", "Last name: Doe", "Email: john@example.com", "Bio: Bio", "Profile view: true", "Message only: false", null);
        assertNotNull(user.logIn());
    }

    @Test
    public void testLogInFailure() throws Exception {
        when(bfrMock.readLine()).thenReturn("User name: johndoe", "Password: wrongpassword", null);
        assertNull(user.logIn());
    }

    @Test
    public void testViewFriendsRequests() throws Exception {
        when(bfrMock.readLine()).thenReturn("Alice", "Bob", null);
        String[] requests = user.viewFriendsRequest();
        assertNotNull(requests);
        assertEquals(2, requests.length);
        assertEquals("Alice", requests[0]);
        assertEquals("Bob", requests[1]);
    }
}
