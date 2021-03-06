package udemy.spring.course.petclinic.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import udemy.spring.course.petclinic.model.Owner;
import udemy.spring.course.petclinic.model.Pet;
import udemy.spring.course.petclinic.model.PetType;
import udemy.spring.course.petclinic.model.Specialty;
import udemy.spring.course.petclinic.model.Vet;
import udemy.spring.course.petclinic.model.Visit;
import udemy.spring.course.petclinic.services.OwnerService;
import udemy.spring.course.petclinic.services.PetTypeService;
import udemy.spring.course.petclinic.services.SpecialtyService;
import udemy.spring.course.petclinic.services.VetService;
import udemy.spring.course.petclinic.services.VisitService;

import java.time.LocalDate;

/**
 * Created by Ari on 29.09.2018
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;

    @Autowired // This annotation is not necessary, but it doesn't hurt either
    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();

        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType savedCatPetType = petTypeService.save(cat);

        System.out.println("Loaded Pet Types...");

        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");
        Specialty savedRadiologySpecialty = specialtyService.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setDescription("Surgery");
        Specialty savedSurgerySpecialty = specialtyService.save(surgery);

        Specialty dentistry = new Specialty();
        dentistry.setDescription("Dentistry");
        Specialty savedDentistrySpecialty = specialtyService.save(dentistry);

        System.out.println("Loaded Specialties...");

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("123 Brickerel");
        owner1.setCity("Miami");
        owner1.setTelephone("1231231234");

        Pet mikesPet = new Pet();
        mikesPet.setPetType(savedDogPetType);
        mikesPet.setOwner(owner1);
        mikesPet.setBirthDate(LocalDate.now());
        mikesPet.setName("Rosco");

        owner1.getPets().add(mikesPet);
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        owner2.setAddress("123 Brickerel");
        owner2.setCity("Miami");
        owner2.setTelephone("1231231234");

        Pet fionasCat = new Pet();
        fionasCat.setPetType(savedCatPetType);
        fionasCat.setOwner(owner2);
        fionasCat.setBirthDate(LocalDate.now());
        fionasCat.setName("Just Cat");

        owner2.getPets().add(fionasCat);
        ownerService.save(owner2);

        System.out.println("Loaded Owners...");

        Visit catVisit = new Visit();
        catVisit.setPet(fionasCat);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Sneezy Kitty");

        visitService.save(catVisit);

        System.out.println("Loaded Visits...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialties().add(savedRadiologySpecialty);
        vet1.getSpecialties().add(savedDentistrySpecialty);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialties().add(savedSurgerySpecialty);
        vetService.save(vet2);

        System.out.println("Loaded Vets...");
    }
}
