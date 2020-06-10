package fun.jwei.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.jwei.community.exception.CustomException;
import fun.jwei.community.dto.QuestionAndUserDTO;
import fun.jwei.community.exception.ExceptionEnum;
import fun.jwei.community.mapper.MyQuestionMapper;
import fun.jwei.community.mapper.QuestionMapper;
import fun.jwei.community.mapper.UserMapper;
import fun.jwei.community.model.Question;
import fun.jwei.community.model.QuestionExample;
import fun.jwei.community.model.User;
import fun.jwei.community.service.QuestionService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private QuestionMapper questionMapper;
    private MyQuestionMapper myQuestionMapper;
    private UserMapper userMapper;

    @Override
    public PageInfo<QuestionAndUserDTO> getAll(Integer page, Integer pageSize) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdIsNotNull();
        questionExample.setOrderByClause("gmt_create desc");
        PageHelper.startPage(page, pageSize);
        List<Question> questions = questionMapper.selectByExample(questionExample);
        return getQuestionAndUserDTOPageInfo(questions);
    }

    @Override
    public PageInfo<QuestionAndUserDTO> findAll(String keyword, Integer page, Integer pageSize) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andTitleLike("%"+keyword+"%");
        PageHelper.startPage(page, pageSize);
        List<Question> questions = questionMapper.selectByExample(questionExample);
        return getQuestionAndUserDTOPageInfo(questions);
    }

    @Override
    public PageInfo<QuestionAndUserDTO> findByCreatorId(Long id, Integer page, Integer pageSize) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorIdEqualTo(id);
        PageHelper.startPage(page, pageSize);
        List<Question> questions = questionMapper.selectByExample(questionExample);
        return getQuestionAndUserDTOPageInfo(questions);
    }

    @Override
    public QuestionAndUserDTO findById(Long id) throws CustomException {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomException(ExceptionEnum.QUESTION_NOT_FOUND);
        }
        return getQuestionAndUserDTO(question);
    }

    @Override
    public void createOrUpdate(Long id, String title, String desc, String tag, Long userId) {
        Question question;
        if (id != null) {
            question = questionMapper.selectByPrimaryKey(id);
            update(title, desc, tag, question);
            questionMapper.updateByPrimaryKeySelective(question);
        } else {
            question = new Question();
            update(title, desc, tag, question);
            question.setCreatorId(userId);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
        }
    }

    @Override
    public void incView(Long id) {
        myQuestionMapper.incView(id);
    }

    @Override
    public List<Question> findRelatedQuestion(Long id, String tag) {
        if (tag.length() == 0) {
            return null;
        }
        String tags = tag.replace(";", "|");
        return myQuestionMapper.findRelatedQuestion(id, tags);
    }

    @Override
    public List<Question> findPopularQuestion(Integer count) {
        return myQuestionMapper.findPopularQuestion(count);
    }

    private void update(String title, String desc, String tag, Question question) {
        question.setGmtModified(System.currentTimeMillis());
        question.setTitle(title);
        question.setDescription(desc);
        question.setTag(tag);
    }

    @NotNull
    private QuestionAndUserDTO getQuestionAndUserDTO(Question question) {
        User user = userMapper.selectByPrimaryKey(question.getCreatorId());
        QuestionAndUserDTO questionAndUserDTO = new QuestionAndUserDTO();
        questionAndUserDTO.setUser(user);
        questionAndUserDTO.setQuestion(question);
        return questionAndUserDTO;
    }

    @NotNull
    private PageInfo<QuestionAndUserDTO> getQuestionAndUserDTOPageInfo(List<Question> questions) {
        List<QuestionAndUserDTO> list = new ArrayList<>();
        PageInfo<Question> questionPageInfo = new PageInfo<>(questions, 5);
        for (Question question : questionPageInfo.getList()) {
            QuestionAndUserDTO questionAndUserDTO = getQuestionAndUserDTO(question);
            list.add(questionAndUserDTO);
        }
        PageInfo<QuestionAndUserDTO> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questionPageInfo, pageInfo);
        pageInfo.setList(list);
        return pageInfo;
    }


    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setMyQuestionMapper(MyQuestionMapper myQuestionMapper) {
        this.myQuestionMapper = myQuestionMapper;
    }
}
