package cn.sibat.boot.server.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 类描述：债权实体
 */
@Entity
@Table(name="t_debt_info")
public class Creditor {
		@Id
		@GeneratedValue()
		@Column(name="d_id", nullable=false)
        //	债权ID(标的ID)	int
        private Integer id;

		@Column(name="d_debt_no")
        // 债权编号	varchar(100)
        private String debtNo;//

		@Column(name="d_contract_No")
        // 合同编号	varchar(100)
        private String contractNo;//

		@Column(name="d_debtors_Name")
        // 债务人名称	varchar(100)
        private String debtorsName;//

		@Column(name="d_debtors_Id")
        // 债务人身份证号	varchar(18)
        private String debtorsId;//

		@Column(name="d_loan_Purpose")
        // 借款用途	varchar(500)
        private String loanPurpose;//

		@Column(name="d_loan_Type")
        // 借款类型	varchar(500)
        private String loanType;//
//		@JsonFormat(pattern="yyyy-MM-dd")

		@Column(name="d_loan_Start_Date")
        //	原始借款开始日期	date
        private Date loanStartDate;

		@Column(name="d_loan_Period")
        //	原始借款期限	int
        private int loanPeriod = 0;
//		@JsonFormat(pattern="yyyy-MM-dd")

		@Column(name="d_loan_End_Date")
        //	原始借款到期日期	date
        private Date loanEndDate = new Date(0);

		@Column(name="d_repayment_Style")
        //	还款方式	int
        private int repaymentStyle = 0;

		@Column(name="d_repaymen_Date")
        //	还款日	varchar(100)
        private String repaymenDate;

		@Column(name="d_repaymen_Money")
        //	还款金额	numeric(15,2)
        private double repaymenMoney = 0.0;

		@Column(name="d_debt_Money")
        //	债权金额	numeric(15,2)
        private double debtMoney = 0.0;

		@Column(name="d_debt_Year_Rate")
        //	债权年化利率	numeric(8,4)
        private double debtYearRate = 0.0;

		@Column(name="d_debt_Month_Rate")
        //	债权月利率	numeric(8,4)
        private double debtMonthRate = 0.0;

		@Column(name="d_debt_Transferred_Money")
        //	债权转入金额	numeric(15,2)
        private double debtTransferredMoney;
//		@JsonFormat(pattern="yyyy-MM-dd")

		@Column(name="d_debt_Transferred_Date")
        //	债权转入日期	timestamp
        private Date debtTransferredDate = new Date(0);

		@Column(name="d_debt_Transferred_Period")
        //	债权转入期限	int
        private int debtTransferredPeriod = 0;

//		@JsonFormat(pattern="yyyy-MM-dd")
		@Column(name="d_debt_Ransfer_Out_Date")
        //	债权转出日期	timestamp
        private Date debtRansferOutDate = new Date(0);

		@Column(name="d_creditor")
        //	债权人	varchar(100)
        private String creditor;

		@Column(name="d_debt_Status")
        //	债权状态	int
        private int debtStatus = 0;

		@Column(name="d_borrower_Id")
        //	借款人ID	int
        private int borrowerId = 0;

		@Column(name="d_available_Period")
        //	可用期限	int
        private int availablePeriod = 0;

		@Column(name="d_available_Money")
        //	可用金额	numeric(15,2)
        private double availableMoney = 0.0;

		@Column(name="d_matched_Money")
        //	已匹配金额	numeric(15,2)
        private double matchedMoney = 0.0;

		@Column(name="d_matched_Status")
        //	匹配状态	int  部分匹配11401,  完全匹配11402,   未匹配11403, 正在匹配11404
        private int matchedStatus = 0;

		@Column(name="d_repayment_style_name")
        //还款方式名称
        private String repaymentStyleName;

		@Column(name="d_debt_status_name")
        //债权状态名字
        private String  debtStatusName;

		@Column(name="d_matched_status_name")
        //匹配状态名称
        private String matchedStatusName;

		@Column(name="d_debt_type")
        //标的类型
        private String debtType;

		@Column(name="d_debt_type_name")
        //标的类型名称
        private String debtTypeName;

