package nl.tudelft.sem.template.authentication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/gateway")
public class GatewayController {
    //Fields for other microservice controllers.

    /**
     * Constructor for GatewayController.
     */
    @Autowired
    public GatewayController(){
        //Set fields
    }

    /**
     * Routing method used to retrieve activities for a certain HOA, if authorized.
     *
     */
    @GetMapping("/pnb/allActivities")
    public void getActivities(/*Hoa*/){
        //TODO: Define return type, define parameters (HOA).
        String url = "http://localhost:8081/pnb/allActivities";
    }

    /**
     * Routing method used to post an activity to the PNB.
     *
     */
    @PostMapping("/HOA/postActivity")
    public void postActivity(/*Activity activity, Hoa HOA*/){
        //TODO: Define parameters (activity, HOA).
    }


}
