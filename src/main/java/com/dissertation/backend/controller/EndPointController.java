package com.dissertation.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@RestController
public class EndPointController {
    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public void EndpointDocController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public EndPointController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @RequestMapping(value="/endpointdoc", method= RequestMethod.GET)
    public Map<RequestMappingInfo, HandlerMethod> show(Model model) {
        System.out.println(this.handlerMapping.getHandlerMethods());
        model.addAttribute("handlerMethods", this.handlerMapping.getHandlerMethods());
        return this.handlerMapping.getHandlerMethods();
    }
}