		//=================================用于前端显示=================================
		@Transient
        //	债权状态
        private String debtStatusDesc;

		@Transient
        //匹配描述
        private String matchedStatusDesc;

		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getDebtNo() {
			return debtNo;
		}
		public void setDebtNo(String debtNo) {
			this.debtNo = debtNo;
		}
		public String getContractNo() {
			return contractNo;
		}
		public void setContractNo(String contractNo) {
			this.contractNo = contractNo;
		}
		public String getDebtorsName() {
			return debtorsName;
		}
		public void setDebtorsName(String debtorsName) {
			this.debtorsName = debtorsName;
		}
		public String getDebtorsId() {
			return debtorsId;
		}
		public void setDebtorsId(String debtorsId) {
			this.debtorsId = debtorsId;
		}
		public String getLoanPurpose() {
			return loanPurpose;
		}
		public void setLoanPurpose(String loanPurpose) {
			this.loanPurpose = loanPurpose;
		}
		public String getLoanType() {
			return loanType;
		}
		public void setLoanType(String loanType) {
			this.loanType = loanType;
		}
		public Date getLoanStartDate() {
			return loanStartDate;
		}
		public void setLoanStartDate(Date loanStartDate) {
			this.loanStartDate = loanStartDate;
		}
		public int getLoanPeriod() {
			return loanPeriod;
		}
		public void setLoanPeriod(int loanPeriod) {
			this.loanPeriod = loanPeriod;
		}
		public Date getLoanEndDate() {
			return loanEndDate;
		}
		public void setLoanEndDate(Date loanEndDate) {
			this.loanEndDate = loanEndDate;
		}
		public int getRepaymentStyle() {
			return repaymentStyle;
		}
		public void setRepaymentStyle(int repaymentStyle) {
			this.repaymentStyle = repaymentStyle;
		}
		public String getRepaymenDate() {
			return repaymenDate;
		}
		public void setRepaymenDate(String repaymenDate) {
			this.repaymenDate = repaymenDate;
		}
		public double getRepaymenMoney() {
			return repaymenMoney;
		}
		public void setRepaymenMoney(double repaymenMoney) {
			this.repaymenMoney = repaymenMoney;
		}
		public double getDebtMoney() {
			return debtMoney;
		}
		public void setDebtMoney(double debtMoney) {
			this.debtMoney = debtMoney;
		}
		public double getDebtYearRate() {
			return debtYearRate;
		}
		public void setDebtYearRate(double debtYearRate) {
			this.debtYearRate = debtYearRate;
		}
		public double getDebtMonthRate() {
			return debtMonthRate;
		}
		public void setDebtMonthRate(double debtMonthRate) {
			this.debtMonthRate = debtMonthRate;
		}
		public double getDebtTransferredMoney() {
			return debtTransferredMoney;
		}
		public void setDebtTransferredMoney(double debtTransferredMoney) {
			this.debtTransferredMoney = debtTransferredMoney;
		}
		public Date getDebtTransferredDate() {
			return debtTransferredDate;
		}
		public void setDebtTransferredDate(Date debtTransferredDate) {
			this.debtTransferredDate = debtTransferredDate;
		}
		public int getDebtTransferredPeriod() {
			return debtTransferredPeriod;
		}
		public void setDebtTransferredPeriod(int debtTransferredPeriod) {
			this.debtTransferredPeriod = debtTransferredPeriod;
		}
		public Date getDebtRansferOutDate() {
			return debtRansferOutDate;
		}
		public void setDebtRansferOutDate(Date debtRansferOutDate) {
			this.debtRansferOutDate = debtRansferOutDate;
		}
		public String getCreditor() {
			return creditor;
		}
		public void setCreditor(String creditor) {
			this.creditor = creditor;
		}
		public int getDebtStatus() {
			return debtStatus;
		}
		public void setDebtStatus(int debtStatus) {
			this.debtStatus = debtStatus;
		}
		public int getBorrowerId() {
			return borrowerId;
		}
		public void setBorrowerId(int borrowerId) {
			this.borrowerId = borrowerId;
		}
		public int getAvailablePeriod() {
			return availablePeriod;
		}
		public void setAvailablePeriod(int availablePeriod) {
			this.availablePeriod = availablePeriod;
		}
		public double getAvailableMoney() {
			return availableMoney;
		}
		public void setAvailableMoney(double availableMoney) {
			this.availableMoney = availableMoney;
		}
		public double getMatchedMoney() {
			return matchedMoney;
		}
		public void setMatchedMoney(double matchedMoney) {
			this.matchedMoney = matchedMoney;
		}
		public int getMatchedStatus() {
			return matchedStatus;
		}
		public void setMatchedStatus(int matchedStatus) {
			this.matchedStatus = matchedStatus;
		}
		public String getDebtStatusDesc() {
			return debtStatusDesc;
		}
		public void setDebtStatusDesc(String debtStatusDesc) {
			this.debtStatusDesc = debtStatusDesc;
		}
		public String getRepaymentStyleName() {
			return repaymentStyleName;
		}
		public void setRepaymentStyleName(String repaymentStyleName) {
			this.repaymentStyleName = repaymentStyleName;
		}
		public String getDebtStatusName() {
			return debtStatusName;
		}
		public void setDebtStatusName(String debtStatusName) {
			this.debtStatusName = debtStatusName;
		}
		public String getMatchedStatusName() {
			return matchedStatusName;
		}
		public void setMatchedStatusName(String matchedStatusName) {
			this.matchedStatusName = matchedStatusName;
		}
		public String getDebtType() {
			return debtType;
		}
		public void setDebtType(String debtType) {
			this.debtType = debtType;
		}
		public String getDebtTypeName() {
			return debtTypeName;
		}
		public void setDebtTypeName(String debtTypeName) {
			this.debtTypeName = debtTypeName;
		}
		/**
		* @return matchedStatusDesc
		*
		*/

