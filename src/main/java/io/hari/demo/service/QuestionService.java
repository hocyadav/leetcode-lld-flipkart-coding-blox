package io.hari.demo.service;

import io.hari.demo.dao.QuestionDao;
import io.hari.demo.constant.Level;
import io.hari.demo.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionDao questionDao;

    public Question createQuestion(String question, Level level, Integer score) {
        final Question newQuestion = Question.builder().question(question).level(level).score(BigInteger.valueOf(score)).build();
        return questionDao.save(newQuestion);
    }

    public List<Question> getAllQuestionLevelWise(final Level questionLevel) {
        final List<Question> allByLevel = questionDao.findAllByLevelQuestions(questionLevel.toString());
        if (allByLevel.size() == 0) return questionDao.findAll();
        return allByLevel;
    }

    public List<Long> randomQuestion(int totalQuestions) {//20
        final Random random = new Random();
        int randomQuestionsCount = random.nextInt(totalQuestions);//3
        while (randomQuestionsCount == 0) randomQuestionsCount = random.nextInt(totalQuestions);
        final List<Long> randomQuestionIds = generateRandomQuestionIds(totalQuestions, randomQuestionsCount);
        return randomQuestionIds;
    }

    public List<Long> generateRandomQuestionIds(int maxLimitBound, int count) {//20, 3
        final Random random = new Random();
        List<Long> list = new LinkedList<>();
        for (int j = 0; j < count; j++) {
            final int nextInt = random.nextInt(maxLimitBound) + 1;
            //duplicate
            list.add(Long.valueOf(nextInt));
        }
        return list;
    }
}
