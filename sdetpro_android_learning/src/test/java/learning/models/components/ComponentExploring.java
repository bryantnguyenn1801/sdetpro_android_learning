package learning.models.components;

import learning.models.pages.LoginPage;

import java.lang.reflect.Constructor;

public class ComponentExploring {
    public <T extends LoginPage> void login(Class<T> loginPageClass){
        Class<?>[] params = new Class[]{};
        try{
            Constructor<T> constructor = loginPageClass.getConstructor(params);
            T loginPageObj =  constructor.newInstance();   // T is-a LoginPage
            loginPageObj.login();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ComponentExploring().login(InternalLoginPage.class);
        new ComponentExploring().login(ExternalLoginPage.class);
    }
}
