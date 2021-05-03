package com.i3AdvancedTech.i3connect;

public class UserProfile
{
    public String Name;
    public String Email;
    public String Phone;
    public String Password;
    public String Gender;

    public  UserProfile() {}

    public UserProfile(String name, String email, String phone, String password, String gender) {
        Name = name;
        Email = email;
        Phone = phone;
        Password = password;
        Gender = gender;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
