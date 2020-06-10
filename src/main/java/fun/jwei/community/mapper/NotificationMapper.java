package fun.jwei.community.mapper;

import fun.jwei.community.model.Notification;
import fun.jwei.community.model.NotificationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NotificationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbg.generated Sun Mar 08 18:42:12 CST 2020
     */
    long countByExample(NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbg.generated Sun Mar 08 18:42:12 CST 2020
     */
    int deleteByExample(NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbg.generated Sun Mar 08 18:42:12 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbg.generated Sun Mar 08 18:42:12 CST 2020
     */
    int insert(Notification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbg.generated Sun Mar 08 18:42:12 CST 2020
     */
    int insertSelective(Notification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbg.generated Sun Mar 08 18:42:12 CST 2020
     */
    List<Notification> selectByExample(NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbg.generated Sun Mar 08 18:42:12 CST 2020
     */
    Notification selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbg.generated Sun Mar 08 18:42:12 CST 2020
     */
    int updateByExampleSelective(@Param("record") Notification record, @Param("example") NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbg.generated Sun Mar 08 18:42:12 CST 2020
     */
    int updateByExample(@Param("record") Notification record, @Param("example") NotificationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbg.generated Sun Mar 08 18:42:12 CST 2020
     */
    int updateByPrimaryKeySelective(Notification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbg.generated Sun Mar 08 18:42:12 CST 2020
     */
    int updateByPrimaryKey(Notification record);
}