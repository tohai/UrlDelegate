package com.examp.urldelegate.controller;

import com.examp.urldelegate.RoutingDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class HomeController {
    //

    @Autowired
    RoutingDelegate routingDelegate;

    @GetMapping ("/get")
    public ResponseEntity<String> home( HttpServletRequest request, HttpServletResponse response){

        return routingDelegate.redirectPost(request,response);
    }

    @GetMapping ("/moc")
    public ResponseEntity<String> moc( HttpServletRequest request, HttpServletResponse response){
        routingDelegate.redirectPost(request,response);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }

}
