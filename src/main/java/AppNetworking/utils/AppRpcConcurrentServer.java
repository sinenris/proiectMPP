package AppNetworking.utils;

import AppNetworking.rpcprotocol.AppClientRpcWorker;
import service.IService;

import java.net.Socket;

public class AppRpcConcurrentServer extends AbsConcurrentServer {
    private IService chatServer;
    public AppRpcConcurrentServer(int port, IService chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatRpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        AppClientRpcWorker worker=new AppClientRpcWorker(chatServer, client);
        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}

