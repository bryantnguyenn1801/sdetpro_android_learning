package learning.models.pages;

public abstract class LoginPage {
    public void login() {
        System.out.println(username());
        System.out.println(password());
        System.out.println(loginBtn());
    }

    public abstract String username();

    public abstract String password();

    public abstract String loginBtn();
}
