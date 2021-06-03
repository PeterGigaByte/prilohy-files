package fei.stuba.socket.controller;

import fei.stuba.socket.server.Server;
import fei.stuba.socket.service.Results;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class ServerController {
    private fei.stuba.socket.service.Results resultsService;

    public ServerController(Results resultsService) throws IOException {
        this.resultsService = resultsService;
        Server server = new Server();
        server.setResultsService(resultsService);
        server.start(8055);
    }
}

