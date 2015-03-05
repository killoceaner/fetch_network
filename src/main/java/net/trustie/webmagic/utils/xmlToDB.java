package net.trustie.webmagic.utils;

/**
 * Created by gyiang on 2014/8/1.
 */

/**
 纯属本人习惯.仅供参考!
 */
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class xmlToDB {

	/**
	 * 给定XML，解析后插入数据库
	 *
	 * @param filePath
	 *            文件的路径名：例如："F:/creatXml.xml"
	 * @throws ClassNotFoundException
	 *
	 */

	public static void importXml(String filePath) {

		System.out.println("============开始导入============");

		// -------------jdbc代码
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/influx";
			String user = "root";
			String password = "a1passz";
			Connection con = DriverManager.getConnection(url, user, password);
			// 打开事物
			con.setAutoCommit(false);
			// SQL执行语句
			String exeSql = "";
			// 插入值
			String insertValue = "";
			// 插入的列名
			String insertColumName = "";
			// 更新的SQL语句
			String updateSQL = "";
			// SQL操作语句（用来判断数据库里就没有记录等）
			String optionSql = "";
			// 当前记录的ID值
			String rowID = "";
			// ID的列名
			String idName = "";
			int flag = 0;

			// ------------读取XML文件,获得document对象.
			SAXReader reader = new SAXReader();
			reader.setEncoding("Utf-8");
			Document document = null;
			document = reader.read(new File(filePath));
			// -----------获取文档的根节点.
			Element root = document.getRootElement();

			// 遍历所有table节点
			for (Iterator rootIter = root.elementIterator("Table"); rootIter
					.hasNext();) {
				Element tableNode = (Element) rootIter.next();
				// 得到table节点的Name属性值，用来判断属于哪一个数据库表
				String tableName = tableNode.attributeValue("Name");
				// 打印数据库表名
				System.out.println("数据库表名" + tableName);
				// 取得table节点下的所有子节点，并且遍历

				for (Iterator tableIter = tableNode.elementIterator(); tableIter
						.hasNext();) {
					Element row = (Element) tableIter.next();
					// 得到每一条记录里面的数据
					for (Iterator rowIter = row.elementIterator(); rowIter
							.hasNext();) {

						flag++;

						Element col = (Element) rowIter.next();

						if (flag == 1) {

							rowID = col.getText();
							idName = col.getName();

						}

						// 得到当前节点的名字，用来判断属于数据库中的哪一列
						String columName = col.getName();
						// 得到该节点的字段类型
						String columType = col.attributeValue("type");

						// 得到当前节点的值，用来插入数据库中
						// 列值。默认为空字符
						String columValue = "";
						// 判断节点字段的类型，然后转换字段类型
						if (columType.equals("date")) {
							columValue = "to_date(substr('" + col.getText()
									+ "',1,10),'yyyy-MM-dd')";

						} else {
							columValue = "'" + col.getText() + "'";
							// System.out.println(columType);
						}

						// 判断是否为最后一个，如果是则不加','号
						if (rowIter.hasNext()) {
							insertValue += columValue + ",";
							insertColumName += columName + ",";

						} else {
							insertValue += columValue;
							insertColumName += columName;

						}
						// 判断是否为最后一个，如果是则不加','号
						if (rowIter.hasNext()) {
							updateSQL += columName + "=" + columValue + ",";
						} else {

							updateSQL += columName + "=" + columValue;
						}

					}

					optionSql = "select * from " + tableName + " where "
							+ idName + "='" + rowID + "'";
					PreparedStatement optionps = con
							.prepareStatement(optionSql);
					ResultSet opRS = optionps.executeQuery();

					System.out.println(optionSql);

					// 假如数据库里没有记录则插入
					if (!opRS.next()) {

						exeSql = "insert into " + tableName + "("
								+ insertColumName + ")" + "values("
								+ insertValue + ")";
						PreparedStatement ps = con.prepareStatement(exeSql);
						System.out.println("插入");
						System.out.println(exeSql);
						ps.execute();
						opRS.close();
						optionps.close();
						ps.close();

					} else {
						// 假如数据库里有记录则更新
						exeSql = "update " + tableName + " set " + updateSQL
								+ " where " + idName + "=" + "'" + rowID + "'";
						PreparedStatement ps = con.prepareStatement(exeSql);
						System.out.println("更新");
						System.out.println(exeSql);
						ps.execute();
						opRS.close();
						optionps.close();
						ps.close();

					}
					// 插入完一条记录以后清空记录信息
					exeSql = "";
					insertValue = "";
					insertColumName = "";
					updateSQL = "";
					optionSql = "";
					rowID = "";
					idName = "";
					flag = 0;

				}
			}
			con.commit();
			con.close();
			System.out.println("============导入完成============");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		importXml("C:/ProgramData/MySQL/MySQL Server 5.5/data/webmagic/Badges.xml");
	}

}
