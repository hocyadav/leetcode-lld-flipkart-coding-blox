package io.hari.demo.service;

import io.hari.demo.config.AppConfig;
import io.hari.demo.constant.ContestStatus;
import io.hari.demo.constant.Level;
import io.hari.demo.dao.ContestDao;
import io.hari.demo.dao.QuestionDao;
import io.hari.demo.dao.UserDao;
import io.hari.demo.entity.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContestService {
    private final ContestDao contestDao;
    private final QuestionDao questionDao;
    private final UserDao userDao;
    private final AppConfig config;


    public Contest createContest(String contestName, Level contestLevel, User user) {
        final List<Long> questions = contestLevelWiseAllQuestions(contestLevel);

        final ContestQuestions contestQuestions = ContestQuestions.builder().questions(questions).build();
        final Contest newContest = Contest.builder().name(contestName)
                .userId(user.getId())
                .level(contestLevel)
                .status(ContestStatus.started)
                .contestQuestions(contestQuestions)
                .build();

        contestDao.save(newContest);
        setContestToUser(user, newContest);
        return newContest;
    }

    public void runContest(Contest contest) {
        validateContest(contest);
        final List<User> allUserByContestsId = userDao.findAllByContestsId(contest.getUserId());
        final String contestLevel = contest.getLevel().toString();
        final BigInteger scoreConstant = config.getScoreConstant().get(contestLevel);
        //for all user solve question and update score
        allUserByContestsId.forEach(user -> {
            final List<Question> userQuestions = getUserQuestions(user);
            calculateUserNewScore(scoreConstant, user, userQuestions);
        });
        contest.setStatus(ContestStatus.ended);
        contestDao.save(contest);
    }

    public void withdrawContest(User user, Contest contest) {
        //todo
        validateContest(contest);
        final Optional<User> optionalUser = userDao.findById(user.getId());
        optionalUser.ifPresent(user1 -> {
            final List<Contest> contests = user1.getContests();
            user1.setContests(null);
            final Collection<Contest> intersection = CollectionUtils.intersection(contests, Arrays.asList(contest));
            final List<Contest> collect = intersection.stream().collect(Collectors.toList());
            user1.setContests(collect);
            userDao.save(user1);
        });
    }

    public List<String> contestHistory(Contest contest) {
        final List<User> allByContestsId = userDao.findAllByContestsId(contest.getId());
        final List<String> usersList = allByContestsId.stream().map(i -> i.getUsername()).collect(Collectors.toList());
        return usersList;
    }

    private void validateContest(Contest contest) {
        if (!contest.getStatus().equals(ContestStatus.started)) {
            System.out.println("contest not started yet");
            throw new RuntimeException("contest not started yet");
        }
    }

    private void setContestToUser(User user, Contest contest) {
        final User fetchedUser = userDao.findById(user.getId()).get();
        fetchedUser.setContests(Arrays.asList(contest));
        fetchedUser.setContestQuestions(contest.getContestQuestions());
        userDao.save(fetchedUser);
    }

    private List<Long> contestLevelWiseAllQuestions(Level questionLevel) {
        final List<Question> questions = questionDao.findAllByLevelQuestions(questionLevel.toString());
        final List<Long> questionIds = questions.stream().map(i -> i.getId()).collect(Collectors.toList());
        return questionIds;
    }

    private List<Question> getUserQuestions(User user) {
        final List<Long> questions = user.getContestQuestions().getQuestions();
        final List<Question> userQuestions = questions.stream().map(i -> questionDao.findById(i).get()).filter(Objects::nonNull)
                .collect(Collectors.toList());
        return userQuestions;
    }

    private void calculateUserNewScore(BigInteger scoreConstant, User user, List<Question> userQuestions) {
        final BigInteger calculatedScore = userQuestions.stream()
                .map(i -> i.getScore())
                .reduce(BigInteger::add).orElseGet(() -> BigInteger.ZERO);
        System.out.println("calculated score = " + calculatedScore);

        final BigInteger currentScore = user.getScore();
        System.out.println("currentScore user score = " + currentScore);
        final BigInteger newScore = currentScore.add(calculatedScore).subtract(scoreConstant);//todo
        System.out.println("newScore user score = " + newScore);
        user.setScore(newScore);
        userDao.save(user);
    }

}
