package com.twenk11k.todolists;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.twenk11k.todolists.common.EmailValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EmailValidatorTest {


    @Test
    public void correctEmail(){
        assertTrue(EmailValidator.isEmailValid("sample@sample.com"));
    }

    @Test
    public void correctEmailHasSubDomain() {
        assertTrue(EmailValidator.isEmailValid("sample@sample.com.tr"));
    }

    @Test
    public void wrongEmailIsEmpty(){
        assertFalse(EmailValidator.isEmailValid(""));
    }

    @Test
    public void wrongEmailIsNull(){
        assertFalse(EmailValidator.isEmailValid(null));
    }

    @Test
    public void wrongEmailHasNoDotCom(){
        assertFalse(EmailValidator.isEmailValid("sample@sample"));
    }

    @Test
    public void wrongEmailHasNoLocalPart(){
        assertFalse(EmailValidator.isEmailValid("@sample.com"));
    }

    @Test
    public void wrongEmailHasNoDomain(){
        assertFalse(EmailValidator.isEmailValid("sample@"));
        assertFalse(EmailValidator.isEmailValid("sample@."));
        assertFalse(EmailValidator.isEmailValid("sample@@."));
    }

    @Test
    public void wrongEmailWithExtraChars(){
        assertFalse(EmailValidator.isEmailValid("sample@sample.,com"));
        assertFalse(EmailValidator.isEmailValid("sample@sample,,,com"));
        assertFalse(EmailValidator.isEmailValid("sample@sample,..com"));
        assertFalse(EmailValidator.isEmailValid("sample@sample.co.k."));
    }

}
