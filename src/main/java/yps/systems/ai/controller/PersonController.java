package yps.systems.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yps.systems.ai.model.Person;
import yps.systems.ai.repository.IPersonRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personService")
public class PersonController {

    private final IPersonRepository personRepository;

    @Autowired
    public PersonController(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/{elementId}")
    public ResponseEntity<Person> findByElementId(@PathVariable String elementId) {
        Optional<Person> personNodeOptional = personRepository.findById(elementId);
        if (personNodeOptional.isPresent()) {
            Person person = personNodeOptional.get();
            return ResponseEntity.ok(person);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        return ResponseEntity.ok(personRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<String> savePersonNode(@RequestBody Person person) {
        Person savedPerson = personRepository.save(person);
        return new ResponseEntity<>(savedPerson.getElementId(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{elementId}")
    public ResponseEntity<String> deletePersonNode(@PathVariable String elementId) {
        Optional<Person> personNodeOptional = personRepository.findById(elementId);
        if (personNodeOptional.isPresent()) {
            personRepository.deleteById(elementId);
            return new ResponseEntity<>("Person deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Person not founded", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{elementId}")
    public ResponseEntity<String> updatePersonNode(@PathVariable String elementId, @RequestBody Person person) {
        Optional<Person> personNodeFounded = personRepository.findById(elementId);
        if (personNodeFounded.isPresent()) {
            person.setElementId(personNodeFounded.get().getElementId());
            personRepository.save(person);
            return new ResponseEntity<>("Person updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Person not founded", HttpStatus.NOT_FOUND);
        }
    }

}
