package nl.tudelft.sem.template.authentication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
    @GetMapping("/HOA/getActivities")
    public void getActivities(/*Hoa*/){
        //TODO: Define return type, define parameters (HOA).
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
