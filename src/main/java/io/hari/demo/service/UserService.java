package io.hari.demo.service;

import io.hari.demo.dao.ContestDao;
import io.hari.demo.dao.UserDao;
import io.hari.demo.entity.Contest;
import io.hari.demo.entity.ContestQuestions;
import io.hari.demo.entity.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final QuestionService questionService;
    private final ContestDao contestDao;


    public void assignContestToUser(User user, Contest contest1) {//low -> 20 , 1,2...20
        //todo : validate
        user.setContests(Arrays.asList(contest1));
//        final List<Long> contestQuestions = contest1.getContestQuestions().getQuestions();
//        user.setContestQuestions(contest1.getContestQuestions());
        final List<Long> randomQuestion1 = questionService.randomQuestion(contest1.getContestQuestions().getQuestions().size());//todo:
        System.out.println("randomQuestion1 = " + randomQuestion1);
        user.setContestQuestions(ContestQuestions.builder().questions(randomQuestion1).build());
        userDao.save(user);
    }

    private void validateContest(Contest contest) {
        final Contest fetchedContest = contestDao.findById(contest.getUserId()).get();
        if (fetchedContest == null) throw new RuntimeException("contest not found");
    }


    public User create(User user) {
        user.setScore(BigInteger.valueOf(1500));
        return userDao.save(user);
    }

    public List<User> createMultiUsers(List<User> users) {
        users.forEach(user -> {
            user.setScore(BigInteger.valueOf(1500));
        });
        return userDao.saveAll(users);
    }

    private boolean validateUserName(String username) {
        if (userDao.findAllByUsername(username).get(0) != null) {//found
            return true;
        }
        return false;
    }
}
