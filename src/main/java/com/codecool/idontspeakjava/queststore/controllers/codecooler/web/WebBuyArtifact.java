package com.codecool.idontspeakjava.queststore.controllers.codecooler.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.*;
import com.codecool.idontspeakjava.queststore.models.*;
import com.sun.net.httpserver.HttpExchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebBuyArtifact extends AbstractHandler{
    private SQLiteArtifactsDAO artifactsDAO = new SQLiteArtifactsDAO();
    private SQLiteOrdersDAO ordersDAO = new SQLiteOrdersDAO();
    private SQLiteWalletsDAO walletsDAO = new SQLiteWalletsDAO();

    @Override
    public void handle(HttpExchange exchange){
        String method = exchange.getRequestMethod();

        if(method.equals("GET")){
            String form = HTMLGenerator.getRadioForm(getArtifactsInfo(), "Choose artifact", "artifact");
            sendTemplateResponseWithForm(exchange, "student_home", form);
        }
        if (method.equals("POST")) {
            List <Artifact> artifacts = artifactsDAO.getAllArtifacts();
            Artifact artifact = getArtifactByName(getArtifactName(exchange), artifacts);

            if(buyArtifact(exchange, artifact)){
                redirectToLocation(exchange, "/alert/success");
            }else {
                redirectToLocation(exchange, "/alert/fail");
            }

        }

    }

    private String getArtifactName(HttpExchange exchange){
        Map<String, String> data = readFormData(exchange);
        String artifactInfo = data.get("artifact");
        return artifactInfo.split(",")[0];
    }

    private boolean buyArtifact(HttpExchange exchange, Artifact artifact) {
        boolean isOperationSucceeded = false;
        User codecooler = getUserBySession(exchange);
        Wallet wallet = walletsDAO.getWalletByUserID(codecooler.getId());
        
        if (artifact != null) {
            long currentState = wallet.getCurrentState();
            if (currentState >= artifact.getPrice()) {
                ordersDAO.createOrder(new Order(artifact.getId(), wallet.getId(), false));
                wallet.setCurrentState(currentState - artifact.getPrice());
                walletsDAO.updateWallet(wallet);
                isOperationSucceeded = true;
            }
        }
        return isOperationSucceeded;
    }

    private List<String> getArtifactsInfo(){
        List<Artifact> artifacts = artifactsDAO.getAllArtifacts(ArtifactCategory.Basic);
        List<String> artifactsInfo = new ArrayList<>();
        for(Artifact artifact: artifacts){
            artifactsInfo.add(artifact.getTitle() + ", Price: " + artifact.getPrice());
        }
        return artifactsInfo;
    }

}
