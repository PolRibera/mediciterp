package com.copernic.demo.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration //Indica al sistema que és una classe de configuració
public class ConfiguracioWeb implements WebMvcConfigurer {

    /*En aquest mètode definirem les pàgines que mostrarem a l'usuari quan es produeixi un esdeveniment,
     *sense passar per un controlador.
    */
    @Override
    public void addViewControllers(ViewControllerRegistry registre) {
        registre.addViewController("/inici"); //Mostrem la pàgina incial que reanomenen com a index, quan encara no ens hem autenticat
        registre.addViewController("/login"); //Mostrem la pàgina login quan l'usuari no ha pogut autenticar-se
        registre.addViewController("/errors/error403").setViewName("/errors/error403"); //Mostrem la pàgina error403 quan l'usuari no pot accedir a una pàgina determinada.
        registre.addViewController("/error").setViewName("/errors/error"); //Mostrem la pàgina error403 quan l'usuari no pot accedir a una pàgina determinada.
    }

}
