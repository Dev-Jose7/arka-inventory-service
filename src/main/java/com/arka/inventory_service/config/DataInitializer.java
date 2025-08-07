package com.arka.inventory_service.config;

import com.arka.inventory_service.model.*;
import com.arka.inventory_service.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final CountryRepository countryRepository;
    private final WarehouseRepository warehouseRepository;
    private final CurrencyRepository currencyRepository;

    @PostConstruct
    public void init() {
        initBrands();
        initCategories();
        initCountry();
        initWarehouses();
        initCurrency();
    }

    private void initBrands() {
        if (brandRepository.count() == 0) {
            Brand nvidia = new Brand();
            nvidia.setName("NVIDIA");

            Brand amd = new Brand();
            amd.setName("AMD");

            Brand intel = new Brand();
            intel.setName("Intel");

            brandRepository.save(nvidia);
            brandRepository.save(amd);
            brandRepository.save(intel);
        }
    }

    private void initCategories() {
        if (categoryRepository.count() == 0) {
            Category gpu = new Category();
            gpu.setName("GPU");
            gpu.setDescription("Tarjetas gráficas");

            Category cpu = new Category();
            cpu.setName("CPU");
            cpu.setDescription("Procesadores");

            Category ram = new Category();
            ram.setName("RAM");
            ram.setDescription("Memoria RAM");

            categoryRepository.save(gpu);
            categoryRepository.save(cpu);
            categoryRepository.save(ram);
        }
    }

    private void initCountry() {
        if (countryRepository.count() == 0) {
            Country colombia = new Country();
            colombia.setName("Colombia");

            Country ecuador = new Country();
            ecuador.setName("Ecuador");

            Country peru = new Country();
            peru.setName("Perú");

            Country chile = new Country();
            chile.setName("Chile");

            countryRepository.save(colombia);
            countryRepository.save(ecuador);
            countryRepository.save(peru);
            countryRepository.save(chile);
        }
    }

    private void initWarehouses() {
        if (warehouseRepository.count() == 0) {
            Warehouse central = new Warehouse();
            central.setName("Almacén Central");
            central.setLocation("Dirección Ciudad A");
            central.setCountry(countryRepository.findByName("Colombia").get());

            Warehouse norte = new Warehouse();
            norte.setName("Sucursal Norte");
            norte.setLocation("Dirección Ciudad B");
            norte.setCountry(countryRepository.findByName("Colombia").get());

            warehouseRepository.save(central);
            warehouseRepository.save(norte);
        }
    }

    private void initCurrency() {
        if (currencyRepository.count() == 0) {
            Currency pesoColombiano = new Currency();
            pesoColombiano.setName("Peso colombiano");
            pesoColombiano.setSymbol("$");
            pesoColombiano.setCode("COP");

            Currency dolarEstadounidense = new Currency();
            dolarEstadounidense.setName("Dólar estadounidense");
            dolarEstadounidense.setSymbol("$");
            dolarEstadounidense.setCode("USD");

            Currency solPeruano = new Currency();
            solPeruano.setName("Sol peruano");
            solPeruano.setSymbol("S/");
            solPeruano.setCode("PEN");

            Currency pesoChileno = new Currency();
            pesoChileno.setName("Peso chileno");
            pesoChileno.setSymbol("$");
            pesoChileno.setCode("CLP");

            currencyRepository.save(pesoColombiano);
            currencyRepository.save(dolarEstadounidense);
            currencyRepository.save(solPeruano);
            currencyRepository.save(pesoChileno);
        }
    }
}
