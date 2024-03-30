package org.telecomnancy.project.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SerializeModelTest {

    Subject subject;
    String subjectJSON;

    Gson gson;

    @BeforeEach
    void init() {
        subject = new Subject();
        subject.questions = new Question[2];
        subject.questions[0] = new Question();
        subject.questions[1] = new Question();
        subject.questions[0].text = "text";
        subject.questions[0].level = Difficulty.Beginner;
        subject.questions[1].text = "text";
        subject.questions[1].level = Difficulty.Advanced;
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

        subjectJSON = "{\"name\":\"subj\",\"description\":\"dec\",\"imagePath\":\"img!\",\"questions\":[{\"options\":[{\"imagePath\":\"path/to/0\"},{\"imagePath\":\"path/to/1\"},{\"imagePath\":\"path/to/2\"},{\"text\":\"bibi\",\"imagePath\":\"path/to/3\"}],\"text\":\"text\",\"level\":\"Beginner\"},{\"options\":[{\"text\":\"option 0\"},{\"text\":\"option 1\"},{\"text\":\"option 2\"},{\"text\":\"option 3\",\"imagePath\":\"bobo\"}],\"imagePath\":\"path\",\"text\":\"text\",\"level\":\"Advanced\"}]}";

        final GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    @Test
    void serializeTest() {
        String serializedSubject = gson.toJson(subject);
        assertEquals(subjectJSON, serializedSubject);
    }

    @Test
    void deserializeTest() {

        Subject parsedSubject = gson.fromJson(subjectJSON, Subject.class);

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