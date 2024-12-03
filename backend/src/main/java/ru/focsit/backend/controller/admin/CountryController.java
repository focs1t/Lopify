package ru.focsit.backend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Country;
import ru.focsit.backend.repository.CountryRepository;

import java.util.List;

@Controller
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryRepository countryRepository;

    // Отображение списка стран
    @GetMapping
    public String getAllCountries(Model model) {
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "admin/countries/list";
    }

    // Формуляр для создания новой страны
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("country", new Country());
        return "admin/countries/new";
    }

    // Создание новой страны
    @PostMapping
    public String createCountry(@ModelAttribute Country country) {
        countryRepository.save(country);
        return "redirect:/countries";
    }

    // Формуляр для редактирования страны
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Country country = countryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid country Id:" + id));
        model.addAttribute("country", country);
        return "admin/countries/edit";
    }

    // Обновление страны
    @PostMapping("/update/{id}")
    public String updateCountry(@PathVariable Long id, @ModelAttribute Country countryDetails) {
        Country country = countryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid country Id:" + id));
        country.setCountryName(countryDetails.getCountryName());
        countryRepository.save(country);
        return "redirect:/countries";
    }

    // Удаление страны
    @GetMapping("/delete/{id}")
    public String deleteCountry(@PathVariable Long id) {
        Country country = countryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid country Id:" + id));
        countryRepository.delete(country);
        return "redirect:/countries";
    }
}
