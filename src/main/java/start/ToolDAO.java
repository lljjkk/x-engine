package start;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import octopus.utils.ManagedInstance;
import octopus.utils.ResultSetJsonBuilder;
import octopus.utils.RtSqlException;

public class ToolDAO extends ToolBase {
	@ManagedInstance
	public QueryWalletByType queryWalletByType = null;

	public class QueryWalletByType extends ResultSetJsonBuilder {
		/*--------------- SQL ---------------*\
		select wallet_coin_type,count(*) count from wallet group by wallet_coin_type
		\*-----------------------------------*/
		String SQL = "select wallet_coin_type,count(*) count from wallet group by wallet_coin_type";

		public PreparedStatement statement = null;
		public ResultSet resultSet = null;

		public String f_WalletCoinType;
		public Long f_Count;

		public long rowCount = -1;
		String SQLCount = "select count(*) as record_countfrom xxxxx";
		StringBuilder countSqlBuilder = new StringBuilder(SQLCount);

		public long getRowCount() {
			rowCount = -1;
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			try {
				statement = (PreparedStatement) dbConn.prepareStatement(SQLCount);
				resultSet = statement.executeQuery();
				if (resultSet.next())
					rowCount = resultSet.getLong(1);
				resultSet.close();
				resultSet = null;
				statement.close();
				statement = null;
				return rowCount;
			} catch (SQLException e) {
				throw RtSqlException.create(e);
			}
		}

		/***
		* 
		*/
		public boolean open() {
			// getRowCount();
			try {
				if (statement == null)
					statement = (PreparedStatement) dbConn.prepareStatement(SQL);
				resultSet = statement.executeQuery();
				return true;
			} catch (SQLException e) {
				try {
					if (statement != null)
						statement.close();
				} catch (SQLException e1) {
				}
				statement = null;
				throw RtSqlException.create(e);
			}
		}

		public boolean read() {
			boolean hasNext;
			try {
				hasNext = resultSet.next();
				if (hasNext) {
					int idx = 1;
					f_WalletCoinType = resultSet.getString(idx++);
					f_Count = resultSet.getLong(idx++);
				}
			} catch (SQLException e) {
				throw RtSqlException.create(e);
			}
			return hasNext;
		}

