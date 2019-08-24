package com.twenk11k.todolists;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.twenk11k.todolists.roomdb.user.User;
import com.twenk11k.todolists.roomdb.user.UserDao;
import com.twenk11k.todolists.roomdb.user.UserDb;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class UserDbInsertTest {

    private UserDao userDao;
    private UserDb userDb;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        userDb = userDb.getDatabase(context);
        userDao = userDb.userDao();
    }

    @After
    public void closeDb() throws IOException {
        userDb.close();
    }

    @Test
    public void insertUserTest() throws Exception {

        User user = new User();
        user.setEmail("samplemail@email.com");
        user.setName("Sample Name");
        user.setSurname("Sample Surname");
        user.setPassword("password");
        userDao.insertTest(user);
        User byEmail = userDao.findUserByEmailTest("samplemail@email.com");
        assertThat(byEmail.getEmail(),equalTo(user.getEmail()));

    }


    @Test
    public void deleteUserTest() throws Exception{

        String email = "samplemail@email.com";
        User byEmail = userDao.findUserByEmailTest(email);
        assertThat(byEmail.getEmail(),equalTo(email));

        userDao.deleteTest(byEmail);

        User byEmailDelete = userDao.findUserByEmailTest(email);
        assertNull(byEmailDelete);
    }


}