package com.cassiezys.transaction.mapper;

import com.cassiezys.transaction.model.Production;
import com.cassiezys.transaction.model.ProductionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ProductionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    long countByExample(ProductionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    int deleteByExample(ProductionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    int insert(Production record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    int insertSelective(Production record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    List<Production> selectByExampleWithBLOBsWithRowbounds(ProductionExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    List<Production> selectByExampleWithBLOBs(ProductionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    List<Production> selectByExampleWithRowbounds(ProductionExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    List<Production> selectByExample(ProductionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    Production selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    int updateByExampleSelective(@Param("record") Production record, @Param("example") ProductionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    int updateByExampleWithBLOBs(@Param("record") Production record, @Param("example") ProductionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    int updateByExample(@Param("record") Production record, @Param("example") ProductionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    int updateByPrimaryKeySelective(Production record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    int updateByPrimaryKeyWithBLOBs(Production record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table production
     *
     * @mbg.generated Mon Feb 10 10:38:27 CST 2020
     */
    int updateByPrimaryKey(Production record);
}