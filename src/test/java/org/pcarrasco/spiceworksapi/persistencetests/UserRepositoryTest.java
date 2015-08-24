package org.pcarrasco.spiceworksapi.persistencetests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.pcarrasco.spiceworksapi.models.User;
import org.pcarrasco.spiceworksapi.persistence.SpiceworksApiUnitOfWorkImpl;
import org.pcarrasco.spiceworksapi.persistence.UserRepositoryImpl;

/**
 * Test user repository operations
 */
public class UserRepositoryTest {

    @Test
    public void testUserCRUD() {
        // Unit of work 1: Insert user
        SpiceworksApiUnitOfWorkImpl unitOfWork1 = new SpiceworksApiUnitOfWorkImpl();

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("LastName");
        user.setEncryptedPassword("Password");
        user.setEmail("test@test.com");

        UserRepositoryImpl userRepository1 = unitOfWork1.getUserRepository();
        userRepository1.create(user);

        unitOfWork1.commit();

        // Save the new user ID
        Integer userId = user.getId();

        // Verify that it is not 0
        assertNotEquals(0, (int)userId);

        // Unit of work 2: Find the user we just inserted
        SpiceworksApiUnitOfWorkImpl unitOfWork2 = new SpiceworksApiUnitOfWorkImpl();
        UserRepositoryImpl userRepository2 = unitOfWork2.getUserRepository();
        user = userRepository2.findOne(userId);

        // Validate that the data is correct
        assertEquals(user.getFirstName(), "Test");
        assertEquals(user.getLastName(), "LastName");
        assertEquals(user.getEmail(), "test@test.com");

        unitOfWork2.commit();

        // User of work 3: Update the user
        SpiceworksApiUnitOfWorkImpl unitOfWork3 = new SpiceworksApiUnitOfWorkImpl();
        UserRepositoryImpl userRepository3 = unitOfWork3.getUserRepository();
        user = userRepository3.findOne(userId);

        // Update the last name of the user in one SQL transaction
        user.setLastName("Updated_LastName");
        userRepository3.update(user);

        unitOfWork3.commit();

        // Unit of Work 4: Get the user and verify that the updates to the user
        // we saved to the DB
        SpiceworksApiUnitOfWorkImpl unitOfWork4 = new SpiceworksApiUnitOfWorkImpl();

        UserRepositoryImpl userRepository4 = unitOfWork4.getUserRepository();
        user = userRepository4.findOne(userId);

        unitOfWork4.commit();

        assertEquals(user.getLastName(), "Updated_LastName");

        // Unit of Work 5: Delete the user
        SpiceworksApiUnitOfWorkImpl unitOfWork5 = new SpiceworksApiUnitOfWorkImpl();

        UserRepositoryImpl userRepository5 = unitOfWork5.getUserRepository();
        user = userRepository5.findOne(userId);
        userRepository5.delete(user);

        unitOfWork5.commit();

        // Unit of Work 6: Find the user and verify it no longer exists in the DB
        SpiceworksApiUnitOfWorkImpl unitOfWork6 = new SpiceworksApiUnitOfWorkImpl();

        UserRepositoryImpl userRepository6 = unitOfWork6.getUserRepository();
        user = userRepository6.findOne(userId);

        assertNull(user);
    }
}