		public String getMatchedStatusDesc() {
			return matchedStatusDesc;
		}
		/**
		* @param matchedStatusDesc 要设置的 matchedStatusDesc
		*
		*/
		public void setMatchedStatusDesc(String matchedStatusDesc) {
			this.matchedStatusDesc = matchedStatusDesc;
		}

	@Override public String toString() {
		return "Creditor{" +
				"id=" + id +
				", debtNo='" + debtNo + '\'' +
				", contractNo='" + contractNo + '\'' +
				", debtorsName='" + debtorsName + '\'' +
				", debtorsId='" + debtorsId + '\'' +
				", loanPurpose='" + loanPurpose + '\'' +
				", loanType='" + loanType + '\'' +
				", loanStartDate=" + loanStartDate +
				", loanPeriod=" + loanPeriod +
				", loanEndDate=" + loanEndDate +
				", repaymentStyle=" + repaymentStyle +
				", repaymenDate='" + repaymenDate + '\'' +
				", repaymenMoney=" + repaymenMoney +
				", debtMoney=" + debtMoney +
				", debtYearRate=" + debtYearRate +
				", debtMonthRate=" + debtMonthRate +
				", debtTransferredMoney=" + debtTransferredMoney +
				", debtTransferredDate=" + debtTransferredDate +
				", debtTransferredPeriod=" + debtTransferredPeriod +
				", debtRansferOutDate=" + debtRansferOutDate +
				", creditor='" + creditor + '\'' +
				", debtStatus=" + debtStatus +
				", borrowerId=" + borrowerId +
				", availablePeriod=" + availablePeriod +
				", availableMoney=" + availableMoney +
				", matchedMoney=" + matchedMoney +
				", matchedStatus=" + matchedStatus +
				", repaymentStyleName='" + repaymentStyleName + '\'' +
				", debtStatusName='" + debtStatusName + '\'' +
				", matchedStatusName='" + matchedStatusName + '\'' +
				", debtType='" + debtType + '\'' +
				", debtTypeName='" + debtTypeName + '\'' +
				", debtStatusDesc='" + debtStatusDesc + '\'' +
				", matchedStatusDesc='" + matchedStatusDesc + '\'' +
				'}';
	}
}
