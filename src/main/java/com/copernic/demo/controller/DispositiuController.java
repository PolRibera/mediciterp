package com.copernic.demo.controller;

//-----------------------//
//@Author Ricard Sierra--//
//-------DAM2T-----------//
//-----------------------//


// DeviceController.java

import com.copernic.demo.dao.ConsultaDAO;
import com.copernic.demo.dao.DispositiuDAO;
import com.copernic.demo.dao.RolDAO;
import com.copernic.demo.domain.Consulta;
import com.copernic.demo.domain.Dispositiu;
import com.copernic.demo.domain.Rol;
import com.copernic.demo.domain.Ticket;
import com.copernic.demo.services.DispositiuService;
import com.copernic.demo.services.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DispositiuController {

    @Autowired
    private DispositiuService dispositiuService;

    @Autowired
    DispositiuDAO dispositiuDAO;

    @Autowired
    ConsultaDAO consultaDAO;

    @Autowired
    RolDAO rolDAO;

    @Autowired
    UsuariService usuariService;





    @GetMapping("/dispositius")
    public String listDevices(Model model) {
        List<Dispositiu> dispositius = dispositiuService.getAllDevices();
        model.addAttribute("dispositius", dispositius);
        return "dispositivos";
    }

    @GetMapping("/nuevoDispositivo")
    public String mostrarFormulario(@RequestParam(name = "consulta", required = false) Long consulta_Id,Dispositiu dispositiu, Model model) {
        if (consulta_Id != null) {
            Consulta consulta = consultaDAO.findById(consulta_Id).orElse(null);
            model.addAttribute("consultes", consulta);
        } else {
            List<Consulta> consultes = consultaDAO.findAll();
            model.addAttribute("consultes", consultes);
        }
        model.addAttribute("dispositiu", dispositiu);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        model.addAttribute("username", username);
        Rol rol = rolDAO.findByNom(usuariService.getUsuariByUsername(username).getRol().getNom());
        model.addAttribute("Rol",rol);
        return "dispositivosForm";
}

    @PostMapping("/nuevoDispositivo")
    public String sumbitForm( Dispositiu dispositiu, Model model) {
        dispositiuService.saveDevice(dispositiu);
        model.addAttribute("dispositiu", dispositiu);
        return "redirect:/dispositius";
    }

    @GetMapping("/editarDispositivo/{id}")
    public String update(Dispositiu dispositiu,Model model) {
        dispositiu = dispositiuService.getDeviceById(dispositiu.getId());
        model.addAttribute("consultes", dispositiu.getConsulta());
        model.addAttribute("dispositiu", dispositiu);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        model.addAttribute("username", username);
        Rol rol = rolDAO.findByNom(usuariService.getUsuariByUsername(username).getRol().getNom());
        model.addAttribute("Rol",rol);
        return "dispositivosForm";
    }


    @GetMapping("/borrarDispositivo/{id}")
    public String delete(@PathVariable Long id, Model model) {
        Dispositiu dispositiu = dispositiuService.getDeviceById(id);
        model.addAttribute("dispositiu", dispositiu);
        return "dispositiuDeleteConfirmation";
    }
    @GetMapping("/borrarDisOk/{id}")
    public String delete(Dispositiu dispositiu) {
        dispositiuService.deleteDevice(dispositiu.getId());
        return "redirect:/dispositius";
    }



}