		public void closeData() {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
					throw RtSqlException.create(e);
				}
			resultSet = null;

			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e1) {
					throw RtSqlException.create(e1);
				}
			statement = null;
		}
	}

	@ManagedInstance
	public QueryCoinByType queryCoinByType = null;

	public class QueryCoinByType extends ResultSetJsonBuilder {
		/*--------------- SQL ---------------*\
		select wallet_coin_type,sum(wallet_coin) allCoin from wallet group by wallet_coin_type
		\*-----------------------------------*/
		String SQL = "select wallet_coin_type,sum(wallet_coin) allCoin from wallet group by wallet_coin_type";

		public PreparedStatement statement = null;
		public ResultSet resultSet = null;

		public String f_WalletCoinType;
		public Float f_Allcoin;

		public long rowCount = -1;
		String SQLCount = "select count(*) as record_countfrom xxxxx";
		StringBuilder countSqlBuilder = new StringBuilder(SQLCount);

		public long getRowCount() {
			rowCount = -1;
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			try {
				statement = (PreparedStatement) dbConn.prepareStatement(SQLCount);
				resultSet = statement.executeQuery();
				if (resultSet.next())
					rowCount = resultSet.getLong(1);
				resultSet.close();
				resultSet = null;
				statement.close();
				statement = null;
				return rowCount;
			} catch (SQLException e) {
				throw RtSqlException.create(e);
			}
		}

		/***
		* 
		*/
		public boolean open() {
			// getRowCount();
			try {
				if (statement == null)
					statement = (PreparedStatement) dbConn.prepareStatement(SQL);
				resultSet = statement.executeQuery();
				return true;
			} catch (SQLException e) {
				try {
					if (statement != null)
						statement.close();
				} catch (SQLException e1) {
				}
				statement = null;
				throw RtSqlException.create(e);
			}
		}

		public boolean read() {
			boolean hasNext;
			try {
				hasNext = resultSet.next();
				if (hasNext) {
					int idx = 1;
					f_WalletCoinType = resultSet.getString(idx++);
					f_Allcoin = resultSet.getFloat(idx++);
				}
			} catch (SQLException e) {
				throw RtSqlException.create(e);
			}
			return hasNext;
		}

		public void closeData() {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
					throw RtSqlException.create(e);
				}
			resultSet = null;

			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e1) {
					throw RtSqlException.create(e1);
				}
			statement = null;
		}
	}

	@ManagedInstance
	public QueryStatistics queryStatistics = null;

	public class QueryStatistics extends ResultSetJsonBuilder {
		/*--------------- SQL ---------------*\
		select 'sum_5'  as var_name,count(id) as quantity from cl_user
		union select 'sum_6'  as var_name,COUNT(cl_user_bank_card.user_id) as count from cl_user_bank_card
		union select 'sum_7'  as var_name,count(fin_statistical.user_id) as count from fin_statistical
		union select 'sum_8'  as var_name,SUM(fin_order.operate_num) as allInAmount from fin_order where (fin_order.order_sate_type = 1)
		union select 'sum_9'  as var_name,SUM(fin_statistical.total_amount) as `allAmount` from fin_statistical
		union SELECT 'sum_10'  as var_name,count(DISTINCT user_id) borrowUser from cl_borrow where state!='已创建' and state!='申请信息待确认' and state!='交易失败'
		\*-----------------------------------*/
		String SQL = "select 'sum_5'  as var_name,count(id) as quantity from cl_user\r\n"
				+ "union select 'sum_6'  as var_name,COUNT(cl_user_bank_card.user_id) as count from cl_user_bank_card\r\n"
				+ "union select 'sum_7'  as var_name,count(fin_statistical.user_id) as count from fin_statistical\r\n"
				+ "union select 'sum_8'  as var_name,SUM(fin_order.operate_num) as allInAmount from fin_order where (fin_order.order_sate_type = 1)\r\n"
				+ "union select 'sum_9'  as var_name,SUM(fin_statistical.total_amount) as `allAmount` from fin_statistical\r\n"
				+ "union SELECT 'sum_10'  as var_name,count(DISTINCT user_id) borrowUser from cl_borrow where state!='已创建' and state!='申请信息待确认' and state!='交易失败'";

		public PreparedStatement statement = null;
		public ResultSet resultSet = null;

		public String f_VarName;
		public Float f_Quantity;

		public long rowCount = -1;
		String SQLCount = "select count(*) as record_countfrom xxxxx";
		StringBuilder countSqlBuilder = new StringBuilder(SQLCount);

		public long getRowCount() {
			rowCount = -1;
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			try {
				statement = (PreparedStatement) dbConn.prepareStatement(SQLCount);
				resultSet = statement.executeQuery();
				if (resultSet.next())
					rowCount = resultSet.getLong(1);
				resultSet.close();
				resultSet = null;
				statement.close();
				statement = null;
				return rowCount;
			} catch (SQLException e) {
				throw RtSqlException.create(e);
			}
		}

		/***
		* 
		*/
		public boolean open() {
			// getRowCount();
			try {
				if (statement == null)
					statement = (PreparedStatement) dbConn.prepareStatement(SQL);
				resultSet = statement.executeQuery();
				return true;
			} catch (SQLException e) {
				try {
					if (statement != null)
						statement.close();
				} catch (SQLException e1) {
				}
				statement = null;
				throw RtSqlException.create(e);
			}
		}

		public boolean read() {
			boolean hasNext;
			try {
				hasNext = resultSet.next();
				if (hasNext) {
					int idx = 1;
					f_VarName = resultSet.getString(idx++);
					f_Quantity = resultSet.getFloat(idx++);
				}
			} catch (SQLException e) {
				throw RtSqlException.create(e);
			}
			return hasNext;
		}

		public void closeData() {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
					throw RtSqlException.create(e);
				}
			resultSet = null;

			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e1) {
					throw RtSqlException.create(e1);
				}
			statement = null;
		}
	}

	@ManagedInstance
	public QuerySum11 querySum11 = null;

	public class QuerySum11 extends ResultSetJsonBuilder {
		/*--------------- SQL ---------------*\
		select product_id,sum(coin) allAmount from cl_borrow where state='待还款' or state='还款信息待确认' or state='还款信息已确认' or state='打包中' or state='已还款' or state='已关闭' group by product_id
		\*-----------------------------------*/
		String SQL = "select product_id,sum(coin) allAmount from cl_borrow where state='待还款' or state='还款信息待确认' or state='还款信息已确认' or state='打包中' or state='已还款' or state='已关闭' group by product_id";

		public PreparedStatement statement = null;
		public ResultSet resultSet = null;

		public Integer f_CoinType;
		public Float f_Allamount;

		public long rowCount = -1;
		String SQLCount = "select count(*) as record_countfrom xxxxx";
		StringBuilder countSqlBuilder = new StringBuilder(SQLCount);

		public long getRowCount() {
			rowCount = -1;
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			try {
				statement = (PreparedStatement) dbConn.prepareStatement(SQLCount);
				resultSet = statement.executeQuery();
				if (resultSet.next())
					rowCount = resultSet.getLong(1);
				resultSet.close();
				resultSet = null;
				statement.close();
				statement = null;
				return rowCount;
			} catch (SQLException e) {
				throw RtSqlException.create(e);
			}
		}

		/***
		* 
		*/
		public boolean open() {
			// getRowCount();
			try {
				if (statement == null)
					statement = (PreparedStatement) dbConn.prepareStatement(SQL);
				resultSet = statement.executeQuery();
				return true;
			} catch (SQLException e) {
				try {
					if (statement != null)
						statement.close();
				} catch (SQLException e1) {
				}
				statement = null;
				throw RtSqlException.create(e);
			}
		}

		public boolean read() {
			boolean hasNext;
			try {
				hasNext = resultSet.next();
				if (hasNext) {
					int idx = 1;
					f_CoinType = resultSet.getInt(idx++);
					f_Allamount = resultSet.getFloat(idx++);
				}
			} catch (SQLException e) {
				throw RtSqlException.create(e);
			}
			return hasNext;
		}

		public void closeData() {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
					throw RtSqlException.create(e);
				}
			resultSet = null;

			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e1) {
					throw RtSqlException.create(e1);
				}
			statement = null;
		}
	}

	@ManagedInstance
	public QuerySum12 querySum12 = null;

	public class QuerySum12 extends ResultSetJsonBuilder {
		/*--------------- SQL ---------------*\
		select 
		  cl_borrow.product_id,
		  sum(coin) as allAmount
		from
		  cl_borrow
		where
		  (state = '待还款') or 
		  (state = '还款信息待确认') or 
		  (state = '还款信息已确认') or 
		  (state = '打包中')
		group by
		  cl_borrow.product_id
		\*-----------------------------------*/
		String SQL = "select \r\n" + "  cl_borrow.product_id,\r\n" + "  sum(coin) as allAmount\r\n" + "from\r\n"
				+ "  cl_borrow\r\n" + "where\r\n" + "  (state = '待还款') or \r\n" + "  (state = '还款信息待确认') or \r\n"
				+ "  (state = '还款信息已确认') or \r\n" + "  (state = '打包中')\r\n" + "group by\r\n"
				+ "  cl_borrow.product_id\r\n";

		public PreparedStatement statement = null;
		public ResultSet resultSet = null;

		public Integer f_CoinType;
		public Float f_Allamount;

		public long rowCount = -1;
		String SQLCount = "select count(*) as record_count\r\n" + "from\r\n" + "  cl_borrow\r\n" + "where\r\n"
				+ "  (state = '待还款') or \r\n" + "  (state = '还款信息待确认') or \r\n" + "  (state = '还款信息已确认') or \r\n"
				+ "  (state = '打包中')\r\n" + "group by\r\n" + "  cl_borrow.product_id";
		StringBuilder countSqlBuilder = new StringBuilder(SQLCount);

		public long getRowCount() {
			rowCount = -1;
			PreparedStatement statement = null;
			ResultSet resultSet = null;
			try {
				statement = (PreparedStatement) dbConn.prepareStatement(SQLCount);
				resultSet = statement.executeQuery();
				if (resultSet.next())
					rowCount = resultSet.getLong(1);
				resultSet.close();
				resultSet = null;
				statement.close();
				statement = null;
				return rowCount;
			} catch (SQLException e) {
				throw RtSqlException.create(e);
			}
		}

		/***
		* 
		*/
		public boolean open() {
			// getRowCount();
			try {
				if (statement == null)
					statement = (PreparedStatement) dbConn.prepareStatement(SQL);
				resultSet = statement.executeQuery();
				return true;
			} catch (SQLException e) {
				try {
					if (statement != null)
						statement.close();
				} catch (SQLException e1) {
				}
				statement = null;
				throw RtSqlException.create(e);
			}
		}

		public boolean read() {
			boolean hasNext;
			try {
				hasNext = resultSet.next();
				if (hasNext) {
					int idx = 1;
					f_CoinType = resultSet.getInt(idx++);
					f_Allamount = resultSet.getFloat(idx++);
				}
			} catch (SQLException e) {
				throw RtSqlException.create(e);
			}
			return hasNext;
		}

		public void closeData() {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException e) {
					throw RtSqlException.create(e);
				}
			resultSet = null;

			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e1) {
					throw RtSqlException.create(e1);
				}
			statement = null;
		}
	}
}
