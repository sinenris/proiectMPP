package AppNetworking.rpc;

import domain.Utilizator;

public class LoginRequest  implements Request{

    String type;
    Object data;
    @Override
    public String getType() {
        return type;
    }

    @Override
    public Object getData() {
        return data;
    }
    LoginRequest(String username, String password){
        this.type = "login";
        this.data = new Utilizator((long)1,username,password);
    }
}
