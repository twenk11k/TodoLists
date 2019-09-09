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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class UserDbInsertDeleteTest {

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
        user.setEmail("sample@sample.com");
        user.setName("S_Name");
        user.setSurname("S_Surname");
        user.setPassword("s_password");

        userDao.insertTest(user);

        User byEmail = userDao.findUserByEmailTest("sample@sample.com");
        assertThat(byEmail.getEmail(),equalTo(user.getEmail()));

    }


    @Test
    public void deleteUserTest() throws Exception{

        String email = "sample@sample.com";
        User byEmail = userDao.findUserByEmailTest(email);
        assertThat(byEmail.getEmail(),equalTo(email));

        userDao.deleteTest(byEmail);

        User byEmailDelete = userDao.findUserByEmailTest(email);
        assertNull(byEmailDelete);

    }

}
