package niss.controller;

import niss.domain.Tutorial;
import niss.repo.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8000") // Т.к. frontend отдельно
@RestController
@RequestMapping("/api/tutorial")
public class TutorialController {

    @Autowired
    private TutorialRepository tutorialRepository;

    @GetMapping
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
        List<Tutorial> tutorials = new ArrayList<>();

        if (title == null) {
            tutorials.addAll(tutorialRepository.findAll());
        } else {
            tutorials.addAll(tutorialRepository.findByTitleContaining(title));
        }

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable("id") long id) {
        Optional<Tutorial> tutorialOpt = tutorialRepository.findById(id);

        return tutorialOpt.map(tutorial -> new ResponseEntity<>(tutorial, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Tutorial> createTutorial(@RequestBody Tutorial tutorial) {
        Tutorial newTutorial = tutorialRepository
                .save(new Tutorial(tutorial.getTitle(), tutorial.getDescription(), false));

        return new ResponseEntity<>(newTutorial, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Optional<Tutorial> tutorialOpt = tutorialRepository.findById(id);

        return tutorialOpt.map(updatableTutorial -> {
            updatableTutorial.setTitle(tutorial.getTitle());
            updatableTutorial.setDescription(tutorial.getDescription());
            updatableTutorial.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutorialRepository.save(updatableTutorial), HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTutorials() {
        tutorialRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutorial(@PathVariable("id") long id) {
        tutorialRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/published")
    public ResponseEntity<List<Tutorial>> getPublishedTutorials() {
        List<Tutorial> tutorials = tutorialRepository.findByPublished(true);

        if (tutorials.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tutorials, HttpStatus.OK);
    }

}
