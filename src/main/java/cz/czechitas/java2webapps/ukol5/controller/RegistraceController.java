package cz.czechitas.java2webapps.ukol5.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Random;

/**
 * Kontroler obsluhující registraci účastníků dětského tábora.
 */

@Controller

public class RegistraceController {

  private final Random random = new Random();


  @GetMapping("")
  public ModelAndView index() {
    ModelAndView modelAndView = new ModelAndView("/formular");
    modelAndView.addObject("form", new RegistraceForm());
    return modelAndView;
  }

  @PostMapping("/")
  public Object form(@ModelAttribute("form") @Valid RegistraceForm form, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return "/formular";
    }

    if (form.getSport().size() < 2){
      bindingResult.rejectValue("sport", "","Je nutné zadat alespon dva sporty");
      return "/formular";
    }

    if (form.getRokNarozeniDitete() < 9 || form.getRokNarozeniDitete() >= 15) {
      bindingResult.rejectValue("datumNarozeni", "", "Vaše dítě nelze na tábor přihlásit, z důvodu nepatřičného věku");
      return "/formular";
    }

    return new ModelAndView("/prihlaseni")
            .addObject("jmeno", form.getJmeno())
            .addObject("prijmeni", form.getPrijmeni())
            .addObject("pohlavi", form.getPohlavi())
            .addObject("sporty", form.getSport())
            .addObject("turnus", form.getTurnus())
            .addObject("email", form.getEmail())
            .addObject("telefon", form.getTelefon());

  }
}
