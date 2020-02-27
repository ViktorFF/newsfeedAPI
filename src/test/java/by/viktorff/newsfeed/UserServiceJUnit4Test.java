package by.viktorff.newsfeed;

import by.viktorff.newsfeed.model.Role;
import by.viktorff.newsfeed.service.UserServiceImpl;
import org.junit.*;

import java.util.HashMap;
import java.util.Map;

public class UserServiceJUnit4Test extends Assert {
    private UserServiceImpl userService = new UserServiceImpl();
    private static final Map<Role, Boolean> isAdminData = new HashMap<>();
    private static final Map<Role, Boolean> isModeratorData = new HashMap<>();

    @Before
    public void setUpIsAdminData() {
        isAdminData.put(Role.ADMIN, true);
        isAdminData.put(Role.MODERATOR, false);
        isAdminData.put(Role.USER, false);
    }

    @Before
    public void setUpIsModeratorData() {
        isModeratorData.put(Role.ADMIN, false);
        isModeratorData.put(Role.MODERATOR, true);
        isModeratorData.put(Role.USER, false);
    }

    @Test
    public void testIsAdmin() {
        for (Map.Entry<Role, Boolean> entry: isAdminData.entrySet()) {
            final Role testRole = entry.getKey();
            final boolean expected = entry.getValue();
            final boolean actual = userService.isAdmin(testRole);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testIsModerator() {
        for (Map.Entry<Role, Boolean> entry: isModeratorData.entrySet()) {
            final Role testRole = entry.getKey();
            final boolean expected = entry.getValue();
            final boolean actual = userService.isModerator(testRole);
            assertEquals(expected, actual);
        }
    }

    @After
    public void tearDownIsAdmin() {
        isAdminData.clear();
    }

    @After
    public void tearDownIsModerator() {
        isModeratorData.clear();
    }


}
