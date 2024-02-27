package org.telecomnancy.project.model;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SerializeModelTest {

    Subject subject;
    String subjectJSON;

    @BeforeEach
    void init() {
        subject = new Subject();
        subject.questions = new Question[2];
        subject.questions[0] = new Question();
        subject.questions[1] = new Question();
        subject.questions[0].text = "text";
        subject.questions[1].text = "text";
        subject.questions[1].imagePath = "path";

        subject.name = "subj";
        subject.description = "dec";
        subject.imagePath = "img!";

        for (int i = 0; i < 4; ++i) {
            Option option1 = new Option();
            Option option2 = new Option();
            option1.imagePath = "path/to/" + i;
            option2.text = "option " + i;

            subject.questions[0].options[i] = option1;
            subject.questions[1].options[i] = option2;
        }
        subject.questions[0].options[3].text = "bibi";
        subject.questions[1].options[3].imagePath = "bobo";

        subjectJSON = "{\"description\":\"dec\",\"imagePath\":\"img!\",\"name\":\"subj\",\"questions\":[{\"options\":[{\"imagePath\":\"path/to/0\"},{\"imagePath\":\"path/to/1\"},{\"imagePath\":\"path/to/2\"},{\"imagePath\":\"path/to/3\",\"text\":\"bibi\"}],\"text\":\"text\"},{\"imagePath\":\"path\",\"options\":[{\"text\":\"option 0\"},{\"text\":\"option 1\"},{\"text\":\"option 2\"},{\"imagePath\":\"bobo\",\"text\":\"option 3\"}],\"text\":\"text\"}]}";
    }

    @Test
    void serializeTest() {

        String serializedSubject;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            serializedSubject = jsonb.toJson(subject);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(subjectJSON, serializedSubject);
    }

    @Test
    void deserializeTest() {
        Jsonb jsonb = JsonbBuilder.create();

        Subject parsedSubject = jsonb.fromJson(subjectJSON, Subject.class);

        assertEquals(subject.questions.length, parsedSubject.questions.length);
        for (int i = 0; i < subject.questions.length; ++i) {
            compareQuestions(subject.questions[i], parsedSubject.questions[i]);
        }
    }

    void compareQuestions(Question expected, Question actual) {
        assertEquals(expected.imagePath, actual.imagePath);
        assertEquals(expected.text, actual.text);
        assertEquals(expected.options.length, actual.options.length);
        for (int i = 0; i < expected.options.length; ++i) {
            compareOptions(expected.options[i], actual.options[i]);
        }
    }

    void compareOptions(Option expected, Option actual) {
        assertEquals(expected.imagePath, actual.imagePath);
        assertEquals(expected.text, actual.text);
    }

}