package cn.sibat.boot.server.entity;

import javax.persistence.*;

/**
 *
* @ClassName: ProductEarningRate
* @Description: 产品收益表实体 以及 产品收益表日志实体
 */
@Entity
@Table(name="T_PRODUCTEARNINGRATE")
public class ProductEarningRate{
	@Id
	@GeneratedValue()
	@Column(name="T_ID", nullable=false)
    //主键id
    private Integer id;

	@Column(name="T_PID")
    //产品id
    private Integer productId;

	@Column(name="T_MONTH")
    //月份
    private Integer month;

	@Column(name="T_INCOMERATE")
    //收益率
    private double incomeRate;

	public ProductEarningRate() {
		super();
	}

	public ProductEarningRate(Integer month, double incomeRate) {
		super();
		this.month = month;
		this.incomeRate = incomeRate;
	}

    /**
     * @return id
     */

    public Integer getId() {
        return id;
    }

    /**
     * @param id 要设置的 id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return productId
     */

    public Integer getProductId() {
        return productId;
    }

    /**
     * @param productId 要设置的 productId
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * @return month
     */

    public Integer getMonth() {
        return month;
    }

    /**
     * @param month 要设置的 month
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * @return incomeRate
     */

    public double getIncomeRate() {
        return incomeRate;
    }

    /**
     * @param incomeRate 要设置的 incomeRate
     */
    public void setIncomeRate(double incomeRate) {
        this.incomeRate = incomeRate;
    }

	/*@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}*/


}
